//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.nav.PDGInfoDialog;
import com.ibm.eacm.tabs.NavController;
import com.ibm.eacm.tabs.TabPanel;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/******************************************************************************
* This is used for the table in the pdg editor
* @author Wendy Stimpson
*/
// $Log: PDGEditTable.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//

public class PDGEditTable extends VertTable {
	private static final long serialVersionUID = 1L;
	private EntityItem pdgitem = null;

	/**
     * getParentInformationAtLevel
     *
     * @param i
     * @return
     */
    public static Object[] getParentInformationAtLevel(int i) {
    	TabPanel eTab =  EACM.getEACM().getCurrentTab();
        if (eTab instanceof NavController) {
			return ((NavController)eTab).getParentInformationAtLevel(i);
		}
		return null;
	}
    
    /**
     * constructor
     */
    public PDGEditTable(Profile prof,EntityItem item) {
        super(null,item);
        pdgitem = item;
    }
  
    public boolean editCellAt(int r, int c, EventObject e) {
    	boolean b = super.editCellAt(r, c, e); 
    	EANAttribute att = getAttribute(r, c);
    	EANMetaAttribute meta = att.getMetaAttribute();
    	if (meta.isPDGINFO()) { // this is completely untested, cant find any meta for it
    		getPDGInfo(meta);
    	}
  
    	return b;
    }
    private void getPDGInfo(EANMetaAttribute meta){
    	EANActionItem ean = pdgitem.getEntityGroup().getEntityList().getParentActionItem();
    	if(ean instanceof PDGActionItem){
    		PDGActionItem pdgai = (PDGActionItem)ean;
    		pdgai.setEntityItem(pdgitem);
    		
			if (pdgai.isCollectParentNavInfo()) {
				try {
					Object[] ao = getParentInformationAtLevel(pdgai.getParentNavInfoLevel());
					if (ao.length == 2) {
						String strEntityType = (String)ao[0];
						int[] aiEntityID = (int[])ao[1];
						EntityItem[] aei = new EntityItem[aiEntityID.length];
						for (int i=0; i < aiEntityID.length; i++) {
							int iEntityID = aiEntityID[i];
							EntityItem ei = new EntityItem(null, pdgitem.getProfile(), strEntityType, iEntityID);
							aei[i] = ei;
						}
						pdgai.setParentNavInfo(aei);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				CollectWorker worker = new CollectWorker(pdgai,meta);
				RMIMgr.getRmiMgr().execute(worker);
			}
    	}
    }
    private class CollectWorker extends DBSwingWorker<PDGCollectInfoList, Void> { 
    	private PDGActionItem pdgai;
    	EANMetaAttribute meta;
    	CollectWorker(PDGActionItem p,EANMetaAttribute m){
    		pdgai = p;
    		meta = m;
    	}
		@Override
		public PDGCollectInfoList doInBackground() {
			PDGCollectInfoList cl = null;
			try {
				cl = pdgai.collectInfo(null, RMIMgr.getRmiMgr().getRemoteDatabaseInterface(), pdgitem.getProfile(), meta);
			}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
				com.ibm.eacm.ui.UI.showErrorMessage(PDGEditTable.this,ex.toString());
			}finally{
				RMIMgr.getRmiMgr().complete(this); 
			}
			return cl;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					PDGCollectInfoList cl = get();
					if (cl !=null){
						PDGInfoDialog pid = new PDGInfoDialog(cl, pdgai,	
								pdgitem.getEntityItemTable(),meta);
						
						pid.setVisible(true);
						
						//update the table model
						updateModel(pdgitem); 
						resizeCells();
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting pdgcollection");
			}finally{
			}
		}
		public void notExecuted(){
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
		}
	}
    
    public void dereference() {
    	super.dereference();
    	pdgitem = null;
    }

    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() { 
         return "PDG";
    }
}

