// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.io.*;

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
 * A JAVA application will be invoked via the AIX Cron facility.
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
 * A new entity will be defined in the PDH via Metadata so that DMINIT will automatically create the ODS table.
 * The entity type will be "FM Change Log Report" (FMCHGLOGRPT).
 * These entities will be created under a Workgroup named CHGLOGEXTRACT via a relator named "WG for FM Change Log Report"
 * (WGFMCHGLOGRPT).
 *
 * The metadata will be posted once available and will supersede this document. It should be noted that the
 * DMINIT/DMNET adds a few columns to each ODS table:  entitytype,  entityid, date/time of ODS insertion, and nlsid.
 *
 * The application will delete (de-activate) reports that are more than 7 days old. The PDH will retain them; however,
 * DMNET will remove them from the ODS.
 *
 * A special Workgroup will be setup named CHGLOGEXTRACT. A user (administrator) will be authorized to administer
 * the work group. This workgroup will reference a set of Workgroups to report on. The relationship is called
 * "WG for Change Log Report" (WGWGCHGRPT). The application will report on all Inventory Groups within this set
 * of Workgroups.
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgLog.java,v $
// Revision 1.23  2005/09/15 13:01:51  wendy
// MN25369700 don't use lastactive date for removed relator
//
// Revision 1.22  2005/07/14 21:27:29  wendy
// use Stopwatch even though it has jtest errors
//
// Revision 1.21  2005/07/14 20:40:32  wendy
// Change to PrintWriter to be more efficient
//
// Revision 1.20  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.19  2005/06/06 13:56:49  wendy
// Added maximum number of hours to be used for 1 day type report
//
// Revision 1.18  2005/05/12 14:31:05  wendy
// Added flushlogs()
//
// Revision 1.17  2005/05/11 19:35:02  wendy
// MN23880559 for slow perf.. split into 1 day and the rest
//
// Revision 1.16  2005/05/11 11:45:09  wendy
// Set lastran interval to 30 if not already set
//
// Revision 1.15  2005/05/10 19:20:37  wendy
// InventoryGroup flag values changed.
//
// Revision 1.14  2005/05/09 14:34:32  wendy
// Added timing
//
// Revision 1.13  2005/05/05 16:22:35  wendy
// Throw exception if meta is not found
//
// Revision 1.12  2005/05/05 14:01:52  wendy
// Setup for CR042605498
//
// Revision 1.11  2005/03/16 18:33:58  wendy
// Use system.exit() to notify shell script of success/failure
//
// Revision 1.10  2005/03/11 14:53:20  wendy
// Chgd method name, protect against missing VE
//
// Revision 1.9  2005/03/11 01:57:18  wendy
// CR0302055218 start 1 day report at last valid run time
//
// Revision 1.8  2005/02/18 15:58:08  wendy
// Added more debug output
//
// Revision 1.7  2005/02/17 17:00:02  wendy
// Attempt deletion of reports a second time, if it fails the first time
//
// Revision 1.6  2004/11/03 12:34:02  wendy
// cvs error
//
// Revision 1.4  2004/11/02 19:22:40  wendy
// containsUpLink() and containsDownLink() were removed from EANEntity, replaced them.
//
// Revision 1.3  2004/10/26 16:14:03  wendy
// more debug msgs
//
// Revision 1.2  2004/10/20 00:36:07  wendy
// Added SuppDev support
//
// Revision 1.1  2004/10/15 23:38:47  wendy
// Init for FM Chg Log application
//
public class FMChgLog
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.23 $";
    private Database dbCurrent;
    private Profile profile = null;
    private PrintWriter dataErrorWriter;
    private PrintWriter traceWriter;
    private static final int MAX_HRS_SINCE_LASTRUN;
    private static final int MAX_AGE;
    static {
        MAX_AGE = FMChgProperties.getMaxAge();
        MAX_HRS_SINCE_LASTRUN = FMChgProperties.getMaxHoursSinceLastRan();
    }
    private static final String DELETEACTION_NAME = "DELFMCHGLOGRPT";
    private int debugLevel= D.EBUG_SPEW; // middleware highest level = 4
    private Vector dataErrorVct = new Vector();
    private static final String LASTRUN_TYPE = "$$Last Run$$";
    private String lastRanDate;
    private Stopwatch swTimer = new Stopwatch();
    private String curDate;
    private EntityItem chglogWGitem;

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    static final int HOURS_IN_DAY =24;
    static final int LINE_LIMIT = 1024;

    /********************************************************************************
    * Constructor
    * Setup trace and data error logs, login and get profile based on property settings
    * get EntityList for profile, this will have CHGLOGEXTRACT WG
    * pull extract using this WG as root to get all current FMCHGLOGRPT and FEATURE,
    * SUPPDEVICE and MACHTYPE
    * deactivate all old FMCHGLOGRPT, create FMChgLogGen to do the rest of the setup
    * and file generation, and save it to PDH
    * @param overrideDate String to override current dts
    * @param overrideLastRanDate String to override last ran dts
    */
    public FMChgLog(String overrideDate, String overrideLastRanDate)
    {
        try{
            String now = "";
            String extractName = "EXRPT3WGCHG";
            EntityList list = null;
            EntityList wglist = null;
            EntityGroup wgGrp = null;
            boolean delRptsDone = false;
            FMChgLogGen chgLog = null;
            debugLevel = FMChgProperties.getDebugTraceLevel();

            dbCurrent = new Database();

            now = dbCurrent.getNow(0);
            curDate = now;
            swTimer.start();

            // use override date if not null
            if (overrideDate!=null) {
                curDate = overrideDate;
            }

            /* In general, a Writer sends its output immediately to the underlying character or byte stream.
            * Unless prompt output is required, it is advisable to wrap a BufferedWriter around any Writer
            * whose write() operations may be costly, such as FileWriters and OutputStreamWriters. For example,
            * PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("foo.out")));
            * will buffer the PrintWriter's output to the file. Without buffering, each invocation of a print()
            * method would cause characters to be converted into bytes that would then be written immediately
            * to the file, which can be very inefficient. */

            // create files for data errorlog and trace
            traceWriter = new PrintWriter(new BufferedWriter(new FileWriter("FMChgLogTrace.txt")));
            dataErrorWriter = new PrintWriter(new BufferedWriter(new FileWriter("FMChgLogDataError.txt")));

            trace(D.EBUG_ERR,false,"FMChgLog() Starting FMChgLog for "+curDate+
                " using "+NEWLINE+"FMChgLog.VERSION: "+VERSION+NEWLINE+
                "FMChgFCSet.VERSION: "+FMChgFCSet.VERSION+NEWLINE+
                "FMChgFCTransSet.VERSION: "+FMChgFCTransSet.VERSION+NEWLINE+
                "FMChgFMRpt.VERSION: "+FMChgFMRpt.VERSION+NEWLINE+
                "FMChgGFWSet.VERSION: "+FMChgGFWSet.VERSION+NEWLINE+
                "FMChgISOCalendar.VERSION: "+FMChgISOCalendar.VERSION+NEWLINE+
                "FMChgLogGen.VERSION: "+FMChgLogGen.VERSION+NEWLINE+
                "FMChgMTMDelSet.VERSION: "+FMChgMTMDelSet.VERSION+NEWLINE+
                "FMChgMTMSet.VERSION: "+FMChgMTMSet.VERSION+NEWLINE+
                "FMChgProperties.VERSION: "+FMChgProperties.VERSION+NEWLINE+
                "FMChgRFASet.VERSION: "+FMChgRFASet.VERSION+NEWLINE+
                "FMChgRpt.VERSION: "+FMChgRpt.VERSION+NEWLINE+
                "FMChgSDMTMDelSet.VERSION: "+FMChgSDMTMDelSet.VERSION+NEWLINE+
                "FMChgSDRpt.VERSION: "+FMChgSDRpt.VERSION+NEWLINE+
                "FMChgSet.VERSION: "+FMChgSet.VERSION+NEWLINE+
                "FMChgSuppDevMTMSet.VERSION: "+FMChgSuppDevMTMSet.VERSION+NEWLINE+
                "FMChgSuppDevSet.VERSION: "+FMChgSuppDevSet.VERSION+NEWLINE+
                "FMChgWritePDH.VERSION: "+FMChgWritePDH.VERSION+NEWLINE);

            logDataError("Starting FMChgLog for "+curDate);

            if (overrideDate!=null)
            {
                trace(D.EBUG_ERR,false,"FMChgLog() WARNING using override date "+curDate+" instead of now: "+now);
                logDataError("FMChgLog WARNING using override date "+curDate+" instead of now: "+now);
            }

            // must login and get profile
            getProfile();
            if (profile==null)
            {
                System.err.println("** Profile is null!! **");
                trace(D.EBUG_ERR,true,"FMChgLog() ** Profile is null!! exiting **");
                traceWriter.close();
                dataErrorWriter.close();
                System.exit(1);
            }

            // get CHGLOGEXTRACT WG
            wglist = dbCurrent.getEntityList(profile);
            trace(D.EBUG_INFO,false,"FMChgLog() EntityList for WG: contains the following entities: ");
            trace(D.EBUG_INFO,false,FMChgLogGen.outputList(wglist));

            wgGrp = wglist.getEntityGroup("WG"); // this has CHGLOGEXTRACT WG
            chglogWGitem = wgGrp.getEntityItem(0);
            // "EXRPT3WGCHG" start at WG and get all possible WG with their MACHTYPE
            // get all current FMCHGLOGRPT and FEATURE,SUPPDEVICE and MACHTYPE to get list of InventoryGroups
            list = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, extractName),
                wgGrp.getEntityItemsAsArray());

            trace(D.EBUG_INFO,false,"FMChgLog() EntityList for "+extractName+": contains the following entities: ");
            trace(D.EBUG_INFO,false,FMChgLogGen.outputList(list));

            if (list.getEntityGroup("WG")==null)
            {
                System.err.println("Extract: "+extractName+" can not be found.  Execution is terminated!"+NEWLINE+
                    "Extracts EXRPT3WGSETUP, EXRPT3FCTRANS and EXRPT3SDMDL may be missing too!");
                trace(D.EBUG_ERR,true,"FMChgLog() Extract: "+extractName+" can not be found.  Execution is terminated!");
                trace(D.EBUG_ERR,true,"FMChgLog() Extracts EXRPT3WGSETUP, EXRPT3FCTRANS and EXRPT3SDMDL may be missing too!");

                traceWriter.close();
                dataErrorWriter.close();
                list.dereference();
                wglist.dereference();
                System.exit(2);
            }

            displayWGcounts(list.getEntityGroup("WG")); // display FEATURE,SUPPDEVICE and MACHTYPE for each WG.. debug only
            flushLogs();

            // deactivate old report entities and determine last ran date,
            delRptsDone = deactivateOldReports(list.getEntityGroup("FMCHGLOGRPT"));

            if (overrideLastRanDate != null)
            {
                trace(D.EBUG_ERR,false,"FMChgLog() WARNING using override last ran date: "+overrideLastRanDate+" instead of: "+lastRanDate);
                lastRanDate = overrideLastRanDate;
            }
            else
            {
                trace(D.EBUG_ERR,false,"************ Using last ran date: "+lastRanDate);
            }

            // build the logs for each WG.. one WG for one BR for one InvGrp in first release
            chgLog = new FMChgLogGen(this, dbCurrent, profile, curDate, lastRanDate, chglogWGitem,list);

            list = null; // list was dereferenced in FMChgLogGen to free up memory faster
            System.gc();

            trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of initialization timing "+getElapsedTime()+" ***************");

            trace(D.EBUG_INFO,false,"FMChgLog() ***** Start build of all Feature Matrix logs ***************");
            // build the Feature Matrix logs
            chgLog.buildFMLogs();
            trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of all Feature Matrix logs timing "+getElapsedTime()+" ***************");

            trace(D.EBUG_INFO,false,"FMChgLog() ***** Start build of all Supported Device logs ***************");
            // build the Supported Device logs
            chgLog.buildSDLogs();
            trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of all Supported Device logs timing "+getElapsedTime()+" ***************");

            // release memory
            chgLog.dereference();

            // update the last ran date
            extractName = "EXRPT3CHGRPT"; // small.. just get FMCHGLOGRPT
            // list was dereferenced in FMChgLogGen so must get it again, use a smaller VE
            list = dbCurrent.getEntityList(profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    wgGrp.getEntityItemsAsArray());

            trace(D.EBUG_INFO,false,"FMChgLog() EntityList for "+extractName+": contains the following entities: ");
            trace(D.EBUG_INFO,false,FMChgLogGen.outputList(list));

            if (list.getEntityGroupCount()==0) // check for missing meta
            {
                trace(D.EBUG_ERR,false,"ERROR:  Extract "+extractName+" is missing!");
                list.dereference();
                // use big one for now!
                // get all current FMCHGLOGRPT and FEATURE,SUPPDEVICE and MACHTYPE to get list of InventoryGroups
                extractName = "EXRPT3WGCHG";
                list = dbCurrent.getEntityList(profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    wgGrp.getEntityItemsAsArray());
            }

            updateLastRanDate(list.getEntityGroup("FMCHGLOGRPT"), "30");

            if (!delRptsDone)  // something failed in deleting the reports, try again
            {
                trace(D.EBUG_ERR,false,"Deletion of old reports failed the first time, trying again");
                // last ran date will be determined but is meaningless at this point
                deactivateOldReports(list.getEntityGroup("FMCHGLOGRPT"));
            }
            list.dereference(); // release memory

            wglist.dereference();
            trace(D.EBUG_ERR,false,"FMChgLog() ***************** Total run time "+formatTime(swTimer.getFinish()));
        }catch(Throwable exc)
        {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            System.err.println("ERROR: "+exc.getMessage());
            System.err.println(exBuf.getBuffer().toString());
            trace(D.EBUG_ERR,true,"ERROR: "+exc.getMessage());
            trace(D.EBUG_ERR,true,exBuf.getBuffer().toString());
            if (dataErrorWriter!=null) {
                dataErrorWriter.close(); }
            if (traceWriter!=null) {
                traceWriter.close(); }
            System.exit(3);  // System.exit will bypass finally block and also rest of main()
        }
        finally
        {
            if (dataErrorWriter!=null) {
                dataErrorWriter.close(); }
            if (traceWriter!=null) {
                traceWriter.close(); }
        }
    }
    String getElapsedTime() { return formatTime(swTimer.getLap()); }

    /**************************************************************************************
    * Get FMCHGLOGRPT entities, look for "$$Last Run$$" report type, if doesn't exist
    * create it.
    * @param interval String with interval to create/update (30 for all rpts, 1 for 1 day rpts)
    */
    void updateLastRanDate(String interval) throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.rmi.RemoteException, Exception
    {
        String extractName = "EXRPT3CHGRPT"; // small.. just get FMCHGLOGRPT
        EntityList list = null;
        if(!FMChgProperties.getUpdateLastRanDTS())  // allow debug to not update this date!
        {
            trace(D.EBUG_ERR,false,"Properties file specified no update for last ran DTS");
            return;
        }

        // update the last ran date
        list = dbCurrent.getEntityList(profile, new ExtractActionItem(null, dbCurrent, profile, extractName),
            new EntityItem[] { chglogWGitem });

        updateLastRanDate(list.getEntityGroup("FMCHGLOGRPT"), interval);
        list.dereference(); // release memory
    }

    /**************************************************************************************
    * Get FMCHGLOGRPT entities, look for "$$Last Run$$" report type, if doesn't exist
    * create it.
    * @param chglogGrp EntityGroup of FMCHGLOGRPT entities
    * @param interval String with interval to create/update (30 for all rpts, 1 for 1 day rpts)
    */
    private void updateLastRanDate(EntityGroup chglogGrp, String interval) throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.rmi.RemoteException, Exception
    {
        EntityItem lastRanItem=null;
        if(!FMChgProperties.getUpdateLastRanDTS())  // allow debug to not update this date!
        {
            trace(D.EBUG_ERR,false,"Properties file specified no update for last ran DTS");
            return;
        }

        for (int i=0; i<chglogGrp.getEntityItemCount(); i++)
        {
            EntityItem chglogItem = chglogGrp.getEntityItem(i);
            //Report Type (FMCHGTTYPE) = {FM Chg Rpt | Spt Dev Chg Rpt | $$Last Run$$}
            String fmChgType = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTTYPE", "", "");
            // get interval.. if it is null, then this is the original marker used for all reports
            String fminterval = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTINTERVAL", "", "30");
            if (fmChgType.equals(LASTRUN_TYPE) && fminterval.equals(interval))
            {
                lastRanItem = chglogItem;
                break;
            }
        }
        if (lastRanItem==null)  // doesn't exist yet so create one
        {
            // create the entity
            CreateActionItem cai = new CreateActionItem(null, dbCurrent, profile, FMChgWritePDH.CREATEACTION_NAME);

            EntityList list = new EntityList(dbCurrent, profile, cai, new EntityItem[] { chglogWGitem });
            EntityGroup eGrp = list.getEntityGroup(FMChgWritePDH.REPORT_TYPE);
            if (eGrp.getEntityItemCount() == 1)
            {
                EANMetaFlagAttribute mfa = null;
                EntityItem relator =null;
                String flagcode = null;
                // write the attributes
                lastRanItem = eGrp.getEntityItem(0);

                // save the Text attributes
                setText(lastRanItem,"FMCHGTTYPE", LASTRUN_TYPE); // FMCHGTTYPE      T   ReportType  Report Type
                setText(lastRanItem,"FMCHGDTS",curDate);         // FMCHGDTS        T   DTS of Rpt  Date/Time of Report
                // 30 indicates the complete set of reports completed
                setText(lastRanItem,"FMCHGTINTERVAL", interval); // FMCHGTINTERVAL  T   ReportInterval  Report Interval
                /*
                ERRORs
                *Brand - empty value: Required information missing
                *Inventory Group - empty value: Required information missing
                so pick first one from meta to use as the value! this will prevent errors if meta is
                changed and hardcoded value no longer exists!
                */
                mfa = (EANMetaFlagAttribute)eGrp.getMetaAttribute("BRAND");
                flagcode = mfa.getMetaFlag(0).getFlagCode();
                setUniqueFlag(lastRanItem,"BRAND",flagcode); //BRAND            U   Brand   Brand

                mfa = (EANMetaFlagAttribute)eGrp.getMetaAttribute("INVENTORYGROUP");
                flagcode = mfa.getMetaFlag(0).getFlagCode();
                setUniqueFlag(lastRanItem,"INVENTORYGROUP",flagcode); //INVENTORYGROUP  U   Inv Group   Inventory Group

                // must commit new entity and the relator to the PDH
                lastRanItem.commit(dbCurrent, null);
                // Commit the relator too
                relator = (EntityItem) lastRanItem.getUpLink(0);
                relator.commit(dbCurrent, null);

                trace(D.EBUG_WARN,false,"FMChgLog created Entity: "+lastRanItem.getKey()+" "+lastRanItem+
                    " and Relator: "+relator.getKey()+" interval: "+interval+" for type: "+LASTRUN_TYPE);
            }
            else
            {
                trace(D.EBUG_ERR,true,"FMChgLog ERROR: Can not create "+FMChgWritePDH.REPORT_TYPE+
                        " entity for interval: "+interval+" and type: "+FMChgLog.LASTRUN_TYPE);
            }
            list.dereference();
        }
        else
        {
            String curinterval = FMChgLogGen.getAttributeValue(lastRanItem,"FMCHGTINTERVAL", "", null);
            if (curinterval==null)  {// original did not have interval set
                setText(lastRanItem,"FMCHGTINTERVAL", "30"); // FMCHGTINTERVAL  T   ReportInterval  Report Interval
            }
            setText(lastRanItem,"FMCHGDTS",curDate);         // FMCHGDTS        T   DTS of Rpt  Date/Time of Report

            // must commit changes
            lastRanItem.commit(dbCurrent, null);

            trace(D.EBUG_WARN,false,"FMChgLog updated Entity: "+lastRanItem.getKey()+" "+lastRanItem+
                    " for interval: "+interval+" and type: "+LASTRUN_TYPE);
        }
    }

    /********************************************************************************
    * Create unique flag attribute and set it in lastran report entity
    * @param lastRanItem EntityItem to add/set attribute to
    * @param attrCode String with attribute code
    * @param flagCode String with value
    */
    private void setUniqueFlag(EntityItem lastRanItem,String attrCode, String flagCode) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EntityGroup eGrp = lastRanItem.getEntityGroup();
        EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
        if (ma.getAttributeType().charAt(0)=='U')
        {
            MetaFlag[] mfa =null;
            EANFlagAttribute flagAttr= new SingleFlagAttribute(lastRanItem, null, (MetaSingleFlagAttribute) ma);
            lastRanItem.putAttribute(flagAttr);
            mfa = (MetaFlag[]) flagAttr.get();
            for (int i = 0; i < mfa.length; i++)
            {
                if (mfa[i].getFlagCode().equals(flagCode))
                {
                    mfa[i].setSelected(true);
                    flagAttr.put(mfa);
                    break;
                }
            }
        }
    }
    /********************************************************************************
    * Create text attribute if needed and set it in lastran report entity
    * @param lastRanItem EntityItem to add/set attribute to
    * @param attrCode String with attribute code
    * @param value String with value
    */
    private void setText(EntityItem lastRanItem, String attrCode, String value) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EntityGroup eGrp = lastRanItem.getEntityGroup();
        EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
        if (ma.getAttributeType().charAt(0) == 'T')
        {
            EANAttribute textAttr = lastRanItem.getAttribute(attrCode);
            if (textAttr == null)
            {
                textAttr = new TextAttribute(lastRanItem, null, (MetaTextAttribute) ma);
                lastRanItem.putAttribute(textAttr);
                textAttr.put(value);
            }
            textAttr.put(value);
        }
    }

    // display FEATURE,SUPPDEVICE and MACHTYPE for each WG.. debug only
    private void displayWGcounts(EntityGroup wgGrp) throws Exception
    {
        for (int i=0; i<wgGrp.getEntityItemCount(); i++)
        {
            EntityItem wgItem = wgGrp.getEntityItem(i);
            int mtCnt =0;
            int fCnt = 0;
            int sCnt = 0;
            // -- Entity:WG---->Relator:WGMACHINETYPE--->Entity:MACHTYPE
            // -- Entity:WG---->Assoc:WGFEATUREA--->Entity:FEATURE
            // -- Entity:WG---->Relator:WGMODEL--->Entity:MODEL
            // -- Entity:MODEL---->Relator:DEVSUPPORT--->Entity:SUPPDEVICE
            for (int d=0; d<wgItem.getDownLinkCount(); d++)
            {
                EANEntity entityLink = wgItem.getDownLink(d);
                if (entityLink.getEntityType().equals("WGMACHINETYPE")) {
                    mtCnt++; }
                if (entityLink.getEntityType().equals("WGFEATUREA")) {
                    fCnt++; }
                if (entityLink.getEntityType().equals("WGMODEL"))
                {
                    for (int d2=0; d2<entityLink.getDownLinkCount(); d2++)
                    {
                        EANEntity entity = entityLink.getDownLink(d2);
                        if (entity.getEntityType().equals("MODEL"))
                        {
                            for (int d3=0; d3<entity.getDownLinkCount(); d3++)
                            {
                                EANEntity entity2 = entity.getDownLink(d3);
                                if (entity2.getEntityType().equals("DEVSUPPORT")) {
                                    sCnt++; }
                            }
                        }
                    }
                }
            }
            trace(D.EBUG_INFO,false,"FMChgLog() "+wgItem.getKey()+" for "+
                FMChgLogGen.getAttributeValue(wgItem, "NAME", "", "")+
                " has "+mtCnt+" MACHTYPEs, "+fCnt+" FEATUREs, "+sCnt+" SUPPDEVICEs tied to it");
            if ((mtCnt+fCnt+sCnt)==0) {
                trace(D.EBUG_WARN,false,"******** WARNING "+wgItem.getKey()+" for "+
                    FMChgLogGen.getAttributeValue(wgItem, "NAME", "", "")+
                    " will NOT have changelog reports generated.  No entities are tied to this WG");
            }
        }
    }

    /******************************************************************************
    * Deactivate old entities
    * These entities were created under a Workgroup named CHGLOGEXTRACT via a relator named "WG for FM Change Log Report"
    * (WGFMCHGLOGRPT).
    *
    * The application will delete (de-activate) reports that are more than 7 days old. The PDH will retain them; however,
    * DMNET will remove them from the ODS.
    *
    * @param chglogGrp EntityGroup with all FMCHGLOGRPT entities in PDH
    * @return boolean  true if successful
    */
    private boolean deactivateOldReports(EntityGroup chglogGrp)
    {
        try{
            Vector delVct = new Vector();
            // get 7 day timestamp
            FMChgISOCalendar isoDate = new FMChgISOCalendar(curDate);
            String delDate = isoDate.getAdjustedDate(MAX_AGE);
            String dts30 = null;
            String dts1 = null;
            trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() delDate "+delDate+" item count: "+chglogGrp.getEntityItemCount());

            // get creation date for each entity
            for (int i=0; i<chglogGrp.getEntityItemCount(); i++)
            {
                EntityItem chglogItem = chglogGrp.getEntityItem(i);
                //Report Type (FMCHGTTYPE) = {FM Chg Rpt | Spt Dev Chg Rpt | $$Last Run$$}
                String fmChgType = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTTYPE", "", "");
                // FMCHGDTS     T   DTS of Rpt  Date/Time of Report
                String dts = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGDTS", "", null);
                if (fmChgType.equals(LASTRUN_TYPE))
                {
                    // get interval.. if it is null, then this is the original marker used for all reports
                    String fminterval = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTINTERVAL", "", "30");
                    if (fminterval.equals("1"))  // use 1 day marker for DTS, not 30 day marker
                    {
                        dts1 = dts;
                    }
                    if (fminterval.equals("30"))
                    {
                        dts30 = dts;
                    }

                    trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() skipping "+chglogItem.getKey()+
                        " it is "+LASTRUN_TYPE+" DTS: "+dts+" interval: "+fminterval);
                    continue;
                }

                if (dts==null)  // error condition, should not happen
                {
                    EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, chglogItem);
                    ChangeHistoryItem chi = eHistGrp.getChangeHistoryItem(0); // first one is creation
                    trace(D.EBUG_ERR,true,"FMChgLog().deactivateOldReports() ERROR "+chglogItem.getKey()+" has null FMCHGDTS! will be deactivated, create date: "+chi.getChangeDate());
                /*if (delDate.compareTo(chi.getChangeDate())>=0) // deactivate this entity and relator
                */
                    delVct.addElement(chglogItem);
                    continue;
                }
    //for debug, display entity item
    //display(dbCurrent, chglogItem);

                if (delDate.compareTo(dts)>=0) // deactivate this entity and relator
                {
                    trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() deactivating "+chglogItem.getKey());
                    delVct.addElement(chglogItem);
                }
            }
            // determine last ran date,
            if (dts1!=null)  // use 1 day marker for DTS, not 30 day marker
            {
                lastRanDate = dts1;
                trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() set lastRanDate using 1 day marker: "+lastRanDate);
            }
            else
            {
                lastRanDate = dts30;
                trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() set lastRanDate using 30 day marker: "+lastRanDate);
            }

            if (lastRanDate !=null)  // if lastRanDate is more than 6 days (144 hrs), set it to null
            {
                int diffHr = isoDate.getDiffHours(lastRanDate);
                if (diffHr > MAX_HRS_SINCE_LASTRUN)
                {
                    long diffDay = diffHr/HOURS_IN_DAY;
                    trace(D.EBUG_INFO,false,"******** WARNING Time since last valid run was too long. Max HRs: "+
                        MAX_HRS_SINCE_LASTRUN+" calc HRs: "+diffHr+" Days: "+diffDay+" setting lastrandate to NULL");
                    lastRanDate=null;
                }
            }

            if (delVct.size()>0)
            {
                EntityItem[] eiArray = new EntityItem[delVct.size()];
                delVct.copyInto(eiArray);
                deactivateReports(eiArray);
                delVct.clear();
            }
            return true;
        }
        catch(Throwable exc)
        {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            System.err.println("ERROR: "+exc.getMessage());
            System.err.println(exBuf.getBuffer().toString());
            trace(D.EBUG_ERR,true,"ERROR: "+exc.getMessage());
            trace(D.EBUG_ERR,true,exBuf.getBuffer().toString());
            return false;
        }
    }
    /******************************************************************************
    * Deactivate these entities
    * @param chglogItemArray EntityItem[] to deactivate
    */
    private void deactivateReports(EntityItem[] chglogItemArray)  throws
        COM.ibm.opicmpdh.middleware.LockException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.sql.SQLException
    {
        DeleteActionItem deleteActionItem = new DeleteActionItem(null, dbCurrent, profile, DELETEACTION_NAME);
        deleteActionItem.setEntityItems(chglogItemArray);
        deleteActionItem.executeAction(dbCurrent, profile);
    }

    /******************************************************************************
    * Flush the logs
    */
    void flushLogs()
    {
        if (dataErrorWriter!=null) {
            dataErrorWriter.flush(); }
        if (traceWriter!=null) {
            traceWriter.flush(); }
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
        if (addToMWlog) {
            D.ebug(level, msg); }

        if (level <= debugLevel &&
            traceWriter!=null)
        {
            // prevent lines longer than 2048 for aix 'vi' and grep
            if (msg.indexOf(NEWLINE)==-1 && msg.length()>LINE_LIMIT)
            {
                String msglines = FMChgLogGen.parseIntoLines(msg, LINE_LIMIT);
                StringTokenizer stLine = new StringTokenizer(msglines, NEWLINE);
                while (stLine.hasMoreTokens()) {
                    traceWriter.println(stLine.nextToken());
                }
            }
            else {
                traceWriter.println(msg);
            }
        }
    }

    /********************************************************************************
    * Write data error msgs
    * @param msg String msg
    */
    void logDataError(String msg)
    {
        if (dataErrorWriter!=null && !dataErrorVct.contains(msg))
        {
            dataErrorWriter.println(msg);
            dataErrorVct.addElement(msg);  // just write it out once
        }
    }

    /*****************************************************************************
    * Login and get a Profile object
    */
    private void getProfile()
        throws VersionException, LoginException, java.sql.SQLException, MiddlewareException
    {
        String enterprise = FMChgProperties.getEnterprise();
        String roleCode = FMChgProperties.getRoleCode();
        String passwd = FMChgProperties.getUserPassword();
        String userid = FMChgProperties.getUserid();
        String versionLiteral = FMChgProperties.getVersionLiteral();
        ProfileSet profileSet = dbCurrent.login(userid, passwd, versionLiteral);
        trace(D.EBUG_INFO,false,"FMChgLog.getProfile() userid: "+userid+" profileSet.size() "+profileSet.size());

        // find role and set it as active connection item
        for (int i = 0,c=profileSet.size(); i < c; i++)
        {
            // connection items contain connId, valOn, effOn, enterprise, etc..
            // set role to be used, like PSG Div Admin
            Profile pf = profileSet.elementAt(i);
            trace(D.EBUG_INFO,false,"FMChgLog.getProfile() profile["+i+"] openid: "+pf.getOPWGID()+" role: <"+pf.getRoleCode()+
                "> enterprise: <"+pf.getEnterprise()+"> looking for role: <"+roleCode+"> enterprise: <"+enterprise+">");
            if (pf.getRoleCode().equals(roleCode)&& pf.getEnterprise().equals(enterprise))
            {
                Vector nlsVct = null;
                profileSet.setActiveProfile(i);
                pf.setValOn(curDate);
                pf.setEffOn(curDate);

                // find nls and set it as active nls item
                nlsVct = profileSet.getActiveProfile().getReadLanguages();
                for (int ii=0, ci=nlsVct.size(); ii<ci; ii++)
                {
                    NLSItem nlsObj = (NLSItem)nlsVct.elementAt(ii);
                    if (nlsObj.getNLSID() ==1) {
                        pf.setReadLanguage(nlsObj); }
                }
                profile = pf;
                return;
            }
        }
        trace(D.EBUG_ERR,true,"FMChgLog.getProfile() could not find profile for role: <"+roleCode+"> enterprise: <"+enterprise+">");
    }

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        profile = null;
        dbCurrent = null;
        dataErrorWriter = null;
        traceWriter= null;
        dataErrorVct.clear();
        dataErrorVct = null;
        swTimer = null;
        curDate = null;
        chglogWGitem = null;
    }

    /********************************************************************************
    * convert time to string format, replace Stopwatch.format because it only takes a long
    * @param dtime double
    * @return String time as 7d23h59m59.999s
    */
    public final static String formatTime(double dtime)
    {
        /*  prevent jtest casting error */
        Double d = new Double(dtime);
        return Stopwatch.format(d.longValue());
    }

    /********************************************************************************
    * Main
    * @param args
    */
    public static void main(String[] args)
    {
        FMChgLog theFM = null;
        String overrideDate = null;
        String overrideLastRanDate = null;
        if (args.length>0)
        {
            overrideDate = args[0];
            if (overrideDate.equals("0")) { // used when only want to override last run date
                overrideDate = null; }
        }
        if (args.length>1) {
            overrideLastRanDate = args[1]; }

        // allow current timestamp and last rundate to be overridden
        theFM = new FMChgLog(overrideDate, overrideLastRanDate);
        theFM.dereference();
        System.exit(0);  // checked by shell script
    }

/*
what are IG and BR now?
EANMetaFlagAttribute mfa = (EANMetaFlagAttribute)chglogGrp.getMetaAttribute("BRAND");
System.err.println("BRAND");
        for (int i = 0; i < mfa.getMetaFlagCount(); i++) {
System.err.println(mfa.getMetaFlag(i)+"["+mfa.getMetaFlag(i).getFlagCode()+"]");
        }
mfa = (EANMetaFlagAttribute)chglogGrp.getMetaAttribute("INVENTORYGROUP");
System.err.println("INVENTORYGROUP");
        for (int i = 0; i < mfa.getMetaFlagCount(); i++) {
System.err.println(mfa.getMetaFlag(i)+"["+mfa.getMetaFlag(i).getFlagCode()+"]");
        }
*/
    static void display(Database dbCurrent2, EntityItem entityItem) throws Exception
    {
        EntityGroup eg = entityItem.getEntityGroup();
        System.out.println(NEWLINE+"################"+NEWLINE+"Attribute info for: "+entityItem.getKey()+NEWLINE);

        // list attributes
        for (int ai=0; ai<eg.getMetaAttributeCount(); ai++)
        {
            EANMetaAttribute metaAttr = eg.getMetaAttribute(ai);
            EANAttribute attr = entityItem.getAttribute(metaAttr.getAttributeCode());
            System.out.print("Attribute Code: "+metaAttr.getAttributeCode());
            if (attr instanceof EANBlobAttribute)
            {
                EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                byte[] byteArray = blobAtt.getBlobValue();
                String report = null;
                if (byteArray == null)
                {
                    try {
                        byteArray = blobAtt.getBlobValue(null, dbCurrent2);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                try{
                    if (byteArray != null) {
                        report = new String(byteArray,"UTF8"); }
                }catch(java.io.UnsupportedEncodingException ee)
                {
                    if (byteArray != null) {
                        report = new String(byteArray);}
                    System.err.println("display() "+ee.getMessage());
                }
                System.out.println(" BLOB:"+NEWLINE+report);

                System.out.println("BLOB getBlobFileName(): "+blobAtt.getBlobFileName());
                System.out.println("BLOB getBlobExtension(): "+blobAtt.getBlobExtension());
                /*OPICMBlobValue obv = blobAtt.getOPICMBlobValue();
                System.out.println("BLOB getOPICMBlobValue(): "+obv);
                if (obv!=null)
                {
                    System.out.println("OPICMBlobValue getBlobExtension(): "+obv.getBlobExtension());
                    byteArray = obv.getBlobValue();
                    report = null;
                    if (byteArray != null)
                        report = new String(byteArray);
                System.out.println("OPICMBlobValue.getBlobValue():\n"+report);
                }*/

            }
            else
            {
                System.out.println(" value: "+FMChgLogGen.getAttributeValue(entityItem, metaAttr.getAttributeCode(), ", ", null));
            }
        }
        System.out.println("################");
    }
} // end-class FMChgLog
