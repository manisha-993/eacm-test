//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;


import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.LockActionTab;
import com.ibm.eacm.tabs.NavController;



/*********************************************************************
 * This is used for view personal persistent locks request
 * @author Wendy Stimpson
 */
//$Log: ViewPersistentLocksAction.java,v $
//Revision 1.2  2012/11/09 20:47:59  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class ViewPersistentLocksAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private NavController parent=null;
	public ViewPersistentLocksAction(NavController n) {
		super(VIEWP_ACTION,KeyEvent.VK_8, Event.CTRL_MASK + Event.ALT_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("lockPanel.gif"));
		parent = n;
	}
	public void dereference(){
		super.dereference();
		parent=null;
	}
	public void actionPerformed(ActionEvent e) {
		if (!EACM.getEACM().viewLockExist(false)) { //if it exists, that tab is selected
			parent.disableActionsAndWait();
			//	this causes this to be executed on the event dispatch thread
			// after actionPerformed returns
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Profile prof = LoginMgr.getNewProfileInstance(parent.getProfile());
					if(prof!=null){
						try {
							LockActionTab lat = new LockActionTab(false,prof);
							EACM.getEACM().addTab(parent, lat);
						}  catch (Exception ex) {
							com.ibm.eacm.ui.UI.showException(null,ex);
						}
					}

					parent.enableActionsAndRestore();
				}
			});
		}
	}
}