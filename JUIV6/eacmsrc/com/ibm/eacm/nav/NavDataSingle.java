//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;


import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;

import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.ui.EntityListTabbedPane;

import COM.ibm.eannounce.objects.*;

import java.awt.*;
import javax.swing.*;
import javax.accessibility.AccessibleContext;

/**
 * this is used for single navigate
 * @author Wendy Stimpson
 */
//$Log: NavDataSingle.java,v $
//Revision 1.2  2014/05/01 19:35:16  wendy
//Add tablayoutpolicy to support scroll of action tabbed pane
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class NavDataSingle extends EntityListTabbedPane implements NavData
{
	private static final long serialVersionUID = 1L;
    private Navigate parent = null;

	/**
     * navDataSingle
     * @param p
     */
    public NavDataSingle(Navigate p) {
		super(p.getMouseListener(),p);
        Dimension d = new Dimension(200,200);
		parent = p;

		setPreferredSize(d);
		setSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		initAccessibility("accessible.navData");
		addChangeListener(parent);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#loadNav(COM.ibm.eannounce.objects.EntityList)
     */
    public EntityGroup loadNav(EntityList eList) {
        removeChangeListener(parent);
    	super.load(eList);
    	addChangeListener(parent);
		return getEntityGroup(0);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getAllEntityItems(boolean, boolean)
     */
    public EntityItem[] getAllEntityItems(boolean bnew, boolean maxEx) throws OutOfRangeException {
    	int i = getSelectedIndex();
        if (i < 0) {
            return null;
		}
        EntityGroupTable egt = getTable(i);
		return egt.getAllEntityItems(bnew, maxEx);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.EntityListTabbedPane#getSelectedEntityItems(boolean, boolean)
     */
    public EntityItem[] getSelectedEntityItems(boolean bnew, boolean bEx) throws OutOfRangeException {
    	int i = getSelectedIndex();
        if (i < 0) {
            return null;
		}
        EntityGroupTable nt = getTable(i);
		if (nt != null) {
			return nt.getSelectedEntityItems(bnew, bEx);
		}
		return null;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#setSelected(java.lang.String, java.lang.String[])
     */
    public void setSelected(String key, String[] keys) {
    	if (key !=null){
    		EntityGroupTable egt = getTable(getIndexOf(key));
    		setSelectedIndex(key); // select tab with this group
    		if (egt != null) {
    			egt.highlight(keys);
    		}
    	}else{
    		if(getTabCount()>0){
    			setSelectedIndex(0);
    		}
    	}
	}

    public boolean requestFocusInWindow(){
    	return getTable(0).requestFocusInWindow();
    }

    /* (non-Javadoc)
     * this is probably a useless string representation
     * @see com.ibm.eacm.nav.NavData#export()
     */
    public String export() {
		return getTable().export();
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getEntityGroupKey()
     */
    public String getEntityGroupKey() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			return eg.getKey();
		}
		return null;
	}


    /**
     * @param s
     */
    private void setSelectedIndex(String s) {
		int index = getIndexOf(s);
		if (index !=-1) {
			setSelectedIndex(index);
		}
	}

    /**
     * find index of entitygroup with this key
     * @param s
     * @return
     */
    private int getIndexOf(String s) {
    	for (int i=0;i<getTabCount();++i) {
    		EntityGroup eg = getEntityGroup(i);
    		if (eg.getKey().equals(s)) {
    			return i;
    		}
    	}
    	return -1;
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#gotoTab(java.lang.String)
     */
    public boolean gotoTab(String s) {
		for (int i=0;i<getTabCount();++i) {
			EntityGroup eg = getEntityGroup(i);
			String desc = eg.toString().toUpperCase();
			if (desc.startsWith(s.toUpperCase())) {
				setSelectedIndex(eg.getKey());
				return true;
			}
		}
		return false;
	}

    /**
     * dereference
     *
     */
    public void dereference() {
		initAccessibility(null);

		removeChangeListener(parent);
		parent = null;

		super.dereference();
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getCurrentEntityItem(boolean)
     */
    public EntityItem getCurrentEntityItem(boolean bnew) {
    	EntityGroupTable nt = getTable();
		if (nt == null) {
            return null;
		}
		EntityItem ei=null;
		try {
			EntityItem eia[] = nt.getSelectedEntityItems(bnew, false);
			ei = eia[0];
		} catch (OutOfRangeException e) {
			com.ibm.eacm.ui.UI.showFYI(this,e);
		}
		return ei;
	}
	/**
     * getCurrentEntityItem - gets it based on row and column
     */
    public EntityItem getCurrentEntityItem() {
    	EntityGroupTable nt = getTable();
		if (nt == null) {
            return null;
		}
		return nt.getCurrentEntityItem();
    }

	/**
     * initAccessibility
     *
     * @param s
     */
    private void initAccessibility(String s) {
    	AccessibleContext ac = getAccessibleContext();
    	if (ac != null) {
    		if (s == null) {
    			ac.setAccessibleName(null);
    			ac.setAccessibleDescription(null);
    		} else {
    			String strAccessible = Utils.getResource(s);
    			ac.setAccessibleName(strAccessible);
    			ac.setAccessibleDescription(strAccessible);
    		}
    	}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.nav.NavData#updateTabPlacement()
	 */
	public void updateTabPlacement() {
		setTabPlacement(BehaviorPref.getTabPlacement());
		revalidate();
	}

}
