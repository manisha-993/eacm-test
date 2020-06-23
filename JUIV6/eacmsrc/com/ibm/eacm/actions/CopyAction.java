//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;


/*************
* 
* copy to clipboard
* @author Wendy Stimpson
*/
//$Log: CopyAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class CopyAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private BaseTable table = null;
	private FormTable formTable = null;

	/**
	 * @param nt
	 */
	public CopyAction(BaseTable nt) {
		super(COPY_ACTION,KeyEvent.VK_C, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("copy.gif"));
	
		setTable(nt);
	}
	/**
	 * @param nt
	 */
	public void setTable(BaseTable nt){
		table = nt;
		formTable=null;
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
		boolean ok = false;
		if(table!=null){
			// should be able to copy anything
			ok = table.getSelectedRowCount()>0;//==1;
			if (ok && (table instanceof MtrxTable || table instanceof CrossTable)){
				ok = table.getModel().getColumnCount()>1; // col0 is hidden
			}
		}else if(formTable != null){
			ok = formTable.getCurrentEditor() != null;
		}
		return ok; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		table = null;
		formTable = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(table!=null){
			table.copy();
		}else if(formTable != null){
			formTable.copy();
		}
	} 
}