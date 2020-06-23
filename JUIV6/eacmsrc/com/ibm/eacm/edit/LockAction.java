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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;


/*************
* 
* this action is used for lock action in all editors
* @author Wendy Stimpson	
*/
//$Log: LockAction.java,v $
//Revision 1.2  2014/10/20 19:56:06  wendy
//Add worker id to timing log output
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class LockAction extends EACMAction implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	public LockAction(EditController ec) {
		super(LOCK_ACTION, KeyEvent.VK_L, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("lock.gif"));
		editCtrl = ec;
		super.setEnabled(false);
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = editCtrl.getCurrentEditor() !=null && editCtrl.getCurrentEditor().canLock();
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
	public void actionPerformed(ActionEvent e) {
		if(worker!=null){ // already running
			return;
		}
		editCtrl.disableActionsAndWait();
		worker = new LockWorker();
		RMIMgr.getRmiMgr().execute(worker);
	} 
	
	private class LockWorker extends DBSwingWorker<Void, Void> { 
		private long t11 = 0L;

		@Override
		public Void doInBackground() {
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				editCtrl.getCurrentEditor().lock();
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}

			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					editCtrl.getCurrentEditor().checkLockStatus();
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