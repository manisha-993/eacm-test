//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ColumnTag.java,v $
// Revision 1.7  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2001/08/22 16:52:51  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 15:39:43  roger
// Misc clean up
//
// Revision 1.4  2001/03/21 00:01:06  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:19  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * Used to associate output from a stored procedure with an XML tag
 * @version 12-01-1999-2000 23:56
 * @see ColumnTags
 */
public final class ColumnTag {

// Instance variables
  private int m_iResultSet = 0;
  private int m_iColumn = 0;
  private String m_strPrefix = null;
  private String m_strSuffix = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
    //int iRS = 1;
    //int iCol = 3;
    ColumnTag ctaThis[] = { new ColumnTag(1, 1, "this", "/this"), new ColumnTag(1, 2, "that", "/that"),
                            new ColumnTag(1, 3, "the", "/the"), new ColumnTag(1, 4, "other", "/other"), new ColumnTag() };
    System.out.println(ctaThis[0].getVersion());
    for (int i = 0; i < ctaThis.length; i++) {
      System.out.println(ctaThis[i]);
    }
    System.out.println(ctaThis[2].getResultSet() + " " + ctaThis[2].getColumn() + " " + ctaThis[2].getPrefix() + " " + ctaThis[2].getSuffix());
  }

/**
 * Construct a <code>ColumnTag</code> for the specified result set# and column#
 */
  public ColumnTag(int iRS, int iCol, String strPrefix, String strSuffix) {

    m_iResultSet = iRS;
    m_iColumn = iCol;
    m_strPrefix = strPrefix;
    m_strSuffix = strSuffix;
  }

/**
 * Construct a default <code>ColumnTag</code> for the specified result set = 0 and column = 0
 */
  public ColumnTag() {
    this(0, 0, "<nothing>", "</nothing>");
  }

/**
 * The <code>ColumnTag</code> as a String
 * @return <code>ColumnTag</code> definition as a <code>String</code>
 */
  public final String toString() {
    return new String("Result Set: " + m_iResultSet + " Column: " + m_iColumn + " Prefix: '" + m_strPrefix + "' Suffix: '" + m_strSuffix + "'");
  }

/**
 * Retrieve the result set number associated with the <code>ColumnTag</code>
 * @return the result set# for this <code>ColumnTag</code>
 */
  public final int getResultSet() {
    return m_iResultSet;
  }

/**
 * Retrieve the column number associated with the <code>ColumnTag</code>
 * @return the column# for this <code>ColumnTag</code>
 */
  public final int getColumn() {
    return m_iColumn;
  }

/**
 * Retrieve the XML tag prefix associated with the <code>ColumnTag</code>
 * @return the XML tag prefix for this <code>ColumnTag</code>
 */
  public final String getPrefix() {
    return m_strPrefix;
  }

/**
 * Retrieve the XML tag suffix associated with the <code>ColumnTag</code>
 * @return the XML tag suffix for this <code>ColumnTag</code>
 */
  public final String getSuffix() {
    return m_strSuffix;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ColumnTag.java,v 1.7 2008/01/31 22:55:00 wendy Exp $");
  }
}
