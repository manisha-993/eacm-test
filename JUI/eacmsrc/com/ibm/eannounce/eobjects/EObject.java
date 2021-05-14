/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EObject.java,v $
 * Revision 1.3  2012/04/05 17:40:44  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2009/05/26 13:08:56  wendy
 * Performance cleanup
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2006/03/16 22:01:29  tony
 * Capture logging
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/08/04 17:49:15  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.2  2004/02/19 21:34:53  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.20  2004/01/09 00:42:44  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.19  2003/09/30 16:36:05  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.18  2003/08/28 18:31:27  tony
 * 51975
 *
 * Revision 1.17  2003/06/25 16:17:06  tony
 * 51325
 *
 * Revision 1.16  2003/06/10 16:46:49  tony
 * 51260
 *
 * Revision 1.15  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.14  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.13  2003/05/29 19:05:40  tony
 * updated report launching.
 *
 * Revision 1.12  2003/05/27 21:23:15  tony
 * updated url logic.
 *
 * Revision 1.11  2003/05/22 16:23:14  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.10  2003/05/15 16:23:38  tony
 * updated messaging logic on search.
 *
 * Revision 1.9  2003/05/13 16:09:26  tony
 * 50621
 *
 * Revision 1.8  2003/05/06 18:59:23  joan
 * 50530
 *
 * Revision 1.7  2003/05/02 20:05:56  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.6  2003/04/21 17:30:19  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.5  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;
import com.ibm.eannounce.dialogpanels.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EObject extends Object implements EAccessConstants {
    /**
     * eObject
     * @author Anthony C. Liberto
     */
    public EObject() {
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
    }

    /*
    eaccess pass thru
    start
    */
    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * getPrefColor
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public Color getPrefColor(String _code, Color _def) {
        return eaccess().getPrefColor(_code, _def);
    }

    /**
     * getPanel
     * @param _panel
     * @return
     * @author Anthony C. Liberto
     */
    public EPanel getPanel(int _panel) {
        return eaccess().getPanel(_panel);
    }

    /**
     * dBase
     * @return
     * @author Anthony C. Liberto
     */
    public DataBase dBase() {
        return eaccess().dBase();
    }

    /**
     * getNewProfileInstance
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getNewProfileInstance() {
        return eaccess().getNewProfileInstance();
    }

    /**
     * gio
     * @return
     * @author Anthony C. Liberto
     */
    public Gio gio() {
        return eaccess().gio();
    }

    /**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getActiveProfile() {
        return eaccess().getActiveProfile();
    }

    /**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
        eaccess().setCode(_code);
    }

    /**
     * setTitle
     * @param _title
     * @author Anthony C. Liberto
     */
    public void setTitle(String _title) {
        eaccess().setTitle(_title);
    }

    /**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
        return eaccess().getString(_code);
    }

    /**
     * getDateRoutines
     * @return
     * @author Anthony C. Liberto
     */
    public DateRoutines getDateRoutines() {
        return eaccess().getDateRoutines();
    }

    /**
     * getChar
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public char getChar(String _code) {
        return eaccess().getChar(_code);
    }

    /**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
        EAccess.appendLog(_message);
    }

    /**
     * getNow
     * @param _format
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format) {
        return eaccess().getNow(_format);
    }

    /**
     * getNow
     * @param _format
     * @param _refreshTime
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format, boolean _refreshTime) {
        return eaccess().getNow(_format, _refreshTime);
    }

    /**
     * setParm
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(String _s) {
        eaccess().setParm(_s);
    }

    /**
     * setParms
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParms(String[] _s) {
        eaccess().setParms(_s);
    }

    /**
     * setParms
     * @param _s
     * @param _delim
     * @author Anthony C. Liberto
     */
    public void setParms(String _s, String _delim) {
        eaccess().setParms(_s, _delim);
    }
    /**
     * setParmCount
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setParmCount(int _i) {
        eaccess().setParmCount(_i);
    }

    /**
     * setParm
     * @param _i
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(int _i, String _s) {
        eaccess().setParm(_i, _s);
    }

    /**
     * clearParms
     * @author Anthony C. Liberto
     */
    public void clearParms() {
        eaccess().clearParms();
    }

    /**
     * getParms
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getParms() {
        return eaccess().getParms();
    }

    /**
     * getMessage
     * @return
     * @author Anthony C. Liberto
     */
    public String getMessage() {
        return eaccess().getMessage();
    }

    /**
     * setMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setMessage(String _s) {
        eaccess().setMessage(_s);
    }

    /**
     * showFYI
     * @param _c
     * @author Anthony C. Liberto
     */
    public void showFYI(Component _c) {
        eaccess().showFYI(_c);
    }

    /**
     * showFYI
     * @param _c
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showFYI(Component _c, String _code) {
        eaccess().showFYI(_c, _code);
    }

    /**
     * showError
     * @param _c
     * @author Anthony C. Liberto
     */
    public void showError(Component _c) {
        eaccess().showError(_c);
    }

    /**
     * showError
     * @param _c
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showError(Component _c, String _code) {
        eaccess().showError(_c, _code);
    }

    /*
     cr_1210035324
    	public void showBookmark(EANActionItem _item) {
    		eaccess().showBookmark(_item);
    		return;
    	}
    */
    /**
     * showConfirm
     * @param _c
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(Component _c, int _buttons, boolean _clear) {
        return eaccess().showConfirm(_c, _buttons, _clear);
    }

    /**
     * showInput
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public String showInput(Component _c) {
        return eaccess().showInput(_c);
    }

    /**
     * show
     * @param _c
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void show(Component _c, int _dialogType, int _msgType, int _buttons, boolean _reset) {
        eaccess().show(_c, _dialogType, _msgType, _buttons, _reset);
    }

    /**
     * show
     * @param _c
     * @param _dc
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(Component _c, DisplayableComponent _dc, boolean _modal) {
        eaccess().show(_c, _dc, _modal);
    }

    /**
     * getImage
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _img) {
        return eaccess().getImage(_img);
    }

    /**
     * getImageIcon
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _img) {
        return eaccess().getImageIcon(_img);
    }

    /**
     * getCursor
     * @return
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        return eaccess().getCursor();
    }

    /**
     * setBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setBusy(boolean _b) {
        eaccess().setBusy(_b);
    }

    /**
     * isBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBusy() {
        return eaccess().isBusy();
    }

    /**
     * isArmed
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isArmed(String _code) {
        return EAccess.isArmed(_code);
    }

    /**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isTestMode() {
        return EAccess.isTestMode();
    }


    /**
     * isCaptureMode
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCaptureMode() {
        return EAccess.isTestMode();
    }

    /**
     * setModalBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalBusy(boolean _b) {
        eaccess().setModalBusy(_b);
    }

    /**
     * isModalBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalBusy() {
        return eaccess().isModalBusy();
    }

    /**
     * isWindows
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isWindows() {
        return eaccess().isWindows();
    }

    /**
     * setFilter
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFilter(boolean _b) {
        eaccess().setFilter(_b);
    }

    /**
     * setNLSData
     * @param _prof
     * @author Anthony C. Liberto
     */
    public void setNLSData(Profile _prof) {
        eaccess().setNLSData(_prof);
    }

    /**
     * isPast
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPast() {
        return eaccess().isPast();
    }

    /**
     * setLockList
     * @param _ll
     * @author Anthony C. Liberto
     */
    public void setLockList(LockList _ll) {
        eaccess().setLockList(_ll);
    }

    /**
     * getLockList
     * @return
     * @author Anthony C. Liberto
     */
    public LockList getLockList() {
        return eaccess().getLockList();
    }

    /**
     * isShowing
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isShowing(int _i) {
        return eaccess().isShowing(_i);
    }
    /*
     50874
    	public void setPanelObject(int _i, Object _o) {
    		eaccess().setPanelObject(_i,_o);
    		return;
    	}
    */
    /**
     * nlsChanged
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void nlsChanged(NLSItem _nls) {
        eaccess().nlsChanged(_nls);
    }

    /**
     * showSearch
     * @param _sai
     * @param _navType
     * @param _o
     * @author Anthony C. Liberto
     */
    public void showSearch(SearchActionItem _sai, int _navType, Object _o) {
        eaccess().showSearch(_sai, _navType, _o);
    }

    /**
     * editContains
     * @param _ei
     * @param _eai
     * @return
     * @author Anthony C. Liberto
     */
    public boolean editContains(EntityItem[] _ei, EANActionItem _eai) {
        return eaccess().editContains(_ei, _eai);
    }

    /*
     51975
    	public void addTab(String _menu, String _tab, String _icon, eTabable _tabComponent, String _tip) {
    		eaccess().addTab(_menu,_tab,_icon,_tabComponent,_tip);
    		return;
    	}
    */
    /**
     * getActiveNLSItem
     * @return
     * @author Anthony C. Liberto
     */
    public NLSItem getActiveNLSItem() {
        return eaccess().getActiveNLSItem();
    }

    /**
     * launchURL
     * @param _url
     * @author Anthony C. Liberto
     * /
    public void launchURL(String _url) {
        eaccess().launchURL(_url);
    }*/

    /**
     * launchReport
     * @param _rai
     * @param _ei
     * @author Anthony C. Liberto
     * /
    public void launchReport(ReportActionItem _rai, EntityItem[] _ei) {
        eaccess().launchReport(_rai, _ei);
    }*/

    /**
     * setWorker
     * @param _worker
     * @author Anthony C. Liberto
     */
    public void setWorker(ESwingWorker _worker) {
        eaccess().setWorker(_worker);
    }

    /**
     * getLogin
     * @return
     * @author Anthony C. Liberto
     */
    public ELogin getLogin() {
        return eaccess().getLogin();
    }

    /**
     * prune
     * @param _hist
     * @author Anthony C. Liberto
     *
    public void prune(Object _hist) {
        eaccess().prune(_hist);
        return;
    }*/

    /**
     * getEditPDGPnl
     * @return
     * @author Anthony C. Liberto
     */
    public EditPDGPanel getEditPDGPnl() { //pdg
        return EAccess.getEditPDGPnl();
    }

    /**
     * getNavPDGPnl
     * @return
     * @author Anthony C. Liberto
     */
    public NavPDGPanel getNavPDGPnl() { //pdg
        return EAccess.getNavPDGPnl();
    }

    /**
     * isPDGOn
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPDGOn() { //50530
        return eaccess().isPDGOn();
    }
    /*
    eaccess pass thru
    complete
    */

    /*
     50621
    */
    /**
     * showError
     * @param _id
     * @author Anthony C. Liberto
     */
    public void showError(InterfaceDialog _id) {
        eaccess().showError((Window) _id);
    }

    /*
     51260
     */
    /**
     * showException
     * @param _x
     * @param _c
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showException(Exception _x, Component _c, int _icon, int _buttons) {
        return eaccess().showException(_x, _c, _icon, _buttons);
    }

    /*
     51325
    */
    /**
     * updateBookmarkAction
     * @param _item
     * @author Anthony C. Liberto
     */
    public void updateBookmarkAction(EANActionItem _item) {
        eaccess().updateBookmarkAction(_item);
    }

    /*
     51975
     */
    /*
     kehrli_20030929
    	public void addTab(eTabable _parentTab,String _menu, String _tab, String _icon, eTabable _tabComponent, String _tip) {
    		eaccess().addTab(_parentTab,_menu,_tab,_icon,_tabComponent,_tip);
    		return;
    	}
     */

    /*
     kehrli_20030929
     */
    /**
     * addTab
     * @param _parentTab
     * @param _tabComponent
     * @author Anthony C. Liberto
     */
    public void addTab(ETabable _parentTab, ETabable _tabComponent) {
        eaccess().addTab(_parentTab, _tabComponent);
    }

    /*
     cr_1210035324
     */
    /**
     * showBookmark
     * @param _item
     * @param _history
     * @author Anthony C. Liberto
     */
    public void showBookmark(EANActionItem _item, EANActionItem[] _history) {
        eaccess().showBookmark(_item, _history);
    }

    /**
     * getUserName
     * @return
     * @author Anthony C. Liberto
     */
    public String getUserName() {
        return eaccess().getUserName();
    }

    /**
     * getInt
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public int getInt(String _code) {
        return eaccess().getInt(_code);
    }

    /**
     * is
     * @param _code
     * @param _look
     * @return
     * @author Anthony C. Liberto
     */
    public boolean is(String _code, boolean _look) {
        return eaccess().is(_code, _look);
    }
}
