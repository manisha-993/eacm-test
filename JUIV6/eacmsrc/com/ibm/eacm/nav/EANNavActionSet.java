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
 * used for the collection of Navigate actions, it will be used with a button and menu and tree
 * @author Wendy Stimpson
 */
// $Log: EANNavActionSet.java,v $
// Revision 1.2  2013/09/09 20:37:23  wendy
// only enable set if actions exist
//
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class EANNavActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private Navigate parent=null;
	
	/**
	 * create action to hold all NAV actions
	 * @param n
	 */
	public EANNavActionSet(Navigate n){
		super(NAV_ACTIONSET,KeyEvent.VK_0, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("nav.gif"));
	    parent = n;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(parent !=null && eai != null){
			ok = parent.hasSelectedData();
		}
		return ok; 
	}
	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANNavAction(eai[0],parent,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANNavAction(eai[i],parent,this);	
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
		if (enable){
			for (int i=0;i<eai.length;++i) {
				if (eai[i] instanceof NavActionItem) {
					if (!((NavActionItem)eai[i]).isPicklist()) {
						cnt++;		
						break;
					}											
				}												
			}
		}

		return enable && cnt>0;
	}
}
