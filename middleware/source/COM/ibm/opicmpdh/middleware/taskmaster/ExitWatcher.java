//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ExitWatcher.java,v $
// Revision 1.20  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.19  2005/01/25 22:34:58  dave
// Jtest Syntax
//
// Revision 1.18  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Stopwatch;


import java.io.File;


// a class which wakes up looks for tmshut file if found causes tm to shut


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public class ExitWatcher extends Thread {
	private static int c_iSleep_time =
		TaskMasterProperties.getShutWatchQuantum();
	private TaskMaster m_tm = null;
	private boolean m_bRun = true;

	/**
     * Create a new Exit Watcher
     * 
     * @param _tm
     * @author Dave
     */
    public ExitWatcher(TaskMaster _tm) {
		m_tm = _tm;
		m_bRun = true;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
		this.start();
		Log.out(this +" ExitWatcher has been started");  //$NON-NLS-1$
	}
	/**
     * shut down this exit watcher
     * 
     * @author Dave
     */
    public final void shut() {
		Log.out(this +" ExitWatcher shut has been called");  //$NON-NLS-1$

		m_bRun = false;
	}
	/**
     * run something
     * 
     * @see java.lang.Runnable#run()
     * @author Dave
     */
    public final void run() {

		File f1 = null;
		
		Log.out(this +" ExitWatcher run has started");  //$NON-NLS-1$
		f1 = new File(TaskMaster.SHUTDOWN_SEMAPHORE_FILE);

		while (m_bRun) {
			try {
				Log.out(
					"ExitWatcher going to sleep for "  //$NON-NLS-1$
						+ Stopwatch.format(c_iSleep_time)
						+ " ...");  //$NON-NLS-1$
				Thread.sleep(c_iSleep_time);
			} catch (Exception x) {
				Log.out("sleep trouble in ExitWatcher " + x);  //$NON-NLS-1$
			}

			Log.out("ExitWatcher is now awake");  //$NON-NLS-1$

			if (f1.exists()) {
				Log.out(this +" detected the taskmaster shut file " + f1);  //$NON-NLS-1$
				m_tm.shut();
			}
		}

		Log.out(this +" ExitWatcher run is stopping");  //$NON-NLS-1$
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: ExitWatcher.java,v 1.20 2005/01/26 17:47:39 dave Exp $";  //$NON-NLS-1$
	}
}
