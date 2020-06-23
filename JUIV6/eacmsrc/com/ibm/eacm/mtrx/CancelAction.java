//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.*;


/*********************************************************************
 * Cancel any updates made in the matrix table
 * @author Wendy Stimpson
 */
//$Log: CancelAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class CancelAction extends EACMAction implements PropertyChangeListener
{
	private RSTTable table = null;
	private static final long serialVersionUID = 1L;
	public CancelAction(RSTTable _tab) {
		super(MTRX_CANCEL,KeyEvent.VK_Z, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("cncl.gif"));
		setTable(_tab);
	}
	public void setTable(RSTTable nt){
		table = nt;
	    setEnabled(true);
	}
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && table!=null && table.hasChanges());
	}
	public void dereference(){
		super.dereference();
		table = null;
	}
	public void actionPerformed(ActionEvent e) {
		if (table instanceof MtrxTable){
			((MtrxTable)table).rollbackMatrix();
		}else if (table instanceof CrossTable){
			((CrossTable)table).rollbackMatrix();
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY))	{
			setEnabled(table.hasChanges());
		}
	}
}