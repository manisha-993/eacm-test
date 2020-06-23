// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;


import COM.ibm.eannounce.objects.RowSelectableTable;

import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.tree.ActionTreeScroll;
import com.ibm.eacm.objects.Utils;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * this is a panel used with dualnav that has a tabbedpane.  the tabbedpane has a Data tab and an Actions tab.
 * the data tab is an entity group table
 * @author Wendy Stimpson
 */
//$Log: DataTab.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class DataTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollData = null;
	private JTabbedPane tabbedPane = null;
	private ActionTreeScroll actionTree = null;
	private EntityGroupTable table = null;
	private Navigate parent = null;

    /**
     * this just creates an empty panel, tab title will say no information is available
     */
	protected DataTab() {}
    
    /**
     * @param tbl
     * @param clicks
     * @param n
     */
	protected DataTab(EntityGroupTable tbl, int clicks,Navigate n) {
		super(new BorderLayout());
		setFocusable(false);
		table = tbl;
		parent = n;
		
		actionTree = createNavActionTree(clicks);

		scrollData = new JScrollPane(table);
		tabbedPane = new JTabbedPane(){
			private static final long serialVersionUID = 1L;

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
		};
		tabbedPane.add(scrollData,0);
		tabbedPane.add(actionTree,1);
		tabbedPane.setTitleAt(0,Utils.getResource("dualnavData"));//_name0); //"Data"  Data = Data
		tabbedPane.setTitleAt(1,Utils.getResource("dualnavAction"));//_name1); //"Action"
		add(tabbedPane,BorderLayout.CENTER);
		
	  	tabbedPane.addChangeListener(parent);
	}
    /**
     * @param clicks
     * @return
     */
    private ActionTreeScroll createNavActionTree(int clicks) {
    	ActionTreeScroll actionTree = new ActionTreeScroll(parent, clicks);
       	RowSelectableTable rst = table.getEntityGroup().getActionGroupTable();
       	String parentkey = null;
       	if(table.getParentAction()!=null){
       		parentkey = table.getParentAction().getActionItemKey()+table.getEntityGroup().getEntityType();
       	}
       	actionTree.load(Utils.getExecutableActionItems(table.getEntityGroup(), rst), 
    			Utils.getActionTitle(table.getEntityGroup(), rst),parentkey); 

    	return actionTree;
    }
	/**
     * getTable
     * @return
     */
    protected EntityGroupTable getTable() {
		return table;
	}

	/**
     * getNavAction - used to update action tree with selected entitygroup actions
     * @return
     */
    protected ActionTreeScroll getNavActionTree() {
		return actionTree;
	}

    /**
     * dereference
     */
    protected void dereference() {	
		if (scrollData != null) {
			scrollData.removeAll();
			scrollData.setUI(null);
			scrollData = null;
		}

		if(tabbedPane!=null){
		   	tabbedPane.removeChangeListener(parent);
			tabbedPane.removeAll();
			tabbedPane.setUI(null);
			tabbedPane = null;
		}
	
		if (actionTree !=null){
			actionTree.dereference();
			actionTree = null;
		}

		if (table!=null){
			table.dereference();
			table = null;
		}
		
		parent = null;
		
		removeAll();
		setUI(null);
	}

	/**
     * setSelectedIndex - used to select action tab when user double clicks on entity table data
     * @param i
     */
    protected void setSelectedIndex(int i) {
		tabbedPane.setSelectedIndex(i);
	}
}
