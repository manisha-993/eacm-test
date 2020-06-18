//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BookmarkItem.java,v $
// Revision 1.52  2010/02/21 18:22:08  wendy
// Add dereference
//
// Revision 1.51  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.50  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.49  2004/06/18 17:11:16  joan
// work on edit relator
//
// Revision 1.48  2004/05/06 16:54:36  gregg
// update profiles in ActionHistory for replay
//
// Revision 1.47  2004/04/21 23:06:14  gregg
// updateProfile
//
// Revision 1.46  2004/04/21 17:57:13  gregg
// storeByProfile-->set passed Profile on action item b4 stor
//
// Revision 1.45  2004/03/31 20:15:50  gregg
// fix replay for non-persistent items
//
// Revision 1.44  2004/03/19 21:50:28  gregg
// some DUP_MODE switches for puts/stores.
//
// Revision 1.43  2004/03/19 19:39:26  gregg
// one mo fix
//
// Revision 1.42  2004/03/19 19:32:03  gregg
// compile fix
//
// Revision 1.41  2004/03/19 19:26:55  gregg
// returning BookmarkItem on store
//
// Revision 1.40  2004/03/16 17:35:44  gregg
// BookmarkSendException throwing
//
// Revision 1.39  2004/03/13 00:31:05  gregg
// some name changes
//
// Revision 1.38  2004/03/13 00:02:04  gregg
// sum Exceptions fixes
//
// Revision 1.37  2004/03/12 23:53:16  gregg
// more send
//
// Revision 1.36  2004/03/12 23:35:41  gregg
// send fix
//
// Revision 1.35  2004/03/12 23:17:33  gregg
// removed setPersistent from send
//
// Revision 1.34  2004/03/12 19:12:01  gregg
// send()/getBuddies() methods
//
// Revision 1.33  2004/03/08 22:27:04  gregg
// replay now ~alway~ grabs new ActionItem from database.
//
// Revision 1.32  2004/01/09 22:04:04  gregg
// Adding in ActionItemHistory
//
// Revision 1.31  2004/01/08 23:51:22  gregg
// setActionItem fix
//
// Revision 1.30  2004/01/08 23:28:28  gregg
// null ptr fix
//
// Revision 1.29  2004/01/08 22:06:59  gregg
// coments
//
// Revision 1.28  2004/01/08 21:59:07  gregg
// action history logic
//
// Revision 1.27  2003/06/25 22:53:51  dave
// minor changes
//
// Revision 1.26  2003/06/10 19:40:32  gregg
// updateDescription logic (sync w/ v111)
//
// Revision 1.25  2003/05/27 19:34:24  dave
// fixed
//
// Revision 1.24  2003/05/27 18:31:41  dave
// Rmi clipping and serialization tracking
//
// Revision 1.23  2003/04/23 22:20:43  gregg
// add Profile param to replay method signature
//
// Revision 1.22  2003/04/16 22:04:59  gregg
// more clip parent of NavActionItem in replay
//
// Revision 1.21  2003/04/16 21:04:10  gregg
// more prof.getNewInstance in replay
//
// Revision 1.20  2003/04/16 20:06:47  gregg
// getNewProfileInstance on replay
//
// Revision 1.19  2003/04/15 17:58:28  gregg
// setting a max limit on # of Bookmarks stored in database
//
// Revision 1.18  2003/04/01 22:06:51  gregg
// extra OPWGID column in bookmark table
//
// Revision 1.17  2003/04/01 00:29:19  gregg
// add enterprise to unique key of object
//
// Revision 1.16  2003/03/31 22:27:54  gregg
// updates for commit,delete table methods
//
// Revision 1.15  2003/03/31 22:12:10  gregg
// updates
//
// Revision 1.14  2003/03/31 19:16:30  gregg
// null pter fix
//
// Revision 1.13  2003/03/31 18:58:26  gregg
// getProfile() fix
//
// Revision 1.12  2003/03/29 01:07:36  gregg
// pass enterprise into deleteBookmark
//
// Revision 1.11  2003/03/29 00:10:48  gregg
// add changes to store nls sensitive action item desc in implicators
//
// Revision 1.10  2003/03/28 23:49:03  gregg
// method stubs for CommitableTableTemplate (for compile)
//
// Revision 1.9  2003/03/28 23:02:43  gregg
// store  ~action item's~  profile
//
// Revision 1.8  2003/03/21 22:34:36  gregg
// persistent logic
//
// Revision 1.7  2003/03/19 22:00:58  gregg
// no longer use get/putLongDescription to store user description:
//  - we DONT want this String to be nls-sensitive!
//
// Revision 1.6  2003/03/19 19:15:33  gregg
// toString() method
//
// Revision 1.5  2003/03/19 18:59:23  gregg
// deleteBookmarkItem methods
//
// Revision 1.4  2003/03/19 17:13:59  gregg
// some comments...
//
// Revision 1.3  2003/03/19 16:57:52  gregg
// do not store locally until requested
//
// Revision 1.2  2003/03/18 22:16:03  gregg
// compile fix
//
// Revision 1.1  2003/03/18 21:59:22  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Provides storage/playback facility to 'Bookmark' one specific location in Navigation.
 */
public final class BookmarkItem extends EANMetaFoundation implements EANTableRowTemplate {

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
 
    /**
     * FIELD
     */
    public static final String USER_DESC_KEY = "User Description";
    /**
     * FIELD
     */
    public static final String ACTION_ITEM_DESC_KEY = "Action Item Description";
    /**
     * FIELD
     */
    public static final String IS_PERSISTENT_KEY = "Is Persistent";
    /**
     * FIELD
     */
    public static final String WORKGROUP_DESC_KEY = "Workgroup Description";
    /**
     * FIELD
     */
    public static final String ROLE_DESC_KEY = "Role Description";

    private EANActionItem m_eai = null;
    private String m_strUserDescription = null;
    private boolean m_bPersistent = false;
    private String m_strRoleName = null;
    private String m_strWGName = null;

    protected void dereference(){
    	m_eai = null;
        m_strUserDescription = null;
        m_strRoleName = null;
        m_strWGName = null;
        
    	super.dereference();
    }
    /**
     * Constructor
     *
     * @param _bg
     * @param _eai
     * @param _strUserDescription
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public BookmarkItem(BookmarkGroup _bg, EANActionItem _eai, String _strUserDescription) throws MiddlewareRequestException {
        super(_bg, _eai.getProfile(), buildKey(_eai, _strUserDescription)); //unique key on bookmark table is OP+ACTIONITEMKEY+USERDESCRIPTION
        m_eai = _eai;
        m_strUserDescription = _strUserDescription;
        // if this is an implicator --> we will set these guys externally!!
        if (!(_eai instanceof ActionItemImplicator)) {
            setRoleName(getProfile().getOPWGName());// the ActionItemImplicator is created using opwgname not roledesc.getRoleDescription());
            setWGName(getProfile().getWGName());
        }
    }

    /**
     * (non-Javadoc)
     * getProfile
     *
     * @see COM.ibm.eannounce.objects.EANObject#getProfile()
     */
    public Profile getProfile() {
        return getActionItem().getProfile();
    }
    /**
     * Provide a single method for determining this Bookmark item's unique key.
     * Note this should mirror Bookmark table's unique primary key.
     *
     * @return String
     * @param _eai
     * @param _strUserDescription 
     */
    protected static String buildKey(EANActionItem _eai, String _strUserDescription) {
        return _eai.getProfile().getEnterprise() + ":" + _eai.getProfile().getOPID() + ":" + _eai.getActionItemKey() + ":" + _strUserDescription;
    }

    /////////////////////////////////
    // EANTableRowTemplate methods //
    /////////////////////////////////

    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANTableRowTemplate#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _strKey) {
        String strReturnValue = null;
        SimpleTextAttribute sta = null;
        if (_strKey.equals(USER_DESC_KEY)) {
            strReturnValue = getUserDescription();
        
        } else if (_strKey.equals(ACTION_ITEM_DESC_KEY)) {
            strReturnValue = getActionItemDesc();
        
        } else if (_strKey.equals(IS_PERSISTENT_KEY)) {
            strReturnValue = String.valueOf(isPersistent());
        
        } else if (_strKey.equals(WORKGROUP_DESC_KEY)) {
            strReturnValue = getWorkgroupDescription();
        
        } else if (_strKey.equals(ROLE_DESC_KEY)) {
            strReturnValue = getRoleDescription();
        }
        try {
            sta = new SimpleTextAttribute(this, getProfile(), _strKey, strReturnValue);
        } catch (MiddlewareRequestException mre) { 
            mre.printStackTrace();
        }
        return sta;
    }

    /**
     * Not editable
     *
     * @return boolean
     * @param _s 
     */
    public boolean isEditable(String _s) {
        return false;
    }

    /**
     * cannot put any objects after initial create.
     *
     * @return boolean
     * @param _strColKey
     * @param _obj 
     */
    public boolean put(String _strColKey, Object _obj) {
        return false;
    }

    ///////////////
    // ACCESSORS //
    ///////////////

    /**
     * getRoleDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getRoleDescription() {
        //return getProfile().getRoleDescription();
        return m_strRoleName;
    }

    /**
     * getWorkgroupDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getWorkgroupDescription() {
        //return getProfile().getWGName();
        return m_strWGName;
    }

    /**
     * getOPID
     *
     * @return
     *  @author David Bigelow
     */
    public int getOPID() {
        return getProfile().getOPID();
    }

    /**
     * getUserDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getUserDescription() {
        return m_strUserDescription;
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getUserDescription();
    }

    /**
     * getActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem() {
        return m_eai;
    }

    /**
     * getActionItemKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionItemKey() {
        return getActionItem().getKey();
    }

    /**
     * getActionItemType
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionItemType() {
        return getActionItem().getActionClass();
    }

    /**
     * getActionItemDesc
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionItemDesc() {
        return getActionItem().getLongDescription();
    }

    /**
     * These are needed as 'placeholder' for useful profile info b4 we retrieve ActionItem blob/Profile.
     *
     * @param _s 
     */
    protected void setRoleName(String _s) {
        m_strRoleName = _s;
    }

    /**
     * These are needed as 'placeholder' for useful profile info b4 we retrieve ActionItem blob/Profile.
     *
     * @param _s 
     */
    protected void setWGName(String _s) {
        m_strWGName = _s;
    }

    /**
     * Report whether or not this bookmark has been commited to the database.
     *
     * @return boolean
     */
    public boolean isPersistent() {
        return m_bPersistent;
    }

    //////////////
    // MUTATORS //
    /////////////

    /**
     * The ONLY way a bookmarkItem may become persistent is through a commit OR a descriptionUpdate if already persistent.
     */
    protected void setPersistent() {
        m_bPersistent = true;
    }

    ///////////
    // OTHER //
    ///////////

    /**
     * Replay this <CODE>BookmarkItem's</CODE> <CODE>EANActionItem<CODE>. This ALWAYS pulls from Database.
     *
     * @return EntityList
     * @param _db
     * @param _profCaller
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.lang.Exception 
     */
    public EntityList replay(Database _db, Profile _profCaller) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, Exception {
        //grab complete object if itsa stub
        if (isPersistent()) {
            m_eai = _db.getBookmarkedActionItem(getProfile(), getActionItemKey(), getUserDescription());
        }
        // only Navigates for now
        if (getActionItem().getActionClass().equals("Navigate")) {
            m_eai.setParent(null);
            m_eai.setProfile(m_eai.getProfile().getNewInstance(_db));
            getProfile().setValOnEffOn(_profCaller.getValOn(), _profCaller.getEffOn());
            m_eai.getActionHistory().updateProfile(_profCaller);
            return ((NavActionItem) m_eai).exec(_db, getProfile());
        }
        return null;
    }

    /**
     * Replay this <CODE>BookmarkItem's</CODE> <CODE>EANActionItem</CODE>. This ALWAYS pulls from Database.
     *
     * @return EntityList
     * @param _rdi
     * @param _profCaller
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception 
     */
    public EntityList replay(RemoteDatabaseInterface _rdi, Profile _profCaller) throws MiddlewareShutdownInProgressException, MiddlewareRequestException, MiddlewareException, RemoteException, Exception {
        //grab complete object if itsa stub
        if (isPersistent()) {
            m_eai = _rdi.getBookmarkedActionItem(getProfile(), getActionItemKey(), getUserDescription());
        }
        // only Navigates for now
        if (getActionItem() instanceof NavActionItem) {
            m_eai.setParent(null);
            m_eai.setProfile(m_eai.getProfile().getNewInstance(_rdi));
            getProfile().setValOnEffOn(_profCaller.getValOn(), _profCaller.getEffOn());
            m_eai.getActionHistory().updateProfile(_profCaller);
            return ((NavActionItem) m_eai).rexec(_rdi, getProfile());
        }
        return null;
    }

    /**
     * Get a list of Profiles which share common roles with this Bookmark's Profile.
     *
     * @return ProfileSet
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     */
    public ProfileSet getBuddies(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        return _db.getCommonProfiles(getProfile());
    }

    /**
     * getBuddies
     *
     * @param _rdi
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public ProfileSet getBuddies(RemoteDatabaseInterface _rdi) throws MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
        return _rdi.getCommonProfiles(getProfile());
    }

    /**
     * Store <CODE>BookmarkItem's</CODE> <CODE>EANActionItem</CODE> in Database.
     *
     * @return BookmarkItem
     * @param _db
     * @param _iDupMode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.BookmarkException 
     */
    public BookmarkItem store(Database _db, int _iDupMode) throws SQLException, MiddlewareException, BookmarkException {
        BookmarkItem bi = storeByProfile(_db, getProfile(), _iDupMode);
        setPersistent();
        return bi;
    }

    /**
     * Store <CODE>BookmarkItem</CODE> in Database
     *
     * @return BookmarkItem
     * @param _rdi
     * @param _iDupMode
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.BookmarkException 
     */
    public BookmarkItem store(RemoteDatabaseInterface _rdi, int _iDupMode) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BookmarkException {
        BookmarkItem bi = storeByProfile(_rdi, getProfile(), _iDupMode);
        setPersistent();
        return bi;
    }

    /**
     * send
     *
     * @param _rdi
     * @param _aProf
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.BookmarkException
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public void send(RemoteDatabaseInterface _rdi, Profile[] _aProf) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BookmarkException, Exception {
        BookmarkSendException bsExc = null;
        if (m_eai instanceof ActionItemImplicator) {
            m_eai = _rdi.getBookmarkedActionItem(getProfile(), getActionItemKey(), getUserDescription());
        }
        for (int i = 0; i < _aProf.length; i++) {
            try {
                storeByProfile(_rdi, _aProf[i], BookmarkGroup.DUP_GEN_NEW_MODE);
            } catch (Exception exc) {
                if (bsExc == null) {
                    bsExc = new BookmarkSendException();
                }
                bsExc.addFailedProfile(_aProf[i], exc);
            }
        }
        if (bsExc != null) {
            throw bsExc;
        }
    }

    /**
     * send
     *
     * @param _db
     * @param _aProf
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.BookmarkException
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public void send(Database _db, Profile[] _aProf) throws SQLException, MiddlewareException, BookmarkException, Exception {

        BookmarkSendException bsExc = null;

        if (m_eai instanceof ActionItemImplicator) {
            // pull our o.g. blob out
            m_eai = _db.getBookmarkedActionItem(getProfile(), getActionItemKey(), getUserDescription());
        }
        for (int i = 0; i < _aProf.length; i++) {
            try {
                storeByProfile(_db, _aProf[i], BookmarkGroup.DUP_GEN_NEW_MODE);
            } catch (Exception exc) {
                if (bsExc == null) {
                    bsExc = new BookmarkSendException();
                }
                bsExc.addFailedProfile(_aProf[i], exc);
            }
        }
        if (bsExc != null) {
            throw bsExc;
        }
    }

    private BookmarkItem storeByProfile(Database _db, Profile _prof, int _iDupMode) throws SQLException, MiddlewareException, BookmarkException {
        EANActionItem ai = getActionItem();
        try {
            NavActionItem nai = new NavActionItem((NavActionItem) ai);
            nai.setProfile(_prof);
            nai.getActionHistory().updateProfile(_prof);
            return _db.putBookmarkedActionItem(_prof, nai, getUserDescription(), _iDupMode);
        } finally {
        }
    }

    private BookmarkItem storeByProfile(RemoteDatabaseInterface _rdi, Profile _prof, int _iDupMode) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BookmarkException {
        EANActionItem ai = getActionItem();
        try {
            if (ai instanceof NavActionItem) {
                NavActionItem nai = new NavActionItem((NavActionItem) ai);
                nai.setProfile(_prof);
                nai.getActionHistory().updateProfile(_prof);
                return _rdi.putBookmarkedActionItem(_prof, nai, getUserDescription(), _iDupMode);
            }
        } finally {
        }
        return this;
    }

    /**
     * Obtain the Path of Action Items leading to the current one (this must be explicitely set w/ setActionHistory()).
     *
     * @return EANActionItem[]
     */
    public EANActionItem[] getActionHistory() {
        EANActionItem[] aai = null;
        EANList el = getActionItem().getActionHistory().getActionItems();
        if (el == null) {
            return new EANActionItem[0];
        }
        aai = new EANActionItem[el.size()];
        for (int i = 0; i < el.size(); i++) {
            aai[i] = (EANActionItem) el.getAt(i);
        }
        return aai;
    }

    /**
     * Set the Path of Action Items leading to the current one.
     *
     * @param _aai 
     */
    public void setActionHistory(EANActionItem[] _aai) {
        EANList el = null;
        
        if (_aai == null) {
            return;
        }
        el = new EANList();
        for (int i = 0; i < _aai.length; i++) {
            el.put(_aai[i]);
        }
        try {
            getActionItem().getActionHistory().setActionItems(el);
        } catch (MiddlewareRequestException exc) {
            exc.printStackTrace(System.err);
        }
    }

    /**
     * hasActionHistory
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasActionHistory() {
        ActionItemHistory aih = getActionItem().getActionHistory();
        if (aih.getActionItems() == null || aih.getActionItems().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Delete this <CODE>BookmarkItem</CODE> from the Database
     *
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     */
    protected void delete(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        _rdi.deleteBookmark(getProfile().getEnterprise(), getOPID(), getActionItemKey(), getUserDescription());
    }

    /**
     * Delete this <CODE>BookmarkItem</CODE> from the Database
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    protected void delete(Database _db) throws SQLException, MiddlewareException {
        _db.deleteBookmark(getProfile().getEnterprise(), getOPID(), getActionItemKey(), getUserDescription());
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer();
        sb.append("-------- BookmarkItem:" + getKey() + " --------" + NEW_LINE);
        sb.append("ActionItemKey:" + getActionItemKey() + NEW_LINE);
        sb.append("ActionItemtype:" + getActionItemType() + NEW_LINE);
        sb.append("UserDescription:" + getUserDescription() + NEW_LINE);
        sb.append("Persistent:" + isPersistent() + NEW_LINE);
        return sb.toString();
    }

    /**
     * isParentAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public boolean isParentAttribute(String _str) {
        return false;
    }
    /**
     * isChildAttribute
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public boolean isChildAttribute(String _str) {
        return false;
    }

}
