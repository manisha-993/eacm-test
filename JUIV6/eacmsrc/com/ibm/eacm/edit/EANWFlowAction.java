//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WorkflowActionItem;
/**
 * this is used for WorkFlow from edit
 *completely untested - not sure this is valid
 *@author Wendy Stimpson
 */
// $Log: EANWFlowAction.java,v $
// Revision 1.2  2014/10/20 19:56:06  wendy
// Add worker id to timing log output
//
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
public class EANWFlowAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private EditController curEditor=null;
	public EANWFlowAction(EANActionItem act,EditController n,EANActionSet p) {
		super(act,ACTION_PURPOSE_WORK_FLOW,p);
		curEditor = n;
	}

	public void dereference(){
		super.dereference();
		curEditor = null;
	}
	
	public void actionPerformed(ActionEvent e) {
		EntityItem[] ei= curEditor.getSelectedEntityItems();
		if (ei != null) {
			if (containsNew(ei)) {
				//msg3007Flow = Workflow activity not permitted until entity(s) have been saved.
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg3007Flow"));
				return;
			}
			//outputDebug(ei);

			((WorkflowActionItem)getEANActionItem()).setEntityItems(ei);

			curEditor.disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new WFWorker(); 
			RMIMgr.getRmiMgr().execute(worker);	
		}
	}

    private boolean containsNew(EntityItem[] _ei) {
        for (int i = 0; i < _ei.length; ++i) {
            if (_ei[i].isNew()) {
                return true;
            }
        }
        return false;
    }
    
	private class WFWorker extends DBSwingWorker<Boolean, Void> { 
		private long t11 = 0L;

		@Override
		public Boolean doInBackground() {
			Boolean bool = new Boolean(false);
			try{	
				boolean isok = false;
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				isok = DBUtils.execWorkflow((WorkflowActionItem)getEANActionItem(),curEditor.getProfile(),curEditor);
				if (isok){
					curEditor.refreshEditor();
				}
				bool = new Boolean(isok);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}
			return bool;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
			curEditor.enableActionsAndRestore();	
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			curEditor.enableActionsAndRestore();
			worker = null; 
		}
	}
}
