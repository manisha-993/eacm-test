//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import COM.ibm.eannounce.objects.NavActionItem;

import com.ibm.eacm.actions.EANAction;
import com.ibm.eacm.actions.EANActionSet;
import com.ibm.eacm.objects.Utils;

/*******
 *
 * used for the collection of Navigate actions, it will be used with a button and menu
 * @author Wendy Stimpson
 */
// $Log: EANNavPickActionSet.java,v $
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class EANNavPickActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private Navigate parent=null;

	/**
	 * create action to hold all NAV picklist actions
	 * @param n
	 */
	public EANNavPickActionSet(Navigate n){
		super(NAVPICK_ACTIONSET,KeyEvent.VK_1, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("navPick.gif"));
	    parent = n;
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){
		return parent.hasSelectedData() && shouldEnable();
	}

	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANNavPickListAction(eai[0],parent,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANNavPickListAction(eai[i],parent,this);
					addAction(act);
				}

				popup.add(act);
			}
		}
	}

	public void dereference(){
		super.dereference();
		parent = null;
	}

	@Override
	protected boolean shouldEnable() {
		boolean enable = parent.hasData();
		int cnt=0;
		if(eai !=null){
			for (int i=0;i<eai.length;++i) {
				if (eai[i] instanceof NavActionItem) {
					if (((NavActionItem)eai[i]).isPicklist()) {
						cnt++;
					}
				}
			}
		}

		return enable&& cnt>0;
	}
}
