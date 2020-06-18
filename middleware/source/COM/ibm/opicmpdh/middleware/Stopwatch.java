//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Stopwatch.java,v $
// Revision 1.9  2001/08/22 16:53:03  roger
// Removed author RM
//
// Revision 1.8  2001/06/02 16:07:04  roger
// Correct evidence of brute-force coding!
//
// Revision 1.7  2001/06/02 03:46:31  roger
// New method for formatting msec time
//
// Revision 1.6  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * A class useful for performance benchmarking
 * @version @date
 */
public final class Stopwatch {

  // Class constants
  private static final long DAY_MS = 86400 * 1000;
  private static final long HOUR_MS = 3600 * 1000;
  private static final long MINUTE_MS = 60 * 1000;
  private static final long SECOND_MS = 1 * 1000;

  // Instance variables
  private long m_lStart = 0; // start time
  private long m_lFinish = 0;// finish time
  private long m_lLap0 = 0;  // prior lap time
  private long m_lLap1 = 0;  // current lap time

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
    Stopwatch swTimeIt = new Stopwatch();

    try {
      swTimeIt.start();
      Thread.sleep(1000);
      System.out.println(swTimeIt.lap() + " (should be 1000)");
      Thread.sleep(2000);
      System.out.println(swTimeIt.lap() + " (should be 2000)");
      Thread.sleep(500);
      System.out.println(swTimeIt.lap() + " (should be 500)");
      Thread.sleep(500);
      System.out.println(swTimeIt.lap() + " (should be 500)");
      Thread.sleep(1000);
      System.out.println(swTimeIt.finish() + " (should be 5000)");
      System.out.println(swTimeIt.finish() + " (should be 0)");
    } catch (Exception x) {}
    ;
  }

/**
 * @return the date/time this class was generated
 */

  // Return a string showing the date this class was generated
  public final String getVersion() {
    return new String("$Id: Stopwatch.java,v 1.9 2001/08/22 16:53:03 roger Exp $");
  }

/**
 * Start the <code>Stopwatch</code>
 */
  public final void start() {
    // Get the current ms since the epoch for start time
    m_lStart = System.currentTimeMillis();
    m_lFinish = 0;
    m_lLap0 = 0;
    m_lLap1 = 0;
  }

/**
 * Stop the <code>Stopwatch</code>
 * @return time (msec.) since start as <code>String</code>
 */
  public final String finish() {
    // Get the current ms since the epoch for finish time
    m_lFinish = System.currentTimeMillis();

    // If start time is zero, then start was not called, force duration to be zero
    if (m_lStart == 0) {
      m_lStart = m_lFinish;
    }

    // Determine difference between start and finish times
    long longDuration = m_lFinish - m_lStart;

    // Reset the start time
    m_lStart = 0;

    return String.valueOf(longDuration) + " ms.";
  }

/**
 * Stop the <code>Stopwatch</code>
 * @return time (msec.) since start as <code>String</code>
 */
  public final double getFinish() {
    // Get the current ms since the epoch for finish time
    m_lFinish = System.currentTimeMillis();

    // If start time is zero, then start was not called, force duration to be zero
    if (m_lStart == 0) {
      m_lStart = m_lFinish;
    }

    // Determine difference between start and finish times
    long longDuration = m_lFinish - m_lStart;

    // Reset the start time
    m_lStart = 0;

    return longDuration;
  }

/**
 * Record current lap time and keep <code>Stopwatch</code> running
 * @return time (msec.) since last lap or start as <code>String</code>
 */
  public final String lap() {
    // If there is no prior lap time then use the start time
    if (m_lLap1 == 0) {
      m_lLap1 = m_lStart;
    }

    // Move old current to prior
    m_lLap0 = m_lLap1;

    // Get the current ms since the epoch for current lap time
    m_lLap1 = System.currentTimeMillis();

    // Determine the difference between the current lap and the prior lap
    return String.valueOf(m_lLap1 - m_lLap0) + " ms.";
  }

/**
 * Record current lap time and keep <code>Stopwatch</code> running
 * @return time (msec.) since last lap or start as <code>String</code>
 */
  public final double getLap() {
    // If there is no prior lap time then use the start time
    if (m_lLap1 == 0) {
      m_lLap1 = m_lStart;
    }

    // Move old current to prior
    m_lLap0 = m_lLap1;

    // Get the current ms since the epoch for current lap time
    m_lLap1 = System.currentTimeMillis();

    // Determine the difference between the current lap and the prior lap
    return (m_lLap1 - m_lLap0);
  }

/**
 * Return long time in msec. as a string
 * @return time (msec.) as <code>String</code> e.g.) 7d23h59m59.999s
 */
  public final static String format(long lTime) {
    long iDays = lTime / DAY_MS;
    String strDays = (iDays > 0) ? iDays + "d" : "";
    long iHours = (lTime % DAY_MS) / HOUR_MS;
    String strHours = (iHours > 0) ? iHours + "h" : "";
    long iMinutes = (lTime % HOUR_MS) / MINUTE_MS;
    String strMinutes = (iMinutes > 0) ? iMinutes + "m" : "";
    long iSeconds = (lTime % MINUTE_MS) / SECOND_MS;
    long iSecondFraction = lTime % SECOND_MS;
    String strSecondFraction = "" + (iSecondFraction + 1000);
    String strSeconds = iSeconds + "." + strSecondFraction.substring(1) + "s";

    return new String(strDays + strHours + strMinutes + strSeconds);
  }
}
