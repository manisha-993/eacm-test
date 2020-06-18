//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnData.java,v $
// Revision 1.8  2008/01/31 22:54:59  wendy
// Cleanup RSA warnings
//
// Revision 1.7  2001/08/22 16:53:00  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:23  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;

/**
 * @version @date
 * @see ReturnDataRow
 * @see ReturnDataResultSet
 * @see ReturnDataResultSetGroup
 */
public abstract class ReturnData extends Vector implements Serializable, Cloneable {

  // Class constants
  private static final String INDENTATION = new String("      ");
  // Instance variables
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  //private int m_iPosition = -1;
  protected String m_strReturnDataName = new String("returndata");

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
  }

/**
 * Creates a stored procedure <code>ReturnData</code> object
 */
  public ReturnData() {
    super();
  }

/**
 * Creates a stored procedure <code>ReturnData</code> object with the specified initial capacity
 */
  public ReturnData(int iInitialCapacity) {
    super(iInitialCapacity);
  }

/**
 * Creates a stored procedure <code>ReturnData</code> object with the specified initial capacity and capacity increment.
 */
  public ReturnData(int iInitialCapacity, int iCapacityIncrement) {
    super(iInitialCapacity, iCapacityIncrement);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public String getVersion() {
    return new String("$Id: ReturnData.java,v 1.8 2008/01/31 22:54:59 wendy Exp $");
  }

/**
 * Display the <code>ReturnData</code> structure
 */
  protected void display(PrintStream out, String strIndentation) {
    out.println(strIndentation + "[ (" + this.m_strReturnDataName + ")");

    // Process all elements at this level
    for (int i = 0; i < this.size(); i++) {
      Object o1 = this.elementAt(i);

      if (o1 instanceof String) {
        // Output an individual element
        out.println(strIndentation + "  " + o1);
      } else if (o1 instanceof ReturnDataResultSetGroup) {
        ((ReturnDataResultSetGroup) o1).display(out);
      } else if (o1 instanceof ReturnDataResultSet) {
        ((ReturnDataResultSet) o1).display(out);
      } else if (o1 instanceof ReturnDataRow) {
        ((ReturnDataRow) o1).display(out);
      } else {
        // Something other than String and ReturnDataX
        out.println(strIndentation + "  " + o1);
      }
    }
    out.println(strIndentation + "]");
  }

/**
 * Display the <code>ReturnData</code> structure
 */
  public void display(PrintStream out) {
    this.display(out, INDENTATION);
  }

/**
 * Return the name of this <code>ReturnData</code> structure
 */
  public final String getName() {
    return m_strReturnDataName;
  }

///**
// * Position to the first element in the list
// * @deprecated
// */
//  public final void first() {
//    m_iPosition = (this.isEmpty()) ? -1 : 0;
//  }
//
///**
// * Position to the last element in the list
// * @deprecated
// */
//  public final void last() {
//    m_iPosition = (this.isEmpty()) ? -1 : this.size() - 1;
//  }
//
///**
// * Position to the next element in the list
// * @deprecated
// */
//  public final boolean next() {
//    ++m_iPosition;
//    boolean bReturn = ((!this.isEmpty()) || (m_iPosition >= 0 && m_iPosition <= this.size() - 1));
//    // Force the position UP to first
//    m_iPosition = (this.isEmpty()) ? -1 : Math.max(m_iPosition, 0);
//    // And DOWN to last
//    m_iPosition = (this.isEmpty()) ? -1 : Math.min(m_iPosition, this.size() - 1);
//    return bReturn;
//  }
//
///**
// * Position to the previous element in the list
// * @deprecated
// */
//  public final boolean previous() {
//    --m_iPosition;
//    boolean bReturn = ((!this.isEmpty()) || (m_iPosition >= 0 && m_iPosition <= this.size() - 1));
//    // Force the position UP to first
//    m_iPosition = (this.isEmpty()) ? -1 : Math.max(m_iPosition, 0);
//    // And DOWN to last
//    m_iPosition = (this.isEmpty()) ? -1 : Math.min(m_iPosition, this.size() - 1);
//    return bReturn;
//  }
//
///**
// * Position after the last element in the list
// * @deprecated
// */
//  public final void afterLast() {
//    m_iPosition = this.size() + 1;
//  }
//
///**
// * Position before the first element in the list
// * @deprecated
// */
//  public final void beforeFirst() {
//    m_iPosition = -1;
//  }
//
///**
// * Position to the specified element in the list
// * @deprecated
// */
//  public final void setPosition(int intPosition) {
//    m_iPosition = intPosition;
//    // Force the position to be within the actual bounds of the vector
//    m_iPosition = Math.max(m_iPosition, 0);
//    m_iPosition = Math.min(m_iPosition, this.size() - 1);
//  }
//
///**
// * Retrieve the current position
// * @deprecated
// */
//  protected final int getPosition() {
//    return m_iPosition;
//  }
//
///**
// * Is the position the last element in the list?
// * @deprecated
// */
//  public final boolean isLast() {
//    return (!this.isEmpty() && m_iPosition == this.size() - 1);
//  }
//
///**
// * Is the position the first element in the list?
// * @deprecated
// */
//  public final boolean isFirst() {
//    return (!this.isEmpty() && m_iPosition == 0);
//  }
//
///**
// * Is the position after the last element in the list?
// * @deprecated
// */
//  public final boolean isAfterLast() {
//    return (this.isEmpty() || m_iPosition > this.size() - 1);
//  }
//
///**
// * Is the position before the first element in the list?
// * @deprecated
// */
//  public final boolean isBeforeFirst() {
//    return (this.isEmpty() || m_iPosition < 0);
//  }
//

/**
 * Returns a clone of the object
 */
  public Object clone() {
    return super.clone();
  }
}
