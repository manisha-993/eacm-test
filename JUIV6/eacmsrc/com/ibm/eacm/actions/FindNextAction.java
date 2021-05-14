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

import com.ibm.eacm.EACM;

/*********************************************************************
 * This controls find next action
 * @author Wendy Stimpson
 */
//$Log: FindNextAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class FindNextAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public FindNextAction() {
		super(FINDNEXT_ACTION,KeyEvent.VK_F3, Event.CTRL_MASK);
	} 
	public void actionPerformed(ActionEvent e)
	{
		EACM.getEACM().getFindMgr().findNext();
	}
}