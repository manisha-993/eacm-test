//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mw;

import java.util.logging.Level;

import javax.swing.*;

/******************************************************************************
* This is used to control number of calls made to mw at one time
* RMIMgr executes this class
* A new instance of javax.swing.SwingWorker is needed for each new background task.
* @author Wendy Stimpson
*/
// $Log: DBSwingWorker.java,v $
// Revision 1.3  2014/10/20 19:56:08  wendy
// Add worker id to timing log output
//
// Revision 1.2  2013/07/18 18:31:06  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:22  wendy
// Initial code
//
public abstract class DBSwingWorker<T, V> extends SwingWorker<T, V>{
	private int id;
	private long starttime=Long.MIN_VALUE;

	/**
	 * @param i
	 */
	public void setId(int i){
		id=i;
	}
	public String getIdStr(){
		return "id_"+id;
	}
	/**
	 * @param l
	 */
	public void setStarttime(long l){
		starttime=l;
	}
	/**
	 * @return
	 */
	public long getStarttime(){
		return starttime;
	}

	/* (non-Javadoc)
	 * assure unique comparison in hashtable
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){ return id;}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object o) {
		return this.id == ((DBSwingWorker<?, ?>)o).id;
	}

	/**
	 * called when RMIMgr will not execute this worker
	 */
	public abstract void notExecuted();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){ return getClass().getName()+"_"+id;}

	/**
	 * @param e
	 * @param src
	 */
	protected void listErr(java.util.concurrent.ExecutionException e,String src) {
        String why = null;
        Throwable cause = e.getCause();
        if (cause != null) {
            why = cause.getMessage();
        } else {
            why = e.getMessage();
            cause = e;
        }
        RMIMgr.logger.log(Level.SEVERE,"Error "+src+": " + why);

		com.ibm.eacm.ui.UI.showException(null,cause);
	}
}
