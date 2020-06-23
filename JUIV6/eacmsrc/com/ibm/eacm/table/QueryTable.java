//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import javax.swing.table.TableCellRenderer;

import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 *  this represents  the query view RST as a Java Table
 * @author Wendy Stimpson
 */
// $Log: QueryTable.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class QueryTable extends RSTTable
{
	private static final long serialVersionUID = 1L;
	private LabelRenderer foundRend = null;
	
	public QueryTable(RowSelectableTable table, Profile p) {
		super(table,p);
		init();
	}
	
	protected void init() {
		super.init();
		foundRend = new LabelRenderer();
		foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
	}  
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.query";
	}
    /**
     * getCellRenderer
     *
     * @param row
     * @param column
     * @return
     */
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (isFound(row, column)) {
            return foundRend;
        }

        return super.getCellRenderer(row, column);
    }
    /**
     * dereference
     */
    public void dereference() {
        super.dereference();
        
        foundRend.dereference();
        foundRend = null;
    }
    
	public String getUIPrefKey() {	
		return "QUERY";
	}
    /* (non-Javadoc)
     * query table is never editable, stop it here
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int r, int c) {
        return false;
    }
}
