//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareTextRuleException.java,v $
// Revision 1.6  2001/08/22 16:52:59  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:08  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:22  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 * @see MiddlewareException
 */
public final class MiddlewareTextRuleException extends MiddlewareException {

  /**
   * Constructs a <code>MiddlewareTextRuleException</code> with no specified detail message
   */
  public MiddlewareTextRuleException() {
    this("no detail message");
  }

  /**
   * Constructs a <code>MiddlewareTextRuleException</code> with the specified detail message
   */
  public MiddlewareTextRuleException(String s) {
    super(s);
  }

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: MiddlewareTextRuleException.java,v 1.6 2001/08/22 16:52:59 roger Exp $");
  }
}
