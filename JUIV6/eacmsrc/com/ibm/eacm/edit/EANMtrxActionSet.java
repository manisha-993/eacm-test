//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import javax.swing.Action;


import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.Utils;

/*******
 * 
 * used for the collection of Edit matrix actions, it will be used with a button and menu
 * completely untested - not sure this is valid
 * @author Wendy Stimpson
 */
// $Log: EANMtrxActionSet.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
public class EANMtrxActionSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	/**
	 * action to hold all Edit matrix actions
	 * @param n
	 * 
	 */
	public EANMtrxActionSet(EditController n){
		super(EDITMTRX_ACTIONSET);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("mtrx.gif"));
	    editCtrl = n;
	}

	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANMtrxAction(eai[0],editCtrl,this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANMtrxAction(eai[i],editCtrl,this);		
					addAction(act);
				}

				popup.add(act);	
			}
		}
	}
	
	public void dereference(){
		super.dereference();
		editCtrl = null;
	}

	@Override
	protected boolean shouldEnable() {
		return editCtrl.hasData();
	}
}
