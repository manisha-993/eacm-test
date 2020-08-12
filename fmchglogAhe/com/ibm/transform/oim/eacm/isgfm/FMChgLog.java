// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

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
 * If a report exceeds 'max_pdh_process_size' value in the properties file then it will not be put into the
 * PDH until all processing is completed.  Memory must be doubled to convert the string into a byte[]
 * for the report blob and there isn't enough room if the entitylists from time1 and time2 are still in memory.
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
// Revision 1.10  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.9  2009/11/04 16:57:26  wendy
// MN41057882 - WGMACHINETYPE relator was replaced by WGMACHTYPEA association.
//
// Revision 1.8  2009/06/25 21:01:27  wendy
// MN39715268 - Support run of 1 day only reports for a subset of InventoryGroups
//
// Revision 1.7  2009/06/23 12:31:48  wendy
// Prevent NPE in deactivate if FMCHGLOGRPT item is corrupted (MN39715268)
//
// Revision 1.6  2006/05/03 17:59:08  wendy
// Added more calls to garbage collection
//
// Revision 1.5  2006/05/01 21:31:07  wendy
// Added property for max size to write to the PDH while processing.
// This was done to reduce memory usage.  If a file is too large it will
// be written to the server and then moved to the PDH after lists are freed.
//
// Revision 1.4  2006/04/19 19:19:57  wendy
// Added check for duplicate 'last ran' records.  Deactivate if found.
//
// Revision 1.3  2006/04/03 22:04:37  wendy
// OIM3.0b datamodel and Supported Device changes
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
public class FMChgLog
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.10 $";
    private Database dbCurrent;
    private Profile profile = null;
    private PrintWriter dataErrorWriter;
    private PrintWriter traceWriter;
    private FMChgWritePDH pdhWriter=null;
    private static final int MAX_HRS_SINCE_LASTRUN;
    private static final int MAX_AGE;
    static {
        MAX_AGE = FMChgProperties.getMaxAge();
        MAX_HRS_SINCE_LASTRUN = FMChgProperties.getMaxHoursSinceLastRan();
    }
    private static final String DELETEACTION_NAME = "DELFMCHGLOGRPT";
    private int debugLevel= D.EBUG_SPEW; // middleware highest level = 4
    private Vector<String> dataErrorVct = new Vector<String>();
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
    * pull extract using this WG as root to get all current FMCHGLOGRPT and MACHTYPE
    * deactivate all old FMCHGLOGRPT, create FMChgLogGen to do the rest of the setup
    * and file generation, and save it to PDH
    * @param overrideDate String to override current dts
    * @param overrideLastRanDate String to override last ran dts
    */
    public FMChgLog(String overrideDate, String overrideLastRanDate)
    {
        try{
            String now = "";
            String extractName = "EXRPT3BWGCHG";
            EntityList list = null;
            EntityList wglist = null;
            EntityGroup wgGrp = null;
            boolean delRptsDone = false;
            FMChgLogGen chgLog = null;
            Vector<FMChgRpt> chgRptVct=null;
            debugLevel = FMChgProperties.getDebugTraceLevel();

            dbCurrent = new Database();  // requires javax/mail/internet/MimeMessage (j2ee.jar)

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
            
    		File folder = new File(FMChgLogGen.WORK_FOLDER);
    		if (folder.exists()) {
    			// delete any serialized files
    			deleteSerializedFiles();
    		} else {
    			folder.mkdirs();
    		}

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

            if (overrideDate!=null)  {
                trace(D.EBUG_ERR,false,"FMChgLog() WARNING using override date "+curDate+" instead of now: "+now);
                logDataError("FMChgLog WARNING using override date "+curDate+" instead of now: "+now);
            }

            // must login and get profile
            getProfile();
            if (profile==null) {
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
            // "EXRPT3BWGCHG" start at WG and get all possible WG with their MACHTYPE
            // get all current FMCHGLOGRPT and MACHTYPE to get list of InventoryGroups
            // Wayne says only need MACHTYPE to get the master list of InventoryGroups
            list = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, extractName),
                wgGrp.getEntityItemsAsArray());

            trace(D.EBUG_INFO,false,"FMChgLog() EntityList for "+extractName+": contains the following entities: ");
            trace(D.EBUG_INFO,false,FMChgLogGen.outputList(list));

            if (list.getEntityGroup("WG")==null) {
                System.err.println("Extract: "+extractName+" can not be found.  Execution is terminated!"+NEWLINE+
                    "Extracts EXRPT3WGSETUP, EXRPT3FCTRANS and EXRPT3CGOSMDL may be missing too!");
                trace(D.EBUG_ERR,true,"FMChgLog() Extract: "+extractName+" can not be found.  Execution is terminated!");
                trace(D.EBUG_ERR,true,"FMChgLog() Extracts EXRPT3WGSETUP, EXRPT3FCTRANS and EXRPT3CGOSMDL may be missing too!");

                traceWriter.close();
                dataErrorWriter.close();
                list.dereference();
                wglist.dereference();
                System.exit(2);
            }

            displayWGcounts(list.getEntityGroup("WG")); // display MACHTYPE for each WG.. debug only
            flushLogs();

            // deactivate old report entities and determine last ran date,
            delRptsDone = deactivateOldReports(list.getEntityGroup("FMCHGLOGRPT"));

            if (overrideLastRanDate != null)  {
                trace(D.EBUG_ERR,false,"FMChgLog() WARNING using override last ran date: "+overrideLastRanDate+" instead of: "+lastRanDate);
                lastRanDate = overrideLastRanDate;
            }
            else {
                trace(D.EBUG_ERR,false,"************ Using last ran date: "+lastRanDate);
            }

            // build the logs for each WG.. one WG for one BR for one InvGrp in first release
            chgLog = new FMChgLogGen(this, dbCurrent, profile, curDate, lastRanDate, chglogWGitem,list);

            list = null; // list was dereferenced in FMChgLogGen to free up memory faster
            System.gc();

        	pdhWriter = new FMChgWritePDH(dbCurrent, profile, this, chglogWGitem);

            trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of initialization timing "+getElapsedTime()+" ***************");

            trace(D.EBUG_INFO,false,"FMChgLog() ***** Start build of all Feature Matrix logs ***************");
            // build the Feature Matrix logs
            chgRptVct = chgLog.buildFMLogs(pdhWriter);
            trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of all Feature Matrix logs timing "+getElapsedTime()+" ***************");
			
			System.gc();  // allow garbage collector to run to release all entity lists, etc
			if (chgRptVct.size()>0){
				boolean had1day = false;
	            trace(D.EBUG_INFO,false,"FMChgLog() ***************** "+chgRptVct.size()+" FM Files were not written to the PDH yet ***************");
				if(!FMChgProperties.getWriteToPDH()) {// used during debug, don't keep writing to pdh
            		trace(D.EBUG_WARN,false,"FMChgLog() not writing to PDH");
				}else{
					for (int i=0; i<chgRptVct.size(); i++){ //write each one to the PDH now
						FMChgRpt chgrpt = (FMChgRpt)chgRptVct.elementAt(i);
						if(chgrpt.getInterval()==1) {
							had1day = true;  // at least one of these files were for 1 day interval
						}

						// read file from server.. copy to PDH
						pdhWriter.createReportEntityFromFile(chgrpt);
						chgrpt.dereference();
					}
				}
				chgRptVct.clear();
				if (had1day) {
            		trace(D.EBUG_WARN,false,"FMChgLog() updating lastran date for interval 1");
    	    		updateLastRanDate("1");  // mark 1 day interval as complete now
				}
			}

            // delete any serialized files
			deleteSerializedFiles();

			System.gc();  // allow garbage collector to run to release all entity lists, etc

			if (!chgLog.isJust1dayRpts()){ // MN39715268
				trace(D.EBUG_INFO,false,"FMChgLog() ***** Start build of all Supported Device logs ***************");
				// build the Supported Device logs
				chgRptVct = chgLog.buildSDLogs(pdhWriter);
				trace(D.EBUG_INFO,false,"FMChgLog() ***************** End of all Supported Device logs timing "+getElapsedTime()+" ***************");
				System.gc();  // allow garbage collector to run to release all entity lists, etc
				if (chgRptVct.size()>0){
					trace(D.EBUG_INFO,false,"FMChgLog() ***************** "+chgRptVct.size()+" SD Files were not written to the PDH yet ***************");
					if(!FMChgProperties.getWriteToPDH()) {// used during debug, don't keep writing to pdh
						trace(D.EBUG_WARN,false,"FMChgLog() not writing to PDH");
					}else{
						for (int i=0; i<chgRptVct.size(); i++){ // write each one to the PDH now
							FMChgRpt chgrpt = (FMChgRpt)chgRptVct.elementAt(i);
							// read file from server.. copy to PDH
							pdhWriter.createReportEntityFromFile(chgrpt);
							chgrpt.dereference();
						}
					}
					chgRptVct.clear();
				}
			}else{
				trace(D.EBUG_INFO,false,"FMChgLog()*****CLSTATUS******* Bypassing build Supported Device logs ***************");
			}
			
            // release memory
            chgLog.dereference();
			System.gc();  // allow garbage collector to run to release all entity lists, etc

            // update the last ran date
            extractName = "EXRPT3CHGRPT"; // small.. just get FMCHGLOGRPT
            // list was dereferenced in FMChgLogGen so must get it again, use a smaller VE
            list = dbCurrent.getEntityList(profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    wgGrp.getEntityItemsAsArray());

            trace(D.EBUG_INFO,false,"FMChgLog() EntityList for "+extractName+": contains the following entities: ");
            trace(D.EBUG_INFO,false,FMChgLogGen.outputList(list));

            if (list.getEntityGroupCount()==0) {// check for missing meta
                trace(D.EBUG_ERR,false,"ERROR:  Extract "+extractName+" is missing!");
                list.dereference();
                // use big one for now!
                // get all current FMCHGLOGRPT and MACHTYPE to get list of InventoryGroups
                extractName = "EXRPT3BWGCHG";
                list = dbCurrent.getEntityList(profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    wgGrp.getEntityItemsAsArray());
            }
            if (!chgLog.isJust1dayRpts()){ // MN39715268
            	updateLastRanDate(list.getEntityGroup("FMCHGLOGRPT"), "30");
			}

            if (!delRptsDone)  {// something failed in deleting the reports, try again
                trace(D.EBUG_ERR,false,"Deletion of old reports failed the first time, trying again");
                // last ran date will be determined but is meaningless at this point
                deactivateOldReports(list.getEntityGroup("FMCHGLOGRPT"));
            }
            list.dereference(); // release memory

            wglist.dereference();
            trace(D.EBUG_ERR,false,"FMChgLog() ***************** Total run time "+formatTime(swTimer.getFinish()));
        }catch(Throwable exc) {
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
        finally {
            if (dataErrorWriter!=null) {
                dataErrorWriter.close(); 
            }
            if (traceWriter!=null) {
                traceWriter.close(); 
            }
        }
    }
    String getElapsedTime() { 
    	return formatTime(swTimer.getLap()); 
    }

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
        if(!FMChgProperties.getUpdateLastRanDTS()) { // allow debug to not update this date!
            trace(D.EBUG_ERR,false,"Properties file specified no update for last ran DTS");
        }else{
            // update the last ran date
            list = dbCurrent.getEntityList(profile, new ExtractActionItem(null, dbCurrent, profile, extractName),
                new EntityItem[] { chglogWGitem });

            updateLastRanDate(list.getEntityGroup("FMCHGLOGRPT"), interval);
            list.dereference(); // release memory
        }
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
        if(!FMChgProperties.getUpdateLastRanDTS()) { // allow debug to not update this date!
            trace(D.EBUG_ERR,false,"Properties file specified no update for last ran DTS");
        }else{
            for (int i=0; i<chglogGrp.getEntityItemCount(); i++) {
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
            if (lastRanItem==null)  { // doesn't exist yet so create one
                // create the entity
                CreateActionItem cai = new CreateActionItem(null, dbCurrent, profile, FMChgWritePDH.CREATEACTION_NAME);

                EntityList list = new EntityList(dbCurrent, profile, cai, new EntityItem[] { chglogWGitem });
                EntityGroup eGrp = list.getEntityGroup(FMChgWritePDH.REPORT_TYPE);
                if (eGrp.getEntityItemCount() == 1) {
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
                else  {
                    trace(D.EBUG_ERR,true,"FMChgLog ERROR: Can not create "+FMChgWritePDH.REPORT_TYPE+
                            " entity for interval: "+interval+" and type: "+FMChgLog.LASTRUN_TYPE);
                }
                list.dereference();
            }
            else {
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
        if (ma.getAttributeType().charAt(0)=='U') {
            MetaFlag[] mfa =null;
            EANFlagAttribute flagAttr= new SingleFlagAttribute(lastRanItem, null, (MetaSingleFlagAttribute) ma);
            lastRanItem.putAttribute(flagAttr);
            mfa = (MetaFlag[]) flagAttr.get();
            for (int i = 0; i < mfa.length; i++) {
                if (mfa[i].getFlagCode().equals(flagCode)) {
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
        if (ma.getAttributeType().charAt(0) == 'T') {
            EANAttribute textAttr = lastRanItem.getAttribute(attrCode);
            if (textAttr == null) {
                textAttr = new TextAttribute(lastRanItem, null, (MetaTextAttribute) ma);
                lastRanItem.putAttribute(textAttr);
                textAttr.put(value);
            }
            textAttr.put(value);
        }
    }

    // display MACHTYPE for each WG.. debug only
    private void displayWGcounts(EntityGroup wgGrp) throws Exception
    {
        for (int i=0; i<wgGrp.getEntityItemCount(); i++)  {
            EntityItem wgItem = wgGrp.getEntityItem(i);
            int mtCnt =0;
            // -- Entity:WG---->Assoc:WGMACHTYPEA--->Entity:MACHTYPE
            for (int d=0; d<wgItem.getDownLinkCount(); d++)  {
                EANEntity entityLink = wgItem.getDownLink(d);
                if (entityLink.getEntityType().equals("WGMACHTYPEA")) {
                    mtCnt++; 
                }
            }
            trace(D.EBUG_INFO,false,"FMChgLog() "+wgItem.getKey()+" for "+
                FMChgLogGen.getAttributeValue(wgItem, "NAME", "", "")+
                " has "+mtCnt+" MACHTYPEs associated with it");
            if (mtCnt==0) {
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
        boolean success=true;
        try{
            Vector<EntityItem> delVct = new Vector<EntityItem>();
            // get 7 day timestamp
            FMChgISOCalendar isoDate = new FMChgISOCalendar(curDate);
            String delDate = isoDate.getAdjustedDate(MAX_AGE);
            String dts30 = null;
            String dts1 = null;
            trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() delDate "+delDate+" item count: "+chglogGrp.getEntityItemCount());

            // get creation date for each entity
            for (int i=0; i<chglogGrp.getEntityItemCount(); i++)  {
                EntityItem chglogItem = chglogGrp.getEntityItem(i);
                //Report Type (FMCHGTTYPE) = {FM Chg Rpt | Spt Dev Chg Rpt | $$Last Run$$}
                String fmChgType = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTTYPE", "", "");
                // FMCHGDTS     T   DTS of Rpt  Date/Time of Report
                String dts = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGDTS", "", null);
                if (fmChgType.equals(LASTRUN_TYPE)) {
                    // get interval.. if it is null, then this is the original marker used for all reports
                    String fminterval = FMChgLogGen.getAttributeValue(chglogItem,"FMCHGTINTERVAL", "", "30");
                    if (fminterval.equals("1"))  {// use 1 day marker for DTS, not 30 day marker
						if (dts1!=null) {  // this shouldn't happen, there should be only 1 entity for this interval
                    		trace(D.EBUG_ERR,true,"FMChgLog().deactivateOldReports() ERROR "+chglogItem.getKey()+
                    			" is a duplicate for "+LASTRUN_TYPE+" interval: "+fminterval+" DTS: "+dts+
                    			" It will be deactivated!");
		                    delVct.addElement(chglogItem);
		                    continue;
						}else {
                        	dts1 = dts;
						}
                    }
                    if (fminterval.equals("30")) {
						if (dts30!=null) {  // this shouldn't happen, there should be only 1 entity for this interval
                    		trace(D.EBUG_ERR,true,"FMChgLog().deactivateOldReports() ERROR "+chglogItem.getKey()+
                    			" is a duplicate for "+LASTRUN_TYPE+" interval: "+fminterval+" DTS: "+dts+
                    			" It will be deactivated!");
		                    delVct.addElement(chglogItem);
		                    continue;
						}else {
                        	dts30 = dts;
						}
                    }

                    trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() skipping "+chglogItem.getKey()+
                        " it is "+LASTRUN_TYPE+" DTS: "+dts+" interval: "+fminterval);
                    continue;
                }

                if (dts==null) { // error condition, should not happen
                    EntityChangeHistoryGroup eHistGrp = new EntityChangeHistoryGroup(dbCurrent, profile, chglogItem);
                    ChangeHistoryItem chi = eHistGrp.getChangeHistoryItem(0); // first one is creation
                    trace(D.EBUG_ERR,true,"FMChgLog().deactivateOldReports() ERROR "+chglogItem.getKey()+" has null FMCHGDTS! will be deactivated, create date: "+
                    		(chi==null?"null":chi.getChangeDate()));
                /*if (delDate.compareTo(chi.getChangeDate())>=0) // deactivate this entity and relator
                */
                    delVct.addElement(chglogItem);
                    continue;
                }
    //for debug, display entity item
    //display(dbCurrent, chglogItem);

                if (delDate.compareTo(dts)>=0) { // deactivate this entity and relator
                    trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() deactivating "+chglogItem.getKey());
                    delVct.addElement(chglogItem);
                }
            }
            // determine last ran date,
            if (dts1!=null) { // use 1 day marker for DTS, not 30 day marker
                lastRanDate = dts1;
                trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() set lastRanDate using 1 day marker: "+lastRanDate);
            }
            else  {
                lastRanDate = dts30;
                trace(D.EBUG_INFO,false,"FMChgLog().deactivateOldReports() set lastRanDate using 30 day marker: "+lastRanDate);
            }

            if (lastRanDate !=null) { // if lastRanDate is more than 6 days (144 hrs), set it to null
                int diffHr = isoDate.getDiffHours(lastRanDate);
                if (diffHr > MAX_HRS_SINCE_LASTRUN) {
                    long diffDay = diffHr/HOURS_IN_DAY;
                    trace(D.EBUG_INFO,false,"******** WARNING Time since last valid run was too long. Max HRs: "+
                        MAX_HRS_SINCE_LASTRUN+" calc HRs: "+diffHr+" Days: "+diffDay+" setting lastrandate to NULL");
                    lastRanDate=null;
                }
            }

            if (delVct.size()>0)  {
                EntityItem[] eiArray = new EntityItem[delVct.size()];
                delVct.copyInto(eiArray);
                deactivateReports(eiArray);
                delVct.clear();
            }
        }
        catch(Throwable exc)  {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            System.err.println("ERROR: "+exc.getMessage());
            System.err.println(exBuf.getBuffer().toString());
            trace(D.EBUG_ERR,true,"ERROR: "+exc.getMessage());
            trace(D.EBUG_ERR,true,exBuf.getBuffer().toString());
            success = false;
        }
        return success;
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
            dataErrorWriter.flush(); 
        }
        if (traceWriter!=null) {
            traceWriter.flush(); 
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
        if (addToMWlog) {
            D.ebug(level, msg); }

        if (level <= debugLevel &&
            traceWriter!=null)  {
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
        if (dataErrorWriter!=null && !dataErrorVct.contains(msg))  {
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
        for (int i = 0,c=profileSet.size(); i < c; i++) {
            // connection items contain connId, valOn, effOn, enterprise, etc..
            // set role to be used, like PSG Div Admin
            Profile pf = profileSet.elementAt(i);
            trace(D.EBUG_INFO,false,"FMChgLog.getProfile() profile["+i+"] openid: "+pf.getOPWGID()+" role: <"+pf.getRoleCode()+
                "> enterprise: <"+pf.getEnterprise()+"> looking for role: <"+roleCode+"> enterprise: <"+enterprise+">");
            if (pf.getRoleCode().equals(roleCode)&& pf.getEnterprise().equals(enterprise))
            {
                Vector<?> nlsVct = null;
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
        if (pdhWriter!=null) {
            pdhWriter.dereference();
        }
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
        if (args.length>0)  {
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
        for (int ai=0; ai<eg.getMetaAttributeCount(); ai++)  {
            EANMetaAttribute metaAttr = eg.getMetaAttribute(ai);
            EANAttribute attr = entityItem.getAttribute(metaAttr.getAttributeCode());
            System.out.print("Attribute Code: "+metaAttr.getAttributeCode());
            if (attr instanceof EANBlobAttribute) {
                EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                byte[] byteArray = blobAtt.getBlobValue();
                String report = null;
                if (byteArray == null)  {
                    try {
                        byteArray = blobAtt.getBlobValue(null, dbCurrent2);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                try{
                    if (byteArray != null) {
                        report = new String(byteArray,"UTF8"); 
                    }
                }catch(java.io.UnsupportedEncodingException ee)
                {
                    if (byteArray != null) {
                        report = new String(byteArray);
                    }
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
            else  {
                System.out.println(" value: "+FMChgLogGen.getAttributeValue(entityItem, metaAttr.getAttributeCode(), ", ", null));
            }
        }
        System.out.println("################");
    }
    /**
     * @param dir
     * @param filter
     * @return list of files in dir matching the filter extension
     */
    public static String[] list(String dir, final String filter) {
        File f = new File(dir);
        String[] file = null;
        if (f.exists()) {
        	if (filter !=null){
        		file = f.list(new FilenameFilter(){
        			public boolean accept(File dir, String name) {
        				return (name.toLowerCase().endsWith(filter.toLowerCase()));
        			}
        		});
        	}else{
            	file = f.list();
            }
        }
        return file;
    }
    /**
     * @param dir
     * @param filter
     * @return list of files in dir matching the filter extension
     */
    static void deleteSerializedFiles() {
        File f = new File(FMChgLogGen.WORK_FOLDER);
        if (f.exists()) {
        	String[] file = f.list(new FilenameFilter(){
        		public boolean accept(File dir, String name) {
        			return (name.toLowerCase().endsWith(FMChgLogGen.SER_SUFFIX.toLowerCase()));
        		}
        	});
        	if(file != null && file.length>0){
        		for(int i=0;i<file.length; i++){
        			File ser = new File(FMChgLogGen.WORK_FOLDER+"/"+file[i]);
        			boolean b = ser.delete();
        			if(!b){
        				java.nio.file.Path path = java.nio.file.Paths.get(FMChgLogGen.WORK_FOLDER+"/"+file[i]);
        				try{
        					java.nio.file.Files.delete(path);
        				}catch(IOException ioe){
                            System.err.println("deleteSerializedFiles() Error deleting "+ser+" "+ioe);
        				}
        			}
        		}
        	}
        }
    }
} // end-class FMChgLog
