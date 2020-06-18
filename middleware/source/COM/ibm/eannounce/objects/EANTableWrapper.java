//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANTableWrapper.java,v $
// Revision 1.43  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.42  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.41  2005/01/10 21:59:49  joan
// fix compile
//
// Revision 1.40  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.39  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.38  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.37  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.36  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.35  2004/04/09 19:37:18  joan
// add duplicate method
//
// Revision 1.34  2003/08/28 16:28:02  joan
// adjust link method to have link option
//
// Revision 1.33  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.32.2.4  2003/06/25 00:46:21  joan
// fix throw exception
//
// Revision 1.32.2.3  2003/06/25 00:28:57  joan
// fix compile
//
// Revision 1.32.2.2  2003/06/24 23:45:18  joan
// fix throw exception
//
// Revision 1.32.2.1  2003/06/24 23:37:24  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.32  2003/01/21 00:20:35  joan
// adjust link method to test VE lock
//
// Revision 1.31  2003/01/14 22:15:42  joan
// fix compile errors
//
// Revision 1.30  2003/01/14 22:05:06  joan
// adjust removeLink method
//
// Revision 1.29  2003/01/08 21:44:04  joan
// add getWhereUsedList
//
// Revision 1.28  2002/10/30 22:45:28  dave
// simple comma missing
//
// Revision 1.27  2002/10/30 22:15:15  dave
// syntax
//
// Revision 1.26  2002/10/30 22:02:32  dave
// added exception throwing to commit
//
// Revision 1.25  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.24  2002/10/28 23:49:13  dave
// attempting the first commit with a row index
//
// Revision 1.23  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.22  2002/10/09 21:32:55  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.21  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.20  2002/07/16 15:38:19  joan
// working on method to return the array of actionitems
//
// Revision 1.19  2002/07/08 17:53:42  joan
// fix link method
//
// Revision 1.18  2002/07/08 16:05:29  joan
// fix link method
//
// Revision 1.17  2002/06/25 20:36:08  joan
// add create method for whereused
//
// Revision 1.16  2002/06/25 17:49:36  joan
// add link and removeLink methods
//
// Revision 1.15  2002/06/19 15:52:18  joan
// work on add column in matrix
//
// Revision 1.14  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.13  2002/06/05 22:18:19  joan
// work on put and rollback
//
// Revision 1.12  2002/06/05 16:28:49  joan
// add getMatrixValue method
//
// Revision 1.11  2002/05/30 22:49:53  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.10  2002/05/08 19:56:40  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.9  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.8  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.7  2002/03/28 00:12:23  dave
// syntax fixes
//
// Revision 1.6  2002/03/27 23:51:56  dave
// syntax for addRow, etc
//
// Revision 1.5  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.4  2002/03/22 22:03:44  dave
// fixes to importing
//
// Revision 1.3  2002/03/22 21:21:13  dave
// syntax
//
// Revision 1.2  2002/03/22 21:10:34  dave
// trace for addToStack
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
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;

/**
* This attempts to define a standard EAN TableModel
*/
public interface EANTableWrapper {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANTableWrapper.java,v 1.43 2005/11/04 14:52:09 tony Exp $";

    // Returns the column Stuff
    /**
     * getColumnList
     *
     * @return
     *  @author David Bigelow
     */
    EANList getColumnList();
    /**
     * getRowList
     *
     * @return
     *  @author David Bigelow
     */
    EANList getRowList();
    /**
     * canCreate
     *
     * @return
     *  @author David Bigelow
     */
    boolean canCreate();
    /**
     * canEdit
     *
     * @return
     *  @author David Bigelow
     */
    boolean canEdit();
    /**
     * addRow
     *
     * @return
     *  @author David Bigelow
     */
    boolean addRow();
    /**
     * add row
     * VEEdit_Iteration2
     * @param strKey
     * @return
     * @author tone
     */
    boolean addRow(String _strKey);

    /**
     * removeRow
     *
     * @param _strKey
     *  @author David Bigelow
     */
    void removeRow(String _strKey);
    /**
     * hasChanges
     *
     * @return
     *  @author David Bigelow
     */
    boolean hasChanges();

    // Commit stuff
    /**
     * commit
     *
     * @param _db
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
    void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException;
    /**
     * getMatrixValue
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    Object getMatrixValue(String _str);
    /**
     * putMatrixValue
     *
     * @param _str
     * @param _o
     *  @author David Bigelow
     */
    void putMatrixValue(String _str, Object _o);
    /**
     * isMatrixEditable
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    boolean isMatrixEditable(String _str);
    /**
     * rollbackMatrix
     *
     *  @author David Bigelow
     */
    void rollbackMatrix();
    /**
     * addColumn
     *
     * @param _ean
     *  @author David Bigelow
     */
    void addColumn(EANFoundation _ean);
    /**
     * generatePickList
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType);
    /**
     * removeLink
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     *  @author David Bigelow
     */
    boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException;
    /**
     * link
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     * @param _aeiChild
     * @param _strLinkOption
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return
     *  @author David Bigelow
     */
    EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException;
    /**
     * create
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType);
    /**
     * edit
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
     * @return
     *  @author David Bigelow
     */
    EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException;

    /**
     * getWhereUsedList
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException;
    /**
     * getActionItemsAsArray
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException;
    /**
     * isDynaTable
     *
     * @return
     *  @author David Bigelow
     */
    boolean isDynaTable();
    /**
     * duplicate
     *
     * @param _strKey
     * @param _iDup
     * @return
     *  @author David Bigelow
     */
    boolean duplicate(String _strKey, int _iDup);
    /**
     * linkAndRefresh
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _lai
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException;
}
