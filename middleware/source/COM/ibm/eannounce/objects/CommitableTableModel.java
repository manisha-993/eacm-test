//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CommitableTableModel.java,v $
// Revision 1.11  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.10  2004/04/09 19:37:18  joan
// add duplicate method
//
// Revision 1.9  2003/04/01 18:24:38  gregg
// deletes+sorts
//
// Revision 1.8  2003/03/29 00:15:45  gregg
// allow add row even if we cant edit
//
// Revision 1.7  2003/03/17 17:42:04  gregg
// put(row,col,obj) method
//
// Revision 1.6  2003/03/13 23:11:09  gregg
// hasChanges method
//
// Revision 1.5  2003/03/13 23:02:02  gregg
// rollback return type void
//
// Revision 1.4  2003/03/13 22:51:11  gregg
// rollback
//
// Revision 1.3  2003/03/13 18:45:41  gregg
// remove addColumn, removeColumn methods from EANCommitableTableTemplate interface
//
// Revision 1.2  2003/03/08 00:49:41  gregg
// end-of-the-day commit (lots-o-changes)
//
// Revision 1.1  2003/03/07 22:13:18  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Object Adapter for the <CODE>EANCommitableTableTemplate</CODE> Interface.
 * <BR>- Editable Flavor of <CODE>SimpleTableModel</CODE>.
 * <BR>- provides routines for editing rows/columns as well as commit for updating
 * changes to the PDH.
 */
public class CommitableTableModel extends SimpleTableModel {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * CommitableTableModel
     *
     * @param _ectt
     * @param _strTitle
     *  @author David Bigelow
     */
    public CommitableTableModel(EANCommitableTableTemplate _ectt, String _strTitle) {
        super(_ectt, _strTitle);
    }

    /**
     * rebuildTable
     *
     *  @author David Bigelow
     */
    protected void rebuildTable() {
        removeFilterGroup();
        buildTable(getCommitableTableTemplate().getRowList(), getCommitableTableTemplate().getColumnList());
    }

    ////////////////////////////////////////
    // EANCommitableTableTemplate Methods //
    ////////////////////////////////////////

    /**
     * hasChanges
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasChanges() {
        return getCommitableTableTemplate().hasChanges();
    }

    /**
     * rollback
     *
     * @param _db
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void rollback(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        getCommitableTableTemplate().rollback(_db, _rdi);
        rebuildTable();
        return;
    }

    /**
     * addRow
     *
     * @return
     *  @author David Bigelow
     */
    public boolean addRow() {
        if (!canCreate()) {
            return false;
        }
        if (getCommitableTableTemplate().addRow()) {
            // this is tricky -- row is already added to the template, now add to table...
            int iRowLast = getAllTableRows().size(); //REMEMBER - this is in object, NOT yet in table
            putTableRow(new TableRow(getCommitableTableTemplate().getRowList().getAt(iRowLast), iRowLast));
            return true;
        }
        return false;
    }

    /**
     * removeRow
     *
     * @param _strRowKey
     *  @author David Bigelow
     */
    public void removeRow(String _strRowKey) {
        TableRow row = null;
        if (!canCreate()) {
            return;
        }
        row = (TableRow) getAllTableRows().get(_strRowKey);
        if (row == null) {
            return;
        }
        // lets remove it from our table structure first
        removeTableRow(row);
        // now from object
        getCommitableTableTemplate().removeRow(_strRowKey);
    }

    /**
     * canEdit
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canEdit() {
        return getCommitableTableTemplate().canEdit();
    }

    /**
     * canCreate
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canCreate() {
        return getCommitableTableTemplate().canCreate();
    }

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
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if (!canCreate() || !canEdit()) {
            return;
        }
        getCommitableTableTemplate().commit(_db, _rdi);
    }
    /*****************
     * Misc Mutators *
     *
     * @param _iRow
     * @param _iCol
     * @param _obj
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @return boolean
     ******************/
    public boolean put(int _iRow, int _iCol, Object _obj) throws EANBusinessRuleException {
        EANTableRowTemplate etrt = getRow(_iRow);
        String strColKey = getColumnKey(_iCol);
        return etrt.put(strColKey, _obj);
    }

    /*****************
     * Misc Accessors *
     *
     * @return EANCommitableTableTemplate
     ******************/
    protected EANCommitableTableTemplate getCommitableTableTemplate() {
        return (EANCommitableTableTemplate) getSimpleTableTemplate();
    }

}
