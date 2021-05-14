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

import com.ibm.eacm.*;


/*********************************************************************
 * This is used for close all tabs request
 * @author Wendy Stimpson
 */
//$Log: CloseAllAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class CloseAllAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public CloseAllAction() {
		super(CLOSEALL_ACTION,KeyEvent.VK_W, Event.CTRL_MASK + Event.SHIFT_MASK);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().canCloseAll(false);
	}
}

