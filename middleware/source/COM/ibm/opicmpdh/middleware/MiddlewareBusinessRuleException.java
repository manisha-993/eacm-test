//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareBusinessRuleException.java,v $
// Revision 1.9  2009/11/03 18:42:11  wendy
// remove redundant code
//
// Revision 1.8  2004/12/21 21:28:52  gregg
// store locked part numbers on MiddlewareBusinessRuleException, so that we can clear any locks we stored
//
// Revision 1.7  2001/08/22 16:52:57  roger
// Removed author RM
//
// Revision 1.6  2001/06/07 07:58:36  dave
// misc toString cleanup
//
// Revision 1.5  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:08  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:20  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.util.*;
import COM.ibm.opicmpdh.objects.*;

/**
 * @version @date
 * @see MiddlewareException
 */
public final class MiddlewareBusinessRuleException extends MiddlewareException {
  public Vector m_vctFailures = null;
  public Vector m_vctMessages = null;
  // we need to save these so that we can clear locks on them when Exception is caught.
  private Vector m_vctLockedPartNos = null;

  /**
   * Constructs a <code>MiddlewareBusinessRuleException</code> with no specified detail message
   */
  public MiddlewareBusinessRuleException() {
    this("no detail message");

   // m_vctFailures = new Vector(); redundant
   // m_vctMessages = new Vector();
  }

  /**
   * Constructs a <code>MiddlewareBusinessRuleException</code> with the specified detail message
   */
  public MiddlewareBusinessRuleException(String s) {
    super(s);

    m_vctFailures = new Vector();
    m_vctMessages = new Vector();
  }

  /**
   * Adds an Attribute and Message to the failure list
   */
  public void add(Object _o, String _m) {
    m_vctFailures.addElement(_o);
    m_vctMessages.addElement(_m);
  }

  /**
   * Basic toString
   */
  public String toString() {
    String strAnswer = "\n";

    for (int x = 0; x < m_vctFailures.size(); x++) {
      Object obj = m_vctFailures.elementAt(x);
      String strObj = "No Object";
      if (obj instanceof Text) {
        strObj = ((Text) obj).m_strAttributeCode;
      }
      String strDesc = (String) m_vctMessages.elementAt(x);
      strAnswer = strAnswer + strDesc + "\n";
    }
    return strAnswer;
  }

  public void setLockedPartNumbers(Vector _v) {
      m_vctLockedPartNos = _v;
  }
  public Vector getLockedPartNumbers() {
      return m_vctLockedPartNos;
  }

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return "$Id: MiddlewareBusinessRuleException.java,v 1.9 2009/11/03 18:42:11 wendy Exp $";
  }
}
