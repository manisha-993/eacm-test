// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * C.   Feature Inventory Group Changes
 * The columns are:
 * Heading                  Description
 * Date/Time of Change      ValFrom of the Entity
 * Change Type              Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor              From the Entity (First 10 characters)
 * FC                       FEATURE.FEATURECODE
 * First Ann Date           FEATURE.FIRSTANNDATE
 * FM                       Derived see below
 * Name                     FEATURE.COMNAME
 * Comments                 FEATURE.COMMENTS CR042605498 added this
 * FM is derived as follows:
 *
 * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
 * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
 * This yields a two character code.
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgFCSet.java,v $
// Revision 1.8  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.7  2005/06/01 18:19:47  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.6  2005/05/06 18:37:40  wendy
// CR042605498 approved
//
// Revision 1.5  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.4  2004/11/09 17:25:18  wendy
// Added support for restored entities.
//
// Revision 1.3  2004/10/26 16:11:38  wendy
// Added check if only inventory group changes
//
// Revision 1.2  2004/10/19 16:49:43  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:47  wendy
// Init for FM Chg Log application
//
class FMChgFCSet extends FMChgSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.8 $";
    private String featureCode="";
    private String firstAnnDate="";
    private String fm="";
    private String name="";
    private String comments="";  // CR042605498

    private static final int FC_COL = 6;
    private static final int ANN_DATE_COL = 10;
    private static final int FM_COL = 2;
    private static final int NAME_COL = 80;
    private static final int CMTS_COL = 80;  // CR042605498

    /********************************************************************************
    * Constructor from and cur items are FEATURE items
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fmg FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgFCSet(Database db, Profile prof, FMChgLogGen fmg, EntityItem fitem, EntityItem citem,
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
        featureCode=null;
        firstAnnDate=null;
        fm=null;
        name=null;
        comments=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        featureCode="";
        firstAnnDate="";
        fm="";
        name="";
        comments="";
    }

    /********************************************************************************
    * Find and set values for FEATURE
    * @param featItem EntityItem
    */
    private void setFeatAttr(EntityItem featItem) throws Exception
    {
        featureCode = FMChgLogGen.getAttributeValue(featItem, "FEATURECODE", ", ", "");
        name = FMChgLogGen.getAttributeValue(featItem, "COMNAME", ", ", "");
        firstAnnDate=FMChgLogGen.getAttributeValue(featItem, "FIRSTANNDATE", ", ", "");
        // derive FM
        fm=deriveFM(featItem);
        comments=FMChgLogGen.getAttributeValue(featItem, "COMMENTS", ", ", "");
    }

    /********************************************************************************
    * Set output for changes in FC structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 19  7   10  6   10  2   80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(featureCode,FC_COL)+COL_DELIMITER+ //FEATURE.FEATURECODE
            formatToWidth(firstAnnDate,ANN_DATE_COL)+COL_DELIMITER+     //FEATURE.FIRSTANNDATE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
//                  formatToWidth(name,NAME_COL)+   //FEATURE.COMNAME
            formatToWidth(name,NAME_COL)+COL_DELIMITER+ //FEATURE.COMNAME
            formatToWidth(comments,CMTS_COL)+   //FEATURE.COMMENTS CR042605498 added this
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
    * Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *
    *    First Ann
    *FC  Date        FM  Name
    *6   10          2   80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("First Ann",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
//          formatToWidth("",NAME_COL)+  //name
            formatToWidth("",NAME_COL)+COL_DELIMITER+  //name
            formatToWidth("",CMTS_COL)+ //comments CR042605498 added this
            FMChgLog.NEWLINE;

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("FC",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("Date",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
//          formatToWidth("Name",NAME_COL)+ //name
            formatToWidth("Name",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Comments",CMTS_COL)+ //comments CR042605498 added this
            FMChgLog.NEWLINE;

        return row1+row2;
    }

    static String getSectionTitle() { return "FEATURE INVENTORY GROUP CHANGES SECTION"; }
    static String getSectionInfo() { return "The following features were added (Add), changed (Change) or deleted (Delete) in this Inventory Group"; }
    static String getNoneFndText() {return "No Feature Inventory Group changes found.";}

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues()
    {
        return (featureCode+firstAnnDate+fm+formatToWidth(name,NAME_COL).trim()
            +formatToWidth(comments,CMTS_COL).trim()); //CR042605498 added this
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues()
    {
        return featureCode+firstAnnDate+fm+name+"|"+comments;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(EntityItem theItem) throws Exception
    {
        // stuff from FEATURE
        setFeatAttr(theItem);
    }
}
