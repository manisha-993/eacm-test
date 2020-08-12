// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2005
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.text.*;
/**********************************************************************************
 * Used for FM type reports
 *
 *@author     Wendy Stimpson
 *@created    Mar 17, 2005
 */
// $Log: FMChgFMRpt.java,v $
// Revision 1.2  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.1  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
class FMChgFMRpt extends FMChgRpt
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";

    /********************************************************************************
    * Constructor
    * @param interval int with number of days for this report
    * @param dts String with date timestamp for report
    * @param invGrpflag String with inventory group flag
    * @param brandflag String with brandflag (first one)
    */
    FMChgFMRpt(FMChgLogGen lg, int interval, String dts, String invGrpflag,
            String brandflag)
    {
        super(lg, FMCHG_RPT, interval, dts, invGrpflag, brandflag);
    }

    /********************************************************************************
    * Add to the report if valid for this report
    * @param theSet FMChgSet object
    */
    boolean isValidChange(FMChgSet theSet)
    {
        String chgType = theSet.getChangeType();
        return (!chgType.equals(FMChgLogGen.NOCHG));
    }

    /********************************************************************************
    * Get filename, RR portion
    * @return String
    */
    String getRRFileName() { return "FM"; }

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
                warningMsg= CHANGE_LIMIT_MSG2;
            }
            else
            {
                MessageFormat mfmsg = new MessageFormat(CHANGE_LIMIT_MSG);
                Object[] args = new String[2];
                args[0]= ""+realNumDays;
                args[1]=" ";
                if (realNumDays!=1) {
                    args[1]="s ";
                }

                warningMsg= mfmsg.format(args);
            }
        }
        else if (getInterval()==1)// allow MTP check to have precedence over the extra hours check for 1 day type report
        {
            //EXCEEDS_24HR
            if (getLogGen().isMoreThan24Hours(fromTime)) {
                warningMsg= EXCEEDS_24HR;
            }
        }

        return warningMsg;
    }
    /**************************************************************************************
    * Get report heading info, must be defined in derived classes
    */
    String getReportInfo()
    {
        return (FMChgLogGen.parseIntoLines("The report is for a single Inventory Group and reports on all Machine Types in "+
                "this Inventory Group. An Inventory Group is the collection of Features (feature codes) "+
                "that have a specific meaning and may not be the same as Features in other Inventory Groups.",WRAP)+FMChgLog.NEWLINE);
    }
}
