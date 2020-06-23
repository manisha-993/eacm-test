//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for addcut request from navigate
 * @author Wendy Stimpson
 */
//$Log: AddCutAction.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
class AddCutAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Navigate nav=null;
	AddCutAction(Navigate n) {
		super(NAVCUT_ACTION,KeyEvent.VK_X, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("cut.gif"));
		nav = n;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(nav !=null){
			ok = nav.hasSelectedData();
		}
		return ok; 
	}
	public void dereference(){
		super.dereference();
		nav=null;
	}
	public void actionPerformed(ActionEvent e) {
		nav.addToCutGroup();
	}
}