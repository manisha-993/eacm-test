package COM.ibm.eannounce.darule;

//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

/*
 if Ready, 
 search for DAENTITYTYPE limited by PDHDOMAIN specified to get offerings needing catdata
 check for wddate and status -  only process those that meet the criteria
 pull extract of subsets to get CATDATA
 
 call DARuleGroup dag = DARuleEngineMgr.getDARuleGroup(..)
 pass in subsets of entityitems at a time
 
 boolean chgsmade[] = dag.idlCatData(...)
 if CATDATA was updated, queue ADSABSTATUS based on entity lifecycle and status values
 
 for performance reasons, do not update the pdh for each offering individually, update them in sets
 
 when done with all offerings, 
 get all DARULE items from the dag and update any that are not  'Obsolete' to 'Production' - this rule may be part of a set that had several 'new' ones
 
 deref extracts
 deref dag
 if Retire,
 get set of darules for this type and attrcode can use
 EntityItem eia[] = DARuleEngine.searchForDARules(db, prof,entityType,attrCode,msgSb);
 
 if any have production, set darules and fail abr
 
 find all CATDATA for that entitytype and attribute code - (do not look at the offering or its status or wddate)
 deactivate all using a DeleteAction
 call DARuleEngineMgr.clearEntityType(Database db, Profile prof, String entitytype, StringBuffer msgSb) so any cached rules will be cleared
 
 change all darule.lifecycle to obsolete
 if any other value,
 fail the ABR - it should only be running when Ready or Retire
 */
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.io.*;

/**
 * VIII.	Execution: IDL Catalog Derivation Rule

	This ABR will be invoked for an instance of an Attribute Derivation Rule (DARULE) whenever an authorized user changes the Life Cycle (DALIFECYCLE) to â€œReadyâ€� (???).
	
	If DARULE.DAENTITYTYPE = â€œFEATUREâ€�, then
	For all FEATURE where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= Global Withdrawal Date Effective (WITHDRAWDATEEFF_T)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)
	
	If DARULE.DAENTITYTYPE = â€œLSEOâ€�, then
	For all LSEO where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= LSEO Unpublish Date - Target (LSEOUNPUBDATEMTRGT)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section) 
	
	Reminder: LSEO  is not supported in BH 1.0
	
	If DARULE.DAENTITYTYPE = â€œLSEOBUNDLEâ€�, then
	For all LSEOBUNDLE where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= Bundle Unpublish Date - Target (BUNDLUNPUBDATEMTRGT)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section) 
	
	If DARULE.DAENTITYTYPE = â€œMODELâ€�, then
	For all MODEL where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= Withdrawal Effective Date (WTHDRWEFFCTVDATE)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)
	 
	If DARULE.DAENTITYTYPE = â€œSVCMODâ€�, then
	For all SVCMOD where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= Withdrawal Effective Date (WTHDRWEFFCTVDATE)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)
	
	
	
	
	
	If DARULE.DAENTITYTYPE = â€œSWFEATUREâ€�, then
	For all WWSEO where the is at least one child LSEO where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= Global Withdrawal Date Effective (WITHDRAWDATEEFF_T)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)
	
	Reminder: SWFEATURE  is not supported in BH 1.0
	
	If DARULE.DAENTITYTYPE = â€œWWSEOâ€�, then
	For all WWSEO where the is at least one child LSEO where
	â€¢	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	â€¢	NOW() <= the parent MODELâ€™s Withdrawal Effective Date (WTHDRWEFFCTVDATE)
	then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)

 * @author guobin
 *
 */
public class ATTRDERIVEABRSTATUS extends PokBaseABR {

	private static int MW_VENTITY_LIMIT;
	
	private static int RUN_LIMIT;
	
	private static final int MAXFILE_SIZE=5000000;
	
	private StringBuffer rptSb = new StringBuffer();

	private StringBuffer xmlgenSb = new StringBuffer();

	private StringBuffer debugSb = new StringBuffer();

	private String deleteActionName = null;

	private static final char[] FOOL_JTEST = { '\n' };

	static final String NEWLINE = new String(FOOL_JTEST);

	private PrintWriter dbgPw = null;
	
	private String dbgfn = null;

	private int dbgLen = 0;;

	private Vector vctReturnsEntityKeys = new Vector();

	protected static final Hashtable ITEM_VE_TBL;

	protected static final Hashtable WTHDRWEFFCTVDATE_Attr_TBL;
    
	protected static final String ATTRDERIVEABRSTATUS_Passed = "0030";
	
	protected static final String ATTRDERIVEABRSTATUS_Failed = "0040";
	
	protected static final String ATTRDERIVE_ATTR = "ATTRDERIVEABRSTATUS";

	protected static final String STATUS_FINAL = "0020";

	protected static final String STATUS_R4REVIEW = "0040";

	protected static final String DALIFECYCLE_Ready = "20";

	protected static final String DALIFECYCLE_Retire = "60";

	protected static final String DALIFECYCLE_Production = "30";

	protected static final String DALIFECYCLE_Change = "50";

	protected static final String DALIFECYCLE_Obsolete = "40";

	private static final String ADS_XMLEED_ATTR = "ADSABRSTATUS"; // attr to																	// queue

	private static final String DARULE_LIFECYCLE__ATTR = "DALIFECYCLE";

	protected static final String LIFECYCLE_Develop = "LF02";// LIFECYCLE	=	"Develop" (LF02)

	protected static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan

	protected static final String LIFECYCLE_Launch = "LF03";// LIFECYCLE	=	LF01	Plan
	
	private EntityItem daRules[] = null;
	// Plan
	private ResourceBundle rsBundle = null;

	private String actionTaken = "";
    
	private String navName = "";
	
	private String rootDesc = "";

	private Object[] args = new String[10];

	static {
		ITEM_VE_TBL = new Hashtable();
		ITEM_VE_TBL.put("WWSEO", "DAVEWWSEO");
		ITEM_VE_TBL.put("FEATURE", "DAVEFEATURE");
		ITEM_VE_TBL.put("MODEL", "DAVEMODEL");
		ITEM_VE_TBL.put("SVCMOD", "DAVESVCMOD");
		ITEM_VE_TBL.put("LSEOBUNDLE", "DAVELSEOBUNDLE");
		ITEM_VE_TBL.put("LSEO", "DAVELSEO");

	}
	static {
		WTHDRWEFFCTVDATE_Attr_TBL = new Hashtable();
		WTHDRWEFFCTVDATE_Attr_TBL.put("FEATURE", "WITHDRAWDATEEFF_T");
		WTHDRWEFFCTVDATE_Attr_TBL.put("LSEO", "LSEOUNPUBDATEMTRGT");
		WTHDRWEFFCTVDATE_Attr_TBL.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
		WTHDRWEFFCTVDATE_Attr_TBL.put("MODEL", "WTHDRWEFFCTVDATE");
		WTHDRWEFFCTVDATE_Attr_TBL.put("SVCMOD", "WTHDRWEFFCTVDATE");
		WTHDRWEFFCTVDATE_Attr_TBL.put("SWFEATURE", "WITHDRAWDATEEFF_T");
		WTHDRWEFFCTVDATE_Attr_TBL.put("WWSEO", "WTHDRWEFFCTVDATE");
	}

	private static final Hashtable CATREL_TBL;
	static {
		CATREL_TBL = new Hashtable();
		CATREL_TBL.put("FEATURE", "FEATURECATDATA");
		CATREL_TBL.put("SWFEATURE", "SWFEATURECATDATA");
		CATREL_TBL.put("MODEL", "MODELCATDATA");
		CATREL_TBL.put("WWSEO", "WWSEOCATDATA");
		CATREL_TBL.put("SVCMOD", "SVCMODCATDATA");
		CATREL_TBL.put("LSEO", "LSEOCATDATA");
		CATREL_TBL.put("LSEOBUNDLE", "LSEOBUNDLECATDATA");
	}

	private static final Hashtable CATACTION_TBL;
	static {
		// delete action names
		CATACTION_TBL = new Hashtable();
		CATACTION_TBL.put("FEATURE", "DELCATDATA");
		CATACTION_TBL.put("SWFEATURE", "DELCATDATA");
		CATACTION_TBL.put("MODEL", "DELCATDATA");
		CATACTION_TBL.put("WWSEO", "DELCATDATA");
		CATACTION_TBL.put("SVCMOD", "DELCATDATA");
		CATACTION_TBL.put("LSEO", "DELCATDATA");
		CATACTION_TBL.put("LSEOBUNDLE", "DELCATDATA");
	}
	private void setupPrintWriter(){
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0,extid+1)+"dbg";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
	}
	/**
	 * Execute ABR.
	 * 
	 * @throws
	 * 
	 */
	public void execute_run() {
		// must split because too many arguments for messageformat, max of 10..
		// this was 11
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
			+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
			+ EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;

		MessageFormat msgf;
		String msg = "";
		String DALifeCycle = "";
		EntityItem DARULEEntity = null;
		println(EACustom.getDocTypeHtml()); // Output the doctype and html
	    EntityList mm_elist = null;
		try {
			start_ABRBuild(false); // dont pull VE yet
			getPropertiesLimitValues();
			// get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
		    //get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			setupPrintWriter();
            mm_elist = m_db.getEntityList(m_prof,
                    new ExtractActionItem(null, m_db, m_prof,"dummy"),
                    new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
		    DARULEEntity = mm_elist.getParentEntityGroup().getEntityItem(0);
		    rootDesc = mm_elist.getParentEntityGroup().getLongDescription();
            //NAME is navigate attributes
			navName = getNavigationName(DARULEEntity);
		    setReturnCode(PASS);
			String rootType = PokUtils.getAttributeValue(DARULEEntity, "DAENTITYTYPE", "", null, false);
			String attrCode = getAttributeFlagEnabledValue(DARULEEntity, "DAATTRIBUTECODE");
		    DALifeCycle = getAttributeFlagEnabledValue(DARULEEntity, DARULE_LIFECYCLE__ATTR);
			daRules = DARuleEngine.searchForDARules(m_db, m_prof, rootType, attrCode, rptSb);
			String VeName = (String) ITEM_VE_TBL.get(rootType);
			addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + DARULEEntity.getKey() + " DALifeCycle:"
				+ DALifeCycle + " DAattrCode:" + attrCode + " extract: " + VeName + " using DTS: " + m_prof.getValOn() + NEWLINE
				+ PokUtils.outputList(mm_elist));
			
			msg = DARULEEntity.getKey();
			if (VeName != null) {
				try {
       				if (DALIFECYCLE_Ready.equals(DALifeCycle)) {
       					addGenMsg("DALIFECYCLE_READY", msg);
       					//actionTaken = rsBundle.getString("DALIFECYCLE_READY");
						long curtime = System.currentTimeMillis();
						// find all ids for this root type and filters
						Vector temVct = getValidEntityIds(rootType, DARULEEntity);
						if (temVct.size() > 0) {
							String queuedValue = getQueuedValue(ADS_XMLEED_ATTR);
							boolean requestQueue = queuedValue.trim().length()>0;
							addOutput((requestQueue?"ADSABRSTATUS set to :" + queuedValue:"An empty value found ATTRDERIVEABRSTATUS_ADSABRSTATUS_queuedValue= in properties file,  the attribute (ADSABRSTATUS) not be updated"));
							DARuleGroup dag = DARuleEngineMgr.getDARuleGroup(m_db, m_prof, rootType, attrCode, rptSb);
							// MW_VENTITY_LIMIT is used to limit the number of
							// items to use in one extract.
							addDebug("VEENTITY_LIMIT :" + MW_VENTITY_LIMIT + " ID Count : " + temVct.size() + " EntityType: "  + rootType + " EntityID: " + temVct);
							Vector idsVct = new Vector();
							if (RUN_LIMIT != 0 ){	
								for (int j=0; j < temVct.size(); j++){
									if (j==RUN_LIMIT)
										break;
									idsVct.add(temVct.elementAt(j));
								}
								addDebug("Run the first "  + RUN_LIMIT + " records  EntityID: " + idsVct);
							}else {
								idsVct = temVct;
							}	
							if (idsVct.size() > MW_VENTITY_LIMIT) {
								int numGrps = 0;
								if (idsVct.size() % MW_VENTITY_LIMIT != 0){
									numGrps = idsVct.size() / MW_VENTITY_LIMIT + 1; 
								}else {
								    numGrps = idsVct.size() / MW_VENTITY_LIMIT;
								}
								int numUsed = 0;
								Vector tmpVec = new Vector();
								Integer id = null;
							    for (int i = 0; i < numGrps; i++) {
									for (int x = 0; x < MW_VENTITY_LIMIT; x++) {
										if (numUsed == idsVct.size()) {
											break;
										}
										id = (Integer) idsVct.elementAt(numUsed++);
										EntityItem tmpEntity = new EntityItem(null, m_prof, rootType, id.intValue());
										tmpVec.add(tmpEntity);
									}
									EntityItem tmpArray[] = new EntityItem[tmpVec.size()];
									tmpVec.copyInto(tmpArray);
									EntityList new_elist = m_db.getEntityList(m_prof,
					                    new ExtractActionItem(null, m_db, m_prof, VeName),tmpArray);
									addDebug("Time to instance EntityList LIMIT: " + MW_VENTITY_LIMIT  + " EntityType: "+ rootType + ": "
										+ Stopwatch.format(System.currentTimeMillis() - curtime) + NEWLINE + PokUtils.outputList(new_elist));
									curtime = System.currentTimeMillis();
									
									EntityGroup mdlGrp = new_elist.getParentEntityGroup();
									addDebug("count number: " + mdlGrp.getEntityItemCount());
									EntityItem itemArray[] = mdlGrp.getEntityItemsAsArray();
									addDebug("Begin to run idlcatData, the itemArrary size: " + itemArray.length);
									if( itemArray.length > 0){
										boolean chgsmade[] = dag.idlCatData(m_db, m_prof, itemArray, rptSb, debugSb);
										addDebug("Time to call idlCatData itemArray : " + itemArray.length  + " EntityType: "+ rootType + ": "
											+ Stopwatch.format(System.currentTimeMillis() - curtime));
										curtime = System.currentTimeMillis();
										if (requestQueue){
											if (chgsmade.length != itemArray.length ){
												addOutput("The length of boolean resulte array is not equal to input Entity array length! ");
											}
											for (int j = 0; j < chgsmade.length; j++) {
												if (chgsmade[j]) {
													EntityItem queuedItem = itemArray[j];
													if("WWSEO".equals(queuedItem.getEntityType())){
////														They  will not queue WWSEO.ADSABRSTATUS, they will queue LSEO.ADSABRSTATUS.  
														//WWSEO does not have an ADSABRSTATUS attribute.
														//The LIFECYCLE check is done on LSEO, not WWSEO
														//For example:
														//Find one WWSEO which has at least one LSEO and LSEO.STATUS is equals to "RFR" and 
														//WWSEO.LSEO.LIFECYCLE <> "Develop" (LF02)  | "Plan" (LF01)  and trigger the ABR by workflow action.
														Vector lseoVct = getValidLSEO(queuedItem);
														for (int jj =0; jj< lseoVct.size(); jj++){
															EntityItem lseo = (EntityItem)lseoVct.elementAt(jj);
															if (lseo != null && isValidQueueItem(lseo)){
																setValues(queuedValue, lseo.getEntityType(), lseo.getEntityID());
																addOutput("Set ADSABRSTATUS of " + lseo.getKey() + " to Queue.");
															}else{
																addOutput("There is no valid LSEO for " + queuedItem.getKey() + ", do not need to queue ADSABRSTATUS.");
															}
														}	
														lseoVct.clear();
													}else if (isValidQueueItem(queuedItem)) {
															setValues(queuedValue, queuedItem.getEntityType(), queuedItem.getEntityID());
															addOutput("Set ADSABRSTATUS of " + queuedItem.getKey() + " to Queue.");
													}
													// this entity does not put it on
													// the queue.. the ADS abr does
													// so it must copy this mq info into
													// the entity.. the ADS abr must
													// clear this attr!!!!!
													if (vctReturnsEntityKeys.size() >= 500) {
														long curtime2 = System.currentTimeMillis();
														int cnt = vctReturnsEntityKeys.size();
														// update the pdh
														updatePDH();
														
														addDebug("Time to update " + cnt + " " + rootType + ": "
															+ Stopwatch.format(System.currentTimeMillis() - curtime2));
														curtime = System.currentTimeMillis();
													}
												}
											}			
										}			
									}
									tmpVec.clear();
									tmpArray = null;
									itemArray = null;
									new_elist.dereference();

								}
								if (vctReturnsEntityKeys.size() > 0) {
									int cnt = vctReturnsEntityKeys.size();
									// update the pdh
									updatePDH();
									addDebug("Time to update " + cnt + " " + rootType + ": "
										+ Stopwatch.format(System.currentTimeMillis() - curtime));
								}
								updateDALifeCycle(daRules, DALIFECYCLE_Production);							
								dag.dereference();
								idsVct.clear();
								addGenMsg("SUCCESS_READY", msg);
							} else {
								// idsVct.size() less than the MW_VENTITY_LIMIT
								EntityItem tmpArray[] = new EntityItem[idsVct.size()];
								Integer id = null;
								for (int i = 0; i < idsVct.size(); i++) {
									id = (Integer) idsVct.elementAt(i);
									tmpArray[i] = new EntityItem(null, m_prof, rootType, id.intValue());
								}
								EntityList new_elist = m_db.getEntityList(m_prof,
				                    new ExtractActionItem(null, m_db, m_prof, VeName),tmpArray);
								EntityGroup mdlGrp = new_elist.getParentEntityGroup();
								EntityItem itemArray[] = mdlGrp.getEntityItemsAsArray();
								addDebug("Begin to run idlcatData, the itemArrary size: " + itemArray.length +  NEWLINE + PokUtils.outputList(new_elist));
								if (itemArray.length > 0){
									boolean chgsmade[] = dag.idlCatData(m_db, m_prof, itemArray, rptSb, debugSb);
									if (requestQueue){
										if (chgsmade.length != itemArray.length ){
											addOutput("The length of boolean resulte array is not equal to input Entity array length! ");
										}
										for (int j = 0; j < chgsmade.length; j++) {
											if (chgsmade[j]) {
												EntityItem queuedItem = itemArray[j];
												if("WWSEO".equals(queuedItem.getEntityType())){
////													They  will not queue WWSEO.ADSABRSTATUS, they will queue LSEO.ADSABRSTATUS.  
													//WWSEO does not have an ADSABRSTATUS attribute.
													//The LIFECYCLE check is done on LSEO, not WWSEO
													//For example:
													//Find one WWSEO which has at least one LSEO and LSEO.STATUS is equals to "RFR" and 
													//WWSEO.LSEO.LIFECYCLE <> "Develop" (LF02)  | "Plan" (LF01)  and trigger the ABR by workflow action.
													Vector lseoVct = getValidLSEO(queuedItem);
													for (int jj =0; jj< lseoVct.size(); jj++){
														EntityItem lseo = (EntityItem)lseoVct.elementAt(jj);
														if (lseo != null && isValidQueueItem(lseo)){
															setValues(queuedValue, lseo.getEntityType(), lseo.getEntityID());
															addOutput("Set ADSABRSTATUS of " + lseo.getKey() + " to Queue.");
														}else{
															addOutput("There is no valid LSEO for " + queuedItem.getKey() + ", do not need to queue ADSABRSTATUS.");
														}
													}
													lseoVct.clear();
												}else if (isValidQueueItem(queuedItem)) {
														setValues(queuedValue, queuedItem.getEntityType(), queuedItem.getEntityID());
														addOutput("Set ADSABRSTATUS of " + queuedItem.getKey() + " to Queue.");
												}
												if (vctReturnsEntityKeys.size() >= 500) {
													int cnt = vctReturnsEntityKeys.size();
													// update the pdh
													updatePDH();
													long curtime2 = System.currentTimeMillis();
													addDebug("Time to update " + cnt + " " + rootType + ": "
														+ Stopwatch.format(curtime2 - curtime));
													curtime = curtime2;
												}

												if (vctReturnsEntityKeys.size() > 0) {
													int cnt = vctReturnsEntityKeys.size();
													// update the pdh
													updatePDH();
													addDebug("Time to update " + cnt + " " + rootType + ": "
														+ Stopwatch.format(System.currentTimeMillis() - curtime));
												}
											}
										}
										chgsmade = null;
									} 
								}
								updateDALifeCycle(daRules, DALIFECYCLE_Production);
								dag.dereference();
								new_elist.dereference();
								tmpArray = null;
								itemArray = null;
								temVct.clear();
								idsVct.clear();
								addGenMsg("SUCCESS_READY", msg);
							}
						}

					} else if (DALIFECYCLE_Retire.equals(DALifeCycle)) {
						addGenMsg("DALIFECYCLE_RETIRE", msg);
						//actionTaken = rsBundle.getString("DALIFECYCLE_RETIRE");
						// step 1 find all CATDATA for that entitytype and
						// attribute code - (do not look at the offering or its
						// status or wddate)
						// step 2 Delete them using DeletAction
						// step handel catch to chang lifecycle to change ro
						// obsolote.
						deleteActionName = CATACTION_TBL.get(rootType).toString();
						long curtime = System.currentTimeMillis();
//						ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof, VeName);
						if (daRules != null) {
							// If life cycle is production, then throw Exception
							verifyDARules(daRules,msg);
//							 build a pdhdomain vct, filter sps need something
							Vector domainVct = new Vector();
							String domains[] = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(DARULEEntity, "PDHDOMAIN"));
							for (int i = 0; i < domains.length; i++) {
								domainVct.add(domains[i]);
							}
							String sql = getCATDATASql(attrCode, rootType, domainVct);
							Vector idsVct = getMatchingDateIds(sql);
							if (idsVct.size() > 0) {
								addDebug("VEENTITY_LIMIT :" + MW_VENTITY_LIMIT + " CATDATA ID Count : " + idsVct.size());
								// MW_VENTITY_LIMIT is used to limit the number
								// of items to use in one extract.
								if (idsVct.size() > MW_VENTITY_LIMIT) {
									int numGrps = 0;
									if (idsVct.size() % MW_VENTITY_LIMIT != 0){
										numGrps = idsVct.size() / MW_VENTITY_LIMIT + 1; 
									}else {
									    numGrps = idsVct.size() / MW_VENTITY_LIMIT;
									}
									Vector tmpVec = new Vector();
									int numUsed = 0;
									Integer id = null;
									for (int i = 0; i < numGrps; i++) {
										for (int x = 0; x < MW_VENTITY_LIMIT; x++) {
											if (numUsed == idsVct.size()) {
												break;
											}
											id = (Integer) idsVct.elementAt(numUsed++);
											EntityItem tmpItem = new EntityItem(null, m_prof, "CATDATA", id.intValue());
											tmpVec.add(tmpItem);
										}
										EntityItem tmpArray[] = new EntityItem[tmpVec.size()];
										tmpVec.copyInto(tmpArray);
//										EntityList new_elist = m_db.getEntityList(m_prof, xai, tmpArray);
//										addDebug("Time to instance EntityList LIMIT: " + MW_VENTITY_LIMIT  + " EntityType: "+ rootType + ": "
//											+ Stopwatch.format(System.currentTimeMillis() - curtime) + NEWLINE + PokUtils.outputList(new_elist));
//										curtime = System.currentTimeMillis();
//										EntityGroup mdlGrp = new_elist.getEntityGroup("CATDATA");
//										EntityItem itemArray[] = mdlGrp.getEntityItemsAsArray();
										for (int j=0; j<tmpArray.length; j++){
											addOutput("CATADATA get from EntityGroup CATDATA[" + j + " ]" +  tmpArray[j].getEntityType() + tmpArray[j].getEntityID() + NEWLINE);
										}
										deleteCATDATAs(tmpArray);
										addDebug("Time to delete CATDATAs LIMIT: " + MW_VENTITY_LIMIT  + " EntityType: CATDATA "
											+ Stopwatch.format(System.currentTimeMillis() - curtime));
										curtime = System.currentTimeMillis();
										// release memory
										tmpArray = null;
										tmpVec.clear();
									}
								} else {

									// idsVct.size() less than the
									// MW_VENTITY_LIMIT
									EntityItem tmpArray[] = new EntityItem[idsVct.size()];
									addDebug("CATDATA id vector are:" + idsVct);
									Integer id = null;
									for (int i = 0; i < idsVct.size(); i++) {
										id = (Integer) idsVct.elementAt(i);
										tmpArray[i] = new EntityItem(null, m_prof, "CATDATA", id.intValue());
									}
									
//									EntityList new_elist = m_db.getEntityList(m_prof, xai, tmpArray);
//									EntityGroup peg = new_elist.getEntityGroup("CATDATA");
//									EntityItem catdata[] = peg.getEntityItemsAsArray();
									deleteCATDATAs(tmpArray);
									// release memory
									tmpArray = null;
								}
							}
							addDebug("Time to retire count " + idsVct.size() + rootType + " attr " + attrCode + ": "
								+ Stopwatch.format(System.currentTimeMillis() - curtime));
							DARuleEngineMgr.clearEntityType(m_db, m_prof, rootType, rptSb);
							// change all darule.lifecycle to obsolete
							updateDALifeCycle(daRules, DALIFECYCLE_Obsolete);
							addGenMsg("SUCCESS_RETIRE", msg);
							domainVct.clear();
							idsVct.clear();
						}
					} else {
						addGenMsg("FAILED_DACYCLE", msg);
						//addError("Fail the ABR - it should only be running when Ready or Retire");
						addError("Error: " + rsBundle.getString("ONLY_RUNING_READY_RETIRE"));
					}
				}catch (InvalidDARuleException InvalidDARexc) {
					if (DALIFECYCLE_Ready.equals(DALifeCycle)){
						Vector daRuleVct = InvalidDARexc.getEntityItems();
						EntityItem daRuleArry[] = new EntityItem[daRuleVct.size()];
						daRuleVct.copyInto(daRuleArry);
						updateDALifeCycle(daRuleArry, DALIFECYCLE_Change);
						for (int i = 0; i < daRuleVct.size(); i++) {
							EntityItem daRuleItem = (EntityItem) daRuleVct.elementAt(i);
							addGenMsg("FOUND_PRODUCTION_DALIFECYCLE", daRuleItem.getKey());
						}
					} else if (DALIFECYCLE_Retire.equals(DALifeCycle)){
						Vector daRuleVct = InvalidDARexc.getEntityItems();
						//EntityItem daRuleArry[] = new EntityItem[daRuleVct.size()];
						//daRuleVct.copyInto(daRuleArry);
						//updateDALifeCycle(daRuleArry, DALIFECYCLE_Production);
						for (int i = 0; i < daRuleVct.size(); i++) {
							EntityItem daRuleItem = (EntityItem) daRuleVct.elementAt(i);
							addGenMsg("FOUND_RETIRE_DALIFECYCLE", daRuleItem.getKey());
						}
					}
					addError("Error: " + InvalidDARexc.getMessage());
				}catch (DARuleException DARexc) {
						String err = DARexc.getMessage();
						addError("Error: " + err);
				}catch (Exception e){
					setReturnCode(INTERNAL_ERROR);
					throw e;
				}
			
			}else{
				addOutput("ATTRDERIVEABR not support this Entity: " + rootType);
			}
		} catch (Throwable exc) {
			addGenMsg("Failed", msg );
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + exc.getMessage());
			logError(exBuf.getBuffer().toString());
			
		} finally {	
			if (daRules != null && DARULEEntity != null) {
				try{
					if (PASS != getReturnCode()) {
						if (DALIFECYCLE_Retire.equals(DALifeCycle)) {
							//From Wayne: the ABR may move DALIFECYCLE back to "Production" when the ABR fails
							addGenMsg("SET_BACK_DACYCLE_PRODUCTION", msg);
							updateDALifeCycle(daRules, DALIFECYCLE_Production);
	
						} else if (DALIFECYCLE_Ready.equals(DALifeCycle)) {
							addGenMsg("SET_BACK_DACYCLE_CHANGE", msg);
							updateDALifeCycle(new EntityItem[] { DARULEEntity }, DALIFECYCLE_Change);
						}
					}
	
					// Set ATTRDERIVEABRSTATUS of these DARULEs consistent
					// with the results.
					addDebug("Set other DARULES consistent with the results");
	
					for (int j = 0; j < daRules.length; j++) {
						if (!daRules[j].getKey().equals(DARULEEntity.getKey())) {
							if (PASS == getReturnCode()) {
								setFlagValue(ATTRDERIVE_ATTR, ATTRDERIVEABRSTATUS_Passed, daRules[j]);
							} else {
								setFlagValue(ATTRDERIVE_ATTR, ATTRDERIVEABRSTATUS_Failed, daRules[j]);
							}
	
						}
					}
				}catch(Exception e){
					addDebug("ATTRDERIVEABRSTATUS, unable to update DALIFECYCLE. "+ e);
				}
			}
			if (mm_elist != null)
				mm_elist.dereference();
			daRules = null;
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriters();
		}

		// Print everything up to </html>
		// Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		//
		String header2 = null;
		header2 = buildDQTriggeredRptHeader();
		restoreXtraContent();
		// XML_MSG= XML Message
		String info = header1 + header2 + "<pre>" + "<br />" + rptSb.toString() + "</pre>" + NEWLINE;
		debugSb.insert(0, info);

		println(debugSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

	/**
	 * find all CATDATA for that entitytype and attribute code - (do not look at
	 * the offering or its status or wddate)
	 * 
	 * @param attrcode
	 * @return
	 */
	private String getCATDATASql(String attrcode, String rootType, Vector domainVct) {
		StringBuffer domainsb = new StringBuffer();
		for (int i = 0; i < domainVct.size(); i++) {
			if (domainsb.length() > 0) {
				domainsb.append(',');
			}
			domainsb.append("'" + domainVct.elementAt(i).toString() + "'");
		}
		StringBuffer sb = new StringBuffer("select distinct root.entityid from opicm.entity root ");
		sb.append("join opicm.relator r1 on root.entitytype=r1.entity2type and root.entityid=r1.entity2id "); // relate
		sb.append("join opicm.flag f on r1.entity1type=f.entitytype and r1.entity1id=f.entityid "); //
		// to
		// this
		// EnityType
		sb.append("join opicm.flag f2 on r1.entity2type=f2.entitytype and r1.entity2id=f2.entityid ");// f1.entitytype
		// =
		// CATDATA
		sb.append("where root.entitytype='CATDATA'");
		sb.append("and root.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and root.valto>current timestamp ");
		sb.append("and root.effto>current timestamp ");
		sb.append("and r1.entity1type='" + rootType + "'");
		sb.append("and r1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and r1.valto>current timestamp ");
		sb.append("and r1.effto>current timestamp ");
		sb.append("and f.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in (" + domainsb.toString() + ") ");
		sb.append("and f2.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f2.valto>current timestamp ");
		sb.append("and f2.effto>current timestamp ");
		sb.append("and f2.attributecode='DAATTRIBUTECODE' ");
		sb.append("and f2.attributevalue='" + attrcode + "' with ur");
		return sb.toString();

	}

	/**
	 * Delete related CATDATA
	 * @param deleteVct
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	private void deleteCATDATAs(EntityItem deleteArry[]) throws MiddlewareRequestException, SQLException, MiddlewareException,
		LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
		addDebug("deletecatdatas cnt " + deleteArry.length);
		if (deleteArry.length == 0) {
			return;
		}
		DeleteActionItem dai = new DeleteActionItem(null, m_db, m_prof, deleteActionName);

//		EntityItem childArray[] = new EntityItem[deleteArry.length];
		for (int i = 0; i < deleteArry.length; i++) {
			EntityItem catdatas = deleteArry[i];
			// get the relator
			//childArray[i] = (EntityItem) catdatas.getUpLink(0);
			EntityGroup eg = (EntityGroup) catdatas.getParent();
		    addDebug("delete " + eg +catdatas.getKey());
			
		}
		long startTime = System.currentTimeMillis();

		// do the delete
		dai.setEntityItems(deleteArry);
		m_db.executeAction(m_prof, dai);
		int deletedCnt = deleteArry.length;

		addDebug("Time to delete unmatched catdatas: " + Stopwatch.format(System.currentTimeMillis() - startTime)
			+ ", total delete count: " + deletedCnt);
	}

	/**
	 * only used for IDL
	 * 
	 * For retired, If any of the DARULEs have DALIFECYCLE of Production, then
	 * this is an error
	 * 
	 * Only one DARULE in a set can be set to 'Retire'.  If more than one is found, the ABR should fail.
	 * 
	 * according to wendy's notes 
	 * I think all DARULEs in the 'set' need to be moved back to 'Production' for a couple reasons.

     * The user may change their mind about retiring the DARULEs.  Having some in the set marked as 'Change' will force another IDL to get all DARULEs to Production state.
     * If the user does not run the ABR again to complete the retire process immediately, some of the DARULEs in the set will not be picked up when the derivation is done by the DQ abrs or the workflow abr.  This may lead to invalid derivations.

	 * @param eia
	 * @throws InvalidDARuleException
	 */
	private void verifyDARules(EntityItem eia[], String rootkey) throws InvalidDARuleException {
		Vector errvct = null;
		Vector errvct2 = null;
		for (int i = 0; i < eia.length; i++) {
			EntityItem daruleItem = eia[i];
			String lifecycle = getAttributeFlagEnabledValue(daruleItem, DARULE_LIFECYCLE__ATTR);
			if (DALIFECYCLE_Production.equals(lifecycle)) {
				if (errvct == null) {
					errvct = new Vector();
				}
				errvct.add(daruleItem);
				continue;
			} else if (!rootkey.equals(daruleItem.getKey()) && DALIFECYCLE_Retire.equals(lifecycle)){
				if (errvct2 == null) {
					errvct2 = new Vector();			
				}
				errvct2.add(daruleItem);
			}
		}
		if (errvct != null && errvct2 !=null){
			errvct2.addAll(errvct);
			throw new InvalidDARuleException("For Retire, the other DARULEs have DALIFECYCLE of 'Production' and there are more than one 'Retire' are found. The ABR failed. set all DARULEs to 'Production'.", errvct2);
		}
		if (errvct2 != null){
			throw new InvalidDARuleException("For Retire, Only one DARULE in a set can be set to 'Retire'. There are more than one found, the ABR failed. set all DARULEs to 'Production'.", errvct2);
		}
		if (errvct != null) {
			throw new InvalidDARuleException("For Retire, the other DARULEs have DALIFECYCLE of 'Production' , this is an error. set All DARULEs to 'Production'.", errvct);
		}

	}

	/**
	 * XML generator ABR may need to be queued. Offering data that is 'Ready for
	 * Review' but never 'Final' or that is â€œFinalâ€� needs to be queued for XML
	 * generation
	 * LIFECYCLE_Develop = "LF02";// LIFECYCLE	=	"Develop" (LF02)
     * LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan

	 * @param queuedItem
	 * @return
	 */
	private boolean isValidQueueItem(EntityItem queuedItem) {
		String statusFlag = getAttributeFlagEnabledValue(queuedItem, "STATUS");
		String lifeCycleFlag = getAttributeFlagEnabledValue(queuedItem, "LIFECYCLE");
		if (statusFlag == null ){
			addOutput("Entity attribute Status is null, do not queue ADSABRSTATUS");
			return false;
		}
		if (STATUS_FINAL.equals(statusFlag)){
			addOutput("Entity attribute Status is final, Queue ADSABRSTATUS");
			return true;
		}
		if (lifeCycleFlag == null) {
			addOutput("Entity attribute LifeCycle is null, do not queue ADSABRSTATUS");	
			return false;
		}
		if (STATUS_R4REVIEW.equals(statusFlag) && LIFECYCLE_Launch.equals(lifeCycleFlag)) {
			return false;
		}
		return true;
	}
	
	/**
	 * MODEL ---> WWSEO----> LSEO
	 * IF DAENTITY is WWSEO then
	 * They  will not queue WWSEO.ADSABRSTATUS, they will queue LSEO.ADSABRSTATUS.  
	 * WWSEO does not have an ADSABRSTATUS attribute.
	 * The LIFECYCLE check is done on LSEO, not WWSEO
	 * For example:
	 * Find one WWSEO which has at least one LSEO and LSEO.STATUS is equals to "RFR" and 
	 * WWSEO.LSEO.LIFECYCLE <> "Develop" (LF02)  | "Plan" (LF01)  and trigger the ABR by workflow action.
	 * @param rootEntity
	 * @return
	 */
	private Vector getValidLSEO(EntityItem rootEntity) {
        Vector lseoVec = new Vector();
		Vector lseoVect = rootEntity.getDownLink();
		for (int i = 0; i < lseoVect.size(); i++) {
			EntityItem relator = (EntityItem) lseoVect.elementAt(i);
			if (relator != null && "WWSEOLSEO".equals(relator.getEntityType())) {
				EntityItem lseo = (EntityItem) relator.getDownLink(0);
				if (lseo != null && "LSEO".equals(lseo.getEntityType())) {
					String statusFlag = getAttributeFlagEnabledValue(lseo, "STATUS");
					if (STATUS_FINAL.equals(statusFlag) || STATUS_R4REVIEW.equals(statusFlag)){
						lseoVec.add(lseo);	
					}
				}
			}
		}
		return lseoVec;
	}

	/**
	 * 1.When Ready queued ABR, then IDL function will update DALIFECYCLE that
	 * are not 'Obsolete' to 'Production' once all updates are made
	 * successfully. 2.When Retired queued ABR, then ABR function will change
	 * DALIFECYCLE to Obsolete once all updates are made successfully. 3.When
	 * Ready or Retired queued ABR, If any of the DARULEs have DALIFECYCLE of
	 * Production, then this is an error. Produce an error report, change
	 * DALIFECYDLE to â€œChangeâ€� , do not process and set ABR to Failed
	 * 
	 * @param daRuleArry
	 * @param daLifeCycle
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void updateDALifeCycle(EntityItem[] daRuleArry, String daLifeCycle) throws SQLException, MiddlewareException {
		if (daRuleArry != null){
			
			for (int i = 0; i < daRuleArry.length; i++) {
				EntityItem daRuleItem = daRuleArry[i];
				String lifeCycleFlag = getAttributeFlagEnabledValue(daRuleItem, DARULE_LIFECYCLE__ATTR);
				if (DALIFECYCLE_Change.equals(daLifeCycle) && !DALIFECYCLE_Change.equals(lifeCycleFlag)) {
					setFlagValue(DARULE_LIFECYCLE__ATTR, daLifeCycle, daRuleItem);
				} else if (!DALIFECYCLE_Obsolete.equals(lifeCycleFlag)) {
					setFlagValue(DARULE_LIFECYCLE__ATTR, daLifeCycle, daRuleItem);
				}
			}
		}
	}

	/***************************************************************************
	 * Sets the specified Flag Attribute on the Root Entity
	 * 
	 * @param profile
	 *            Profile
	 * @param strAttributeCode
	 *            The Flag Attribute Code
	 * @param strAttributeValue
	 *            The Flag Attribute Value
	 */
	private void setFlagValue(String strAttributeCode, String strAttributeValue, EntityItem daRuleItem)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {
		// logMessage(getDescription() + " ***** " + strAttributeCode + " set
		// to: " + strAttributeValue);
		addDebug("setFlagValue entered for " + daRuleItem.getKey() + " " + strAttributeCode + " set to: " + strAttributeValue);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = daRuleItem.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (metaAttr == null) {
			addDebug("setFlagValue: " + strAttributeCode + " was not in meta for " + daRuleItem.getEntityType()
				+ ", nothing to do");
			// logMessage(getDescription() + " ***** " + strAttributeCode + "
			// was not in meta for " + daRuleItem.getEntityType()
			// + ", nothing to do");
			return;
		}
		if (strAttributeValue != null) {
			if (strAttributeValue.equals(getAttributeFlagEnabledValue(daRuleItem, strAttributeCode))) {
				addDebug("setFlagValue " + daRuleItem.getKey() + " " + strAttributeCode + " already matches: "
					+ strAttributeValue);
			} else {
				try {
					if (m_cbOn == null) {
						setControlBlock(); // needed for attribute updates
					}
					ReturnEntityKey rek = new ReturnEntityKey(daRuleItem.getEntityType(), daRuleItem.getEntityID(), true);

					SingleFlag sf = new SingleFlag(m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
						strAttributeCode, strAttributeValue, 1, m_cbOn);
					Vector vctAtts = new Vector();
					Vector vctReturnsEntityKeys = new Vector();
					vctAtts.addElement(sf);
					rek.m_vctAttributes = vctAtts;
					vctReturnsEntityKeys.addElement(rek);

					m_db.update(m_prof, vctReturnsEntityKeys, false, false);
					addDebug(daRuleItem.getKey() + " had " + strAttributeCode + " set to: " + strAttributeValue);
				} finally {
					m_db.commit();
					m_db.freeStatement();
					m_db.isPending("finally after update in setflag value");
				}
			}
		}
	}

	/***************************************************************************
	 * Sets the specified Attributes on the specified Entity
	 * 
	 * @param mqFlags
	 * @param queuedValue
	 * @param etype
	 * @param eid
	 */
	private void setValues(String queuedValue, String etype, int eid) {
		if (m_cbOn == null) {
			setControlBlock(); // needed for attribute updates
		}

		ReturnEntityKey rek = new ReturnEntityKey(etype, eid, true);
		Vector vctAtts = new Vector();
		rek.m_vctAttributes = vctAtts;
		vctReturnsEntityKeys.addElement(rek);

		// queue the abr
		SingleFlag sf = new SingleFlag(m_prof.getEnterprise(), etype, eid, ADS_XMLEED_ATTR, queuedValue, 1, m_cbOn);
		// tm was picking it up before the propfile is set, so defer it
		sf.setDeferredPost(true);

		vctAtts.addElement(sf);
	}

	/**
	 * ATTRDERIVEABRSTATUS_ADSABRSTATUS_queuedValue=0020
	 * 
	 * @param abrcode
	 * @return
	 */
	private String getQueuedValue(String abrcode) {
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(m_abri.getABRCode() + "_" + abrcode);
	}

	/**
	 * get the query to use to find entities that meet the date criteria Status
	 * is RFR/Final and withdraw Effective Date > = now
	 * 
	 * 
	 * @param entityType
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private Vector getValidEntityIds(String entityType, EntityItem DARULEEntity) throws SQLException, MiddlewareException {
		String sql = null;
		Vector idVct = new Vector(1);
		String cofgrpAttr = "STATUS";
		StringBuffer cofgrpValue = new StringBuffer();
		cofgrpValue.append("'" + STATUS_R4REVIEW + "'");
		cofgrpValue.append(',');
		cofgrpValue.append("'" + STATUS_FINAL + "'");
		// build a pdhdomain vct, filter sps need something
		Vector domainVct = new Vector();
		String domains[] = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(DARULEEntity, "PDHDOMAIN"));
		for (int i = 0; i < domains.length; i++) {
			domainVct.add(domains[i]);
		}
		long startTime = System.currentTimeMillis();
		String dateAttr = (String) WTHDRWEFFCTVDATE_Attr_TBL.get(entityType);
		if (dateAttr != null) {
			String dateValue = m_prof.getNow().substring(0, 10);
			if (!"WWSEO".equals(entityType) ) {
				sql = getFilteredDateSql(entityType, dateAttr, dateValue, cofgrpAttr, cofgrpValue, domainVct);
				if (sql != null) {
					idVct = getMatchingDateIds(sql);
					addDebug("Time to filter on dates: " + Stopwatch.format(System.currentTimeMillis() - startTime));
				}
			} else {
				Vector tmpidVct = null;
				String sql2 = null;
				sql = getFilteredWWSEOSql(cofgrpAttr, cofgrpValue, domainVct);
				if (sql != null) {
					tmpidVct = getMatchingDateIds(sql);
					addDebug("Time to filter on dates: " + Stopwatch.format(System.currentTimeMillis() - startTime));
				}
				if (tmpidVct.size() > 0) {
					sql2 = getFilteredLSEOSql(dateAttr, dateValue, cofgrpAttr, cofgrpValue);
					if (sql2 != null) {
						idVct = getMatchingDateIds(sql2, tmpidVct);
					}
				}
			}

		}
		return idVct;
	}

	/**
	 * get the query to use to find entities that meet the date criteria Status
	 * is RFR/Final
	 * 
	 * This query returns active entities which either do not have an active
	 * withdrawdate attribute or the value of this attribute is >= to the value
	 * of the parameter
	 * 
	 * @param entityType
	 * @param dateAttr
	 * @param dateValue
	 * @param domainVct
	 * @return
	 */
	private String getFilteredDateSql(String entityType, String dateAttr, String dateValue, String cofgrpAttr,
		StringBuffer cofgrpValue, Vector domainVct) {
		StringBuffer domainsb = new StringBuffer();
		for (int i = 0; i < domainVct.size(); i++) {
			if (domainsb.length() > 0) {
				domainsb.append(',');
			}
			domainsb.append("'" + domainVct.elementAt(i).toString() + "'");
		}
		StringBuffer sb = new StringBuffer("select mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
		sb.append("where mdl.entitytype='" + entityType + "' ");
		sb.append("and mdl.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in (" + domainsb.toString() + ") ");
		sb.append("and f1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='" + cofgrpAttr + "'");
		sb.append("and f1.attributevalue in (" + cofgrpValue.toString() + ") ");
		sb.append("and not exists ");
		sb.append("(select t.entityid from opicm.text t where ");
		sb.append("t.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and t.entitytype='" + entityType + "' ");
		sb.append("and t.entityid=mdl.entityid ");
		sb.append("and t.attributecode='" + dateAttr + "' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp) ");
		sb.append("union ");
		sb.append("select mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
		sb.append("join opicm.text t on t.entitytype=mdl.entitytype and t.entityid=mdl.entityid ");
		sb.append("where mdl.entitytype='" + entityType + "' ");
		sb.append("and mdl.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in (" + domainsb.toString() + ") ");
		sb.append("and f1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='" + cofgrpAttr + "'");
		sb.append("and f1.attributevalue in (" + cofgrpValue.toString() + ") ");
		sb.append("and t.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and t.attributecode='" + dateAttr + "' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp ");
		sb.append("and t.attributevalue>='" + dateValue + "' with ur");

		return sb.toString();
	}

	/**
	 * This query returns active WWSEO related LSEOs which do have Status is
	 * RFR/Final and related MODEL either have no WithDrawEffective attribute or
	 * the value of this attribute is >= to the value of the parameter
	 * 
	 * @param dateAttr
	 * @param dateValue
	 * @param cofgrpAttr
	 * @param cofgrpValue
	 * @param domainVct
	 * @return
	 */
	private String getFilteredLSEOSql(String dateAttr, String dateValue, String cofgrpAttr, StringBuffer cofgrpValue) {
		StringBuffer sb = new StringBuffer("select distinct wwseo.entityid from opicm.entity wwseo ");
		sb.append("join opicm.relator r1 on wwseo.entitytype=r1.entity1type and wwseo.entityid=r1.entity1id "); // relate
		// to
		// lseo
		sb.append("join opicm.relator r2 on wwseo.entitytype=r2.entity2type and wwseo.entityid=r2.entity2id "); // realte
		// to
		// model
		sb.append("join opicm.flag f1 on r1.entity2type=f1.entitytype and r1.entity2id=f1.entityid ");// f1.entitytype
		// =
		// lseo
		sb.append("where wwseo.entitytype='WWSEO' ");
		sb.append("and wwseo.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and wwseo.valto>current timestamp ");
		sb.append("and wwseo.effto>current timestamp ");
		sb.append("and r1.entitytype='WWSEOLSEO' ");
		sb.append("and r1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and r1.valto>current timestamp ");
		sb.append("and r1.effto>current timestamp ");
		sb.append("and r2.entitytype='MODELWWSEO' ");
		sb.append("and r2.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and r2.valto>current timestamp ");
		sb.append("and r2.effto>current timestamp ");
		sb.append("and f1.attributecode='" + cofgrpAttr + "' ");
		sb.append("and f1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributevalue in (" + cofgrpValue.toString() + ") ");
		sb.append("and not exists  ");
		sb.append("(select t.entityid from opicm.text t where ");
		sb.append("t.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and t.entitytype='MODEL' ");
		sb.append("and t.entityid=r2.entity1id ");
		sb.append("and t.attributecode='" + dateAttr + "' ");
		sb.append("and t.valto>current timestamp  ");
		sb.append("and t.effto>current timestamp) ");
		sb.append("union  ");
		sb.append("select distinct wwseo.entityid from opicm.entity wwseo ");
		sb.append("join opicm.relator r1 on wwseo.entitytype=r1.entity1type and wwseo.entityid=r1.entity1id "); // relate
		// to
		// lseo
		sb.append("join opicm.relator r2 on wwseo.entitytype=r2.entity2type and wwseo.entityid=r2.entity2id "); // relate
		// to
		// Model
		sb.append("join opicm.flag f1 on r1.entity2type=f1.entitytype and r1.entity2id=f1.entityid ");// f1.entitytype
		// =
		// lseo
		sb.append("join opicm.text t on r2.entity1type=t.entitytype and r2.entity1id=t.entityid ");// relate
		// to
		// model
		sb.append("where wwseo.entitytype='WWSEO' ");
		sb.append("and wwseo.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and wwseo.valto>current timestamp ");
		sb.append("and wwseo.effto>current timestamp ");
		sb.append("and r1.entitytype='WWSEOLSEO' ");
		sb.append("and r1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and r1.valto>current timestamp ");
		sb.append("and r1.effto>current timestamp ");
		sb.append("and r2.entitytype='MODELWWSEO' ");
		sb.append("and r2.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and r2.valto>current timestamp ");
		sb.append("and r2.effto>current timestamp ");
		sb.append("and f1.attributecode='" + cofgrpAttr + "' ");
		sb.append("and f1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributevalue in (" + cofgrpValue.toString() + ") ");
		sb.append("and t.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and t.attributecode='" + dateAttr + "' ");
		sb.append("and t.valto>current timestamp  ");
		sb.append("and t.effto>current timestamp ");
		sb.append("and t.attributevalue >='" + dateValue + "' with ur");
		return sb.toString();
	}

	/**
	 * This query returns active WWSEO which both get STATUS is RFR/final and
	 * PDHDOMAIN in domainVct attribute or the value of this attribute is >= to
	 * the value of the parameter and The LSEO's MODEL COFGRP attribute matches
	 * the parameter
	 * 
	 * @param cofgrpAttr
	 * @param cofgrpValue
	 * @param domainVct
	 * @return
	 */
	private String getFilteredWWSEOSql(String cofgrpAttr, StringBuffer cofgrpValue, Vector domainVct) {
		StringBuffer domainsb = new StringBuffer();
		for (int i = 0; i < domainVct.size(); i++) {
			if (domainsb.length() > 0) {
				domainsb.append(',');
			}
			domainsb.append("'" + domainVct.elementAt(i).toString() + "'");
		}
		StringBuffer sb = new StringBuffer("select distinct wwseo.entityid from opicm.entity wwseo ");
		sb.append("join opicm.flag f2 on wwseo.entitytype=f2.entitytype and wwseo.entityid=f2.entityid ");// f1.entitytype
		// =
		// wwse
		sb.append("where wwseo.entitytype='WWSEO' ");
		sb.append("and wwseo.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and wwseo.valto>current timestamp ");
		sb.append("and wwseo.effto>current timestamp ");
		sb.append("and f2.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f2.valto>current timestamp ");
		sb.append("and f2.effto>current timestamp ");
		sb.append("and f2.attributecode='PDHDOMAIN' ");
		sb.append("and f2.attributevalue in (" + domainsb.toString() + ") ");
		sb.append("intersect  ");
		sb.append("select distinct wwseo.entityid from opicm.entity wwseo ");
		sb.append("join opicm.flag f1 on wwseo.entitytype=f1.entitytype and wwseo.entityid=f1.entityid ");// f1.entitytype
		// =
		// wwse
		sb.append("where wwseo.entitytype='WWSEO' ");
		sb.append("and wwseo.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and wwseo.valto>current timestamp ");
		sb.append("and wwseo.effto>current timestamp ");
		sb.append("and f1.enterprise='" + m_prof.getEnterprise() + "' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue in (" + cofgrpValue.toString() + ") with ur");
		return sb.toString();

	}

	/**
	 * find ids that meet the withdrawn date conditions
	 * 
	 * @param sql
	 * @param idVct
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 */
	private Vector getMatchingDateIds(String sql) throws SQLException, MiddlewareException {
		Vector matchIdVct = new Vector();

		addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
		PreparedStatement ps = null;
		ResultSet result = null;

		try {
			ps = m_db.getPDHConnection().prepareStatement(sql);

			result = ps.executeQuery();

			while (result.next()) {
				int iEntityID = result.getInt(1);
				if (iEntityID > 0) { // bypass default entities
					matchIdVct.add(new Integer(iEntityID));
				}
			}
			addDebug("getMatchingDateIds all matchIdVct.cnt " + matchIdVct.size());
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
					System.err.println("getMatchingDateIds(), unable to close result. " + e);
				}
				result = null;
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					System.err.println("getMatchingDateIds(), unable to close ps. " + e);
				}
				ps = null;
			}

			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}

		return matchIdVct;
	}

	/**
	 * find ids that meet the withdrawn date conditions
	 * 
	 * @param sql
	 * @param idVct
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private Vector getMatchingDateIds(String sql, Vector idVct) throws SQLException, MiddlewareException {
		Vector matchIdVct = new Vector();

		addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
		PreparedStatement ps = null;
		ResultSet result = null;

		try {
			ps = m_db.getPDHConnection().prepareStatement(sql);

			result = ps.executeQuery();

			while (result.next()) {
				int iEntityID = result.getInt(1);
				if (iEntityID > 0) { // bypass default entities
					matchIdVct.add(new Integer(iEntityID));
				}
			}
			addDebug("getMatchingDateIds all matchIdVct.cnt " + matchIdVct.size());
			// find the intersection of the 2 sets
			matchIdVct.retainAll(idVct);
			addDebug("getMatchingDateIds after retainall matchIdVct " + matchIdVct.size());
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
					System.err.println("getMatchingDateIds(), unable to close result. " + e);
				}
				result = null;
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					System.err.println("getMatchingDateIds(), unable to close ps. " + e);
				}
				ps = null;
			}

			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}

		return matchIdVct;
	}

	/***************************************************************************
	 * build debug msg xmlgenSb = new StringBuffer();
	 */
	protected void addGenMsg(String rsrc, String info) {
	       MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
	       Object args[] = new Object[]{info};
	       xmlgenSb.append(msgf.format(args)+"<br />");
	}

	/***************************************************************************
	 * A. Data Quality Triggered ABRs
	 * 
	 * The Report should identify: - USERID (USERTOKEN) - Role - Workgroup -
	 * Date/Time - Status - Prior feed Date/Time - Prior Status - Action Taken
	 */
	private String buildDQTriggeredRptHeader() {
		String HEADER2 = "<table>" + NEWLINE 
		    + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
			+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE 
			+ "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE
			+ "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE 
			+ "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE 
			+ "<tr><th>Action Taken: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE;
		MessageFormat msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = m_prof.getNow();
		args[4] = rootDesc+": "+navName;
		args[5] = actionTaken + "<br />" + xmlgenSb.toString();
		return msgf.format(args);
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg =  new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
		for (int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			navName.append(" ");
		}

		return navName.toString();
	}
	
	private void closePrintWriters() {
		if (dbgPw != null) {
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
	}

	/***************************************************************************
	 * get database
	 */
	protected Database getDB() {
		return m_db;
	}

	/***************************************************************************
	 * get attributecode
	 */
	protected String getABRAttrCode() {
		return m_abri.getABRCode();
	}

	/***************************************************************************
	 * add msg to report output
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/***************************************************************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		if (dbgPw != null) {
			dbgLen += msg.length();
			dbgPw.println(msg);
			dbgPw.flush();
		} else {
			debugSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}

	/***************************************************************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

	/***************************************************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		// return "1.8"; cvs failure
		return "1.12";// "1.10";
	}

	/***************************************************************************
	 * Get ABR description
	 * 
	 * @return java.lang.String
	 */
	public String getDescription() {
		return "ATTRDERIVEABRSTATUS";
	}

	/***************************************************************************
	 * Update the PDH with the values in the vector, do all at once
	 * 
	 */
	private void updatePDH() throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		java.rmi.RemoteException, COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.EANBusinessRuleException {
		logMessage(getDescription() + " updating PDH with " + vctReturnsEntityKeys.size() + " entitykeys");
		addDebug("updatePDH entered for vctReturnsEntityKeys: " + vctReturnsEntityKeys.size());
		if (vctReturnsEntityKeys.size() > 0) {
			try {
				m_db.update(m_prof, vctReturnsEntityKeys, false, false);
			} finally {
				vctReturnsEntityKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}
	/**
	 * get limit values such as run_limit and MW_VEETITY_LIMIT from abr.server.properties file
	 *
	 */
	private void getPropertiesLimitValues(){
		String velimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ATTRDERIVEABRSTATUS", "_velimit",
		"5");
		if(isDigit(velimit)){
			// MW_VENTITY_LIMIT is used to limit the number of entities one extract of EntityList
			MW_VENTITY_LIMIT = Integer.parseInt(velimit);
		}else{
			MW_VENTITY_LIMIT = 5;
		}
		
		String runlimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ATTRDERIVEABRSTATUS", "_runlimit",
		"0");
		if(isDigit(runlimit)){
		     //run_limit is used by tester to limit the number of entity ID. 
		    RUN_LIMIT = Integer.parseInt(runlimit);
		}else{
			RUN_LIMIT = 0;
		}
    }
	/**
	 * merge debug info into html rpt if possible
	 */
	private void restoreXtraContent(){
		// if written to file and still small enough, restore debug to the abr rpt and delete the file
		if (dbgLen+rptSb.length()<MAXFILE_SIZE){
			// read the file in and put into the stringbuffer
			InputStream is = null;
			FileInputStream fis = null;
			BufferedReader rdr = null;
			try{
				fis = new FileInputStream(dbgfn);
				is = new BufferedInputStream(fis);

				String s=null;
				StringBuffer sb = new StringBuffer();
				rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// append lines until done
				while((s=rdr.readLine()) !=null){
					sb.append(s+NEWLINE);
				}
				rptSb.append("<!-- "+sb.toString()+" -->"+NEWLINE);

				// remove the file
				File f1 = new File(dbgfn);
				if (f1.exists()) {
					f1.delete();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if (is!=null){
					try{
						is.close();
					}catch(Exception x){
						x.printStackTrace();
					}
				}
				if (fis!=null){
					try{
						fis.close();
					}catch(Exception x){
						x.printStackTrace();
					}
				}
			}
		}
	}
}
