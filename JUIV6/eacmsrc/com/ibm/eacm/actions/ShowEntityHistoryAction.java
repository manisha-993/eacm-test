//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.ChangeHistoryGroup;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.HistoryInterface;
import com.ibm.eacm.tabs.DataActionPanel;


/**
 * show history for an entity
 * @author Wendy Stimpson
 *
 */
// $Log: ShowEntityHistoryAction.java,v $
// Revision 1.2  2013/09/13 18:20:56  wendy
// show frame if iconified
//
// Revision 1.1  2012/09/27 19:39:17  wendy
// Initial code
//
public class ShowEntityHistoryAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private HistoryInterface hist=null;
	private DataActionPanel tab;
	
	public ShowEntityHistoryAction(DataActionPanel dap, HistoryInterface n) {
		super(SHOWHIST_ACTION, KeyEvent.VK_F11, Event.CTRL_MASK);
		setHistoryInterface(n);
		tab = dap;
	}
	public void setHistoryInterface(HistoryInterface nt){
		hist = nt;
	    setEnabled(true);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		return hist!=null && hist.enableHistory(); 
	}

	public void dereference(){
		super.dereference();
		hist=null;
	}
	public void actionPerformed(ActionEvent e) {
		EntityItem item = hist.getHistoryEntityItem();
		if(item==null){
			return;
		}
		tab.disableActionsAndWait();
		//make sure one isnt already open
		com.ibm.eacm.ui.HistoryFrame bmd = EACM.getEACM().getHistoryFrame();
		if (bmd==null){
			worker = new HistWorker(item);
			RMIMgr.getRmiMgr().execute(worker);
		}else{
			String eikey = item.getKey();
			if(eikey.equals(bmd.getHistoryKey())){
		   		if(bmd.getState()==com.ibm.eacm.ui.HistoryFrame.ICONIFIED){
	    			bmd.setState(com.ibm.eacm.ui.HistoryFrame.NORMAL);
	    		}
				bmd.toFront();
				tab.enableActionsAndRestore();	
			}else{
				worker = new HistWorker(item);
				RMIMgr.getRmiMgr().execute(worker);
			}
		}
	} 

	private class HistWorker extends DBSwingWorker<ChangeHistoryGroup, Void> { 
		private EntityItem item;
		HistWorker(EntityItem ei){
			item = ei;
		}
		@Override
		public ChangeHistoryGroup doInBackground() {
			ChangeHistoryGroup mfm = null;
			try{
				mfm = getChangeHistory(item);
			}catch(Exception x){
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",x);
			}finally{
				RMIMgr.getRmiMgr().complete(this);
				worker = null; 
			}
			return mfm;
		}

		@Override
		public void done() {
			//this will be on the event dispatch thread
			try {
				if(!isCancelled()){
					ChangeHistoryGroup mfm = get();
					if (mfm != null){      	
						//make sure one isnt already open
						com.ibm.eacm.ui.HistoryFrame bmd = EACM.getEACM().getHistoryFrame();
						if(bmd==null){
							bmd = new com.ibm.eacm.ui.HistoryFrame(mfm);
							bmd.setVisible(true);
						}else{
							bmd.updateHistory(mfm);
					   		if(bmd.getState()==com.ibm.eacm.ui.HistoryFrame.ICONIFIED){
				    			bmd.setState(com.ibm.eacm.ui.HistoryFrame.NORMAL);
				    		}
							bmd.toFront();
						}
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting ChangeHistoryGroup");
			}finally{
				tab.enableActionsAndRestore();
				item = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
			tab.enableActionsAndRestore();
			worker = null; 
		}
	}

	private ChangeHistoryGroup getChangeHistory(EntityItem item) {
		ChangeHistoryGroup out = null;
		try {
			out =  item.getChangeHistoryGroup(ro());
		}catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						out =  item.getChangeHistoryGroup(ro());
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(tab, e) == RETRY) {
								return getChangeHistory(item);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(tab,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(tab,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(tab, exc) == RETRY) {
						return getChangeHistory(item);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(tab,exc, "mw.err-title");
				}
			} 
		}

		return out;
	}	
}