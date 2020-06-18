
//Copyright (c) 2002,2013 International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: TaskMaster.java,v $
//Revision 1.238  2014/02/28 21:21:07  wendy
//JVMCI015:OutOfMemoryError cleanup
//
//Revision 1.237  2013/09/11 18:52:45  wendy
//add property for refreshing queue after abr completes
//
//Revision 1.236  2013/08/22 16:34:13  wendy
//get now from profile, synch on class object
//
//Revision 1.235  2013/08/16 22:43:22  wendy
//increment jobid when launching job
//
//Revision 1.234  2013/08/06 18:40:50  wendy
//do not freestatement in run loop
//
//Revision 1.233  2013/07/29 16:38:29  wendy
//change trace level on some msgs
//
//Revision 1.232  2013/05/24 14:12:50  wendy
//taskmaster perf enhancements include:
// - Made idler classes case sensitive - can have 52 now
// - Added callback to attempt to run another ABR as soon as one completes.
// - Replaced string lookups with hash set
// - if ABR is inprocess status when taskmaster starts, attempt to run the ABR again if readonly, if not readonly then fail it in the pdh and notify user that ABR was aborted
//
//Revision 1.231  2008/11/06 16:26:52  wendy
//Release memory

//Revision 1.230  2006/08/18 20:28:59  joan
//back to rev 1.228

//Revision 1.229  2006/08/14 16:50:06  joan
//use fifo to get queued item from the group

//Revision 1.228  2006/06/22 20:19:33  dave
//ok.. no more waits

//Revision 1.227  2006/06/22 05:19:02  dave
//more changes

//Revision 1.226  2006/06/22 04:59:05  dave
//ok more trimming

//Revision 1.225  2006/06/22 04:54:38  dave
//more spew removal

//Revision 1.224  2006/06/22 04:52:09  dave
//more stuff

//Revision 1.223  2006/06/22 04:40:33  dave
//more changes

//Revision 1.222  2006/06/22 04:37:49  dave
//more tweeks

//Revision 1.221  2006/06/22 04:28:37  dave
//a couple more things

//Revision 1.220  2006/06/22 04:06:41  dave
//more spew

//Revision 1.219  2006/06/22 03:48:33  dave
//SPEWING more DETAIL LESS

//Revision 1.218  2006/06/22 03:38:01  dave
//some trickery

//Revision 1.217  2006/06/22 03:35:12  dave
//some trickery

//Revision 1.216  2006/06/21 17:24:31  roger
//Needed import

//Revision 1.215  2006/06/21 17:23:45  roger
//Changed to spew

//Revision 1.214  2006/06/21 17:18:36  roger
//Change to spew

//Revision 1.213  2006/03/03 17:19:06  roger
//For recurring ABRs

//Revision 1.212  2006/03/03 17:14:09  roger
//For recurring ABRs

//Revision 1.211  2006/03/03 17:05:33  roger
//For recurring ABRs

//Revision 1.210  2006/03/01 16:58:53  roger
//For recurring ABRs

//Revision 1.209  2005/03/29 17:01:30  roger
//Fix

//Revision 1.208  2005/03/29 16:52:34  roger
//Try this for setting read/write lang

//Revision 1.207  2005/03/16 17:21:43  dave
//cannot use an NLSID as in integer index into the
//vector that holds NLSItems.  Get and out of bounce
//exception.

//Revision 1.206  2005/03/11 20:28:56  roger
//Support foreign ABR

//Revision 1.205  2005/02/01 19:43:37  dave
//testing

//Revision 1.204  2005/02/01 19:29:12  dave
//Removed Static Referene

//Revision 1.203  2005/02/01 19:22:38  dave
//more Jtest clean up.

//Revision 1.202  2005/01/26 17:53:53  dave
//some fixes

//Revision 1.201  2005/01/26 17:47:39  dave
//more JTest reformatting per IBM

//Revision 1.200  2005/01/26 01:18:11  dave
//syntax

//Revision 1.199  2005/01/26 01:05:19  dave
//Jtest cleanup

//Revision 1.198  2005/01/25 23:16:50  dave
//more JTest cleanup

//Revision 1.197  2005/01/12 22:20:49  roger
//Try this for TM logging

//Revision 1.196  2004/11/23 17:05:33  roger
//setInstanceName for "profile" database

//Revision 1.195  2004/11/22 21:57:51  roger
//Set InstanceName properly

//Revision 1.194  2004/10/14 17:19:02  dave
//more trace for ECCM Stuff

//Revision 1.193  2004/10/13 21:26:52  dave
//more trace on interleave ABR

//Revision 1.192  2004/10/12 22:35:26  dave
//minor fixes and taking a nap .. if all idlers are busy

//Revision 1.191  2004/08/30 16:26:29  dave
//syntax

//Revision 1.190  2004/08/30 16:19:58  dave
//ok.. if no abrs are found to run and idlers are still
//free.. might as well as sleep

//Revision 1.189  2004/08/17 15:49:59  roger
//Support interleaved execution of ABRs

//Revision 1.188  2004/06/10 18:12:12  dave
//either sleep or use the SP time for GBL3000 as sleep if there
//is work to do

//Revision 1.187  2004/05/26 18:34:24  dave
//added stacktrace

//Revision 1.186  2004/05/26 17:28:07  roger
//More T.est

//Revision 1.185  2004/04/22 22:21:12  roger
//Show the build date/time in start banner

//Revision 1.184  2004/04/07 20:14:32  roger
//Needed to move to next ABR once launched

//Revision 1.183  2004/04/07 18:06:52  roger
//Messages

//Revision 1.182  2004/04/07 16:13:30  roger
//Actually remove ABRs which can never be run

//Revision 1.181  2004/04/06 23:17:24  roger
//Closer

//Revision 1.180  2004/04/06 23:01:29  roger
//Should be done

//Revision 1.179  2004/04/06 22:53:57  roger
//Closing in ...

//Revision 1.178  2004/04/06 22:43:01  roger
//What a dumb mistake ...

//Revision 1.177  2004/04/06 22:17:11  roger
//Chase

//Revision 1.176  2004/04/06 22:07:59  roger
//Still chasing

//Revision 1.175  2004/04/06 21:54:25  roger
//Chase

//Revision 1.174  2004/04/06 20:52:27  roger
//Clean up - pretty print

//Revision 1.173  2004/04/06 20:24:27  roger
//Chase

//Revision 1.172  2004/04/06 20:15:23  roger
//Chase

//Revision 1.171  2004/04/06 20:01:52  roger
//Chase

//Revision 1.170  2004/04/06 19:22:10  roger
//Chase

//Revision 1.169  2004/04/06 18:03:10  roger
//Chase

//Revision 1.168  2004/04/06 17:20:26  roger
//Chase

//Revision 1.167  2004/04/06 17:07:58  roger
//Chasing a bug, don't know where it is

//Revision 1.166  2004/04/06 16:46:53  roger
//Chasing bug in abr q

//Revision 1.165  2004/04/06 16:35:53  roger
//Remove the remove

//Revision 1.164  2004/04/06 16:33:28  roger
//New message

//Revision 1.163  2004/04/06 16:16:00  roger
//Chasing bug and added code to remove ABRs which can never be run based on class

//Revision 1.162  2004/04/05 23:16:23  roger
//Chasing bug

//Revision 1.161  2004/04/05 23:03:59  roger
//Chase it

//Revision 1.160  2004/04/05 22:53:34  roger
//Fix the class compare

//Revision 1.159  2004/04/05 22:45:10  roger
//More detailed messages

//Revision 1.158  2004/03/30 21:44:37  roger
//Fix typo

//Revision 1.157  2004/03/30 21:38:16  roger
//Launch based on class match

//Revision 1.156  2004/03/18 21:46:42  roger
//Off by one ...

//Revision 1.155  2004/03/18 21:07:21  roger
//Make a little prettier

//Revision 1.154  2004/03/18 20:54:52  roger
//Make it compile

//Revision 1.153  2004/03/18 20:44:32  roger
//ABRs need execution class for Idler assignment

//Revision 1.152  2004/02/26 20:43:20  dave
//tracking inprocess abri's in the abrGroup

//Revision 1.151  2004/01/26 19:03:22  dave
//added one more gate.ungate

//Revision 1.150  2004/01/21 18:34:47  dave
//temp ungate launch

//Revision 1.149  2004/01/21 18:09:57  dave
//testing the removal of the last ungate in the final

//Revision 1.148  2004/01/19 21:24:55  dave
//uncommenting the commented out stuff for IBM

//Revision 1.147  2004/01/17 00:10:28  dave
//back out of occasional idler for ibm prod

//Revision 1.146  2004/01/16 22:54:33  dave
//try better syntax this time

//Revision 1.145  2004/01/16 22:38:10  dave
//alternate sync

//Revision 1.144  2004/01/16 22:31:48  dave
//try again

//Revision 1.143  2004/01/16 22:28:31  dave
//more trace

//Revision 1.142  2004/01/16 22:13:02  dave
//more trace

//Revision 1.141  2004/01/16 22:11:00  dave
//typo

//Revision 1.140  2004/01/16 22:09:22  dave
//more gate/ debug info to log file

//Revision 1.139  2004/01/16 19:13:58  dave
//added another loop to keep going if it finds another idler,
//and another abr - prior to it sleeping

//Revision 1.138  2003/12/18 17:54:25  roger
//On load failure, also shutdown

//Revision 1.137  2003/12/18 17:52:20  roger
//Really don't need this line twice

//Revision 1.136  2003/12/18 17:50:09  roger
//If a launch fails, set noLaunchMode

//Revision 1.135  2003/12/16 23:42:11  joan
//fix compile

//Revision 1.134  2003/12/16 21:53:37  roger
//Let's long DB gates and ungates for giggles, something fishy here

//Revision 1.133  2003/12/16 20:49:31  dave
//do we need to unGates in TaskMaster?

//Revision 1.132  2003/12/16 20:30:07  dave
//trace for gate issue.. use database connection for profile
//fetching

//Revision 1.131  2003/09/17 17:08:41  roger
//Mode is QEXIT not QEDIT!

//Revision 1.130  2003/09/17 16:35:36  roger
//Ooops, QEXIT needs to refresh *one* time

//Revision 1.129  2003/09/10 20:04:58  roger
//Must remove the tmrunning file in QEXIT mode

//Revision 1.128  2003/09/09 20:39:05  roger
//Sheesh

//Revision 1.127  2003/09/09 20:30:30  roger
//Have to write to the file

//Revision 1.126  2003/09/09 20:16:05  roger
//TM should be shut by creating the shut semaphore file

//Revision 1.125  2003/09/09 19:51:26  roger
//Issue message before quitting

//Revision 1.124  2003/09/09 19:03:40  roger
//Implement a run mode which grabs a queue and exits when the queue is completed

//Revision 1.123  2003/08/13 22:31:16  dave
//making changes for set status in TranslationII

//Revision 1.122  2003/07/16 16:52:28  dave
//adding one more ungate

//Revision 1.121  2003/05/14 20:30:59  dave
//need a quick fix for release that allows you to create
//a profile from an OPWGID that existed but is not current

//Revision 1.120  2002/11/08 22:31:44  roger
//refresh ABR queue only when needed (empty)

//Revision 1.119  2002/11/08 17:24:10  roger
//XXX status does not exist

//Revision 1.118  2002/11/07 23:18:03  roger
//Remove magic numbers for status values

//Revision 1.117  2002/11/07 21:58:50  roger
//Clean up

//Revision 1.116  2002/11/07 19:08:35  roger
//Must also enable the ABR

//Revision 1.115  2002/11/07 18:37:39  roger
//Must catch Exception

//Revision 1.114  2002/11/07 18:29:46  roger
//Need feature to select In Process ABRs

//Revision 1.113  2002/11/06 21:58:14  roger
//Fix once and for all

//Revision 1.112  2002/11/06 20:40:44  dave
//Temp comment out to bypass syntax error

//Revision 1.111  2002/11/06 20:08:28  roger
//Hmm

//Revision 1.110  2002/11/06 19:49:13  roger
//Cast to fix

//Revision 1.109  2002/11/06 19:34:00  roger
//Needed classname

//Revision 1.108  2002/11/06 19:14:19  roger
//Create restart/recovery feature

//Revision 1.107  2002/10/29 20:27:16  dave
//Added db Gating when we generate the the ABR profile
//for launch.

//Revision 1.106  2002/10/16 22:38:35  roger
//Cosmetic change just to bump id

//Revision 1.105  2002/10/16 19:37:01  roger
//Support multiple instances

//Revision 1.104  2002/10/15 23:13:05  roger
//More details in message

//Revision 1.103  2002/10/15 21:02:11  roger
//Show connection details

//Revision 1.102  2002/10/15 18:41:07  roger
//Use Connection.createURL to include non-standard port/host, etc

//Revision 1.101  2002/10/14 20:00:08  roger
//Syntax

//Revision 1.100  2002/10/14 19:47:40  roger
//Make user id a property

//Revision 1.99  2002/10/02 23:06:25  roger
//CVS change history/log restored



package COM.ibm.opicmpdh.middleware.taskmaster;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareServerDynamicProperties;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.D;

import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.transactions.OPICMABRGroup;
import COM.ibm.opicmpdh.transactions.OPICMABRItem;
import java.io.File;
import java.io.FileWriter;
import java.rmi.MarshalledObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.activation.ActivationID;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

//import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Properties;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public final class TaskMaster extends RemoteTask implements TaskMasterInterface, Runnable {
	private static final long serialVersionUID = 1L;
	/**
	 * TBD
	 *
	 */
	public static final String OBJECT_NAME = "TaskMaster"; //$NON-NLS-1$
	private static final Integer ILOCK = new Integer(0);
	private static final Integer ALOCK = new Integer(0);
	/**
	 * Name of LogFile
	 */
	public static final String LOGFILENAME = "taskmaster.out";
	/**
	 * Sephamore shutdown file
	 */
	public static final String SHUTDOWN_SEMAPHORE_FILE = "tmshut";
	/**
	 * am I running
	 */
	public static final String RUNNING_SEMAPHORE_FILE = "tmrunning";
	private static final String CURRENT_VERSION_LITERAL = ABRServerProperties.
	getVersionLiteral();
	private static final String PASSWORD =
		"put-anything-you-want-here-no-auth-is-done-anyway";

	private static int c_iIdlerCount = TaskMasterProperties.getIdlerCount();
	private static String c_strRunMode = TaskMasterProperties.getRunMode();
	private static int c_iJob = 0;
	private static int c_iTaskCount = 0;
	private static int c_iPulseTime = TaskMasterProperties.getPulseQuantum();
	private static long c_lStartTime = 0;
	private static boolean c_bCanLaunch = false;
	private static boolean c_bRunPulse = false;
	private static String c_strObjectName = "unknown";
	private static String strInterleavedMode = TaskMasterProperties.getInterleavedMode();
	private static boolean c_bRefreshOnCompletion = TaskMasterProperties.refreshQonCompletion(); 
	private static String c_strUser = TaskMasterProperties.getUserId();
	private static String c_strDBUser = null;
	private static String c_strEnterprise = null;

	static {
		L.debug(MiddlewareServerProperties.getDebugTraceLevel());  // match mw debug level
	}

	private Database m_db = null;
	private Connection m_aconn[] = new Connection[c_iIdlerCount];
	private ConnectionManager m_conMgr = null;
	private ExitWatcher m_ew = null;
	private RegisterObject m_ro = null;

	private TaskMasterRunner m_atmr[] = new TaskMasterRunner[c_iIdlerCount];
	private Thread m_thPulse = null;
	protected IdlerInterface m_aii[] = new IdlerInterface[c_iIdlerCount];
	private Profile m_prof = null;
	private OPICMABRGroup m_abrQueue = null;
	private DatePackage m_dpDates = null;
	private boolean m_bBusyDB = false;
	private HashSet idlerClassesSets[] = new HashSet[c_iIdlerCount];
	private HashSet allIdlerClasses = new HashSet();

	/**
	 * Taskmaster main
	 *
	 * @param _id
	 * @param _data
	 * @throws java.rmi.RemoteException
	 * @author Dave
	 */
	public TaskMaster(ActivationID _id, MarshalledObject _data) throws RemoteException {
		super(_id, 0);

		try {
			c_strObjectName = (String) _data.get();
		}
		catch (Exception x) {
			L.debug("MarshalledObject.get failed " + x);
		}

		System.out.println("TaskMaster is starting $Revision: 1.238 $"
				+ " run mode "
				+ c_strRunMode);

		try {
			//      c_log = new Log(c_strObjectName);
			L.debug("TaskMaster is starting $Revision: 1.238 $");
			setName(c_strObjectName);

			// Setup and start the PULSE thread
			m_thPulse = new Thread(this);

			m_thPulse.setPriority(Thread.MIN_PRIORITY);
			m_thPulse.setDaemon(true);
			m_thPulse.start();
		}
		catch (Exception x) {
			L.debug("an exception was thrown in the activation constructor for taskmaster "+ x);
		}
	}

	/**
	 * anyRunning?
	 *
	 * @return
	 * @author Dave
	 */
	public final boolean anyRunning() {
		return (getBusyCount() > 0);
	}

	private final void connectIdlers() {
		L.debug("# Idlers needed = " + m_aii.length);

		for (int i = 0; i < m_aii.length; i++) {
			m_aconn[i] = new Connection("Idler" + (char) ( ( (int) 'A' + i)));
			m_aii[i] = (IdlerInterface) m_conMgr.addConnection(m_aconn[i]);
		}
	}

	/**
	 * TBD
	 *
	 * @param _iWhich
	 * @author Dave
	 */
	public final void freeIdler(int _iWhich) {
		try {
			if (_iWhich >= 0) {
				L.debug("freeing " + m_aii[_iWhich].getName());
				m_aii[_iWhich].setBusy(false);
			}
		}
		catch (Exception x) {
			L.debug("trouble freeing Idler " + x);
		}
	}

	/**
	 * gateDB
	 *
	 * @param _strLocation
	 * @author Dave
	 * @concurrency $none
	 */
	public final void gateDB(String _strLocation) {

		while (m_bBusyDB) {
			L.debug("blocked at "
					+ _strLocation + " by "
					+ c_strDBUser + "; waiting ..."
					+ TaskMasterProperties.getGateDBDelay() + "millis");

			try {
				Thread.sleep(TaskMasterProperties.getGateDBDelay());
			}
			catch (Exception x) {
				L.debug("trouble in gateDB " + x);
			}
		}

		synchronized (ILOCK) {
			m_bBusyDB = true;
			c_strDBUser = _strLocation;
		}

		L.debug("gated DB at " + c_strDBUser);
	}

	/**
	 * ungateDB
	 *
	 * @author Dave
	 * @concurrency $none
	 */
	public final void ungateDB() {
		synchronized (ILOCK) {
			m_bBusyDB = false;
			c_strDBUser = "";
		}

		L.debug("ungated DB");
	}

	/**
	 * getBusyCount
	 *
	 * @return
	 * @author Dave
	 */
	public final int getBusyCount() {
		int iResult = 0;

		for (int i = 0; i < m_aii.length; i++) {
			try {
				if (m_aii[i] == null || m_aii[i].getBusy()) {
					++iResult;
				}
			}catch (Exception x) {
				L.debug("trouble getBusyCount " + x);
			}
		}

		return iResult;
	}

	/** getCanLaunch
	 * @return
	 * @author Dave
	 * @concurrency $none
	 * @concurrency $none
	 */
	public final synchronized boolean getCanLaunch() {
		return c_bCanLaunch;
	}

	/**
	 * getFirstFreeIdlerIndex - doesnt seem to be used
	 *
	 * @return
	 * @author Dave
	 * @concurrency $none
	 */
	public synchronized final int getFirstFreeIdlerIndex() {

		int iCandidateIndex = -1;
		int iCandidateUseCount = Integer.MAX_VALUE;

		// Find the least-used free Idler
		for (int i = 0; i < m_aii.length; i++) {
			try {
				if (!m_aii[i].getBusy()) {
					int iUseCount = m_aii[i].getUseCount();

					if (iUseCount < iCandidateUseCount) {
						iCandidateIndex = i;
						iCandidateUseCount = iUseCount;
					}
				}
			}
			catch (Exception x) {
				L.debug("trouble finding free idler " + x);
			}
		}

		try {
			if (iCandidateIndex >= 0) {
				m_aii[iCandidateIndex].setBusy(true);
			}
		}
		catch (Exception x) {
			L.debug("trouble locking Idler " + x);
		}

		L.debug("handing out idler index = " + iCandidateIndex);

		return iCandidateIndex;
	}

	/**
	 * getFreeCount
	 * @return
	 * @author Dave
	 */
	public final int getFreeCount() {
		return m_aii.length - getBusyCount();
	}

	/**
	 * getNextJobID
	 *
	 * @author Dave
	 * @concurrency $none
	 */
	public final int getNextJobID() {
		// should really use nextid table !!!
		synchronized(ALOCK){
			++c_iJob;
		}
		return c_iJob;
	}

	/**
	 * synchronize incrementing the task count
	 */
	private synchronized final void incrementTaskCnt() {
		++c_iTaskCount;
	}
	/** 
	 * launch
	 *
	 * @author Dave
	 * @param _iWhich
	 * @param _strJobName
	 * @param _iJobID
	 * @param _abri
	 * @throws java.rmi.RemoteException
	 */
	public final void launch(
			int _iWhich,
			String _strJobName,
			int _iJobID,
			OPICMABRItem _abri) throws RemoteException {
		L.debug("preparing to launch " + _abri);

		if(getCanLaunch()) { 
			//increment jobid when used in launch getNextJobID();

			incrementTaskCnt(); 

			// Build the Profile needed by the ABR
			Profile profTask = null;

			try {
				// Tmp fix to get around a blok to the m_db.
				// we can simply create a new database connection for the profile
				// then close it.. since launch is multi threaded
				// // Lets not gate the use of this
				Database db = new Database();
			//	DatePackage dp = null;

				db.setInstanceName(MiddlewareServerDynamicProperties.getInstanceName() + ":profile");
				db.setLogPrefix(this +"");
		//		dp = new DatePackage(db); get now from profile, one less call to the db
				profTask =	new Profile(db, c_strEnterprise, _abri.getOpenID(), true);
				T.est(profTask != null, "Profile is null in launchClass");
				//T.est(dp.m_strNow != null, "m_strNow is null in launchClass");
				T.est(profTask.getNow() != null, "m_strNow is null in launchClass");
				//profTask.setValOnEffOn(dp.m_strNow, dp.m_strNow);
				profTask.setValOnEffOn(profTask.getNow(), profTask.getNow());
//				profTask.setReadLanguage(0);
//				profTask.setWriteLanguage(0);
				NLSItem nlsRead = new NLSItem(_abri.getRef_NLSID(), "not used");
				NLSItem nlsWrite = new NLSItem(_abri.getRef_NLSID(), "not used");
				L.debug("LANG Setting Read/Write language to " + nlsRead);
				profTask.setReadLanguage(nlsRead);
				profTask.setWriteLanguage(nlsWrite);

				T.est(_abri != null, "_abri is null in launchClass");
				T.est(_strJobName != null, "_strJobName is null in launchClass");

				//rc = 
				m_aii[_iWhich].launchClass(
						_abri.getABRDescription(),
						_abri.getABRClassName(),
						_strJobName,
						_iJobID,
						profTask,
						_abri,
						_iWhich);
			}
			catch (Exception x) {
				L.debug("trouble in launchClass for "+_abri+" :" + x);
				x.printStackTrace();
				// we seem to be terminally hosed
				forceShutdown();
			}
			finally {
				// Ungate if not yet done
				//if (blocked) {
		 		//  ungateDB();
				//}
			}
		}
		else {
			L.debug("launches are currently disabled!");
		}

		freeIdler(_iWhich);
		// remove the abri from the inprocess list
		m_abrQueue.removeABRInProcess(_abri);
		L.debug("launch method is returning for "+m_aii[_iWhich].getName());
	}

	/* (non-Javadoc)
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.TaskMasterInterface#taskComplete()
	 */
	public final void taskComplete() throws RemoteException {
		// if no longer can launch return
		if(!getCanLaunch()){
			return;
		}
		try{
			// assign idlers to abrs
			assignRunners(false,c_bRefreshOnCompletion);
		}catch(Throwable exc) { // catch java.lang.OutOfMemoryError
			L.debug("taskComplete: Error trying to assign idler to another ABR "+exc);
			if(exc instanceof OutOfMemoryError){
				L.debug(":idler count: " +c_iIdlerCount +" Thread.activeCount: "+Thread.activeCount());
			}
			exc.printStackTrace();
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 * @author Dave
	 */
	public final void run() {
		m_prof = initAndLogin();
		c_strEnterprise = m_prof.getEnterprise();

		gateDB("TaskMaster getDates");
		m_dpDates = new DatePackage(m_db);
		ungateDB();
		m_prof.setValOnEffOn(m_dpDates.m_strNow, m_dpDates.m_strNow);

		m_abrQueue = new OPICMABRGroup(c_strEnterprise);

		L.debug("Enterprise = " + c_strEnterprise);

		c_lStartTime = System.currentTimeMillis();

		L.debug(c_strObjectName + " checking for In Process ABRs ...");
		checkForABRsToRestart();

		L.debug("Checking if scheduled ABRs can run in this instance");
		checkScheduledABRs();

		this.setRunPulse(true);
		this.setCanLaunch(true);
		L.debug(c_strObjectName + " is ready");

		boolean bFirstTime = true;
		
		while (c_bRunPulse) {
			try {
				// assign idlers to abrs
				assignRunners(bFirstTime,true);

				try {
					L.debug("run thread going to sleep for "+ Stopwatch.format(c_iPulseTime)+ " ...");
					Thread.sleep(c_iPulseTime);
				} catch (InterruptedException e) {
				}
				// show status of idlers
				this.status();
			} catch (Throwable x) { // catch java.lang.OutOfMemoryError
				L.debug(":pulse:" + x);
				if(x instanceof OutOfMemoryError){
					L.debug(":idler count: " +c_iIdlerCount +" Thread.activeCount: "+Thread.activeCount());
				}
				
				x.printStackTrace();
			} finally {
				//dont do this here - assignrunners will do it as needed
				//if done here - it may close the statement when taskcomplete has invoked assignrunners 
				//m_db.freeStatement();
				//m_db.isPending("finally y");
				//ungateDB();
			}

			bFirstTime = false;
		}

		L.debug("run thread is exiting.");
	}

	/**
	 * check for free idlers, queued ABRs and assign if possible
	 * @param bFirstTime
	 * @param bRefreshQ
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException
	 */
	private boolean assignRunners(boolean bFirstTime,boolean bRefreshQ) throws MiddlewareException, SQLException,
	MiddlewareShutdownInProgressException, RemoteException
	{
		synchronized (ALOCK) { // dont synch on tm instance - use variable to allow other synch methods to execute
			// Lets count the number of free Idlers
			int iFreeIdlers = getFreeCount();

			// Report conditions
			L.debug("ABRs in Queued state = "+ m_abrQueue.getOPICMABRItemCount());
			L.debug("free Idlers = " + iFreeIdlers);

			// NORMAL mode allows the Queue to refresh, QEXIT mode only refreshes first time
			if ( c_strRunMode.equalsIgnoreCase("NORMAL")
					|| (c_strRunMode.equalsIgnoreCase("QEXIT") && bFirstTime)) {
				//if ( m_abrQueue.getOPICMABRItemCount() < iFreeIdlers) { chgd because other abrs may have been queued that can use free idlers
				// if we have free idlers or no work in the queue lets use the run time of the fetch queue as
		        // the sleep mechanism..
				if (bRefreshQ &&  // may be false if called when abr completes - tm property -refreshQ_onCompletion
						((m_abrQueue.getOPICMABRItemCount() < 1) || (iFreeIdlers > 0))) {
					// Refresh the runQ
					refreshABRWorkQueue();
				}
			}

			if (!getCanLaunch()) {
				L.debug("** We are in NO LAUNCH mode");
				// this will cause it to sleep a bit and then check the runpulse
				return true;
			}

			// If no work has been found.. 
			if (m_abrQueue.getOPICMABRItemCount() < 1) {
				L.debug("nothing to do..");
				// Sleep a bit
				return true;
			}
			// If there are no free Idlers then we can not do work
			if (iFreeIdlers == 0) {
				L.debug("** All Idlers are busy, can't work this cycle..");
				// Sleep a bit
				return true;
			}

			// If this is QEXIT mode and nothing to run and all idlers are free, let's exit
			checkQexitMode();

			L.debug("Queue contains "+ m_abrQueue.getOPICMABRItemCount()+ " ABRs");
			// If we have any work, we can attempt to launch it
			if (m_abrQueue.getOPICMABRItemCount() > 0) {
				int iABR = 0;

				// Show the ABR items in the Queue
				for (int i = 0;	i < m_abrQueue.getOPICMABRItemCount();	i++) {
					L.debug(D.EBUG_SPEW,"ABRI " + m_abrQueue.getOPICMABRItem(i));
				}

				// While there are free Idlers and we have something to run
				while ( (iFreeIdlers > 0) && (iABR < m_abrQueue.getOPICMABRItemCount())) {

					// Point to the specific ABR item in the Queue
					OPICMABRItem abri =	m_abrQueue.getOPICMABRItem(iABR);

					L.debug(D.EBUG_SPEW, "Inspecting ABRI " + abri);

					// If the ABR is queued and enabled, check all available Idlers and launch ABR in it -- if class is match
					if (abri.isQueued() && abri.isEnabled()) {
						// Look for an Idler which can run this ABR
						for (int iIdlerIndex = 0; iIdlerIndex < c_iIdlerCount;	iIdlerIndex++) {
							// Available Idler?
							if (!m_aii[iIdlerIndex].getBusy()) {
								// Class match between idler and abr?
								L.debug(D.EBUG_SPEW,"Idler["+iIdlerIndex+"] classes "+ idlerClassesSets[iIdlerIndex]+
										" ABR class "+ abri.getIdlerClass());
								if (idlerClassesSets[iIdlerIndex].contains(abri.getIdlerClass())) {
									L.debug(D.EBUG_SPEW,"** Changing ABR internal status to "
											+ AbstractTask.ABR_STATUS_INPROCESS+ " "+ abri);
									abri.setABRState(AbstractTask.ABR_STATUS_INPROCESS.m_strAttributeValue);

									L.debug(D.EBUG_INFO,"** addABRInProcess and Remove ABR item from queue:"+ abri);
									m_abrQueue.addABRInProcess(abri);
									m_abrQueue.removeOPICMABRItem(iABR);

									m_aii[iIdlerIndex].setBusy(true);

									int jobid = getNextJobID();
									m_atmr[iIdlerIndex] = new TaskMasterRunner(this,iIdlerIndex,
											"job" + jobid,	jobid,	abri);
									m_atmr[iIdlerIndex].start();

									--iABR;

									// Don't look at any more idlers for THIS abr cause it launched
									break;
								} else {
									L.debug(D.EBUG_SPEW,"class mismatch - can't run idler["+iIdlerIndex+"]");
								}
							} else {
								L.debug(D.EBUG_SPEW,"idler["+iIdlerIndex+"] is busy - can't run");
							}
						}
						L.debug(D.EBUG_SPEW, "checked all idlers");
					} else {
						L.debug("** Removing ABR item from queue. It is not queued and enabled:"	+ abri);
						m_abrQueue.removeOPICMABRItem(iABR);
						--iABR;
					}

					// Lets refresh the number of free idlers
					iFreeIdlers = getFreeCount();

					// Look at next ABR and try to run it
					++iABR;
				}
			} // end abrs exist
			else {
				L.debug("** No work found");
			}
			
			return true;//getFreeCount()==0; must let it sleep 
		}
	}

	/**
	 * were any abrs in process when this shutdown?  if they are readonly they can be restarted
	 */
	private void checkForABRsToRestart(){

		// Get list of In Process ABRs
		try { 
			gateDB("TaskMaster refresh Queue #1");
			m_db.refreshOPICMABRGroup(
					m_prof,
					m_abrQueue,
					AbstractTask.ABR_STATUS_INPROCESS.m_strAttributeValue,
					strInterleavedMode);
		}catch (Exception z) {
			L.debug(this+" trouble getting ABR Queue of in process ABRs for restart "+ z);
		}finally{
			ungateDB(); 
		}

		if (m_abrQueue.getOPICMABRItemCount() > 0) {
			L.debug(c_strObjectName + " examining In Process ABRs for restart");
			Vector abrs2FailVct = new Vector();

			// Process the items in the Queue
			for (int i = 0; i < m_abrQueue.getOPICMABRItemCount(); i++) {
				OPICMABRItem abri = m_abrQueue.getOPICMABRItem(i);

				//check if taskmaster can run this class, if not don't modify it
				boolean bCanRun = allIdlerClasses.contains(abri.getIdlerClass());
				if (!bCanRun) {
					L.debug(D.EBUG_SPEW, c_strObjectName+" This instance does not run ABR's of class "
							+ abri.getIdlerClass()+ ".  Removing "+ abri);
					m_abrQueue.removeOPICMABRItem(i);
					--i;
					continue;
				}
				
				if (abri.getReadOnly()) {
					// read-only should be re-queued
					L.debug(D.EBUG_WARN, c_strObjectName	+ " changing read-only ABR status to queued for "+ abri);
					abri.setABRState(AbstractTask.ABR_STATUS_QUEUED.m_strAttributeValue);
					abri.setEnabled(true);
				} else {
					// not read-only should be failed
					L.debug(D.EBUG_WARN, c_strObjectName	+ " changing ABR status to failed for "	+ abri);

					abri.setABRState(AbstractTask.ABR_STATUS_FAILED.m_strAttributeValue);
					//abri.setEnabled(true);
					abrs2FailVct.add(abri);
					m_abrQueue.removeOPICMABRItem(i);
					--i;
				}
			}

			if(abrs2FailVct.size()>0){
				failAbrsInPDH(abrs2FailVct);
				abrs2FailVct.clear();
			}
			L.debug(c_strObjectName + " restart in process ABRs complete");
		}
	}

	/**
	 * fail these abrs in the PDH and notify the user
	 * @param abrs2FailVct
	 */
	private void failAbrsInPDH(Vector abrs2FailVct){
		for (int i=0;i<abrs2FailVct.size(); i++){
			OPICMABRItem abri = (OPICMABRItem)abrs2FailVct.elementAt(i);
			// create an abstracttask so it can update the pdh
			String strClassName = abri.getABRClassName();
			try {
				L.debug("failAbrsInPDH creating instance of ABR code class = " + strClassName);
				AbstractTask m_at = (AbstractTask) Class.forName(strClassName).newInstance();
				m_at.setDatabase(m_db);
				Profile profTask =	new Profile(m_db, c_strEnterprise, abri.getOpenID(), true);
				DatePackage dp = new DatePackage(m_db);
				profTask.setValOnEffOn(dp.m_strNow, dp.m_strNow);
				profTask.setReadLanguage(new NLSItem(abri.getRef_NLSID(), "not used"));
				profTask.setWriteLanguage(new NLSItem(abri.getRef_NLSID(), "not used"));
				m_at.setProfile(profTask);
				m_at.setABRItem(abri);
				DatePackage dpDates = new DatePackage(m_db);
				m_at.initPrintWriter(dpDates.m_strNow);
				m_at.changeStatusABR(AbstractTask.ABR_STATUS_FAILED);

				AbortedTaskWriter.buildAbortedReport(m_at);
				m_at.finish();
				
				m_at.dereference();
			} catch (Exception x) {
				L.debug("failAbrsInPDH trouble loading class '" + strClassName + "' " + x);
			}
		}
	}

	/**
	 * check if scheduled abrs need to run
	 */
	private void checkScheduledABRs(){
		if (TaskMasterProperties.getRunsRecurring()) {
			L.debug("  scheduled ABRs CAN run here");
			L.debug("  re-queuing SCHEDABRs if defined.");
			try {
				ReturnStatus returnStatus = new ReturnStatus( -1);
				gateDB("TaskMaster requeue SCHEDABRs");
				m_db.callGBL9975(returnStatus);
			}catch (Exception x) {
				L.debug("TaskMaster unable to requeue SCHEDABRs " + x);
			}finally {
				m_db.freeStatement();
				m_db.isPending("finally at requeue SCHEDABRs");
				ungateDB();
			}
		}
		else {
			L.debug("  scheduled ABRs do NOT run here");
		}
	}

	/**
	 * if this is QEXIT mode and no work is found, create semaphore file to shutdown
	 */
	private void checkQexitMode(){
		int iFreeIdlers = getFreeCount();
		if (c_strRunMode.equalsIgnoreCase("QEXIT")
				&& (m_abrQueue.getOPICMABRItemCount() == 0)
				&& (iFreeIdlers == c_iIdlerCount)) {

			forceShutdown();
			L.debug("QEXIT mode has nothing left to do ...");
		}
	}

	/**
	 * force taskmaster to shutdown
	 */
	private void forceShutdown(){
		setCanLaunch(false);
		FileWriter fw1 = null;
		try{
			//mark shutdown - nothing to do
			File f1 = new File(TaskMaster.SHUTDOWN_SEMAPHORE_FILE);
			fw1 = new FileWriter(f1);
			fw1.write(' ');
		}catch(Exception io){}
		finally{
			if(fw1!=null){
				try{
					fw1.close();
				}catch(Exception io){}
				fw1 = null;
			}
		}
	}
	/**
	 * check for any abrs to run, remove any that can be run by this instance
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 */
	private void refreshABRWorkQueue() throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException{
		L.debug("** Refreshing ABR work queue");
		try{
			gateDB("TaskMaster refreshOPICMABRGroup");
			m_db.refreshOPICMABRGroup(
					m_prof,
					m_abrQueue,
					AbstractTask.ABR_STATUS_QUEUED.m_strAttributeValue,
					strInterleavedMode);
		}finally{
			ungateDB();
		}

		// let's do a pass thru the Q and remove items which can NEVER run
		L.debug("ABRCheck Starting - starting count = "	+ m_abrQueue.getOPICMABRItemCount());

		for (int i = 0; i < m_abrQueue.getOPICMABRItemCount(); i++) {
			OPICMABRItem abri = m_abrQueue.getOPICMABRItem(i);
			boolean bCanRun = allIdlerClasses.contains(abri.getIdlerClass());
			if (!bCanRun) {
				L.debug(D.EBUG_SPEW, "ABRCheck - This instance does not run ABR's of class "
						+ abri.getIdlerClass()+ ".  Removing "+ abri);
				m_abrQueue.removeOPICMABRItem(i);
				--i;
			}
		}
		L.debug("ABRCheck Completed - remaining count = "+ m_abrQueue.getOPICMABRItemCount());

	}

	/**
	 * initialize and login
	 * @return profile
	 */
	private Profile initAndLogin(){
		m_ew = new ExitWatcher(this);
		m_conMgr = new ConnectionManager(c_strObjectName);

		connectIdlers();
		L.debug("Idler class assignments ...");

		for (int iIdlerIndex = 0; iIdlerIndex < c_iIdlerCount; iIdlerIndex++) {
			idlerClassesSets[iIdlerIndex] = new HashSet();
			String idlerclasses = TaskMasterProperties.getIdlerClasses(iIdlerIndex);
			StringCharacterIterator sci = new StringCharacterIterator(idlerclasses);
			char ch = sci.first();
			while(ch != CharacterIterator.DONE)	{
				if(Character.isLetter(ch)){
					idlerClassesSets[iIdlerIndex].add(new String(new char[]{ch}));
				}else{
					L.debug("Invalid idler class "+ch+" for Idler"+ (char) ( ( (int) 'A' + (iIdlerIndex))));
				}
				ch = sci.next();
			}

			L.debug("  Idler"
					+ (char) ( ( (int) 'A' + (iIdlerIndex)))
					+ " assigned to classes "
					+ idlerClassesSets[iIdlerIndex]);

			allIdlerClasses.addAll(idlerClassesSets[iIdlerIndex]);
		}


		m_ro = new RegisterObject(c_strObjectName, this);
		m_db = new Database();

		//    m_db.setInstanceName(getName());
		m_db.setInstanceName(
				MiddlewareServerDynamicProperties.getInstanceName());
		m_db.setLogPrefix(this +"");

		m_db.freeStatement();
		m_db.isPending("after set instance name");

		try {
			gateDB("TaskMaster main login");

			ProfileSet profs = m_db.login(c_strUser, PASSWORD, CURRENT_VERSION_LITERAL);

			if ( (profs == null) || profs.isEmpty()) {
				throw new Exception("Invalid profileset -- CAN'T CONTINUE");
			}
			return profs.elementAt(0);
		}catch (Exception x) {
			L.debug("TaskMaster unable to login " + x);
			// If trouble, then fail hard
			System.exit( -1);
		}finally {
			m_db.freeStatement();
			m_db.isPending("finally at login");
			ungateDB();
		}

		return null;
	}
	/**
	 * setCanLaunch
	 *
	 * @param _bCanLaunch
	 * @author Dave
	 * @concurrency $none
	 */
	public final synchronized void setCanLaunch(boolean _bCanLaunch) {
		c_bCanLaunch = _bCanLaunch;

		L.debug("setting launches = " + c_bCanLaunch);
	}

	/**
	 * setPulseRule
	 *
	 * @param _bRunPulse
	 * @author Dave
	 */
	public final void setRunPulse(boolean _bRunPulse) {
		c_bRunPulse = _bRunPulse;

		L.debug("setting run pulse = " + c_bRunPulse);
	}

	/**
	 * showArray
	 *
	 * @param _astrArray
	 * @return
	 * @author Dave
	 */
	public static final String showArray(String[] _astrArray) {
		StringBuffer strbResult = new StringBuffer();

		for (int i = 0; i < _astrArray.length; i++) {
			strbResult.append(_astrArray[i] + " ");
		}

		return strbResult.toString();
	}

	/**
	 * shut
	 *
	 * @author Dave
	 */
	public final void shut() {

		m_ew.shut();
		this.setCanLaunch(false);
		L.debug("has been requested to exit - check for busy Idlers");
		this.status();

		while (anyRunning()) {
			L.debug(
					"[can't shut because "
					+ getBusyCount()
					+ " Idler(s) are active] -- waiting for idlers to finish ...");

			try {
				Thread.sleep(5000);
			}
			catch (Exception x) {
				L.debug("sleep trouble in shut " + x);
			}
		}

		// All idlers have finished
		m_ro.shut();
		setRunPulse(false);
		L.debug("has been requested to exit - ALL Idlers now free");

		for (int i = 0; i < m_aii.length; i++) {
			L.debug("shutting " + m_aconn[i]);
			m_conMgr.closeAll();

			try {
				m_conMgr.removeConnection(m_aconn[i]);
				m_aii[i].shut();

				m_aii[i] = null;
				m_aconn[i] = null;
			}
			catch (Exception x) {
				L.debug(
						"trouble shutting an idler "
						+ m_aconn[i]+ " -- normal "+ x);
			}
		}

		// Everything as expected, we can initiate our shutdown now
		new Shutter(
				this,
				OBJECT_NAME,
				TaskMasterProperties.getTaskMasterShutDownDelay());

		// Since QEXIT mode stops without using a unix script, we must remove the running semaphore file
		if (c_strRunMode.equalsIgnoreCase("QEXIT")) {
			try {
				File f = new File(RUNNING_SEMAPHORE_FILE);

				if (f.exists()) {
					f.delete();
				}
			}
			catch (Exception x) {
			}
		}
	}

	/**
	 * get idler status
	 *
	 * @author Dave
	 */
	public final void status() {

		long lNow = System.currentTimeMillis();

		L.debug(
				"PULSE "
				+ "uptime: "
				+ Stopwatch.format(lNow - c_lStartTime)
				+ " launch attempts: "
				+ c_iTaskCount);

		for (int i = 0; i < m_aii.length; i++) {
			try {
				if (m_aii[i] != null) {
					L.debug("PULSE " + m_aii[i].getStatus());
				}
				else {
					L.debug("PULSE unknown idler");
				}
			} catch (Exception x) {
				L.debug("trouble getting status on idler " + x);
			}
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 * @author Dave
	 */
	public final String toString() {
		return "+" + getName() + "+";
	}

	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static String getVersion() {
		return "$Id: TaskMaster.java,v 1.238 2014/02/28 21:21:07 wendy Exp $";
	}
	/**
	 * mainline
	 *
	 * @param args
	 * @throws java.lang.Exception
	 * @author Dave
	 */
	public static void main(String[] args) throws Exception {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("register")) {
				Properties propJVM = new Properties();
				ActivationGroupDesc.CommandEnvironment commandEnv = null;
				String strObjectName = null;
				ActivationGroupID agi_0 = null;
				ActivationDesc desc_0 = null;
				TaskMasterInterface stub_0 = null;

				L.debug("Registering TaskMaster <" + Database.c_strBuildTimeStamp + ">" +
				"$Revision : 1.198 $ ");
				commandEnv = new ActivationGroupDesc.CommandEnvironment(
						TaskMasterProperties.getJavaExecutableName(),
						TaskMasterProperties.getJVMParameters());
				L.debug("JVMs will use interpreter = "
						+ TaskMasterProperties.getJavaExecutableName());
				L.debug(
						"JVMs will use interpreter parameters = "
						+ TaskMaster.showArray(
								TaskMasterProperties.getJVMParameters()));

				strObjectName = TaskMaster.OBJECT_NAME;

				agi_0 = ActivationGroup.getSystem().registerGroup(
						new ActivationGroupDesc(propJVM, commandEnv));
				desc_0 =
					new ActivationDesc(
							agi_0,
							"COM.ibm.opicmpdh.middleware.taskmaster.TaskMaster",
							TaskMasterProperties.getCodebaseRMI(),
							new MarshalledObject(strObjectName),
							false);
				stub_0 =
					(TaskMasterInterface) Activatable.register(desc_0);

				Naming.rebind(Connection.createURL(strObjectName), stub_0);
				L.debug(
						"Registered "
						+ strObjectName
						+ " as "
						+ Connection.createURL(strObjectName));

				// Idlers
				for (int i = 0; i < TaskMaster.c_iIdlerCount; i++) {

					ActivationGroupID agi_1 = null;
					ActivationDesc desc_1 = null;
					IdlerInterface stub_1 = null;

					strObjectName =
						Idler.BASE_OBJECT_NAME + (char) ( ( (int) 'A' + i));

					agi_1 = ActivationGroup.getSystem().registerGroup(
							new ActivationGroupDesc(propJVM, commandEnv));
					desc_1 =
						new ActivationDesc(
								agi_1,
								"COM.ibm.opicmpdh.middleware.taskmaster.Idler",
								TaskMasterProperties.getCodebaseRMI(),
								new MarshalledObject(strObjectName),
								false);
					stub_1 =
						(IdlerInterface) Activatable.register(desc_1);

					Naming.rebind(Connection.createURL(strObjectName), stub_1);
					L.debug(
							"Registered "
							+ strObjectName
							+ " as "
							+ Connection.createURL(strObjectName));
				}
			}
			else {

				L.debug("Initializing TaskMaster <" + Database.c_strBuildTimeStamp +
				"> $Revision: 1.238 $");

				try {
					L.debug(
							"Attempting to start TaskMaster by 'pinging' "
							+ Connection.createURL(TaskMaster.OBJECT_NAME));

					TaskMasterInterface tmi =
						(TaskMasterInterface) Naming.lookup(
								Connection.createURL(TaskMaster.OBJECT_NAME));
					tmi.ping();
				}
				catch (Exception x) {
					L.debug("ping failed " + x);
				}
			}
		}
	}
}
