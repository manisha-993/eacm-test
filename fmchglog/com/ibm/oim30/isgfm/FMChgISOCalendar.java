// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2005
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.util.*;
/**********************************************************************************
 * Used for date manipulation
 *
 *@author     Wendy Stimpson
 *@created    June 9, 2005
 */
// $Log: FMChgISOCalendar.java,v $
// Revision 1.3  2005/07/14 20:32:17  wendy
// Add cvs revision tag
//
// Revision 1.2  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.1  2005/06/09 13:16:10  wendy
// Made this a separate class to support jtest
//
class FMChgISOCalendar
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.3 $";

    private GregorianCalendar calendar = new GregorianCalendar(); // doesnt' seem like any DateFormat will parse an ISO date
    private String isoDate;
    private String microSecStr="";

    private static final int L11 = 11;
    private static final int L13 = 13;
    private static final int L14 = 14;
    private static final int L16 = 16;
    private static final int L17 = 17;
    private static final int L19 = 19;
    private static final int L20 = 20;
    private static final int SIXTY = 60;
    private static final int THOUSAND = 1000;

    FMChgISOCalendar(String theDate)
    {
        isoDate = theDate;
    }
    void dereference()
    {
        calendar.clear();
        calendar = null;
        isoDate = null;
        microSecStr = null;
    }
    private void init()
    {
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        calendar.clear();

        //YYYY-MM-DD-HH.MM.SS.ssssss
        year = Integer.parseInt(isoDate.substring(0,4));
        month = Integer.parseInt(isoDate.substring(5,7))-1;// months are counted from 0
        day = Integer.parseInt(isoDate.substring(8,10));
        // now parse time, more granularity needed for setting minimum time to go back to
        hour = Integer.parseInt(isoDate.substring(L11,L13));//0;
        minute = Integer.parseInt(isoDate.substring(L14,L16));//0;
        second = Integer.parseInt(isoDate.substring(L17,L19));//0;
//System.err.println("setting calendar with yr "+year+" month: "+month+" day: "+day+" hr "+hour+" min "+minute+" sec "+second);
        microSecStr = isoDate.substring(L20);

        calendar.set(year, month, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND,0); // really want to set MICROSECOND but it doesn't support it
    }

    // go back the entire number of days.. not just to midnight of that day
    String getAdjustedDate(int numDays)
    {
        int dts = 0;
        // build new date string
        StringBuffer datesb = new StringBuffer();
        init();
        // must manipulate date, subtract X days
        calendar.add(Calendar.DATE, -numDays);
        dts = calendar.get(Calendar.YEAR);
        datesb.append(dts);
        datesb.append("-");
        dts = calendar.get(Calendar.MONTH)+1; // months are counted from 0
        if (dts<10) {
            datesb.append("0"); }
        datesb.append(dts+"-");
        dts = calendar.get(Calendar.DAY_OF_MONTH);
        if (dts<10) {
            datesb.append("0"); }
        // the 1 day is the greater of 24hrs or the last time you ran to now
        datesb.append(dts+"-");
        dts = calendar.get(Calendar.HOUR_OF_DAY);
        if (dts<10) {
            datesb.append("0");}
        datesb.append(dts+".");
        dts = calendar.get(Calendar.MINUTE);
        if (dts<10) {
            datesb.append("0");}
        datesb.append(dts+".");
        dts = calendar.get(Calendar.SECOND);
        if (dts<10) {
            datesb.append("0");}
        datesb.append(dts+"."+microSecStr);

//          datesb.append(dts+"-00.00.00.000000"); // hr, min, sec always 0,, start of that day
        return datesb.toString();
    }
    long getTimeInMillis()
    {
        init();
//System.err.println("date "+calendar.getTime());
//System.err.println("date.gettime() "+calendar.getTime().getTime());
//System.err.println("getTimeInMillis() "+calendar.getTimeInMillis());

        //return calendar.getTimeInMillis();  // java 1.4 only!!
        return calendar.getTime().getTime();
    }

    // find number of days between current time and previous time
    int getDiffHours(String prevTime)
    {
        init();
        // prevTime must be before curtime (isodate)
        if (isoDate.compareTo(prevTime)>0)
        {
            // calculate number of days between dates
            long curSec = getTimeInMillis()/THOUSAND;  // milliseconds seem to fluctuate
            FMChgISOCalendar prevDate= new FMChgISOCalendar(prevTime);
            long prevSec = prevDate.getTimeInMillis()/THOUSAND;  // milliseconds seem to fluctuate
            long diffSec = curSec-prevSec;
            long diffHr = 0;
            prevDate.dereference(); // release memory
            if (diffSec<0) // can't really happen because of previous check.. but just in case
            {
                System.err.println("ERROR curTime: "+isoDate+" is before prevTime: "+prevTime+" curSec: "+curSec+" prevSec: "+prevSec);
                return -1;
            }
            // milliseconds seem to fluctuate, so add 5 seconds to account for this
            diffSec +=5;
//  System.err.println("diff in sec "+diffSec);
//  System.err.println("diff in min "+diffSec/SIXTY);
            diffHr = (diffSec/SIXTY)/SIXTY;
//  System.err.println("diff in hr "+diffHr);
            return (int)diffHr;
        }
        return -1;
    }
}
