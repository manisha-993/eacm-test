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

import COM.ibm.eannounce.objects.EntityList;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for reset navigation request from navigate
 * @author Wendy Stimpson
 */
//$Log: ResetAction.java,v $
//Revision 1.2  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class ResetAction extends EACMAction
{
	private Navigate nav=null;
	private static final long serialVersionUID = 1L;
	public ResetAction(Navigate n) {
		super(RESET_ACTION);
		nav=n;
	}

	public void actionPerformed(ActionEvent e) {
    	//msg0008 = Reset History?
        int reply =  com.ibm.eacm.ui.UI.showConfirmOkCancel(nav, Utils.getResource("msg0008"));
        if (reply == OK_BUTTON){
        	if (nav.isPin()){
        		// remove the pin so history can be cleared
        		nav.getAction(PIN_ACTION).actionPerformed(null);
        	}

            nav.disableActionsAndWait(); //disable all other actions and also set wait cursor
    
    		worker = new LoadWorker();	        
    		RMIMgr.getRmiMgr().execute(worker);
        }
	}

	private class LoadWorker extends DBSwingWorker<EntityList, Void> { 
		private long t11 = 0L;

		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				list = DBUtils.getNavigateEntry(nav.getProfile());
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
						list.getProfile().setReadLanguage(nav.getProfile().getReadLanguage());
				        nav.resetHistory();  // this will deref lists, so do it right before adding new list
			            nav.finishAction(list, Navigate.NAVIGATE_RESET);
			            nav.getNavController().resetBookmark();
			        	EACM.getEACM().setIconAt(nav.getNavController(), nav.getNavController().getTabIcon());
			        	EACM.getEACM().setTitleAt(nav.getNavController(), 
			        			nav.getTabTitle());
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entry entity list");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				nav.enableActionsAndRestore();	
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			nav.enableActionsAndRestore();
			worker = null; 
		}
	}
}