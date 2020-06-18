//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LockActionItem.java,v $
// Revision 1.62  2009/05/19 16:05:16  wendy
// Support dereference for memory release
//
// Revision 1.61  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.60  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.59  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.58  2005/04/14 16:38:54  joan
// adjust code to pull entitylist once for velock
//
// Revision 1.57  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.56  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.55  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.54  2003/10/30 00:56:16  dave
// more profile fixes
//
// Revision 1.53  2003/10/30 00:43:33  dave
// fixing all the profile references
//
// Revision 1.52  2003/06/02 18:40:02  dave
// error messaging
//
// Revision 1.51  2003/04/08 02:59:34  dave
// commit()
//
// Revision 1.50  2003/03/10 17:18:00  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.49  2003/01/14 00:45:31  joan
// add checkOrphan
//
// Revision 1.48  2002/12/20 21:05:04  joan
// debug
//
// Revision 1.47  2002/12/13 21:30:52  joan
// fix bugs
//
// Revision 1.46  2002/12/13 20:41:01  joan
// fix for addition column in Softlock table
//
// Revision 1.45  2002/12/12 23:25:37  joan
// take out System.out
//
// Revision 1.44  2002/12/12 17:39:49  joan
// debug unlock
//
// Revision 1.43  2002/12/12 01:13:13  joan
// debug
//
// Revision 1.42  2002/12/11 21:48:49  joan
// check downlink
//
// Revision 1.41  2002/12/10 22:01:16  joan
// fix bug
//
// Revision 1.40  2002/12/10 21:37:38  joan
// fix bug
//
// Revision 1.39  2002/12/10 21:01:36  joan
// fix bug
//
// Revision 1.38  2002/12/10 18:24:20  joan
// debug
//
// Revision 1.37  2002/12/10 18:06:34  joan
// update velock
//
// Revision 1.36  2002/12/10 01:03:53  joan
// debug
//
// Revision 1.35  2002/12/10 00:43:57  joan
// fix lock
//
// Revision 1.34  2002/11/21 23:14:40  joan
// fix persistent lock
//
// Revision 1.33  2002/11/21 00:50:37  joan
// adjust softlock
//
// Revision 1.32  2002/11/20 22:32:37  joan
// fix lock
//
// Revision 1.31  2002/11/20 20:57:49  joan
// adjust lockaction
//
// Revision 1.30  2002/11/19 00:33:13  joan
// fix compile errors
//
// Revision 1.29  2002/11/19 00:22:29  joan
// fix compile
//
// Revision 1.28  2002/11/15 00:33:26  joan
// remove System.out
//
// Revision 1.27  2002/11/14 23:46:14  joan
// add debug statement
//
// Revision 1.26  2002/11/14 23:25:25  joan
// use ExtractActionItem insteadof LockActionItem
//
// Revision 1.25  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.24  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.23  2002/06/19 17:25:52  joan
// add logic to lock entity items in parent entity group
//
// Revision 1.22  2002/06/05 17:51:58  joan
// fix ActionItem
//
// Revision 1.21  2002/05/16 22:52:36  joan
// add another constructor
//
// Revision 1.20  2002/05/15 21:14:17  joan
// add debug statements
//
// Revision 1.19  2002/05/15 18:31:27  joan
// fix setEntityItem
//
// Revision 1.18  2002/05/15 18:02:57  joan
// add some debug statements
//
// Revision 1.17  2002/05/15 17:54:46  joan
// fix null pointer
//
// Revision 1.16  2002/05/15 17:28:22  joan
// fix null pointer
//
// Revision 1.15  2002/05/15 16:40:55  joan
// fix removeLockItem
//
// Revision 1.14  2002/05/15 16:20:04  joan
// fix throwing exception
//
// Revision 1.13  2002/05/15 16:08:16  joan
// fix errors
//
// Revision 1.12  2002/05/15 15:58:50  joan
// add executeAction for LockActionItem
//
// Revision 1.11  2002/05/15 15:25:38  joan
// work on lock
//
// Revision 1.10  2002/05/14 23:27:46  joan
// fixing error
//
// Revision 1.9  2002/05/14 23:10:44  dave
// changes for joan and my shift right
//
// Revision 1.8  2002/05/14 21:38:00  joan
// debug
//
// Revision 1.7  2002/05/14 21:12:30  joan
// fixing bug
//
// Revision 1.6  2002/05/14 20:49:32  joan
// debug
//
// Revision 1.5  2002/05/14 19:46:40  joan
// debug
//
// Revision 1.4  2002/05/14 18:22:24  joan
// fix compile errors
//
// Revision 1.3  2002/05/14 18:12:21  joan
// put some display statements
//
// Revision 1.2  2002/05/14 18:07:43  joan
// working on LockActionItem
//
// Revision 1.1  2002/05/14 16:09:33  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
//import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 * LockActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LockActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;
    private EANList m_el = new EANList();
    private ExtractActionItem m_xai = null;
    private boolean m_bLock = false;
    private boolean m_bVELock = false;
    private String m_strEntityType = null;
    private boolean m_bUseXAI = false;
    private Hashtable m_levelHsh = new Hashtable();

    public void dereference(){
    	super.dereference();
    
    	if (m_el !=null){
    		for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_el.clear();
    		m_el = null;
    	}
    	if (m_xai!=null){
    		m_xai.dereference();
    		m_xai = null;
    	}
        
        m_strEntityType = null;
        if (m_levelHsh!= null){
        	m_levelHsh.clear();
        	m_levelHsh = null;
        }
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LockActionItem.java,v 1.62 2009/05/19 16:05:16 wendy Exp $";
    }

    /**
     * LockActionItem
     *
     * @param _emf
     * @param _lai
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockActionItem(EANMetaFoundation _emf, LockActionItem _lai) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _lai);
        setExtractActionItem(_lai.getExtractActionItem());
        setLock(_lai.getLock());
        setVELock(_lai.isVELock());
        setEntityType(_lai.getEntityType());
        setUseXAI(_lai.getUseXAI());
        setLevelHsh(_lai.getLevelHsh());
    }

    /**
     * This represents a Lock Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public LockActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);
        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!= null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes

                if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Lock")) {
                    setLock(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("VELock")) {
                    setVELock(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("ActionItem")) {
                    setUseXAI(true);
                    setExtractActionItem(new ExtractActionItem(_emf, _db, _prof, strValue));
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else {
                    System.out.println("strType: " + strType + ", strCode: " + strCode + ", strValue: " + strValue);
                    m_levelHsh.put(strType + strCode, strValue);
                }

            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        strbResult.append("LockActionItem:" + super.dump(_bBrief));

        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Lock";
    }

    /**
     * setLock
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setLock(String _str) {
        m_bLock = (_str.equalsIgnoreCase("Y") ? true : false);
    }

    /**
     * setLock
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setLock(boolean _b) {
        m_bLock = _b;
    }

    /**
     * getLock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getLock() {
        return m_bLock;
    }

    /**
     * setVELock
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setVELock(String _str) {
        m_bVELock = (_str.equalsIgnoreCase("Y") ? true : false);
    }

    /**
     * setVELock
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setVELock(boolean _b) {
        m_bVELock = _b;
    }

    /**
     * isVELock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isVELock() {
        return m_bVELock;
    }

    /**
     * setExtractActionItem
     *
     * @param _xai
     *  @author David Bigelow
     */
    protected void setExtractActionItem(ExtractActionItem _xai) {
        m_xai = _xai;
        m_xai.setPullAttributes(false);
    }

    /**
     * getExtractActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public ExtractActionItem getExtractActionItem() {
        return m_xai;
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
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * setUseXAI
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setUseXAI(boolean _b) {
        m_bUseXAI = _b;
    }

    /**
     * getUseXAI
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getUseXAI() {
        return m_bUseXAI;
    }

    /**
     * setLevelHsh
     *
     * @param _hsh
     *  @author David Bigelow
     */
    protected void setLevelHsh(Hashtable _hsh) {
        m_levelHsh = _hsh;
    }

    /**
     * getLevelHsh
     *
     * @return
     *  @author David Bigelow
     */
    public Hashtable getLevelHsh() {
        return m_levelHsh;
    }

    /**
     * setEntityItems
     *
     * @param _aei
     *  @author David Bigelow
     */
    public void setEntityItems(EntityItem[] _aei) {
        resetEntityItems();
        // loop through and place the entityItem in the EANList
        for (int ii = 0; ii < _aei.length; ii++) {
            EntityItem ei = _aei[ii];
            EntityItem processedEI = null;
            boolean bMatch = false;
            if (!ei.getEntityType().equals(getEntityType())) {
                EntityGroup eg = (EntityGroup) ei.getParent();
                // It cannot be added to the list
                if (eg == null) {
                    continue;
                }
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) ei.getDownLink(0);
                    if (!eic.getEntityType().equals(getEntityType())) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                        if (!eip.getEntityType().equals(getEntityType())) {
                            bMatch = false;
                        } else {
                            processedEI = eip;
                            bMatch = true;
                        }
                    } else {
                        processedEI = eic;
                        bMatch = true;
                    }
                }
            } else {
                processedEI = ei;
                bMatch = true;
            }

            // If you found a match on entitytype.. please put it on the list
            if (bMatch) {
                m_el.put(processedEI);
            } else {
                System.out.println("No match for entityItem and EntityType in action:" + getEntityType() + ":" + ei.getKey());
            }
        }
    }

    /**
     * resetEntityItems
     *
     *  @author David Bigelow
     */
    public void resetEntityItems() {
        m_el = new EANList();
    }

    private EntityItem[] getEntityItemArray() {
        int size = m_el.size();
        EntityItem[] aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            EntityItem ei = (EntityItem) m_el.getAt(i);
            aeiReturn[i] = ei;
        }
        return aeiReturn;
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     *  @author David Bigelow
     */
    public void exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException {
        _db.executeAction(_prof, this);
        resetEntityItems();
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     *  @author David Bigelow
     */
    public void rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
        EANList el = new EANList();
        EANFoundation ef = getParent();

        for (int ii = 0; ii < m_el.size(); ii++) {
            el.put(new EntityItem((EntityItem) m_el.getAt(ii)));
        }
        m_el = el;

        setParent(null);
        _rdi.executeAction(_prof, this);
        setParent(ef);
        resetEntityItems();
    }

    private Hashtable getVELevel(StringBuffer _sb) {

        int iDepth = 0;
        String strET = null;
        String strPET = null;

        Hashtable hshTable = new Hashtable();

        StringTokenizer st1 = new StringTokenizer(_sb.toString(), NEW_LINE);
        while (st1.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ":");
            if (st2.hasMoreTokens()) {
                iDepth = Integer.parseInt(st2.nextToken());
                st2.nextToken();
                st2.nextToken();
                strET = st2.nextToken();
                st2.nextToken();
                strPET = st2.nextToken();
                st2.nextToken();
                st2.nextToken();

                hshTable.put(strET + strPET, String.valueOf(iDepth));
            }
        }
        return hshTable;
    }

    private boolean levelFound(String _strEI, String _strPEI, Hashtable _levelHsh) {
        //      System.out.println("levelFound(" + _strEI + ", " + _strPEI + ")");
        Object o1 = _levelHsh.get(_strEI + _strPEI);
        Object o2 = m_levelHsh.get(_strEI + _strPEI);
        if (o1 instanceof String && o2 instanceof String) {
            String s1 = ((String) o1).trim();
            String s2 = ((String) o2).trim();
            //          System.out.println("s1: " + s1);
            //          System.out.println("s2: " + s2);
            if (s1.equals(s2)) {
                return true;
            }
        }
        return false;
    }

    private void persistentLock(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException {
        String strTraceBase = " LockActionItem persistentLock method";

        // Initialize some SP specific objects needed in this method
        EntityItem lockOwnerEI = null;
        String strLockOwner = null;

        // Placeholders for dates
       // DatePackage dpNow = null;
        //String strNow = null;
       // String strForever = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();

        try {

           // dpNow = _db.getDates();
           // strNow = dpNow.getNow();
           // strForever = dpNow.getForever();
            lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, iOPWGID);
            strLockOwner = lockOwnerEI.getKey();

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_el.size() > 0, "104035 No Items are in the EntityItems to " + (m_bLock ? "lock." : "unlock"));

            if (m_bUseXAI) {

                LockGroup lg = null;

                EntityItem[] aei = getEntityItemArray();
                EntityList el = EntityList.getEntityList(_db, _prof, getExtractActionItem(), aei);
                // lock all entity items in parent entity group
                EntityGroup egParent = el.getParentEntityGroup();
                if (egParent != null) {
                    for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {

                        EntityItem eip = egParent.getEntityItem(ii);
                        String strEntityType = eip.getEntityType();
                        int iEntityID = eip.getEntityID();

                        if (m_bLock) {
                            _db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
                        } else {
                            _db.debug(D.EBUG_DETAIL, strTraceBase + " unlocking " + strEntityType + ", " + iEntityID);
                            lg = new LockGroup(_db, _prof, lockOwnerEI, eip, strLockOwner, LockGroup.LOCK_PERSISTENT, false);
                            lg.removeLockItem(_db, eip, _prof, lockOwnerEI, strLockOwner, LockGroup.LOCK_PERSISTENT);
                        }
                    }
                }

                // lock entity items in other groups
                for (int i = 0; i < el.getEntityGroupCount(); i++) {
                    EntityGroup eg = el.getEntityGroup(i);
                    for (int j = 0; j < eg.getEntityItemCount(); j++) {

                        EntityItem ei = eg.getEntityItem(j);
                        String strEntityType = ei.getEntityType();
                        int iEntityID = ei.getEntityID();

                        if (m_bLock) {
                            _db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
                        } else {
                            _db.debug(D.EBUG_DETAIL, strTraceBase + " unlocking " + strEntityType + ", " + iEntityID);
                            lg = new LockGroup(_db, _prof, lockOwnerEI, ei, strLockOwner, LockGroup.LOCK_PERSISTENT, false);
                            lg.removeLockItem(_db, ei, _prof, lockOwnerEI, strLockOwner, LockGroup.LOCK_PERSISTENT);
                        }
                    }
                }
            } else {
                LockGroup lg = null;

                for (int i = 0; i < m_el.size(); i++) {

                    EntityItem ei = (EntityItem) m_el.getAt(i);
                    String strEntityType = ei.getEntityType();
                    int iEntityID = ei.getEntityID();
                    if (m_bLock) {
                        _db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
                    } else {
                        _db.debug(D.EBUG_DETAIL, strTraceBase + " unlocking " + strEntityType + ", " + iEntityID);
                        lg = new LockGroup(_db, _prof, ei, lockOwnerEI, strLockOwner, LockGroup.LOCK_PERSISTENT, false);
                        lg.removeLockItem(_db, ei, _prof, lockOwnerEI, strLockOwner, LockGroup.LOCK_PERSISTENT);
                    }
                }
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    private void veLock(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException {
        String strTraceBase = " LockActionItem veLock method";

        // Initialize some SP specific objects needed in this method

        // Placeholders for dates
        ExtractActionItem xai = null;

        //String strNow = null;
        //String strForever = null;
        Hashtable hshMap = null;
        String strLockOwner = null;

        try {

          //  DatePackage dpNow = _db.getDates();
          //  strNow = dpNow.getNow();
          //  strForever = dpNow.getForever();
            xai = getExtractActionItem();

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_el.size() > 0, "104035 No Items are in the EntityItems.");

            _db.test(xai != null, " No ExtractActionItem for VE Lock.");

            hshMap = xai.generateVESteps(_db, _prof, getEntityType());
            strLockOwner = xai.getKey();
// adjusted to get EntityList once
            EntityItem[] aei = getEntityItemArray();
            EntityList el = EntityList.getEntityList(_db, _prof, getExtractActionItem(), aei);

			EANList elist = new EANList();
            for (int i = 0; i < m_el.size(); i++) {

                LockGroup lg = null;

	            EntityItem ei = (EntityItem) m_el.getAt(i);

                String strOwnerEntityType = ei.getEntityType();
                int iOwnerEntityID = ei.getEntityID();
                EntityItem lockOwnerEI = new EntityItem(null, _prof, strOwnerEntityType, iOwnerEntityID);

				EntityItem pei = el.getParentEntityGroup().getEntityItem(ei.getKey());
                StringBuffer sb = EntityList.pull(pei, 0, pei, new Hashtable(), 0, "Root", hshMap);
//                System.out.println(strTraceBase + " el pull for pei: " + ei.getKey() + "\n" + sb.toString());

                Hashtable veLevelHsh = getVELevel(sb);

                // lock all entity items in parent entity group
                EntityGroup egParent = el.getParentEntityGroup();
                if (egParent != null) {
                    for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
                        EntityItem eip = egParent.getEntityItem(ii);
                        String strEntityType = eip.getEntityType();
                        int iEntityID = eip.getEntityID();
                        if (!eip.getKey().equals(pei.getKey())) {
							continue;
						}

						String strKey = strEntityType + ":" + iEntityID + ":" + strOwnerEntityType + ":" + iOwnerEntityID + ":" + strLockOwner;
//						System.out.println(strTraceBase + " strKey: " + strKey);
                        if (m_bLock) {
							if (elist.get(strKey) == null) {
                                if (levelFound(strEntityType, strEntityType, veLevelHsh)) {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
                                    lg = new LockGroup(_db, _prof, lockOwnerEI, eip, strLockOwner, LockGroup.LOCK_VE, true);
                                    elist.put(strKey, strKey);
                                }
                            }
                        } else {
                            _db.debug(D.EBUG_DETAIL, strTraceBase + " unlocking " + strEntityType + ", " + iEntityID);
                            lg = new LockGroup(_db, _prof, lockOwnerEI, eip, strLockOwner, LockGroup.LOCK_VE, false);
                            lg.removeLockItem(_db, eip, _prof, lockOwnerEI, strLockOwner, LockGroup.LOCK_VE);
                        }
                    }
                }

                // lock entity items in other groups
                for (int k = 0; k < el.getEntityGroupCount(); k++) {
                    EntityGroup eg = el.getEntityGroup(k);
                    //                  System.out.println(strTraceBase + " eg key: " + eg.getKey());
                    for (int j = 0; j < eg.getEntityItemCount(); j++) {
                        EntityItem ei1 = eg.getEntityItem(j);
                        String strEntityType = ei1.getEntityType();
                        int iEntityID = ei1.getEntityID();
                        //System.out.println(strTraceBase + "strEntityType: " + strEntityType + ", iEntityID: " + iEntityID);
                        int iFound = sb.toString().indexOf(":" + strEntityType + ":" + iEntityID + ":");

						String strKey = strEntityType + ":" + iEntityID + ":" + strOwnerEntityType + ":" + iOwnerEntityID + ":" + strLockOwner;

						// need to check for this, otherwise getting wrong Lock Owner EI.
                        if (iFound >= 0) {
							if (m_bLock) {
								// check the uplink
								for (int ii = 0; ii < ei1.getUpLinkCount(); ii++) {
									EntityItem eip1 = (EntityItem) ei1.getUpLink(ii);
									String strPEI = eip1.getEntityType();

									if (elist.get(strKey) == null) {
										if (levelFound(strEntityType, strPEI, veLevelHsh)) {
											_db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
											lg = new LockGroup(_db, _prof, lockOwnerEI, ei1, strLockOwner, LockGroup.LOCK_VE, true);
											elist.put(strKey, strKey);
										}
									}
								}
								// check the downlink
								for (int ii = 0; ii < ei1.getDownLinkCount(); ii++) {
									EntityItem eip1 = (EntityItem) ei1.getDownLink(ii);
									String strPEI = eip1.getEntityType();

									if (elist.get(strKey) == null) {
										if (levelFound(strEntityType, strPEI, veLevelHsh)) {
											_db.debug(D.EBUG_DETAIL, strTraceBase + " locking " + strEntityType + ", " + iEntityID);
											lg = new LockGroup(_db, _prof, lockOwnerEI, ei1, strLockOwner, LockGroup.LOCK_VE, true);

											elist.put(strKey, strKey);
										}
									}
								}
							} else {
								_db.debug(D.EBUG_DETAIL, strTraceBase + " unlocking " + strEntityType + ", " + iEntityID);
								lg = new LockGroup(_db, _prof, lockOwnerEI, ei1, strLockOwner, LockGroup.LOCK_VE, false);
								lg.removeLockItem(_db, ei1, _prof, lockOwnerEI, strLockOwner, LockGroup.LOCK_VE);
							}
						}
                    }
                }
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * executeAction
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException {
        if (isVELock()) {
            veLock(_db, _prof);
        } else {
            persistentLock(_db, _prof);
        }
    }


    /**
     * updatePdhMeta
     * @return true if successful, false if nothing to update or unsuccessful
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }
}
