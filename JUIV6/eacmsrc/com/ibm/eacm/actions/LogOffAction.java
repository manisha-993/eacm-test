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
 * This is used for logoff requests
 * @author Wendy Stimpson
 */
//$Log: LogOffAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class LogOffAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public LogOffAction() {
		super(LOGOFF_ACTION,KeyEvent.VK_Q, Event.CTRL_MASK);
	}
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().logOff();
	}
}