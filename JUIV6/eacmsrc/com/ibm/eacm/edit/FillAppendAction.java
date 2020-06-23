//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.table.RSTTable;

/*************
* 
* collect more attributes for later fillpaste
*
* @author Wendy Stimpson
*/
//$Log: FillAppendAction.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class FillAppendAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private RSTTable table = null;

	/**
	 * @param nt
	 */
	public FillAppendAction(RSTTable nt) {
		super(FILLAPPEND_ACTION,KeyEvent.VK_F11, 0);
	
		setTable(nt);
	}
	/**
	 * @param nt
	 */
	public void setTable(RSTTable nt){
		table = nt;
	    setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(table!=null){
			// should be able to copy anything
			ok = table.getSelectedRowCount()>0;
		}
		return ok; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		table = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(table!=null){
			table.fillAppend();
		}
	} 
}