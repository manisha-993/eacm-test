//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RemoteRunnable.java,v $
// Revision 1.14  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.13  2002/10/02 23:06:24  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public interface RemoteRunnable extends Remote {
	/**
	 * getBusy
	 * @throws java.rmi.RemoteException
	 * @return
	 * @author Dave
	 */
	boolean getBusy() throws RemoteException;
	/**
	 * getUseCount
	 * 
	 * @throws java.rmi.RemoteException
	 * @return
	 * @author Dave
	 */
	int getUseCount() throws RemoteException;
	/**
	 *  ping
	 * 
	 * @throws java.rmi.RemoteException
	 * @return
	 * @author Dave
	 */
	String ping() throws RemoteException;
	/**
	 * setBusy
	 * 
	 * @param _bBusy
	 * @throws java.rmi.RemoteException
	 * @author Dave
	 */
	void setBusy(boolean _bBusy) throws RemoteException;
}
