//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANTableModel.java,v $
// Revision 1.70  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.69  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.68  2005/01/05 19:24:07  joan
// add new method
//
// Revision 1.67  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.66  2003/08/28 16:35:58  joan
// adjust link method
//
// Revision 1.65  2003/08/18 21:18:52  dave
// isLocked Modifier
//
// Revision 1.64  2003/08/18 21:05:08  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.63  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.62.2.4  2003/06/25 15:13:37  joan
// fix throw exceptions
//
// Revision 1.62.2.3  2003/06/25 00:46:21  joan
// fix throw exception
//
// Revision 1.62.2.2  2003/06/25 00:15:25  joan
// fix compile
//
// Revision 1.62.2.1  2003/06/24 23:37:24  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.62  2003/01/21 00:20:35  joan
// adjust link method to test VE lock
//
// Revision 1.61  2003/01/14 22:24:39  joan
// fix error
//
// Revision 1.60  2003/01/08 21:44:03  joan
// add getWhereUsedList
//
// Revision 1.59  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.58  2002/11/19 18:27:42  joan
// adjust lock, unlock
//
// Revision 1.57  2002/11/19 00:06:26  joan
// adjust isLocked method
//
// Revision 1.56  2002/10/31 00:06:39  dave
// added more includeds
//
// Revision 1.55  2002/10/30 23:50:31  dave
// more syntax
//
// Revision 1.54  2002/10/30 23:36:57  dave
// more throwing fixes
//
// Revision 1.53  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.52  2002/10/28 23:49:13  dave
// attempting the first commit with a row index
//
// Revision 1.51  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.50  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.49  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.48  2002/08/08 20:51:49  joan
// fix setParentEntityItem
//
// Revision 1.47  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.46  2002/07/16 15:38:19  joan
// working on method to return the array of actionitems
//
// Revision 1.45  2002/07/08 16:05:28  joan
// fix link method
//
// Revision 1.44  2002/06/26 22:27:04  joan
// fixing bugs
//
// Revision 1.43  2002/06/25 20:36:08  joan
// add create method for whereused
//
// Revision 1.42  2002/06/25 17:49:36  joan
// add link and removeLink methods
//
// Revision 1.41  2002/06/19 18:01:15  joan
// addColumn for an array
//
// Revision 1.40  2002/06/19 15:52:18  joan
// work on add column in matrix
//
// Revision 1.39  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.38  2002/06/07 22:29:42  joan
// working on getRowHeader for matrix
//
// Revision 1.37  2002/06/05 22:40:06  joan
// fix error
//
// Revision 1.36  2002/06/05 22:18:19  joan
// work on put and rollback
//
// Revision 1.35  2002/06/05 16:39:04  joan
// fix compile error
//
// Revision 1.34  2002/06/05 16:28:49  joan
// add getMatrixValue method
//
// Revision 1.33  2002/05/30 22:49:52  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.32  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.31  2002/05/14 18:07:43  joan
// working on LockActionItem
//
// Revision 1.30  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.29  2002/05/13 20:40:33  joan
// add resetLockGroup method
//
// Revision 1.28  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.27  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.26  2002/05/08 19:56:40  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.25  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.24  2002/04/23 17:05:59  joan
// working on lock method
//
// Revision 1.23  2002/04/22 22:18:24  joan
// working on unlock
//
// Revision 1.22  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.21  2002/04/19 23:01:50  joan
// import profile
//
// Revision 1.20  2002/04/19 22:45:19  joan
// fixing compiling error
//
// Revision 1.19  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.18  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.17  2002/04/09 18:44:46  joan
// working on put
//
// Revision 1.16  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.15  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.14  2002/03/23 01:27:39  dave
// more fixes
//
// Revision 1.13  2002/03/22 21:35:44  dave
// more fixes to syntax
//
// Revision 1.12  2002/03/22 21:21:13  dave
// syntax
//
// Revision 1.11  2002/03/22 21:10:34  dave
// trace for addToStack
//
// Revision 1.10  2002/03/21 18:38:57  dave
// added getHelp to the EANTableModel
//
// Revision 1.9  2002/03/21 01:30:32  dave
// more EANTable Model interface stuff
//
// Revision 1.8  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.7  2002/03/19 21:35:49  dave
// interface fix
//
// Revision 1.6  2002/03/18 20:22:22  dave
// interface update for EANFoundation
//
// Revision 1.5  2002/03/11 22:35:46  dave
// more syntax errors
//
// Revision 1.4  2002/03/11 20:56:15  dave
// mass changes for beginnings of edit
//
// Revision 1.3  2001/08/09 18:40:04  dave
// compile challenged
//
// Revision 1.2  2001/08/09 18:04:30  dave
// first attempt at a table model
//
// Revision 1.1  2001/08/08 17:42:57  dave
// first attempt at Search API standard table interface
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.sql.SQLException;
import java.rmi.RemoteException;

/**
* This attempts to define a standard EAN TableModel
*/
public interface EANTableModel {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANTableModel.java,v 1.70 2005/02/28 19:43:53 dave Exp $";

    /**
     * getTableTitle
     *
     * @return
     *  @author David Bigelow
     */
    String getTableTitle();
    /**
     * setTableTitle
     *
     * @param _s
     *  @author David Bigelow
     */
    void setTableTitle(String _s);

    /**
     * getColumnHeader
     *
     * @param _c
     * @return
     *  @author David Bigelow
     */
    String getColumnHeader(int _c);
    /**
     * getColumn
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    EANFoundation getColumn(int _i);
    /**
     * getColumn
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    EANFoundation getColumn(String _str);
    /**
     * getColumnCount
     *
     * @return
     *  @author David Bigelow
     */
    int getColumnCount();
    /**
     * getColumnIndex
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    int getColumnIndex(String _str);
    /**
     * getColumnKey
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    String getColumnKey(int _i);

    // Returns Row Stuff
    /**
     * getRowHeader
     *
     * @param _r
     * @return
     *  @author David Bigelow
     */
    String getRowHeader(int _r);
    /**
     * getRowCount
     *
     * @return
     *  @author David Bigelow
     */
    int getRowCount();
    /**
     * getRow
     *
     * @param _f
     * @return
     *  @author David Bigelow
     */
    EANFoundation getRow(int _f);
    /**
     * getRow
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    EANFoundation getRow(String _str);
    /**
     * getRowIndex
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    int getRowIndex(String _str);
    /**
     * getRowKey
     *
     * @param _f
     * @return
     *  @author David Bigelow
     */
    String getRowKey(int _f);
    /**
     * getNativeRowsAsArray
     *
     * @return
     *  @author David Bigelow
     */
    EANFoundation[] getNativeRowsAsArray();
    /**
     * getTableRowsAsArray
     *
     * @return
     *  @author David Bigelow
     */
    EANFoundation[] getTableRowsAsArray();

    // get Class stuff
    /**
     * getColumnClass
     *
     * @param _c
     * @return
     *  @author David Bigelow
     */
    Class getColumnClass(int _c);
    /**
     * getRowClass
     *
     * @param _r
     * @return
     *  @author David Bigelow
     */
    Class getRowClass(int _r);

    /**
     * isEditable
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    boolean isEditable(int _r, int _c);
    /**
     * isLocked
     *
     * @param _r
     * @param _c
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     * @param _strTime
     * @param _bCreateLock
     * @return
     *  @author David Bigelow
     */
    boolean isLocked(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock);
    /**
     * getLockGroup
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    LockGroup getLockGroup(int _r, int _c);
    /**
     * hasLock
     *
     * @param _r
     * @param _c
     * @param _lockOwnerEI
     * @param _prof
     * @return
     *  @author David Bigelow
     */
    boolean hasLock(int _r, int _c, EntityItem _lockOwnerEI, Profile _prof);
    // Cell related stuff
    /**
     * get
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    Object get(int _r, int _c);
    /**
     * getEANObject
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    EANFoundation getEANObject(int _r, int _c);
    // basic put back to the table
    /**
     * put
     *
     * @param _r
     * @param _c
     * @param _o
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    void put(int _r, int _c, Object _o) throws EANBusinessRuleException;

    // Rollback Stuff
    /**
     * rollback
     *
     * @param _r
     * @param _c
     *  @author David Bigelow
     */
    void rollback(int _r, int _c);
    /**
     * rollback
     *
     * @param _r
     *  @author David Bigelow
     */
    void rollback(int _r);
    /**
     * rollback
     *
     *  @author David Bigelow
     */
    void rollback();

    // Commit stuff
    /**
     * commit
     *
     * @param _db
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    void commit(Database _db) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException;
    /**
     * commit
     *
     * @param _rdi
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    void commit(RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException;

    // Active Stuff
    /**
     * getActiveRow
     *
     * @return
     *  @author David Bigelow
     */
    int getActiveRow();
    /**
     * setActiveRow
     *
     * @param _i
     *  @author David Bigelow
     */
    void setActiveRow(int _i);
    /**
     * setRowHeaderIndex
     *
     * @param _ic
     *  @author David Bigelow
     */
    void setRowHeaderIndex(int _ic);

    // Help Stuff

    /**
     * getHelp
     *
     * @param _ir
     * @param _ic
     * @return
     *  @author David Bigelow
     */
    String getHelp(int _ir, int _ic);

    // Needs the ability to  refresh this object fron the parent
    /**
     * refresh
     *
     *  @author David Bigelow
     */
    void refresh();

    // Can this table be edited?
    /**
     * canEdit
     *
     * @return
     *  @author David Bigelow
     */
    boolean canEdit();
    // Can this table be added to?
    /**
     * canCreate
     *
     * @return
     *  @author David Bigelow
     */
    boolean canCreate();
    // adds a row to this table
    /**
     * addRow
     *
     * @return
     *  @author David Bigelow
     */
    boolean addRow();
    /**
     * removeRow
     *
     * @param r
     *  @author David Bigelow
     */
    void removeRow(int r);

    /**
     * hasChanges
     *
     * @return
     *  @author David Bigelow
     */
    boolean hasChanges();

    //these are for unlock
    /**
     * unlock
     *
     * @param _r
     * @param _c
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void unlock(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);
    /**
     * unlock
     *
     * @param _r
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void unlock(int _r, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);
    /**
     * unlock
     *
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void unlock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);

    /**
     * lock
     *
     * @param _r
     * @param _c
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     * @param _strTime
     *  @author David Bigelow
     */
    void lock(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime);
    /**
     * lock
     *
     * @param _r
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void lock(int _r, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);
    /**
     * lock
     *
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    void lock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType);

    /**
     * resetLockGroup
     *
     * @param _r
     * @param _c
     * @param _ll
     *  @author David Bigelow
     */
    void resetLockGroup(int _r, int _c, LockList _ll);
    /**
     * resetLockGroup
     *
     * @param _r
     * @param _ll
     *  @author David Bigelow
     */
    void resetLockGroup(int _r, LockList _ll);
    /**
     * resetLockGroup
     *
     * @param _ll
     *  @author David Bigelow
     */
    void resetLockGroup(LockList _ll);

    /**
     * setParentEntityItem
     *
     * @param _r
     * @param _ei
     *  @author David Bigelow
     */
    void setParentEntityItem(int _r, EntityItem _ei);
    /**
     * setParentEntityItem
     *
     * @param _ei
     *  @author David Bigelow
     */
    void setParentEntityItem(EntityItem _ei);

    /**
     * getMatrixValue
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    Object getMatrixValue(int _r, int _c);
    /**
     * putMatrixValue
     *
     * @param _r
     * @param _c
     * @param _o
     *  @author David Bigelow
     */
    void putMatrixValue(int _r, int _c, Object _o);
    /**
     * isMatrixEditable
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    boolean isMatrixEditable(int _r, int _c);
    /**
     * rollbackMatrix
     *
     *  @author David Bigelow
     */
    void rollbackMatrix();
    /**
     * getRowHeaderMatrix
     *
     * @param _r
     * @return
     *  @author David Bigelow
     */
    String getRowHeaderMatrix(int _r);
    /**
     * addColumn
     *
     * @param _ean
     *  @author David Bigelow
     */
    void addColumn(EANFoundation _ean);
    /**
     * addColumn
     *
     * @param _aean
     *  @author David Bigelow
     */
    void addColumn(EANFoundation[] _aean);
    /**
     * generatePickList
     *
     * @param _i
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _bIsColumn
     * @return
     *  @author David Bigelow
     */
    EntityList generatePickList(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof, boolean _bIsColumn);
    /**
     * removeLink
     *
     * @param _ar
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     *  @author David Bigelow
     */
    boolean removeLink(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws EANBusinessRuleException;
    /**
     * link
     *
     * @param _ar
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _aeiChild
     * @param _strLinkOption
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     *  @author David Bigelow
     */
    EANFoundation[] link(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException;
    /**
     * create
     *
     * @param _i
     * @param _db
     * @param _rdi
     * @param _prof
     * @return
     *  @author David Bigelow
     */
    EntityList create(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof);
    /**
     * edit
     *
     * @param _ai
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    EntityList edit(int[] _ai, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException;

    /**
     * getWhereUsedList
     *
     * @param _i
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    WhereUsedList getWhereUsedList(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException;

    /**
     * getActionItemsAsArray
     *
     * @param _i
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    Object[] getActionItemsAsArray(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException;
}
