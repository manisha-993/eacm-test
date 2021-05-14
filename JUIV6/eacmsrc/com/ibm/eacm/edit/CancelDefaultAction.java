//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
* This is used to cancel default
* @author Wendy Stimpson
*/
//$Log: CancelDefaultAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class CancelDefaultAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	private boolean profCanEditDefault = false;

	/**
	 * constructor
	 * @param ed
	 */
	public CancelDefaultAction(EditController ed) {
		super(CANCELDEFAULT_ACTION);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("dcncl.gif"));
		editCtrl = ed;
		profCanEditDefault = editCtrl.canEditDefault(); 
		super.setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(profCanEditDefault){
			if(editCtrl.getCurrentEditor()!=null){
				ok = editCtrl.getCurrentEditor().isNew();
			}
		}
		return ok;
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
			setEnabled(true);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (editCtrl.getCurrentEditor().canStopEditing()) {
			editCtrl.disableActionsAndWait();
			worker = new SaveWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}else{
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//display problem to user
					editCtrl.getCurrentEditor().stopCellEditing();
				}
			});
		}
	}
	private class SaveWorker extends HeavyWorker<Exception, Void> { 
		@Override
		public Exception doInBackground() {
			try{
				EntityItem ei = editCtrl.getCurrentEditor().getCurrentEntityItem(); 
				ei.resetWGDefault(null, RMIMgr.getRmiMgr().getRemoteDatabaseInterface());
			}catch(Exception x){
				return x;
			}finally{
				RMIMgr.getRmiMgr().complete(this);
				worker = null; 
			}
			return null;
		}

		@Override
		public void done() {
			//this will be on the event dispatch thread
			try {
				if(!isCancelled()){
					Exception e = get();
					if (e != null){      	
						if (e instanceof EANBusinessRuleException){
							editCtrl.getCurrentEditor().moveToError((EANBusinessRuleException) e);
						}

						com.ibm.eacm.ui.UI.showException(null,e);
					}else{
						editCtrl.commitCompleted(); 
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"saving to PDH");
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

}

