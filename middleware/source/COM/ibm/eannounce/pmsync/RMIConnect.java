//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: RMIConnect.java,v $
//Revision 1.2  2007/07/31 13:03:45  chris
//Rational Software Architect v7
//
//Revision 1.1  2003/05/29 19:53:42  gregg
//initial load
//
//

package COM.ibm.eannounce.pmsync;

import java.rmi.RemoteException;

import COM.ibm.opicmpdh.middleware.Middleware;
import COM.ibm.opicmpdh.middleware.MiddlewareClientProperties;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
 * provides a RemoteDatabase connection object via RMI
 */
public final class RMIConnect {

	private boolean m_bRmiReady = false;
	private RemoteDatabaseInterface m_ro = null;
	private Middleware m_oMw = null;

	private static final int MAX_RETRIES = 10;

    private static String c_strMiddlewareServerName = null;;
    private static String c_strMiddlewareObjectName = null;
    private static int    c_iMiddlewarePort         = -1;

	public RMIConnect() {}

	public RemoteDatabaseInterface connect() {
		RMIReset();
		return m_ro;
	}
    
    public void disconnect() {
        if(m_ro != null) {
            Middleware.disconnect(m_ro);
        }
    }

/**************************************************************************************************/
/*************************************  static Accessors  *****************************************/
/**************************************************************************************************/

    public static String getServer() {
        return c_strMiddlewareServerName;
    }
    public static int getPort() {
        return c_iMiddlewarePort;
    }
    public static String getName() {
        return c_strMiddlewareObjectName;
    }

/**************************************************************************************************/
/*************************************  RMI CONTROLS  *********************************************/
/**************************************************************************************************/

	public void RMIReset() {
		m_bRmiReady = false;
		RMIControl();
		return;
	}

	private void RMIControl() {
		if (m_bRmiReady)
			return;
		int i = 0;
		for (i = 0; i < MAX_RETRIES; ++i) {
			if (initiateRMI())
				break;
			else
				waitFor(1000);
		}
		if (i >= MAX_RETRIES)
			rmiFailure("1007");
		return;
	}

	private boolean initiateRMI() {
		if (m_bRmiReady)
			return m_bRmiReady;
		try {
			//read from middleware.client.properties
			m_ro = m_oMw.connect();
			//set these for external access
			c_strMiddlewareServerName = MiddlewareClientProperties.getObjectConnectIpAddress();
			c_strMiddlewareObjectName = MiddlewareClientProperties.getDatabaseObjectName();
			c_iMiddlewarePort         = Integer.parseInt(MiddlewareClientProperties.getObjectConnectPort());			
			m_bRmiReady = true;
		} catch (RemoteException re) {
			System.out.println("remoteException" + re);
		} catch (Exception ex) {
			System.out.println(ex);
			rmiFailure("1007");
		}
		return m_bRmiReady;
	}

	private void waitFor(int _i) {
		Thread t = new Thread();
		try {
			t.sleep(_i);
		} catch (Exception x) {
			System.out.println("waitFor failure:  " + x);
		}
		return;
	}

	private void closeRMI() {
		if (!m_bRmiReady)
			return;
		try {
			m_oMw.disconnect(m_ro);
		} catch (Exception ex) {
			rmiFailure("1010");
		}
		return;
	}

	private void rmiFailure(String _s) {
		System.out.println("rmiFailure: " + _s);
		return;
    }

	private int MWError(Exception _x, String _str) {
	    System.out.println("MWError:" + _str);
		if (_x instanceof java.net.NoRouteToHostException) {
			RMIReset();
		}
		return -1;
	}

}
