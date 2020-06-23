//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*************
* 
* this action is used for removing new rows action 
* @author Wendy Stimpson		
*/
//$Log: RemoveNewAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class RemoveNewAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	/**
	 * constructor
	 * @param ec
	 */
	public RemoveNewAction(EditController ec) {
		super(REMOVENEW_ACTION);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("waste.gif"));
		editCtrl = ec;
		setEnabled(false);
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		// depends on selection of a new row
		ok = editCtrl.getCurrentEditor()!=null &&
		editCtrl.getCurrentEditor().hasNewSelected();
		if(ok){
			if(editCtrl.getCurrentEditor() instanceof FormEditor){
				ok = editCtrl.getEgRstTable().getRowCount()>1; // cant remove the last entity in a form
			}
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
		editCtrl.getCurrentEditor().terminateEditor();
		
		editCtrl.disableActionsAndWait();
		
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// remove any selected new rows
				editCtrl.removeNewRows();
				editCtrl.enableActionsAndRestore();	
				editCtrl.getCurrentEditor().requestFocusInWindow();
			}
		});
	} 
}