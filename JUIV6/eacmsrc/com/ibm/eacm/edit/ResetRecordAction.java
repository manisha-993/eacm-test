//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.ibm.eacm.actions.EACMAction;

/*********************************************************************
 * This is used to reset records
 * @author Wendy Stimpson
 */
//$Log: ResetRecordAction.java,v $
//Revision 1.2  2013/10/10 21:41:49  wendy
//allow reset record for new entity
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class ResetRecordAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param ed
	 */
	public ResetRecordAction(EditController ed) {
		super(RESETRECORD_ACTION,KeyEvent.VK_Z, Event.CTRL_MASK + Event.ALT_MASK);
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
			// any changed rowids, and new
			ok= editCtrl.getCurrentEditor().canResetRecord();
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
		// user wants to reset.. cancel any edit
		editCtrl.getCurrentEditor().cancelCurrentEdit();

		// this will reset all selected rows 
		editCtrl.getCurrentEditor().rollbackRow();
	}
}

