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

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.PivotMtrxTableModel;
import com.ibm.eacm.table.RSTTable;


/*********************************************************************
 * pivot the table, if MtrxTable, it will also pivot the CrossTable
 * @author Wendy Stimpson
 */
//$Log: PivotAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class PivotAction extends EACMAction
{
	private RSTTable table = null;
	private static final long serialVersionUID = 1L;
	private static String PIVOT = Utils.getResource(MTRX_PIVOT);
	private static String UNPIVOT = Utils.getResource(MTRX_UNPIVOT);
	public PivotAction(RSTTable _tab) { 
		super(MTRX_PIVOT,KeyEvent.VK_P, Event.CTRL_MASK);
		setTable(_tab);
	}
	public void setTable(RSTTable nt){
		table = nt;
	    setEnabled(true);
		// change the menu text
	    if (table!=null){
	    	setMenuText();
	    }
	}
	
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && table!=null && 
				table.getModel().getRowCount()>0 && table.getModel().getColumnCount()>1);//look at model, rows may be filtered
	}

	public void dereference(){
		super.dereference();
		table = null;
	}
	private void setMenuText(){
		if (table.getModel() instanceof PivotMtrxTableModel){
			putValue(Action.NAME, UNPIVOT); 
		}else{
			putValue(Action.NAME, PIVOT);
		}	
	}
	public void actionPerformed(ActionEvent e) {	
		if (table instanceof MtrxTable){
			((MtrxTable)table).pivot();
		}else if (table instanceof CrossTable){
			((CrossTable)table).pivot();
		}
		// change the menu text
		setMenuText();
	}
}