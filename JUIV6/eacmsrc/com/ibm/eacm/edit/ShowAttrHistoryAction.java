//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.ChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANAttribute;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;


/**
 * show history for an attribute
 * @author Wendy Stimpson
 *
 */
// $Log: ShowAttrHistoryAction.java,v $
// Revision 1.2  2013/09/13 18:29:16  wendy
// show frame if iconified
//
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
public class ShowAttrHistoryAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl=null;

	public ShowAttrHistoryAction(EditController edc) {
		super(SHOWATTRHIST_ACTION, KeyEvent.VK_F11, Event.CTRL_MASK);
		editCtrl = edc;
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		return editCtrl.getSelectedAttribute()!=null; 
	}

	public void dereference(){
		super.dereference();
		editCtrl=null;
	}
	public void actionPerformed(ActionEvent e) {
		EANAttribute attr = editCtrl.getSelectedAttribute();
		editCtrl.disableActionsAndWait();
		//make sure one isnt already open
		com.ibm.eacm.ui.HistoryFrame bmd = EACM.getEACM().getHistoryFrame();
		if (bmd==null){
			worker = new HistWorker(attr);
			RMIMgr.getRmiMgr().execute(worker);
		}else{
			String eikey = attr.getParent().getKey()+attr.getKey();
			if(eikey.equals(bmd.getHistoryKey())){
		   		if(bmd.getState()==com.ibm.eacm.ui.HistoryFrame.ICONIFIED){
	    			bmd.setState(com.ibm.eacm.ui.HistoryFrame.NORMAL);
	    		}
		   		bmd.toFront();
				editCtrl.enableActionsAndRestore();	
			}else{
				worker = new HistWorker(attr);
				RMIMgr.getRmiMgr().execute(worker);
			}
		}
	} 

	private class HistWorker extends DBSwingWorker<ChangeHistoryGroup, Void> { 
		private EANAttribute attr;
		HistWorker(EANAttribute a){
			attr = a;
		}
		@Override
		public ChangeHistoryGroup doInBackground() {
			ChangeHistoryGroup mfm = null;
			try{
				mfm = getChangeHistory(attr);
			}catch(Exception x){
				Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"Exception",x);
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
							bmd.setHistoryKey(attr.getParent().getKey()+attr.getKey());
							bmd.setVisible(true);
						}else{
							bmd.updateHistory(mfm);
							bmd.setHistoryKey(attr.getParent().getKey()+attr.getKey());
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
				editCtrl.enableActionsAndRestore();
				attr = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"not executed");
			editCtrl.enableActionsAndRestore();
			worker = null; 
		}
	}

	private ChangeHistoryGroup getChangeHistory(EANAttribute attr) {
		ChangeHistoryGroup out = null;
		try {
			 out = ro().getAttributeChangeHistoryGroup(editCtrl.getProfile(), attr);
		}catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						 out = ro().getAttributeChangeHistoryGroup(editCtrl.getProfile(), attr);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(editCtrl, e) == RETRY) {
								return getChangeHistory(attr);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(editCtrl,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(editCtrl,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(editCtrl, exc) == RETRY) {
						return getChangeHistory(attr);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(editCtrl,exc, "mw.err-title");
				}
			} 
		}

		return out;
	}	
}