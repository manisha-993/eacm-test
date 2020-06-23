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
import java.beans.*;

import com.ibm.eacm.mw.*;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to kill all running workers and clear any waiting workers
 * @author Wendy Stimpson
 */
//$Log: BreakAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class BreakAction extends EACMAction implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	public BreakAction() {
		super(BREAK_ACTION,KeyEvent.VK_CANCEL, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("break.gif"));
	    RMIMgr.getRmiMgr().addPropertyChangeListener(this);
	    setEnabled(false);
	}
	public void actionPerformed(ActionEvent e) {
		RMIMgr.getRmiMgr().interrupt();
	}
	public void dereference(){
		super.dereference();
		RMIMgr.getRmiMgr().removePropertyChangeListener(this);
	}
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(RMIMgr.WORKER_PROPERTY))	{
			setEnabled(((Boolean)event.getNewValue()));
		}
	}
}

