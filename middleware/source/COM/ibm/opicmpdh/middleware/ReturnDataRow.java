//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnDataRow.java,v $
// Revision 1.7  2001/08/22 16:53:02  roger
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
 * @see ReturnDataResultSet
 * @see ReturnDataResultSetGroup
 */
public final class ReturnDataRow extends ReturnData {

  // Class constants
  private static final String INDENTATION = new String("    ");

  // Instance variables
  private int m_iPosition = -1;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {

//    ReturnDataRow rdr = new ReturnDataRow(10, 1);
//
//    rdr.addElement("start");
//    rdr.addElement("test1");
//    rdr.addElement("test2");
//    rdr.addElement("test3");
//    rdr.addElement("end");
//    rdr.display(System.out);
//
//    System.out.println("'" + rdr.getColumn(0) + "' should be 'start'");
//    System.out.println("'" + rdr.getColumn(4) + "' should be 'end'");
//    rdr.next();
//    rdr.next();
//    System.out.println("test " + rdr.getColumn());
//    System.out.println("test " + rdr.getColumn(1));
//    rdr.next();
//    rdr.next();
//    rdr.next();
//    rdr.next();
//    System.out.println(rdr.getColumn());
//    rdr.first();
//    System.out.println(rdr.getColumn());
//    System.out.println(rdr.getColumnCount());
//    System.out.println(rdr.getColsAsVector());
//    String[] straCols = new String[rdr.size()];
//    straCols = rdr.getColsAsArray();
//    for (int i = 0; i < straCols.length; i++)
//      System.out.println(straCols[i]);
//
//    ReturnDataRow rd = new ReturnDataRow();
//
//    rd.display(System.out);
//      T.est(rd.isBeforeFirst(), " 1A. isBeforeFirst on empty vector", true);
//      T.est(rd.isAfterLast(), " 1B. isAfterLast on empty vector", true);
//      T.est(rd.isFirst(), " 1C. isFirst on empty vector", false);
//      T.est(rd.isLast(), " 1D. isLast on empty vector", false);
//      T.est((rd.next()), " 1E. next()", false);
//      T.est((rd.previous()), " 1F. previous()", false);
//    rd.setPosition(1000);
//      T.est(rd.isBeforeFirst(), " 2A. isBeforeFirst on empty vector after setPosition", true);
//      T.est(rd.isAfterLast(), " 2B. isAfterLast on empty vector after setPosition", true);
//      T.est(rd.isFirst(), " 2C. isFirst on empty vector after setPosition", false);
//      T.est(rd.isLast(), " 2D. isLast on empty vector after setPosition", false);
//      T.est((rd.next()), " 2E. next()", false);
//      T.est((rd.previous()), " 2F. previous()", false);
//    rd.addElement("test1");
//    rd.display(System.out);
//      T.est(rd.isBeforeFirst(), " 3A. isBeforeFirst after addElement", true);
//      T.est(rd.isAfterLast(), " 3B. isAfterLast after addElement", false);
//      T.est(rd.isFirst(), " 3C. isFirst after addElement", false);
//      T.est(rd.isLast(), " 3D. isLast after addElement", false);
//      T.est((rd.next()), " 3E. next()", true);
//      T.est((rd.previous()), " 3F. previous()", false);
//    rd.setPosition(100);
//      T.est(rd.isBeforeFirst(), " 4A. isBeforeFirst after addElement and setPosition", false);
//      T.est(rd.isAfterLast(), " 4B. isAfterLast after addElement and setPosition", false);
//      T.est(rd.isFirst(), " 4C. isFirst after addElement and setPosition", true);
//      T.est(rd.isLast(), " 4D. isLast after addElement and setPosition", true);
//    rd.addElement("test2");
//    rd.display(System.out);
//      T.est(rd.isFirst(), " 5A. isFirst after 2nd addElement", true);
//      T.est(rd.isLast(), " 5B. isLast after 2nd addElement", false);
//    rd.first();
//      T.est(rd.isFirst(), " 6A. isFirst after first()", true);
//      T.est(rd.isLast(), " 6B. isLast after first()", false);
//      T.est((rd.previous()), " 6C. previous()", false);
//      T.est((rd.next()), " 6D. next()", true);
//    rd.last();
//      T.est(rd.isFirst(), " 7A. isFirst after last()", false);
//      T.est(rd.isLast(), " 7B. isLast after last()", true);
//      T.est((rd.next()), " 7C. next()", false);
//      T.est((rd.previous()), " 7D. previous()", true);
//    rd.addElement("test3");
//    rd.display(System.out);
//      T.est(rd.isFirst(), " 8A. isFirst after 3rd addElement", true);
//      T.est(rd.isLast(), " 8B. isLast after 3rd addElement", false);
//    rd.first();
//      T.est(rd.isFirst(), " 9A. isFirst after first()", true);
//      T.est(rd.isLast(), " 9B. isLast after first()", false);
//      T.est((rd.next()), " 9C. next()", true);
//      T.est((rd.next()), " 9D. next()", true);
//      T.est((rd.next()), " 9E. next()", false);
//      T.est((rd.next()), " 9F. next()", false);
//      T.est(rd.isFirst(), " 9G. isFirst after next()s", false);
//      T.est(rd.isLast(), " 9H. isLast after next()s", true);
//      T.est((rd.previous()), " 9I. previous()", true);
//      T.est((rd.previous()), " 9J. previous()", true);
//      T.est((rd.previous()), " 9K. previous()", false);
//      T.est((rd.previous()), " 9L. previous()", false);
//      T.est(rd.isFirst(), " 9M. isFirst after previous()s", true);
//      T.est(rd.isLast(), " 9N. isLast after previous()s", false);
//    rd.beforeFirst();
//      T.est(rd.isBeforeFirst(), "10A. isBeforeFirst after beforeFirst", true);
//      T.est(rd.isAfterLast(), "10B. isAfterLast after beforeFirst", false);
//      T.est(rd.isFirst(), "10C. isFirst after beforeFirst", false);
//      T.est(rd.isLast(), "10D. isLast after beforeFirst", false);
//    rd.afterLast();
//      T.est(rd.isBeforeFirst(), "11A. isBeforeFirst after afterLast", false);
//      T.est(rd.isAfterLast(), "11B. isAfterLast after afterLast", true);
//      T.est(rd.isFirst(), "11C. isFirst after afterLast", false);
//      T.est(rd.isLast(), "11D. isLast after afterLast", false);
//    rd.last();
//      T.est(rd.isAfterLast(), "12A. isAfterLast after Last", false);
//    rd.setPosition(rd.getPosition() + 1);
//      T.est(rd.isAfterLast(), "12A. isAfterLast after Last", false);
//
  }

/**
 * Creates a stored procedure <code>ReturnDataRow</code> object
 */
  public ReturnDataRow() {
    super();

    this.m_strReturnDataName = new String("returndatarow");
  }

/**
 * Creates a stored procedure <code>ReturnDataRow</code> object with the specified initial capacity
 */
  public ReturnDataRow(int iInitialCapacity) {
    super(iInitialCapacity);

    this.m_strReturnDataName = new String("returndatarow");
  }

/**
 * Creates a stored procedure <code>ReturnData</code> object with the specified initial capacity and capacity increment.
 */
  public ReturnDataRow(int iInitialCapacity, int iCapacityIncrement) {
    super(iInitialCapacity, iCapacityIncrement);

    this.m_strReturnDataName = new String("returndatarow");
  }

/**
 * Creates a stored procedure <code>ReturnDataRow</code> object
 */
  public ReturnDataRow(String strColValue) {
    super();

    this.m_strReturnDataName = new String("returndatarow");
    this.addElement(strColValue);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ReturnDataRow.java,v 1.7 2001/08/22 16:53:02 roger Exp $");
  }

/**
 * Display the ReturnDataRow structure
 */
  public final void display(PrintStream out) {
    super.display(out, INDENTATION);
  }

/**
 * Return the number of columns in the row
 * @return Return the number of columns in the row
 */
  public final int getColumnCount() {
    return this.size();
  }

/**
 * Return the column content for the specified column as a <code>String</code>
 * @return The column content for the specified column as a <code>String</code>
 */
  public final String getColumn(int iCol) {
    return (String) this.elementAt(iCol);
  }

/**
 * Return the column content for the current column as a <code>String</code>
 * @return The column content for the current column as a <code>String</code>
 */
  public final String getColumn() {
    return this.getColumn(m_iPosition);
  }

/**
 * Return the row content as a <code>Vector</code>
 * @return The row content as a <code>Vector</code>
 */
  public final Vector getColsAsVector() {
    return (Vector) this;
  }

/**
 * Return the row content as a <code>String</code> array
 * @return The row content as a <code>String</code> array
 */
  public final String[] getColsAsArray() {
    String[] straReturn = new String[this.size()];

    for (int i = 0; i < this.size(); i++) {
      straReturn[i] = this.getColumn(i);
    }
//    this.toArray(straReturn);
    return straReturn;
  }
}
