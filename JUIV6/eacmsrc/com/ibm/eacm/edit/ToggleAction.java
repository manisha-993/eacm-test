//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.actions.EACMAction;


/*************
* 
* this action is used for toggling the split pane for the actions, not sure if it is really useful
* @author Wendy Stimpson	
*/
//$Log: ToggleAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class ToggleAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	/**
	 * constructor
	 * @param ec
	 */
	public ToggleAction(EditController ec) {
		super(TOGGLE_ACTION,KeyEvent.VK_T, Event.CTRL_MASK);
		editCtrl = ec;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		editCtrl = null;
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
		editCtrl.toggleSplit();
	} 
}