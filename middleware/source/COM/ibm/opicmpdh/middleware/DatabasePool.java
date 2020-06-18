//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DatabasePool.java,v $
// Revision 1.178  2015/03/11 15:57:41  stimpsow
// RTC1119727 - add support for second ods
//
// Revision 1.177  2014/02/24 14:57:56  wendy
// RCQ285768 - view cached XML in JUI
//
// Revision 1.176  2009/03/13 11:29:51  wendy
// Cleanup RSA warnings
//
// Revision 1.175  2006/06/24 04:05:01  dave
// freeing up resources
//
// Revision 1.174  2006/02/13 22:00:52  dave
// no frills profile constructor
//
// Revision 1.173  2006/02/09 18:52:41  dave
// commenting out status log on busy connection for now
//
// Revision 1.172  2005/12/19 22:16:50  roger
// Change count
//
// Revision 1.171  2005/12/19 20:59:43  roger
// Still trying
//
// Revision 1.170  2005/12/19 20:42:13  roger
// Fix it
//
// Revision 1.169  2005/12/19 20:30:32  roger
// I wasn't happy with the other way
//
// Revision 1.168  2005/12/19 20:15:04  roger
// Comment
//
// Revision 1.167  2005/12/19 18:55:28  roger
// Keep alive
//
// Revision 1.166  2005/12/19 17:48:19  roger
// Stub the keep alive feature
//
// Revision 1.165  2004/07/30 16:59:04  gregg
// Commented out objectPool code.  Now use PartNo object for uniqueness checks.
//
// Revision 1.164  2004/02/23 22:07:39  dave
// fixed conflict
//
// Revision 1.163  2004/02/23 21:48:13  gregg
// compile fix (missing '}')
//
// Revision 1.162  2004/02/19 19:28:52  dave
// cutting down on pulse status
//
// Revision 1.161  2003/06/03 17:15:47  roger
// Need Database methods for hasODS + hasPDH
//
// Revision 1.160  2003/06/03 17:08:43  roger
// Test using GBL9999 should only be done if ODS is configured
//
// Revision 1.159  2003/06/02 21:39:33  roger
// Change property name
//
// Revision 1.158  2003/06/02 20:58:51  roger
// Need freeStatement
//
// Revision 1.157  2003/06/02 20:44:50  roger
// Connect to ODS also
//
// Revision 1.156  2003/06/02 19:22:57  roger
// Close early to eliminate isPending trouble
//
// Revision 1.155  2003/06/02 18:03:59  roger
// Did something bad
//
// Revision 1.154  2003/06/02 17:56:44  roger
// Brace matching
//
// Revision 1.153  2003/06/02 17:50:44  roger
// Test PDH+ODS at init
//
// Revision 1.152  2003/06/02 17:37:53  roger
// Try to run SP in ODS (won't work)
//
// Revision 1.151  2003/06/02 17:05:43  roger
// Property names have changed for PDH+ODS
//
// Revision 1.150  2002/11/07 18:56:11  dave
// visual basic mind melt!
//
// Revision 1.149  2002/11/07 18:24:52  dave
//  funky kludge to break out of a log jam if getConnection bit the dust in the middle
//  of aquiring a connection and leaving the cbBusyDB in a true state.
//
// Revision 1.148  2002/10/16 16:03:30  dave
// re-arranged order so stID can come out on Locked debug statement
//
// Revision 1.147  2002/10/16 07:11:37  dave
// removal of probe code
//
// Revision 1.146  2002/10/16 05:55:04  dave
// back off from orig assumption that websphere does not
// execute the finally (Dave's orig assumption).
//
// Revision 1.145  2002/10/16 05:44:13  dave
// more trace cleanup
//
// Revision 1.144  2002/10/16 05:38:06  dave
// lining up debug. and removing some trace
//
// Revision 1.143  2002/10/16 05:01:30  dave
// more syntax need to try/catch
//
// Revision 1.142  2002/10/16 04:58:22  dave
// more display.
//
// Revision 1.141  2002/10/16 04:49:42  dave
// i does not equal _i + more debug
//
// Revision 1.140  2002/10/16 04:37:22  dave
// removed a FISH
//
// Revision 1.139  2002/10/16 04:35:44  dave
// attempt to force a reconnect when you hand out the
// same connection to the same strId and dump to log file
// .  I want to see what breaks
//
// Revision 1.138  2002/10/16 03:49:23  dave
// syntax
//
// Revision 1.137  2002/10/16 03:38:30  dave
// Adding strID to free connection to compare values to that of
// get connection.
// EC may be chaining servlets and jsp's together within one
// session.
//
// Revision 1.136  2002/10/15 22:38:08  dave
// if i could type...
//
// Revision 1.135  2002/10/15 22:22:33  dave
// put the word FISH in trace statements to I can catch a fish
// w EC code
//
// Revision 1.134  2002/10/15 22:16:56  dave
// idwhich is not a variable
//
// Revision 1.133  2002/10/15 21:29:02  dave
// added some additional info for EC troubleshooting
//
// Revision 1.132  2002/10/15 18:11:03  dave
// found a place where c_bBusyDB needs to be set prior
// to throwing an MiddlewareWaitTimeOut exception
//
// Revision 1.131  2002/10/14 17:00:44  roger
// Keep count of pool instances
//
// Revision 1.130  2002/10/11 16:22:09  roger
// Formatting message
//
// Revision 1.129  2002/10/11 16:07:29  roger
// Keep a count of pulse threads
//
// Revision 1.128  2002/10/10 21:00:27  roger
// Thread should be max pri
//
// Revision 1.127  2002/10/10 20:29:53  roger
// Change housekeeping thread priority + make it a daemon thread
//
// Revision 1.126  2002/10/10 16:31:40  roger
// Blocking interferes with shutdown
//
// Revision 1.125  2002/10/10 16:15:51  roger
// Try blocking again
//
// Revision 1.124  2002/10/09 22:39:02  roger
// Blocking getConnection is trouble
//
// Revision 1.123  2002/10/09 22:26:21  roger
// Make getConnection truly single-threaded with race condition
//
// Revision 1.122  2002/10/03 20:00:46  roger
// Hand out Database connection in clean state
//
// Revision 1.121  2002/10/02 15:50:47  roger
// Serves no purpose for testing
//
// Revision 1.120  2002/10/01 19:35:38  roger
// Don't reset the session Id at free time
//
// Revision 1.119  2002/10/01 19:30:57  roger
// Remove Id on freeConnection
//
// Revision 1.118  2002/10/01 19:25:32  roger
// Have fix ready for connection Id reset on freeConnection
//
// Revision 1.117  2002/10/01 17:44:05  roger
// Lock count and start time need to be set
//
// Revision 1.116  2002/10/01 16:53:41  roger
// Show Id only if locked
//
// Revision 1.115  2002/10/01 16:26:40  roger
// Support session Id locks single Database
//
// Revision 1.114  2002/09/30 22:40:37  roger
// getConnection and Id
//
// Revision 1.113  2002/09/30 22:35:13  roger
// getConnection can now accept an Id to associate with the Database object returned
//
// Revision 1.112  2002/03/20 19:16:53  roger
// Change sleep time in shutdown
//
// Revision 1.111  2002/03/20 17:57:07  roger
// Restore old shutdown method
//
// Revision 1.110  2002/03/20 17:38:22  roger
// Change sleep time in shutdown
//
// Revision 1.109  2002/03/20 17:16:18  roger
// Is lockAll used?
//
// Revision 1.108  2002/03/18 18:31:54  roger
// Some searches in pool occur because getConnection doesn't throw Exception on shutdown
//
// Revision 1.107  2002/03/13 19:01:20  roger
// Get more information about why searches are happening in WASPool
//
// Revision 1.106  2002/03/11 16:22:21  roger
// Initialize counters
//
// Revision 1.105  2002/03/11 15:09:56  roger
// Initialize counters
//
// Revision 1.104  2002/03/08 22:45:01  roger
// Code cleanup
//
// Revision 1.103  2002/03/08 21:51:33  roger
// Housekeeping thread not stopping cause WASPool restarts in less than sleep time
//
// Revision 1.102  2002/03/08 16:59:44  roger
// Stop housekeeping thread when pool is shutdown
//
// Revision 1.101  2002/03/06 23:09:02  roger
// Destroy the checker thread in shutdown method
//
// Revision 1.100  2002/01/30 21:13:53  roger
// Chasing get/free still
//
// Revision 1.99  2002/01/30 18:12:00  roger
// Working on free/get Connection and synch issues
//
// Revision 1.98  2002/01/30 00:05:33  roger
// A bit more synch
//
// Revision 1.97  2002/01/29 23:54:56  roger
// Clean up
//
// Revision 1.96  2002/01/29 23:26:05  roger
// Restore 1.91 - is this actually broken?
//
// Revision 1.91  2002/01/29 17:38:00  roger
// Work on gating get and free Connection calls
//
// Revision 1.90  2002/01/29 00:50:33  roger
// Fix again
//
// Revision 1.89  2002/01/29 00:40:52  roger
// Fixes
//
// Revision 1.88  2002/01/29 00:31:55  roger
// Synchronize changes to InUse state for connection
//
// Revision 1.87  2002/01/28 23:40:31  roger
// Is there trouble with freeConnection?
//
// Revision 1.86  2002/01/25 21:12:35  roger
// Make it work
//
// Revision 1.85  2002/01/25 16:48:21  roger
// Show uptime on pulse
//
// Revision 1.84  2002/01/24 23:37:25  roger
// Chasing a freeConnection issue
//
// Revision 1.83  2002/01/24 21:39:57  roger
// Display log messages when creating/deleting semaphore files
//
// Revision 1.82  2002/01/24 20:59:41  roger
// Log messages for init and shutdown calls
//
// Revision 1.81  2002/01/24 20:57:34  roger
// Different message for mwcycle vs. mwshut
//
// Revision 1.80  2002/01/24 19:21:43  roger
// More parens
//
// Revision 1.79  2002/01/24 19:13:41  roger
// Don't fudge the last connection freed timer
//
// Revision 1.78  2002/01/24 18:34:20  roger
// Work on inactivity
//
// Revision 1.77  2002/01/24 17:17:30  roger
// Some clean up and work on inactivity shutdown
//
// Revision 1.76  2002/01/24 00:41:31  roger
// Inactive shutdown trouble is back under WAS4 now
//
// Revision 1.75  2002/01/21 23:57:20  roger
// Shutdown seems fixed, but I didn't do anything!
//
// Revision 1.74  2002/01/21 20:08:45  roger
// Still working on shutdown
//
// Revision 1.73  2002/01/21 19:59:56  roger
// Get the shut down thingie
//
// Revision 1.72  2002/01/21 18:35:27  roger
// Try to solve the shutdown problem with WASPool
//
// Revision 1.71  2002/01/18 17:06:02  roger
// Clean up
//
// Revision 1.70  2002/01/17 18:53:48  roger
// Line breaks
//
// Revision 1.69  2002/01/17 18:19:38  roger
// Typo
//
// Revision 1.68  2002/01/17 18:13:13  roger
// Need line breaks to work
//
// Revision 1.67  2002/01/17 17:59:09  roger
// Too many parens
//
// Revision 1.66  2002/01/17 17:50:18  roger
// Return Pool status as a String
//
// Revision 1.65  2002/01/16 23:43:06  roger
// No need to debug method
//
// Revision 1.64  2002/01/16 18:36:46  roger
// Change order of D.ebug settings
//
// Revision 1.63  2002/01/16 18:23:39  roger
// Set D.ebug properties (on/off, level, redirection) consistently in DB/RDB/Pool
//
// Revision 1.62  2002/01/16 17:56:40  roger
// Shutdown method belongs in DatabasePool not RemoteDatabase
//
// Revision 1.61  2002/01/16 17:36:35  roger
// Redirect D.ebug output if configured
//
// Revision 1.60  2002/01/16 00:58:04  roger
// Not resolved!
//
// Revision 1.59  2002/01/16 00:38:34  roger
// Shutdown solved?
//
// Revision 1.58  2002/01/16 00:31:54  roger
// When init is called, set the last connection freed time
//
// Revision 1.57  2002/01/16 00:29:43  roger
// Isolate shutdown problem
//
// Revision 1.56  2002/01/15 23:59:38  roger
// Exit after test
//
// Revision 1.55  2002/01/15 23:33:05  roger
// Still shuts down
//
// Revision 1.54  2002/01/15 22:34:16  roger
// Changes for WAS pool
//
// Revision 1.53  2002/01/15 22:07:44  roger
// Changes for WAS pool
//
// Revision 1.52  2002/01/15 21:59:34  roger
// New init method
//
// Revision 1.51  2002/01/15 21:37:16  roger
// Isolate problem by removing mwcycle creation
//
// Revision 1.50  2002/01/15 21:04:36  roger
// Remove instance name, now on UDP log only
//
// Revision 1.49  2001/10/18 00:21:22  dave
// more fixes
//
// Revision 1.48  2001/10/17 23:48:01  dave
// more tracing stuff
//
// Revision 1.47  2001/10/17 23:29:40  dave
// trace statements to see database connect timings
//
// Revision 1.46  2001/08/22 16:52:55  roger
// Removed author RM
//
// Revision 1.45  2001/08/08 18:57:25  dave
// minor syntax
//
// Revision 1.44  2001/08/08 18:31:14  dave
// fixed object cache.. was not picking up reset
//
// Revision 1.43  2001/07/19 03:37:02  roger
// Show time since last transaction completed
//
// Revision 1.42  2001/06/27 18:07:59  roger
// Changed acquired message
//
// Revision 1.41  2001/06/27 17:20:08  roger
// Misc
//
// Revision 1.40  2001/06/27 16:49:06  roger
// Add'l literal
//
// Revision 1.39  2001/06/27 16:48:23  roger
// Remove redundant connectionID from acquired output
//
// Revision 1.38  2001/06/27 16:47:15  roger
// Remove redundant connectionID on locked output
//
// Revision 1.37  2001/06/27 16:46:02  roger
// Needed space
//
// Revision 1.36  2001/06/27 16:45:19  roger
// Remove redundant connectionID on freed output
//
// Revision 1.35  2001/06/27 16:42:42  roger
// Remove redundant connectionID from PULSE output
//
// Revision 1.34  2001/06/27 15:51:24  roger
// Grab the instance name from the dynamic property file
//
// Revision 1.33  2001/06/20 16:06:45  roger
// More connectionID in debug output
//
// Revision 1.32  2001/06/16 05:51:32  roger
// Add'l D.ebug method
//
// Revision 1.31  2001/06/16 05:35:02  roger
// Needed debug method here
//
// Revision 1.30  2001/06/16 05:14:53  roger
// Wrong subscript variable
//
// Revision 1.29  2001/06/16 05:13:46  roger
// D.ebug and literal changes
//
// Revision 1.28  2001/06/16 04:38:00  roger
// More D.ebug to debug changes
//
// Revision 1.27  2001/06/16 01:59:06  roger
// More changes to support connectionID in all log output
//
// Revision 1.26  2001/06/15 04:30:46  roger
// syntax fixes
//
// Revision 1.25  2001/06/15 03:55:54  roger
// Prepare ground-work for showing connectionID on all output
//
// Revision 1.24  2001/06/02 03:50:47  roger
// Show ages as days/hours/minutes/seconds instead of ms.
//
// Revision 1.23  2001/05/25 18:22:15  roger
// PULSE alignment
//
// Revision 1.22  2001/05/21 19:50:45  dave
// syntax fix for display line
//
// Revision 1.21  2001/05/11 21:49:08  roger
// Shorten PULSE output line
//
// Revision 1.20  2001/05/11 15:41:34  dave
// fix to return results 1 from 2913
//
// Revision 1.19  2001/05/09 23:47:15  dave
// moved clearcache to databasepool (where it belongs)
//
// Revision 1.18  2001/05/07 20:35:49  dave
// first attempt at setting up the clearing of the Object Cache
//
// Revision 1.17  2001/04/25 02:38:19  dave
// remove the sync option on the object cache
//
// Revision 1.16  2001/04/24 23:54:06  dave
// Tightning up cache concept for meta object sharing
//
// Revision 1.15  2001/04/19 22:47:42  dave
// fixed import statement
//
// Revision 1.14  2001/04/19 22:43:33  dave
// added the transactions package to this class
//
// Revision 1.13  2001/04/19 22:11:17  dave
// Test to see if an OPICMList can be shared between database connections in the pool
//
// Revision 1.12  2001/04/18 23:07:14  dave
// Added a tremendous ammount of debug statements in the
// navigate method (along with connection id dumps in the debug
// statements in hopes to trap an error where the connection
// is not being freed properly
//
// Revision 1.11  2001/03/27 18:45:58  roger
// Put the restart code back in
//
// Revision 1.10  2001/03/26 16:33:20  roger
// Misc formatting clean up
//
// Revision 1.9  2001/03/21 21:16:41  roger
// Make the GBL####A SPs named GBL####
//
// Revision 1.8  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.7  2001/03/19 23:13:41  roger
// Changed inactive timer message layout
//
// Revision 1.6  2001/03/16 15:52:20  roger
// Added Log keyword
//
package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.sql.*;
//import COM.ibm.opicmpdh.transactions.*;

/**
 * A pool of <code>Database</code> connections
 * @version @date
 * @see Database
 * @see RemoteDatabase
 * @see Middleware
 * @see MiddlewareServerProperties
 */
public final class DatabasePool
implements Runnable {
	// Class variables
	private static Database c_adbOPICM[] = null;
	private static boolean c_abConnectionInUse[] = null;
	private static String c_strConnectionId[] = null;
	//private static OPICMList c_oObjectCache = null;
	// Number of databases in pool
	private static int c_iMaxConnections = 0;
	// Properties
	private static Thread c_thChecker = null;
	private boolean c_bCheckerRun = true;
	private static int c_iSleepTime = 0;
	private static int c_iUseConnection = -1;
	private static int c_iWaitTimeout = -1;
	private static int c_iHousekeepingPause = 0;
	private static int c_iDeadConnectionAge = 0;
	private static long c_lLastConnectionFreed = 0;
	private static long c_lStartTime = 0;
	private static int c_iKeepAlive = 0;
	private static long c_lKeepAliveDue = 0;
	// Properties
	private boolean bShutDownPending = false;
	// Counters
	private static int c_iTransactions = 0;
	private static int c_iSearches = 0;
	private static int c_iSleeps = 0;
	private static int c_iTimeouts = 0;
	private static int c_iReconnects = 0;
	// From the property file
	private String m_strInstanceName = "iname";
	public final static String NOID_SPECIFIED = "NOID";
	private static boolean c_bBusyDB = false;
	private static int c_iThreadCount = 0;
	private static int c_iPoolCount = 0;

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String[] _arg) {

		DatabasePool poolDatabase = new DatabasePool();
		ReturnStatus returnStatus = new ReturnStatus( -1);

		try {
			D.ebug(D.EBUG_DETAIL, "Verify connectivity to database");

			for (int i = 0; i <= c_iMaxConnections * 2; ++i) {
				Database dbCurrent = poolDatabase.getConnection();

				ResultSet rsPDH = dbCurrent.callGBL2028(returnStatus);
				while (rsPDH.next()) {
					D.ebug(D.EBUG_DETAIL, "PDH now     ==" + rsPDH.getString(1) + "==");
					D.ebug(D.EBUG_DETAIL, "PDH forever ==" + rsPDH.getString(2) + "==");
					D.ebug(D.EBUG_DETAIL, "PDH epoch   ==" + rsPDH.getString(3) + "==");
				}
				rsPDH.close();
				dbCurrent.freeStatement();

				if (dbCurrent.hasODS()) {
					ResultSet rsODS = dbCurrent.callGBL9999(returnStatus);
					while (rsODS.next()) {
						D.ebug(D.EBUG_DETAIL, "ODS now     ==" + rsODS.getString(1) + "==");
						D.ebug(D.EBUG_DETAIL, "ODS forever ==" + rsODS.getString(2) + "==");
						D.ebug(D.EBUG_DETAIL, "ODS epoch   ==" + rsODS.getString(3) + "==");
					}
					rsODS.close();
					dbCurrent.freeStatement();
				}
				poolDatabase.freeConnection(dbCurrent);
			}

			D.ebug(D.EBUG_DETAIL, "Test complete");
		}
		catch (Exception x) {
			D.ebug(D.EBUG_ERR, "DatabasePool:main" + x);
		}

		System.exit(0);
	}

	/**
	 * Constructs a <code>DatabasePool</code> of n <code>Database</code> objects based on <code>MiddlewareServerProperties</code> setting
	 */
	public DatabasePool() {
		++c_iPoolCount;
		// How many connections in the pool?
		c_iMaxConnections = MiddlewareServerProperties.getDatabaseConnections();
		// Build the array of variables
		c_adbOPICM = new Database[c_iMaxConnections];
		c_abConnectionInUse = new boolean[c_iMaxConnections];
		c_strConnectionId = new String[c_iMaxConnections];

		// Keep others away while we grab all connections
		// Lock all the connections
		for (int i = 0; i < this.getPoolSize(); i++) {
			setInUse(i, true);
		}

		// We own all the connections now so we are safe to do whatever we wish with them
		// Used for calculating uptime
		c_lStartTime = System.currentTimeMillis();
		// This is the Object Cache
		//c_oObjectCache = new OPICMList();

		// Redirect output if requested
		String strLogFileName = MiddlewareServerProperties.getLogFileName();

		if (strLogFileName.length() > 0) {
			D.ebugSetOut(strLogFileName);
		}

		D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());
		D.ebug(MiddlewareServerProperties.getTrace());
		D.ebug(D.EBUG_DETAIL, "(DatabasePool) tracing enabled");
		D.ebug(D.EBUG_DETAIL,
				"Using " + c_iMaxConnections + " " + ( (c_iMaxConnections == 1) ? "connection" : "connections") + " to DB2");

		c_iSleepTime = MiddlewareServerProperties.getWaitSleepTimeMS();

		D.ebug(D.EBUG_DETAIL, "Wait sleep time " + c_iSleepTime + " ms.");

		c_iWaitTimeout = MiddlewareServerProperties.getWaitTimeoutMS();

		D.ebug(D.EBUG_DETAIL, "Wait Timeout " + c_iWaitTimeout + " ms.");

		c_iHousekeepingPause = MiddlewareServerProperties.getConnectionCheckerQuantum();

		D.ebug(D.EBUG_DETAIL, "Check for failed transactions every " + c_iHousekeepingPause + " ms.");

		c_iDeadConnectionAge = MiddlewareServerProperties.getConnectionDead();

		// Determine the keep alive time
		c_iKeepAlive = MiddlewareServerProperties.getKeepAliveQuantum();

		D.ebug(D.EBUG_DETAIL, "Assume lock older than " + c_iDeadConnectionAge + " ms. is a failed transaction");
		D.ebug(D.EBUG_DETAIL,
				"Restart server if no activity within " + MiddlewareServerProperties.getInactiveRestartQuantum() + " ms.");

		m_strInstanceName = MiddlewareServerDynamicProperties.getInstanceName();

		// Initialize the arrays of variables (keep connections locked)
		for (int i = 0; i < this.getPoolSize(); i++) { 
			D.ebug(D.EBUG_DETAIL, "Establishing connection [" + i + "] in DatabasePool constructor");

			c_adbOPICM[i] = new Database(MiddlewareServerProperties.getPDHDatabaseURL(),
					MiddlewareServerProperties.getPDHDatabaseUser(),
					MiddlewareServerProperties.getPDHDatabasePassword(),
					MiddlewareServerProperties.getODSDatabaseURL(),
					MiddlewareServerProperties.getODSDatabaseUser(),
					MiddlewareServerProperties.getODSDatabasePassword(),
					MiddlewareServerProperties.getODS2DatabaseURL(), //RTC1119727
					MiddlewareServerProperties.getODS2DatabaseUser(),
					MiddlewareServerProperties.getODS2DatabasePassword());

			// Set some properties
			c_adbOPICM[i].setInstanceName(m_strInstanceName);
			c_adbOPICM[i].setConnectionID(i);
			// attach the Object Cache to it
			//c_adbOPICM[i].setObjectPool(c_oObjectCache);
			D.ebug(D.EBUG_DETAIL, "Connection [" + i + "] is initialized");
		}

		// Initialize the counters
		c_iTransactions = 0;
		c_iSearches = 0;
		c_iSleeps = 0;
		c_iTimeouts = 0;
		c_iReconnects = 0;

		// Unlock all the connections (don't need to synch cause OK if they are used as soon as freed)
		for (int i = 0; i < this.getPoolSize(); i++) {
			setInUse(i, false);
		}

		// Setup and start the housekeeping thread to find failed transactions
		try {
			c_thChecker = new Thread(this);
			c_bCheckerRun = true;
			++c_iThreadCount;
			//c_thChecker.setPriority(Thread.MIN_PRIORITY);
			c_thChecker.setPriority(Thread.MAX_PRIORITY);
			c_thChecker.setDaemon(true);

			//      c_thChecker.setPriority(Thread.MAX_PRIORITY);
			c_thChecker.start();
		}
		catch (Exception x) {
			D.ebug(D.EBUG_ERR, "Exception creating housekeeping thread " + x);
			System.exit( -1);
		}
	}

	/**
	 * Properties have changed, change appropriate behaviours
	 */
	protected final static void reloadProperties() {
		c_iSleepTime = MiddlewareServerProperties.getWaitSleepTimeMS();
		c_iWaitTimeout = MiddlewareServerProperties.getWaitTimeoutMS();
		c_iHousekeepingPause = MiddlewareServerProperties.getConnectionCheckerQuantum();
		c_iDeadConnectionAge = MiddlewareServerProperties.getConnectionDead();
	}

	/**
	 * Return the size of the <code>DatabasePool</code>
	 * @return The number of connections maintained by this pool
	 */
	public final int getPoolSize() {
		return c_adbOPICM.length;
	}

	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public final String getVersion() {
		return "$Id: DatabasePool.java,v 1.178 2015/03/11 15:57:41 stimpsow Exp $";
	}

	/**
	 * Acquire a <code>Database</code> object to use and flag the connection as in use
	 * @return Available <code>Database</code> object
	 * @exception MiddlewareWaitTimeoutException
	 * @exception MiddlewareShutdownInProgressException
	 */
	public final Database getConnection() throws MiddlewareWaitTimeoutException, MiddlewareShutdownInProgressException {
		return this.getConnection("new", NOID_SPECIFIED);
	}

	/**
	 * Acquire a <code>Database</code> object to use and flag the connection as in use
	 * @return Available <code>Database</code> object
	 * @exception MiddlewareWaitTimeoutException
	 * @exception MiddlewareShutdownInProgressException
	 */
	public synchronized final Database getConnection(String _strPurpose) throws MiddlewareWaitTimeoutException,
	MiddlewareShutdownInProgressException {
		return this.getConnection(_strPurpose, NOID_SPECIFIED);
	}

	/**
	 * Acquire a <code>Database</code> object to use and flag the connection as in use
	 * @return Available <code>Database</code> object
	 * @exception MiddlewareWaitTimeoutException
	 * @exception MiddlewareShutdownInProgressException
	 */
	public synchronized final Database getConnection(String _strPurpose, String _strId) throws MiddlewareWaitTimeoutException,
	MiddlewareShutdownInProgressException {

		long lWaitBegin = 0;
		long lNow = 0;
		int iStartingPoint = 0;
		int iSearched = 0;
		int iSlept = 0;
		File f1 = new File("mwshut");
		File f2 = new File("mwcycle");
		File f3 = new File("mwinit");
		int iIdConnection = -1;
		int iReturnIndex = -1;

		if (f1.exists() || bShutDownPending) {
			throw new MiddlewareShutdownInProgressException("Middleware shutdown is pending");
		}

		if (f2.exists()) {
			throw new MiddlewareShutdownInProgressException("Middleware cycle is pending");
		}

		if (f3.exists() && ! (_strPurpose.equals("verify"))) {
			throw new MiddlewareShutdownInProgressException("Middleware is initializing");
		}

		// DWB funky kludge to break out of a log jam if getConnection bit the dust in the middle
		// of aquiring a connection and leaving the cbBusyDB in a true state.
		int iCount = 0;
		while (c_bBusyDB && iCount < 60) {
			try {
				D.ebug("FISH:" + (iCount++) + ": blocked in getConnection for Purpose: " + _strPurpose + " Id: " + _strId);
				if (iCount == 30) {
					D.ebug("CUT BAIT: Breaking the block!: we looped " + iCount + " times.");
				}
				Thread.sleep(1000);
			}
			catch (Exception x) {
			}
		}

		c_bBusyDB = true;

		++c_iTransactions;

		iIdConnection = -1;

		// Check if _strId is already holding a connection, if so report it.
		if (! (_strId.equalsIgnoreCase(NOID_SPECIFIED))) {
			D.ebug("getConnection called for Purpose = '" + _strPurpose + "' Id = '" + _strId + "'");
			for (int i = 0; i < c_strConnectionId.length; i++) {
				if (c_strConnectionId[i].equalsIgnoreCase(_strId)) {
					D.ebug("FISH getConnection found assigned Database for Id = '" + _strId + "' index = " + i);
					break;
				}
			}
		}

		iReturnIndex = -1;

		// Did we find an existing connection? use it!
		if (iIdConnection >= 0) {
			iReturnIndex = iIdConnection;
		} else {
			// Record the time when we started searching
			lWaitBegin = System.currentTimeMillis();
			// Keep starting point of search
			iStartingPoint = c_iUseConnection;
			// Bump past last used connection
			c_iUseConnection = ( (++c_iUseConnection) % c_iMaxConnections);

			// Cycle to next AVAIL connection, not just the next one - wait if necessary!
			while (getInUse(c_iUseConnection) == true) {
				++c_iSearches;

				// Searches are a bad thing, let's report it
				D.ebug("forced to do a search to find a connection");
				// Show the pool status so we can see what is happening
				// looking for rogue database pools.  This guy is adding stuff to the log
				// so comment out for now
				// this.status();

				c_iUseConnection = ( (++c_iUseConnection) % c_iMaxConnections);

				++iSearched;

				// If we have looped completely around, lets take some action
				if (c_iUseConnection == iStartingPoint) {
					lNow = System.currentTimeMillis();

					// If we have been trying for specific time it's time to bail out!
					if ( (lNow - lWaitBegin) > c_iWaitTimeout) {
						++c_iTimeouts;
						// DWB If we get a timeout .. we need to unbusify it before we leave
						c_bBusyDB = false;
						throw new MiddlewareWaitTimeoutException();
					}

					// Can't get a connection now, let's sleep a bit
					try {
						Thread.sleep(c_iSleepTime);

						++iSlept; ++c_iSleeps;
					}
					catch (Exception x) {}
				}
			}
			iReturnIndex = c_iUseConnection;
		}

		if (getInUse(iReturnIndex) == true) {
			D.ebug("FISH someone grabbed connection before we could!");
		}

		// Save the Id for future use
		if (_strId.equalsIgnoreCase(NOID_SPECIFIED)) {
			c_strConnectionId[iReturnIndex] = "";
		} else {
			// Save the Id
			c_strConnectionId[iReturnIndex] = _strId;
		}

		// This will show for both
		// Show some stats about what was involved to acquire this connection
		c_adbOPICM[iReturnIndex].debug(D.EBUG_DETAIL,
				"CONNECTION acquired POOL searched: " + iSearched + " times slept: " + iSlept + " times");
		c_adbOPICM[iReturnIndex].debug(D.EBUG_DETAIL,
				"CONNECTION locked (" + _strPurpose + ") " + "Id (" + c_strConnectionId[iReturnIndex] + ")");

		// Got a connection, mark it as in use
		setInUse(iReturnIndex, true);

		// Record the lock time
		c_adbOPICM[iReturnIndex].m_lLockStart = System.currentTimeMillis();

		// Bump the lock count for the database
		++c_adbOPICM[iReturnIndex].m_lLockCount;

		// Report if there are any statements open - should be none
		c_adbOPICM[iReturnIndex].isPending();
		c_adbOPICM[iReturnIndex].setConnectionID(iReturnIndex);

		// Describe what we want to do with the connection
		c_adbOPICM[iReturnIndex].m_strPurpose = _strPurpose;

		// Show the pool status
		// D.ebug(this.getStatus());

		// Ensure we are handing out in clean state
		c_adbOPICM[iReturnIndex].freeStatement();
		c_adbOPICM[iReturnIndex].isPending();

		c_bBusyDB = false;

		// Return the database to be used
		return c_adbOPICM[iReturnIndex];
	}

	/**
	 *
	 */
	private final boolean getInUse(int _iWhich) {
		return c_abConnectionInUse[_iWhich];
	}

	/**
	 *
	 */
	private final void setInUse(int _iWhich, boolean _bInUse) {
		c_abConnectionInUse[_iWhich] = _bInUse;
	}

	/**
	 * Return the connection to the available pool
	 */
	public final void freeConnection(Database _db) {
		this.freeConnection(_db, NOID_SPECIFIED);
	}

	/**
	 * Return the connection to the available pool
	 */
	public final void freeConnection(int _iWhich) {
		this.freeConnection(_iWhich, NOID_SPECIFIED);
	}

	/**
	 * Return the connection to the available pool
	 */
	public final void freeConnection(Database _db, String _strId) {
		// Search for this db in the pool
		int iWhich = whichConnection(_db);

		if (iWhich >= 0) {
			freeConnection(iWhich, _strId);
		} else {
			D.ebug("whichConnection returned a bad index!");
		}
	}

	/**
	 * Return the connection to the available pool
	 */
	public synchronized final void freeConnection(int _iWhich, String _strId) {

		if (getInUse(_iWhich) == false) {
			D.ebug("FISH freeing a free connection at index = " + _iWhich + ".  Passed Id = '" + _strId + "'.");
			return;
		}

		// If we attempt to free a connection and we are specifying an ID.. and that ID does not match what we
		// are tracking.. log it
		if (!_strId.equalsIgnoreCase(NOID_SPECIFIED) && !c_strConnectionId[_iWhich].equalsIgnoreCase(_strId)) {
			D.ebug("FISH connectionId's do not match for index " + _iWhich + ".  current strId:" + c_strConnectionId[_iWhich] +
					":Passed:" + _strId);
			return;
		}

		c_adbOPICM[_iWhich].debug(D.EBUG_DETAIL,
				"CONNECTION freed (" + c_adbOPICM[_iWhich].getPurpose() + ") Id (" + c_strConnectionId[_iWhich] +
				") " + Stopwatch.format(c_adbOPICM[_iWhich].getLockAge()));
		// Close any statements
		c_adbOPICM[_iWhich].freeStatement();
		// Report if there are any statements open - should be none
		c_adbOPICM[_iWhich].isPending();

		// Change properties to show we are freed
		c_adbOPICM[_iWhich].m_lLockStart = 0;
		c_adbOPICM[_iWhich].m_strPurpose = "available";

		setInUse(_iWhich, false);

		c_strConnectionId[_iWhich] = "";
		c_lLastConnectionFreed = System.currentTimeMillis();

		housekeepingStatus();
	}

	/**
	 * Return the connection index in the pool of connections
	 */
	public final int whichConnection(Database _db) {

		// Search for this db in the pool
		for (int i = 0; i < this.getPoolSize(); i++) {
			// If the objects match return the index
			if (_db == c_adbOPICM[i]) {
				return i;
			}
		}

		// Something is wrong, can't find database in the pool
		return -1;
	}

	/**
	 * Get the pool status as a string
	 */
	public final String getStatus() {

		char ch_lf = 10;
		StringBuffer strbReturn = new StringBuffer();

		// Show the pool status, and then ...
		strbReturn.append(this.toString() + ch_lf);

		// Print the status of each connection
		for (int i = 0; i < this.getPoolSize(); i++) {
			strbReturn.append("[" + i + "] " + "PULSE (" + c_adbOPICM[i].getPurpose() + ") connect age: " +
					Stopwatch.format(c_adbOPICM[i].getConnectionAge()) + " lock age: " +
					Stopwatch.format(c_adbOPICM[i].getLockAge()) + " locks: " + c_adbOPICM[i].getLockCount() +
					( (getInUse(i)) ? " LOCKED" + " Id: " + c_strConnectionId[i] : "") +
					( (c_adbOPICM[i].getForceReconnect()) ? " RECONNECT" : ""));
			strbReturn.append(ch_lf);
		}

		return new String(strbReturn);
	}

	/**
	 * Show the pool status
	 */
	public final void status() {
		// Show the pool status, and then ...
		D.ebug(D.EBUG_DETAIL, this.toString());

		// Print the status of each connection
		for (int i = 0; i < this.getPoolSize(); i++) {
			c_adbOPICM[i].debug(D.EBUG_DETAIL,
					"PULSE (" + c_adbOPICM[i].getPurpose() + ") connect age: " +
							Stopwatch.format(c_adbOPICM[i].getConnectionAge()) + " lock age: " +
							Stopwatch.format(c_adbOPICM[i].getLockAge()) + " locks: " + c_adbOPICM[i].getLockCount() +
							( (getInUse(i)) ? " LOCKED" + " Id: " + c_strConnectionId[i] : "") +
							( (c_adbOPICM[i].getForceReconnect()) ? " RECONNECT" : ""));
		}
	}

	/**
	 * Run the housekeeping thread
	 */
	public final void run() {

		while (c_bCheckerRun) {
			try {
				// Sleep a bit
				Thread.sleep(c_iHousekeepingPause);
				D.ebug(D.EBUG_DETAIL, "PULSE Housekeeping thread going to sleep for " + Stopwatch.format(c_iHousekeepingPause));
				this.status();

				File f3 = new File("mwcache");

				if (f3.exists()) {
					D.isplay("Removing mwcache to change operational mode");
					f3.delete();
					D.ebug(D.EBUG_DETAIL, "Clearing Object Cache");
					//resetObjectCache();
					D.ebug(D.EBUG_DETAIL, "Cleared cache .. mwcache file");
				}

				// If a certain amount of time has elapsed since a transaction, restart the server
				if ( (c_lLastConnectionFreed > 0) &&
						(System.currentTimeMillis() - c_lLastConnectionFreed) >= MiddlewareServerProperties.getInactiveRestartQuantum()) {
					D.ebug(D.EBUG_DETAIL,
							"PULSE system restart will take place (on next pulse) due to inactivity:" +
									(System.currentTimeMillis() - c_lLastConnectionFreed) + " > " +
									MiddlewareServerProperties.getInactiveRestartQuantum());

					// Effect a restart
					File f2 = new File("mwcycle");
					FileWriter fw2 = new FileWriter(f2);

					D.isplay("Creating mwcycle to change operational mode");
					fw2.write(' ');
					fw2.close();
				}

				// If there is a mwreset file, let's reconnect ALL by forcing their age
				File f1 = new File("mwreset");

				if (f1.exists()) {
					D.isplay("Removing mwreset to change operational mode");
					f1.delete();
					D.ebug(D.EBUG_DETAIL, "PULSE forcing all connections to reconnect");

					for (int i = 0; i < c_adbOPICM.length; i++) {
						c_adbOPICM[i].m_lLockStart = 1;
					}
				}

				// Search and repair "failed" transactions
				for (int i = 0; i < c_adbOPICM.length; i++) {
					// Is transaction taking too long (has it "failed")?
					if (c_adbOPICM[i].getLockAge() > c_iDeadConnectionAge) {
						// Mark it as InUse so it won't be used
						setInUse(i, true);
						c_adbOPICM[c_iUseConnection].debug(D.EBUG_DETAIL, "PULSE [" + i + "] has a failed transaction - reconnect");

						try {
							c_adbOPICM[i].close();
						}
						catch (MiddlewareException mx) {
							System.out.println("DatabasePool:cleanup on close:" + mx);
						}

						// Reconnect to the database
						c_adbOPICM[i] = null;
						c_adbOPICM[i] = new Database(MiddlewareServerProperties.getPDHDatabaseURL(),
								MiddlewareServerProperties.getPDHDatabaseUser(), MiddlewareServerProperties.getPDHDatabasePassword(),
								MiddlewareServerProperties.getODSDatabaseURL(), MiddlewareServerProperties.getODSDatabaseUser(),
								MiddlewareServerProperties.getODSDatabasePassword(),
								MiddlewareServerProperties.getODS2DatabaseURL(), MiddlewareServerProperties.getODS2DatabaseUser(),
								MiddlewareServerProperties.getODS2DatabasePassword());

						// attach the Object Cache to it
						//c_adbOPICM[i].setObjectPool(c_oObjectCache);
						c_adbOPICM[i].setInstanceName(m_strInstanceName);
						c_adbOPICM[i].setConnectionID(i);
						// go ahead, make the connection
						c_adbOPICM[i].connect();
						// And make it available again
						setInUse(i, false);

						++c_iReconnects;
					}
				}

				// Implement a keep alive feature which exercises connections on a regular basis [firewall workaround]
				if (c_iKeepAlive > 0) {
					// If current time > time due
					if (System.currentTimeMillis() > c_lKeepAliveDue) {
						// Do exercise
						exercise();
						// Save current time + quantum as next due time
						c_lKeepAliveDue = System.currentTimeMillis() + c_iKeepAlive;
					}
				}
			}
			catch (Exception x) {
				System.out.println("DatabasePool:cleanup:" + x);
			}
		}

		D.ebug("DatabasePool housekeeping thread is exiting.");
	}

	/**
	 * Perform a transaction to "exercise" each connection [if possible]
	 */
	public final void exercise() {
		ReturnStatus returnStatus = new ReturnStatus( -1);

		try {
			for (int i = 0; i <= c_iMaxConnections; ++i) {
				D.ebug("KEEP ALIVE transaction initiated");
				Database dbCurrent = getConnection("keep_alive");

				ResultSet rsPDH = dbCurrent.callGBL2028(returnStatus);
				while (rsPDH.next()) {
				}
				rsPDH.close();
				dbCurrent.commit();
				dbCurrent.freeStatement();
				freeConnection(dbCurrent);
				D.ebug("KEEP ALIVE transaction completed");
			}
		}
		catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble exercising " + x);
		}
	}

	/**
	 * Return the characteristics of the <code>DatabasePool</code> object
	 */
	public final String toString() {

		StringBuffer strbStats = new StringBuffer();
		long lNow = System.currentTimeMillis();

		strbStats.append("PULSE threads " + c_iThreadCount + " pools " + c_iPoolCount);
		strbStats.append(" uptime: " + Stopwatch.format(lNow - c_lStartTime));
		strbStats.append(" transactions: " + c_iTransactions);
		strbStats.append(" searches: " + c_iSearches);
		strbStats.append(" sleeps: " + c_iSleeps);
		strbStats.append(" timeouts: " + c_iTimeouts);
		strbStats.append(" reconnects: " + c_iReconnects);
		strbStats.append(" last transaction: " +
				( (c_lLastConnectionFreed > 0) ? (Stopwatch.format(lNow - c_lLastConnectionFreed) + " ago") : "none"));

		return new String(strbStats);
	}

	/**
	 * Return an individual <code>Database</code> object in the <code>DatabasePool</code> object
	 */
	public final Database getDatabase(int _iWhich) {
		return c_adbOPICM[_iWhich];
	}

	/**
	 * Force a reset of a database (does this need to be sync'd?)
	 */
	public final void reset(int _iWhich) {
		setInUse(_iWhich, false);
	}

	/**
	 * An internal private method to display the status of the Housekeeping thread
	 */
	private final void housekeepingStatus() {

		if (c_thChecker != null) {
			D.ebug(D.EBUG_SPEW,
					"THREAD Housekeeping thread alive: " + c_thChecker.isAlive() + " interrupted: " + c_thChecker.isInterrupted());

			if (!c_thChecker.isAlive()) {
				D.ebug(D.EBUG_ERR, "THREAD ERR Housekeeping thread is not alive!");
			}

			if (c_thChecker.isInterrupted()) {
				D.ebug(D.EBUG_ERR, "THREAD ERR Housekeeping thread is interrupted!");
			}
		} else {
			D.ebug(D.EBUG_ERR, "THREAD ERR Housekeeping thread is null");
		}
	}

	//protected final void resetObjectCache() {
	//  c_oObjectCache.removeAll();

	//  c_oObjectCache = new OPICMList();
	//}

	//  /**
	//   * Acquire all connections in the pool
	//   */
	//  protected final void lockAll(String _strPurpose) {
	//
	//    long lStartTime = System.currentTimeMillis();
	//    long lCurrentTime = 0;
	//    Database[] dbCurrent = new Database[this.getPoolSize()];
	//
	//    // Grab all the database connections in the pool
	//    for (int i = 0; i < this.getPoolSize(); i++) {
	//      lCurrentTime = System.currentTimeMillis();
	//
	//      if ((lCurrentTime - lStartTime) > 60000) {
	//        D.ebug(D.EBUG_ERR, "Been trying to get all connections for too long - giving up");
	//
	//        return;
	//      }
	//
	//      try {
	//        dbCurrent[i] = this.getConnection(_strPurpose);
	//      } catch (MiddlewareShutdownInProgressException sx) {}
	//      catch (MiddlewareException mx) {
	//        // Will need to try this one again
	//        --i;
	//
	//        // Sleep a bit, don't work server too hard, let current transactions finish
	//        try {
	//          Thread.sleep(1000);
	//        } catch (Exception x) {}
	//      }
	//    }
	//  }
	/**
	 * init the pool
	 */
	public final void init() {

		D.isplay("DatabasePool has been requested to initialize");

		// Test connections
		try {
			ReturnStatus returnStatus = new ReturnStatus( -1);

			if (MiddlewareServerProperties.getTestConnect()) {
				D.ebug(D.EBUG_DETAIL, "Verify connectivity to database");

				for (int i = 0; i < c_iMaxConnections; i++) {
					Database dbCurrent = null;

					dbCurrent = this.getConnection("verify");

					if (i == 0) {
						// Show version literal
						D.ebug(D.EBUG_INFO, "Program version literal: " + Database.CURRENT_VERSION_LITERAL);
					}

					ResultSet rsPDH = dbCurrent.callGBL2028(returnStatus);
					while (rsPDH.next()) {
						dbCurrent.debug(D.EBUG_DETAIL, "PDH now     ==" + rsPDH.getString(1).trim() + "==");
						dbCurrent.debug(D.EBUG_DETAIL, "PDH forever ==" + rsPDH.getString(2).trim() + "==");
						dbCurrent.debug(D.EBUG_DETAIL, "PDH epoch   ==" + rsPDH.getString(3).trim() + "==");
					}
					rsPDH.close();
					dbCurrent.freeStatement();

					if (dbCurrent.hasODS()) {
						ResultSet rsODS = null;
						try{
							// RCQ285768 ODS usually do not have SPs, log only if it fails
							rsODS = dbCurrent.callGBL9999(returnStatus);
							while (rsODS.next()) {
								dbCurrent.debug(D.EBUG_DETAIL, "ODS now     ==" + rsODS.getString(1).trim() + "==");
								dbCurrent.debug(D.EBUG_DETAIL, "ODS forever ==" + rsODS.getString(2).trim() + "==");
								dbCurrent.debug(D.EBUG_DETAIL, "ODS epoch   ==" + rsODS.getString(3).trim() + "==");
							}
						}catch(Exception exc){
							dbCurrent.debug(D.EBUG_ERR, "WARNING: Unable to verify ODS connection. " + exc.getMessage());
						}finally{
							if(rsODS!=null){
								try{
									rsODS.close();
								}catch(Exception e2){}
							}
							dbCurrent.freeStatement();
							dbCurrent.closeODS();
						}
					}

					if (dbCurrent.hasODS2()) { //RTC1119727
						ResultSet rsODS =null;
						try{
							rsODS = dbCurrent.callGBL99992(returnStatus);
							while (rsODS.next()) {
								dbCurrent.debug(D.EBUG_DETAIL, "ODS2 now     ==" + rsODS.getString(1).trim() + "==");
								dbCurrent.debug(D.EBUG_DETAIL, "ODS2 forever ==" + rsODS.getString(2).trim() + "==");
								dbCurrent.debug(D.EBUG_DETAIL, "ODS2 epoch   ==" + rsODS.getString(3).trim() + "==");
							}
						}catch(Exception exc){
							dbCurrent.debug(D.EBUG_ERR, "WARNING: Unable to verify ODS2 connection. " + exc.getMessage());
						}finally{
							if(rsODS!=null){
								try{
									rsODS.close();
								}catch(Exception e2){}
							}
							dbCurrent.freeStatement();
							dbCurrent.closeODS2();
						}
					}


					dbCurrent.debug(D.EBUG_DETAIL, "Return Status = " + returnStatus);
					this.freeConnection(dbCurrent);

					if (returnStatus.intValue() != 0) {
						dbCurrent.debug(D.EBUG_ERR, "Error establishing connectivity - goodbye!");
						System.exit( -1);
					}

					try {
						Thread.sleep(MiddlewareServerProperties.getConnectionPause());
					}
					catch (Exception x) {}
				}
			}else{
				D.ebug(D.EBUG_DETAIL, "Initialize PDH connection to database");
				// the pdh connection is never established if the test isnt done and all subsequent remote calls fail
				// when setAutoCommit() executes because there is no pdh connection
				for (int i = 0; i < c_iMaxConnections; i++) {
					Database dbCurrent = this.getConnection("verify");
					dbCurrent.connect();
					this.freeConnection(dbCurrent);

					try {
						Thread.sleep(MiddlewareServerProperties.getConnectionPause());
					}
					catch (Exception x) {}
				}
			}
		}
		catch (Exception x) {
			D.ebug(D.EBUG_ERR, "RemoteDatabase:main " + x);
			// If test fails on any connection, then fail hard
			System.exit( -1);
		}

		// Ready for action
		try {
			File f1 = new File("mwinit");

			if (f1.exists()) {
				D.isplay("Removing mwinit to change operational mode");
				f1.delete();
				D.ebug(D.EBUG_DETAIL, "Clearing mwinit file");
			}
		}
		catch (Exception x) {}

		D.ebug("Ready for incoming connections");
	}

	/**
	 * shutdown the pool
	 */
	public final void shutdown() {

		int iConnections = 0;
		Database dbCurrent[] = null;

		iConnections = MiddlewareServerProperties.getDatabaseConnections();
		dbCurrent = new Database[iConnections];

		D.isplay("DatabasePool has been requested to shutdown");

		// Grab all the database connections in the pool
		for (int i = 0; i < iConnections; i++) {
			try {
				dbCurrent[i] = this.getConnection("shutdown");
			}
			catch (MiddlewareShutdownInProgressException mx) {}
			catch (MiddlewareException mx) {}

			// Sleep a bit, don't work server too hard, let current transactions finish
			try {
				Thread.sleep(1000);
			}
			catch (Exception x) {}
		}

		// Close them all
		for (int i = 0; i < iConnections; i++) {
			try {
				dbCurrent[i].close();
			}
			catch (Exception x) {}

			dbCurrent[i] = null;
		}

		// Set a value which will cause the run method to exit
		c_bCheckerRun = false;
		// The thread will no longer exist now
		c_thChecker = null;
		// So getConnection will throw Exception
		bShutDownPending = true;
	}

}
