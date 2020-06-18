//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: InactiveItem.java,v $
// Revision 1.6  2009/05/19 16:21:47  wendy
// Support dereference for memory release
//
// Revision 1.5  2005/03/03 22:39:32  dave
// JTest working and cleanup
//
// Revision 1.4  2004/06/18 17:24:07  joan
// work on edit relator
//
// Revision 1.3  2003/08/18 21:05:09  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.2  2003/07/23 22:09:58  joan
// throw exception
//
// Revision 1.1  2003/07/23 21:21:35  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
* This is a container for Inactive Entities and Relators.
* It belongs inside of an PDHInactiveEntityGroup
*
* @author  David Bigelow
* @version @date
* @see PDHInactiveEntityGroup
*/
public class InactiveItem extends EANDataFoundation implements EANAddressable {

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    /**
     *  FIELD
     */
    public static final String CLASS = "0";
    /**
     *  FIELD
     */
    public static final String TYPE = "1";
    /**
    *  FIELD
    */
    public static final String ID = "2";
    /**
     *  FIELD
     */
    public static final String INACTIVEDATE = "3";
    /**
     *  FIELD
     */
    public static final String DESCRIPTION = "4";
    /**
     *  FIELD
     */
    public static final String NAME = "5";
    /**
     *  FIELD
     */
    public static final String USER = "6";
    /**
     *  FIELD
     */
    public static final String ROLE = "7";

    // Member variables
    private String m_strEntityClass = null;
    private String m_strEntityType = null;
    private int m_iEntityID = 0;
    private String m_strInactiveDate = null;
    private String m_strDescription = null;
    private String m_strDisplayName = null;
    private String m_strUserName = null;
    private String m_strRoleDescription = null;
    
    protected void dereference(){
    	m_strEntityClass = null;
        m_strEntityType = null;
        m_strInactiveDate = null;
        m_strDescription = null;
        m_strDisplayName = null;
        m_strUserName = null;
        m_strRoleDescription = null;
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
     * Creates a InactiveItem  with all attributes specified.
     *
     * @param _f
     * @param _strEntityClass
     * @param _strEntityType
     * @param _iEntityID
     * @param _strDeactivatedDate
     * @param _strEntityDescription
     * @param _strDisplayName
     * @param _strUserName
     * @param _strRoleInfo
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */

    public InactiveItem(EANFoundation _f, String _strEntityClass, String _strEntityType, int _iEntityID, String _strDeactivatedDate, String _strEntityDescription, String _strDisplayName, String _strUserName, String _strRoleInfo) throws MiddlewareRequestException {
        super(_f, null, _strEntityClass + _strEntityType + _iEntityID);
        //Set from parms
        m_strEntityClass = _strEntityClass;
        m_strEntityType = _strEntityType;
        m_iEntityID = _iEntityID;
        m_strInactiveDate = _strDeactivatedDate;
        m_strDescription = _strEntityDescription;
        m_strDisplayName = _strDisplayName;
        m_strUserName = _strUserName;
        m_strRoleDescription = _strRoleInfo;
    }

    /**
     * getInactiveGroup
     *
     * @return
     *  @author David Bigelow
     */
    public InactiveGroup getInactiveGroup() {
        return (InactiveGroup) getParent();
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
            strbResult.append(NEW_LINE + "		m_strEntityClass: " + m_strEntityClass);
            strbResult.append(NEW_LINE + "		m_strEntityType: " + m_strEntityType);
            strbResult.append(NEW_LINE + "		m_iEntityID: " + m_iEntityID);
            strbResult.append(NEW_LINE + "		m_strInactiveDate: " + m_strInactiveDate);
            strbResult.append(NEW_LINE + "		m_strDescription: " + m_strDescription);
            strbResult.append(NEW_LINE + "		m_strDisplayName: " + m_strDisplayName);
            strbResult.append(NEW_LINE + "		m_strUserName: " + m_strUserName);
            strbResult.append(NEW_LINE + "		m_strRoleDescription: " + m_strRoleDescription);
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
       // StringBuffer strbResult = new StringBuffer();
       // strbResult.append(getKey());
        return getKey();//new String(strbResult);
    }

    /**
     * setEntityClass
     *
     * @param _strEntityClass
     *  @author David Bigelow
     */
    public void setEntityClass(String _strEntityClass) {
        m_strEntityClass = _strEntityClass;
    }

    /**
     * getEntityClass
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityClass() {
        return m_strEntityClass;
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
     * setInactiveDate
     *
     * @param _strInactiveDate
     *  @author David Bigelow
     */
    public void setInactiveDate(String _strInactiveDate) {
        m_strInactiveDate = _strInactiveDate;
    }

    /**
     * getInactiveDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getInactiveDate() {
        return m_strInactiveDate;
    }

    /**
     * setDescription
     *
     * @param _strDescription
     *  @author David Bigelow
     */
    public void setDescription(String _strDescription) {
        m_strDescription = _strDescription;
    }

    /**
     * getDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getDescription() {
        return m_strDescription;
    }

    /**
     * getDisplayName
     *
     * @return
     *  @author David Bigelow
     */
    public String getDisplayName() {
        return m_strDisplayName;
    }

    /**
     * setDisplayName
     *
     * @param _strDisplayName
     *  @author David Bigelow
     */
    public void setDisplayName(String _strDisplayName) {
        m_strDisplayName = _strDisplayName;
    }

    /**
     * getUserName
     *
     * @return
     *  @author David Bigelow
     */
    public String getUserName() {
        return m_strUserName;
    }

    /**
     * setUserName
     *
     * @param _strUserName
     *  @author David Bigelow
     */
    public void setUserName(String _strUserName) {
        m_strUserName = _strUserName;
    }

    /**
     * getRoleDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getRoleDescription() {
        return m_strRoleDescription;
    }

    /**
     * setRoleDescription
     *
     * @param _s1
     *  @author David Bigelow
     */
    public void setRoleDescription(String _s1) {
        m_strRoleDescription = _s1;
    }

    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _s, boolean _b) {
        if (_s.equals(CLASS)) {
            return getEntityClass();
        } else if (_s.equals(TYPE)) {
            return getEntityType();
        } else if (_s.equals(ID)) {
            return getEntityID() + "";
        } else if (_s.equals(INACTIVEDATE)) {
            return getInactiveDate();
        } else if (_s.equals(DESCRIPTION)) {
            return getDescription();
        } else if (_s.equals(NAME)) {
            return getDisplayName();
        } else if (_s.equals(USER)) {
            return getUserName();
        } else if (_s.equals(ROLE)) {
            return getRoleDescription();
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
