//
// Copyright (c) 2006, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DatabaseAdapter.java,v $
// Revision 1.15  2006/05/17 20:44:32  roger
// Changes for new WAS pooling
//
// Revision 1.14  2006/05/17 19:57:14  roger
// New pooling for WAS
//
// Revision 1.13  2006/05/16 21:22:47  roger
// More logging to find problem
//
// Revision 1.12  2006/05/04 16:29:05  roger
// Changes for new WAS pooling
//
// Revision 1.11  2006/03/13 17:53:45  roger
// Fix
//
// Revision 1.10  2006/03/13 17:30:48  roger
// Fix
//
// Revision 1.9  2006/03/10 18:25:40  roger
// Fixes
//
// Revision 1.8  2006/03/10 18:17:37  roger
// Fixes
//
// Revision 1.7  2006/03/10 18:11:48  roger
// Fixes
//
// Revision 1.6  2006/03/10 17:23:14  roger
// Fixes
//
// Revision 1.5  2006/03/10 17:17:27  roger
// Fixes
//
// Revision 1.4  2006/03/10 17:02:51  roger
// Fixes
//
// Revision 1.3  2006/03/10 16:56:54  roger
// Fixes
//
// Revision 1.2  2006/03/10 16:50:58  roger
// Fixes
//
// Revision 1.1  2006/03/10 16:42:41  roger
// New pooling for WAS
//
//
package COM.ibm.opicmpdh.middleware;

import COM.ibm.db2.jdbc.app.*;
import java.io.*;
import java.sql.*;
import COM.ibm.opicmpdh.transactions.*;

public final class DatabaseAdapter {
  // Class variables
  private static Database c_adbOPICM[] = null;
  private static boolean c_abConnectionInUse[] = null;
  private static String c_strConnectionId[] = null;
  // Number of databases in pool
  private static int c_iMaxConnections = 0;
  private static int c_iSleepTime = 0;
  private static int c_iUseConnection = -1;
  private static int c_iWaitTimeout = -1;
  private static long c_lLastConnectionFreed = 0;
  private static long c_lStartTime = 0;
  // Counters
  private static int c_iTransactions = 0;
  private static int c_iSearches = 0;
  private static int c_iSleeps = 0;
  private static int c_iTimeouts = 0;
  // From the property file
  private String m_strInstanceName = "iname";
  public final static String NOID_SPECIFIED = "NOID";
  private static boolean c_bBusyDB = false;

  public DatabaseAdapter() {

    // How many connections in the adapter?
    c_iMaxConnections = MiddlewareServerProperties.getDatabaseConnections();
    // Build the array of variables
    c_adbOPICM = new Database[c_iMaxConnections];
    c_abConnectionInUse = new boolean[c_iMaxConnections];
    c_strConnectionId = new String[c_iMaxConnections];

    // Keep others away while we grab all connections
    // Lock all the connections
    for (int i = 0; i < this.getPoolSize(); i++) {
      setInUse(i, true);

      c_strConnectionId[i] = "";
    }

    // We own all the connections now so we are safe to do whatever we wish with them
    // Used for calculating uptime
    c_lStartTime = System.currentTimeMillis();

    // Redirect output if requested
    String strLogFileName = MiddlewareServerProperties.getLogFileName();

    if (strLogFileName.length() > 0) {
      D.ebugSetOut(strLogFileName);
    }

    D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());
    D.ebug(MiddlewareServerProperties.getTrace());
    D.ebug(D.EBUG_DETAIL, "(DatabaseAdapter) tracing enabled");
    D.ebug(D.EBUG_DETAIL, "Creating " + c_iMaxConnections + " connection " + ((c_iMaxConnections == 1) ? "adapter" : "adapters") + " to WebSphere Pool");

    c_iSleepTime = MiddlewareServerProperties.getWaitSleepTimeMS();

    D.ebug(D.EBUG_DETAIL, "Wait sleep time " + c_iSleepTime + " ms.");

    c_iWaitTimeout = MiddlewareServerProperties.getWaitTimeoutMS();

    D.ebug(D.EBUG_DETAIL, "Wait Timeout " + c_iWaitTimeout + " ms.");

    m_strInstanceName = MiddlewareServerDynamicProperties.getInstanceName();

    // Initialize the arrays of variables (keep connections locked)
    for (int i = 0; i < this.getPoolSize(); i++) {
      D.ebug(D.EBUG_DETAIL, "Establishing adapter [" + i + "] in DatabaseAdapter constructor");

      c_adbOPICM[i] = new Database();

      // Set some properties
      c_adbOPICM[i].setInstanceName(m_strInstanceName);
      c_adbOPICM[i].setConnectionID(i);
      D.ebug(D.EBUG_DETAIL, "Adapter [" + i + "] is initialized");
    }

    // Initialize the counters
    c_iTransactions = 0;
    c_iSearches = 0;
    c_iSleeps = 0;
    c_iTimeouts = 0;

    // Unlock all the connections (don't need to synch cause OK if they are used as soon as freed)
    for (int i = 0; i < this.getPoolSize(); i++) {
      setInUse(i, false);
    }
  }
  protected final static void reloadProperties() {
    c_iSleepTime = MiddlewareServerProperties.getWaitSleepTimeMS();
    c_iWaitTimeout = MiddlewareServerProperties.getWaitTimeoutMS();
  }
  public final int getPoolSize() {
    return c_adbOPICM.length;
  }
  public final String getVersion() {
    return new String("$Id: DatabaseAdapter.java,v 1.15 2006/05/17 20:44:32 roger Exp $");
  }
  public synchronized final Database getConnection(String _strPurpose, Connection _PDHconn, Connection _ODSconn, String _strId) throws MiddlewareWaitTimeoutException, MiddlewareShutdownInProgressException {

    long lWaitBegin = 0;
    long lNow = 0;
    int iStartingPoint = 0;
    int iSearched = 0;
    int iSlept = 0;
    int iIdConnection = -1;
    int iReturnIndex = -1;

    c_bBusyDB = true;

    ++c_iTransactions;

    iIdConnection = -1;

    // Check if _strId is already holding a connection, if so report it.
    if (!(_strId.equalsIgnoreCase(NOID_SPECIFIED))) {
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
      c_iUseConnection = ((++c_iUseConnection) % c_iMaxConnections);

      // Cycle to next AVAIL connection, not just the next one - wait if necessary!
      while (getInUse(c_iUseConnection) == true) {
        ++c_iSearches;

        // Searches are a bad thing, let's report it
        D.ebug("CONNECTION forced to do a search to find a connection");

        c_iUseConnection = ((++c_iUseConnection) % c_iMaxConnections);

        ++iSearched;

        // If we have looped completely around, lets take some action
        if (c_iUseConnection == iStartingPoint) {
          lNow = System.currentTimeMillis();

          // If we have been trying for specific time it's time to bail out!
          if ((lNow - lWaitBegin) > c_iWaitTimeout) {
            ++c_iTimeouts;

            // DWB If we get a timeout .. we need to unbusify it before we leave
            c_bBusyDB = false;

            D.ebug("CONNECTION could not acquire connection within specified timeout period");

            throw new MiddlewareWaitTimeoutException();
          }

          // Can't get a connection now, let's sleep a bit
          try {
            Thread.sleep(c_iSleepTime);
            D.ebug("CONNECTION sleeping ...");

            ++iSlept;
            ++c_iSleeps;
          } catch (Exception x) {}
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
    c_adbOPICM[iReturnIndex].debug(D.EBUG_DETAIL, "CONNECTION acquired POOL searched: " + iSearched + " times slept: " + iSlept + " times");
    c_adbOPICM[iReturnIndex].debug(D.EBUG_DETAIL, "CONNECTION locked (" + _strPurpose + ") " + "Id (" + c_strConnectionId[iReturnIndex] + ")");
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

    // Ensure we are handing out in clean state
    c_adbOPICM[iReturnIndex].freeStatement();
    c_adbOPICM[iReturnIndex].isPending();

    c_bBusyDB = false;

    // insert the connection handles passed
    c_adbOPICM[iReturnIndex].setPDHHandle(_PDHconn);
    c_adbOPICM[iReturnIndex].setODSHandle(_ODSconn);

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
    if (!_strId.equalsIgnoreCase(NOID_SPECIFIED) &&!c_strConnectionId[_iWhich].equalsIgnoreCase(_strId)) {
      D.ebug("FISH connectionId's do not match for index " + _iWhich + ".  current strId:" + c_strConnectionId[_iWhich] + ":Passed:" + _strId);

      return;
    }

    c_adbOPICM[_iWhich].debug(D.EBUG_DETAIL, "CONNECTION freed (" + c_adbOPICM[_iWhich].getPurpose() + ") Id (" + c_strConnectionId[_iWhich] + ") " + Stopwatch.format(c_adbOPICM[_iWhich].getLockAge()));
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
  public final Database getDatabase(int _iWhich) {
    return c_adbOPICM[_iWhich];
  }
}
