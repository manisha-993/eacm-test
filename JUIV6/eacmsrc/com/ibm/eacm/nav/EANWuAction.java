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

import com.ibm.eacm.wused.WUsedActionTab;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WhereUsedActionItem;
import COM.ibm.eannounce.objects.WhereUsedList;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * this is used for Navigate where used action
 * @author Wendy Stimpson
 */
//$Log: EANWuAction.java,v $
//Revision 1.5  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.4  2013/10/24 16:58:26  wendy
//add more logging
//
//Revision 1.3  2013/07/18 20:06:18  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.2  2012/11/09 20:47:59  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANWuAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANWuAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_WHERE_USED,p);
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
		if (ei!=null){
			//check for existing wu for same profile, action and ei
			if (!EACM.getEACM().tabExists(getNavigate().getProfile(), ei, getEANActionItem())) {
				outputDebug(ei);
				getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
				worker = new WUWorker(ei);
				RMIMgr.getRmiMgr().execute(worker);
			}
		}
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
			    Profile prof = LoginMgr.getNewProfileInstance(getNavigate().getProfile());
			    if(prof !=null){
			    	wulist = DBUtils.getWUList(wai, prof, getNavigate());
			    }
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
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
						WUsedActionTab wut = new WUsedActionTab(wulist,getNavigate().getKey());
						EACM.getEACM().addTab(getNavController(), wut);
						wut.requestFocusInWindow();
						clearAction(eia);	// why do this?
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting whereused list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				eia = null;
				getNavigate().enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}
}
