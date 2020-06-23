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

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;

/**
 * this is used for lock persistant from navigate  not tested at all, have to find how to enable this
 *@author Wendy Stimpson
 */
// $Log: EANLockPAction.java,v $
// Revision 1.3  2014/10/20 19:56:07  wendy
// Add worker id to timing log output
//
// Revision 1.2  2013/07/18 20:06:18  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class EANLockPAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANLockPAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_LOCK,p);
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
   			outputDebug(ei);

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

			((LockActionItem)getEANActionItem()).setEntityItems(ei);

			worker = new LockWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
	}
	private class LockWorker extends DBSwingWorker<Void, Void> {
		private long t11 = 0L;
		@Override
		public Void doInBackground() {
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				execLock((LockActionItem)getEANActionItem());
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}
			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
			getNavigate().enableActionsAndRestore();
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}

    private void execLock(LockActionItem _lock) {
        try {
            _lock.rexec(ro(), getNavigate().getProfile());
        }catch(DomainException de) { // RQ0713072645
        	com.ibm.eacm.ui.UI.showException(getNavigate(),de);
            de.dereference();
		} catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        		if (RMIMgr.getRmiMgr().reconnectMain()) {
        			try {
        				 _lock.rexec(ro(), getNavigate().getProfile());
        			} catch (Exception e) {
        				if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
        					if (com.ibm.eacm.ui.UI.showMWExcPrompt(getNavigate(), e) == RETRY) {
        					      execLock(_lock);
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
        			      execLock(_lock);
        			}// else user decide to ignore or exit
        		}else{
        			com.ibm.eacm.ui.UI.showException(getNavigate(),exc, "mw.err-title");
        		}
        	}
        }
    }
}
