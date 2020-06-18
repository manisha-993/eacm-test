//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnDataResultSetGroup.java,v $
// Revision 1.7  2001/08/22 16:53:01  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:24  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;

/**
 * @version @date
 * @see ReturnData
 * @see ReturnDataRow
 * @see ReturnDataResultSet
 */
public final class ReturnDataResultSetGroup extends ReturnData {

  // Class constants
  private static final String INDENTATION = new String("");

  // Instance variables
  private int m_iPosition = -1;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {

    ReturnDataRow rdr = null;
    ReturnDataResultSet rdrs = new ReturnDataResultSet();
    ReturnDataResultSetGroup rdrsg = new ReturnDataResultSetGroup();

    for (int k = 0; k < 4; k++) {
      for (int i = 0; i < 3; i++) {
        rdr = new ReturnDataRow();

        for (int j = 0; j < 2; j++) {
          rdr.addElement(String.valueOf((i + 1) * Math.pow(10, k) + j + 1));
        }

        rdrs.addElement(rdr);
      }

      rdrsg.addElement(rdrs);

      rdrs = new ReturnDataResultSet();
    }

    System.out.println("The entire result set group:");
    rdrsg.display(System.out);
    System.out.println("col 1 of rs 1 " + rdrsg.getColumnFromAllRowsAsVector(1, 1));
    System.out.println("col 0 of rs 3 " + rdrsg.getColumnFromAllRowsAsVector(3, 0));
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSetGroup</code> object
 */
  public ReturnDataResultSetGroup() {
    super();

    this.m_strReturnDataName = new String("returndataresultsetgroup");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSetGroup</code> object with the specified initial capacity
 */
  public ReturnDataResultSetGroup(int iInitialCapacity) {
    super(iInitialCapacity);

    this.m_strReturnDataName = new String("returndataresultsetgroup");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSetGroup</code> object with the specified initial capacity and capacity increment.
 */
  public ReturnDataResultSetGroup(int iInitialCapacity, int iCapacityIncrement) {
    super(iInitialCapacity, iCapacityIncrement);

    this.m_strReturnDataName = new String("returndataresultsetgroup");
  }

/**
 * Creates a stored procedure <code>ReturnDataResultSetGroup</code> object containing a single <code>ReturnDataResultSet</code>
 */
  public ReturnDataResultSetGroup(ReturnDataResultSet rdrs) {
    super();

    this.m_strReturnDataName = new String("returndataresultsetgroup");
    this.addElement(rdrs);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ReturnDataResultSetGroup.java,v 1.7 2001/08/22 16:53:01 roger Exp $");
  }

/**
 * Display the ReturnDataResultSetGroup structure
 */
  public final void display(PrintStream out) {
    super.display(out, INDENTATION);
  }

/**
 * Return the number of result sets in the result set group
 * @return Return the number of result sets in the result set group
 */
  public final int getResultSetCount() {
    return this.size();
  }

/**
 * Retrieve the specified result set as a <code>ReturnDataResultSet</code>
 */
  public final ReturnDataResultSet getResultSet(int iResultSet) {
    return (ReturnDataResultSet) this.elementAt(iResultSet);
  }

/**
 * Retrieve the current result set as a <code>ReturnDataResultSet</code>
 */
  public final ReturnDataResultSet getResultSet() {
    return this.getResultSet(m_iPosition);
  }

///**
// * Set the position to the specified result set and row
// * @deprecated
// */
//  public final void setPosition(int iResultSet, int iRow) {
//    super.setPosition(iResultSet);
//    ReturnDataRow rdr = ((ReturnDataRow)this.elementAt(m_iPosition));
//    rdr.setPosition(iRow);
//  }
//
///**
// * Set the position to the specified result set, row and column
// * @deprecated
// */
//  public final void setPosition(int iResultSet, int iRow, int iCol) {
//    super.setPosition(iResultSet);
//    ReturnDataRow rdr = (ReturnDataRow)this.elementAt(m_iPosition);
//    rdr.setPosition(iRow);
//    ReturnData rdc = (ReturnData)rdr.elementAt(iCol);
//    rdc.setPosition(iCol);
//  }
//

/**
 * Retrieve the content for the specified result set, row, and column as a <code>String</code>
 */
  public final String getColumn(int iResultSet, int iRow, int iCol) {
    ReturnDataResultSet rdrs = (ReturnDataResultSet) this.elementAt(iResultSet);
    ReturnDataRow rdr = (ReturnDataRow) rdrs.elementAt(iRow);

    return (String) rdr.elementAt(iCol);
  }

/**
 * Retrieve the content for the specified result set, and column of all rows as a <code>Vector</code>
 */
  public final Vector getColumnFromAllRowsAsVector(int iResultSet, int iCol) {
    ReturnDataResultSet rdrs = (ReturnDataResultSet) this.elementAt(iResultSet);

    return rdrs.getColumnFromAllRowsAsVector(iCol);
  }

/**
 * Retrieve the content for the specified result set, and column of all rows as an array of <code>String</code>
 */
  public final String[] getColumnFromAllRowsAsArray(int iResultSet, int iCol) {
    ReturnDataResultSet rdrs = (ReturnDataResultSet) this.elementAt(iResultSet);

    return rdrs.getColumnFromAllRowsAsArray(iCol);
  }
}
