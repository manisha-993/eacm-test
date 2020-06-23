//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.table;

import COM.ibm.eannounce.objects.*;
import javax.swing.*;

/**
 * this maps a RowSelectableTable for a FilterGroup to the java table model
 * @author Wendy Stimpson
 */
//$Log: FilterTableModel.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class FilterTableModel extends RSTTableModel {
	private static final long serialVersionUID = 1L;
 
	/**
	 * @param table
	 */
	public FilterTableModel(RSTTable table){
		super(table);
	}
	
    /**
     * called when celleditor is done editing
     * @param att
     * @param r
     * @param c
     */
    protected void putValueAt(Object att, int r, int c)  { 
        try {
        	getRSTable().put(r, c, att);
            fireTableCellUpdated(r, c);
        } catch (EANBusinessRuleException bre) { // cant really happen, no rules for filters
        	com.ibm.eacm.ui.UI.showException(null,bre);
        	bre.dereference();
        }
    }
    /**
     * add a new filteritem
     * @param c
     */
    protected void addRow(JComponent c) { 
    	boolean success = getRSTable().addRow();
    	if (success){
    	
            // All row data in the table has changed, listeners should discard any state 
            // or must notify for each individual row
            //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
    		refresh();
    	}else{
    		com.ibm.eacm.ui.UI.showErrorMessage(c,"Error adding row, see log for details");
    	}
    }

    /**
     * remove selected filter items
     * @param r
     */
    protected void removeRows(int[] r) {
        for (int i = (r.length - 1); i >= 0; i--) {
        	getRSTable().removeRow(r[i]);
        }
        getRSTable().refresh();
    	
        // All row data in the table has changed, listeners should discard any state 
        // or must notify for each individual row
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        updatedModel();    
    }
    /**
     * remove all filter items
     */
    protected void removeAllItems() {
        int ii = getRSTable().getRowCount();
        for (int i = (ii - 1); i >= 0; --i) {
        	getRSTable().removeRow(i);
        }
        getRSTable().refresh();
        // All row data in the table has changed, listeners should discard any state 
        // or must notify for each individual row
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        updatedModel();
    }

	/**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int r,int c) {
		if (getRSTable() != null) {
			return getRSTable().isEditable(r,c);
		}
		return false;
	}
}