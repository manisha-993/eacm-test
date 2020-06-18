//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LockItem.java,v $
// Revision 1.27  2009/05/19 15:57:23  wendy
// Support dereference for memory release
//
// Revision 1.26  2005/03/04 19:02:21  dave
// more Jtest
//
// Revision 1.25  2004/06/18 17:24:07  joan
// work on edit relator
//
// Revision 1.24  2003/08/18 21:05:09  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.23  2003/04/30 23:24:23  joan
// fix fb 24413
//
// Revision 1.22  2002/12/16 22:57:58  joan
// fix bugs
//
// Revision 1.21  2002/12/16 20:51:25  joan
// work on softlock
//
// Revision 1.20  2002/12/13 21:30:52  joan
// fix bugs
//
// Revision 1.19  2002/12/13 20:41:01  joan
// fix for addition column in Softlock table
//
// Revision 1.18  2002/11/21 00:50:38  joan
// adjust softlock
//
// Revision 1.17  2002/11/19 23:26:56  joan
// fix hasLock method
//
// Revision 1.16  2002/11/19 18:27:43  joan
// adjust lock, unlock
//
// Revision 1.15  2002/11/19 00:06:27  joan
// adjust isLocked method
//
// Revision 1.14  2002/11/15 23:12:04  joan
// take out entity name
//
// Revision 1.13  2002/10/07 17:41:39  joan
// add getLockGroup method
//
// Revision 1.12  2002/08/08 20:51:49  joan
// fix setParentEntityItem
//
// Revision 1.11  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.10  2002/07/18 18:03:31  joan
// create LockList table
//
// Revision 1.9  2002/05/13 16:42:08  joan
// fixing unlock method
//
// Revision 1.8  2002/05/10 20:45:55  joan
// fixing lock
//
// Revision 1.7  2002/04/23 17:05:59  joan
// working on lock method
//
// Revision 1.6  2002/04/22 16:54:46  joan
// working on lock
//
// Revision 1.5  2002/04/19 22:34:06  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.4  2002/04/19 20:31:28  joan
// fixing errors
//
// Revision 1.3  2002/04/19 20:13:54  joan
// working on lock
//
// Revision 1.2  2002/04/16 21:20:07  joan
// syntax
//
// Revision 1.1  2002/04/16 20:56:04  joan
// initial load
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


/**
 * LockItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LockItem extends EANDataFoundation implements EANAddressable {

    private String m_strEntityType = null;
    private int m_iEntityID = 0;
    private String m_strLockEntityType = null;
    private int m_iLockEntityID = 0;
    private String m_strLockOwner = null;
    private String m_strLockOwnerDesc = null;
    private String m_strLockedOn = null;
    private int m_iOPWGID = 0;
    private int m_iLockType = -1;
    private String m_strAgentName = null;
    private String m_strRoleCode = null;
    private String m_strEntityDescription = null;
    private String m_strEntityDisplayName = null;
    private boolean m_bLockAcquired = false;

    /**
     * FIELD
     */
    public static final String ENTITYTYPE = "0";
    /**
     * FIELD
     */
    public static final String ENTITYID = "1";
    /**
     * FIELD
     */
    public static final String DESCRIPTION = "2";
    /**
     * FIELD
     */
    public static final String LOCKENTITYTYPE = "3";
    /**
     * FIELD
     */
    public static final String LOCKENTITYID = "4";
    /**
     * FIELD
     */
    public static final String AGENTNAME = "5";
    /**
     * FIELD
     */
    public static final String ROLECODE = "6";
    /**
     * FIELD
     */
    public static final String LOCKEDON = "7";
    /**
     * FIELD
     */
    public static final String OPENID = "8";
    /**
     * FIELD
     */
    public static final String LOCKTYPE = "9";
    /**
     * FIELD
     */
    public static final String LOCKOWNER = "10";
    /**
     * FIELD
     */
    public static final String LOCKOWNERDESC = "11";

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    
    protected void dereference(){
    	m_strEntityType = null;
        m_strLockEntityType = null;
        m_strLockOwner = null;
        m_strLockOwnerDesc = null;
        m_strLockedOn = null;
        m_strAgentName = null;
        m_strRoleCode = null;
        m_strEntityDescription = null;
        m_strEntityDisplayName = null;
    	super.dereference();
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LockItem.java,v 1.27 2009/05/19 15:57:23 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * LockItem
     *
     * @param _li
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockItem(LockItem _li) throws MiddlewareRequestException {
        super(null, _li.getProfile(), _li.getEntityType() + _li.getEntityID() + _li.getLockEntityType() + _li.getLockEntityID());
        setEntityType(_li.getEntityType());
        setEntityID(_li.getEntityID());
        setEntityDisplayName(_li.getEntityDisplayName());
        setLockEntityType(_li.getLockEntityType());
        setLockEntityID(_li.getLockEntityID());
        setLockOwner(_li.getLockOwner());
        setLockOwnerDesc(_li.getLockOwnerDesc());
        setOPWGID(_li.getOPWGID());
    }

    /**
     * LockItem
     *
     * @param _f
     * @param _strEntityType
     * @param _iEntityID
     * @param _strLockEntityType
     * @param _iLockEntityID
     * @param _strLockOwner
     * @param _iOPWGID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LockItem(EANFoundation _f, String _strEntityType, int _iEntityID, String _strLockEntityType, int _iLockEntityID, String _strLockOwner, int _iOPWGID) throws MiddlewareRequestException {
        super(_f, null, _strEntityType + _iEntityID + _strLockEntityType + _iLockEntityID + _strLockOwner);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
        setLockEntityType(_strLockEntityType);
        setLockEntityID(_iLockEntityID);
        setLockOwner(_strLockOwner);
        setOPWGID(_iOPWGID);
    }

    /**
     * getLockGroup
     *
     * @return
     *  @author David Bigelow
     */
    public LockGroup getLockGroup() {
        return (LockGroup) getParent();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "	LockItem:" + getKey() + ":");
        if (!_bBrief) {
            strbResult.append(NEW_LINE + "		Entity Display Name: " + m_strEntityDisplayName);
            strbResult.append(NEW_LINE + "		Lock Owner Entity Type: " + m_strLockEntityType);
            strbResult.append(NEW_LINE + "		Lock Owner Entity ID: " + m_iLockEntityID);
            strbResult.append(NEW_LINE + "		Lock Owner: " + m_strLockOwner);
            strbResult.append(NEW_LINE + "		m_strLockedOn: " + m_strLockedOn);
            strbResult.append(NEW_LINE + "		m_iOPWGID: " + m_iOPWGID);
            strbResult.append(NEW_LINE + "		m_iLockType: " + m_iLockType);
            strbResult.append(NEW_LINE + "		m_strAgentName: " + m_strAgentName);
            strbResult.append(NEW_LINE + "		m_strRoleCode: " + m_strRoleCode);
        }

        return strbResult.toString();

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(getKey());
        return new String(strbResult);
    }

    /**
     * setEntityType
     *
     * @param _strEntityType
     *  @author David Bigelow
     */
    public void setEntityType(String _strEntityType) {
        m_strEntityType = _strEntityType;
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
     * setEntityID
     *
     * @param _iEntityID
     *  @author David Bigelow
     */
    public void setEntityID(int _iEntityID) {
        m_iEntityID = _iEntityID;
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
     * setLockEntityType
     *
     * @param _strLockEntityType
     *  @author David Bigelow
     */
    public void setLockEntityType(String _strLockEntityType) {
        m_strLockEntityType = _strLockEntityType;
    }

    /**
     * getLockEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getLockEntityType() {
        return m_strLockEntityType;
    }

    /**
     * setLockEntityID
     *
     * @param _iLockEntityID
     *  @author David Bigelow
     */
    public void setLockEntityID(int _iLockEntityID) {
        m_iLockEntityID = _iLockEntityID;
    }

    /**
     * getLockEntityID
     *
     * @return
     *  @author David Bigelow
     */
    public int getLockEntityID() {
        return m_iLockEntityID;
    }

    /**
     * getLockOwner
     *
     * @return
     *  @author David Bigelow
     */
    public String getLockOwner() {
        return m_strLockOwner;
    }

    /**
     * setLockOwner
     *
     * @param _strLockOwner
     *  @author David Bigelow
     */
    public void setLockOwner(String _strLockOwner) {
        m_strLockOwner = _strLockOwner;
    }

    /**
     * getLockOwnerDesc
     *
     * @return
     *  @author David Bigelow
     */
    public String getLockOwnerDesc() {
        return m_strLockOwnerDesc;
    }

    /**
     * setLockOwnerDesc
     *
     * @param _strLockOwnerDesc
     *  @author David Bigelow
     */
    public void setLockOwnerDesc(String _strLockOwnerDesc) {
        m_strLockOwnerDesc = _strLockOwnerDesc;
    }

    /**
     * getOPWGID
     *
     * @return
     *  @author David Bigelow
     */
    public int getOPWGID() {
        return m_iOPWGID;
    }

    /**
     * setOPWGID
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setOPWGID(int _i) {
        m_iOPWGID = _i;
    }

    /**
     * getLockedOn
     *
     * @return
     *  @author David Bigelow
     */
    public String getLockedOn() {
        return m_strLockedOn;
    }

    /**
     * setLockedOn
     *
     * @param _s1
     *  @author David Bigelow
     */
    public void setLockedOn(String _s1) {
        m_strLockedOn = _s1;
    }

    /**
     * setLockType
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setLockType(int _i) {
        m_iLockType = _i;
    }

    /**
     * getLockType
     *
     * @return
     *  @author David Bigelow
     */
    public int getLockType() {
        return m_iLockType;
    }

    /**
     * getLockTypeDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getLockTypeDescription() {
        if (m_iLockType == 0) {
            return "Normal Lock";
        
        } else if (m_iLockType == 1) {
            return "Persistent Lock";
        
        } else if (m_iLockType == 2) {
            return "VE Lock";
        }
        return "";
    }

    /**
     * isNormalLock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isNormalLock() {
        return (m_iLockType == 0 ? true : false);
    }

    /**
     * isPersistentLock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPersistentLock() {
        return (m_iLockType == 1 ? true : false);
    }

    /**
     * isVELock
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isVELock() {
        return (m_iLockType == 2 ? true : false);
    }

    /**
     * getAgentName
     *
     * @return
     *  @author David Bigelow
     */
    public String getAgentName() {
        return m_strAgentName;
    }

    /**
     * setAgentName
     *
     * @param _s1
     *  @author David Bigelow
     */
    public void setAgentName(String _s1) {
        m_strAgentName = _s1;
    }

    /**
     * getRoleCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getRoleCode() {
        return m_strRoleCode;
    }

    /**
     * setRoleCode
     *
     * @param _s1
     *  @author David Bigelow
     */
    public void setRoleCode(String _s1) {
        m_strRoleCode = _s1;
    }

    /**
     * getEntityDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityDescription() {
        return m_strEntityDescription;
    }

    /**
     * setEntityDescription
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setEntityDescription(String _s) {
        m_strEntityDescription = _s;
    }

    /**
     * getEntityDisplayName
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityDisplayName() {
        return m_strEntityDisplayName;
    }

    /**
     * setEntityDisplayName
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setEntityDisplayName(String _s) {
        m_strEntityDisplayName = _s;
    }

    /**
     * getLockAcquired
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getLockAcquired() {
        return m_bLockAcquired;
    }

    /**
     * setLockAcquired
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setLockAcquired(boolean _b) {
        m_bLockAcquired = _b;
    }

    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _s, boolean _b) {
        if (_s.equals(ENTITYTYPE)) {
            return getEntityType();
        } else if (_s.equals(ENTITYID)) {
            return getEntityID() + "";
        } else if (_s.equals(DESCRIPTION)) {
            return getEntityDisplayName();
        } else if (_s.equals(LOCKENTITYTYPE)) {
            return getLockEntityType();
        } else if (_s.equals(LOCKENTITYID)) {
            return getLockEntityID() + "";
        } else if (_s.equals(AGENTNAME)) {
            return getAgentName();
        } else if (_s.equals(ROLECODE)) {
            return getRoleCode();
        } else if (_s.equals(LOCKEDON)) {
            return getLockedOn();
        } else if (_s.equals(OPENID)) {
            return getOPWGID() + "";
        } else if (_s.equals(LOCKTYPE)) {
            return getLockTypeDescription();
        } else if (_s.equals(LOCKOWNER)) {
            return getLockOwner();
        } else if (_s.equals(LOCKOWNERDESC)) {
            String s = getLockOwnerDesc();
            if (s != null) {
                return s;
            } else {
                return "";
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _str) {
        return null;
    }
    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        return false;
    }
    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isEditable(java.lang.String)
     */
    public boolean isEditable(String _s) {
        return false;
    }
    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return false;
    }
    /*
    * No LockGroup to return
    */
    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getLockGroup(java.lang.String)
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }

    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return false;
    }
    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {
    }
    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        return null;
    }

    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
    }
    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
    }
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
    }
    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _str) {
        return false;
    }

}
