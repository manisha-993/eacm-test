//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: NewPasswordException.java,v $
// Revision 1.8  2001/08/22 16:52:59  roger
// Removed author RM
//
// Revision 1.7  2001/05/07 16:54:10  dave
// Added toString for client display
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
 * An exception which indicates that the new password parameter does not follow rules for a valid password
 * @version @date
 * @see LoginException
 */
public final class NewPasswordException extends LoginException {

  private String m_strMessage;

/**
 * Constructs a <code>NewPasswordException</code> with no specified detail message
 */
  public NewPasswordException() {
    this("No Detail Message");
  }

/**
 * Constructs a <code>NewPasswordException</code> with the specified detail message
 */
  public NewPasswordException(String s) {
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
    return new String("$Id: NewPasswordException.java,v 1.8 2001/08/22 16:52:59 roger Exp $");
  }
}
