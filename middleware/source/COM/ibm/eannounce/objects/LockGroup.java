//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LockGroup.java,v $
// Revision 1.35  2009/05/19 15:58:13  wendy
// Support dereference for memory release
//
// Revision 1.34  2009/05/13 15:58:00  wendy
// check for rs!=null before close()
//
// Revision 1.33  2005/03/04 18:40:11  dave
// Jtest
//
// Revision 1.32  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.31  2004/08/02 19:34:01  joan
// add CGActionItem
//
// Revision 1.30  2003/05/15 16:17:17  joan
// work on lock
//
// Revision 1.29  2002/12/16 22:57:58  joan
// fix bugs
//
// Revision 1.28  2002/12/16 20:51:25  joan
// work on softlock
//
// Revision 1.27  2002/12/13 21:30:52  joan
// fix bugs
//
// Revision 1.26  2002/12/13 20:41:01  joan
// fix for addition column in Softlock table
//
// Revision 1.25  2002/11/21 00:50:37  joan
// adjust softlock
//
// Revision 1.24  2002/11/20 20:57:49  joan
// adjust lockaction
//
// Revision 1.23  2002/11/20 01:09:45  joan
// fix bugs
//
// Revision 1.22  2002/11/19 23:26:56  joan
// fix hasLock method
//
// Revision 1.21  2002/11/19 18:27:43  joan
// adjust lock, unlock
//
// Revision 1.20  2002/11/19 00:22:28  joan
// fix compile
//
// Revision 1.19  2002/11/19 00:06:25  joan
// adjust isLocked method
//
// Revision 1.18  2002/11/18 22:03:22  joan
// fixing softlock
//
// Revision 1.17  2002/10/07 17:41:39  joan
// add getLockGroup method
//
// Revision 1.16  2002/10/04 17:59:33  dave
// System.out.messages
//
// Revision 1.15  2002/07/18 18:03:31  joan
// create LockList table
//
// Revision 1.14  2002/05/15 16:49:18  joan
// fix throwing exception
//
// Revision 1.13  2002/05/15 16:40:56  joan
// fix removeLockItem
//
// Revision 1.12  2002/05/13 17:48:17  joan
// debug lock
//
// Revision 1.11  2002/05/13 16:42:08  joan
// fixing unlock method
//
// Revision 1.10  2002/05/10 21:18:11  joan
// compiling error
//
// Revision 1.9  2002/05/10 20:45:53  joan
// fixing lock
//
// Revision 1.8  2002/05/10 15:31:33  joan
// add commit()
//
// Revision 1.7  2002/05/07 22:36:14  joan
// add logic to lock when persistence or noboby has the lock
//
// Revision 1.6  2002/04/22 23:26:55  joan
// working on unlock
//
// Revision 1.5  2002/04/22 21:12:41  joan
// move stuffs in createLock to LockGroup constructor
//
// Revision 1.4  2002/04/22 16:54:46  joan
// working on lock
//
// Revision 1.3  2002/04/19 22:34:06  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.2  2002/04/17 21:46:39  joan
// add methods to clear lock
//
// Revision 1.1  2002/04/16 20:56:04  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
* This object all the LockItems For a given e-announce EntityItem
*/
public class LockGroup extends EANMetaEntity {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strEntityType = null;
    private int m_iEntityID = 0;
    /**
     * FIELD
     */
    public static final int LOCK_NORMAL = 0;
    /**
     * FIELD
     */
    public static final int LOCK_PERSISTENT = 1;
    /**
     * FIELD
     */
    public static final int LOCK_VE = 2;
    /**
     * FIELD
     */
    public static final int ALLLOCK = 3;
    
    protected void dereference(){
    	m_strEntityType = null;
    	for (int i=0; i<getLockItemCount(); i++){
    		LockItem li = getLockItem(i);
    		li.dereference();
    	}
    	super.dereference();
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * LockGroup
     *
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockGroup(Profile _prof, String _strEntityType, int _iEntityID) throws MiddlewareRequestException {
        super(null, _prof, _strEntityType + _iEntityID);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
    }

    /**
     * LockGroup
     *
     * @param _db
     * @param _prof
     * @param _lockEI
     * @param _ei
     * @param _strLockOwner
     * @param _iLockType
     * @param _bCreateLock
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockGroup(Database _db, Profile _prof, EntityItem _lockEI, EntityItem _ei, String _strLockOwner, int _iLockType, boolean _bCreateLock) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _ei.getEntityType() + _ei.getEntityID());

        String strMethod = "LockGroup constructor";

        _db.test(_iLockType >= LOCK_NORMAL && _iLockType <= LOCK_VE, "Wrong input lock type.");

        try {
 
            String strLockEntityType = null;
            int iLockEntityID = 0;

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            // A copy of the current time
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();

            String strEnterprise = _prof.getEnterprise();
            int iOPWGID = _prof.getOPWGID();
            int iTranID = _prof.getTranID();

            boolean bNormalLock = true;

            String strEntityType = _ei.getEntityType();
            int iEntityID = _ei.getEntityID();
            
            setEntityType(strEntityType);
            setEntityID(iEntityID);
 
            strLockEntityType = _lockEI.getEntityType();
            iLockEntityID = _lockEI.getEntityID();

            // The stored procedure ReturnStatus

            if (_iLockType != LockGroup.LOCK_NORMAL) {
                bNormalLock = false;
            }

            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.debug(D.EBUG_DETAIL, "LockGroup: Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "LockGroup: OPENID: " + iOPWGID);
            _db.debug(D.EBUG_DETAIL, "LockGroup:" + strEntityType + ":" + iEntityID);
            _db.test(strEntityType != null, "entityType is null");
            _db.test(iEntityID > 0, "entityID <= 0");

            try {
                rs = _db.callGBL2015(returnStatus, strEnterprise, strEntityType, iEntityID);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                int tempID = rdrs.getColumnInt(j, 5);
                String strOwnerEntityType = rdrs.getColumn(j, 8);
                int iOwnerEntityID = rdrs.getColumnInt(j, 9);
                String strLockOwner = rdrs.getColumn(j, 10);
                LockItem li = new LockItem(this, strEntityType, iEntityID, strOwnerEntityType, iOwnerEntityID, strLockOwner, tempID);
             
                _db.debug(D.EBUG_SPEW, "gbl2015 answers:" + strEntityType + ":" + iEntityID + ":" + strOwnerEntityType + ":" + iOwnerEntityID + ":" + strLockOwner);

                //create a LockItem for each lock record
                li.setAgentName(rdrs.getColumn(j, 2).trim());
                li.setRoleCode(rdrs.getColumn(j, 3).trim());
                li.setLockedOn(rdrs.getColumn(j, 4).trim());
                li.setLockType(rdrs.getColumnInt(j, 6));
                li.setLockOwnerDesc(rdrs.getColumn(j, 11));

                //add to LockGroup
                putLockItem(li);
            }

            if (_bCreateLock) {

                String profKey = strEntityType + iEntityID + strLockEntityType + iLockEntityID + _strLockOwner;
                LockItem profLI = getLockItem(profKey);

                if (profLI == null) {
                    // LockItem for this lock owner isn't in lock table, create one
                    // only when persistence, VELock are requested or nobody has the lock
                    if (_iLockType != LockGroup.LOCK_NORMAL || (getLockItemCount() == 0)) {
                        profLI = new LockItem(this, strEntityType, iEntityID, strLockEntityType, iLockEntityID, _strLockOwner, iOPWGID);

                        // lets lock it!
                        _db.callGBL2031(returnStatus, strEnterprise, strEntityType, iEntityID, _iLockType, strLockEntityType, iLockEntityID, _strLockOwner, iOPWGID, iTranID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                        profLI.setAgentName("Get the Agent Name!");
                        profLI.setRoleCode(_prof.getRoleCode());
                        profLI.setLockedOn(strNow);
                        profLI.setLockType(_iLockType);
                        profLI.setLockOwner(_strLockOwner);
                    }
                } else {
                    if (profLI.isNormalLock() && _iLockType == LockGroup.LOCK_PERSISTENT) {

                        // upgrade the lock to persistent
                        _db.debug(D.EBUG_DETAIL, "upgrading lock to " + _iLockType + " for: " + strEntityType + ", " + iEntityID + ", " + strLockEntityType + ":" + iLockEntityID + ":" + iOPWGID);
                        _db.callGBL5031(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, _iLockType);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                        profLI.setLockType(_iLockType);
                    }
                }

                //add the LockItem in LockGroup
                if (profLI != null) {
                    putLockItem(profLI);
                }
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();
            
            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);
            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {
            // Free any statement
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // DO NOT FREE THE CONNECTION
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
    }

    /**
     * removeLockItem
     *
     * @param _db
     * @param _ei
     * @param _prof
     * @param _lockOwnerEI
     * @param _strLockOwner
     * @param _iLockType
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public LockGroup removeLockItem(Database _db, EntityItem _ei, Profile _prof, EntityItem _lockOwnerEI, String _strLockOwner, int _iLockType) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();
        String key = strEntityType + iEntityID + _lockOwnerEI.getEntityType() + _lockOwnerEI.getEntityID() + _strLockOwner;
        LockItem li = getLockItem(key);

        if (li != null) {
            if (_iLockType != LockGroup.ALLLOCK) {
                if (_iLockType == LockGroup.LOCK_NORMAL && !li.isNormalLock()) {
                    return this;
                } else if (_iLockType == LockGroup.LOCK_PERSISTENT && !li.isPersistentLock()) {
                    return this;
                } else if (_iLockType == LockGroup.LOCK_VE && !li.isVELock()) {
                    return this;
                }
            }

            _db.clearLock(_prof, li.getEntityType(), li.getEntityID(), li.getLockEntityType(), li.getLockEntityID(), li.getLockOwner());
            removeLockItem(key);
        }
        return this;
    }

    /**
     * reRemoveLockItem
     *
     * @param _rdi
     * @param _ei
     * @param _prof
     * @param _lockOwnerEI
     * @param _strLockOwner
     * @param _iLockType
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public LockGroup reRemoveLockItem(RemoteDatabaseInterface _rdi, EntityItem _ei, Profile _prof, EntityItem _lockOwnerEI, String _strLockOwner, int _iLockType) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();
        String key = strEntityType + iEntityID + _lockOwnerEI.getEntityType() + _lockOwnerEI.getEntityID() + _strLockOwner;
        LockItem li = getLockItem(key);

        if (li != null) {
            if (_iLockType != LockGroup.ALLLOCK) {
                if (_iLockType == LockGroup.LOCK_NORMAL && !li.isNormalLock()) {
                    return this;
                } else if (_iLockType == LockGroup.LOCK_PERSISTENT && !li.isPersistentLock()) {
                    return this;
                } else if (_iLockType == LockGroup.LOCK_VE && !li.isVELock()) {
                    return this;
                }
            }

            _rdi.clearLock(_prof, li.getEntityType(), li.getEntityID(), li.getLockEntityType(), li.getLockEntityID(), li.getLockOwner());
            removeLockItem(key);
        }

        return this;
    }

    /**
     * removeLockItem
     *
     * @param _db
     * @param _prof
     * @param _li
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public LockGroup removeLockItem(Database _db, Profile _prof, LockItem _li) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        if (_li != null) {
            String key = _li.getKey();
            _db.clearLock(_prof, _li.getEntityType(), _li.getEntityID(), _li.getLockEntityType(), _li.getLockEntityID(), _li.getLockOwner());
            removeLockItem(key);
        }

        return this;
    }

    /**
     * reRemoveLockItem
     *
     * @param _rdi
     * @param _prof
     * @param _li
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public LockGroup reRemoveLockItem(RemoteDatabaseInterface _rdi, Profile _prof, LockItem _li) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        if (_li != null) {
            String key = _li.getKey();
            _rdi.clearLock(_prof, _li.getEntityType(), _li.getEntityID(), _li.getLockEntityType(), _li.getLockEntityID(), _li.getLockOwner());
            removeLockItem(key);
        }

        return this;
    }

    /**
     * putLockItem
     *
     * @param _li
     *  @author David Bigelow
     */
    public void putLockItem(LockItem _li) {
        putData(_li);
    }

    /**
     * returns the EANList that contains all the tracked LockItems
     *
     * @return EANList
     */
    public EANList getLockItem() {
        return getData();
    }

    /**
     * getLockItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public LockItem getLockItem(int _i) {
        return (LockItem) getData(_i);
    }

    /*
    * Return the lockitem in the group based upon the passed Key
    * @param str (EntityType + EntityID + OwnerEntityType + OwnerEntityID)
    */
    /**
     * getLockItem
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public LockItem getLockItem(String _str) {
        return (LockItem) getData(_str);
    }

    /*
    * Resets the LockItems in this group
    */
    /**
     * removeLockItem
     *
     *  @author David Bigelow
     */
    public void removeLockItem() {
        resetData();
    }

    /*
    * Removes the lockitem in the group based upon the passed Key
    * @param str (EntityType + EntityID + OwnerEntityType + OwnerEntityID)
    */
    /**
     * removeLockItem
     *
     * @param _str
     *  @author David Bigelow
     */
    public void removeLockItem(String _str) {
        getData().remove(_str);
    }

    /*
    * Removes the lockgroup in the list based upon the passed index
    * @param i index
    */
    /**
     * removeLockItem
     *
     * @param _i
     *  @author David Bigelow
     */
    public void removeLockItem(int _i) {
        getData().remove(_i);
    }

    /**
     * getLockItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getLockItemCount() {
        return getDataCount();
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getEntityID
     *
     * @return
     *  @author David Bigelow
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    /**
     * setEntityID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setEntityID(int _i) {
        m_iEntityID = _i;
    }

    /**
     * setEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     * hasExclusiveLock
     *
     * @param _lockOwnerEI
     * @param _strLockOwner
     * @param _prof
     * @return
     *  @author David Bigelow
     */
    public boolean hasExclusiveLock(EntityItem _lockOwnerEI, String _strLockOwner, Profile _prof) {
        String liKey = m_strEntityType + m_iEntityID + _lockOwnerEI.getEntityType() + _lockOwnerEI.getEntityID() + _strLockOwner;
        if (getLockItemCount() == 1 && getLockItem(liKey) != null) {
            return true;
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "LockGroup:" + getKey() + ":");
        if (!_bBrief) {
            for (int i = 0; i < getLockItemCount(); i++) {
                LockItem li = getLockItem(i);
                strbResult.append(NEW_LINE + "i: " + i + li.dump(_bBrief));
            }
        }

        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LockGroup.java,v 1.35 2009/05/19 15:58:13 wendy Exp $";
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < getLockItemCount(); j++) {
            LockItem li = getLockItem(j);
            sb.append(getKey() + " owned by " + li.getLockEntityType() + li.getLockEntityID());
            if (li.isVELock()) {
                sb.append(", " + li.getLockOwner() + " (" + li.getLockOwnerDesc() + ")");
            }
            sb.append(", locked on " + li.getLockedOn() + " by " + li.getAgentName() + ":" + li.getOPWGID() + ":" + li.getRoleCode() + NEW_LINE + "");
        }
        return sb.toString();
    }

}
