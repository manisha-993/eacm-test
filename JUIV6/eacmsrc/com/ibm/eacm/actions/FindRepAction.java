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

import javax.swing.Action;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This controls find/replace window
 * @author Wendy Stimpson
 */
//$Log: FindRepAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class FindRepAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public FindRepAction() {
		super(FINDREP_ACTION,KeyEvent.VK_F, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("find.gif"));
	}

	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().getFindMgr().showFindRepFrame();
	}
}