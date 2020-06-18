//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: VersionException.java,v $
// Revision 1.7  2001/08/22 16:53:05  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * A class of exceptions used by the login transaction to indicate failure
 * @version @date
 */
public final class VersionException extends Exception {

/**
 * Constructs a <code>VersionException</code> with no specified detail message
 */
  public VersionException() {
    super();
  }

/**
 * Constructs a <code>VersionException</code> with the specified detail message
 */
  public VersionException(String s) {
    super(s);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: VersionException.java,v 1.7 2001/08/22 16:53:05 roger Exp $");
  }
}
