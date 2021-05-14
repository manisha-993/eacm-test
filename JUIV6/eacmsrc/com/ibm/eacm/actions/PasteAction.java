//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.tabs.DataActionPanel;


/*************
* 
* paste from clipboard
* @author Wendy Stimpson
*/
//$Log: PasteAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class PasteAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private RSTTable table = null;
	private FormTable formTable = null;
	private DataActionPanel parent = null;

	public PasteAction(RSTTable nt) {
		this(null,nt);
	}
	public PasteAction(DataActionPanel panel, RSTTable nt) {
		super(PASTE_ACTION,KeyEvent.VK_V, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("paste.gif"));

	    parent = panel;
		setTable(nt);
	}
	public void setTable(RSTTable nt){
		table = nt;
		formTable = null;
	    setEnabled(true);
	}
	/**
	 * called when formeditor is loaded
	 * @param nt
	 */
	public void setFormTable(FormTable nt){
		formTable = nt;
		table = null;
	    setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		//check cell is editable
		boolean iseditable = false;
		if(table!=null){
			iseditable = table.canPaste();
		}else if(formTable != null){
			iseditable = formTable.canPaste();
		}

		return iseditable;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		parent = null;
		table = null;
		formTable = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(parent!=null && formTable ==null){ // dont do this for form, editor loses focus and then no current editor
			parent.disableActionsAndWait();
		}
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(table!=null){
					table.paste();
					table.repaint();
				}else if (formTable !=null){
					formTable.paste();
					formTable.repaint();
				}
				if(parent!=null && formTable==null){
					parent.enableActionsAndRestore();
				}
			}	
		});
	} 
}