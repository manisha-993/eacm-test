/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SubActionMenu.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.12  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.11  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.10  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.9  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.8  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.7  2004/04/06 18:55:17  tony
 * removed test logic
 *
 * Revision 1.6  2004/04/06 18:15:21  tony
 * adjusted logic to prevent menu duplication.
 *
 * Revision 1.5  2004/03/30 17:37:20  tony
 * cr_209046022
 * added ability to scroll while mouse depressed.
 *
 * Revision 1.4  2004/03/26 21:21:05  tony
 * cr_812022711 -- adjusted to prvent compiler warning.
 *
 * Revision 1.3  2004/03/26 20:58:21  tony
 * cr_812022711 -- behavior modification.
 *
 * Revision 1.2  2004/03/26 20:46:28  tony
 * cr_812022711
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.3  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.2  2003/03/07 21:40:47  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2002/11/07 16:58:31  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.*;
import java.awt.event.*;
import javax.accessibility.*;
import javax.swing.*;
import com.ibm.eannounce.eobjects.EScrollPopup; //cr_209046022
import javax.swing.event.*; //cr_209046022

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SubActionMenu extends EMenu2 implements Accessible, SubActionContainer {
	private static final long serialVersionUID = 1L;
	private ActionListener al = null;
    private ButtonGroup m_languageBG = null;
    /**
     * keyAccel
     */
    private KeyStroke keyAccel = null; //cr_812022711
    //access	private eScrollPopup scrollPopup = null;		//cr_209046022
    private JPopupMenu scrollPopup = null; //access

    /**
     * subActionMenu
     * @param _s
     * @author Anthony C. Liberto
     */
    public SubActionMenu(String _s) {
        super(_s);
    }

    /**
     * setActionListener
     *
     * @author Anthony C. Liberto
     * @param _al
     */
    public void setActionListener(ActionListener _al) {
        al = _al;
        return;
    }

    //50827	public void adjustSubItems(EANActionItem[] _eai, boolean _bHasData) {
    /**
     * adjustSubItems
     *
     * @author Anthony C. Liberto
     * @param _bHasData
     * @param _eai
     * @param _type
     */
    public void adjustSubItems(EANActionItem[] _eai, boolean _bHasData, int _type) { //50827
        int ii = -1;
        if (isPopupMenuVisible()) {
            setPopupMenuVisible(false);
        }
        clearSubItems();
        if (_eai == null) {
            setEnabled(false);
            return;
        }
        ii = _eai.length;
        if (ii == 0) {
            setEnabled(false);
        } else if (_type == ACTION_PICK_LIST) { //50827
            setEnabled(false); //50827
            for (int i = 0; i < ii; ++i) { //50827
                if (_eai[i] instanceof NavActionItem) { //50827
                    if (((NavActionItem) _eai[i]).isPicklist()) { //50827
                        addSubItem(_eai[i]); //50827
                        setEnabled(_bHasData); //50827
                    } //50827
                } //50827
            } //50827
        } else if (_type == ACTION_NAVIGATE) { //50827
            setEnabled(false); //50827
            for (int i = 0; i < ii; ++i) { //50827
                if (_eai[i] instanceof NavActionItem) { //50827
                    if (!((NavActionItem) _eai[i]).isPicklist()) { //50827
                        addSubItem(_eai[i]); //50827
                        setEnabled(_bHasData); //50827
                    } //50827
                } //50827
            } //50827
        } else {
            for (int i = 0; i < ii; ++i) {
                addSubItem(_eai[i]);
            }
            setEnabled(_bHasData);
        }
        return;
    }

    /**
     * addSubItem
     *
     * @author Anthony C. Liberto
     * @param _item
     */
    public void addSubItem(EANActionItem _item) {
        add(new SubActionMenuItem(_item, getActionCommand(), al));
        return;
    }

    /**
     * clearSubItems
     * @author Anthony C. Liberto
     */
    public void clearSubItems() {
        while (getItemCount() > 0) {
            JMenuItem item = getItem(0);
            if (item != null) {
                item.removeActionListener(al);
                item.removeAll();
                remove(item);
            }
        }
        return;
    }

    /**
     * @see javax.swing.AbstractButton#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
        super.setText(_s);
        getAccessibleContext().setAccessibleDescription(_s);
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        clearSubItems();
        al = null;
        removeAll();
        return;
    }

    //20020711

    /**
     * adjustLanguageItems
     * @param _nlsArray
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void adjustLanguageItems(NLSItem[] _nlsArray, NLSItem _nls) {
        int ii = -1;
        clearSubItems();
        m_languageBG = new ButtonGroup();
        if (_nlsArray == null) {
            setEnabled(false);
            return;
        }
        ii = _nlsArray.length;
        if (ii == 0) {
            setEnabled(false);
        } else {
            for (int i = 0; i < ii; ++i) {
                LanguageMenuItem lmi = new LanguageMenuItem(_nlsArray[i], al);
                if (_nlsArray[i].equals(_nls)) {
                    lmi.setSelected(true);
                }
                m_languageBG.add(lmi);
                add(lmi);
            }
            setEnabled(true);
        }
    }

    /*
     cr_812022711
    */
    /**
     * @see javax.swing.JMenuItem#setAccelerator(javax.swing.KeyStroke)
     * @author Anthony C. Liberto
     */
    public void setAccelerator(KeyStroke _key) {
        KeyStroke old = keyAccel;
        keyAccel = _key;
        firePropertyChange("accelerator", old, keyAccel);
        return;
    }

    /**
     * @see javax.swing.JMenuItem#getAccelerator()
     * @author Anthony C. Liberto
     */
    public KeyStroke getAccelerator() {
        return keyAccel;
    }

    /**
     * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
     * @author Anthony C. Liberto
     */
    protected boolean processKeyBinding(KeyStroke _ks, KeyEvent _ke, int _i, boolean _b) {
        boolean out = super.processKeyBinding(_ks, _ke, _i, _b);
        if (out) {
            int iCode = _ke.getKeyCode();
            if ((iCode >= KeyEvent.VK_0 && iCode <= KeyEvent.VK_9 && _ke.isControlDown()) || (iCode >= KeyEvent.VK_0 && iCode <= KeyEvent.VK_5 && _ke.isAltDown())) {
                processKeyEvent(new KeyEvent((Component) _ke.getSource(), _ke.getID(), _ke.getWhen(), 0, KeyEvent.VK_DOWN, '?'));
                if (getItemCount() == 1) {
                    getItem(0).doClick();
                    MenuSelectionManager.defaultManager().clearSelectedPath();
                }
            }

        }
        return out;
    }
    /*
     cr_209046022
     */

    /**
     * @see javax.swing.JMenu#getPopupMenu()
     * @author Anthony C. Liberto
     */
    public JPopupMenu getPopupMenu() {
        if (EAccess.isAccessible()) {
            scrollPopup = super.getPopupMenu();
        } else if (scrollPopup == null) {
            scrollPopup = new EScrollPopup();
            scrollPopup.setInvoker(this);
            popupListener = createWinListener(scrollPopup);
            scrollPopup.addPopupMenuListener(new PopupMenuListener() {
                    public void popupMenuWillBecomeVisible(PopupMenuEvent _pme) {
                    }
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent _pme) {
                    }
                    public void popupMenuCanceled(PopupMenuEvent _pme) {
                        fireMenuCanceled();
                        return;
                    }
            });
        }
        return scrollPopup;
    }

    /**
     * @see javax.swing.JMenu#getItemCount()
     * @author Anthony C. Liberto
     */
    public int getItemCount() {
        if (EAccess.isAccessible()) {
            return super.getItemCount();
        }
        return ((EScrollPopup) getPopupMenu()).getMenuItemCount();
    }

    /**
     * @see javax.swing.JMenu#getItem(int)
     * @author Anthony C. Liberto
     */
    public JMenuItem getItem(int _i) {
        if (EAccess.isAccessible()) {
            return super.getItem(_i);
        }
        return (JMenuItem) ((EScrollPopup) getPopupMenu()).getMenuItem(_i);
    }

    /**
     * @see javax.swing.JMenu#isPopupMenuVisible()
     * @author Anthony C. Liberto
     */
    public boolean isPopupMenuVisible() {
        if (EAccess.isAccessible()) {
            return super.isPopupMenuVisible();
        }
        return getPopupMenu().isVisible();
    }

    /**
     * @see javax.swing.JMenu#setPopupMenuVisible(boolean)
     * @author Anthony C. Liberto
     */
    public void setPopupMenuVisible(boolean _vis) {
        boolean isVisible = false;
        if (EAccess.isAccessible()) {
            super.setPopupMenuVisible(_vis);
            return;
        }
        isVisible = isPopupMenuVisible();
        if (_vis != isVisible && (isEnabled() || !_vis)) {
            if ((_vis == true) && isShowing()) {
                Point pt = getPopupMenuOrigin();
                getPopupMenu().show(this, pt.x, pt.y);
            } else {
                getPopupMenu().setVisible(false);
            }
        }
        return;
    }

    /**
     * @see javax.swing.JMenu#add(javax.swing.JMenuItem)
     * @author Anthony C. Liberto
     */
    public JMenuItem add(JMenuItem _menu) {
        if (EAccess.isAccessible()) {
            return super.add(_menu);
        }
        _menu.getAccessibleContext().setAccessibleParent(this);
        return getPopupMenu().add(_menu);
    }

    /**
     * @see java.awt.Container#add(java.awt.Component)
     * @author Anthony C. Liberto
     */
    public Component add(Component _c) {
        if (EAccess.isAccessible()) {
            return super.add(_c);
        }
        getPopupMenu().add(_c);
        return _c;
    }

    /**
     * add
     * @param _jc
     * @return
     * @author Anthony C. Liberto
     */
    public Component add(JComponent _jc) {
        if (EAccess.isAccessible()) {
            return super.add(_jc);
        }
        _jc.getAccessibleContext().setAccessibleParent(this);
        getPopupMenu().add(_jc);
        return _jc;
    }

    /**
     * @see java.awt.Container#add(java.awt.Component, int)
     * @author Anthony C. Liberto
     */
    public Component add(Component _c, int _i) {
        if (EAccess.isAccessible()) {
            return super.add(_c, _i);
        }
        getPopupMenu().add(_c, _i);
        return _c;
    }

    /**
     * add
     * @param _jc
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Component add(JComponent _jc, int _i) {
        if (EAccess.isAccessible()) {
            return super.add(_jc, _i);
        }
        _jc.getAccessibleContext().setAccessibleParent(this);
        getPopupMenu().add(_jc, _i);
        return _jc;
    }

    /**
     * @see javax.swing.JMenu#remove(javax.swing.JMenuItem)
     * @author Anthony C. Liberto
     */
    public void remove(JMenuItem _menu) {
        if (EAccess.isAccessible()) {
            super.remove(_menu);
            return;
        }
        ((EScrollPopup) getPopupMenu()).removeMenuItem(_menu);
        return;
    }

    /**
     * @see javax.swing.JMenu#addSeparator()
     * @author Anthony C. Liberto
     */
    public void addSeparator() {
        if (EAccess.isAccessible()) {
            super.addSeparator();
            return;
        }
        getPopupMenu().addSeparator();
        return;
    }

    /**
     * @see javax.swing.JMenu#insert(java.lang.String, int)
     * @author Anthony C. Liberto
     */
    public void insert(String _s, int _pos) {
        if (EAccess.isAccessible()) {
            super.insert(_s, _pos);
            return;
        }
        if (_pos >= 0) {
            getPopupMenu().insert(new JMenuItem(_s), _pos);
        } else {
            throw new IllegalArgumentException("index less than zero.");
        }
        return;
    }

    /**
     * @see javax.swing.JMenu#insert(javax.swing.JMenuItem, int)
     * @author Anthony C. Liberto
     */
    public JMenuItem insert(JMenuItem _menu, int _pos) {
        if (EAccess.isAccessible()) {
            return super.insert(_menu, _pos);
        }
        if (_pos >= 0) {
            _menu.getAccessibleContext().setAccessibleParent(this);
            getPopupMenu().insert(_menu, _pos);
        } else {
            throw new IllegalArgumentException("index less than zero.");
        }
        return _menu;
    }

    /**
     * @see javax.swing.JMenu#insert(javax.swing.Action, int)
     * @author Anthony C. Liberto
     */
    public JMenuItem insert(Action _act, int _pos) {
        JMenuItem menu = null;
        if (EAccess.isAccessible()) {
            return super.insert(_act, _pos);
        }
        if (_pos >= 0) {
            menu = new JMenuItem((String) _act.getValue(Action.NAME), (Icon) _act.getValue(Action.SMALL_ICON));
            menu.setHorizontalTextPosition(JButton.TRAILING);
            menu.setVerticalTextPosition(JButton.CENTER);
            menu.setEnabled(_act.isEnabled());
            menu.setAction(_act);
            getPopupMenu().insert(menu, _pos);
        } else {
            throw new IllegalArgumentException("index less than zero.");
        }
        return menu;
    }

    /**
     * @see javax.swing.JMenu#insertSeparator(int)
     * @author Anthony C. Liberto
     */
    public void insertSeparator(int _i) {
        if (EAccess.isAccessible()) {
            super.insertSeparator(_i);
            return;
        }
        if (_i > 0) {
            getPopupMenu().insert(new JPopupMenu.Separator(), _i);
        } else {
            throw new IllegalArgumentException("index less than or equal to zero.");
        }
        return;
    }
}
