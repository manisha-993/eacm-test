//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.edit.EditController;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * this is used for edit and veedit (uses extractaction) from navigate
 * @author Wendy Stimpson
 */
//$Log: EANEditAction.java,v $
//Revision 1.6  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.5  2013/11/08 20:42:55  wendy
//prevent null ptr
//
//Revision 1.4  2013/07/18 20:06:19  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.3  2013/05/01 18:35:12  wendy
//perf updates for large amt of data
//
//Revision 1.2  2012/11/09 20:47:58  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANEditAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANEditAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_EDIT,p);
	}

	public void actionPerformed(ActionEvent e) {
		outputStartInfo();

		EntityItem[] ei = null;
		try{
			ei = getEntityItems(false);
        } catch (OutOfRangeException range) {
        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),range);
        	return;
        }
        if(!checkValidSingleInput(ei)){ // make sure this action has correct number ei selected
        	return;
        }

		if (ei != null) {
			if (EACM.getEACM().tabExists(getNavigate().getProfile(),ei, getEANActionItem())) { //20059
				return; //20059
			}

			outputDebug(ei);

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

			// check the domains here
			// check edit to output warning msg
			try{
				EntityList.checkDomain(getNavigate().getProfile(),getEANActionItem(),ei); // RQ0713072645
			}catch(DomainException de){
				// just do it for edit here to get msg, it will be enforced in mw
				com.ibm.eacm.ui.UI.showFYI(getNavigate(), de.getMessage());
				de.dereference();
			}


			//check for existing wu for same profile, action and ei
			//if (!ELogin.getEACM().tabExists(getNavigate().getProfile(), ei, getEANActionItem())) {

			worker = new EditWorker(ei);
			RMIMgr.getRmiMgr().execute(worker);
			//}
		}
	}
	private class EditWorker extends DBSwingWorker<EntityList, Void> {
		private EntityItem[] eia = null;
		private EntityItem[] eiaSrc = null;
		private long t11 = 0L;
		EditWorker(EntityItem[] ei){
			eia = ei;
			if(getEANActionItem() instanceof EditActionItem){
				eiaSrc = findEditItems((EditActionItem)getEANActionItem(), eia);
			}
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				Profile prof = LoginMgr.getNewProfileInstance(getNavigate().getProfile());
				if(prof !=null){
					if(getEANActionItem() instanceof ExtractActionItem){
						list = DBUtils.getVEEdit((ExtractActionItem)getEANActionItem(), eia,
								prof, getNavigate());
					}else{
						list = DBUtils.getEdit((EditActionItem)getEANActionItem(), eia,
								prof, getNavigate());
					}
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
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
						Long t1 = System.currentTimeMillis();
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
						EditController ec = new EditController(list, getNavigate());
						if(getEANActionItem() instanceof EditActionItem){ //dont do this for VEEdits
							ec.setSourceEntityItems(eiaSrc);
						}
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" time to create ec "+Utils.getDuration(t1));
						ec.setParentProfile(getNavigate().getProfile());
						EACM.getEACM().addTab(getNavigate().getNavController(), ec);
					}
					clearAction(eia);	// why do this?
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				eia = null;
				eiaSrc = null;
				if(getNavigate()!=null){ // if cancelled, deref could have run
					getNavigate().enableActionsAndRestore();
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}

}
