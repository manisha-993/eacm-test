//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
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
import com.ibm.eacm.tabs.ABRQSActionTab;
import com.ibm.eacm.tabs.NavController;



/*********************************************************************
* This is used for abr queue status request
* @author Wendy Stimpson
*/
//$Log: ABRQSAction.java,v $
//Revision 1.3  2015/03/05 02:20:01  stimpsow
//correct keybd accelerators
//
//Revision 1.2  2013/10/10 21:50:39  wendy
//view ABR status in UI
//
//Revision 1.1  2013/09/19 16:33:11  wendy
//add abr queue status
//
public class ABRQSAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private NavController parent=null;
	public ABRQSAction(NavController n) {
		super(ABRQS_ACTION,KeyEvent.VK_3, Event.ALT_MASK,true);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("abrView.gif"));
		parent = n;
	}
	public void dereference(){
		super.dereference();
		parent=null;
	}
	public void actionPerformed(ActionEvent e) {
		if (!EACM.getEACM().abrQStatusExist()) { //if it exists, that tab is selected
			parent.disableActionsAndWait();
			//	this causes this to be executed on the event dispatch thread
			// after actionPerformed returns
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Profile prof = LoginMgr.getNewProfileInstance(parent.getProfile());
					if(prof!=null){
						try {
							ABRQSActionTab lat = new ABRQSActionTab(prof);
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