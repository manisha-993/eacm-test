//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*************
* 
* this action changes the editor type to form
* @author Wendy Stimpson		
*/
//$Log: SetFormEditorAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class SetFormEditorAction extends EACMAction 
{
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	private int editorType = EditController.FORM_EDITOR;

	/**
	 * @param ec
	 */
	public SetFormEditorAction(EditController ec) {
		super(FORMEDITOR_ACTION,KeyEvent.VK_3, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("form.gif"));
		editCtrl = ec;
	}

	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		boolean ok = editCtrl.isFormCapable() && editCtrl.getCurrentEditorType()!=editorType;
		if(ok){
			GridEditor ge = editCtrl.getGridEditor();
			if(ge!=null){
				// must have at least one item in the grid to load into a vertical editor
				ok = ge.getJTable().getRowCount()>0;
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
		String attrkey = editCtrl.getCurrentEditor().getSelectionKey();
		editCtrl.setEditor(editorType,attrkey);
	} 
}