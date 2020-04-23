//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.SingleFlag;


/**********************************************************************************
 * ELEMENTABRSTATUS class 
 *  
 *BH FS ABR Element CATDATA 20110525.doc
 * need ELEMENTSTATUS and ELEMENTABRSTATUS abr attr for it added to all elements
 * need transitions of ELEMENTSTATUS to queue this ELEMENTABRSTATUS
 * need ELEMENTCATDATA and all attr and VE defs and actions to create/edit
 * All attributes will be required via an EXIST rule.
 * ECSACTIVE and ECSCHGET will be SEARCHABLE
 * Need search action to find ELEMENTCATDATA
 *
 * The purpose of this document is to describe the ABR and process that supports changes to entities that do not have Data Quality rules 
 * but affect the derivation of Catalog Data (CATDATA) the downstream systems can process the XML feed of this data
 *
 * An ABR is an application that is automatically queued by a change in the “Status” of one of these entity types. 
 * These entity types will have a new attribute code “Status” (ELEMENTSTATUS) that has three values:
 * •	“Draft”
 * •	“Change”
 * •	“Final”
 * 
 * A change in “Status” from “Draft” to “Final” will queue this ABR and lock all attributes from further update. 
 * A user may change the “Status” to “Change” at any time. A change in “Status” from “Change” to “Final” will 
 * queue this ABR. There is no need to queue the Data Quality ABR.
 * 
 * Changes to “Catalog Derived Data” (CATDATA) may require that the parent entity be queued in order to send 
 * XML downstream.
 *
 * Work Item 472923 “Catalog Derivation - Linda has concerns with the process with respect to updating an Element and having the changes flow”
 *
 * There are a significant number of entities that will have a new attribute “Status” (ELEMENTSTATUS)  added 
 * in order to manage the potential impact to Catalog Derived Data” (CATDATA).
 * 
 * The entities involved are all of the children and grand children of Features (FEATURE) and some children of 
 * “WW Single Entity Offering” (WWSEO). These entity types are listed in a table on the next page.
 * 
 * When a new instance of any of these entity types is created, the “Status” will default to “Draft”. Once the 
 * data is entered, the user will change “Status” to “Final”. This will queue this ABR.
 * 
 * In order to change any data, the user will have to change “Status” to “Change”, make the changes, and then 
 * change “Status” back to “Final”. Again, this will queue this ABR.
 *
EntityType
AUD
BAY
BAT
BAYSAVAIL
CABLE
CNTRYPACK
DERIVEDDATA
ENVIRNMTLINFO
EXPDUNIT
FAXMODM
GRPHADAPTER
HDC
HDD
INPUTDVC
KEYBD
LANGPACK
MECHPKG
MECHRACKCAB
MEMORY
MEMORYCARD
MONITOR
NIC
OPTCALDVC
PLANAR
PORT
PRN
PROC
PWRSUPPLY
REMOVBLSTOR
SECUR
SLOT
SLOTSAVAIL
SPEAKER
SVC
SYSMGADPTR
TAPEDRIVE
TECHCAP
TECHINFO
TECHINFOFEAT
WEIGHTNDIMN
WLESSNC

 * VII.	ABR
 * 
 * The ABR will be invoked for a single instance of one of these entity types. The ABR will look up the entity type 
 * in “ELEMENT CATDATA Setup” (ELEMENTCATDATA) using attribute code “Entity Type Changed” (ECSCHGET). If the 
 * attribute “Active” (ECSACTIVE) is set to “InActive”, do not proceed. For each instance where attribute “Active” 
 * (ECSACTIVE) is set to “Active”, proceed with the following.
 * 
 * Use the Virtual Entity “VENAME” (ECSVENAME) to extract the structure of interest. The entity type that may be 
 * impacted is identified via attribute “Entity Type Impacted” (ECSIMPACTET) and is found in the structure obtained 
 * using this VE. The VE will be such that there is only one path from ECSCHGET to ECSIMPACTET and hence the code 
 * may simply look for the “Entity Type Impacted” within the structure. There may be multiple instances of this entity 
 * type, all of which are impacted.
 * 
 * If the following criteria is met
 * 1.	Data Quality of the “Entity Type Impacted” (ECSIMPACTET) entity – either of the following:
 * 	“Life Cycle” (LIFECYCLE) = "Develop" (LF02)  | "Plan" (LF01)
 *            and
 *   "Status” (STATUS) = "Ready for Review" (0040)
 *   “Status” (STATUS) = "Final" (0020)
 * 2.	“Entity Type Impacted” (ECSIMPACTET) entity not withdrawn
 * •	FEATURE
 * WITHDRAWDATEEFF_T  > NOW()
 * •	MODEL
 * WTHDRWEFFCTVDATE > NOW()
 * •	WWSEO
 * MODELWWSEO-u: MODEL. WTHDRWEFFCTVDATE > NOW()
 * 
 * Then the ABR for “Entity Type Impacted” (ECSIMPACTET) should set “Set Attribute” (ECSSETATTR) to “Queued” 
 * (0020) if  the current value is “Passed” (0030).
 * 
 * If “Set Attribute” (ECSSETATTR) is “In Process” (0050), then wait for 5 minutes and check again. 
 * This step should be repeated for a maximum of 5 times after which, simply exit. For all other values of 
 * “ABR Advance Status”, simply exit.
 * 
 * 

The subscription information for the ABR Report will be:
CAT1= “ELEMENTCATDATA”
CAT2= value of (root entity.PDHDOMAIN)
CAT3=RptStatus (aka Return Code)
CAT4= 

 *
ELEMENTABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.ELEMENTABRSTATUS
ELEMENTABRSTATUS_enabled=true
ELEMENTABRSTATUS_idler_class=A
ELEMENTABRSTATUS_keepfile=true
ELEMENTABRSTATUS_report_type=DGTYPE01
ELEMENTABRSTATUS_vename=dummy
ELEMENTABRSTATUS_CAT1=ELEMENTCATDATA
ELEMENTABRSTATUS_CAT2=RPTCLASS.PDHDOMAIN
ELEMENTABRSTATUS_CAT3=RPTSTATUS
ELEMENTABRSTATUS_debugLevel=0
 */
//$Log: ELEMENTABRSTATUS.java,v $
//Revision 1.2  2014/01/13 13:53:29  wendy
//migration to V17
//
//Revision 1.1  2011/06/30 20:24:16  wendy
//BH FS ABR Element CATDATA 20110525.doc - Work Item 472923
//
public class ELEMENTABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];

	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private int abr_debuglvl=D.EBUG_ERR;
	private String navName = "";
	private Vector skippedStatusVct = new Vector(); // status not rfr or final
	private Hashtable queuedTbl = new Hashtable();
	private Vector withdrawnVct = new Vector(); // withdrawn
	private Vector skippedABRVct = new Vector(); // abr not passed or inprocess
	private boolean hasWaitedBefore = false;

	private static final String ELEMENTCATDATA_SRCHACTION_NAME = "SRDELCATDATA"; 

	private static final String ABR_QUEUED = "0020";
	private static final String ABR_PASSED = "0030";
	private static final String ABR_INPROCESS = "0050";
	private static final String ECSACTIVE_Active="ECS1";
	private static final String LIFECYCLE_Develop	= "LF02";// LIFECYCLE	=	"Develop" (LF02)
	private static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan
	private static final String STATUS_FINAL = "0020";
	private static final String STATUS_R4REVIEW   = "0040";
	private final static String FOREVER_DATE = "9999-12-31";

	private StringBuffer summarySb = new StringBuffer();
	private Vector vctReturnsEntityKeys = new Vector();
	private String strNow=null;

	/**********************************
	 *  Execute ABR.
	 */
	public void execute_run()
	{
		/*
The report should identify the entity that was being processed as follows:
•	Entity Type using Long Description
•	Entity Navigation Display Name
•	Entity Id
•	Date Time Stamp

Error conditions should be reported via a report.

A summary of data impacted should be produced. This summary show by impacted entity type a count of 
affected entities and counts for entities skipped because they are withdrawn and skipped due to status.

For example (similar or alternative formatting is acceptable):
•	Model
o	Queued	456
o	Withdrawn	247
o	Status		123
•	FEATURE
o	Queued	57
o	Withdrawn	25
o	Status		12
•	WWSEO
o	Queued	654
o	Withdrawn	150
o	Status		154

		 */
		// must split because too many arguments for messageformat, max of 10.. this was 11
		String HEADER = "<head>"+
		EACustom.getMetaTags(getDescription()) + NEWLINE +
		EACustom.getCSS() + NEWLINE +
		EACustom.getTitle("{0} {1}") + NEWLINE +
		"</head>" + NEWLINE + "<body id=\"ibm-com\">" +
		EACustom.getMastheadDiv() + NEWLINE +
		"<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
		"<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
		"<tr><th>Entity Id: </th><td>{5}</td></tr>"+NEWLINE +
		"<tr><th>Return code: </th><td>{6}</td></tr>"+NEWLINE +
		"</table>"+NEWLINE+
		"<!-- {7} -->" + NEWLINE;

		MessageFormat msgf;
		String rootDesc="";
		String abrversion="";

		println(EACustom.getDocTypeHtml()); //Output the doctype and html
  
		try
		{
			long startTime = System.currentTimeMillis();
			start_ABRBuild(); // pull dummy VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			// get root from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			// debug display list of groups
			addDebug(D.EBUG_ERR,"DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey());
			//  +" extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			addDebug(D.EBUG_SPEW,"Time to pull root VE: "+Stopwatch.format(System.currentTimeMillis()-startTime));

			//Default set to pass
			setReturnCode(PASS);
//			fixme remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			//NAME is navigate attributes
			navName = getNavigationName(rootEntity);

			rootDesc = m_elist.getParentEntityGroup().getLongDescription()+": "+navName;

			// find all Active ELEMENTCATDATA 
			Vector activeVct = searchForELEMENTCATDATA(rootEntity.getEntityType());
			if(activeVct!=null){ // error finding flagcode if null returned
				strNow = m_db.getDates().getNow().substring(0, 10);

				if (activeVct.size()==0){
					//NO_ACTIVE_FND = No Active ELEMENTCATDATA found.
					addOutput(rsBundle.getString("NO_ACTIVE_FND"));
				}else{
					// sort active on VENAME, only pull when needed - some maybe shared
					Collections.sort(activeVct, new AttrComparator("ECSVENAME"));
					String VeName = null;
					String prevType = null;
					EntityList offeringlist = null;
					Set completedTypeSet = new HashSet();
					String ecdNavName = "";

					//For each instance where attribute “Active” (ECSACTIVE) is set to “Active”, proceed with the following.
					//loop thru active
					for(int i=0; i<activeVct.size(); i++){
						EntityItem ecdItem = (EntityItem)activeVct.elementAt(i);

						// pull VE to get to offerings for each, maybe use VE already pulled
						String curVeName = PokUtils.getAttributeValue(ecdItem, "ECSVENAME", "", "", false);//use long desc
						String impactedType = PokUtils.getAttributeValue(ecdItem, "ECSIMPACTET", "", "", false);//use long desc
						String setAttr = PokUtils.getAttributeValue(ecdItem, "ECSSETATTR", "", "", false);//use long desc

						addDebug(D.EBUG_INFO,"activeVct["+i+"] "+ecdItem.getKey()+" vename "+curVeName+" impactedType "+impactedType+
								" setAttr "+setAttr);
						String key = impactedType+":"+setAttr;
						if(completedTypeSet.contains(key)){
							addDebug(D.EBUG_WARN,"Skipping "+ecdItem.getKey()+" duplicate key "+key);
							continue;
						}
						completedTypeSet.add(key);  // keep track of any duplicate requests
						
						if(ecdNavName==null){ // get it for the first summary output
							ecdNavName = ecdItem.getEntityGroup().getLongDescription()+": "+this.getNavigationName(ecdItem);
						}
						
						// output summary info for prevtype
						if(prevType!=null){
							outputSummary(prevType,ecdNavName);
						}
						ecdNavName = ecdItem.getEntityGroup().getLongDescription()+": "+this.getNavigationName(ecdItem); // get it after outputting summary info
		
						if(!curVeName.equals(VeName)){
							if(offeringlist!=null){
								offeringlist.dereference();
							}
							VeName = curVeName;
							//Use the Virtual Entity “VENAME” (ECSVENAME) to extract the structure of interest. 
							offeringlist = m_db.getEntityList(m_prof,
									new ExtractActionItem(null, m_db, m_prof, VeName),
									new EntityItem[] {rootEntity});
							addDebug(D.EBUG_ERR,"Extract "+VeName+NEWLINE+PokUtils.outputList(offeringlist));
							
							//use this ve and extract to get to prodstructs.. then pull 
							// another ve from prodstructs to wwseo and its model
							// if impacted type is WWSEO
							if(impactedType.equals("WWSEO") &&
									offeringlist.getEntityGroup("WWSEO")==null){ // some VEs dont go thru prodstruct
								EntityList wwseolist = getWWSEOList(offeringlist);
								if(wwseolist!=null){
									offeringlist.dereference();
									offeringlist = wwseolist;
								}else{
									// there was a VE def error
									continue;
								}
							}
						}else{
							addDebug(D.EBUG_WARN,"using same ve "+curVeName); 
							if((!impactedType.equals(prevType)) && // type changed
									("WWSEO".equals(impactedType) || "WWSEO".equals(prevType))){ // one was WWSEO
								if("WWSEO".equals(impactedType)){ // now wwseo
									addDebug(D.EBUG_WARN,"types chgd, now wwseo impactedType "+impactedType+" prevType "+prevType);
									if(offeringlist.getEntityGroup("WWSEO")==null){ // some VEs dont go thru prodstruct
										// get wwseo ve
										EntityList wwseolist = getWWSEOList(offeringlist);
										if(wwseolist!=null){
											offeringlist.dereference();
											offeringlist = wwseolist;
										}else{
											// there was a VE def error
											continue;
										}
									}
								}else{ // was wwseo, pull again to get this ve
									addDebug(D.EBUG_WARN,"types chgd, was wwseo impactedType "+impactedType+" prevType "+prevType);
									if(!offeringlist.getParentActionItem().getActionItemKey().equals(VeName)){
										offeringlist.dereference();
										//Use the Virtual Entity “VENAME” (ECSVENAME) to extract the structure of interest. 
										offeringlist = m_db.getEntityList(m_prof,
												new ExtractActionItem(null, m_db, m_prof, VeName),
												new EntityItem[] {rootEntity});
										addDebug(D.EBUG_ERR,"Extract "+VeName+NEWLINE+PokUtils.outputList(offeringlist));
									}else{
										addDebug(D.EBUG_WARN,"types chgd, ve was not updated");			
									}
								}
							}
						}
						
						prevType = impactedType;

						//The entity type that may be impacted is identified via attribute “Entity Type Impacted” (ECSIMPACTET)
						//and is found in the structure obtained using this VE. The VE will be such that there is only one path 
						//from ECSCHGET to ECSIMPACTET and hence the code may simply look for the “Entity Type Impacted” within 
						// the structure. There may be multiple instances of this entity type, all of which are impacted.
						EntityGroup impactedGrp = offeringlist.getEntityGroup(impactedType);
						if(impactedGrp!=null){
							// check if it is in the meta.. user may have mixed impacttype and attr to queue
							// if meta does not have this attribute it is an error
							EANMetaAttribute metaAttr = impactedGrp.getMetaAttribute(setAttr);
							if (metaAttr==null) {
								addDebug(D.EBUG_ERR,setAttr+" was not in meta for "+impactedGrp.getEntityType());
								//ATTR_ERR = {0} was not found in meta for {1}
								args[0] = setAttr;
								args[1] = impactedGrp.getEntityType();
								addError("ATTR_ERR",args);
								continue;
							}
							// do this here to get message if nothing is queued
							String qkey = metaAttr.getActualLongDescription();
							Vector codevct = (Vector)queuedTbl.get(qkey);
							if(codevct == null){
								codevct = new Vector();
								queuedTbl.put(qkey,codevct);
							}
							for(int g=0; g<impactedGrp.getEntityItemCount(); g++){
								EntityItem impactedItem = impactedGrp.getEntityItem(g);
								if(!checkABRStatus(impactedItem,setAttr)){
									//skipped because of abr status
									skippedABRVct.add(impactedItem.getKey());
									continue;
								}

								// check lifecycle and status
								if(!checkStatus(impactedItem)){
									//skipped because of status
									skippedStatusVct.add(impactedItem.getKey());
									continue;
								}
								// check withdrawn
								if(!checkDates(impactedItem)){
									//skipped because of dates
									withdrawnVct.add(impactedItem.getKey());
									continue;
								}

								//all criteria are met
								queueABR(impactedItem, setAttr);	
								
							} // end impacted group
						}//end group not null
						else{
							//VEDEF_ERR = {0} was not found in the {1} extract.
							args[0] = impactedType;
							args[1] = VeName;
							addError("VEDEF_ERR",args);
						}
					}   // end active loop

					// output summary info for prevtype
					if(prevType!=null){
						outputSummary(prevType,ecdNavName);
					}

					if(getReturnCode()==PASS){
						updatePDH();
					}else{
						addDebug(D.EBUG_ERR,"Errors occured. PDH was not updated");
						summarySb.setLength(0);
					}

					if(offeringlist!=null){
						offeringlist.dereference(); 
						offeringlist = null;
					}

					addDebug(D.EBUG_SPEW,"Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
					completedTypeSet.clear();
					activeVct.clear();
					queuedTbl.clear();
					skippedStatusVct.clear();
					withdrawnVct.clear();
					skippedABRVct.clear();
				}
			}// activevct != null
		}
		catch(Throwable exc) {
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE="<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
		}
		finally
		{
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if(!isReadOnly())
			{
				clearSoftLock();
			}
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getDescription();
		args[1] = rootDesc;
		String header1 = msgf.format(args);
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		args[4] = rootDesc;
		args[5] = ""+m_abri.getEntityID();
		args[6] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
		args[7] = abrversion+" "+getABRVersion();

		rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

		rptSb.append(summarySb.toString());

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		metaTbl.clear();
	}

	/**
	 * get to WWSEO from PRODSTRUCTs
	 * @param offeringlist
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private EntityList getWWSEOList(EntityList offeringlist) 
	throws MiddlewareRequestException, SQLException, MiddlewareException
	{
		EntityGroup psGrp = offeringlist.getEntityGroup("PRODSTRUCT");
		if(psGrp==null){
			//VEDEF2_ERR = {0} or {1} was not found in the {2} extract.  Cannot get to WWSEO.
			args[0] = "WWSEO";
			args[1] = "PRODSTRUCT";
			args[2] = offeringlist.getParentActionItem().getActionItemKey();
			addError("VEDEF2_ERR",args);
			return null;
		}
	
		EntityItem[] eia = null;
		if(psGrp.getEntityItemCount()>0){
			eia = psGrp.getEntityItemsAsArray();
		}else{
			// do this to get the structure
			eia = new EntityItem[]{ new EntityItem(null, m_prof, "PRODSTRUCT", 0) };
		}
		
		EntityList list = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db, m_prof, "XCDPRODSTRUCT"),
				eia);
		addDebug(D.EBUG_ERR,"getWWSEOList Extract XCDPRODSTRUCT"+NEWLINE+PokUtils.outputList(list));
		return list;
	}
	/**
	 * check status of ABR to be queued, it must be passed or inprocess
	 * @param impactedItem
	 * @param setAttr
	 * @return
	 */
	private boolean checkABRStatus(EntityItem impactedItem,String setAttr){
		String attrValue = PokUtils.getAttributeFlagValue(impactedItem, setAttr);
		addDebug(D.EBUG_SPEW,"checkABRStatus "+impactedItem.getKey()+" "+setAttr+" value "+attrValue);
		//If the following criteria is met
		return (ABR_PASSED.equals(attrValue) || ABR_INPROCESS.equals(attrValue));
	}

	/**
	 * 1.	Data Quality of the “Entity Type Impacted” (ECSIMPACTET) entity – either of the following:
	 * “Life Cycle” (LIFECYCLE) = "Develop" (LF02)  | "Plan" (LF01)
	 *            and
	 * "Status” (STATUS) = "Ready for Review" (0040) 
	 * OR
	 * “Status” (STATUS) = "Final" (0020)
	 * @param impactedItem
	 * @return
	 */
	private boolean checkStatus(EntityItem impactedItem){
		// check lifecycle and status
		String status = PokUtils.getAttributeFlagValue(impactedItem, "STATUS");
		String lifecycle = PokUtils.getAttributeFlagValue(impactedItem, "LIFECYCLE");
		addDebug(D.EBUG_WARN,"checkStatus "+impactedItem.getKey()+"  status "+status+" lifecycle "+lifecycle);
		if (lifecycle==null || lifecycle.length()==0){ 
			lifecycle = LIFECYCLE_Plan;
		}

		return (STATUS_FINAL.equals(status) || 
				(STATUS_R4REVIEW.equals(status) && 
						// first time moving to RFR or been RFR before
						(LIFECYCLE_Plan.equals(lifecycle) ||LIFECYCLE_Develop.equals(lifecycle))));
	}
	/**
	 * 2.	“Entity Type Impacted” (ECSIMPACTET) entity not withdrawn
	 * •	FEATURE - WITHDRAWDATEEFF_T  > NOW()
	 * •	MODEL - WTHDRWEFFCTVDATE > NOW()
	 * •	WWSEO MODELWWSEO-u: MODEL. WTHDRWEFFCTVDATE > NOW()
	 * @param impactedItem
	 * @return
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private boolean checkDates(EntityItem impactedItem) throws SQLException, MiddlewareException{
		// check dates
		String impactedType = impactedItem.getEntityType();
		if(impactedType.equals("MODEL")){
			//	MODEL - WTHDRWEFFCTVDATE > NOW()
			String wdDate = PokUtils.getAttributeValue(impactedItem, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
			addDebug(D.EBUG_WARN,"checkDates "+impactedItem.getKey()+" wdDate: "+wdDate+" now: "+strNow);
			return strNow.compareTo(wdDate)<0;
		}
		if(impactedType.equals("FEATURE")){
			//	FEATURE - WITHDRAWDATEEFF_T  > NOW()
			String wdDate = PokUtils.getAttributeValue(impactedItem, "WITHDRAWDATEEFF_T", "", FOREVER_DATE, false);
			addDebug(D.EBUG_WARN,"checkDates "+impactedItem.getKey()+" wdDate: "+wdDate+" now: "+strNow);
			return strNow.compareTo(wdDate)<0;
		}
		if(impactedType.equals("WWSEO")){
			Vector modelVct = PokUtils.getAllLinkedEntities(impactedItem, "MODELWWSEO", "MODEL");
			if(modelVct.size()==0){
				//MODEL_ERR = A Model was not found for {0}.
				args[0] = impactedItem.getEntityGroup().getLongDescription()+": "+this.getNavigationName(impactedItem);
				addError("MODEL_ERR",args);
			}
			for(int i=0; i<modelVct.size(); i++){
				//WWSEO MODELWWSEO-u: MODEL. WTHDRWEFFCTVDATE > NOW()
				EntityItem mdlitem = (EntityItem)modelVct.elementAt(i);// there should only be one
				String wdDate = PokUtils.getAttributeValue(mdlitem, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
				addDebug(D.EBUG_WARN,"checkDates "+mdlitem.getKey()+" wdDate: "+wdDate+" now: "+strNow);
				return strNow.compareTo(wdDate)<0;
			}
		}
		return false;
	}
	/**
	 * A summary of data impacted should be produced. This summary show by impacted entity type a count of affected entities and counts for entities skipped because they are withdrawn and skipped due to status.

For example (similar or alternative formatting is acceptable):
•	Model
o	Queued	456
o	Withdrawn	247
o	Status		123
•	FEATURE
o	Queued	57
o	Withdrawn	25
o	Status		12
•	WWSEO
o	Queued	654
o	Withdrawn	150
o	Status		154

	 * @return
	 */
	private void outputSummary(String impactedType, String ecdNavName){
		summarySb.append("<br /><h2>"+ecdNavName+"</h2>"+NEWLINE);
		summarySb.append("<h3>"+impactedType+":</h3>"+NEWLINE);
		summarySb.append("<table width='350'>"+NEWLINE);
		summarySb.append("<colgroup><col width=\"5%\"><col width=\"60%\"/><col width=\"35%\"/></colgroup>"+NEWLINE);

		String attrdesc ="";
		for(Enumeration e = queuedTbl.keys(); e.hasMoreElements();) {
			attrdesc = (String) e.nextElement();
			Vector codevct = (Vector)queuedTbl.get(attrdesc);
			//QUEUED_MSG = Queued {0}:
			String msg = rsBundle.getString("QUEUED_MSG");
			MessageFormat msgf = new MessageFormat(msg);
			args[0]=attrdesc;
			msg = msgf.format(args);
	
			summarySb.append("<tr><td colspan='2'>"+msg+"</td><td>"+codevct.size()+"</td></tr>"+NEWLINE);
			codevct.clear();
		}
	
		summarySb.append("<tr><td colspan='3'>"+NEWLINE);
		//NOT_PROC_MSG = Not processed due to the following:
		String msg = rsBundle.getString("NOT_PROC_MSG");
		summarySb.append("<span class=\"orange-med\"><b>"+msg+"</b></span></td></tr>"+NEWLINE);
		//STATUS_MSG= Status:
		msg = rsBundle.getString("STATUS_MSG");
		summarySb.append("<tr><td>&nbsp;</td><td>"+msg+"</td><td>"+skippedStatusVct.size()+"</td></tr>"+NEWLINE);
		//WITHDRAWN_MSG = Withdrawn:
		msg = rsBundle.getString("WITHDRAWN_MSG");
		summarySb.append("<tr><td>&nbsp;</td><td>"+msg+"</td><td>"+withdrawnVct.size()+"</td></tr>"+NEWLINE);
		summarySb.append("<tr><td>&nbsp;</td><td>"+attrdesc+":</td><td>"+skippedABRVct.size()+"</td></tr>"+NEWLINE);

		summarySb.append("</table>"+NEWLINE);
		queuedTbl.clear();
		skippedStatusVct.clear();
		withdrawnVct.clear();
		skippedABRVct.clear();
	}

	/*************************************
	 * The ABR will be invoked for a single instance of one of these entity types. 
	 * The ABR will look up the entity type in ELEMENTCATDATA 
	 * using attribute code “Changed” (ECSCHGET). If the attribute “Active” (ECSACTIVE) is set to “InActive”, do not proceed.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private Vector searchForELEMENTCATDATA(String elemType) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem eia[]= null;
		Vector eiVct = new Vector(1);
		Vector valVct = new Vector(2);
		Vector attrVct = new Vector(2);

		attrVct.addElement("ECSCHGET");
		attrVct.addElement("ECSACTIVE");

		String elemFlag = null;
		
		PDGUtility pdgutil = new PDGUtility();
		// find flag code corresponding to the ECSCHGET desc
		String[] flagArray = pdgutil.getFlagCodeForExactDesc(m_db, m_prof, "ECSCHGET", elemType);
		pdgutil.dereference();
		if (flagArray != null && flagArray.length > 0) {
			elemFlag = flagArray[0];
			addDebug(D.EBUG_INFO,"searchForELEMENTCATDATA ECSCHGET desc : "+elemType+" flagcode "+elemFlag);
		}else{
			addDebug(D.EBUG_ERR,"searchForELEMENTCATDATA NO match found for ECSCHGET desc : "+elemType);
			//METADEF_ERR = A corresponding Flag code was not found for {0} {1}
			args[0] = "ECSCHGET";
			args[1] = elemType;
			addError("METADEF_ERR",args);
			return null;
		}

		valVct.addElement(elemFlag); // this needs a flag value, not the flag desc
		valVct.addElement(ECSACTIVE_Active); 
		addDebug(D.EBUG_INFO,"searchForELEMENTCATDATA attrVct "+attrVct+" valVct "+valVct);
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					ELEMENTCATDATA_SRCHACTION_NAME, "ELEMENTCATDATA", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(D.EBUG_INFO,debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug(D.EBUG_WARN,"searchForELEMENTCATDATA SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug(D.EBUG_INFO,"searchForELEMENTCATDATA found "+eia[i].getKey());
				eiVct.add(eia[i]);
				eia[i] = null;
			}
		}

		attrVct.clear();
		valVct.clear();

		attrVct=null;
		valVct=null;

		return eiVct;
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 */
	private void updatePDH()
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug(D.EBUG_ERR,"updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys.size());

		if(vctReturnsEntityKeys.size()>0) {
			try {
				m_db.update(m_prof, vctReturnsEntityKeys, false, false);
			}
			finally {
				vctReturnsEntityKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}

	/***********************************************	
	 * Then the ABR for “Entity Type Impacted” (ECSIMPACTET) should set “Set Attribute” (ECSSETATTR) to “Queued” 
	 * (0020) if the current value is “Passed” (0030).
	 * 
	 * If “Set Attribute” (ECSSETATTR) is “In Process” (0050), then wait for 5 minutes and check again. 
	 * This step should be repeated for a maximum of 5 times after which, simply exit. For all other values of 
	 * “ABR Advance Status”, simply exit.
	 * @param item
	 * @param strAttributeCode
	 */
	private void queueABR(EntityItem item, String strAttributeCode)
	{
		logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" set to: " + ABR_QUEUED);
		addDebug(D.EBUG_INFO,"queueABR entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
				ABR_QUEUED);

		EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(strAttributeCode);
		
		// make sure it isnt 'in process' now
		// if the specified abr is inprocess, wait
		if(!checkForInProcess(item,strAttributeCode)){
			// abr failed or timed out
			skippedABRVct.add(item);
			return;
		}

		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		Vector vctAtts = null;
		// look at each key to see if root is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == item.getEntityID() &&
					rek.getEntityType().equals(item.getEntityType())){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}

		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), item.getEntityType(), item.getEntityID(),
				strAttributeCode, ABR_QUEUED, 1, m_cbOn);

		// look at each attr to see if this is there yet
		for (int i=0; i<vctAtts.size(); i++){
			Attribute attr = (Attribute)vctAtts.elementAt(i);
			if (attr.getAttributeCode().equals(strAttributeCode)){
				sf = null;
				break;
			}
		}
		if(sf != null){
			vctAtts.addElement(sf);
			String key = metaAttr.getActualLongDescription();
			Vector codevct = (Vector)queuedTbl.get(key);
			codevct.add(item);
		}else{
			addDebug(D.EBUG_INFO,"queueABR:  "+item.getKey()+" "+strAttributeCode+" was already added for updates ");
		}
	}

	/**
	 * if the specified abr is inprocess, wait
	 * If “ABR Advance Status” is “In Process” (0050), then wait for 5 minutes and check again. 
	 * This step should be repeated for a maximum of 5 times after which, simply exit. For all other values of 
	 * “ABR Advance Status”, simply exit.
	 * 
	 * @param item
	 * @param strAttributeCode
	 */
	private boolean checkForInProcess(EntityItem item,String strAttributeCode){
		boolean passed = true;
		try{
			int maxTries = 0;
			String curval = // doesnt work on 'A' type attr getAttributeFlagEnabledValue(item,strAttributeCode);
				PokUtils.getAttributeFlagValue(item,strAttributeCode);

			addDebug(D.EBUG_WARN,"checkForInProcess:  entered "+item.getKey()+" "+strAttributeCode+" is "+curval);
			
			if (ABR_INPROCESS.equals(curval)){
				logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" INPROCESS");
				DatePackage dpNow = m_db.getDates();
				// get current updates by setting to endofday
				m_prof.setValOnEffOn(dpNow.getEndOfDay(),dpNow.getEndOfDay());

				if(hasWaitedBefore){
					// get entity again- the previous wait may have cleared this value
					EntityGroup eg = new EntityGroup(null,m_db, m_prof, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, m_prof, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem,strAttributeCode);
					addDebug(D.EBUG_WARN,"checkForInProcess: haswaitedbefore "+strAttributeCode+" is now "+curval);
					eg.dereference();
				}
				hasWaitedBefore = true;
				while(ABR_INPROCESS.equals(curval) && maxTries<5){ // allow 25 minutes
					maxTries++;
					addDebug(D.EBUG_WARN,"checkForInProcess: "+strAttributeCode+" is "+curval+" sleeping 5 mins");
					logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" "+curval+" sleeping 5 mins");
					Thread.sleep(300000); //sleep 5 minutes
					// get entity again
					EntityGroup eg = new EntityGroup(null,m_db, m_prof, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, m_prof, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem,strAttributeCode);
					addDebug(D.EBUG_WARN,"checkForInProcess: valon "+m_prof.getValOn()+" "+strAttributeCode+" is now "+curval+" after sleeping");
					logMessage(getDescription()+" ***** valon "+m_prof.getValOn()+" "+item.getKey()+" "+strAttributeCode+" is now "+curval+" after sleeping");
					dpNow = m_db.getDates();
					// get current updates by setting to endofday - do this just in case crossed midnight
					m_prof.setValOnEffOn(dpNow.getEndOfDay(),dpNow.getEndOfDay());
					eg.dereference();
				}
				passed = ABR_PASSED.equals(curval);
			}
		}catch(Exception exc){
			System.err.println("Exception in checkForInProcess "+exc);
			exc.printStackTrace();
		}
		return passed;
	}
	/******
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){
		super.dereference();

		rsBundle = null;
		rptSb = null;
		args = null;

		metaTbl = null;
		navName = null;
		vctReturnsEntityKeys=null;
		skippedStatusVct.clear();
		skippedStatusVct = null;
		queuedTbl.clear();
		queuedTbl = null;
		withdrawnVct.clear();
		withdrawnVct = null;
	}

	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************
	 * support conditional msgs
	 * @param level
	 * @param msg
	 */
	private void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}

	/**********************************
	 * used for error output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	private void addError(String errCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(rsBundle.getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	} 

	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[])
	{
		String msg = rsBundle.getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();

		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)	{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++){
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}

		return navName.toString().trim();
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.2 $";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "ELEMENTABRSTATUS";
	}

}
