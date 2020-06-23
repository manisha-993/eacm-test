//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;


import javax.swing.Action;

import COM.ibm.eannounce.objects.EditActionItem;

import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.Utils;


/*******
 * 
 * used for the collection of WhereUsed edit actions, it will be used with a button and menu
 * @author Wendy Stimpson
 */
// $Log: EANEditActionSet.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class EANEditActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wuAction=null;
	
	/**
	 * edit action to hold all WU edit actions
	 * @param n
	 * 
	 */
	public EANEditActionSet(WUsedActionTab n){
		super(WUEDIT_ACTIONSET);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("edit.gif"));
	    wuAction = n;
	}

	protected void addActions() {
		if(((EditActionItem)eai[0]).canEdit()){
			putValue(Action.SHORT_DESCRIPTION, Utils.getToolTip(WUEDIT_ACTIONSET));
			putValue(Action.NAME, Utils.getResource(WUEDIT_ACTIONSET));
		}else{
			putValue(Action.SHORT_DESCRIPTION, "View");
			putValue(Action.NAME, "View");
		}
			
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANEditAction(eai[0],wuAction,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANEditAction(eai[i],wuAction,this);		
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
		return wuAction.hasData();
	}
}
