//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareWaitTimeoutException.java,v $
// Revision 1.7  2001/08/22 16:52:59  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:22  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 * @see MiddlewareException
 */
public final class MiddlewareWaitTimeoutException extends MiddlewareException {

/**
 * Constructs a <code>MiddlewareWaitTimeoutException</code> with no specified detail message
 */
  public MiddlewareWaitTimeoutException() {
  }

/**
 * Constructs a <code>MiddlewareWaitTimeoutException</code> with the specified detail message
 */
  public MiddlewareWaitTimeoutException(String s) {
    super(s);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: MiddlewareWaitTimeoutException.java,v 1.7 2001/08/22 16:52:59 roger Exp $");
  }
}
