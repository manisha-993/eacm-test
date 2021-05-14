/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.3  2003/03/01
 * @author Anthony C. Liberto
 *
 * $Log: Worker.java,v $
 * Revision 1.2  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.5  2005/02/03 21:26:16  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.3  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.2  2004/05/18 17:37:28  tony
 * updated server logic.
 *
 * Revision 1.1  2004/03/01 19:38:05  tony
 * usability enhancements.
 *
 *
 */
package com.ibm.eannounce.eserver;
import javax.swing.SwingUtilities;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class Worker {
    //private Thread thread = null;

    private static class ThreadVar {
        private Thread thread;
        ThreadVar(Thread t) {
            thread = t;
        }
        synchronized Thread getThread() {
            return thread;
        }
        synchronized void clear() {
            thread = null;
            return;
        }
    }

    private ThreadVar threadVar;

    /**
     * interrupt
     * @author Anthony C. Liberto
     */
    public void interrupt() {
        Thread t = threadVar.getThread();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
        wrapup();
        return;
    }

    /**
     * worker
     * @author Anthony C. Liberto
     */
    public Worker() {
        final Runnable doFinished = new Runnable() {
            public void run() {
                wrapup();
            }
        };

        Runnable doProcess = new Runnable() {
            public void run() {
                try {
                    System.out.println("starting process: " + getMethodName());
                    process();
                    System.out.println("finishing process: " + getMethodName());
                } finally {
                    threadVar.clear();
                }
                if (hasWrapup()) {
                    SwingUtilities.invokeLater(doFinished);
                }
            }
        };

        Thread t = new Thread(doProcess);
        threadVar = new ThreadVar(t);
    }

    /**
     * start
     * @author Anthony C. Liberto
     */
    public void start() {
        Thread t = threadVar.getThread();
        if (t != null) {
            t.start();
        }
    }

    /**
     * process
     * @author Anthony C. Liberto
     */
    public abstract void process();
    /**
     * wrapup
     * @author Anthony C. Liberto
     */
    public void wrapup() {
    }
    /**
     * getMethodName
     * @return
     * @author Anthony C. Liberto
     */
    public String getMethodName() {
        return "method Name";
    }
    /**
     * isInterruptable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isInterruptable() {
        return false;
    }
    /**
     * hasWrapup
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasWrapup() {
        return true;
    }
}
