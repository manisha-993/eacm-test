/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ToolbarList.java,v $
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/08 21:38:39  tony
 * JTest Formatting
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/06/19 16:06:29  tony
 * toolbar functionality update 1.2h
 *
 * Revision 1.3  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.2  2003/03/18 22:39:11  tony
 * more accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:37  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.elogin.*;
import com.ibm.eannounce.eobjects.EList2;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ToolbarList extends EList2 {
	private static final long serialVersionUID = 1L;
	private Vector v = new Vector();
	private ListRend rend = null;
	private HashMap map = new HashMap();

	/**
     *  toolbarList
     *  @author Anthony C. Liberto
     */
    public ToolbarList() {
		super();
		rend = new ListRend(this);
		setCellRenderer(rend);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return;
	}

	/**
     * importToolbar
     * @param _item
     * @author Anthony C. Liberto
     */
    public void importToolbar(ToolbarItem[] _item) {
		int ii = 0;
        clear();
		if (_item == null) {
			return;
		}
		ii = _item.length;
		for (int i=0;i<ii;++i) {
			addToolbarItem(_item[i]);
		}
		setListData(v);
		if (!v.isEmpty()) {
			setSelectedIndex(0);
		}
		revalidate();
		return;
	}

	/**
     * exportToolbar
     * @return
     * @author Anthony C. Liberto
     */
    public ToolbarItem[] exportToolbar() {
		int ii = v.size();
		ToolbarItem[] out = new ToolbarItem[ii];
		for (int i=0;i<ii;++i) {
			out[i] = (ToolbarItem)v.get(i);
		}
		return out;
	}

	private boolean addToolbarItem(ToolbarItem _item) {
		String key = _item.getKey();
		if (key == null || !map.containsKey(key) || _item.isSeparator()) {
			v.add(_item);
			if (key != null) {
				map.put(key,null);
			}
			return true;
		}
		return false;
	}

	/**
     * add
     * @param _item
     * @author Anthony C. Liberto
     */
    public void add(ToolbarItem _item) {
		addToolbarItem(_item);
		setListData(v);
		setSelectedValue(_item,true);
		return;
	}

	/**
     * getToolbarItem
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public ToolbarItem getToolbarItem(int _i) {
		return (ToolbarItem)v.get(_i);
	}

	/**
     * moveItem
     * @param _item
     * @param _dir
     * @author Anthony C. Liberto
     */
    public void moveItem(ToolbarItem _item, int _dir) {
		moveItem(v.indexOf(_item),_dir);
		return;
	}

	/**
     * moveItem
     * @param _indx
     * @param _dir
     * @author Anthony C. Liberto
     */
    public void moveItem(int _indx, int _dir) {
		int newLoc = _indx + _dir;
		Object o = null;
        if (newLoc < 0 || newLoc >= v.size()) {
			return;
		}
		o = v.remove(_indx);
		v.add(newLoc, o);
		setListData(v);
		setSelectedValue(getToolbarItem(newLoc),true);
		return;
	}

	/**
     * remove
     * @param _item
     * @author Anthony C. Liberto
     */
    public void remove(ToolbarItem _item) {
		remove(v.indexOf(_item));
	}

	/**
     * @see java.awt.Container#remove(int)
     * @author Anthony C. Liberto
     */
    public void remove(int _i) {
        ToolbarItem item = null;
        String key = null;
        int iSize = 0;
        if (_i >= v.size() || _i < 0) {
            return;
		}
		item = (ToolbarItem)v.remove(_i);
		key = item.getKey();
		if (key != null && map.containsKey(key)) {
			map.remove(key);
		}
		setListData(v);
		iSize = v.size();
		if (_i < iSize) {
			setSelectedValue(getToolbarItem(_i),true);
		} else if (_i == iSize) {
			int x = _i - 1;
			if (x <  0) {
				setSelectedValue(null,true);
			} else {
				setSelectedValue(getToolbarItem(x),true);
			}
		} else {
			setSelectedValue(null,true);
		}
		return;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		v.clear();
		map.clear();
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();
		v = null;
		removeAll();
		removeNotify();
		return;
	}

	private class ListRend extends ELabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;
		private ToolbarList list = null;

		/**
         * listRend
         * @param _list
         * @author Anthony C. Liberto
         */
        private ListRend(ToolbarList _list) {
			list = _list;
			setHorizontalAlignment(SwingConstants.LEFT);
			setOpaque(true);
			setUseDefined(false);
			return;
		}

		/**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         * @author Anthony C. Liberto
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (value instanceof ToolbarItem) {
				ToolbarItem barItem = (ToolbarItem)value;
				setFont(list.getFont());
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					if (cellHasFocus) {
						setBorder(UIManager.getBorder("eannounce.focusborder"));
					} else {
						setBorder(UIManager.getBorder("eannounce.emptyBorder"));
					}
					if (barItem.getType() == ToolbarItem.SEPARATOR_TYPE) {
						setText(getString("sep"));
						setIcon(null);
					} else {
						setText(getString(barItem.getTipDisplay()));
						setIcon(barItem.getIcon());
					}
					return this;
				}
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				if (barItem.getType() == ToolbarItem.SEPARATOR_TYPE) {
					setText(getString("sep"));
					setIcon(null);
				} else {
					setText(getString(barItem.getTipDisplay()));
					setIcon(barItem.getIcon());
				}
				if (cellHasFocus) {
					setBorder(UIManager.getBorder("eannounce.focusborder"));
				} else {
					setBorder(UIManager.getBorder("eannounce.emptyBorder"));
				}
				return this;
			} else {
				setText("");
				setIcon(null);
			}
			return this;
		}

		/**
         * dereference
         * @author Anthony C. Liberto
         */
        public void dereference() {
			list = null;
			removeAll();
			removeNotify();
		}

	}

	private String getString(String _code) {
		return eaccess().getString(_code);
	}
}
