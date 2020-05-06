/*
 * Created on Dec 29, 2004
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */
package com.ibm.transform.oim.eacm.xalan.table;

import com.ibm.transform.oim.eacm.xalan.Table;


/**
 * This class implements the Table interface using a String Array.
 *
 * <pre>
 * $Log: SimpleTable.java,v $
 * Revision 1.2  2006/01/26 15:24:30  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:13:55  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class SimpleTable implements Table {
    private String[][] table = new String[][] {
        {">row one & column one<",">row one & column two<"},  //$NON-NLS-2$  //$NON-NLS-1$
        {">row two & column one<",">row two & column two<"}  //$NON-NLS-2$  //$NON-NLS-1$
    };

    private String[] header = new String[] {
        "\"Column One Header\"", "\"Column Two Header\""  //$NON-NLS-1$  //$NON-NLS-2$
    };

    /**
     * Default constructor. A table with two rows and two columns.
     */
    public SimpleTable() {
    }

    /**
     * Creates a table with the specified number of rows and columns and initializes the cells and headers to the empty string.
     * If columnCount or rowCount are less than 1 a new table will not be allocated.
     * Only the default 2x2 table will be available.
     * @param rowCount
     * @param columnCount
     */
    public SimpleTable(int rowCount, int columnCount) {
        if (rowCount < 1 || columnCount < 1) {
            System.err.println("TestTable(int,int): Cannot create table with less than one row or one column. Default table will be used.");  //$NON-NLS-1$
            return;
        }
        table = new String[rowCount][columnCount];
        header = new String[columnCount];
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                table[r][c] = "";  //$NON-NLS-1$
            }
        }
        for (int c = 0; c < columnCount; c++) {
            header[c] = "";  //$NON-NLS-1$
        }
    }

    /**
     * Set the cell specified by row and column to the value.
     * If value is null or row or column are out of bounds the cell will remain unchanged.
     *
     * @param row
     * @param column
     * @param value
     * @return true if cell value changed. false otherwise.
     */
    public boolean set(int row, int column, String value) {
        if (value == null) {
            System.err.println("TestTable.set(int,int,String): value is null");  //$NON-NLS-1$
            return false;
        }
        if (row < 0 || row >= table.length) {
            System.err.println("TestTable.set(int,int,String): row is out of bounds");  //$NON-NLS-1$
            return false;
        }
        if (column < 0 || column >= header.length) {
            System.err.println("TestTable.set(int,int,String): col is out of bounds");  //$NON-NLS-1$
            return false;
        }

        table[row][column] = value;
        return true;
    }

    /**
     * Sets the specified column header to the value.
     * If the column is out of bounds the header will not be set
     * @param column
     * @param value
     * @return true if column header set. false otherwise.
     */
    public boolean setColumnHeader(int column, String value) {
        if (value == null) {
            System.err.println("TestTable.set(int,int,String): value is null");  //$NON-NLS-1$
            return false;
        }
        if (column < 0 || column >= header.length) {
            System.err.println("TestTable.set(int,int,String): col is out of bounds");  //$NON-NLS-1$
            return false;
        }
        header[column] = value;
        return true;
    }

    /**
     * Gets the value in the cell specified by the row and column.
     * If the row or column are out of bounds the empty string is returned.
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column) {
        String returnValue = "";  //$NON-NLS-1$
        if ((row < table.length && row >= 0) && (column < header.length && column >= 0)) {
            returnValue = table[row][column];
        }
        else {
            System.err.println("TestTable.get(int,int): Index out of bounds empty string returned");  //$NON-NLS-1$
        }
        return returnValue;
    }

    /**
     * Gets the Column header specified by column.
     * If column is out of bounds the null string is returned.
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     * @param column
     */
    public String getColumnHeader(int column) {
        String returnValue = "";  //$NON-NLS-1$
        if ((column < header.length && column >= 0)) {
            returnValue = header[column];
        }
        else {
            System.err.println("TestTable.getColumnHeader(int): Index out of bounds empty string returned");  //$NON-NLS-1$
        }
        return returnValue;
    }

    /**
     * Gets the number of columns in the table
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        return header.length;
    }

    /**
     * Gets the number of rows in the table
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        return table.length;
    }
}
