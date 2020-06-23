//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.edit.EditController;


import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.BluePageEntry;

/**
 * this is used for Create from Navigate
 * @author Wendy Stimpson
 */
//$Log: EANCreateAction.java,v $
//Revision 1.4  2016/05/16 19:49:49  stimpsow
//Support Global ID format
//
//Revision 1.3  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.2  2013/07/18 20:06:18  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class EANCreateAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;
	private BluePageWorker bpeWorker;

	public EANCreateAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_CREATE,p);
	}

	public void actionPerformed(ActionEvent e) {
		outputStartInfo();

		if (Utils.isPast(getNavigate().getProfile())) {
			return; // this action should not be enabled if past
		}
		EntityItem[] ei = null;
		CreateActionItem create = (CreateActionItem) getEANActionItem();

		try{
			EntityList.checkDomain(getNavigate().getProfile(),getEANActionItem(),ei); // RQ0713072645
		}catch(DomainException de){
			// just do it for edit here to get msg, it will be enforced in mw
			com.ibm.eacm.ui.UI.showFYI(getNavigate(), de.getMessage());
			de.dereference();
			return;
		}

		if (create.isPeerCreate()) { //peer_create
			ei = getParentEntityItems();
			if (ei == null) {
				// msg11006 = Selected Action could not be completed because no Entity(s) were selected.
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11006"));
			}
		}else{
			try{
				ei = getEntityItems(false);
	        } catch (OutOfRangeException range) {
	        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),range);
	        	return;
	        }
	        if(!checkValidSingleInput(ei)){ // make sure this action has correct number ei selected
	        	return;
	        }
		}

		if (ei!=null){
			outputDebug(ei);

			if (EACM.getEACM().tabExists(getNavigate().getProfile(),ei, create)) { //20059
				return; //20059
			} //20059

			//check for existing wu for same profile, action and ei
			//if (!ELogin.getEACM().tabExists(getNavigate().getProfile(), ei, getEANActionItem())) {
			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

			if (create.isBluePage()) {
				// must setup create action before doing the create
				// reset create info
				create.setEmailAddress(null);
				create.setSelectedBluePageEntries(null);

				//blueID = Please enter Search Criteria Name (last,first) or ID
				String sUser = com.ibm.eacm.ui.UI.showInput(getNavigate(),Utils.getResource("blueID"), "",15);
				if (sUser == null) {//user cancelled, ignore it
					getNavigate().enableActionsAndRestore();
					return;
				}
				if (!Routines.have(sUser) || sUser.length() < 2) {
					//"msg5020"=Search Criteria must be at least 2 characters in length.
					com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg5020"));
					getNavigate().enableActionsAndRestore();
					return;
				}
				if (isUserID(sUser)) {
					create.setEmailAddress(sUser.trim());

					worker = new CreateWorker(ei);
					RMIMgr.getRmiMgr().execute(worker);
				}else {
					String[] name = Routines.getStringArray(sUser, ",");
					if (name != null) {
						String firstname="";
						String lastname=name[0].trim();
						if (name.length > 1) {
							firstname = name[1].trim();
						}
						// instantiate a bluepage worker
						bpeWorker = new BluePageWorker(firstname, lastname,ei);
						RMIMgr.getRmiMgr().execute(bpeWorker);
					}
				}
			}else{
				worker = new CreateWorker(ei);
				RMIMgr.getRmiMgr().execute(worker);
			}
			//}
		}
	}

	private boolean isUserID(String _s) {
		if (Routines.have(_s)) {
			_s.toLowerCase().trim();
			//return (_s.endsWith(".ibm.com") && _s.indexOf("@") > 0);
			return (_s.endsWith("ibm.com") && _s.indexOf("@") > 0); //Global ID support
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private BluePageEntry[] selectBluePageEntries(BluePageEntry[] _bpe) {
		Vector<BluePageEntry> vct = new Vector<BluePageEntry>(_bpe.length);
        for (int i=0; i<_bpe.length; i++){
        	vct.add(_bpe[i]);
        }
        Collections.sort(vct);

		Object values[] = com.ibm.eacm.ui.UI.showList(getNavigate(),
				Utils.getResource("bluepage.create"),vct.toArray(new BluePageEntry[vct.size()]));

		vct.clear();

		if (values != null) {
			for (int i = 0; i < values.length; ++i) {
				vct.add((BluePageEntry) values[i]);
			}
			if (!vct.isEmpty()) {
				return (BluePageEntry[]) vct.toArray(new BluePageEntry[vct.size()]);
			}
		}
		return null;
	}

	private class BluePageWorker extends DBSwingWorker<BluePageEntry[], Void> {
		private String first = null;
		private String last = null;
		private EntityItem[] eia = null;
		private long t11 = 0L;
		private boolean docreate = false;
		BluePageWorker(String f, String l,EntityItem[] e){
			first = f;
			last = l;
			eia = e;
		}
		@Override
		public BluePageEntry[] doInBackground() {
			BluePageEntry[] bpe = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				bpe = getBluePageEntries(first, last);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				bpeWorker = null;
				first = null;
				last = null;
			}

			return bpe;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					BluePageEntry[] bpe = get();
					if (!isCancelled() && bpe != null) {
						if (bpe.length == 1) {
							((CreateActionItem) getEANActionItem()).setSelectedBluePageEntries(bpe);
							docreate= true;
						} else {
							BluePageEntry[] sel = selectBluePageEntries(bpe);
							if (sel != null) {
								((CreateActionItem) getEANActionItem()).setSelectedBluePageEntries(sel);
								docreate= true;
							}
						}
						if(docreate){
							worker = new CreateWorker(eia);
							RMIMgr.getRmiMgr().execute(worker);
						}
					} else {
						//"msg5018"=No Matching Blue Page Record(s) found.
						com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg5018"));
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting bluepages list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"dispatch ended "+Utils.getDuration(t11));
				if(!docreate){
					getNavigate().enableActionsAndRestore();
					eia = null;
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
			getNavigate().enableActionsAndRestore();
			bpeWorker = null;
		}
	}

    /**
     * @param _first
     * @param _last
     * @return
     */
    private BluePageEntry[] getBluePageEntries(String _first, String _last) {
        BluePageEntry[] out = null;
        try {
            out = ((CreateActionItem) getEANActionItem()).getBluePageEntries(null, ro(), _first, _last);
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        		if (RMIMgr.getRmiMgr().reconnectMain()) {
        			try {
        		         out = ((CreateActionItem) getEANActionItem()).getBluePageEntries(null, ro(), _first, _last);
        			} catch (Exception e) {
        				com.ibm.eacm.ui.UI.showException(getNavigate(),e, "mw.err-title");
        			}
        		} else{	// reconnect failed
        			com.ibm.eacm.ui.UI.showException(getNavigate(),exc, "mw.err-title");
        		}
        	}else{
        		com.ibm.eacm.ui.UI.showException(getNavigate(),exc, "mw.err-title");
        	}
        }
        return out;
    }

	private class CreateWorker extends DBSwingWorker<EntityList, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		CreateWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				list = DBUtils.create((CreateActionItem)getEANActionItem(), eia,getNavigate().getProfile(), getNavigate());
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
						EditController ec = new EditController(list, getNavigate());
						ec.setParentProfile(getNavigate().getProfile());
						//ec.setSelectorEnabled(true);
						EACM.getEACM().addTab(getNavigate().getNavController(), ec);
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
