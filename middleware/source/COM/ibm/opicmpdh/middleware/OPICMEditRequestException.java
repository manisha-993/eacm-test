//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// Revision 1.1  2001/05/02 20:10:54  dave
// cleaned up linkUI and added 100 transaction limit on the
// remote side to not allow for big transactions through the UI
//
// $Log: OPICMEditRequestException.java,v $
// Revision 1.3  2001/08/22 16:53:00  roger
// Removed author RM
//
// Revision 1.2  2001/06/02 18:43:15  dave
// fix to pull all data from normal tables to x tables on restore
//
// Revision 1.1  2001/06/02 17:50:52  dave
// new expection for getPDHEntities
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 * @see MiddlewareException
 */

public final class OPICMEditRequestException extends MiddlewareException {

  private String m_strMessage = "";

  /**
  * Constructs a <code> OPICMEditRequestException</code> with no specified detail message
  */
  public OPICMEditRequestException() {
    super("No Message");
    m_strMessage = new String("No Message");

  }

  /**
  * Constructs a <code> OPICMEditRequestException</code> with the specified detail message
  */
  public OPICMEditRequestException(String s) {
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
    return new String("$Id: OPICMEditRequestException.java,v 1.3 2001/08/22 16:53:00 roger Exp $");
  }
}
