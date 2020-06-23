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

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.NavController;


/*********************************************************************
 * This is used for pin request
 * @author Wendy Stimpson
 */
//$Log: PinAction.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class PinAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private NavController parent=null;
	/**
	 * @param n
	 */
	public PinAction(NavController n) {
		super(PIN_ACTION, KeyEvent.VK_P, Event.CTRL_MASK + Event.SHIFT_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("pin.gif"));
		parent = n;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		parent=null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (parent.isPin()) {
			EACM.getEACM().setIconAt(parent, Utils.getImageIcon(
					EACMProperties.getProperty(parent.getNavigate().getTabIconKey()))); 
			parent.setPin(false);
		} else {
			EACM.getEACM().setIconAt(parent, Utils.getImageIcon("pin.gif"));
			parent.setPin(true);
		}    
	}
}