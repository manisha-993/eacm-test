//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ConnectionManager.java,v $
// Revision 1.29  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.28  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;



import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */


public final class ConnectionManager {
	// Holds the Connections we need to keep in contact with
	private Vector m_vctConnection = new Vector();
	// Does the actual work of ensuring we are in contact with Connection
	private ConnectionWatcher m_connWatch = null;
	// Name of ConnectionManager owner
	private String m_strOwnerName = null;
	private long m_lLeaseDuration = 0;

	/**
     * @param _strOwnerName
     * @author Dave
     */
    public ConnectionManager(String _strOwnerName) {
		m_strOwnerName = _strOwnerName;
		m_connWatch = new ConnectionWatcher(m_vctConnection, this);
	}
	/**
     * @param _conn
     * @return
     * @author Dave
     */
    public final Object addConnection(Connection _conn) {
		Log.out("ConnectionManager " + this +" now monitoring " + _conn);  //$NON-NLS-1$  //$NON-NLS-2$
		m_vctConnection.add(_conn);

		return _conn.getObject();
	}
	/**
     * @author Dave
     */
    protected final void closeAll() {
		Log.out(this +" ConnectionManager closeAll called");  //$NON-NLS-1$
		m_connWatch.shut();
		// Remove all Connections
		m_vctConnection.clear();
	}
	/**
     * @param _conn
     * @return
     * @author Dave
     */
    public final Connection getConnection(Connection _conn) {
		for (int i = 0; i < m_vctConnection.size(); i++) {
			if (_conn.equals(m_vctConnection.elementAt(i))) {
				return (Connection) m_vctConnection.elementAt(i);
			}
		}

		return null;
	}
	/**
     * @param _conn
     * @author Dave
     */
    public final void removeConnection(Connection _conn) {
		for (int i = 0; i < m_vctConnection.size(); i++) {
			if (_conn.equals(m_vctConnection.elementAt(i))) {
				m_vctConnection.removeElementAt(i);
			}
		}
	}
	/**
     * @param _lLeaseDuration
     * @author Dave
     */
    protected final void setLeaseDuration(long _lLeaseDuration) {
		m_lLeaseDuration = _lLeaseDuration;
	}
	/**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public final String toString() {
		return "<" + m_strOwnerName + ">";  //$NON-NLS-1$  //$NON-NLS-2$
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: ConnectionManager.java,v 1.29 2005/01/25 22:24:35 dave Exp $";  //$NON-NLS-1$
	}
}
