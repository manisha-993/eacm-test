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

import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to duplicate rows
 * @author Wendy Stimpson
 */
//$Log: DuplicateAction.java,v $
//Revision 1.3  2013/11/07 20:45:25  wendy
//VK_INSERT pressed key event is not generated in the accelerator
//
//Revision 1.2  2013/07/26 17:28:48  wendy
//support duplication of multiple rows
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class DuplicateAction extends EACMAction
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param ed
	 */
	public DuplicateAction(EditController ed) {
		super(DUPLICATE_ACTION,KeyEvent.VK_INSERT, Event.CTRL_MASK,true); // KeyEvent.VK_INSERT does not send a pressed event!!!!
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("duplicate.gif"));
	    editCtrl = ed;
	    super.setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = editCtrl.isCreatable();
		if(ok){
			if (editCtrl.getCurrentEditor()!=null){ 
				EntityItem[] eia = editCtrl.getCurrentEditor().getSelectedEntityItems();
				ok=(eia!=null && eia.length>0);
				eia=null;
			}
		}
		return ok;
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
		
		//msg3013 =How many times would you like to paste the Item (1 - 99)?
		final int copies = com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1);
		if (copies == CLOSED) {
			return;
		}
		editCtrl.disableActionsAndWait();
		
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// add duplicated rows
				editCtrl.duplicate(copies);
				editCtrl.enableActionsAndRestore();	
			}
		});
	}
}

