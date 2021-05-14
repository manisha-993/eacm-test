// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mw;

import java.io.*;
import java.nio.channels.*;
/******************************
 * File locking, introduced in JDK 1.4, allows you to synchronize access to a file as a shared resource.
 * However, the two threads that contend for the same file may be in different JVMs, or one may be a Java
 * thread and the other some native thread in the operating system. The file locks are visible to other
 * operating system processes because Java file locking maps directly to the native operating system
 * locking facility.
 *
 * Once a lock is acquired it is immutable in all its state except isValid(). The lock will initially be valid,
 * but may be rendered invalid by explicit removal of the lock, using release(), or implicitly by closing
 * the channel or exiting the process (terminating the JVM).
 * @author Wendy Stimpson
 */
//$Log: UILock.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
//
public class UILock {
    private String appName;
    private File file;
    private RandomAccessFile raf;
    private FileChannel channel;
    private FileLock lock;
    private Thread shutdownHook;

    /**
     * @param appName2
     */
    protected UILock(String appName2) {
        this.appName = appName2;
    }

    /**
     * Attempt to get a lock on the channel
     * @return
     * @throws IOException
     */
    protected boolean isAppActive()
    {
        try {
        	// Get a file channel for the file
            file = new File(System.getProperty("user.home"), appName + ".tmp");
            raf = new RandomAccessFile(file, "rw");
            channel = raf.getChannel();

            try {
            	//This method does not block. An invocation always returns
            	//immediately, either having acquired a lock on the requested region or
            	//having failed to do so. If it fails to acquire a lock because an
            	//overlapping lock is held by another program then it returns
            	//null. If it fails to acquire a lock for any other reason then
            	//an appropriate exception is thrown.
                lock = channel.tryLock();
            }
            catch (OverlappingFileLockException e) {
            	// File is already locked in this thread or virtual machine
                closeLock();
                return true;
            }

            if (lock == null) {
                closeLock();
                return true;
            }

            shutdownHook = new Thread() {
            	// destroy the lock when the JVM is closing
            	public void run() {
            		closeLock();
            		deleteFile();
            	}
            };
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            return false;
        }catch(IOException e) {
            closeLock();
            return true;
        }
    }

    /***************
     * generate an unrecognizable file name for this UI instance
     * @param sUserName
     * @param location
     * @return
     */
    protected static String getLockFileName(String sUserName, String location){
    	String uiInstance = sUserName+location;
    	uiInstance = "JUI"+uiInstance.hashCode();
    	uiInstance = uiInstance.replace('-', '_');// remove any negative number
    	return uiInstance;
    }

    /*****************************
     * if login fails, must forcibly release this lock
     */
    protected void forceRelease(){
    	if (shutdownHook != null){
    		Runtime.getRuntime().removeShutdownHook(shutdownHook);
    		shutdownHook = null;
    	}
    	closeLock();
		deleteFile();
    }

    /**
     * Release resources
     */
    private void closeLock() {
    	if (lock!= null){
    		try {
    			lock.release();
    		} catch (IOException e) {
    			// e.printStackTrace();
    		}

    		lock=null;
    	}
    	if (channel!= null){
    		try {
    			channel.close();
    		} catch (IOException e) {
    			// e.printStackTrace();
    		}

    		channel = null;
    	}
    	if (raf != null){
    		try {
    			raf.close();
    		} catch (IOException e) {
    			//e.printStackTrace();
    		}

    		raf = null;
    	}
    }

    /**
     * remove the file
     */
    private void deleteFile() {
    	if (file!=null){
    		try {
    			file.delete();
    		}catch(Exception e) {
    			//e.printStackTrace();
    		}
    		file = null;
    	}
    }
}
