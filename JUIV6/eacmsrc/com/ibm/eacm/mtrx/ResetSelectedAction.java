//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;


/*********************************************************************
 * If RELATTR is defined for a CrossTable, then the relator attribute is set to null
 * else this is used to set to 0 number of relators in a column in CrossTable
 * @author Wendy Stimpson
 */
//$Log: ResetSelectedAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class ResetSelectedAction extends EACMAction implements FocusListener
{
	private CrossTable crssTable = null;
	private static final long serialVersionUID = 1L;
	private boolean hasFocus = true;

	public ResetSelectedAction(CrossTable _tab) { 
		super(MTRX_RESETSEL,KeyEvent.VK_DELETE, Event.SHIFT_MASK);
		setTable(_tab);
	}
	public void setTable(CrossTable nt){
		crssTable = nt;
	    setEnabled(true);
	}
	
	public void setEnabled(boolean newValue) {
		super.setEnabled(hasFocus && newValue && crssTable!=null && crssTable.hasSelectedDataColumns()
				&& !Utils.isPast(crssTable.getProfile()));
	}
	
	public void dereference(){
		super.dereference();
		crssTable = null;
	}
	
	public void actionPerformed(ActionEvent e) {
		crssTable.cancelCurrentEdit();
		crssTable.resetSelected();
	}

	/* (non-Javadoc)
	 * this is needed when crosstable is used with a matrixtable, disable when matrixtable has focus
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		hasFocus = e.getSource() instanceof CrossTable;
		setEnabled(hasFocus);
	}
	public void focusLost(FocusEvent e) {}
}