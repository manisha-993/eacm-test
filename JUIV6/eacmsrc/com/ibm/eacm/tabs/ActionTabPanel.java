//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;


import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.EANAction;
import com.ibm.eacm.actions.EANActionSet;
import com.ibm.eacm.actions.ResetDateAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.BehaviorPref;

/**
 * base class for action tabs- lock, query, restore,editcontroller, whereused, matrix
 * @author Wendy Stimpson
 */
//$Log: ActionTabPanel.java,v $
//Revision 1.2  2013/07/18 18:45:25  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public abstract class ActionTabPanel extends TabPanel {
	private static final long serialVersionUID = 1L;
	private JLabel sesLbl = null;
	private String parentKey = null; 
	
	/**
	 * constructor used by lock, query and restore
	 */
	protected ActionTabPanel(){
    	this(null);
	}
	/**
	 * constructor used by edit, matrix and whereused
	 * @param key
	 */
	protected ActionTabPanel(String key){
		parentKey = key;
	}
	/**
	 * actionitem key from parent that created this tab
	 * @return
	 */
	public String getParentKey() { return parentKey;}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#dereference()
	 */
	public void dereference(){
		super.dereference();

		parentKey = null;
		
		if (sesLbl!=null){
			sesLbl.removeAll();
			sesLbl.setUI(null);
			sesLbl = null;
		}
	}
	/**
	 * disable an EANxxx actions - used when nothing is selected in the table
	 */
	protected void disableEANActions() { 
		for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement(); 
			if(action instanceof EANAction){
				action.setEnabled(false);
			}else if(action instanceof EANActionSet){
				action.setEnabled(false);
			}
		}
	}
    /**
     * this displays where working
     * @param s
     */
	protected void setSessionTagText(String s) {
		sesLbl = new JLabel();

		//sesLbl = You are currently working in {0}.
		String	msg= Utils.getResource("sesLbl",s);

		sesLbl.setText(msg);
		sesLbl.setToolTipText(msg);

		add(sesLbl,BorderLayout.NORTH);
    }

    /* (non-Javadoc)
     * this tab is now selected, update as needed
     * @see com.ibm.eacm.tabs.TabPanel#select()
     */
    public void select() { 

    	if (shouldRefresh()) { 
    		if (BehaviorPref.alwaysRefresh()) { 
    			refresh(); 
    		} else if (BehaviorPref.neverRefresh()) { 
    		} else { 
    			//msg11012 = The information currently displayed has changed, \nwould you like to refresh the display?
    			int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(this, Utils.getResource("msg11012"));
    			if (reply==YES_BUTTON){
    				refresh();
    			}
    		} 
    	} 
    	
    	EACMAction act = EACM.getEACM().getGlobalAction(RESETDATE_ACTION);
    	if(act instanceof ResetDateAction) {
    		((ResetDateAction)act).setCurrentTab(this);
    	}
    	
    	EACM.getEACM().updateMenuBar(getMenubar());
    	EACM.getEACM().setHiddenStatus(hasHiddenColumns());
    	EACM.getEACM().setFilterStatus(isFiltered());
    	EACM.getEACM().setActiveProfile(getProfile());

    	EACM.getEACM().setActionEnabled(RESETDATE_ACTION, false);
        EACM.getEACM().setPastStatus(); 

    	requestFocusInWindow(); 
    }

    /**
     * are any filters applied to the table 
     * @return
     */
    protected boolean isFiltered(){
    	if (getJTable()!=null){
    		return getJTable().isFiltered();
    	}
    	return false;
	}
	/**
	 * are any columns hidden in the table
	 * @return
	 */
	protected boolean hasHiddenColumns() {
		if (getJTable()!=null){
    		return getJTable().hasHiddenCols();
    	}
    	return false;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabMenuTitle()
	 */
	public String getTabMenuTitle(){
		return Utils.getResource("tab.title.menu",Utils.getResource(getTabMenuTitleKey()), 
				getTableTitle(),getProfile().toString());
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabToolTipText()
	 */
	public String getTabToolTipText() {
		return getTabMenuTitle();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabTitle()
	 */
	public String getTabTitle() {
		return Utils.getResource("tab.title",getTableTitle(),
				getProfile().toString());
	}
	/**
	 * get the resource key for the window menu
	 * @return
	 */
	protected abstract String getTabMenuTitleKey();
	
	/**
	 * createPopupMenu
	 */
	protected void createPopupMenu() {
		popup = new JPopupMenu();

		popup.add(getAction(MOVECOL_LEFT_ACTION));
		popup.add(getAction(MOVECOL_RIGHT_ACTION));
		popup.addSeparator();

		popup.add(getAction(FINDREP_ACTION));
		popup.add(getAction(FINDNEXT_ACTION));
		popup.add(getAction(HIDECOL_ACTION));
		popup.add(getAction(UNHIDECOL_ACTION));
		popup.addSeparator();
		popup.add(getAction(SORT_ACTION));
		popup.add(getAction(FILTER_ACTION));
		popup.addSeparator();
		popup.add(getAction(SELECTALL_ACTION));
		popup.add(getAction(SELECTINV_ACTION));
	}
}
