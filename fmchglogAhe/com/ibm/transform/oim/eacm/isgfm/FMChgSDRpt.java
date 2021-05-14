// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.text.*;
/**********************************************************************************
 * Used for SD type reports
 *
 *@author     Wendy Stimpson
 *@created    Mar 17, 2005
 */
// $Log: FMChgSDRpt.java,v $
// Revision 1.4  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.3  2006/04/03 22:04:38  wendy
// OIM3.0b datamodel and Supported Device changes
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
class FMChgSDRpt extends FMChgRpt
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";

    /********************************************************************************
    * Constructor
    * @param interval int with number of days for this report
    * @param dts String with date timestamp for report
    * @param invGrpflag String with inventory group flag
    * @param brandflag String with brandflag (first one)
    */
    FMChgSDRpt(FMChgLogGen lg, int interval, String dts, String invGrpflag,
            String brandflag)
    {
        super(lg, SDCHG_RPT, interval, dts, invGrpflag, brandflag);
    }

    /********************************************************************************
    * Get filename, RR portion
    * @return String
    */
    String getRRFileName() { return "SD"; }

    /**************************************************************************************
    * Get warning msg, must be defined in derived classes
    */
    String getWarningMsg(String fromTime)
    {
        String warningMsg="";
        int realNumDays = getLogGen().checkDaysSinceMTP(fromTime, getInterval());
        if (realNumDays!=getInterval())// limited by MTP date
        {
            //CHANGE_LIMIT_MSG
            if (realNumDays==-1) {
                warningMsg= CHANGE_LIMIT_MSG2; }
            else
            {
                MessageFormat mfmsg = new MessageFormat(CHANGE_LIMIT_MSG);
                Object[] args = new String[2];
                args[0]= ""+realNumDays;
                args[1]=" ";
                if (realNumDays!=1) {
                    args[1]="s "; }

                warningMsg= mfmsg.format(args);
            }
        }

        return warningMsg;
    }
    /**************************************************************************************
    * Get report heading info, must be defined in derived classes
    */
    String getReportInfo()
    {
        return (FMChgLogGen.parseIntoLines("The report is for a single Inventory Group and reports on all Supported Devices (SUPPDEVICE) for this Inventory Group",WRAP)+FMChgLog.NEWLINE);
    }
}
