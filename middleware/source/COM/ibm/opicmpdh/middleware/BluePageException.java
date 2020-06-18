//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BluePageException.java,v $
// Revision 1.2  2004/07/09 19:53:44  joan
// change to MiddlewareException
//
// Revision 1.1  2004/06/18 19:35:46  roger
// New
//
//

package COM.ibm.opicmpdh.middleware;

/**
 * A class of exceptions used by the BluePageEntry transaction to indicate failure
 * @version @date
 */
public class BluePageException extends MiddlewareException {

/**
 * Constructs a <code>BluePageException</code> with no specified detail message
 */
  public BluePageException() {
    super();
  }

/**
 * Constructs a <code>BluePageException</code> with the specified detail message
 */
  public BluePageException(String s) {
    super(s);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public String getVersion() {
    return new String("$Id: BluePageException.java,v 1.2 2004/07/09 19:53:44 joan Exp $");
  }
}
