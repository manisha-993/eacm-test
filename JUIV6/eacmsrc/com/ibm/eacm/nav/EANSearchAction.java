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

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.PickFrame;
import com.ibm.eacm.ui.SearchFrame;


import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;


/**
 * this is used for Search
 *@author Wendy Stimpson
 */
// $Log: EANSearchAction.java,v $
// Revision 1.7  2014/10/20 19:56:07  wendy
// Add worker id to timing log output
//
// Revision 1.6  2013/09/25 11:10:40  wendy
// look for existing picklist for the search action
//
// Revision 1.5  2013/09/13 18:17:43  wendy
// show frame if iconified
//
// Revision 1.4  2013/07/18 20:06:19  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.3  2013/03/14 17:30:12  wendy
// enable actions if null rst is returned from search
//
// Revision 1.2  2012/11/09 20:47:58  wendy
// check for null profile from getNewProfileInstance()
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class EANSearchAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANSearchAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_SEARCH,p);
	}

    protected boolean isParentlessSearch() {
         return ((SearchActionItem) getEANActionItem()).isParentLess();
    }

	public void actionPerformed(ActionEvent e) {
		SearchActionItem search = (SearchActionItem) getEANActionItem();
		//make sure one isnt already open
    	SearchFrame sf = EACM.getEACM().getSearchFrame(getNavigate().getUID()+":"+search.getActionItemKey());
    	if (sf!=null){
       		if(sf.getState()==SearchFrame.ICONIFIED){
    			sf.setState(SearchFrame.NORMAL);
    		}
    		sf.toFront();
    		getParentActionSet().setEnabled(true);  // reenable this parent action only
    		return;
    	}
    	
		//make sure picklist isnt already open - uid for NavPickFrame comes from nav.navcontroller
    	PickFrame pf = EACM.getEACM().getPickFrame(getNavigate().getNavController().getUID()+":"+search.getActionItemKey());
    	if (pf!=null){
       		if(pf.getState()==PickFrame.ICONIFIED){
    			pf.setState(PickFrame.NORMAL);
    		}
    		pf.toFront();
    		getParentActionSet().setEnabled(true);  // reenable this parent action only
    		return;
    	}
    	
		// are any other actions in this set open?
    	SearchFrame other = getParentActionSet().otherSearchFrameOpen(getNavigate().getUID(), search.getActionItemKey());
		if(other !=null){
		    UIManager.getLookAndFeel().provideErrorFeedback(null);
       		if(other.getState()==SearchFrame.ICONIFIED){
    			other.setState(SearchFrame.NORMAL);
    		}
    		other.toFront();
			return;
		}

		outputStartInfo();

		EntityItem[] ei = null;
		try{
			ei = getEntityItems(true);
        } catch (OutOfRangeException range) {
        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),range);
        	return;
        }
        if(!checkValidSingleInput(ei)){ // make sure this action has correct number ei selected
        	return;
        }

		// SearchActionItem.setParentEntityItems() removes EntityGroup so cant check for nav attribute in SearchAction
		// check the domains here
		// check edit to output warning msg, stop report here too
		try{
			EntityList.checkDomain(getNavigate().getProfile(),getEANActionItem(),ei); // RQ0713072645
		}catch(DomainException de){
			com.ibm.eacm.ui.UI.showException(getNavigate(),de);
			return;
		}

		if (ei == null && !isParentlessSearch()) {
			// msg11006 = Selected Action could not be completed because no Entity(s) were selected.
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11006"));
			return;
		}

		if (getEANActionItem().useRootEI()) { //root_search
			EntityItem[] pei = getParentEntityItems();
			if(pei==null || pei.length==0){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getEANActionItem().getActionItemKey()+
						" is useRootEI but no parents found!");
				// loggers need this put into separate lines
				String listArray[] = Routines.splitString(Utils.outputList(getNavigate().getCurrentEntityList()), NEWLINE);
				for(int i=0;i<listArray.length; i++){
					Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING, listArray[i]);
				}
			}
			ei = pei;
		}

        outputDebug(ei);

        getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

		if (search.isGrabByEntityID()) {
			//searchID = Please enter Entity ID to Retrieve.
			int iID = com.ibm.eacm.ui.UI.showIntInput(getNavigate(),Utils.getResource("searchID"));
			if (iID >= 0) {
				search.setGrabEntityID(iID);

		        worker = new SrchWorker(ei);
		        RMIMgr.getRmiMgr().execute(worker);
			} else{
				getNavigate().enableActionsAndRestore();
			}
		} else {
			if (ei == null && search.isParentLess()) {
			} else {
				search.setParentEntityItems(ei);
			}

			if (search.isDynaSearchEnabled()) {
				worker = new DynaSrchWorker();
				RMIMgr.getRmiMgr().execute(worker);
			}else{
				// this causes this to be executed on the event dispatch thread
				// after actionPerformed returns - actions weren't disabling until this was visible
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						SearchFrame sf = new SearchFrame((SearchActionItem) getEANActionItem(), null,getNavigate());
						sf.setVisible(true);
						getParentActionSet().setEnabled(true);  // reenable this parent action only
					}
				});
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
				// make sure same language is used
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				((SearchActionItem) getEANActionItem()).verifyReadLanguage(getNavigate().getProfile().getReadLanguage());
				rst = ((SearchActionItem) getEANActionItem()).getDynaSearchTable(ro());
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
				if(isCancelled()){
					getNavigate().enableActionsAndRestore();
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
						SearchFrame sf = new SearchFrame((SearchActionItem) getEANActionItem(), rst, getNavigate());
						sf.setVisible(true);
						getParentActionSet().setEnabled(true);  // reenable this parent action only
					}else{
						getNavigate().enableActionsAndRestore();
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
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}

	private class SrchWorker extends DBSwingWorker<EntityList, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		SrchWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				Profile profile = getNavigate().getProfile();
				if (getNavigate().isPin()) {
					profile  = LoginMgr.getNewProfileInstance(profile);
				}
				if(profile!=null){
					list = DBUtils.doSearch((SearchActionItem)getEANActionItem(),profile, getNavigate());
				}
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
				        if (list.getTable().getRowCount() == 0) {
				            //msg24005 = Search found no matching items.
				        	com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg24005"));
				        	list.dereference();
				        }else{
							Long t1 =System.currentTimeMillis();
							Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
							list.getProfile().setReadLanguage(getNavigate().getProfile().getReadLanguage());
							getNavigate().load(list, Navigate.NAVIGATE_INIT_LOAD);
							Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" search display ended "+Utils.getDuration(t1));
				        }

						clearAction(eia);	// why do this?
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting search entity list");
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
