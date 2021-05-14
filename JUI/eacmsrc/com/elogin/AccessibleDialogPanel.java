// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2003, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: AccessibleDialogPanel.java,v $
 * Revision 1.3  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/11/13 17:12:23  couto
 * Preventing null pointer.
 *
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:35  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/03/03 21:46:39  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.4  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2003/12/19 23:14:01  tony
 * acl20031219
 * updated logic so that on 'x' close click on longeditor
 * editing is completed.
 *
 * Revision 1.22  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.21  2003/12/01 17:46:08  tony
 * accessibility
 *
 * Revision 1.20  2003/11/06 22:36:19  tony
 * added print statement
 *
 * Revision 1.19  2003/10/23 17:03:50  tony
 * 52684
 *
 * Revision 1.18  2003/07/10 20:11:29  tony
 * added shouldResetBusy Logic to assist in providing more
 * control of cursor busy.
 *
 * Revision 1.17  2003/06/05 20:15:23  tony
 * 51169
 *
 * Revision 1.16  2003/06/05 19:19:27  tony
 * fixed possible null pointer situation.
 *
 * Revision 1.15  2003/05/30 21:09:16  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.14  2003/05/28 16:28:22  tony
 * 50924
 *
 * Revision 1.13  2003/05/07 15:55:48  tony
 * 24306
 *
 * Revision 1.12  2003/04/14 21:55:32  joan
 * 50362
 *
 * Revision 1.11  2003/04/10 20:06:23  tony
 * updated logic to allow for dialogs to properly
 * eminiate from the dialogs parent.
 *
 * Revision 1.10  2003/04/10 16:13:16  tony
 * showError replacing a showFYI
 *
 * Revision 1.9  2003/04/09 17:39:21  tony
 * repaired logic to allow for menu to be shown on
 * xmlEditor
 *
 * Revision 1.8  2003/04/03 18:49:25  tony
 * adjusted logic to display individualized icon
 * for each frameDialog.
 *
 * Revision 1.7  2003/03/29 00:07:36  tony
 * added remove Menu logic
 *
 * Revision 1.6  2003/03/17 23:32:30  tony
 * accessibility update.
 *
 * Revision 1.5  2003/03/13 18:38:42  tony
 * accessibility and column Order.
 *
 * Revision 1.4  2003/03/11 00:33:22  tony
 * accessibility changes
 *
 * Revision 1.3  2003/03/07 21:40:45  tony
 * Accessibility update
 *
 * Revision 1.2  2003/03/04 22:34:48  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:39  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class AccessibleDialogPanel extends EPanel implements Accessible, ActionListener, DisplayableComponent {
	private static final long serialVersionUID = 1L;
	/**
     * id
     */
    protected InterfaceDialog id = null;
    private boolean bModal = false;
    private boolean bResizable = false; //50362
    private String title = null;
    /**
     * menubar
     */
    protected EMenubar menubar = new EMenubar();

    /**
     * accessibleDialogPanel
     * @author Anthony C. Liberto
     */
    public AccessibleDialogPanel() {
        super();
        createMenu();
    }

    /**
     * accessibleDialogPanel
     * @param _doubleBuffer
     * @author Anthony C. Liberto
     */
    public AccessibleDialogPanel(boolean _doubleBuffer) {
        super(_doubleBuffer);
        createMenu();
    }

    /**
     * accessibleDialogPanel
     * @param _lm
     * @author Anthony C. Liberto
     */
    public AccessibleDialogPanel(LayoutManager _lm) {
        super(_lm);
        createMenu();
    }

    /**
     * accessibleDialogPanel
     * @param _lm
     * @param _doubleBuffer
     * @author Anthony C. Liberto
     */
    public AccessibleDialogPanel(LayoutManager _lm, boolean _doubleBuffer) {
        super(_lm, _doubleBuffer);
        createMenu();
    }

    /**
     * setParentDialog
     * @author Anthony C. Liberto
     * @param _id
     */
    public void setParentDialog(InterfaceDialog _id) {
        id = _id;
    }

    /**
     * getParentDialog
     * @author Anthony C. Liberto
     * @return InterfaceDialog
     */
    public InterfaceDialog getParentDialog() {
        return id;
    }

    /**

     * repaintDialogImmediately
     * @author Anthony C. Liberto
     */
    public void repaintDialogImmediately() {
        if (id != null) {
            repaintImmediately();
        }
    }

    /**
     * getParentMenuBar
     * @return
     * @author Anthony C. Liberto
     */
    public JMenuBar getParentMenuBar() {
        if (id instanceof JFrame) {
            return ((JFrame) id).getJMenuBar();
        } else if (id instanceof JDialog) {
            return ((JDialog) id).getJMenuBar();
        }
        return null;
    }

    /**
     * getString
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getString(String _code) {
        return eaccess().getString(_code);
    }

    /**
     * showFYI
     * @author Anthony C. Liberto
     */
    public void showFYI() {
        eaccess().showFYI(this);
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
     * @author Anthony C. Liberto
     * @param _code
     */
    public void showError(String _code) {
        eaccess().showError(this, _code);
    }

    /**
     * dispose dialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
		if (okToClose()) {
	        eaccess().dispose(getParentDialog());
		}
    }

    /**
     * okToClose
     * @author Anthony C. Liberto
     * @return
     */
    public boolean okToClose() {
		return true;
	}

    /**
     * hideDialog
     * @author Anthony C. Liberto
     */
    public void hideDialog() {
        if (isShowing()) {
            eaccess().hide(getParentDialog());
        }
    }

    /**
     * packDialog
     * @author Anthony C. Liberto
     */
    public void packDialog() {
        eaccess().pack(getParentDialog());
    }

    /**
     * validateDialog
     * @author Anthony C. Liberto
     */
    public void validateDialog() {
        if (menubar != null) {
            menubar.revalidate();
        }
        eaccess().validate(getParentDialog());
    }

    /**
     * showDialog
     * @param _c
     * @param _eaccessp
     * @author Anthony C. Liberto
     */
    public void showDialog(Component _c, AccessibleDialogPanel _eaccessp) {
        eaccess().show(_c, _eaccessp, isModal());
    }

    /**
     * setModal
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModal(boolean _b) {
        bModal = _b;
    }

    /**
     * isModal
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModal() {
        return bModal;
    }

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_ACCESSIBLEDIALOGPANEL;
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    	title = _title;
    }

    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        if (title == null) {
            return getString("name");
        }
        return title;
    }

    /**
     * isResizable
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isResizable() {
        return bResizable;
    }

    /**
     * setResizable
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setResizable(boolean _b) { //50362
        bResizable = _b;
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(ActionEvent _ae) {
        actionPerformed(_ae.getActionCommand());
    }

    /**
     * actionPerformed
     * @param _action
     * @author Anthony C. Liberto
     */
    protected void actionPerformed(String _action) {
        appendLog("AccessibleDialogPanel.actionPerformed: " + _action);
        if ("exit".equals(_action)) {
            disposeDialog();
        }
    }

    /**
     * getMenubar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return menubar;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    	if (menubar != null) {
    		menubar.removeAll();
    		menubar.addMenu("File", "exit", this, KeyEvent.VK_F4, Event.ALT_MASK, true);
    		menubar.setMenuMnemonic("File", 'F');
    	}
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    	if (menubar != null) {
    		menubar.removeMenuItem("exit", this);
    		menubar.removeAll();
    	}
    }

    /*
     	Abstract
    */
    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return DEFAULT_ICON;
    }
    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
    }
    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
    }
    /**
     * hideMe
     * @author Anthony C. Liberto
     */
    public void hideMe() {
    }
    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        id = null;
        removeMenu();
        removeAll();
        removeNotify();
    }
    /*
     50924
     */
    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
        return null;
    }

    /*
     51169
     */
    /**
     * toFron
     * @author Anthony C. Liberto
     */
    public void toFront() {
        if (id != null) {
            id.toFront();
        }
    }

    /**
     * shouldResetBusy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean shouldResetBusy() {
        return true;
    }

    /*
     52684
     */
    /**
     * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
     * @author Anthony C. Liberto
     */
    protected boolean processKeyBinding(KeyStroke _ks, KeyEvent _ke, int _cond, boolean _press) {
        if (_ke != null) {
            int iKeyCode = _ke.getKeyCode();
            if (_ke.isControlDown()) {
                if (iKeyCode == KeyEvent.VK_CANCEL) {
                    eaccess().interrupt();
                    return false;
                }
            }
        }
        return super.processKeyBinding(_ks, _ke, _cond, _press);
    }

    /*
     accessible
     */
    /**
     * getAccessibleDescription
     * @author Anthony C. Liberto
     * @return String
     */
    public String getAccessibleDescription() {
        return getAccessibleContext().getAccessibleDescription();
    }

    /**
     * hasCustomFocusPolicy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean hasCustomFocusPolicy() {
        return false;
    }

    /*
     acl20031219
     */
    /**
     * canWindowClose
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean canWindowClose() {
        return true;
    }

}
