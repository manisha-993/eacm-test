//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.edit;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;

/*************
* 
* paste the collected attributes into specified entities 
* @author Wendy Stimpson
*/
//$Log: FillPasteAction.java,v $
//Revision 1.2  2014/10/20 19:56:06  wendy
//Add worker id to timing log output
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class FillPasteAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;

	/**
	 * @param nt
	 */
	public FillPasteAction(EditController ed) {
		super(FILLPASTE_ACTION,KeyEvent.VK_F12, 0);
		editCtrl = ed;
		super.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		// must have done fillcopy or fillappend and have rows selected
		if(editCtrl.getCurrentEditor()!=null){
			ok = editCtrl.getCurrentEditor().canFillPaste();
		}
		return ok; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		editCtrl = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(worker!=null){ // already running
			return;
		}
		editCtrl.disableActionsAndWait();
		worker = new FillWorker();
		RMIMgr.getRmiMgr().execute(worker);
	} 
	
	private class FillWorker extends DBSwingWorker<Void, Void> { 
		private long t11 = 0L;

		@Override
		public Void doInBackground() {
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				editCtrl.getCurrentEditor().fillPaste();
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception",ex);
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
					editCtrl.getCurrentEditor().finishAction();
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