// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * D.   Global Feature Withdrawal Changes
 *
 * The columns are:
 *
 * Heading                  Description
 * Date/Time of Change      ValFrom of the Entity
 * Change Type              Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor              From the Entity (First 10 characters)
 * FC                       FEATURE.FEATURECODE
 * FM                       Derived
 * Name                     FEATURE.COMNAME
 * Global Withdrawal Announce Date      FEATURE.WITHDRAWANNDATE_T
 * Global Withdrawal Date Effective FEATURE.WITHDRAWDATEEFF_T
 * Withdrawal Comments      FEATURE.WDCOMMENTS
 * FM is derived as follows:
 *
 * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
 * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
 * This yields a two character code.
 *
 * CR042605498
 * ...
 * 7. Global Withdrawal Section
 *  Only include if either the Global Withdrawal Announce Date (WITHDRAWANNDATE_T) or the Global Withdrawal Date Effective (WITHDRAWDATEEFF_T) is changed
 *
 *@author     Wendy Stimpson
 *@created    Oct 8, 2004
 */
// $Log: FMChgGFWSet.java,v $
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
// Revision 1.2  2004/10/19 16:49:44  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:47  wendy
// Init for FM Chg Log application
//
class FMChgGFWSet extends FMChgSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.8 $";
    private String featureCode="";
    private String annDate="";
    private String effDate="";
    private String fm="";
    private String name="";
    private String wdcmt="";
    private static final int FC_COL = 6;
    private static final int FM_COL = 2;
    private static final int NAME_COL = 80;
    private static final int ANN_DATE_COL = 10;
    private static final int EFF_DATE_COL = 10;
    private static final int WDCMT_COL = 80;
    private boolean hasWDdates=false; //MN24140028

    /********************************************************************************
    * Constructor  from and cur items are FEATURE items
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fmg FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgGFWSet(Database db, Profile prof, FMChgLogGen fmg, EntityItem fitem, EntityItem citem,
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
        annDate=null;
        effDate=null;
        fm=null;
        name=null;
        wdcmt=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        featureCode="";
        annDate="";
        effDate="";
        fm="";
        name="";
        wdcmt="";
    }

    /********************************************************************************
    * Find and set values for FEATURE
    * @param featItem EntityItem
    */
    private void setFeatAttr(EntityItem featItem) throws Exception
    {
        featureCode = FMChgLogGen.getAttributeValue(featItem, "FEATURECODE", ", ", "");
        name = FMChgLogGen.getAttributeValue(featItem, "COMNAME", ", ", "");
        annDate=FMChgLogGen.getAttributeValue(featItem, "WITHDRAWANNDATE_T", ", ", "");
        effDate=FMChgLogGen.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", ", ", "");
        wdcmt=FMChgLogGen.getAttributeValue(featItem, "WDCOMMENTS", ", ", "");
        // derive FM
        fm=deriveFM(featItem);
        if (annDate.length()>0 || effDate.length()>0) { //MN24140028
            hasWDdates = true;
        }
    }

    /********************************************************************************
    * Set output for changes in FC structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 19  7   10  6   2 80 10 10  80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(featureCode,FC_COL)+COL_DELIMITER+ //FEATURE.FEATURECODE
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+COL_DELIMITER+ //name
            formatToWidth(annDate,ANN_DATE_COL)+COL_DELIMITER+  //FEATURE.WITHDRAWANNDATE_T
            formatToWidth(effDate,EFF_DATE_COL)+COL_DELIMITER+  //FEATURE.WITHDRAWDATEEFF_T
            formatToWidth(wdcmt,WDCMT_COL)+FMChgLog.NEWLINE); //FEATURE.WDCOMMENTS
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
    *Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *
    *            Global      Global
    *            Withdrawal  Withdrawal
    *            Announce    Effective
    *FC  FM  Name    Date        Date        Withdrawal Comments
    *6   2   80      10          10          80
    */
    static String getColumnHeader()
    {
        String row1 =
            formatToWidth("",DATE_CHG_COL)+COL_DELIMITER+ //chg date
            formatToWidth("",CHG_TYPE_COL)+COL_DELIMITER+ //type
            formatToWidth("",LAST_ED_COL)+COL_DELIMITER+ //editor
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Global",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Global",EFF_DATE_COL)+COL_DELIMITER+ //effdate
            formatToWidth("",WDCMT_COL)+FMChgLog.NEWLINE; //withdraw cmt
        String row2 =
            formatToWidth("",DATE_CHG_COL)+COL_DELIMITER+ //chg date
            formatToWidth("",CHG_TYPE_COL)+COL_DELIMITER+ //type
            formatToWidth("",LAST_ED_COL)+COL_DELIMITER+ //editor
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Withdrawal",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Withdrawal",EFF_DATE_COL)+COL_DELIMITER+ //effdate
            formatToWidth("",WDCMT_COL)+FMChgLog.NEWLINE; //withdraw cmt
        String row3 =getDateTypeEditorHeaderRow1()+
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Announce",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Effective",EFF_DATE_COL)+COL_DELIMITER+ //effdate
            formatToWidth("",WDCMT_COL)+FMChgLog.NEWLINE; //withdraw cmt
        String row4 = getDateTypeEditorHeaderRow2()+
            formatToWidth("FC",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("Name",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Date",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Date",EFF_DATE_COL)+COL_DELIMITER+ //effdate
            formatToWidth("Withdrawal Comments",WDCMT_COL)+FMChgLog.NEWLINE; //withdraw cmt

        return row1+row2+row3+row4;
    }
    static String getSectionTitle() { return "GLOBAL FEATURE WITHDRAWAL CHANGES SECTION";}
    static String getSectionInfo() { return "Feature Withdrawals:  The following features were added (Add), changed (Change) or deleted (Delete)";}
    static String getNoneFndText() { return "No Global Feature Withdrawal changes found.";}

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues()
    {
//      return featureCode+annDate+effDate+fm+name+wdcmt;
        return annDate+effDate; // CR042605498 only show if chgs in wd dates
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

    /********************************************************************************
    * Calculate output for changes in root only, no structure checks
    * ONLY output if withdrawal dates exist!
    * CR042605498 ...
    * 7. Global Withdrawal Section
    *   Only include if either the Global Withdrawal Announce Date (WITHDRAWANNDATE_T) or the
    * Global Withdrawal Date Effective (WITHDRAWDATEEFF_T) is changed
    * MN24140028 was outputting deleted Feature and it didn't have dates
    */
    void calculateOutput() throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        super.calculateOutput();
        if (!hasWDdates && (!getChgType().equals(FMChgLogGen.NOCHG))) // MN24140028
        {
            getLogGen().trace(D.EBUG_DETAIL,false,"FMChgGFWSet.calculateOutput() "+getCurItem().getKey()+
                " did NOT have WD set or changed so it will be ignored!");

            // withdrawal dates were not found.. so reset and mark as unchanged!
            setChgType(FMChgLogGen.NOCHG);
            getOutputSb().setLength(0);
        }
    }
}
