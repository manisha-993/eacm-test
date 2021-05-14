//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.MetaFlagMaintList;
import COM.ibm.eannounce.objects.MetaMaintActionItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;

/*************
 * 
 * this action is used for flag maintanence
 * @author Wendy Stimpson		
 */
//$Log: FlagMaintAction.java,v $
//Revision 1.3  2013/09/23 21:26:04  wendy
//add method to check attribute maintainability
//
//Revision 1.2  2013/09/13 18:26:12  wendy
//show frame if iconified
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class FlagMaintAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	private MetaMaintActionItem maintAction = null;

	/**
	 * constructor
	 * @param ec
	 */
	protected FlagMaintAction(EditController ec) {
		super(FLAGMAINT_ACTION,KeyEvent.VK_M,Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("maint.gif"));
		editCtrl = ec;
		setEnabled(false);
	}

	/**
	 * does this profile have the ability to addflags and is there a maint action?
	 * @return
	 */
	protected boolean isMaintenanceable(){
		return maintAction!=null && editCtrl.getProfile().hasRoleFunction(Profile.ROLE_FUNCTION_ADDFLAG);
	}

	/**
	 * set the maintaction to use 
	 * @param m
	 */
	protected void setMaintActionItem(MetaMaintActionItem m){
		maintAction = m;
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		// depends on profile and maint action
		if(isMaintenanceable()){
			// does the current editor have a flag attr selected
			if(editCtrl.getCurrentEditor()!=null ){
				ok = maintAction.canMaintenance(editCtrl.getCurrentEditor().getSelectedEANMetaAttribute());
			}
		}
		return ok;
	}
	
	/**
	 * can this attribute have flag meta added?
	 * @param ma
	 * @return
	 */
	public boolean canMaintenance(EANMetaAttribute ma){
		boolean ok = false;
		if(maintAction != null){
			ok = maintAction.canMaintenance(ma);
		}
		return ok;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		editCtrl = null;
		maintAction = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		editCtrl.disableActionsAndWait();
		//make sure one isnt already open
		com.ibm.eacm.ui.MaintenanceFrame bmd = EACM.getEACM().getFlagMaint();
		if (bmd==null){
			worker = new MaintWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}else{
			EANMetaAttribute metaAttr = editCtrl.getCurrentEditor().getSelectedEANMetaAttribute();
			if(metaAttr.getKey().equals(bmd.getMetaFlagKey())){
	    		if(bmd.getState()==com.ibm.eacm.ui.MaintenanceFrame.ICONIFIED){
	    			bmd.setState(com.ibm.eacm.ui.MaintenanceFrame.NORMAL);
	    		}
				bmd.toFront();
				editCtrl.enableActionsAndRestore();	
			}else{
				worker = new MaintWorker();
				RMIMgr.getRmiMgr().execute(worker);
			}
		}
	} 

	class MaintWorker extends DBSwingWorker<MetaFlagMaintList, Void> { 
		@Override
		public MetaFlagMaintList doInBackground() {
			MetaFlagMaintList mfm = null;
			try{
				EANMetaAttribute metaAttr = editCtrl.getCurrentEditor().getSelectedEANMetaAttribute();
				mfm = getMaintList(metaAttr);
			}catch(Exception x){
				Logger.getLogger(EDIT_PKG_NAME).log(Level.FINER,"Exception",x);
			}finally{
				RMIMgr.getRmiMgr().complete(this);
				worker = null; 
			}
			return mfm;
		}

		@Override
		public void done() {
			//this will be on the event dispatch thread
			try {
				if(!isCancelled()){
					MetaFlagMaintList mfm = get();
					if (mfm != null){      	
						//make sure one isnt already open
						com.ibm.eacm.ui.MaintenanceFrame bmd = EACM.getEACM().getFlagMaint();
						if(bmd==null){
							bmd = new com.ibm.eacm.ui.MaintenanceFrame(mfm,editCtrl.getEntityList());
							bmd.setVisible(true);
						}else{
							bmd.updateLists(mfm,editCtrl.getEntityList());
				    		if(bmd.getState()==com.ibm.eacm.ui.MaintenanceFrame.ICONIFIED){
				    			bmd.setState(com.ibm.eacm.ui.MaintenanceFrame.NORMAL);
				    		}
				    		bmd.toFront();
						}
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting MetaFlagMaintList");
			}finally{
				editCtrl.enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"not executed");
			editCtrl.enableActionsAndRestore();
			worker = null; 
		}
	}

	private MetaFlagMaintList getMaintList(EANMetaAttribute _meta) {
		MetaFlagMaintList out = null;
		// force flags to use nlsid=1, flags must be created using nlsid=1
		Profile curProfile = editCtrl.getEntityList().getProfile();
		NLSItem curItem = curProfile.getReadLanguage();
		if (curItem.getNLSID()!=1){
			curProfile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
			// show msg here 
			com.ibm.eacm.ui.UI.showFYI(editCtrl, Utils.getResource("msg5027.0"));
		}
		try {
			out = maintAction.rexec(ro(), curProfile,_meta);
		}catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						out = maintAction.rexec(ro(), curProfile,_meta);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(editCtrl, e) == RETRY) {
								return getMaintList(_meta);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(editCtrl,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(editCtrl,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(editCtrl, exc) == RETRY) {
						return getMaintList(_meta);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(editCtrl,exc, "mw.err-title");
				}
			} 
		}
		// restore the user's nlsid
		if (curItem.getNLSID()!=1){
			curProfile.setReadLanguage(curItem);
		}

		return out;
	}	
}