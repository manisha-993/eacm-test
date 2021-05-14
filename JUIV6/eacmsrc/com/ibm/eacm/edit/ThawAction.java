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
* this action is used for thaw action in grideditor
* @author Wendy Stimpson		
*/
//$Log: ThawAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class ThawAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	
	public ThawAction(EditController ec) {
		super(THAW_ACTION);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("thaw.gif"));
		editCtrl = ec;
		setEnabled(false);
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean	ok = editCtrl.getCurrentEditorType()==EditController.GRID_EDITOR &&
				((GridEditor)editCtrl.getCurrentEditor()).isFrozen();
		return ok;
	}
	public void dereference(){
		super.dereference();
		editCtrl = null;
	}
	public void actionPerformed(ActionEvent e) {
		((GridEditor)editCtrl.getCurrentEditor()).thaw();
		setEnabled(false);
	} 
}