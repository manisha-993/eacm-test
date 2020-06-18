//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RegisterObject.java,v $
// Revision 1.23  2014/02/28 21:21:42  wendy
// JVMCI015:OutOfMemoryError cleanup
//
// Revision 1.22  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.21  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.20  2002/10/22 16:22:42  roger
// Rebind must occur using complete URL
//
// Revision 1.19  2002/10/02 23:06:24  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Stopwatch;


import java.rmi.Naming;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public final class RegisterObject extends Thread {
	private static int c_iSleep_time = TaskMasterProperties.getRebindQuantum();
	private boolean m_bRun = true;
	private RemoteTask m_rt = null;
	private String m_strObjectName = null;

	/**
     * Register the Object
     * 
     * @param _strObjectName
     * @param _rt
     * @author Dave
     */
    public RegisterObject(String _strObjectName, RemoteTask _rt) {
		m_strObjectName = _strObjectName;
		m_rt = _rt;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
		Log.out(this +" RegisterObject will be started");
		this.start();
	}
	/**
     * @see java.lang.Runnable#run()
     * @author Dave
     */
    public final void run() {

		//    Log.out(this + " RegisterObject run has started");
		while (m_bRun) {
			try {
				Log.out(
					this
						+ " RegisterObject going to sleep for "
						+ Stopwatch.format(c_iSleep_time)
						+ " ...");
				Thread.sleep(c_iSleep_time);
			} catch (Exception x) {
				Log.out("sleep trouble in RegisterObject " + x);
			}

			if (m_bRun) { 
				try {
					Log.out(this +" Rebinding " + m_strObjectName);
					Naming.rebind(Connection.createURL(m_strObjectName), m_rt);
				} catch (Throwable x) { // catch java.lang.OutOfMemoryError
					Log.out(this +" rebind failed " + x);
					if(x instanceof OutOfMemoryError){
						Log.out("Thread.activeCount: "+Thread.activeCount());
						x.printStackTrace();
					}
				}
			} else {
				Log.out(
					this
						+ " RegisterWatcher was scheduled for rebind but shut requested");
			}
		}

		Log.out(this +" RegisterWatcher run is stopping");
	}
	/**
     * Shuts the Registered Object Down
     * 
     * @author Dave
     */
    public final void shut() {
		Log.out(this +" RegisterObject shut has been called");

		m_bRun = false;
	}
	/**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public final String toString() {
		return "#" + m_strObjectName + "#";
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: RegisterObject.java,v 1.23 2014/02/28 21:21:42 wendy Exp $";
	}
}
