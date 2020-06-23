//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.ibm.eacm.actions.EACMAction;

/*********************************************************************
* This is used to deactivate the selected attr 
* @author Wendy Stimpson
*/
//$Log: DeactivateAttrAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class DeactivateAttrAction extends EACMAction implements PropertyChangeListener
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param ed
	 */
	public DeactivateAttrAction(EditController ed) {
		super(DEACTIVATEATTR_ACTION,KeyEvent.VK_DELETE, 0);
		editCtrl = ed;
		super.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		editCtrl = null;
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(editCtrl.getCurrentEditor()!=null){
			ok = editCtrl.getCurrentEditor().hasEditableAttrSelected();
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
		// user wants to deactivate.. cancel any edit
		editCtrl.getCurrentEditor().cancelCurrentEdit();

		//deactivate the attribute
		editCtrl.getCurrentEditor().deactivateAttribute();
	}
	
}

