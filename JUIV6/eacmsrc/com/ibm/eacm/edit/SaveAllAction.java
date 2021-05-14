//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import COM.ibm.eannounce.objects.EANBusinessRuleException;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used to save all records 
 * @author Wendy Stimpson
 */
//$Log: SaveAllAction.java,v $
//Revision 1.4  2013/09/09 21:38:19  wendy
//readonly profile is not able to edit
//
//Revision 1.3  2013/08/21 00:22:17  wendy
//disable tab close during save
//
//Revision 1.2  2013/05/07 18:25:26  wendy
//another perf update for large amt of data
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class SaveAllAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param ed
	 */
	public SaveAllAction(EditController ed) {
		super(SAVEALL_ACTION,KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("saveAll.gif"));
	    editCtrl = ed;
	    super.setEnabled(false);
	}
	
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		if(editCtrl.getProfile()==null || editCtrl.getProfile().isReadOnly()){
			return false;
		}
		boolean ok = editCtrl.hasMasterChanges();
		if(!ok){
			if(editCtrl.getCurrentEditor()!=null){
				//any changed entity items
				ok = editCtrl.getCurrentEditor().hasAnyChanges();
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
			//disable the closetab action too
			EACM.getEACM().setActionEnabled(CLOSETAB_ACTION, false);
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
				editCtrl.commitAllBg();
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
				EACM.getEACM().setActionEnabled(CLOSETAB_ACTION, true);
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"not executed");
			editCtrl.enableActionsAndRestore();
			EACM.getEACM().setActionEnabled(CLOSETAB_ACTION, true);
			worker = null; 
		}
	}
}

