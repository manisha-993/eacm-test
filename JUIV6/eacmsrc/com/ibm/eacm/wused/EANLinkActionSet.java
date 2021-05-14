//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;


import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.Action;


import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.Utils;


/*******
 * 
 * used for the collection of WhereUsed link actions, it will be used with a button and menu
 * @author Wendy Stimpson
 */
// $Log: EANLinkActionSet.java,v $
// Revision 1.2  2013/11/13 14:53:10  wendy
// add ctrl+p shortcut
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANLinkActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	
	/**
	 * link action to hold all WU link actions
	 * @param n
	 * 
	 */
	public EANLinkActionSet(WUsedActionTab n){	
		super(WULINK_ACTIONSET,KeyEvent.VK_P, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("link.gif"));
	    wuAction = n;
	}

	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = createAction(eai[0]);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = createAction(eai[i]);	
					addAction(act);
				}

				popup.add(act);	
			}
		}
	}
	private EANAction createAction(EANActionItem eanAction){
		EANAction act = null;
		if(eanAction instanceof SearchActionItem){
			act = new EANLinkSrchAction(eanAction,wuAction,this);	
		}else{
			act = new EANLinkNavAction(eanAction,wuAction,this);// assumption this is a NavActionItem
		}
		return act;
	}
	
	public void dereference(){
		super.dereference();
		wuAction = null;
	}

	@Override
	protected boolean shouldEnable() {
		return wuAction.hasData();
	}
}
