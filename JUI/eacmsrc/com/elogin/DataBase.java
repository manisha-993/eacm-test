/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: DataBase.java,v $
 * Revision 1.6  2009/11/03 19:04:45  wendy
 * SR11, SR15 and SR17 restrict create of relator - recognize this error
 *
 * Revision 1.5  2009/09/01 17:22:25  wendy
 * removed useless code and cleanup
 *
 * Revision 1.4  2009/05/28 14:04:42  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/08/04 14:01:36  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.2  2008/02/21 19:18:52  wendy
 * Add access to change history for relators
 *
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2006/03/07 22:23:20  tony
 * JT_20060307 removed unnecessary middleware classes that
 * are obsolete.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:35  tony
 * This is the initial load of OPICM
 *
 * Revision 1.21  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.20  2005/08/11 20:35:31  tony
 * catalog changes
 *
 * Revision 1.19  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.18  2005/05/03 20:54:19  tony
 * MN23855544
 *
 * Revision 1.17  2005/03/24 20:51:04  tony
 * jt_20050324
 * added ability to unretire flags
 *
 * Revision 1.16  2005/03/08 20:33:54  tony
 * added updateFlagCodes logic
 *
 * Revision 1.15  2005/03/03 21:46:39  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.14  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.13  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.12  2005/01/10 22:30:29  tony
 * multiple edit from whereUsed.
 *
 * Revision 1.11  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.10  2004/11/04 22:29:38  tony
 * *** empty log message ***
 *
 * Revision 1.9  2004/11/04 14:43:22  tony
 * searchable picklist
 *
 * Revision 1.8  2004/09/15 22:45:48  tony
 * updated blue pages add logic
 *
 * Revision 1.7  2004/07/29 22:34:38  tony
 * updated locking of array
 * dwb_20040726
 *
 * Revision 1.6  2004/06/08 20:41:02  tony
 * 5ZPTCX.2
 *
 * Revision 1.5  2004/05/24 21:48:54  tony
 * cr_ActChain
 *
 * Revision 1.4  2004/03/15 18:17:34  tony
 *  cr_6303 take two too many bookmarks
 *
 * Revision 1.3  2004/03/12 23:07:47  tony
 * cr_6303
 * send bookmark to a friend.
 *
 * Revision 1.2  2004/02/26 21:53:16  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2003/12/16 20:24:25  tony
 * 52910
 *
 * Revision 1.21  2003/12/09 20:01:51  tony
 * reporting update
 *
 * Revision 1.20  2003/07/24 17:31:52  tony
 * restore functionality addition.
 * First pass complete.
 *
 * Revision 1.19  2003/07/03 16:38:03  tony
 * improved scripting logic.
 *
 * Revision 1.18  2003/06/25 19:14:18  tony
 * joan.20030625 -- updated logic to match middleware changes
 *
 * Revision 1.17  2003/06/17 19:47:42  joan
 * work on SPDGActionItem
 *
 * Revision 1.16  2003/05/30 21:09:16  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.15  2003/05/15 18:49:49  tony
 * updated persistant lock functionality.
 *
 * Revision 1.14  2003/05/15 16:26:33  joan
 * work on pop up dialog when editing PDG
 *
 * Revision 1.13  2003/04/29 20:03:58  tony
 * SBRActionItem remove implemented per a middleware
 * incompatibility issue.
 *
 * Revision 1.12  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.transactions.BluePageEntry;
import java.awt.*;
import java.rmi.RemoteException;
import com.ibm.eannounce.eobjects.*;
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DataBase extends EObject {
    private ThinBase tBase = null;

    /**
     * DataBase
     * @param _tBase
     */
    public DataBase(ThinBase _tBase) {
        tBase = _tBase;
    }

    /**
     * ro
     * @return
     * @author Anthony C. Liberto
     */
    public RemoteDatabaseInterface ro() {
        return tBase.getRemoteDatabaseInterface();
    }

    /**
     * getEntityList
     *
     * @author Anthony C. Liberto
     * @return EntityList
     * @param _c
     * @param _ean
     * @param _ei
     */
    public EntityList getEntityList(EANActionItem _ean, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("getEntityList", _ean, _ei));
        } //replay_log
        if (_ean == null) {
            return navigate(_c);
        }
        if (_ean instanceof SearchActionItem) {
            return searchNavigate((SearchActionItem) _ean, _c);
        } else if (_ean instanceof CreateActionItem) {
            return create((CreateActionItem) _ean, _ei, _c);
        } else if (_ean instanceof ExtractActionItem) {
            return extract((ExtractActionItem) _ean, _ei, _c);
        } else if (_ean instanceof EditActionItem) {
            return edit((EditActionItem) _ean, _ei, _c);
        } else if (_ean instanceof NavActionItem) {
            return navigate((NavActionItem) _ean, _ei, _c);
        } else if (_ean instanceof CopyActionItem) { //copyAction
            return navigate((CopyActionItem) _ean, _ei, _c); //copyAction
        } else if (_ean instanceof ABRStatusActionItem) { //cr_2115
            return navigate((ABRStatusActionItem) _ean, _ei, _c); //cr_2115
        }
        return null;
    }

    /**
     * getChangeHistory
     *
     * @author Anthony C. Liberto
     * @return EntityChangeHistoryGroup
     * @param _c
     * @param _ei
     */
    public EntityChangeHistoryGroup getChangeHistory(EntityItem _ei, Component _c) {
        EntityChangeHistoryGroup out = null;
        try {
            out = _ei.getChangeHistoryGroup(ro());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _ei.getChangeHistoryGroup(ro());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_getchangehistory(EntityItem)", _c) == RETRY) {
                        out = getChangeHistory(_ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getchangehistory(EntityItem)", _c) == RETRY) {
                    out = getChangeHistory(_ei, _c);
                }
            }
        }
        return out;
    }

    /**
     * getChangeHistory
     *
     * @author Anthony C. Liberto
     * @return EntityChangeHistoryGroup
     * @param _c
     * @param _ei
     */
    public EntityChangeHistoryGroup getThisChangeHistory(EntityItem _ei, Component _c) {
        EntityChangeHistoryGroup out = null;
        try {
            out = _ei.getThisChangeHistoryGroup(ro());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _ei.getThisChangeHistoryGroup(ro());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_getchangehistory(EntityItem)", _c) == RETRY) {
                        out = getThisChangeHistory(_ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getchangehistory(EntityItem)", _c) == RETRY) {
                    out = getThisChangeHistory(_ei, _c);
                }
            }
        }
        return out;
    }    
    /**
     * getChangeHistory
     *
     * @author Anthony C. Liberto
     * @return AttributeChangeHistoryGroup
     * @param _att
     * @param _c
     */
    public AttributeChangeHistoryGroup getChangeHistory(EANAttribute _att, Component _c) {
        AttributeChangeHistoryGroup out = null;
        try {
            out = ro().getAttributeChangeHistoryGroup(getActiveProfile(), _att);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = ro().getAttributeChangeHistoryGroup(getActiveProfile(), _att);
                } catch (Exception _x) {
                    if (mwError(_x, "mw_getchangehistory(EANAttribute)", _c) == RETRY) {
                        out = getChangeHistory(_att, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getchangehistory(EANAttribute)", _c) == RETRY) {
                    out = getChangeHistory(_att, _c);
                }
            }
        }
        return out;
    }

    /**
     * getMetaColumnOrderGroup
     *
     * @author Anthony C. Liberto
     * @return MetaColumnOrderGroup
     * @param _c
     * @param _group
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup(EntityGroup _group, Component _c) {
        MetaColumnOrderGroup mcog = null;
        try {
            mcog = _group.getMetaColumnOrderGroup(null, ro());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    mcog = _group.getMetaColumnOrderGroup(null, ro());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_getMetaColumnOrderGroup()", _c) == RETRY) {
                        mcog = getMetaColumnOrderGroup(_group, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getMetaColumnOrderGroup()", _c) == RETRY) {
                    mcog = getMetaColumnOrderGroup(_group, _c);
                }
            }
        }
        return mcog;
    }

    /**
     * putBlob
     *
     * @author Anthony C. Liberto
     * @param _b
     * @param _c
     * @param _nls
     */
    public void putBlob(Blob _b, int _nls, Component _c) {
        appendLog("putBlob(<<Blob>>," + _nls + ")");
        try {
            ro().putBlob(getActiveProfile(), _b, _nls);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ro().putBlob(getActiveProfile(), _b, _nls);
                } catch (Exception x) {
                    if (mwError(x, "mw_putblob", _c) == RETRY) {
                        putBlob(_b, _nls, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_putblob", _c) == RETRY) {
                    putBlob(_b, _nls, _c);
                }
            }
        }
        return;
    }

    /**
     * getBookmarkGroup
     *
     * @author Anthony C. Liberto
     * @return BookmarkGroup
     * @param _c
     */
    public BookmarkGroup getBookmarkGroup(Component _c) {
        BookmarkGroup bg = null;
        try {
            bg = ro().getBookmarkGroup(getActiveProfile());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    bg = ro().getBookmarkGroup(getActiveProfile());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_getBookmarkGroup", _c) == RETRY) {
                        bg = getBookmarkGroup(_c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getBookmarkGroup", _c) == RETRY) {
                    bg = getBookmarkGroup(_c);
                }
            }
        }
        return bg;
    }

    /**
     * getBlob
     *
     * @author Anthony C. Liberto
     * @return Blob
     * @param _attCode
     * @param _c
     * @param _eType
     * @param _nls
     * @param _opwg
     */
    public Blob getBlob(String _eType, int _opwg, String _attCode, int _nls, Component _c) {
        Blob out = null;
        appendLog("getBlob(" + _eType + ", " + _opwg + ", " + _attCode + ", " + _nls + ")");
        try {
            out = ro().getBlob(getActiveProfile(), _eType, _opwg, _attCode, _nls);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = ro().getBlob(getActiveProfile(), _eType, _opwg, _attCode, _nls);
                } catch (Exception x) {
                    if (mwError(x, "mw_getblob", _c) == RETRY) {
                        out = getBlob(_eType, _opwg, _attCode, _nls, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getblob", _c) == RETRY) {
                    out = getBlob(_eType, _opwg, _attCode, _nls, _c);
                }
            }
        }
        return out;
    }

    private void reconnect(Component _c) throws RemoteException {
        tBase.databaseConnect(_c);
        return;
    }

    private int mwError(Exception _x, String _str, Component _c) {        
        int r = -1;
        if (_str != null){
        	String ss = Routines.splitString(_str, 100);
        	appendLog("MWError "+ss);
        }
        _x.printStackTrace();
        if (_x instanceof IllegalStateException) { //acl_20031219
            return IGNORE; //acl_20031219
        } //acl_20031219
        
        r = showException(_x, _c, QUESTION_MESSAGE, ABORT_RETRY_IGNORE); //51260
        logError(_x.toString(), r);
        if (r == ABORT) {
            eaccess().exit("abort DataBase");
        } else if (r == IGNORE) {
            return r;
        } else if (_x instanceof java.net.NoRouteToHostException || _x instanceof java.rmi.ConnectException) {
            try {
                reconnect(_c);
            } catch (RemoteException _re) {
                _re.printStackTrace();
            }
        }
        if (r == RETRY) {
            waitFor(2000);
        }
        return r;
    }

    private void logError(String s, int i) {
        appendLog("Error --> " + s.trim() + " {User selected (" + i + ")}");
        return;
    }

    private void waitFor(int i) {
        Thread t = new Thread();
        Routines.sleep(t, i);
        return;
    }

    private EntityList navigate(Component _c) {
        EntityList el = null;
        try {
            el = ro().getEntityList(getActiveProfile());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = ro().getEntityList(getActiveProfile());
                } catch (Exception x) {
                    if (mwError(x, "mw_navigate", _c) == RETRY) {
                        el = navigate(_c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_navigate", _c) == RETRY) {
                    el = navigate(_c);
                }
            }
        }
        return el;
    }

    private EntityList navigate(NavActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        Long t1=null;
        try {
        	t1 = eaccess().timestamp("   DataBase navigate started");
            el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
        } catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		} catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_navigate", _c) == RETRY) {
                        el = navigate(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_navigate", _c) == RETRY) {
                    el = navigate(_ai, _ei, _c);
                }
            }
        }
        eaccess().timestamp("   DataBase navigate ended",t1);
        return el;
    }
    public boolean hasChanges(EntityItem[] _aei, String fromTime, String _actionKey){
    	boolean hasChgs = true;
    	Long t1 = eaccess().timestamp("   DataBase hasChanges started");
    	 try {
    		 hasChgs = ro().hasChanges(getActiveProfile(), _aei, fromTime, _actionKey);
    	 } catch (Exception _ex) {
             _ex.printStackTrace();
         }
         eaccess().timestamp("   DataBase hasChanges ended",t1);
    	
    	return hasChgs;
    }

    /**
     * replay
     *
     * @author Anthony C. Liberto
     * @return EntityList
     * @param _c
     * @param _item
     */
    public EntityList replay(BookmarkItem _item, Component _c) {
        EntityList el = null;

        appendLog("replay(BookmarkItem)");
        if (_item != null) {
            try {
                el = _item.replay(ro(), getActiveProfile());
            } catch (Exception _ex) {
                if (tBase.reconnectMain()) {
                    try {
                        el = _item.replay(ro(), getActiveProfile());
                    } catch (Exception x) {
                        if (mwError(x, "mw_navigate", _c) == RETRY) {
                            el = replay(_item, _c);
                        }
                    }
                } else {
                    if (mwError(_ex, "mw_navigate", _c) == RETRY) {
                        el = replay(_item, _c);
                    }
                }
            }
        }
        return el;
    }

    private EntityList searchNavigate(SearchActionItem _sai, Component _c) {
        EntityList el = null;

        appendLog("searchNavigate(SearchActionItem)");
        try {
            el = _sai.rexec(ro(), getActiveProfile());
        } catch (Exception _ex) {
        	if (_ex instanceof SearchExceedsLimitException){
        		mwError(_ex, "mw_navigate", _c);
        		return el;
        	}
            if (tBase.reconnectMain()) {
                try {
                    el = _sai.rexec(ro(), getActiveProfile());
                } catch (Exception x) {
                    if (mwError(x, "mw_navigate", _c) == RETRY) {
                        el = searchNavigate(_sai, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_navigate", _c) == RETRY) {
                    el = searchNavigate(_sai, _c);
                }
            }
        }
        return el;
    }

    private EntityList edit(EditActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        try {
            el = EntityList.getEntityList(ro(), getNewProfileInstance(), _ai, _ei);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getNewProfileInstance(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_edit", _c) == RETRY) {
                        el = edit(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_edit", _c) == RETRY) {
                    el = edit(_ai, _ei, _c);
                }
            }
        }
        return el;
    }

    private EntityList extract(ExtractActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        try {
            el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
        } catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_extract", _c) == RETRY) {
                        el = extract(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_extract", _c) == RETRY) {
                    el = extract(_ai, _ei, _c);
                }
            }
        }
        return el;
    }

    private EntityList create(CreateActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        try {
            el = EntityList.getEntityList(ro(), getNewProfileInstance(), _ai, _ei);
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getNewProfileInstance(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_create", _c) == RETRY) {
                        el = create(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_create", _c) == RETRY) {
                    el = create(_ai, _ei, _c);
                }
            }
        }
        return el;
    }

    /*
     rexec
    */
    /*cr_ActChain*/
    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return Object
     * @param _c
     * @param _eiChild
     * @param _eiParent
     * @param _lai
     */
    public Object rexec(LinkActionItem _lai, EntityItem[] _eiParent, EntityItem[] _eiChild, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _lai, _eiParent, _eiChild));
        } //replay_log
        _lai.setParentEntityItems(_eiParent);
        _lai.setChildEntityItems(_eiChild);
        
        return rexec(_lai, _c);
    }

    private Object rexec(LinkActionItem _lai, Component _c) {
    	Long t1 = EAccess.eaccess().timestamp("Database LinkAction started");
        Object o = null;
        try {
            o = _lai.rexec(ro(), getActiveProfile());
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (LockException _le) {
            tBase.showMessage(_c, _le.toString());
        } catch(EntityItemException eie){
        	//SR11, SR15 and SR17. dont execute again
        	showException(eie, _c, ERROR_MESSAGE, OK);
        	eie.dereference();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    o = _lai.rexec(ro(), getActiveProfile());
                } catch (LockException _le) {
                    tBase.showMessage(_c, _le.toString());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_linkActionItem", _c) == RETRY) {
                        return rexec(_lai, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_linkActionItem", _c) == RETRY) {
                    return rexec(_lai, _c);
                }
            }
        }
        EAccess.eaccess().timestamp("Database LinkAction ended",t1);
        return o;
    }

    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return WhereUsedList
     * @param _c
     * @param _ei
     * @param _wai
     */
    public WhereUsedList rexec(WhereUsedActionItem _wai, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _wai, _ei));
        } //replay_log
        _wai.setEntityItems(_ei);
        return rexec(_wai, _c);
    }

    private WhereUsedList rexec(WhereUsedActionItem _wai, Component _c) {
        WhereUsedList wl = null;
        try {
            wl = _wai.rexec(ro(), getNewProfileInstance());
        }/*catch(DomainException de) { // RQ0713072645  RQ0713072645-3 will handle this now
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}*/catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    wl = _wai.rexec(ro(), getNewProfileInstance());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_WhereUsedActionItem", _c) == RETRY) {
                        wl = rexec(_wai, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_WhereUsedActionItem", _c) == RETRY) {
                    wl = rexec(_wai, _c);
                }
            }
        }
        return wl;
    }
    /**
     * rexec
     *
     *@param _qai
     *@param _ei
     *@param _c
     *@return QueryList
     */
    public QueryList rexec(QueryActionItem _qai, EntityItem[] _ei, Component _c) {
        _qai.setEntityItems(_ei);
        return rexec(_qai, _c);
    }

    private QueryList rexec(QueryActionItem _qai, Component _c) {
    	QueryList ql = null;
        try {
            ql = _qai.rexec(ro(), getNewProfileInstance());
        }
        catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ql = _qai.rexec(ro(), getNewProfileInstance());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_QueryActionItem", _c) == RETRY) {
                        ql = rexec(_qai, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_QueryActionItem", _c) == RETRY) {
                    ql = rexec(_qai, _c);
                }
            }
        }
        return ql;
    }
    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return MatrixList
     * @param _c
     * @param _ei
     * @param _mai
     */
    public MatrixList rexec(MatrixActionItem _mai, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _mai, _ei));
        } //replay_log
        _mai.setEntityItems(_ei);
        return rexec(_mai, _c);
    }

    private MatrixList rexec(MatrixActionItem _mai, Component _c) {
        MatrixList ml = null;
        try {
            ml = _mai.rexec(ro(), getNewProfileInstance());
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		} catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ml = _mai.rexec(ro(), getNewProfileInstance());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_MatrixActionItem", _c) == RETRY) {
                        ml = rexec(_mai, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_MatrixActionItem", _c) == RETRY) {
                    ml = rexec(_mai, _c);
                }
            }
        }
        return ml;
    }

    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _ei
     * @param _flow
     */
    public boolean rexec(WorkflowActionItem _flow, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _flow, _ei));
        } //replay_log
        _flow.setEntityItems(_ei);
        return rexec(_flow, _c);
    }

    private boolean rexec(WorkflowActionItem _flow, Component _c) {
        try {
            _flow.rexec(ro(), getActiveProfile());
            return true;
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (LockException _le) {
            tBase.showMessage(_c, _le.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _flow.rexec(ro(), getActiveProfile());
                    return true;
                } catch (LockException _le) {
                    tBase.showMessage(_c, _le.toString());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_WorkFlowActionItem", _c) == RETRY) {
                        return rexec(_flow, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_WorkFlowActionItem", _c) == RETRY) {
                    return rexec(_flow, _c);
                }
            }
        }
        return false;
    }

    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _del
     * @param _ei
     */
    public boolean rexec(DeleteActionItem _del, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _del, _ei));
        } //replay_log
        _del.setEntityItems(_ei);
        return rexec(_del, _c);
    }

    private boolean rexec(DeleteActionItem _del, Component _c) {
        try {
            _del.rexec(ro(), getActiveProfile());
            return true;
        } catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		} catch (LockException _le) {
            tBase.showMessage(_c, _le.toString());
        } catch (EANBusinessRuleException _ean) { //53761
            tBase.showMessage(_c, _ean.toString()); //53761
        } catch (MiddlewareRequestException _mre) { //53832
            tBase.showMessage(_c, _mre.toString()); //53832
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _del.rexec(ro(), getActiveProfile());
                    return true;
                } catch (LockException _le) {
                    tBase.showMessage(_c, _le.toString());
                } catch (EANBusinessRuleException _ean) { //53761
                    tBase.showMessage(_c, _ean.toString()); //53761
                } catch (MiddlewareRequestException _mre) { //53832
                    tBase.showMessage(_c, _mre.toString()); //53832
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_DeleteActionItem", _c) == RETRY) {
                        return rexec(_del, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_DeleteActionItem", _c) == RETRY) {
                    return rexec(_del, _c);
                }
            }
        }
        return false;
    }

    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _ei
     * @param _lock
     */
    public boolean rexec(LockActionItem _lock, EntityItem[] _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _lock, _ei));
        } //replay_log
        _lock.setEntityItems(_ei);
        return rexec(_lock, _c);
    }

    private boolean rexec(LockActionItem _lock, Component _c) {
        try {
            _lock.rexec(ro(), getActiveProfile());
            return true;
        }catch(DomainException de) { // RQ0713072645
//            tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _lock.rexec(ro(), getActiveProfile());
                    return true;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_LockActionItem", _c) == RETRY) {
                        return rexec(_lock, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_LockActionItem", _c) == RETRY) {
                    return rexec(_lock, _c);
                }
            }
        }
        return false;
    }

    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _up
//JT_20060307
    public boolean rexec(HWUPGRADEActionItem _up, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rexec", _up));
        } //replay_log
        try {
            _up.rexec(ro(), getActiveProfile());
            return true;
        } catch (SBRException _e) { //22814
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _up.rexec(ro(), getActiveProfile());
                    return true;
                } catch (SBRException _e) { //22814
                    _e.printStackTrace();
                    tBase.showMessage(_c, _e.toString());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_HWUPGRADEActionItem", _c) == RETRY) {
                        return rexec(_up, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_HWUPGRADEActionItem", _c) == RETRY) {
                    return rexec(_up, _c);
                }
            }
        }
        return false;
    }
*/
    /**
     * rexec
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _up
//JT_20060307
	 public boolean rexec(HWPDGActionItem _up, Component _c) {
        try {
            _up.rexec(ro(), getActiveProfile());
            return true;
        } catch (SBRException _e) { //22814
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _up.rexec(ro(), getActiveProfile());
                    return true;
                } catch (SBRException _e) { //22814
                    _e.printStackTrace();
                    tBase.showMessage(_c, _e.toString());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rexec_HWPDGActionItem", _c) == RETRY) {
                        return rexec(_up, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rexec_HWPDGActionItem", _c) == RETRY) {
                    return rexec(_up, _c);
                }
            }
        }
        return false;
    }
*/
    /**
     * rget
     *
     * @author Anthony C. Liberto
     * @return HWUpgradeList
     * @param _c
     * @param _ei
     * @param _up
//JT_20060307
	 public HWUpgradeList rget(HWUPGRADEActionItem _up, EntityItem _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("rget", _up, _ei));
        } //replay_log
        _up.setEntityItem(_ei);
        return rget(_up, _c);
    }

    private HWUpgradeList rget(HWUPGRADEActionItem _up, Component _c) {
        HWUpgradeList hl = null;
        try {
            hl = _up.rgetHWUpgradeList(ro(), getActiveProfile());
        } catch (SBRException _e) {
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    hl = _up.rgetHWUpgradeList(ro(), getActiveProfile());
                } catch (SBRException _e) {
                    _e.printStackTrace();
                    tBase.showMessage(_c, _e.toString());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_rget_HWUPGRADEActionItem", _c) == RETRY) {
                        hl = rget(_up, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_rget_HWUPGRADEActionItem", _c) == RETRY) {
                    hl = rget(_up, _c);
                }
            }
        }
        return hl;
    }
*/
    /**
     * setHWPDGEntityItem
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _ei
     * @param _up
//JT_20060307
    public Exception setHWPDGEntityItem(HWPDGActionItem _up, EntityItem _ei) {
        Exception e = null;
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("setHWPDGEntityItem", _up, _ei));
        } //replay_log
        try {
            _up.setEntityItem(_ei, null, ro(), getActiveProfile());
        } catch (SBRException _x) {
            e = _x;
        } catch (Exception _ex) {
            EAccess.report(_ex,false);
            if (tBase.reconnectMain()) {
                try {
                    _up.setEntityItem(_ei, null, ro(), getActiveProfile());
                } catch (SBRException _x) {
                    e = _x;
                }
            }
        }
        return e;
    }
*/
    /**
     * commit
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _c
     * @param _table
     */
    public Exception commit(RowSelectableTable _table, Component _c) {
        Exception e = null;
        try {
            _table.commit(ro());
        } catch (MiddlewareSFRuleException _msfre) { //51991
            _msfre.printStackTrace(); //51991
            return _msfre; //51991
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            return _mbre;
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            return _bre;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.commit(ro());
                } catch (MiddlewareSFRuleException _msfre) { //51991
                    _msfre.printStackTrace(); //51991
                    return _msfre; //51991
                } catch (MiddlewareBusinessRuleException _mbre) {
                    _mbre.printStackTrace();
                    return _mbre;
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    return _bre;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_commit_RowSelectableTable", _c) == RETRY) {
                        e = commit(_table, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_commit_RowSelectableTable", _c) == RETRY) {
                    e = commit(_table, _c);
                }
            }
        }
        return e;
    }

    /**
     * commit
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _c
     * @param _table
     */
    public Exception commit(MetaColumnOrderTable _table, Component _c) {
        Exception e = null;
        try {
            _table.commit(null, ro());
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            return _mbre;
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            return _bre;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.commit(null, ro());
                } catch (MiddlewareBusinessRuleException _mbre) {
                    _mbre.printStackTrace();
                    return _mbre;
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    return _bre;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_commit_MetaColumnOrderTable", _c) == RETRY) {
                        e = commit(_table, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_commit_MetaColumnOrderTable", _c) == RETRY) {
                    e = commit(_table, _c);
                }
            }
        }
        return e;
    }

    /**
     * commit
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _c
     * @param _ei
     */
    public Exception commit(EntityItem _ei, Component _c) {
        Exception e = null;
        try {
            _ei.commit(null, ro());
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            return _mbre;
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            return _bre;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _ei.commit(null, ro());
                } catch (MiddlewareBusinessRuleException _mbre) {
                    _mbre.printStackTrace();
                    return _mbre;
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    return _bre;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_commit_EntityItem", _c) == RETRY) {
                        e = commit(_ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_commit_EntityItem", _c) == RETRY) {
                    e = commit(_ei, _c);
                }
            }
        }
        return e;
    }

    /**
     * getLockList
     *
     * @author Anthony C. Liberto
     * @return LockList
     * @param _c
     * @param _getAll
     */
    public LockList getLockList(boolean _getAll, Component _c) {
System.out.println("DataBase.getLockList entered _getAll "+_getAll+" _c: "+_c);
        LockList ll = null;
        try {
            ll = ro().getLockList(getActiveProfile(), _getAll);
        } catch (Exception x) {
            if (mwError(x, "mw_getLockList", _c) == RETRY) {
                ll = getLockList(_getAll, _c);
            }
        }
System.out.println("DataBase.getLockList ll "+ll);

        return ll;
    }

    /**
     * getAllSoftLocksForWGID
     *
     * @author Anthony C. Liberto
     * @return LockList
     * @param _c
     */
    public LockList getAllSoftLocksForWGID(Component _c) {
        LockList ll = null;
        try {
            ll = ro().getAllSoftLocksForWGID(getActiveProfile());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ll = ro().getAllSoftLocksForWGID(getActiveProfile());
                } catch (Exception x) {
                    if (mwError(x, "mw_getLockList", _c) == RETRY) {
                        ll = getAllSoftLocksForWGID(_c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getLockList", _c) == RETRY) {
                    ll = getAllSoftLocksForWGID(_c);
                }
            }
        }
        return ll;
    }

    /**
     * genPDGData
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _ei
     * @param _pdgai
     */
    public boolean genPDGData(PDGActionItem _pdgai, EntityItem _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("genPDGData", _pdgai, _ei));
        } //replay_log
        _pdgai.setEntityItem(_ei);
        return genPDGData(_pdgai, _c);
    }

    private boolean genPDGData(PDGActionItem _pdgai, Component _c) {
        try {
            _pdgai.rexec(ro(), getActiveProfile());
            return true;
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (SBRException _e) {
            if (_e.resetPDGCollectInfo()) {
                _pdgai.resetPDGCollectInfo();
            }
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            _ex.printStackTrace();
            tBase.showMessage(_c, _ex.toString());
            /*
            			if (tBase.reconnectMain()) {
            				try {
            					_pdgai.rexec(ro(),getActiveProfile());
            					return true;
            				} catch (SBRException _e) {
            					if (_e.resetPDGCollectInfo()) {
            						_pdgai.resetPDGCollectInfo();
            					}
            					_e.printStackTrace();
            					tBase.showMessage(_c, _e.toString());
            				} catch (Exception _x) {
            					if (_pdgai instanceof HWProdInitialPDG && _x instanceof java.rmi.ServerError) {		//52489
            						return false;																	//52489
            					}

            					if (MWError(_x, _pdgai.toString() + " REXEC", _c) == RETRY) {
            						return genPDGData(_pdgai, _c);
            					}
            				}
            			} else {
            				if (MWError(_ex, _pdgai.toString() + " REXEC", _c) == RETRY) {
            					return genPDGData(_pdgai, _c);
            				}
            			}
            */
        }
        return false;
    }

    /**
     * viewMissingPDGData
     *
     * @author Anthony C. Liberto
     * @return byte[]
     * @param _c
     * @param _ei
     * @param _pdgai
     */
    public byte[] viewMissingPDGData(PDGActionItem _pdgai, EntityItem _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("viewMissingPDGData", _pdgai, _ei));
        } //replay_log
        _pdgai.setEntityItem(_ei);
        return viewMissingPDGData(_pdgai, _c);
    }

    private byte[] viewMissingPDGData(PDGActionItem _pdgai, Component _c) {
        byte[] baReturn = null;
        try {
            baReturn = _pdgai.rviewMissingData(ro(), getActiveProfile());
        } catch (SBRException _e) {
            if (_e.resetPDGCollectInfo()) {
                _pdgai.resetPDGCollectInfo();
            }
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    baReturn = _pdgai.rviewMissingData(ro(), getActiveProfile());
                } catch (SBRException _e) {
                    if (_e.resetPDGCollectInfo()) {
                        _pdgai.resetPDGCollectInfo();
                    }
                    _e.printStackTrace();
                    tBase.showMessage(_c, _e.toString());
                } catch (Exception _x) {
                    if (mwError(_x, _pdgai.toString() + " VIEWMISSINGDATA", _c) == RETRY) {
                        return viewMissingPDGData(_pdgai, _c);
                    }
                }
            } else {
                if (mwError(_ex, _pdgai.toString() + " VIEWMISSINGDATA", _c) == RETRY) {
                    return viewMissingPDGData(_pdgai, _c);
                }
            }
        }
        return baReturn;
    }

    /**
     * genPDGData
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _ei
     * @param _spdgai
     */
    public boolean genPDGData(SPDGActionItem _spdgai, EntityItem _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("genPDGData", _spdgai, _ei));
        } //replay_log
        _spdgai.setEntityItem(_ei);
        return genPDGData(_spdgai, _c);
    }

    private boolean genPDGData(SPDGActionItem _spdgai, Component _c) {
        try {
            _spdgai.rexec(ro(), getActiveProfile());
            return true;
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (SBRException _e) {
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            _ex.printStackTrace();
            tBase.showMessage(_c, _ex.toString());

            /*  it's not always good to rexec again, generating duplicate data

            			if (tBase.reconnectMain()) {
            				try {
            					_spdgai.rexec(ro(),getActiveProfile());
            					return true;
            				} catch (SBRException _e) {
            					_e.printStackTrace();
            					tBase.showMessage(_c, _e.toString());
            				} catch (Exception _x) {
            					if (MWError(_x, _spdgai.toString() + " REXEC", _c) == RETRY) {
            						return genPDGData(_spdgai, _c);
            					}
            				}
            			} else {
            				if (MWError(_ex, _spdgai.toString() + " REXEC", _c) == RETRY) {
            					return genPDGData(_spdgai, _c);
            				}
            			}
            */
        }
        return false;
    }

    /**
     * viewMissingPDGData
     *
     * @author Anthony C. Liberto
     * @return byte[]
     * @param _c
     * @param _ei
     * @param _spdgai
     */
    public byte[] viewMissingPDGData(SPDGActionItem _spdgai, EntityItem _ei, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            appendLog(getCommand("viewMissingPDGData", _spdgai, _ei));
        } //replay_log
        _spdgai.setEntityItem(_ei);
        return viewMissingPDGData(_spdgai, _c);
    }

    private byte[] viewMissingPDGData(SPDGActionItem _spdgai, Component _c) {
        byte[] baReturn = null;
        try {
            baReturn = _spdgai.rviewMissingData(ro(), getActiveProfile());
        } catch (SBRException _e) {
            _e.printStackTrace();
            tBase.showMessage(_c, _e.toString());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    baReturn = _spdgai.rviewMissingData(ro(), getActiveProfile());
                } catch (SBRException _e) {
                    _e.printStackTrace();
                    tBase.showMessage(_c, _e.toString());
                } catch (Exception _x) {
                    if (mwError(_x, _spdgai.toString() + " VIEWMISSINGDATA", _c) == RETRY) {
                        return viewMissingPDGData(_spdgai, _c);
                    }
                }
            } else {
                if (mwError(_ex, _spdgai.toString() + " VIEWMISSINGDATA", _c) == RETRY) {
                    return viewMissingPDGData(_spdgai, _c);
                }
            }
        }
        return baReturn;
    }

    /*
     joan.20030625
     */
    /**
     * getWhereUsedList
     *
     * @author Anthony C. Liberto
     * @return WhereUsedList
     * @param _c
     * @param _key
     * @param _table
     */
    public WhereUsedList getWhereUsedList(RowSelectableTable _table, String _key, Component _c) {
        WhereUsedList wul = null;
        try {
            wul = _table.getWhereUsedList(_table.getRowIndex(_key), null, ro(), getActiveProfile());
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    wul = _table.getWhereUsedList(_table.getRowIndex(_key), null, ro(), getActiveProfile());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_table_getWhereUsedList", _c) == RETRY) {
                        wul = getWhereUsedList(_table, _key, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_table_getWhereUsedList", _c) == RETRY) {
                    wul = getWhereUsedList(_table, _key, _c);
                }
            }
        }
        return wul;
    }

    /**
     * \getActionItemsAsArray
     *
     * @author Anthony C. Liberto
     * @return Object[]
     * @param _c
     * @param _row
     * @param _table
     */
    public Object[] getActionItemsAsArray(RowSelectableTable _table, int _row, Component _c) {
        Object[] ean = null;
        try {
            ean = _table.getActionItemsAsArray(_row, null, ro(), getActiveProfile());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ean = _table.getActionItemsAsArray(_row, null, ro(), getActiveProfile());
                } catch (Exception _x) {
                    if (mwError(_x, "mw_table_getActionItemAsArray", _c) == RETRY) {
                        ean = getActionItemsAsArray(_table, _row, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_table_getActionItemAsArray", _c) == RETRY) {
                    ean = getActionItemsAsArray(_table, _row, _c);
                }
            }
        }
        return ean;
    }

   //private String getCommand(String _method, EANActionItem _action) {
   //     return "<database method=\"" + _method + "\" " + getActionCommand(_action) + "/>";
   // }

    private String getCommand(String _method, EANActionItem _action, EntityItem[] _parent, EntityItem[] _child) {
        return "<database method=\"" + _method + "\" " + getActionCommand(_action) + getEntityItems("ParentItems", _parent) + "\" " + getEntityItems("ChildItems", _child) + "\"/>";
    }

    private String getCommand(String _method, EANActionItem _action, EntityItem[] _ei) {
        return "<database method=\"" + _method + "\" " + getActionCommand(_action) + getEntityItems("EntityItems", _ei) + "\"/>";
    }

    private String getCommand(String _method, EANActionItem _action, EntityItem _ei) {
        return "<database method=\"" + _method + "\" " + getActionCommand(_action) + getEntityItem("EntityItems", _ei) + "\"/>";
    }

    private String getActionCommand(EANActionItem _action) {
        return "ActionCommand=\"" + ((_action == null) ? "" : new String(_action.getKey())) + "\" "; //replay_log
    }

    /**
     * \getEntityItems
     *
     * @author Anthony C. Liberto
     * @return String
     * @param _ei
     * @param _str
     */
    public String getEntityItems(String _str, EntityItem[] _ei) {
        StringBuffer sb = new StringBuffer();
        sb.append(_str + "=\"");
        if (_ei == null) {
            return sb.toString();
        }
        for (int i = 0; i < _ei.length; ++i) {
            if (i > 0) {
                sb.append(ARRAY_DELIMIT);
            }
            sb.append(new String(_ei[i].getKey())); //replay_log
        }
        return sb.toString();
    }

    private String getEntityItem(String _str, EntityItem _ei) {
        return _str + "=\"" + new String(_ei.getKey()); //replay_log
    }

    /*
     restore
     */
    /**
     * getInactiveGroup
     *
     * @author Anthony C. Liberto
     * @return InactiveGroup
     * @param _c
     * @param _date
     * @param _viewAll
     */
    public InactiveGroup getInactiveGroup(String _date, boolean _viewAll, Component _c) {
        InactiveGroup ig = null;
        appendLog("getInactiveGroup(" + _date + ", " + _viewAll + ", Component)");
        try {
            ig = ro().getInactiveGroup(getActiveProfile(), _date, _viewAll);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ig = ro().getInactiveGroup(getActiveProfile(), _date, _viewAll);
                } catch (Exception _x) {
                    if (mwError(_x, "mw_table_getInactiveGroup", _c) == RETRY) {
                        ig = getInactiveGroup(_date, _viewAll, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_table_getInactiveGroup", _c) == RETRY) {
                    ig = getInactiveGroup(_date, _viewAll, _c);
                }
            }
        }
        return ig;
    }

    /**
     * removeInactiveItem
     *
     * @author Anthony C. Liberto
     * @return InactiveGroup
     * @param _c
     * @param _group
     * @param _items
     */
    public InactiveGroup removeInactiveItem(InactiveGroup _group, InactiveItem[] _items, Component _c) {
        InactiveGroup ig = null;
        try {
            ig = _group.removeInactiveItem(null, ro(), getActiveProfile(), _items);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ig = _group.removeInactiveItem(null, ro(), getActiveProfile(), _items);
                } catch (Exception _x) {
                    if (mwError(_x, "mw_table_removeInactiveItem", _c) == RETRY) {
                        ig = removeInactiveItem(_group, _items, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_table_removeInactiveItem", _c) == RETRY) {
                    ig = removeInactiveItem(_group, _items, _c);
                }
            }
        }
        return ig;
    }

    /*
     52910
     */

    /**
     * commit
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _c
     * @param _mcog
     */
    public Exception commit(MetaColumnOrderGroup _mcog, Component _c) {
        Exception e = null;
        try {
            _mcog.commit(null, ro());
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            return _mbre;
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            return _bre;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _mcog.commit(null, ro());
                } catch (MiddlewareBusinessRuleException _mbre) {
                    _mbre.printStackTrace();
                    return _mbre;
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    return _bre;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_commit_MetaColumnOrderGroup", _c) == RETRY) {
                        e = commit(_mcog, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_commit_MetaColumnOrderGroup", _c) == RETRY) {
                    e = commit(_mcog, _c);
                }
            }
        }
        return e;
    }

    /**
     * resetToDefaults
     *
     * @author Anthony C. Liberto
     * @return Exception
     * @param _c
     * @param _mcog
     */
    public Exception resetToDefaults(MetaColumnOrderGroup _mcog, Component _c) {
        Exception e = null;
        try {
            _mcog.resetToDefaults(null, ro());
        } catch (MiddlewareBusinessRuleException _mbre) {
            _mbre.printStackTrace();
            return _mbre;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _mcog.resetToDefaults(null, ro());
                } catch (MiddlewareBusinessRuleException _mbre) {
                    _mbre.printStackTrace();
                    return _mbre;
                } catch (Exception _x) {
                    if (mwError(_x, "mw_resetToDefaults_MetaColumnOrderGroup", _c) == RETRY) {
                        e = commit(_mcog, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_resetToDefaults_MetaColumnOrderGroup", _c) == RETRY) {
                    e = commit(_mcog, _c);
                }
            }
        }
        return e;
    }

    /*
     moved all dbcalls to centralized location
     */
    /**
     * link
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eiChild
     * @param _eiParent
     * @param _linkCount
     * @param _linkType
     * @param _rel
     * @param _str
     */
    public boolean link(String _str, EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel, int _linkType, int _linkCount) {
    	Long t1 = EAccess.eaccess().timestamp("   DataBase.link3 started");
    	boolean ok = true;
        try {
            EANUtility.linkEntityItems(ro(), getActiveProfile(), _str, _eiParent, _eiChild, _rel, _linkType, _linkCount);
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            ok= false;
        } catch(MiddlewareBusinessRuleException eie){
        	//SR11, SR15 and SR17. dont execute again
        	showException(eie, EAccess.eaccess().getLogin(), ERROR_MESSAGE, OK);
        	ok=false;
        }catch (MiddlewareException _me) {
            _me.printStackTrace();
            ok= false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            ok= false;
        }catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    EANUtility.linkEntityItems(ro(), getActiveProfile(), _str, _eiParent, _eiChild, _rel, _linkType, _linkCount);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    ok= false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    ok= false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    ok= false;
                } catch (RemoteException _re) {
                    _re.printStackTrace();
                    ok= false;
                }
            } else {
                _ex.printStackTrace();
                ok= false;
            }
        }
        EAccess.eaccess().timestamp("   DataBase.link3 ended",t1);
        return ok;
    }

    /**
     * rollback
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _mcot
     */
    public boolean rollback(MetaColumnOrderTable _mcot) {
        try {
            _mcot.rollback(null, ro());
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
            return false;
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return false;
        } catch (java.sql.SQLException _sql) {
            _sql.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _mcot.rollback(null, ro());
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                    return false;
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return false;
                } catch (java.sql.SQLException _sql) {
                    _sql.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * resetToDefaults
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _mcot
     */
    public boolean resetToDefaults(MetaColumnOrderTable _mcot) {
        try {
            _mcot.resetToDefaults(null, ro());
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
            return false;
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return false;
        } catch (java.sql.SQLException _sql) {
            _sql.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _mcot.resetToDefaults(null, ro());
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                    return false;
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return false;
                } catch (java.sql.SQLException _sql) {
                    _sql.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * setUpdateDefaults
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _b
     * @param _mcot
     */
    public boolean setUpdateDefaults(MetaColumnOrderTable _mcot, boolean _b) {
        try {
            _mcot.setUpdateDefaults(_b);
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _mcot.setUpdateDefaults(_b);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * addToCart
     *
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _ei
     */
    public EntityGroup addToCart(EntityList _eList, EntityItem[] _ei) {
        EntityGroup out = null;
        try {
            out = _eList.addToCart(ro(), _ei, true);
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return null;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return null;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return null;
        } catch (RemoteException _remote) {
            _remote.printStackTrace();
            return null;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _eList.addToCart(ro(), _ei, true);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return null;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return null;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return null;
                } catch (RemoteException _remote) {
                    _remote.printStackTrace();
                    return null;
                }
            } else {
                _ex.printStackTrace();
                return null;
            }
        }
        return out;
    }

    /**
     * linkAll
     *
     * @author Anthony C. Liberto
     * @return OPICMList
     * @param _b
     * @param _copies
     * @param _eiChild
     * @param _eiParent
     * @param _iType
     * @param _linkType
     * @param _c
     */
//MN23855544    public OPICMList linkAll(String _linkType, EntityItem[] _eiParent, EntityItem[] _eiChild, int _iType, int _copies, boolean _b) {
    public OPICMList linkAll(String _linkType, EntityItem[] _eiParent, EntityItem[] _eiChild, int _iType, int _copies, boolean _b, Component _c) {		//MN23855544
    	Long t1 = EAccess.eaccess().timestamp("   DataBase.linkall started");
        OPICMList oList = null;
        try {
            oList = EANUtility.linkAllEntityItems(ro(), null, getActiveProfile(), _linkType, _eiParent, _eiChild, _iType, _copies, _b);
		} catch (LinkException _le) {						//MN23855544
            showException(_le, _c, ERROR_MESSAGE, OK); 	//MN23855544
			_le.printStackTrace();							//MN23855544
        } catch(MiddlewareBusinessRuleException eie){
        	//SR11, SR15 and SR17. dont execute again
        	showException(eie, _c, ERROR_MESSAGE, OK);
        }catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
        } catch (RemoteException _re) {
            _re.printStackTrace();
        } catch (java.sql.SQLException _sql) {
            _sql.printStackTrace();
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    oList = EANUtility.linkAllEntityItems(ro(), null, getActiveProfile(), _linkType, _eiParent, _eiChild, _iType, _copies, _b);
				} catch (LinkException _le) {						//MN23855544
                    showException(_le, _c, ERROR_MESSAGE, OK); 	//MN23855544
					_le.printStackTrace();							//MN23855544
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                } catch (RemoteException _re) {
                    _re.printStackTrace();
                } catch (java.sql.SQLException _sql) {
                    _sql.printStackTrace();
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        EAccess.eaccess().timestamp("   DataBase.linkall ended",t1);
        return oList;
    }

    /**
     * cloneEntityItem
     *
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _eList
     * @param _ei
     */
    public EntityGroup cloneEntityItem(EntityList _eList, EntityItem[] _ei) {
        EntityGroup out = null;
        try {
            out = EANUtility.cloneEntityItems(_eList, getActiveProfile(), ro(), _ei);
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
        } catch (MiddlewareShutdownInProgressException _msipe) {
            _msipe.printStackTrace();
        } catch (RemoteException _re) {
            _re.printStackTrace();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = EANUtility.cloneEntityItems(_eList, getActiveProfile(), ro(), _ei);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                } catch (MiddlewareShutdownInProgressException _msipe) {
                    _msipe.printStackTrace();
                } catch (RemoteException _re) {
                    _re.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return out;
    }

    /**
     * unlock
     *
     * @author Anthony C. Liberto
     * @return String
     * @param _li
     * @param _ll
     */
    public String unlock(LockList _ll, LockItem[] _li) {
        try {
            _ll.removeLockItem(null, ro(), getActiveProfile(), _li);
			if (logLock()) {
				logLock("unlock",_ll);
			}
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return _mre.toString();
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return _me.toString();
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return _shut.toString();
        } catch (java.sql.SQLException _sql) {
            _sql.printStackTrace();
            return _sql.toString();
        } catch (java.rmi.RemoteException _rmi) {
            _rmi.printStackTrace();
            return _rmi.toString();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _ll.removeLockItem(null, ro(), getActiveProfile(), _li);
					if (logLock()) {
						logLock("unlock",_ll);
					}
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return _mre.toString();
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return _me.toString();
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return _shut.toString();
                } catch (java.sql.SQLException _sql) {
                    _sql.printStackTrace();
                    return _sql.toString();
                } catch (java.rmi.RemoteException _rmi) {
                    _rmi.printStackTrace();
                    return _rmi.toString();
                }
            } else {
                return _ex.toString();
            }
        }
        return null;
    }

    /**
     * getEntityGroup
     *
     * @author Anthony C. Liberto
     * @return EntityGroup
     * @param _i
     * @param _mel
     */
    public EntityGroup getEntityGroup(MetaEntityList _mel, int _i) {
        EntityGroup eg = null;
        try {
            eg = _mel.getEntityGroup(ro(), _i);
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    eg = _mel.getEntityGroup(ro(), _i);
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return eg;
    }

    /**
     * setSearchString
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _sai
     * @param _str
     */
    public boolean setSearchString(SearchActionItem _sai, String _str) {
        try {
            _sai.setSearchString(_str);
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _sai.setSearchString(_str);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * addBookmarkItem
     *
     * @author Anthony C. Liberto
     * @return BookmarkItem
     * @param _bgt
     * @param _desc
     * @param _item
     * @param _items
     */
    public BookmarkItem addBookmarkItem(BookmarkGroupTable _bgt, EANActionItem _item, EANActionItem[] _items, String _desc) {
        BookmarkItem item = null;
        if (_item != null && _desc != null) {
            try {
                item = _bgt.addNewBookmarkItem(_item, _desc);
                if (_items != null) {
                    item.setActionHistory(_items);
                }
            } catch (MiddlewareRequestException _mre) {
                _mre.printStackTrace();
            } catch (DuplicateBookmarkException _dup) {
                _dup.printStackTrace();
            } catch (Exception _ex) {
                if (tBase.reconnectMain()) {
                    try {
                        item = _bgt.addNewBookmarkItem(_item, _desc);
                        if (_items != null) {
                            item.setActionHistory(_items);
                        }
                    } catch (DuplicateBookmarkException _dupl) {
                        _dupl.printStackTrace();
                    } catch (MiddlewareRequestException _mre) {
                        _mre.printStackTrace();
                    }
                } else {
                    _ex.printStackTrace();
                }
            }
        }
        return item;
    }

    /**
     * saveBookmark
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bgt
     * @param _item
     */
    public boolean saveBookmark(BookmarkGroupTable _bgt, BookmarkItem _item) {
        try {
            _bgt.storeRow(ro(), _item.getKey());
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
            return false;
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return false;
        } catch (BookmarkException _book) {
            _book.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _bgt.storeRow(ro(), _item.getKey());
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                    return false;
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return false;
                } catch (BookmarkException _book) {
                    _book.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * deleteBookmarks
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bgt
     * @param _keys
     */
    public boolean deleteBookmarks(BookmarkGroupTable _bgt, String[] _keys) {
        try {
            _bgt.deleteRows(ro(), _keys);
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
            return false;
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _bgt.deleteRows(ro(), _keys);
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                    return false;
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * deleteBookmark
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bgt
     * @param _key
     */
    public boolean deleteBookmark(BookmarkGroupTable _bgt, String _key) {
        try {
            _bgt.deleteRow(ro(), _key);
        } catch (java.rmi.RemoteException _re) {
            _re.printStackTrace();
            return false;
        } catch (MiddlewareRequestException _mre) {
            _mre.printStackTrace();
            return false;
        } catch (MiddlewareException _me) {
            _me.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException _shut) {
            _shut.printStackTrace();
            return false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _bgt.deleteRow(ro(), _key);
                } catch (java.rmi.RemoteException _re) {
                    _re.printStackTrace();
                    return false;
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                    return false;
                } catch (MiddlewareException _me) {
                    _me.printStackTrace();
                    return false;
                } catch (MiddlewareShutdownInProgressException _shut) {
                    _shut.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * lock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _table
     */
    public boolean lock(RowSelectableTable _table, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {
        try {
            _table.lock(ro(), null, _ll, _prof, _owner, _lockType);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.lock(ro(), null, _ll, _prof, _owner, _lockType);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock() && _owner != null) {
			logLock("lock", _ll);
		}
        return true;
    }

    /**
     * lock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _row
     * @param _table
     */
    public boolean lock(RowSelectableTable _table, int _row, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {
        try {
            _table.lock(_row, ro(), null, _ll, _prof, _owner, _lockType);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.lock(_row, ro(), null, _ll, _prof, _owner, _lockType);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock()) {
			logLock("lock", _ll);
		}
        return true;
    }

    /**
     * unlock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _table
     */
    public boolean unlock(RowSelectableTable _table, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {
        try {
            _table.unlock(ro(), null, _ll, _prof, _owner, _lockType);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.unlock(ro(), null, _ll, _prof, _owner, _lockType);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock()) {
			logLock("unlock", _ll);
		}
		return true;
    }

    /**
     * unlock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _row
     * @param _table
     */
    public boolean unlock(RowSelectableTable _table, int _row, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {
        try {
            _table.unlock(_row, ro(), null, _ll, _prof, _owner, _lockType);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.unlock(_row, ro(), null, _ll, _prof, _owner, _lockType);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock()) {
			logLock("unlock", _ll);
		}
		return true;
    }

    /**
     * getBlobValue
     *
     * @author Anthony C. Liberto
     * @return byte[]
     * @param _att
     */
    public byte[] getBlobValue(EANBlobAttribute _att) {
        byte[] out = null;
        try {
            out = _att.getBlobValue(ro(), null);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _att.getBlobValue(ro(), null);
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return out;
    }

    /**
     * generatePicklist
     *
     * @author Anthony C. Liberto
     * @return EntityList
     * @param _bMatrix
     * @param _row
     * @param _rst
     */
    public EntityList generatePicklist(RowSelectableTable _rst, int _row, boolean _bMatrix) {
        try {
            return _rst.generatePickList(_row, null, ro(), getActiveProfile(), _bMatrix);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    return _rst.generatePickList(_row, null, ro(), getActiveProfile(), _bMatrix);
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * create
     *
     * @author Anthony C. Liberto
     * @return EntityList
     * @param _row
     * @param _rst
     */
    public EntityList create(RowSelectableTable _rst, int _row) {
        try {
            return _rst.create2(_row, null, ro(), getActiveProfile());
        } catch(DomainException de) { // RQ0713072645
			showException(de, null, ERROR_MESSAGE, OK);
			de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    return _rst.create(_row, null, ro(), getActiveProfile());
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * link
     *
     * @author Anthony C. Liberto
     * @return EANFoundation[]
     * @param _c
     * @param _ei
     * @param _i
     * @param _linkType
     * @param _rst
     */
    public EANFoundation[] link(RowSelectableTable _rst, int[] _i, EntityItem[] _ei, String _linkType, Component _c) {
    	Long t11 = EAccess.eaccess().timestamp("   DataBase.link1 started");
        EANFoundation[] out = null;
        try {
            out = _rst.link(_i, null, ro(), getActiveProfile(), _ei, _linkType);
        } catch (EANBusinessRuleException _bre) { // RQ0713072645 added domain exception to this exc
            _bre.printStackTrace();
            showException(_bre, _c, ERROR_MESSAGE, OK);   // RQ0713072645
            _bre.dereference();
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _rst.link(_i, null, ro(), getActiveProfile(), _ei, _linkType);
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        EAccess.eaccess().timestamp("   DataBase.link1 ended",t11);
        return out;
    }

    /**
     * unlink
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _i
     * @param _rst
     */
    public boolean unlink(RowSelectableTable _rst, int[] _i, Component _c) {
    	boolean ok = true;
    	Long t11 = EAccess.eaccess().timestamp("   DataBase.unlink started");
        try {
            _rst.removeLink(_i, null, ro(), getActiveProfile());
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            showException(_bre, _c, ERROR_MESSAGE, OK); //MN_20104036
            _bre.dereference();
            ok = false;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _rst.removeLink(_i, null, ro(), getActiveProfile());
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    showException(_bre, _c, ERROR_MESSAGE, OK); //MN_20104036
                    ok = false;
                }
            } else {
                _ex.printStackTrace();
                ok = false;
            }
        }
        EAccess.eaccess().timestamp("   DataBase.unlink ended",t11);
        return ok;
    }

    /**
     * isCellLocked
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _acquireLock
     * @param _c
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _r
     * @param _rst
     * @param _time
     */
    public boolean isCellLocked(RowSelectableTable _rst, int _r, int _c, LockList _ll, Profile _prof, EntityItem _owner, int _lockType, String _time, boolean _acquireLock) {
        try {
            boolean out = _rst.isLocked(_r, _c, ro(), null, _ll, _prof, _owner, _lockType, _time, _acquireLock);
			if (_acquireLock) {
				if (logLock()) {
					logLock("lock", _ll);
				}
			}
            return out;
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    boolean out = _rst.isLocked(_r, _c, ro(), null, _ll, _prof, _owner, _lockType, _time, _acquireLock);
					if (_acquireLock) {
						if (logLock()) {
							logLock("lock", _ll);
						}
					}
                    return out;
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
    }
    /*
     cr_6303
     */
    /**
     * getBuddies
     *
     * @author Anthony C. Liberto
     * @return ProfileSet
     * @param _book
     */
    public ProfileSet getBuddies(BookmarkItem _book) {
        try {
            return _book.getBuddies(ro());
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    return _book.getBuddies(ro());
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * sendBookmark
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _book
     * @param _c
     * @param _prof
     */
    public boolean sendBookmark(BookmarkItem _book, Profile[] _prof, Component _c) {
        try {
            if (_book != null && _prof != null) {
                _book.send(ro(), _prof);
                return true;
            }
        } catch (BookmarkSendException _be) {
            showMessage(_c, _be);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    if (_book != null && _prof != null) {
                        _book.send(ro(), _prof);
                        return true;
                    }
                } catch (BookmarkSendException _bme) {
                    showMessage(_c, _bme);
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return true;
    }

    private void showMessage(Component _c, BookmarkSendException _bse) {
        StringBuffer sb = null;
        int ii = -1;
        Profile prof = null;
        if (_bse != null) {
            setTitle(getString("mw"));
            sb = new StringBuffer();
            ii = _bse.getFailedProfileCount();
            for (int i = 0; i < ii; ++i) {
                Exception ex = _bse.getProfileException(i);
                if (ex != null) {
                    if (i > 0) {
                        sb.append(RETURN);
                    }
                    sb.append(ex.toString());
                }
                prof = _bse.getFailedProfile(i);
                if (prof != null) {
                    sb.append(" ");
                    sb.append(getString("for"));
                    sb.append(" ");
                    sb.append(prof.getOPName());
                }
            }
            setMessage(sb.toString());
            eaccess().showError(_c);
        }
        return;
    }
    /*
     5ZPTCX.2
     */
    /**
     * link
     *
     * @author Anthony C. Liberto
     * @return Object
     * @param _c
     * @param _child
     * @param _lai
     * @param _parent
     * @param _rst
     */
    public Object link(RowSelectableTable _rst, LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Component _c) {
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) {
            appendLog(getCommand("link", _lai, _parent, _child));
        }
        Object obj = null;
        Long t1 = EAccess.eaccess().timestamp("   DataBase.link2 started");
        if (_rst != null && _parent != null && _child != null) {
            _lai.setParentEntityItems(_parent);
            _lai.setChildEntityItems(_child);
            obj = link(_rst, _lai, _c);
        }
        EAccess.eaccess().timestamp("   DataBase.link2 ended",t1);
        return obj;
    }

    private Object link(RowSelectableTable _rst, LinkActionItem _lai, Component _c) {
        Object o = null;
        if (_rst != null) {
            try {
                o = _rst.link(null, ro(), getActiveProfile(), _lai);
            } catch(DomainException de) { // RQ0713072645
        	    //tBase.showMessage(_c, de.getMessage());
        	    showException(de, _c, ERROR_MESSAGE, OK);
	            de.dereference();
			} catch(EntityItemException eie){
	        	//SR11, SR15 and SR17. dont execute again
	        	showException(eie, _c, ERROR_MESSAGE, OK);
	        	eie.dereference();
	        }catch (Exception _ex) {
                if (tBase.reconnectMain()) {
                    try {
                        o = _rst.link(null, ro(), getActiveProfile(), _lai);
                    } catch (Exception _x) {
                        if (mwError(_x, "mw_table_linkActionItem", _c) == RETRY) {
                            o= link(_rst, _lai, _c);
                        }
                    }
                } else {
                    if (mwError(_ex, "mw_table_linkActionItem", _c) == RETRY) {
                        o= link(_rst, _lai, _c);
                    }
                }
            }
        }
        return o;
    }

    /*
     copyAction
     */
    private EntityList navigate(CopyActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        try {
			EntityList.checkDomain(getActiveProfile(),_ai,_ei); // RQ0713072645
            el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
        }catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_navigate_copy", _c) == RETRY) {
                        el = navigate(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_navigate_copy", _c) == RETRY) {
                    el = navigate(_ai, _ei, _c);
                }
            }
        }
        return el;
    }

    /*
     cr_2115
     */
    private EntityList navigate(ABRStatusActionItem _ai, EntityItem[] _ei, Component _c) {
        EntityList el = null;
        try {
			EntityList.checkDomain(getActiveProfile(),_ai,_ei); // RQ0713072645
            el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
        } catch(DomainException de) { // RQ0713072645
            //tBase.showMessage(_c, de.getMessage());
            showException(de, _c, ERROR_MESSAGE, OK);
            de.dereference();
		}catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    el = EntityList.getEntityList(ro(), getActiveProfile(), _ai, _ei);
                } catch (Exception x) {
                    if (mwError(x, "mw_navigate_abrStatus", _c) == RETRY) {
                        el = navigate(_ai, _ei, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_navigate_abrStatus", _c) == RETRY) {
                    el = navigate(_ai, _ei, _c);
                }
            }
        }
        return el;
    }

    /*
     update

     */
    /**
     * getUpdate
     * @param _eType
     * @param _opwg
     * @param _attCode
     * @param _nls
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Blob getUpdate(String _eType, int _opwg, String _attCode, int _nls, Component _c) {
        Blob out = null;
        appendLog("getBlob(" + _eType + ", " + _opwg + ", " + _attCode + ", " + _nls + ")");
        try {
            out = ro().getBlob(getActiveProfile(), _eType, _opwg, _attCode, _nls);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = ro().getBlob(getActiveProfile(), _eType, _opwg, _attCode, _nls);
                } catch (Exception x) {
                    if (mwError(x, "mw_getblob", _c) == RETRY) {
                        out = getBlob(_eType, _opwg, _attCode, _nls, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_getblob", _c) == RETRY) {
                    out = getBlob(_eType, _opwg, _attCode, _nls, _c);
                }
            }
        }
        return out;
    }
    /**
     * putUpdate
     * @param _b
     * @param _nls
     * @param _c
     * @author Anthony C. Liberto
     */
    public void putUpdate(Blob _b, int _nls, Component _c) {
        appendLog("putBlob(<<Blob>>," + _nls + ")");
        try {
            ro().putBlob(getActiveProfile(), _b, _nls);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    ro().putBlob(getActiveProfile(), _b, _nls);
                } catch (Exception x) {
                    if (mwError(x, "mw_putblob", _c) == RETRY) {
                        putBlob(_b, _nls, _c);
                    }
                }
            } else {
                if (mwError(_ex, "mw_putblob", _c) == RETRY) {
                    putBlob(_b, _nls, _c);
                }
            }
        }
        return;
    }

    /*
     dwb_20040726
     */
    /**
     * lock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _row
     * @param _table
     */
    public boolean lock(RowSelectableTable _table, int[] _row, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {

        try {
            _table.lockEntityItems(_row, ro(), null, _ll, _prof, _owner, _lockType, true);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.lockEntityItems(_row, ro(), null, _ll, _prof, _owner, _lockType, true);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock() && _row != null) {
			logLock("lock", _ll);
		}
		return true;
    }

    /**
     * unlock
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _ll
     * @param _lockType
     * @param _owner
     * @param _prof
     * @param _row
     * @param _table
     */
    public boolean unlock(RowSelectableTable _table, int[] _row, LockList _ll, Profile _prof, EntityItem _owner, int _lockType) {
        try {
            _table.unlockEntityItems(_row, ro(), null, _ll, _prof, _owner, _lockType);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    _table.unlockEntityItems(_row, ro(), null, _ll, _prof, _owner, _lockType);
                } catch (Exception _x) {
                    _x.printStackTrace();
                    return false;
                }
            } else {
                _ex.printStackTrace();
                return false;
            }
        }
		if (logLock()) {
			logLock("unlock", _ll);
		}
		return true;
    }

    /*
     blue page create
     */
    /**
     * getBluePageEntries
     *
     * @author Anthony C. Liberto
     * @return BluePageEntry[]
     * @param _cai
     * @param _first
     * @param _last
     */
    public BluePageEntry[] getBluePageEntries(CreateActionItem _cai, String _first, String _last) {
        BluePageEntry[] out = null;
        try {
            out = _cai.getBluePageEntries(null, ro(), _first, _last);
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
                    out = _cai.getBluePageEntries(null, ro(), _first, _last);
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return out;
    }

    /*
     6554
     */
    /**
     * edit
     *
     * @author Anthony C. Liberto
     * @return EntityList
     * @param _row
     * @param _rst
     */
    public EntityList edit(RowSelectableTable _rst, int[] _row) {
		EntityList list = null;
		StringBuffer sb = new StringBuffer();
        try {
			list = _rst.edit(_row, null, ro(), getActiveProfile(), sb);
			if (sb.length()>0){ //RQ0713072645 must do check in whereusedlist to access items and action
	            tBase.showMessage(null, sb.toString());
			} //end RQ0713072645
        } catch (Exception _ex) {
            if (tBase.reconnectMain()) {
                try {
					list = _rst.edit(_row, null, ro(), getActiveProfile(), sb);
					if (sb.length()>0){ //RQ0713072645 must do check in whereusedlist to access items and action
						tBase.showMessage(null, sb.toString());
					} //end RQ0713072645
                } catch (Exception _x) {
                    _x.printStackTrace();
                }
            } else {
                _ex.printStackTrace();
            }
        }
        return list;
    }

	/**
     * cr_FlagUpdate
     *
     * @author Anthony C. Liberto
     * @return MetaFlagMaintList
     * @param _c
     * @param _meta
     * @param _mmai
     */
    public MetaFlagMaintList rexec(MetaMaintActionItem _mmai, EANMetaAttribute _meta, Component _c) {
		MetaFlagMaintList out = null;
		if (_mmai != null && _meta != null) {
			// force flags to use nlsid=1, flags must be created using nlsid=1
			Profile curProfile = getActiveProfile();
			NLSItem curItem = curProfile.getReadLanguage();
			if (curItem.getNLSID()!=1){
				curProfile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
				// show msg here 
				eaccess().showFYI( _c, "msg5027.0");
			}
			try {
				out = _mmai.rexec(ro(), curProfile,_meta);
			} catch (Exception _ex) {
				if (tBase.reconnectMain()) {
					try {
						out = _mmai.rexec(ro(), curProfile,_meta);
					} catch (Exception _x) {
						if (mwError(_x, "mw_rexec_MetaMaintActionItem", _c) == RETRY) {
							return rexec(_mmai, _meta, _c);
						}
						_x.printStackTrace();
					}
				} else {
					if (mwError(_ex, "mw_rexec_MetaMaintActionItem", _c) == RETRY) {
						return rexec(_mmai, _meta, _c);
					}
					_ex.printStackTrace();
				}
			}
			// restore the user's nlsid
			if (curItem.getNLSID()!=1){
				curProfile.setReadLanguage(curItem);
			}
		}
		return out;
	}

	/**
     * retireFlags
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _rows
     * @param _rst
     */
    public boolean retireFlags(RowSelectableTable _rst, int[] _rows, Component _c) {
		boolean out = false;
		if (_rst != null) {
			try {
				_rst.retireFlags(null,ro(),getActiveProfile(),_rows);
				out = true;
			} catch (Exception _ex) {
				if (tBase.reconnectMain()) {
					try {
						_rst.retireFlags(null,ro(),getActiveProfile(),_rows);
						out = true;
					} catch (Exception _x) {
						if (mwError(_x, "mw_retireFlags",_c) == RETRY) {
							return retireFlags(_rst,_rows,_c);
						}
					}
				} else {
					if (mwError(_ex,"mw_retireFlags",_c) == RETRY) {
						return retireFlags(_rst,_rows,_c);
					}
				}
			}
		}
		return out;
	}

	/**
     * updateFlagCodes
     *
     * cr_FlagUpdate
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _list
     * @param _rst
     */
	public boolean updateFlagCodes(RowSelectableTable _rst, EntityList _list, Component _c) {
		boolean out = false;
		if (_rst != null && _list != null) {
			try {
				_rst.updateFlagCodes(null,ro(),getActiveProfile(),_list);
				out = true;
			} catch (Exception _ex) {
				if (tBase.reconnectMain()) {
					try {
						_rst.updateFlagCodes(null,ro(),getActiveProfile(),_list);
						out = true;
					} catch (Exception _x) {
						if (mwError(_x, "mw_updateFlagCodes",_c) == RETRY) {
							return updateFlagCodes(_rst,_list,_c);
						}
					}
				} else {
					if (mwError(_ex,"mw_updateFlagCodes",_c) == RETRY) {
						return updateFlagCodes(_rst,_list,_c);
					}
				}
			}
		}
		return out;
	}

	/**
     * unexpireFlags
     *
     * jt_20050324
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _rows
     * @param _rst
     */
    public boolean unexpireFlags(RowSelectableTable _rst, int[] _rows, Component _c) {
		boolean out = false;
		if (_rst != null) {
			try {
				_rst.unexpireFlags(null,ro(),getActiveProfile(),_rows);
				out = true;
			} catch (Exception _ex) {
				if (tBase.reconnectMain()) {
					try {
						_rst.unexpireFlags(null,ro(),getActiveProfile(),_rows);
						out = true;
					} catch (Exception _x) {
						if (mwError(_x, "mw_unexpireFlags",_c) == RETRY) {
							return unexpireFlags(_rst,_rows,_c);
						}
					}
				} else {
					if (mwError(_ex,"mw_unexpireFlags",_c) == RETRY) {
						return unexpireFlags(_rst,_rows,_c);
					}
				}
			}
		}
		return out;
	}
	/**
     * get target version
     *
     * @author Anthony C. Liberto
     * @return array
     * @param _c
     * @param _eod
     * @param _s
     */
    public String[] getMiddlewareVersions(String[] _s, String _eod, Component _c) {
		String[] out = null;
		try {
			out = ro().getTargetVersions(getActiveProfile(),_s,_eod);
		} catch (Exception _ex) {
			if (tBase.reconnectMain()) {
				try {
					out = ro().getTargetVersions(getActiveProfile(),_s,_eod);
				} catch (Exception _x) {
					if (mwError(_x, "mw_version_check",_c) == RETRY) {
						return getMiddlewareVersions(_s,_eod,_c);
					}
				}
			} else {
				if (mwError(_ex,"mw_version_check",_c) == RETRY) {
					return getMiddlewareVersions(_s,_eod,_c);
				}
			}
		}
		return out;
	}
    /**
     * rexecAdditional
     * @author Anthony C. Liberto
     * @return Object
     * @param _c
     * @param _eai
     * @param _ei
     * @param _parms
     */
    public Object rexecAdditional(EANActionItem _eai, EntityItem[] _ei, Object[] _parms, Component _c) {
    	Object out = null;
		if (_eai != null) {
			try {
				out = _eai.rexecAdditional(ro(), getActiveProfile(),_ei,_parms);
			} catch (Exception _ex) {
				if (tBase.reconnectMain()) {
					try {
						out = _eai.rexecAdditional(ro(), getActiveProfile(),_ei,_parms);
					} catch (Exception _x) {
						if (mwError(_x, "mw_rexec_EANActionItem_additional", _c) == RETRY) {
							return rexecAdditional(_eai, _ei,_parms, _c);
						}
					}
				} else {
					if (mwError(_ex, "mw_rexec_EANActionItem_additional", _c) == RETRY) {
						return rexecAdditional(_eai, _ei, _parms, _c);
					}
				}
			}
		}
		return out;
	}

/*
 log locking
 */
	public boolean logLock() {
		return EAccess.isMonitor();
	}

	public void logLock(String _s, Object _o) {
		EAccess.monitor(_s,_o);
	}
}
