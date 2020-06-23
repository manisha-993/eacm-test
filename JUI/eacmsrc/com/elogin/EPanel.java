/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EPanel.java,v $
 * Revision 1.4  2012/04/05 17:32:15  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2009/05/28 13:59:13  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2006/11/09 15:51:06  tony
 * more monitor logic
 *
 * Revision 1.3  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/06/21 17:58:01  tony
 * 24423680 -- updating non-existant blobs.
 *
 * Revision 1.9  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.8  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.7  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.6  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.4  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.3  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.2  2004/02/24 18:01:31  tony
 * e-announce13 send tab
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.38  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.37  2004/01/09 00:42:43  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.36  2003/12/19 18:42:18  tony
 * acl_20031219
 * updated logic to prevent error and null pointers when
 * painting or validating components.
 *
 * Revision 1.35  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.34  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.33  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.32  2003/10/07 21:36:58  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.31  2003/09/30 16:33:38  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.30  2003/09/29 17:19:44  tony
 * 52439
 *
 * Revision 1.29  2003/08/28 18:36:01  tony
 * 51975
 *
 * Revision 1.28  2003/06/26 16:46:04  tony
 * updated cipher logic.
 *
 * Revision 1.27  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.26  2003/06/10 16:46:47  tony
 * 51260
 *
 * Revision 1.25  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.24  2003/05/29 21:20:45  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.23  2003/05/29 19:05:20  tony
 * updated report launching.
 *
 * Revision 1.22  2003/05/22 16:23:12  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.21  2003/05/16 18:23:35  tony
 * updated logic to enhance functionality
 *
 * Revision 1.20  2003/05/13 16:06:00  tony
 * 50621
 *
 * Revision 1.19  2003/05/09 17:30:50  tony
 * updated wizard logic
 *
 * Revision 1.18  2003/05/08 19:18:10  tony
 * 50445
 *
 * Revision 1.17  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.16  2003/04/22 16:37:04  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.15  2003/04/18 20:10:30  tony
 * added tab placement to preferences.
 *
 * Revision 1.14  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.*;
//import com.ibm.eannounce.dialogpanels.MWChooser;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.*;
//import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EPanel extends JPanel implements Accessible, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
    /**
     * bUseBack
     */
    private boolean bUseBack = true;
    /**
     * bUseFont
     */
    private boolean bUseFont = true;
    /**
     * bUseFore
     */
    private boolean bUseFore = true;

    /**
     * create a default GPanel
     * which is not Opaque
     */
    public EPanel() {
        super();
        setLookAndFeel();
    }

    /**
     * create a default GPanel
     * which is not Opaque
     * @param b is doubleBuffered
     */
    public EPanel(boolean b) {
        super(b);
        setLookAndFeel();
    }

    /**
     * create a default GPanel
     * which is not Opaque
     * @param lm layout manager
     */
    public EPanel(LayoutManager lm) {
        super(lm);
        setLookAndFeel();
    }

    /**
     * create a default GPanel
     * which is not Opaque
     * @param b is doubleBuffered
     * @param lm layout manager
     */
    public EPanel(LayoutManager lm, boolean b) {
        super(lm, b);
        setLookAndFeel();
    }

    /**
     * setTransparent
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setTransparent(boolean _b) {
        setOpaque(!_b);
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeAll();
        removeNotify();
        setBusy(false);
    }

    /**
     * setObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setObject(Object _o) {
    }

    /**
     * getPanelType
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_EPANEL;
    }

    /**
     * isPanelType
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPanelType(String _s) {
        return _s.equals(getPanelType());
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
     * getMiddlewareChooser
     * @param _id
     * @param _standAlone
     * @return
     * @author Anthony C. Liberto
     * /
    public MWChooser getMiddlewareChooser(InterfaceDialog _id, boolean _standAlone) {
        return eaccess().getMiddlewareChooser(_id, _standAlone);
    }*/

    /**
     * getDisplayable
     * @author Anthony C. Liberto
     * @return EDisplayable
     */
    public EDisplayable getDisplayable() {
        Container par = getParent();
        if (par instanceof EDisplayable) {
            return (EDisplayable) par;
        }
        return null;
    }

    /**
     * setUseDefined
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseDefined(boolean _b) {
        setUseBack(_b);
        setUseFore(_b);
        setUseFont(_b);
    }

    /**
     * setUseBack
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseBack(boolean _b) {
        bUseBack = _b;
    }

    /**
     * setUseFore
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseFore(boolean _b) {
        bUseFore = _b;
    }

    /**
     * setUseFont
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseFont(boolean _b) {
        bUseFont = _b;
    }

    /**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (eaccess().canOverrideColor() && bUseBack) {
            if (isEnabled()) {
                return eaccess().getBackground();
            } else {
                return eaccess().getDisabledBackground();
            }
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
        if (eaccess().canOverrideColor() && bUseFore) {
            if (isEnabled()) {
                return eaccess().getForeground();
            } else {
                return eaccess().getDisabledForeground();
            }
        }
        return super.getForeground();
    }

    /**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
        if (EANNOUNCE_UPDATE_FONT && bUseFont) {
            return eaccess().getFont();
        }
        return super.getFont();
    }

    /**
     * getDateRoutines
     * @return
     * @author Anthony C. Liberto
     * /
    public DateRoutines getDateRoutines() {
        return eaccess().getDateRoutines();
    }*/

    /**
     * getLogin
     * @return
     * @author Anthony C. Liberto
     * /
    public ELogin getLogin() {
        return eaccess().getLogin();
    }*/

    /**
     * launch
     * @param _command
     * @author Anthony C. Liberto
     * /
    public void launch(String _command) {
        eaccess().launch(_command);
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
     * getBlobCount
     * @return
     * @author Anthony C. Liberto
     * /
    public int getBlobCount() {
        return eaccess().getBlobCount();
    }*/

    /**
     * dBase
     * @return
     * @author Anthony C. Liberto
     */
    public DataBase dBase() {
        return eaccess().dBase();
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
     * getStringArray
     * @param _code
     * @return
     * @author Anthony C. Liberto
     * /
    public String[] getStringArray(String _code) {
        return eaccess().getStringArray(_code);
    }*/

    /**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
        eaccess().setCode(_code);
    }

    /**
     * getFocusOwner
     * @return
     * @author Anthony C. Liberto
     * /
    public Component getFocusOwner() {
        return eaccess().getFocusOwner();
    }*/

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
     * /
    public String getNow(String _format) {
        return eaccess().getNow(_format);
    }*/

    /**
     * getNow
     * @param _format
     * @param _refreshTime
     * @return
     * @author Anthony C. Liberto
     * /
    public String getNow(String _format, boolean _refreshTime) {
        return eaccess().getNow(_format, _refreshTime);
    }*/

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
     * /
    public void setParms(String[] _s) {
        eaccess().setParms(_s);
    }*/

    /**
     * setParms
     * @param _s
     * @param _delim
     * @author Anthony C. Liberto
     * /
    public void setParms(String _s, String _delim) {
        eaccess().setParms(_s, _delim);
    }*/
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
     * /
    public void clearParms() {
        eaccess().clearParms();
    }*/

    /**
     * getParms
     * @return
     * @author Anthony C. Liberto
     * /
    public String[] getParms() {
        return eaccess().getParms();
    }*/

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
     * @author Anthony C. Liberto
     * /
    public void showFYI() {
        eaccess().showFYI(this);
    }*/

    /**
     * showFYI
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showFYI(String _code) {
        eaccess().showFYI(this, _code);
    }

    /**
     * showError
     * @author Anthony C. Liberto
     */
    public void showError() {
        eaccess().showError(this);
    }

    /**
     * showError
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showError(String _code) {
        eaccess().showError(this, _code);
    }

    /**
     * showError
     * MN24423680
     * @param _code
     * @param _parms
     * @author Anthony C. Liberto
     * /
    public void showError(String _code, String[] _parms) {
        eaccess().showError(this, _code,_parms);
    }*/

    /**
     * showConfirm
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, boolean _clear) {
        return eaccess().showConfirm(this, _buttons, _clear);
    }

    /**
     * showInput
     * @return
     * @author Anthony C. Liberto
     * /
    public String showInput() {
        return eaccess().showInput(this);
    }*/

    /**
     * show
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _reset
     * @author Anthony C. Liberto
     * /
    public void show(int _dialogType, int _msgType, int _buttons, boolean _reset) {
        eaccess().show(this, _dialogType, _msgType, _buttons, _reset);
    }*/

    /**
     * show
     * @param _c
     * @param _adp
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(Component _c, AccessibleDialogPanel _adp, boolean _modal) {
        eaccess().show(_c, _adp, _modal);
    }

    /**
     * getImage
     * @param _img
     * @return
     * @author Anthony C. Liberto
     * /
    public Image getImage(String _img) {
        return eaccess().getImage(_img);
    }*/

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
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        if (isModalCursor()) {
            return eaccess().getModalCursor();
        } else {
            EDisplayable disp = getDisplayable();
            if (disp != null) {
                return disp.getCursor();
            }
        }
        return eaccess().getCursor();
    }

    /**
     * setModalCursor
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setModalCursor(boolean _b) {
        bModalCursor = _b;
    }

    /**
     * isModalCursor
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalCursor() {
        return bModalCursor;
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
    }*/

    /**
     * getLockList
     * @return
     * @author Anthony C. Liberto
     */
    public LockList getLockList() {
        return eaccess().getLockList();
    }

    /**
     * setLockList
     * @param _ll
     * @author Anthony C. Liberto
     * /
    public void setLockList(LockList _ll) {
        eaccess().setLockList(_ll);
    }*/

    /**
     * getNumber
     * @param _win
     * @param _message
     * @return
     * @author Anthony C. Liberto
     * /
    public int getNumber(Window _win, String _message) {
        return eaccess().getNumber(_win, _message);
    }*/

    /**
     * showLinkDialog
     * @param _title
     * @param _message
     * @author Anthony C. Liberto
     * /
    public void showLinkDialog(String _title, String _message) {
        eaccess().showLinkDialog(_title, _message);
    }*/

    /**
     * showLinkDialog
     * @param _win
     * @param _title
     * @param _message
     * @author Anthony C. Liberto
     * /
    public void showLinkDialog(Window _win, String _title, String _message) { //50445
        eaccess().showLinkDialog(_win, _title, _message); //50445
    } //50445
    */

    /**
     * getRemoteDatabaseInterface
     * @return
     * @author Anthony C. Liberto
     */
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
        return eaccess().getRemoteDatabaseInterface();
    }

    /**
     * getOPWGID
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
        return eaccess().getOPWGID();
    }

    /**
     * shouldRefresh
     * @param _tab
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean shouldRefresh(ETabable _tab) {
        return eaccess().shouldRefresh(_tab);
    }*/

    /**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getActiveProfile() {
        return eaccess().getActiveProfile();
    }

    /**
     * getActiveNLSItem
     * @return
     * @author Anthony C. Liberto
     * /
    public NLSItem getActiveNLSItem() {
        return eaccess().getActiveNLSItem();
    }*/

    /**
     * canPaste
     * @param _b
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean canPaste(boolean _b) {
        return eaccess().canPaste(_b);
    }*/

    /**
     * getPanel
     * @param _panel
     * @return
     * @author Anthony C. Liberto
     * /
    public EPanel getPanel(int _panel) {
        return eaccess().getPanel(_panel);
    }*/

    /**
     * editContains
     * @param _ei
     * @param _eai
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean editContains(EntityItem[] _ei, EANActionItem _eai) {
        return eaccess().editContains(_ei, _eai);
    }*/

    /**
     * close
     * @param _tab
     * @author Anthony C. Liberto
     * /
    public void close(ETabable _tab) {
        eaccess().close(_tab);
    }*/

    /**
     * closeAll
     * @author Anthony C. Liberto
     * /
    public void closeAll() {
        eaccess().closeAll();
    }*/

    /**
     * exit
     * @author Anthony C. Liberto
     * /
    public void exit() {
        eaccess().exit("exit ePanel");
    }*/

    /**
     * isPast
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPast() {
        return eaccess().isPast();
    }

    /**
     * gio
     * @return
     * @author Anthony C. Liberto
     * /
    public Gio gio() {
        return eaccess().gio();
    }

    /**
     * setFilter
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setFilter(boolean _b) {
        eaccess().setFilter(_b);
    }*/

    /**
     * repaintImmediately
     * @author Anthony C. Liberto
     */
    public void repaintImmediately() {
        eaccess().repaintImmediately();
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        eaccess().refreshAppearance();
    }

    /**
     * getTabTitle
     * @param _code
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabTitle(String _code, Profile _prof) {
        return eaccess().getTabTitle(_code, _prof);
    }

    /**
     * setWorker
     * @param _worker
     * @author Anthony C. Liberto
     */
    public void setWorker(ESwingWorker _worker) {
        eaccess().setWorker(_worker);
    }

    /**
     * setEMenuBar
     * @param _bar
     * @author Anthony C. Liberto
     * /
    public void setEMenuBar(EMenubar _bar) {
        eaccess().setEMenuBar(_bar);
    }*/

    /**
     * actionPerformed
     * @param _ae
     * @author Anthony C. Liberto
     * /
    public void actionPerformed(ActionEvent _ae) {
    }

    /**
     * save
     * @param _o
     * @author Anthony C. Liberto
     * /
    public void save(Object _o) {
        eaccess().save(_o);
    }*/

    /**
     * load
     * @author Anthony C. Liberto
     * /
    public void load() {
        eaccess().load();
    }

    /**
     * print
     * @param _c
     * @author Anthony C. Liberto
     * /
    public void print(Component _c) {
        eaccess().print(_c);
    }*/

    /**
     * setPrefString
     * @param _code
     * @param _val
     * @author Anthony C. Liberto
     */
    public void setPrefString(String _code, String _val) {
        eaccess().setPrefString(_code, _val);
    }

    /**
     * getPrefString
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public String getPrefString(String _code, String _def) {
        return eaccess().getPrefString(_code, _def);
    }

    /**
     * clearPref
     * @param _code
     * @param _write
     * @author Anthony C. Liberto
     */
    public void clearPref(String _code, boolean _write) {
        eaccess().clearPref(_code, _write);
    }
    /**
     * writePref
     * @author Anthony C. Liberto
     * /
    public void writePref() {
        eaccess().writePref();
    }

    /**
     * setPrefObject
     * @param _code
     * @param _o
     * @author Anthony C. Liberto
     * /
    public void setPrefObject(String _code, Object _o) {
        eaccess().setPrefObject(_code, _o);
    }

    /**
     * setPrefColor
     * @param _code
     * @param _color
     * @author Anthony C. Liberto
     * /
    public void setPrefColor(String _code, Color _color) {
        eaccess().setPrefColor(_code, _color);
    }*/

    /**
     * getPrefColor
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     * /
    public Color getPrefColor(String _code, Color _def) {
        return eaccess().getPrefColor(_code, _def);
    }*/

    /**
     * setPrefFont
     * @param _code
     * @param _fnt
     * @author Anthony C. Liberto
     * /
    public void setPrefFont(String _code, Font _fnt) {
        eaccess().setPrefFont(_code, _fnt);
    }

    /**
     * getPrefFont
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     * /
    public Font getPrefFont(String _code, Font _def) {
        return eaccess().getPrefFont(_code, _def);
    }

    /**
     * setPrefInt
     * @param _code
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setPrefInt(String _code, int _i) {
        eaccess().setPrefInt(_code, _i);
    }

    /**
     * getPrefInt
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     * /
    public int getPrefInt(String _code, int _def) {
        return eaccess().getPrefInt(_code, _def);
    }

    /**
     * setPrefDouble
     * @param _code
     * @param _dbl
     * @author Anthony C. Liberto
     * /
    public void setPrefDouble(String _code, double _dbl) {
        eaccess().setPrefDouble(_code, _dbl);
    }

    /**
     * getPrefDouble
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     * /
    public double getPrefDouble(String _code, double _def) {
        return eaccess().getPrefDouble(_code, _def);
    }

    /**
     * setPrefBoolean
     * @param _code
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setPrefBoolean(String _code, boolean _b) {
        eaccess().setPrefBoolean(_code, _b);
    }

    /**
     * getPrefBoolean
     * @param _code
     * @param _b
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean getPrefBoolean(String _code, boolean _b) {
        return eaccess().getPrefBoolean(_code, _b);
    }

    /**
     * showConfirm
     * @param _buttons
     * @param _code
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, String _code, boolean _clear) {
        return eaccess().showConfirm(this, _buttons, _code, _clear);
    }

    /**
     * getProfileSet
     * @return
     * @author Anthony C. Liberto
     * /
    public ProfileSet getProfileSet() {
        return eaccess().getProfileSet();
    }

    /**
     * getDefaultProfile
     * @return
     * @author Anthony C. Liberto
     * /
    public Profile getDefaultProfile() {
        return eaccess().getDefaultProfile();
    }

    /**
     * setDefaultProfile
     * @param _prof
     * @author Anthony C. Liberto
     * /
    public void setDefaultProfile(Profile _prof) {
        eaccess().setDefaultProfile(_prof);
    }

    //52439	public void resetDefaultProfile(int _opwg) {
    //52439		eaccess().resetDefaultProfile(_opwg);
    /**
     * resetDefaultProfile
     * @param _user
     * @author Anthony C. Liberto
     * /
    public void resetDefaultProfile(String _user) { //52439
        eaccess().resetDefaultProfile(_user); //52439
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
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showException(Exception _x, int _icon, int _buttons) {
        return eaccess().showException(_x, this, _icon, _buttons);
    }
    /*
     1.2h
     */
    /**
     * is
     * @param _code
     * @param _look
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean is(String _code, boolean _look) {
        return eaccess().is(_code, _look);
    }

    /**
     * isArmed
     * @param _code
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isArmed(String _code) {
        return EAccess.isArmed(_code);
    }*/

    /**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isTestMode() {
        return EAccess.isTestMode();
    }

    /**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isCaptureMode() {
        return EAccess.isCaptureMode();
    }*/
  
    /**
     * load
     * @param _parTab
     * @param _o
     * @param _book
     * @param _gif
     * @author Anthony C. Liberto
     * /
    public void load(ETabable _parTab, Object _o, BookmarkItem _book, String _gif) { //cr_1210035324
        eaccess().load(_parTab, _o, _book, _gif); //cr_1210035324
    }*/

    /*
     kehrli_20030929
     */
    /**
     * addTab
     * @param _parTab
     * @param _tabComponent
     * @author Anthony C. Liberto
     */
    public void addTab(ETabable _parTab, ETabable _tabComponent) {
        eaccess().addTab(_parTab, _tabComponent);
    }

    /**
     * addTab
     * @param _parTab
     * @param _tabComponent
     * @param _gif
     * @author Anthony C. Liberto
     * /
    public void addTab(ETabable _parTab, ETabable _tabComponent, String _gif) {
        eaccess().addTab(_parTab, _tabComponent, _gif);
    }*/

    /**
     * addTab
     * @param _parTab
     * @param _tabComponent
     * @param _gif
     * @param _title
     * @author Anthony C. Liberto
     * /
    public void addTab(ETabable _parTab, ETabable _tabComponent, String _gif, String _title) {
        eaccess().addTab(_parTab, _tabComponent, _gif, _title);
    }*/

    /*
     acl_20031007
     */
    /**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    private void setLookAndFeel() {
    	 /*breaks win7if (EAccess.isAccessible()) {
            LookAndFeel lnf = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(WINDOWS_LNF);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SwingUtilities.updateComponentTreeUI(this);

            try {
                UIManager.setLookAndFeel(lnf);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/
    }

    /*
     acl_20031219
     */
    /**
     * @see java.awt.Component#validate()
     * @author Anthony C. Liberto
     * /
    public void validate() {
        try {
            super.validate();
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }*/

    /**
     * @see javax.swing.JComponent#paintImmediately(int, int, int, int)
     * @author Anthony C. Liberto
     */
    public void paintImmediately(int _x, int _y, int _w, int _h) {
        try {
            paintImmediately2(_x, _y, _w, _h);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }

    private void paintImmediately2(int _x, int _y, int _w, int _h) throws Exception {
        super.paintImmediately(_x, _y, _w, _h);
    }
}
