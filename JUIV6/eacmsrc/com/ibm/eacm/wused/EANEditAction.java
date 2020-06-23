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

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EditActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;

/**
 * this is used for WhereUsed edit action
 * @author Wendy Stimpson
 */
// $Log: EANEditAction.java,v $
// Revision 1.3  2013/10/24 16:59:01  wendy
// add more logging
//
// Revision 1.2  2012/12/03 19:40:56  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANEditAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	private StringBuffer sb = new StringBuffer();

	public EANEditAction(EANActionItem act,WUsedActionTab n,EANActionSet p) {
		super(act,ACTION_PURPOSE_EDIT,p);
		wuAction = n;
	}
	public void dereference(){
		super.dereference();
		wuAction = null;
		sb = null;
	}
	public void actionPerformed(ActionEvent e) {
    	wuAction.disableActionsAndWait();
    	boolean editable = false;
    	if(getEANActionItem() instanceof EditActionItem){
    		editable=((EditActionItem)getEANActionItem()).canEdit();
    	}

    	int[] rows = wuAction.getEditRowIndexes(editable);

    	if(rows!=null){
    		//is there a tab open already for this action and items?
    		EntityItem[] ei = wuAction.getRelatedItemForMdlIndexes(rows);
    		if (!EACM.getEACM().tabExists(wuAction.getProfile(), ei, getEANActionItem())) {
    			outputDebug(ei);
    			worker = new EditWorker(rows);
    			RMIMgr.getRmiMgr().execute(worker);
    		}else{
            	wuAction.enableActionsAndRestore();
            }
        	rows = null;
        }else{
        	wuAction.enableActionsAndRestore();
        }
    }

    private class EditWorker extends DBSwingWorker<EntityList, Void> {
    	private  int [] rows = null;

    	EditWorker(int[] vk){
    		rows = vk;
    	}
    	@Override
    	public EntityList doInBackground() {
    		EntityList list = null;
    		try{
    			list = getEntityList(rows);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
            	worker = null;
            	rows = null;
    		}

			return list;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
            	if(!isCancelled()){
            		EntityList list = get();
            		if (sb.length()>0){ //RQ0713072645 must do check in whereusedlist to access items and action
            			com.ibm.eacm.ui.UI.showErrorMessage(null,sb.toString());
            		} //end RQ0713072645
            		if(list!=null){
            			EditController ec = new EditController(list, null);
            			ec.setParentProfile(wuAction.getProfile());
            			//ec.setSelectorEnabled(true);
            			ec.setParentTab(wuAction);
            			EACM.getEACM().addTab(wuAction, ec);
            		}
            	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting entitylist for edit");
            }finally{
            	wuAction.enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
     		wuAction.enableActionsAndRestore();
     		worker = null;
    	}
    }

    private EntityList getEntityList(int[] rows) {
    	EntityList list = null;
    	try {
    		list = wuAction.getRSTable().edit(rows, null, ro(),wuAction.getProfile(), sb);
    	}  catch (Exception exc) {
    		if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
    				RMIMgr.getRmiMgr().reconnectMain()) {
    			try {
    				list = wuAction.getRSTable().edit(rows, null, ro(), wuAction.getProfile(), sb);
    			} catch(Exception ex){
    				com.ibm.eacm.ui.UI.showException(wuAction,ex, "mw.err-title");
    			}
    		} else {
    			com.ibm.eacm.ui.UI.showException(wuAction,exc, "mw.err-title");
    		}
    	}
    	return list;
    }
}
