/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EMenubar.java,v $
 * Revision 1.4  2012/04/05 17:31:20  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2009/05/28 14:00:26  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2007/03/27 14:57:37  wendy
 * MN31311135 prevent null ptr in removeNotify()
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.7  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.6  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.4  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.3  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.2  2004/03/26 20:46:28  tony
 * cr_812022711
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2004/01/08 18:14:05  tony
 * 53412b
 *
 * Revision 1.21  2003/11/21 00:11:48  tony
 * removed accessibility test methods
 *
 * Revision 1.20  2003/11/20 19:58:30  tony
 * accessibility
 *
 * Revision 1.19  2003/11/14 17:23:39  tony
 * accessibility
 *
 * Revision 1.18  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.17  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.16  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.15  2003/10/07 21:36:58  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.14  2003/09/05 17:32:05  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.13  2003/08/15 15:53:53  tony
 * cr_0805036452
 *
 * Revision 1.12  2003/07/18 22:14:43  joan
 * 51336
 *
 * Revision 1.11  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.10  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EMenubar extends JMenuBar implements Accessible, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private HashMap hash = new HashMap();
    private KeyStroke ks = null;
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

	// added for debug
	private String debugId="";
	public String toString() { return "Id:"+debugId+" "+super.toString();}
	private static int countid=0;
    /**
     * eMenubar
     * @author Anthony C. Liberto
     */
    public EMenubar() {
		debugId = debugId+(++countid);
        setLookAndFeel();
        if (!EAccess.isAccessible()) { //access
            KeyStroke tmpKs = KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0);
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(tmpKs, "none");
        } //access
    }

    /**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        if (isModalCursor()) {
            return EAccess.eaccess().getModalCursor();
        } else {
            EDisplayable disp = getDisplayable();
            if (disp != null) {
                return disp.getCursor();
            }
        }
        return EAccess.eaccess().getCursor();
    }

    /**
     * setModalCursor
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setModalCursor(boolean _b) {
        bModalCursor = _b;
        return;
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
        return;
    }

    /**
     * setUseBack
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseBack(boolean _b) {
        bUseBack = _b;
        return;
    }

    /**
     * setUseFor
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseFore(boolean _b) {
        bUseFore = _b;
        return;
    }

    /**
     * setUseFont
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setUseFont(boolean _b) {
        bUseFont = _b;
        return;
    }

    /**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (EAccess.eaccess().canOverrideColor() && bUseBack) {
            if (isEnabled()) {
                return EAccess.eaccess().getBackground();
            } else {
                return EAccess.eaccess().getDisabledBackground();
            }
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
        if (EAccess.eaccess().canOverrideColor() && bUseFore) {
            if (isEnabled()) {
                return EAccess.eaccess().getForeground();
            } else {
                return EAccess.eaccess().getDisabledForeground();
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
            return EAccess.eaccess().getFont();
        }
        return super.getFont();
    }

    /**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
        super.removeAll();
        if (hash != null) {
            hash.clear();
        }
    }

    /**
     * addSeparator
     * @param _menuName
     * @author Anthony C. Liberto
     */
    public void addSeparator(String _menuName) {
        getMenu(_menuName).addSeparator();
    }

    /**
     * addMenu
     * @param _menuName
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void addMenu(String _menuName, String _s, ActionListener _al, int _key, int _mod, boolean _enabled) {
        EMenuItem menuItem = null;
        if (hash.containsKey(_s)) {
            return;
        }
        menuItem = new EMenuItem(getString(_s));
        menuItem.setModalCursor(isModalCursor()); //51336
        menuItem.setName(_s);
        setMnemonic(menuItem, _s + "-s");
        menuItem.setActionCommand(_s);
        menuItem.addActionListener(_al);
        menuItem.setEnabled(_enabled);
        if (_key > 0) { //acl_Mem_20020130
            ks = KeyStroke.getKeyStroke(_key, _mod, false); //acl_Mem_20020130
            menuItem.setAccelerator(ks); //acl_Mem_20020130
        } //acl_Mem_20020130
        menuItem.setVerifyInputWhenFocusTarget(false);
        menuItem.setFont(getFont());
        hash.put(_s, menuItem);
        getMenu(_menuName).add(menuItem);
    }

    /**
     * addSubMenu
     * @param _menuName
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @author Anthony C. Liberto
     */
    public void addSubMenu(String _menuName, String _s, ActionListener _al, int _key, int _mod) {
        SubActionMenu menuItem = null;
        if (hash.containsKey(_s)) {
            return;
        }
        menuItem = new SubActionMenu(getString(_s));
        menuItem.setName(_s);
        setMnemonic(menuItem, _s + "-s");
        menuItem.setActionCommand(_s);
        menuItem.setActionListener(_al);
        if (_key > 0) { //cr_812022711
            ks = KeyStroke.getKeyStroke(_key, _mod, false); //cr_812022711
            menuItem.setAccelerator(ks); //cr_812022711
        } //cr_812022711
        menuItem.setVerifyInputWhenFocusTarget(false);
        menuItem.setFont(getFont());
        hash.put(_s, menuItem);
        getMenu(_menuName).add(menuItem);
    }

    private void setMnemonic(JMenuItem _menu, String _s) {
        char c = getChar(_s);
        _menu.setMnemonic(c);
    }

    /**
     * addRadioMenu
     * @param _menuName
     * @param _s
     * @param _al
     * @param _bg
     * @param _key
     * @param _mod
     * @param _enabled
     * @param _selected
     * @author Anthony C. Liberto
     * /
    public void addRadioMenu(String _menuName, String _s, ActionListener _al, ButtonGroup _bg, int _key, int _mod, boolean _enabled, boolean _selected) {
        String s = getString(_s);
        ERadioButtonMenuItem menuItem = new ERadioButtonMenuItem(s);
        _bg.add(menuItem);
        menuItem.addActionListener(_al);
        menuItem.setSelected(_selected);
        menuItem.setActionCommand(_s);
        menuItem.setVerifyInputWhenFocusTarget(false);
        menuItem.setEnabled(_enabled);
        if (_key > 0) { //acl_Mem_20020130
            ks = KeyStroke.getKeyStroke(_key, _mod, false); //acl_Mem_20020130
            menuItem.setAccelerator(ks); //acl_Mem_20020130
        } //acl_Mem_20020130
        //acl_Mem_20020130		if (_key > 0) menuItem.setAccelerator(KeyStroke.getKeyStroke(_key,_mod,false));
        menuItem.setFont(getFont());
        hash.put(_s, menuItem);
        getMenu(_menuName).add(menuItem);
    }*/

    /**
     * addCheckMenu
     * @param _menuName
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @param _selected
     * @author Anthony C. Liberto
     * /
    public void addCheckMenu(String _menuName, String _s, ActionListener _al, int _key, int _mod, boolean _enabled, boolean _selected) {
        String s = getString(_s);
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(s);
        menuItem.addActionListener(_al);
        menuItem.setSelected(_selected);
        menuItem.setActionCommand(_s);
        menuItem.setVerifyInputWhenFocusTarget(false);
        menuItem.setEnabled(_enabled);
        if (_key > 0) { //acl_Mem_20020130
            ks = KeyStroke.getKeyStroke(_key, _mod, false); //acl_Mem_20020130
            menuItem.setAccelerator(ks); //acl_Mem_20020130
        } //acl_Mem_20020130
        //acl_Mem_20020130		if (_key > 0) menuItem.setAccelerator(KeyStroke.getKeyStroke(_key,_mod,false));
        menuItem.setFont(getFont());
        hash.put(_s, menuItem);
        getMenu(_menuName).add(menuItem);
    }*/

    /**
     * setMenuMnemonic
     * @param _menu
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean setMenuMnemonic(String _menu, char _c) {
        JMenu menu = getMenu(_menu, false);
        if (menu == null) {
            return false;
        }
        if (EAccess.isAccessible()) {
            String strText = menu.getText();
            menu.getAccessibleContext().setAccessibleDescription(strText);
            menu.getAccessibleContext().setAccessibleName(strText);
        } else {
            menu.setMnemonic(_c);
        }
        return true;
    }

    /**
     * setItemMnemonic
     * @param _item
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean setItemMnemonic(String _item, char _c) {
        EMenuItem item = getMenuItem(_item);
        if (item == null) {
            return false;
        }
        item.setMnemonic(_c);
        return true;
    }*/

    /**
     * getMenu
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private EMenu2 getMenu(String _s) {
        return getMenu(_s, true);
    }

    /**
     * getMenu
     * @param _s
     * @param _create
     * @return
     * @author Anthony C. Liberto
     */
    private EMenu2 getMenu(String _s, boolean _create) {
        String s = _s + "menu";
        EMenu2 menu = null;
        if (hash.containsKey(s)) {
            return (EMenu2) hash.get(s);
        }
        if (!_create) {
            return null;
        }
        menu = new EMenu2(_s);
        menu.setFont(getFont());
        hash.put(s, menu);
        add(menu);
        menu.setModalCursor(isModalCursor()); //51336
        return menu;
    }

    /**
     * addMenu
     * @param _menu
     * @return
     * @author Anthony C. Liberto
     */
    protected EMenu addMenu(EMenu _menu) {
//        String s = _menu.getName() + "menu";
        add(_menu);
        return _menu;
    }

    /**
     * addMenu
     * @param _menu
     * @param _index
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenu addMenu(EMenu _menu, int _index) {
 //       String s = _menu.getName() + "menu";
        addImpl(_menu, null, _index);
        return _menu;
    }*/

    /**
     * getMenuItem
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private EMenuItem getMenuItem(String _s) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof EMenuItem) {
                return (EMenuItem) o;
            }
        }
        return null;
    }

    /**
     * doClick
     * @param _s
     * @author Anthony C. Liberto
     */
    public void doClick(String _s) {
        EMenuItem menu = getMenuItem(_s);
        if (menu != null) {
            menu.doClick();
        }
    }

    /**
     * setMenuEnabled
     * @param _s
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setMenuEnabled(String _s, boolean _b) {
        JMenu menu = getMenu(_s, false);
        if (menu != null) {
            menu.setEnabled(_b);
        }
    }

    /**
     * setEnabled
     * @param _s
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(String _s, boolean _b) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof JMenu) {
                ((JMenu) o).setEnabled(_b);

            } else if (o instanceof EMenuItem) {
                ((EMenuItem) o).setEnabled(_b);
            }
        }
    }

    /**
     * setVisible
     * @param _s
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setVisible(String _s, boolean _b) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof JMenu) {
                ((JMenu) o).setVisible(_b);

            } else if (o instanceof EMenuItem) {
                ((EMenuItem) o).setVisible(_b);
            }
        }
    }

    /**
     * setSelected
     * @param _s
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSelected(String _s, boolean _b) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof ERadioButtonMenuItem) {
                ((ERadioButtonMenuItem) o).setSelected(_b);
            } else if (o instanceof JCheckBoxMenuItem) {
                ((JCheckBoxMenuItem) o).setSelected(_b);
            }
        }
    }

    /**
     * isSelected
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSelected(String _s) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof ERadioButtonMenuItem) {
                return ((ERadioButtonMenuItem) o).isSelected();
            } else if (o instanceof JCheckBoxMenuItem) {
                return ((JCheckBoxMenuItem) o).isSelected();
            }
        }
        return false;
    }

    /**
     * hasMenu
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasMenu(String _s) {
        return hash.containsKey(_s + "menu");
    }*/

    /**
     * hasMenuItem
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasMenuItem(String _s) {
        return hash.containsKey(_s);
    }*/

    /**
     * removeMenu
     * @param _s
     * @author Anthony C. Liberto
     */
    public void removeMenu(String _s) { //acl_Mem_20020130
        JMenu item = getMenu(_s, false);
        if (item != null) { //acl_Mem_20020130
            remove(item); //acl_Mem_20020130
            hash.remove(_s + "menu");
            item.getUI().uninstallUI(item); //acl_Mem_20020130
            item.removeAll(); //acl_Mem_20020130
            item.removeNotify(); //acl_Mem_20020131
        } //acl_Mem_20020130
    } //acl_Mem_20020130

    /**
     * removeMenuItem
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeMenuItem(String _s, ActionListener _al) { //acl_Mem_20020130
        //53412b		if (hash.containsKey(_s)) {								//acl_Mem_20020130
        if (_s != null &&
			hash!=null && //MN31311135 prevent null ptr on hash if called after deref
	        hash.containsKey(_s)) { //53412b
            Object o = hash.remove(_s); //acl_Mem_20020130
            if (o instanceof SubActionMenu) {
                SubActionMenu item = (SubActionMenu) o; //acl_Mem_20020130
                remove(item); //acl_Mem_20020130
                item.removeActionListener(_al); //acl_Mem_20020130
                item.getUI().uninstallUI(item); //acl_Mem_20020130
                item.removeAll(); //acl_Mem_20020130
                item.removeNotify(); //acl_Mem_20020131
            } else if (o instanceof EMenuItem) { //acl_Mem_20020130
                EMenuItem item = (EMenuItem) o; //acl_Mem_20020130
                remove(item); //acl_Mem_20020130
                item.removeActionListener(_al); //acl_Mem_20020130
                item.setAccelerator(null); //acl_Mem_20020130
                item.getUI().uninstallUI(item); //acl_Mem_20020130
                item.removeAll(); //acl_Mem_20020130
                item.removeNotify(); //acl_Mem_20020131
            } else if (o instanceof Component) {
                remove((Component) o);
            } //acl_Mem_20020130
        } //acl_Mem_20020130
    } //acl_Mem_20020130

    /**
     * removeRadioMenu
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     * /
    public void removeRadioMenu(String _s, ActionListener _al) { //acl_Mem_20020130
        if (hash.containsKey(_s)) { //acl_Mem_20020130
            Object o = hash.remove(_s); //acl_Mem_20020130
            if (o instanceof ERadioButtonMenuItem) { //acl_Mem_20020130
                ERadioButtonMenuItem item = (ERadioButtonMenuItem) o; //acl_Mem_20020130
                remove(item); //acl_Mem_20020130
                item.removeActionListener(_al); //acl_Mem_20020130
                item.setAccelerator(null); //acl_Mem_20020130
                item.getUI().uninstallUI(item); //acl_Mem_20020130
                item.removeAll(); //acl_Mem_20020130
                item.removeNotify(); //acl_Mem_20020131
            } //acl_Mem_20020130
        } //acl_Mem_20020130
    } //acl_Mem_20020130
    */

    /**
     * removeCheckMenu
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeCheckMenu(String _s, ActionListener _al) { //acl_Mem_20020204
        if (hash.containsKey(_s)) { //acl_Mem_20020204
            Object o = hash.remove(_s); //acl_Mem_20020204
            if (o instanceof JCheckBoxMenuItem) { //acl_Mem_20020204
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) o; //acl_Mem_20020204
                remove(item); //acl_Mem_20020204
                item.removeActionListener(_al); //acl_Mem_20020204
                item.setAccelerator(null); //acl_Mem_20020204
                item.getUI().uninstallUI(item); //acl_Mem_20020204
                item.removeAll(); //acl_Mem_20020204
                item.removeNotify(); //acl_Mem_20020204
            } //acl_Mem_20020204
        } //acl_Mem_20020204
    } //acl_Mem_20020204

    //50827	public void adjustSubAction(String _s, EANActionItem[] _eai, boolean _hasData) {
    /**
     * adjustSubAction
     * @param _s
     * @param _eai
     * @param _hasData
     * @param _type
     * @author Anthony C. Liberto
     */
    public void adjustSubAction(String _s, EANActionItem[] _eai, boolean _hasData, int _type) { //50827
        SubActionMenu menu = getSubActionMenu(_s);
        if (menu != null) {
            //50827			menu.adjustSubItems(_eai, _hasData);
            menu.adjustSubItems(_eai, _hasData, _type); //50827
        }
    }

    /**
     * getSubActionMenu
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private SubActionMenu getSubActionMenu(String _s) {
        if (hash.containsKey(_s)) {
            Object o = hash.get(_s);
            if (o instanceof SubActionMenu) {
                return (SubActionMenu) o;
            }
        }
        return null;
    }

    /**
     * JMenuBar Overrides JComponent.removeNotify to unregister this menu bar with the current keyboard manager.
     * make sure this has a parent!!
     * MN31311135
     */
	public void removeNotify(){
		if (getParent()!=null){
			// get null ptr if do not have a parent.
			// removeNotify was already handled - JRE calls this when menubar is replaced
			super.removeNotify();
		}else{
			//System.err.println("Warning: Attempted to removeNotify() a menubar that did not have a parent!");
		}
	}

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeAll();
        removeNotify(); //acl_Mem_20020204
 
        if (hash != null) {
            hash.clear();
            hash = null;
        }
        ks = null; //acl_Mem_20020130
    }

    /**
     * adjustLanguageMenu
     * @param _s
     * @param _nlsArray
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void adjustLanguageMenu(String _s, NLSItem[] _nlsArray, NLSItem _nls) { //20020711
        SubActionMenu menu = getSubActionMenu(_s);
        if (menu != null) {
            menu.adjustLanguageItems(_nlsArray, _nls);
        }
    }

    /**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    private String getString(String _code) {
        return EAccess.eaccess().getString(_code);
    }

    /**
     * getChar
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    private char getChar(String _code) {
        return EAccess.eaccess().getChar(_code);
    }

    /**
     * repaintImmediately
     * @author Anthony C. Liberto
     * /
    public void repaintImmediately() {
        update(getGraphics());
    }*/
    /*
     acl_20031007
     */
    /**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    private void setLookAndFeel() {
    	 /*breaks win7 if (EAccess.isAccessible()) {
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
     pivot
     */

    /**
     * renameItem
     * @param _itemKey
     * @param _newDisplayName
     * @author Anthony C. Liberto
     */
    public void renameItem(String _itemKey, String _newDisplayName) {
        String strName = getString(_newDisplayName);
        char cMnemonic = getChar(_newDisplayName + "-s");
        if (hash.containsKey(_itemKey)) {
            Object o = hash.get(_itemKey);
            if (o instanceof JMenu) {
                JMenu menu = (JMenu) o;
                menu.setText(strName);
                menu.setMnemonic(cMnemonic);
                menu.revalidate();
            } else if (o instanceof EMenuItem) {
                EMenuItem menu = (EMenuItem) o;
                menu.setText(strName);
                menu.setMnemonic(cMnemonic);
                menu.revalidate();
            }
        }
    }
}
