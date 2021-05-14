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
import com.ibm.eacm.tabs.NavController;



/*********************************************************************
 * This is used for clearing change groups request
 * @author Wendy Stimpson
 */
//$Log: ClearChgGrpAction.java,v $
//Revision 1.2  2015/03/05 02:20:01  stimpsow
//correct keybd accelerators
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class ClearChgGrpAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private NavController parent=null;
	public ClearChgGrpAction(NavController n) {
		super(CGRP_ACTION, KeyEvent.VK_5, Event.ALT_MASK,true);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("leaveGroup.gif"));
		setEnabled(false);
		parent = n;
	}
	public void dereference(){
		super.dereference();
		parent=null;
	}
	public void actionPerformed(ActionEvent e) {
		 parent.resetChangeGroup();
	}
}