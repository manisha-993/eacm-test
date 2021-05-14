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

import com.ibm.eacm.*;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.objects.SerialPref;

/*********************************************************************
 * This listens for exit requests, then checks if a save is needed or
 * not and displays a dialog. Use
 * setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) to specify
 * that your window listener takes care of all window-closing duties.
 * @author Wendy Stimpson
 */
// $Log: ExitAction.java,v $
// Revision 1.6  2015/03/13 18:28:21  stimpsow
// restore keybd accelerators
//
// Revision 1.5  2015/03/05 02:20:01  stimpsow
// correct keybd accelerators
//
// Revision 1.4  2013/07/31 19:59:48  wendy
// can not use a separate thread when invoked from autoupdate
//
// Revision 1.3  2013/07/31 18:01:32  wendy
// use invokelater to display the wait cursor
//
// Revision 1.2  2013/02/05 18:21:39  wendy
// Disable other actions when exiting
//
// Revision 1.1  2012/09/27 19:39:16  wendy
// Initial code
//
public class ExitAction extends EACMAction implements WindowListener
{
	private static final long serialVersionUID = 1L;
	public ExitAction() {
		super(EXIT_ACTION,KeyEvent.VK_F4, Event.ALT_MASK);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			EACM.getEACM().disableAllActionsAndWait();

			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try{
						EACM.getEACM().savePreferences();

						if (EACM.getEACM().canCloseAll(true)) {  
							SerialPref.writePreferences(); // write user preferences now
							for (int i=0; i<EACM.getEACM().getMainPane().getComponentCount(); i++){
								if (EACM.getEACM().getMainPane().getComponent(i) instanceof LoginMgr){
									((LoginMgr)EACM.getEACM().getMainPane().getComponent(i)).cancelLogin(); // cancel any login
									break;
								}
							}

							EACM.getEACM().dereference();

							// the close operation was overridden, hide here
							EACM.getEACM().setVisible(false);
							EACM.getEACM().dispose();
							System.exit(0); // this shuts all other Frames too
						}else{
							EACM.getEACM().enableAllActionsAndRestore();
						}

					}catch(Throwable t){ // allow jui to exit when something goes really wrong
						EACM.stderr.println("Exception trying to exit, force it!");
						t.printStackTrace(System.err);
						System.exit(0);
					}
				}
			});
		}catch(Throwable tt){ // allow jui to exit when something goes really wrong
			EACM.stderr.println("Exception trying to exit, force it!");
			tt.printStackTrace(System.err);
			System.exit(0);
		}
	}

	/**
	 * exit without using another thread
	 */
	public void exitNow(){
		try{
			EACM.getEACM().savePreferences();

			EACM.getEACM().canCloseAll(true);
			SerialPref.writePreferences(); // write user preferences now
			for (int i=0; i<EACM.getEACM().getMainPane().getComponentCount(); i++){
				if (EACM.getEACM().getMainPane().getComponent(i) instanceof LoginMgr){
					((LoginMgr)EACM.getEACM().getMainPane().getComponent(i)).cancelLogin(); // cancel any login
					break;
				}
			}

			EACM.getEACM().dereference();

			// the close operation was overridden, hide here
			EACM.getEACM().setVisible(false);
			EACM.getEACM().dispose();
			System.exit(0); // this shuts all other Frames too
		}catch(Throwable t){ // allow jui to exit when something goes really wrong
			EACM.stderr.println("Exception trying to exit, force it!");
			t.printStackTrace(System.err);
			System.exit(0);
		}
	}
	public void windowActivated(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
		// if there is nothing to undo, assumption is that all has been saved
		// or restored
		actionPerformed(null);
	}

	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	//	 A Window Listener is placed on the main frame,
	//	 and it's windowOpened method is used to listen for the window to become
	//	 active. then, requestFocusInWindow is used to call the focus to the item the developer 
	//	 thinks should receive initial focus.  Note, don't use 
	//	 windowActivated to try to accomplish this task. If this is 
	//	 used, the focus will be changed on the user each time they switch away from, and then return
	//	 to this application's window. This can be very disorienting. Only set the  focus
	//	 the first time a new window is entered.
	public void windowOpened(WindowEvent e) {
		for (int i=0; i<EACM.getEACM().getMainPane().getComponentCount(); i++){
			if (EACM.getEACM().getMainPane().getComponent(i) instanceof LoginMgr){
				EACM.getEACM().getMainPane().getComponent(i).requestFocusInWindow(); // focus in login
				return;
			}
		}
		EACM.getEACM().getMainPane().requestFocusInWindow(); // restore focus so ESC can close window
	}
}