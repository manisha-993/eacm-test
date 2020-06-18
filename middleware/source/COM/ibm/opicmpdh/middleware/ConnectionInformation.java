//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ConnectionInformation.java,v $
// Revision 1.10  2011/09/13 21:42:09  wendy
// remove unneeded new String()
//
// Revision 1.9  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2001/08/22 16:52:52  roger
// Removed author RM
//
// Revision 1.7  2001/06/29 18:04:41  roger
// Needed data type on lNow
//
// Revision 1.6  2001/06/29 16:19:58  roger
// Make information more useful
//
// Revision 1.5  2001/03/26 16:33:19  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:19  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;


/**
 * @version 2000-11-16-16.00.30.620000
 * @see ConnectionInformation
 */
public final class ConnectionInformation {

  // Class constants
  // Class variables
  // Instance variables
  private String m_strHost = "";
  private long m_lConnected = 0;
  private String m_strUserID = "unknown";
  private int m_iSessionID = -1;
  private String m_strLastMethod = "";
  private long m_lLastMethod = 0;
  private long m_lTransactionCount = 0;

/**
 * Main method which performs a simple test of this class
 * @exception Exception
 */
  public static void main(String[] args) throws Exception {
  }

/**
 * Construct the <code>ConnectionInformation</code> object
 */
  public ConnectionInformation() {
    this("");
  }

/**
 * Construct the <code>ConnectionInformation</code> object
 */
  public ConnectionInformation(String _strHost) {
    m_strHost = _strHost;
    m_lConnected = System.currentTimeMillis();
  }

/**
 * Set the Host property
 */
  public void setHost(String _strHost) {
    m_strHost = _strHost;
  }

/**
 * Set the SessionID property
 */
  public void setSessionID(int _iSessionID) {
    m_iSessionID = _iSessionID;
  }

/**
 * Set the Last Method property
 */
  public void setLastMethod(String _strLastMethod) {
    m_strLastMethod = _strLastMethod;
    m_lLastMethod = System.currentTimeMillis();
    ++m_lTransactionCount;
  }

/**
 * Get the Host property
 */
  public String getHost() {
    return m_strHost;
  }

/**
 * Get the Last Method property
 */
  public String getLastMethod() {
    return m_strLastMethod;
  }

/**
 * Get the Transaction Count property
 */
  public long getTransactionCount() {
    return m_lTransactionCount;
  }

/**
 * Show the state of the <code>ConnectionInformation</code> object
 * @return a string representation of this object.
 */
  public String toString() {
    StringBuffer strbResult = new StringBuffer();
    long lNow = System.currentTimeMillis();

    strbResult.append("Host: \"" + m_strHost + "\"");
    strbResult.append("; Connection Age: " + Stopwatch.format(lNow - m_lConnected));
    strbResult.append("; UserID: " + m_strUserID);
    strbResult.append("; SessionID: " + m_iSessionID);
    strbResult.append("; Last Method: " + m_strLastMethod);
    strbResult.append("; Last Method Time: -" + Stopwatch.format(lNow - m_lLastMethod));
    strbResult.append("; Transactions: " + m_lTransactionCount);
    return strbResult.toString();
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return "$Id: ConnectionInformation.java,v 1.10 2011/09/13 21:42:09 wendy Exp $";
  }
}
