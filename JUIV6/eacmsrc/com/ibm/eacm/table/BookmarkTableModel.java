//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.table;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import javax.swing.event.*;

import com.ibm.eacm.objects.EACMGlobals;

/**
 * this represents a BookmarkGroupTable as a java tablemodel
 * @author Wendy Stimpson
 */
// $Log: BookmarkTableModel.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class BookmarkTableModel extends javax.swing.table.AbstractTableModel implements EACMGlobals { 
	private static final long serialVersionUID = 1L;
	private BookmarkGroupTable ctm = null;

	/**
     * release memory
     */
    protected void dereference() {
    	if (ctm!=null){
    		ctm.dereference();
    		ctm = null;
		}
	}

	/**
     * getTable
     * @return
     */
    protected BookmarkGroupTable getTable() {
		return ctm;
	}
	/**
     * updateModel with new pdh model
     * @param ctm2
     */
    protected void updateModel(BookmarkGroupTable ctm2) {
     	if (ctm!=null){
    		ctm.dereference();
    	}
	
		this.ctm = ctm2;														

	    // All row data in the table has changed, listeners should discard any state 
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        fireTableChanged(new TableModelEvent(this));
	}
	/**
     * getRowKey
     * @param i
     * @return
     */
    protected String getRowKey(int i) {
		if (ctm != null) {
			return ctm.getRowKey(i);
		}
		return null;
	}
	/**
	 * get row count from pdh model
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
		if (ctm != null) {
			return ctm.getRowCount();
		}
		return 0;
	}

	/**
	 * get column count from pdh model
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
		if (ctm != null) {
			return ctm.getColumnCount();
		}
		return 0;
	}

	/**
	 * get column name from pdh model
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int i) {
		if (ctm != null) {
			return ctm.getColumnHeader(i);
		}
		return null;
	}

	/**
	 * get class from pdh model
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class<?> getColumnClass(int i) {
		if (ctm != null) {
			return ctm.getColumnClass(i);
		}
		return String.class;
	}

	/**
	 * get the value from the pdh model
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int r, int c) {
		if (ctm != null) {
			return ctm.getValueAt(r,c);
		}
		return null;
	}

	/**
     * get item for this row
     * @param row
     * @return
     */
    protected BookmarkItem getRowItem(int row) {
		return (BookmarkItem)ctm.getRow(row);
	}
												
    /**
     * this is done on the event thread after the row is deleted
     * @param row
     */
    protected void fireRowDeleted(int row){
    	fireTableRowsDeleted(row,row);
    }

    /**
     * dump
     * @param brief
     * @return
     */
    protected String dump(boolean brief) {
    	StringBuffer sb = new StringBuffer();
		sb.append("TableModel.getRowCount(): " + getRowCount() + RETURN);
		sb.append("TableModel.getColumnCount(): " + getColumnCount() + RETURN);
		if (!brief) {
			int rr = getRowCount();
			int cc = getColumnCount();
			int lastCol = (cc - 1);
			for (int r=0;r<rr;++r) {
				sb.append("Row(" + r + "): ");
				for (int c=0;c<cc;++c) {
					Object o = getValueAt(r,c);
					if (o != null) {
						sb.append(o.toString());
					}
					if (c != lastCol) {
						sb.append(", ");
					}
				}
				sb.append(RETURN);
			}
		}
		return sb.toString();
	}
    
    /**
     * called on event thread after new bookmarkitem has been added to pdh and table
     * also called when an item has been renamed, rename is a delete followed by an add
     */
    protected void updatedTbl(){
		// a row may have been removed as well as added, 
	    // All row data in the table has changed, listeners should discard any state 
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        fireTableChanged(new TableModelEvent(this));
    }


//  ====================================================================
//  Filterable support
	/**
     * getFilterGroup
     * @return
     */
    protected FilterGroup getFilterGroup(Profile prof) {
    	FilterGroup fg = null;
		if (ctm != null) {
			fg = ctm.getFilterGroup();
			if (fg == null) {
				try {
					fg = new FilterGroup(null,prof,ctm);
					setFilterGroup(fg);
				} catch (MiddlewareRequestException mre) {
					mre.printStackTrace();
				}
			}
		}
		return fg;
	}

	/**
     * setFilterGroup
     * @param group
     */
    protected void setFilterGroup(FilterGroup group) {
		if (ctm != null) {
			ctm.setFilterGroup(group);
		}
	}
}
