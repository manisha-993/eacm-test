//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareSFRuleException.java,v $
// Revision 1.1  2003/05/02 17:24:57  dave
// unique EntiyType/AttributeCode/AttributeValue chcck
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 * @see MiddlewareException
 */
public final class MiddlewareSFRuleException extends MiddlewareException {

  /**
   * Constructs a <code>MiddlewareSFRuleException</code> with no specified detail message
   */
  public MiddlewareSFRuleException() {
    this("no detail message");
  }

  /**
   * Constructs a <code>MiddlewareTextRuleException</code> with the specified detail message
   */
  public MiddlewareSFRuleException(String s) {
    super(s);
  }

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: MiddlewareSFRuleException.java,v 1.1 2003/05/02 17:24:57 dave Exp $");
  }
}
