// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2001, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: MiddlewareRequestException.java,v $
// Revision 1.8  2008/10/10 13:17:13  wendy
// Allow UI to recognize search limit failure to improve performance
//
// Revision 1.7  2001/08/22 16:52:58  roger
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
 * @version @date
 * @see MiddlewareException
 */
public class MiddlewareRequestException extends MiddlewareException {

  /**
   * Constructs a <code>MiddlewareRequestException</code> with no specified detail message
   */
  public MiddlewareRequestException() {
    this("no detail message");
  }

  /**
   * Constructs a <code>MiddlewareRequestException</code> with the specified detail message
   */
  public MiddlewareRequestException(String s) {
    super(s);
  }

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return "$Id: MiddlewareRequestException.java,v 1.8 2008/10/10 13:17:13 wendy Exp $";
  }
}
