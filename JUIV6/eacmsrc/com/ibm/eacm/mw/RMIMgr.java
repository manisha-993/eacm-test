//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.mw;

import COM.ibm.opicmpdh.transactions.Cipher; //used for encrypted uid and pw, requires jre1.4+
import COM.ibm.opicmpdh.middleware.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.ibm.eacm.*;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.PrefMgr;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.*;
import java.io.PrintStream;
/**
 * This class manages the RemoteDatabaseInterface connection and login to mw
 * @author Wendy Stimpson
 */
//$Log: RMIMgr.java,v $
//Revision 1.9  2014/01/24 12:47:12  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.8  2013/07/25 19:07:07  wendy
//change logging
//
//Revision 1.7  2013/07/18 18:31:06  wendy
//fix compiler warnings
//
//Revision 1.6  2013/05/22 16:25:00  wendy
//check for mw connection during logout
//
//Revision 1.5  2013/03/11 14:35:06  wendy
//check for valid ro in testGetServerTime
//
//Revision 1.4  2013/02/15 11:53:52  wendy
//Update display time after computer standby
//
//Revision 1.3  2013/02/05 18:23:22  wendy
//throw/handle exception if ro is null
//
//Revision 1.2  2012/11/12 22:47:48  wendy
//AutoUpdate changes
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code

public class RMIMgr implements EACMGlobals {
	public static final String WORKER_PROPERTY="DBSwingWorker";
	protected static final Logger logger;
	static{
		String loggerName = com.ibm.eacm.mw.RMIMgr.class.getPackage().getName();
		logger = Logger.getLogger(loggerName);
		logger.setLevel(PrefMgr.getLoggerLevel(loggerName, Level.INFO));

		setMwDebugLevel();
	}

	/**
	 * set middleware trace level corresponding to logger setting
	 */
	public static void setMwDebugLevel(){
		Level curLvl = logger.getLevel();
		// set corresponding middleware debug levels
		/*
		from D.java, it defaults to EBUG_DETAIL
		public static final int EBUG_ERR = 0;
		public static final int EBUG_WARN = 1;
		public static final int EBUG_INFO = 2;
		public static final int EBUG_DETAIL = 3;
		public static final int EBUG_SPEW = 4;
		 */
		int debuglvl = D.EBUG_DETAIL;
		if(curLvl.intValue()<Level.FINE.intValue()){
			debuglvl = D.EBUG_SPEW;
		}else if(curLvl.intValue()==Level.FINE.intValue()){
			debuglvl = D.EBUG_DETAIL;
		}else if(curLvl.intValue()==Level.INFO.intValue()){
			debuglvl = D.EBUG_INFO;
		}else if(curLvl.intValue()==Level.WARNING.intValue()){
			debuglvl = D.EBUG_WARN;
		}else if(curLvl.intValue()>=Level.SEVERE.intValue()){
			debuglvl = D.EBUG_ERR;
		}

		D.ebugLevel(debuglvl); // mw debug info was getting lost TIR6YZSXM, allow override
	}
	private PropertyChangeSupport changeSupport = null; // notify break button

	private RemoteDatabaseInterface ro = null;
	private Timer tTimer = new Timer();

	private String user = null;

	private static final int MAX_WORKERS = EACMProperties.rmiMaxWorkers();

	private Vector<DBSwingWorker<?, ?>> waitingToRunVct = new Vector<DBSwingWorker<?, ?>>(); // DBSwingWorker waiting to run
	private Set<DBSwingWorker<?,?>> runningSet = new HashSet<DBSwingWorker<?, ?>>();		// ids of currently running DBSwingWorker
	private static int DBSWcnt = 0;

	private RMIMgr() {}
	private static RMIMgr rmiMgr = new RMIMgr();
	public static RMIMgr getRmiMgr() { return rmiMgr;}

	private boolean hasHeavyWeight(Collection<?> col){
		Iterator<?> itr = col.iterator();
		while (itr.hasNext()) {
			DBSwingWorker<?, ?> dbsw  = (DBSwingWorker<?, ?>)itr.next();
			if(dbsw instanceof HeavyWorker){
				return true;
			}
		}
		return false;
	}
	/**
	 * @param dbsw DBSwingWorker requesting execution
	 */
	public synchronized void execute(DBSwingWorker<?, ?> dbsw){
		dbsw.setId(DBSWcnt++);
		logger.log(Level.FINER, dbsw+" runningSet "+runningSet+" waitingToRunVct "+waitingToRunVct);
		boolean hasHeavy = false;
		if (runningSet.size()<MAX_WORKERS){
			hasHeavy = hasHeavyWeight(runningSet);
			if(hasHeavy){ // only one non-interruptable task at a time, these tasks modify data.. all others must wait
				waitingToRunVct.add(dbsw);
			}else{
				hasHeavy = dbsw instanceof HeavyWorker;
				if(hasHeavy){ // new one is heavy, only run if no others are running
					if (runningSet.size()==0){
						long starttime = System.currentTimeMillis();
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,dbsw.toString());
						dbsw.setStarttime(starttime);
						dbsw.execute();
						runningSet.add(dbsw);
					}else{
						waitingToRunVct.add(dbsw);
					}
				}else{
					long starttime = System.currentTimeMillis();
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,dbsw.toString());
					dbsw.setStarttime(starttime);
					dbsw.execute();
					runningSet.add(dbsw);
				}
			}
		}else{
			waitingToRunVct.add(dbsw);
			hasHeavy = hasHeavyWeight(waitingToRunVct); // dont enable break action if there is one non-interruptable
		}
		logger.log(Level.FINER, "hasHeavy "+hasHeavy+" runningSet "+runningSet+" waitingToRunVct "+waitingToRunVct);
		firePropertyChange(WORKER_PROPERTY,false, !hasHeavy);//true);//  think about when to enable/disable break action
	}
	/**
	 * @param dbsw DBSwingWorker that is now done
	 */
	public synchronized void complete(DBSwingWorker<?, ?> dbsw){
		if(dbsw==null){
			return; // already called this
		}
		runningSet.remove(dbsw);
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,dbsw.toString()+" ended "+Utils.getDuration(dbsw.getStarttime()));

		if(waitingToRunVct.size()>0){
			if (!hasHeavyWeight(runningSet)){ // only one non-interruptable task at a time, these tasks modify data.. all others must wait
				DBSwingWorker<?, ?> waitdbsw = waitingToRunVct.remove(0);
				long starttime = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,waitdbsw.toString());
				waitdbsw.setStarttime(starttime);
				waitdbsw.execute();
				runningSet.add(waitdbsw);
			}
		}else{
			if (runningSet.size()==0){
				firePropertyChange(WORKER_PROPERTY,true, false);
			}
		}
		logger.log(Level.FINER, "complete runningSet "+runningSet+" waitingToRunVct "+waitingToRunVct);
	}

	/**
	 * cancel this worker if it is running or remove it from wait queue
	 *
	 * @param worker
	 * @param mayInterruptIfRunning
	 * @return
	 */
	public synchronized boolean cancelWorker(DBSwingWorker<?, ?> worker, boolean mayInterruptIfRunning) {
		boolean b = true;
		// remove this worker from runningSet or waitingToRunVct
		for (int i=0; i<waitingToRunVct.size(); i++){
			DBSwingWorker<?, ?> waitdbsw = waitingToRunVct.elementAt(i);
			if(waitdbsw == worker){
				waitingToRunVct.remove(waitdbsw);
				return b;
			}
		}
		Iterator<DBSwingWorker<?, ?>> itr = runningSet.iterator();
		while (itr.hasNext()) {
			DBSwingWorker<?, ?> dbsw  = (DBSwingWorker<?, ?>)itr.next();
			if(dbsw == worker){
				b = dbsw.cancel(true);
				runningSet.remove(dbsw);
				break;
			}
		}

		if (runningSet.size()==0){
			firePropertyChange(WORKER_PROPERTY,true, false);
		}
		return b;
	}
	/**
	 * kill all workers unless they are not interruptable
	 */
	public synchronized void interrupt(){
		boolean workersExisted = false;
		logger.log(Level.FINER, "runningSet "+runningSet+" waitingToRunVct "+waitingToRunVct);
		// notify all waiting to run that they will not run
		for (int i=0; i<waitingToRunVct.size(); i++){
			workersExisted = true;
			DBSwingWorker<?, ?> waitdbsw = waitingToRunVct.elementAt(i);
			waitdbsw.notExecuted();
		}
		waitingToRunVct.clear();
		Iterator<DBSwingWorker<?, ?>> itr = runningSet.iterator();
		while (itr.hasNext()) {
			DBSwingWorker<?, ?> dbsw  = (DBSwingWorker<?, ?>)itr.next();
			if(!(dbsw instanceof HeavyWorker)){
				dbsw.cancel(true);
			}
			workersExisted = true;
		}
		runningSet.clear();

		if (workersExisted){
			firePropertyChange(WORKER_PROPERTY,true, false);
		}
	}
	// support break button enablement
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(listener);
	}
	public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners();
	}
	private void firePropertyChange(String propertyName,
			boolean oldValue, boolean newValue) {
		if (changeSupport == null || oldValue == newValue) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * @return
	 */
	public Timer getTimer() { return tTimer;}
	/**
	 * dereference- called when user exits the application
	 */
	public void dereference() {
		logOff(true);
	}

	/**
	 * user has logged off, release RMI and stop timer
	 */
	protected void logOff() {
		logOff(false);
	}
	/**
	 * @param isExiting
	 */
	protected void logOff(final boolean isExiting) {
		logger.log(Level.INFO, "logoff(" + user + ")");
		if (isExiting){ // do it now, if autoupdate is done and this is threaded, it cant find the class
			addLogoff2MWLog();

			// release all memory
			changeSupport = null; // notify break button
			tTimer = null;
			waitingToRunVct = null;
			runningSet = null;
			rmiMgr = null;
			ro = null;
		}else{
			// do this in a bg thread
			Runnable logoff = new Runnable() {
				public void run() {
					addLogoff2MWLog();
				}
			};

			Thread t = new Thread(logoff);
			t.start();
		}
	}

	/**
	 * add user logoff info to mw log
	 */
	private void addLogoff2MWLog(){
		long t1=System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting logoff");
		boolean canReachMW = true;
		// if cant verify connection to mw then cant put logout info in mw log
		try {
			String time = RMIMgr.getRmiMgr().testGetServerTime();
			if(time==null){
				canReachMW = false;
			}
		} catch (Exception e) {
			canReachMW = false;
		} 
		if(canReachMW){
			try {
				// re-using login method to avoid another method in the rmi interface
				// all it does is put an entry in the mw log
				//ro.login(user, COM.ibm.opicmpdh.middleware.Database.LOGOUT,
				//		COM.ibm.opicmpdh.middleware.Database.LOGOUT, "UI");
				byte[][] encrypted = Cipher.encryptUidPw(user,COM.ibm.opicmpdh.middleware.Database.LOGOUT);
				ro.secureLogin(encrypted,COM.ibm.opicmpdh.middleware.Database.LOGOUT,"UI");
			} catch (Exception ve) {}
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"logoff ended "+Utils.getDuration(t1));
		}else{
			Logger.getLogger(TIMING_LOGGER).log(Level.SEVERE,"Unable to contact server, cannot indicate logout");	
		}

		clearRMI(true);
		user=null;	
	}
	/**
	 * @param killWorkers
	 */
	private void clearRMI(boolean killWorkers){
		if (ro != null) {
			//mw.disconnect calls this java.rmi.server.UnicastRemoteObject.unexportObject((Remote)ro,true);
			//Middleware.disconnect(ro);// always gets NoSuchObjectException.. was Remote ever exported?
			ro = null;
		}
		tTimer.logOff();

		if(killWorkers){ // if reconnecting when a worker is running, dont kill them
			interrupt(); // kill all workers
		}
	}

	/**
	 * get rmi connection - called during login process
	 * @param mw
	 * @return
	 */
	protected boolean connect2RMI(MWObject mw)
	{
		clearRMI(false);

		for (int i = 0; i < EACMProperties.rmiMaxTries(); ++i) {
			try {
				connect(mw);
				if (ro != null) {
					String servertime = getServerTime();
					// if you cant get the servertime, something is very wrong
					if(servertime!=null){
						tTimer.setNow(servertime);
						return true;
					}else{
						return false;
					}
				}

				Thread.sleep(1000);
			}catch (InterruptedException ex) {
				//login was cancelled
				return false;
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * get the RemoteDatabase object for the specified ip, port and mw name
	 * @throws java.rmi.RemoteException
	 *
	 */
	private void connect(MWObject mwo) throws RemoteException {
		PrintStream outps = System.out;
		System.setOut(EACM.stdout);
		//Middleware.connect spews info, avoid it in the app log, it will go to output.log
		ro = Middleware.connect(mwo.getIP(), mwo.getPort(), mwo.getName());
		System.setOut(outps);
		LoginMgr.setCurrentMWO(mwo);
	}


	/*********************
	 *	Timing Methods  **
	 *
	 * @return String
	 *********************/
	public String getServerTime() {
		ReturnDataResultSetGroup ld = null;
		try {
			if(ro==null){
				connect(LoginMgr.getCurrentMWO());
				if (ro != null) {
					ld = ro.remoteGBL2028();
				}else{
					Logger.getLogger(TIMING_LOGGER).log(Level.SEVERE,"getServerTime failed, unable to connect");
				}
			}else{
				ld = ro.remoteGBL2028();
			}
		} catch (Exception _ce) {
			Logger.getLogger(TIMING_LOGGER).log(Level.SEVERE,"getServerTime failed",_ce);

			try {
				// prevent recursion, try to connect again
				connect(LoginMgr.getCurrentMWO());
				if (ro != null) {
					ld = ro.remoteGBL2028();
				}
			} catch (Exception _e) {
				Logger.getLogger(TIMING_LOGGER).log(Level.SEVERE,"getServerTime second attempt failed",_e);
			}
		}

		if (ld!=null){
			String datNow = ld.getColumn(0, 0, 0);
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"complete: "+datNow);
			return datNow;
		}

		return null;
	}

	/**
	 * used to test connection to middleware
	 * @return
	 * @throws RemoteException
	 * @throws MiddlewareException
	 */
	public String testGetServerTime() throws RemoteException, MiddlewareException{
		if(ro !=null){
			ReturnDataResultSetGroup ld = ro.remoteGBL2028();
			if (ld!=null){
				return ld.getColumn(0, 0, 0);
			}
		}

		return null;	
	}
	/**
	 * login
	 * this is called on the background thread of the LoginMgr.LoginWorker
	 * @param userid
	 * @param pw
	 * @return
	 * @throws VersionException
	 * @throws LoginException
	 */
	protected ProfileSet login(String userid, char[] pw) throws VersionException, LoginException
	{
		ProfileSet ld = null;
		user = userid;
		logger.log(Level.INFO, "login(" + userid + ",**password**)");
		try {
			byte[][] encrypted = Cipher.encryptUidPw(userid,pw);
			// secure login
			ld = ro.secureLogin(encrypted,EACM.EACM_TOKEN,"UI");
			//ld = ro.login(userid, new String(pw), ELogin.EACM_TOKEN, "UI"); // String(pw) is a security risk
		} catch (VersionException ve) {
			logger.log(Level.SEVERE, "version error "+ve);
			throw ve;
		} catch (LoginException le) {
			logger.log(Level.SEVERE, "login error"+le);
			throw le;
		} catch (Exception x) {
			com.ibm.eacm.ui.UI.showException(null,x,"mw.connect.err-title");
		}finally{
			//Zero out the possible password, for security.
			Arrays.fill(pw, '0');
		}
		return ld;
	}

	/**
	 * reconnectMain using current mw object
	 * called when there is an exception using the RemoteDatabaseInterface
	 * @return
	 */
	public boolean reconnectMain() {
		return connect2RMI(LoginMgr.getCurrentMWO());
	}
	/**
	 * decide to try to reconnect to rmi based on exception type
	 * @param exc
	 * @return
	 */
	public static boolean shouldTryReconnect(Exception exc){
		//java.rmi.ConnectException is a RemoteException
		return  (exc instanceof RemoteException || exc instanceof SQLException);
	}

	/**
	 * decide to allow user to terminate or not
	 * @param exc
	 * @return
	 */
	public static boolean shouldPromptUser(Exception exc){
		return  (exc instanceof MiddlewareException);
	}
	/**
	 * getRemoteDatabaseInterface
	 * @return RemoteDatabaseInterface
	 * @throws RemoteException 
	 */
	public RemoteDatabaseInterface getRemoteDatabaseInterface() throws RemoteException {
		if(ro==null){
			throw new RemoteException("Unable to establish database connection");
		}
		return ro;
	}

	/**
	 * getRemoteVersion
	 * @return String
	 */
	public String getRemoteVersion() {
		String out = "0000-00-00-00.00.00.000000";
		if (ro != null) {
			try {
				out = ro.getVersion();
			} catch (RemoteException _ex) { // cant happen, just in method signature
				_ex.printStackTrace();
			}
		}
		return out;
	}

}
