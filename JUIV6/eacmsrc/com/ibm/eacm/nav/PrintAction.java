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
import java.awt.print.PrinterException;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.NavController;

/*********************************************************************
 * This is used for print request from navigates
 * @author Wendy Stimpson
 */
//$Log: PrintAction.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class PrintAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private NavController parent=null;
	/**
	 * @param n
	 */
	public PrintAction(NavController n) {
		super(PRINT_ACTION,KeyEvent.VK_P, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("print.gif"));
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
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try{
					parent.getNavigate().getJTable().print();
				} catch (PrinterException e) {
					com.ibm.eacm.ui.UI.showException(parent,e);
				}
			}
		});
	}
}