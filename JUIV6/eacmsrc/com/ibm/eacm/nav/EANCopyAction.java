//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.edit.EditController;

import COM.ibm.eannounce.objects.*;


/**
 * this is used for Copy from Navigate
 * @author Wendy Stimpson
 */
// $Log: EANCopyAction.java,v $
// Revision 1.3  2014/10/20 19:56:07  wendy
// Add worker id to timing log output
//
// Revision 1.2  2013/07/18 20:06:18  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class EANCopyAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANCopyAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_COPY,p);
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
		if (ei == null) {
        	return;
		}
		CopyActionItem cai = (CopyActionItem) getEANActionItem();

		try {
			EntityList.checkDomain(getNavigate().getProfile(),cai,ei); // RQ0713072645
		}catch(DomainException de) { // RQ0713072645
			com.ibm.eacm.ui.UI.showException(getNavigate(),de);
			de.dereference();
			return;
		}

		outputDebug(ei);

		getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
		//msg3013 = How many times would you like to paste the Item (1 - 99)?
		int iCopies = com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1);
		if (iCopies > 0) { //copyAction
			cai.setNumOfCopy(iCopies); //copyAction
			worker = new CopyWorker(ei);
			RMIMgr.getRmiMgr().execute(worker);
		} else{
			getNavigate().enableActionsAndRestore();
		}
	}

	private class CopyWorker extends DBSwingWorker<EntityList, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		CopyWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				list = getCopyList(eia);

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
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
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
    private EntityList getCopyList(EntityItem[] _ei) {
        EntityList el = null;
        try {
            el = EntityList.getEntityList(ro(), getNavigate().getProfile(),
            		(CopyActionItem) getEANActionItem(), _ei);
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        		if (RMIMgr.getRmiMgr().reconnectMain()) {
        			try {
        				   el = EntityList.getEntityList(ro(), getNavigate().getProfile(),
                           		(CopyActionItem) getEANActionItem(), _ei);
        			} catch (Exception e) {
        				if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
        					if (com.ibm.eacm.ui.UI.showMWExcPrompt(getNavigate(), e) == RETRY) {
        					    el = getCopyList(_ei);
        					}
        				}else{
        					com.ibm.eacm.ui.UI.showException(getNavigate(),e, "mw.err-title");
        				}
        			}
        		} else{	// reconnect failed
        			com.ibm.eacm.ui.UI.showException(getNavigate(),exc, "mw.err-title");
        		}
        	}else{ // show user msg and ask what to do
        		if(RMIMgr.shouldPromptUser(exc)){
        			if (com.ibm.eacm.ui.UI.showMWExcPrompt(getNavigate(), exc) == RETRY) {
        			    el = getCopyList(_ei);
        			}// else user decide to ignore or exit
        		}else{
        			com.ibm.eacm.ui.UI.showException(getNavigate(),exc, "mw.err-title");
        		}
        	}
        }
        return el;
    }
}
