// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.hula.*;

/**********************************************************************************
 * All changes to data are 'logged' by EACM at the attribute, entity and relator level.
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
 * If a report exceeds 'max_pdh_process_size' value in the properties file then it will not be put into the
 * PDH until all processing is completed.  Memory must be doubled to convert the string into a byte[]
 * for the report blob and there isn't enough room if the entitylists from time1 and time2 are still in memory.
 *
 *@author     Wendy Stimpson
 *@created    Oct 5, 2004
 */
// $Log: FMChgLogGen.java,v $
// Revision 1.14  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.13  2013/02/07 12:14:05  wendy
// CQ236604 filter out changeitems made by cron jobs
//
// Revision 1.12  2009/11/04 16:57:26  wendy
// MN41057882 - WGMACHINETYPE relator was replaced by WGMACHTYPEA association.
//
// Revision 1.11  2009/07/31 13:41:54  wendy
// Support 'all' in properties file to run just 1 day logs for all invgrps
//
// Revision 1.10  2009/06/25 21:01:27  wendy
// MN39715268 - Support run of 1 day only reports for a subset of InventoryGroups
//
// Revision 1.9  2008/01/22 19:05:03  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2007/10/17 11:52:45  wendy
// MN33235098 use TOMACHTYPE instead of FROMMACHTYPE
//
// Revision 1.7  2006/07/26 15:27:45  wendy
// Added pulling extract again if MW doesn't return any EntityItems, a bandaid for MW error
//
// Revision 1.6  2006/05/03 18:01:29  wendy
// CR0503064033 prevent 7 and 30 day logs for specified invgrps (configurable)
//
// Revision 1.5  2006/05/01 21:31:07  wendy
// Added property for max size to write to the PDH while processing.
// This was done to reduce memory usage.  If a file is too large it will
// be written to the server and then moved to the PDH after lists are freed.
//
// Revision 1.4  2006/04/27 17:13:13  wendy
// Go from MDLCGOSMDL to MODELa in some cases
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
//
class FMChgLogGen
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2017  All Rights Reserved.";
	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.14 $";
	static final String DELIMITER = "|";
	static final String WORK_FOLDER = "work";
	static final String SER_SUFFIX = ".ser";
	static final String RFA_CHG_SET = WORK_FOLDER+"/RFA";
	static final String FEATWD_CHG_SET = WORK_FOLDER+"/FEATWD";
	static final String FC_CHG_SET= WORK_FOLDER+"/FC";
	static final String MTM_CHG_SET= WORK_FOLDER+"/MTM";
	static final String FCTRANS_CHG_SET= WORK_FOLDER+"/FCTRANS";
	private static final int MW_VENTITY_LIMIT=FMChgProperties.getMaxVELimit();

	private Database dbCurrent;
	private Profile profile;
	private String curTime, mtpFromTime, lastRanDate=null;

	/*
    Terminology:

    Delete - the entity is deleted
    Remove - the relator is deleted

    so, on SupportedDevice & ProductStructure tabs - use Remove
    on the other tabs - use Delete
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

	private Hashtable<String, String> MTinvGrpTbl = new Hashtable<String, String>(); // key is machinetype.desc, value is invGrp flagcode
	private Hashtable<String, String> FMmapTbl = new Hashtable<String, String>(); // key is MAPFEATURE.INVENTORYGROUP, HWFCCAT, and HWFCSUBCAT flags, value is concatenated FMGROUPCODE and FMSUBGROUPCODE
	private Hashtable<String, String> SDmapTbl = new Hashtable<String, String>(); // key is MAPSUPPDEVICE.INVENTORYGROUP and FMGROUP, value is FMGROUPCODE
	private Hashtable<String, Vector<String>> invGrpBrTbl = new Hashtable<String, Vector<String>>(); // key is invGrp flagcode, value is vector of br desc
	private Hashtable<String, String> invGrpMasterTbl = new Hashtable<String, String>(); // key is invGrp flagcode value is desc, it is also the list of all valid invgrp
	private Hashtable<String, String> brDescTbl = new Hashtable<String, String>(); // key is br flagcode value is desc
	private FMChgISOCalendar isoDate = null;
	private FMChgLog theFmLog =null;
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
	private static final String SD_ROOTTYPE = "MODEL";

	//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS
	private static final String SD_EXTRACTNAME = "EX3CGOSMDL"; // used only for edoc, chg in invgrp(MODELa) doesn't need to be tracked
	private static final String SD_MTM_EXTRACTNAME = "EX3MDLCGMDL"; // this goes to the MODELa that has the suppdevice MODELc
	// it uses the MODELc root and goes up to MODELa in the report
	// changes to inventorygroup (thru MODELa) will not be captured.  MODELa is only used for invgrp and
	// output in the MTM column of the SD matrix section
	//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS use MACHTYPEATR on MODELc to determine the invgrp for FM mapping
	//-- Entity:MODELCGOS<----Relator:MDLCGMDLCGOS<---Entity:MODELCG
	//-- Entity:MODELCG---->Relator:MDLCGMDL--->Entity:MODELa  use MACHTYPEATR on MODELa to determine the invgrp for this suppdevice

	private static final String FC_ROOTTYPE = "FCTRANSACTION";
	private static final String FC_EXTRACTNAME = "EXRPT3FCTRANS";
	private static final String FEAT_ROOTTYPE = "FEATURE";
	private static final String FEAT_EXTRACTNAME = "EXRPT3FM";
	private Stopwatch swTimer = new Stopwatch();
	private Vector<FMChgRpt> chgRptVct = new Vector<FMChgRpt>(); // hold onto the chg info for each inv group, the blob will be
	// written to the server and must be moved to the PDH when done
	private Vector<String> invGrpToSkipVct = new Vector<String>(1);    // list of invgrps to skip for 7 and 30 day rpts CR0503064033
	String getMTInvGrp(String mtdesc)  {
		return (String)MTinvGrpTbl.get(mtdesc); // match on desc
	}
	String getInvGrpDesc(String invGrp)  {
		return (String)invGrpMasterTbl.get(invGrp);
	}
	private Hashtable<String, EntityChangeHistoryGroup> entityChgHistTbl = new Hashtable<String, EntityChangeHistoryGroup>();  // hang onto EntityChangeHistory to reduce PDH calls
	private Hashtable<String, AttributeChangeHistoryGroup> attrChgHistTbl = new Hashtable<String, AttributeChangeHistoryGroup>();  // hang onto AttributeChangeHistory to reduce PDH calls

	EntityChangeHistoryGroup getChgHistGroup(String key){ // key is entity.getKey(), value is changehistorygroup
		return (EntityChangeHistoryGroup)entityChgHistTbl.get(key); 
	}
	void addChgHistGroup(String key, EntityChangeHistoryGroup histGrp) {
		entityChgHistTbl.put(key, histGrp);
	}

	private Hashtable<String, Vector<ChangeHistoryItem>> entityChgHistVctTbl = new Hashtable<String, Vector<ChangeHistoryItem>>(); //CQ236604 - drop system ids
	Vector<ChangeHistoryItem> getChgHistGroupVct(String key) {// key is entity.getKey(), value is vector of changehistoryitems
		return (Vector<ChangeHistoryItem>)entityChgHistVctTbl.get(key); 
	}
	void clearChgHistGroupVct(){
		for (Enumeration<Vector<ChangeHistoryItem>> e = entityChgHistVctTbl.elements(); e.hasMoreElements();){
			Vector<ChangeHistoryItem> vct = (Vector<ChangeHistoryItem>)e.nextElement();
			vct.clear();
		}
		entityChgHistVctTbl.clear();
	}
	void addChgHistGroupVct(String key, Vector<ChangeHistoryItem> histGrpVct) {
		entityChgHistVctTbl.put(key, histGrpVct);
	}

	AttributeChangeHistoryGroup getAttrChgHistGroup(String key) {// key is entity.getKey(), value is attrchangehistorygroup
		return (AttributeChangeHistoryGroup)attrChgHistTbl.get(key); 
	}
	void addAttrChgHistGroup(String key, AttributeChangeHistoryGroup histGrp) {
		attrChgHistTbl.put(key, histGrp);
	}
	boolean isMoreThan24Hours(String fromTime) { 
		return isoDate.getDiffHours(fromTime)>FMChgLog.HOURS_IN_DAY; 
	}
	String getElapsedTime() { 
		return FMChgLog.formatTime(swTimer.getLap()); 
	}

	private boolean runOnly1dayrpts = false;  //MN39715268
	// MN39715268 are chgs to support a list of invgpr for 1 day only rpts - no 7 or 30 day rpts!!!
	protected boolean isJust1dayRpts() { 
		return runOnly1dayrpts;
	} // MN39715268

	/**************************************************************************************
	 * Constructor
	 *
	 * Get all BRAND to INVENTORYGROUP mappings from all MACHTYPE
	 * pull EntityList with setup data for FM mappings (MAPFEATURE and MAPSUPPDEVICE)
	 * setup PDH writer to be reused for each generated file
	 *
	 * @param fmlog FMChgLog driver object
	 * @param db Database object
	 * @param p Profile object
	 * @param curDate String getNow() timestamp
	 * @param lastRanDate2 String with last valid date might be null
	 * @param chglogWG EntityItem CHGLOGEXTRACT WG to hang FMCHGLOGRPT entities off of
	 * @param list EntityList with MACHTYPE used to get MACHTYPE BR-InvGrp mappings
	 */
	FMChgLogGen(FMChgLog fmlog, Database db, Profile p, String curDate, String lastRanDate2,
			EntityItem chglogWG, EntityList list) throws
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			java.sql.SQLException, Exception
	{
		StringBuffer allInvGrpSb= new StringBuffer();
		String extractName = "EXRPT3WGSETUP";
		EntityList setuplist = null;

		dbCurrent = db;
		profile = p;
		theFmLog = fmlog;
		curTime = curDate;
		this.lastRanDate = lastRanDate2;

		// takes too long to run this, something may change so use current timestamp, not eod
		//      eodTime = curTime.substring(0,10)+"-23.59.59.999999"; // end of current day is end of time range

		// find out if there is a minimum date, if an IDL happened, don't want to check it
		mtpFromTime = FMChgProperties.getMinimumDate();
		if (mtpFromTime.length()>0)  {
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

		list.dereference(); // don't need this info any more, free the memory asap

		// stop if no inventory grps are found, error in "EXRPT3BWGCHG" extract
		if (invGrpMasterTbl.size()==0){
			throw new Exception("No Inventory Groups found in EXRPT3BWGCHG extract, no files can be generated.");
		}

		for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();)  {
			String invGrp = (String)e.nextElement(); // key is invgrp flag code
			String invGrpDesc = (String)invGrpMasterTbl.get(invGrp);
			allInvGrpSb.append(" "+invGrpDesc+"["+invGrp+"]");
		}
		trace(D.EBUG_ERR,true,"*****CLSTATUS******* Set of Master InvGrp: "+allInvGrpSb.toString());
		Vector<String> invGrpFor1DayOnlyVct = get1DayonlyInvGrps(); // MN39715268 get list if any, of 1 day only rpts
		if (invGrpFor1DayOnlyVct!=null){
			runOnly1dayrpts = true; // no 7 or 30 day rpts will be run for any invgrp
			if(invGrpFor1DayOnlyVct.size()>0){
				// remove extra invgrps from the master set
				Set<String> igcol = invGrpMasterTbl.keySet();
				Iterator<String> itr = igcol.iterator();
				HashSet<String> removeSet = new HashSet<String>();
				while(itr.hasNext()){
					String invgrp = (String)itr.next();
					if (!invGrpFor1DayOnlyVct.contains(invgrp)){
						removeSet.add(invgrp);
						trace(D.EBUG_INFO,false,"FMChgLogGen() removing "+invgrp+" from master set");
					}
				}
				igcol.removeAll(removeSet);
				removeSet.clear();

				invGrpFor1DayOnlyVct.clear();
				allInvGrpSb = new StringBuffer();
				for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();)
				{
					String invGrp = (String)e.nextElement(); // key is invgrp flag code
					String invGrpDesc = (String)invGrpMasterTbl.get(invGrp);
					allInvGrpSb.append(" "+invGrpDesc+"["+invGrp+"]");
				}
				trace(D.EBUG_ERR,true,"*****CLSTATUS******* ONLY 1 DAY reports will be executed using "+
						" UPDATED Set of Master InvGrp: "+allInvGrpSb.toString());
			}else{
				trace(D.EBUG_ERR,true,"*****CLSTATUS******* ONLY 1 DAY reports will be executed using "+
						" entire Set of Master InvGrp: "+allInvGrpSb.toString());
			}
		}else{
			getInvGrpToSkip();  // list of inv grps to skip for 7 and 30 day rpts  CR0503064033
		}

		// build FM mappings
		setuplist = dbCurrent.getEntityList(profile,
				new ExtractActionItem(null, dbCurrent, profile, extractName),
				new EntityItem[] { chglogWG });

		trace(D.EBUG_INFO,false,"FMChgLogGen() EntityList for "+extractName+": contains the following entities: ");
		trace(D.EBUG_INFO,false,outputList(setuplist));

		setFMMapping(setuplist.getEntityGroup("MAPFEATURE"));
		setFMSDMapping(setuplist.getEntityGroup("MAPSUPPDEVICE"));

		setuplist.dereference();
		flushLogs();
	}

	/**************************************************************************************
	 * MN39715268
	 * Get list of inv grps to use for 1 day report
	 * WARNING: 7 and 30 day reports will not be run at all
	 * This will NOT override set of WG linked
	 * @throws Exception 
	 */
	private Vector<String> get1DayonlyInvGrps() throws Exception {
		Vector<String> invGrpFor1DayOnlyVct = null;
		String invFlags = FMChgProperties.get1DayonlyInvGrps().trim();
		if (invFlags.length()>0){ // it is a comma delimited list of invgrp flag codes
			invGrpFor1DayOnlyVct = new Vector<String>();
			if (invFlags.equalsIgnoreCase("all")){
				trace(D.EBUG_ERR,true,"*****CLSTATUS******* ONLY run 1 day only rpts for all invgrps");
			}else{
				StringTokenizer stComma = new StringTokenizer(invFlags, ",");
				while (stComma.hasMoreTokens()) {
					String invgrp = stComma.nextToken().trim();
					if (!invGrpMasterTbl.containsKey(invgrp)){
						throw new Exception("Set of Master Inventory Groups does not contain "+invgrp+
								" from property run_1day_only_invgrps.");
					}
					invGrpFor1DayOnlyVct.add(invgrp);
				}

				trace(D.EBUG_ERR,true,"*****CLSTATUS******* Set of InvGrp to run 1 day only rpts: "+invGrpFor1DayOnlyVct);
			}
		}
		return invGrpFor1DayOnlyVct;
	}

	/**************************************************************************************
	 * Get list of inv grps to skip for 30 and 7 day reports CR0503064033
	 */
	private void getInvGrpToSkip() {
		String invFlags = FMChgProperties.getSkippedInvGrps_7_30();
		if (invFlags.length()>0){ // it is a comma delimited list of invgrp flag codes
			StringTokenizer stComma = new StringTokenizer(invFlags, ",");
			while (stComma.hasMoreTokens()) {
				invGrpToSkipVct.add(stComma.nextToken().trim());
			}

			trace(D.EBUG_ERR,true,"*****CLSTATUS******* Set of InvGrp to skip for 7,30 rpts: "+invGrpToSkipVct);
		}
	}

	/**************************************************************************************
	 * Build SD mapping for later use
	 * FM is derived as follows:
	 *
	 * Use entity MDLCGOSMDL->MODEL.to find a matching MAPSUPPDEVICE. The matching is based on
	 * INVENTORYGROUP from this MODEL’s parent MACHTYPE and COMPATDVCCAT from the MODEL itself.
	 * Given a matching entity MAPSUPPDEVICE using INVENTORYGROUP and FMGROUP, then this gives
	 * FMGROUPCODE.  This yields a one character code.
	 * @param eGrp EntityGroup of MAPSUPPDEVICE
	 */
	private void setFMSDMapping(EntityGroup eGrp) throws Exception
	{
		for (int mt=0; mt<eGrp.getEntityItemCount(); mt++)  {
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
		if (fmsd==null)   {
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
		for (int mt=0; mt<eGrp.getEntityItemCount(); mt++) {
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
		if (fm==null)  {
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
		StringBuffer mtsb = new StringBuffer();
		// get all MACHTYPE
		for (int ii=0; ii<mtGrp.getEntityItemCount(); ii++) {
			EntityItem mtItem = mtGrp.getEntityItem(ii);
			String mtDesc = null;
			Vector<String> brVct = null;
			// get parent WG for msgs only   WG<-WGMACHINETYPE
			EntityItem wgItem = (EntityItem)mtItem.getUpLink(0).getUpLink(0);
			String brDesc = getAttributeValue(mtItem, "BRAND", ", ", null);
			String brflag = getAttributeFlagValue(mtItem,"BRAND");
			String invGrpDesc = getAttributeValue(mtItem, "INVENTORYGROUP", ", ", "XX");
			String invGrpflag = getAttributeFlagValue(mtItem,"INVENTORYGROUP");
			String wgKey="";
			if (wgItem!=null) {
				wgKey = wgItem.getKey(); }
			if (brDesc == null || invGrpflag==null) {
				if (brDesc == null)  {
					trace(D.EBUG_WARN,false,"FMChgLogGen.getBRtoInvGrpMapping() "+wgKey+" "+mtItem.getKey()+" has null BRAND");
					logDataError(wgKey+" "+mtItem.getKey()+" has null BRAND");
				}
				if (invGrpflag == null) {
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
			// FCTRANSACTION.TOMACHTYPE is T not U!!
			MTinvGrpTbl.put(mtDesc,invGrpflag);

			mtsb.append(" "+mtDesc+"["+invGrpflag+"]");

			// set up inventory to brand mapping
			brVct = (Vector<String>)invGrpBrTbl.get(invGrpflag);
			if (brVct==null) {
				brVct = new Vector<String>();
				invGrpBrTbl.put(invGrpflag, brVct); // group by brand under invgrp
			}
			if (!brVct.contains(brflag)) {
				brVct.addElement(brflag); 
			}

			trace(D.EBUG_INFO,false,"FMChgLogGen.getBRtoInvGrpMapping() "+wgKey+" "+mtItem.getKey()+" MACHTYPEATR: "+mtDesc+" IG: "+
					invGrpDesc+"["+invGrpflag+"] for BR: "+brDesc+"["+brflag+"] BRvct: "+brVct);
		}

		trace(D.EBUG_ERR,true,"*****CLSTATUS******* Set of Machtype[InvGrpflag]: "+mtsb.toString());

		/*
        Wendy   can one machinetype have more than one inventory group?  (different MACHTYPE entities)
        Wayne Kehrli    machinetype should be unique
        Wayne Kehrli    one entity
        Wendy   i am trying to set up the mappings.. will i find one entity a machinetype='0017' and invgrp='squad2' and another entity with a machinetype='0017' and invgrp='as400'?
        Wayne Kehrli    there will only be one instance of machtype with machtypeattr = '0017'
		 */
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
		if (!addToMWlog && msg.trim().length()>0 && invDebugStr.length()>0) {
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
		if (isoDate!=null) {
			isoDate.dereference();
			isoDate = null;
		}

		for (Enumeration<Vector<String>> e = invGrpBrTbl.elements(); e.hasMoreElements();)  {
			Vector<String> brVct = (Vector<String>)e.nextElement();
			brVct.clear();
		}
		invGrpBrTbl.clear();
		invGrpBrTbl = null;
		invGrpMasterTbl.clear();
		invGrpToSkipVct.clear();
		invGrpToSkipVct = null;
		invGrpMasterTbl = null;
		brDescTbl.clear();
		brDescTbl = null;
		MTinvGrpTbl.clear();
		MTinvGrpTbl = null;
		FMmapTbl.clear();
		FMmapTbl=null;
		SDmapTbl.clear();
		SDmapTbl=null;

		entityChgHistTbl.clear();
		entityChgHistTbl = null;
		attrChgHistTbl.clear();
		attrChgHistTbl = null;

		//CQ236604- drop system ids
		clearChgHistGroupVct();
		entityChgHistVctTbl = null;
		invDebugStr = null;
		swTimer = null;

		chgRptVct = null;
	}

	/**************************************************************************************
	 * Check for calculated time predating the mtp minimum time
	 * @param fromTime String with dts of time 1 calculation for interval
	 * @param numDays int number of days used for interval
	 * @return int with number of days to use, -1 use alternate msg
	 */
	int checkDaysSinceMTP(String fromTime, int numDays)
	{
		int calcDays = numDays;
		//System.err.println("checkDaysSinceMTP() entered curTime: "+curTime+" fromTime: "+fromTime+" numdays: "+numDays+" mtpTime: "+mtpFromTime);
		if (mtpFromTime.length()>0) { // a date was set, does it preceed this fromTime?
			// if mtpTime > curtime, properties file may have tomorrow as first
			// valid day if IDL was today!
			if (mtpFromTime.compareTo(curTime)>0) {
				calcDays=0;
			}
			else {
				if (fromTime.compareTo(mtpFromTime)<=0) { // from time is before mtp time
					// calculate real number of days
					int diffHr = isoDate.getDiffHours(mtpFromTime);
					int diffDay = diffHr/FMChgLog.HOURS_IN_DAY;
					int diffLeft = diffHr%FMChgLog.HOURS_IN_DAY;
					if (diffHr==-1)  { // error condition..
						calcDays=0;
					}
					else{
						// this is only used for the msg.. so round things off
						if (diffDay == 0) {
							calcDays = -1;
						} // less than 1 day
						else {
							if (diffLeft>=(FMChgLog.HOURS_IN_DAY/2)) {
								diffDay+=1;
							} // more than half day so increment

							calcDays=diffDay;
						}
					}
				}
			}
		}
		return calcDays;
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
		if (numDays==1)  {
			if (lastRanDate !=null && lastRanDate.compareTo(fromTime)<0) { // lastRanDate is before the calculated time
				int diffHr = isoDate.getDiffHours(lastRanDate);
				long diffDay = diffHr/FMChgLog.HOURS_IN_DAY;
				fromTime = lastRanDate;
				trace(D.EBUG_INFO,false,"******** Time since last valid run hrs: "+diffHr+" days: "+diffDay+
						" using lastrandate "+lastRanDate);
			}
		}

		if (mtpFromTime.length()>0) {// a date was set, does it preceed this fromTime?
			// if mtpTime > curtime, then use 0 days.. properties file may have tomorrow as first
			// valid day if IDL was today!
			if (mtpFromTime.compareTo(curTime)>0)  {
				trace(D.EBUG_ERR,false,"MTP Minimum time: "+mtpFromTime+" exceeds curTime: "+curTime+", current time will be used");
				fromTime = curTime;
			}
			else if (fromTime.compareTo(mtpFromTime)<=0) {
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
	 * @param pdhWriter FMChgWritePDH
	 * @return Vector of FMChgRpt with info to write to PDH
	 */
	Vector<FMChgRpt> buildFMLogs(FMChgWritePDH pdhWriter) throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	java.io.UnsupportedEncodingException,
	java.rmi.RemoteException, Exception
	{
		// build 1 day log, so it gets moved to the ODS first.. then do 30 and 7 day if needed
		// the report type is one day BUT the time span may be more if app didn't run one or more days
		//CR0302055218 run from last valid run
		int numDays = ONE; 
		swTimer.start();
		// find daily change and write out
		buildFMLogsForInterval(pdhWriter, numDays);

		if (!runOnly1dayrpts){ // MN39715268
			// delete any serialized files
			FMChgLog.deleteSerializedFiles();
			// find out what changed over 30 days
			numDays = THIRTY;
			buildFMLogsForInterval(pdhWriter, numDays);

			// delete any serialized files
			FMChgLog.deleteSerializedFiles();
			numDays = SEVEN;
			// find 7 day changes and write out
			buildFMLogsForInterval(pdhWriter, numDays);
		}else{
			trace(D.EBUG_ERR,true,"***CLSTATUS**** Bypassing build of FM logs for 30 and 7 days");
		}

		return chgRptVct;
	}

	/**
	 * build the logs for the specified interval
	 * @param pdhWriter
	 * @param numDays
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	private void buildFMLogsForInterval(FMChgWritePDH pdhWriter, int numDays) throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	java.io.UnsupportedEncodingException,
	java.rmi.RemoteException, Exception
	{
		Hashtable<String,Vector<String>> delRelatorTbl = new Hashtable<String,Vector<String>>();  // keep track of removed relators
		Vector<String> tmp = new Vector<String>();

		//CR0302055218 run from last valid run for 1 day report
		trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
		// find changes and write out
		String fromTime = calculateFromTime(numDays);

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of FM logs for "+numDays+" days curTime "+
				curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

		trace(D.EBUG_INFO,false,"buildFMLogsForInterval()***** Find changed FEATURE-PRODSTRUCT-MODEL ***************");
		Vector<String> vctArray[] = getAffectedRootsAndChildren(FEAT_EXTRACTNAME,FEAT_ROOTTYPE,fromTime, delRelatorTbl);
		trace(D.EBUG_ERR,true,"buildFMLogsForInterval() number of changed FEATURE roots: "+vctArray[0].size());

		Profile fromProfile = profile.getNewInstance(dbCurrent);
		fromProfile.setValOnEffOn(fromTime, fromTime);

		// frequent OOM exceptions when massive changes are made - many of the changes should not be reflected
		// in the chglog files but must be looked at anyway
		// pull subsets if needed, serialize data by invgrp until done, then read back in and complete reports
		if (vctArray[0].size()>MW_VENTITY_LIMIT) {
			int numGrps = vctArray[0].size()/MW_VENTITY_LIMIT;
			int numUsed=0;
			for (int i=0; i<=numGrps; i++) {
				tmp.clear();
				for (int x=0; x<MW_VENTITY_LIMIT; x++) {
					if (numUsed == vctArray[0].size())
						break;

					String eid = vctArray[0].elementAt(numUsed++);
					tmp.addElement(eid);
				}

				if (tmp.size()>0) { // could be 0 if num entities is multiple of limit
					createFeatureChangeSets(fromProfile,tmp, numDays, vctArray[1],delRelatorTbl);
				}
			}
		}
		else {
			tmp.addAll(vctArray[0]);
			// pull extracts
			if (tmp.size()>0) {
				createFeatureChangeSets(fromProfile,tmp, numDays, vctArray[1],delRelatorTbl);
			}
		}

		tmp.clear();
		for (Enumeration<Vector<String>> e = delRelatorTbl.elements(); e.hasMoreElements();)  {
			Vector<String> vct = (Vector<String>)e.nextElement();
			vct.clear();
		}
		delRelatorTbl.clear();
		delRelatorTbl = null;
		// release memory
		vctArray[0].clear(); // roots that changed 
		vctArray[1].clear(); // child or root that changed
		
		// all feature, mtm changes are now saved to disk by invgroup for day 1

		trace(D.EBUG_INFO,false,"buildFMLogsForInterval()***** Find changed FCTRANSACTION ***************");
		// get feature transaction change info
		Vector<String> vctFcArray[] = getAffectedRootsAndChildren(FC_EXTRACTNAME,FC_ROOTTYPE,fromTime, null);
		trace(D.EBUG_ERR,true,"buildFMLogsForInterval() number of changed FCTRANSACTION roots: "+vctFcArray[0].size());

		// pull subsets if needed, serialize data by invgrp until done, then read back in and complete reports
		if (vctFcArray[0].size()>MW_VENTITY_LIMIT) {
			int numGrps = vctFcArray[0].size()/MW_VENTITY_LIMIT;
			int numUsed=0;
			for (int i=0; i<=numGrps; i++) {
				tmp.clear();
				for (int x=0; x<MW_VENTITY_LIMIT; x++) {
					if (numUsed == vctFcArray[0].size())
						break;

					String eid = vctFcArray[0].elementAt(numUsed++);
					tmp.addElement(eid);
				}

				if (tmp.size()>0) { // could be 0 if num entities is multiple of limit
					createFctransChangeSets(fromProfile,tmp, numDays, vctFcArray[1]);
				}
			}
		} else {
			tmp.addAll(vctFcArray[0]);
			// pull extracts
			if (tmp.size()>0) {
				createFctransChangeSets(fromProfile,tmp, numDays, vctFcArray[1]);
			}
		}

		tmp.clear();
		
		clearChgHistGroupVct();
		entityChgHistTbl.clear();  // don't need FEATURE, PRODSTRUCT or MODEL history any more
		attrChgHistTbl.clear(); // don't need these attributes anymore

		finishFMLogForInterval(pdhWriter, numDays, fromTime); 

		// a separate marker is needed for 1 day versus all the rest
		// these could be ready and moved before the others are complete
		if(chgRptVct.size()==0){  // if anything is in this vector then all files were not written to the
			// PDH because they were too big to do inline, they will be done later
			trace(D.EBUG_WARN,false,"buildFMLogsForInterval() updating lastran date for interval "+numDays);
			theFmLog.updateLastRanDate(""+numDays);
		}else{
			trace(D.EBUG_WARN,false,"buildFMLogsForInterval() NOT updating lastran date for interval "+numDays+" because at least one exceeded max size");
		}
		flushLogs();

		// release memory
		vctFcArray[0].clear();
		vctFcArray[1].clear();
	}

	/**
	 * @param fromProfile
	 * @param fcRootChgVct
	 * @param fromTime
	 * @param numDays
	 * @param fcChildChgVct
	 * @throws Exception
	 */
	private void createFctransChangeSets(Profile fromProfile,Vector<String> fcRootChgVct,
			int numDays, Vector<String> fcChildChgVct) throws Exception {
		Hashtable<String, EntityItem> fctransTbl = new Hashtable<String, EntityItem>(); // contains entityitems
		String fromTime = fromProfile.getValOn();
		
		// pull 1 day affected fctransaction entities
		EntityList fctrans1dayList = pullEntityItems(fcRootChgVct, profile,FC_EXTRACTNAME, FC_ROOTTYPE);
		trace(D.EBUG_INFO,false,"createFctransChangeSets() fctrans1dayList: "+FMChgLog.NEWLINE+outputList(fctrans1dayList));
		flushLogs();

		// copy entityitems into a hashtable
		if (fctrans1dayList!=null) {
			EntityGroup eg = fctrans1dayList.getParentEntityGroup();
			for (int i=0; i <eg.getEntityItemCount(); i++) { 
				fctransTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));
			}
		}

		// get feature transaction change info
		EntityList fromFCList = pullEntityItems(fcRootChgVct, fromProfile,FC_EXTRACTNAME, FC_ROOTTYPE);
		trace(D.EBUG_INFO,false,"createFctransChangeSets() fromFCList: "+FMChgLog.NEWLINE+outputList(fromFCList));

		trace(D.EBUG_INFO,false,"createFctransChangeSets()***** Group changed FCTRANSACTION by InventoryGroup ***************");
		Hashtable<String, Vector<EntitySet>> fcByInvGrpTbl = getFCByInvGrp(fctransTbl, fromFCList, fcRootChgVct, numDays);

		// complete one inv grp at a time, write it out
		for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();) {
			String invGrp = (String)e.nextElement(); // key is invgrp flag code

			invDebugStr="FM"+numDays+invGrpMasterTbl.get(invGrp);
			// one Vector for each FCTRANSACTION.invGrp
			Vector<EntitySet> fcVct = (Vector<EntitySet>)fcByInvGrpTbl.get(invGrp);
			getFCTransChgs(fcVct,fcChildChgVct,invGrp, fromTime);
			if (fcVct !=null) {
				// release memory
				for (int ii=0; ii<fcVct.size(); ii++) {
					EntitySet theSet = (EntitySet)fcVct.elementAt(ii);
					// remove any changehistory
					entityChgHistTbl.remove(theSet.getCurItem().getKey());  // FCTRANSACTION

					theSet.dereference();
				}
				fcVct.clear();
			}
		}

		if (fctrans1dayList!=null) {
			fctrans1dayList.dereference();
		}
		if (fromFCList!=null) {
			fromFCList.dereference(); 
		}
	}
	/**
	 * @param rootChgVct - set of root ids from edoc - could be a subset
	 * @param fromTime
	 * @param numDays
	 * @param featChildChgVct
	 * @param delPSRelatorTbl
	 * @throws Exception
	 */
	private void createFeatureChangeSets(Profile fromProfile,Vector<String> rootChgVct,
			int numDays, Vector<String> featChildChgVct,  Hashtable<String,Vector<String>> delPSRelatorTbl) throws Exception {

		String fromTime = fromProfile.getValOn();
		Hashtable<String, EntityItem> featTbl = new Hashtable<String, EntityItem>(); // contains entityitems
		// pull 1 day affected feature entities, index 0 is roots (ids) of structure with changes
		EntityList feat1dayList = pullEntityItems(rootChgVct, profile,FEAT_EXTRACTNAME,FEAT_ROOTTYPE);
		trace(D.EBUG_INFO,false,"createFeatureChangeSets() feat1dayList: "+FMChgLog.NEWLINE+outputList(feat1dayList));

		if (feat1dayList != null) {
			EntityGroup eg = feat1dayList.getParentEntityGroup();
			for (int i=0; i <eg.getEntityItemCount(); i++) { 
				featTbl.put(eg.getEntityItem(i).getKey(),eg.getEntityItem(i));
			}
		}

		// get entities at from time
		EntityList fromFeatList = pullEntityItems(rootChgVct, fromProfile,FEAT_EXTRACTNAME, FEAT_ROOTTYPE);
		trace(D.EBUG_INFO,false,"createFeatureChangeSets() fromTime: "+fromTime+" fromFeatList: "+FMChgLog.NEWLINE+outputList(fromFeatList));

		trace(D.EBUG_INFO,false,"createFeatureChangeSets()***** Group changed FEATURE structure by InventoryGroup ***************");

		// all need output based on inv grp do here once
		Hashtable<String, Vector<EntitySet>> featByInvGrpTbl = 
				getFeaturesByInvGrp(featTbl, fromFeatList, rootChgVct,numDays);

		//get changes for each inv grp and write to disk
		for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();) {
			String invGrp = (String)e.nextElement(); // key is invgrp flag code

			invDebugStr="FM"+numDays+invGrpMasterTbl.get(invGrp);

			//CR0503064033 skip 7, 30 day rpts for some invgrps
			if (numDays>1 && invGrpToSkipVct.contains(invGrp)) {
				trace(D.EBUG_WARN,false,"createFeatureChangeSets() Skipping Inventory Group ["+invGrp+"] FM report for "+
						"interval: "+numDays);
				continue;
			}

			trace(D.EBUG_ERR,true,FMChgLog.NEWLINE+"***CLSTATUS**** build FM log for InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+invGrp+"] ***************");

			// one Vector for each FEATURE.invGrp
			Vector<EntitySet> featVct = (Vector<EntitySet>)featByInvGrpTbl.get(invGrp);
			trace(D.EBUG_INFO,false,"createFeatureChangeSets() found "+((featVct!=null)?""+featVct.size():"0")+" changed "+invGrpMasterTbl.get(invGrp)+" FEATURE and structure");
			getMTMChgs(featVct,featChildChgVct,invGrp, delPSRelatorTbl, fromTime);

			Vector<EntitySet> chgRootVct = new Vector<EntitySet>(); // find all roots that had changes once and use over
			if (featVct!=null) {
				for (int i=0; i<featVct.size(); i++)  {
					EntitySet theSet = (EntitySet)featVct.elementAt(i);
					// use cur entity as basis
					EntityItem curItem = theSet.getCurItem();  // FEATURE
					// was a change made in the root FEATURE? no structure needed
					if (featChildChgVct.contains(curItem.getKey()))  {
						trace(D.EBUG_INFO,false,"createFeatureChangeSets() "+curItem.getKey()+" had some change ");
						chgRootVct.addElement(theSet);
					} else {
						trace(D.EBUG_INFO,false,"createFeatureChangeSets() "+curItem.getKey()+" did NOT change ");
						entityChgHistTbl.remove(curItem.getKey());  // don't need this FEATURE history any more
					}
					// don't need PRODSTRUCT history any more
					for (int d=0; d<curItem.getDownLinkCount(); d++) {
						entityChgHistTbl.remove(curItem.getDownLink(d).getKey());
					}
				}
			}
			// pass chgRootVct into methods that check feature root only
			trace(D.EBUG_INFO,false,"createFeatureChangeSets() found "+chgRootVct.size()+" changed "+invGrpMasterTbl.get(invGrp)+" FEATURE");

			getFCChgs(chgRootVct,invGrp, fromTime);
			getFeatureWithdrawalChgs(chgRootVct,invGrp, fromTime);

			getFeatureRFAChgs(chgRootVct,invGrp, fromTime);
			if (featVct !=null) {
				// release memory
				for (int ii=0; ii<featVct.size(); ii++) {
					((EntitySet)featVct.elementAt(ii)).dereference(); }
				featVct.clear();
			}
			chgRootVct.clear();
		}
		invDebugStr="";

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Created FM log data for "+rootChgVct.size()+" roots for "+numDays+" days timing: "+getElapsedTime()+
				" ***************");
		flushLogs();

		// release memory
		if (fromFeatList!=null) {
			fromFeatList.dereference(); 
		}

		featByInvGrpTbl.clear();    	
	}

    /**************************************************************************************
	 * finish logs for a particular interval - read in all sections for each inventory group
	 * @param pdhWriter FMChgWritePDH
	 * @param numDays int with interval
	 * @param fromTime String with dts of time 1 based on minimum date
	 * @throws Exception 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private void finishFMLogForInterval(FMChgWritePDH pdhWriter, int numDays, String fromTime) 
			throws MiddlewareRequestException, SQLException, MiddlewareException, Exception
	{
		trace(D.EBUG_INFO,false,"finishFMLogForInterval() for "+numDays+" days entered");

		// complete one inv grp at a time, write it out
		for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();) {
			FMChgRpt chgRpt = null;
			String invGrp = (String)e.nextElement(); // key is invgrp flag code
			// create the report object
			String brandflag = BRANDFLAG_DEFAULT;
			Vector<String> brVct = (Vector<String>)invGrpBrTbl.get(invGrp);
			if (brVct!=null)  {
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

			//CR0503064033 skip 7, 30 day rpts for some invgrps
			if (numDays>1 && invGrpToSkipVct.contains(invGrp)) {
				trace(D.EBUG_WARN,false,"finishFMLogForInterval() Skipping Inventory Group ["+invGrp+"] FM report for "+
						"interval: "+numDays);
				continue;
			}

			chgRpt = new FMChgFMRpt(this, numDays, curTime, invGrp, brandflag); // used for all changes, restricted and unrestricted

			trace(D.EBUG_ERR,true,FMChgLog.NEWLINE+"***CLSTATUS**** build FM log for InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+invGrp+"] ***************");

			chgRpt.setHeading(fromTime,(String)invGrpMasterTbl.get(invGrp),getBrandForInvGrp(invGrp));

			// read MTM chgsets
			completeMTMChgs(chgRpt,invGrp);
			// read feature chgsets
			completeFCChgs(chgRpt, invGrp);
			// read feature withdrawal chgsets
			completeFeatureWithdrawalChgs(chgRpt,invGrp);
			// read featuretransaction chgsets
			completeFCTransChgs(chgRpt, invGrp);
			// read feature rfa chgsets
			completeFeatureRFAChgs(chgRpt,invGrp);

			// output the report to the server,  if small enough, write each one to the pdh
			// if too large copy file to pdh when finished with
			// all FM invgrps to avoid memory issues for large files
			if (pdhWriter.createReportEntity(chgRpt)) {  // true then blob was small enough to write to PDH now
				chgRpt.dereference();
			}else {
				chgRptVct.add(chgRpt);  // use this later to copy file from server and put in PDH
			}
		}
		invDebugStr="";

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Completed FM logs for "+numDays+" days timing: "+getElapsedTime()+
				" ***************");
		flushLogs();
	}

    /**************************************************************************************
	 * An Inventory Group that is not found on a Machine Type won't have a Brand.
	 *   In this case, the report will specify a Brand of pSeries
	 * @param invGrp String with inventorygroup flag value
	 */
	private String getBrandForInvGrp(String invGrp)
	{
		String brand = BRAND_DEFAULT;
		Vector<String> brVct = (Vector<String>)invGrpBrTbl.get(invGrp);
		if (brVct!=null) {
			brand = "";
			for (int bi=0; bi<brVct.size(); bi++) {
				if (bi>0) {
					brand=brand+", "; 
				}
				brand=brand+brDescTbl.get(brVct.elementAt(bi)).toString();
			}
		}
		else {
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
	 * @param delRelatorTbl Hashtable to hang onto vector of deleted relator typeid
	 * @param fromTime String with dts of time 1 based on minimum date
	 * @return Vector array, index0 is roots that had changes in structure, index1 is child or root that had the change
	 */
	private Vector<String>[] getAffectedRootsAndChildren(String extractName, String rootType, String fromTime,
			Hashtable<String,Vector<String>> delRelatorTbl) throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		ExtractActionItem eai = new ExtractActionItem(null, dbCurrent, profile, extractName);
		eDoc edoc = null;
		Vector<String> rootChgVct = new Vector<String>();
		Vector<String> chgEntityVct = new Vector<String>();
		@SuppressWarnings("unchecked")
		Vector<String>[] vctArray = new Vector[2];
		ReturnDataResultSet rdrs = null;

		trace(D.EBUG_ERR,true,"getAffectedRootsAndChildren() Running edoc with root: "+rootType+" extract: "+extractName+" fromTime "+fromTime+" curTime "+curTime);
		edoc = new eDoc(dbCurrent,profile, eai,rootType,fromTime, curTime);

		// FEATURE can be linked to many PRODSTRUCT, keep track of what changed
		vctArray[0] = rootChgVct;
		vctArray[1] = chgEntityVct;
		rdrs = edoc.getTransactions();
		for (int r = 0; r < rdrs.getRowCount(); r++)  {
			ReturnDataRow row = rdrs.getRow(r);
			StringBuffer sb = new StringBuffer();
			boolean wasRelator = false;
			String rootChgId = null;
			String rootChgType=null;
			boolean rootOff = false;
			String childTypeId = null;
			boolean childOff = false;
			// col 0 is the 'general'area' value.. which will always be 000000
			for (int c=1; c<row.getColumnCount(); c++) {
				sb.append("["+r+"]["+c+"] "+rdrs.getColumn(r,c)+" ");
			}
			trace(D.EBUG_DETAIL,false,sb.toString());

			/* PRODSTRUCT 83 created and deleted in same interval
[155][1] FEATURE [155][2] 27 [155][3] OFF [155][4] MODEL [155][5] 5 [155][6] OFF [155][7] 0 [155][8] E [155][9] PRODSTRUCT [155][10] NOOP [155][11] 0
[156][1] FEATURE [156][2] 27 [156][3] OFF [156][4] PRODSTRUCT [156][5] 83 [156][6] OFF [156][7] 0 [156][8] R [156][9] PRODSTRUCT [156][10] MODEL [156][11] 5
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
			if (rootOff && childOff && wasRelator) { // relator was deleted, hang onto this info, may not have existed before interval
				trace(D.EBUG_INFO,false,rootChgType+rdrs.getColumn(r,2)+" had Relator "+childTypeId+" removed");
				if (delRelatorTbl !=null) { // if null, don't care about relator changes
					Vector<String> vct = (Vector<String>)delRelatorTbl.get(rootType+rootChgId);
					if (vct==null) {
						vct = new Vector<String>();
						delRelatorTbl.put(rootType+rootChgId,vct);
					}
					if (!vct.contains(childTypeId)) {
						vct.addElement(childTypeId); // hang onto type and id
					}
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
	EntityList pullEntityItems(Vector<String> idVct, Profile dtsProfile,String extractName, String rootType) throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		EntityItem[] eiArray = null;
		EntityList list = null;
		if (idVct.size()>0) {
			//int itemCnt = 0;
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
			/*gbl8000 was changed, it now fails instead of returning no items
            // mw occasionally malfunctions and doesnt' return any entity items even though they exist
            for (int i=0; i<list.getEntityGroupCount(); i++){
				EntityGroup eg = list.getEntityGroup(i);
				itemCnt+=eg.getEntityItemCount();
			}

			if (itemCnt==0){
				list.dereference();  // release memory for parent items
	            trace(D.EBUG_ERR,true,"*****CLSTATUS******* WARNING: FMChgLogGen.pullEntityItems() for extract: "+extractName+" root "+rootType+" id count: "+idVct.size()+
	                " valon: "+dtsProfile.getValOn()+" did NOT return any EntityItems, trying one more time");
	            list = dbCurrent.getEntityList(dtsProfile,
	                new ExtractActionItem(null, dbCurrent, dtsProfile, extractName),eiArray);
			}*/
		}

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
		Vector<String> idVct = new Vector<String>();
		idVct.addElement(""+theItem.getEntityID());

		trace(D.EBUG_INFO,false,"getValueForNullAttr() entered for "+theItem.getKey()+" attrCode: "+attrCode);
		// get entity history
		histGrp = getChgHistGroup(theItem.getKey());
		if (histGrp==null) {
			histGrp = new EntityChangeHistoryGroup(dbCurrent, profile, theItem);
			addChgHistGroup(theItem.getKey(),histGrp);
		}

		if (histGrp.getChangeHistoryItemCount()==0){
			trace(D.EBUG_WARN,false,"getValueForNullAttr() ERROR: No change history items found for "+theItem.getKey());
			logDataError("No change history items found for "+theItem.getKey());
		}else{
			createChi = histGrp.getChangeHistoryItem(0);
			lastChi = histGrp.getChangeHistoryItem(histGrp.getChangeHistoryItemCount()-1);
			lastActiveDate = createChi.getChangeDate();
			// make sure lastChi is before curtime
			if (lastChi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
				for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--)  {
					ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
					// history is complete, if user makes chgs after chglog starts, don't pick them up!
					if (chi.getChangeDate().compareTo(curTime)>0) {// date is AFTER curtime
						trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
						continue;
					}
					lastChi=chi;
					break;
				}
			}

			// get delete date and lastActiveDate
			if (!lastChi.isActive()) {  // was DELETED
				for (int i=histGrp.getChangeHistoryItemCount()-1; i>=0; i--)   {
					ChangeHistoryItem chi = histGrp.getChangeHistoryItem(i);
					trace(D.EBUG_DETAIL,false,"getValueForNullAttr() chi["+i+"] for "+theItem.getKey()+" isActive: "+chi.isActive()+
							" isValid: "+chi.isValid()+" chgdate: "+chi.getChangeDate());
					// history is complete, if user makes chgs after chglog starts, don't pick them up!
					if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime
						trace(D.EBUG_DETAIL,false,theItem.getKey()+" skipping chgdate: "+chi.getChangeDate()+" is AFTER curtime: "+curTime);
						continue;
					}

					if (chi.isActive())   {
						// set this with the first active one before deletion, don't overwrite
						lastActiveDate = chi.getChangeDate();
						break;
					}
					if (chi.getChangeDate().compareTo(fromTime)<=0) {// date is BEFORE fromtime
						break;
					}
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

				if (retValues[0] == null)  {// nothing found yet
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
						if (row != -1)  {
							EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
							if (attStatus != null)    {
								AttributeChangeHistoryGroup ahistGrp = getAttrChgHistGroup(keyStr);
								trace(D.EBUG_DETAIL,false,"getValueForNullAttr() AttributeChangeHistory for "+attrCode+" in "+theItem.getKey());
								if (ahistGrp==null)  {
									ahistGrp = dbCurrent.getAttributeChangeHistoryGroup(profile, attStatus);
									addAttrChgHistGroup(keyStr,ahistGrp);
								}
								for (int ci= ahistGrp.getChangeHistoryItemCount()-1; ci>=0; ci--) {// go from most recent to earliest
									ChangeHistoryItem chi = ahistGrp.getChangeHistoryItem(ci);
									// history is complete, if user makes chgs after chglog starts, don't pick them up!
									if (chi.getChangeDate().compareTo(curTime)>0) { // date is AFTER curtime                                   
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
									break;//return retValues;  // only do latest value it may be invalid
								} // each history item
							}
							else {
								trace(D.EBUG_INFO,false,"getValueForNullAttr() EANAttribute was null for "+attrCode+" in "+theItem.getKey());
							}
						}
						else {
							trace(D.EBUG_ERR,true,"getValueForNullAttr() Row for "+attrCode+" was not found for "+theItem.getKey());
						}
					} catch (Exception ee)  {
						trace(D.EBUG_ERR,true,"getValueForNullAttr() Exception getting history for "+attrCode+": "+ee.getMessage());
					}
				}
			}
			else  {
				trace(D.EBUG_DETAIL,false,theItem.getKey()+" is Active from: "+lastChi.getChangeDate()+" "+attrCode+" is null");
				// it is active at from time, so there is no value to get
			}
		}

		return retValues;//nulls may be in array
	}

	/**************************************************************************************
	 * Get FCTRANSACTION entities by invgrp that match the ids in rootchgvct
	 * The list of Feature Transactions (FCTRANSACTION) is filtered where Feature Transaction Category
	 * (FTCAT) is equal to Feature Conversion (406).
	 *
	 * @param fctransTbl Hashtable has all fctrans ids there were affected
	 * @param fromFCList EntityList has ids from rootChgVct
	 * @param rootChgVct Vector of changed entity ids that were affected in this interval
	 * @param numDays int
	 * @return Hashtable of EntitySets, key is invGrp
	 */
	private Hashtable<String, Vector<EntitySet>> getFCByInvGrp(Hashtable<String, EntityItem> fctransTbl,EntityList fromFCList,
			Vector<String> rootChgVct, int numDays) throws Exception
	{
		Hashtable<String, Vector<EntitySet>> fcByInvGrpTbl = new Hashtable<String, Vector<EntitySet>>();
		EntityGroup fromfcGrp = null;
		if (fctransTbl.size()>0 && fromFCList!=null) { // changed fctransaction found
			fromfcGrp = fromFCList.getParentEntityGroup();
			for(int i=0; i<rootChgVct.size(); i++)  {
				String mtdesc = null;
				String entityId = (String)rootChgVct.elementAt(i);
				EntityItem fromItem= fromfcGrp.getEntityItem("FCTRANSACTION"+entityId);
				EntityItem curItem= (EntityItem)fctransTbl.get("FCTRANSACTION"+entityId);
				EntitySet theSet = new EntitySet(fromItem, curItem);

				// only need Feature Transactions (FCTRANSACTION), filter where Feature Transaction Category (FTCAT) is equal
				// to Feature Conversion (406) as a current value
				String ftcat = getAttributeFlagValue(curItem, "FTCAT");
				String ftcatdesc = getAttributeValue(curItem, "FTCAT", "", null);
				if (ftcat==null)   {
					// try fromItem, note it may not have values at deletion, it will have values at fromTime
					ftcat = getAttributeFlagValue(fromItem, "FTCAT");
					ftcatdesc = getAttributeValue(fromItem, "FTCAT", "", null);
					if (ftcat==null)  {
						String tmp[]=getValueForNullAttr(fromItem, "FTCAT", "DUMMY", fromItem.getProfile().getValOn());
						ftcatdesc=tmp[0];
						ftcat=tmp[1];
						tmp[0]=null;
						tmp[1]=null;
					}
				}
				if (ftcat==null || !ftcat.equals(FTCAT_MATCH)) {
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
				//MN33235098 use TOMACHTYPE instead of FROMMACHTYPE
				//MN33235098    mtdesc = getAttributeValue(curItem, "FROMMACHTYPE", "", null);
				mtdesc = getAttributeValue(curItem, "TOMACHTYPE", "", null);
				if (mtdesc==null) {
					//try fromitem, note it may not have values at deletion, it will have values at fromTime
					//MN33235098        mtdesc = getAttributeValue(fromItem, "FROMMACHTYPE", "", null);
					mtdesc = getAttributeValue(fromItem, "TOMACHTYPE", "", null);
					if (mtdesc==null)  {
						// MN33235098           String tmp[]=getValueForNullAttr(fromItem, "FROMMACHTYPE", "DUMMY", fromItem.getProfile().getValOn());
						String tmp[]=getValueForNullAttr(fromItem, "TOMACHTYPE", "DUMMY", fromItem.getProfile().getValOn());
						mtdesc=tmp[0];
						tmp[0]=null;
						tmp[1]=null;
					}
				} // end mtdesc from curTime =null
				if (mtdesc!=null) {
					Vector<EntitySet> vct = null;
					String invGrpflag = (String)MTinvGrpTbl.get(mtdesc); // match on desc
					if (invGrpflag==null)  {
						//MN33235098            trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR: No inventory group found for "+curItem.getKey()+".FROMMACHTYPE = "+mtdesc);
						trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR: No inventory group found for "+curItem.getKey()+".TOMACHTYPE = "+mtdesc);
						//MN33235098            logDataError("No inventory group found for "+curItem.getKey()+".FROMMACHTYPE = "+mtdesc);
						logDataError("No inventory group found for "+curItem.getKey()+".TOMACHTYPE = "+mtdesc);
						continue;
					}
					//CR0503064033 skip 7, 30 day rpts for some invgrps
					if (numDays>1 && invGrpToSkipVct.contains(invGrpflag)) {
						trace(D.EBUG_SPEW,false,"getFCByInvGrp() Skipping Inventory Group ["+invGrpflag+"] for "+
								curItem.getKey()+" interval: "+numDays);
						theSet.dereference();
						continue;
					}

					vct = (Vector<EntitySet>)fcByInvGrpTbl.get(invGrpflag);
					if (vct==null)  {
						vct = new Vector<EntitySet>();
						fcByInvGrpTbl.put(invGrpflag,vct);
					}
					vct.addElement(theSet);
				}
				else {
					//MN33235098        trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR FROMMACHTYPE was not found for "+curItem.getKey());
					trace(D.EBUG_WARN,false,"getFCByInvGrp() ERROR TOMACHTYPE was not found for "+curItem.getKey());
				}
			} // check each changed root
		}
		return fcByInvGrpTbl;
	}
	/**************************************************************************************
	 * Get feature entities by invgrp that match the ids in rootchgvct
	 * @param featTbl Hashtable has feature ids there were affected
	 * @param fromFeatList EntityList has ids from rootChgVct
	 * @param rootChgVct Vector of changed entity ids that were affected in this interval
	 * @param numDays int
	 * @return Hashtable of EntitySets, key is invGrp
	 */
	private Hashtable<String, Vector<EntitySet>> getFeaturesByInvGrp(Hashtable<String, EntityItem> featTbl,
			EntityList fromFeatList, Vector<String> rootChgVct, int numDays)
					throws Exception
	{
		Hashtable<String,Vector<EntitySet>> featByInvGrpTbl = new Hashtable<String,Vector<EntitySet>>();
		EntityGroup fromfeatGrp = null;
		if (featTbl.size()>0 && fromFeatList!=null)  { // changed features found
			fromfeatGrp = fromFeatList.getParentEntityGroup();
			for(int i=0; i<rootChgVct.size(); i++)  {
				String entityId = (String)rootChgVct.elementAt(i);
				EntityItem fromItem= fromfeatGrp.getEntityItem("FEATURE"+entityId);
				EntityItem curItem= (EntityItem)featTbl.get("FEATURE"+entityId);
				EntitySet theSet = new EntitySet(fromItem, curItem);

				// try to get invgrp from curentity, if it was deactivated, it will be null
				// could use fromitem but invgrp value may have changed
				String invGrpDesc = getAttributeValue(curItem, "INVENTORYGROUP", "", null);
				String invGrpflag = getAttributeFlagValue(curItem, "INVENTORYGROUP");
				if (invGrpDesc==null)  {
					// try fromitem, note it may not have values at deletion, it will have values at fromTime
					invGrpDesc = getAttributeValue(fromItem, "INVENTORYGROUP", "", null);
					invGrpflag = getAttributeFlagValue(fromItem, "INVENTORYGROUP");
					if (invGrpDesc==null)  {
						String tmp[]=getValueForNullAttr(fromItem, "INVENTORYGROUP", "DUMMY", fromItem.getProfile().getValOn());
						invGrpDesc=tmp[0];
						invGrpflag=tmp[1];
						tmp[0]=null;
						tmp[1]=null;
					}
				} // end curTime invgrp returned null
				if (invGrpflag !=null) {
					Vector<EntitySet> vct = null;

					if (invGrpMasterTbl.get(invGrpflag)==null)  {
						logDataError("Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
						trace(D.EBUG_WARN,false,"getFeaturesByInvGrp() Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+curItem.getKey()+" not in master list, skipping it");
						theSet.dereference();
						continue;
					}

					//CR0503064033 skip 7, 30 day rpts for some invgrps
					if (numDays>1 && invGrpToSkipVct.contains(invGrpflag)) {
						trace(D.EBUG_SPEW,false,"getFeaturesByInvGrp() Skipping Inventory Group "+invGrpDesc+"["+invGrpflag+"] for "+
								curItem.getKey()+" interval: "+numDays);
						theSet.dereference();
						continue;
					}

					vct = (Vector<EntitySet>)featByInvGrpTbl.get(invGrpflag);
					if (vct==null)   {
						vct = new Vector<EntitySet>();
						featByInvGrpTbl.put(invGrpflag,vct);
					}
					vct.addElement(theSet);
				}
				else {
					trace(D.EBUG_ERR,false,"getFeaturesByInvGrp() ERROR could not find INVENTORYGROUP for "+curItem.getKey()); }
			} // check each changed root
		}
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
	 * @param invGrp
	 * @param delPSRelatorTbl Hashtable with deleted PRODSTRUCT relator typeids
	 * @param fromTime String with dts of time 1 based on minimum date
	 */
	private void getMTMChgs(Vector<EntitySet> featVct, Vector<String> chgChildVct, 
			String invGrp, Hashtable<String,Vector<String>> delPSRelatorTbl,
			String fromTime)  throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(MTM_CHG_SET+invGrp+SER_SUFFIX);
		// output this sections info
		if (featVct==null) {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getMTMChgs() No changes found");
		} else  {
			FMChgMTMSet mtm = null;
			for (int i=0; i<featVct.size(); i++)  {
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
				Vector<String> relIdVct = (Vector<String>)delPSRelatorTbl.get(curItem.getKey());
				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getMTMChgs() checking root "+curItem.getKey());
				if (relIdVct!=null)   {
					// look to see if curitem or fromitem have downlinks to 'deleted' relators in hashtable
					// if they do, this relator will be handled later
					for (int r=0; r<relIdVct.size(); r++)   {
						String relId = (String)relIdVct.elementAt(r);
						if (fromItem.getDownLink(relId)==null &&
								curItem.getDownLink(relId)==null)    {
							// split relId into type and id
							String theId = getId(relId);
							trace(D.EBUG_INFO,false,"getMTMChgs() "+relId+
									" relator was created and deleted in this interval for "+curItem.getKey());
							mtm = new FMChgMTMDelSet(theId, curItem.getEntityID(), fromTime, curTime);
							mtm.calculateOutput(dbCurrent,profile,this);
							if(FMChgRpt.isValidChange(mtm)){
								chgSetVct.addElement(mtm);
							}
						} else {
							trace(D.EBUG_INFO,false,"getMTMChgs() "+relId+" relator was deleted for "+
									curItem.getKey()+" BUT was found in fromitem or curitem");
						}
					}
				}

				if (curItem.getDownLinkCount()==0&& fromItem.getDownLinkCount()==0)   {
					// prodstruct must have been created and deleted in the timeframe or never existed
					trace(D.EBUG_INFO,false,"getMTMChgs() No PRODSTRUCT relator found for "+curItem.getKey());
					continue;
				}

				// get prodstructs
				for (int p=0; p<curItem.getDownLinkCount(); p++)    {
					EntityItem theProdItem = (EntityItem)curItem.getDownLink(p);
					// was this item added?
					if (fromItem.getDownLink(theProdItem.getKey())==null)  {
						trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" was ADDED or RESTORED");
						mtm = new FMChgMTMSet(null, theProdItem, fromTime, curTime);
						mtm.calculateOutput(dbCurrent,profile,this);
						if(FMChgRpt.isValidChange(mtm)){
							chgSetVct.addElement(mtm);
						}
					} else
						if (chgChildVct.contains(theProdItem.getKey()))   {
							EntityItem fitem = null;
							trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" had some change ");

							fitem = (EntityItem)fromItem.getDownLink(theProdItem.getKey());
							mtm = new FMChgMTMSet(fitem, theProdItem, fromTime, curTime);
							mtm.calculateOutput(dbCurrent,profile,this);
							if(FMChgRpt.isValidChange(mtm)){
								chgSetVct.addElement(mtm);
							}
						} else   {
							trace(D.EBUG_SPEW,false,"getMTMChgs() "+curItem.getKey()+" relator "+theProdItem.getKey()+" did NOT change");
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
				for (int p=0; p<fromItem.getDownLinkCount(); p++)  {
					EntityItem theProdItem = (EntityItem)fromItem.getDownLink(p);
					// was this item deleted?
					if (curItem.getDownLink(theProdItem.getKey())==null) {
						trace(D.EBUG_INFO,false,"getMTMChgs() relator "+theProdItem.getKey()+" was DELETED");
						mtm = new FMChgMTMSet(theProdItem, null, fromTime, curTime);
						mtm.calculateOutput(dbCurrent,profile,this);
						if(FMChgRpt.isValidChange(mtm)){
							chgSetVct.addElement(mtm);
						}
					}
				}
			}

			writeChgSet(MTM_CHG_SET+invGrp+SER_SUFFIX,chgSetVct);
		}
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
	 * @param chgRpt FMChgRpt for output
	 * @param invGrp inventory group
	 */
	private void completeMTMChgs(FMChgRpt chgRpt, String invGrp)  throws
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(MTM_CHG_SET+invGrp+SER_SUFFIX);
		String sectionTitle = FMChgMTMSet.getSectionTitle();
		String sectionInfo = FMChgMTMSet.getSectionInfo();
		String noneFndText = FMChgMTMSet.getNoneFndText();

		// output this sections info
		if (chgSetVct.size()==0) {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getMTMChgs() No changes found");
		}
		else  {
			Collections.sort(chgSetVct);
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgMTMSet.getColumnHeader(), noneFndText);

		// release memory
		for (int p=0; p<chgSetVct.size(); p++) {
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
	 * @param invGrp inventory group
	 * @param fromTime dts of start of interval
	 */
	private void getFCChgs(Vector<EntitySet> chgRootVct, String invGrp, String fromTime)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FC_CHG_SET+invGrp+SER_SUFFIX); // accumulate all for this invgrp
		if (chgRootVct.size()>0)   {
			// handle all changed feature
			for (int p=0; p<chgRootVct.size(); p++)   {
				FMChgFCSet fc = null;
				EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCChgs() checking "+theSet);
				fc = new FMChgFCSet(theSet.getFromItem(), theSet.getCurItem(), fromTime, curTime);
				fc.calculateOutput(dbCurrent,profile,this);
				
				if(FMChgRpt.isValidChange(fc)){
					chgSetVct.addElement(fc);
				}
			}
			writeChgSet(FC_CHG_SET+invGrp+SER_SUFFIX,chgSetVct);
		}
		else {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCChgs() No changes found");
		}
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
	 * @param chgRootVct
	 * @param invGrp
	 * @param fromTime
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws Exception
	 */
	private void completeFCChgs(FMChgRpt chgRpt, String invGrp)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FC_CHG_SET+invGrp+SER_SUFFIX);
		String sectionTitle = FMChgFCSet.getSectionTitle();
		String sectionInfo = FMChgFCSet.getSectionInfo();
		String noneFndText = FMChgFCSet.getNoneFndText();

		if (chgSetVct.size()>0)   {
			// handle all changed feature
			Collections.sort(chgSetVct);
		}  else {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCChgs() No changes found");
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgFCSet.getColumnHeader(), noneFndText);

		// release memory
		for (int p=0; p<chgSetVct.size(); p++)  {
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
	 * @param fromTime dts of start of interval
	 */
	private void getFeatureWithdrawalChgs(Vector<EntitySet> chgRootVct, String invGrp, String fromTime)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FEATWD_CHG_SET+invGrp+SER_SUFFIX); // accumulate all for this invgrp
		if (chgRootVct.size()>0)    {
			// handle all changed feature
			for (int p=0; p<chgRootVct.size(); p++)    {
				FMChgGFWSet fc = null;
				EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureWithdrawalChgs() checking "+theSet);
				fc = new FMChgGFWSet(theSet.getFromItem(), theSet.getCurItem(), fromTime, curTime);
				fc.calculateOutput(dbCurrent,profile,this);
				if(FMChgRpt.isValidChange(fc)){
					chgSetVct.addElement(fc);
				}
			}
			writeChgSet(FEATWD_CHG_SET+invGrp+SER_SUFFIX,chgSetVct);
		}  else  {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureWithdrawalChgs() No changes found");
		}
	}
	/**
	 * @param chgRpt
	 * @param invGrp
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws Exception
	 */
	private void completeFeatureWithdrawalChgs(FMChgRpt chgRpt, String invGrp)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FEATWD_CHG_SET+invGrp+SER_SUFFIX);
		String sectionTitle = FMChgGFWSet.getSectionTitle();
		String sectionInfo = FMChgGFWSet.getSectionInfo();
		String noneFndText = FMChgGFWSet.getNoneFndText();

		if (chgSetVct.size()>0)    {
			// handle all changed feature
			Collections.sort(chgSetVct);
		}  else    {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureWithdrawalChgs() No changes found");
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgGFWSet.getColumnHeader(), noneFndText);

		// release memory
		for (int p=0; p<chgSetVct.size(); p++)   {
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
	 * @param invGrp
	 * @param fromTime dts of start of interval
	 */
	private void getFCTransChgs(Vector<EntitySet> fcVct, Vector<String> chgChildVct, String invGrp,String fromTime)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FCTRANS_CHG_SET+invGrp+SER_SUFFIX);

		// output this sections info
		if (fcVct!=null)    {
			for (int i=0; i<fcVct.size(); i++)    {
				EntitySet theSet = (EntitySet)fcVct.elementAt(i);
				// use cur entity as basis
				EntityItem curItem = theSet.getCurItem();  // FCTRANSACTION
				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCTransChgs() checking "+curItem.getKey());
				// was a change made in the root FCTRANSACTION?
				if (chgChildVct.contains(curItem.getKey()))  {
					trace(D.EBUG_INFO,false,"getFCTransChgs() "+curItem.getKey()+" had some change ");
					FMChgFCTransSet fc = new FMChgFCTransSet(theSet.getFromItem(),
							theSet.getCurItem(), fromTime, curTime);
					fc.calculateOutput(dbCurrent,profile,this);
					if(FMChgRpt.isValidChange(fc)){
						chgSetVct.addElement(fc);
					}
				}
				else   {
					trace(D.EBUG_INFO,false,"getFCTransChgs() "+curItem.getKey()+" did NOT change ");
					continue;  // no structure here
				}
			}
			writeChgSet(FCTRANS_CHG_SET+invGrp+SER_SUFFIX,chgSetVct);
		}
		else  {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCTransChgs() No changes found");
		}
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
	private void completeFCTransChgs(FMChgRpt chgRpt, String invGrp)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(FCTRANS_CHG_SET+invGrp+SER_SUFFIX);
		String sectionTitle = FMChgFCTransSet.getSectionTitle();
		String sectionInfo = FMChgFCTransSet.getSectionInfo();
		String noneFndText = FMChgFCTransSet.getNoneFndText();

		// output this sections info
		if (chgSetVct.size()>0)   {
			Collections.sort(chgSetVct);
		}
		else  {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFCTransChgs() No changes found");
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgFCTransSet.getColumnHeader(), noneFndText);

		// release memory
		for (int p=0; p<chgSetVct.size(); p++)   {
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
	private void getFeatureRFAChgs(Vector<EntitySet> chgRootVct, String invGrp,String fromTime)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(RFA_CHG_SET+invGrp+SER_SUFFIX); // accumulate all for this invgrp
		if (chgRootVct.size()>0)  {
			// handle all changed feature
			for (int p=0; p<chgRootVct.size(); p++)  {
				FMChgRFASet fc = null;
				EntitySet theSet = (EntitySet)chgRootVct.elementAt(p);
				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureRFAChgs() checking "+theSet);
				fc = new FMChgRFASet(theSet.getFromItem(),
						theSet.getCurItem(), fromTime, curTime);
				fc.calculateOutput(dbCurrent,profile,this);
				if(FMChgRpt.isValidChange(fc)){
					chgSetVct.addElement(fc);
				}
			}
			writeChgSet(RFA_CHG_SET+invGrp+SER_SUFFIX,chgSetVct);
		}
		else   {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureRFAChgs() No changes found");
		}
	}
	/**
	 * read in the RFA change set, sort and write to the change report
	 * @param chgRpt
	 * @param invGrp
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws Exception
	 */
	private void completeFeatureRFAChgs(FMChgRpt chgRpt,String invGrp)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		Vector<FMChgSet> chgSetVct = readChgSet(RFA_CHG_SET+invGrp+SER_SUFFIX);
		String sectionTitle = FMChgRFASet.getSectionTitle();
		String sectionInfo = FMChgRFASet.getSectionInfo();
		String noneFndText = FMChgRFASet.getNoneFndText();

		if (chgSetVct.size()>0)  {
			Collections.sort(chgSetVct);
		}  else   {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getFeatureRFAChgs() No changes found");
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgRFASet.getColumnHeader(), noneFndText);

		// release memory
		for (int p=0; p<chgSetVct.size(); p++)  {
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
	 * Start at 30 day interval, call eDoc to find all changed MODELc roots and changed children
	 * using a VE that does not include MODELa.  MODELa.MACHTYPEATR is used to determine the InventoryGrp
	 *
	 * Pull EntityList for all affected roots at curTime using VE with MODELa, hang onto it for 7 day check
	 * buildSDLog pulls EntityList for all affected roots at fromTime and pairs the entity at curTime
	 * with its match at fromTime.
	 * Find all MODELc-->MDLCGOSMDL that should be output, then find the InventoryGrp from MODELa.
	 * Determine InventoryGrp last because of the possible breakage of links back to MODELa.
	 * Calls to eDoc with the large VE may be needed to find the deleted InvGrp.
	 *
	 * It is possible that the changes are not in the set of attributes reported in each section.
	 * A file is generated for each InventoryGroup found from setup. Each section is handled and
	 * then written to the PDH
	 * @param pdhWriter FMChgWritePDH
	 * @return Vector of FMChgRpt with info to write to PDH
	 */
	Vector<FMChgRpt> buildSDLogs(FMChgWritePDH pdhWriter)
			throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			COM.ibm.eannounce.objects.EANBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
			java.io.UnsupportedEncodingException,
			java.rmi.RemoteException, Exception
	{
		String fromTime = null;
		Hashtable<String, Vector<String>> delRelatorTbl = null;
		Vector<String> vctArray[] = null;
		Vector<String> rootChgVct = null;
		EntityList curSDList = null;

		int numDays = THIRTY;
		swTimer.lap(); // reset timer

		// find out what changed over 30 days
		trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"********************* "+numDays+" Days *****************************************************");
		fromTime = calculateFromTime(numDays);

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of SD logs for "+numDays+" days  curTime "+
				curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

		trace(D.EBUG_INFO,false,"buildSDLogs()***** Find changed MODELCGOS-MDLCGOSMDL-MODELc ***************");

		delRelatorTbl = new Hashtable<String, Vector<String>>();
		// use small VE to find get MODELc and MDLCGOSMDL changes
		//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS
		vctArray = getAffectedRootsAndChildren(SD_EXTRACTNAME,SD_ROOTTYPE, fromTime, delRelatorTbl);
		rootChgVct = vctArray[0];  // index 0 is roots of structure with changes

		// pull all affected entities
		// do actual pull for these items.. do 30 first, that will include all for 7 day
		// do it here and pass it to reuse it in 7 day
		//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS use MACHTYPEATR on MODELc to determine the invgrp for FM mapping
		//-- Entity:MODELCGOS<----Relator:MDLCGMDLCGOS<---Entity:MODELCG
		//-- Entity:MODELCG---->Relator:MDLCGMDL--->Entity:MODELa  use MACHTYPEATR on MODELa to determine the invgrp for this suppdevice
		curSDList = pullEntityItems(rootChgVct, profile,SD_MTM_EXTRACTNAME,SD_ROOTTYPE);
		trace(D.EBUG_INFO,false,"buildSDLogs() curSDList: "+FMChgLog.NEWLINE+outputList(curSDList));
		flushLogs();

		// find 30 day changes and write out
		buildSDLog(pdhWriter, vctArray,curSDList,numDays, fromTime, delRelatorTbl);
		vctArray[0].clear(); // root that changed structure
		vctArray[1].clear(); // child or root that changed
		for (Enumeration<Vector<String>> e = delRelatorTbl.elements(); e.hasMoreElements();)    {
			Vector<String> vct = (Vector<String>)e.nextElement();
			vct.clear();
		}
		delRelatorTbl.clear();

		numDays = SEVEN;
		trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"******************** "+numDays+" Days ******************************************************");
		// find 7 day changes and write out
		fromTime = calculateFromTime(numDays);

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Start of SD logs for "+numDays+" days  curTime "+
				curTime+" fromTime: "+fromTime+" mtpFromTime: "+mtpFromTime+" ***************");

		trace(D.EBUG_INFO,false,"buildSDLogs()***** Find changed MODELCGOS-MDLCGOSMDL-MODELc ***************");
		vctArray = getAffectedRootsAndChildren(SD_EXTRACTNAME,SD_ROOTTYPE, fromTime, delRelatorTbl);
		flushLogs();

		buildSDLog(pdhWriter,vctArray,curSDList, numDays, fromTime,delRelatorTbl);
		vctArray[0].clear(); // root that changed structure
		vctArray[1].clear(); // child or root that changed

		for (Enumeration<Vector<String>> e = delRelatorTbl.elements(); e.hasMoreElements();) {
			Vector<String> vct = (Vector<String>)e.nextElement();
			vct.clear();
		}
		delRelatorTbl.clear();
		delRelatorTbl = null;
		if (curSDList !=null) {
			curSDList.dereference(); }

		return chgRptVct;
	}

	/**************************************************************************************
	 * Build SD logs for a particular interval
	 * @param pdhWriter FMChgWritePDH
	 * @param vctArray Vector[] index[0]with changed root entity ids, index[1] entities that had changes, child or root
	 * @param numDays int with interval
	 * @param fromTime String with dts of time 1 based on minimum date
	 * @param delRelatorTbl Hashtable with deleted relator typeids
	 */
	private void buildSDLog(FMChgWritePDH pdhWriter, Vector<String> vctArray[], EntityList curSDList,
			int numDays, String fromTime, Hashtable<String, Vector<String>> delRelatorTbl)
					throws java.sql.SQLException,
					COM.ibm.opicmpdh.middleware.MiddlewareException,
					COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
					COM.ibm.eannounce.objects.EANBusinessRuleException,
					COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
					java.io.UnsupportedEncodingException,
					java.rmi.RemoteException, Exception
	{
		EntityList fromSDList = null;
		Hashtable<String, Hashtable<String, FMChgSet>> sdByInvGrpTbl = new Hashtable<String, Hashtable<String, FMChgSet>>();
		Hashtable<String, Hashtable<String, FMChgSet>> sdMTMByInvGrpTbl = new Hashtable<String, Hashtable<String, FMChgSet>>();

		Vector<String> rootChgVct=vctArray[0];
		Vector<String> sdChildChgVct=vctArray[1];
		// get entities at from time
		Profile fromProfile = profile.getNewInstance(dbCurrent);
		fromProfile.setValOnEffOn(fromTime, fromTime);
		//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS use MACHTYPEATR on MODELc to determine the invgrp for FM mapping
		//-- Entity:MODELCGOS<----Relator:MDLCGMDLCGOS<---Entity:MODELCG
		//-- Entity:MODELCG---->Relator:MDLCGMDL--->Entity:MODELa  use MACHTYPEATR on MODELa to determine the invgrp for this suppdevice
		fromSDList = pullEntityItems(rootChgVct, fromProfile,SD_MTM_EXTRACTNAME, SD_ROOTTYPE);
		trace(D.EBUG_INFO,false,"buildSDLog() fromTime: "+fromTime+" fromSDList: "+FMChgLog.NEWLINE+outputList(fromSDList));

		trace(D.EBUG_INFO,false,"buildSDLog()***** Group changed SUPPDEVICE by InventoryGroup for "+numDays+" days ***************");
		// find MODELc and MDLCGOSMDL that need to be output
		getChangedSD(curSDList, fromSDList, rootChgVct, sdChildChgVct,
				delRelatorTbl, fromTime, sdByInvGrpTbl,sdMTMByInvGrpTbl);

		// complete one inv grp at a time, write it out
		for (Enumeration<String> e = invGrpMasterTbl.keys(); e.hasMoreElements();)  {
			FMChgRpt chgRpt = null;
			Hashtable<String, FMChgSet> sdTbl = null;
			String invGrp = (String)e.nextElement(); // key is invgrp flag code

			// write out the outputSb
			String brandflag = BRANDFLAG_DEFAULT;
			Vector<String> brVct = (Vector<String>)invGrpBrTbl.get(invGrp);
			if (brVct!=null)    {
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

			//CR0503064033 skip 7, 30 day rpts for some invgrps
			if (numDays>1 && invGrpToSkipVct.contains(invGrp)) {
				trace(D.EBUG_SPEW,false,"buildSDLog() Skipping Inventory Group ["+invGrp+"] SD report for "+
						"interval: "+numDays);
				continue;
			}

			chgRpt = new FMChgSDRpt(this, numDays, curTime, invGrp, brandflag);

			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"***CLSTATUS**** build SD log for InventoryGroup "+invGrpMasterTbl.get(invGrp)+"["+invGrp+"] ***************");

			chgRpt.setHeading(fromTime,(String)invGrpMasterTbl.get(invGrp),getBrandForInvGrp(invGrp));

			// one Hashtable for each (MDLCGOSMDL) MODELa.invGrp
			sdTbl = (Hashtable<String, FMChgSet>)sdMTMByInvGrpTbl.get(invGrp);
			trace(D.EBUG_INFO,false,"buildSDLog() found "+((sdTbl!=null)?""+sdTbl.size():"0")+" changed "+invGrpMasterTbl.get(invGrp)+" MODELc and structure");
			getSuppDevMTMChgs(sdTbl,chgRpt);

			// one Hashtable for each MODELa.invGrp
			sdTbl = (Hashtable<String, FMChgSet>)sdByInvGrpTbl.get(invGrp);
			trace(D.EBUG_INFO,false,"buildSDLog() found "+((sdTbl!=null)?""+sdTbl.size():"0")+" changed "+invGrpMasterTbl.get(invGrp)+" MODELc");
			getSuppDevChgs(sdTbl,chgRpt);
			// a suppdevice may be in more than one invgrp so dereference all when done
			// output the report
			if (pdhWriter.createReportEntity(chgRpt)) {  // true then blob was small enough to write to PDH now
				chgRpt.dereference();
			}else {
				chgRptVct.add(chgRpt);  // use this later to copy file from server and put in PDH
			}
		}
		invDebugStr="";

		trace(D.EBUG_ERR,true,"***CLSTATUS**** Completed SD logs for "+numDays+" days timing "+
				getElapsedTime()+" ***************");
		flushLogs();

		// release memory
		if (fromSDList!=null) {
			fromSDList.dereference(); }

		sdMTMByInvGrpTbl.clear();
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
		String lines=text;
		if (text.length()>MAX_STR_LEN){
			int lineLength = 0;
			int start =  0;
			int end =0;
			BreakIterator boundary = BreakIterator.getLineInstance();
			StringBuffer sb = new StringBuffer();

			boundary.setText(text);
			start = boundary.first();
			end = boundary.next();
			while (end != BreakIterator.DONE)   {
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
			lines= sb.toString();
		}
		return lines;
	}

	/**************************************************************************************
	 * Find MODELc and MDLCGOSMDL that have changed and need to be output
	 * idea is to find the entities that have tracked changes and then find the invgrps to
	 * put them into
	 *
	 * @param curSDList EntityList with extract at curtime for all changed root entity ids (30 and 7 days)
	 * @param fromSDList EntityList with extract at fromtime for changed root entity ids
	 * @param rootChgVct Vector with changed root entity ids
	 * @param chgChildVct Vector with child or root that had the change
	 * @param delRelatorTbl Hashtable with deleted MDLCGOSMDL relator ids
	 * @param fromTime String with dts of time 1 based on minimum date
	 * @param sdByInvGrpTbl Hashtable to be filled in with SuppDev chgs
	 * @param sdMTMByInvGrpTbl Hashtable to be filled in with SuppDev matrix chgs
	 */
	private void getChangedSD(EntityList curSDList,EntityList fromSDList,
			Vector<String> rootChgVct, Vector<String> chgChildVct, Hashtable<String, 
			Vector<String>> delRelatorTbl, String fromTime,
			Hashtable<String, Hashtable<String, FMChgSet>> sdByInvGrpTbl, 
			Hashtable<String, Hashtable<String, FMChgSet>> sdMTMByInvGrpTbl) throws Exception
	{
		// these lists will have used the large extract
		if (curSDList!=null && fromSDList!=null){  //changed MODELc (suppdev) found
			EntityGroup cursdGrp = curSDList.getParentEntityGroup();
			EntityGroup fromsdGrp = fromSDList.getParentEntityGroup();

			for(int i=0; i<rootChgVct.size(); i++) {// root ids that changed in this interval
				String entityId = (String)rootChgVct.elementAt(i); // MODELc roots
				// these items will exist because they are roots, they may have null attributes
				// if the entity was deactivated at the DTS of the extract
				EntityItem fromItem= fromsdGrp.getEntityItem(cursdGrp.getEntityType()+entityId);
				EntityItem curItem= cursdGrp.getEntityItem(cursdGrp.getEntityType()+entityId);
				Vector<String> relIdVct = (Vector<String>)delRelatorTbl.get(curItem.getKey());

				trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"###############################"+FMChgLog.NEWLINE+
						"getChangedSD() start check MODELc root["+i+"] "+curItem.getKey()+
						" MACHTYPEATR:"+ getAttributeValue(curItem, "MACHTYPEATR", "", null));

				// was a change made in the root MODELc?
				if (chgChildVct.contains(curItem.getKey())) {
					trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" create FMChgSuppDevSet to chk MODELc chgs");
					createSuppDevSet(fromItem, curItem, fromTime, sdByInvGrpTbl); // root MODELc had chgs
					trace(D.EBUG_INFO,false,"-------------------------------"+FMChgLog.NEWLINE);
				}
				else{
					trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" did NOT change");
				}

				//-- Entity:MODELc<----Relator:MDLCGOSMDL<--Entity:MODELCGOS
				// look at delRelatorTbl to see if a relator was deleted
				if (relIdVct!=null)   {
					// look to see if curitem or fromitem have uplinks to 'deleted' relators in hashtable
					// if they do, this relator will be handled later
					for (int r=0; r<relIdVct.size(); r++)   {
						String relId = (String)relIdVct.elementAt(r);
						// only care about immediate relators here, small VE was used in edoc so this should be the only relator
						if(relId.startsWith("MDLCGOSMDL")){
							if (fromItem.getUpLink(relId)==null &&
									curItem.getUpLink(relId)==null)  // relator was created and deleted in the interval
							{
								// split relId into type and id
								String theId = getId(relId);

								trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" "+relId+
										" relator was created and deleted in this interval create FMChgSDMTMDelSet");
								createSuppDevMTMDelSet(theId, curItem, fromTime, sdMTMByInvGrpTbl);
								trace(D.EBUG_INFO,false,"-------------------------------"+FMChgLog.NEWLINE);
							}
							else {
								trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" "+relId+
										" relator was deleted BUT was found in fromitem or curitem");
							}
						}
						else {
							trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" "+relId+
									" relator was deleted for BUT was not MDLCGOSMDL");
						}
					}
				}

				if (curItem.getUpLinkCount()==0&& fromItem.getUpLinkCount()==0)
				{
					// devsupport must have been created and deleted in the timeframe or never existed
					// it would have been handled in relIdVct check
					trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+
							" No uplinks (MDLCGOSMDL relator) found");
					continue;
				}

				if(curItem.getUpLinkCount()>0){
					trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getChangedSD() "+curItem.getKey()+" checking "+curItem.getUpLinkCount()+
							" current uplinks");
					// get changes
					for (int p=0; p<curItem.getUpLinkCount(); p++)	{
						EntityItem relItem = (EntityItem)curItem.getUpLink(p);
						if (relItem.getEntityType().equals("MDLCGOSMDL")) {
							// was this item added?
							if (fromItem.getUpLink(relItem.getKey())==null)	{
								trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" curtime relator "+relItem.getKey()+
										" was ADDED or RESTORED. create FMChgSuppDevMTMSet");
								createSuppDevMTMSet(null, relItem, fromTime,sdMTMByInvGrpTbl);
								trace(D.EBUG_INFO,false,"-------------------------------"+FMChgLog.NEWLINE);
							}
							else {// fromtime has this relator too
								if (chgChildVct.contains(relItem.getKey())){// was it changed?
									EntityItem fitem = (EntityItem)fromItem.getUpLink(relItem.getKey());
									trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+
											" checking for MDLCGOSMDL chgs rel: "+relItem.getKey()+" create FMChgSuppDevMTMSet");
									createSuppDevMTMSet(fitem, relItem, fromTime,sdMTMByInvGrpTbl);
									trace(D.EBUG_INFO,false,"-------------------------------"+FMChgLog.NEWLINE);
								}
								else  {
									trace(D.EBUG_SPEW,false,"getChangedSD() "+curItem.getKey()+" curtime relator "+relItem.getKey()+" did NOT change");
								}
							}
						}else{
							trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" found unwanted curtime relator from large VE "+relItem.getKey());
						}
					}
				}

				if (fromItem.getUpLinkCount()>0){
					trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getChangedSD() "+curItem.getKey()+" checking "+fromItem.getUpLinkCount()+
							" fromtime uplinks");
					for (int p=0; p<fromItem.getUpLinkCount(); p++)	{
						EntityItem relItem = (EntityItem)fromItem.getUpLink(p);
						if (relItem.getEntityType().equals("MDLCGOSMDL")) {
							// was this item deleted?
							if (curItem.getUpLink(relItem.getKey())==null)	{
								trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" fromtime relator "+relItem.getKey()+
										" was DELETED. create FMChgSuppDevMTMSet");
								createSuppDevMTMSet(relItem, null, fromTime,sdMTMByInvGrpTbl);
								trace(D.EBUG_INFO,false,"-------------------------------"+FMChgLog.NEWLINE);
							} else  {
								trace(D.EBUG_SPEW,false,"getChangedSD() "+curItem.getKey()+" fromtime relator "+relItem.getKey()+" did NOT change (not deleted)");
							}
						}else{
							trace(D.EBUG_INFO,false,"getChangedSD() "+curItem.getKey()+" found unwanted fromtime relator from large VE "+relItem.getKey());
						}
					}
				}
			} // end rootchg loop
		}
	}
	/**************************************************************************************
	 * SuppDevice must get Inventory Group from MODELa.MACHTYPEATR desc match.
	 * these FMChgSets could be linked to more than one MODELa and the MODELa is
	 * needed for output info in the MTM section.  So now must find the MODELas
	 * and create a unique FMChgSet for each one, with its inventorygroup
	 *
	 * This is called when the MDLCGOSMDL relator was created and deleted within the interval,
	 * it wasn't in the fromtime or curtime entitylist
	 *
	 *@param relId String  MDLCGOSMDL relator id that was deleted
	 *@param curItem EntityItem MODELc at curtime
	 *@param fromTime String fromtime
	 *@param sdMTMByInvGrpTbl Hashtable to be filled in
	 */
	private void createSuppDevMTMDelSet(String relId, EntityItem curItem, String fromTime,
			Hashtable<String, Hashtable<String, FMChgSet>> sdMTMByInvGrpTbl) throws Exception
	{
		String[] extractDates;
		String delDate, createDate;
		FMChgSDMTMDelSet chgSet = new FMChgSDMTMDelSet(
				relId, curItem.getEntityID(), fromTime, curTime);
		chgSet.calculateOutput(dbCurrent,profile,this);  // reconstitute MDLCGOSMDL, to do pull to find MODELa
		trace(D.EBUG_INFO,false,"createSuppDevMTMDelSet() created FMChgSDMTMDelSet for "+chgSet.getKey());

		extractDates = chgSet.getExtractDates();
		createDate = extractDates[0];
		delDate = extractDates[1];
		if (delDate!=null && createDate!=null)	{
			EntityList fromlist, curlist;
			EntityItem fromItem=null, delItem=null, tmpItem;
			Vector<String> idVct = new Vector<String>();
			Vector<EANEntity> fromMdlVct, curMdlVct;
			Profile fromProfile = profile.getNewInstance(dbCurrent); // create
			Profile curProfile = profile.getNewInstance(dbCurrent);  // delete
			fromProfile.setValOnEffOn(createDate, createDate);
			curProfile.setValOnEffOn(delDate, delDate);

			idVct.addElement(""+curItem.getEntityID());

			// this is a duplicate pull but is needed here because MODELa item is used as part of MTM output
			fromlist = pullEntityItems(idVct, fromProfile,chgSet.getExtractName(), curItem.getEntityType());
			trace(D.EBUG_INFO,false,"createSuppDevMTMDelSet EntityList for "+chgSet.getExtractName()+" for createDate: "+createDate+
					" contains the following entities: ");
			trace(D.EBUG_INFO,false,outputList(fromlist));

			curlist = pullEntityItems(idVct, curProfile,chgSet.getExtractName(), curItem.getEntityType());
			trace(D.EBUG_INFO,false,"createSuppDevMTMDelSet EntityList for "+chgSet.getExtractName()+" for delDate: "+delDate+
					" contains the following entities: ");
			trace(D.EBUG_INFO,false,outputList(curlist));

			tmpItem = fromlist.getParentEntityGroup().getEntityItem(0);
			if (tmpItem!=null && tmpItem.getUpLinkCount()>0) {  // get the MDLCGOSMDL relator, only want MODELa to this relator
				fromItem = (EntityItem)tmpItem.getUpLink(0);
			}
			tmpItem = curlist.getParentEntityGroup().getEntityItem(0);
			if (tmpItem!=null && tmpItem.getUpLinkCount()>0) {  // get the MDLCGOSMDL relator, only want MODELa to this relator
				delItem = (EntityItem)tmpItem.getUpLink(0);
			}

			fromMdlVct = getMODELa(fromItem);  // use reconstituted MDLCGOSMDL at create time
			curMdlVct = getMODELa(delItem);  // use reconstituted MDLCGOSMDL at delete time

			createSuppDevByInvGrp(curMdlVct, fromMdlVct, chgSet, sdMTMByInvGrpTbl, true);

			// important.. the MODELa must be used for calculateoutput before this dereference is called
			fromlist.dereference();
			curlist.dereference();

			curMdlVct.clear();
			fromMdlVct.clear();

			idVct.clear();
		}else {
			trace(D.EBUG_INFO,false,"createSuppDevMTMDelSet() could not get timestamp to pull MODELa for "+curItem.getKey());
		}

		chgSet.dereference();  // this one no longer needed, a copy of it with model was created
	}

	/**************************************************************************************
	 * SuppDevice must get Inventory Group from MODELa.MACHTYPEATR desc match.
	 * these FMChgSets could be linked to more than one MODELa and the MODELa is
	 * needed for output info in the MTM section.  So now must find the MODELas
	 * and create a unique FMChgSet for each one, with its inventorygroup
	 *
	 * This is called for SuppDev matrix changes, it also could have been created or removed
	 * in this interval
	Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS
	Entity:MODELCGOS<----Relator:MDLCGMDLCGOS<---Entity:MODELCG
	Entity:MODELCG---->Relator:MDLCGMDL--->Entity:MODELa

	MODEL84639<---MDLCGOSMDL32<--MODELCGOS16
	chgd attr on MDLCGOSMDL32
	[0] 000000      GenArea
	[1] MODEL       RootType
	[2] 84639       RootID
	[3] ON          RootTran
	[4] MDLCGOSMDL  strChildType
	[5] 32          ChildID
	[6] ON          ChildTran
	[7] 0           ChildLevel
	[8] R           ChildClass
	[9] MDLCGOSMDL  ChildPath
	[10] MODEL      RelChildType
	[11] 84639      RelChildID
	[12] MODELCGOS
	[13] 16
	[14] DEPRECATED
	Matrix (MDLCGOSMDL) section affected by:
		changes in MDLCGOSMDL.PUBFROM or add or remove of MDLCGOSMDL relator
		last editor and date is based on MDLCGOSMDL relator history
		File it is output in affected by:
			MODELa MACHTYPEATR--->invgrp
        add/delete of MDLCGMDL are ignored
        add/delete of MDLCGMDLCGOS are ignored
    Problems here because a MDLCGOSMDL may have a new MODELa parent (or invgrp) but the editor and date
    are based on MDLCGOSMDL, not on the reason MDLCGOSMDL has a new MODELa parent (or invgrp) so
    these chgs are not tracked!!
	 *@param fromItem EntityItem MDLCGOSMDL relator at fromtime may be null
	 *@param curItem EntityItem MDLCGOSMDL relator at curtime may be null
	 *@param fromTime String fromtime
	 *@param sdMTMByInvGrpTbl Hashtable to be filled in
	 */
	private void createSuppDevMTMSet(EntityItem fromItem, EntityItem curItem, String fromTime,
			Hashtable<String, Hashtable<String, FMChgSet>> sdMTMByInvGrpTbl) throws Exception
	{
		String chgType;
		FMChgSuppDevMTMSet chgSet = new FMChgSuppDevMTMSet(fromItem,
				curItem, fromTime, curTime);

		chgSet.calculateOutput(dbCurrent,profile,this); // find out if there was a valid change or not
		trace(D.EBUG_INFO,false,"createSuppDevMTMSet() created FMChgSuppDevMTMSet for "+chgSet.getKey());

		/*
		 * SuppDev must find MODELa parent for inv group and the MTM column in the SD matrix section
		 * If structure (MTM section) is involved, then from (previous) MODELa is
		 * needed if the MDLCGOSMDL relator was changed or removed.  If it was changed then use
		 * current MODELa invgrp, if removed, then use previous MODELa invgrp
		 */
		chgType = chgSet.getChgType();
		if (chgType.equals(FMChgLogGen.NOCHG)){
			trace(D.EBUG_INFO,false,"createSuppDevMTMSet() "+chgSet.getKey()+
					" had some change but not one of the output attributes");
		}else { // must be REMOVED, ADDED or CHANGED, either case use current MODELa invgrp
			Vector<EANEntity> curMdlVct,fromMdlVct;
			boolean useCurModela = true;//(!chgType.equals(FMChgLogGen.REMOVED));
			if(curItem!=null){ // must be MDLCGOSMDL
				curMdlVct = getMODELa(curItem);
			}else{
				curMdlVct = new Vector<EANEntity>(1);
				useCurModela = false;
			}
			if(fromItem!=null){ // must be MDLCGOSMDL
				fromMdlVct = getMODELa(fromItem);
			}else{
				fromMdlVct = new Vector<EANEntity>(1);
			}

			createSuppDevByInvGrp(curMdlVct, fromMdlVct, chgSet, sdMTMByInvGrpTbl,useCurModela);

			curMdlVct.clear();
			fromMdlVct.clear();
		}
		chgSet.dereference();  // this one no longer needed, a copy of it with model was created
	}
	/**************************************************************************************
	 * SuppDevice must get Inventory Group from MODELa.MACHTYPEATR desc match.
	 * these FMChgSets could be linked to more than one MODELa and the MODELa is
	 * needed for output info in the MTM section.  So now must find the MODELas
	 * and create a unique FMChgSet for each one, with its inventorygroup
	 *
	 * This is called for SuppDev changes only, not structure
	MODELc section affected by:
		specific attribute changes in MODELc or add or delete MODELc entity
		last editor and date is based on MODELc entity history
		File it is output in affected by:
			 MODELa MACHTYPEATR--->invgrp
			add/delete of MDLCGMDL - ignored
			add/delete of MDLCGMDLCGOS- ignored
			add/delete of MDLCGOSMDL- ignored
		Problems here because a MODELc may have a new MODELa parent (or invgrp) but the editor and date
		are based on MODELc, not on the reason MODELc has a new MODELa parent (or invgrp)

		Also what happens if MODELc has some valid change in the interval and for some reason
		it now has a new invgrp?
		Is it a type=Change in the new invgrp file and use the values of MODELc at current time?
		or is it type=Add for the new invgrp using the values of MODELc at current time?
		and/or is it type=Delete for the old invgrp using values of MODELc at current time?
		or is it type=Delete for the old invgrp using values of MODELc at from time?
		Do I need to check if the MODELc changes preceded the change that affected the invgrp?
		What editor and date are to be used in all of these conditions?
		What editor and date to use if several things caused change in invgrp, for example an
		add or deletion of multiple relators in the path to MODELa?

		The spec states chgs in invgrp will not be captured.
	 *
	 *@param fromItem EntityItem MODELc at fromtime
	 *@param curItem EntityItem MODELc at curtime
	 *@param fromTime String fromtime
	 *@param sdByInvGrpTbl Hashtable to be filled in
	 */
	private void createSuppDevSet(EntityItem fromItem, EntityItem curItem, String fromTime,
			Hashtable<String, Hashtable<String, FMChgSet>> sdByInvGrpTbl) throws Exception
	{
		Vector<EANEntity> fromMdlVct = new Vector<EANEntity>(1);
		Vector<EANEntity> curMdlVct = new Vector<EANEntity>(1);
		String chgType;
		// instantiate a chgset, it can easily determine if tracked changes occurred
		FMChgSuppDevSet chgSet = new FMChgSuppDevSet(fromItem,
				curItem, fromTime, curTime); // don't know invgrp(s) to put this in yet
		chgSet.calculateOutput(dbCurrent,profile,this); // find out if there was a valid change or not
		trace(D.EBUG_INFO,false,"createSuppDevSet() created FMChgSuppDevSet for "+chgSet.getKey());

		/*
		 * SuppDev must find MODELa parent for inv group and the MTM column in the SD matrix section
		 * If no structure (not MTM section) is involved, then from (previous) MODELa is only
		 * needed if the MODELc was deactivated.
		 */

		chgType = chgSet.getChgType();
		if (chgType.equals(FMChgLogGen.DELETED)){
			fromMdlVct = getMODELa(fromItem); // get all MODELa parents at fromtime
			// create FMChgSets for the MODELa invGrps
			createSuppDevByInvGrp(curMdlVct, fromMdlVct, chgSet, sdByInvGrpTbl, false);
			fromMdlVct.clear();
		}else if (chgType.equals(FMChgLogGen.NOCHG)){
			trace(D.EBUG_INFO,false,"createSuppDevSet() ROOT "+curItem.getKey()+
					" had some change but not one of the output attributes");
		}else { // must be ADDED or CHANGED, either case use current MODELa invgrp
			curMdlVct = getMODELa(curItem);  // get all MODELa parents at curtime
			// create FMChgSets for the MODELa invGrps
			createSuppDevByInvGrp(curMdlVct, fromMdlVct, chgSet, sdByInvGrpTbl,true);
			curMdlVct.clear();
		}

		chgSet.dereference();  // this one no longer needed, a copy of it with model was created
	}

	/**************************************************************************************
	 * SuppDevice must get Inventory Group from MODELa.MACHTYPEATR desc match.
	 * these FMChgSets could be linked to more than one MODELa and the MODELa is
	 * needed for output info in the MTM section.  So now must find the MODELas
	 * and create a unique FMChgSet for each one, with its inventorygroup
	 *
	 *@param curMdlVct Vector all MODELa parents at curtime for item in chgSet
	 *@param fromMdlVct Vector all MODELa parents at fromtime for item in chgSet
	 *@param chgSet FMChgSet SD item or SDMTM item that had valid changes in this interval
	 *@param sdByInvGrpTbl Hashtable to be filled in
	 *@param useCurrentModela boolean if true, find invgrp from current MODELa
	 */
	private void createSuppDevByInvGrp(Vector<EANEntity> curMdlVct, Vector<EANEntity> fromMdlVct, FMChgSet chgSet,
			Hashtable<String, Hashtable<String, FMChgSet>> sdByInvGrpTbl, boolean useCurrentModela) throws Exception
	{
		if (curMdlVct.size()==0 && fromMdlVct.size()==0){
			trace(D.EBUG_ERR,false,"createSuppDevByInvGrp() WARNING Cannot find InvGrp for MODELc "+chgSet.getKey()+
					" because no MODELa found at either time");
		}else{
			// can't just get fromtime MODELa from the from EntityGroup because there is no
			// guarantee that it was linked to MODELc at that time.. it may exist..but tied to another
			// look at all current models and see if it was linked at fromtime.. it may or maynot have
			// same MACHTYPEATR (invgrp) value!!
			Vector<EntitySet> mdlMatchVct = new Vector<EntitySet>();
			Iterator<EANEntity> fromItr = fromMdlVct.iterator();
			while (fromItr.hasNext()) {
				EntityItem fromMdl = (EntityItem)fromItr.next();
				Iterator<EANEntity> curItr = curMdlVct.iterator();
				while (curItr.hasNext()) {
					EntityItem mdlItem = (EntityItem)curItr.next();
					if (fromMdl.getKey().equals(mdlItem.getKey())){
						mdlMatchVct.addElement(new EntitySet(fromMdl, mdlItem));
						fromItr.remove(); // remove model from the from vector
						curItr.remove(); // remove model from the current vector
					}
				}
			}
			trace(D.EBUG_INFO,false,"createSuppDevByInvGrp() Find InvGrp for MODELc "+chgSet.getKey()+
					" "+curMdlVct.size()+
					" cur MODELa, "+fromMdlVct.size()+" from MODELa and "+mdlMatchVct.size()+" matching MODELa");

			// mdlMatchVct has all MODELa that existed at both fromtime and curtime
			// get invgrp from each
			for(int m=0; m<mdlMatchVct.size(); m++) {
				EntitySet mdlSet = (EntitySet)mdlMatchVct.elementAt(m);
				EntityItem mdlItem = mdlSet.getCurItem();
				EntityItem frommdlItem = mdlSet.getFromItem();
				// this is MODELa, get its inventory group, if valid create a FMChgSet using it for this
				// FMChgSet's MODELc
				if (useCurrentModela) {
					String invGrpflag = getMODELInvGrp(mdlItem);
					trace(D.EBUG_INFO,false,"createSuppDevByInvGrp() MODELc "+chgSet.getKey()+" current invgrp for MODELa["+m+"] "+
							mdlItem.getKey()+" is "+invGrpflag);
					if (invGrpflag!=null) { // matched at curtime
						createSDForInvGrp(invGrpflag, sdByInvGrpTbl, chgSet, mdlItem, frommdlItem);
					}// end valid cur invgrp found
					else{
						logDataError("Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
						trace(D.EBUG_ERR,false,"createSuppDevByInvGrp() ERROR Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
					}
				}
				else {
					String frominvGrpflag = getMODELInvGrp(frommdlItem);
					trace(D.EBUG_INFO,false,"createSuppDevByInvGrp() MODELc "+chgSet.getKey()+" from invgrp for MODELa["+m+"] "+
							frommdlItem.getKey()+" is "+frominvGrpflag);
					// valid invgrp not found at curtime, so use invgrp from fromtime
					// this may be wrong because invgrp is separate from the entities that are checked
					if (frominvGrpflag!=null){
						createSDForInvGrp(frominvGrpflag, sdByInvGrpTbl, chgSet, mdlItem,frommdlItem);
					}else{
						logDataError("Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+frommdlItem.getKey()+" could not be determined.");
						trace(D.EBUG_ERR,false,"createSuppDevByInvGrp() ERROR Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+frommdlItem.getKey()+" could not be determined.");
					}
				}

				mdlSet.dereference();
			} // end of matched modela for curtime and fromtime
			mdlMatchVct.clear();

			// anything left in this vector only has links to current models
			if (useCurrentModela) {
				for(int m=0; m<curMdlVct.size(); m++) {
					EntityItem mdlItem = (EntityItem)curMdlVct.elementAt(m);
					// this is MODELa, get its inventory group, if valid create a FMChgSet using it for this
					// FMChgSet's MODELc
					String invGrpflag = getMODELInvGrp(mdlItem);
					trace(D.EBUG_INFO,false,"createSuppDevByInvGrp()2 MODELc "+chgSet.getKey()+" current invgrp for MODELa["+m+"] "+
							mdlItem.getKey()+" is "+invGrpflag);
					if (invGrpflag!=null){
						createSDForInvGrp(invGrpflag, sdByInvGrpTbl, chgSet, mdlItem,null);
					} // end valid invgrp
					else{
						logDataError("Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
						trace(D.EBUG_ERR,false,"createSuppDevByInvGrp()2 ERROR Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
					}
				} // end of new curtime modela
			}else {
				// anything left in this vector only has links to from models
				// these should be DELETEs of suppdevice from that invgrp.. but it will only show change
				// in ROOT MODELc now!!
				for(int m=0; m<fromMdlVct.size(); m++) {
					EntityItem mdlItem = (EntityItem)fromMdlVct.elementAt(m);
					// this is MODELa, get its inventory group, if valid create a FMChgSet using it for this
					// FMChgSet's MODELc
					String invGrpflag = getMODELInvGrp(mdlItem);
					trace(D.EBUG_INFO,false,"createSuppDevByInvGrp()3 MODELc "+chgSet.getKey()+" from invgrp for MODELa["+m+"] "+
							mdlItem.getKey()+" is "+invGrpflag);
					if (invGrpflag!=null){
						createSDForInvGrp(invGrpflag, sdByInvGrpTbl, chgSet, null,mdlItem);
					} // end valid invgrp
					else{
						logDataError("Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
						trace(D.EBUG_ERR,false,"createSuppDevByInvGrp()3 ERROR Could not create entry for Supported Device: "+chgSet.getKey()+
								" because Inventory Group for "+mdlItem.getKey()+" could not be determined.");
					}
				}  // end fromtime only modela
			}
		}
	}

	/**************************************************************************************
	 * Create a chgset using this inventory group
	 * these FMChgSets could be linked to more than one MODELa and the MODELa is
	 * needed for output info in the MTM section.  So now must find the MODELas
	 * and create a unique FMChgSet for each one, with its inventorygroup
	 *@param invGrpflag String invgrp flag
	 *@param sdByInvGrpTbl Hashtable to be filled in
	 *@param chgSet FMChgSet SD item or SDMTM item that had valid changes in this interval
	 *@param mdlItem EntityItem MODELa entity item at curtime
	 *@param frommdlItem EntityItem MODELa entity item at fromtime
	 */
	private void createSDForInvGrp(String invGrpflag, Hashtable<String, Hashtable<String, FMChgSet>> sdByInvGrpTbl, FMChgSet chgSet,
			EntityItem mdlItem, EntityItem frommdlItem)
					throws Exception
	{
		//CR0503064033 skip 7, 30 day rpts for some invgrps
		if (//numDays>1 &&  no suppdev for 1 day interval now
				invGrpToSkipVct.contains(invGrpflag)) {
			trace(D.EBUG_SPEW,false,"createSDForInvGrp() Skipping Inventory Group ["+invGrpflag+"] for "+
					chgSet.getKey());
		}
		else{
			Hashtable<String, FMChgSet> invtbl = (Hashtable<String, FMChgSet>)sdByInvGrpTbl.get(invGrpflag);
			String key=null;
			if (invtbl==null) {
				invtbl = new Hashtable<String, FMChgSet>();
				sdByInvGrpTbl.put(invGrpflag,invtbl);
			}

			if (chgSet instanceof FMChgSuppDevSet) {
				key = invGrpflag+":"+chgSet.getKey();
			}else if (chgSet instanceof FMChgSuppDevMTMSet) {  // FMChgSDMTMDelSet matches this too here
				if (mdlItem!=null) {
					key = invGrpflag+":"+chgSet.getKey()+":"+mdlItem.getKey();
				}else {
					key = invGrpflag+":"+chgSet.getKey()+":"+frommdlItem.getKey();
				}
			}

			// make sure this invgrp doesn't have duplicates of this suppdevice
			// could happen if multiple MODELa have same invgrp
			// but MODELa is part of MTM output so that must be part of key for FMChgSuppDevMTMSet
			if (invtbl.get(key)==null) {
				FMChgSet sdSet = null;
				if (chgSet instanceof FMChgSuppDevSet) {
					sdSet = new FMChgSuppDevSet((FMChgSuppDevSet)chgSet); // no calculateoutput needed, done already
					trace(D.EBUG_INFO,false,"createSDForInvGrp() created MODELc chgset for "+sdSet.getKey()+
							" for invgrp:"+invGrpflag);
				}else if (chgSet instanceof FMChgSuppDevMTMSet) {
					if (chgSet instanceof FMChgSDMTMDelSet) {
						sdSet = new FMChgSDMTMDelSet((FMChgSDMTMDelSet)chgSet,mdlItem,frommdlItem);
					}
					else{
						sdSet = new FMChgSuppDevMTMSet((FMChgSuppDevMTMSet)chgSet,mdlItem,frommdlItem);
					}
					trace(D.EBUG_INFO,false,"createSDForInvGrp() created MDLCGOSMDL chgset for "+sdSet.getKey()+
							" for invgrp:"+invGrpflag);
				}

				invtbl.put(key,sdSet);
			}else{ // already contains it, so release it
				// this can only happen for FMChgSuppDevSet not MTM set because MTM uses
				// more then invgrp from MODELa
				trace(D.EBUG_INFO,false,"createSDForInvGrp() MODELc chgset for invgrp:chgset:mdl "+key+
						" was a duplicate");
			}
		}
	}

	/**************************************************************************************
	 * Get all MODELa parents for this MODELc child, may have multiple MODELa parents
	 * if the change is only on the MDLCGOSMDL relator, then you only want to find the MODELa parents of
	 * the MDLCGOSMDL, not all MODELa parents for MODELc
	 *
	 * @param theItem EntityItem MODELc or MDLCGOSMDL
	 * @return Vector of MODELa EntityItem
	 */
	private Vector<EANEntity> getMODELa(EntityItem theItem)
	{
		//-- Entity:MODELc<----Relator:MDLCGOSMDL<---Entity:MODELCGOS use MACHTYPEATR on MODELc to determine the invgrp for FM mapping
		//-- Entity:MODELCGOS<----Relator:MDLCGMDLCGOS<---Entity:MODELCG
		//-- Entity:MODELCG---->Relator:MDLCGMDL--->Entity:MODELa  use MACHTYPEATR on MODELa to determine the invgrp for this suppdevice
		Vector<EANEntity> modelVct = null;

		if (theItem!=null) {
			Iterator<EANEntity> itr=null;
			Hashtable<String, EntityItem> mdlTbl = new Hashtable<String, EntityItem>();  // used to remove duplicates
			Vector<EANEntity> modelcgosVct;
			Vector<EANEntity> modelcgVct;
			if (theItem.getEntityType().equals("MODEL")){
				modelcgosVct = FMChgLogGen.getAllLinkedEntities(theItem, "MDLCGOSMDL", "MODELCGOS");
				modelcgVct = FMChgLogGen.getAllLinkedEntities(modelcgosVct, "MDLCGMDLCGOS", "MODELCG");
			}
			else {  // must be MDLCGOSMDL, only want the MODELa's thru this relator
				modelcgosVct = new Vector<EANEntity>(1);
				for (int ui=0; ui<theItem.getUpLinkCount(); ui++) {
					EntityItem cgosItem = (EntityItem)theItem.getUpLink(ui);
					if (cgosItem.getEntityType().equals("MODELCGOS")) {
						modelcgosVct.addElement(cgosItem);
					}
				}
				modelcgVct = FMChgLogGen.getAllLinkedEntities(modelcgosVct, "MDLCGMDLCGOS", "MODELCG");
			}
			modelVct = FMChgLogGen.getAllLinkedEntities(modelcgVct, "MDLCGMDL", "MODEL");
			// make sure there aren't duplicates in modelVct, could happen if MODELc was linked to MODELa
			// thru different relators (lattice)
			itr = modelVct.iterator();
			while (itr.hasNext()) {
				EntityItem mdl = (EntityItem)itr.next();
				Object obj = mdlTbl.get(mdl.getKey());
				if (obj==null){
					mdlTbl.put(mdl.getKey(),mdl);
				}else{
					// already found this one
					itr.remove(); // remove model from the vector
					trace(D.EBUG_INFO,false,"getMODELa() duplicate MODELa "+mdl.getKey()+" found for "+theItem.getKey()+
							" removing it");
				}
			}

			// release memory
			mdlTbl.clear();
			modelcgosVct.clear();
			modelcgVct.clear();
		}else {
			modelVct = new Vector<EANEntity>(1); // make sure there is something valid to return
		}

		return modelVct;
	}
	/**************************************************************************************
	 * SuppDevice must get Inventory Group from MODELa.MACHTYPEATR desc match.  It is possible
	 * that the MACHTYPEATR has a value that isn't in the set of MACHTYPE entities tied to the
	 * WG.  This is just to make sure.  This method is also used by FMChgSet's to determine
	 * MODELc invgrp.
	 * @param modelItem EntityItem with model to find inventorygroup for
	 * @return boolean String with inventorygroup flag value
	 */
	String getMODELInvGrp(EntityItem modelItem) throws Exception
	{
		String invGrpflag = null;
		String mtDesc = FMChgLogGen.getAttributeValue(modelItem, "MACHTYPEATR", "", null);
		if (mtDesc!=null) {
			invGrpflag = getMTInvGrp(mtDesc);
		}

		if (invGrpflag ==null) {
			logDataError("Inventory Group for "+modelItem.getKey()+" with MACHTYPEATR ["+
					mtDesc+"] could not be found.  This MACHTYPE was not linked to a WG on "+
					modelItem.getProfile().getValOn());
			trace(D.EBUG_ERR,false,"getMODELInvGrp() ERROR could not find INVENTORYGROUP for MACHTYPEATR["+
					mtDesc+"] for MODEL "+modelItem.getKey()+" on "+modelItem.getProfile().getValOn()+
					" This MACHTYPE was not linked to a WG");
		}
		return invGrpflag;
	}

	/**************************************************************************************
	 * B.    Supported Device Matrix Changes
	 *
	 * The columns are:
	 *
	 * Heading               Description
	 * Date/Time of Change   ValFrom of the Relator = MDLCGOSMDL
	 * Change Type           Add = New in this time period,Change = More than one record in this period,Delete = Currently Deactivated
	 * Last Editor           From the Relator( First 10 characters)
	 * MTM                   MDLCGMDL->MODEL.MACHTYPEATR &-& MODEL.MODELATR
	 * SptDev MTM            MDLCGOSMDL->MODEL.MACHTYPEATR &-&  MODEL.MODELATR
	 * Announce Date         MDLCGOSMDL.ANNDATE
	 * FM                    Derived  see below
	 * Name                  MDLCGOSMDL->MODEL.INTERNALNAME
	 *
	 * FM is derived as follows:
	 *
	 * Use entity MDLCGOSMDL->MODEL.to find a matching MAPSUPPDEVICE. The matching is based on
	 * INVENTORYGROUP from this MODEL’s parent MACHTYPE and COMPATDVCCAT from the MODEL itself.
	 * Given a matching entity MAPSUPPDEVICE using INVENTORYGROUP and FMGROUP, then this gives FMGROUPCODE.
	 * This yields a one character code.
	 *
	 * @param chgSetTbl Hashtable with FMChgSuppDevMTMSet objects grouped by invgrp
	 * @param chgRpt FMChgRpt for output
	 */
	private void getSuppDevMTMChgs(Hashtable<String, FMChgSet> chgSetTbl, FMChgRpt chgRpt)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		String sectionTitle = FMChgSuppDevMTMSet.getSectionTitle();
		String sectionInfo = FMChgSuppDevMTMSet.getSectionInfo();
		String noneFndText = FMChgSuppDevMTMSet.getNoneFndText();
		Vector<FMChgSet> chgSetVct = null;
		// output this sections info
		if (chgSetTbl==null)    {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevMTMChgs() No changes found");
			chgSetVct = new Vector<FMChgSet>(1); // needed for report output
		}else{
			chgSetVct = new Vector<FMChgSet>(chgSetTbl.values());
			Collections.sort(chgSetVct);
			chgSetTbl.clear();
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgSuppDevMTMSet.getColumnHeader(), noneFndText);
		// release memory
		for (int p=0; p<chgSetVct.size(); p++) {
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
	 * @param chgSetTbl Hashtable with FMChgSuppDevSet objects grouped by invgrp
	 * @param chgRpt FMChgRpt for output
	 */
	private void getSuppDevChgs(Hashtable<String, FMChgSet> chgSetTbl, FMChgRpt chgRpt)
			throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, Exception
	{
		String sectionTitle = FMChgSuppDevSet.getSectionTitle();
		String sectionInfo = FMChgSuppDevSet.getSectionInfo();
		String noneFndText = FMChgSuppDevSet.getNoneFndText();
		Vector<FMChgSet> chgSetVct = null;
		// output this sections info
		if (chgSetTbl==null)  {
			trace(D.EBUG_INFO,false,FMChgLog.NEWLINE+"getSuppDevChgs() No changes found");
			chgSetVct = new Vector<FMChgSet>(1);  // needed for report output
		}else{
			chgSetVct = new Vector<FMChgSet>(chgSetTbl.values());
			Collections.sort(chgSetVct);
			chgSetTbl.clear();
		}

		chgRpt.addSection(chgSetVct, sectionTitle, sectionInfo, FMChgSuppDevSet.getColumnHeader(), noneFndText);
		// release memory
		for (int p=0; p<chgSetVct.size(); p++)  {
			FMChgSet theSet = (FMChgSet)chgSetVct.elementAt(p);
			theSet.dereference();
		}
		chgSetVct.clear();
	}

	//===============================================================================================
	//===============================================================================================
	/********************************************************************************
	 * Split relId into type and id
	 *
	 * @param relId     String of TYPEID
	 * @returns String of ID
	 */
	private static String getId(String relId){
		StringCharacterIterator sci = new StringCharacterIterator(relId);
		int end = 0;
		for (char c = sci.first();
				c != CharacterIterator.DONE && !Character.isDigit(c);
				c = sci.next()) {
		}
		end = sci.getIndex();
		return relId.substring(end);
	}

	/********************************************************************************
	 * Find entities of the destination type linked to the EntityItems in the source
	 * vector through the specified link type.  Both uplinks and downlinks are checked
	 * though only one will contain a match.
	 * All objects in the source Vector must be EntityItems of the same entity type
	 *
	 * @param srcVct     Vector of EntityItems
	 * @param linkType   String Association or Relator type linking the entities
	 * @param destType   String EntityType to match
	 * @returns Vector of EntityItems
	 */
	public static Vector<EANEntity> getAllLinkedEntities(Vector<EANEntity> srcVct, String linkType, String destType)
	{
		// find entities thru 'linkType' relators
		Vector<EANEntity> destVct = new Vector<EANEntity>(1);

		Iterator<EANEntity> srcItr = srcVct.iterator();
		while (srcItr.hasNext())   {
			EntityItem entityItem = (EntityItem)srcItr.next();
			getLinkedEntities(entityItem, linkType, destType, destVct);
		}

		return destVct;
	}

	/********************************************************************************
	 * Find entities of the destination type linked to the EntityItems in the source
	 * vector through the specified link type.  Both uplinks and downlinks are checked
	 * though only one will contain a match.
	 * All objects in the source Vector must be EntityItems of the same entity type
	 *
	 * @param entityItem EntityItem
	 * @param linkType   String Association or Relator type linking the entities
	 * @param destType   String EntityType to match
	 * @returns Vector of EntityItems
	 */
	public static Vector<EANEntity> getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
	{
		// find entities thru 'linkType' relators
		Vector<EANEntity> destVct = new Vector<EANEntity>(1);
		getLinkedEntities(entityItem, linkType, destType, destVct);
		return destVct;
	}

	/********************************************************************************
	 * Find entities of the destination type linked to the specified EntityItem through
	 * the specified link type.  Both uplinks and downlinks are checked though only
	 * one will contain a match.
	 *
	 * @param entityItem EntityItem
	 * @param linkType   String Association or Relator type linking the entities
	 * @param destType   String EntityType to match
	 * @param destVct    Vector EntityItems found are returned in this vector
	 */
	private static void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
			Vector<EANEntity> destVct)
	{
		if (entityItem!=null) {
			// see if this relator is used as an uplink
			for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)   {
				EANEntity entityLink = entityItem.getUpLink(ui);
				if (entityLink.getEntityType().equals(linkType))  {
					// check for destination entity as an uplink
					for (int i=0; i<entityLink.getUpLinkCount(); i++)    {
						EANEntity entity = entityLink.getUpLink(i);
						if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
							destVct.addElement(entity); }
					}
				}
			}

			// see if this relator is used as a downlink
			for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)    {
				EANEntity entityLink = entityItem.getDownLink(ui);
				if (entityLink.getEntityType().equals(linkType))    {
					// check for destination entity as a downlink
					for (int i=0; i<entityLink.getDownLinkCount(); i++)   {
						EANEntity entity = entityLink.getDownLink(i);
						if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
							destVct.addElement(entity); }
					}
				}
			}
		}
	}

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
		String value = defValue;
		if (item!=null)   {
			EANAttribute attr = null;
			EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(attCode);
			if (metaAttr==null) {
				throw new Exception("Attribute "+attCode+" NOT found in "+
						item.getEntityType()+" META data for valon: "+item.getProfile().getValOn()+".");
			}

			attr = item.getAttribute(attCode);
			if (attr != null) {
				if (attr instanceof EANFlagAttribute)     {
					value = attr.toString(); // this is faster
					if (metaAttr.getAttributeType().equals("F"))     {
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
				else if (attr instanceof EANTextAttribute)    {
					value = attr.get().toString();
				}
				else if (attr instanceof EANBlobAttribute)    {
					value= "Blob Attribute type "+metaAttr.getAttributeType()+" for "+attCode+" NOT supported";
				}
			}
		}
		return value;
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
		String value=null;
		// Multi-flag values will be separated by |
		EANAttribute attr = entityItem.getAttribute(attrCode);
		if (attr != null) {
			if (attr instanceof EANFlagAttribute)  {
				StringBuffer sb = new StringBuffer();

				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) attr.get();
				for (int i = 0; i < mfArray.length; i++)  {
					// get selection
					if (mfArray[i].isSelected()) {
						if (sb.length()>0) {
							sb.append(DELIMITER); }
						sb.append(mfArray[i].getFlagCode());
					}
				}
				if (sb.length()>0) {  // somehow flag attribute exists but isn't set!
					value = sb.toString();
				}
			}
		}
		return value;
	}

	static String outputList(EntityList list) // debug
	{
		StringBuffer sb = new StringBuffer();
		EntityGroup peg = null;
		if (list==null) {
			sb.append("Null list");
		}
		else {
			peg =list.getParentEntityGroup();
			if (peg!=null)   {
				sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" Parent entity items. ");
				if (peg.getEntityItemCount()>0)   {
					StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
					tmpsb.append("IDs(");
					for (int e=0; e<peg.getEntityItemCount(); e++)  {
						tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
						if (tmpsb.length()>LEN256)  {
							sb.append(tmpsb.toString()+FMChgLog.NEWLINE);
							tmpsb.setLength(0);
						}
					}
					tmpsb.append(")");
					sb.append(tmpsb.toString());
				}
				sb.append(FMChgLog.NEWLINE);
			}

			for (int i=0; i<list.getEntityGroupCount(); i++)    {
				EntityGroup eg =list.getEntityGroup(i);
				sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
				if (eg.getEntityItemCount()>0)   {
					StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
					tmpsb.append("IDs(");
					for (int e=0; e<eg.getEntityItemCount(); e++)    {
						tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
						if (tmpsb.length()>LEN256)   {
							sb.append(tmpsb.toString()+FMChgLog.NEWLINE);
							tmpsb.setLength(0);
						}
					}
					tmpsb.append(")");
					sb.append(tmpsb.toString());
				}
				sb.append(FMChgLog.NEWLINE);
			}
		}
		return sb.toString();
	}

	/**
	 * write object to file
	 * @param file
	 * @param o
	 * @throws IOException 
	 */
	public void writeChgSet(String file, Vector<FMChgSet> o) throws IOException {
		if (file != null) {
			FileOutputStream fout = null;
			ObjectOutputStream outStream = null;
			try {
				fout = new FileOutputStream(file);
				outStream = new ObjectOutputStream(fout);
				outStream.writeObject(o);
				outStream.flush(); 
				outStream.reset(); 
			} finally {
				if (outStream != null) {
					try {
						outStream.close();
					} catch (IOException ioe) {}
					outStream=null;
				}
				if (fout != null) {
					try{
						fout.close(); 
					} catch (IOException ioe) {}
					fout = null;
				}
			}
		}
	}
	private static boolean fileExists(String file){
		File f = new File(file);
		return f.exists();
	}
	/********
	 * Load an object from file, throw exceptions
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public Vector<FMChgSet> readChgSet(String file) throws IOException, ClassNotFoundException {
		FileInputStream fis = null;
		ObjectInputStream inStream = null;
		Vector<FMChgSet> o = null;
		if (fileExists(file)) {
			try {
				fis = new FileInputStream(file);
				inStream = new ObjectInputStream(fis);
				o = (Vector<FMChgSet>)inStream.readObject();
			} 			
			finally {
				if (inStream != null) {
					try{
						inStream.close(); 
					} catch (IOException ioe) {}
					inStream = null;
				}
				if (fis != null) {
					try{
						fis.close(); 
					} catch (IOException ioe) {}
					fis = null;
				}
			}
		}else{
			o = new Vector<FMChgSet>();
		}
		return o;
	}

	/*************************************************************************************
	 * Convenience classes
	 **************************************************************************************/
	// a pair of items with same entity id from each time
	private static class EntitySet
	{
		private EntityItem fromItem;
		private EntityItem curItem;
		EntitySet(EntityItem fitem, EntityItem citem)  {
			fromItem = fitem;
			curItem = citem;
		}
		EntityItem getCurItem() { return curItem;}
		EntityItem getFromItem() { return fromItem;}
		public String toString()  {
			return " From: "+fromItem.getKey()+" Current: "+curItem.getKey();
		}

		void dereference()   {
			fromItem = null;
			curItem = null;
		}
	}
}
