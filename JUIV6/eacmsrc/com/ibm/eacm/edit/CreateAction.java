//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to create a new row
 * @author Wendy Stimpson
 */
//$Log: CreateAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class CreateAction extends EACMAction
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param ed
	 */
	public CreateAction(EditController ed) {
		super(CREATE_ACTION,KeyEvent.VK_N, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("new.gif"));
	    editCtrl = ed;
	    super.setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		return editCtrl.isCreatable();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (!editCtrl.getCurrentEditor().canStopEditing()) { // end any current edits
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//display problem to user
					editCtrl.getCurrentEditor().stopCellEditing();
				}
			});
			return;
		}

		editCtrl.disableActionsAndWait();
		
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// add a new row
				editCtrl.addNewRow();
				editCtrl.enableActionsAndRestore();	
				editCtrl.getCurrentEditor().requestFocusInWindow();
			}
		});
	}
}

