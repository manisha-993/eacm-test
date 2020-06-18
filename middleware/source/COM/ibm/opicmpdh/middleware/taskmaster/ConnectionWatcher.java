//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ConnectionWatcher.java,v $
// Revision 1.31  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.30  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;

import COM.ibm.opicmpdh.middleware.Stopwatch;


import java.rmi.RemoteException;
import java.util.Vector;


final class ConnectionWatcher extends Thread {
	private static final long iSLEEP_TIME =
		TaskMasterProperties.getConnectionLeaseRefreshQuantum();
	private Vector m_vctConnection = null;
	private ConnectionManager m_conMgr = null;
	private boolean m_bRun = true;
	private final boolean m_bPingWithRetry = true;

	/**
     * @param _vctConnection
     * @param _conMgr
     * @author Dave
     */
    ConnectionWatcher(
		Vector _vctConnection,
		ConnectionManager _conMgr) {
		m_vctConnection = _vctConnection;
		m_conMgr = _conMgr;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
		Log.out(m_conMgr + " ConnectionWatcher will be started");  //$NON-NLS-1$
		this.start();
		//Log.out(m_conMgr + " ConnectionWatcher has been started");!!!
	}
	/**
     * @param _conn
     * @return
     * @author Dave
     */
    protected final boolean ping(Connection _conn) {

		String strResponse = null;

		Log.out(m_conMgr + " going to ping now ...");  //$NON-NLS-1$

		try {
			strResponse = (_conn.getObject().ping()) + "";  //$NON-NLS-1$
		} catch (RemoteException rx) {
			Log.out(m_conMgr + " ping failure " + _conn + " " + rx);  //$NON-NLS-1$  //$NON-NLS-2$
		}

		//Log.out(m_conMgr + " ping response from " + _conn + " was '" + strResponse + "'");
		return (strResponse.equalsIgnoreCase(RemoteTask.PING_RESPONSE));
	}
	/**
     * @param _conn
     * @return
     * @author Dave
     */
    protected final boolean pingWithRetry(Connection _conn) {

		for (int i = 0; i < TaskMasterProperties.getPingRetryCount(); i++) {
			if (ping(_conn)) {
				return true;
			}

			try {
				Thread.sleep(TaskMasterProperties.getPingRetryDelay());
			} catch (Exception x) {
				L.debug("sleep trouble in pingWithRetry " + x);  //$NON-NLS-1$
			}
		}

		Log.out("pingWithRetry failed");  //$NON-NLS-1$

		return false;
	}
	/**
     * @see java.lang.Runnable#run()
     * @author Dave
     */
    public void run() {

		//Log.out(m_conMgr + " ConnectionWatcher run has started");!!!
		while (m_bRun) {
			
			long lNow = System.currentTimeMillis();

			try {
				Log.out(
					m_conMgr
						+ " ConnectionWatcher is going to sleep for "  //$NON-NLS-1$
						+ Stopwatch.format(iSLEEP_TIME)
						+ " ...");  //$NON-NLS-1$
				Thread.sleep(iSLEEP_TIME);
			} catch (Exception x) {
				Log.out(
					m_conMgr
						+ " ConnectionWatcher having trouble sleeping "  //$NON-NLS-1$
						+ x);
			}

			Log.out(
				m_conMgr
					+ " ConnectionWatcher is awake and managing "  //$NON-NLS-1$
					+ m_vctConnection.size()
					+ " connections");  //$NON-NLS-1$

			for (int i = 0; i < m_vctConnection.size(); i++) {
				if (m_vctConnection.elementAt(i) instanceof Connection) {
					Connection conn = (Connection) m_vctConnection.elementAt(i);
					String strLease = conn.getObjectName();

					Log.out(
						m_conMgr
							+ " has lease with "  //$NON-NLS-1$
							+ strLease
							+ " remaining "  //$NON-NLS-1$
							+ Stopwatch.format(conn.getLeaseRemaining())
							+ " expired: "  //$NON-NLS-1$
							+ (conn.isLeaseExpired()));

					if (conn.isLeaseExpired()) {
						if (m_bPingWithRetry) {
							if (!pingWithRetry(conn)) {
								Log.out("pingWithRetry failed");  //$NON-NLS-1$
							}
						} else {
							if (!ping(conn)) {
								Log.out("ping failed");  //$NON-NLS-1$
							}
						}

						conn.setLeaseExpireTime(
							lNow
								+ TaskMasterProperties
									.getConnectionLeaseDuration());
						Log.out("we exited from ping");  //$NON-NLS-1$
					}
				}
			}
		}

		Log.out(m_conMgr + " ConnectionWatcher is exiting");  //$NON-NLS-1$
	}
	/**
     * @author Dave
     */
    public final void shut() {
		Log.out(m_conMgr + " ConnectionWatcher shut has been called");  //$NON-NLS-1$

		m_bRun = false;
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: ConnectionWatcher.java,v 1.31 2005/01/25 22:24:35 dave Exp $";  //$NON-NLS-1$
	}
}
