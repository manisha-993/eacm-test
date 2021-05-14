//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.actions.*;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;

/**
 * this is used for workflow from Navigate
 * @author Wendy Stimpson
 */
//$Log: EANWFlowAction.java,v $
//Revision 1.3  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.2  2013/07/18 20:06:18  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANWFlowAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANWFlowAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_WORK_FLOW,p);
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

			((WorkflowActionItem)getEANActionItem()).setEntityItems(ei);

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new WFWorker(ei); 
			RMIMgr.getRmiMgr().execute(worker);	
		}
	}

	private class WFWorker extends DBSwingWorker<Boolean, Void> { 
		private EntityItem[] eia = null;
		private long t11 = 0L;
		WFWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public Boolean doInBackground() {
			Boolean bool = new Boolean(false);
			try{	
				boolean isok = false;
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				isok = DBUtils.execWorkflow((WorkflowActionItem)getEANActionItem(),getNavigate().getProfile(),getNavigate());
				bool = new Boolean(isok);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}
			return bool;
		}

		@Override
		public void done() {
			boolean isrefreshing = false;
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Boolean isok = get();
					if(isok){
						isrefreshing = true;
						getNavigate().enableActionsAndRestore();
						getNavigate().refresh();//runs a worker
						clearAction(eia);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"exec workflow");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				if(!isrefreshing){
					getNavigate().enableActionsAndRestore();
				}
				eia=null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null; 
		}
	}
}
