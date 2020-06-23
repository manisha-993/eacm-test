//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.table;


import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 *  this represents a RowSelectableTable as a java tablemodel supporting Matrix action
 *
 * add one more column (0) to rst, column 0 has row header information, it is part of the table - so java
 * sort and filter can be used.
 * @author Wendy Stimpson
 */
//$Log: MtrxTableModel.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class MtrxTableModel extends RSTTableModel {
	private static final long serialVersionUID = 1L;

	private boolean useLongDesc = true;

	/**
	 * create a matrix tablemodel with a max column name length
	 *
	 * @param table
	 * @param maxlen
	 */
	public MtrxTableModel(RSTTable table,int maxlen){
		super(table,maxlen);
	}

	/**
	 * create a matrix tablemodel with a max column name length and specify use long desc or not
	 * used by featurematrix crosstable
	 * @param longdesc
	 * @param maxlen
	 */
	public MtrxTableModel(RSTTable table,boolean longdesc,int maxlen) {
		super(table,maxlen);
		useLongDesc = longdesc;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTableModel2#updateModel(COM.ibm.eannounce.objects.RowSelectableTable)
	 */
	protected void updateModel(RowSelectableTable ctm) {
		super.updateModel(ctm);
		if(getRSTable() != null){
			getRSTable().setLongDescription(useLongDesc);
		}
	}

	/**
	 * get the EANFoundation object for this column
	 * @param column
	 * @return
	 */
	protected EANFoundation getColumnEAN(int column) {
		if (column==0){
			column++;  // do not return MATRIX row header column
		}
		return getEANByKey(getColumnKey(column));
	}

	/**
	 * get the EANFoundation object for this key from rst column
	 * @param key
	 * @return
	 */
	protected EANFoundation getEANByKey(String key) {
		return getRSTable().getColumn(key);
	}

	/* (non-Javadoc)
	 * get key for this column, must skip column 0
	 * @see com.ibm.eacm.table.RSTTableModel2#getColumnKey(int)
	 */
	public String getColumnKey(int i) {
		if(i==0){
			return "MATRIX";
		}
		return getRSTable().getColumnKey(i-1);
	}

    /* (non-Javadoc)
     * must add 1 for matrix row info added in column 0
     * @see com.ibm.eacm.table.RSTTableModel2#getColumnCount()
     */
    public int getColumnCount() {
		return super.getColumnCount()+1;
	}

    /**
     * get the column name without truncating
     * @param col
     * @return
     */
    protected String getFullColumnName(int col){
		String name = null;
		if(col==0){
			if (getRSTable()!=null){ // rst is not set when tablemodel is initialized
				name = getTableTitle();
			}
		}else{
			name= super.getColumnName(col-1);
		}
		return name;
    }

	/* (non-Javadoc)
	 * column 0 is row header info
	 * @see com.ibm.eacm.table.RSTTableModel2#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return getTruncName(getFullColumnName(col)); // truncate if needed
	}

    /* (non-Javadoc)
     * allow for matrix info in column 0
     * @see com.ibm.eacm.table.RSTTableModel2#getColumnClass(int)
     */
    public Class<?> getColumnClass(int i) {
		if (i ==0) {
			return String.class;
		}
		return super.getColumnClass(i-1);
	}

	/* (non-Javadoc)
	 * allow for matrix info column 0
	 * @see com.ibm.eacm.table.RSTTableModel2#isCellLocked(int, int)
	 */
	public boolean isCellLocked(int r, int c, Profile prof) {
		if (c==0){
			return false;
		}
		return super.isCellLocked(r, c-1, prof);
	}

	/* (non-Javadoc)
	 * allow for matrix info column 0
	 * @see com.ibm.eacm.table.RSTTableModel2#getEANObject(int, int)
	 */
	protected EANFoundation getEANObject(int r, int c) {
		if (c==0){
			return null;
		}
		return getRSTable().getEANObject(r, c-1);
	}


	/**
	 * get around col0 adjust done in derived mtrxtablemodel
	 * @param r
	 * @param c
	 * @return
	 */
	protected EANFoundation getCellValue(int r, int c){
		return (EANFoundation)getValueAt(r, c);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTableModel2#getValueAt(int, int)
	 */
	public Object getValueAt(int r, int c){
		return getValueAt(r, c, false);
	}
	/* (non-Javadoc)
	 * allow for matrix info column 0
	 * @see com.ibm.eacm.table.RSTTableModel2#getValueAt(int, int, boolean)
	 */
	public Object getValueAt(int r, int c, boolean longDesc) {
		if(c==0){
			return getRSTable().getRowHeaderMatrix(r);
		}
		return getRSTable().getMatrixValue(r, c-1);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int r, int c) {
		if(c!=0){
			getRSTable().putMatrixValue(r, c-1, aValue);
			// notify listeners that table has changed
			fireTableCellUpdated(r, c);
		}
	}

    /**
     * rollbackMatrix - reset all changed values
     */
    protected void rollbackMatrix() {
    	getRSTable().rollbackMatrix();
    	updatedModel();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int r, int c) {
        return c>0 && Utils.isEditable(getRSTable(), parentTable.getProfile());  // check for dialed back timestamp and rst can be edited
    }
    /* (non-Javadoc)
     * do col-1 for info in col0
     * @see com.ibm.eacm.table.RSTTableModel2#getCellLock(int, int)
     */
    public boolean getCellLock(int r, int c, Profile prof) {
    	return super.getCellLock(r, c-1, prof);
    }
    /* (non-Javadoc)
     * do col-1 for info in col0
     * @see com.ibm.eacm.table.RSTTableModel2#getLockGroup(int, int)
     */
    public LockGroup getLockGroup(int r, int c){
    	if(c==0){
    		return null;
    	}
    	return super.getLockGroup(r, c-1);
    }

}
