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
// Revision 1.4  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.3  2006/04/03 22:04:37  wendy
// OIM3.0b datamodel and Supported Device changes
//
// Revision 1.2  2006/01/25 19:26:02  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:14  wendy
// Init for AHE
//
//
class FMChgFCTransSet extends FMChgSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";
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
    * @param fm FMChgLogGen object driver for file generation
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgFCTransSet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        super(fitem, citem, time1, time2);
    }

    /********************************************************************************
    * Used for sorting output
    * @return String
    */
    String getSortKey() { return fromFC+toFC;}

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
    protected boolean invGrpChanged(FMChgLogGen logGen)
    {
        boolean changed=false;
        String curIG=null;
        String fromIG=null;
        if (getCurItem()!=null)
        {
            try{
                // get FROMMACHTYPE from curentity, if it was deactivated, it will be null
                String mtdesc = FMChgLogGen.getAttributeValue(getCurItem(), "FROMMACHTYPE", "", null);
                if (mtdesc!=null)
                {
                    curIG = logGen.getMTInvGrp(mtdesc); // match on desc
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
                    fromIG = logGen.getMTInvGrp(mtdesc); // match on desc
                }
            }catch(Exception e){
                System.err.println(e.getMessage()); // ignore this
            }
        }
        logGen.trace(D.EBUG_INFO,false,"FMChgFCTransSet.invGrpChanged() curitem: "+(getCurItem()==null?"null":getCurItem().getKey())+
            " fromitem: "+(getFromItem()==null?"null":getFromItem().getKey())+" curIG: "+curIG+" fromIG: "+fromIG);

        if (curIG==null){ // entity was deleted, don't flag that here
            curIG=fromIG;}
        if (fromIG==null){ // entity was added, don't flag that here
            fromIG=curIG;}

        if (curIG!=null) {
            changed = !curIG.equals(fromIG);
        }

        return changed;
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
    protected String getRowValues(FMChgLogGen logGen)
    {
        return annDate+fromMTM+toMTM+fromFC+toFC;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception
    {
        // stuff from FCTRANSACTION
        setFCAttr(theItem);
    }
}
