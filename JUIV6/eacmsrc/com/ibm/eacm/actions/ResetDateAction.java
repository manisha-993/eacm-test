//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.TabPanel;

/*********************************************************************
 * This is used for changing profile valon requests
 * @author Wendy Stimpson
 */
//$Log: ResetDateAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class ResetDateAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private TabPanel currentTab = null;
	private static String NO_PROF = Utils.getResource(RESETDATE_ACTION);
	
	/**
	 * constructor
	 */
	public ResetDateAction() {
		super(RESETDATE_ACTION);
		setEnabled(false);
	}

	/**
	 * @param t
	 */
	public void setCurrentTab(TabPanel t){
		currentTab = t;
	    setEnabled(true);
		// change the menu text
	    setMenuText();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && currentTab!=null && currentTab.getProfile()!=null);
	}
	
	/**
	 * indicate profile that will be affected
	 */
	private void setMenuText(){
		if (currentTab==null || currentTab.getProfile()==null){
			putValue(Action.NAME, NO_PROF); 
		}else{
			putValue(Action.NAME, Utils.getResource(RESETDATEPROF,currentTab.getProfile().toString()));
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		currentTab = null;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (currentTab.isDateDialedBack()) { // profile already dialed back in time
			//msg23042 = Would you like to run in the present?
			int r = com.ibm.eacm.ui.UI.showConfirmYesNoCancel(null, Utils.getResource("msg23042"));
			if (r == YES_BUTTON){      
				currentTab.setProcessTime(DateRoutines.getEOD());  
			} else if (r == NO_BUTTON){
				showResetDateDialog();
			} 
		} else {
			showResetDateDialog();
		}
	}
	/**
	 * show the date dialog
	 */
	private void showResetDateDialog(){
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			   	com.ibm.eacm.ui.ResetDateDialog bmd =  new com.ibm.eacm.ui.ResetDateDialog(currentTab);
		    	bmd.setVisible(true);
			}
		});
	}
}