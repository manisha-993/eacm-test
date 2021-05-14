//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*************
* 
* this action is used for next arrow button in RecordToggle for vertical editor and form editor
* @author Wendy Stimpson		
*/
//$Log: NextEditAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class NextEditAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	/**
	 * @param ec
	 */
	public NextEditAction(EditController ec) {
		super(NEXTEDIT_ACTION,KeyEvent.VK_N, Event.CTRL_MASK + Event.SHIFT_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("right.gif"));
		editCtrl = ec;
		setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = editCtrl.getCurrentEditorType()==EditController.VERT_EDITOR ||
			editCtrl.getCurrentEditorType()==EditController.FORM_EDITOR;
		if(ok){
			int rowcnt = editCtrl.getRecordToggle().getViewRowCount();
			ok = rowcnt>1 && rowcnt > editCtrl.getRecordToggle().getCurrentIndex()+1;
		}

		return ok;
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
		editCtrl.disableActionsAndWait();
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editCtrl.setNextEdit(); 
				editCtrl.enableActionsAndRestore();	
				editCtrl.getCurrentEditor().requestFocusInWindow();
			}
		});
	} 
}