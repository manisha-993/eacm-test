// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;


import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.tree.ActionTreeScroll;


import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

import javax.accessibility.AccessibleContext;

/**
 * this is used for dual nav
 * @author Wendy Stimpson
 */
// $Log: NavDataDouble.java,v $
// Revision 1.4  2014/05/01 19:35:16  wendy
// Add tablayoutpolicy to support scroll of action tabbed pane
//
// Revision 1.3  2013/09/19 22:12:24  wendy
// control sort when a row is updated
//
// Revision 1.2  2013/05/01 18:35:13  wendy
// perf updates for large amt of data
//
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class NavDataDouble extends JTabbedPane implements NavData, RowSorterListener, EACMGlobals
{
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
    private EntityList entityList = null;
    private MouseAdapter mouseL = null;

    private int clicks = -1;

    private DataTab pnlDummy = null;
    private EntityGroup peg = null;

    /**
     *
     * @param pnt
     * @param clks
     */
    protected NavDataDouble(Navigate pnt, int clks) {
        parent = pnt;
        clicks = clks;
        mouseL = new MouseAdapter() {
        	public void mouseClicked(MouseEvent me) {
        		if (me.getClickCount() == 2) {
        			Component c = getComponentAt(getSelectedIndex());
        			if (c instanceof DataTab) {
        				((DataTab) c).setSelectedIndex(1);
        			}
        		}
        	}
        };

        Dimension d = new Dimension(200, 200);
        setPreferredSize(d);
        setSize(d);
        setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        addChangeListener(parent);
        initAccessibility("accessible.navData");
    }

    /**
     * getNavigate used to set current navigate when focus changes
     *
     * @return
     */
    public Navigate getNavigate() {
        return parent;
    }


    /* (non-Javadoc)
     * @see com.ibm.eannounce.eforms.navigate.NavData#loadNav(COM.ibm.eannounce.objects.EntityList)
     */
    public EntityGroup loadNav(EntityList eList) {
        int x = 0;

        removeChangeListener(parent);

        removeAll();
        entityList = eList;

		RowSelectableTable rst = entityList.getTable();

		EANFoundation[] ean = rst.getTableRowsAsArray();

		Arrays.sort(ean, new java.util.Comparator<Object>(){
			 public int compare(Object o1, Object o2) {
				 return o1.toString().compareToIgnoreCase(o2.toString());
			 }
		});

        peg = eList.getParentEntityGroup(); //peg_show
        if (peg != null && eList.isParentDisplayable()) { //peg_show
            addTab(peg, x++); //peg_show
        } //peg_show

        // add in sorted order
        for (int i = 0; i < ean.length; ++i) {
            EntityGroup eg = eList.getEntityGroup(ean[i].getKey());
            if (eg.isDisplayable()) {
                addTab(eg, x++);
            }
        }

        if (getTabCount() == 0) {
        	if(pnlDummy==null){
        		pnlDummy = new DataTab();
        	}
            add(pnlDummy, 0);
            setTitleAt(0, Utils.getResource("nia"));
            return null;
        }

        setSelectedIndex(-1); // deselect all or notification may not be sent when (0) is selected

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
     * @see com.ibm.eacm.nav.NavData#getSelectedEntityItems(boolean, boolean)
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
    		setSelectedIndex(key);
    		EntityGroupTable nt = getTable(key);
    		if (nt != null) {
    			nt.highlight(keys);
    		}
    	}else{
    		if(getTabCount()>0){
    			setSelectedIndex(0);
    		}
    	}
    }

    /**
     * setSelectedIndex
     * @param key
     */
    private void setSelectedIndex(String key) {
		int index = getIndexOf(key);
		if (index != -1) {
			setSelectedIndex(index);
		}
    }


    /**
     * getTable
     * @param key
     * @return
     */
    private EntityGroupTable getTable(String key) {
    	EntityGroupTable nt = getTable(getIndexOf(key));
        if (nt != null) {
            return nt;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getTable()
     */
    public EntityGroupTable getTable() {
        return getTable(getSelectedIndex());
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getTable(int)
     */
    public EntityGroupTable getTable(int i) {
        DataTab tab = getDataTab(i);
        if (tab != null) {
            return tab.getTable();
        }
        return null;
    }
	/**
	 * entityitems were updated after an edit, so refresh the table
	 */
    public void updateTable(){
    	for (int i = 0; i < getTabCount(); ++i) {
    		DataTab tab = getDataTab(i);
    		if (tab != null) {
    			boolean wasFiltered = tab.getTable().isFiltered();
    			tab.getTable().updateTable();
    			if(wasFiltered){
    				tab.getTable().filter();
    			}
    		}
    	}
    }
    
	/**
	 * entityitems were updated after an edit, so refresh the table
	 */
    public void updateTableWithSelectedRows(){
    	for (int i = 0; i < getTabCount(); ++i) {
    		DataTab tab = getDataTab(i);
    		if (tab != null) {
    			boolean wasFiltered = tab.getTable().isFiltered();
    			tab.getTable().updateTableWithSelectedRows();
    			if(wasFiltered){
    				tab.getTable().filter();
    			}
    		}
    	}
    }
	
    /**
     * getDataTab
     * @return
     */
    protected DataTab getDataTab() {
        return getDataTab(getSelectedIndex());
    }

    /**
     * getNavActionTree
     * @return
     */
    protected ActionTreeScroll getNavActionTree() {
        DataTab tab = getDataTab();
        if (tab != null) {
            return tab.getNavActionTree();
        }
        return null;
    }

    /**
     * getDataTab
     * @param i
     * @return
     */
    private DataTab getDataTab(int i) {
        if (i >= getTabCount() || i < 0) {
            return null;
        }
        return (DataTab) getComponentAt(i);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#export()
     */
    public String export() {
        return getTable().export();
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#getEntityGroup()
     */
    public EntityGroup getEntityGroup() {
        return getEntityGroup(getSelectedIndex());
    }

    /**
     * getEntityGroup
     *
     * @param i
     * @return
     */
    private EntityGroup getEntityGroup(int i) {
    	EntityGroupTable nt = getTable(i);
        if (nt != null) {
            return nt.getEntityGroup();
        }
        return null;
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


    private void addTab(EntityGroup eg, int index) {
    	EntityGroupTable nt =  new EntityGroupTable(eg);

    	// table deref will remove all mouselisteners
        nt.addMouseListener(parent.getMouseListener());//this opens the popup menu
        nt.addMouseListener(mouseL); // this selects the action tab when double click on the entitytable
    	nt.getSelectionModel().addListSelectionListener(parent);

        if(nt.getRowSorter() !=null){
        	nt.getRowSorter().addRowSorterListener(this);
        }

        DataTab dTab = new DataTab(nt, clicks,parent);

        add(dTab, index);

        // entity group is the title of the tab
        setToolTipTextAt(index, getTip(eg));
        setTitleAt(index, getTitle(eg));
    }


    /**
     * getIndexOf
     * @return int
     * @param s
     */
    private int getIndexOf(String s) {
    	if (Routines.have(s)) {
    		for (int i = 0; i < getTabCount(); ++i) {
    			EntityGroup eg = getEntityGroup(i);
    			String tmp = eg.getKey();
    			if (s.equals(tmp)) {
    				return i;
    			}
    		}
    	}
    	return -1;
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#gotoTab(java.lang.String)
     */
    public boolean gotoTab(String s) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = getEntityGroup(i);
            String desc = eg.toString().toUpperCase();
            if (desc.startsWith(s.toUpperCase())) {
                setSelectedIndex(eg.getKey());
                return true;
            }
        }
        return false;
    }
    private String getTitle(EntityGroup eg) {
        String s = null;
        if (eg == peg) {
            s = INDICATE_PARENT + eg.toString();
        } else {
            s = eg.toString();
        }
        int i = eg.getEntityItemCount();
        if (i == 0) {
            return Routines.truncate(s, 20);
        }
        return Routines.truncate(s, 20) + " (" + i + ")";
    }

    private String getTip(EntityGroup eg) {
        int i = eg.getEntityItemCount();
        if (i == 0) {
            return eg.toString();
        }
        return eg.toString() + " (" + i + ")";
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.nav.NavData#dereference()
     */
    public void dereference() {
    	initAccessibility(null);

    	removeChangeListener(parent);

    	if (pnlDummy != null) {
    		pnlDummy.dereference();
            pnlDummy = null;
        }

        entityList = null;// nso will deref the list

        mouseL = null;
        peg = null;

        removeAll();
        setUI(null);

    	parent = null;
    }

    /**
     * @see java.awt.Container#removeAll()
     */
    public void removeAll() {
    	while (getTabCount() > 0) {
    		DataTab tab = getDataTab(0);

    		EntityGroupTable nt = tab.getTable();
    		if (nt != null) {
    			if(nt.getRowSorter() !=null){
    				nt.getRowSorter().removeRowSorterListener(this);
    			}
    			nt.getSelectionModel().removeListSelectionListener(parent);
    		}
    		tab.dereference();// this will deref the table

    		removeTabAt(0);
    	}
    }

    /* (non-Javadoc)
     * - based on row only
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

    /* (non-Javadoc)
     * - gets it based on row and column
     * @see com.ibm.eacm.nav.NavData#getCurrentEntityItem()
     */
    public EntityItem getCurrentEntityItem() {
    	EntityGroupTable nt = getTable();
		if (nt == null) {
            return null;
		}
		return nt.getCurrentEntityItem();
    }


    /**
     * @see javax.swing.JComponent#getBorder()
     * highlight current nav with blue border
     */
    public Border getBorder() {
    	Border out = parent.getSelectedBorder();
    	if (out != null) {
    		return out;
    	}
    	return super.getBorder();
    }
	/*
	 * make selected tab lighter than the rest
	 * @see javax.swing.JTabbedPane#getBackgroundAt(int)
	 */
	public Color getBackgroundAt(int index) {
		Color out = super.getBackgroundAt(index);
		if (out != null) {
			if (getSelectedIndex()!=index) {
				out = out.darker();
			}
		}
		return out;
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
    

	/* (non-Javadoc)
	 * @see javax.swing.event.RowSorterListener#sorterChanged(javax.swing.event.RowSorterEvent)
	 */
	public void sorterChanged(RowSorterEvent e) {
		// force highlighted border if the table column header is clicked
		parent.getNavController().setCurrentNavigate(parent);
	}

}
