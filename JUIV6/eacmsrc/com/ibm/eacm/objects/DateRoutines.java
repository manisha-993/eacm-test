//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.eacm.mw.RMIMgr;

/**
 * class used for dates
 * @author Wendy Stimpson
 */
//$Log: DateRoutines.java,v $
//Revision 1.2  2012/10/26 21:47:09  wendy
//update comments
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class DateRoutines implements EACMGlobals
{
    /**
     * @return "yyyy-MM-dd"
     */
    public static String getToday() {
        return RMIMgr.getRmiMgr().getTimer().getToday();
    }


    /**
     * @return "yyyy-MM-dd-23.59.59.999999"
     */
    public static String getEOD() {
        return RMIMgr.getRmiMgr().getTimer().getEOD();
    }

    /**
     * @param _pattern
     * @param _date
     * @return
     */
    public static String formatDate(String _pattern, Date _date) {
        if (_pattern == null || _date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(_pattern);
        return dateFormat.format(_date);
    }

    /**
     * @param _pattern
     * @param _date
     * @return
     */
    public static Date parseDate(String _pattern, String _date) {
        Date out = null;
        if (_pattern != null && _date != null) {
            try {
            	SimpleDateFormat dateFormat = new SimpleDateFormat(_pattern);
                out = dateFormat.parse(_date);
            } catch (ParseException _pe) {
                _pe.printStackTrace();
            }
        }
        return out;
    }

	/**
     * isPastDate
     * @param date
     * @param now
     * @return
     */
    public static boolean isPastDate(String date, String now) {
		if (date != null && now != null) {
			if (date.compareToIgnoreCase(now) <= 0)	{
				return false;
			}
			return true;
		}
		return false;
	}

	/**
     * isFutureDate
     * @param date
     * @param now
     * @return
     */
    public static boolean isFutureDate(String date, String now) {
		return !(date.compareToIgnoreCase(now) >= 0);
	}

}

