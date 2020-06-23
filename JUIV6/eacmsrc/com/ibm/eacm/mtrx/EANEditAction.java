//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.mtrx;

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
 * this is used for Matrix edit action
 * not tested at all, modeled after nav editaction
 *@author Wendy Stimpson
 */
//$Log: EANEditAction.java,v $
//Revision 1.5  2014/10/20 19:56:08  wendy
//Add worker id to timing log output
//
//Revision 1.4  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.3  2012/12/03 19:41:15  wendy
//fix typo
//
//Revision 1.2  2012/11/09 20:47:58  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class EANEditAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private MatrixActionTab mtrxActionTab=null;
	public EANEditAction(EANActionItem act,MatrixActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_EDIT,p);
		mtrxActionTab = n;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			EntityItem[] ei = mtrxActionTab.getRelatedEntityItems();

			if (ei != null) {
				if (EACM.getEACM().tabExists(mtrxActionTab.getProfile(),ei, getEANActionItem())) {
					return;
				}

				outputDebug(ei);

				mtrxActionTab.disableActionsAndWait(); //disable all other actions and also set wait cursor

				// check the domains here
				// check edit to output warning msg
				try{
					EntityList.checkDomain(mtrxActionTab.getProfile(),getEANActionItem(),ei); // RQ0713072645
				}catch(DomainException de){
					// just do it for edit here to get msg, it will be enforced in mw
					com.ibm.eacm.ui.UI.showFYI(mtrxActionTab, de.getMessage());
					de.dereference();
				}


				//check for existing wu for same profile, action and ei
				//if (!ELogin.getEACM().tabExists(mtrxActionTab.getProfile(), ei, getEANActionItem())) {

				worker = new EditWorker(ei);
				RMIMgr.getRmiMgr().execute(worker);
				//}
			}else{
				//msg11014.1 = None of the selected items were elgible for {0}.
				com.ibm.eacm.ui.UI.showFYI(mtrxActionTab, Utils.getResource("msg11014.1",Utils.getResource("edit")));
			}
		} catch (OutOfRangeException _range) {
			com.ibm.eacm.ui.UI.showFYI(mtrxActionTab,_range);
		}
	}
	public void dereference(){
		super.dereference();
		mtrxActionTab = null;
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
				Profile prof = LoginMgr.getNewProfileInstance(mtrxActionTab.getProfile());
				if(prof!=null){
					if(getEANActionItem() instanceof ExtractActionItem){
						list = DBUtils.getVEEdit((ExtractActionItem)getEANActionItem(), eia,
								prof, mtrxActionTab);
					}else{
						list = DBUtils.getEdit((EditActionItem)getEANActionItem(), eia,
								prof, mtrxActionTab);
					}
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(MTRX_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
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
						EditController ec = new EditController(list, null);//mtrxActionTab);
						if(getEANActionItem() instanceof EditActionItem){ // dont do this for veedits
							ec.setSourceEntityItems(eiaSrc);
						}
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" time to create ec "+Utils.getDuration(t1));
						ec.setParentProfile(mtrxActionTab.getProfile());
						EACM.getEACM().addTab(mtrxActionTab, ec);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				eia = null;
				eiaSrc = null;
				mtrxActionTab.enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			mtrxActionTab.enableActionsAndRestore();
			worker = null;
		}
	}

}
