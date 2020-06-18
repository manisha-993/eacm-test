//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BookmarkGroupTable.java,v $
// Revision 1.18  2010/02/21 18:22:08  wendy
// Add dereference
//
// Revision 1.17  2005/02/09 23:52:27  dave
// minor syntax
//
// Revision 1.16  2005/02/09 23:46:47  dave
// more Jtest Cleanup
//
// Revision 1.15  2004/03/22 22:10:25  gregg
// some more Exception Handling compile fixes
//
// Revision 1.14  2004/03/19 21:50:28  gregg
// some DUP_MODE switches for puts/stores.
//
// Revision 1.13  2004/03/19 19:14:55  gregg
// gen new BookmarkItem for dup's on store logic
//
// Revision 1.12  2003/06/19 23:14:59  gregg
// remove updateDescription methods
//
// Revision 1.11  2003/06/10 22:45:33  gregg
// updateDescription fix
//
// Revision 1.10  2003/06/10 19:39:08  gregg
// updateDescription (sync w/ v111)
//
// Revision 1.9  2003/04/15 17:58:27  gregg
// setting a max limit on # of Bookmarks stored in database
//
// Revision 1.8  2003/04/09 21:48:19  gregg
// deleteRows methods
//
// Revision 1.7  2003/04/01 18:24:38  gregg
// deletes+sorts
//
// Revision 1.6  2003/03/31 22:50:05  gregg
// some protected's made public
//
// Revision 1.5  2003/03/31 22:33:57  gregg
// changes to deleteRow
//
// Revision 1.4  2003/03/31 22:27:54  gregg
// updates for commit,delete table methods
//
// Revision 1.3  2003/03/31 22:12:10  gregg
// updates
//
// Revision 1.2  2003/03/31 18:58:26  gregg
// getProfile() fix
//
// Revision 1.1  2003/03/29 00:58:57  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Custom table for a BookmarkGroup.
 */
public class BookmarkGroupTable extends CommitableTableModel {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * BookmarkGroupTable
     *
     * @param _bg
     * @param _strTitle
     *  @author David Bigelow
     */
    public BookmarkGroupTable(BookmarkGroup _bg, String _strTitle) {
        super(_bg, _strTitle);
        getBookmarkGroup().saveClonedItems();
    }

    /**
     * addNewBookmarkItem
     *
     * @param _eai
     * @param _strUserDescription
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.eannounce.objects.DuplicateBookmarkException
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem addNewBookmarkItem(EANActionItem _eai, String _strUserDescription) throws MiddlewareRequestException, DuplicateBookmarkException {
        BookmarkItem bi = null;
        int iRowLast = 0;
        
        if (!canCreate()) {
            return null;
        }
        // check if this exists already...
        bi = new BookmarkItem(getBookmarkGroup(), _eai, _strUserDescription);
        //System.out.println("BookmarkGroupTable.addNewBookmarkItem containsItem " + bi.getKey() + " ? " + containsItem(bi));
        if (containsItem(bi)) {
            if (getBookmarkGroup().getDupMode() == BookmarkGroup.DUP_REJECT_MODE) {
                throw new DuplicateBookmarkException(_eai.getProfile(), _eai, _strUserDescription);
            }
           //bi = getBookmarkGroup().putBookmarkItem(bi); // if bi currently exists, a new one is created with diff name
           //return bi; // dont return here or new row does not show up in UI 
        }
   
        bi = getBookmarkGroup().putBookmarkItem(bi); // if bi currently exists, a new one is created with diff name
        iRowLast = getAllTableRows().size(); //REMEMBER - this is in object, NOT yet in table
        putTableRow(new TableRow(getCommitableTableTemplate().getRowList().getAt(iRowLast), iRowLast));
        return bi;
    }

    /**
     * setDupMode
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setDupMode(int _i) {
        BookmarkGroup bg = (BookmarkGroup) getCommitableTableTemplate();
        bg.setDupMode(_i);
    }

    /**
     * containsItem
     *
     * @param _bi
     * @return
     *  @author David Bigelow
     */
    public boolean containsItem(BookmarkItem _bi) {
        return (getRow(_bi.getKey()) != null);
    }

    /**
     * getBookmarkGroup
     *
     * @return
     *  @author David Bigelow
     */
    protected BookmarkGroup getBookmarkGroup() {
        return (BookmarkGroup) getCommitableTableTemplate();
    }

    /**
     * Stire <CODE>BookmarkItem's</CODE> <CODE>EANActionItem</CODE> in Database.
     *
     * @param _db
     * @param _strKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.BookmarkException 
     */
    public void storeRow(Database _db, String _strKey) throws SQLException, MiddlewareException, BookmarkException {
        BookmarkItem bi = (BookmarkItem) getRow(_strKey);
        if (bi != null) {
            BookmarkItem biStore = bi.store(_db, getBookmarkGroup().getDupMode());
            if (!getBookmarkGroup().contains(biStore)) {
                addNewBookmarkItem(biStore.getActionItem(), biStore.getUserDescription());
            }
            getBookmarkGroup().saveClonedItems();
        }
    }

    /**
     * Store <CODE>BookmarkItem</CODE> in Database
     *
     * @param _rdi
     * @param _strKey
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.BookmarkException 
     */
    public void storeRow(RemoteDatabaseInterface _rdi, String _strKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BookmarkException {
        BookmarkItem bi = (BookmarkItem) getRow(_strKey);
        if (bi != null) {
            BookmarkItem biStore = bi.store(_rdi, getBookmarkGroup().getDupMode());
            if (!getBookmarkGroup().contains(biStore)) {
                addNewBookmarkItem(biStore.getActionItem(), biStore.getUserDescription());
            }
            getBookmarkGroup().saveClonedItems();
        }
    }

    /**
     * Delete this <CODE>BookmarkItem</CODE> from the Database
     * AND remove row from local object table.
     *
     * @param _rdi
     * @param _strKey
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     */
    public void deleteRow(RemoteDatabaseInterface _rdi, String _strKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        BookmarkItem bi = getBookmarkGroup().getBookmarkItem(_strKey);
        if (bi == null) {
            return;
        }
        removeRow(_strKey);
        bi.delete(_rdi);
        getBookmarkGroup().saveClonedItems();
    }

    /**
     * deleteRows
     *
     * @param _rdi
     * @param _saKeys
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     *  @author David Bigelow
     */
    public void deleteRows(RemoteDatabaseInterface _rdi, String[] _saKeys) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        for (int i = 0; i < _saKeys.length; i++) {
            BookmarkItem bi = getBookmarkGroup().getBookmarkItem(_saKeys[i]);
            if (bi == null) {
                continue;
            }
            removeRow(_saKeys[i]);
            bi.delete(_rdi);
        }
        if (_saKeys.length > 0) {
            getBookmarkGroup().saveClonedItems();
        }
    }

    /**
     * Delete this <CODE>BookmarkItem</CODE> from the Database
     * AND remove row from local object table.
     *
     * @param _db
     * @param _strKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void deleteRow(Database _db, String _strKey) throws SQLException, MiddlewareException {
        BookmarkItem bi = getBookmarkGroup().getBookmarkItem(_strKey);
        if (bi == null) {
            return;
        }
        removeRow(_strKey);
        bi.delete(_db);
        getBookmarkGroup().saveClonedItems();
    }

    /**
     * deleteRows
     *
     * @param _db
     * @param _saKeys
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    public void deleteRows(Database _db, String[] _saKeys) throws SQLException, MiddlewareException {
        for (int i = 0; i < _saKeys.length; i++) {
            BookmarkItem bi = getBookmarkGroup().getBookmarkItem(_saKeys[i]);
            if (bi == null) {
                continue;
            }
            removeRow(_saKeys[i]);
            bi.delete(_db);
        }
        if (_saKeys.length > 0) {
            getBookmarkGroup().saveClonedItems();
        }
    }

}
