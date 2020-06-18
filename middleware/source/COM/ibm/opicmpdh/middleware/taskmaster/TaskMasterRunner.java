//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TaskMasterRunner.java,v $
// Revision 1.23  2014/02/28 21:20:51  wendy
// JVMCI015:OutOfMemoryError cleanup
//
// Revision 1.22  2013/05/24 14:12:50  wendy
// taskmaster perf enhancements include:
//  - Made idler classes case sensitive - can have 52 now
//  - Added callback to attempt to run another ABR as soon as one completes.
//  - Replaced string lookups with hash set
//  - if ABR is inprocess status when taskmaster starts, attempt to run the ABR again if readonly, if not readonly then fail it in the pdh and notify user that ABR was aborted
//
// Revision 1.21  2008/11/06 16:26:52  wendy
// Release memory
//
// Revision 1.20  2008/01/31 21:53:52  wendy
// Cleanup RSA warnings
//
// Revision 1.19  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.18  2002/11/08 17:23:27  roger
// Why ABR not being changed to Queued?
//
// Revision 1.17  2002/10/02 23:06:25  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;

import COM.ibm.opicmpdh.transactions.OPICMABRItem;

/** 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */

public final class TaskMasterRunner extends Thread {
	private int m_iWhich = -1;
	private String m_strJobName = null;
	private int m_iJobID = -1;
	private OPICMABRItem m_abri = null;
	private TaskMasterInterface m_tmi = null;

	/**
     * @param _tmi
     * @param _iWhich
     * @param _strJobName
     * @param _iJobID
     * @param _abri
     * @author Dave
     */
    public TaskMasterRunner(
		TaskMasterInterface _tmi,
		int _iWhich,
		String _strJobName,
		int _iJobID,
		OPICMABRItem _abri) {
		m_iWhich = _iWhich;
		m_strJobName = _strJobName;
		m_iJobID = _iJobID;
		m_tmi = _tmi;
		m_abri = _abri;

		this.setPriority(Thread.MIN_PRIORITY);
		this.setDaemon(true);
	}
	/**
     * @see java.lang.Runnable#run()
     * @author Dave
     */
    public final void run() {
		try { 
			m_tmi.launch(m_iWhich, m_strJobName, m_iJobID, m_abri);
			// dont do this now, depending on tm workload it can use too many threads
			// java.lang.OutOfMemoryError: JVMCI015:OutOfMemoryError, cannot create anymore threads due to memory or resource constraints
			// m_tmi.taskComplete();
		} catch (Throwable x) { // catch java.lang.OutOfMemoryError
			Log.out("TaskMasterRunner could not launch "+m_abri+": " + x);
			if(x instanceof OutOfMemoryError){
				L.debug("Thread.activeCount: "+Thread.activeCount());
				x.printStackTrace();
			}
		}finally{
			// release memory now
			m_strJobName = null;
			m_abri.dereference();
			m_abri = null;
			m_tmi = null;	
		}
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: TaskMasterRunner.java,v 1.23 2014/02/28 21:20:51 wendy Exp $";
	}
}
