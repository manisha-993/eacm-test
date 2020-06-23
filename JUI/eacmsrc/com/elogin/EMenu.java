/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EMenu.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.9  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.8  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.6  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.5  2004/03/24 21:21:11  tony
 * accessibility
 *
 * Revision 1.4  2004/03/24 20:09:39  tony
 * accessibility
 *
 * Revision 1.3  2004/03/24 16:15:57  tony
 * accessibility.
 *
 * Revision 1.2  2004/03/22 21:54:03  tony
 * accessibility
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/11/14 17:23:39  tony
 * accessibility
 *
 * Revision 1.5  2003/10/07 21:36:59  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.4  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.Profile;
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
public class EMenu extends EMenu2 implements Accessible {
	private static final long serialVersionUID = 1L;
	private HashMap hash = new HashMap();


	/**
     * eMenu
     * @param _code
     * @author Anthony C. Liberto
     */
    public EMenu(String _code) {
		super(eaccess().getString(_code));
		if (!EAccess.isAccessible()) {
			setMnemonic(eaccess().getChar(_code + "-s"));
		}
		return;
	}

	/**
     * @see javax.swing.AbstractButton#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		super.setText(_s);
		getAccessibleContext().setAccessibleName(_s);
		return;
	}

	/**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
		super.removeAll();
		hash.clear();
	}

	/**
     * addMenuItem
     * @param _s
     * @param _al
     * @return
     * @author Anthony C. Liberto
     */
    public EMenuItem addMenuItem(String _s, ActionListener _al) {
		EMenuItem item = generateMenuItem(_s,_al);
		add(item);
		return item;
	}

	/**
     * addMenuItem
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @return
     * @author Anthony C. Liberto
     */
    public EMenuItem addMenuItem(String _s, ActionListener _al, int _key, int _mod, boolean _enabled) {
		EMenuItem item = generateMenuItem(_s,_al,_key,_mod);
		item.setEnabled(_enabled);
		add(item);
		return item;
	}

	/**
     * addRadioMenu
     * @param _s
     * @param _al
     * @param _bg
     * @param _key
     * @param _mod
     * @param _enabled
     * @param _selected
     * @author Anthony C. Liberto
     */
    public void addRadioMenu(String _s, ActionListener _al, ButtonGroup _bg, int _key, int _mod, boolean _enabled, boolean _selected) {
		String s = getString(_s);
		ERadioButtonMenuItem menuItem = new ERadioButtonMenuItem(s);
		_bg.add(menuItem);
		menuItem.addActionListener(_al);
		menuItem.setSelected(_selected);
		menuItem.setActionCommand(_s);
		menuItem.setVerifyInputWhenFocusTarget(false);
		menuItem.setEnabled(_enabled);
		if (_key > 0) {
			KeyStroke ks = KeyStroke.getKeyStroke(_key,_mod,false);
			menuItem.setAccelerator(ks);
		}
		menuItem.setFont(getFont());
		hash.put(_s, menuItem);
		add(menuItem);
		return;
	}

	/**
     * removeRadioMenu
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeRadioMenu(String _s, ActionListener _al) {
		if (hash.containsKey(_s)) {
			Object o = hash.remove(_s);
			if (o instanceof ERadioButtonMenuItem) {
				ERadioButtonMenuItem item = (ERadioButtonMenuItem)o;
				remove(item);
				item.removeActionListener(_al);
				item.setAccelerator(null);
				item.getUI().uninstallUI(item);
				item.removeAll();
				item.removeNotify();
			}
		}
		return;
	}

	/**
     * generateMenu
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @return
     * @author Anthony C. Liberto
     */
    public JMenu generateMenu(String _s, ActionListener _al, int _key, int _mod, boolean _enabled) {
		String str = getString(_s);
		JMenu menu = new JMenu(str);
		menu.getAccessibleContext().setAccessibleDescription(str);
		setMnemonic(menu,_s+"-s");
		if (EAccess.isAccessible()) {
			setToolTipText(str);
		}
		menu.setVerifyInputWhenFocusTarget(false);
		menu.setEnabled(_enabled);
		menu.setFont(getFont());
		hash.put(_s, menu);
		return menu;
	}

	/**
     * removeMenuItem
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeMenuItem(String _s, ActionListener _al) {
		if (hash.containsKey(_s)) {
			Object o = hash.remove(_s);
			if (o instanceof EMenuItem) {
				EMenuItem item = (EMenuItem)o;
				item.removeActionListener(_al);
				remove(item);
			}
		}
		return;
	}

	private void setMnemonic(JMenu _menu, String _s) {
		char c = getChar(_s);
		_menu.setMnemonic(c);
		return;
	}

	private void setMnemonic(EMenuItem _menu, String _s) {
		char c = getChar(_s);
		_menu.setMnemonic(c);
		return;
	}

	/**
     * getMenu
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public JMenu getMenu(String _s) {
		if (hash.containsKey(_s)) {
			Object o = hash.get(_s);
			if (o instanceof JMenu) {
				return (JMenu)o;
			}
		}
		return null;
	}

	/**
     * generateMenuItem
     * @param _s
     * @param _al
     * @return
     * @author Anthony C. Liberto
     */
    public EMenuItem generateMenuItem(String _s, ActionListener _al) {
		EMenuItem menu = new EMenuItem(_s);
		menu.setName(_s);
		menu.getAccessibleContext().setAccessibleDescription(_s);
		menu.addActionListener(_al);
		menu.setVerifyInputWhenFocusTarget(false);
		menu.setFont(getFont());
		hash.put(_s, menu);
		return menu;
	}

	/**
     * generateMenuItem
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @return
     * @author Anthony C. Liberto
     */
    public EMenuItem generateMenuItem(String _s, ActionListener _al, int _key, int _mod) {
		String str = getString(_s);
		EMenuItem menu = new EMenuItem(str);
		menu.getAccessibleContext().setAccessibleDescription(str);
		menu.setName(_s);
		setMnemonic(menu,_s+"-s");
		menu.setActionCommand(_s);
		menu.addActionListener(_al);
		if (_key > 0) {
            menu.setAccelerator(KeyStroke.getKeyStroke(_key,_mod,false));
		}
		menu.setVerifyInputWhenFocusTarget(false);
		menu.setFont(getFont());
		hash.put(_s, menu);
		return menu;
	}

	/**
     * getMenuItem
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public EMenuItem getMenuItem(String _s) {
		if (hash.containsKey(_s)) {
			Object o = hash.get(_s);
			if (o instanceof EMenuItem) {
				return (EMenuItem)o;
			}
		}
		return null;
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
				((JMenu)o).setEnabled(_b);
			} else if (o instanceof EMenuItem) {
				((EMenuItem)o).setEnabled(_b);
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
				((JMenu)o).setVisible(_b);

			} else if (o instanceof EMenuItem) {
				((EMenuItem)o).setVisible(_b);
			}
		}
	}

	//PMI
	/**
     * addWorkgroupMenu
     * @param _s
     * @param _i
     * @param _al
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void addWorkgroupMenu(String _s, int _i, ActionListener _al, boolean _enabled) {
        SubWorkgroupMenu menu = null;
        if (hash.containsKey(_s + _i)) {
            return;
		}
		menu = new SubWorkgroupMenu(_s);
		menu.setActionListener(_al);
		menu.setVerifyInputWhenFocusTarget(false);
		menu.setEnabled(_enabled);
		menu.setFont(getFont());
		hash.put(_s + _i, menu);
		add(menu);
		return;
	}

	/**
     * removeWorkgroupMenu
     * @param _s
     * @param _i
     * @param _al
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void removeWorkgroupMenu(String _s, int _i, ActionListener _al, boolean _enabled) {
		if (hash.containsKey(_s + _i)) {
			Object o = hash.remove(_s + _i);
			if (o instanceof SubWorkgroupMenu) {
				SubWorkgroupMenu menu = (SubWorkgroupMenu)o;
				menu.removeActionListener(_al);
				menu.dereference();
				remove(menu);
			}
		}
		return;
	}

	/**
     * adjustSubWorkgroup
     * @param _s
     * @param _i
     * @param _pArray
     * @author Anthony C. Liberto
     */
    public void adjustSubWorkgroup(String _s, int _i, Profile[] _pArray) {
		SubWorkgroupMenu menu = getWorkgroupMenu(_s, _i);
		if (menu != null) {
			menu.adjustSubItems(_pArray);
		}
	}

	/**
     * getWorkgroupMenu
     * @param _s
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public SubWorkgroupMenu getWorkgroupMenu(String _s, int _i) {
		if (hash.containsKey(_s + _i)) {
			Object o = hash.get(_s + _i);
			if (o instanceof SubWorkgroupMenu) {
				return (SubWorkgroupMenu)o;
			}
		}
		return null;
	}
}
