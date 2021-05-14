// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

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
* SptDev MTM                SUPPDEVICE.MACHTYPESD &-& SUPPDEVICE.MODELATR
* Announce Date             SUPPDEVICE.SDANNDATE
* FM                        Derived  see below
* Name                      SUPPDEVICE.INTERNALNAME
* Comment                   SUPPDEVICE.EDITORNOTE
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
// Revision 1.8  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.7  2005/06/01 18:19:48  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.6  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.5  2004/11/09 17:25:18  wendy
// Added support for restored entities.
//
// Revision 1.4  2004/11/08 16:01:24  wendy
// Added missing blank between last 2 columns
//
// Revision 1.3  2004/10/26 16:11:39  wendy
// Added check if only inventory group changes
//
// Revision 1.2  2004/10/19 16:49:44  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:48  wendy
// Init for FM Chg Log application
//
class FMChgSuppDevSet extends FMChgSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.8 $";
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
    * Constructor
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fmg FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    // from and cur items are SUPPDEVICE items
    FMChgSuppDevSet(Database db, Profile prof, FMChgLogGen fmg, EntityItem fitem, EntityItem citem,
        String time1, String time2)
    {
        super(db, prof, fmg, fitem, citem, time1, time2);
    }

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
    * Find and set values for SUPPDEVICE
    * @param sdItem EntityItem
    * @throws java.lang.Exception
    */
    private void setSDAttr(EntityItem sdItem) throws Exception
    {
        mtm = FMChgLogGen.getAttributeValue(sdItem, "MACHTYPESD", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(sdItem, "MODELATR", ", ", "");
        annDate=FMChgLogGen.getAttributeValue(sdItem, "SDANNDATE", ", ", "");
        name = FMChgLogGen.getAttributeValue(sdItem, "INTERNALNAME", ", ", "");
        cmt = FMChgLogGen.getAttributeValue(sdItem, "EDITORNOTE", ", ", "");
        // derive FM
        fm=deriveSDFM(sdItem);
    }

    /********************************************************************************
    * Set output for changes in SUPPDEVICE structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 8   10  1   80  80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(mtm,SDMTM_COL)+COL_DELIMITER+ //SUPPDEVICE.MACHTYPESD & SUPPDEVICE.MODELATR
            formatToWidth(annDate,ANNDATE_COL)+COL_DELIMITER+   //SUPPDEVICE.SDANNDATE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+COL_DELIMITER+ //SUPPDEVICE.INTERNALNAME
            formatToWidth(cmt,CMT_COL)+ //SUPPDEVICE.EDITORNOTE
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged()
    {
        return invGrpChanged(getCurItem(), getFromItem());
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
            formatToWidth("SptDev",SDMTM_COL)+COL_DELIMITER+ //SUPPDEVICE.MACHTYPESD & SUPPDEVICE.MODELATR
            formatToWidth("Announce",ANNDATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("",CMT_COL)+FMChgLog.NEWLINE; //cmt

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("MTM",SDMTM_COL)+COL_DELIMITER+ //SUPPDEVICE.MACHTYPESD & SUPPDEVICE.MODELATR
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
    protected String getRowValues()
    {
        return mtm+annDate+fm+formatToWidth(name,NAME_COL).trim()+
        formatToWidth(cmt,CMT_COL).trim();
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues()
    {
        return mtm+annDate+fm+name+cmt;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(EntityItem theItem) throws Exception
    {
        // stuff from SUPPDEVICE
        setSDAttr(theItem);
    }
}
