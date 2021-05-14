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
 * B.   Product Structure (MTM) Changes
 *
 * The columns are:
 *
 * Heading                  Description
 * Date/Time of Change      ValFrom of the Relator = PRODSTRUCT
 * Change Type              Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
 * Last Editor              From the Entity (First 10 characters)
 * MTM                      MODEL.MACHTYPEATR &-& MODEL.MODELATR
 * FC                       FEATURE.FEATURECODE
 * Ann Date Override        PRODSTRUCT.ANNDATE
 * Min Req                  PRODSTRUCT.SYSTEMMIN
 * Max                      PRODSTRUCT.SYSTEMMAX
 * OS Lev (Chg)             PRODSTRUCT.OSLEVEL (If the list is changed, then Change; else blank)
 * Order Type               PRODSTRUCT.ORDERCODE
 * CSU                      PRODSTRUCT.INSTALL CR042605498 added this
 * FM                       (Derived  see below)
 * Name                     FEATURE.COMNAME
 * Comments                 PRODSTRUCT.COMMENTS CR042605498 added this
 * Inventory Group			FEATURE.INVENTORYGROUP MN33235098 added this
Wendy   hi.. COMMENTS is supposed to be added to the 'FEATURE INVENTORY GROUP CHANGES SECTION' and the 'PRODUCT STRUCTURE CHANGES SECTION', correct?  the 'FEATURE INVENTORY GROUP CHANGES SECTION' uses FEATURE.COMMENTS.. does the 'PRODUCT STRUCTURE CHANGES SECTION' use FEATURE.COMMENTS or PRODSTRUCT.COMMENTS?
Rupal   hmmmm
Rupal   I would think one from PRODSTRUCT.SECTION
Rupal   PRODSTRUCT.COMMENTs i menat
Wendy   ok, i will use PRODSTRUCT.COMMENTS in the prodstruct section.. so that means it will be a 'watched' attribute for that section, correct?
Rupal   correct
 *
 * FM is derived as follows:
 *
 * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
 * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
 * This yields a two character code.
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgMTMSet.java,v $
// Revision 1.7  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.6  2016/05/05 11:02:39  stimpsow
// RCQ00390335-WI eFM- increase Changelog max quantity field column width from 4 to 5
//
// Revision 1.5  2007/10/17 11:52:45  wendy
// MN33235098 use TOMACHTYPE instead of FROMMACHTYPE
//
// Revision 1.4  2006/04/07 13:24:41  wendy
// Add setting key
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
class FMChgMTMSet extends FMChgSet
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.7 $";
    private String mtm="";
    private String featureCode="";
    private String annDateOverride="";
    private String minReq="";
    private String max="";
    private String osLevel="";
    private String orderCode="";
    private String fm="";
    private String name="";
    private String comments="";     // CR042605498
    private String csu="";          // CR042605498
    private String fcinvgrp = "";   // MN33235098
    private String thekey="";

    private static final int MTM_COL = 8;
    private static final int FC_COL = 6;
    private static final int ANN_DATE_COL = 10;
    private static final int MIN_COL = 4;
    private static final int MAX_COL = 5;// RCQ00390335-WI 4;
    private static final int OSLVL_COL = 8;
    private static final int ORDERCODE_COL = 10;
    private static final int FM_COL = 2;
    private static final int NAME_COL = 80;
    private static final int CSU_COL = 4;  // CR042605498
    private static final int CMTS_COL = 80;  // CR042605498
    private static final int FCINVGRP_COL = 30;  // MN33235098
    private static final String EXTRACTNAME = "EXRPT3FM";

    /********************************************************************************
    * Constructor from and cur items are PRODSTRUCT items
    * @param fitem EntityItem for fromtime interval
    * @param citem EntityItem for curtime
    * @param time1 String with start timestamp of interval
    * @param time2 String with end timestamp of interval
    */
    FMChgMTMSet(EntityItem fitem, EntityItem citem,
        String time1, String time2) 
    {
        super(fitem, citem, time1, time2);
        if (getCurItem()!=null) {
            // root:relator
            thekey = getCurItem().getKey();
            if (getCurItem().getUpLinkCount()>0){
            	thekey = getCurItem().getUpLink(0).getKey()+":"+thekey;
			}
            if (getCurItem().getDownLinkCount()>0){
            	thekey = thekey+":"+getCurItem().getDownLink(0).getKey();
			}
        }else if (getFromItem()!=null) {
            // root:relator
            thekey = getFromItem().getKey();
            if (getFromItem().getUpLinkCount()>0){
            	thekey = getFromItem().getUpLink(0).getKey()+":"+thekey;
			}
            if (getFromItem().getDownLinkCount()>0){
            	thekey = thekey+":"+getFromItem().getDownLink(0).getKey();
			}
        }
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
        mtm=null;
        featureCode=null;
        annDateOverride=null;
        minReq=null;
        max=null;
        osLevel=null;
        orderCode=null;
        comments=null;
        csu=null;
        fm=null;
        name=null;
        thekey=null;
        fcinvgrp = null; //MN33235098
    }
    /********************************************************************************
    * Restore default values
    */
    protected void reset() // reset for old vs new or add vs change records
    {
        super.reset();
        mtm="";
        featureCode="";
        annDateOverride="";
        minReq="";
        max="";
        osLevel="";
        orderCode="";
        comments="";
        csu="";
        fm="";
        name="";
        fcinvgrp = ""; //MN33235098
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
    * Find and set values for FEATURE
    * @param featItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setFeatAttr(FMChgLogGen logGen,EntityItem featItem) throws Exception
    {
        featureCode = FMChgLogGen.getAttributeValue(featItem, "FEATURECODE", ", ", "");
        fcinvgrp = FMChgLogGen.getAttributeValue(featItem, "INVENTORYGROUP", ", ", "");//MN33235098
        name = FMChgLogGen.getAttributeValue(featItem, "COMNAME", ", ", "");
        // derive FM
        fm=deriveFM(logGen,featItem);
    }
    /********************************************************************************
    * Find and set values for PRODSTRUCT
    * @param prodItem EntityItem
    * @throws java.lang.Exception
    */
    protected void setProdAttr(FMChgLogGen logGen,EntityItem prodItem) throws Exception
    {
        annDateOverride = FMChgLogGen.getAttributeValue(prodItem, "ANNDATE", ", ", "");
        minReq = FMChgLogGen.getAttributeValue(prodItem, "SYSTEMMIN", ", ", "");
        max = FMChgLogGen.getAttributeValue(prodItem, "SYSTEMMAX", ", ", "");
        orderCode = FMChgLogGen.getAttributeValue(prodItem, "ORDERCODE", ", ", "");
        comments = FMChgLogGen.getAttributeValue(prodItem, "COMMENTS", ", ", "");
        csu = FMChgLogGen.getAttributeValue(prodItem, "INSTALL", ", ", "");
        osLevel = "";
        // actual value of oslevel is not displayed, just blank or change
        if (getCurItem()!=null && getFromItem()!=null) // this is looking for a change, not add or del
        {
            String curOsLvl = FMChgLogGen.getAttributeValue(getCurItem(), "OSLEVEL", "", "");
            String fromOsLvl = FMChgLogGen.getAttributeValue(getFromItem(), "OSLEVEL", "", "");
            if (!curOsLvl.equals(fromOsLvl) &&  // was changed
                prodItem==getCurItem())  // and looking for current value
            {
                osLevel = FMChgLogGen.CHANGED;
                logGen.trace(D.EBUG_DETAIL,false,"FMChgMTMSet.setProdAttr() "+getCurItem().getKey()+" fromOsLvl: *"+fromOsLvl+"*");
                logGen.trace(D.EBUG_DETAIL,false,"FMChgMTMSet.setProdAttr() "+getCurItem().getKey()+"  curOsLvl: *"+curOsLvl+"*");
            }
        }
    }

    /********************************************************************************
    * Set output for changes in MTM structure using col widths
    *Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *Col Width: 19  7   10  8   6   10  4   4   8   10  2   80
    */
    protected void setOutput()
    {
        getOutputSb().append(
            getDateChg()+COL_DELIMITER+  // ValFrom of the Entity
            getChangeType()+COL_DELIMITER+  //Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
            getLastEditor()+COL_DELIMITER+  //From the Entity (First 10 characters) not attribute
            formatToWidth(mtm,MTM_COL)+COL_DELIMITER+   //MODEL.MACHTYPEATR & - & MODEL.MODELATR
            formatToWidth(featureCode,FC_COL)+COL_DELIMITER+ //FEATURE.FEATURECODE
            formatToWidth(annDateOverride,ANN_DATE_COL)+COL_DELIMITER+  //PRODSTRUCT.ANNDATE
            formatToWidth(minReq,MIN_COL)+COL_DELIMITER+        //PRODSTRUCT.SYSTEMMIN
            formatToWidth(max,MAX_COL)+COL_DELIMITER+           //PRODSTRUCT.SYSTEMMAX
            formatToWidth(osLevel,OSLVL_COL)+COL_DELIMITER+     //PRODSTRUCT.OSLEVEL    If the list is changed, the Change; else blank
            formatToWidth(orderCode,ORDERCODE_COL)+COL_DELIMITER+   //PRODSTRUCT.ORDERCODE
            formatToWidth(csu,CSU_COL)+COL_DELIMITER+   //PRODSTRUCT.INSTALL CR042605498 added this
            formatToWidth(fm,FM_COL)+COL_DELIMITER+ //derived
            formatToWidth(name,NAME_COL)+COL_DELIMITER+ //FEATURE.COMNAME
            formatToWidth(comments,CMTS_COL)+COL_DELIMITER+   //PRODSTRUCT.COMMENTS CR042605498 added this
            formatToWidth(fcinvgrp,FCINVGRP_COL)+   //FEATURE.INVENTORYGROUP MN33235098 added this
            FMChgLog.NEWLINE);
    }
    /********************************************************************************
    * Check to see if inventory group was changed in this interval
    * @return boolean
    */
    protected boolean invGrpChanged(FMChgLogGen logGen)
    {
        EntityItem citem = null;
        EntityItem fitem = null;
        if (getCurItem() !=null) {
            citem = (EntityItem)getCurItem().getUpLink(0); } // get to current FEATURE
        if (getFromItem() !=null) {
            fitem = (EntityItem)getFromItem().getUpLink(0); } // get to from FEATURE

        return invGrpChanged(logGen,citem, fitem);
    }
    /********************************************************************************
    * Get column headers
    *Wayne Kehrli    it is not stated - but, there should be one blank space between columns
    *
    *        Ann Date    Min Max OS Lev  Order
    *MTM FC  Override    Req     (Chg)   Code    FM  Name
    *8   6   10          4   4   8       10      2   80
    */
    static String getColumnHeader()
    {
        String row1 = getDateTypeEditorHeaderRow1()+
            formatToWidth("",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("Ann Date",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Min",MIN_COL)+COL_DELIMITER+ //Min
            formatToWidth("Max",MAX_COL)+COL_DELIMITER+ //Max
            formatToWidth("OS Lev",OSLVL_COL)+COL_DELIMITER+ //os lev
            formatToWidth("Order",ORDERCODE_COL)+COL_DELIMITER+ //order code
            formatToWidth("",CSU_COL)+COL_DELIMITER+ //csu CR042605498 added this
            formatToWidth("",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("",CMTS_COL)+COL_DELIMITER+ //comments CR042605498 added this
            formatToWidth("Inventory",FCINVGRP_COL)+   //FEATURE.INVENTORYGROUP MN33235098 added this
            FMChgLog.NEWLINE;

        String row2 = getDateTypeEditorHeaderRow2()+
            formatToWidth("MTM",MTM_COL)+COL_DELIMITER+ //MTM
            formatToWidth("FC",FC_COL)+COL_DELIMITER+ //FC
            formatToWidth("Override",ANN_DATE_COL)+COL_DELIMITER+ //Anndate
            formatToWidth("Req",MIN_COL)+COL_DELIMITER+ //Min
            formatToWidth("",MAX_COL)+COL_DELIMITER+ //Max
            formatToWidth("(Chg)",OSLVL_COL)+COL_DELIMITER+ //os lev
            formatToWidth("Type",ORDERCODE_COL)+COL_DELIMITER+ //order code
            formatToWidth("CSU",CSU_COL)+COL_DELIMITER+ //csu CR042605498 added this
            formatToWidth("FM",FM_COL)+COL_DELIMITER+ //FM
            formatToWidth("Name",NAME_COL)+COL_DELIMITER+ //name
            formatToWidth("Comments",CMTS_COL)+COL_DELIMITER+ //comments CR042605498 added this
            formatToWidth("Group",FCINVGRP_COL)+   //FEATURE.INVENTORYGROUP MN33235098 added this
            FMChgLog.NEWLINE;

        return row1+row2;
    }

    static String getSectionTitle() { return "PRODUCT STRUCTURE CHANGES SECTION"; }
    static String getSectionInfo() { return "The following features were added (Add), changed (Change) or deleted (Remove) "+
        "from the product structure for the Machine Type Model shown."+FMChgLog.NEWLINE+
        "Note:  If OS Level is changed (add or remove one or more), then this is indicated by \"Change\""; }
    static String getNoneFndText() { return "No Product Structure changes found."; }

    /********************************************************************************
    * Get row values
    * @return String with all row values concatenated, used for any changes check
    */
    protected String getRowValues(FMChgLogGen logGen)
    {
//      return mtm+featureCode+annDateOverride+minReq+max+osLevel+orderCode+fm+name; //CR042605498 only trigger on PRODSTRUCT chgs
        return annDateOverride+minReq+max+osLevel+orderCode+csu+formatToWidth(comments,CMTS_COL).trim();
    }
    /********************************************************************************
    * Get row values, untruncated!
    * @return String with all row values concatenated, used for debug
    */
    protected String getRowFullValues(FMChgLogGen logGen)
    {
        return annDateOverride+minReq+max+osLevel+orderCode+csu+comments;
    }
    /********************************************************************************
    * Set row values
    * @param theItem EntityItem to use for finding values
    * @throws java.lang.Exception
    */
    protected void setRowValues(FMChgLogGen logGen,EntityItem theItem) throws Exception
    {
        // stuff from PRODSTRUCT
        setProdAttr(logGen,theItem);
        // stuff from FEATURE
        logGen.trace(D.EBUG_DETAIL,false,"FMChgMTMSet.setRowValues() UpLink: "+theItem.getUpLink(0).getKey());
        setFeatAttr(logGen,(EntityItem)theItem.getUpLink(0));
        // stuff from MODEL
        logGen.trace(D.EBUG_DETAIL,false,"FMChgMTMSet.setRowValues() DownLink: "+theItem.getDownLink(0).getKey());
        setModelAttr((EntityItem)theItem.getDownLink(0));
    }

    /********************************************************************************
    * Get extract name to use for structure check or just a pull for deleted roots
    * @return String
    */
    String getExtractName() { return EXTRACTNAME;}

    /********************************************************************************
    * Get root for structure, MTM uses FEATURE, SD uses SUPPDEVICE
    * @return EntityItem
    */
    protected EntityItem getRootItem() { return (EntityItem)getFromItem().getUpLink(0); }

    /********************************************************************************
    * Calculate output for changes in MTM structure
    */
    void calculateOutput(Database dbCurrent,Profile profile,FMChgLogGen logGen) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        calculateMTMOutput(dbCurrent,profile,logGen);
    }
    /********************************************************************************
    * Get key  FEATURE:PRODSTRUCT:MODEL
    * @return String
    */
    String getKey() { return thekey; }
}
