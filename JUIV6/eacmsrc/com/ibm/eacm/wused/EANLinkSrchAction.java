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

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.ui.PickFrame;
import com.ibm.eacm.ui.SearchFrame;

import COM.ibm.eannounce.objects.*;

/**
 * this is used for WhereUsed link with a search action
 * @author Wendy Stimpson
 */
// $Log: EANLinkSrchAction.java,v $
// Revision 1.5  2014/10/20 19:56:06  wendy
// Add worker id to timing log output
//
// Revision 1.4  2013/10/24 16:59:01  wendy
// add more logging
//
// Revision 1.3  2013/09/16 17:24:43  wendy
// show frame if iconified
//
// Revision 1.2  2012/12/03 19:40:56  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANLinkSrchAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	public EANLinkSrchAction(EANActionItem act,WUsedActionTab n, EANActionSet p) {
		super(act,ACTION_PURPOSE_SEARCH,p);
		wuAction = n;
	}

	public void dereference(){
		super.dereference();
		wuAction = null;
	}

	//=========================================================
    /**
     * called from link button or menu item
     * @param _search
     */
	public void actionPerformed(ActionEvent e) {
    	SearchActionItem sai = (SearchActionItem)getEANActionItem();

    	outputDebug(null);
    	
        if (sai.isGrabByEntityID()) {
        	wuAction.disableActionsAndWait();

            int iID = com.ibm.eacm.ui.UI.showIntInput(null,Utils.getResource("searchID"));
            if (iID >= 0) {
                //  not tested at all
                sai.setGrabEntityID(iID);
                worker = new SearchWorker2();
              	RMIMgr.getRmiMgr().execute(worker);

            }else{
            	wuAction.enableActionsAndRestore();
            }
        } else {
    		//make sure one isnt already open
        	SearchFrame sf = EACM.getEACM().getSearchFrame(wuAction.getUID()+":"+sai.getActionItemKey());
        	if (sf==null){
        		// are any other actions in this set open?
            	SearchFrame other = getParentActionSet().otherSearchFrameOpen(wuAction.getUID(), sai.getActionItemKey());
        		if(other !=null){
        		    UIManager.getLookAndFeel().provideErrorFeedback(null);
               		if(other.getState()==SearchFrame.ICONIFIED){
            			other.setState(SearchFrame.NORMAL);
            		}
            		other.toFront();
            		//wuAction.enableActionsAndRestore();
        			return;
        		}

        		// look for pickframe now
        		PickFrame wupf = EACM.getEACM().getPickFrame(wuAction.getUID()+":"+sai.getActionItemKey());
        		if (wupf==null){
                	wuAction.disableActionsAndWait();
        			if (sai.isDynaSearchEnabled()) {
        				worker = new DynaSrchWorker();
        				RMIMgr.getRmiMgr().execute(worker);
        			}else{
        				// this causes this to be executed on the event dispatch thread
        				// after actionPerformed returns - actions weren't disabling until this was visible
        				SwingUtilities.invokeLater(new Runnable() {
        					public void run() {
        						SearchFrame sf = new SearchFrame((SearchActionItem) getEANActionItem(), null,wuAction);
        						sf.setVisible(true);
        						getParentActionSet().setEnabled(true);  // reenable this parent action only
        					}
        				});
        			}
        		}else{
        			if(wupf.getState()==PickFrame.ICONIFIED){
            			wupf.setState(PickFrame.NORMAL);
            		}

        			wupf.toFront();
        			getParentActionSet().setEnabled(true);  // reenable this parent action only
        		}
        	}else{
        		if(sf.getState()==SearchFrame.ICONIFIED){
        			sf.setState(SearchFrame.NORMAL);
        		}

        		sf.setVisible(true);
        		sf.toFront();
        		getParentActionSet().setEnabled(true);  // reenable this parent action only
        	}
        }
    }

	private class DynaSrchWorker extends DBSwingWorker<RowSelectableTable, Void> {
		private long t11 = 0L;

		@Override
		public RowSelectableTable doInBackground() {
			RowSelectableTable rst = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				// make sure same language is used
				getEANActionItem().getProfile().setReadLanguage(wuAction.getProfile().getReadLanguage());

				rst = ((SearchActionItem) getEANActionItem()).getDynaSearchTable(ro());
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
				if(isCancelled()){
					wuAction.enableActionsAndRestore();
				}
			}

			return rst;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					RowSelectableTable rst = get();
					if (rst !=null){
						SearchFrame sf = new SearchFrame((SearchActionItem) getEANActionItem(), rst, wuAction);
						sf.setVisible(true);
           				getParentActionSet().setEnabled(true);  // reenable this parent action only
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting search rst");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
			}
		}
		public void notExecuted(){
			Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			wuAction.enableActionsAndRestore();
			worker = null;
		}
	}
    private class SearchWorker2 extends DBSwingWorker<EntityList, Void> {
    	@Override
    	public EntityList doInBackground() {
    		EntityList list = null;
    		try{
    			  //  not tested at all
                list = DBUtils.doSearch((SearchActionItem)getEANActionItem(),wuAction.getProfile(), wuAction);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
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
           			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting entitylist for picklist");
            }finally{
        		if(isCancelled()){
					wuAction.enableActionsAndRestore();
				}
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
     		wuAction.enableActionsAndRestore();
     		worker = null;
    	}
    }

}
