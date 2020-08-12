// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.util.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * Maintain information about the report getting generated.
 *
 *@author     Wendy Stimpson
 *@created    Mar 17, 2005
 */
// $Log: FMChgRpt.java,v $
// Revision 1.7  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.6  2008/01/22 19:05:03  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2006/05/03 18:05:34  wendy
// Added support if file exceeds blob size, disabled right now
//
// Revision 1.4  2006/05/01 21:31:07  wendy
// Added property for max size to write to the PDH while processing.
// This was done to reduce memory usage.  If a file is too large it will
// be written to the server and then moved to the PDH after lists are freed.
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
abstract class FMChgRpt
{
    static final String FMCHG_RPT= "FM Chg Rpt";
    static final String SDCHG_RPT= "Spt Dev Chg Rpt";
    static final int WRAP=80;
    static final int L19 = 19;
    //private static final int MAX_BLOB_SIZE=9999999;  // blob limit from Tony

    static final String CHANGE_LIMIT_MSG ="*** Only {0} Day{1}of Changes since MTP ***";
    static final String CHANGE_LIMIT_MSG2 ="*** Less than 1 Day of Changes since MTP ***";
    static final String EXCEEDS_24HR = "*** Changes reported for more than one day ***";
    static final String NO_CHANGES_FND = "NO_CHGS_";  // add this to file name if no changes were found for the inv grp

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.7 $";
    private String type;
    private int interval;
    private String dts;
    private String invGrpflag;
    private String brandflag;
    private StringBuffer reportDataSb, reportDataContinuedSb, continuedHeadingSb;
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
        continuedHeadingSb = new StringBuffer();
        logGen = loggen;
    }

    /********************************************************************************
    * Add to the report if valid for this report
    * @param theSet FMChgSet object
    * @return boolean
    */
    static boolean isValidChange(FMChgSet theSet)
    {
        String chgType = theSet.getChgType();
        return (!chgType.equals(FMChgLogGen.NOCHG));
    }

    /********************************************************************************
    * Add to this section to the report
    * @param fmchgSetVct Vector of FMChgSet objects
    * @param sectionTitle String
    * @param sectionInfo String
    * @param colHeader String
    * @param noneFndText String
    */
    void addSection(Vector<FMChgSet> fmchgSetVct, String sectionTitle, String sectionInfo, String colHeader, String noneFndText)
    {
        StringBuffer fcSb = new StringBuffer();  // some of these entities may not have valid changes
        reportDataSb.append(FMChgLog.NEWLINE+sectionTitle+FMChgLog.NEWLINE);
        int debuglvl = FMChgProperties.getDebugTraceLevel();
        for (int p=0; p<fmchgSetVct.size(); p++)  {
            FMChgSet theSet = (FMChgSet)fmchgSetVct.elementAt(p);
            // see if valid chg for this report.. like unrestricted or not!
            if(isValidChange(theSet))  {
                fcSb.append(theSet.getOutput());
                if(debuglvl>=D.EBUG_DETAIL){ //log gets big
                	logGen.trace(D.EBUG_DETAIL,false,"Writing "+theSet.getKey()+" "+theSet.getOutput());
                }else {
                	logGen.trace(D.EBUG_INFO,false,"Writing "+theSet.getKey()+" "+theSet.getDateChg());
                }
            }
        }

        if (fcSb.length()>0)  {
            reportDataSb.append(sectionInfo+FMChgLog.NEWLINE);
            // output column header
            reportDataSb.append(colHeader);
            reportDataSb.append(fcSb.toString());
            chgsFnd = true;
        }  else  {
            reportDataSb.append(noneFndText+FMChgLog.NEWLINE);
        }
    }

    /********************************************************************************
    * Add to this section to the report use this if they ever need to truncate files for exceeding blob attr size
    * @param fmchgSetVct Vector of FMChgSet objects
    * @param sectionTitle String
    * @param sectionInfo String
    * @param colHeader String
    * @param noneFndText String
    *
    void addSectionMaySplit(Vector fmchgSetVct, String sectionTitle, String sectionInfo, String colHeader, String noneFndText)
    {
		boolean sectionHasChgs = false;
        // make sure this report will not exceed the max size of a blob
        String tmp = FMChgLog.NEWLINE+sectionTitle+FMChgLog.NEWLINE;
        StringBuffer curRptSb = reportDataSb;
        if(!isOk(tmp+noneFndText+FMChgLog.NEWLINE)){  // allow for title and nonefound text
			createBuffer2(null);
			curRptSb = reportDataContinuedSb;
		}
       	curRptSb.append(tmp);
        // some of these entities may not have valid changes
        for (int p=0; p<fmchgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)fmchgSetVct.elementAt(p);
            // see if valid chg for this report.. like unrestricted or not!
            if(isValidChange(theSet))
            {
				if (!sectionHasChgs) {  // first change found for this section
					sectionHasChgs = true;
					tmp = sectionInfo+FMChgLog.NEWLINE+colHeader+theSet.getOutput();
				}else {
					tmp = theSet.getOutput();
				}
				// does the string fit into the first part?  this may or may not be the
				// first entry in the section
				if (curRptSb != reportDataContinuedSb && !isOk(tmp)){
					String tmp2 = "";
					if (!tmp.startsWith(sectionInfo)) {
						tmp2 = sectionInfo+FMChgLog.NEWLINE+colHeader;
					}
					createBuffer2(FMChgLog.NEWLINE+sectionTitle+FMChgLog.NEWLINE+tmp2);
					curRptSb = reportDataContinuedSb;
				}
                curRptSb.append(tmp);
            	logGen.trace(D.EBUG_INFO,false,"Writing "+theSet.getKey());
            }
        }

        if (sectionHasChgs) {
            chgsFnd = true;
        }
        else {
            curRptSb.append(noneFndText+FMChgLog.NEWLINE);
        }
    }*/
    /********************************************************************************
    * Create the second buffer
    * @param sectionHeaders String, if not null append this to the second buffer
    * /
	private void createBuffer2(String sectionHeaders) {
		if (reportDataContinuedSb==null) {
			reportDataContinuedSb = new StringBuffer();
			logGen.trace(D.EBUG_INFO,false,"Exceeded blob size.  Creating second report buffer.");
			// add heading
			reportDataContinuedSb.append(continuedHeadingSb.toString());

			if (sectionHeaders!=null) {
				reportDataContinuedSb.append(sectionHeaders); // starting a new section
			}
		}
	}
    /********************************************************************************
    * Check to see if report exceeds max blob size
    * @return boolean
    * /
    private boolean isOk(String tmp) {
		return (reportDataContinuedSb==null) && // already exceeded the size if not null
			(reportDataSb.length()+tmp.length())<=MAX_BLOB_SIZE;
	}

    /********************************************************************************
    * Get report
    * @return String
    */
    String getReport() { return reportDataSb.toString();}

    /********************************************************************************
    * Get continued report
    * @return String may be null
    */
    String getReport2() {
		String tmp=null;
		if (reportDataContinuedSb!=null) {
			tmp = reportDataContinuedSb.toString();
		}
		return tmp;
	}

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

// fixme how to notify user that more than one file exists for an invgrp and interval??
        // build the heading for the continued rpt here, this is the only time the info is available
        continuedHeadingSb.append("IBM CONFIDENTIAL "+getDTS().substring(0,L19)+FMChgLog.NEWLINE);
        continuedHeadingSb.append("Report Type: "+getInterval()+" day ***Continued***"+FMChgLog.NEWLINE);
        continuedHeadingSb.append("Changes made since: "+fromTime.substring(0,L19)+"  "+getWarningMsg(fromTime)+FMChgLog.NEWLINE);
        // if a feature has an inv grp that isn't in the MACHTYPE inv grp list write it out with brand=pseries
        continuedHeadingSb.append("Brand: "+brands+FMChgLog.NEWLINE);
        continuedHeadingSb.append("Inventory Group: "+invGrpDesc+FMChgLog.NEWLINE);
        continuedHeadingSb.append(getReportInfo());
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
    * Release memory for the report itself, it was written to file and will be read from
    * the server later and written to the PDH
    */
    void releaseRptBlob()
    {
        reportDataSb.setLength(0);
        if (reportDataContinuedSb!=null) {
    	    reportDataContinuedSb.setLength(0); // no longer needed but don't null it yet
		}
    }

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
        if (reportDataContinuedSb!=null) {
	        reportDataContinuedSb.setLength(0);
	        reportDataContinuedSb = null;
		}
		continuedHeadingSb.setLength(0);
		continuedHeadingSb = null;
        logGen = null;
    }
}
