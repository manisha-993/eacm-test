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
import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * This is used to reset a single attribute, user must have the lock
 * @author Wendy Stimpson
 */
//$Log: ResetSingleAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class ResetSingleAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param ed
	 */
	public ResetSingleAction(EditController ed) {
		super(RESETONEATTR_ACTION,KeyEvent.VK_Z, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("cnclS.gif"));
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
			//must have a single attribute selected and it must have changes
			ok = editCtrl.getCurrentEditor().hasChangedAttrSelected();
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
		String attrKey = editCtrl.getCurrentEditor().getSelectionKey();
	
		// user wants to reset.. cancel any edit
		editCtrl.getCurrentEditor().cancelCurrentEdit();

		// this will reset selected attribute and restore focus to the first selected attr
		editCtrl.getCurrentEditor().rollbackSingle(attrKey);
	}
}

