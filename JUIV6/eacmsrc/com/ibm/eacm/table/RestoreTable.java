//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;

import javax.swing.table.TableCellRenderer;

import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.InactiveItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;



/**
 * restore action table
 * @author Wendy Stimpson
 */
//$Log: RestoreTable.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class RestoreTable extends RSTTable
{
	private static final long serialVersionUID = 1L;
	private LabelRenderer foundRend = null;
	
	public RestoreTable(RowSelectableTable table, Profile p) {
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
		return "accessible.restoreTbl";
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
	public InactiveItem[] getInactiveItems() {
		int[] rows = getSelectedRows(); // these are from the view
		InactiveItem[] wi = new InactiveItem[rows.length];
		for (int r=0;r<rows.length;++r) {
			wi[r] = (InactiveItem)((RSTTableModel)getModel()).getRowEAN(this.convertRowIndexToModel(rows[r]));
		}
		return wi;
	}

	/**
	 * getUIPrefKey
	 *
	 * @return
	 */
	public String getUIPrefKey() {
		return "RESTORE";
	}

    /* (non-Javadoc)
     * restore table is never editable, stop it here
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int r, int c) {
        return false;
    }
}

