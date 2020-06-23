//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import COM.ibm.eannounce.objects.EANBusinessRuleException;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;


/*************
 * 
 * this action is used for unlock action in all editors
 * @author Wendy Stimpson	
 */
//$Log: UnlockAction.java,v $
//Revision 1.3  2014/10/20 19:56:06  wendy
//Add worker id to timing log output
//
//Revision 1.2  2013/07/18 18:33:20  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class UnlockAction extends EACMAction implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;

	public UnlockAction(EditController ec) {
		super(UNLOCK_ACTION, KeyEvent.VK_U, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("ulck.gif"));
		editCtrl = ec;
		super.setEnabled(false);
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = editCtrl.isEditable();
		if(ok && editCtrl.getCurrentEditor()!=null){
			ok = editCtrl.getCurrentEditor().hasLocks();
		}
		return ok;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATALOCKED_PROPERTY)) {
			setEnabled(true);
		}
	}
	public void dereference(){
		super.dereference();
		editCtrl = null;
	}
	// this is only enabled if something selected in a editor has a lock
	public void actionPerformed(ActionEvent e) {
		if(worker!=null){ // already running
			return;
		}

		// user wants to unlock.. cancel any edit
		editCtrl.getCurrentEditor().cancelCurrentEdit();

		editCtrl.disableActionsAndWait();

		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				boolean unlocknow = false;

				// get all selected locked keys - selection may be lost
				Vector<Integer> locksvct = editCtrl.getCurrentEditor().getSelectedLockedIds();
				Vector<Integer> chgsIdVct = null;
				// does the editor have any unsaved changes? only selected items are unlocked
				if(editCtrl.getCurrentEditor().hasChanges()){
					// get all selected changed rowids
					chgsIdVct = editCtrl.getCurrentEditor().getSelectedChangedRowIds();
					if(chgsIdVct.size()==1){ // vertical and form editor always come here
						//updtMsg2 = Table values have changed. Would you like to save the change(s)?
						int action= com.ibm.eacm.ui.UI.showConfirmYesNoCancel(editCtrl, Utils.getResource("updtMsg2"));
						if (action == YES_BUTTON){
//							return getEditController().commit();
						} else if (action ==NO_BUTTON){
							//moved to bg editCtrl.getCurrentEditor().rollbackRows(chgsIdVct);
							unlocknow = true;
						}else{
							editCtrl.enableActionsAndRestore();
							return;
						}
					}else{
						//updtMsg3 = Some of the entities have changed.
						int action = com.ibm.eacm.ui.UI.showConfirmAllNoneChooseCancel(editCtrl,Utils.getResource("updtMsg3"));
						if (action == ALL_BUTTON) {
//							return getEditController().commitAll();
						} else if (action == CHOOSE_BUTTON) {
							// adjust the chgsvct
							chooseToSave(chgsIdVct);
							if(chgsIdVct.size()==0){
								unlocknow = true;
							}
						}else if (action == NONE_BUTTON) {
							//moved to bg editCtrl.getCurrentEditor().rollbackRows(chgsIdVct);
							unlocknow = true;
						}else{
							editCtrl.enableActionsAndRestore();
							return;
						} 
					}
				}else{
					unlocknow = true;
				}

				if(unlocknow){
					worker = new UnlockWorker(locksvct,chgsIdVct);
				}else{
					//commit any changes then unlock if successful
					worker = new SaveWorker(chgsIdVct,locksvct);
				}

				RMIMgr.getRmiMgr().execute(worker);
			}
		});
	} 

	private class SaveWorker extends HeavyWorker<Exception, Void> { 
		private Vector<Integer>chgsVct;
		private Vector<Integer>locksvct;

		SaveWorker(Vector<Integer> v, Vector<Integer> l){
			chgsVct = v;
			locksvct = l;
		}
		@Override
		public Exception doInBackground() {
			try{
				editCtrl.getCurrentEditor().commitRows(chgsVct); 
			}catch(Exception x){
				return x;
			}finally{
				RMIMgr.getRmiMgr().complete(this);
				worker = null; 
				chgsVct.clear();
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
						editCtrl.enableActionsAndRestore();
					}else{
						editCtrl.commitCompleted();
						worker = new UnlockWorker(locksvct,null);
						RMIMgr.getRmiMgr().execute(worker);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"saving to PDH");
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,"not executed");
			editCtrl.enableActionsAndRestore();
			worker = null; 
		}
	}

	private void chooseToSave(Vector<Integer> chgsvct) {
		Iterator<Integer> itr = chgsvct.iterator();
		Vector<Integer> rollbackVct = new Vector<Integer>();

		while (itr.hasNext()) {
			Integer rowid = (Integer)itr.next();
			// this is only applicable to the grideditor
			editCtrl.getCurrentEditor().selectRow(rowid.intValue());
			//updtMsg1= Record has changed. Would you like to save it?
			int action=  com.ibm.eacm.ui.UI.showConfirmYesNoCancel(editCtrl, Utils.getResource("updtMsg1"));
			if (action == YES_BUTTON){
			} else if (action == NO_BUTTON){//1) {
				itr.remove();
				rollbackVct.add(rowid);
			} else {
				break;
			}
		}
		if(rollbackVct.size()>0){ // wont be saved, roll them back
			editCtrl.getCurrentEditor().rollbackRows(rollbackVct);
			rollbackVct.clear();
		}
	}

	private class UnlockWorker extends DBSwingWorker<Void, Void> { 
		private long t11 = 0L;
		private Vector<Integer>locksVct;
		private Vector<Integer>chgsVct;

		UnlockWorker(Vector<Integer> v,Vector<Integer> c){
			locksVct = v;
			chgsVct = c;
		}
		@Override
		public Void doInBackground() {
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				if(chgsVct!=null && chgsVct.size()>0){
					editCtrl.getCurrentEditor().rollbackRows(chgsVct);
				}

				editCtrl.getCurrentEditor().unlock(locksVct);

			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
				locksVct.clear();
				if(chgsVct!=null){
					chgsVct.clear();
				}
			}

			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					editCtrl.getCurrentEditor().resizeAndRepaint();
				}
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				editCtrl.enableActionsAndRestore();	
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			editCtrl.enableActionsAndRestore();
			worker = null; 
		}
	}
}