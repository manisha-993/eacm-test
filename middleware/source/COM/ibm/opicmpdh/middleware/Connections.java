//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Connections.java,v $
// Revision 1.12  2001/09/12 17:52:14  roger
// Profile changes
//
// Revision 1.11  2001/08/22 16:52:52  roger
// Removed author RM
//
// Revision 1.10  2001/06/27 15:41:48  roger
// Changed trace statements to SPEW mode
//
// Revision 1.9  2001/06/26 16:04:25  roger
// Remove redundant find
//
// Revision 1.8  2001/06/20 16:06:45  roger
// More connectionID in debug output
//
// Revision 1.7  2001/06/16 05:13:45  roger
// D.ebug and literal changes
//
// Revision 1.6  2001/06/16 04:46:43  roger
// Changed some literals and D.ebug levels
//
// Revision 1.5  2001/03/26 16:33:20  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:19  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.util.Enumeration;
import java.util.Hashtable;
import COM.ibm.opicmpdh.middleware.ConnectionInformation;

/**
 * @version 2000-11-16-16.00.30.620000
 * @see ConnectionInformation
 */
public final class Connections {

  // Class constants
  // Class variables
  // Instance variables
  Hashtable hashConnections = new Hashtable();

/**
 * Main method which performs a simple test of this class
 * @exception Exception
 */
  public static void main(String[] args) throws Exception {
  }

/**
 * Construct the <code>Connections</code> object
 */
  public Connections() {
  }

/**
 * Record the call
 */
  // needs sessionid + userid parms !!!
  public void recordCall(String _strHost, String _strMethod) {
    ConnectionInformation ciCurrent = findHost(_strHost);

    if (ciCurrent == null) {
      ciCurrent = addHost(_strHost);
    }
    ciCurrent.setLastMethod(_strMethod);
  }

/**
 * Find the entry for the specified host
 */
  public ConnectionInformation findHost(String _strHost) {
    ConnectionInformation ciResult = (ConnectionInformation) hashConnections.get(_strHost);

    if (ciResult == null) {
      D.ebug(D.EBUG_SPEW, "ConnectionInformation for '" + _strHost + "'" + " NOT found");
    } else {
      D.ebug(D.EBUG_SPEW, "ConnectionInformation for '" + _strHost + "'" + " found");
    }
    return ciResult;
  }

/**
 * Put the entry for the specified host
 */
  public ConnectionInformation addHost(String _strHost) {
    ConnectionInformation ciResult = new ConnectionInformation();

    D.ebug(D.EBUG_SPEW, "adding ConnectionInformation for '" + _strHost + "'");
    ciResult.setHost(_strHost);
    hashConnections.put(_strHost, ciResult);
    return ciResult;
  }

/**
 * Get an enumeration into the elements
 */
  public Enumeration elements() {
    D.ebug(D.EBUG_SPEW, "has " + hashConnections.size() + " elements");
    return hashConnections.elements();
  }

/**
 * Show the state of the <code>Connections</code> object
 * @return a string representation of this object.
 */
  public String toString() {
    StringBuffer strbResult = new StringBuffer();
    return new String(strbResult);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: Connections.java,v 1.12 2001/09/12 17:52:14 roger Exp $");
  }
}
