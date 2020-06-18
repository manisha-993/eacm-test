//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Idler.java,v $
// Revision 1.109  2008/11/06 16:26:52  wendy
// Release memory
//
// Revision 1.108  2008/10/31 14:17:44  wendy
// Release memory between launches
//
// Revision 1.107  2006/06/22 20:19:33  dave
// ok.. no more waits
//
// Revision 1.106  2006/06/22 06:15:57  dave
// more cleanup
//
// Revision 1.105  2006/06/22 06:00:11  dave
// more stuff
//
// Revision 1.104  2006/06/22 05:38:50  dave
// more cleanujp
//
// Revision 1.103  2006/06/22 05:36:20  dave
// more db2 stuff
//
// Revision 1.102  2006/06/22 05:19:02  dave
// more changes
//
// Revision 1.101  2005/02/03 19:30:15  dave
// cleanup
//
// Revision 1.100  2005/02/01 20:13:40  dave
// extra bracker
//
// Revision 1.99  2005/02/01 20:04:36  dave
// fixed null pointer
//
// Revision 1.98  2005/02/01 20:00:59  dave
// trapping a null pointer
//
// Revision 1.97  2005/02/01 19:43:46  dave
// testing
//
// Revision 1.96  2005/01/31 18:37:33  dave
// Jtest  cleanup
//
// Revision 1.95  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.94  2005/01/26 01:05:18  dave
// Jtest cleanup
//
// Revision 1.93  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.92  2005/01/24 21:58:58  dave
// starting to clean up per new IBM standards
//
// Revision 1.91  2005/01/12 23:05:39  roger
// Try to get job # in logging
//
// Revision 1.90  2005/01/12 19:49:16  roger
// Try this for TM logging
//
// Revision 1.89  2004/11/22 21:57:51  roger
// Set InstanceName properly
//
// Revision 1.88  2004/10/13 21:02:56  dave
// syntax
//
// Revision 1.87  2004/10/13 20:57:33  dave
// logging for interleaved
//
// Revision 1.86  2004/10/05 20:16:06  roger
// Show OPENID at start
//
// Revision 1.85  2004/06/02 16:38:22  roger
// Show ABR time in Queue on PSTAT line (XXX changed to PSTAT)
//
// Revision 1.84  2004/05/27 20:47:01  dave
// changed add defaults
//
// Revision 1.83  2004/05/26 21:06:37  roger
// change STAT to XXX
//
// Revision 1.82  2004/05/26 20:53:23  roger
// Output STAT properly
//
// Revision 1.81  2004/05/26 18:53:07  dave
// extra cleanup
//
// Revision 1.80  2004/05/19 16:35:29  roger
// Work around protection
//
// Revision 1.79  2004/05/19 16:24:29  roger
// Include some run stats to help categorize ABRs
//
// Revision 1.78  2004/05/11 15:11:17  dave
// clean.connect attempt. cannot trust abstract task to keep
// database connection clean and error free
//
// Revision 1.77  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;

import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareServerDynamicProperties;
import COM.ibm.opicmpdh.middleware.T;
import java.rmi.MarshalledObject;


import java.rmi.RemoteException;
import java.rmi.activation.ActivationID;


import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;

import COM.ibm.opicmpdh.transactions.OPICMABRItem;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public final class Idler extends RemoteTask implements IdlerInterface {
  /**
     * BASE OBJECT NAME
   */
  public final static String BASE_OBJECT_NAME = "Idler";
  private static Log c_log = null;
  private static Connection c_connTaskMaster = null;
  private static String c_strObjectName = "unknown";
  private AbstractTask m_at = null;
  private Database m_db = null;
  private Shutter m_shut = null;
  private RegisterObject m_ro = null;
  private int m_iLaunchCount = 0;

  /**
     * Makes a New Idler Object for RMI activation
     *
     * @param _id
     * @param _data
     * @throws java.rmi.RemoteException
     * @author Dave
     */
  public Idler(ActivationID _id, MarshalledObject _data)
    throws RemoteException {

    super(_id, 0);

    try {
      c_strObjectName = (String) _data.get();
    } catch (Exception x) {
      Log.out("unable to get in activation constructor " + x);
    }

    try {

      c_log = new Log(c_strObjectName);

      setName(c_strObjectName);

      c_connTaskMaster = new Connection(TaskMaster.OBJECT_NAME);
      // Keep this object registered
      m_ro = new RegisterObject(c_strObjectName, this);

      c_log.log("Initializing database connection");

      m_db = new Database();

      T.estIt(m_db != null, "Database is null!");
      m_db.setInstanceName(MiddlewareServerDynamicProperties.getInstanceName() + ":" + getName());
      m_db.setLogPrefix(this +"");
      // We are ready
      c_log.log(this +" is ready");
    } catch (Exception z) {
      Log.out("an exception was thrown in the activation constructor for idler " + z);
    }
  }
  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.IdlerInterface#getStatus()
   * @author Dave
   */
  public final String getStatus() {

    String strResult = "" + this;

    try {
      if (getBusy()) {
        strResult = strResult + " BUSY";

        if (m_at == null) {
          strResult = strResult + " elapsed: " + "UNKNOWN";
        } else {
          String strAt = "" + m_at;

          if (strAt.length() > 0) {
            strResult = strResult + " " + strAt;
          }

          strResult = strResult + " " + m_at.getABRDescription() + " elapsed: " + Stopwatch.format(m_at.getElapsedTime());
        }
      } else {
        strResult = strResult + " AVAILABLE";
      }
    } catch (Exception x) {
      c_log.log("trouble checking if task busy " + x);
    }

    strResult = strResult + " launches: " + m_iLaunchCount+" "+D.etermineMemory();

    return strResult;
  }
  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.RemoteRunnable#getUseCount()
   * @author Dave
   */
  public int getUseCount() throws RemoteException {
    return m_iLaunchCount;
  }
  /**
     * launchClass
     *
     * @author Dave
     * @return int
     * @param _strTaskName
     * @param _strClassName
     * @param _strJobName
     * @param _iJobID
     * @param _prof
     * @param _abri
     * @param _iWhich
     * @throws java.rmi.RemoteException
     */
  public final int launchClass(
    String _strTaskName,
    String _strClassName,
    String _strJobName,
    int _iJobID,
    Profile _prof,
    OPICMABRItem _abri,
    int _iWhich)
    throws RemoteException {

    int rc = AbstractTask.RETURNCODE_FAILURE;
    boolean bCanRunABR = true;

    T.estIt(m_db != null, "Database is null!");
    // Validate parameters
    T.estIt(_strTaskName != null, "TaskName is null");
    T.estIt(_strClassName != null, "ClassName is null");
    T.estIt(_strJobName != null, "JobName is null");
    T.estIt(_iJobID >= 0, "jobID is too small");
    T.estIt(_prof != null, "Profile is null");
    T.estIt(_abri != null, "ABRItem is null");
    T.estIt(_iWhich >= 0, "which is too small");

    // Load the specified ABR class in preparation for execution
    try {
      System.gc(); // allow any memory to be released
      c_log.log(this+" creating instance of ABR code class = '" + _strClassName + "' "+D.etermineMemory());

      m_at = (AbstractTask) Class.forName(_strClassName).newInstance();
    } catch (Exception x) {
      c_log.log("trouble loading class '" + _strClassName + "' " + x);

      bCanRunABR = false;
    }

    // Can't run a null task
    if (m_at == null) {
      c_log.log("can't run a null AbstractTask");
      bCanRunABR = false;
    }

    // If all is well, we can attempt to run ABR now
    if (bCanRunABR) {

      boolean bAgain = true;

      DatePackage dpDates = null;
      Runner runner = null;

      try {
        c_log.log(this +" testing a get now!!");
        c_log.log(this +" forcing a clean connection ");
        m_db.getNow();
        m_db.close();
        m_db.connect();
      } catch (Exception x) {
          // Lets hand out a new clean database connection
          c_log.log(this +" problems ... forcing a clean connection ");
          try {
            m_db = null;
            m_db = new Database();
          } catch (Exception xx) {
            c_log.log(this + " had trouble cleaning connection " + xx);
          }
      } finally {
    	  if (m_db!= null){
    		  m_db.freeStatement();
    		  m_db.isPending("flushing clean connection");
    	  }
      }

      dpDates = new DatePackage(m_db);

      // Set AbstractTask values as needed to run the ABR
      c_log.log("setting values on AT now");
      c_log.log("setting job name to " + _strJobName);
      m_at.setJobName(_strJobName);
      c_log.log("setting job id to " + _iJobID);
      m_at.setJobID(_iJobID);
      c_log.log("setting database");
      m_at.setDatabase(m_db);
      c_log.log("setting profile");
      m_at.setProfile(_prof);
      c_log.log("setting abri");
      m_at.setABRItem(_abri);
      c_log.log("initPrintWriter now");
      m_at.initPrintWriter(dpDates.m_strNow);
      c_log.log("creating runner for " + m_at + " . " + m_at.getABRDescription());

      m_db.setLogPrefix(this +" " + m_at);

      // Create a thread we will run the ABR in
      runner = new Runner(m_at, this);

      T.estIt(runner != null, "runner is null");
      c_log.log("runner will now start the ABR");
      runner.start();
      c_log.log(m_at + " task has been INITIATED");

      ++m_iLaunchCount;


      while (bAgain) {
        try {
          c_log.log("waiting for task completion ...");
          Thread.sleep(TaskMasterProperties.getTaskCompletionWait());

          bAgain = false;

          if ((runner != null) && runner.isAlive()) {
            bAgain = true;
          }
        } catch (Exception x) {
          c_log.log("error waiting for task completion " + x);
        }
      }

      c_log.log("runner has completed the ABR");
      m_at.finish();

      m_db.setLogPrefix(this +"");
      rc = m_at.getReturnCode();

      if (rc == AbstractTask.RETURNCODE_SUCCESS) {
        L.debug(
          m_at
            + " task has SUCCEEDED elapsed time: "
            + Stopwatch.format(m_at.getElapsedTime())
            + " RC("
            + rc
            + ")");
      } else {
        L.debug(
          m_at
            + " task has FAILED elapsed time: "
            + Stopwatch.format(m_at.getElapsedTime())
            + " RC("
            + rc
            + ")");
      }
      L.debug(
          "PSTAT"
          + " idler:"
          + c_strObjectName
          + " idlerClass:"
          + _abri.getIdlerClass()
          + " abrClass:"
          + _abri.getABRClassName()
          + " inQ:"
          + Stopwatch.format(_abri.getInQueueTime())
          + " elapsed:"
          + Stopwatch.format(m_at.getElapsedTime()));

    } else {
      L.debug(
        _strJobName
          + " FAILED unable to run null task"
          + " RC("
          + rc
          + ")");
    }

    m_at.setDefaults();
    m_at.dereference();
    m_at=null;
    return rc;
  }
  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.IdlerInterface#shut()
   * @author Dave
   */
  public final void shut() throws RemoteException {
    c_log.log("has been requested to exit");
    m_ro.shut();

    m_shut =
      new Shutter(
        this,
        c_strObjectName,
        TaskMasterProperties.getIdlerShutDownDelay());
  }
  /**
   * @see java.lang.Object#toString()
   * @author Dave
   */
  public final String toString() {
    return "[" + c_strObjectName + "]";
  }
  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public static String getVersion() {
    return "$Id: Idler.java,v 1.109 2008/11/06 16:26:52 wendy Exp $";
  }
}
