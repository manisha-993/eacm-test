//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TestMiddleware.java,v $
// Revision 1.11  2003/06/04 18:17:02  roger
// Change output formatting
//
// Revision 1.10  2003/06/04 18:01:56  roger
// Handle detection of ODS differently
//
// Revision 1.9  2003/06/04 17:44:39  roger
// Expose hasPDH() and hasODS() to RemoteDatabase users
//
// Revision 1.8  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.7  2001/03/21 21:16:41  roger
// Make the GBL####A SPs named GBL####
//
// Revision 1.6  2001/03/21 16:12:38  roger
// Tweaked and/or added getVersion method
//
// Revision 1.5  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import COM.ibm.opicmpdh.middleware.Middleware;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;

public class TestMiddleware {
  public static void main(String[] args) {

    RemoteDatabaseInterface roOPICM = null;
    ReturnDataResultSetGroup rdrsg = null;
    String strIP = null;
    String strPort = null;
    String strObject = null;
    String strResult = null;
    String strVersion = null;
    Exception xSave = null;
    boolean bHasODS = false;

    System.out.println("Testing middleware ...");
    System.out.println();

    strIP = MiddlewareServerProperties.getServerBindIpAddress();
    strPort = MiddlewareServerProperties.getServerBindPort();
    strObject = MiddlewareServerProperties.getDatabaseObjectName();

    System.out.println("Using parameters from 'middleware.server.properties' file in current directory.");
    System.out.println("object_name=" + strObject);
    System.out.println("server_bind_ip_address=" + strIP);
    System.out.println("server_bind_port=" + strPort);
    System.out.println();
    System.out.print("1 of 4. connect to middleware : ");

    try {
      roOPICM = Middleware.connect(strIP, Integer.parseInt(strPort), strObject);
      strResult = "ok";
      bHasODS = roOPICM.hasODS();
    } catch (Exception x) {
      strResult = "ERROR";
      xSave = x;
    }

    System.out.println(strResult);

    if (xSave != null) {
      System.out.println(xSave);

      xSave = null;
    }

    try {
      System.out.print("2 of 4. version identifier : ");

      strVersion = roOPICM.getVersion();
    } catch (Exception x) {
      strVersion = "UNKNOWN";
      xSave = x;
    }

    System.out.println(strVersion);

    if (xSave != null) {
      System.out.println(xSave);

      xSave = null;
    }

    System.out.print("3 of 4. execute a PDH SP : ");

    try {
      rdrsg = roOPICM.remoteGBL2028();
      strResult = "ok";
    } catch (Exception x) {
      strResult = "ERROR";
      xSave = x;
    }

    System.out.println(strResult);

    if (xSave != null) {
      System.out.println(xSave);

      xSave = null;
    }

    if (bHasODS) {
      System.out.print("3 of 4. execute an ODS SP : ");

      try {
        rdrsg = roOPICM.remoteGBL9999();
        strResult = "ok";
      } catch (Exception x) {
        strResult = "ERROR";
        xSave = x;
      }

      System.out.println(strResult);

      if (xSave != null) {
        System.out.println(xSave);

        xSave = null;
      }
    }

    System.out.print("4 of 4. disconnect from middleware : ");
    Middleware.disconnect(roOPICM);

    strResult = "ok";

    System.out.println(strResult);
    System.out.println();
    System.out.println("Testing complete.");
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: TestMiddleware.java,v 1.11 2003/06/04 18:17:02 roger Exp $");
  }
}
