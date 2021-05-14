// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * F.   Feature RFA Changes
 *
 * The columns are:
 *
 * Heading              Description
 * Date/Time of Change  ValFrom of the Entity
 * Change Type          Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor          From the Entity (First 10 characters)
 * FC                   FEATURE.FEATURECODE
 * Cntry List           If changes in FEATURE.COUNTRYLIST, then this column equals Change
 * Other Info           TBD
 * Marketing Name       FEATURE.MKTGNAME
 * Price File Name      FEATURE.INVNAME  CR042605498 added this
 *
 *@author     Wendy Stimpson
 *@created    Oct 8, 2004
 */
// $Log: FMChgRFASet.java,v $
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
class FMChgRFASet extends FMChgSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
    private String featureCode="";
    private String other="";
    private String ctrylist="";
    private String mktname="";
    private String invname="";
//  private boolean hasRFAattr = false;

    private static final int FC_COL = 6;
    private static final int CTRYLIST_COL = 6;
    private static final int OTHER_COL = 6;
    private static final int MKTNAME_COL = 80;
    private static final int INVNAME_COL = 28;  // CR042605498, attr has 28 as max len

    /********************************************************************************
    * Constructor from and cur items are FEATURE items
    * @param fm FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgRFASet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        super(fitem, citem, time1, time2);
    }

    /********************************************************************************
    * Used for sorting output
    * @return String
    */
    String getSortKey() { return featureCode;}

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        super.dereference();
        featureCode=null;
        other= null;
        mktname = null;
        invname=null;
        ctrylist = null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        featureCode = "";
        other= "";
        mktname = "";
        invname="";
        ctrylist = "";
    }

    /********************************************************************************
    * Find and set values for FEATURE
    * @param featItem EntityItem
    */
    private void setFeatAttr(FMChgLogGen logGen,EntityItem featItem) throws Exception
    {
        featureCode = FMChgLogGen.getAttributeValue(featItem, "FEATURECODE", ", ", "");
        // actual value of other is not displayed, just blank or change
        other= "";
        mktname = FMChgLogGen.getAttributeValue(featItem, "MKTGNAME", ", ", "");
        invname = FMChgLogGen.getAttributeValue(featItem, "INVNAME", ", ", "");
        ctrylist = "";
        // actual value of ctrylist is not displayed, just blank or change
        if (!getChgType().equals(FMChgLogGen.ADDED)&&
            !getChgType().equals(FMChgLogGen.DELETED)) // this is looking for a change, not add or del
        {
            String curctrylist = FMChgLogGen.getAttributeValue(getCurItem(), "COUNTRYLIST", ",", "");
            String fromctrylist = FMChgLogGen.getAttributeValue(getFromItem(), "COUNTRYLIST", ",", "");
            if (!curctrylist.equals(fromctrylist) &&  // was changed
                featItem==getCurItem())  // and looking for current value
            {
                ctrylist = FMChgLogGen.CHANGED;
                logGen.trace(D.EBUG_DETAIL,false,"FMChgRFASet.setFeatAttr() "+getCurItem().getKey()+" fromcntrylist: *"+fromctrylist+"*");
                logGen.trace(D.EBUG_DETAIL,false,"FMChgRFASet.setFeatAttr() "+getCurItem().getKey()+"  curcntrylist: *"+curctrylist+"*");
            }
        }

        /*if (featureCode.length()>0 || other.length()>0 || mktname.length()>0 || invname.length()>0 || ctrylist.length()>0)
        {
            hasRFAattr = true;
        }*/
    }

    /********************************************************************************
    * Set output for changes in FC structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 19  7   10  6   6   6   80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(featureCode,FC_COL)+COL_DELIMITER+ //FEATURE.FEATURECODE
            formatToWidth(ctrylist,CTRYLIST_COL)+COL_DELIMITER+     //If changes in FEATURE.COUNTRYLIST, then this column equals Change
            formatToWidth(other,OTHER_COL)+COL_DELIMITER+   //TBD
//                  formatToWidth(mktname,MKTNAME_COL)+ //FEATURE.MKTGNAME
            formatToWidth(mktname,MKTNAME_COL)+COL_DELIMITER+   //FEATURE.MKTGNAME
            formatToWidth(invname,INVNAME_COL)+ //FEATURE.INVNAME CR042605498 added this
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged(FMChgLogGen logGen)
    {
        return invGrpChanged(logGen,getCurItem(), getFromItem());
    }
    /********************************************************************************
    * Get column headers
    *Wayne Kehrli   it is not stated - but, there should be one blank space between columns
    *   Cntry   Other   Marketing
    *FC List    Info    Name
    *6  6       6       80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("Cntry",CTRYLIST_COL)+COL_DELIMITER+ //ctry list
            formatToWidth("Other",OTHER_COL)+COL_DELIMITER+ //other
//                  formatToWidth("Marketing",MKTNAME_COL)+  //Marketing name
            formatToWidth("Marketing",MKTNAME_COL)+COL_DELIMITER+  //Marketing name
            formatToWidth("Price File",INVNAME_COL)+ //price file name CR042605498 added this
            FMChgLog.NEWLINE;

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("FC",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("List",CTRYLIST_COL)+COL_DELIMITER+ //ctry list
            formatToWidth("Info",OTHER_COL)+COL_DELIMITER+ //other
//                  formatToWidth("Name",MKTNAME_COL)+ //Marketing name
            formatToWidth("Name",MKTNAME_COL)+COL_DELIMITER+ //Marketing name
            formatToWidth("Name",INVNAME_COL)+  //price file name CR042605498 added this
            FMChgLog.NEWLINE;

        return row1+row2;
    }
    static String getSectionTitle() { return "FEATURE RFA CHANGES SECTION";}
    static String getSectionInfo() { return "The following feature RFA information was added (Add), changed (Change) or "+
        "deleted (Delete) in this Inventory Group"+FMChgLog.NEWLINE+"Note:  If the list of Countries is changed "+
        "(add or remove one or more), then this is indicated by \"Change\""+FMChgLog.NEWLINE+"Note:  If other information "+
        "is changed (add or remove one or more), then this is indicated by \"Change\""+FMChgLog.NEWLINE+
        "\"Other Information\" remains to be defined"; }
    static String getNoneFndText() { return "No Feature RFA changes found."; }

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues(FMChgLogGen logGen)
    {
        return featureCode+other+formatToWidth(mktname,MKTNAME_COL).trim()+
        invname+ //CR042605498 added this
        ctrylist;
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues(FMChgLogGen logGen)
    {
        return featureCode+other+mktname+"|"+invname+"|"+ctrylist;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception
    {
        // stuff from FEATURE
        setFeatAttr(logGen,theItem);
    }

    /********************************************************************************
    * Calculate output for changes in root only, no structure checks
    * ONLY output if RFA attributes exist!
    * MN24140028 was outputting deleted Feature and it didn't have RFA attributes
    BUT this is not in the spec, and how to deal with ctrylist..
    if a new FEATURE is created, COUNTRYLIST will have a value so checking for anything in this row
    'adding, deleting' is a problem unless i ignore countrylist

    * /
    void calculateOutput() throws
            COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
            java.sql.SQLException,
            COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        super.calculateOutput();
        // can't do this without more discussion.. ctrylist will always have a value
        if (!hasRFAattr && (!getChgType().equals(FMChgLogGen.NOCHG))) // MN24140028
        {
logGen.trace(D.EBUG_DETAIL,false,"FMChgRFASet.calculateOutput() "+getCurItem().getKey()+" did NOT have RFA attr set or changed!");

            // withdrawal dates were not found.. so reset and mark as unchanged!
            setChgType(FMChgLogGen.NOCHG);
            outputSb.setLength(0);
        }
    }*/
}
