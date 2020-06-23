/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ESwingWorker.java,v $
 * Revision 1.4  2009/05/28 13:57:50  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/10 11:33:20  wendy
 * Add timestamp for debug
 *
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/09/23 16:27:23  tony
 * cleaned-up formatting
 *
 * Revision 1.3  2003/05/28 17:04:33  tony
 * updated eSwingworker.
 *
 * Revision 1.2  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import javax.swing.SwingUtilities;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class ESwingWorker {
	private Object value = null;  // see getValue(), setValue()
//	private Thread thread = null;
	private boolean bBreak = true;
	private static int count=0;
	private String swname = null;

    /**
     * Class to maintain reference to current worker thread
     * under separate synchronization control.
     */
	private static class ThreadVar {
		private Thread thread;
		ThreadVar(Thread t) { thread = t; }
		synchronized Thread get() {
			return thread;
		}
		synchronized void clear() {
			thread = null;
			return;
		}
	}

	private ThreadVar threadVar;
	private Long workerStarted = null;

	/**
     * Get the value produced by the worker thread, or null if it
     * hasn't been constructed yet.
     *
     * @return Object
     * @concurrency $none
     */
	protected synchronized Object getValue() {
		return value;
	}

	/**
	 * Set the value produced by worker thread
	 */
	private synchronized void setValue(Object x) {
		value = x;
	}

	/**
     * Compute the value to be returned by the <code>get</code> method.
     *
     * @return Object
     */
	public abstract Object construct();

	/**
	 * Called on the event dispatching thread (not on the worker thread)
	 * after the <code>construct</code> method has returned.
	 */
	public void finished() {}

	/**
     * isInterruptable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isInterruptable() {
		return bBreak;
	}

	/**
     * setInterruptable
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setInterruptable(boolean _b) {
		bBreak = _b;
		return;
	}

	/**
	 * A new method that interrupts the worker thread.  Call this method
	 * to force the worker to stop what it's doing.
	 */
	public void interrupt() {
		Thread t = threadVar.get();
		if (t != null) {
			t.interrupt();
			t.stop();																//ctrl-break
		}
		threadVar.clear();
		finished();
		return;
	}

	/**
	 * Return the value created by the <code>construct</code> method.
	 * Returns null if either the constructing thread or the current
	 * thread was interrupted before a value was produced.
	 *
	 * @return the value created by the <code>construct</code> method
	 */
	public Object get() {
		while (true) {
			Thread t = threadVar.get();
			if (t == null) {
				return getValue();
			}
			try {
				t.join();
			} catch (InterruptedException e) {
                EAccess.report(e,false);
				Thread.currentThread().interrupt(); // propagate
				return null;
			}
		}
	}

	/**
	 * Start a thread that will call the <code>construct</code> method
	 * and then exit.
	 */
	public ESwingWorker() {
		count++;
		swname=count+"SwingWorker";
		final Runnable doFinished = new Runnable() {
			public void run() {
				try{
					finished();
				}catch(java.lang.NoClassDefFoundError nce){
					nce.printStackTrace();
					EAccess.eaccess(). getLogin().reportProperties();
				}
				EAccess.eaccess().timestamp(swname+" Ended",workerStarted);
			}
		};

		Runnable doConstruct = new Runnable() {
			public void run() {
				try {
					setValue(construct());
				} finally {
					threadVar.clear();
				}
				SwingUtilities.invokeLater(doFinished);
			}
		};

		Thread t = new Thread(doConstruct);
		threadVar = new ThreadVar(t);
	}

	/**
	 * Start the worker thread.
	 */
	public void start() {
		workerStarted = EAccess.eaccess().timestamp(swname+" Started");
		Thread t = threadVar.get();
		if (t != null) {
			t.start();
		}
	}

	/**
     * pause
     * @param _i
     * @author Anthony C. Liberto
     */
    public void pause(int _i) {
		//Thread t = threadVar.get();
		try {
			Thread.sleep(_i);
		} catch (InterruptedException _ie) {
			_ie.printStackTrace();
		}
		return;
	}
}
