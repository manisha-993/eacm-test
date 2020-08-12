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
* C.    Supported Device Changes
*
* The columns are:
*
* Heading                   Description
* Date/Time of Change       ValFrom of the Entity
* Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
* Last Editor               From the Entity (First 10 characters)
* SptDev MTM                MODELc.MACHTYPEATR &-& MODELc.MODELATR
* Announce Date             MODELc.ANNDATE
* FM                        Derived  see below
* Name                      MODELc.INTERNALNAME
* Comment                   MODELc.COMMENTS
*
* FM is derived as follows:
*
* Use entity SUPPDEVICE to find a matching MAPSUPPDEVICE. The matching is based on INVENTORYGROUP,
* and FMGROUP. Given a matching entity, then this gives FMGROUPCODE. This yields a one character code.
*
*@author     Wendy Stimpson
*@created    Oct 10, 2004
*/
// $Log: FMChgSuppDevSet.java,v $
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
class FMChgSuppDevSet extends FMChgSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
    private String mtm="";
    private String annDate="";
    private String fm="";
    private String name="";
    private String cmt="";

    private static final int SDMTM_COL = 8;
    private static final int ANNDATE_COL = 10;
    private static final int FM_COL = 1;
    private static final int NAME_COL = 80;
    private static final int CMT_COL = 80;

    /********************************************************************************
    * Constructor used to find if this item has changes to output
    * @param fitem EntityItem MODELc for fromtime interval
    * @param citem EntityItem MODELc for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgSuppDevSet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        super(fitem, citem, time1, time2);
    }

    /********************************************************************************
    * Constructor used to duplicate this ChgSet for an inventorygroup (MODELa)
    * @param copy FMChgSuppDevSet object to duplicate
    */
    FMChgSuppDevSet(FMChgSuppDevSet copy)
    {
        super(copy);
        mtm = copy.mtm;  // used for sortkey
    }

    /********************************************************************************
    * Used for sorting output
    * @return String
    */
    String getSortKey() { return mtm;}

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        super.dereference();
        mtm=null;
        annDate=null;
        fm=null;
        name=null;
        cmt=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        mtm="";
        annDate="";
        fm="";
        name="";
        cmt="";
    }

    /********************************************************************************
    * Find and set values for MODELc
    * @param mdlcItem EntityItem
    * @throws java.lang.Exception
    */
    private void setSDAttr(FMChgLogGen logGen,EntityItem mdlcItem) throws Exception
    {
        mtm = FMChgLogGen.getAttributeValue(mdlcItem, "MACHTYPEATR", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(mdlcItem, "MODELATR", ", ", "");
        annDate=FMChgLogGen.getAttributeValue(mdlcItem, "ANNDATE", ", ", "");
        name = //getKey();
        	FMChgLogGen.getAttributeValue(mdlcItem, "INTERNALNAME", ", ", "");
        cmt = FMChgLogGen.getAttributeValue(mdlcItem, "COMMENTS", ", ", "");
        // derive FM
        fm=deriveSDFM(logGen,mdlcItem);
    }

    /********************************************************************************
    * Set output for changes in MODELc structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 8   10  1   80  80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(mtm,SDMTM_COL)+COL_DELIMITER+ //MODELc.MACHTYPESD & MODELc.MODELATR
            formatToWidth(annDate,ANNDATE_COL)+COL_DELIMITER+   //MODELc.SDANNDATE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+COL_DELIMITER+ //MODELc.INTERNALNAME
            formatToWidth(cmt,CMT_COL)+ //MODELc.COMMENTS
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged(FMChgLogGen logGen)
    {
        return false;  // if inv is changed, it will need to go into a different file
    }
    /********************************************************************************
    * Get column headers
    *Wayne Kehrli   it is not stated - but, there should be one blank space between columns
    *
    *SptDev Announce
    *MTM        Date        FM  Name    Comment
    *8      10          1   80      80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("SptDev",SDMTM_COL)+COL_DELIMITER+ //MODELc.MACHTYPESD & MODELc.MODELATR
            formatToWidth("Announce",ANNDATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("",CMT_COL)+FMChgLog.NEWLINE; //cmt

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("MTM",SDMTM_COL)+COL_DELIMITER+ //MODELc.MACHTYPESD & MODELc.MODELATR
            formatToWidth("Date",ANNDATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("Name",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Comment",CMT_COL)+FMChgLog.NEWLINE; //cmt

        return row1+row2;
    }
    static String getSectionTitle() { return "Supported Device Changes"; }
    static String getSectionInfo() { return "The following Supported Devices were added (Add), changed (Change) or deleted (Delete) in this Inventory Group";}
    static String getNoneFndText() { return "No Supported Device changes found.";}

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues(FMChgLogGen logGen)
    {
        return mtm+annDate+fm+formatToWidth(name,NAME_COL).trim()+
        formatToWidth(cmt,CMT_COL).trim();
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues(FMChgLogGen logGen)
    {
        return mtm+annDate+fm+name+cmt;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception
    {
        logGen.trace(D.EBUG_DETAIL,false,"FMChgSuppDevSet.setRowValues() "+theItem.getKey()+
            " on "+theItem.getProfile().getValOn());
        // stuff from MODELc
        setSDAttr(logGen,theItem);
    }
}
