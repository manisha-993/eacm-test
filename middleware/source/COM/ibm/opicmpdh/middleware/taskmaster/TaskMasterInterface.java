//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TaskMasterInterface.java,v $
// Revision 1.15  2013/05/24 14:12:50  wendy
// taskmaster perf enhancements include:
//  - Made idler classes case sensitive - can have 52 now
//  - Added callback to attempt to run another ABR as soon as one completes.
//  - Replaced string lookups with hash set
//  - if ABR is inprocess status when taskmaster starts, attempt to run the ABR again if readonly, if not readonly then fail it in the pdh and notify user that ABR was aborted
//
// Revision 1.14  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.13  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.12  2002/10/02 23:06:25  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;

import COM.ibm.opicmpdh.transactions.OPICMABRItem;
import java.rmi.RemoteException;

 
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public interface TaskMasterInterface extends RemoteRunnable {
	/**
     * launch routine
     * 
     * @param _iWhich
     * @param _strName
     * @param _iJobID
     * @param _abri
     * @throws java.rmi.RemoteException
     * @author Dave
     */
    void launch(
		int _iWhich,
		String _strName,
		int _iJobID,
		OPICMABRItem _abri)
		throws RemoteException;
	/**
     * notify task is complete
     */
    void taskComplete() throws RemoteException;
}
