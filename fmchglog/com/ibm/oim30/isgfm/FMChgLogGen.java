// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.util.*;
import java.text.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.hula.*;

/**********************************************************************************
 * All changes to data are 'logged' by e-announce at the attribute, entity and relator level.
 * Each change consists of a date/time stamp and who made the change (userid and role).
 *
 * The change reports consist of a set of text files based on the following:
 *  1 day, 7 day, 30 day
 *  Inventory Group
 *  Brand is informational
 *  Two Reports: Feature Matrix and Supported Devices (only 7 and 30 day)
 *
 * e.g.  Consider three Inventory Groups:  iSeries, pSeries, eServer, then there would be 15 files created every day.
 *
 * A Java Application will be developed that is deployed on the e-announce application server.
 * It will be run daily via the AIX CRON function. The resultant files will be stored in the PDH and moved
 * to the ODS by DMNET for subsequent retrieval by ISG.
 *
 * The current requirement is to run this application once per day just before mid-night
 *  (the server time is currently Eastern Standard Time).
 *
 * The output is a set of text based flat files.
 *
 * This class generates two reports for SG OIM3.0a. The Feature Matrix Change Report
 * consists of five sections. The Supported Device Change Report consists of two sections.
 * A data error log is generated as well as a trace log.
 * The reports are written to the PDH, the logs remain on the server.
 *
 *@author     Wendy Stimpson
 *@created    Oct 5, 2004
 */
// $Log: FMChgLogGen.java,v $
// Revision 1.35  2005/12/05 23:19:22  wendy
// Account for edoc returning relator in root column
//
// Revision 1.34  2005/11/28 13:51:41  wendy
// Added check for existance of ChangeHistoryItems
//
// Revision 1.33  2005/09/15 13:01:51  wendy
// MN25369700 don't use lastactive date for removed relator
//
// Revision 1.32  2005/08/02 14:31:28  wendy
// Return array with null values, not null array for getValueForNullAttr()
//
// Revision 1.31  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.30  2005/06/06 13:58:27  wendy
// Added deref to ISOEntityDate
//
// Revision 1.29  2005/06/01 18:19:47  wendy
// Limited usage of AttributeChangeHistory and made changes for MN24140028
//
// Revision 1.28  2005/05/12 14:31:05  wendy
// Added flushlogs()
//
// Revision 1.27  2005/05/11 19:35:02  wendy
// MN23880559 for slow perf.. split into 1 day and the rest
//
// Revision 1.26  2005/05/10 12:44:01  wendy
// Add check for empty flag attribute
//
// Revision 1.24  2005/05/09 14:34:33  wendy
// Added timing
//
// Revision 1.23  2005/05/06 18:37:40  wendy
// CR042605498 approved
//
// Revision 1.22  2005/05/05 16:22:36  wendy
// Throw exception if meta is not found
//
// Revision 1.21  2005/05/05 14:01:51  wendy
// Setup for CR042605498
//
// Revision 1.20  2005/03/16 18:33:58  wendy
// Use system.exit() to notify shell script of success/failure
//
// Revision 1.19  2005/03/11 14:53:20  wendy
// Chgd method name, protect against missing VE
//
// Revision 1.18  2005/03/11 01:57:18  wendy
// CR0302055218 start 1 day report at last valid run time
//
// Revision 1.17  2005/01/18 16:39:17  wendy
// Allow time in minimum date
//
// Revision 1.16  2004/11/17 20:36:58  wendy
// RowSelectableTable.getRowIndex() changes
//
// Revision 1.15  2004/11/16 13:18:00  wendy
// Throw exception if no inventory groups are found
//
// Revision 1.14  2004/11/09 17:25:18  wendy
// Added support for restored entities.
//
// Revision 1.13  2004/11/08 16:08:44  wendy
// more debug msgs
//
// Revision 1.12  2004/11/03 19:28:42  wendy
// remove Calendar.getTimeInMillis() is JRE 1.4 only
//
// Revision 1.11  2004/11/03 19:01:32  wendy
// Added minimum date support
//
// Revision 1.10  2004/11/03 12:22:25  wendy
// Added hanging onto EntityChangeHistoryGroup
//
// Revision 1.9  2004/11/02 19:22:40  wendy
// containsUpLink() and containsDownLink() were removed from EANEntity, replaced them.
//
// Revision 1.8  2004/10/27 23:41:42  wendy
// Added getMTInvGrp()
//
// Revision 1.7  2004/10/27 20:29:04  wendy
// Hang onto all  changed roots and reuse vector.
//
// Revision 1.6  2004/10/26 16:49:46  wendy
// Removed using EOD timestamp, takes too long to run.
//
// Revision 1.5  2004/10/26 16:14:03  wendy
// more debug msgs
//
// Revision 1.4  2004/10/21 18:18:57  wendy
// Added more debug
//
// Revision 1.3  2004/10/20 00:35:38  wendy
// Added more debug msgs
//
// Revision 1.2  2004/10/19 16:49:44  wendy
// Reorganize for SD
//
// Revision 1.1  2004/10/15 23:38:47  wendy
// Init for FM Chg Log application
//
class FMChgLogGen
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.35 $";
    static final String DELIMITER = "|";
    private Database dbCurrent;
    private Profile profile;
    private String curTime, mtpFromTime, lastRanDate=null;

    /*
    Terminology:

    Deleted - the entity is deleted
    Removed - the relator is deleted

    so, on SupportedDevice & ProductStructure tabs - use Removed
    on the other tabs - use deleted
    */
    static final String ADDED = "Add";
    static final String CHANGED = "Change";
    static final String REMOVED = "Remove";
    static final String DELETED = "Delete";
    static final String NOCHG = "NoChange"; // just an indicator from chgset class
    static final int THIRTY = 30;
    static final int SEVEN = 7;
    static final int ONE = 1;
    static final int LEN256 = 256;
    static final int L13 = 13;
    static final int L16 = 16;
    static final int L19 = 19;
    static final int L26 = 26;

    private Hashtable MTinvGrpTbl = new Hashtable(); // key is machinetype.desc, value is invGrp flagcode
    private Hashtable FMmapTbl = new Hashtable(); // key is MAPFEATURE.INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT flags, value is concatenated FMGROUPCODE and FMSUBGROUPCODE
    private Hashtable SDmapTbl = new Hashtable(); // key is MAPSUPPDEVICE.INVENTORYGROUP and FMGROUP, value is FMGROUPCODE
    private Hashtable invGrpBrTbl = new Hashtable(); // key is invGrp flagcode, value is vector of br desc
    private Hashtable invGrpMasterTbl = new Hashtable(); // key is invGrp flagcode value is desc, it is also the list of all valid invgrp
    private Hashtable brDescTbl = new Hashtable(); // key is br flagcode value is desc
    private FMChgISOCalendar isoDate = null;
    private FMChgLog theFmLog =null;
    private FMChgWritePDH pdhWriter=null;
    private String invDebugStr="";
    /*
BRAND   0010    iSeries
BRAND   0020    pSeries
BRAND   0030    Total Storage
BRAND   0040    xSeries
BRAND   0050    zSeries
    */
    private static final String BRAND_DEFAULT="pSeries";
    private static final String BRANDFLAG_DEFAULT="0020";
    private static final String FTCAT_MATCH = "406"; //(FTCAT) must equal Feature Conversion (406) as a current value
    private static final String SD_ROOTTYPE = "SUPPDEVICE";
    private static final String SD_EXTRACTNAME = "EXRPT3SDMDL";
    private static final String FC_ROOTTYPE = "FCTRANSACTION";
    private static final String FC_EXTRACTNAME = "EXRPT3FCTRANS";
    private static final String FEAT_ROOTTYPE = "FEATURE";
    private static final String FEAT_EXTRACTNAME = "EXRPT3FM";
    private static final int LASTINTERVAL=7;  // use this to remove items from hashtable when no longer needed
    private Stopwatch swTimer = new Stopwatch();

    String getMTInvGrp(String mtdesc)
    {
        return (String)MTinvGrpTbl.get(mtdesc); // match on desc
    }

    private Hashtable entityChgHistTbl = new Hashtable();  // hang onto EntityChangeHistory to reduce PDH calls
    private Hashtable attrChgHistTbl = new Hashtable();  // hang onto AttributeChangeHistory to reduce PDH calls
    EntityChangeHistoryGroup getChgHistGroup(String key) // key is entity.getKey(), value is changehistorygroup
    { return (EntityChangeHistoryGroup)entityChgHistTbl.get(key); }
    void addChgHistGroup(String key, EntityChangeHistoryGroup histGrp) {entityChgHistTbl.put(key, histGrp);}
    AttributeChangeHistoryGroup getAttrChgHistGroup(String key) // key is entity.getKey(), value is attrchangehistorygroup
    { return (AttributeChangeHistoryGroup)attrChgHistTbl.get(key); }
    void addAttrChgHistGroup(String key, AttributeChangeHistoryGroup histGrp) {attrChgHistTbl.put(key, histGrp);}
    boolean isMoreThan24Hours(String fromTime) { return isoDate.getDiffHours(fromTime)>FMChgLog.HOURS_IN_DAY; }
    String getElapsedTime() { return FMChgLog.formatTime(swTimer.getLap()); }

    /**************************************************************************************
    * Constructor
    *
    * Get all BRAND to INVENTORYGROUP mappings from all MACHTYPE
    * get all possible INVENTORYGROUP from FEATURE and SUPPDEVICE (also from MACHTYPE)
    * pull EntityList with setup data for FM mappings (MAPFEATURE and MAPSUPPDEVICE)
    * setup PDH writer to be reused for each generated file
    *
    * @param fmlog FMChgLog driver object
    * @param db Database object
    * @param p Profile object
    * @param curDate String getNow() timestamp
    * @param lastRanDate2 String with last valid date might be null
    * @param chglogWG EntityItem CHGLOGEXTRACT WG to hang FMCHGLOGRPT entities off of
    * @param list EntityList with FEATURE, SUPPDEVICE and MACHTYPE used to get MACHTYPE BR-InvGrp mappings
    */
    FMChgLogGen(FMChgLog fmlog, Database db, Profile p, String curDate, String lastRanDate2,
        EntityItem chglogWG, EntityList list) throws
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException, Exception
    {
        String allInvGrp="";
        String extractName = "EXRPT3WGSETUP";
        EntityList setuplist = null;
        EntityGroup egrp = null;

        dbCurrent = db;
        profile = p;
        theFmLog = fmlog;
        curTime = curDate;
        this.lastRanDate = lastRanDate2;

        // takes too long to run this, something may change so use current timestamp, not eod
//      eodTime = curTime.substring(0,10)+"-23.59.59.999999"; // end of current day is end of time range

        // find out if there is a minimum date, if an IDL happened, don't want to check it
        mtpFromTime = FMChgProperties.getMinimumDate();
        if (mtpFromTime.length()>0)
        {
            //2004-11-03-07.20.57.355331
            // allow more granular time to be specified
            switch(mtpFromTime.length())
            {
            case 10: // just date specified = append -hr.min.sec.ms
                mtpFromTime=mtpFromTime+"-00.00.00.000000";
                break;
            case L13: // date and hour = append .min.sec.ms
                mtpFromTime=mtpFromTime+".00.00.000000";
                break;
            case L16: // date and hour, min = append .sec.ms
                mtpFromTime=mtpFromTime+".00.000000";
                break;
            case L19: // date and hour, min, sec  = append .ms
                mtpFromTime=mtpFromTime+".000000";
                break;
            case L26: // complete date
                break;
            default:
                trace(D.EBUG_ERR,false,"Invalid minimum date format, current date will be used!");
                mtpFromTime=curTime;
                break;
            }
            trace(D.EBUG_INFO,false,"************ WARNING Minimum time was specified! "+mtpFromTime);
        }

        isoDate= new FMChgISOCalendar(curTime);

        // get all current MACHTYPE to find br to invg mappings...
        getBRtoInvGrpMapping(list.getEntityGroup("MACHTYPE")); // for current time
        egrp = list.getEntityGroup("FEATURE");
        if (egrp != null) {
            getInvGrp(egrp); // for current time
        }else {
            trace(D.EBUG_ERR,false,"ERROR: FEATURE EntityGroup was not in setup extract!!");
        }
        egrp = list.getEntityGroup("SUPPDEVICE");
        if (egrp != null) {
            getInvGrp(egrp); // for current time
        }else {
            trace(D.EBUG_ERR,false,"ERROR: SUPPDEVICE EntityGroup was not in setup extract!!");
        }

        list.dereference(); // don't need this info any more, free the memory asap

        // stop if no inventory grps are found, error in "EXRPT3WGCHG" extract
        if (invGrpMasterTbl.size()==0){
            throw new Exception("No Inventory Groups found in EXRPT3WGCHG extract, no files can be generated.");}

        for (Enumeration e = invGrpMasterTbl.keys(); e.hasMoreElements();)
        {
            String invGrp = (String)e.nextElement(); // key is invgrp flag code
            String invGrpDesc = (String)invGrpMasterTbl.get(invGrp);
            allInvGrp=allInvGrp+(" "+invGrpDesc+"["+invGrp+"]");
        }
        trace(D.EBUG_ERR,true,"*****CLSTATUS******* Set of Master InvGrp: "+allInvGrp);

        // build FM mappings
        setuplist = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, extractName),
                new EntityItem[] { chglogWG });

        trace(D.EBUG_INFO,false,"FMChgLogGen() EntityList for "+extractName+": contains the following entities: ");
        trace(D.EBUG_INFO,false,outputList(setuplist));

        setFMMapping(setuplist.getEntityGroup("MAPFEATURE"));
        setFMSDMapping(setuplist.getEntityGroup("MAPSUPPDEVICE"));

        pdhWriter = new FMChgWritePDH(dbCurrent, profile, this, chglogWG);

        setuplist.dereference();
        flushLogs();
    }

    /**************************************************************************************
    * Build SD mapping for later use
    * FM is derived as follows:
    *
    * Use entity SUPPDEVICE to find a matching MAPSUPPDEVICE. The matching is based on
    * INVENTORYGROUP, and FMGROUP. Given a matching entity, then this gives FMGROUPCODE.
    * This yields a one character code.
    * @param eGrp EntityGroup of MAPSUPPDEVICE
    */
    private void setFMSDMapping(EntityGroup eGrp) throws Exception
    {
        for (int mt=0; mt<eGrp.getEntityItemCount(); mt++)
        {
            EntityItem mapItem = eGrp.getEntityItem(mt);
            String invGrpDesc = getAttributeValue(mapItem, "INVENTORYGROUP", "", "");
            String fmGrpDesc = getAttributeValue(mapItem, "FMGROUP", "", "");
            String invGrp = getAttributeFlagValue(mapItem, "INVENTORYGROUP");
            String fmGrp = getAttributeFlagValue(mapItem, "FMGROUP");
            String fmGrpCode = getAttributeValue(mapItem, "FMGROUPCODE", "", "");
            trace(D.EBUG_DETAIL,false,"FMChgLogGen.setFMSDMapping() "+mapItem.getKey()+" key: "+invGrp+fmGrp+" desc: "+
                invGrpDesc+fmGrpDesc+" fmGrpCode: "+fmGrpCode);

            SDmapTbl.put(invGrp+fmGrp,fmGrpCode);
        }
    }
    /**************************************************************************************
    * Get FMSD mapping
    * @param key String with invgrp+fmgrp from SUPPDEVICE
    * @return FM code if found
    */
    String getFMSDmapping(String key)
    {
        String fmsd = (String)SDmapTbl.get(key);
        if (fmsd==null)
        {
//          trace(D.EBUG_WARN,false,"FMChgLogGen.getFMSDMapping() was NULL for "+key);
            fmsd="";
        }
        return fmsd;
    }

    /**************************************************************************************
    * Build FM mapping for later use
    * FM is derived as follows:
    *
    * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
    * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
    * This yields a two character code.
    * @param eGrp EntityGroup of MAPFEATURE
    */
    private void setFMMapping(EntityGroup eGrp) throws Exception
    {
        for (int mt=0; mt<eGrp.getEntityItemCount(); mt++)
        {
            EntityItem mapItem = eGrp.getEntityItem(mt);
            String invGrp = getAttributeFlagValue(mapItem, "INVENTORYGROUP");
            String category = getAttributeFlagValue(mapItem, "HWFCCAT");
            String subCat = getAttributeFlagValue(mapItem, "HWFCSUBCAT");
            String invGrpDesc = getAttributeValue(mapItem, "INVENTORYGROUP", "", "");
            String categoryDesc = getAttributeValue(mapItem, "HWFCCAT", "", "");
            String subCatDesc = getAttributeValue(mapItem, "HWFCSUBCAT", "", "");
            String fmGrp = getAttributeValue(mapItem, "FMGROUPCODE", "", "");
            String fmSubGrp = getAttributeValue(mapItem, "FMSUBGROUPCODE", "", "");
            trace(D.EBUG_DETAIL,false,"FMChgLogGen.setFMMapping() "+mapItem.getKey()+" key: "+
                invGrp+category+subCat+" desc: "+invGrpDesc+categoryDesc+subCatDesc+" fmcode: "+fmGrp+fmSubGrp);

            FMmapTbl.put(invGrp+category+subCat,fmGrp+fmSubGrp);
        }
    }

    /**************************************************************************************
    * Get FM mapping
    * @param key String with invgrp+cat+subcat from FEATURE
    * @return FM code if found
    */
    String getFMmapping(String key)
    {
        String fm = (String)FMmapTbl.get(key);
        if (fm==null)
        {
//          trace(D.EBUG_WARN,false,"FMChgLogGen.getFMMapping() was NULL for "+key);
            fm="";
        }
        return fm;
    }

    /**************************************************************************************
    * get BRAND to INVENTORYGROUP mapping for current time
    * actual structure doesn't seem to matter, only the BR-INVENTORYGROUP mappings
    * @param mtGrp EntityGroup with MACHTYPE for BR-InvGrp mapping
    */
    private void getBRtoInvGrpMapping(EntityGroup mtGrp) throws Exception
    {
        // get all MACHTYPE
        for (int ii=0; ii<mtGrp.getEntityItemCount(); ii++)
        {
            EntityItem mtItem = mtGrp.getEntityItem(ii);
            String mtDesc = null;
            Vector brVct = null;
            // get parent WG for msgs only   WG<-WGMACHINETYPE
            EntityItem wgItem = (EntityItem)mtItem.getUpLink(0).getUpLink(0);
            String brDesc = getAttributeValue(mtItem, "BRAND", ", ", null);
            String brflag = getAttributeFlagValue(mtItem,"BRAND");
            String invGrpDesc = getAttributeValue(mtItem, "INVENTORYGROUP", ", ", "XX");
            String invGrpflag = getAttributeFlagValue(mtItem,"INVENTORYGROUP");
            String wgKey="";
            if (wgItem!=null) {
                wgKey = wgItem.getKey(); }
            if (brDesc == null || invGrpflag==null)
            {
                if (brDesc == null)
                {
                    trace(D.EBUG_WARN,false,"FMChgLogGen.getBRtoInvGrpMapping() "+wgKey+" "+mtItem.getKey()+" has null BRAND");
                    logDataError(wgKey+" "+mtItem.getKey()+" has null BRAND");
                }
                if (invGrpflag == null)
                {
                    trace(D.EBUG_WARN,false,"FMChgLogGen.getBRtoInvGrpMapping() "+wgKey+" "+mtItem.getKey()+" has null INVENTORYGROUP");
                    logDataError(wgKey+" "+mtItem.getKey()+" has null INVENTORYGROUP");
                }
                continue;
            }

            mtDesc = getAttributeValue(mtItem, "MACHTYPEATR", ", ", "XX");
            if (invGrpMasterTbl.get(invGrpflag)==null) {
                trace(D.EBUG_INFO,false,"FMChgLogGen.getBRtoInvGrpMapping() Adding IG: "+invGrpDesc+" ["+invGrpflag+"] to master table");
            }

            invGrpMasterTbl.put(invGrpflag, invGrpDesc); // for easy log generation later and master list
            brDescTbl.put(brflag, brDesc); // for easy log generation later
            // setup machine type (description not flagcode!) to inventory mapping
            // FCTRANSACTION.FROMMACHTYPE is T not U!!
            MTinvGrpTbl.put(mtDesc,invGrpflag);

            // set up inventory to brand mapping
            brVct = (Vector)invGrpBrTbl.get(invGrpflag);
            if (brVct==null)
            {
                brVct = new Vector();
                invGrpBrTbl.put(invGrpflag, brVct); // group by brand under invgrp
            }
            if (!brVct.contains(brflag)) {
                brVct.addElement(brflag); }

            trace(D.EBUG_INFO,false,"FMChgLogGen.getBRtoInvGrpMapping() "+wgKey+" "+mtItem.getKey()+" MACHTYPEATR: "+mtDesc+" IG: "+
                invGrpDesc+"["+invGrpflag+"] for BR: "+brDesc+"["+brflag+"] BRvct: "+brVct);
        }
        /*
        Wendy   can one machinetype have more than one inventory group?  (different MACHTYPE entities)
        Wayne Kehrli    machinetype should be unique
        Wayne Kehrli    one entity
        Wendy   i am trying to set up the mappings.. will i find one entity a machinetype='0017' and invgrp='squad2' and another entity with a machinetype='0017' and invgrp='as400'?
        Wayne Kehrli    there will only be one instance of machtype with machtypeattr = '0017'
        */
    }

    /**************************************************************************************
    * get INVENTORYGROUPs for this entity type for current time
    * @param eGrp EntityGroup
    */
    private void getInvGrp(EntityGroup eGrp) throws Exception
    {
        for (int ii=0; ii<eGrp.getEntityItemCount(); ii++)
        {
            EntityItem eItem = eGrp.getEntityItem(ii);
            String invGrpDesc = getAttributeValue(eItem, "INVENTORYGROUP", ", ", "XX");
            String invGrpflag = getAttributeFlagValue(eItem,"INVENTORYGROUP");
            if (invGrpflag==null)
            {
                trace(D.EBUG_WARN,false,"FMChgLogGen.getInvGrp() "+eItem.getKey()+" has null INVENTORYGROUP");
                logDataError(eItem.getKey()+" has null INVENTORYGROUP");
                continue;
            }

            if (invGrpMasterTbl.get(invGrpflag)==null) {
                trace(D.EBUG_INFO,false,"FMChgLogGen.getInvGrp() "+eGrp.getEntityType()+" Adding IG: "+invGrpDesc+" ["+invGrpflag+"] to master table");
            }
            invGrpMasterTbl.put(invGrpflag, invGrpDesc); // all valid invGrp and for easy log generation later
        }
    }

    /********************************************************************************
    * Write trace msgs for debug
    * D.EBUG_ERR = 0;
    * D.EBUG_WARN = 1;
    * D.EBUG_INFO = 2;
    * D.EBUG_DETAIL = 3;
    * D.EBUG_SPEW = 4;
    *
    * @param level int with debug level, matches COM.ibm.opicmpdh.middleware.D settings
    * @param addToMWlog boolean if true, append to middleware's log too
    * @param msg String msg
    */
    void trace(int level, boolean addToMWlog, String msg)
    {
        // write to log
        if (!addToMWlog && msg.trim().length()>0 && invDebugStr.length()>0)
        {
            if (msg.startsWith(FMChgLog.NEWLINE)){
                msg = FMChgLog.NEWLINE+invDebugStr+" "+msg.substring(1);}
            else {
                msg = invDebugStr+" "+msg; } // prepend type+numdays+invgrp to debug string to chglog's debug output
        }
        theFmLog.trace(level, addToMWlog, msg);
    }
    /********************************************************************************
    * Write data error msgs
    * @param msg String msg
    */
    void logDataError(String msg)  // data errors only
    {
        // write to log
        theFmLog.logDataError(msg);
    }
    /******************************************************************************
    * Flush the logs
    */
    void flushLogs()
    {
        theFmLog.flushLogs();
    }
    /**************************************************************************************
    * Release memory
    */
    void dereference()
    {
        theFmLog = null;
        dbCurrent = null;
        profile = null;
        curTime = null;
        lastRanDate=null;
        mtpFromTime = null;
        if (isoDate!=null)
        {
            isoDate.dereference();
            isoDate = null;
        }

        for (Enumeration e = invGrpBrTbl.elements(); e.hasMoreElements();)
        {
            Vector brVct = (Vector)e.nextElement();
            brVct.clear();
        }
        invGrpBrTbl.clear();
        invGrpBrTbl = null;
        invGrpMasterTbl.clear();
        invGrpMasterTbl = null;
        brDescTbl.clear();
        brDescTbl = null;
        MTinvGrpTbl.clear();
        MTinvGrpTbl = null;
        FMmapTbl.clear();
        FMmapTbl=null;
        SDmapTbl.clear();
        SDmapTbl=null;
        if (pdhWriter!=null) {
            pdhWriter.dereference(); }
        pdhWriter=null;

        entityChgHistTbl.clear();
        entityChgHistTbl = null;
        attrChgHistTbl.clear();
        attrChgHistTbl = null;
        invDebugStr = null;
        swTimer = null;
    }

    /**************************************************************************************
    * Check for calculated time predating the mtp minimum time
    * @param fromTime String with dts of time 1 calculation for interval
    * @param numDays int number of days used for interval
    * @return int with number of days to use, -1 use alternate msg
    */
    int checkDaysSinceMTP(String fromTime, int numDays)
    {
//System.err.println("checkDaysSinceMTP() entered curTime: "+curTime+" fromTime: "+fromTime+" numdays: "+numDays+" mtpTime: "+mtpFromTime);
        if (mtpFromTime.length()>0) // a date was set, does it preceed this fromTime?
        {
            // if mtpTime > curtime, properties file may have tomorrow as first
            // valid day if IDL was today!
            if (mtpFromTime.compareTo(curTime)>0)
            {
                return 0;
            }

            if (fromTime.compareTo(mtpFromTime)<=0) // from time is before mtp time
            {
                // calculate real number of days
                int diffHr = isoDate.getDiffHours(mtpFromTime);
                int diffDay = diffHr/FMChgLog.HOURS_IN_DAY;
                int diffLeft = diffHr%FMChgLog.HOURS_IN_DAY;
                if (diffHr==-1)  // error condition..
                {
                    return 0;
                }

                // this is only used for the msg.. so round things off
                if (diffDay == 0) {
                    return -1; } // less than 1 day
                if (diffLeft>=(FMChgLog.HOURS_IN_DAY/2)) {
                    diffDay+=1; } // more than half day so increment

                return diffDay;
            }
        }
        return numDays;
    }

    /**************************************************************************************
    * Find from time for a particular interval
    * @param numDays int with interval
    * @return String with fromtime to use for this interval
    */
    private String calculateFromTime(int numDays)
    {
        // find daily change and write out
        String fromTime = isoDate.getAdjustedDate(numDays);  // get previous time

        /* how long is a 1 day report?
        chglog calculates what the timetamp is for 24hrs earlier than the current time
        if the last ran timestamp is earlier than this, the last ran timestamp is used
        UNLESS there was a MTP
        it will never move to a timestamp that is earlier than the MTP

        so the answer is.. it may be more or less than 24 hours for a 1 day report
        */
        if (numDays==1)
        {
            if (lastRanDate !=null && lastRanDate.compareTo(fromTime)<0)  // lastRanDate is before the calculated time
            {
                int diffHr = isoDate.getDiffHours(lastRanDate);
                long diffDay = diffHr/FMChgLog.HOURS_IN_DAY;
                fromTime = lastRanDate;
                trace(D.EBUG_INFO,false,"******** Time since last valid run hrs: "+diffHr+" days: "+diffDay+
                    " using lastrandate "+lastRanDate);
            }
        }

        if (mtpFromTime.length()>0) // a date was set, does it preceed this fromTime?
        {
            // if mtpTime > curtime, then use 0 days.. properties file may have tomorrow as first
            // valid day if IDL was today!
            if (mtpFromTime.compareTo(curTime)>0)
            {
                trace(D.EBUG_ERR,false,"MTP Minimum time: "+mtpFromTime+" exceeds curTime: "+curTime+", current time will be used");
                fromTime = curTime;
            }
            else if (fromTime.compareTo(mtpFromTime)<=0)
            {
                fromTime = mtpFromTime;
            }
        }
        return fromTime;
    }

    /**************************************************************************************
    * Main entry point to kick off builds of Feature Matrix logs, 30, 7 and 1 day intervals
    *
    * Start at 1 day interval to get these files completed and sent asap,
    * call eDoc to find all changed FEATURE roots and changed children
    * call eDoc to find all changed FCTRANSACTION roots
    * buildFMLog pulls EntityList for all affected roots at fromTime, it then groups all
    * FEATURE by InventoryGrp and pairs the entity at curTime with its match at fromTime
    * buildFMLog groups all FCTRANSACTION by InventoryGrp and pairs the entity at curTime with its
    * match at fromTime
    * It is possible that the changes are not in the set of attributes reported in each section.
    * A file is generated for each InventoryGroup found from setup. Each section is handled and
    * then written to the PDH
    *
    * Changes are found for 30 day interval, but only those that were not in the 1 day interval
    * are pulled in the curtime extracts.  Changed entityitems (curtime) are accumulated in hashtables.
    *
    * Changes are found for 7 day interval, but pull not needed at current time. Those entityitems
    * will be in the hashtable.
    */
    void buildFMLogs() throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException, Exception
    {
        Hashtable delRelatorTbl = new Hashtable();  // keep track of removed relators
        Hashtable featTbl = new Hashtable(); // contains entityitems
        Hashtable fctransTbl = new Hashtable(); // contains entityitems
        String fromTime = "";
        Vector vctArray[] = null;
        Vector root1dayIdVct = null;
        EntityList feat1dayList = null;
        Vector vctFcArray[] = null;
        Vector fcroot1dayIdVct = null;
        EntityList fctrans1dayList = null;
        Vector tmp = null;
        EntityList featList = null;
        EntityList fctransList = null;

        // build 1 day log, so it gets moved to the ODS first.. then do 30 and 7 day
        // hang onto entitylist from 1 day and don't pull those entities again

        // the report type is one day BUT the time span may be more if app didn't run one or more days
        //CR0302055218 run from last valid run
        int numDays = ONE;
        swTimer.start();
        trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
        // find daily change and write out
        fromTime = calculateFromTime(numDays);

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of FM logs for "+numDays+" days  curTime "+
            curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

        trace(D.EBUG_INFO,false,"buildFMLogs()***** Find changed FEATURE-PRODSTRUCT-MODEL ***************");
        vctArray = getAffectedRootsAndChildren(FEAT_EXTRACTNAME,FEAT_ROOTTYPE,fromTime, delRelatorTbl);
        root1dayIdVct = vctArray[0]; // roots that changed, hang onto this so dont have to pull again for bigger interval

        // pull 1 day affected feature entities, index 0 is roots (ids) of structure with changes
        feat1dayList = pullEntityItems(root1dayIdVct, profile,FEAT_EXTRACTNAME,FEAT_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLogs() feat1dayList: "+FMChgLog.NEWLINE+outputList(feat1dayList));

        // copy entityitems into a hashtable, 30 day will be added to this
        if (feat1dayList != null)
        {
            EntityGroup eg = feat1dayList.getParentEntityGroup();
            for (int i=0; i <eg.getEntityItemCount(); i++)
            { featTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));}
        }

        trace(D.EBUG_INFO,false,"buildFMLogs()***** Find changed FCTRANSACTION ***************");
        // get feature transaction change info
        vctFcArray = getAffectedRootsAndChildren(FC_EXTRACTNAME,FC_ROOTTYPE,fromTime, null);
        fcroot1dayIdVct = vctFcArray[0]; // fcroots that changed, hang onto this so dont have to pull again for bigger interval
        // pull 1 day affected fctransaction entities
        fctrans1dayList = pullEntityItems(fcroot1dayIdVct, profile,FC_EXTRACTNAME, FC_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLogs() fctrans1dayList: "+FMChgLog.NEWLINE+outputList(fctrans1dayList));
        flushLogs();

        // copy entityitems into a hashtable, 30 day will be added to this
        if (fctrans1dayList!=null)
        {
            EntityGroup eg = fctrans1dayList.getParentEntityGroup();
            for (int i=0; i <eg.getEntityItemCount(); i++)
            { fctransTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));}
        }

        buildFMLog(vctArray, vctFcArray,featTbl,fctransTbl,numDays,fromTime,delRelatorTbl);
        // a separate marker is needed for 1 day versus all the rest
        // these could be ready and moved before the others are complete
        theFmLog.updateLastRanDate(""+numDays);
        flushLogs();

        // release memory
        //vctArray[0].clear(); // roots that changed cant clear here.. needed for 30 day chk
        vctArray[1].clear(); // child or root that changed
        //vctFcArray[0].clear();
        vctFcArray[1].clear();

        for (Enumeration e = delRelatorTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            vct.clear();
        }
        delRelatorTbl.clear();

        // find out what changed over 30 days
        numDays = THIRTY;
        trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
        fromTime = calculateFromTime(numDays);

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of FM logs for "+numDays+" days  curTime "+
            curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");
        trace(D.EBUG_INFO,false,"buildFMLogs()***** Find changed FEATURE-PRODSTRUCT-MODEL ***************");

        vctArray = getAffectedRootsAndChildren(FEAT_EXTRACTNAME,FEAT_ROOTTYPE, fromTime,delRelatorTbl);
        tmp = (Vector)vctArray[0].clone(); // copy it, orig is needed to track what really chgd
        for (int i=0; i<root1dayIdVct.size(); i++)
        {
            // remove ids that were in the 1 day list, don't need to get entity items for these again
            tmp.remove(root1dayIdVct.elementAt(i));
        }

        // pull affected entities that weren't pulled at the 1 day change
        featList = pullEntityItems(tmp, profile,FEAT_EXTRACTNAME,FEAT_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLogs() 30 minus 1 day featList: "+FMChgLog.NEWLINE+outputList(featList));
        if (featList != null)
        {
            EntityGroup eg = featList.getParentEntityGroup();
            for (int i=0; i <eg.getEntityItemCount(); i++)
            { featTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));}
        }
        tmp.clear();
        // get feature transaction change info
        vctFcArray = getAffectedRootsAndChildren(FC_EXTRACTNAME,FC_ROOTTYPE,fromTime, null);
        tmp = (Vector)vctFcArray[0].clone(); // copy it, orig is needed to track what really chgd
        for (int i=0; i<fcroot1dayIdVct.size(); i++)
        {
            // remove ids that were in the 1 day list, don't need to get entity items for these again
            tmp.remove(fcroot1dayIdVct.elementAt(i));
        }

        fctransList = pullEntityItems(tmp, profile,FC_EXTRACTNAME, FC_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLogs() 30 minus 1 day fctransList: "+FMChgLog.NEWLINE+outputList(fctransList));
        if (fctransList != null)
        {
            EntityGroup eg = fctransList.getParentEntityGroup();
            for (int i=0; i <eg.getEntityItemCount(); i++)
            { fctransTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));}
        }
        tmp.clear();
        root1dayIdVct.clear(); //  feature entityitems that changed in 1 days not needed anymore
        fcroot1dayIdVct.clear();// fctransaction entityitems that changed in 1 days
        flushLogs();

        // find 30 day changes and write out
        // hashtables now have all changes for 30 days, the lists will have subsets because
        // pulled some for 1 day and rest for 30 day in an attempt to write out 1 day files
        // first and then get the other slower info
        buildFMLog(vctArray, vctFcArray,featTbl,fctransTbl,numDays,fromTime,delRelatorTbl);
        vctArray[0].clear(); // root that changed structure
        vctArray[1].clear(); // child or root that changed
        vctFcArray[0].clear(); // root that changed structure
        vctFcArray[1].clear(); // child or root that changed
        for (Enumeration e = delRelatorTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            vct.clear();
        }
        delRelatorTbl.clear();

        numDays = SEVEN;
        trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
        // find 7 day changes and write out
        fromTime = calculateFromTime(numDays);

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of FM logs for "+numDays+" days  curTime "+curTime+
            " fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

        trace(D.EBUG_INFO,false,"buildFMLogs()***** Find changed FEATURE-PRODSTRUCT-MODEL ***************");
        vctArray = getAffectedRootsAndChildren(FEAT_EXTRACTNAME,FEAT_ROOTTYPE,fromTime,delRelatorTbl);
        vctFcArray = getAffectedRootsAndChildren(FC_EXTRACTNAME,FC_ROOTTYPE,fromTime, null);
        buildFMLog(vctArray, vctFcArray,featTbl,fctransTbl,numDays,fromTime,delRelatorTbl);
        vctArray[0].clear(); // root that changed structure
        vctArray[1].clear(); // child or root that changed
        vctFcArray[0].clear(); // root that changed structure
        vctFcArray[1].clear(); // child or root that changed
        for (Enumeration e = delRelatorTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            vct.clear();
        }
        delRelatorTbl.clear();

        // release all
        delRelatorTbl = null;

        if (featList !=null){
            featList.dereference(); // feature entityitems that changed in 30 days but NOT in 1 day
        }
        if (fctransList !=null){
            fctransList.dereference(); // fctransaction entityitems that changed in 30 days but NOT in 1 day
        }
        if (feat1dayList!=null){
            feat1dayList.dereference(); // feature entityitems that changed in 1 day
        }
        if (fctrans1dayList!=null) {
            fctrans1dayList.dereference(); // fctransaction entityitems that changed in 1 day
        }
        featTbl.clear();  // all feature entityitems that changed in 30 days (including 1 day)
        fctransTbl.clear(); // all fctransaction entityitems that changed in 30 days (including 1 day)
    }

    /**************************************************************************************
    * Build logs for a particular interval
    * @param vctArray Vector[] index[0]with changed root entity ids, index[1] entitys that had changes, child or root
    * @param vctFcArray Vector[] index[0]with changed fc root entity ids, index[1] entitys that had changes, child or root
    * @param featTbl Hashtable with FEATURE entityitems
    * @param fctransTbl Hashtable with FCTRANSACTION entityitems
    * @param numDays int with interval
    * @param fromTime String with dts of time 1 based on minimum date
    * @param delPSRelatorTbl Hashtable with deleted PRODSTRUCT relators
    */
    private void buildFMLog(Vector vctArray[], Vector vctFcArray[], Hashtable featTbl,Hashtable fctransTbl,
            int numDays, String fromTime, Hashtable delPSRelatorTbl)
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException, Exception
    {
        Vector rootChgVct=vctArray[0];
        Vector featChildChgVct=vctArray[1];
        Vector fcRootChgVct = null;
        Vector fcChildChgVct= null;
        EntityList fromFCList = null;
        Hashtable fcByInvGrpTbl = null;
        Vector fcVct = null;

        // get entities at from time
        Profile fromProfile = profile.getNewInstance(dbCurrent);
        EntityList fromFeatList = null;
        Hashtable featByInvGrpTbl = null;
        fromProfile.setValOnEffOn(fromTime, fromTime);
        fromFeatList = pullEntityItems(rootChgVct, fromProfile,FEAT_EXTRACTNAME, FEAT_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLog() fromTime: "+fromTime+" fromFeatList: "+FMChgLog.NEWLINE+outputList(fromFeatList));

        trace(D.EBUG_INFO,false,"buildFMLog()***** Group changed FEATURE structure by InventoryGroup ***************");
        // all need output based on inv grp do here once
        featByInvGrpTbl = getFeaturesByInvGrp(featTbl, fromFeatList, rootChgVct);

        // get feature transaction change info
        fcRootChgVct = vctFcArray[0];
        fcChildChgVct= vctFcArray[1];
        fromFCList = pullEntityItems(fcRootChgVct, fromProfile,FC_EXTRACTNAME, FC_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildFMLog() fromFCList: "+FMChgLog.NEWLINE+outputList(fromFCList));

        trace(D.EBUG_INFO,false,"buildFMLog()***** Group changed FCTRANSACTION by InventoryGroup ***************");
        fcByInvGrpTbl = getFCByInvGrp(fctransTbl, fromFCList, fcRootChgVct);

        // complete one inv grp at a time, write it out
        for (Enumeration e = invGrpMasterTbl.keys(); e.hasMoreElements();)
        {
            FMChgRpt chgRpt = null;
            Vector featVct = null;
            Vector chgRootVct = null;
            String invGrp = (String)e.nextElement(); // key is invgrp flag code
            // create the report object
            String brandflag = BRANDFLAG_DEFAULT;
            Vector brVct = (Vector)invGrpBrTbl.get(invGrp);
            if (brVct!=null)
            {
                /*
                Wendy   how to handle a file for an inventory group that has more than one brand?  FMCHGLOGRPT.BRAND is a U flag
                Wendy   right now i generate one file, just says Brand= brandx, brandy
                Wayne Kehrli    oh - that is fine
                Wayne Kehrli    one report
                Wayne Kehrli    stored by the first brand
                Wayne Kehrli    but the contents of the report can list multiple
                */
                //brand needs to be first flag code
                brandflag = brVct.elementAt(0).toString();
            }
            invDebugStr="FM"+numDays+invGrpMasterTbl.get(invGrp);
            chgRpt = new FMChgFMRpt(this, numDays, curTime, invGrp, brandflag); // used for all changes, restricted and unrestricted

            trace(D.EBUG_ERR,true,FMChgLog.NEWLINE+"***CLSTATUS**** build FM log for InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+invGrp+"] ***************");

            chgRpt.setHeading(fromTime,(String)invGrpMasterTbl.get(invGrp),getBrandForInvGrp(invGrp));

            // one Vector for each FEATURE.invGrp
            featVct = (Vector)featByInvGrpTbl.get(invGrp);
            trace(D.EBUG_INFO,false,"buildFMLog() found "+((featVct!=null)?""+featVct.size():"0")+" changed "+invGrpMasterTbl.get(invGrp)+" FEATURE and structure");
            getMTMChgs(featVct,featChildChgVct,chgRpt, delPSRelatorTbl, fromTime);

            chgRootVct = new Vector(); // find all roots that had changes once and use over
            if (featVct!=null)
            {
                for (int i=0; i<featVct.size(); i++)
                {
                    EntitySet theSet = (EntitySet)featVct.elementAt(i);
                    // use cur entity as basis
                    EntityItem curItem = theSet.getCurItem();  // FEATURE
                    // was a change made in the root FEATURE? no structure needed
                    if (featChildChgVct.contains(curItem.getKey()))
                    {
                        trace(D.EBUG_INFO,false,"buildFMLog() "+curItem.getKey()+" had some change ");
                        chgRootVct.addElement(theSet);
                    }
                    else
                    {
                        trace(D.EBUG_INFO,false,"buildFMLog() "+curItem.getKey()+" did NOT change ");
                        // remove any changehistory when at last interval
                        if (numDays==LASTINTERVAL) {
                            entityChgHistTbl.remove(curItem.getKey()); } // don't need this FEATURE history any more
                    }
                    // don't need PRODSTRUCT history any more when at last interval
                    if (numDays==LASTINTERVAL) {
                        for (int d=0; d<curItem.getDownLinkCount(); d++)
                        {
                            entityChgHistTbl.remove(curItem.getDownLink(d).getKey());
                        }
                    }
                }
            }
            // pass chgRootVct into methods that check feature root only
            trace(D.EBUG_INFO,false,"buildFMLog() found "+chgRootVct.size()+" changed "+invGrpMasterTbl.get(invGrp)+" FEATURE");

            getFCChgs(chgRootVct,chgRpt, fromTime);
            getFeatureWithdrawalChgs(chgRootVct,chgRpt, fromTime);
            // one Vector for each FCTRANSACTION.invGrp
            fcVct = (Vector)fcByInvGrpTbl.get(invGrp);
            getFCTransChgs(fcVct,fcChildChgVct,chgRpt, fromTime);
            if (fcVct !=null)
            {
                // release memory
                for (int ii=0; ii<fcVct.size(); ii++)
                {
                    EntitySet theSet = (EntitySet)fcVct.elementAt(ii);
                    // remove any changehistory when at last interval
                    if (numDays==LASTINTERVAL)
                    {
                        entityChgHistTbl.remove(theSet.getCurItem().getKey());  // FCTRANSACTION
                    }

                    theSet.dereference();
                }
                fcVct.clear();
            }
            getFeatureRFAChgs(chgRootVct,chgRpt, fromTime);
            if (featVct !=null)
            {
                // release memory
                for (int ii=0; ii<featVct.size(); ii++) {
                    ((EntitySet)featVct.elementAt(ii)).dereference(); }
                featVct.clear();
            }
            chgRootVct.clear();

            // output the report
            pdhWriter.createReportEntity(chgRpt);
            chgRpt.dereference();
        }
        invDebugStr="";

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Completed FM logs for "+numDays+" days timing: "+getElapsedTime()+
            " ***************");
        flushLogs();

        // release memory
        if (fromFeatList!=null) {
            fromFeatList.dereference(); }
        if (fromFCList!=null) {
            fromFCList.dereference(); }
        featByInvGrpTbl.clear();
        fcByInvGrpTbl.clear();
    }

    /**************************************************************************************
    * An Inventory Group that is not found on a Machine Type won't have a Brand.
    *   In this case, the report will specify a Brand of pSeries
    * @param invGrp String with inventorygroup flag value
    */
    private String getBrandForInvGrp(String invGrp)
    {
        String brand = BRAND_DEFAULT;
        Vector brVct = (Vector)invGrpBrTbl.get(invGrp);
        if (brVct!=null)
        {
            brand = "";
            for (int bi=0; bi<brVct.size(); bi++)
            {
                if (bi>0) {
                    brand=brand+", "; }
                brand=brand+brDescTbl.get(brVct.elementAt(bi)).toString();
            }
        }
        else
        {
            trace(D.EBUG_WARN,false,"InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+
                invGrp+"] does not have a BRAND, using 'pSeries'");
            logDataError("InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+
                invGrp+"] does not have a BRAND, using 'pSeries'");
        }

        return brand;
    }

//EXRPT3MTM: Entity:MACHTYPE---->Assoc:MACHINETYPEMODELA->Entity:MODEL<---Relator:PRODSTRUCT<---Entity:FEATURE
//EXRPT3FMTM: Entity:FEATURE->Relator:PRODSTRUCT->Entity:MODEL ->Assoc:MODELMACHINETYPEA-->Entity:MACHTYPE
//EXRPT3FM: Entity:FEATURE->Relator:PRODSTRUCT->Entity:MODEL
    /**************************************************************************************
    * find out what has changed in this interval
    * @param extractName String
    * @param rootType String
    * @param delRelatorTbl Hashtable to hang onto vector of deleted relator id
    * @param fromTime String with dts of time 1 based on minimum date
    * @return Vector array, index0 is roots that had changes in structure, index1 is child or root that had the change
    */
    private Vector[] getAffectedRootsAndChildren(String extractName, String rootType, String fromTime,
        Hashtable delRelatorTbl) throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
        eDoc edoc = null;
        Vector rootChgVct = new Vector();
        Vector chgEntityVct = new Vector();
        Vector[] vctArray = new Vector[2];
        ReturnDataResultSet rdrs = null;

        trace(D.EBUG_ERR,true,"getAffectedRootsAndChildren() Running edoc with root: "+rootType+" extract: "+extractName+" fromTime "+fromTime+" curTime "+curTime);
        edoc = new eDoc(dbCurrent,profile, eai,rootType,fromTime, curTime);

        // FEATURE can be linked to many PRODSTRUCT, keep track of what changed
        vctArray[0] = rootChgVct;
        vctArray[1] = chgEntityVct;
        rdrs = edoc.getTransactions();
        for (int r = 0; r < rdrs.getRowCount(); r++)
        {
            ReturnDataRow row = rdrs.getRow(r);
            StringBuffer sb = new StringBuffer();
            boolean wasRelator = false;
            String rootChgId = null;
            String rootChgType=null;
            boolean rootOff = false;
            String childTypeId = null;
            boolean childOff = false;
            // col 0 is the 'general'area' value.. which will always be 000000
            for (int c=1; c<row.getColumnCount(); c++)
            {
                sb.append("["+r+"]["+c+"] "+rdrs.getColumn(r,c)+" ");
            }
            trace(D.EBUG_DETAIL,false,sb.toString());

/* PRODSTRUCT 83 created and deleted in same interval
[155][1] FEATURE [155][2] 27 [155][3] OFF [155][4] MODEL [155][5] 5 [155][6] OFF [155][7] 0 [155][8] E [155][9] PRODSTRUCT [155][10] NOOP [155][11] 0
[156][1] FEATURE [156][2] 27 [156][3] OFF [156][4] PRODSTRUCT [156][5] 83 [156][6] OFF [156][7] 0 [156][8] R [156][9] PRODSTRUCT [156][10] MODEL [156][11] 5

BUT VE defined in reverse dir has problems
DEVSUPPORT6 created and deleted in same interval
[6][1] SUPPDEVICE [6][2] 5 [6][3] OFF [6][4] DEVSUPPORT [6][5] 5 [6][6] OFF [6][7] 0 [6][8] R [6][9] DEVSUPPORT [6][10] SUPPDEVICE [6][11] 5
[7][1] SUPPDEVICE [7][2] 5 [7][3] OFF [7][4] MODEL [7][5] 5 [7][6] OFF [7][7] 0 [7][8] E [7][9] DEVSUPPORT [7][10] NOOP [7][11] 0
[8][1] SUPPDEVICE [8][2] 5 [8][3] OFF [8][4] SUPPDEVICE [8][5] 5 [8][6] OFF [8][7] 0 [8][8] E [8][9]  [8][10] NOOP [8][11] 0
*/
            rootChgType = rdrs.getColumn(r,1);
            if (!rootChgType.equals(rootType)){  // edoc is returning relator instead of root in col 1.. skip it
                trace(D.EBUG_ERR,false,rootChgType+rdrs.getColumn(r,2)+" was not expected root "+rootType+", skipping it");
                continue;
            }
            rootChgId = rdrs.getColumn(r,2);
            rootOff = rdrs.getColumn(r,3).equals("OFF");
            childTypeId = rdrs.getColumn(r,4)+rdrs.getColumn(r,5); // may also be root, just thing that changed
            childOff = rdrs.getColumn(r,6).equals("OFF");
            // really only care which roots were impacted, hang onto ids and pull all at current time
            if (!rootChgVct.contains(rootChgId)) {
                rootChgVct.addElement(rootChgId); }

            if (!chgEntityVct.contains(childTypeId)) {
                chgEntityVct.addElement(childTypeId); }

            // was the relator deleted?
            wasRelator = rdrs.getColumn(r,8).equals("R");
            if (rootOff && childOff && wasRelator) // relator was deleted, hang onto this info, may not have existed before interval
            {
                trace(D.EBUG_INFO,false,rootChgType+rdrs.getColumn(r,2)+" had Relator "+childTypeId+" removed");
                if (delRelatorTbl !=null)  // if null, don't care about relator changes
                {
                    Vector vct = (Vector)delRelatorTbl.get(rootType+rootChgId);
                    if (vct==null)
                    {
                        vct = new Vector();
                        delRelatorTbl.put(rootType+rootChgId,vct);
                    }
                    if (!vct.contains(rdrs.getColumn(r,5))) {
                        vct.addElement(rdrs.getColumn(r,5)); }
                }
            }
        }
        trace(D.EBUG_INFO,false,"getAffectedRootsAndChildren() root id size: "+rootChgVct.size()+" vct: "+rootChgVct);
        trace(D.EBUG_INFO,false,"getAffectedRootsAndChildren() child id size: "+chgEntityVct.size()+" vct: "+chgEntityVct);
        return vctArray;
    }

    /**************************************************************************************
    * pull items for ids in this vector using this profile
    * @param idVct Vector of entity ids
    * @param dtsProfile Profile for this time
    * @param extractName String
    * @param rootType String
    */
    EntityList pullEntityItems(Vector idVct, Profile dtsProfile,String extractName, String rootType) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        EntityItem[] eiArray = null;
        EntityList list = null;
        if (idVct.size()==0) {
            return null;}
        trace(D.EBUG_ERR,true,"FMChgLogGen.pullEntityItems() for extract: "+extractName+" root "+rootType+" id count: "+idVct.size()+
            " valon: "+dtsProfile.getValOn());

        eiArray = new EntityItem[idVct.size()];
        // it is possible some of these ids were deleted, the entityitem returned will have nulls for attr
        // but the entityitem will not be null and can be used as part of the ChgSet
        for (int i=0; i<idVct.size(); i++) {
            eiArray[i] = new EntityItem(null, dtsProfile, rootType, Integer.parseInt(idVct.elementAt(i).toString()));
        }
        list = dbCurrent.getEntityList(dtsProfile,
            new ExtractActionItem(null, dbCurrent, dtsProfile, extractName),eiArray);

        return list;
    }

    /**************************************************************************************
    * Get value for attribute when it wasn't in the entity item.. might not have ever been
    * set or the entity was deleted
    * First find last active date and use that for a pull, if still not found, then
    * use AttributeChangeHistory.. warning.. more work needed if multiflag attributes are needed!
    *
    * @param theItem EntityItem to find attribute value for
    * @param attrCode String attribute code
    * @param extractName String with extract action name
    * @param fromTime String with dts of start interval
    * @return String[]  index[0] is desc, index[1] is flagcode, nulls in array if not set
    */
    private String[] getValueForNullAttr(EntityItem theItem, String attrCode, String extractName,
        String fromTime)    throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        ChangeHistoryItem createChi = null;
        ChangeHistoryItem lastChi = null;
        String lastActiveDate = null;
        Profile dtsProfile = null;
        String adjDate ="";
        EntityList theList = null;
        EntityItem theValidItem = null;
        EntityChangeHistoryGroup histGrp = null;
        String retValues[] = new String[2];
        String rootType = theItem.getEntityType();
        Vector idVct = new Vector();
        idVct.addElement(""+theItem.getEntityID());

        trace(D.EBUG_INFO,false,"getValueForNullAttr() entered for "+theItem.getKey()+" attrCode: "+attrCode);
        // get entity history
        histGrp = getChgHistGroup(theItem.getKey());
        if (histGrp==null)
        {
            histGrp = new EntityChangeHistoryGroup(dbCurrent, profile, theItem);
            addChgHistGroup(theItem.getKey(),histGrp);
        }

        if (histGrp.getChangeHistoryItemCount()==0){
            trace(D.EBUG_WARN,false,"getValueForNullAttr() ERROR: No change history items found for "+theItem.getKey());
            logDataError("No change history items found for "+theItem.getKey());
            return retValues;
        }

        createChi = histGrp.getChangeHistoryItem(0);
        lastChi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
        lastActiveDate = createChi.getChangeDate();
        // make sure lastChi is before curtime
        if (lastChi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
        {
            for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
            {
                ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
                // history is complete, if user makes chgs after chglog starts, don't pick them up!
                if (chi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
                {
                    trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
                    continue;
                }
                lastChi=chi;
                break;
            }
        }

        // get delete date and lastActiveDate
        if (!lastChi.isActive())  // was DELETED
        {
            for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
            {
                ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
                trace(D.EBUG_DETAIL,false,"getValueForNullAttr() chi["+i+"] for "+theItem.getKey()+" isActive: "+chi.isActive()+
                    " isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
                // history is complete, if user makes chgs after chglog starts, don't pick them up!
                if (chi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
                {
                    trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
                    continue;
                }

                if (chi.isActive())
                {
                     // set this with the first active one before deletion, don't overwrite
                    lastActiveDate = chi.getChangeDate();
                    break;
                }
                if (chi.getChangeDate().compareTo(fromTime)<=0) // date is BEFORE fromtime
                {
                    break;
                }
            }
        }
        else
        {
            trace(D.EBUG_DETAIL,false,theItem.getKey()+" is Active from: "+lastChi.getChangeDate()+" "+attrCode+" is null");
            // it is active at from time, so there is no value to get
            return retValues;//nulls will be in array
        }

        // do a pull using modified delete date, getting eanattribute for attribute chg history is not reliable
        dtsProfile = profile.getNewInstance(dbCurrent);

        // subtract an increment from deletedate to allow for slightly earlier attribute timestamps
        // find a time between these 2 dates
        adjDate = FMChgSet.getISOEntityAdjustedDeleteDate(lastActiveDate, lastChi.getChangeDate());

        dtsProfile.setValOnEffOn(adjDate, adjDate);

        theList = pullEntityItems(idVct, dtsProfile,extractName, rootType);
        theValidItem = theList.getParentEntityGroup().getEntityItem(0);

        retValues[0] = getAttributeValue(theValidItem, attrCode, "", null);
        retValues[1] = getAttributeFlagValue(theValidItem, attrCode);

        theList.dereference();
        if (retValues[0] != null)
        {
            return retValues;
        }
        else
        {
            // attempt to use attributehistory if can't get value any other way, multiflags need more work if this is
            // used
            RowSelectableTable itemTable = theItem.getEntityItemTable();
            String keyStr = theItem.getEntityType() + ":" + attrCode;
            trace(D.EBUG_INFO,false,"getValueForNullAttr() "+theItem.getKey()+" returned null for "+
                attrCode+", using AttrChangeHistory");
            try {
                int row = itemTable.getRowIndex(keyStr);
                if (row < 0) {
                    row = itemTable.getRowIndex(keyStr + ":C");
                }
                if (row < 0) {
                    row = itemTable.getRowIndex(keyStr + ":R");
                }
                if (row != -1)
                {
                    EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
                    if (attStatus != null)
                    {
                        AttributeChangeHistoryGroup ahistGrp = getAttrChgHistGroup(keyStr);
                        trace(D.EBUG_DETAIL,false,"getValueForNullAttr() AttributeChangeHistory for "+attrCode+" in "+theItem.getKey());
                        if (ahistGrp==null)
                        {
                            ahistGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
                            addAttrChgHistGroup(keyStr,ahistGrp);
                        }
                        for (int ci= ahistGrp.getChangeHistoryItemCount()-1; ci>=0; ci--) // go from most recent to earliest
                        {
                            ChangeHistoryItem chi = ahistGrp.getChangeHistoryItem(ci);
                            // history is complete, if user makes chgs after chglog starts, don't pick them up!
                            if (chi.getChangeDate().compareTo(curTime)>0) // date is AFTER curtime
                            {
                                trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
                                continue;
                            }

                            retValues[0] = chi.get("ATTVAL",true).toString();
                            retValues[1] = chi.getFlagCode(); // this may be 'N/A' if attribute was not Flag
                            trace(D.EBUG_DETAIL,false,"getValueForNullAttr() AttrChangeHistoryItem["+ci+"] chgDate: "+chi.getChangeDate()+
                                    " isValid: "+chi.isValid()+
                                    " isActive: "+chi.isActive()+" value: "+
                                    chi.get("ATTVAL",true).toString()+
                                    " user: "+chi.getUser());
                            return retValues;  // only do latest value it may be invalid
                        } // each history item
                    }
                    else {
                        trace(D.EBUG_INFO,false,"getValueForNullAttr() EANAttribute was null for "+attrCode+" in "+theItem.getKey());}
                }
                else {
                    trace(D.EBUG_ERR,true,"getValueForNullAttr() Row for "+attrCode+" was not found for "+theItem.getKey());}
            } catch (Exception ee)  {
                trace(D.EBUG_ERR,true,"getValueForNullAttr() Exception getting history for "+attrCode+": "+ee.getMessage());
            }
        }

        return retValues;//nulls will be in array
    }

    /**************************************************************************************
    * Get FCTRANSACTION entities by invgrp that match the ids in rootchgvct
    * The list of Feature Transactions (FCTRANSACTION) is filtered where Feature Transaction Category
    * (FTCAT) is equal to Feature Conversion (406).
    *
    * @param fctransTbl Hashtable has all fctrans ids there were affected
    * @param fromFCList EntityList has ids from rootChgVct
    * @param rootChgVct Vector of changed entity ids that were affected in this interval
    * @return Hashtable of EntitySets, key is invGrp
    */
    private Hashtable getFCByInvGrp(Hashtable fctransTbl,EntityList fromFCList, Vector rootChgVct) throws Exception
    {
        Hashtable fcByInvGrpTbl = new Hashtable();
        EntityGroup fromfcGrp = null;
        if (fctransTbl.size()==0 || fromFCList==null) { // no changed fctransaction found
            return fcByInvGrpTbl; }

        fromfcGrp = fromFCList.getParentEntityGroup();
        for(int i=0; i<rootChgVct.size(); i++)
        {
            String mtdesc = null;
            String entityId = (String)rootChgVct.elementAt(i);
            EntityItem fromItem= fromfcGrp.getEntityItem("FCTRANSACTION"+entityId);
            EntityItem curItem= (EntityItem)fctransTbl.get("FCTRANSACTION"+entityId);
            EntitySet theSet = new EntitySet(fromItem, curItem);

            // only need Feature Transactions (FCTRANSACTION), filter where Feature Transaction Category (FTCAT) is equal
            // to Feature Conversion (406) as a current value
            String ftcat = getAttributeFlagValue(curItem, "FTCAT");
            String ftcatdesc = getAttributeValue(curItem, "FTCAT", "", null);
            if (ftcat==null)
            {
                // try fromItem, note it may not have values at deletion, it will have values at fromTime
                ftcat = getAttributeFlagValue(fromItem, "FTCAT");
                ftcatdesc = getAttributeValue(fromItem, "FTCAT", "", null);
                if (ftcat==null)
                {
                    String tmp[]=getValueForNullAttr(fromItem, "FTCAT", "DUMMY", fromItem.getProfile().getValOn());
                    ftcatdesc=tmp[0];
                    ftcat=tmp[1];
                    tmp[0]=null;
                    tmp[1]=null;
                }
            }
            if (ftcat==null || !ftcat.equals(FTCAT_MATCH))
            {
                trace(D.EBUG_INFO,false,"getFCByInvGrp() skipping "+curItem.getKey()+" FTCAT was not Feature Conversion["+
                    FTCAT_MATCH+"]: it was "+ftcatdesc+"["+ftcat+"]");
                continue;
            }

            /*
            Wendy   hi, have another question for FCTRANSACTION section, how do i determine which inventory group it goes in?
            Wendy   that determines which file it goes into...
            Wayne Kehrli    the spreadsheet in the teamroom has FROMMACHTYPE as T
            Wendy   that needs to match the MACHTYPEATR attr
            Wayne Kehrli    yes - i.e. the T needs to match the description of the U
            Wendy   oh boy.. so if it doesn't, where does this info end up?
            Wendy   i won't be able to find an invgrp and file to put it in
            Wayne Kehrli    two answers
            1) we have an ABR that checks it on update (save)
            2) if no match, just report under INVENTORY GROUP = unknown
            and list as MACHINE TYPE = unknown
            Wayne Kehrli    or have an error section
            Wayne Kehrli    or error file
            Wayne Kehrli    and log the errror there
            Wayne Kehrli    I like the concept of the error file
            */
            // try to get FROMMACHTYPE from curentity, if it was deactivated, it will be null
            // could use fromitem but invgrp value may have changed
            mtdesc = getAttributeValue(curItem, "FROMMACHTYPE", "", null);
            if (mtdesc==null)
            {
                //try fromitem, note it may not have values at deletion, it will have values at fromTime
                mtdesc = getAttributeValue(fromItem, "FROMMACHTYPE", "", null);
                if (mtdesc==null)
                {
                    String tmp[]=getValueForNullAttr(fromItem, "FROMMACHTYPE", "DUMMY", fromItem.getProfile().getValOn());
                    mtdesc=tmp[0];
                    tmp[0]=null;
                    tmp[1]=null;
                }
            } // end mtdesc from curTime =null
            if (mtdesc!=null)
            {
                Vector vct = null;
                String invGrpflag = (String)MTinvGrpTbl.get(mtdesc); // match on desc
                if (invGrpflag==null)
                {
                    trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR: No inventory group found for "+curItem.getKey()+".FROMMACHTYPE = "+mtdesc);
                    logDataError("No inventory group found for "+curItem.getKey()+".FROMMACHTYPE = "+mtdesc);
                    continue;
                }
                vct = (Vector)fcByInvGrpTbl.get(invGrpflag);
                if (vct==null)
                {
                    vct = new Vector();
                    fcByInvGrpTbl.put(invGrpflag,vct);
                }
                vct.addElement(theSet);
            }
            else {
                trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR FROMMACHTYPE was not found for "+curItem.getKey()); }
        } // check each changed root

        return fcByInvGrpTbl;
    }
    /**************************************************************************************
    * Get feature entities by invgrp that match the ids in rootchgvct
    * @param featTbl Hashtable has feature ids there were affected
    * @param fromFeatList EntityList has ids from rootChgVct
    * @param rootChgVct Vector of changed entity ids that were affected in this interval
    * @return Hashtable of EntitySets, key is invGrp
    */
    private Hashtable getFeaturesByInvGrp(Hashtable featTbl,EntityList fromFeatList, Vector rootChgVct)
        throws Exception
    {
        Hashtable featByInvGrpTbl = new Hashtable();
        EntityGroup fromfeatGrp = null;
        if (featTbl.size()==0 || fromFeatList==null)  { // no changed features found
            return featByInvGrpTbl; }

        fromfeatGrp = fromFeatList.getParentEntityGroup();
        for(int i=0; i<rootChgVct.size(); i++)
        {
            String entityId = (String)rootChgVct.elementAt(i);
            EntityItem fromItem= fromfeatGrp.getEntityItem("FEATURE"+entityId);
            EntityItem curItem= (EntityItem)featTbl.get("FEATURE"+entityId);
            EntitySet theSet = new EntitySet(fromItem, curItem);

            // try to get invgrp from curentity, if it was deactivated, it will be null
            // could use fromitem but invgrp value may have changed
            String invGrpDesc = getAttributeValue(curItem, "INVENTORYGROUP", "", null);
            String invGrpflag = getAttributeFlagValue(curItem, "INVENTORYGROUP");
            if (invGrpDesc==null)
            {
                // try fromitem, note it may not have values at deletion, it will have values at fromTime
                invGrpDesc = getAttributeValue(fromItem, "INVENTORYGROUP", "", null);
                invGrpflag = getAttributeFlagValue(fromItem, "INVENTORYGROUP");
                if (invGrpDesc==null)
                {
                    String tmp[]=getValueForNullAttr(fromItem, "INVENTORYGROUP", "DUMMY", fromItem.getProfile().getValOn());
                    invGrpDesc=tmp[0];
                    invGrpflag=tmp[1];
                    tmp[0]=null;
                    tmp[1]=null;
                }

            } // end curTime invgrp returned null
            if (invGrpflag !=null)
            {
                Vector vct = null;
                if (invGrpMasterTbl.get(invGrpflag)==null)
                {
                    logDataError("Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
                    trace(D.EBUG_WARN,false,"getFeaturesByInvGrp() Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
                    theSet.dereference();
                    continue;
                }

                vct = (Vector)featByInvGrpTbl.get(invGrpflag);
                if (vct==null)
                {
                    vct = new Vector();
                    featByInvGrpTbl.put(invGrpflag,vct);
                }
                vct.addElement(theSet);
            }
            else {
                trace(D.EBUG_ERR,false,"getFeaturesByInvGrp() ERROR could not find INVENTORYGROUP for "+curItem.getKey()); }
        } // check each changed root

        return featByInvGrpTbl;
    }

    /**************************************************************************************
    * B.    Product Structure (MTM) Changes
    * The columns are:
    * Heading                   Description
    * Date/Time of Change       ValFrom of the Relator = PRODSTRUCT
    * Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor               From the Entity (First 10 characters)
    * MTM                       MODEL.MACHTYPEATR & - & MODEL.MODELATR
    * FC                        FEATURE.FEATURECODE
    * Ann Date Override         PRODSTRUCT.ANNDATE
    * Min Req                   PRODSTRUCT.SYSTEMMIN
    * Max                       PRODSTRUCT.SYSTEMMAX
    * OS Lev (Chg)              PRODSTRUCT.OSLEVEL (If the list is changed, the Change; else blank)
    * Order Code                PRODSTRUCT.ORDERCODE
    * FM                        (Derived  see below)
    * Name                      FEATURE.COMNAME
    *
    * FM is derived as follows:
    *
    * Use entity FEATURE to find a matching MAPFEATURE. The matching is based on INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT.
    * Given a matching entity, then this gives FMGROUPCODE and FMSUBGROUPCODE. Concatenate these two values.
    * This yields a two character code.
    *
    * @param featVct Vector with sets of FEATURE items for curTime and fromtime grouped by invgrp
    * @param chgChildVct Vector with all changed children or root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param delPSRelatorTbl Hashtable with deleted PRODSTRUCT relator ids
    * @param fromTime String with dts of time 1 based on minimum date
    */
    private void getMTMChgs(Vector featVct, Vector chgChildVct, FMChgRpt chgRpt, Hashtable delPSRelatorTbl,
        String fromTime)  throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector();
        String sectionTitle = FMChgMTMSet.getSectionTitle();
        String sectionInfo = FMChgMTMSet.getSectionInfo();
        String noneFndText = FMChgMTMSet.getNoneFndText();

        // output this sections info
        if (featVct==null)
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getMTMChgs() No changes found");
        }
        else
        {
            FMChgMTMSet mtm = null;
            for (int i=0; i<featVct.size(); i++)
            {
                EntitySet theSet = (EntitySet)featVct.elementAt(i);
                // use cur entity as basis
                EntityItem curItem = theSet.getCurItem();  // FEATURE
                EntityItem fromItem = theSet.getFromItem();

                /*CR042605498 only trigger on PRODSTRUCT chgs
                boolean rootChg = false;
                // was a change made in the root FEATURE?
                if (chgChildVct.contains(curItem.getKey()))
                {
                    trace(D.EBUG_INFO,false,"getMTMChgs() ROOT "+curItem.getKey()+" had some change ");
                    rootChg = true;
                }
                else
                    trace(D.EBUG_INFO,false,"getMTMChgs() ROOT "+curItem.getKey()+" did NOT change ");
                    CR042605498*/

                // look at delRelatorTbl to see if a relator was deleted
                Vector relIdVct = (Vector)delPSRelatorTbl.get(curItem.getKey());
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getMTMChgs() checking root "+curItem.getKey());
                if (relIdVct!=null)
                {
                    // look to see if curitem or fromitem have downlinks to 'deleted' relators in hashtable
                    // if they do, this relator will be handled later
                    for (int r=0; r<relIdVct.size(); r++)
                    {
                        String relId = (String)relIdVct.elementAt(r);
                        if (fromItem.getDownLink("PRODSTRUCT"+relId)==null &&
                            curItem.getDownLink("PRODSTRUCT"+relId)==null)
                        {
                            trace(D.EBUG_INFO,false,"getMTMChgs() PRODSTRUCT"+relId+
                                " relator was created and deleted in this interval for "+curItem.getKey());

                            mtm = new FMChgMTMDelSet(dbCurrent, profile, this,
                                relId, curItem.getEntityID(), fromTime, curTime);
                            mtm.calculateOutput();
                            chgSetVct.addElement(mtm);
                        }
                        else {
                            trace(D.EBUG_INFO,false,"getMTMChgs() PRODSTRUCT"+relId+" relator was deleted for "+
                                curItem.getKey()+" BUT was found in fromitem or curitem");
                        }
                    }
                }

                if (curItem.getDownLinkCount()==0&& fromItem.getDownLinkCount()==0)
                {
                    // prodstruct must have been created and deleted in the timeframe or never existed
                    trace(D.EBUG_INFO,false,"getMTMChgs() No PRODSTRUCT relator found for "+curItem.getKey());
                    continue;
                }

                // get prodstructs
                for (int p=0; p<curItem.getDownLinkCount(); p++)
                {
                    EntityItem theProdItem = (EntityItem)curItem.getDownLink(p);
                    // was this item added?
                    if (fromItem.getDownLink(theProdItem.getKey())==null)
                    {
                        trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" was ADDED or RESTORED");
                        mtm = new FMChgMTMSet(dbCurrent, profile, this, null, theProdItem, fromTime, curTime);
                        mtm.calculateOutput();
                        chgSetVct.addElement(mtm);
                    }
                    else
                    if (chgChildVct.contains(theProdItem.getKey()))
                    {
                        EntityItem fitem = null;
                        trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" had some change ");

                        fitem = (EntityItem)fromItem.getDownLink(theProdItem.getKey());
                        mtm = new FMChgMTMSet(dbCurrent, profile, this, fitem, theProdItem, fromTime, curTime);
                        mtm.calculateOutput();
                        chgSetVct.addElement(mtm);
                    }
                    else
                    {
                        trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" did NOT change");
//trace(D.EBUG_INFO,false,"getMTMChgs() model "+theProdItem.getDownLink(0).getKey()+" ???");
                        /*CR042605498 only trigger on PRODSTRUCT chgs
                        // but did its' model?
                        EntityItem mdlItem = (EntityItem)theProdItem.getDownLink(0);
                        if (chgChildVct.contains(mdlItem.getKey()))
                        {
                            trace(D.EBUG_INFO,false,"getMTMChgs() child "+mdlItem.getKey()+
                                    " had some change so add relator "+theProdItem.getKey());
                            EntityItem fitem = (EntityItem)fromItem.getDownLink(theProdItem.getKey());
                            FMChgMTMSet mtm = new FMChgMTMSet(dbCurrent, profile, this, fitem, theProdItem, fromTime, curTime);
                            mtm.calculateOutput();
                            chgSetVct.addElement(mtm);
                        }
                        else
                        if (rootChg)
                        {
                            trace(D.EBUG_INFO,false,"getMTMChgs() child "+mdlItem.getKey()+
                                    " did NOT change BUT root parent did so add relator "+theProdItem.getKey());
                            EntityItem fitem = (EntityItem)fromItem.getDownLink(theProdItem.getKey());
                            FMChgMTMSet mtm = new FMChgMTMSet(dbCurrent, profile, this, fitem, theProdItem, fromTime, curTime);
                            mtm.calculateOutput();
                            chgSetVct.addElement(mtm);
                        }
                        else
                            trace(D.EBUG_INFO,false,"getMTMChgs() child "+mdlItem.getKey()+
                                    " did NOT change so skipping relator "+theProdItem.getKey());
                        CR042605498*/
                    }
                }
                for (int p=0; p<fromItem.getDownLinkCount(); p++)
                {
                    EntityItem theProdItem = (EntityItem)fromItem.getDownLink(p);
                    // was this item deleted?
                    if (curItem.getDownLink(theProdItem.getKey())==null)
                    {
                        trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" was DELETED");
                        mtm = new FMChgMTMSet(dbCurrent, profile, this,theProdItem, null, fromTime, curTime);
                        mtm.calculateOutput();
                        chgSetVct.addElement(mtm);
                    }
                }
            }
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgMTMSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

    /**************************************************************************************
    * C.    Feature Inventory Group Changes
    * The columns are:
    * Heading                   Description
    * Date/Time of Change       ValFrom of the Entity
    * Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor               From the Entity (First 10 characters)
    * FC                        FEATURE.FEATURECODE
    * First Ann Date            FEATURE.FIRSTANNDATE
    * FM                        Derived see Section IV.B.
    * Name                      FEATURE.COMNAME
    *
    * @param chgRootVct Vector with all changed root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param fromTime dts of start of interval
    */
    private void getFCChgs(Vector chgRootVct, FMChgRpt chgRpt, String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector(chgRootVct.size());
        String sectionTitle = FMChgFCSet.getSectionTitle();
        String sectionInfo = FMChgFCSet.getSectionInfo();
        String noneFndText = FMChgFCSet.getNoneFndText();

        if (chgRootVct.size()>0)
        {
            // handle all changed feature
            for (int p=0; p<chgRootVct.size(); p++)
            {
                FMChgFCSet fc = null;
                EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCChgs() checking "+theSet);
                fc = new FMChgFCSet(dbCurrent, profile, this, theSet.getFromItem(), theSet.getCurItem(), fromTime, curTime);
                fc.calculateOutput();
                chgSetVct.addElement(fc);
            }
        }
        else
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCChgs() No changes found");
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgFCSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

    /******************************************************************************************
    * D.    Global Feature Withdrawal Changes
    *
    * The columns are:
    * Heading                   Description
    * Date/Time of Change       ValFrom of the Entity
    * Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor               From the Entity (First 10 characters)
    * FC                        FEATURE.FEATURECODE
    * FM                        Derived  see Section IV.B.
    * Name                      FEATURE.COMNAME
    * Global Withdrawal Announce Date       FEATURE.WITHDRAWANNDATE_T
    * Global Withdrawal Date Effective  FEATURE.WITHDRAWDATEEFF_T
    * Withdrawal Comments       FEATURE.WDCOMMENTS
    *
    * @param chgRootVct Vector with all changed root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param fromTime dts of start of interval
    */
    private void getFeatureWithdrawalChgs(Vector chgRootVct, FMChgRpt chgRpt, String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector(chgRootVct.size());
        String sectionTitle = FMChgGFWSet.getSectionTitle();
        String sectionInfo = FMChgGFWSet.getSectionInfo();
        String noneFndText = FMChgGFWSet.getNoneFndText();

        if (chgRootVct.size()>0)
        {
            // handle all changed feature
            for (int p=0; p<chgRootVct.size(); p++)
            {
                FMChgGFWSet fc = null;
                EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureWithdrawalChgs() checking "+theSet);
                fc = new FMChgGFWSet(dbCurrent, profile, this, theSet.getFromItem(), theSet.getCurItem(), fromTime, curTime);
                fc.calculateOutput();
                chgSetVct.addElement(fc);
            }
        }
        else
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureWithdrawalChgs() No changes found");
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgGFWSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

    /**************************************************************************************************
    * E.    Feature Conversion Changes
    *
    * The list of Feature Transactions (FCTRANSACTION) is filtered where Feature Transaction Category (FTCAT) is equal to Feature Conversion (406).
    *
    * The columns are:
    * Heading                   Description
    * Date/Time of Change       ValFrom of the Entity
    * Change Type               Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor               From the Entity (First 10 characters)
    * Announce Date             FCTRANSACTION.ANNDATE
    * From MTM                  FCTRANSACTION.FROMMACHTYPE &-& FCTRANSACTION.FROMMODEL
    * From FC                   FCTRANSACTION.FROMFEATURECODE
    * To MTM                    FCTRANSACTION.TOMACHTYPE &-& FCTRANSACTION.TOMODEL
    * To FC                     FCTRANSACTION.TOFEATURECODE
    *
    * @param fcVct Vector with sets of FCTRANSACTION items for curTime and fromtime grouped by invgrp
    * @param chgChildVct Vector with all changed children and root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param fromTime dts of start of interval
    */
    private void getFCTransChgs(Vector fcVct, Vector chgChildVct, FMChgRpt chgRpt, String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector();
        String sectionTitle = FMChgFCTransSet.getSectionTitle();
        String sectionInfo = FMChgFCTransSet.getSectionInfo();
        String noneFndText = FMChgFCTransSet.getNoneFndText();

        // output this sections info
        if (fcVct!=null)
        {
            for (int i=0; i<fcVct.size(); i++)
            {
                EntitySet theSet = (EntitySet)fcVct.elementAt(i);
                // use cur entity as basis
                EntityItem curItem = theSet.getCurItem();  // FCTRANSACTION
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCTransChgs() checking "+curItem.getKey());
                // was a change made in the root FCTRANSACTION?
                if (chgChildVct.contains(curItem.getKey()))
                {
                    FMChgFCTransSet fc = null;
                    trace(D.EBUG_INFO,false,"getFCTransChgs() "+curItem.getKey()+" had some change ");
                    fc = new FMChgFCTransSet(dbCurrent, profile, this, theSet.getFromItem(),
                        theSet.getCurItem(), fromTime, curTime);
                    fc.calculateOutput();
                    chgSetVct.addElement(fc);
                }
                else
                {
                    trace(D.EBUG_INFO,false,"getFCTransChgs() "+curItem.getKey()+" did NOT change ");
                    continue;  // no structure here
                }
            }
        }
        else
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCTransChgs() No changes found");
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgFCTransSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

    /****************************************************************************************
    * F.    Feature RFA Changes
    *
    * The columns are:
    * Heading               Description
    * Date/Time of Change   ValFrom of the Entity
    * Change Type           Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor           From the Entity (First 10 characters)
    * FC                    FEATURE.FEATURECODE
    * Cntry List            If changes in FEATURE.COUNTRYLIST, then this column equals Change
    * Other Info            TBD
    * Marketing Name        FEATURE.MKTGNAME
    *
    * @param chgRootVct Vector with all changed root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param fromTime dts of start of interval
    */
    private void getFeatureRFAChgs(Vector chgRootVct, FMChgRpt chgRpt, String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector(chgRootVct.size());
        String sectionTitle = FMChgRFASet.getSectionTitle();
        String sectionInfo = FMChgRFASet.getSectionInfo();
        String noneFndText = FMChgRFASet.getNoneFndText();

        if (chgRootVct.size()>0)
        {
            // handle all changed feature
            for (int p=0; p<chgRootVct.size(); p++)
            {
                FMChgRFASet fc = null;
                EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureRFAChgs() checking "+theSet);
                fc = new FMChgRFASet(dbCurrent, profile, this, theSet.getFromItem(),
                    theSet.getCurItem(), fromTime, curTime);
                fc.calculateOutput();
                chgSetVct.addElement(fc);
            }
        }
        else
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureRFAChgs() No changes found");
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgRFASet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

//===============================================================================================
//============== SUPPDEVICE section ============================================================
//===============================================================================================
    /**************************************************************************************
    * Main entry point to kick off builds of Supported Device logs 30 and 7 day intervals
    *
    * Start at 30 day interval, call eDoc to find all changed SUPPDEVICE roots and changed children
    * Pull EntityList for all affected roots at curTime, hang onto it for 7 day check
    * buildSDLog pulls EntityList for all affected roots at fromTime, it then groups all
    * SUPPDEVICE by InventoryGrp and pairs the entity at curTime with its match at fromTime
    *
    * It is possible that the changes are not in the set of attributes reported in each section.
    * A file is generated for each InventoryGroup found from setup. Each section is handled and
    * then written to the PDH
    */
    void buildSDLogs()
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException, Exception
    {
        String fromTime = null;
        Hashtable delRelatorTbl = null;
        Vector vctArray[] = null;
        Vector rootChgVct = null;
        EntityList curSDList = null;

        int numDays = THIRTY;
        swTimer.lap(); // reset timer
        entityChgHistTbl.clear();  // don't need FEATURE, PRODSTRUCT or MODEL history any more
        attrChgHistTbl.clear(); // don't need these attributes anymore

        // find out what changed over 30 days
        trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"********************* "+numDays+" Days *****************************************************");
        fromTime = calculateFromTime(numDays);

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of SD logs for "+numDays+" days  curTime "+
            curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

        trace(D.EBUG_INFO,false,"buildSDLogs()***** Find changed SUPPDEVICE-DEVSUPPORT-MODEL ***************");

        delRelatorTbl = new Hashtable();
        vctArray = getAffectedRootsAndChildren(SD_EXTRACTNAME,SD_ROOTTYPE, fromTime, delRelatorTbl);
        rootChgVct = vctArray[0];  // index 0 is roots of structure with changes

        // pull all affected entities
        // do actual pull for these items.. do 30 first, that will include all for 7 and 1 day
        // do it here and pass it to reuse it in sections 1-3
        curSDList = pullEntityItems(rootChgVct, profile,SD_EXTRACTNAME,SD_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildSDLogs() curSDList: "+FMChgLog.NEWLINE+outputList(curSDList));
        flushLogs();

        // find 30 day changes and write out
        buildSDLog(vctArray,curSDList,numDays, fromTime, delRelatorTbl);
        vctArray[0].clear(); // root that changed structure
        vctArray[1].clear(); // child or root that changed
        for (Enumeration e = delRelatorTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            vct.clear();
        }
        delRelatorTbl.clear();

        numDays = SEVEN;
        trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
        // find 7 day changes and write out
        fromTime = calculateFromTime(numDays);

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of SD logs for "+numDays+" days  curTime "+
            curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

        trace(D.EBUG_INFO,false,"buildSDLogs()***** Find changed SUPPDEVICE-DEVSUPPORT-MODEL ***************");
        vctArray = getAffectedRootsAndChildren(SD_EXTRACTNAME,SD_ROOTTYPE, fromTime, delRelatorTbl);
        flushLogs();

        buildSDLog(vctArray,curSDList, numDays, fromTime,delRelatorTbl);
        vctArray[0].clear(); // root that changed structure
        vctArray[1].clear(); // child or root that changed

        for (Enumeration e = delRelatorTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            vct.clear();
        }
        delRelatorTbl.clear();
        delRelatorTbl = null;
        if (curSDList !=null) {
            curSDList.dereference(); }
    }

    /**************************************************************************************
    * Build SD logs for a particular interval
    * @param vctArray Vector[] index[0]with changed root entity ids, index[1] entities that had changes, child or root
    * @param numDays int with interval
    * @param fromTime String with dts of time 1 based on minimum date
    * @param delRelatorTbl Hashtable with deleted DEVSUPPORT relator ids
    */
    private void buildSDLog(Vector vctArray[], EntityList curSDList,
        int numDays, String fromTime, Hashtable delRelatorTbl)
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException, Exception
    {
        EntityList fromSDList = null;
        Hashtable sdByInvGrpTbl = null;

        Vector rootChgVct=vctArray[0];
        Vector sdChildChgVct=vctArray[1];
        // get entities at from time
        Profile fromProfile = profile.getNewInstance(dbCurrent);
        fromProfile.setValOnEffOn(fromTime, fromTime);
        fromSDList = pullEntityItems(rootChgVct, fromProfile,SD_EXTRACTNAME, SD_ROOTTYPE);
        trace(D.EBUG_INFO,false,"buildSDLog() fromTime: "+fromTime+" fromSDList: "+FMChgLog.NEWLINE+outputList(fromSDList));

        trace(D.EBUG_INFO,false,"buildSDLog()***** Group changed SUPPDEVICE structure by InventoryGroup ***************");
        // all need output based on inv grp do here once
        sdByInvGrpTbl = getSuppDeviceByInvGrp(curSDList, fromSDList, rootChgVct);

        // complete one inv grp at a time, write it out
        for (Enumeration e = invGrpMasterTbl.keys(); e.hasMoreElements();)
        {
            FMChgRpt chgRpt = null;
            Vector sdVct = null;
            String invGrp = (String)e.nextElement(); // key is invgrp flag code

            // write out the outputSb
            String brandflag = BRANDFLAG_DEFAULT;
            Vector brVct = (Vector)invGrpBrTbl.get(invGrp);
            if (brVct!=null)
            {
/*
Wendy   how to handle a file for an inventory group that has more than one brand?  FMCHGLOGRPT.BRAND is a U flag
Wendy   right now i generate one file, just says Brand= brandx, brandy
Wayne Kehrli    oh - that is fine
Wayne Kehrli    one report
Wayne Kehrli    stored by the first brand
Wayne Kehrli    but the contents of the report can list multiple
*/
                //brand needs to be first flag code
                brandflag = brVct.elementAt(0).toString();
            }
            invDebugStr="SD"+numDays+invGrpMasterTbl.get(invGrp);

            chgRpt = new FMChgSDRpt(this, numDays, curTime, invGrp, brandflag);

            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"***CLSTATUS**** build SD log for InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+invGrp+"] ***************");

            chgRpt.setHeading(fromTime,(String)invGrpMasterTbl.get(invGrp),getBrandForInvGrp(invGrp));

            // one Vector for each SUPPDEVICE.invGrp
            sdVct = (Vector)sdByInvGrpTbl.get(invGrp);
            trace(D.EBUG_INFO,false,"buildSDLog() found "+((sdVct!=null)?""+sdVct.size():"0")+" changed "+invGrpMasterTbl.get(invGrp)+" SUPPDEVICE and structure");
            getSuppDevMTMChgs(sdVct,sdChildChgVct,chgRpt,delRelatorTbl,fromTime);
            getSuppDevChgs(sdVct,sdChildChgVct,chgRpt, fromTime);
            if (sdVct !=null)
            {
                // release memory
                for (int ii=0; ii<sdVct.size(); ii++) {
                    ((EntitySet)sdVct.elementAt(ii)).dereference(); }
                sdVct.clear();
            }

            // output the report
            pdhWriter.createReportEntity(chgRpt);
            chgRpt.dereference();
        }
        invDebugStr="";

        trace(D.EBUG_ERR,true,"***CLSTATUS**** Completed SD logs for "+numDays+" days timing "+
            getElapsedTime()+" ***************");
        flushLogs();

        // release memory
        if (fromSDList!=null) {
            fromSDList.dereference(); }
        sdByInvGrpTbl.clear();
    }

    /**************************************************************************************
    * break text into 80 char lines at wrap boundaries
    * @param text String
    * @param MAX_STR_LEN int
    * @return String with newlines at 80 char boundaries
    */
    static String parseIntoLines(String text, int MAX_STR_LEN)
    {
        if (text.length()>MAX_STR_LEN)
        {
            int lineLength = 0;
            int start =  0;
            int end =0;
            BreakIterator boundary = BreakIterator.getLineInstance();
            StringBuffer sb = new StringBuffer();

            boundary.setText(text);
            start = boundary.first();
            end = boundary.next();
            while (end != BreakIterator.DONE)
            {
                String word = text.substring(start,end);
                lineLength = lineLength + word.length();
                if (lineLength > MAX_STR_LEN)
                {
                    lineLength = word.length();
                    sb.append(FMChgLog.NEWLINE);
                }
                sb.append(word);

                start = end;
                end = boundary.next();
            }
            return sb.toString();
        }
        else {
            return text; }
    }
    /**************************************************************************************
    * Get supported device entities by invgrp that match the ids in rootchgvct
    * @param curSDList EntityList has all suppdevice ids there were affected
    * @param fromSDList EntityList has ids from rootChgVct
    * @param rootChgVct Vector of changed entity ids that were affected in this interval
    * @return Hashtable of EntitySets, key is invGrp
    */
    private Hashtable getSuppDeviceByInvGrp(EntityList curSDList,EntityList fromSDList, Vector rootChgVct)
        throws Exception
    {
        Hashtable sdByInvGrpTbl = new Hashtable();
        EntityGroup cursdGrp = null;
        EntityGroup fromsdGrp = null;
        if (curSDList==null || fromSDList==null)  {// no changed suppdev found
            return sdByInvGrpTbl; }
        cursdGrp = curSDList.getParentEntityGroup();
        fromsdGrp = fromSDList.getParentEntityGroup();
        for(int i=0; i<rootChgVct.size(); i++)
        {
            String entityId = (String)rootChgVct.elementAt(i);
            EntityItem fromItem= fromsdGrp.getEntityItem(cursdGrp.getEntityType()+entityId);
            EntityItem curItem= cursdGrp.getEntityItem(cursdGrp.getEntityType()+entityId);
            EntitySet theSet = new EntitySet(fromItem, curItem);

            // try to get INVENTORYGROUP from curentity, if it was deactivated, it will be null
            // try to get invgrp from curentity, if it was deactivated, it will be null
            // could use fromitem but invgrp value may have changed
            String invGrpDesc = getAttributeValue(curItem, "INVENTORYGROUP", "", null);
            String invGrpflag = getAttributeFlagValue(curItem, "INVENTORYGROUP");
            if (invGrpDesc==null)
            {
                // try fromitem, note it may not have values at deletion, it will have values at fromTime
                invGrpDesc = getAttributeValue(fromItem, "INVENTORYGROUP", "", null);
                invGrpflag = getAttributeFlagValue(fromItem, "INVENTORYGROUP");
                if (invGrpDesc==null)
                {
                    String tmp[]=getValueForNullAttr(fromItem, "INVENTORYGROUP", "DUMMY", fromItem.getProfile().getValOn());
                    invGrpDesc=tmp[0];
                    invGrpflag=tmp[1];
                    tmp[0]=null;
                    tmp[1]=null;
                }
            } // end invgrpdesc from curTime =null
            if (invGrpflag !=null)
            {
                Vector vct = null;
                if (invGrpMasterTbl.get(invGrpflag)==null)
                {
                    logDataError("Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
                    trace(D.EBUG_WARN,false,"getSuppDeviceByInvGrp() Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
                    theSet.dereference();
                    continue;
                }

                vct = (Vector)sdByInvGrpTbl.get(invGrpflag);
                if (vct==null)
                {
                    vct = new Vector();
                    sdByInvGrpTbl.put(invGrpflag,vct);
                }
                vct.addElement(theSet);
            }
            else {
                trace(D.EBUG_ERR,true,"getSuppDeviceByInvGrp() ERROR could not find INVENTORYGROUP for "+curItem.getKey()); }
        } // check each changed root

        return sdByInvGrpTbl;
    }

    /**************************************************************************************
    * B.    Supported Device Matrix Changes
    *
    * The columns are:
    *
    * Heading               Description
    * Date/Time of Change   ValFrom of the Relator = DEVSUPPORT
    * Change Type           Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
    * Last Editor           From the Entity( First 10 characters)
    * MTM                   MODEL.MACHTYPEATR &-& MODEL.MODELATR
    * SptDev MTM            SUPPDEVICE.MACHTYPESD &-& SUPPDEVICE.MODELATR
    * Announce Date         DEVSUPPORT.ANNDATE
    * FM                    Derived  see below
    * Name                  SUPPDEVICE.INTERNALNAME
    *
    * FM is derived as follows:
    *
    * Use entity SUPPDEVICE to find a matching MAPSUPPDEVICE. The matching is based on INVENTORYGROUP, and
    * FMGROUP. Given a matching entity, then this gives FMGROUPCODE. This yields a one character code.
    *
    * @param sdVct Vector with sets of SUPPDEVICE items for curTime and fromtime grouped by invgrp
    * @param chgChildVct Vector with all changed children and root found by edoc
    * @param chgRpt FMChgRpt for output
    * @param delRelatorTbl Hashtable with deleted DEVSUPPORT relator ids
    * @param fromTime String with dts of time 1 based on minimum date
    */
    private void getSuppDevMTMChgs(Vector sdVct, Vector chgChildVct, FMChgRpt chgRpt, Hashtable delRelatorTbl,
        String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector();
        String sectionTitle = FMChgSuppDevMTMSet.getSectionTitle();
        String sectionInfo = FMChgSuppDevMTMSet.getSectionInfo();
        String noneFndText = FMChgSuppDevMTMSet.getNoneFndText();

        // output this sections info
        if (sdVct==null)
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevMTMChgs() No changes found");
        }
        else
        {
            FMChgSuppDevMTMSet mtm = null;
            for (int i=0; i<sdVct.size(); i++)
            {
                EntitySet theSet = (EntitySet)sdVct.elementAt(i);
                // use cur entity as basis
                EntityItem curItem = theSet.getCurItem();  // SUPPDEVICE
                EntityItem fromItem = theSet.getFromItem();
                //boolean rootChg = false;
                Vector relIdVct = (Vector)delRelatorTbl.get(curItem.getKey());

                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevMTMChgs() checking root "+curItem.getKey());
                // was a change made in the root SUPPDEVICE?
                /*if (chgChildVct.contains(curItem.getKey()))
                {
                    trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() ROOT "+curItem.getKey()+" had some change ");
                    rootChg = true;
                }
                else
                    trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() ROOT "+curItem.getKey()+" did NOT change ");
                */

    //-- Entity:SUPPDEVICE<----Relator:DEVSUPPORT<-Entity:MODEL
                // look at delRelatorTbl to see if a relator was deleted
                if (relIdVct!=null)
                {
                    // look to see if curitem or fromitem have uplinks to 'deleted' relators in hashtable
                    // if they do, this relator will be handled later
                    for (int r=0; r<relIdVct.size(); r++)
                    {
                        String relId = (String)relIdVct.elementAt(r);
                        if (fromItem.getUpLink("DEVSUPPORT"+relId)==null &&
                            curItem.getUpLink("DEVSUPPORT"+relId)==null)
                        {
                            trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() DEVSUPPORT"+relId+
                                " relator was created and deleted in this interval for "+curItem.getKey());

                            mtm = new FMChgSDMTMDelSet(dbCurrent, profile, this,
                                    relId, curItem.getEntityID(), fromTime, curTime);
                            mtm.calculateOutput();
                            chgSetVct.addElement(mtm);
                        }
                        else {
                            trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() DEVSUPPORT"+relId+" relator was deleted for "+
                                curItem.getKey()+" BUT was found in fromitem or curitem"); }
                    }
                }

                if (curItem.getUpLinkCount()==0&& fromItem.getUpLinkCount()==0)
                {
                    // devsupport must have been created and deleted in the timeframe or never existed
                    trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() No DEVSUPPORT relator found for "+curItem.getKey());
                    continue;
                }

                // get changes
                for (int p=0; p<curItem.getUpLinkCount(); p++)
                {
                    EntityItem relItem = (EntityItem)curItem.getUpLink(p);
                    // was this item added?
                    if (fromItem.getUpLink(relItem.getKey())==null)
                    {
                        trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() relator "+relItem.getKey()+" was ADDED or RESTORED");
                        mtm = new FMChgSuppDevMTMSet(dbCurrent, profile, this, null, relItem, fromTime, curTime);
                        mtm.calculateOutput();
                        chgSetVct.addElement(mtm);
                    }
                    else
                    {
                        if (chgChildVct.contains(relItem.getKey()))// was it changed?
                        {
                            EntityItem fitem = (EntityItem)fromItem.getUpLink(relItem.getKey());
                            trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() relator "+relItem.getKey()+" had some change");
                            mtm = new FMChgSuppDevMTMSet(dbCurrent, profile, this, fitem, relItem, fromTime, curTime);
                            mtm.calculateOutput();
                            chgSetVct.addElement(mtm);
                        }
                        else
                        {
                            trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() relator "+relItem.getKey()+" did NOT change");
                            // did its' model chg?
                            /*EntityItem mdlItem = (EntityItem)relItem.getUpLink(0);
                            if (chgChildVct.contains(mdlItem.getKey()))
                            {
                                trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() parent "+mdlItem.getKey()+
                                        " had some change so add relator "+relItem.getKey());
                                EntityItem fitem = (EntityItem)fromItem.getUpLink(relItem.getKey());
                                FMChgSuppDevMTMSet mtm = new FMChgSuppDevMTMSet(dbCurrent, profile, this, fitem, relItem, fromTime, curTime);
                                mtm.calculateOutput();
                                chgSetVct.addElement(mtm);
                            }
                            else // model didn't change
                            if (rootChg)
                            {
                                trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() parent "+mdlItem.getKey()+
                                        " did NOT change BUT root child did so add relator "+relItem.getKey());
                                EntityItem fitem = (EntityItem)fromItem.getUpLink(relItem.getKey());
                                FMChgSuppDevMTMSet mtm = new FMChgSuppDevMTMSet(dbCurrent, profile, this, fitem, relItem, fromTime, curTime);
                                mtm.calculateOutput();
                                chgSetVct.addElement(mtm);
                            }
                            else
                                trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() parent "+mdlItem.getKey()+
                                        " did NOT change so skipping relator "+relItem.getKey());*/
                        }
                    }
                }
                for (int p=0; p<fromItem.getUpLinkCount(); p++)
                {
                    EntityItem relItem = (EntityItem)fromItem.getUpLink(p);
                    // was this item deleted?
                    if (curItem.getUpLink(relItem.getKey())==null)
                    {
                        trace(D.EBUG_INFO,false,"getSuppDevMTMChgs() relator "+relItem.getKey()+" was DELETED");
                        mtm = new FMChgSuppDevMTMSet(dbCurrent, profile, this,relItem, null, fromTime, curTime);
                        mtm.calculateOutput();
                        chgSetVct.addElement(mtm);
                    }
                }
            }
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgSuppDevMTMSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

    /**************************************************************************************
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
    * @param sdVct Vector with sets of SUPPDEVICE items for curTime and fromtime grouped by invgrp
    * @param chgChildVct Vector with all changed children and rootfound by edoc
    * @param chgRpt FMChgRpt for output
    * @param fromTime dts of start of interval
    */
    private void getSuppDevChgs(Vector sdVct, Vector chgChildVct, FMChgRpt chgRpt, String fromTime)
        throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
    {
        Vector chgSetVct = new Vector();
        String sectionTitle = FMChgSuppDevSet.getSectionTitle();
        String sectionInfo = FMChgSuppDevSet.getSectionInfo();
        String noneFndText = FMChgSuppDevSet.getNoneFndText();

        // output this sections info
        if (sdVct==null)
        {
            trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevChgs() No changes found");
        }
        else
        {
            for (int i=0; i<sdVct.size(); i++)
            {
                EntitySet theSet = (EntitySet)sdVct.elementAt(i);
                // use cur entity as basis
                EntityItem curItem = theSet.getCurItem();  // SUPPDEVICE
                trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevChgs() checking "+curItem.getKey());
                // was a change made in the root SUPPDEVICE?
                if (chgChildVct.contains(curItem.getKey()))
                {
                    FMChgSuppDevSet fc = null;
                    trace(D.EBUG_INFO,false,"getSuppDevChgs() "+curItem.getKey()+" had some change ");
                    fc = new FMChgSuppDevSet(dbCurrent, profile, this, theSet.getFromItem(),
                        theSet.getCurItem(), fromTime, curTime);
                    fc.calculateOutput();
                    chgSetVct.addElement(fc);
                }
                else
                {
                    trace(D.EBUG_INFO,false,"getSuppDevChgs() "+curItem.getKey()+" did NOT change ");
                    continue;  // no structure here
                }
            }
        }

        chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgSuppDevSet.getColumnHeader(), noneFndText);

        // release memory
        for (int p=0; p<chgSetVct.size(); p++)
        {
            FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
            theSet.dereference();
        }
        chgSetVct.clear();
    }

//===============================================================================================
//===============================================================================================

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @returns String attribute value
    * @throws java.lang.Exception
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue)
        throws Exception
    {
        String value = null;
        EANMetaAttribute metaAttr = null;
        EANAttribute attr = null;
        if (item==null) {
            return defValue; }

        metaAttr = item.getEntityGroup().getMetaAttribute(attCode);
        if (metaAttr==null) {
            throw new Exception("Attribute "+attCode+" NOT found in "+
                item.getEntityType()+" META data for valon: "+item.getProfile().getValOn()+".");
        }

        attr = item.getAttribute(attCode);
        if (attr == null) {
            return defValue;}

        if (attr instanceof EANFlagAttribute)
        {
            value = attr.toString(); // this is faster
            if (metaAttr.getAttributeType().equals("F"))
            {
                // Multiflags come back with newlines and possible '*'
                value = value.replace('\n',' ');
                value = value.replace('\r',' ');
                value = value.replace('*',' ');
            }

            // Get all the Flag values.
            /*MetaFlag[] mfArray = (MetaFlag[]) attr.get();
            for (int i = 0; i < mfArray.length; i++)
            {
                // get selection
                if (mfArray[i].isSelected())
                {
                    if (sb.length()>0)
                        sb.append(delim);
                    sb.append(mfArray[i].toString());
                }
            }*/
            // keep jtest happy, do something with delim
            if (delim==null) {
                System.out.print("");
            }
        }
        else if (attr instanceof EANTextAttribute)
        {
            value = attr.get().toString();
        }
        else if (attr instanceof EANBlobAttribute)
        {
            return "Blob Attribute type "+metaAttr.getAttributeType()+" for "+attCode+" NOT supported";
        }

        if (value !=null && value.length()>0) {
            return value; }
        return defValue;
    }

    /*****************************************************************************
    * Get the current Flag Value for the specified attribute, null if not set
    *
    * @param entityItem EntityItem
    * @param attrCode String attribute code to get value for
    * @returns String attribute flag code
    */
    public static String getAttributeFlagValue(EntityItem entityItem, String attrCode)
    {
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr == null) {
            return null; }

        if (attr instanceof EANFlagAttribute)
        {
            StringBuffer sb = new StringBuffer();

            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) attr.get();
            for (int i = 0; i < mfArray.length; i++)
            {
                // get selection
                if (mfArray[i].isSelected())
                {
                    if (sb.length()>0) {
                        sb.append(DELIMITER); }
                    sb.append(mfArray[i].getFlagCode());
                }
            }
            if (sb.length()>0) {  // somehow flag attribute exists but isn't set!
                return sb.toString();}
        }

        return null;
    }

    static String outputList(EntityList list) // debug
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg = null;
        if (list==null) {
            return "Null list";}
        peg =list.getParentEntityGroup();
        if (peg!=null)
        {
            sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" Parent entity items. ");
            if (peg.getEntityItemCount()>0)
            {
                StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                tmpsb.append("IDs(");
                for (int e=0; e<peg.getEntityItemCount(); e++)
                {
                    tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
                    if (tmpsb.length()>LEN256)
                    {
                        sb.append(tmpsb.toString()+FMChgLog.NEWLINE);
                        tmpsb.setLength(0);
                    }
                }
                tmpsb.append(")");
                sb.append(tmpsb.toString());
            }
            sb.append(FMChgLog.NEWLINE);
        }

        for (int i=0; i<list.getEntityGroupCount(); i++)
        {
            EntityGroup eg =list.getEntityGroup(i);
            sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
            if (eg.getEntityItemCount()>0)
            {
                StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                tmpsb.append("IDs(");
                for (int e=0; e<eg.getEntityItemCount(); e++)
                {
                    tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                    if (tmpsb.length()>LEN256)
                    {
                        sb.append(tmpsb.toString()+FMChgLog.NEWLINE);
                        tmpsb.setLength(0);
                    }
                }
                tmpsb.append(")");
                sb.append(tmpsb.toString());
            }
            sb.append(FMChgLog.NEWLINE);
        }
        return sb.toString();
    }

    /*************************************************************************************
    * Convenience classes
    **************************************************************************************/
    // a pair of items with same entity id from each time
    private static class EntitySet
    {
        private EntityItem fromItem;
        private EntityItem curItem;
        EntitySet(EntityItem fitem, EntityItem citem)
        {
            fromItem = fitem;
            curItem = citem;
        }
        EntityItem getCurItem() { return curItem;}
        EntityItem getFromItem() { return fromItem;}
        public String toString()
        {
            return " From: "+fromItem.getKey()+" Current: "+curItem.getKey();
        }

        void dereference()
        {
            fromItem = null;
            curItem = null;
        }
    }
}
