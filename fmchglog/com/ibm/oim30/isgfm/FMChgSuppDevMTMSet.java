// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
* B.    Supported Device Matrix Changes
*
* The columns are:
*
* Heading                   Description
* Date/Time of Change       ValFrom of the Relator = DEVSUPPORT
* Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
* Last Editor               From the Entity (First 10 characters)
* MTM                       MODEL.MACHTYPEATR &-& MODEL.MODELATR
* SptDev MTM                SUPPDEVICE.MACHTYPESD &-& SUPPDEVICE.MODELATR
* Announce Date             DEVSUPPORT.ANNDATE
* FM                        Derived  see below
* Name                      SUPPDEVICE.INTERNALNAME
*
* FM is derived as follows:
*
* Use entity SUPPDEVICE to find a matching MAPSUPPDEVICE. The matching is based on INVENTORYGROUP,
* and FMGROUP. Given a matching entity, then this gives FMGROUPCODE. This yields a one character code.
*
*@author     Wendy Stimpson
*@created    Oct 10, 2004
*/
// $Log: FMChgSuppDevMTMSet.java,v $
// Revision 1.7  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.6  2005/06/01 18:19:48  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.5  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.4  2004/10/26 16:11:39  wendy
// Added check if only inventory group changes
//
// Revision 1.3  2004/10/20 00:35:38  wendy
// Added more debug msgs
//
// Revision 1.2  2004/10/19 16:49:44  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:48  wendy
// Init for FM Chg Log application
//
class FMChgSuppDevMTMSet extends FMChgSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.7 $";
    private String mtm="";
    private String sdmtm="";
    private String annDate="";
    private String fm="";
    private String name="";

    private static final int MTM_COL = 8;
    private static final int SDMTM_COL = 8;
    private static final int ANNDATE_COL = 10;
    private static final int FM_COL = 1;
    private static final int NAME_COL = 80;
    private static final String EXTRACTNAME = "EXRPT3SDMDL";

    /********************************************************************************
    * Constructor from and cur items are DEVSUPPORT items
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fmg FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgSuppDevMTMSet(Database db, Profile prof, FMChgLogGen fmg, EntityItem fitem, EntityItem citem,
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
        sdmtm=null;
        annDate=null;
        fm=null;
        name=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        mtm="";
        sdmtm="";
        annDate="";
        fm="";
        name="";
    }

    /********************************************************************************
    * Find and set values for MODEL
    * @param mdlItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setModelAttr(EntityItem mdlItem) throws Exception
    {
        mtm = FMChgLogGen.getAttributeValue(mdlItem, "MACHTYPEATR", ", ", "")+  //MODEL.MACHTYPEATR
            "-"+FMChgLogGen.getAttributeValue(mdlItem, "MODELATR", ", ", "");       //MODEL.MODELATR
    }

    /********************************************************************************
    * Find and set values for DEVSUPPORT
    * @param devItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setDevAttr(EntityItem devItem) throws Exception
    {
        annDate = FMChgLogGen.getAttributeValue(devItem, "ANNDATE", ", ", "");
    }
    /********************************************************************************
    * Find and set values for SUPPDEVICE
    * @param sdItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setSDAttr(EntityItem sdItem) throws Exception
    {
        sdmtm = FMChgLogGen.getAttributeValue(sdItem, "MACHTYPESD", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(sdItem, "MODELATR", ", ", "");
        name = FMChgLogGen.getAttributeValue(sdItem, "INTERNALNAME", ", ", "");
        // derive FM
        fm=deriveSDFM(sdItem);
    }

    /********************************************************************************
    * Set output for changes in MTM structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 8   8       10      1   80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(mtm,8)+COL_DELIMITER+     //MODEL.MACHTYPEATR & - & MODEL.MODELATR
            formatToWidth(sdmtm,8)+COL_DELIMITER+ //SUPPDEVICE.MACHTYPESD & SUPPDEVICE.MODELATR
            formatToWidth(annDate,ANNDATE_COL)+COL_DELIMITER+   //DEVSUPPORT.ANNDATE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+   //SUPPDEVICE.INTERNALNAME
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged()
    {
        EntityItem citem = null;
        EntityItem fitem = null;
        if (getCurItem() !=null) {
            citem = (EntityItem)getCurItem().getDownLink(0); } // get to current SUPPDEVICE
        if (getFromItem() !=null) {
            fitem = (EntityItem)getFromItem().getDownLink(0); } // get to from SUPPDEVICE

        return invGrpChanged(citem, fitem);
    }
    /********************************************************************************
    * Get column headers
    *Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *   SptDev  Announce
    *MTM MTM     Date    FM  Name
    *8   8       10      1   80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("SptDev",SDMTM_COL)+COL_DELIMITER+ //SDMTM
            formatToWidth("Announce",ANNDATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+FMChgLog.NEWLINE; //name

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("MTM",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("MTM",SDMTM_COL)+COL_DELIMITER+ //SDMTM
            formatToWidth("Date",ANNDATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("Name",NAME_COL)+FMChgLog.NEWLINE; //name

        return row1+row2;
    }
    static String getSectionTitle() { return "Supported Device Matrix Changes";}
    static String getSectionInfo() { return "The following Supported Devices were added (Add), changed (Change) or deleted (Remove) from the MTM";}
    static String getNoneFndText() { return "No Supported Device Matrix changes found."; }

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues()
    {
//      return mtm+sdmtm+annDate+fm+formatToWidth(name,NAME_COL).trim();
        return annDate; // only use values from the relator for changes
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues()
    {
        return mtm+sdmtm+annDate+fm+name;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(EntityItem theItem) throws Exception
    {
        // stuff from DEVSUPPORT
        setDevAttr(theItem);
        // stuff from SUPPDEVICE
        getLogGen().trace(D.EBUG_DETAIL,false,"FMChgSuppDevMTMSet.setRowValues() DownLink: "+theItem.getDownLink(0).getKey());
        setSDAttr((EntityItem)theItem.getDownLink(0));
        // stuff from MODEL
        getLogGen().trace(D.EBUG_DETAIL,false,"FMChgSuppDevMTMSet.setRowValues() UpLink: "+theItem.getUpLink(0).getKey());
        setModelAttr((EntityItem)theItem.getUpLink(0));
    }
    /********************************************************************************
    * Get extract name to use for structure check or just a pull for deleted roots
    * @return String
    */
    protected String getExtractName() { return EXTRACTNAME;}
    /********************************************************************************
    * Get root for structure, MTM uses FEATURE, SD uses SUPPDEVICE
    * @return EntityItem
    */
    protected EntityItem getRootItem() { return (EntityItem)getFromItem().getDownLink(0); }

    /********************************************************************************
    * Calculate output for changes in MTM structure
    */
    void calculateOutput() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        calculateMTMOutput();
    }
}
