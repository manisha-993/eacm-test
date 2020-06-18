//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Runner.java,v $
// Revision 1.15  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.14  2002/11/08 17:23:27  roger
// Why ABR not being changed to Queued?
//
// Revision 1.13  2002/10/02 23:06:25  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */


public final class Runner extends Thread {
	private AbstractTask m_at = null;
	private Idler m_idler = null;

	/**
     * @param _at
     * @param _idler
     * @author Dave
     */
    public Runner(AbstractTask _at, Idler _idler) {
		m_at = _at;
		m_idler = _idler;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
	}
	/**
     * @see java.lang.Runnable#run()
     * @author Dave
     */
    public final void run() {

		try {
			// Restart not yet supported
			if (1 == 1) {
				m_at.run();
			} else {
				m_at.restart();
			}
		} catch (RuntimeException rx) {
			Log.out("Trouble running task " + m_at + " " + rx);
			System.gc();
			System.runFinalization();
			System.exit(-1);
		}
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: Runner.java,v 1.15 2005/01/26 17:47:39 dave Exp $";
	}
}
