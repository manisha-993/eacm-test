//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.tabs;


import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.tree.ActionTreeScroll;

import javax.swing.JSplitPane;
import javax.swing.KeyStroke;


/**
 * base class for action tabs with actiontrees
 * @author Wendy Stimpson
 */
//$Log: ActionTreeTabPanel.java,v $
//Revision 1.3  2014/10/03 11:08:07  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.2  2013/07/18 18:45:25  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public abstract class ActionTreeTabPanel extends ActionTabPanel {
	private static final long serialVersionUID = 1L;
	private ActionTreeScroll treeScroll = null;  // this is a JScrollPane
	private JSplitPane amSplit = null;

	/**
	 * constructor
	 * @param key
	 */
	protected ActionTreeTabPanel(String parentkey){
		super(parentkey);

		treeScroll = new ActionTreeScroll(this, 1);
	
		amSplit = new JSplitPane();
		amSplit.setOneTouchExpandable(true);
		amSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		// actions will be visible for things like whereused
		if (BehaviorPref.isExpandAction()) { 
			amSplit.setDividerLocation(treeScroll.getPreferredWidth()); 
		} else { 
			amSplit.setDividerLocation(0); 
		} 

		amSplit.setLastDividerLocation(100);
		
		//IN5515352 remove F8 keyboard mapping
		KeyStroke keyToRemove = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		Utils.removeKeyBoardMapping(amSplit, keyToRemove);
		
		add(amSplit,BorderLayout.CENTER);
	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.DataActionPanel#disableActionsAndWait()
     */
    public void disableActionsAndWait(){
     	treeScroll.setEnabled(false);
    	super.disableActionsAndWait();
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.DataActionPanel#enableActionsAndRestore()
     */
    public void enableActionsAndRestore(){
    	super.enableActionsAndRestore();
    	if(isWaiting()){ // all workers have not completed
    		return;
    	}
       	treeScroll.setEnabled(true);
    }

	/**
	 * get the splitpane so components can be added
	 * @return
	 */
	protected JSplitPane getSplitPane() { 
		return amSplit;
	}
	
	/**
	 * get the action tree
	 * @return
	 */
	protected ActionTreeScroll getActionTree() {
		return treeScroll;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTabPanel#dereference()
	 */
	public void dereference(){
		super.dereference();

		amSplit.removeAll();
		amSplit.setUI(null);
		amSplit = null;
		
		treeScroll.dereference();
		treeScroll = null;
	}
}
