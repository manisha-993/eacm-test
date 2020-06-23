//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.actions;


import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import com.ibm.eacm.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.NavController;

import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.Profile;

/*********************************************************************
 * This is used for Roles in Workgroups - using the profile and loading the entry point into the UI
 * @author Wendy Stimpson
 */
//$Log: WGRoleAction.java,v $
//Revision 1.6  2014/10/20 19:56:09  wendy
//Add worker id to timing log output
//
//Revision 1.5  2013/10/24 15:33:10  wendy
//added rolecode to timing msg
//
//Revision 1.4  2013/10/15 17:20:47  wendy
//check for busy navigate before replacing it
//
//Revision 1.3  2012/11/09 20:47:59  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.2  2012/10/26 21:45:11  wendy
//removed extra call to disableActionsAndWait()
//
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class WGRoleAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Profile profile = null;
	
	public WGRoleAction(Profile prof) {
		putValue(Action.NAME, prof.toString());
		profile = prof;
		setActionKey(profile.getWGName()+profile.getOPWGID());
	}

	public void dereference(){
		super.dereference();
		profile = null;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().setActiveProfile(profile);
		
		// does a tab exist with this profile?
		int navIndex = EACM.getEACM().getNavigateIndex(profile);
		if (navIndex >= 0) {
			// is this navigate busy?
			NavController navCtrl = (NavController)EACM.getEACM().getTab(navIndex);
			if(!navCtrl.isWaiting()){ // if this is busy, it can not be replaced/reused
				//msg12001.0 = Existing navigate Found, would you like\nto create a new navigate?
				int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(null, Utils.getResource("msg12001.0"));
				if (reply != YES_BUTTON){
					EACM.getEACM().setSelectedIndex(navIndex);
					return;
				}
			}
		}

		EACM.getEACM().disableActionsAndWait(); //disable all other actions and also set wait cursor
		worker = new LoadWorker(profile);
		RMIMgr.getRmiMgr().execute(worker);
	}

	private class LoadWorker extends DBSwingWorker<EntityList, Void> { 
		private Profile prof = null;
		private  NavController navCtrl = null;
		private long t11 = 0L;
		LoadWorker(Profile nc){
			prof=nc;	
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,prof.getRoleCode()+" starting "+getIdStr());
				int navIndex = EACM.getEACM().getNavigateIndex(prof);
				if (navIndex >= 0) { // user already said yes to create a new tab
					prof= LoginMgr.getNewProfileInstance(prof);
				}
				if(prof !=null){
					navCtrl = new NavController(prof);
					prof.setValOnEffOn(DateRoutines.getEOD(), DateRoutines.getEOD());

					list = DBUtils.getNavigateEntry(prof);
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" "+prof.getRoleCode()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}

			return list;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					EntityList list = get();
					if (list !=null){
						navCtrl.init(list);
						EACM.getEACM().loadEntryNavigate(navCtrl);
						EACM.getEACM().setPastStatus(); // this loads status bar
						Utils.monitor("profileSelected " + navCtrl.getProfile().getOPWGID(),new Date());            
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entry entity list");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" "+prof.getRoleCode()+" dispatch ended "+Utils.getDuration(t11));
				EACM.getEACM().enableActionsAndRestore();	
				navCtrl = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			EACM.getEACM().enableActionsAndRestore();
			worker = null; 
		}
	}
}

