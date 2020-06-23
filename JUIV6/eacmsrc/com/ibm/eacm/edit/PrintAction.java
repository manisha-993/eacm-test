//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to print editors
 */
//$Log: PrintAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class PrintAction extends EACMAction
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param ed
	 */
	public PrintAction(EditController ed) {
		super(PRINT_ACTION,KeyEvent.VK_P, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("print.gif"));
	    editCtrl = ed;
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		editCtrl.getCurrentEditor().stopCellEditing();

		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					editCtrl.getCurrentEditor().print();
				} catch (PrinterException e) {
					com.ibm.eacm.ui.UI.showException(editCtrl,e);
				}
			}
		});
	}
}

