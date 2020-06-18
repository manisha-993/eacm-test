//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareWatchdog.java,v $
// Revision 1.7  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 16:12:38  roger
// Tweaked and/or added getVersion method
//
// Revision 1.4  2001/03/16 15:52:22  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

public class MiddlewareWatchdog {
  final static int STRING_LENGTH = 1024;
  final static int PASSES = 160;

  public static void main(String[] args) {

    Stopwatch timeIt = new Stopwatch();
    RemoteDatabaseInterface roOPICM = null;
    String strReturn = null;
    StringBuffer strbPassString = new StringBuffer();
    String strPassString = null;
    double dMS = 0;
    String strErrorInd = null;

    // Build a long string to pass to the remote method
    for (int i = 1; i <= STRING_LENGTH; i++) {
      strbPassString.append("X");
    }

    strPassString = new String(strbPassString);

    if (strPassString.length() != STRING_LENGTH) {
      System.out.println("Error allocating pass string");
    }

    try {
      System.out.println("Middleware Watchdog <0.0>");

      roOPICM = Middleware.connect();

      while (true) {
        strErrorInd = "";

        // Test the thruput
        timeIt.start();

        // Send the string n times
        for (int i = 1; i <= PASSES; i++) {
          strReturn = roOPICM.testThruPut(strPassString.length(), strPassString);

          if (strReturn.length() != STRING_LENGTH) {
            roOPICM.log("network - expected length [" + STRING_LENGTH + "] <> actual length [" + strPassString.length() + "]");

            strErrorInd = " : error";
          }
        }

        // Get the time
        dMS = timeIt.getFinish();

        // Record the timing on the server
        strReturn = roOPICM.log("network " + PASSES * STRING_LENGTH * 2 + " bytes transferred in " + dMS + " ms. (" + (int) ((PASSES * STRING_LENGTH * 2) / dMS) + " bytes/ms.)" + strErrorInd);

        // Sleep a while
        try {
          Thread.sleep(60000);
        } catch (Exception tx) {}
      }

//      Middleware.disconnect(roOPICM);
    } catch (Exception x) {
      System.out.println(x);
    }
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: MiddlewareWatchdog.java,v 1.7 2008/01/31 22:55:00 wendy Exp $");
  }
}
