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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.ibm.eacm.ui.EACMDialog;

/******************************************************************************
* This is used to close dialogs. 
* @author Wendy Stimpson 
*/
// $Log: CloseDialogAction.java,v $
// Revision 1.3  2015/03/13 18:28:21  stimpsow
// restore keybd accelerators
//
// Revision 1.2  2015/03/05 02:20:01  stimpsow
// correct keybd accelerators
//
// Revision 1.1  2012/09/27 19:39:16  wendy
// Initial code
//
public class CloseDialogAction extends EACMAction implements WindowListener 
{
	private static final long serialVersionUID = 1L;
	private EACMDialog dialog = null;
	public CloseDialogAction(EACMDialog d) {
		super(EXITDIALOG_ACTION,KeyEvent.VK_F4, Event.ALT_MASK);
        dialog=d;
	}

	public CloseDialogAction(EACMDialog d, String name) {
		super(name);
        dialog=d;
	}
	
	public void actionPerformed(ActionEvent e) {	
		// the close operation was overridden, hide here
		dialog.setVisible(false);
		dialog.dispose();
		dialog.dereference();
	} 

	public void dereference(){
		super.dereference();
		dialog = null;
	}
	/**
	 * Invoked when the user attempts to close the window
	 * from the window's system menu.
	 */
	public void windowClosing(WindowEvent e) {
		actionPerformed(null);	
	}

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     */
	public void windowClosed(WindowEvent e) {
		dialog.dereference();
	}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}