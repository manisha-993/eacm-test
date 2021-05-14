//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;


import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.toolbar.ToolbarItem;

import java.awt.*;
import javax.swing.*;

/**
 * this is used to display the toolbar components in the preference dialog
 * @author Wendy Stimpson
 */
// $Log: ToolbarList.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class ToolbarList extends JList implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private ListRend rend = null;
	private DefaultListModel listModel = null;

	/**
     *  toolbarList
     */
    protected ToolbarList() {
        listModel = new DefaultListModel();
        this.setModel(listModel);
		rend = new ListRend();
		setCellRenderer(rend);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
     * importToolbar
     * @param _item
     */
    protected void importToolbar(ToolbarItem[] _item) {
    	listModel.clear();	
		for (int i=0;i<_item.length;++i) {
			if (_item[i].equals(ToolbarItem.SEPARATOR) || !listModel.contains(_item[i])) {
				listModel.addElement(_item[i]);
			}
		}
	
		if (!listModel.isEmpty()){
			setSelectedIndex(0);
		}
	
		revalidate();
	}

	/**
     * exportToolbar
     * @return
     */
    protected ToolbarItem[] exportToolbar() {
		ToolbarItem[] out = new ToolbarItem[listModel.size()];
		listModel.copyInto(out);
		return out;
	}

	/**
     * add
     * @param _item
     */
	protected void add(ToolbarItem _item) {
		int id = this.getSelectedIndex();
		if (_item.equals(ToolbarItem.SEPARATOR) || !listModel.contains(_item)) {
			if (id<0){
				id=0;
			}
			listModel.insertElementAt(_item, id);
		}else{
			id = listModel.indexOf(_item); // select it
		}

		setSelectedIndex(id);
		ensureIndexIsVisible(id);
	}

	/**
     * getToolbarItem
     * @param _i
     * @return
     */
	protected ToolbarItem getToolbarItem(int _i) {
		return (ToolbarItem)listModel.get(_i);
	}

	/**
     * moveItem
     * @param _indx
     * @param _dir
	 */
	protected void moveItem(int _indx, int _dir) {
		int newLoc = _indx + _dir;
		if(newLoc>=0 && newLoc<listModel.size()){	
			Object aObject = listModel.getElementAt(_indx);
			Object bObject = listModel.getElementAt(newLoc);
			listModel.set(_indx, bObject);
			listModel.set(newLoc, aObject);
			setSelectedValue(getToolbarItem(newLoc),true);
		}
	}

	/**
     * remove selected item
     */
	protected void removeSelected() {
	    ListSelectionModel lsm = getSelectionModel();
        int firstSelected = lsm.getMinSelectionIndex();
        int lastSelected = lsm.getMaxSelectionIndex();
        listModel.removeRange(firstSelected, lastSelected);
        if(firstSelected<listModel.size()){
        	setSelectedIndex(firstSelected);
        }else{
        	setSelectedIndex(listModel.size()-1);
        }
	}

	/**
	 * release memory
     */
    protected void dereference() {
    	rend.dereference();
    	rend = null;
    	listModel.clear();
    	listModel = null;
		removeAll();
		setUI(null);
	}

	private class ListRend extends JLabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

        private ListRend() {
			setHorizontalAlignment(SwingConstants.LEFT);
			setOpaque(true);
		}

		/**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setFont(list.getFont());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}else{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			if (cellHasFocus) {
				setBorder(UIManager.getBorder(FOCUS_BORDER_KEY));
			} else {
				setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));
			}
			if (value instanceof ToolbarItem) {
				ToolbarItem barItem = (ToolbarItem)value;
				if (barItem.equals(ToolbarItem.SEPARATOR)) {
					setText(Utils.getResource("sep"));
					setIcon(null);
				} else {
					setText(Utils.getResource(barItem.getActionName()));
					setIcon(barItem.getIcon());
				}
			} else {
				setText("");
				setIcon(null);
			}
			return this;
		}

		/**
         * release memory
         */
        private void dereference() {
			removeAll();
			setUI(null);
		}
        /**
         * Overridden for performance reasons.
         */
        public void invalidate() {}

        /**
         * Overridden for performance reasons.
         */
        public void validate() {}

        /**
         * Overridden for performance reasons.
         */
        public void revalidate() {}

        /**
         * Overridden for performance reasons.
         */
        public void repaint(long tm, int x, int y, int width, int height) {}

        /**
         * Overridden for performance reasons.
         */
        public void repaint(Rectangle r) { }

        /**
         * Overridden for performance reasons.
         */
        public void repaint() { }

        /**
         * Overridden for performance reasons.
         */
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {	
    	// Strings get interned...
    	if (propertyName=="text"
                    || propertyName == "labelFor"
                    || propertyName == "displayedMnemonic"
                    || ((propertyName == "font" || propertyName == "foreground")
                        && oldValue != newValue
                        && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

                super.firePropertyChange(propertyName, oldValue, newValue);
            }
        }

        /**
         * Overridden for performance reasons.
         */
        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }
	}
}
