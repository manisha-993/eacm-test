//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareConsole.java,v $
// Revision 1.7  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 16:12:37  roger
// Tweaked and/or added getVersion method
//
// Revision 1.4  2001/03/16 15:52:21  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MiddlewareConsole {
  public static void main(String[] args) {

    BufferedReader readInput = new BufferedReader(new InputStreamReader(System.in));
    Stopwatch timeIt = new Stopwatch();
    RemoteDatabaseInterface roOPICM = null;
    String strCommand = null;
    String[] astrOutput = null;

    try {
      System.out.println("Middleware Console <0.0>");

      roOPICM = Middleware.connect();

      while (true) {
        System.out.print("@");
        strCommand = readInput.readLine();
        if (strCommand.equalsIgnoreCase("quit") || strCommand.equalsIgnoreCase("exit") || strCommand.equalsIgnoreCase("stop")) {
          Middleware.disconnect(roOPICM);
          System.exit(0);
        }

        timeIt.start();
        astrOutput = roOPICM.command(strCommand);
        for (int i = 0; i < astrOutput.length; i++) {
          System.out.println(astrOutput[i]);
        }

        System.out.println(timeIt.lap());
      }
    } catch (Exception x) {
      System.out.println(x);
    }
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public static final String getVersion() {
    return new String("$Id: MiddlewareConsole.java,v 1.7 2008/01/31 22:55:00 wendy Exp $");
  }
}
