//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ClassificationBinder.java,v $
// Revision 1.28  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.27  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.26  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.25  2005/01/05 19:24:07  joan
// add new method
//
// Revision 1.24  2004/06/18 17:11:17  joan
// work on edit relator
//
// Revision 1.23  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.22  2004/06/08 17:37:11  joan
// add method
//
// Revision 1.21  2004/04/09 19:37:18  joan
// add duplicate method
//
// Revision 1.20  2003/08/28 16:28:02  joan
// adjust link method to have link option
//
// Revision 1.19  2003/08/18 21:05:07  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.18  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.17.2.2  2003/06/25 00:01:44  joan
// fix compile
//
// Revision 1.17.2.1  2003/06/24 23:37:24  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.17  2003/01/21 00:20:35  joan
// adjust link method to test VE lock
//
// Revision 1.16  2003/01/14 22:05:07  joan
// adjust removeLink method
//
// Revision 1.15  2003/01/08 21:44:03  joan
// add getWhereUsedList
//
// Revision 1.14  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.13  2002/11/19 18:27:41  joan
// adjust lock, unlock
//
// Revision 1.12  2002/11/19 00:06:25  joan
// adjust isLocked method
//
// Revision 1.11  2002/10/30 22:39:12  dave
// more cleanup
//
// Revision 1.10  2002/10/30 22:36:18  dave
// clean up
//
// Revision 1.9  2002/10/30 22:02:31  dave
// added exception throwing to commit
//
// Revision 1.8  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.7  2002/10/28 23:49:13  dave
// attempting the first commit with a row index
//
// Revision 1.6  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.5  2002/10/09 21:32:55  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.4  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.3  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.2  2002/09/20 16:48:55  dave
// syntax fixes
//
// Revision 1.1  2002/09/20 16:21:52  dave
// classification first pass for the structure
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 * ClassificationBinder
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClassificationBinder extends EANMetaEntity implements EANAddressable, EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private EntityItem m_ei = null;

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
        return "$Id: ClassificationBinder.java,v 1.28 2005/11/04 14:52:09 tony Exp $";
    }

    /**
     * ClassificationBinder
     *
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ClassificationBinder(EntityItem _ei) throws MiddlewareRequestException {
        super(null, _ei.getProfile(), _ei.getKey());
        m_ei = _ei;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        if (_bBrief) {
            strbResult.append("ClassificationBinder:" + getKey());
        } else {
            strbResult.append("ClassificationBinder:" + getKey());
            strbResult.append("\nEntityItem:" + m_ei.dump(_bBrief));
        }

        return new String(strbResult);

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return m_ei.toString();
    }

    //
    // This is the table wrapper stuff to Render its Action Groups
    //

    /*
    * return the Row Information Here
    * This is simply the entityItem
    */
    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {

        resetData();
        try {
            putData(m_ei);
        } catch (Exception x) {
            x.printStackTrace();
        }

        return getData();
    }

    /*
    *	Return the column information here
    */
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {

        EntityGroup eg = null;
        resetMeta();

        eg = m_ei.getEntityGroup();

        if (eg == null) {
            return null;
        }

        // If we are looking at a relator.. lets the child first
        if (eg.isRelator() || eg.isAssoc()) {
            EntityList entl = eg.getEntityList();
            if (entl != null) {
                EntityGroup egDown = entl.getEntityGroup(eg.getEntity2Type());
                for (int ii = 0; ii < egDown.getMetaAttributeCount(); ii++) {
                    EANMetaAttribute ma = egDown.getMetaAttribute(ii);
                    if (ma.isClassified()) {
                        try {
                            putMeta(new Implicator(ma, getProfile(), egDown.getEntityType() + ":" + ma.getKey()));
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                    }
                }
            }
        }

        // Now .. lets get the thing we are looking at
        for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = eg.getMetaAttribute(ii);
            if (ma.isClassified()) {
                try {
                    putMeta(new Implicator(ma, getProfile(), eg.getEntityType() + ":" + ma.getKey()));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }

        // Now return the answer
        return getMeta();

    }

    // No commiting happening here
    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }

    // Needs to be similar to the one in EntityItem...
    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return false;
    }

    // Not needed here
    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canCreate()
     */
    public boolean canCreate() {
        return false;
    }

    // Not needed here
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

    // Not needed here
    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
    }

    // Same as Entity Item
    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }

    // Not needed here
    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        return null;
    }

    // Not Needed here
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

    // Not needed here
    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#rollbackMatrix()
     */
    public void rollbackMatrix() {
    }

    //Not needed Here
    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
    }

    // Not needed here
    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#generatePickList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    // Not Needed here
    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeLink(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }

    // Not Needed Here
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
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _str, boolean _b) {
        return m_ei.get(_str, _b);
    }

    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _str) {
        return m_ei.getEANObject(_str);
    }

    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        return m_ei.getHelp(_str);
    }

    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return m_ei.hasLock(_str, _lockOwnerEI, _prof);
    }

    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isEditable(java.lang.String)
     */
    public boolean isEditable(String _str) {
        return m_ei.isEditable(_str);
    }

    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _str, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return m_ei.isLocked(_str, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
    }

    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _str, Object _o) throws EANBusinessRuleException {
        return m_ei.put(_str, _o);
    }

    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
        m_ei.resetLockGroup(_s, _ll);
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {
        m_ei.rollback(_str);
    }

    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
        // do nothing
    }

    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        m_ei.unlock(_s, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
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
