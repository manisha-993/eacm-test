/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * an assistive class for processing accessible tables.
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EAccessibleTable.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/10/03 16:39:59  tony
 * updated accessibility.
 *
 *
 */
package com.elogin;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EAccessibleTable {
	/**
     * updateContext
     * @param _ac
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    AccessibleContext updateContext(AccessibleContext _ac, int _row, int _col);
	/**
     * isAccessibleCellEditable
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    boolean isAccessibleCellEditable(int _row, int _col);
	/**
     * isAccessibleCellLocked
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    boolean isAccessibleCellLocked(int _row, int _col);
	/**
     * getAccessibleValueAt
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    Object getAccessibleValueAt(int _row, int _col);
	/**
     * getAccessibleColumnNameAt
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    String getAccessibleColumnNameAt(int _col);
	/**
     * getAccessibleRowNameAt
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    String getAccessibleRowNameAt(int _row);
	/**
     * convertAccessibleColumn
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    int convertAccessibleColumn(int _col);
	/**
     * convertAccessibleRow
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    int convertAccessibleRow(int _row);
	/**
     * isCellFocused
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    boolean isCellFocused(int _row, int _col);
	/**
     * getAccessibleCell
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    String getAccessibleCell(int _row, int _col);
}
