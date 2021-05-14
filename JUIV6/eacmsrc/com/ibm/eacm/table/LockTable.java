//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;


import java.awt.FontMetrics;

import javax.swing.table.TableCellRenderer;

import com.ibm.eacm.rend.LabelRenderer;
import com.ibm.eacm.rend.LineRenderer;

import COM.ibm.eannounce.objects.LockItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;



/**
 * lock action table
 * @author Wendy Stimpson
 */
//$Log: LockTable.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class LockTable extends RSTTable
{
	private static final long serialVersionUID = 1L;
	private LabelRenderer foundRend = null;

	public LockTable(RowSelectableTable table, Profile p) {
		super(table,p);
		init();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.BaseTable#init()
	 */
	protected void init() {
		super.init();
		foundRend = new LabelRenderer();
		foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
		setDefaultRenderer(Object.class, new LineRenderer());
	}
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.lockTable";
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
	/**
	 * InactiveItems from selected rows
	 * @return
	 */
	public LockItem[] getLockItems() {
		int[] rows = getSelectedRows(); // these are from the view
		LockItem[] wi = new LockItem[rows.length];
		for (int r=0;r<rows.length;++r) {
			wi[r] = (LockItem)((RSTTableModel)getModel()).getRowEAN(this.convertRowIndexToModel(rows[r]));
		}
		return wi;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable2#getRowHeight(java.awt.FontMetrics, int, java.lang.String)
	 */
	protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
		return fm.getHeight();
	}
	/**
	 * getUIPrefKey
	 *
	 * @return
	 */
	public String getUIPrefKey() {
		return "LOCK";
	}

    /* (non-Javadoc)
     * lock table is never editable, stop it here
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
