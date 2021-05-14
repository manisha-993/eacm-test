//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;

import com.ibm.eacm.edit.EditController;

import COM.ibm.eannounce.objects.DomainException;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityList;

/**
 * this is used for WhereUsed create action
 * @author Wendy Stimpson
 */
// $Log: EANCreateAction.java,v $
// Revision 1.4  2014/10/20 19:56:06  wendy
// Add worker id to timing log output
//
// Revision 1.3  2013/10/24 16:59:01  wendy
// add more logging
//
// Revision 1.2  2012/12/03 19:40:56  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANCreateAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	public EANCreateAction(EANActionItem act,WUsedActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_CREATE,p);
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
			outputDebug(null);
			worker = new CreateWorker(rows[0]);
			RMIMgr.getRmiMgr().execute(worker);
			rows = null;
		}else{
			wuAction.enableActionsAndRestore();
		}
	}

	private class CreateWorker extends DBSwingWorker<EntityList, Void> {
		private int mdlrowid = 0;
		CreateWorker(int vk){
			mdlrowid = vk;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				list = create(mdlrowid);
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
					EntityList list = get();

					if(list!=null){
						EditController ec = new EditController(list, null);
						ec.setParentProfile(wuAction.getProfile());

						ec.setParentTab(wuAction);
						EACM.getEACM().addTab(wuAction, ec);
						wuAction.updateTable();
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting list for create");
			}finally{
				wuAction.enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			wuAction.enableActionsAndRestore();
			worker = null;
		}
	}

	private EntityList create(int _row) {
		try {
			return wuAction.getRSTable().create2(_row, null, ro(), wuAction.getProfile());
		} catch(DomainException de) {
			com.ibm.eacm.ui.UI.showException(wuAction,de);
			de.dereference();
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
					RMIMgr.getRmiMgr().reconnectMain()) {
				try{
					return wuAction.getRSTable().create2(_row, null, ro(), wuAction.getProfile());
				}catch(DomainException de) {
					com.ibm.eacm.ui.UI.showException(wuAction,de);
					de.dereference();
				}catch(Exception e){
					com.ibm.eacm.ui.UI.showException(wuAction,e, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(wuAction,exc, "mw.err-title");
			}
		}

		return null;
	}
}
