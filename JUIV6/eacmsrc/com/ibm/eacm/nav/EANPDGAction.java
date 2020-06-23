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
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;

/**
 * this is used for pdg from navigate
 * @author Wendy Stimpson
 */
// $Log: EANPDGAction.java,v $
// Revision 1.3  2014/10/20 19:56:08  wendy
// Add worker id to timing log output
//
// Revision 1.2  2013/07/18 20:06:19  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class EANPDGAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANPDGAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_PDG,p);
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

	        //can only have one
	        if (EACM.getEACM().getEditPDGFrame() !=null) { 
	        	com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),"Open only one PDG dialog at a time.");
	        	EACM.getEACM().getEditPDGFrame().toFront();
	        	return;
	        }

			PDGTemplateActionItem pdgtemp = (PDGTemplateActionItem)getEANActionItem();
			
	        NavActionItem nai = pdgtemp.getPDGNavAction();
	        
	        if(pdgtemp instanceof PDGActionItem){
				PDGActionItem pdgai = (PDGActionItem)getEANActionItem();
	        	for (int i = 0; i < ei.length; i++) { 
	        		EntityItem eitem = ei[i]; 
	        		EntityGroup eg = eitem.getEntityGroup(); 
	        		if (eg.isRelator()||eg.isAssoc()) { //MN32759321 must handle assoc too
	        			ei[i] = (EntityItem) eitem.getDownLink(0); 
	        		}
	        	}
		        if (nai != null) {
		        	getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
		        	worker = new NavWorker(ei);
	    			RMIMgr.getRmiMgr().execute(worker); 
		        } else {
		        	EditActionItem eai = pdgai.getPDGEditAction();
		        	if (eai != null) {
		        		getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
		    			worker = new EditWorker(ei);
		    			RMIMgr.getRmiMgr().execute(worker);  
		        	}
		        }
	        }else{	
	            if (nai != null) {
	            	getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
	    			worker = new NavWorker(ei);
	    			RMIMgr.getRmiMgr().execute(worker);  
	            }
	        }   
		}		
	}
	private class EditWorker extends DBSwingWorker<EntityList, Void> { 
		private EntityItem[] eia = null;
		private long t11 = 0L;
		EditWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				list = DBUtils.getEdit(((PDGTemplateActionItem)getEANActionItem()).getPDGEditAction(), eia, getNavigate().getProfile(), getNavigate());
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
				        EntityItem theItem = null;
				        for (int i = 0; i < list.getEntityGroupCount(); ++i) {
				            EntityGroup eg = list.getEntityGroup(i);
				            if (eg.isDisplayable()) {
				                if (eg.getEntityItemCount() > 0) {
				                    EntityItem ei = eg.getEntityItem(0);
				                    theItem = ei;
				                }
				                break;
				            }
				        }
				        if(theItem==null){
				          	com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),"No displayable entity item found.");
				        }else{
				        	EditPDGFrame edf = new EditPDGFrame(((PDGActionItem)getEANActionItem()),list, theItem);
				        	edf.setVisible(true);
				        }
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
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

	private class NavWorker extends DBSwingWorker<EntityList, Void> { 
		private EntityItem[] eia = null;
		private long t11 = 0L;
		NavWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				list = DBUtils.navigate(((PDGTemplateActionItem)getEANActionItem()).getPDGNavAction(), eia, getNavigate().getProfile(), getNavigate());
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
		                PDGDialog pdg = new PDGDialog(list, ((PDGTemplateActionItem)getEANActionItem()));
		                pdg.setVisible(true);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
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
}
