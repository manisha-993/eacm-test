//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: D.java,v $
// Revision 1.62  2007/04/23 16:41:16  bala
// return DebugLevel
//
// Revision 1.61  2005/10/04 17:36:45  dave
// Category
//
// Revision 1.60  2005/06/04 15:54:44  dave
// Lets start going after WW product collection
//
// Revision 1.59  2004/03/01 17:57:36  roger
// Include hostname
//
// Revision 1.58  2004/02/26 20:16:58  roger
// Use local address as from
//
// Revision 1.57  2004/02/26 20:14:29  roger
// Also output normally
//
// Revision 1.56  2004/02/26 19:40:26  roger
// And more fixes
//
// Revision 1.55  2004/02/26 19:39:25  roger
// Fixes
//
// Revision 1.54  2004/02/26 19:32:43  roger
// Fixes
//
// Revision 1.53  2004/02/26 19:25:32  roger
// Create new method to send D.ebug by mail
//
// Revision 1.52  2002/10/01 20:39:49  roger
// Redirect StdErr as well
//
// Revision 1.51  2002/04/16 17:03:50  roger
// Squish it
//
// Revision 1.50  2002/04/16 16:55:57  roger
// Watch your scope!
//
// Revision 1.49  2002/04/16 16:47:43  roger
// Close should have been in a finally
//
// Revision 1.48  2002/04/15 17:52:41  dave
// too much closing
//
// Revision 1.47  2002/04/15 17:39:11  dave
// more file closes
//
// Revision 1.46  2002/04/08 20:04:05  dave
// adding file closes
//
// Revision 1.45  2002/03/08 22:47:12  roger
// Comments
//
// Revision 1.44  2002/03/08 17:58:05  roger
// Code formatting
//
// Revision 1.43  2002/03/08 17:30:31  roger
// Now get the true instance name
//
// Revision 1.42  2002/03/07 21:34:43  roger
// Still trying ...
//
// Revision 1.41  2002/03/07 21:25:00  roger
// Sheesh!
//
// Revision 1.40  2002/03/07 21:08:28  roger
// Fix it
//
// Revision 1.39  2002/03/07 21:08:04  roger
// Check if null, default if needed
//
// Revision 1.38  2002/03/07 20:55:18  roger
// Test
//
// Revision 1.37  2002/03/07 19:29:46  roger
// Ouch!
//
// Revision 1.36  2002/03/07 19:22:23  roger
// Test
//
// Revision 1.35  2002/03/07 19:15:14  roger
// Set defaults
//
// Revision 1.34  2002/03/07 18:33:30  roger
// Fix it
//
// Revision 1.33  2002/03/07 18:25:55  roger
// Restore lookup of properties
//
// Revision 1.32  2002/03/06 22:27:06  roger
// Fix it
//
// Revision 1.31  2002/03/06 22:18:30  roger
// Put instance back in
//
// Revision 1.30  2002/03/06 22:10:31  roger
// Handle System property
//
// Revision 1.29  2002/03/06 21:54:17  roger
// Fix it
//
// Revision 1.28  2002/03/06 21:52:40  roger
// Fix it
//
// Revision 1.27  2002/03/06 21:42:14  roger
// Fix it
//
// Revision 1.26  2002/03/06 21:30:56  roger
// Fix it
//
// Revision 1.25  2002/03/06 21:23:03  roger
// Fix it
//
// Revision 1.24  2002/03/06 18:14:11  roger
// Show instance name on local output once again
//
// Revision 1.23  2002/01/16 23:59:21  roger
// Enable autoflush on PrintStream
//
// Revision 1.22  2002/01/16 17:33:07  roger
// Need append output for redirected output
//
// Revision 1.21  2002/01/16 16:51:27  roger
// Missing semicolon
//
// Revision 1.20  2002/01/16 16:44:42  roger
// Allow redirection of output
//
// Revision 1.19  2002/01/16 00:07:08  roger
// Change default debug level
//
// Revision 1.18  2002/01/15 20:50:22  roger
// Wrong spot
//
// Revision 1.17  2002/01/15 20:44:03  roger
// Make UDP logging conditional, use properties
//
// Revision 1.16  2002/01/15 18:44:28  roger
// Need to show timestamp also
//
// Revision 1.15  2002/01/15 18:33:01  roger
// Wrap in exception handling
//
// Revision 1.14  2002/01/15 18:26:09  roger
// Needed import
//
// Revision 1.13  2002/01/15 18:19:53  roger
// Fix typo
//
// Revision 1.12  2002/01/15 18:11:14  roger
// Add UDP logging feature
//
// Revision 1.11  2001/10/31 19:25:11  dave
// double print in display
//
// Revision 1.10  2001/08/22 16:52:55  roger
// Removed author RM
//
// Revision 1.9  2001/08/01 19:25:35  roger
// Don't check length of date
// Make multi-line change to display method as well
//
// Revision 1.8  2001/08/01 19:23:34  roger
// If string to display contains newline characters, output multiple lines
//
// Revision 1.7  2001/06/15 03:55:54  roger
// Prepare ground-work for showing connectionID on all output
//
// Revision 1.6  2001/03/26 16:33:20  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:20  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

/**
 * A class for conditional output of debug/trace statements to the <code>System.out</code> and <code>System.out</code> print streams
 * @version @date
 */
public final class D {
  // Class constants
  public static final int EBUG_ERR = 0;
  public static final int EBUG_WARN = 1;
  public static final int EBUG_INFO = 2;
  public static final int EBUG_DETAIL = 3;
  public static final int EBUG_SPEW = 4;
  // Class variables
  private static boolean c_bEnabled = true;
  private static SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
  private static int c_iDebugLevel = D.EBUG_DETAIL;

/**
 * Don't let others instantiate this class.
 */
  protected D() {
  }
  private synchronized static final void output(String strText) {

    DatagramSocket out = null;
    String strInstance = MiddlewareServerDynamicProperties.getInstanceName();
    String strDate = c_sdfTimestamp.format(new Date());

    // Output message to log file
    System.out.println(strDate + " " + strInstance + " " + strText);

    // Output message to host:port via UDP
    byte[] abBuffer = new String(strInstance + " " + strDate + " " + strText).getBytes();
    String strHost = MiddlewareServerProperties.getLogHost();
    int iPort = MiddlewareServerProperties.getLogHostPort();

    if (iPort > 0) {
      try {
        out = new DatagramSocket();
        DatagramPacket dgp = new DatagramPacket(abBuffer, abBuffer.length, InetAddress.getByName(strHost), iPort);

        out.send(dgp);
      } catch (Exception x) {
        System.out.println("error writing output from D.output " + x);
      } finally {
        out.close();
      }
    }
  }
/**
 * Turn debug/trace output on (true), or off (false) for <b><u>ALL</u></b> users of this class
 * @param Turn debug/trace output on (true), or off (false) for <b><u>ALL</u></b> users of this class
 */
  public static final void ebug(boolean bSetting) {
    c_bEnabled = bSetting;
  }
/**
 * Conditionally output a string to the <code>System.out</code> print stream depending on trace setting only
 * @param String to be output
 */
  public static final void ebug(String strText) {
    StringTokenizer stLine = new StringTokenizer(strText, "\n");

    if (c_bEnabled) {
      while (stLine.hasMoreTokens()) {
        output(stLine.nextToken());
      }
    }
  }
/**
 * Conditionally output a string to the <code>System.out</code> print stream depending on debug level setting
 * @param String to be output
 */
  public static final void ebug(int iDebugLevel, String strText) {
    StringTokenizer stLine = new StringTokenizer(strText, "\n");

    if (c_bEnabled) {
      if (iDebugLevel <= c_iDebugLevel) {
        while (stLine.hasMoreTokens()) {
          output(stLine.nextToken());
        }
      }
    }
  }

  /**
   * Conditionally output a string to the <code>System.out</code> print stream depending on debug level setting
   * @param String to be output
   */
	public static final void ebug(Object _obj, int iDebugLevel, String strText) {
	  String strObj = _obj.getClass().getName();
	  int i = strObj.lastIndexOf(".");
	  if (i > -1) {
	  	strObj = strObj.substring(i+1);
	  }
	  StringTokenizer stLine = new StringTokenizer(strObj + "-" + strText, "\n");
	  if (c_bEnabled) {
		if (iDebugLevel <= c_iDebugLevel) {
		  while (stLine.hasMoreTokens()) {
			output(stLine.nextToken());
		  }
		}
	  }
	}
/**
 * Unconditionally output a string to the <code>System.out</code> print stream
 * @param String to be output
 */
  public static final void isplay(String strText) {
    StringTokenizer stLine = new StringTokenizer(strText, "\n");

    while (stLine.hasMoreTokens()) {
      output(stLine.nextToken());
    }
  }
/**
 * Set the output debug level for <b><u>ALL</u></b> users of this class
 * @param Debug level for <b><u>ALL</u></b> users of this class
 */
  public static final void ebugLevel(int iLevel) {
    c_iDebugLevel = iLevel;
  }

  /**
   *
   * @return the Current debug level
   */

  public static final int ebugShow() {
    return c_iDebugLevel;
  }


  /**
 * Return the current amount of memory alloc'd/free and percentage free as <code>String</code>
 */
  public static final String etermineMemory() {
    Runtime c_runInfo = Runtime.getRuntime();
    long lTotalMemory = c_runInfo.totalMemory();
    long lFreeMemory = c_runInfo.freeMemory();

    return new String("free memory: (" + lFreeMemory + "/" + lTotalMemory + ") " + ((lFreeMemory * 100) / lTotalMemory) + "% free");
  }
/**
 * Conditionally output the current amount of memory alloc'd/free and percentage free using D.ebug
 */
  public static final void isplayMemory() {
    D.ebug(D.EBUG_DETAIL, D.etermineMemory());
  }
/**
 * Redirect the output
 */
  public static final void ebugSetOut(PrintStream ps) {
    System.setOut(ps);
    System.setErr(ps);
  }
/**
 * Redirect the output
 */
  public static final void ebugSetOut(String strFileName) {

    PrintStream ps = null;

    try {
      // New PrintStream for filename (with append)
      FileOutputStream fos = new FileOutputStream(strFileName, true);
      ps = new PrintStream(fos, true);

    } catch (FileNotFoundException fne) {
      D.ebug("can't redirect output " + fne);
    }

    D.ebugSetOut(ps);
  }
/**
 * Send a debug message via mail
 */
  public static final void ebugByMail(String strText) {
    String strDate = c_sdfTimestamp.format(new Date());

    // get a mail object
    MiddlewareMail mm = new MiddlewareMail(MiddlewareMail.getSession());

    try {
      InetAddress iaLocal = InetAddress.getLocalHost() ;
      mm.setFrom(new InternetAddress("javaclient@" + iaLocal.getHostAddress()));
      mm.setRecipients(Message.RecipientType.TO, "roger_mccarty@hotmail.com");
      // stuff the mail object and send it
      mm.sendContentText("D.ebug ByMail", strDate + " " + strText);
    } catch (Exception x) {
	  D.ebug("can't send D.ebugByMail " + x);
	}
    mm = null;
    // also output normally
    D.ebug(strText);
  }
/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: D.java,v 1.62 2007/04/23 16:41:16 bala Exp $");
  }
}
