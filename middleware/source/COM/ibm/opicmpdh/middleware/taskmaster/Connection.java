//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Connection.java,v $
// Revision 1.37  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.36  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.35  2005/01/25 22:34:57  dave
// Jtest Syntax
//
// Revision 1.34  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.33  2002/11/08 17:23:27  roger
// Why ABR not being changed to Queued?
//
// Revision 1.32  2002/10/15 16:42:25  roger
// Parameterize port settings to allow multiple instances of TM
//
// Revision 1.31  2002/10/02 23:06:10  roger
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

public final class Connection {
  private static final String RMI_PROTOCOL = "rmi"; //$NON-NLS-1$
  private static final String RMI_HOST =
    TaskMasterProperties.getServerBindIpAddress();
  private static final int RMI_PORT =
    Integer.parseInt(TaskMasterProperties.getRegistryPort());
  private static final String RMI_NAME = "OBJECT"; //$NON-NLS-1$
  private String m_strProtocol = null;
  private String m_strHost = null;
  private int m_iPort = 0;
  private String m_strObjectName = null;
  private String m_strURL = null;
  private RemoteRunnable m_rr = null;
  private long m_lLeaseExpireTime = 0;

  /**
   * create a new Connection
   *
   * @author Dave
   */
  public Connection() {
    this(RMI_PROTOCOL, RMI_HOST, RMI_PORT, RMI_NAME);
  }
  /**
   * make a new Connection with a connection name
   *
   * @param _strObjectName
   * @author Dave
   */
  public Connection(String _strObjectName) {
    this(RMI_PROTOCOL, RMI_HOST, RMI_PORT, _strObjectName);
  }
  /**
   * Connection
   *
   * @param _strHost
   * @param _iPort
   * @param _strObjectName
   * @author Dave
   */
  public Connection(String _strHost, int _iPort, String _strObjectName) {
    this(RMI_PROTOCOL, _strHost, _iPort, _strObjectName);
  }
  /**
   * Connection
   *
   * @param _strHost
   * @param _strObjectName
   * @author Dave
   */
  public Connection(String _strHost, String _strObjectName) {
    this(RMI_PROTOCOL, _strHost, RMI_PORT, _strObjectName);
  }
  /**
   * Connection
   *
   * @param _strProtocol
   * @param _strHost
   * @param _iPort
   * @param _strObjectName
   * @author Dave
   */
  public Connection(
    String _strProtocol,
    String _strHost,
    int _iPort,
    String _strObjectName) {
    m_strProtocol = _strProtocol;
    m_strHost = _strHost;
    m_iPort = _iPort;
    m_strObjectName = _strObjectName;
    m_strURL = _strProtocol + "://" + _strHost + ":" + _iPort + "/" + _strObjectName; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
    m_lLeaseExpireTime =
      System.currentTimeMillis()
      + TaskMasterProperties.getConnectionLeaseDuration();
  }
  /**
   * @author Dave
   */
  protected final void connect() {

    Log.out("start of connect " + this); //$NON-NLS-1$

    try {
      Log.out("lookup begin " + this); //$NON-NLS-1$

      m_rr = (RemoteRunnable) Naming.lookup(m_strURL);

      Log.out("lookup complete " + this); //$NON-NLS-1$
      // Refresh the lease
      setLeaseExpireTime(0);
    } catch (Exception x) {
      Log.out("can't connect to object " + m_strURL + " " + x); //$NON-NLS-1$  //$NON-NLS-2$

      m_rr = null;
    }

    Log.out("connect complete to " + this); //$NON-NLS-1$
  }
  /**
   * creates a new URL given a string Object Name
   *
   * @param _strObjectName
   * @return
   * @author Dave
   */
  public final static String createURL(String _strObjectName) {
    return RMI_PROTOCOL + "://" + RMI_HOST + ":" + RMI_PORT + "/" + _strObjectName; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
  }
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   * @author Dave
   */
  public final boolean equals(Object _obj) {
    if ((_obj != null) && (_obj instanceof Connection)) {
      Connection test = (Connection) _obj;

      return (("" + this).equals("" + test)); //$NON-NLS-1$  //$NON-NLS-2$
    }

    return false;
  }
  /**
   * getLeaseExpireTime
   *
   * @return
   * @author Dave
   */
  protected final long getLeaseExpireTime() {
    return m_lLeaseExpireTime;
  }
  /**
   * getLeaseRemaining
   *
   * @return
   * @author Dave
   */
  protected final long getLeaseRemaining() {
    long lNow = System.currentTimeMillis();

    return m_lLeaseExpireTime - lNow;
  }
  /**
   * getObject
   *
   * @return
   * @author Dave
   */
  public final RemoteRunnable getObject() {
    if (m_rr == null) {
      connect();
    }

    if (m_rr == null) {
      Log.out("getObject in Connection will return null"); //$NON-NLS-1$
    }

    return m_rr;
  }
  /**
   * getObjectName
   *
   * @return
   * @author Dave
   */
  public final String getObjectName() {
    return m_strObjectName;
  }
  /**
   * getURL
   *
   * @return
   * @author Dave
   */
  public final String getURL() {
    return m_strURL;
  }
  /**
   * isLeaseExpired
   *
   * @return
   * @author Dave
   */
  protected final boolean isLeaseExpired() {
    return (System.currentTimeMillis() > m_lLeaseExpireTime);
  }
  /**
   * setLeaseExpireTime - how long should a lease remain?
   *
   * @param _lTime
   * @author Dave
   */
  protected final void setLeaseExpireTime(long _lTime) {

    long lNow = System.currentTimeMillis();

    // If time is specified use that as base, else use now as base
    if (_lTime != 0) {
      m_lLeaseExpireTime = _lTime;
    } else {
      m_lLeaseExpireTime =
        lNow + TaskMasterProperties.getConnectionLeaseDuration();
    }

    Log.out("lease updated for " + this +" new expiration in " + Stopwatch.format(m_lLeaseExpireTime - lNow)); //$NON-NLS-1$  //$NON-NLS-2$
  }
  /**
   * @see java.lang.Object#toString()
   * @author Dave
   */
  public final String toString() {
    return m_strURL;
  }
  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public static final String getVersion() {
    return "$Id: Connection.java,v 1.37 2005/01/27 04:02:36 dave Exp $"; //$NON-NLS-1$
  }

}
