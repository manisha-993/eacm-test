//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: T.java,v $
// Revision 1.11  2002/06/26 15:39:39  roger
// Method which does not throw exception
//
// Revision 1.10  2002/02/02 19:49:00  dave
// more foundation work
//
// Revision 1.9  2001/08/22 16:53:04  roger
// Removed author RM
//
// Revision 1.8  2001/06/15 06:11:46  roger
// Database will also need its own test method
//
// Revision 1.7  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.6  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.5  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.text.*;
import java.util.*;

/**
 * A class for simplifying output from test cases
 * @version @date
 */
public final class T {

  // Class variables
  private static SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");

/**
 * Don't let others instantiate this class.
 */
  protected T() {}

/**
 * Execute the test, output the description compare output to expected and show result
 */
  public synchronized final static void est(boolean bValue, String strCaseDescription, boolean bExpectedValue) {
    System.out.println(c_sdfTimestamp.format(new Date()) + " " + strCaseDescription + " expected '" + bExpectedValue + "' returned '" + bValue + ((bValue == bExpectedValue) ? "' : success" : "' : FAILURE"));
  }

/**
 * A simplistic assertion feature
 */
  public synchronized final static void estIt(boolean bValue, String strCaseDescription) {
    if (!bValue) {
      System.out.println(c_sdfTimestamp.format(new Date()) + " " + "Assertion failed: " + strCaseDescription);
    }
  }

/**
 * A simplistic assertion feature
 * @exception MiddlewareRequestException
 */
  public synchronized final static void est(boolean bValue, String strCaseDescription) throws MiddlewareRequestException {
    if (!bValue) {
      System.out.println(c_sdfTimestamp.format(new Date()) + " " + "Assertion failed: " + strCaseDescription);
      throw new MiddlewareRequestException(strCaseDescription);
    }
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: T.java,v 1.11 2002/06/26 15:39:39 roger Exp $");
  }
}
