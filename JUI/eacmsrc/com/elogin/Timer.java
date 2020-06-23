/**
 * Copyright (c) 2002-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.3  2004/03/02
 * @author Anthony C. Liberto
 *
 * $Log: Timer.java,v $
 * Revision 1.5  2009/05/29 00:28:22  wendy
 * prevent npe if getnow fails
 *
 * Revision 1.4  2009/05/28 13:54:44  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/05/31 18:16:57  wendy
 * prevent NPE
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/04/28 15:04:14  tony
 * MN_18928920
 *
 * Revision 1.1  2004/03/02 21:01:12  tony
 * updated getNow logic.  should now call only once.
 *
 *
 */
package com.elogin;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Timer implements Runnable, EAccessConstants {
    private final int INTERVAL = 15;
    private final int SECOND = 1000;
//    private final int MINUTE = 60 * SECOND;
//    private final int HOUR = 60 * MINUTE;
//    private final int DAY = 24 * HOUR;

    private Date dTime = null;
    private Calendar cTime = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat();
    private ThinBase tBase = null;
    private Thread tTime = null;
    private boolean bUpdate = false;

    /**
     * timer
     * @author Anthony C. Liberto
     */
    protected Timer() {
    }

    /**
     * triggerUpdate
     * @author Anthony C. Liberto
     * /
    public void triggerUpdate() {
        bUpdate = true;
    }*/

    /**
     * setBase
     * @param _tBase
     * @author Anthony C. Liberto
     */
    protected void setBase(ThinBase _tBase) {
        tBase = _tBase;
        if (tBase != null) {
            dTime = updateTime();
            if (dTime!=null){ // prevent NPE when connect fails
            	cTime.setTime(dTime);
            	start();
			}
        }
    }

    /**
     * getNow
     * @return
     * @author Anthony C. Liberto
     */
    protected String getNow() {
        if (bUpdate) {
            bUpdate = false;
            dTime = updateTime();
            if (dTime!=null){ // prevent NPE
        	    cTime.setTime(dTime);
			}
        }
        System.setProperty("mw.now.date", formatDate(FORMATTED_DATE, dTime));
        System.setProperty("mw.now.time", formatDate(FORMATTED_TIME, dTime));
        return formatDate(FORMAT_IN, dTime);
    }

    private Date updateTime() {
    	Date thedate = null;
    	String now = tBase.getNOW();
    	if (now!=null){
    		String strServerTime = now.substring(0, 19);
    		thedate = parseDate(FORMAT_IN, strServerTime);
    	}
        
        return thedate;
    }

    /*
     convenience methods
     */
    /**
     * formatDate
     *
     * @param _pattern
     * @return
     * @author Anthony C. Liberto
     * @concurrency $none
     */
    protected synchronized String formatDate(String _pattern) {
        if (_pattern == null) {
            return null;
        }
        dateFormat.applyPattern(_pattern);
        return dateFormat.format(dTime);
    }

    /**
     * formatDate
     *
     * @param _pattern
     * @param _date
     * @return
     * @author Anthony C. Liberto
     * @concurrency $none
     */
    protected synchronized String formatDate(String _pattern, Date _date) {
        if (_pattern == null || _date == null) {
            return null;
        }
        dateFormat.applyPattern(_pattern);
        return dateFormat.format(_date);
    }

    /**
     * parseDate
     *
     * @param _pattern
     * @param _date
     * @return
     * @author Anthony C. Liberto
     * @concurrency $none
     */
    protected synchronized Date parseDate(final String _pattern, final String _date) {
        Date out = null;
        if (_pattern != null && _date != null) {
            try {
                dateFormat.applyPattern(_pattern);
                out = dateFormat.parse(_date);
            } catch (ParseException _pe) {
                _pe.printStackTrace();
            }
        }
        return out;
    }

    /*
     thread
     */
    private void start() {
        if (tTime == null) {
            tTime = new Thread(this);
            tTime.start();
        }
    }

    /**
     * @see java.lang.Runnable#run()
     * @author Anthony C. Liberto
     */
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (tTime == thisThread) {
            try {
                cTime.add(Calendar.SECOND, INTERVAL); //second
                //				cTime.add(Calendar.MINUTE,INTERVAL);	//minute
                //MN_18928920				dTime = cTime.getTime();
                dTime = getTime(); //MN_18928920
                Thread.sleep(INTERVAL * SECOND); //second
                //				tTime.sleep(INTERVAL * MINUTE);			//minute
            } catch (InterruptedException _ie) {
                _ie.printStackTrace();
            }
        }
    }

    /*
     MN_18928920
     */
    /**
     * getTime
     * @return
     * @author Anthony C. Liberto
     */
    private Date getTime() {
        Date dateOut = cTime.getTime();
        String curDate = formatDate(DATE_ONLY, dTime);
        String newDate = formatDate(DATE_ONLY, dateOut);
        if (!curDate.equals(newDate)) {
        	EAccess.eaccess().updateProcessTime(formatDate(END_OF_DAY, (Date) dateOut.clone()));
        }
        return dateOut;
    }
}
