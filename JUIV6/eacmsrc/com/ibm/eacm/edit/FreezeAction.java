//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;


import java.awt.event.ActionEvent;
import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*************
* 
* this action is used for freeze action in grideditor
* @author Wendy Stimpson	
*/
//$Log: FreezeAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class FreezeAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	private Action thawaction = null;
	
	/**
	 * constructor
	 * @param ec
	 * @param act
	 */
	public FreezeAction(EditController ec, Action act) {
		super(FREEZE_ACTION);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("frze.gif"));
		editCtrl = ec;
		thawaction = act;
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		return
			editCtrl.getCurrentEditorType()==EditController.GRID_EDITOR &&
			((GridEditor)editCtrl.getCurrentEditor()).getSelectedColumnCount()==1;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		editCtrl = null;
		thawaction = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		((GridEditor)editCtrl.getCurrentEditor()).freeze();
		thawaction.setEnabled(true);
	} 
}