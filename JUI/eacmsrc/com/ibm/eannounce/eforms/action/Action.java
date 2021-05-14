/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: Action.java,v $
 * Revision 1.2  2009/05/26 13:00:14  wendy
 * Performance cleanup
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/03/28 17:56:36  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.5  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.3  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:36  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/12/17 19:14:18  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.9  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.8  2003/09/12 16:14:54  tony
 * 52189
 *
 * Revision 1.7  2003/07/18 18:59:08  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.6  2003/07/11 17:00:16  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.5  2003/07/07 14:55:26  tony
 * added process methods
 *
 * Revision 1.4  2003/07/03 17:26:55  tony
 * updated logic to improve scripting
 *
 * Revision 1.3  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.2  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2002/11/07 16:58:23  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.action;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import com.elogin.*;
import com.ibm.eannounce.eobjects.ESplitPane;
//import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.eforms.navigate.NavCartDialog;
//import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class Action extends EPanel implements ActionListener {
	/**
	 * amSplit
	 */
	private ESplitPane amSplit = new ESplitPane();
	protected ESplitPane getSplitPane() { return amSplit;}
	private ActionController ac = null;
	//protected Object object = null;

	private EMenubar menubar = new EMenubar();
	private NavCartDialog cart = null;

	/**
	 * actionMaster
	 * @param _parent
	 * @param _o
	 * @author Anthony C. Liberto
	 */
	protected Action(ActionController _parent){ //, Object _o) {
		super(new BorderLayout());
		ac = _parent;
		//setObject(_o);
		//		init();
	}

	/**
	 * setCart
	 * @author Anthony C. Liberto
	 * @param _cart
	 */
	protected void setCart(NavCartDialog _cart) {
		cart = _cart;
	}

	/**
	 * getCart
	 * @author Anthony C. Liberto
	 * @return NavCart
	 */
	protected NavCartDialog getCart() {
		return cart;
	}

	/**
	 * isValidSplit
	 * @param _m
	 * @param _c
	 * @return
	 * @author Anthony C. Liberto
	 */
	protected boolean isValidSplit(String _m, String _c) {
		if (_m.equalsIgnoreCase(_c)) {
			return false;
		}
		if (_m.equalsIgnoreCase("top") || _m.equalsIgnoreCase("bottom")) {
			if (!_c.equalsIgnoreCase("top") && !_c.equalsIgnoreCase("bottom")) {
				return false;
			}
		}
		if (_m.equalsIgnoreCase("left") || _m.equalsIgnoreCase("right")) {
			if (!_c.equalsIgnoreCase("left") && !_c.equalsIgnoreCase("right")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * init
	 * @author Anthony C. Liberto
	 */
	protected void init() {
		amSplit.setOneTouchExpandable(true);
		amSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		if (eaccess().isExpandAction()) { //xpnd_action
			amSplit.setDividerLocation(ac.getTree().getPreferredWidth()); //xpnd_action
		} else { //xpnd_action
			amSplit.setDividerLocation(0); //xpnd_action
		} //xpnd_action

		amSplit.setLastDividerLocation(100);

		add("Center", amSplit);
	}

	/**
	 * setActionController
	 * @author Anthony C. Liberto
	 * @param _ac
	 * /
	private void setActionController(ActionController _ac) {
		ac = _ac;
	}*/

	/**
	 * getActionController
	 * @author Anthony C. Liberto
	 * @return ActionController
	 */
	public ActionController getActionController() {
		return ac;
	}

	/**
	 * setObject
	 * @author Anthony C. Liberto
	 * @param _o
	 * /
	public void setObject(Object _o) {
		object = _o;
	}*/

	/**
	 * getObject
	 * @author Anthony C. Liberto
	 * @return Object
	 * /
	public Object getObject() {
		return object;
	}*/

	/**
	 * getMenubar
	 * @author Anthony C. Liberto
	 * @return EMenubar
	 */
	protected EMenubar getMenubar() {
		return menubar;
	}

	/**
	 * setEnabled
	 * @author Anthony C. Liberto
	 * @param _enabled
	 * @param _key
	 */
	protected void setEnabled(String _key, boolean _enabled) {
		getMenubar().setEnabled(_key, _enabled);
	}

	/**
	 * createMenus
	 * @author Anthony C. Liberto
	 */
	protected void createMenus() {
		createFileMenu();
		createTableMenu();
	}

	/**
	 * removeMenus
	 * @author Anthony C. Liberto
	 */
	protected void removeMenus() {
		if (menubar != null) { //rm_20041122
			removeFileMenu();
			removeTableMenu();
			menubar.removeAll();
		} //rm_20041122
	}

	/**
	 * createFileMenu
	 * @author Anthony C. Liberto
	 */
	protected void createFileMenu() {
		String strKey = getString("file");
		menubar.addMenu(strKey, "clsT", this, KeyEvent.VK_W, Event.CTRL_MASK, true);
		menubar.addMenu(strKey, "clsA", this, KeyEvent.VK_W, Event.CTRL_MASK + Event.SHIFT_MASK, true); //20062
		menubar.addSeparator(strKey);
		if (EAccess.isTestMode()) {
			menubar.addMenu(strKey, "saveT", this, 0, 0, true);
		} //51497
		menubar.addMenu(strKey, "save", this, KeyEvent.VK_S, Event.CTRL_MASK, false);
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey, "exit", this, KeyEvent.VK_F4, Event.ALT_MASK, true);
		menubar.setMenuMnemonic(strKey, getChar("file-s"));
	}

	/**
	 * removeFileMenu
	 * @author Anthony C. Liberto
	 */
	protected void removeFileMenu() {
		if (menubar != null ){//MN31311135 prevent null ptr if deref is called before this is
			menubar.removeMenuItem("clsT", this);
			menubar.removeMenuItem("clsA", this); //20062
			if (EAccess.isTestMode()) {
				menubar.removeMenuItem("saveT", this);
			} //51497
			menubar.removeMenuItem("exit", this);
			menubar.removeMenu(getString("file"));
		}
	}

	/**
	 * \createTableMenu
	 * @author Anthony C. Liberto
	 */
	protected void createTableMenu() {
		String strKey = getString("tbl");
		//cr_0805036452		menubar.addMenu(strKey,"left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
		menubar.addMenu(strKey, "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true); //cr_0805036452
		//cr_0805036452		menubar.addMenu(strKey,"right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK + Event.SHIFT_MASK,true);
		menubar.addMenu(strKey, "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); //cr_0805036452
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey, "srt", this, 0, 0, true);
		menubar.addSeparator(strKey); //51832
		menubar.addMenu(strKey, "hide", this, 0, 0, true); //51832
		menubar.addMenu(strKey, "unhide", this, 0, 0, true); //51832
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
		//cr_0805036452		menubar.addMenu(strKey,"fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
		menubar.addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey, "histI", this, KeyEvent.VK_F11, Event.CTRL_MASK, true);
		menubar.addMenu(strKey, "histR", this, KeyEvent.VK_F11, Event.SHIFT_MASK, true); 
		menubar.addMenu(strKey, "eData", this, KeyEvent.VK_F12, Event.CTRL_MASK, true);
		menubar.setMenuMnemonic(strKey, getChar("tbl-s"));
	}

	/**
	 * \removeTableMenu
	 * @author Anthony C. Liberto
	 */
	protected void removeTableMenu() {
		if (menubar != null ){//MN31311135 prevent null ptr if deref is called before this is
			menubar.removeMenuItem("left", this);
			menubar.removeMenuItem("right", this);
			menubar.removeMenuItem("selA", this);
			menubar.removeMenuItem("iSel", this);
			menubar.removeMenuItem("srt", this);
			menubar.removeMenuItem("hide", this); //51832
			menubar.removeMenuItem("unhide", this); //51832
			menubar.removeMenuItem("f/r", this);
			menubar.removeMenuItem("fltr", this);
			menubar.removeMenuItem("histI", this);
			menubar.removeMenuItem("histR", this);
			menubar.removeMenuItem("eData", this);
			menubar.removeMenu(getString("tbl"));
		}
	}

	/**
	 * dereference
	 * @author Anthony C. Liberto
	 */
	public void dereference() {
		ac = null;

		if (amSplit != null) {
			amSplit.dereference();
			amSplit.removeAll();
			amSplit.removeNotify();
			amSplit = null;
		}
		if (menubar != null) {
			removeMenus();
			menubar.dereference();
			//MN31311135 menubar.dereference() handles these
			//menubar.removeAll();
			//menubar.removeNotify();
			menubar = null;
		}
		//object = null;
		if (cart != null) {
			//52787			cart.dereference();
			//52787			cart.removeAll();
			//52787			cart.removeNotify();
			cart = null;
		}
		removeAll();
		removeNotify();
		super.dereference();
	}

	protected String getTabToolTipText() {
        return getTabMenuTitle();
    }
	protected abstract String getTabIconKey();
	protected Icon getTabIcon(){
		return getImageIcon(getString(getTabIconKey()));
	}
	protected String getTabTitle() {
        String name = null;
        setCode("tab.title");
        setParmCount(2);
        setParm(0, ac.getTableTitle());
        setParm(1, ac.getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }
	protected abstract String getTabMenuTitleKey();
	protected String getTabMenuTitle(){
	    String name = null;
        setCode("tab.title.menu");
        setParmCount(3);
        setParm(0, getString(getTabMenuTitleKey()));
        setParm(1, ac.getTableTitle());
        setParm(2, ac.getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
	}

	protected void refreshToolbar() {}

	protected void process(String _method, String _action, String[] _parent, String[] _child) {}
	protected void performAction(EANActionItem _ai, int _navType) {}
	protected boolean contains(EntityItem[] _ei, EANActionItem _eai) { return false;}
	protected void requestFocus(int _i) {}
	protected void refreshMenu() {}

	protected abstract String getTableTitle();
	protected abstract void selectKeys(String[] _str);
	/**
	 * actionPerformed
	 * @author Anthony C. Liberto
	 * @param _ae
	 */
	public abstract void actionPerformed(ActionEvent _ae);
	/**
	 * getProfile
	 * @author Anthony C. Liberto
	 * @return Profile
	 */
	protected abstract Profile getProfile();
	/**
	 * getPanelType
	 * @author Anthony C. Liberto
	 * @return String
	 */
	public abstract String getPanelType();
	/**
	 * okToClose
	 * @author Anthony C. Liberto
	 * @return boolean
	 * @param _b
	 */
	protected boolean okToClose(boolean _b){
		return true;
	}

	/**
	 * getHelpText
	 * @return String
	 */
	protected String getHelpText() {
		return getString("nia");
	}

	/**
	 * isHidden
	 * @return boolean
	 */
	protected boolean isHidden() {
        return false;
    }
	/**
	 * isFiltered
	 * @return boolean
	 */
	protected boolean isFiltered(){
		return false;
	}

	/**
	 * getSearchableObject
	 * @author Anthony C. Liberto
	 * @return Object
	 */
	protected Object getSearchableObject() { //22377
		return null; //22377
	} //22377

	/*
	     52189
	 */
	/**
	 * select - never used
	 * @author Anthony C. Liberto
	 */
	protected void select() {
	}
	/**
	 * deselect - never used
	 * @author Anthony C. Liberto
	 */
	protected void deselect() {
	}

	/*
	     acl_20031217
	 */
	/**
	 * hasHiddenAttributes
	 * @author Anthony C. Liberto
	 * @return boolean
	 */
	protected boolean hasHiddenAttributes() {
		return false;
	}
	/*
	     6554
	 */
	/**
	 * refreshTable
	 * @author Anthony C. Liberto
	 */
	protected void refreshTable() {
	}
	
	// old or unused methods
	/**
	 * find
	 * @author Anthony C. Liberto
	 */
	//public abstract void find();
	/**
	 * filter
	 * @author Anthony C. Liberto
	 * /
	public abstract void filter();
	*/
	/**
	 * getTable
	 * @author Anthony C. Liberto
	 * @return JTable
	 */
	//public abstract JTable getTable();

	/**
	 * refresh
	 * @author Anthony C. Liberto
	 */
	//public abstract void refresh();
	/**
	 * update
	 * @author Anthony C. Liberto
	 * @return boolean
	 */
	//public abstract boolean update();
	/**
	 * copy
	 * @author Anthony C. Liberto
	 */
	//public abstract void copy();
	/**
	 * paste
	 * @author Anthony C. Liberto
	 */
	//public abstract void paste();
	/**
	 * moveColumn
	 * @author Anthony C. Liberto
	 * @param _left
	 */
	//public abstract void moveColumn(boolean _left);
	/**
	 * sort
	 * @author Anthony C. Liberto
	 * @param _ascending
	 */
	//public abstract void sort(boolean _ascending);
}

