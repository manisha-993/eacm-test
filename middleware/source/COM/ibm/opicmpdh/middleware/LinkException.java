//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LinkException.java,v $
// Revision 1.3  2001/08/22 16:52:56  roger
// Removed author RM
//
// Revision 1.2  2001/05/02 23:31:05  dave
// final adjustments for LinkException
//
// Revision 1.1  2001/05/02 20:10:54  dave
// cleaned up linkUI and added 100 transaction limit on the
// remote side to not allow for big transactions through the UI
//
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 * @see MiddlewareException
 */

public final class LinkException extends MiddlewareException {

  private String m_strMessage = "";

  /**
  * Constructs a <code> LinkException</code> with no specified detail message
  */
  public LinkException() {
    super("No Message");
    m_strMessage = new String("No Message");

  }

  /**
  * Constructs a <code> LinkException</code> with the specified detail message
  */
  public LinkException(String s) {
    super(s);
    m_strMessage = new String(s);

  }

  public String toString() {
     return m_strMessage;
  }
 /**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: LinkException.java,v 1.3 2001/08/22 16:52:56 roger Exp $");
  }
}
