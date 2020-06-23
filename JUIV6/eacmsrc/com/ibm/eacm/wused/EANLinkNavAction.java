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
import com.ibm.eacm.objects.*;
import com.ibm.eacm.ui.PickFrame;

import COM.ibm.eannounce.objects.*;
/**
 * this is used for WhereUsed link with a navaction picklist
 * @author Wendy Stimpson
 */
// $Log: EANLinkNavAction.java,v $
// Revision 1.4  2013/10/24 16:59:01  wendy
// add more logging
//
// Revision 1.3  2013/09/16 17:27:19  wendy
// show frame if iconified
//
// Revision 1.2  2012/12/03 19:40:56  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANLinkNavAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	public EANLinkNavAction(EANActionItem act,WUsedActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_NAVIGATE,p);
		wuAction = n;
	}

	public void actionPerformed(ActionEvent e) {
		PickFrame pf = EACM.getEACM().getPickFrame(wuAction.getUID()+":"+getEANActionItem().getActionItemKey());
		if (pf==null){
			outputDebug(null);
		   	wuAction.disableActionsAndWait();
			worker = new ListWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}else{
			if(pf.getState()==PickFrame.ICONIFIED){
    			pf.setState(PickFrame.NORMAL);
    		}

			pf.setVisible(true);
			pf.toFront();
			getParentActionSet().setEnabled(true);  // reenable this parent action only
		}
	}
	public void dereference(){
		super.dereference();
		wuAction = null;
	}
    private class ListWorker extends DBSwingWorker<EntityList, Void> {
    	@Override
    	public EntityList doInBackground() {
    		EntityList list=null;
    		try{
    			list = wuAction.getPicklist();
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(worker);
            	worker = null;
				if(isCancelled()){
					wuAction.enableActionsAndRestore();
				}
    		}
			return list;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			EntityList list = get(); // may be null
        	    	if (list == null) {
        	    		//msg23007.1 = No picklist available for this entity.
        	    		com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.1"));
        	    		wuAction.enableActionsAndRestore();
        	    	} else if (!hasData(list)) {
        	    		//msg23007.0 = No rows found for selected picklist.
        	    		com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.0"));
        	    		wuAction.enableActionsAndRestore();
        	    	}else{
        	    		wuAction.showPicklist(list);
        	    		getParentActionSet().setEnabled(true);  // reenable this parent action only
        	    	}
        		}else{
        			wuAction.enableActionsAndRestore();
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting picklist");
            	wuAction.enableActionsAndRestore();
            }finally{
            	//wuAction.enableActionsAndRestore(); picklist is open, dont restore
        		worker = null;
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
     		wuAction.enableActionsAndRestore();
     		worker = null;
    	}
    }
}
