//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ABRStatus.java,v $
// Revision 1.8  2013/09/11 19:54:23  wendy
// add method to access attribute value
//
// Revision 1.7  2008/01/22 18:17:14  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.5  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.4  2005/01/24 21:58:57  dave
// starting to clean up per new IBM standards
//
// Revision 1.3  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import java.io.Serializable;


/**
 * A class which defines the ABRStatus for TaskMaster
 * @version @date
 */
public final class ABRStatus implements Serializable {

  // Class constants
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  // Class variables
  // Instance variable
  /**
    * FIELD
     */
    protected String m_strAttributeValue = null;
  /**
    * FIELD
     */
    protected String m_strLongDescription = null;


  /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
  public static void main(String _args[]) {
    ABRStatus pkg1 = new ABRStatus("0010", "X");  //$NON-NLS-1$  //$NON-NLS-2$
    ABRStatus pkg2 = new ABRStatus("0010", "Z");  //$NON-NLS-1$  //$NON-NLS-2$
    ABRStatus pkg3 = new ABRStatus("0011", "X");  //$NON-NLS-1$  //$NON-NLS-2$

    System.out.println(
      pkg1
        + " compared to "  //$NON-NLS-1$
        + pkg2
        + " equals = "  //$NON-NLS-1$
        + pkg1.equals(pkg2)
        + " s/b true");  //$NON-NLS-1$
    System.out.println(
      pkg2
        + " compared to "  //$NON-NLS-1$
        + pkg3
        + " equals = "  //$NON-NLS-1$
        + pkg2.equals(pkg3)
        + " s/b false");  //$NON-NLS-1$
    System.out.println(
      pkg3
        + " compared to "  //$NON-NLS-1$
        + pkg1
        + " equals = "  //$NON-NLS-1$
        + pkg3.equals(pkg1)
        + " s/b false");  //$NON-NLS-1$
    System.out.println(
      pkg1
        + " compared to "  //$NON-NLS-1$
        + pkg1
        + " equals = "  //$NON-NLS-1$
        + pkg1.equals(pkg1)
        + " s/b true");  //$NON-NLS-1$
  }

  /**
     * Construct a <code>ABRStatus</code> object
     *
     * @param _strAttributeValue
     * @param _strLongDescription
     */
  public ABRStatus(String _strAttributeValue, String _strLongDescription) {
    this.m_strAttributeValue = _strAttributeValue;
    this.m_strLongDescription = _strLongDescription;
  }

  /**
   * Return the status description of the <code>ABRStatus</code>
   *
   * @return String
   */
  public final String getStatusDescription() {
	  return m_strLongDescription;
  }

  /**
   * Return the attribute value of the <code>ABRStatus</code>
   *
   * @return String
   */
  public final String getAttributeValue() {
	  return m_strAttributeValue;
  }

  /**
     * Compare <code>ABRStatus</code> values
     *
     * @param _obj
     * @return boolean
     */
  public final boolean equals(Object _obj) {
    if ((_obj != null) && (_obj instanceof ABRStatus)) {
      return m_strAttributeValue.equals(
        ((ABRStatus) _obj).m_strAttributeValue);
    }

    return false;
  }

  /**
     * Return the <code>ABRStatus</code> as a <code>String</code>
     *
     * @return String
     */
  public final String toString() {
    return m_strAttributeValue + "-" + m_strLongDescription;  //$NON-NLS-1$
  }

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final static String getVersion() {
    return "$Id: ABRStatus.java,v 1.8 2013/09/11 19:54:23 wendy Exp $";  //$NON-NLS-1$
  }
}
