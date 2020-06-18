//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Shutter.java,v $
// Revision 1.19  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.18  2002/10/15 16:53:00  roger
// Show owner in message
//
// Revision 1.17  2002/10/02 23:06:25  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Stopwatch;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public final class Shutter extends Thread {
	private RemoteTask m_task = null;
	private static int c_iDelay = 0;
	private String m_strOwnerName = null;

	/**
     * Shutter
     *
     * @param _task
     * @param _strOwnerName
     * @param _iDelay
     * @author Dave
     */
	public Shutter(RemoteTask _task, String _strOwnerName, int _iDelay) {
		m_task = _task;
		m_strOwnerName = _strOwnerName;
		c_iDelay = _iDelay;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
		Log.out(this +" Shutter will be started");
		this.start();
	}
	// When we run, we sleep for a while and then tell Task to exit
	/**
	 * @see java.lang.Runnable#run()
	 * @author Dave
	 */
	public final void run() {

		Log.out(this +" Shutter run has started");

		try {
			Log.out(
				this
					+ " Shutter going to sleep for "
					+ Stopwatch.format(c_iDelay)
					+ " ...");
			Thread.sleep(c_iDelay);
			Log.out(this +" Shutter will now request task to exit");
			m_task.exit();
		} catch (Exception x) {
			Log.out("Trouble sending exit" + x);
		}

		Log.out(this +" Shutter is stopping");
	}
	/**
	 * @see java.lang.Object#toString()
	 * @author Dave
	 */
	public final String toString() {
		return "~" + m_strOwnerName + "~";
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: Shutter.java,v 1.19 2005/01/26 01:05:19 dave Exp $";
	}
}
