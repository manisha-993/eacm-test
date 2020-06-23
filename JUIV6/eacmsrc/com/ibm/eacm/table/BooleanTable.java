//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.rend.BooleanRend;

import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;
/******************************************************************************
* This is used to display the search table with boolean column 0. 
* @author Wendy Stimpson
*/
// $Log: BooleanTable.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class BooleanTable extends RSTTable
{
	private static final long serialVersionUID = 1L;

	public BooleanTable(RowSelectableTable table,Profile prof) {
		super(table,prof); 
		init();
	}

	protected void init() {
		super.init();

		setDefaultRenderer(Boolean.class,new BooleanRend());
	}
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.booleantable";
	}
	protected void setSortKeys(){
		// must be done after the columns are setup in the model
		//The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
		List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // default to first column
		setSortKeys(sortKeys);
	}
	public void updateModel(RowSelectableTable rst, Profile prof) {
		super.updateModel(rst, prof);

		clearSelection();

		if (getRowCount() > 0) {
			setRowSelectionInterval(0,0);
		}
		int colCount = getColumnCount();
		if (colCount >= 2) {
			setColumnSelectionInterval(1,1);
		} else if (colCount > 0) {
			setColumnSelectionInterval(0,0);
		}
	}

	/**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     */
    public boolean editCellAt(int r, int c, EventObject e) {
		if (e instanceof MouseEvent) {
			if (c == 0) {
				toggleValue(r,0);
			} else if (((MouseEvent)e).getClickCount() == 2) {
				toggleValue(r,0);
			}
		} else if (e instanceof KeyEvent) {
			int code = ((KeyEvent)e).getKeyCode();
			if (code == KeyEvent.VK_SPACE) {
				toggleValue(r,0);
			}
		}
		return false;
	}

	private void toggleValue(int r, int c) {
		Object o = getValueAt(r,c);
		if (o instanceof Boolean) {
			setValueAt(new Boolean(!((Boolean)o).booleanValue()),r,c);	
			repaint();
		}
	}

	/**
     * hasSelection
     * @return
     */
    public boolean hasSelection() {
		for (int r=0;r< getRowCount();++r) {
			Object o = getValueAt(r,0);
			if (o instanceof Boolean) {
				if (((Boolean)o).booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}
    
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getContext()
     */
    protected String getContext() {
		return ".bool";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleAttributeType(int, int)
     */
    protected String getAccessibleAttributeType(int row, int col) {
		if (col == 1) {
			Object o = getValueAt(row,0);
			if (o != null && o instanceof Boolean) {
				if (((Boolean)o).booleanValue()) {
					return Utils.getResource("accessible.booleantable.selected");
				} else {
					return Utils.getResource("accessible.booleantable.unselected");
				}
			}
		}
		return "";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleValueAt(int, int)
     */
    protected Object getAccessibleValueAt(int row, int col) {
		return getValueAt(row,1);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleColumnNameAt(int)
     */
    protected String getAccessibleColumnNameAt(int col) {
		return "";
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleRowNameAt(int)
     */
    protected String getAccessibleRowNameAt(int row) {
		return "";
	}

	/**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     */
    public void changeSelection(int row, int col, boolean toggle, boolean extend) {
		if (getColumnCount() >= 2) {
			super.changeSelection(row,1,toggle,extend);
		} else {
			super.changeSelection(row,col,toggle,extend);
		}
	}
}