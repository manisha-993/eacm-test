//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareException.java,v $
// Revision 1.8  2009/11/03 18:39:48  wendy
// new string() not needed
//
// Revision 1.7  2001/08/22 16:52:57  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:08  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:21  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * The basic exception used by middleware
 * @version @date
 * @see MiddlewareWaitTimeoutException
 */
public class MiddlewareException extends Exception {

/**
 * Constructs a <code>MiddlewareException</code> with no specified detail message
 */
  public MiddlewareException() {
    this("no detail message");
  }

/**
 * Constructs a <code>MiddlewareException</code> with the specified detail message
 */
  public MiddlewareException(String s) {
    super(s);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public String getVersion() {
    return "$Id: MiddlewareException.java,v 1.8 2009/11/03 18:39:48 wendy Exp $";
  }
}
