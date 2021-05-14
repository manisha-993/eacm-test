//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
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
import com.ibm.eacm.wused.WUsedActionTab;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WhereUsedActionItem;
import COM.ibm.eannounce.objects.WhereUsedList;
import COM.ibm.opicmpdh.middleware.Profile;
/**
 * this is used for Matrix whereused actions
 * not tested at all, modeled after nav wu action
 *@author Wendy Stimpson
 */
// $Log: EANWuAction.java,v $
// Revision 1.4  2014/10/20 19:56:08  wendy
// Add worker id to timing log output
//
// Revision 1.3  2012/12/03 19:41:15  wendy
// fix typo
//
// Revision 1.2  2012/11/09 20:47:58  wendy
// check for null profile from getNewProfileInstance()
//
// Revision 1.1  2012/09/27 19:39:22  wendy
// Initial code
//
public class EANWuAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private MatrixActionTab mtrxActionTab=null;
	public EANWuAction(EANActionItem act,MatrixActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_WHERE_USED,p);
		mtrxActionTab = n;
	}

	public void actionPerformed(ActionEvent e) {
		outputStartInfo();
		try {
			EntityItem[] ei = mtrxActionTab.getRelatedEntityItems();
			if (ei!=null){
				//check for existing wu for same profile, action and ei
				if (!EACM.getEACM().tabExists(mtrxActionTab.getProfile(), ei, getEANActionItem())) {
					mtrxActionTab.disableActionsAndWait(); //disable all other actions and also set wait cursor
					worker = new WUWorker(ei);
					RMIMgr.getRmiMgr().execute(worker);
				}
			}
		} catch (OutOfRangeException _range) {
			com.ibm.eacm.ui.UI.showFYI(mtrxActionTab,_range);
		}
	}
	public void dereference(){
		super.dereference();
		mtrxActionTab = null;
	}
	private class WUWorker extends DBSwingWorker<WhereUsedList, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		WUWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public WhereUsedList doInBackground() {
			WhereUsedList wulist = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				WhereUsedActionItem wai = (WhereUsedActionItem)getEANActionItem();
				wai.setEntityItems(eia);
			    Profile prof = LoginMgr.getNewProfileInstance(mtrxActionTab.getProfile());
			    if(prof!=null){
			    	wulist = DBUtils.getWUList(wai, prof, mtrxActionTab);
			    }
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(MTRX_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}

			return wulist;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					WhereUsedList wulist = get();
					if (wulist !=null){
						WUsedActionTab wut = new WUsedActionTab(wulist,mtrxActionTab.getKey());
						EACM.getEACM().addTab(mtrxActionTab, wut);
						wut.requestFocusInWindow();
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting whereused list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				eia = null;
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
