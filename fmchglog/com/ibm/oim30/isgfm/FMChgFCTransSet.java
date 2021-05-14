// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
 * E.   Feature Conversion Changes
 *
 * The list of Feature Transactions (FCTRANSACTION) is filtered where Feature Transaction Category (FTCAT) is equal
 * to Feature Conversion (406).
 *
 * The columns are:
 *
 * Heading                  Description
 * Date/Time of Change      ValFrom of the Entity
 * Change Type              Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor              From the Entity (First 10 characters)
 * Announce Date            FCTRANSACTION.ANNDATE
 * From MTM                 FCTRANSACTION.FROMMACHTYPE & - & FCTRANSACTION.FROMMODEL
 * From FC                  FCTRANSACTION.FROMFEATURECODE
 * To MTM                   FCTRANSACTION.TOMACHTYPE & - & FCTRANSACTION.TOMODEL
 * To FC                    FCTRANSACTION.TOFEATURECODE
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgFCTransSet.java,v $
// Revision 1.8  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.7  2005/06/01 18:19:47  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.6  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.5  2004/11/09 17:25:18  wendy
// Added support for restored entities.
//
// Revision 1.4  2004/10/27 23:46:17  wendy
// add code to find invgrp
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
class FMChgFCTransSet extends FMChgSet
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.8 $";
    private String annDate="";
    private String fromMTM="";
    private String toMTM="";
    private String fromFC="";
    private String toFC="";
    private static final int ANN_DATE_COL = 10;
    private static final int FROMMTM_COL = 8;
    private static final int FROMFC_COL = 6;
    private static final int TOMTM_COL = 8;
    private static final int TOFC_COL = 6;
    /********************************************************************************
    * Constructor  from and cur items are FCTRANSACTION items
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fm FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgFCTransSet(Database db, Profile prof, FMChgLogGen fm, EntityItem fitem, EntityItem citem,
        String time1, String time2)
    {
        super(db, prof, fm, fitem, citem, time1, time2);
    }

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        super.dereference();
        annDate=null;
        fromMTM=null;
        toMTM=null;
        fromFC=null;
        toFC=null;
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        annDate="";
        fromMTM="";
        toMTM="";
        fromFC="";
        toFC="";
    }

    /********************************************************************************
    * Find and set values for FCTRANSACTION
    * @param fcItem EntityItem
    */
    private void setFCAttr(EntityItem fcItem) throws Exception
    {
        annDate = FMChgLogGen.getAttributeValue(fcItem, "ANNDATE", ", ", "");
        fromMTM = FMChgLogGen.getAttributeValue(fcItem, "FROMMACHTYPE", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(fcItem, "FROMMODEL", ", ", "");
        toMTM = FMChgLogGen.getAttributeValue(fcItem, "TOMACHTYPE", ", ", "")+"-"+
            FMChgLogGen.getAttributeValue(fcItem, "TOMODEL", ", ", "");
        fromFC = FMChgLogGen.getAttributeValue(fcItem, "FROMFEATURECODE", ", ", "");
        toFC = FMChgLogGen.getAttributeValue(fcItem, "TOFEATURECODE", ", ", "");
    }

    /********************************************************************************
    * Set output for changes in FC structure using col widths
    Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 19  7   10  10  8   6   8   6
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(annDate,ANN_DATE_COL)+COL_DELIMITER+ //FCTRANSACTION.ANNDATE
            formatToWidth(fromMTM,FROMMTM_COL)+COL_DELIMITER+   //FCTRANSACTION.FROMMACHTYPE & - & FCTRANSACTION.FROMMODEL
            formatToWidth(fromFC,FROMFC_COL)+COL_DELIMITER+ //FCTRANSACTION.FROMFEATURECODE
            formatToWidth(toMTM,TOMTM_COL)+COL_DELIMITER+   //FCTRANSACTION.TOMACHTYPE & - & FCTRANSACTION.TOMODEL
            formatToWidth(toFC,TOFC_COL)+   //FCTRANSACTION.TOFEATURECODE
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged()
    {
        String curIG=null;
        String fromIG=null;
        if (getCurItem()!=null)
        {
            try{
                // get FROMMACHTYPE from curentity, if it was deactivated, it will be null
                String mtdesc = FMChgLogGen.getAttributeValue(getCurItem(), "FROMMACHTYPE", "", null);
                if (mtdesc!=null)
                {
                    curIG = getLogGen().getMTInvGrp(mtdesc); // match on desc
                }
            }catch(Exception e){
                System.err.println(e.getMessage()); // ignore this
            }
        }
        if (getFromItem()!=null)
        {
            try{
                // get FROMMACHTYPE from fromentity
                String mtdesc = FMChgLogGen.getAttributeValue(getFromItem(), "FROMMACHTYPE", "", null);
                if (mtdesc!=null)
                {
                    fromIG = getLogGen().getMTInvGrp(mtdesc); // match on desc
                }
            }catch(Exception e){
                System.err.println(e.getMessage()); // ignore this
            }
        }
        getLogGen().trace(D.EBUG_INFO,false,"FMChgFCTransSet.invGrpChanged() curitem: "+(getCurItem()==null?"null":getCurItem().getKey())+
            " fromitem: "+(getFromItem()==null?"null":getFromItem().getKey())+" curIG: "+curIG+" fromIG: "+fromIG);

        if (curIG==null){ // entity was deleted, don't flag that here
            curIG=fromIG;}
        if (fromIG==null){ // entity was added, don't flag that here
            fromIG=curIG;}

        if (curIG==null) {
            return false;  // can't happen, just in case
        }

        return !curIG.equals(fromIG);
    }

    /********************************************************************************
    * Get column headers
    *Wayne Kehrli   it is not stated - but, there should be one blank space between columns
    *Announce   From    From    To  To
    *Date       MTM     FC      MTM FC
    *10         8       6       8   6
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("Announce",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("From",FROMMTM_COL)+COL_DELIMITER+ //From mtm
            formatToWidth("From",FROMFC_COL)+COL_DELIMITER+ //From fc
            formatToWidth("To",TOMTM_COL)+COL_DELIMITER+ //to mtm
            formatToWidth("To",TOFC_COL)+FMChgLog.NEWLINE; //to fc

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("Date",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("MTM",FROMMTM_COL)+COL_DELIMITER+ //From mtm
            formatToWidth("FC",FROMFC_COL)+COL_DELIMITER+ //From fc
            formatToWidth("MTM",TOMTM_COL)+COL_DELIMITER+ //to mtm
            formatToWidth("FC",TOFC_COL)+FMChgLog.NEWLINE; //to fc

        return row1+row2;
    }
    static String getSectionTitle() { return "FEATURE CONVERSIONS CHANGE SECTION";}
    static String getSectionInfo() { return "Feature Conversions  The following features were added (Add), changed (Change) or deleted (Delete)";}
    static String getNoneFndText() { return "No Feature Conversions changes found.";}

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues()
    {
        return annDate+fromMTM+toMTM+fromFC+toFC;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(EntityItem theItem) throws Exception
    {
        // stuff from FCTRANSACTION
        setFCAttr(theItem);
    }
}
