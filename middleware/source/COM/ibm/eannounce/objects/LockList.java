// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: LockList.java,v $
// Revision 1.91  2009/05/19 15:58:50  wendy
// Support dereference for memory release
//
// Revision 1.90  2007/04/25 20:39:26  wendy
// log stacktraces
//
// Revision 1.89  2007/04/25 15:49:30  wendy
// MN31595506 "My Persistent Locks" function wasn't showing anything
//
// Revision 1.88  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.87  2005/03/04 19:02:21  dave
// more Jtest
//
// Revision 1.86  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.85  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.84  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.83  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.82  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.81  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.80  2003/08/28 16:28:07  joan
// adjust link method to have link option
//
// Revision 1.79  2003/08/14 22:38:33  dave
// remove trace statements dumpstacks
//
// Revision 1.78  2003/08/14 21:41:07  dave
// syntax
//
// Revision 1.77  2003/08/14 21:29:36  dave
// Lock Tracing
//
// Revision 1.76  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.75  2003/05/15 16:51:47  joan
// fix compile
//
// Revision 1.74  2003/05/15 16:46:14  joan
// fix compile
//
// Revision 1.73  2003/05/15 16:30:00  joan
// fix compile
//
// Revision 1.72  2003/05/15 16:17:17  joan
// work on lock
//
// Revision 1.71  2003/05/14 23:52:06  joan
// fix compile
//
// Revision 1.70  2003/05/14 23:44:44  joan
// fix compile
//
// Revision 1.69  2003/05/14 23:32:13  joan
// add getAllSoftLockforWGID method
//
// Revision 1.68  2003/04/30 23:24:23  joan
// fix fb 24413
//
// Revision 1.67  2003/01/21 00:20:36  joan
// adjust link method to test VE lock
//
// Revision 1.66  2003/01/14 22:05:05  joan
// adjust removeLink method
//
// Revision 1.65  2003/01/08 21:44:05  joan
// add getWhereUsedList
//
// Revision 1.64  2002/12/16 23:14:26  joan
// fix bug
//
// Revision 1.63  2002/12/16 20:59:50  joan
// fix error
//
// Revision 1.62  2002/12/16 20:51:25  joan
// work on softlock
//
// Revision 1.61  2002/12/13 21:30:52  joan
// fix bugs
//
// Revision 1.60  2002/12/13 20:41:01  joan
// fix for addition column in Softlock table
//
// Revision 1.59  2002/12/06 19:25:46  joan
// add new contructor for get locklist by lockentity
//
// Revision 1.58  2002/11/26 22:08:13  joan
// add get lockitem by lockentity
//
// Revision 1.57  2002/11/21 16:52:27  joan
// fix error
//
// Revision 1.56  2002/11/21 16:36:38  joan
// fix gbl2015 to return lockentitytype and id
//
// Revision 1.55  2002/11/21 01:33:11  joan
// fix dump method
//
// Revision 1.54  2002/11/21 00:50:38  joan
// adjust softlock
//
// Revision 1.53  2002/11/19 23:26:56  joan
// fix hasLock method
//
// Revision 1.52  2002/11/19 00:33:13  joan
// fix compile errors
//
// Revision 1.51  2002/11/19 00:22:29  joan
// fix compile
//
// Revision 1.50  2002/11/15 23:50:29  joan
// fix bug
//
// Revision 1.49  2002/11/15 23:12:05  joan
// take out entity name
//
// Revision 1.48  2002/10/30 22:39:13  dave
// more cleanup
//
// Revision 1.47  2002/10/30 22:36:20  dave
// clean up
//
// Revision 1.46  2002/10/30 22:02:34  dave
// added exception throwing to commit
//
// Revision 1.45  2002/10/29 00:02:55  dave
// backing out row commit for 1.1
//
// Revision 1.44  2002/10/28 23:49:15  dave
// attempting the first commit with a row index
//
// Revision 1.43  2002/10/18 20:18:53  joan
// add isMatrixEditable method
//
// Revision 1.42  2002/10/09 21:32:57  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.41  2002/09/27 17:11:00  dave
// made addRow a boolean
//
// Revision 1.40  2002/07/23 17:24:07  joan
// fix getLockList
//
// Revision 1.39  2002/07/23 16:30:46  joan
// fix lock
//
// Revision 1.38  2002/07/23 16:05:10  joan
// fix errors
//
// Revision 1.37  2002/07/23 15:50:15  joan
// fix getAllSoftLock
//
// Revision 1.36  2002/07/19 23:35:56  joan
// fix getSoftLock method
//
// Revision 1.35  2002/07/19 22:24:40  joan
// fix locklist
//
// Revision 1.34  2002/07/18 18:17:07  joan
// work on table
//
// Revision 1.33  2002/07/18 18:03:31  joan
// create LockList table
//
// Revision 1.32  2002/05/13 16:42:08  joan
// fixing unlock method
//
// Revision 1.31  2002/05/10 21:06:20  joan
// compiling errors
//
// Revision 1.30  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.29  2002/04/25 16:14:59  dave
// making sure that we send only the entity item up as oppossed
// to the whole entitylist
//
// Revision 1.28  2002/04/23 23:58:25  joan
// working on entityid < 0
//
// Revision 1.27  2002/04/23 23:43:34  joan
// working on entityid < 0
//
// Revision 1.26  2002/04/23 22:44:31  joan
// debug
//
// Revision 1.25  2002/04/22 23:26:55  joan
// working on unlock
//
// Revision 1.24  2002/04/22 22:35:58  joan
// fix null pointer
//
// Revision 1.23  2002/04/22 22:18:23  joan
// working on unlock
//
// Revision 1.22  2002/04/22 21:38:49  joan
// debug
//
// Revision 1.21  2002/04/22 21:12:41  joan
// move stuffs in createLock to LockGroup constructor
//
// Revision 1.20  2002/04/22 20:17:59  joan
// working on unlock
//
// Revision 1.19  2002/04/22 17:12:17  joan
// import exception
//
// Revision 1.18  2002/04/22 16:54:46  joan
// working on lock
//
// Revision 1.17  2002/04/19 22:34:05  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.16  2002/04/19 20:55:32  joan
// fixing errors
//
// Revision 1.15  2002/04/19 20:48:05  joan
// fixing compiling error
//
// Revision 1.14  2002/04/19 20:13:54  joan
// working on lock
//
// Revision 1.13  2002/04/18 18:29:28  joan
// bring back the part of getting child EntityItem
//
// Revision 1.12  2002/04/18 16:50:09  joan
// debugging
//
// Revision 1.11  2002/04/17 23:38:56  joan
// fixing bugs
//
// Revision 1.10  2002/04/17 22:43:54  joan
// fixing bugs
//
// Revision 1.9  2002/04/17 21:46:39  joan
// add methods to clear lock
//
// Revision 1.8  2002/04/17 20:07:06  joan
// error
//
// Revision 1.7  2002/04/17 19:05:58  joan
// fixing error
//
// Revision 1.6  2002/04/17 18:13:34  joan
// fixing createLock method and add lockgroup methods in entityitem
//
// Revision 1.5  2002/04/17 00:02:32  joan
// syntax
//
// Revision 1.4  2002/04/16 23:54:12  joan
// syntax
//
// Revision 1.3  2002/04/16 23:44:09  joan
// add lock method
//
// Revision 1.2  2002/04/16 21:20:07  joan
// syntax
//
// Revision 1.1  2002/04/16 20:56:04  joan
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 * LockList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LockList extends EANMetaEntity implements EANTableWrapper {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private EANList m_egList = new EANList();

    public void dereference(){
    	if (m_egList!=null){
    		for (int i=0; i<m_egList.size(); i++){
    			EntityGroup mt = (EntityGroup) m_egList.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_egList.clear();
    		m_egList = null;
    	}
    	
    	for (int i=0; i<getLockGroupCount(); i++) {
    		LockGroup lg = getLockGroup(i);
    		lg.dereference();
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
     * Creates a new LockList for the given Profile
     *
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public LockList(Profile _prof) throws MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _prof.getOPWGID() + "");
    }

    /**
     * LockList
     *
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockList(Database _db, Profile _prof) throws MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _prof.getOPWGID() + "");
        try {
            getAllSoftLocksForOPWGID(_db, _prof);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LockList
     *
     * @param _db
     * @param _prof
     * @param _lockEI
     * @param _strLockOwner
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockList(Database _db, Profile _prof, EntityItem _lockEI, String _strLockOwner) throws MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _lockEI.getKey());
        try {
            getAllSoftLocksForLockEntity(_db, _prof, _lockEI, _strLockOwner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * putLockGroup
     *
     * @param _lg
     *  @author David Bigelow
     */
    public void putLockGroup(LockGroup _lg) {
        //System.out.println("TRACE:" + _lg.dump(false));
        //java.lang.Thread.currentThread().dumpStack();
        putData(_lg);
    }
    /**
     * returns the number of SoftLockGroups in this list
     *
     * @return int
     */
    public int getLockGroupCount() {
        return getDataCount();
    }

    /**
     * returns the EANList that contains all the tracked LockGroups
     *
     * @return EANList
     */
    public EANList getLockGroup() {
        return getData();
    }

    /**
     * returns the LockGroup at index i
     *
     * @return LockGroup
     * @param _i
     */
    public LockGroup getLockGroup(int _i) {
        return (LockGroup) getData(_i);
    }

    /**
     * returns the LockGroup based upon the passed Key (EntityType + EntityID)
     *
     * @return LockGroup
     * @param _str
     */
    public LockGroup getLockGroup(String _str) {
        return (LockGroup) getData(_str);
    }

    /**
     * returns the LockGroup based upon the passed EntityItem
     *
     * @return LockGroup
     * @param _ei
     */
    public LockGroup getLockGroup(EntityItem _ei) {
        return (LockGroup) getData(_ei.getKey());
    }

    /*
    * Resets the LockGroups in this list
    */
    /**
     * removeLockGroup
     *
     *  @author David Bigelow
     */
    public void removeLockGroup() {
        resetData();
    }

    /*
    * Removes the lockgroup in the list based upon the passed Key
    * @param str (EntityType + EntityID)
    */
    /**
     * removeLockGroup
     *
     * @param _str
     *  @author David Bigelow
     */
    public void removeLockGroup(String _str) {
        //java.lang.Thread.currentThread().dumpStack();
        getData().remove(_str);
    }

    /*
    * Removes the lockgroup in the list based upon the passed index
    * @param i index
    */
    /**
     * removeLockGroup
     *
     * @param _i
     *  @author David Bigelow
     */
    public void removeLockGroup(int _i) {
        //java.lang.Thread.currentThread().dumpStack();
        getData().remove(_i);
    }

    /*
    * Removes the lockgroup in the list based upon EntityItem's getKey
    */
    /**
     * removeLockGroup
     *
     * @param _ei
     *  @author David Bigelow
     */
    public void removeLockGroup(EntityItem _ei) {
        getData().remove(_ei.getKey());
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LockList.java,v 1.91 2009/05/19 15:58:50 wendy Exp $";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("LockList:" + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getLockGroupCount(); i++) {
                LockGroup lg = getLockGroup(i);
                strbResult.append(NEW_LINE + lg.dump(_bBrief));
            }
        }
        return new String(strbResult);
    }

    private void getAllSoftLocksForOPWGID(Database _db, Profile _prof) throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException {

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        String strMethod = "LockList getAllSoftLocksforOPWGID";
        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        m_egList = new EANList();

        try {
            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.test(strEnterprise != null, "enterprise is null");
            _db.test(iOPWGID > 0, "openID <= 0");
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForOPWGID:Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForOPWGID:OPENID: " + iOPWGID);

            try {
                rs = _db.callGBL5015(returnStatus, strEnterprise, iOPWGID);
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
/*
    [ (returndatarow)
      GEODATE
      2
      Geography Dates
      Tim Ragosta
      GFS
      2007-04-03 16:13:51.218452
      2298
      0
      OPWG
      2298
      2298
      OPWG2298
      N/A
    ]
*/
            for (int j = 0; j < rdrs.size(); j++) {

                LockGroup lg = null;
                LockItem li = null;

                int ii = 0;
                String strEntityType = rdrs.getColumn(j, ii++);
                int iEntityID = rdrs.getColumnInt(j, ii++);
                String strEntityDescription = rdrs.getColumn(j, ii++);
                String strAgentName = rdrs.getColumn(j, ii++);
                String strRoleCode = rdrs.getColumn(j, ii++);
                String strLockedOn = rdrs.getColumn(j, ii++);
                // need to step up the column by one
//                int ij = ii++; MN31595506 was getting java.lang.NumberFormatException: OPWG because it wasnt incrementing
                int ij = ++ii;
                int iLockType = rdrs.getColumnInt(j, ij++);
                String strLockEntityType = rdrs.getColumn(j, ij++);
                int iLockEntityID = rdrs.getColumnInt(j, ij++);
                int tempOPWGID = rdrs.getColumnInt(j, ij++);
                String strLockOwner = rdrs.getColumn(j, ij++);
                String strLockOwnerDesc = rdrs.getColumn(j, ij++);

                _db.debug(D.EBUG_SPEW, "gbl5015 answers:" + strEntityType + ":" + iEntityID + ":" + strAgentName + ":" + strRoleCode + ":" + strLockedOn + ":" + iLockType + ":" + strLockEntityType + ":" + iLockEntityID + ":" + strLockOwner);

                //create the LockGroup
                lg = getLockGroup(strEntityType + iEntityID);
                if (lg == null) {
                    lg = new LockGroup(_prof, strEntityType, iEntityID);
                    putLockGroup(lg);
                }

                li = new LockItem(lg, strEntityType, iEntityID, strLockEntityType, iLockEntityID, strLockOwner, tempOPWGID);
                li.setEntityDescription(strEntityDescription);
                li.setAgentName(strAgentName);
                li.setRoleCode(strRoleCode);
                li.setLockedOn(strLockedOn);
                li.setLockType(iLockType);
                li.setLockOwnerDesc(strLockOwnerDesc);
                li.setEntityDisplayName(getEntityDisplayName(_db, _prof, strEntityType, iEntityID));
                lg.putLockItem(li);
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {

            // Free any statement
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
    }

    private void getAllSoftLocksForLockEntity(Database _db, Profile _prof, EntityItem _lockEI, String _strLockOwner) throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException {

        String strMethod = "LockList getAllSoftLocksforLockEntity";

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;

        String strMyLockEntityType = null;
        int iMyLockEntityID = 0;

        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        ReturnDataResultSet rdrs = null;
        m_egList = new EANList();

        try {

            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.test(strEnterprise != null, "enterprise is null");
            _db.test(iOPWGID > 0, "openID <= 0");
            _db.test(_lockEI != null, " _lockEI is null");

            strMyLockEntityType = _lockEI.getEntityType();
            iMyLockEntityID = _lockEI.getEntityID();

            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForLockEntity:Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForLockEntity:strLockEntityType: " + strMyLockEntityType);
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForLockEntity:iLockEntityID: " + iMyLockEntityID);
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForLockEntity:LockOwner: " + _strLockOwner);

            try {
                rs = _db.callGBL5016(returnStatus, strEnterprise, strMyLockEntityType, iMyLockEntityID, _strLockOwner);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
            }

            for (int j = 0; j < rdrs.size(); j++) {

                LockGroup lg = null;
                LockItem li = null;

                int ii = 0;

                String strEntityType = rdrs.getColumn(j, ii++);
                int iEntityID = rdrs.getColumnInt(j, ii++);
                String strEntityDescription = rdrs.getColumn(j, ii++).trim();
                String strAgentName = rdrs.getColumn(j, ii++).trim();
                String strRoleCode = rdrs.getColumn(j, ii++).trim();
                String strLockedOn = rdrs.getColumn(j, ii++).trim();
//                int ij = ii++;  MN31595506
                int ij = ++ii;
                int iLockType = rdrs.getColumnInt(j, ij++);
                String strLockEntityType = rdrs.getColumn(j, ij++);
                int iLockEntityID = rdrs.getColumnInt(j, ij++);
                int tempOPWGID = rdrs.getColumnInt(j, ij++);
                String strLockOwner = rdrs.getColumn(j, ij++);
                String strLockOwnerDesc = rdrs.getColumn(j, ij++);

                _db.debug(D.EBUG_SPEW, "gbl5016 answers:" + strEntityType + ":" + iEntityID + ":" + strAgentName + ":" + strRoleCode + ":" + strLockedOn + ":" + iLockType + ":" + strLockEntityType + ":" + iLockEntityID + ":" + strLockOwner);

                //create the LockGroup
                lg = getLockGroup(strEntityType + iEntityID);
                if (lg == null) {
                    lg = new LockGroup(_prof, strEntityType, iEntityID);
                    putLockGroup(lg);
                }

                li = new LockItem(lg, strEntityType, iEntityID, strLockEntityType, iLockEntityID, strLockOwner, tempOPWGID);
                li.setEntityDescription(strEntityDescription);
                li.setAgentName(strAgentName);
                li.setRoleCode(strRoleCode);
                li.setLockedOn(strLockedOn);
                li.setLockType(iLockType);
                li.setLockOwnerDesc(strLockOwnerDesc);
                li.setEntityDisplayName(getEntityDisplayName(_db, _prof, strEntityType, iEntityID));
                lg.putLockItem(li);
            }
        } catch (RuntimeException rx) {

            StringWriter writer = new StringWriter();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {

            // Free any statement
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
    }

    /**
     * getAllSoftLocksForWGID
     *
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void getAllSoftLocksForWGID(Database _db, Profile _prof) throws MiddlewareException, SQLException {

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        String strMethod = "LockList getAllSoftLocksforWGID";
        String strEnterprise = _prof.getEnterprise();
        int iWGID = _prof.getWGID();
        ReturnDataResultSet rdrs = null;
        m_egList = new EANList();
        try {

            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.test(strEnterprise != null, "enterprise is null");
            _db.test(iWGID > 0, "openID <= 0");
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForWGID:Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "getAllSoftLocksForWGID:WGID: " + iWGID);

            try {
                rs = _db.callGBL5017(returnStatus, strEnterprise, iWGID);
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

                LockGroup lg = null;
                LockItem li = null;

                int ii = 0;
                String strEntityType = rdrs.getColumn(j, ii++);
                int iEntityID = rdrs.getColumnInt(j, ii++);
                String strEntityDescription = rdrs.getColumn(j, ii++);
                String strAgentName = rdrs.getColumn(j, ii++);
                String strRoleCode = rdrs.getColumn(j, ii++);
                String strLockedOn = rdrs.getColumn(j, ii++);
//                int ij = ii++;
                int ij = ++ii;
                int iLockType = rdrs.getColumnInt(j, ij++);
                String strLockEntityType = rdrs.getColumn(j, ij++);
                int iLockEntityID = rdrs.getColumnInt(j, ij++);
                int tempOPWGID = rdrs.getColumnInt(j, ij++);
                String strLockOwner = rdrs.getColumn(j, ij++);
                String strLockOwnerDesc = rdrs.getColumn(j, ij++);

                _db.debug(D.EBUG_SPEW, "gbl5017 answers:" + strEntityType + ":" + iEntityID + ":" + strAgentName + ":" + strRoleCode + ":" + strLockedOn + ":" + iLockType + ":" + strLockEntityType + ":" + iLockEntityID + ":" + strLockOwner);

                //create the LockGroup
                lg = getLockGroup(strEntityType + iEntityID);
                if (lg == null) {
                    lg = new LockGroup(_prof, strEntityType, iEntityID);
                    putLockGroup(lg);
                }

                li = new LockItem(lg, strEntityType, iEntityID, strLockEntityType, iLockEntityID, strLockOwner, tempOPWGID);
                li.setEntityDescription(strEntityDescription);
                li.setAgentName(strAgentName);
                li.setRoleCode(strRoleCode);
                li.setLockedOn(strLockedOn);
                li.setLockType(iLockType);
                li.setLockOwnerDesc(strLockOwnerDesc);
                li.setEntityDisplayName(getEntityDisplayName(_db, _prof, strEntityType, iEntityID));
                lg.putLockItem(li);
            }
        } catch (RuntimeException rx) {

            StringWriter writer = new StringWriter();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {

            // Free any statement
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
    }

    /**
     * getTable
     *
     * @return
     *  @author David Bigelow
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getLongDescription());
    }

    private String getEntityDisplayName(Database _db, Profile _prof, String _strEntityType, int _iEntityID) throws MiddlewareException, MiddlewareRequestException, SQLException {
        EntityGroup eg = (EntityGroup) m_egList.get(_strEntityType);
        EntityItem ei = null;
        if (eg == null) {
            eg = new EntityGroup(null, _db, _prof, _strEntityType, "Navigate");
            m_egList.put(eg);
        }
        ei = new EntityItem(eg, _prof, _db, _strEntityType, _iEntityID);
        return ei.toString();
    }

    // Returns the column Stuff
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {
        EANList colList = new EANList();
        try {
            MetaLabel ml1 = new MetaLabel(this, getProfile(), LockItem.ENTITYTYPE, "Entity Type", String.class);
            MetaLabel ml2 = new MetaLabel(this, getProfile(), LockItem.ENTITYID, "Entity ID", String.class);
            MetaLabel ml3 = new MetaLabel(this, getProfile(), LockItem.DESCRIPTION, "Description", String.class);
            MetaLabel ml4 = new MetaLabel(this, getProfile(), LockItem.LOCKENTITYTYPE, "Lock Entity Type", String.class);
            MetaLabel ml5 = new MetaLabel(this, getProfile(), LockItem.LOCKENTITYID, "Lock Entity ID", String.class);
            MetaLabel ml6 = new MetaLabel(this, getProfile(), LockItem.AGENTNAME, "Agent Name", String.class);
            MetaLabel ml7 = new MetaLabel(this, getProfile(), LockItem.ROLECODE, "Role Code", String.class);
            MetaLabel ml8 = new MetaLabel(this, getProfile(), LockItem.LOCKEDON, "Locked On", String.class);
            MetaLabel ml9 = new MetaLabel(this, getProfile(), LockItem.OPENID, "OPWG ID", String.class);
            MetaLabel ml10 = new MetaLabel(this, getProfile(), LockItem.LOCKTYPE, "Lock Type", String.class);
            MetaLabel ml11 = new MetaLabel(this, getProfile(), LockItem.LOCKOWNER, "Lock Owner", String.class);
            MetaLabel ml12 = new MetaLabel(this, getProfile(), LockItem.LOCKOWNERDESC, "Lock Owner Description", String.class);

            ml1.putShortDescription("Entity Type");
            ml2.putShortDescription("Entity ID");
            ml3.putShortDescription("Description");
            ml4.putShortDescription("Lock Entity Type");
            ml5.putShortDescription("Lock Entity ID");
            ml6.putShortDescription("Agent Name");
            ml7.putShortDescription("Role Code");
            ml8.putShortDescription("Locked On");
            ml9.putShortDescription("OPWG ID");
            ml10.putShortDescription("Lock Type");
            ml11.putShortDescription("Lock Owner");
            ml12.putShortDescription("Lock Owner Desc");

            colList.put(ml1);
            colList.put(ml2);
            colList.put(ml3);
            colList.put(ml4);
            colList.put(ml5);
            colList.put(ml6);
            colList.put(ml7);
            colList.put(ml8);
            colList.put(ml9);
            colList.put(ml10);
            colList.put(ml11);
            colList.put(ml12);
        } catch (Exception x) {
            x.printStackTrace();
        }
        return colList;
    }

    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {
        EANList rowList = new EANList();
        for (int i = 0; i < getLockGroupCount(); i++) {
            LockGroup lg = getLockGroup(i);
            for (int j = 0; j < lg.getLockItemCount(); j++) {
                LockItem li = lg.getLockItem(j);
                try {
                    rowList.put(new Implicator(li, getProfile(), li.getKey()));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
        return rowList;
    }

    /**
     * removeLockItem
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _ali
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @throws java.rmi.RemoteException
     *  @author David Bigelow
     */
    public void removeLockItem(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LockItem[] _ali) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        if (_db == null && _rdi == null) {
            System.out.println("LockList removeLockItem: both db and rdi are null");
            return;
        }

        if (_ali == null) {
            System.out.println("LockList removeLockItem: lock items are null");
            return;
        }

        for (int i = 0; i < _ali.length; i++) {
            LockItem li = _ali[i];
            if (li != null) {
                String key = li.getEntityType() + li.getEntityID();
                LockGroup lg = getLockGroup(key);
                if (lg == null) {
                    System.out.println("LockList removeLockItem: lock group " + key + " is null");
                    return;
                }

                if (_db != null) {
                    lg = lg.removeLockItem(_db, _prof, li);
                } else {
                    lg = lg.reRemoveLockItem(_rdi, _prof, li);
                }
                putLockGroup(lg);
            }
        }
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canCreate()
     */
    public boolean canCreate() {
        return false;
    }
    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return false;
    }
    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow()
     */
    public boolean addRow() {
        return false;
    }
    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow(String)
     */
    public boolean addRow(String _strKey) {
        return false;
    }
    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
    }
    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }
    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }
    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        return null;
    }
    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#putMatrixValue(java.lang.String, java.lang.Object)
     */
    public void putMatrixValue(String _str, Object _o) {
    }
    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isMatrixEditable(java.lang.String)
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#rollbackMatrix()
     */
    public void rollbackMatrix() {
    }
    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
    }
    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#generatePickList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeLink(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }
    /**
     * (non-Javadoc)
     * link
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#link(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, COM.ibm.eannounce.objects.EntityItem[], java.lang.String)
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }
    /**
     * (non-Javadoc)
     * create
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#create(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * (non-Javadoc)
     * edit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#edit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String[])
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     * (non-Javadoc)
     * getWhereUsedList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getWhereUsedList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * (non-Javadoc)
     * getActionItemsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getActionItemsAsArray(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }
    /**
     * (non-Javadoc)
     * isDynaTable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isDynaTable()
     */
    public boolean isDynaTable() {
        return false;
    }
    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }
    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }
}
