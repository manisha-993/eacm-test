//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.JPopupMenu;

import COM.ibm.eannounce.objects.EANActionItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.EANActionMenu;
import com.ibm.eacm.ui.ScrollPopupMenu;
import com.ibm.eacm.ui.SearchFrame;

/*******
 *
 * used for the collection of EANActions, like all Navigate actions, it will be used with a button and menu and tree
 * @author Wendy Stimpson
 */
//$Log: EANActionSet.java,v $
//Revision 1.5  2015/03/05 02:20:01  stimpsow
//correct keybd accelerators
//
//Revision 1.4  2013/11/06 21:39:16  wendy
//call menu.doclick() when invoked from a menu
//
//Revision 1.3  2013/09/13 18:19:38  wendy
//show frame if iconified
//
//Revision 1.2  2013/07/18 18:36:10  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public abstract class EANActionSet extends EACMAction {
	private static final long serialVersionUID = 1L;
	private String baseTooltip = null;

	protected EANAction singleAction = null;
	protected ScrollPopupMenu popup = new ScrollPopupMenu();

	protected EANActionItem[] eai=null;

	private Hashtable<String, EANAction> actionTbl = new Hashtable<String, EANAction>();


	/**
	 * check for any other search actions in this set already open
	 * @param uid
	 * @param actionKey
	 * @return
	 */
	public SearchFrame otherSearchFrameOpen(String uid, String actionKey) {
		if(eai!=null && eai.length>1){
			for (int i=0; i<eai.length; i++){
				String actionkey = eai[i].getActionItemKey();
				if(actionKey.equals(actionkey)){
					continue;
				}
				//make sure one isnt already open
				SearchFrame sf = EACM.getEACM().getSearchFrame(uid+":"+actionkey);
				if (sf!=null){
					return sf;
				}
			}
		}

		return null;
	}
	/**
	 * @param act
	 */
	protected void addAction(EANAction act){
		actionTbl.put(act.getActionKey(), act);
	}

	/**
	 * how many actions are in this set?
	 * @return
	 */
	public int getActionCount(){
		return actionTbl.size();
	}

	/**
	 * used to cancel any workers or put up dialog if worker should not be canceled
	 * user is trying to close the tab with this action
	 * must look at all 'child' actions
	 * @return
	 */
    public boolean canClose() {
    	boolean ok = true;
		for (Enumeration<EANAction> e = actionTbl.elements(); e.hasMoreElements();){
			EANAction action = (EANAction)e.nextElement();
			if(!action.canClose()){
				ok = false;
				break;
			}
		}
    	return ok;
    }
	/**
	 * used to check if worker is running, or going to run
	 * must look at all 'child' actions
	 * @return
	 */
	public boolean canDerefWorker() {
    	boolean ok = true;
		for (Enumeration<EANAction> e = actionTbl.elements(); e.hasMoreElements();){
			EANAction action = (EANAction)e.nextElement();
			if(!action.canDerefWorker()){
				ok = false;
				break;
			}
		}
    	return ok;
	}

	/**
	 * @param name
	 * @param ttargs
	 */
	public EANActionSet(String name, Object ... ttargs){
		super(name,ttargs);
		popup.setName(name);// debug only
		baseTooltip = (String)getValue(Action.SHORT_DESCRIPTION);
	}

	/**
	 * @param name
	 * @param keyCode
	 * @param modifiers
	 */
	public EANActionSet(String name, int keyCode, int modifiers){
		this(name, keyCode, modifiers,false);
	}
	/**
	 * @param name
	 * @param keyCode
	 * @param modifiers
	 * @param onrelease - alt modifier must be true
	 */
	public EANActionSet(String name, int keyCode, int modifiers,boolean onrelease){
		super(name, keyCode, modifiers,onrelease);
		setEnabled(false);
		popup.setName(name);// debug only
		baseTooltip = (String)getValue(Action.SHORT_DESCRIPTION);
	}

    /**
     * get EANAction for use in the tree or reuse in child actions
     * @return
     */
    public EANAction getEANAction(String actionKey){
    	return actionTbl.get(actionKey);
    }
	/**
	 * clear any current actions and add the new actions
	 * @param _eai
	 */
	public void updateEANActions(EANActionItem[] _eai){
		// if they are the same, dont reload the popup
		if (eai !=null && _eai!=null){
			if (eai.length==_eai.length){
				boolean match = true;
				for (int i=0; i<eai.length; i++){
					if(!eai[i].equals(_eai[i])){
						match = false;
						break;
					}
				}
				if(match){
					setEnabled(shouldEnable());
					return;
				}
			}
		}
		eai = _eai;

		popup.clearActions();

		singleAction = null;
		if (baseTooltip!= null){ // restore original
			putValue(Action.SHORT_DESCRIPTION, baseTooltip);
		}

		if (eai == null || eai.length==0) {
			setEnabled(false);
		}else{
			setEnabled(shouldEnable());
			addActions();
			if(singleAction!=null){
				// add more info to the base tooltip
				String value = (String)singleAction.getValue(Action.NAME);
				if (value!=null){
					if (baseTooltip!= null){
						value = baseTooltip+" - "+value;
					}
					putValue(Action.SHORT_DESCRIPTION, value);
				}
			}
		}
	}


	/**
	 * this popup will display the actions for the derived class
	 * used by EANActionMenu
	 * @return
	 */
	public JPopupMenu getPopup(){
		return popup;
	}

	/**
	 * each derived class knows what actions to pull out of this list
	 * @param _eai
	 */
	protected abstract void addActions();

	protected abstract boolean shouldEnable();

	/*
	 * when used in a button, this will display the popup
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (singleAction!=null){
			singleAction.actionPerformed(e);
		}else{
			Component comp = (Component)e.getSource();
			if(comp instanceof EANActionMenu){
				EANActionMenu menu = (EANActionMenu)comp;
				menu.doClick(1);
			}else{
				// this is used for the toolbar button
				popup.show(comp, comp.getWidth()/2,comp.getHeight()/2);
			}
		}
	}

	/*
	 * release memory
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();

		popup.dereference();
		popup = null;

    	// deref actions here actionTbl
		for (Enumeration<EANAction> e = actionTbl.elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement();
			action.dereference();
		}

		actionTbl.clear();
		actionTbl = null;

		singleAction = null;
	}
}
