/*
 * Created on Dec 28, 2004
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */
package com.ibm.transform.oim.eacm.xalan;

/**
 * Defines a simple interface for Table like data.
 * Classes that implement this are not expected to do any data pulls until one of these methods is executed.
 *
 * <pre>
 * $Log: Table.java,v $
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:03  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public interface Table {
    /**
     * Gets the value of the cell specified by row and column.
     * If the row or column is out of bounds it should return the empty string.
     *
     * @param row
     * @param column
     * @return String
     */
    Object get(int row, int column);

    /**
     * Gets the column header specified by column.
     * If the column is out of bounds it should return the empty string.
     *
     * @param column
     * @return String
     */

    String getColumnHeader(int column);
    /**
     * Get the number of columns in the table.
     *
     * @return int
     */
    int getColumnCount();

    /**
     * Get the number of rows in the table.
     *
     * @return int
     */
    int getRowCount();
}
