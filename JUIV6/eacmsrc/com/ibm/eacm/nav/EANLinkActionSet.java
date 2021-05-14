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

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.LinkActionItem;

import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.Utils;


/*******
 *
 * used for the collection of Navigate link actions, it will be used with a button and menu
 * @author Wendy Stimpson
 */
// $Log: EANLinkActionSet.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class EANLinkActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private Navigate parent=null;

	/**
	 * create action to hold all NAV link actions
	 * @param n
	 *
	 */
	public EANLinkActionSet(Navigate n){
		super(NAVLINK_ACTIONSET,KeyEvent.VK_8, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("link.gif"));
	    parent = n;
	}

	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANLinkAction(eai[0],parent,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANLinkAction(eai[i],parent,this);
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
		boolean enable = false;
		int oppselcnt = 0;

		if (eai.length>0){
			for (int i = 0; i < eai.length; ++i) {
				EANActionItem eai22 = eai[i];
				if (eai22 instanceof LinkActionItem &&((LinkActionItem) eai22).isOppSelect()) {
					oppselcnt++;
				}
			}
			if (oppselcnt==eai.length){
				enable = true;
			}else{
		//		if (parent.getNavigate()!=null){
					enable = parent.hasData();
		//		}
			}
		}

		return enable;
	}
}
