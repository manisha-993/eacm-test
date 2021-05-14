//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.wused;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.*;

import COM.ibm.eannounce.objects.DomainException;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WhereUsedList;
/**
 * this is used for WhereUsed from a whereused
 * @author Wendy Stimpson
 */
//$Log: EANWuAction.java,v $
//Revision 1.3  2013/10/24 16:59:01  wendy
//add more logging
//
//Revision 1.2  2012/12/03 19:40:56  wendy
//fix typo
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class EANWuAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	public EANWuAction(EANActionItem act,WUsedActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_WHERE_USED,p);
		wuAction = n;
	}

	public void dereference(){
		super.dereference();
		wuAction = null;
	}

	public void actionPerformed(ActionEvent e) {
		wuAction.disableActionsAndWait();
		int rows[] = wuAction.getSelectedModelRowIndexes();
		// only use row[0]
		if(rows!=null){
			if(rows.length>1){
				//msg23007.4 = Multiple rows selected, only the first one will be used.
			 	com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.4"));
			}
			//check for existing wu for same profile, action and ei
			EntityItem ei = wuAction.getRelatedItemForMdlIndex(rows[0]);
			EntityItem[] eia = new EntityItem[]{ei};
			if (!EACM.getEACM().tabExists(wuAction.getProfile(), eia, getEANActionItem()))
			{
				outputDebug(eia);
				worker = new WUWorker(rows[0], wuAction.getSelectedKeys()[0]);
				RMIMgr.getRmiMgr().execute(worker);
			}else{
				wuAction.enableActionsAndRestore();
			}
			rows = null;
		}else{
			wuAction.enableActionsAndRestore();
		}
	}

	private class WUWorker extends DBSwingWorker<WhereUsedList, Void> {
		private int mdlrowid = 0;
		private String rowKey = null;
		WUWorker(int vk, String key){
			mdlrowid = vk;
			rowKey = key;
		}
		@Override
		public WhereUsedList doInBackground() {
			WhereUsedList list = null;
			try{
				list = getWhereUsedList(mdlrowid);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
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
					WhereUsedList list = get();

					if(list!=null){
						WUsedActionTab wutab = new WUsedActionTab(list, rowKey);
						wutab.setParentProfile(wuAction.getProfile());
						EACM.getEACM().addTab(wuAction, wutab);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting list for whereused");
			}finally{
				wuAction.enableActionsAndRestore();
				rowKey = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
			wuAction.enableActionsAndRestore();
			worker = null;
		}
	}
	private WhereUsedList getWhereUsedList(int mdlrowid) {
		WhereUsedList wul = null;
		try {
			wul = wuAction.getRSTable().getWhereUsedList(mdlrowid, null, ro(),wuAction.getProfile());
		}catch(DomainException de) {
			com.ibm.eacm.ui.UI.showException(wuAction,de);
			de.dereference();
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
					RMIMgr.getRmiMgr().reconnectMain()) {
				try{
					wul = wuAction.getRSTable().getWhereUsedList(mdlrowid, null, ro(), wuAction.getProfile());
				}catch(Exception e){
					if(RMIMgr.shouldPromptUser(exc)){
						if (com.ibm.eacm.ui.UI.showMWExcPrompt(wuAction, exc) == RETRY) {
							wul = getWhereUsedList(mdlrowid);
						}// else user decide to ignore or exit
					}else{
						com.ibm.eacm.ui.UI.showException(wuAction,exc, "mw.err-title");
					}
				}
			} else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(wuAction, exc) == RETRY) {
						wul = getWhereUsedList(mdlrowid);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(wuAction,exc, "mw.err-title");
				}
			}
		}

		return wul;
	}
}
