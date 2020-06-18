//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnDataResultSet.java,v $
// Revision 1.17  2015/05/28 15:40:42  wangyul
// Roll back code to 1.15, remove update for timestamp issue
//
// Revision 1.15  2002/03/06 00:54:14  dave
// more syntax for Search API
//
// Revision 1.14  2001/09/26 16:30:44  dave
// changes for Search
//
// Revision 1.13  2001/08/22 16:53:01  roger
// Removed author RM
//
// Revision 1.12  2001/06/26 23:12:16  roger
// Remove rows contained message
//
// Revision 1.11  2001/06/05 21:04:12  roger
// Good gosh, will it ever end?
//
// Revision 1.10  2001/06/05 20:57:46  roger
// Sheesh2 error!
//
// Revision 1.9  2001/06/05 20:51:24  roger
// Sheesh error
//
// Revision 1.8  2001/06/04 22:32:15  roger
// Row, rows
//
// Revision 1.7  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.6  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.5  2001/03/16 15:52:23  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * @version @date
 * @see ReturnData
 * @see ReturnDataRow
 * @see ReturnDataResultSetGroup
 */
public final class ReturnDataResultSet extends ReturnData {

  // Class constants
  private static final String INDENTATION = new String("  ");
  // Instance variables
  private int m_iPosition = -1;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
    ReturnDataRow rdr = null;
    ReturnDataResultSet rdrs = new ReturnDataResultSet();

    for (int i = 0; i < 4; i++) {
      rdr = new ReturnDataRow();
      for (int j = 0; j < 5; j++) {
        rdr.addElement(String.valueOf((i + 1) * 100 + j + 1));
      }
      rdrs.addElement(rdr);
    }

    System.out.println("The entire result set:");
    rdrs.display(System.out);
    System.out.println(rdrs.getColumn(0, 1) + " should be '102'");
    System.out.println(rdrs.getColumn(3, 3) + " should be '404'");
    System.out.println("RowCount = " + rdrs.getRowCount());
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSet</code> object
 */
  public ReturnDataResultSet() {
    super();

    this.m_strReturnDataName = new String("returndataresultset");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSet</code> object with the specified initial capacity
 */
  public ReturnDataResultSet(int iInitialCapacity) {
    super(iInitialCapacity);

    this.m_strReturnDataName = new String("returndataresultset");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSet</code> object with the specified initial capacity and capacity increment.
 */
  public ReturnDataResultSet(int iInitialCapacity, int iCapacityIncrement) {
    super(iInitialCapacity, iCapacityIncrement);

    this.m_strReturnDataName = new String("returndataresultset");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSet</code> object containing a single <code>ReturnDataRow</code>
 */
  public ReturnDataResultSet(ReturnDataRow rdr) {
    super();

    this.m_strReturnDataName = new String("returndataresultset");
    this.addElement(rdr);
  }

/**
 * Creates a stored procedure ReturnDataResultSet object from a SQL ResultSet
 * @exception SQLException
 */
  public ReturnDataResultSet(ResultSet rs) throws SQLException {
    this(rs, "returndataresultset");
  }

/**
 * Creates a stored procedure ReturnDataResultSet object from a SQL ResultSet
 * @exception SQLException
 */
  public ReturnDataResultSet(ResultSet rs, String strResultSetName) throws SQLException {

    // ReturnDataRow containing columns (as elements) for the current row
    ReturnDataRow rdrRow = null;
    String strReturnData = null;

    // Assign the name
    this.m_strReturnDataName = new String(strResultSetName);

    // How many cols in output ResultSet?
    int iCols = rs.getMetaData().getColumnCount();

    while (rs.next()) {
      rdrRow = new ReturnDataRow(iCols); // initialize a new ReturnDataRow with exact capacity needed for all columns
      // Process all columns
      for (int iCol = 1; iCol <= iCols; iCol++) {
        strReturnData = rs.getString(iCol);
        if (strReturnData != null) {
//          D.ebug(D.EBUG_SPEW, "before rtrim '" + strReturnData + "'");
          strReturnData = Unicode.rtrim(strReturnData);
//          D.ebug(D.EBUG_SPEW, "after  rtrim '" + strReturnData + "'");
        }
        rdrRow.addElement(strReturnData);// place each column into the ReturnDataRow
      }
      this.addElement(rdrRow);           // place the ReturnDataRow into the current ReturnDataResultSet
    }
//    D.ebug(D.EBUG_DETAIL, strResultSetName + " resultset contains " + ((this.size()==1) ? "" + this.size() + " row" : "" + this.size() + " rows"));
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ReturnDataResultSet.java,v 1.17 2015/05/28 15:40:42 wangyul Exp $");
  }

/**
 * Display the <code>ReturnDataResultSet</code> structure
 */
  public void display(PrintStream out) {
    super.display(out, INDENTATION);
  }

/**
 * Return the number of rows in the result set
 * @return Return the number of rows in the result set
 */
  public final int getRowCount() {
    return this.size();
  }

/**
 * Return the content for the specified row and column as a <code>String</code>
 */
  public final String getColumn(int iRow, int iCol) {
    ReturnDataRow rdr = (ReturnDataRow) this.elementAt(iRow);

    return rdr.getColumn(iCol);
  }
  
  public final int getColumnInt(int iRow, int iCol) {
  	ReturnDataRow rdr = (ReturnDataRow)this.elementAt(iRow);
  	
  	return Integer.valueOf(rdr.getColumn(iCol)).intValue();
  }
  
  public final String getColumnDate(int iRow, int iCol) {
  	ReturnDataRow rdr = (ReturnDataRow)this.elementAt(iRow);
  	return rdr.getColumn(iCol).replace(' ','-').replace(':','.');
  }
  
/**
 * Return the content for the specified column in all rows as a <code>Vector</code>
 */
  public final Vector getColumnFromAllRowsAsVector(int iCol) {
    ReturnDataRow rdr = null;
    Vector vReturn = new Vector(this.size());

    for (int i = 0; i < this.size(); i++) {
      rdr = (ReturnDataRow) this.elementAt(i);
      vReturn.addElement(rdr.getColumn(iCol));
    }
    return vReturn;
  }

/**
 * Return the content for the specified column in all rows as an array of <code>String</code>
 */
  public final String[] getColumnFromAllRowsAsArray(int iCol) {
    ReturnDataRow rdr = null;
    String[] straReturn = new String[this.size()];

    for (int i = 0; i < this.size(); i++) {
      rdr = (ReturnDataRow) this.elementAt(i);
      straReturn[i] = rdr.getColumn(iCol);
    }
    return straReturn;
  }

/**
 * Return the specified row as a <code>ReturnDataRow</code>
 */
  public final ReturnDataRow getRow(int iRow) {
    return (ReturnDataRow) this.elementAt(iRow);
  }

/**
 * Return the current row as a <code>ReturnDataRow</code>
 */
  public final ReturnDataRow getRow() {
    return this.getRow(m_iPosition);
  }
}
