//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.SearchActionItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.ui.PickFrame;
import com.ibm.eacm.ui.SearchFrame;

/*********************************************************************
 * this displays a pickframe and selected items are added as columns to the matrix table
 * not completely tested, no searchactions
 * @author Wendy Stimpson
 */
//$Log: AddColAction.java,v $
//Revision 1.3  2013/10/18 12:12:19  wendy
//check for searchframe and put to front if it exists
//
//Revision 1.2  2013/09/13 18:18:36  wendy
//show frame if iconified
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class AddColAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	
	private CrossTable crssTable = null;
	private MatrixActionBase actionTab = null;
	private String listactionkey = "";
	
	public AddColAction(CrossTable _tab,MatrixActionBase mab) { 
		super(MTRX_ADDCOL,KeyEvent.VK_L, Event.CTRL_MASK + Event.SHIFT_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("addcol.gif"));
		setTable(_tab);
		actionTab = mab;
	}
	
	public void setTable(CrossTable nt){
		crssTable = nt;
		// enable if it has picklist available?
	    setEnabled(true);
	}
	
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && crssTable!=null && !Utils.isPast(crssTable.getProfile()));
	}

	public void dereference(){
		super.dereference();
		crssTable = null;
		actionTab = null;
	}
	public void actionPerformed(ActionEvent e) {
		crssTable.cancelCurrentEdit();

		// look for searchframe now  
		SearchFrame sf = EACM.getEACM().getSearchFrame(actionTab.getUID()+":"+listactionkey);
		if(sf !=null){
	   		if(sf.getState()==SearchFrame.ICONIFIED){
    			sf.setState(SearchFrame.NORMAL);
    		}
			sf.toFront();
			setEnabled(true);  // reenable this action only 
			return;
		}
		
		// look for pickframe now  
		PickFrame pf = EACM.getEACM().getPickFrame(actionTab.getUID()+":"+listactionkey);
		if (pf==null){
			actionTab.disableActionsAndWait();
			worker = new ColWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}else{
	   		if(pf.getState()==PickFrame.ICONIFIED){
    			pf.setState(PickFrame.NORMAL);
    		}
			pf.toFront();
			setEnabled(true);  // reenable this action only 
		}
	}

	/**
	 * this class is used to allow the dispatch thread to repaint and then run this, wait cursor is displayed this way
	 *
	 */
    private class ColWorker extends DBSwingWorker<EntityList, Void> { 
    	private SearchFrame sf = null;
    	private RowSelectableTable rst = null;
    	private SearchActionItem sai = null;
    	@Override
    	public EntityList doInBackground() {
    		EntityList list=null;
	
    		try{
    			EANActionItem[] act = actionTab.getActionItemsAsArray(crssTable);
    			boolean bPickProcessed = false;
    			if (act != null) {
    				for (int i = 0; i < act.length; ++i) {
    					if (act[i] instanceof SearchActionItem) {// not tested at all
    						sai = (SearchActionItem) act[i];
    						// make sure same language is used
    						sai.getProfile().setReadLanguage(actionTab.getProfile().getReadLanguage());
    			
    						if (sai.isGrabByEntityID()) {
    							int iID = com.ibm.eacm.ui.UI.showIntInput(actionTab,Utils.getResource("searchID"));
    							if (iID >= 0) {
    								sai.setGrabEntityID(iID);
    								list = DBUtils.doSearch(sai,actionTab.getProfile(),actionTab); 
    							}
    						} else {
    							if (sai.isDynaSearchEnabled()) { 
    								rst = sai.getDynaSearchTable(ro()); 
    							}else{
    								sf = new SearchFrame(sai, actionTab,null, crssTable); 
    							}
    						}
    						bPickProcessed = true;
    						break;
    					} else if (act[i] instanceof NavActionItem) {
    						list = actionTab.getPicklistNavigate(crssTable);
    						bPickProcessed = true;
    						break;
    					}
    				}
    			}
    			if (!bPickProcessed) {
    				list = actionTab.getPicklistNavigate(crssTable);
    			}
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(MTRX_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(worker);
            	worker = null; 
    		}
			return list;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			EntityList list = get(); // may be null 
        			if (rst!=null) { // no search testing done
						sf = new SearchFrame(sai, actionTab,rst, crssTable); 
        				sf.setVisible(true); 
        				listactionkey=sai.getActionItemKey();
        				setEnabled(true);  // reenable this action only so searchframe can be moved tofront if open
					}else if(sf!=null){
        				sf.setVisible(true); 
        				listactionkey=sai.getActionItemKey();
        				setEnabled(true);  // reenable this action only so searchframe can be moved tofront if open
        			}else{
        				if(list!=null){
        					listactionkey = list.getParentActionItem().getActionItemKey();
        					if (!hasData(list)) {
            					//msg23007.0=No rows found for selected picklist.
            					com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.0"));
            					list.dereference();
            					list = null;
            				}
        				}else{
        					listactionkey="";
        					//msg23007.1 = No picklist available for this entity.
        					com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.1"));
        				}
        				if (list != null) {
        					actionTab.showPicklist(crssTable,list);
        				}else{
        					actionTab.enableActionsAndRestore();
        				}
        			
        				setEnabled(true);  // reenable this action only so picklist can be moved tofront if open
        			}
        		}else{
        			//user cancelled, restore actions
        			actionTab.enableActionsAndRestore();
        			listactionkey="";
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"adding Column dialog");
            	actionTab.enableActionsAndRestore();
            	listactionkey="";
            }finally{
            	//actionTab.enableActionsAndRestore(); picklist is open, dont restore
        		worker = null; 
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,"not executed");
     		actionTab.enableActionsAndRestore();
     		worker = null; 
    	}
    }
}