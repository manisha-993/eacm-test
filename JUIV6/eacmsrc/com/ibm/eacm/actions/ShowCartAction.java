//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.tabs.TabPanel;

/*********************************************************************
 * This is used for showing the cart (workfolder) request
 * @author Wendy Stimpson
 */
//$Log: ShowCartAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class ShowCartAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private TabPanel parent=null; 
	public ShowCartAction(TabPanel n) {
		super(SHOWCART_ACTION, KeyEvent.VK_W, Event.CTRL_MASK + Event.ALT_MASK);
		parent = n;
	}
	public void dereference(){
		super.dereference();
		parent=null;
	}
	public void actionPerformed(ActionEvent e) {
		parent.popupCart();
	}
}