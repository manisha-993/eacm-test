//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to reset all selected records, user must have the lock
 * a new record will be removed if part of the selection - only in grideditor
 * @author Wendy Stimpson
 */
//$Log: ResetAllAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class ResetAllAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param ed
	 */
	public ResetAllAction(EditController ed) {
		super(RESETALLATTR_ACTION,KeyEvent.VK_Z, Event.CTRL_MASK + Event.SHIFT_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("cncl.gif"));
		editCtrl = ed;
		super.setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(editCtrl.getCurrentEditor()!=null){
			// get all changed rowids
			Vector<Integer> chgsIdVct = editCtrl.getCurrentEditor().getChangedRowIds();
			ok = chgsIdVct.size()>0;
			chgsIdVct.clear();
			if(ok){
				if(editCtrl.getCurrentEditor() instanceof FormEditor && editCtrl.getCurrentEditor().isNew()){
					ok = editCtrl.getEgRstTable().getRowCount()>1; // cant remove the last entity in a form
				}
			}
		}

		return ok;
	}
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
			setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// this will reset all selected rows and remove any new rows	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// user wants to reset.. cancel any edit
				editCtrl.getCurrentEditor().cancelCurrentEdit();
				editCtrl.disableActionsAndWait();
				editCtrl.resetAll();
				editCtrl.enableActionsAndRestore();
				editCtrl.getCurrentEditor().requestFocusInWindow();
			}
		});
	}
}

