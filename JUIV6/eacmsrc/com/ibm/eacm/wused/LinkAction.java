//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WhereUsedItem;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.ui.UI;

/*********************************************************************
 * This is used for link request from WUPickFrame
 * moved to WUsedActionTab because if pickframe is closed during link it gets all messed up
 * @author Wendy Stimpson
 */
//$Log: LinkAction.java,v $
//Revision 1.3  2013/09/16 17:31:00  wendy
//maintain up and down links for relator info
//
//Revision 1.2  2012/12/03 19:40:56  wendy
//fix typo
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class LinkAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wusedTab=null;
	private WUPickFrame pickFrame=null;
    private Hashtable<String,String> infoTbl = new Hashtable<String, String>();

	public LinkAction(WUsedActionTab n) {
		super(LINK_ACTION);
		wusedTab = n;
	}
	public void dereference(){
		super.dereference();
		infoTbl.clear();
		infoTbl = null;
		wusedTab=null;
		pickFrame=null;
	}

	public void actionPerformed(ActionEvent e) {}

    protected void link(EntityItem[] dataItems){
    	worker = new LinkWorker(dataItems);
		RMIMgr.getRmiMgr().execute(worker);
    }
    protected void setPickFrame(WUPickFrame pf){
      	pickFrame = pf;
    }

	private class LinkWorker extends HeavyWorker<EANFoundation[], Void> {
		private EntityItem[] dataItems = null; //these are the items to link

		LinkWorker(EntityItem[] d){
			// make a clone of these dataitems incase user closes the pickframe
			dataItems = new EntityItem[d.length];
			for (int i = 0; i < d.length; ++i) {
				try {
					EntityItem data = d[i];
					infoTbl.put(data.getKey(), data.toString()); // get it here so relators wont have newly linked info appended
					dataItems[i] = new EntityItem(data); // pdhdomain is never checked so this is ok to do here
					if(data.getEntityGroup().isRelator()){
						// restore any up and down links so wu table has correct info
						Vector<?> links = data.getUpLink();
						for(int x=0;x<links.size();x++){
							EntityItem linkeditem = (EntityItem)links.elementAt(x);
							dataItems[i].putUpLink(new EntityItem(linkeditem));
						}
						links = data.getDownLink();
						for(int x=0;x<links.size();x++){
							EntityItem linkeditem = (EntityItem)links.elementAt(x);
							dataItems[i].putDownLink(new EntityItem(linkeditem));
						}
					}
				} catch (MiddlewareRequestException _mre) {
					_mre.printStackTrace();
				}
			}
		}
		@Override
		public EANFoundation[] doInBackground() {
			EANFoundation[] added = null;
			if(derefCaller){ // user closed the tab before this could run, dont run at all
				return null;
			}
			try{
				added = wusedTab.dolink(dataItems,pickFrame);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				RMIMgr.getRmiMgr().complete(this);
			 	if(isCancelled() && derefCaller){
					wusedTab.derefWorkerData();
			 	}
			 	dataItems = null;
				worker = null;
			}
			return added;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread if not cancelled before
			try {
				if(!isCancelled()){
					EANFoundation[] added = get();
					if (added!= null){ // null is an exception someplace
						if(added.length>0) {
							wusedTab.updateTable(added[0]); // notify listeners and select first added item
							UI.showLinkResults(pickFrame,reportLink((WhereUsedItem[])added));
						}else{
							//
						    String[] sArray = EACMProperties.getLinkTypes();
						    String linkoption = Utils.getResource(sArray[BehaviorPref.getLinkType()]);
							//msg23007.3 = No items linked for {0}.
							UI.showFYI(pickFrame, Utils.getResource("msg23007.3",linkoption));
						}
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"linking items");
			}finally{
				if (pickFrame!=null){ // pick frame is still open
					pickFrame.enableActionsAndRestore();
				}else {
					if(derefCaller){ // user closed the tab
						if(wusedTab!=null){
							wusedTab.derefWorkerData();
						}
					}else{
						wusedTab.enableActionsAndRestore();//pickframe was closed
					}
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
			if (pickFrame!=null){
				pickFrame.enableActionsAndRestore();
			}else{
				wusedTab.enableActionsAndRestore();//pickframe was closed
			}
			worker = null;
		}
	}

    private String reportLink(WhereUsedItem[] wuItems) {
        StringBuffer sb = new StringBuffer();
        String msg = " " + Utils.getResource("linked2") + " ";

        for(int i=0; i<wuItems.length; i++){
        	WhereUsedItem wui = wuItems[i];
    //    	System.err.println("reportlink "+wui.getKey()+" wui.getWhereUsedGroup().isParent() "+wui.getWhereUsedGroup().isParent());
        	EntityItem parent = wui.getOriginalEntityItem();
        	EntityItem child = wui.getRelatedEntityItem();
        	if(wui.getWhereUsedGroup().isParent()){ // swap them for msg
        		child = wui.getOriginalEntityItem();
        		parent = wui.getRelatedEntityItem();
        	}
        	EntityItem relator = wui.getRelatorEntityItem(); 
        	String parentinfo = infoTbl.get(parent.getKey());
        	if(parentinfo==null){
        		parentinfo = parent.toString();
        	}
        	String childinfo = infoTbl.get(child.getKey());
        	if(childinfo==null){
        		childinfo = child.toString();
        	}
        	
        	sb.append(parent.getEntityGroup().getLongDescription()+" "+parentinfo+
        			msg+
        			child.getEntityGroup().getLongDescription()+" "+childinfo+NEWLINE);
        	Logger.getLogger(WU_PKG_NAME).log(Level.INFO,"Linked "+parent.getKey()+" thru "+relator.getKey()+" to "+child.getKey());
        }

        return sb.toString();
    }

}