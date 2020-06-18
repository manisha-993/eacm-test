//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ChangeHistoryItem.java,v $
// Revision 1.17  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.16  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.15  2004/09/28 22:22:43  dave
// kin tics
//
// Revision 1.14  2004/09/28 22:14:12  dave
// final fix
//
// Revision 1.13  2004/09/28 21:25:12  dave
// need another parm
//
// Revision 1.12  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.11  2004/09/16 00:13:06  dave
// had parms switched
//
// Revision 1.10  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.9  2004/06/18 17:11:16  joan
// work on edit relator
//
// Revision 1.8  2003/08/18 21:05:07  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.7  2003/06/19 20:31:59  dave
// changes to ChangeHistoryItem
//
// Revision 1.6  2003/03/11 21:16:48  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.5  2003/02/28 00:42:11  gregg
// moving everything we can up into abstract ChangeHistory objects
//
// Revision 1.4  2003/02/27 22:30:22  gregg
// using SimpleTextAttributes for properties in order to fit the RowSelectableTableModel
//
// Revision 1.3  2003/02/25 23:39:21  gregg
// add valid boolean in constructor
//
// Revision 1.2  2003/02/25 23:37:57  gregg
// isValid, setValid methods
//
// Revision 1.1  2003/02/21 01:09:46  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/*********************************************************
 * RE: A couple notes here on to define any subclasses in order for this guy to work in a RowSelectableTable:
 *     1) Any properties (i.e. getUser() here) should be defined as a SimpleTextAttribute
 *        - use putSimpleTextAttribute(strKey,strValue);
 *        - unique keys must be the same keys as the column's MetaLabel.
 *        - this way, any call to getEANObject(strKey) will retrieve the proper SimpleTextAttribute
 *     2) Remember that these guys really wont be changed once built!!
 *********************************************************/

/**
 * Represents one Change History record in the PDH for one EANFoundation
 * i.e. who changed the record and when was it changed
 */
public abstract class ChangeHistoryItem extends EANMetaFoundation implements EANAddressable {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static final String CURRENT = "Current";
    /**
     * FIELD
     */
    protected static final String EXPIRED = "Expired";

    /**
     * FIELD
     */
    protected static final String ACTIVE = "Active";
    /**
     * FIELD
     */
    protected static final String INACTIVE = "Inactive";

    /**
     * FIELD
     */
    protected static final String USER_KEY = "USER";
    /**
     * FIELD
     */
    protected static final String USER_DESC = "User";
    /**
     * FIELD
     */
    protected static final String ROLE_KEY = "ROLE";
    /**
     * FIELD
     */
    protected static final String ROLE_DESC = "Role";

    /**
     * FIELD
     */
    protected static final String CHANGE_KEY = "CHANGEGROUP";
    /**
     * FIELD
     */
    protected static final String CHANGE_DESC = "Change Group";
    /**
     * FIELD
     */
    protected static final String CHANGEDATE_KEY = "CHANGEDATE";
    /**
     * FIELD
     */
    protected static final String CHANGEDATE_DESC = "Change Date";
    /**
     * FIELD
     */
    protected static final String VALID_KEY = "VALID";
    /**
     * FIELD
     */
    protected static final String VALID_DESC = "Is Record Valid";
    /**
     * FIELD
     */
    protected static final String ACTIVE_KEY = "ACTIVE";
    /**
     * FIELD
     */
    protected static final String ACTIVE_DESC = "Is Record Active";

    private EANList m_elAttributes = null;

    /**
     * Think about this ... why would anybody need to create one of these?? (so protected for now)
     *
     * @param _chg
     * @param _prof
     * @param _strUser
     * @param _strRoleDesc
     * @param _strChgDesc
     * @param _strChangeDate
     * @param _bValid
     * @param _bActive
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected ChangeHistoryItem(ChangeHistoryGroup _chg, Profile _prof, String _strUser, String _strRoleDesc, String _strChgDesc, String _strChangeDate, boolean _bValid, boolean _bActive) throws MiddlewareRequestException {

        super(_chg, _prof, _strUser + ":" + _strChangeDate + ":" + (_bValid ? CURRENT : EXPIRED) + ":" + (_bActive ? ACTIVE : INACTIVE));
        m_elAttributes = new EANList();
        putSimpleTextAttribute(USER_KEY, _strUser);
        putSimpleTextAttribute(ROLE_KEY, _strRoleDesc);
        putSimpleTextAttribute(CHANGE_KEY, _strChgDesc);
        putSimpleTextAttribute(CHANGEDATE_KEY, _strChangeDate);
        putSimpleTextAttribute(VALID_KEY, (_bValid ? CURRENT : EXPIRED));
        putSimpleTextAttribute(ACTIVE_KEY, (_bActive ? ACTIVE : INACTIVE));

        return;

    }

    ///////////////
    // ACCESSORS //
    ///////////////

    /**
     * getSimpleTextAttribute
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected SimpleTextAttribute getSimpleTextAttribute(String _strKey) {
        return (SimpleTextAttribute) m_elAttributes.get(_strKey);
    }
    /**
     * Who changed the record?
     *
     * @return String
     */
    public String getUser() {
        return getSimpleTextAttribute(USER_KEY).getValue();
    }
    /**
     * What Role was being used?
     *
     * @return String
     */
    public String getRole() {
        return getSimpleTextAttribute(USER_KEY).getValue();
    }

    /**
     * What Role was being used?
     *
     * @return String
     */
    public String getChangeGroup() {
        return getSimpleTextAttribute(CHANGE_KEY).getValue();
    }

    /**
     * When was the record changed?
     *
     * @return String
     */
    public String getChangeDate() {
        return getSimpleTextAttribute(CHANGEDATE_KEY).getValue();
    }
    /**
     * Is this guy a valid record (i.e. not expired)?
     *
     * @return boolean
     */
    public boolean isValid() {
        return getSimpleTextAttribute(VALID_KEY).getValue().equalsIgnoreCase(CURRENT);
    }
    /**
     * Is this guy a valid record (i.e. not expired)?
     *
     * @return boolean
     */
    public boolean isActive() {
        return getSimpleTextAttribute(ACTIVE_KEY).getValue().equalsIgnoreCase(ACTIVE);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // MUTATORS - for the most part, these should be private (they all should come from database //
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * putSimpleTextAttribute
     *
     * @param _strKey
     * @param _strValue
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected void putSimpleTextAttribute(String _strKey, String _strValue) throws MiddlewareRequestException {
        m_elAttributes.put(new SimpleTextAttribute(this, getProfile(), _strKey, _strValue));
    }
    ///////////////
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("ChangeHistoryItem:" + getKey() + NEW_LINE);
        sb.append("getUser:" + getUser() + NEW_LINE);
        sb.append("getChangeDate:" + getChangeDate() + NEW_LINE);
        sb.append("isValid:" + isValid() + NEW_LINE);
        sb.append("isActive:" + isActive() + NEW_LINE);
        return sb.toString();
    }
    ////////////////////////////////////////
    /// EANAddressable interface methods ///
    ////////////////////////////////////////

    /**
     * Get Cell value
     *
     * @return Object
     * @param _s
     * @param _b 
     */
    public Object get(String _s, boolean _b) {
        EANFoundation ef = getEANObject(_s);
        if (ef == null) {
            System.err.println("ChangeHstoryItem.get(" + _s + "," + _b + ") - could not locate!");
            return "";
        }
        // this should be the SimpleTextAttribute.getValue() of the corresponding key.
        return ef.toString();
    }
    /**
     * Get the SimpleTextAttribute for the speicified key's column
     *
     * @return EANFoundation
     * @param _strKey 
     */
    public EANFoundation getEANObject(String _strKey) {
        return getSimpleTextAttribute(_strKey);
    }
    /**
     * No External Puts into a ChangeHistoryGroup table
     *
     * @return boolean
     * @param _s
     * @param _o
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException 
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        return false;
    }
    /**
     * return getKey() as defined in EANMetaFoundation
     *
     * @return String
     */
    public String getKey() {
        return super.getKey();
    }
    /**
     * Return true
     *
     * @return true
     * @param _str
     * @param _lockOwnerEI
     * @param _prof 
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return true;
    }
    /**
     * Return false
     *
     * @return false
     * @param _s 
     */
    public boolean isEditable(String _s) {
        return false;
    }
    /**
     * Return true
     *
     * @return true
     * @param _s
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     * @param _strTime
     * @param _bCreateLock 
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return true;
    }
    /**
     * Return null
     *
     * @return null
     * @param _str 
     */
    public String getHelp(String _str) {
        return null;
    }
    /**
     * Return null
     *
     * @return null
     * @param _s 
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryItem
     *
     * @param _s
     * @param _ll 
     */
    public void resetLockGroup(String _s, LockList _ll) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryItem
     *
     * @param _str 
     */
    public void rollback(String _str) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryItem
     *
     * @param _ei 
     */
    public void setParentEntityItem(EntityItem _ei) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryItem
     *
     * @param _s
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType 
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        return;
    }

    /**
     * getFlagCode
     *
     * @return
     *  @author David Bigelow
     */
    public abstract String getFlagCode();

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
