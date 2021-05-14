//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

import com.ibm.eacm.mw.*;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * Base class for EACM actions
 * @author Wendy Stimpson
 */
//$Log: EACMAction.java,v $
//Revision 1.5  2014/01/24 12:47:12  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.4  2013/11/07 20:45:25  wendy
//VK_INSERT pressed key event is not generated in the accelerator
//
//Revision 1.3  2013/07/18 18:36:10  wendy
//fix compiler warnings
//
//Revision 1.2  2013/02/05 18:23:21  wendy
//throw/handle exception if ro is null
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public abstract class EACMAction extends AbstractAction implements EACMGlobals 
{
	private static final long serialVersionUID = 1L;
	private String actionKey = "";
	protected DBSwingWorker<?, ?> worker = null;
	protected boolean derefCaller = false;
	
	public EACMAction(){} // support actions without constant names like wg
	
	public EACMAction(String name, int keyCode, int modifiers){
		this(name, keyCode, modifiers,false); // false will cause the pressed keystroke to be the accelerator
		//KeyEvent.VK_INSERT requires onreleased, it does not generate a keypressed event for the accelerator!!!
	}
	
	public EACMAction(String name, int keyCode, int modifiers, boolean onrelease){
		this(name);
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(keyCode, modifiers,onrelease));
	}

	public EACMAction(String name){
		this(name,(Object[])null);
	}
	
	public EACMAction(String name, Object ... ttargs){
		super(Utils.getResource(name));
		actionKey = name;
		String value = Utils.getToolTip(name,ttargs);
		if (value!=null){
			putValue(Action.SHORT_DESCRIPTION, value);
		}
		putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(name)));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractAction#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue)  {
		boolean ok = false;
		if(newValue){
			ok = canEnable();
		}
		
		super.setEnabled(newValue && ok);
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ return true; }
	
	public String getActionKey() { return actionKey;}
	
	/**
	 * used to cancel any workers or put up dialog if worker should not be canceled
	 * user is trying to close the tab with this action and the action is running
	 * @return
	 */
	public boolean canClose() {
		boolean ok = true;
	
		if(worker!=null){
			if (worker instanceof HeavyWorker){
				//msg6000.9 = {0} is currently running.  Terminating it may corrupt data.\nDo you want to close anyway?
    			int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(null, Utils.getResource("msg6000.9",getValue(Action.NAME)));
    			if (reply==NO_BUTTON || reply==CLOSED){ // no or cancel will not close
    				ok = false;
    			}
			}
			if (ok) { 
				//worker.cancel(true); // try to stop this worker, even if it may do bad things
				RMIMgr.getRmiMgr().cancelWorker(worker,true);
			}
		}

    	return ok;
    }

	/**
	 * used to check if worker is running, or going to run
	 * this is called when the 'caller' is getting dereferenced, user probably closed the tab
	 * @return
	 */
	public boolean canDerefWorker() {
		if (worker!=null){
			derefCaller = true;  // deref of caller's worker data must be done when worker finishes
		}
		return worker==null;
	}
	
	protected void setActionKey(String s){
		actionKey=s;
	}
    protected RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }
    protected boolean hasData(EntityList _eList) {
    	if (_eList != null) {
    		for (int i = 0; i < _eList.getEntityGroupCount(); ++i) {
    			EntityGroup eg = _eList.getEntityGroup(i);
    			if (eg.isDisplayable() && eg.getEntityItemCount() > 0) {
    				return true;
    			}
    		}
        }
        return false;
    }
	public void dereference(){
		putValue(Action.NAME, null);
		putValue(Action.SHORT_DESCRIPTION, null);
        putValue(Action.MNEMONIC_KEY,null);
        putValue(Action.ACCELERATOR_KEY,null);
        putValue(Action.SMALL_ICON,null);
        putValue(Action.LARGE_ICON_KEY,null);
        actionKey = null;
		worker = null;
	}
}
