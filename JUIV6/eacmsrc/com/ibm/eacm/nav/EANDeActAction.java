//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;


import COM.ibm.eannounce.objects.DeleteActionItem;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;


/**
 * this is used for deactivate entities
 * @author Wendy Stimpson
 */
// $Log: EANDeActAction.java,v $
// Revision 1.4  2013/10/24 16:58:26  wendy
// add more logging
//
// Revision 1.3  2013/07/30 20:02:34  wendy
// force refresh after delete
//
// Revision 1.2  2013/07/18 20:06:18  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class EANDeActAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANDeActAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_DELETE,p);
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

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

			EntityItem[] aei = getDeactivateEntityItems(ei);
			if (aei != null && aei.length > 0) {
				outputDebug(aei);
				worker = new DeleteWorker(aei);
				RMIMgr.getRmiMgr().execute(worker);
			}else{
				getNavigate().enableActionsAndRestore();
			}
		}
	}
	private EntityItem[] getDeactivateEntityItems(EntityItem[] _ei) {
		EntityItem[] out = null;

		int reply = CLOSED;
		Vector<EntityItem> v = new Vector<EntityItem>();
		if ( _ei.length==1){
			String s = _ei[0].toString() + " (" + _ei[0].getEntityType() + ":" + _ei[0].getEntityID() + ")";
			//msg23032.1 = Perform {0} on:\n {1}?
			reply =  com.ibm.eacm.ui.UI.showConfirmOkCancel(getNavigate(),
					Utils.getResource("msg23032.1",getEANActionItem().toString(),s));
			if (reply == YES_BUTTON) {
				v.add(_ei[0]);
			}
		}else{
			for (int i = 0; i <  _ei.length; ++i) {
				if (reply != ALL_BUTTON) {
					//msg23032.1 = Perform {0} on:\n {1}?
					String s = _ei[i].toString() + " (" + _ei[i].getEntityType() + ":" + _ei[i].getEntityID() + ")";
					reply = com.ibm.eacm.ui.UI.showConfirmYesNoAllCancel(getNavigate(),
							Utils.getResource("msg23032.1",getEANActionItem().toString(),s));
				}

				if (reply == YES_BUTTON || reply == ALL_BUTTON) {
					v.add(_ei[i]);
				} else if (reply == CLOSED || reply==CANCEL_BUTTON) {
					v.clear();
					break;
				}
			}
		}

		if (!v.isEmpty()) {
			out = new EntityItem[v.size()];
			v.copyInto(out);
		}

        return out;
    }

	private class DeleteWorker extends HeavyWorker<Boolean, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
	
		DeleteWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public Boolean doInBackground() {
			Boolean bool = null;
			try{
				boolean isok = false;
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				DeleteActionItem del = (DeleteActionItem)getEANActionItem();
				del.setEntityItems(eia);
				isok= DBUtils.doDelete(del, getNavigate().getProfile(), getNavigate());
				bool = new Boolean(isok);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}

			return bool;
		}

		@Override
		public void done() {
			boolean isrefreshing = false;
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Boolean isok = get();
					if (isok.booleanValue()){
						isrefreshing = true;
						getNavigate().enableActionsAndRestore();
						getNavigate().forceRefresh(); //spawns a thread - gbl8199x not recognizing deletes for some reason

						//msg11020.0 = {0}\ncompleted successfully.
						com.ibm.eacm.ui.UI.showFYI(getNavigate(), Utils.getResource("msg11020.0",getEANActionItem().toString()));

						// notify cart that items were deleted
						getNavigate().getCart().updateDeletedCartItems(eia);
					}
					clearAction(eia);	// why do this?
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"deleting entities");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"dispatch ended "+Utils.getDuration(t11));
				eia = null;
				if(!isrefreshing){
					getNavigate().enableActionsAndRestore();
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
			getNavigate().enableActionsAndRestore();
			worker = null;
		}
	}

}
