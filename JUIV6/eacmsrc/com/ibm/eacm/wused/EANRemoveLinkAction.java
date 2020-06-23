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

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;

import COM.ibm.eannounce.objects.EANActionItem;
/**
 * this is used for WhereUsed remove link
 * @author Wendy Stimpson
 */
// $Log: EANRemoveLinkAction.java,v $
// Revision 1.3  2013/10/24 16:59:01  wendy
// add more logging
//
// Revision 1.2  2012/12/03 19:40:56  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANRemoveLinkAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	public EANRemoveLinkAction(EANActionItem act,WUsedActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_DELETE,p);
		wuAction = n;
	}

	public void actionPerformed(ActionEvent e) {
    	wuAction.disableActionsAndWait();

        String [] validKeys = wuAction.getKeysToRemove(this.getPurpose());

        if(validKeys!=null){
        	outputDebug(null);
        	worker = new RemoveLinkWorker(validKeys);
        	RMIMgr.getRmiMgr().execute(worker);
        	validKeys = null;
        }else{
        	wuAction.enableActionsAndRestore();
        }
	}

	public void dereference(){
		super.dereference();
		wuAction = null;
	}

    //======================================================================================
    private class RemoveLinkWorker extends HeavyWorker<Boolean, Void> {
    	private  String [] validKeys = null;
    	RemoveLinkWorker(String[] vk){
    		validKeys = vk;
    	}
    	@Override
    	public Boolean doInBackground() {
    		Boolean ok = false;
    		if(derefCaller){ // user closed the tab before this could run, dont run at all
				return null;
			}
    		try{
    			ok = wuAction.removeLinks(validKeys);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
        	 	if(isCancelled() && derefCaller){
        	 		wuAction.derefWorkerData();
			 	}
            	worker = null;
            	validKeys = null;
    		}

			return ok;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
           		if(!isCancelled()){
           			Boolean b = get();
           			if (b){
           				wuAction.updateTable();
           			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"removing link");
            }finally{
            	if(derefCaller){ // user closed the tab
            		if(wuAction!=null){
            			wuAction.derefWorkerData();
            		}
            	}else{
            		wuAction.enableActionsAndRestore();
            	}
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
     		wuAction.enableActionsAndRestore();
     		worker = null;
    	}
    }
}
