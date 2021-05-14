//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.table;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;


/**
*  this represents a RowSelectableTable as a java tablemodel supporting Matrix pivot action
*
*  added one more column to rst to use java sort and filter support
*  @author Wendy Stimpson
*/
//$Log: PivotMtrxTableModel.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class PivotMtrxTableModel extends MtrxTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * create a pivot matrix tablemodel with a max column name length
	 * @param maxlen
	 */
	public PivotMtrxTableModel(RSTTable table, int maxlen){
		super(table,maxlen);
	}

	/* (non-Javadoc)
	 * get the EANFoundation object for this key from rst row
	 * @see com.ibm.eacm.table.MtrxTableModel#getEANByKey(java.lang.String)
	 */
	protected EANFoundation getEANByKey(String key) {
		return getRSTable().getRow(key); // col is the row in the rst
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.MtrxTableModel#getColumnKey(int)
	 */
	public String getColumnKey(int i) {
		if(i==0){
			return "MATRIX"; // row header info
		}

		return getRSTable().getRowKey(i-1); // col is the row in the rst
	}
	/**
     * get item for this row
     * row is really the column
     * @param row
     * @return
     */
    protected EANFoundation getRowItem(int row) {
		return getRSTable().getColumn(row);
	}
    /* (non-Javadoc)
     * must add 1 for matrix row info added in column 0
     * @see com.ibm.eacm.table.RSTTableModel2#getColumnCount()
     */
    public int getColumnCount() {
		return super.getRowCount()+1;
	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTableModel2#getRowCount()
     */
    public int getRowCount() {
		return super.getColumnCount()-1; // remove 1 added by mtrxtblmodel
	}

    /**
     * get the title for the data in this table
     * @return
     */
    public String getTableTitle() {
        return super.getTableTitle()+ " (Pivot)";
    }

	/* (non-Javadoc)
	 * get the column name without truncating
	 * @see com.ibm.eacm.table.MtrxTableModel#getFullColumnName(int)
	 */
	protected String getFullColumnName(int col){
		String name = null;
		if (getRSTable()!=null){ // rst is not set when tablemodel is first initialized
			if(col==0){
				name = getTableTitle();
			}else{
				name = getRSTable().getRowHeaderMatrix(col-1); // column header is now the row info
			}
		}
		return name;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.MtrxTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return getTruncName(getFullColumnName(col)); // truncate if needed
	}

	/* (non-Javadoc)
	 * get column index for this string, it is rst.row
	 * @see com.ibm.eacm.table.MtrxTableModel#getColumnIndex(java.lang.String)
	 */
	public int getColumnIndex(String s) {
		return getRSTable().getRowIndex(s);
	}
	/* (non-Javadoc)
	 * allow for matrix info in column 0, class comes from the rst row
	 * @see com.ibm.eacm.table.MtrxTableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int i) {
		if (getRSTable() != null && i!=0) {
			return getRSTable().getRowClass(i-1);
		}
		return String.class;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.MtrxTableModel#getEANObject(int, int)
	 */
	protected EANFoundation getEANObject(int r, int c) {
		if (c==0){
			return null;
		}
		return getRSTable().getEANObject(c-1,r);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.MtrxTableModel#getValueAt(int, int, boolean)
	 */
	public Object getValueAt(int r, int c, boolean longDesc) {
		if(c==0){
			return getRSTable().getColumnHeader(r);
		}


		return getRSTable().getMatrixValue(c-1, r);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTableModel2#getRowByKey(java.lang.String)
     */
    public EANFoundation getRowByKey(String key) {
    	if (key!=null){
    		return getRSTable().getColumn(key); //pivot
    	}else{
    		System.err.println("PivotMtrxTableModel.getRowByKey key is null!!");
    		Thread.dumpStack();
    	}
        return null;
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.MtrxTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int r, int c) {
		if(c>0){
			getRSTable().putMatrixValue(c-1, r, aValue);
			// notify listens that table has changed
			fireTableCellUpdated(r, c);
		}
	}

    /* (non-Javadoc)
     * allow for col0 info, swap r, c and base class will do -1 on col again
     * @see com.ibm.eacm.table.MtrxTableModel#getCellLock(int, int, java.awt.Component)
     */
    public boolean getCellLock(int r, int c, Profile prof) {
    	return super.getCellLock(c-1, r+1, prof);
    }
    /* (non-Javadoc)
     * allow for col0 info, swap r, c and base class will do -1 on col again
     * @see com.ibm.eacm.table.MtrxTableModel#getLockGroup(int, int)
     */
    public LockGroup getLockGroup(int r, int c){
    	if(c==0){
    		return null;
    	}
    	return super.getLockGroup(c-1, r+1);
    }

}
