//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LogWatcher.java,v $
// Revision 1.7  2008/01/31 22:54:59  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2002/01/15 23:22:24  roger
// Clean up
//
// Revision 1.5  2002/01/15 23:04:10  roger
// Clean up
//
// Revision 1.4  2002/01/15 22:59:12  roger
// Missing =
//
// Revision 1.3  2002/01/15 22:43:05  roger
// Clean up
//
// Revision 1.2  2002/01/15 22:41:37  roger
// Make port a parameter
//
// Revision 1.1  2002/01/15 18:58:41  roger
// Program to watch UDP logging
//

package COM.ibm.opicmpdh.middleware;

import java.net.*;

public class LogWatcher {
  public static void main (String args[]) {
    int iPort = 0;
	try {
      iPort = Integer.parseInt(args[0]);
    }
    catch (Exception x) {
	}

    if (iPort == 0) {
      iPort = 1234;
    }

    try {
      DatagramSocket socket = new DatagramSocket(iPort);
      while (true) {
        byte[] abBuffer = new byte[256];
        DatagramPacket dg = new DatagramPacket(abBuffer, abBuffer.length);
        socket.receive(dg);
        System.out.println(new String(abBuffer));
      }
    }
    catch (Exception x) {
      System.out.println(x);
    }
  }
}
