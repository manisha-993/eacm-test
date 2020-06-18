//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ColumnTags.java,v $
// Revision 1.6  2001/08/22 16:52:52  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 15:39:43  roger
// Misc clean up
//
// Revision 1.4  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:19  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * An array of <code>ColumnTag</code> with some helper methods
 * @version 12-01-1999-2000 23:56
 * @see ColumnTag
 */
public final class ColumnTags {

// Instance variables
  private ColumnTag[] m_ctaXML = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
    ColumnTag ctaThis[] = { new ColumnTag(1, 1, "this", "/this"), new ColumnTag(1, 2, "that", "/that"),
                            new ColumnTag(1, 3, "the", "/the"), new ColumnTag(1, 4, "other", "/other"), new ColumnTag() };
    ColumnTags ctsXML = new ColumnTags(ctaThis);
    System.out.println(ctsXML.getVersion());
    ctsXML.show();
    System.out.println(ctsXML.getPrefix(1, 1) + ctsXML.getSuffix(1, 1));
    System.out.println(ctsXML.getPrefix(1, 3) + ctsXML.getSuffix(1, 3));
    System.out.println(ctsXML.getPrefix(0, 0) + ctsXML.getSuffix(0, 0));
  }

/**
 * Construct a <code>ColumnTags</code> object from a list of <code>ColumnTag</code>s
 */
  public ColumnTags(ColumnTag[] ctTag) {
    m_ctaXML = ctTag;
  }

/**
 * Retrieve the XML prefix for the specified result set# and column#
 * @return the XML prefix for the specified result set# and column#
 */
  public final String getPrefix(int iRS, int iCol) {
    String strReturn = null;
    int i = -1;

    for (i = 0; i < m_ctaXML.length; i++) {
      if ((m_ctaXML[i].getResultSet() == iRS) && (m_ctaXML[i].getColumn() == iCol)) {
        break;
      }
    }

    if (i < m_ctaXML.length) {
      strReturn = new String(m_ctaXML[i].getPrefix());
    } else {
      strReturn = new String();
    }
    return strReturn;
  }

/**
 * Retrieve the XML suffix for the specified result set# and column#
 * @return the XML suffix for the specified result set# and column#
 */
  public final String getSuffix(int iRS, int iCol) {
    String strReturn = null;
    int i = -1;

    for (i = 0; i < m_ctaXML.length; i++) {
      if ((m_ctaXML[i].getResultSet() == iRS) && (m_ctaXML[i].getColumn() == iCol)) {
        break;
      }
    }
    if (i < m_ctaXML.length) {
      strReturn = new String(m_ctaXML[i].getSuffix());
    } else {
      strReturn = new String();
    }
    return strReturn;
  }

/**
 * Output the entire array to <code>System.out</code>
 */
  public final void show() {
    try {
      for (int i = 0; i < m_ctaXML.length; i++) {
        System.out.println(m_ctaXML[i]);
      }
    } catch (Exception x) {};
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ColumnTags.java,v 1.6 2001/08/22 16:52:52 roger Exp $");
  }
}
