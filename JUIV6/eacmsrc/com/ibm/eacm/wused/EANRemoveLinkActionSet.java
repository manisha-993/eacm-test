//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;


import javax.swing.Action;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.Utils;


/*******
 * 
 * used for the collection of WhereUsed remove link actions, it will be used with a button and menu and actiontree
 * @author Wendy Stimpson
 */
// $Log: EANRemoveLinkActionSet.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANRemoveLinkActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	
	/**
	 * action to hold all WU remove link actions
	 *
	 * @param n
	 * 
	 */
	public EANRemoveLinkActionSet(WUsedActionTab n){	
		super(WURLINK_ACTIONSET);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("rlink.gif"));
	    wuAction = n;
	}

	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANRemoveLinkAction(eai[0],wuAction,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANRemoveLinkAction(eai[i],wuAction,this);	//all do the same thing
					addAction(act);
				}

				popup.add(act);	
			}
		}
	}
	
	public void dereference(){
		super.dereference();
		wuAction = null;
	}

	@Override
	protected boolean shouldEnable() { 
		return wuAction.hasData();// enable if any data in table, first selected row will control if remove link is valid
	}
}
