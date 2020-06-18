//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaColumnOrderTable.java,v $
// Revision 1.16  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.15  2003/04/03 18:37:20  gregg
// fix for rollback
//
// Revision 1.14  2003/04/03 00:23:23  gregg
// fix for resetToDefaults
//
// Revision 1.13  2003/04/02 22:43:06  gregg
// getMetaColumnOrderGroup().saveClonedItems(); in resetToDefaults method
//
// Revision 1.12  2003/04/02 18:07:57  gregg
// resetToDefaults
//
// Revision 1.11  2003/03/28 00:36:24  gregg
// saveClonedItems
//
// Revision 1.10  2003/03/26 01:14:40  gregg
// default order update logic
//
// Revision 1.9  2003/03/25 01:25:41  gregg
// fix for rollback
//
// Revision 1.8  2003/03/14 22:47:18  gregg
// changes to update/commit
//
// Revision 1.7  2003/03/14 18:03:43  gregg
// update moved down into item + use db, rdi methods (not MetaRow objects directly)
//
// Revision 1.6  2003/03/13 18:28:18  gregg
// return an EANMetaEntity on performSort so we can sort & construct table in one step
//
// Revision 1.5  2003/03/13 01:29:55  gregg
// out of bounds fix
//
// Revision 1.4  2003/03/12 23:46:18  gregg
// start column orders @ 1 (not 0)
//
// Revision 1.3  2003/03/12 23:44:12  gregg
// extend CommitableTableModel
//
// Revision 1.2  2003/03/12 23:43:02  gregg
// sorting list ...
//
// Revision 1.1  2003/03/12 22:56:48  gregg
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
 * Custom table for a MetaColumnOrderGroup.
 * Items' columnorders are tied directly to their row indexes.
 */
public class MetaColumnOrderTable extends CommitableTableModel {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * MetaColumnOrderTable
     *
     * @param _mcog
     * @param _strTitle
     *  @author David Bigelow
     */
    public MetaColumnOrderTable(MetaColumnOrderGroup _mcog, String _strTitle) {
        super((MetaColumnOrderGroup) _mcog.performSort(MetaColumnOrderGroup.COLORDER_KEY, true), _strTitle);
        //sequentially number colorders from 1->rowCount
        for (int i = 0; i < getRowCount(); i++) {
            ((MetaColumnOrderItem) getRow(i)).setColumnOrder(i + 1);
        }
        getMetaColumnOrderGroup().saveClonedItems();
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.CommitableTableModel#rollback(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void rollback(Database _db, RemoteDatabaseInterface _rdi) throws java.rmi.RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        super.rollback(_db, _rdi);
        getMetaColumnOrderGroup().performSort(MetaColumnOrderGroup.COLORDER_KEY, true);
        rebuildTable();
        for (int i = 0; i < getRowCount(); i++) {
            //should all be visible at this point....
            ((MetaColumnOrderItem) getRow(i)).setColumnOrder(i + 1);
        }
    }

    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.CommitableTableModel#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        super.commit(_db, _rdi);
        getMetaColumnOrderGroup().saveClonedItems();
    }

    /**
     * resetToDefaults
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
    public void resetToDefaults(Database _db, RemoteDatabaseInterface _rdi) throws java.rmi.RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        getMetaColumnOrderGroup().resetToDefaults(_db, _rdi);
        rebuildTable();
        for (int i = 0; i < getRowCount(); i++) {
            //should all be visible at this point....
            ((MetaColumnOrderItem) getRow(i)).setColumnOrder(i + 1);
        }
        getMetaColumnOrderGroup().saveClonedItems();
    }

    /**
     * This will effectively set the column order of the MetaColumnItem at _iOld index to the columnorder of the
     * item at_iNew index, and rearrange other column orders accordingly.
     * Note that if there are rows being filtered (hidden), then the column order will be set to the
     * row's ~absolute~ index.
     *
     * @concurrency $none
     * @param _iOld
     * @param _iNew 
     */
    public synchronized void moveRow(int _iOld, int _iNew) {
        //do our standard move of visible's
        super.moveRow(_iOld, _iNew);

        //ensure that all rows contain sequential absolute indexes???
        //(not necessarily)recalcAbsoluteRowIndexes();

        //now enforce the column order in our object
        for (int i = 0; i < getRowCount(); i++) {
            TableRow row = getVisibleTableRow(i);
            ((MetaColumnOrderItem) row.getObject()).setColumnOrder(row.getAbsoluteIndex() + 1);
        }
        return;
    }

    /**
     * getMetaColumnOrderGroup
     *
     * @return
     *  @author David Bigelow
     */
    protected MetaColumnOrderGroup getMetaColumnOrderGroup() {
        return (MetaColumnOrderGroup) getCommitableTableTemplate();
    }

    /**
     * Set whether or not this guy will update the Default Column Orders
     *
     * @param _b
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public void setUpdateDefaults(boolean _b) throws MiddlewareRequestException {
        getMetaColumnOrderGroup().setUpdateDefaults(_b);
    }

    /**
     * isUpdateDefaults
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isUpdateDefaults() {
        return getMetaColumnOrderGroup().isUpdateDefaults();
    }
}
