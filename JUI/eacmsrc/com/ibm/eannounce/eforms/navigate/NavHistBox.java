// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eannounce.eforms.navigate;
import com.elogin.EAccessConstants;
import com.elogin.EAccess;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.HistRend;

import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.border.*;																	//50871
import javax.swing.plaf.basic.*;					//acl_selection

/**
 * This class gives a dropdown representation for history.  It is created
 * by Navigate.build() from a predefined form in the pdh - it is the default.  Changed it
 * to manipulate strings instead of the NavSerialObjects, this frees up memory.  They will be reloaded
 * when needed.
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavHistBox.java,v $
 * Revision 1.5  2012/04/05 17:37:17  wendy
 * jre142 and win7 changes
 *
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:01  tony
 * This is the initial load of OPICM
 *
 */
public class NavHistBox extends EComboBox implements Accessible, ActionListener, NavHist, EAccessConstants {			//acl_selection
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
	private Hashtable hashFilename = new Hashtable();
	private HashMap hashAction = new HashMap();
	private SerialObjectController nsoController = null;
	/**
     * noFocusBorder
     */
    private Border noFocusBorder = UIManager.getBorder("eannounce.loweredBorder");				//50871
	/**
     * focusBorder
     */
    private Border focusBorder = UIManager.getBorder("eannounce.etchedBorder");				//50871

	/**
     * navHistBox - called by Navigate.createHistory()
     * @param _parent
     * 
     */
    public NavHistBox(Navigate _parent) {
		parent = _parent;
		nsoController = _parent.getNavSerialObjectController();
		setRenderer(new HistRend(this));
		initAccessibility("accessible.navHist");
		setUI(new NavHistUI());
	}   

    /*
     * Used in cell renderer
     */
    public boolean isPickList(Object value){
    	boolean ispicklist = false;
    	if (value != null){
    		EANActionItem action = (EANActionItem)hashAction.get(value);
    		//
    		if (action instanceof NavActionItem){
    			ispicklist = ((NavActionItem)action).isPicklist();
    		}
    	}
    	
    	return ispicklist;
    }
    /**
     * reset - called by Navigate.resetHistory()
     *
     */
    public void reset() {
		while (getHistoryCount() > 0) {
			Object o = getItemAt(0);
			nsoController.delete((String)hashFilename.get(o));// get filename 
			hashFilename.remove(o);
			hashAction.remove(o);
			removeItemAt(0);
		}
	}


    /**
     * refreshAppearance
     *
     * 
     */
    public void refreshAppearance() {
		FontMetrics fm = getFontMetrics(getFont());
		d.setSize(getPreferredWidth(fm),Math.max(fm.getHeight(),20));
		setSize(d);
		setPreferredSize(d);
		((NavHistUI)getUI()).refreshAppearance();
		validate();
		if (isShowing()) {
			getParent().validate();
		}
	}

	/**
     * load
     * 
     * @param _nso
     * @param _eList
     * @param _remove
     */
    public void load(NavSerialObject _nso, EntityList _eList, boolean _remove) {		//20105
    	int iCurrent = getSelectedIndex();
    	if (_remove && iCurrent < getHistoryCount() -1) {
    		removeHistory(iCurrent, true);
    	}

    	addItem(_nso.toString());
    	hashFilename.put(_nso.toString(),_nso.getFileName());
    	hashAction.put(_nso.toString(),_nso.getAction());

    	if (_eList != null) {
    		setSelectedItem(_nso.toString());						//20011102
    	}
  
    	if (getHistoryCount() > Navigate.MAXIMUM_NAVIGATE_HISTORY) {
    		removeHistory(0,false);
    	}
	}

    /**
     * removeHistory
     *
     * @param _i
     * @param _below = true, remove everything after this item
     * 
     */
    public void removeHistory(int _i, boolean _below) {
		if (_below) {
			int i = _i + 1;
			while (getHistoryCount() > i) {
				removeHistory(i, false);
			}
		} else {
			Object o = getItemAt(_i);
			nsoController.delete((String)hashFilename.get(o));// get filename 
			hashFilename.remove(o);	
			hashAction.remove(o);
			removeItemAt(_i);
		}
	}

	/**
     * setSelectedIndex
     * @param _i
     * @param _setWait
     * 
     */
    private void setSelectedIndex(int _i, boolean _setWait) {
		if (_setWait && eaccess().isBusy()) {
            return;
		}
		if (_setWait) {
			eaccess().setBusy(true);
		}
		if (!parent.isPin()) {
			super.setSelectedIndex(_i);
		}
		if (_i >= 0) {
			String nsoFn = ((String)hashFilename.get(getItemAt(_i)));
			if (nsoFn != null) {
				parent.renavigate(nsoFn,NAVIGATE_RENAVIGATE);
			}			
		}
	}
    
    public Object[] getHistory(int _i){
    	Object obj[] = new Object[3];
    	obj[0]= getItemAt(_i); // this is the value seen (NavSerialObj.toString())
    	obj[1] = hashFilename.get(obj[0]);  // this is the filename needed to read in the NavSerialObj
    	obj[2] = hashAction.get(obj[0]); // the action
    	return obj;
    }

    /**
     * getEntityItems
     *
     * @param _i
     * @return
     * 
     * /
    public EntityItem[] getEntityItems(int _i) {
		Object o = getItemAt(_i);
		Object o2 = hashFilename.get(o);			
		EntityItem[] nsoEI = nsoController.getEntityItems((String)o2);
		return nsoEI;
	}*/

    /**
     * dereference
     *
     * 
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		hashFilename.clear();
		hashAction.clear();
		parent = null;
		nsoController = null;
		HistRend hr = (HistRend)getRenderer();
		if (hr != null){
			hr.dereference();
		}		
	}

    /**
     * backup
     * 
     */
    public void backup() {
		int i = getSelectedIndex() -1;
		int count = getHistoryCount();
		if (i < 0) {
			setSelectedIndex(count -1, false);
		} else if (i >= count) {
			setSelectedIndex(0, false);
		} else if (i >= 0 && i < count) {
			setSelectedIndex(i, false);
		}
	}

    /**
     * load from another NavHist- done when a tab is pinned
     */
    public void load(NavHist hist, int _navType){
    	String curname = null;
		Object o = getSelectedItem();
		if (o instanceof String) {
			curname = (String)o;
		}    	
    	// load this history from the passed in history
		for (int i=0;i<hist.getHistoryCount();++i) {
			Object other[] = hist.getHistory(i); 
			if (other[0].equals(curname)){
				return;
			}
			if (!hashFilename.containsKey(other[0])) {				
				insertItemAt(other[0],i);
				hashFilename.put(other[0],other[1]); // key=toString value, value=filename
				hashAction.put(other[0],other[2]);
			}				
		}  	
    }

	/**
     * remove
     * 
     * @param _s
     */
    public void remove(String _s) {
		if (hashFilename.containsKey(_s)) {
			Object o = hashFilename.remove(_s);
			hashAction.remove(_s);
			if (o instanceof String) {
				nsoController.delete((String)o);
			}			
			removeItem(o);
		}
	}

    /**
     * getHistoryCount
     *
     * @return
     * 
     */
    public int getHistoryCount() {
		return getItemCount();
	}
	
	private class NavHistUI extends javax.swing.plaf.metal.MetalComboBoxUI
	//WindowsComboBoxUI 
	{
		/**
         * refreshAppearance
         * 
         */
        public void refreshAppearance() {
			((EList2)listBox).setFixedCellHeight();
		}

		/**
         * @see javax.swing.plaf.basic.BasicComboBoxUI#createPopup()
         * 
         */
        protected ComboPopup createPopup() {
			BasicComboPopup popup = new BasicComboPopup(comboBox){
				private static final long serialVersionUID = 1L;
				protected MouseListener createListMouseListener() {
					return new MyListMouseHandler();
				}

				class MyListMouseHandler extends MouseAdapter {
					public void mouseReleased(MouseEvent _me) {
						setSelectedIndex(list.getSelectedIndex(),true);
						hide();
					}
				}

				protected KeyListener createKeyListener() {
					return new MyInvocationKeyHandler();
				}

				class MyInvocationKeyHandler extends KeyAdapter {
					public void keyReleased(KeyEvent _ke) {
						if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
							setSelectedIndex(list.getSelectedIndex(),true);
						}
					}
				}

				protected JList createList() {
					return new EList2(comboBox.getModel()) {
						private static final long serialVersionUID = 1L;
						public void processMouseEvent(MouseEvent e)  {
							if (e.isControlDown())  {
								e = new MouseEvent((Component)e.getSource(), e.getID(), e.getWhen(),
									e.getModifiers() ^ InputEvent.CTRL_MASK,
                                    e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
							}
							super.processMouseEvent(e);
						}
					};
			    }

			};
			return popup;
		}

		/**
         * @see javax.swing.plaf.basic.BasicComboBoxUI#paintCurrentValue(java.awt.Graphics, java.awt.Rectangle, boolean)
         * 
         */
        public void paintCurrentValue(Graphics g,Rectangle bounds,boolean hasFocus) {
			ListCellRenderer renderer = getRenderer();
			Component c = null;
			if (comboBox.getSelectedIndex() == -1) {
				if (hasFocus && !isPopupVisible(comboBox)) {
					c = renderer.getListCellRendererComponent(listBox, "", -1, true, false);
				} else {
					c = renderer.getListCellRendererComponent(listBox, "", -1, false, false);
				}
				if (hasFocus) {
					c.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
				} else {
					c.setBackground(getBackground());
				}
				c.setFont(getFont());
				currentValuePane.paintComponent(g,c,comboBox,bounds.x,bounds.y,bounds.width,bounds.height);
				return;
			}

			if (hasFocus && !isPopupVisible(comboBox)) {
				c = renderer.getListCellRendererComponent(listBox,getSelectedItem(),-1,true,false);
			} else {
				c = renderer.getListCellRendererComponent(listBox,getSelectedItem(),-1,false,false);
			}
			if (hasFocus) {																			//50871
				setBorder(focusBorder);																//50871
			} else {																				//50871
				setBorder(noFocusBorder);															//50871
			}																						//50871
			c.setBackground(getBackground());
			c.setFont(getFont());
			currentValuePane.paintComponent(g,c,comboBox,bounds.x,bounds.y,bounds.width,bounds.height);
		}
	}
/*
 kehrli_20030929
 */
	/**
     * @see javax.swing.JComponent#getToolTipText()
     * 
     */
    public String getToolTipText() {
		Object o = getSelectedItem();
		if (o != null) {
			return o.toString();
		}
		return null;
	}
/*
 cr_1210035324
 */
	/**
     * getNavigationHistory
     * 
     * @return EANActionItem[]
     */
    public EANActionItem[] getNavigationHistory() {
    	EANActionItem[] items = null;
		int ii = getHistoryCount();
		if (ii>0){
			ii--; // skip last one
			items = new EANActionItem[ii];	
			
			for (int i=0;i<ii;++i) {
				Object o = getItemAt(i);
				items[i] = (EANActionItem)hashAction.get(o);
			}			
		}
		return items;
	}

	/**
     * loadBookmarkHistory -
     * 
     * @param _book
     * @param _control
     */
    public void loadBookmarkHistory(BookmarkItem _book) {
		if (_book != null && _book.hasActionHistory()) {
			EANActionItem[] hist = _book.getActionHistory();
			int ii = hist.length;
			for (int i=0;i<ii;++i) {
				if (hist[i] instanceof NavActionItem) {
					NavSerialObject tmp = nsoController.generate((NavActionItem)hist[i]);
					insertItemAt(tmp.toString(),i);
					hashFilename.put(tmp.toString(), tmp.getFileName());	
					hashAction.put(tmp.toString(), tmp.getAction());
				}
			}
		}
	}

/*
 accessibility
 */
	/**
     * initAccessibility
     *
     * 
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
	}

	/**
     * reselectIndex
     *
     * @param _i
     * 
     */
    public void reselectIndex(int _i) {
		if (_i >= 0 && _i < getItemCount()) {
			super.setSelectedIndex(_i);
		}
	}
}
