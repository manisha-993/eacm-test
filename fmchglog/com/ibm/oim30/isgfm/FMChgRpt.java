// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2005
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.util.*;
/**********************************************************************************
 * Maintain information about the report getting generated.
 *
 *@author     Wendy Stimpson
 *@created    Mar 17, 2005
 */
// $Log: FMChgRpt.java,v $
// Revision 1.2  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.1  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
abstract class FMChgRpt
{
    static final String FMCHG_RPT= "FM Chg Rpt";
    static final String SDCHG_RPT= "Spt Dev Chg Rpt";
    static final int WRAP=80;
    static final int L19 = 19;

    static final String CHANGE_LIMIT_MSG ="*** Only {0} Day{1}of Changes since MTP ***";
    static final String CHANGE_LIMIT_MSG2 ="*** Less than 1 Day of Changes since MTP ***";
    static final String EXCEEDS_24HR = "*** Changes reported for more than one day ***";
    static final String NO_CHANGES_FND = "NO_CHGS_";  // add this to file name if no changes were found for the inv grp

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";
    private String type;
    private int interval;
    private String dts;
    private String invGrpflag;
    private String brandflag;
    private StringBuffer reportDataSb;
    private boolean chgsFnd=false;  // flag for any changes in this inventory group
    private FMChgLogGen logGen;
    /********************************************************************************
    * get changes found indicator
    * @return boolean
    */
    protected boolean getChgsFnd() { return chgsFnd; }
    /********************************************************************************
    * set changes found indicator
    * @param b boolean
    */
    protected void setChgsFnd(boolean b) { chgsFnd = b; }
    /********************************************************************************
    * get FmChgLogGen reference
    * @return FMChgLogGen
    */
    protected FMChgLogGen getLogGen() { return logGen; }

    /********************************************************************************
    * Constructor
    * @param loggen FMChgLogGen
    * @param types String with report type
    * @param i int with number of days for this report
    * @param dts2 String with date timestamp for report
    * @param igflag String with inventory group flag
    * @param bflag String with brandflag (first one)
    */
    protected FMChgRpt(FMChgLogGen loggen, String types, int i, String dts2, String igflag,
            String bflag)
    {
        type = types;
        interval = i;
        dts = dts2;
        invGrpflag = igflag;
        brandflag = bflag;
        reportDataSb = new StringBuffer();
        logGen = loggen;
    }

    /********************************************************************************
    * Add to the report if valid for this report
    * @param theSet FMChgSet object
    * @return boolean
    */
    abstract boolean isValidChange(FMChgSet theSet);

    /********************************************************************************
    * Add to this section to the report
    * @param fmchgSetVct Vector of FMChgSet objects
    * @param sectionTitle String
    * @param sectionInfo String
    * @param colHeader String
    * @param noneFndText String
    */
    void addSection(Vector fmchgSetVct, String sectionTitle, String sectionInfo, String colHeader, String noneFndText)
    {
        StringBuffer fcSb = new StringBuffer();  // some of these entities may not have valid changes
        reportDataSb.append(FMChgLog.NEWLINE+sectionTitle+FMChgLog.NEWLINE);
        for (int p=0; p<fmchgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)fmchgSetVct.elementAt(p);
            // see if valid chg for this report.. like unrestricted or not!
            if(isValidChange(theSet))
            {
                fcSb.append(theSet.getOutput());
            }
        }

        if (fcSb.length()>0)
        {
            reportDataSb.append(sectionInfo+FMChgLog.NEWLINE);
            // output column header
            reportDataSb.append(colHeader);
            reportDataSb.append(fcSb.toString());
            chgsFnd = true;
        }
        else
        {
            reportDataSb.append(noneFndText+FMChgLog.NEWLINE);
        }
    }

    /********************************************************************************
    * Get report
    * @return String
    */
    String getReport() { return reportDataSb.toString();}
    /********************************************************************************
    * Get report type
    * @return String
    */
    String getType() { return type;}
    /********************************************************************************
    * Get report interval
    * @return int
    */
    int getInterval() { return interval;}
    /********************************************************************************
    * Get report DTS
    * @return String
    */
    String getDTS() { return dts;}
    /********************************************************************************
    * Get inventory group (flag code)
    * @return String
    */
    String getInvGrp() { return invGrpflag;}
    /********************************************************************************
    * Get brand (flag code)
    * @return String
    */
    String getBrand() { return brandflag;}

    /********************************************************************************
    * Get filename prefix, RRDD portion
    * The File name for each report will be: RRDDGG
    * Where:
    * RR is FM if FMCHGTTYPE = 'FM Chg Rpt' or else SD
    * DD is {01 | 07 | 30}
    * GG is a 2 character sequence # for Inventory Group, Wayne says just a count to make it unique
    * Note:  GG may vary from day to day for a given Inventory Group
    * @return String
    */
    String getFileNamePrefix()
    {
        String fileName="";
        if (!chgsFnd)
        {
            fileName = NO_CHANGES_FND;
        }
        fileName=fileName+getRRFileName();
        if (interval<10){
            fileName=fileName+"0";}
        fileName=fileName+interval;

        return fileName;
    }

    /**************************************************************************************
    * Set report header
    *
    * @param fromTime String with dts of actual time 1
    * @param invGrpDesc String inventory group flag description
    * @param brands String with one or more brands this inv grp was found in
    */
    void setHeading(String fromTime, String invGrpDesc, String brands)
    {
        reportDataSb.append("IBM CONFIDENTIAL "+getDTS().substring(0,L19)+FMChgLog.NEWLINE);
        reportDataSb.append("Report Type: "+getInterval()+" day"+FMChgLog.NEWLINE);
        reportDataSb.append("Changes made since: "+fromTime.substring(0,L19)+"  "+getWarningMsg(fromTime)+FMChgLog.NEWLINE);
        // if a feature has an inv grp that isn't in the MACHTYPE inv grp list write it out with brand=pseries
        reportDataSb.append("Brand: "+brands+FMChgLog.NEWLINE);
        reportDataSb.append("Inventory Group: "+invGrpDesc+FMChgLog.NEWLINE);
        reportDataSb.append(getReportInfo());
    }
    /********************************************************************************
    * Get filename, RR portion, must be defined in derived classes
    * The File name for each report will be: RRDDGG
    * Where:
    * RR is FM if FMCHGTTYPE = 'FM Chg Rpt' or else SD
    * ...
    * @return String
    */
    abstract String getRRFileName();
    /**************************************************************************************
    * Get warning msg, must be defined in derived classes
    * @return String
    */
    abstract String getWarningMsg(String fromTime);
    /**************************************************************************************
    * Get report heading info, must be defined in derived classes
    * @return String
    */
    abstract String getReportInfo();

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        type = null;
        dts = null;
        invGrpflag = null;
        brandflag = null;
        reportDataSb.setLength(0);
        reportDataSb = null;
        logGen = null;
    }
}
