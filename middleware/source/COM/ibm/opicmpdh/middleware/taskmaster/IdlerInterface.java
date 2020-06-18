//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: IdlerInterface.java,v $
// Revision 1.13  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.12  2005/01/26 01:05:18  dave
// Jtest cleanup
//
// Revision 1.11  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.Profile;


import COM.ibm.opicmpdh.transactions.OPICMABRItem;
import java.rmi.RemoteException;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */


public interface IdlerInterface extends RemoteRunnable {
	/**
     * getName
     * 
     * @throws java.rmi.RemoteException
     * @return
     * @author Dave
     */
    String getName() throws RemoteException;
	/**
     * getStatus
     * 
     * @throws java.rmi.RemoteException
     * @return
     * @author Dave
     */
    String getStatus() throws RemoteException;
	/**
     * launchClass
     * 
     * @param _strTaskName
     * @param _strClassName
     * @param _strJobName
     * @param _iJobID
     * @param _prof
     * @param _abri
     * @param _iWhich
     * @throws java.rmi.RemoteException
     * @return
     * @author Dave
     */
    int launchClass(
		String _strTaskName,
		String _strClassName,
		String _strJobName,
		int _iJobID,
		Profile _prof,
		OPICMABRItem _abri,
		int _iWhich)
		throws RemoteException;
	/**
     * shut
     * 
     * @throws java.rmi.RemoteException
     * @author Dave
     */
    void shut() throws RemoteException;
}
