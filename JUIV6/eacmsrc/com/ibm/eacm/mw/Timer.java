//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mw;

import java.awt.event.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMGlobals;

/**
 * This class starts a timer when user connects and checks every 15 seconds if profile timestamps need updating
 * it also 'manages' current time. Current time is based on server time, updated every 15 seconds
 * @author Wendy Stimpson
 */
//$Log: Timer.java,v $
//Revision 1.8  2013/07/26 17:36:45  wendy
//adjust for time diff between server and client
//
//Revision 1.7  2013/07/25 19:07:07  wendy
//change logging
//
//Revision 1.6  2013/07/25 18:30:22  wendy
//give timer longer before pulling time from server
//
//Revision 1.5  2013/07/25 18:27:12  wendy
//add warning if cant get server time
//
//Revision 1.4  2013/02/26 21:12:52  wendy
//increase time delay before getting time from server
//
//Revision 1.3  2013/02/15 11:53:52  wendy
//Update display time after computer standby
//
//Revision 1.2  2012/10/26 20:56:15  wendy
//Make EOD match mw EOD seconds
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class Timer implements EACMGlobals, ActionListener {
    private static final int INTERVAL = 15;
    private static final int SECOND = 1000;

    private static final String DATE_ONLY = "yyyyMMdd";
    public static final String PROF_VALON_UPDATE = "PROF_VALON_UPDATE";
    public static final String TIMER_UPDATE = "TIMER_UPDATE";
    public static final String EOD_SECONDS=".59.999999";
    private static final String END_OF_DAY = "yyyy-MM-dd-23.59"+EOD_SECONDS;

	private PropertyChangeSupport changeSupport = null; // notify
    private Date dTime = null;
    private Calendar cTime = Calendar.getInstance();
    private javax.swing.Timer timer = new javax.swing.Timer(INTERVAL * SECOND, this);
    private int localtimeDiff=0;

    /********
     * init this timer with server time
     * @param now
     */
    protected void setNow(String now) {
    	String strServerTime = now.substring(0, 19);
    	dTime = DateRoutines.parseDate(FORMAT_IN, strServerTime);
    	cTime.setTime(dTime); // set calendar to server time
    	Date local = new Date();
    	localtimeDiff = (int)(local.getTime()-cTime.getTimeInMillis());
    	Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"localtimeDiff "+localtimeDiff); 
    	if (!timer.isRunning()) {
    		timer.start();
    	}
    }

    /**
     * user has logged off, stop the timer
     */
    protected void logOff(){
    	if(timer.isRunning()){
    		timer.stop();
    	}
    }

    /**
     * format the current date using the specified pattern
     * @param _pattern
     * @return
     */
    private synchronized String formatDate(String _pattern) {
        return DateRoutines.formatDate(_pattern, dTime);
    }
    /**
     * getNow  in "yyyy-MM-dd-HH.mm.ss" format
     * @return
     */
    public String getNowWithTime() {
        return formatDate(FORMAT_IN);
    }

    /**
     * @return "yyyy-MM-dd"
     */
    public String getToday() {
        return formatDate(FORMATTED_DATE);
    }

    /**
     * @return "yyyy-MM-dd-23.59.59.999999"
     */
    public String getEOD() {
        return formatDate(END_OF_DAY);
    }
    /**
     * @return  "yyyy-MM-dd-HH.mm.ss.SSSSSS"
     */
    public String getISODate() {
        return formatDate(ISO_DATE);
    }

    //public synchronized Date getDate() { return (Date)dTime.clone();}
    private synchronized void setDate(Date d) { dTime=d;}

    /**
     * check for change in yyyyMMdd and update profile valon if needed
     * set Date to Calendar time
     */
    private void checkTime() {
        Date dateOut = cTime.getTime();
        String curDate = DateRoutines.formatDate(DATE_ONLY, dTime);
        String newDate = DateRoutines.formatDate(DATE_ONLY, dateOut);

        String prevEOD = getEOD();

        setDate(dateOut);

        if (!curDate.equals(newDate)) {
        	// update profile valon if needed
        	firePropertyChange(PROF_VALON_UPDATE,prevEOD,this.getEOD());
        }

        firePropertyChange(TIMER_UPDATE, null, dateOut.clone());
    }

    private boolean outputTimerMsg = false; // dont fill log with this msg
    /***************
     * called by the Timer when 15 seconds have elapsed
     */
    public void actionPerformed(ActionEvent e) {
    	//if the computer goes into standby, the timer is off
    	Date now = new Date();
    	int maxtime = timer.getDelay()*8;
    	int difftime = (int)((now.getTime()-localtimeDiff)-cTime.getTimeInMillis());
    	if(difftime>maxtime){ // 15 seconds*8
    		if(!outputTimerMsg){
    			outputTimerMsg = true;
    			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,
    					"Interval exceeded "+(maxtime/1000)+" seconds, reset time from server"); 
    		}
    	   	
    		String strServerTime = RMIMgr.getRmiMgr().getServerTime();
    		if(strServerTime!=null){
    			strServerTime = strServerTime.substring(0, 19);
    			dTime = DateRoutines.parseDate(FORMAT_IN, strServerTime);
            	cTime.setTime(dTime); // set calendar to server time
    		}else{
        		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,
        				"Unable to get time from server"); 
        		// update timer based on system time
        		cTime.add(Calendar.MILLISECOND, (int)difftime); //add milliseconds to the Calendar
    		}
    	}else{
    		cTime.add(Calendar.SECOND, INTERVAL); //add seconds to the Calendar
    	}
    	checkTime();
    }
    //=====================================================================================
    // must notify actions when changing
    public synchronized void addPropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		// add listener
		changeSupport.addPropertyChangeListener(propertyName,listener);
	}
    public synchronized void removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(propertyName,listener);
	}

    private void firePropertyChange(String propertyName,
    		Object oldValue, Object newValue) {
		if (changeSupport == null || oldValue == newValue) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
