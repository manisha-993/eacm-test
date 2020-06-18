//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ChangeHistoryGroup.java,v $
// Revision 1.28  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.27  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.26  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.25  2005/02/10 01:32:03  dave
// NEW_LINE issues
//
// Revision 1.24  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.23  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.22  2005/01/05 19:24:07  joan
// add new method
//
// Revision 1.21  2004/09/28 22:14:12  dave
// final fix
//
// Revision 1.20  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.19  2004/09/15 23:50:47  dave
// ok.. more change
//
// Revision 1.18  2004/09/15 23:39:10  dave
// fixing change group and sp's
//
// Revision 1.17  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.16  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.15  2004/04/09 19:37:17  joan
// add duplicate method
//
// Revision 1.14  2003/08/28 16:28:02  joan
// adjust link method to have link option
//
// Revision 1.13  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.11.2.3  2003/06/25 00:01:44  joan
// fix compile
//
// Revision 1.11.2.2  2003/06/24 23:37:24  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.11.2.1  2003/06/02 17:17:26  gregg
// check for non-null EAFoundation passed in Constructor
//
// Revision 1.11  2003/05/09 21:58:27  gregg
// remove emf parent param from ChangeHistoryGroup constructor
//
// Revision 1.10  2003/03/11 22:17:08  gregg
// put short desc for role desc col hdr.
//
// Revision 1.9  2003/03/11 21:16:48  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.8  2003/03/05 00:03:51  gregg
// ok, remove getClassSpecificInfo altogether
//
// Revision 1.7  2003/03/04 23:58:14  gregg
// abstract cloneFoundation method replaced by getClassSpecific info method
//
// Revision 1.6  2003/03/03 20:50:31  gregg
// getChangeHistoryGroupTable()
//
// Revision 1.5  2003/03/03 20:47:13  gregg
// abstract getRowSelectableTable method
//
// Revision 1.4  2003/02/28 00:51:20  gregg
// compile ficks
//
// Revision 1.3  2003/02/28 00:42:11  gregg
// moving everything we can up into abstract ChangeHistory objects
//
// Revision 1.2  2003/02/27 22:30:22  gregg
// using SimpleTextAttributes for properties in order to fit the RowSelectableTableModel
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
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/**
 * Manage a collection of ChangeHistoryItems
 */
public abstract class ChangeHistoryGroup extends EANMetaEntity implements EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private static final String USER_KEY = ChangeHistoryItem.USER_KEY;
    private static final String USER_DESC = ChangeHistoryItem.USER_DESC;
    private static final String ROLE_KEY = ChangeHistoryItem.ROLE_KEY;
    private static final String ROLE_DESC = ChangeHistoryItem.ROLE_DESC;
    private static final String CHANGE_KEY = ChangeHistoryItem.CHANGE_KEY;
    private static final String CHANGE_DESC = ChangeHistoryItem.CHANGE_DESC;
    private static final String CHANGEDATE_KEY = ChangeHistoryItem.CHANGEDATE_KEY;
    private static final String CHANGEDATE_DESC = ChangeHistoryItem.CHANGEDATE_DESC;
    private static final String VALID_KEY = ChangeHistoryItem.VALID_KEY;
    private static final String VALID_DESC = ChangeHistoryItem.VALID_DESC;
    private static final String ACTIVE_KEY = ChangeHistoryItem.ACTIVE_KEY;
    private static final String ACTIVE_DESC = ChangeHistoryItem.ACTIVE_DESC;

    /**
     * ChangeHistoryGroup
     *
     * @param _db
     * @param _prof
     * @param _ef
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ChangeHistoryGroup(Database _db, Profile _prof, EANFoundation _ef) throws MiddlewareRequestException {
        super(null, _prof, "ChangeHistoryGroup:" + (_ef != null ? _ef.getKey() : "null EANFoundationPassed"));

        try {
            MetaLabel mlUser = null;
            MetaLabel mlRole = null;
            MetaLabel mlChange = null;
            MetaLabel mlChangeDate = null;
            MetaLabel mlValid = null;
            MetaLabel mlActive = null;

            mlUser = new MetaLabel(null, getProfile(), USER_KEY, USER_DESC, SimplePicklistAttribute.class);
            mlUser.putShortDescription(USER_DESC);
            putMeta(mlUser);
            //
            mlRole = new MetaLabel(null, getProfile(), ROLE_KEY, ROLE_DESC, SimplePicklistAttribute.class);
            mlRole.putShortDescription(ROLE_DESC);
            putMeta(mlRole);
            //
            mlChange = new MetaLabel(null, getProfile(), CHANGE_KEY, CHANGE_DESC, SimplePicklistAttribute.class);
            mlChange.putShortDescription(CHANGE_DESC);
            putMeta(mlChange);
            //
            mlChangeDate = new MetaLabel(null, getProfile(), CHANGEDATE_KEY, CHANGEDATE_DESC, SimplePicklistAttribute.class);
            mlChangeDate.putShortDescription(CHANGEDATE_DESC);
            putMeta(mlChangeDate);
            //
            mlValid = new MetaLabel(null, getProfile(), VALID_KEY, VALID_DESC, SimplePicklistAttribute.class);
            mlValid.putShortDescription(VALID_DESC);
            putMeta(mlValid);

            //
            mlActive = new MetaLabel(null, getProfile(), ACTIVE_KEY, ACTIVE_DESC, SimplePicklistAttribute.class);
            mlActive.putShortDescription(ACTIVE_DESC);
            putMeta(mlActive);
        } finally {

        }

        // now put additional columns as defined by any sub-class
        putColumns();
    }

    /**
     * - This method should build any additional columns needed (i.e. putMeta([MetaLabel]))
     * - These will be returned by any subsequent call to getColumnList(), in addition to the default
     *   columns defined in this class
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    protected abstract void putColumns() throws MiddlewareRequestException;

    /**
     * Render this ChangeHistoryGroup as a RowSelectableTable
     * @return a *new instance* of RowSelectableTable for this ChangeHistoryGroup
     */
    public RowSelectableTable getChangeHistoryGroupTable() {
        return new RowSelectableTable(this, getKey());
    }
    ///// MUTATORS ///////

    /**
     * putChangeHistoryItem
     *
     * @param _chi
     *  @author David Bigelow
     */
    protected void putChangeHistoryItem(ChangeHistoryItem _chi) {
        putData(_chi);
    }

    ///// ACCESSORS

    /**
     * getChangeHistoryItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public ChangeHistoryItem getChangeHistoryItem(int _i) {
        return (ChangeHistoryItem) getData(_i);
    }

    /**
     * getChangeHistoryItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public ChangeHistoryItem getChangeHistoryItem(String _strKey) {
        return (ChangeHistoryItem) getData(_strKey);
    }

    /**
     * getChangeHistoryItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getChangeHistoryItemCount() {
        return getDataCount();
    }

    ////////////////////////////////////////////////////
    /// UTILITIES, ETC..
    ////////////////////////////////////////////////////

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("ChangeHistoryGroup:" + getKey() + NEW_LINE);
        sb.append("getProfile:" + getProfile().dump(_bBrief) + NEW_LINE);
        sb.append("---- displaying ChangeHistoryItem dumps: ----" + NEW_LINE);
        for (int i = 0; i < getChangeHistoryItemCount(); i++) {
            getChangeHistoryItem(i).dump(false);
        }
        return sb.toString();
    }

    ///////////////////////////////////////////
    ///  EANTableWrapper interface methods  ///
    ///////////////////////////////////////////

    /**
     * get an EANList of Columns
     *
     * @return EANList
     */
    public EANList getColumnList() {
        return getMeta();
    }
    /**
     * Get an EANList containing all ChangeHistoryItems in this Group
     * @return getData()
     */
    public EANList getRowList() {
        return getData();
    }
    /**
     * Returns false
     * @return false;
     */
    public boolean addRow() {
        return false;
    }
    /**
     * Returns false
     * @return false;
     */
    public boolean addRow(String _strKey) {
        return false;
    }
    /**
     * Call removeChangeHistoryItem(_strKey)
     *
     * @param _strKey
     */
    public void removeRow(String _strKey) {
        return;
    }
    /**
     * Returns false
     * @return false
     */
    public boolean hasChanges() {
        return false;
    }
    /**
     * Returns false
     * @return false
     */
    public boolean canCreate() {
        return false;
    }
    /**
     * Returns false
     * @return false
     */
    public boolean canEdit() {
        return false;
    }
    /**
     * Returns false
     *
     * @return false
     * @param _str
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @param _ean
     */
    public void addColumn(EANFoundation _ean) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @param _str
     * @param _o
     */
    public void putMatrixValue(String _str, Object _o) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return Object
     * @param _str
     */
    public Object getMatrixValue(String _str) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     */
    public void rollbackMatrix() {
        return;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @param _db
     * @param _rdi
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) {
        return;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return EntityList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return EntityList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return Object[]
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strKey
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return boolean
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) {
        return false;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return EANFoundation[]
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     * @param _aeiChild
     * @param _strLinkOption
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @return WhereUsedList
     * @param _db
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _astrKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return EntityList
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     * Does nothing for ChangeHistoryGroup
     *
     * @return boolean
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
