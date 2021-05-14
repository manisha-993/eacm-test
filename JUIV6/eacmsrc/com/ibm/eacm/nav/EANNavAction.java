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

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;


import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * this is used for Navigate.. much work needed to pull crap out of Navigate
 * @author Wendy Stimpson
 */
// $Log: EANNavAction.java,v $
// Revision 1.4  2014/10/20 19:56:07  wendy
// Add worker id to timing log output
//
// Revision 1.3  2013/07/18 20:06:19  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.2  2012/11/09 20:47:58  wendy
// check for null profile from getNewProfileInstance()
//
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class EANNavAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;
	private int navType = Navigate.NAVIGATE_LOAD;

	public EANNavAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_NAVIGATE,p);
	}

	public void actionPerformed(ActionEvent e) {
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
        if (ei == null) {
        	NavActionItem nav = (NavActionItem) getEANActionItem();
        	if (!nav.isHomeEnabled() && !(nav.isPicklist() && nav.useRootEI())) {
        		//msg11006 = Selected Action could not be completed because no Entity(s) were selected.
        		com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11006"));
        		return;
        	}
        }

		NavActionItem nav = (NavActionItem) getEANActionItem();
		if (nav.isPicklist() && nav.useRootEI()) { //DWB_20040908
		} else if (!nav.isHomeEnabled() && !validEntityItems(ei)) { //dwb_20030827
			// msg11006 = Selected Action could not be completed because no Entity(s) were selected.
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11006"));
			return; //dwb_20030827
		} //dwb_20030827

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

		if (ei!=null){
			outputDebug(ei);
			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new NavWorker(ei);
			RMIMgr.getRmiMgr().execute(worker);
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
				Profile prof = getNavigate().getProfile();
				if (getNavigate().isPin()) {
					prof = LoginMgr.getNewProfileInstance(prof);
				}
				if(prof!=null){
					if (((NavActionItem)getEANActionItem()).isHomeEnabled()) {
						list = 	getNavigate().navigate((NavActionItem) getEANActionItem(), null);
					} else if (validEntityItems(eia)) {
						list = getNavigate().navigate((NavActionItem)getEANActionItem(), eia);
					} else {
						list = DBUtils.getNavigateEntry(prof);
					}
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
					Logger.getLogger(NAV_PKG_NAME).log(Level.FINER," list ");
					// loggers need this put into separate lines
					String listArray[] = Routines.splitString(Utils.outputList(list), NEWLINE);
					for(int i=0;i<listArray.length; i++){
						Logger.getLogger(NAV_PKG_NAME).log(Level.FINER, listArray[i]);
					}
					if (list !=null){
						Long t1  = System.currentTimeMillis();
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
						getNavigate().load(list, navType);
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" display ended "+Utils.getDuration(t1));
					}
					clearAction(eia);	// why do this?
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
