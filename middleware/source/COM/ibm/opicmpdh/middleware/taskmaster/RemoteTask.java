//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RemoteTask.java,v $
// Revision 1.23  2008/01/31 21:53:52  wendy
// Cleanup RSA warnings
//
// Revision 1.22  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.21  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.20  2002/10/17 20:21:21  roger
// Change ping response back to normal value
//
// Revision 1.19  2002/10/16 19:59:47  roger
// Change ping response
//
// Revision 1.18  2002/10/02 23:06:24  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import java.rmi.RemoteException;


import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public class RemoteTask extends Activatable implements RemoteRunnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Ping Response contant
     */
    public static final String PING_RESPONSE = "pong";
	private String m_strName = null;
	private boolean m_bBusy = false;

	/**
     * eAnnounce Remote Task
     * @param _id
     * @param _iPort
     * @throws java.rmi.RemoteException
     * @author Dave
     */
    public RemoteTask(ActivationID _id, int _iPort) throws RemoteException {
		super(_id, 0);
	}
	/**
     * exit this JVM
     * 
     * @author Dave
     */
    public final void exit() {
		Log.out(this + " will now exit");
		System.gc();
		System.runFinalization();
		System.exit(0);
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.RemoteRunnable#getBusy()
     * @author Dave
     */
    public final boolean getBusy() throws RemoteException {
		return m_bBusy;
	}
	/**
     * getName
     * @return
     * @author Dave
     */
    public final String getName() {
		return m_strName;
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.RemoteRunnable#getUseCount()
     * @author Dave
     */
    public int getUseCount() throws RemoteException {
		return -1;
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.RemoteRunnable#ping()
     * @author Dave
     */
    public final String ping() throws RemoteException {
		return PING_RESPONSE;
	}
	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.RemoteRunnable#setBusy(boolean)
     * @author Dave
     */
    public final void setBusy(boolean _bBusy) throws RemoteException {
		m_bBusy = _bBusy;
	}
	/**
     * sets the name of the RemoatTask
     * 
     * @param _strName
     * @author Dave
     */
    public final void setName(String _strName) {
		m_strName = _strName;
	}
	/**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
		return "*" + m_strName + "*";
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static String getVersion() {
		return "$Id: RemoteTask.java,v 1.23 2008/01/31 21:53:52 wendy Exp $";
	}
}
