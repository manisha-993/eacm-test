/*
 * Created on Aug 3, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.transform.oim.eacm.util;

import COM.ibm.opicmpdh.middleware.D;

/**
 * @author cstolpe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * Unfortunately there are two Log classes. One for taskmaster and one for the web app.
 * I don't want to have to change the class if I run in web app or taskmaster
 * @author cstolpe
 */
public class Logger implements Log {
	private StringBuffer msgBuff = new StringBuffer();
	private String identifier = "not identified";
	
	/**
	 * Default constructor. Identifier will be "not identified";
	 */
	public Logger(){
	}
	/**
	 * Use AbstractTask.toString() when creating within taskmaster and the request session ID in the web app
	 * 
	 * @param anIdentifier
	 */
	public Logger(String anIdentifier){
		if (anIdentifier != null) {
			identifier = anIdentifier;
		}
	}
	/**
     * Appends the o.toString() to the buffer
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void print(Object o) {
		msgBuff.append(o);
	}
	/**
     * Appends the o.toString() to the buffer
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void print(boolean o) {
		msgBuff.append(o);
	}
	/**
     * Appends the o.toString() to the buffer
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void print(int o) {
		msgBuff.append(o);
	}
	/**
     * Appends the o.toString() to the buffer and then prints it to the log
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void println(Object o) {
		print(o);
		println();
	}
	/**
     * Appends the o.toString() to the buffer and then prints it to the log
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void println(boolean o) {
		print(o);
		println();
	}
	/**
     * Appends the o.toString() to the buffer and then prints it to the log
     *
     * @param o
     * @concurrency $none
     */
    synchronized public void println(int o) {
		print(o);
		println();
	}

	/**
	 * Inserts the identifier into the buffer then Prints the buffer to the log using D.isplay(). Buffer is cleared.
	 */
	synchronized private void println() {
		msgBuff.insert(0, " ");
		msgBuff.insert(0, identifier);
		D.isplay(msgBuff.toString());
		msgBuff.setLength(0);
	}
	/**
	 * Getter method for field
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
     * Setter method for field
     *
     * @concurrency $none
     * @param anIdentifier
     * @return boolean
     */
	synchronized public boolean setIdentifier(String anIdentifier) {
		boolean result = anIdentifier != null;
		if (result) {
			identifier = anIdentifier;
		}
		return result;
	}

}
