// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.navigate;
import com.elogin.EAccess;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.HistRend;
import COM.ibm.eannounce.objects.*;

import java.awt.event.*;
import java.util.*;

import javax.accessibility.AccessibleContext;

/**
 * This class gives a list representation for history.  It takes a lot of space.  It is created
 * by Navigate.build() from a predefined form in the pdh - doesnt ever seem to be used.  Changed it
 * to manipulate strings instead of the NavSerialObjects, this frees up memory.  They will be reloaded
 * when needed.
 *
 * $Log: NavHistList.java,v $
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.2  2008/01/30 16:26:55  wendy
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
 */

public class NavHistList extends EScrollPane implements MouseListener, KeyListener, NavHist {
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
	private EList list = new EList();
	private SerialObjectController nsoController = null;
	private Hashtable hashFilename = new Hashtable();
	private HashMap hashAction = new HashMap();
	private int clicks = 0;

	/**
     * navHistList
     * @param _parent
     * @param _clicks
     * 
     */
    public NavHistList(Navigate _parent, int _clicks) {
		setViewportView(list);
		parent = _parent;
		nsoController = _parent.getNavSerialObjectController();
		clicks = _clicks;
		list.setCellRenderer(new HistRend(this));
		list.getAccessibleContext().setAccessibleDescription(getString("accessible.navHist"));
		list.addMouseListener(this);
		list.addKeyListener(this);
		initAccessibility("accessible.navHist");
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
     * reset
     *
     * 
     */
    public void reset() {
		while (getHistoryCount() > 0) {
			Object o = list.getItemAt(0);
			nsoController.delete((String)hashFilename.get(o));// get filename 
			hashFilename.remove(o);
			hashAction.remove(o);
			list.removeAt(0);
		}
	}

 	/**
     * load
     * 
     * @param _nso
     * @param _eList
     * @param _setSelected
     * @param _getEquivalent
     * @param _remove
     */
    public void load(NavSerialObject _nso, EntityList _eList, boolean _remove) {		
		int iCurrent = getSelectedIndex();
		if (_remove && iCurrent < getHistoryCount() -1) {
			removeHistory(iCurrent, true);
		}

		list.loadObject(_nso.toString());
		hashFilename.put(_nso.toString(),_nso.getFileName());
		hashAction.put(_nso.toString(),_nso.getAction());

		if (_eList != null) {
			list.setSelectedValue(_nso.toString(),true);
		}			
		if (getHistoryCount() > Navigate.MAXIMUM_NAVIGATE_HISTORY) {
			removeHistory(0, false);
		}		
	}
    
    /**
     * removeHistory
     *
     * @param _i
     * @param _below
     * 
     */
    public void removeHistory(int _i, boolean _below) {
		if (_below) {
			int i = _i + 1;
			while (getHistoryCount() > i) {
				removeHistory(i, false);
			}
		} else {
			Object o = list.getItemAt(_i);
			nsoController.delete((String)hashFilename.get(o));// get filename 
			hashFilename.remove(o);
			hashAction.remove(o);
			list.removeAt(_i);
		}
	}

 	/**
     * setSelectedIndex
     * @param _i
     * @param _setWait
     */
    private void setSelectedIndex(int _i, boolean _setWait) {
		Vector v = null;
        Object o = null;
        if (_setWait && eaccess().isBusy()) {
            return;
		}
		if (_setWait) {
			eaccess().setBusy(true);
		}
		v = list.getData();
		o = v.get(_i);
		if (o != null) {
			if (!parent.isPin()) {
				list.setSelectedValue(o, true);
			} else {
				Object o2 = hashFilename.get(o);
				if (o2 instanceof String) {
					parent.renavigate((String)o2,NAVIGATE_RENAVIGATE);
				}			
				
			}
		}
		if (_setWait) {
			eaccess().setBusy(false);
		}
	}

    /**
     * getEntityItems
     *
     * @param _i
     * @return
     * 
     * /
    public EntityItem[] getEntityItems(int _i) {
		Object o = list.getItemAt(_i);
		Object o2 = hashFilename.get(o);
		EntityItem[] nsoEI = nsoController.getEntityItems((String)o2);
		return nsoEI;	
	}*/

	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * 
     */
    public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == clicks) {
			Object o = hashFilename.get(list.getSelectedValue());
			if (o instanceof String) {
				parent.renavigate((String)o,NAVIGATE_RENAVIGATE);
			}			
		}
	}
	/**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * 
     */
    public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			Object o = hashFilename.get(list.getSelectedValue());
			if (o instanceof String) {
				parent.renavigate((String)o,NAVIGATE_RENAVIGATE);
			}			
		}
	}

	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * 
     */
    public void mouseEntered(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * 
     */
    public void mouseExited(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * 
     */
    public void mousePressed(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * 
     */
    public void mouseReleased(MouseEvent me) {}
	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * 
     */
    public void keyPressed(KeyEvent ke) {}
	/**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * 
     */
    public void keyTyped(KeyEvent ke) {}

    /**
     * dereference
     *
     * 
     */
    public void dereference() {
		initAccessibility(null);
		parent = null;
		nsoController = null;
		list.removeMouseListener(this);
		list.removeKeyListener(this);
		list.clear();
		hashFilename.clear();
		hashAction.clear();
		HistRend hr = (HistRend)list.getCellRenderer();
		if (hr != null){
			hr.dereference();
		}
		removeAll();
	}

    /**
     * getSelectedIndex
     *
     * @return
     * 
     */
    public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	/**
     * getItemCount
     * @return
     * 
     */
    public int getItemCount() {
		return list.getComponentCount();
	}

    /**
     * backup
     * 
     */
    public void backup() {
		int i = getSelectedIndex() -1;
		int count = getItemCount();
		if (i < 0) {
			setSelectedIndex(count -1, false);
		} else if (i >= count) {
			setSelectedIndex(0, false);
		} else if (i >= 0 && i < count) {
			setSelectedIndex(i, false);
		}
	}
    
    public Object[] getHistory(int _i){
    	Object obj[] = new Object[3];
    	obj[0]= list.getItemAt(_i); // this is the value seen (NavSerialObj.toString())
    	obj[1] = hashFilename.get(obj[0]);  // this is the filename needed to read in the NavSerialObj
    	obj[2] = hashAction.get(obj[0]);
    	return obj;
    }    

    /**
     * load from another NavHist
     */
    public void load(NavHist hist, int _navType){
		int ii = hist.getHistoryCount();
		if (_navType == NAVIGATE_RENAVIGATE) {
			ii-=2;
		}
 	
    	// load this history from the passed in history
		for (int i=0;i<ii;++i) {
			Object other[] = hist.getHistory(i); 
	
			if (!hashFilename.containsKey(other[0])) {				
				list.loadObject(other[0],i);
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
			list.removeItem(o);
		}
	}

    /**
     * refreshAppearance
     *
     * 
     */
    public void refreshAppearance() {
		revalidate();
		repaint();
	}

    /**
     * getHistoryCount
     *
     * @return
     * 
     */
    public int getHistoryCount() {
		return list.getDataSize();
	}

	/**
     * @see javax.swing.JComponent#getToolTipText()
     * 
     */
    public String getToolTipText() {
		Object o = list.getSelectedValue();
		if (o != null) {
			return o.toString();
		}
		return null;
	}

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
				Object o = list.getItemAt(i);
				items[i] = (EANActionItem)hashAction.get(o);
			}			
		}
		return items;
	}    

	/**
     * loadBookmarkHistory
     * 
     * @param _book
     */
    public void loadBookmarkHistory(BookmarkItem _book) {
		if (_book != null && _book.hasActionHistory()) {
			EANActionItem[] hist = _book.getActionHistory();
			int ii = hist.length;
			for (int i=0;i<ii;++i) {
				if (hist[i] instanceof NavActionItem) {
					NavSerialObject tmp = nsoController.generate((NavActionItem)hist[i]);
					list.addItem(i,tmp.toString());
					hashFilename.put(tmp.toString(), tmp.getFileName());
					hashAction.put(tmp.toString(), tmp.getAction());
				}
			}
			list.setListData(true);
		}
	}
/*
 accessibility
 */
	/**
     * initAccessibility
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
	}
}
