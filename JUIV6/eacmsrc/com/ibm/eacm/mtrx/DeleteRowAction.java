//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.mtrx;


import com.ibm.eacm.actions.EACMAction;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;

/*********************************************************************
 * This is used for delete a row of relators in CrossTable
 * @author Wendy Stimpson
 */
//$Log: DeleteRowAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class DeleteRowAction extends EACMAction implements FocusListener
{
	private static final long serialVersionUID = 1L;
	private CrossTable crssTable = null;
	private boolean hasFocus = true;
	private MatrixActionBase actionTab = null;

	public DeleteRowAction(CrossTable _tab,MatrixActionBase mab) {
		super(MTRX_DELETEROW,KeyEvent.VK_DELETE, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("0row.gif"));
		setTable(_tab);
		actionTab = mab;
	}
	public void setTable(CrossTable nt){
		crssTable = nt;
	    setEnabled(true);
	}

	public void setEnabled(boolean newValue) {
		if (actionTab!=null && actionTab.isWaiting()){
			newValue=false; // dont enable if some action is running
		}
		super.setEnabled(hasFocus && newValue && crssTable!=null
				&& crssTable.hasSelectedDataColumns() && (!crssTable.showRelAttr()) && !Utils.isPast(crssTable.getProfile()));
	}
	
	public void dereference(){
		super.dereference();
		crssTable = null;
	}
	public void actionPerformed(ActionEvent e) {
		crssTable.cancelCurrentEdit();
		crssTable.verticalAdjust("0");
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

