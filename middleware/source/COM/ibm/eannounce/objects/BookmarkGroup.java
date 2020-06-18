//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BookmarkGroup.java,v $
// Revision 1.45  2010/02/21 18:22:08  wendy
// Add dereference
//
// Revision 1.44  2009/09/03 19:16:12  wendy
// check for null before close()
//
// Revision 1.43  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.42  2005/02/10 01:32:03  dave
// NEW_LINE issues
//
// Revision 1.41  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.40  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.39  2004/08/10 22:13:51  gregg
// reorder meta columns
//
// Revision 1.38  2004/04/09 19:37:17  joan
// add duplicate method
//
// Revision 1.37  2004/03/25 18:20:16  gregg
// fix putNewBookmarkItem
//
// Revision 1.36  2004/03/22 22:10:25  gregg
// some more Exception Handling compile fixes
//
// Revision 1.35  2004/03/22 22:02:50  gregg
// more DuplicateBookmarkException handling
//
// Revision 1.34  2004/03/19 21:58:17  gregg
// some comnpile fixes
//
// Revision 1.33  2004/03/19 21:50:28  gregg
// some DUP_MODE switches for puts/stores.
//
// Revision 1.32  2004/03/19 19:10:58  gregg
// gen new key on dup's logic
//
// Revision 1.31  2003/12/08 19:28:23  gregg
// MAX_BOOKMARKITEMS = 30
//
// Revision 1.30  2003/09/09 23:06:19  gregg
// Bookmark Logic now spans Enterprises!!!
//
// Revision 1.29  2003/06/19 23:14:59  gregg
// remove updateDescription methods
//
// Revision 1.28  2003/06/18 22:29:37  gregg
// updateDescription null ptr fix
//
// Revision 1.27  2003/06/10 23:56:56  gregg
// sync w/ v111
//
// Revision 1.26  2003/06/10 19:38:04  gregg
// sync w/ v111
//
// Revision 1.25  2003/04/15 18:26:50  gregg
// Message for max limit of Items
//
// Revision 1.24  2003/04/15 17:58:27  gregg
// setting a max limit on # of Bookmarks stored in database
//
// Revision 1.23  2003/04/09 21:43:44  gregg
// moving previously mentioned array delete up into table
//
// Revision 1.22  2003/04/09 21:25:32  gregg
// deletes for arrays of keys
//
// Revision 1.21  2003/04/03 22:11:36  gregg
// removeBookmarkItem made public again
//
// Revision 1.20  2003/04/01 22:24:51  gregg
// compile fix
//
// Revision 1.19  2003/04/01 22:06:50  gregg
// extra OPWGID column in bookmark table
//
// Revision 1.18  2003/04/01 18:24:38  gregg
// deletes+sorts
//
// Revision 1.17  2003/03/31 22:40:47  gregg
// excetion reporting fix
//
// Revision 1.16  2003/03/31 22:40:14  gregg
// clone list fix
//
// Revision 1.15  2003/03/31 22:38:55  gregg
// rollbackfix
//
// Revision 1.14  2003/03/31 22:30:04  gregg
// update
//
// Revision 1.13  2003/03/31 22:12:10  gregg
// updates
//
// Revision 1.12  2003/03/29 00:49:47  gregg
// initial load
//
// Revision 1.11  2003/03/29 00:10:48  gregg
// add changes to store nls sensitive action item desc in implicators
//
// Revision 1.10  2003/03/28 23:49:03  gregg
// method stubs for CommitableTableTemplate (for compile)
//
// Revision 1.9  2003/03/28 23:16:39  gregg
// implement EANTableRowTemplate
//
// Revision 1.8  2003/03/27 23:07:19  dave
// adding some timely commits to free up result sets
//
// Revision 1.7  2003/03/21 22:34:36  gregg
// persistent logic
//
// Revision 1.6  2003/03/19 19:41:10  gregg
// removeBookmarkItem now protected
//
// Revision 1.5  2003/03/19 19:10:04  gregg
// method name change: delete-->deleteBookmarkItem
//
// Revision 1.4  2003/03/19 18:59:22  gregg
// deleteBookmarkItem methods
//
// Revision 1.3  2003/03/19 17:13:59  gregg
// some comments...
//
// Revision 1.2  2003/03/18 22:16:03  gregg
// compile fix
//
// Revision 1.1  2003/03/18 21:59:22  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manage a collection of <CODE>BookmarkItems</CODE> per Operator (OPID).
 */
public final class BookmarkGroup extends EANMetaEntity implements EANCommitableTableTemplate {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    // Limit the number of BookmarkItems
    /**
     * FIELD
     */
    public static final int MAX_BOOKMARKITEMS = 30;

    /**
     * FIELD
     */
    public static final int DUP_REPLACE_MODE = 0;
    /**
     * FIELD
     */
    public static final int DUP_GEN_NEW_MODE = 1;
    /**
     * FIELD
     */
    public static final int DUP_REJECT_MODE = 2;
    /**
     * FIELD
     */
    public static final int DESCRIPTION_LENGTH = 225;

    private int m_iDupMode = DUP_REPLACE_MODE;

    /**
     * FIELD
     */
    public static final String USER_DESC_KEY = BookmarkItem.USER_DESC_KEY;
    /**
     * FIELD
     */
    public static final String ACTION_ITEM_DESC_KEY = BookmarkItem.ACTION_ITEM_DESC_KEY;
    /**
     * FIELD
     */
    public static final String IS_PERSISTENT_KEY = BookmarkItem.IS_PERSISTENT_KEY;
    /**
     * FIELD
     */
    public static final String WORKGROUP_DESC_KEY = BookmarkItem.WORKGROUP_DESC_KEY;
    /**
     * FIELD
     */
    public static final String ROLE_DESC_KEY = BookmarkItem.ROLE_DESC_KEY;

    private byte[] m_byteArrayItems = null;

    /**
     * BookmarkGroup
     *
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public BookmarkGroup(Database _db, Profile _prof) throws MiddlewareException, MiddlewareRequestException {
        super(null, _prof, "BookmarkGroup:" + _prof.getOPID());
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            MetaLabel ml1 = null;
            MetaLabel ml2 = null;
            MetaLabel ml3 = null;
            MetaLabel ml4 = null;
            MetaLabel ml5 = null;
            
            try {
                rs = _db.callGBL7543(new ReturnStatus(-1), getProfile().getEnterprise(), getProfile().getOPID(), getProfile().getReadLanguage().getNLSID());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strActionItemKey = rdrs.getColumn(row, 0);
                String strActionItemType = rdrs.getColumn(row, 1);
                String strUserDescription = rdrs.getColumn(row, 2);
                String strActionItemDescription = rdrs.getColumn(row, 3);
                String strRoleName = rdrs.getColumn(row, 4);
                String strWGName = rdrs.getColumn(row, 5);
                String strEnterprise = rdrs.getColumn(row, 6);
                int iOPWGID = rdrs.getColumnInt(row, 7);

                // we need this guy b/c he might not necessarily be the same as BookmarkGroup's Profile Object
                // b/c of the poss. spanning of enterprises/OPWGIDs.
                Profile profLocal = new Profile(_db, strEnterprise, iOPWGID);

                //store stub as implicator
                ActionItemImplicator aii = new ActionItemImplicator(null, profLocal, strActionItemKey, strActionItemType);
                aii.putLongDescription(strActionItemDescription);
                aii.putShortDescription(strActionItemDescription);
                try {
                    BookmarkItem bi = putNewBookmarkItem(aii, strUserDescription);
                    bi.setPersistent();
                    bi.setRoleName(strRoleName);
                    bi.setWGName(strWGName);
                } catch (DuplicateBookmarkException dbExc) {
                    _db.debug(D.EBUG_ERR, "Duplicate BookmarkItem in BookmarkGroup Constructor:" + dbExc.getMessage());
                    continue;
                }
            }
            // User Description
            ml1 = new MetaLabel(null, getProfile(), USER_DESC_KEY, USER_DESC_KEY, SimpleTextAttribute.class);
            ml1.putShortDescription(USER_DESC_KEY);
            putMeta(ml1);
            // Action Item Description
            ml2 = new MetaLabel(null, getProfile(), ACTION_ITEM_DESC_KEY, ACTION_ITEM_DESC_KEY, SimpleTextAttribute.class);
            ml2.putShortDescription(ACTION_ITEM_DESC_KEY);
            putMeta(ml2);
            // Role Description
            ml3 = new MetaLabel(null, getProfile(), ROLE_DESC_KEY, ROLE_DESC_KEY, SimpleTextAttribute.class);
            ml3.putShortDescription(ROLE_DESC_KEY);
            putMeta(ml3);
            // Is Persistent
            ml4 = new MetaLabel(null, getProfile(), IS_PERSISTENT_KEY, IS_PERSISTENT_KEY, SimpleTextAttribute.class);
            ml4.putShortDescription(IS_PERSISTENT_KEY);
            putMeta(ml4);
            // WG Description
            ml5 = new MetaLabel(null, getProfile(), WORKGROUP_DESC_KEY, WORKGROUP_DESC_KEY, SimpleTextAttribute.class);
            ml5.putShortDescription(WORKGROUP_DESC_KEY);
            putMeta(ml5);

        } catch (SQLException sqlExc) {
            _db.debug("BookmarkGroup Constructor:" + sqlExc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    public void dereference(){
    	for (int i = 0; i < getBookmarkItemCount(); i++) {
    		getBookmarkItem(i).dereference();
    	}

		super.dereference();
		m_byteArrayItems = null;
	}
    ////////////////////////////////
    // EANCommitableTableTemplate //
    ////////////////////////////////

    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getColumnList()
     */
    public EANList getColumnList() {
        return getMeta();
    }

    /**
     * (non-Javadoc)
     * getRow
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getRow(java.lang.String)
     */
    public EANTableRowTemplate getRow(String _strKey) {
        return getBookmarkItem(_strKey);
    }

    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getRowList()
     */
    public EANList getRowList() {
        return getData();
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#addRow()
     */
    public boolean addRow() {
        return false;
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#canCreate()
     */
    public boolean canCreate() {
        return true;
    }

    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#canEdit()
     */
    public boolean canEdit() {
        return false;
    }

    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#removeRow(java.lang.String)
     */
    public void removeRow(String _strRowKey) {
        BookmarkItem bi = getBookmarkItem(_strRowKey);
        if (bi != null) {
            removeBookmarkItem(bi);
        }
    }

    ///////////////
    // ACCESSORS //
    ///////////////

    /**
     * getBookmarkItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getBookmarkItemCount() {
        return getDataCount();
    }

    /**
     * getBookmarkItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem getBookmarkItem(String _strKey) {
        return (BookmarkItem) getData(_strKey);
    }

    /**
     * getBookmarkItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem getBookmarkItem(int _i) {
        return (BookmarkItem) getData(_i);
    }

    /**
     * getDupMode
     *
     * @return
     *  @author David Bigelow
     */
    protected int getDupMode() {
        return m_iDupMode;
    }

    //////////////
    // MUTATORS //
    //////////////

    /**
     * setDupMode
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setDupMode(int _i) {
        m_iDupMode = _i;
    }

    /**
     * putNewBookmarkItem
     *
     * @param _eai
     * @param _strDescription
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.eannounce.objects.DuplicateBookmarkException
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem putNewBookmarkItem(EANActionItem _eai, String _strDescription) throws MiddlewareRequestException, DuplicateBookmarkException {
        return putBookmarkItem(new BookmarkItem(this, _eai, _strDescription));
    }

    /**
     * putBookmarkItem
     *
     * @param _bi
     * @throws COM.ibm.eannounce.objects.DuplicateBookmarkException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem putBookmarkItem(BookmarkItem _bi) throws DuplicateBookmarkException, MiddlewareRequestException {
        if (this.contains(_bi) && m_iDupMode == BookmarkGroup.DUP_REJECT_MODE) {
            throw new DuplicateBookmarkException(getProfile(), _bi.getActionItem(), _bi.getUserDescription());
        } else if (this.contains(_bi) && m_iDupMode == BookmarkGroup.DUP_GEN_NEW_MODE) {
            _bi = genNewUserDescription(_bi);
        }
        putData(_bi);
        return getBookmarkItem(_bi.getKey());
    }

    private BookmarkItem genNewUserDescription(BookmarkItem _bi) throws DuplicateBookmarkException, MiddlewareRequestException {
        String strUserDescNew = _bi.getUserDescription();
        // we need timestamp length + " - " xtra.
        if (strUserDescNew.length() >= DESCRIPTION_LENGTH) {
            strUserDescNew = strUserDescNew.substring(0, DESCRIPTION_LENGTH);
        }
        strUserDescNew = strUserDescNew + " - " + getNowTime();
        return new BookmarkItem(this, _bi.getActionItem(), strUserDescNew);
    }

    private String getNowTime() {
        SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
        return sdfTimestamp.format(new Date());
    }

    /**
     * removeBookmarkItem
     *
     * @param _bi
     * @return
     *  @author David Bigelow
     */
    public BookmarkItem removeBookmarkItem(BookmarkItem _bi) {
        return (BookmarkItem) removeData(_bi);
    }

    ///////////
    // OTHER //
    ///////////

    /**
     * contains
     *
     * @param _bi
     * @return
     *  @author David Bigelow
     */
    public boolean contains(BookmarkItem _bi) {
        for (int i = 0; i < getBookmarkItemCount(); i++) {
            if (getBookmarkItem(i).getKey().equals(_bi.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete the specified <CODE>BookmarkItem</CODE> from the Database
     * If using the BookmarkGroupTable interface, please use its deleteRow method.
     *
     * @param _strKey
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     */
    public void deleteBookmarkItem(String _strKey, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        BookmarkItem bi = getBookmarkItem(_strKey);
        if (bi != null) {
        	removeBookmarkItem(bi).delete(_rdi);
        }
    }

    /**
     * Delete the specified <CODE>BookmarkItem</CODE> from the Database
     * If using the BookmarkGroupTable interface, please use its deleteRow method.
     *
     * @param _strKey
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void deleteBookmarkItem(String _strKey, Database _db) throws SQLException, MiddlewareException {
        BookmarkItem bi = getBookmarkItem(_strKey);
        if (bi != null) {
            removeBookmarkItem(bi).delete(_db);
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer();
        sb.append("======== BookmarkGroup:" + getKey() + " ========" + NEW_LINE);
        if (!_bBrief) {
            for (int i = 0; i < getBookmarkItemCount(); i++) {
                sb.append(getBookmarkItem(i).dump(_bBrief));
            }
        }
        return sb.toString();
    }

    private byte[] cloneItems() {
        byte[] ba = null;
        ByteArrayOutputStream baosObject = null;
        ObjectOutputStream oosObject = null;
        try {
            //
            for (int i = 0; i < getData().size(); i++) {
                BookmarkItem bi = (BookmarkItem) getData(i);
                bi.setTransientParent();
                bi.setParent(null);
            }
            
            try {
                baosObject = new ByteArrayOutputStream();
                oosObject = new ObjectOutputStream(baosObject);
                oosObject.writeObject(getData());
                oosObject.flush();
                oosObject.reset();
                ba = new byte[baosObject.size()];
                ba = baosObject.toByteArray();
            } finally {
            	if (oosObject!=null){
            		oosObject.close();
            		oosObject = null;
            	}
            	if (baosObject!=null){
            		baosObject.close();
            		baosObject = null;
            	}
            }
            //
            for (int i = 0; i < getData().size(); i++) {
                BookmarkItem bi = (BookmarkItem) getData(i);
                bi.setParent(this);
                bi.resetTransientParent();
            }
            //
        } catch (IOException ioe) {
            System.err.println("BookmarkGroupcloneItems Exception:" + ioe);
        }
        if (ba == null) {
            System.out.println("BookmarkGroupcloneItems - byte array is null!!");
        } else {
        }
        return ba;
    }

    /**
     * saveClonedItems
     *
     *  @author David Bigelow
     */
    protected void saveClonedItems() {
        m_byteArrayItems = cloneItems();
    }

    private EANList getClonedItems(byte[] _ba) {
        EANList el = null;
        ByteArrayInputStream baisObject = null;
        ObjectInputStream oisObject = null;
        try {
            try {
                baisObject = new ByteArrayInputStream(m_byteArrayItems);
                oisObject = new ObjectInputStream(baisObject);
                el = (EANList) oisObject.readObject();
            } finally {
            	if (oisObject!=null){
            		oisObject.close();
            		oisObject = null;
            	}
            	if (baisObject!=null){
            		baisObject.close();
            		baisObject = null;
            	}
            }
        } catch (Exception e) {
            System.err.println("BookmarkGroupgetClonedItems Exception:" + e);
        }
        return el;
    }

    /**
     * commit ALL
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
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        // pre-check this
        if (getBookmarkItemCount() > MAX_BOOKMARKITEMS) {
            throw new MiddlewareException("Commit not performed. " + BookmarkException.MAX_LIMIT_MSG);
        }

        try {
            // 1 - Remove any old records that are no longer in our structure (deltas)
            BookmarkGroup bg_db = (_db != null ? _db.getBookmarkGroup(getProfile()) : _rdi.getBookmarkGroup(getProfile()));
            for (int i = 0; i < bg_db.getBookmarkItemCount(); i++) {
                BookmarkItem bi_db = bg_db.getBookmarkItem(i);
                // if NOT in current structure -- delete!!!
                if (this.getBookmarkItem(bi_db.getKey()) == null) {
                    if (_db != null) {
                        bi_db.delete(_db);
                    } else {
                        bi_db.delete(_rdi);
                    }
                }
            }
            // now we can update 'da rest
            for (int i = 0; i < getBookmarkItemCount(); i++) {
                String strOldKey = getBookmarkItem(i).getKey();
                BookmarkItem biStore = null;
                if (_db != null) {
                    biStore = getBookmarkItem(i).store(_db, m_iDupMode);
                } else if (_rdi != null) {
                    biStore = getBookmarkItem(i).store(_rdi, m_iDupMode);
                }
                if (!strOldKey.equals(biStore.getKey())) {
                    putNewBookmarkItem(biStore.getActionItem(), biStore.getUserDescription());
                }
            }
        } catch (BookmarkException bExc) {
            // this case should already be checked at start of method
            throw new MiddlewareException(bExc.toString());
        }
        saveClonedItems();
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#rollback(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void rollback(Database _db, RemoteDatabaseInterface _rdi) throws MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        EANList el = null;
        resetData();
        el = getClonedItems(m_byteArrayItems);
        for (int i = 0; i < el.size(); i++) {
            BookmarkItem bi = (BookmarkItem) el.getAt(i);
            bi.setParent(this);
        }
        setData(el);
    }

    /**
     * duplicate
     *
     * @param _strKey
     * @param _iDup
     * @return
     *  @author David Bigelow
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }

}
