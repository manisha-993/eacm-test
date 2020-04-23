//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.wave2;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**********************************************************************************
 *
 The ABR will use the Role "SYSFEEDADMIN" which identifies the subset of attributes that are of interest.

 If SYSFEEDRESEND = 'Yes' (Yes) THEN
 If Status (STATUS) = 'Ready for Review' (0040) or 'Final' THEN
 T1 = �980-01-01�
 ELSE
 Set SYSFEEDRESEND = 'No' (No)
 ErrorMessage LD(of the Root Entity Type) NDN(of the Root Entity) 'was queued to resend data; however,
 	the data is not Ready for Review or Final'.
 Done
 END IF
 ELSE
 If this is the first time that STATUS (STATUS) = 'Ready for Review' (0040) or 'Final' (0020) THEN
 T1 = �980-01-01�
 ELSE
 T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of Queued prior to the last value of Success.
 END IF
 END IF

 B.	XML

 The Virtual Entity (VE) extract will be performed two times:
 1.	T1 is the DTS determined by the prior section
 2.	T2 is the Date/Time Stamp (DTS) that the ABR was Queued (0020).



actionTaken
ACTION_R4R_FIRSTTIME = Ready for Review - First Time
ACTION_R4R_CHANGES = Ready for Review - Changes
ACTION_R4R_RESEND = Ready for Review - Resend
ACTION_FINAL_FIRSTTIME = Final - First Time
ACTION_FINAL_CHANGES = Final - Changes
ACTION_FINAL_RESEND = Final - Resend
 *
 */
//ADSABRSTATUS.java,v
//ADSABRSTATUS.java,v
//ADSABRSTATUS.java,v
//Revision 1.12  2010/01/07 17:58:36  wendy
//cvs failure again
//
//Revision 1.10  2009/12/08 12:18:54  wendy
//cvs failure - restore version and logging
//
//Revision 1.8  2009/08/18 19:31:44  wendy
//Allow extractaction to cleanup
//
//Revision 1.7  2008/05/28 13:51:25  wendy
//put mqcid into hashtable for mq usage
//
//Revision 1.6  2008/05/27 14:28:58  wendy
//Clean up RSA warnings
//
//Revision 1.5  2008/05/03 23:29:54  wendy
//Changed to support generation of large XML files
//
//Revision 1.4  2008/05/01 18:10:16  wendy
//Fix error msgs
//
//Revision 1.3  2008/05/01 13:58:38  wendy
//output current and prior status in systemresend
//
//Revision 1.2  2008/05/01 12:12:14  wendy
//Allow access to convertToHTML() from derived class
//
//Revision 1.1  2008/04/29 14:34:18  wendy
//Init for
// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//

public class ADSABRSTATUS extends PokBaseABR
{
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);

	protected final static String ADSMQSERIES = "ADSMQSERIES";
    protected static final String SYSFEEDRESEND_YES = "Yes";
    protected static final String SYSFEEDRESEND_NO = "No";

	private StringBuffer xmlgenSb = new StringBuffer();
	private PrintWriter dbgPw=null;
	private PrintWriter userxmlPw=null;
	private String dbgfn = null;
	private String userxmlfn = null;
	private int userxmlLen=0;
	private int dbgLen=0;
	private static final int MAXFILE_SIZE=5000000;

	protected static final String STATUS_DRAFT = "0010";
	protected static final String STATUS_FINAL = "0020";
	protected static final String STATUS_R4REVIEW = "0040";
	protected static final String STATUS_CHGREQ = "0050";
        // ADSABRSTATUS Queued = 0020, Passed = 0030
	protected static final String STATUS_QUEUE = "0020";
        protected static final String STATUS_PASSED = "0030";
        protected static final String CHEAT = "@@";

	// VE_Filter_Array contains the filtering which is applied.
	// The first column contains the VE name.
	// The second column contains the entity subject to filtering.
	// The third column contains the filtering instruction when the root entity is FINAL.
	// IF 'RFR Final' then RFR AND FINAL are allowed (ie - NOT filtered).
	// If 'Final' then only FINAL is allowed. 
	protected static final String[][] VE_Filter_Array = 
	{
		{"ADSLSEO","AVAIL","RFR Final"},
		{"ADSLSEO","ANNOUNCEMENT","RFR Final"},
		{"ADSMODEL","AVAIL","RFR Final"},
		{"ADSMODEL","ANNOUNCEMENT","RFR Final"},
		{"ADSMODEL","IMG","Final"},
		{"ADSMODEL","MM","Final"},
		{"ADSSVCMOD","AVAIL","RFR Final"},
		{"ADSSVCMOD","ANNOUNCEMENT","RFR Final"}
	};

	private ResourceBundle rsBundle = null;
	private String priorStatus = "&nbsp;";
	private String curStatus = "&nbsp;";
	private boolean isPeriodicABR = false;
	private boolean isSystemResend = false;
        // when Status goes to RFR, but it has been Final before,it is True; Status never been Final, it is False. 
	private boolean RFRPassedFinal = false;
	private String actionTaken="";

	private static final Hashtable READ_LANGS_TBL;  // tbl of NLSitems, 1 for each profile language
	private static final Hashtable ABR_TBL;
	protected static final Hashtable ADSTYPES_TBL;
	// ITEM_STATUS_ATTR_TBL contains the status attribute codes for entities which are 
	// subject to filtering
	protected static final Hashtable ITEM_STATUS_ATTR_TBL;

        // DQ abr table copy from DQABRSTATUS
	private static final Hashtable ABR_ATTR_TBL;
	
	protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
	static{
		ABR_TBL = new Hashtable();
		ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.wave2.ADSMODELABR");
//		ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.sg.ADSFEATUREABR");
//		ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.sg.ADSSWFEATUREABR");
//		ABR_TBL.put("SVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSSVCFEATUREABR");
//		ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSFCTRANSABR");
//		ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSMODELCONVERTABR");
//		ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSPRODSTRUCTABR");
//		ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSWPRODSTRUCTABR");
//		ABR_TBL.put("SVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSVCPRODSTRUCTABR");
		ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.wave2.ADSLSEOABR");
                ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.wave2.ADSSVCMODABR");
//              ABR_TBL.put("SLEORGNPLNTCODE", "COM.ibm.eannounce.abr.sg.wave2.ADSSLEORGNPLNTCODEABR");
//		ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.sg.ADSCATNAVABR");
//		ABR_TBL.put("XLATE", "COM.ibm.eannounce.abr.sg.ADSXLATEABR");
//		ABR_TBL.put("MODELCG", "COM.ibm.eannounce.abr.sg.ADSWWCOMPATABR"); // MODELCG is root for WWCOMPAT ve
		ABR_TBL.put("GENAREA", "COM.ibm.eannounce.abr.sg.wave2.ADSGENAREAABR");

//		ABR_TBL.put("DELFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELFEATUREABR");
//		ABR_TBL.put("DELFCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSDELFCTRANSABR");
//		ABR_TBL.put("DELMODEL", "COM.ibm.eannounce.abr.sg.ADSDELMODELABR");
//		ABR_TBL.put("DELMODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSDELMODELCONVERTABR");
//		ABR_TBL.put("DELPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELPRODSTRUCTABR");
//		ABR_TBL.put("DELSVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSVCFEATUREABR");
//		ABR_TBL.put("DELSVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSVCPRODSTRUCTABR");
//		ABR_TBL.put("DELSWFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSWFEATUREABR");
//		ABR_TBL.put("DELSWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSWPRODSTRUCTABR");

		READ_LANGS_TBL = new Hashtable();
		// fill in with all languages defined in profile, actual languages used is based on properties file
		READ_LANGS_TBL.put(""+Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
		READ_LANGS_TBL.put(""+Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);

		ADSTYPES_TBL = new Hashtable();
		ADSTYPES_TBL.put("10","CATNAV");
		ADSTYPES_TBL.put("100","SVCPRODSTRUCT");
		ADSTYPES_TBL.put("110","SWFEATURE");
		ADSTYPES_TBL.put("120","SWFPRODSTRUCT");
		ADSTYPES_TBL.put("130","MODELCG"); // MODELCG root for WWCOMPAT ve
		ADSTYPES_TBL.put("140","LSEO");
        ADSTYPES_TBL.put("150","SVCMOD");
		ADSTYPES_TBL.put("20","GENAREA");
		ADSTYPES_TBL.put("30","FEATURE");
		ADSTYPES_TBL.put("40","FCTRANSACTION");
		ADSTYPES_TBL.put("50","XLATE"); // flags
		ADSTYPES_TBL.put("60","MODEL");
		ADSTYPES_TBL.put("70","MODELCONVERT");
		ADSTYPES_TBL.put("80","PRODSTRUCT");
		ADSTYPES_TBL.put("90","SVCFEATURE");
	}
	
	static {
		ITEM_STATUS_ATTR_TBL = new Hashtable();
		ITEM_STATUS_ATTR_TBL.put("AVAIL","STATUS");
		ITEM_STATUS_ATTR_TBL.put("ANNOUNCEMENT","ANNSTATUS");
		ITEM_STATUS_ATTR_TBL.put("IMG","STATUS");
		ITEM_STATUS_ATTR_TBL.put("MM","MMSTATUS");		
	}
        static {
		ABR_ATTR_TBL = new Hashtable(); // needed when looking up values to queue on other entities
		ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
		ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
		ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
		ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
		ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
		ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
		ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
		ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
		ABR_ATTR_TBL.put("IPSCFEAT", "IPSCFEATABRSTATUS");
		ABR_ATTR_TBL.put("IPSCSTRUC", "IPSCSTRUCABRSTATUS");
		ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
		ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
		ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
		ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
		ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
	}

	private Object[] args = new String[10];
        private String abrversion="";
        private String t2DTS = "&nbsp;";  // T2
        private String t1DTS = "&nbsp;";   // T1
	private StringBuffer userxmlSb= new StringBuffer(); // output in the report, not value sent to MQ - diff transform used

/*
ADSTYPE	10	CATNAV
ADSTYPE	100	Service Product Structure
ADSTYPE	110	Software Feature
ADSTYPE	120	Software Product Structure
ADSTYPE	130	WWCOMPAT
ADSTYPE 140     Lseo
ADSTYPE 150     Svcmod
ADSTYPE	20	GENAREA
ADSTYPE	30	Feature
ADSTYPE	40	Feature Transaction
ADSTYPE	50	Flags
ADSTYPE	60	Model
ADSTYPE	70	Model Conversion
ADSTYPE	80	Product Structure
ADSTYPE	90	Service Feature

ADSENTITY	10	CATNAV
ADSENTITY	20	Deletes
ADSENTITY	30	NA

ADSATTRIBUTE	10	NA
ADSATTRIBUTE	20	OSLEVEL
ADSATTRIBUTE	30	WARRPRIOD
ADSATTRIBUTE	40	WARRTYPE

*/

	protected String getSimpleABRName(String type)
	{
		// find class to instantiate based on entitytype
		// Load the specified ABR class in preparation for execution
		String clsname = (String) ABR_TBL.get(type);
		addDebug("creating instance of ADSABR  = '" + clsname + "' for "+type);
		return clsname;
	}

	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run()
	{
		String navName = "";
		// must split because too many arguments for messageformat, max of 10.. this was 11
		String HEADER = "<head>"+
		EACustom.getMetaTags(getDescription()) + NEWLINE +
		EACustom.getCSS() + NEWLINE +
		EACustom.getTitle("{0} {1}") + NEWLINE +
		"</head>" + NEWLINE + "<body id=\"ibm-com\">" +
		EACustom.getMastheadDiv() + NEWLINE +
		"<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;

		MessageFormat msgf;

		println(EACustom.getDocTypeHtml()); //Output the doctype and html
		try
		{
			long startTime = System.currentTimeMillis();

			start_ABRBuild(false); // dont pull VE yet

			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

			//Default set to pass
			setReturnCode(PASS);

                        //Default set to false
			//RFRPassedFinal = false;

			//get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof,
					new ExtractActionItem(null, m_db, m_prof,"dummy"),
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

			try{
				// get root from VE
				EntityItem rootEntity  = m_elist.getParentEntityGroup().getEntityItem(0);

				isPeriodicABR = getEntityType().equals("ADSXMLSETUP");

				String etype = getEntityType();
				//int eid = getEntityID();
				String ADSTYPE = PokUtils.getAttributeFlagValue(rootEntity, "ADSTYPE");
				String ADSENTITY = PokUtils.getAttributeFlagValue(rootEntity, "ADSENTITY");

				if (isPeriodicABR){
					// look at ADSTYPE, ADSENTITY
					if (ADSTYPE != null){
						etype = (String)ADSTYPES_TBL.get(ADSTYPE);
					}
					if ("20".equals(ADSENTITY)){ // this is deletes
						etype = "DEL"+etype;
					}
				}

				// find class to instantiate based on entitytype
				// Load the specified ABR class in preparation for execution
				String clsname = getSimpleABRName(etype);
  				/**
  				 * For Release 0.5 only, a temporary filter needs to be supported. This filter should exclude SVCMOD 
  				 * where SVCMODSUBCATG = “Productized Service” (SCSC0004)
  				 */
				boolean isExcludeSVCMOD = checkSVCMOD(rootEntity);
				if (clsname!=null && isExcludeSVCMOD==false){					
					
					boolean shouldExecute = true;
					XMLMQ mqAbr = (XMLMQ) Class.forName(clsname).newInstance();

					abrversion = getShortClassName(mqAbr.getClass())+" "+mqAbr.getVersion();

					if (!isPeriodicABR){
						String statusAttr = mqAbr.getStatusAttr();
						String sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
						String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
						isSystemResend = SYSFEEDRESEND_YES.equals(sysfeedFlag);
						addDebug("execute: "+rootEntity.getKey()+" "+statusAttr+": "+
							PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false)+" ["+statusFlag+"] sysfeedFlag: "+
							sysfeedFlag);

						if (isSystemResend){
							// get _SYSFeedResendValue from abr.server.properties
							String sysResendStatus= sysFeedResendStatus(m_abri.getABRCode(),SYSFeedResendValue,"Both");
                                                        // if _SYSFeedResendValue is equal to Final, then resend status must be Final.
							if(STATUS_FINAL.equals(sysResendStatus)){
								if (STATUS_FINAL.equals(statusFlag)){
								    actionTaken = rsBundle.getString("ACTION_FINAL_RESEND");
								}else{
									addDebug(rootEntity.getKey()+" is not Final");
									//RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
									addError(rsBundle.getString("RESEND_ONLY_FINAL"));								
								}
							}else if(STATUS_R4REVIEW.equals(sysResendStatus)){
								if (STATUS_R4REVIEW.equals(statusFlag)){
//                             						get ADSABRSTATUS changed history group
									AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory();
									//get STATUS changed history group
									AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
									// if Status is RFR, and has Passed Final before, then exit with Error.            
									if (existPassedFinal(adsStatusHistoy,statusHistory)){
										addDebug(rootEntity.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
										addError(rsBundle.getString("RESEND_R4R_PASSEDFINAL"));
									}else{
									actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
                                                                        }
								}else{
									addDebug(rootEntity.getKey()+" is not RFR");
									//RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
									addError(rsBundle.getString("RESEND_ONLY_R4REVIEW"));								
								}
							// both Final and R4R trigger resend data	
							}else{
								 if (!STATUS_FINAL.equals(statusFlag)&& !STATUS_R4REVIEW.equals(statusFlag)){
										addDebug(rootEntity.getKey()+" is not Final or R4R");
										//RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
										addError(rsBundle.getString("RESEND_NOT_R4RFINAL"));
									}else{
										if (STATUS_FINAL.equals(statusFlag)){
											actionTaken = rsBundle.getString("ACTION_FINAL_RESEND");
										}else{
                                                                                //get ADSABRSTATUS changed history group
										AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory();
										//get STATUS changed history group
										AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
										if (existPassedFinal(adsStatusHistoy,statusHistory)){
											addDebug(rootEntity.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
											addError(rsBundle.getString("RESEND_R4R_PASSEDFINAL"));
										}else{
											actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
										}
                                                                        							
									}
									}		
							}			
							// put values into the status and priorstatus fields
							curStatus = PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false);
							priorStatus = curStatus;
						}else{
							if (!STATUS_FINAL.equals(statusFlag)&& !STATUS_R4REVIEW.equals(statusFlag)){
								addDebug(rootEntity.getKey()+" is not Final or R4R");
								//NOT_R4RFINAL = Status is not Ready for Review or Final.
								addError(rsBundle.getString("NOT_R4RFINAL"));
							}else{
								shouldExecute = mqAbr.createXML(rootEntity);
								if (!shouldExecute){
									addDebug(rootEntity.getKey()+" will not have XML generated, createXML=false");
								}
							}
						}
					}else{
						addDebug("execute: periodic "+rootEntity.getKey());
					}
                                        //get ADSABRSTATUS changed history group
					AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory();
					//get STATUS changed history group
					AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
					//get &DTFS from properties file					
					String dtfs = getDTFS(rootEntity, mqAbr);
					setT2DTS(adsStatusHistoy, dtfs); // T2 timestamp
					setT1DTS(mqAbr, adsStatusHistoy, statusHistory, dtfs); // T1 timestamp

					if (getReturnCode()==PASS && shouldExecute){
						
						// switch the role and use the time2 DTS (current change)
						Profile profileT2 = switchRole(mqAbr.getRoleCode());
						if (profileT2!=null){
							profileT2.setValOnEffOn(t2DTS, t2DTS);
							profileT2.setEndOfDay(t2DTS); // used for notification time
							profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english

							Profile profileT1 = profileT2.getNewInstance(m_db);
							profileT1.setValOnEffOn(t1DTS, t1DTS);
							profileT1.setEndOfDay(t2DTS); // used for notification time
							profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english

							String errmsg = "";
							try{
								if (isPeriodicABR){
									// look at ADSTYPE, ADSENTITY and ADSATTRIBUTE
									String typeToChk = "";
									if (ADSTYPE != null){
										typeToChk = (String)ADSTYPES_TBL.get(ADSTYPE);
									}
									errmsg = "Periodic "+typeToChk;
									if ("20".equals(ADSENTITY)){ // this is deletes
										errmsg = "Deleted "+typeToChk;
									}
									setupPrintWriters();
									mqAbr.processThis(this, profileT1, profileT2, rootEntity);
								}else{ // queued from DQ ABRs
									errmsg = rootEntity.getKey();
									// check if pdhdomain is in domain list for this entity
									if (domainNeedsChecks(rootEntity)){
                                                                                if (!RFRPassedFinal) {
										mqAbr.processThis(this, profileT1, profileT2, rootEntity);
                                                                                }
									}else{
										//DOMAIN_NOT_LISTED = Domain was not in the list of supported Domains. Execution bypassed for {0}.
										addXMLGenMsg("DOMAIN_NOT_LISTED",errmsg);
									}
								}
							}catch(IOException ioe){
								// only get this if a required node was not populated
								//REQ_ERROR = Error: {0}
								msgf = new MessageFormat(rsBundle.getString("REQ_ERROR"));
								args[0] = ioe.getMessage();
								addError(msgf.format(args));
								addXMLGenMsg("FAILED", errmsg);
							}catch(java.sql.SQLException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}catch(ParserConfigurationException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}catch(javax.xml.transform.TransformerException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}catch(java.util.MissingResourceException x){
								addXMLGenMsg("FAILED", errmsg);
								throw x;
							}
						}
					}
				}else{
					if(isExcludeSVCMOD){
						addError(getShortClassName(getClass())+" exclude SVCMOD where SVCMODCATG = “Productized Service” (SCSC0004) "+etype);
					}else{
						addError(getShortClassName(getClass())+" does not support "+etype);
					}
					
				}
				//NAME is navigate attributes
				navName = getNavigationName(rootEntity);

// fixme remove
//	setCreateDGEntity(false);

				// update lastran date
				if (isPeriodicABR && getReturnCode()==PASS) {
					PDGUtility pdgUtility = new PDGUtility();
					OPICMList attList = new OPICMList();
					attList.put("ADSDTS","ADSDTS=" + t2DTS);
					pdgUtility.updateAttribute(m_db, m_prof, rootEntity, attList);
				}

				addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
			}catch(Exception e){
				throw e;
			}finally{
				if (isSystemResend){
					setFlagValue("SYSFEEDRESEND", SYSFEEDRESEND_NO); // reset the flag
				}
			}
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
			if(!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriters();
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		//
		String header2 = null;
		if (isPeriodicABR){
			header2 = buildPeriodicRptHeader();
			restoreXtraContent();
		}else{
			header2 = buildDQTriggeredRptHeader();
		}

		//XML_MSG= XML Message
		String info = header1+header2+"<pre>"+
			rsBundle.getString("XML_MSG")+"<br />"+
			userxmlSb.toString()+"</pre>" + NEWLINE;
		rptSb.insert(0,info);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

    private boolean checkSVCMOD(EntityItem rootEntity) {
    	boolean isExcludeSVCMOD =false;
    	String attributevalue ="";
    	String entitytype = rootEntity.getEntityType();
    	if(entitytype.equals("SVCMOD")){
    		attributevalue = PokUtils.getAttributeFlagValue(rootEntity, "SVCMODSUBCATG");
    		if(attributevalue.equals("SCSC0004")){
    			isExcludeSVCMOD = true;
    		}
    	}
		return isExcludeSVCMOD;
	}

			/**
	 * @param rootEntity
	 * @param mqAbr
	 * Return &DTFS
	 */
	private String getDTFS(EntityItem rootEntity, XMLMQ mqAbr) {
		String statusAttr = mqAbr.getStatusAttr();
		String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
		String queuedvalue = "";
		if (STATUS_FINAL.equals(statusFlag)) {
			queuedvalue = getQueuedValue("ADSABRSTATUS");
			
		} else {
			queuedvalue =  getRFRQueuedValue("ADSABRSTATUS");
		}
		addDebug("getDTFS " + getEntityType() + statusFlag + " from properties file is " + queuedvalue);
		return queuedvalue;
	}

	private void setupPrintWriters(){
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0,extid+1)+"dbg";
		userxmlfn = fn.substring(0,extid+1)+"userxml";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
		try {
			userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userxmlfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating xmlgen PrintWriter " + x);
		}
	}
    private void closePrintWriters() {
		if (dbgPw != null){
        	dbgPw.flush();
        	dbgPw.close();
        	dbgPw = null;
		}
		if (userxmlPw != null){
        	userxmlPw.flush();
        	userxmlPw.close();
        	userxmlPw = null;
		}
    }

	private void restoreXtraContent(){
		// if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
		if (userxmlLen+rptSb.length()<MAXFILE_SIZE){
			// read the file in and put into the stringbuffer
			InputStream is = null;
			FileInputStream fis = null;
			BufferedReader rdr = null;
			try{
				fis = new FileInputStream(userxmlfn);
				is = new BufferedInputStream(fis);

				String s=null;
				rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// append lines until done
				while((s=rdr.readLine()) !=null){
					userxmlSb.append(convertToHTML(s)+NEWLINE);
				}
				// remove the file
				File f1 = new File(userxmlfn);
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
		}else{
			userxmlSb.append("XML generated was too large for this file");
		}
		// if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
		if (dbgLen+userxmlSb.length()+rptSb.length()<MAXFILE_SIZE){
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

	/******************************************
	* build xml generation msg
	*/
	protected void addXMLGenMsg(String rsrc, String info)
	{
		MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
		Object args[] = new Object[]{info};
		xmlgenSb.append(msgf.format(args)+"<br />");
	}

	/******************************************
	* A.	Data Quality Triggered ABRs
	*
	* The Report should identify:
	* -	USERID (USERTOKEN)
	* -	Role
	* -	Workgroup
	* -	Date/Time
	* -	Status
	* -	Prior feed Date/Time
	* -	Prior Status
	* -	Action Taken
	*/
	private String buildDQTriggeredRptHeader(){
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
             "<tr><th>Date/Time: </th><td>{3}</td></tr>"+NEWLINE +
             "<tr><th>Status: </th><td>{4}</td></tr>"+NEWLINE +
             "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>"+NEWLINE +
             "<tr><th>Prior Status: </th><td>{6}</td></tr>"+NEWLINE +
             "<tr><th>Action Taken: </th><td>{7}</td></tr>"+NEWLINE+
             "</table>"+NEWLINE+
            "<!-- {8} -->" + NEWLINE;
		MessageFormat msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = t2DTS;
		args[4] = curStatus;
		args[5] = t1DTS;
		args[6] = priorStatus;
        args[7] = actionTaken+"<br />"+xmlgenSb.toString();
        args[8] = abrversion+" "+getABRVersion();

        return msgf.format(args);
	}

	/******************************************
	* B.	Periodic ABRs
	*
	* The Report should identify:
	* -	Date/Time of this Run
	* -	Last Ran Date/Time Stamp
	* -	Action Taken
	*/
	private String buildPeriodicRptHeader(){
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Action Taken: </th><td>{2}</td></tr>"+NEWLINE+
             "</table>"+NEWLINE+
            "<!-- {3} -->" + NEWLINE;
		MessageFormat msgf = new MessageFormat(HEADER2);
		args[0] = t2DTS;
		args[1] = t1DTS;
        args[2] = xmlgenSb.toString();
        args[3] = abrversion+" "+getABRVersion();

        return msgf.format(args);
	}

	/******************************************
	* get entitylist used for compares
	*/
	protected EntityList getEntityListForDiff(Profile prof, String veName, EntityItem item) throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		ExtractActionItem xai = new ExtractActionItem(null, m_db, prof, veName);
		//xai.setSkipCleanup(true);
		EntityList list = m_db.getEntityList(prof, xai,
				new EntityItem[] { new EntityItem(null, prof, item.getEntityType(), item.getEntityID())});

		// debug display list of groups
		addDebug("EntityList for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
				PokUtils.outputList(list));
		
		// check if this VE is subject to status filtering
		if (isVEFiltered(veName)) {
		// Get the status for the root	
		  EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0); 
		  String root_status = PokUtils.getAttributeFlagValue(root_ei,"STATUS");
		  addDebug("The status of the root for VE " + veName + " is: "+ root_status);
		  
		  // For each entity type for the VE in VE_Filter_Array, get the group and 
		  // check the entities in the group for filtering.
		  for (int i=0; i<VE_Filter_Array.length; i++) {
			  addDebug("Looking at VE_filter_Array" + VE_Filter_Array[i][0]+" "
					  + VE_Filter_Array[i][1] + " " + VE_Filter_Array[i][2]); 
			  if (VE_Filter_Array[i][0].equals(veName)) {
				EntityGroup eg = list.getEntityGroup(VE_Filter_Array[i][1]);
				addDebug("Found "+ list.getEntityGroup(VE_Filter_Array[i][1]));
				// There should always be a group, unless the VE has been changed and the 
				// VE_Filter_Array has not been updated. 
			  if (eg != null){					  
				            
                // Load the EntityItem ojects into an array for processing.
				EntityItem ei_array[] = eg.getEntityItemsAsArray();
                
                // Loop through the entity items in the group.
                for (int e=0; e<ei_array.length; e++)
                {
                 String item_status = null;
                 boolean remove_item = true;
                 EntityItem ei = ei_array[e]; 
                 String item_type = ei.getEntityType(); 
                 
                 addDebug("Looking at entity "+ei.getEntityType()+" "+ei.getEntityID());
                 
                 // Get the filter criteria
                 String item_filter_status = VE_Filter_Array[i][2];
                 
                 // Get the status of the item
                 item_status = PokUtils.getAttributeFlagValue(ei,(String)ITEM_STATUS_ATTR_TBL.get(item_type));
                 // Now determine if the entity should be removed from the list (ie - filtered).
                 // If the status is null then it is, assumed to be 'old' data that is FINAL and will not filtered. 
                 // If it is FINAL, then it will never be filtered. 
                 // If it is RFR then it will not be filtered if the root is also RFR or the filer criteria is 'RFR Final'.
                 // Therefore - entity IS filtered for the following cases -
                 // - the status is DRAFT or CR
                 // - the status is RFR and the root status is FINAL and 'Final' is epcified as the criteria
                 addDebug((String)ITEM_STATUS_ATTR_TBL.get(item_type)+ " is "+item_status);
                 if (item_status == null) remove_item=false;
                 else if (item_status.equals(STATUS_FINAL)) remove_item=false;
                 else if (item_status.equals(STATUS_R4REVIEW) && (root_status.equals(STATUS_R4REVIEW) || 
                		 item_filter_status.equals("RFR Final"))) remove_item=false;
                 
                 if (remove_item == true) 
                  {                	
                	addDebug("Removing "+ item_type+" "+ ei.getEntityID() + " " + item_status + " from list");
                	addDebug("Filter criteria is " + item_filter_status);
                	
                	// This item will be removed.
                	// Lets examine the uplinks from this item.
                	// Each uplink from this item will be removed.
                	// In addition the item on the other side of the uplink will be removed if 
                	// it has no additional downlinks. 
                	//
                	// Likewise the downlinks must also be examined.
                	// Each downlink from this item will be removed.
                	// In addition the item on the other side of the downlink will be removed if
                	// it has no additional uplinks.
                	// Whenever an item is removed, its uplinks and dowlinks are removed and the 
                	// parent is set to null. 
                	// 
                	// As of BH .5 we have the following cases to consider: 
                	// 
                	// LSEO - LSEOAVAIL - AVAIL - AVAILANNA - ANNOUNCEMENT
                	//
                	// If the AVAIL is removed then the LSEOAVAIL will also be removed.
                	// The AVAILANNA may also be removed if it has no additional uplinks other than this one.
                	// If the ANNOUNCEMENT is removed then the AVAILANNA will also be removed if it has no additional 
                	// downlinks other than this one. 
                	// 
                	// SVCMOD - SVCMODAVAIL - AVAIL - AVAILANNA - ANNOUNCEMENT
                	// 
                	// MODEL - MODELAVAIL - AVAIL - AVAILANNA - ANNOUNCEMENT
                	// MODEL - MODELMM - MM 
                	// MODEL - MODELIMG - IMG
                	
                	// Load the uplinks for the item to be removed into and array for processing
                	EntityItem up_item_array[] = new EntityItem[ei.getUpLinkCount()];
                	ei.getUpLink().copyInto(up_item_array);
                    
                	// Loop through the uplinks
                    for (int j=0; j<up_item_array.length; j++){
                    	// Get the entity on the other side of the uplink
        				EntityItem up_item = up_item_array[j];
        				addDebug("uplink: "+ up_item.getKey());
        				// remove the uplink from the item being removed
        				ei.removeUpLink(up_item);
        				// make sure the upside group is not already empty (they may have 
        				// already been all removed)
        				if (up_item.getEntityGroup() != null) {
        				        				        				
        				// If the up side item has no more downlinks then it will be removed also
        				if (up_item.hasDownLinks()== false) {
        				   // remove the attributes from the up side item
        				   for (int z = up_item.getAttributeCount() - 1; z >= 0; z--) {
                               EANAttribute att = up_item.getAttribute(z);
                               up_item.removeAttribute(att);
                           }
        				   // remove the up side item from its group
        				   up_item.getEntityGroup().removeEntityItem(up_item);
        				   
        				   // Remove all the uplinks for the up side item being removed
        				   for (int k = up_item.getUpLinkCount() - 1; k >= 0; k--) {
        					   EntityItem up_item2 = (EntityItem)up_item.getUpLink(k);
        					   up_item.removeUpLink(up_item2);
        				   }
        				   // Set parent null for the up side item
        				   up_item.setParent(null);
        				} // end if up side item has more downlinks
        				} // end if up side item group is not null
                        // ***help gc do a better job
        				up_item_array[j]=null;
                    } // end loop through uplinks
                    // help gc do a better job
                    up_item_array=null;    
                    // Load the downlinks for the item to be removed into and array for processing
                    EntityItem down_item_array[] = new EntityItem[ei.getDownLinkCount()];
                	ei.getDownLink().copyInto(down_item_array);
                    
                    // Loop through the downlinks
                    for (int j=0; j<down_item_array.length; j++){
                    	// Get the entity on the other side of the downlink
        				EntityItem down_item = down_item_array[j];
        				addDebug("Downlink: "+ down_item.getKey());
        				// remove the downlink from the item being removed
        				ei.removeDownLink(down_item);
//        				 make sure the downside group is not already empty (they may have 
        				// already been all removed)
        				if (down_item.getEntityGroup() != null) {
        				        				        				
        				// If the up downside item has no more uplinks then it will be removed also
        				if (down_item.hasUpLinks()== false) {
                           // Remove the attributes from the down side item
        				   for (int z = down_item.getAttributeCount() - 1; z >= 0; z--) {
                               EANAttribute att = down_item.getAttribute(z);
                               down_item.removeAttribute(att);
                           }
                           // Remove the down side item from its group
        				   down_item.getEntityGroup().removeEntityItem(down_item);
        				   
                           // Remove all the downlinks for the down side item being removed
        				   for (int k = down_item.getUpLinkCount() - 1; k >= 0; k--) {
        					   EntityItem down_item2 = (EntityItem)down_item.getUpLink(k);
        					   down_item.removeDownLink(down_item2);
        				   }
        				   // Set parent null for the down side item
        				   down_item.setParent(null);
        				}  // end if down side item has more up links 
        				}  // end if down side item group is not null
                        // help gc do a better job
        				down_item_array[j]=null;
        			} // end loop through the downlinks
                    // help gc do a better job
                    down_item_array=null; 
                    // set parent null for the filtered item
                	ei.setParent(null);
                	// Remove the attributes for the filtered item
                	for (int z = ei.getAttributeCount() - 1; z >= 0; z--) {
                        EANAttribute att = ei.getAttribute(z);
                        ei.removeAttribute(att);
                    }
                	// Remove the filitered item from the group.
               		eg.removeEntityItem(ei);
                  } // end if remove_item is true
                } // end for loop for Entity Items
              } // end if eg != null
		      } // end if (VE_Filter_Array[i][0].equals(veName))  
            } // end loop through VE_Filter_Array 
			
//			 debug display list of groups after filtering
			addDebug("EntityList after filtering for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
					PokUtils.outputList(list));
			
		} // end if VE is subject to filtering
		
		return list;
        }

	/**********************************************************************************

	 * Get the history of the ABR (ADSABRSTATUS) in VALFROM order
	 * T2STATUS = the active value of STATUS for TQ 
	 * T2 = VALFROM of T2STATUS
	 * Is there a prior value of “Passed�? (0030) for ADSABRSTATUS
	 * No:
	 *    T1 = “1980-01-01 00:00:00.00000�?
	 *    EXIT this logic since T1 and T2 have values
	 * Yes:
	 *    Is the value T2STATUS = “Final�? (0020)
	 *    No:
	 *     f. If the value T2STATUS <> “Ready for Review�? (0040) then this is an error and the ABR should not send data and it should set its attribute to “Failed�?
	 *      Does STATUS have a prior value of “Final�? (0020)
	 *      Yes: 
	 *          EXIT this logic and do not send any data.
	 *          Set the ABR attribute to “Passed�?
	 *      No: 
	 *          RFRNEXT: 
	 *          Note: TQ is the current time for looking at ADSABRSTATUS
	 *          Is there a prior value of “Passed�? for ADSABRSTATUS?
	 *          No: 
	 *             T1 = “1980-01-01 00:00:00.00000�?
	 *             EXIT this logic since T1 and T2 have values
	 *          Yes:
	 *             The prior value should be “Queued�? (0020)
	 *             Find the prior value for &DTFS if it is not null
	 *             TQ = VALFROM of this row
	 *             T1STATUS = the active value of STATUS for TQ
	 *             T1 = VALFROM of T1STATUS
	 *             If T1STATUS <> “Ready for Review�? then go to RFRNEXT
	 *             T1 = VALFROM of T1STATUS
	 *             EXIT this logic since T1 and T2 have values
	 *  Yes:
	 *  RFRNEXT: 
	 *  Note: TQ is the current time for looking at ADSABRSTATUS
	 *  Is there a prior value of “Passed�? for ADSABRSTATUS?
	 *     No: 
	 *         T1 = “1980-01-01 00:00:00.00000�?
	 *         EXIT this logic since T1 and T2 have values
	 *     Yes:
	 *         The prior value should be “Queued�? (0020)
	 *         Find the prior value for &DTFS if it is not null
	 *         TQ = VALFROM of this row
	 *         T1STATUS = the active value of STATUS for TQ
	 *         T1 = VALFROM of T1STATUS
	 *         If T1STATUS <> “Final�? then go to RFRNEXT
	 *         T1 = VALFROM of T1STATUS
	 *         EXIT this logic since T1 and T2 have values
	 * 	END IF
	 */
	private void setT1DTS(XMLMQ mqAbr, AttributeChangeHistoryGroup adsStatusHistoy, AttributeChangeHistoryGroup statusHistoy, String dtfs) 
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException, MiddlewareException
	{
		t1DTS = m_strEpoch;
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		if (isPeriodicABR){
			addDebug("getT1 entered for Periodic ABR "+rootEntity.getKey());
			// get it from the attribute
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("ADSDTS");
        	if (metaAttr==null) {
				throw new MiddlewareException("ADSDTS not in meta for Periodic ABR "+rootEntity.getKey());
			}

			t1DTS = PokUtils.getAttributeValue(rootEntity, "ADSDTS", ", ", m_strEpoch, false);
		}else{
			String attCode = mqAbr.getStatusAttr();
			addDebug("getT1 entered for DQ ABR "+rootEntity.getKey()+" "+attCode+" isSystemResend:"+isSystemResend);
			if (!isSystemResend){
				// get T2Status and set curstatus and priorStatus.
				String t2Status = getT2Status(statusHistoy);
				// look back in the ADS history for any prior "Passed" value. if exist ,then return ture.
				if (existBefore(adsStatusHistoy, STATUS_PASSED)) {
					// if T2 status is RFR
					if (t2Status.equals(STATUS_R4REVIEW)) {
						t1DTS = getTQRFR(adsStatusHistoy, statusHistoy, dtfs);
						if (t1DTS.equals(m_strEpoch)) {
							actionTaken = rsBundle.getString("ACTION_R4R_FIRSTTIME");
						} else if(RFRPassedFinal != true){
							actionTaken = rsBundle.getString("ACTION_R4R_CHANGES");
						}
						// get valfrom of "RFR(0040)" for prior &DTFS.		
					} else if (t2Status.equals(STATUS_FINAL)) {
						t1DTS = getTQFinal(adsStatusHistoy, statusHistoy, dtfs);
						if (t1DTS.equals(m_strEpoch)) {
							actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
						} else {
							actionTaken = rsBundle.getString("ACTION_FINAL_CHANGES");

						}
					}
				} else {
					if (t2Status.equals(STATUS_R4REVIEW)) {
						actionTaken = rsBundle.getString("ACTION_R4R_FIRSTTIME");
					}else if (t2Status.equals(STATUS_FINAL)){
						actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
					}
					addDebug("getT1 for " + rootEntity.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
				}
			}
		}
	}

	/**********************************************************************************
	 *checking whether has passed queue in ADSABRSTATUS
	 */
	private boolean existBefore(AttributeChangeHistoryGroup achg, String value) {
		if (achg != null) {
		    for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
		        AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
			    if (achi.getFlagCode().equals(value)) {
				    return true;
			    }
		    }
		}
		return false;
	}

	/**********************************************************************************
	 * Get the history of the ABR (ADSABRSTATUS) in VALFROM order
	 *  The current value should be “In Process�? (0050)
	 *  The prior value should be “Queued�? (0020)
	 *  Find the prior value for &DTFS if it is not null
	 *  TQ = VALFROM of this row.
	 *  T2 = TQ
	 * @throws MiddlewareException 
	 */
	private void setT2DTS(AttributeChangeHistoryGroup adsStatusHistoy, String dtfs) throws MiddlewareException {
		if (adsStatusHistoy != null && adsStatusHistoy.getChangeHistoryItemCount() > 1) {
			// get the historyitem count.
			int i = adsStatusHistoy.getChangeHistoryItemCount();
			// Find the time stamp for "Queued" Status. Notic: last chghistory
			// is the current one(in process),-2 is queued.
			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistoy.getChangeHistoryItem(i - 2);
			if (achi != null) {
				addDebug("getT2Time [" + (i - 2) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
					+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
				if (achi.getFlagCode().equals(STATUS_QUEUE)) {
					t2DTS = achi.getChangeDate();
				} else {
					addDebug("getT2Time for the value of " + achi.getFlagCode()
						+ "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
					t2DTS = getNow();
				}
			}
			// Continue find time stamp for &DTFS Status. Notic: last chghistory
			// is the current one(in process),-2 is queued, -3 Maybe is &DTFS
			achi = (AttributeChangeHistoryItem) adsStatusHistoy.getChangeHistoryItem(i - 3);
			if (achi != null) {
				addDebug("getT2Time [" + (i - 3) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
					+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
				if (achi.getFlagCode().equals(dtfs)) {
					t2DTS = achi.getChangeDate();
				} else {
					addDebug("getT2Time for the value of " + achi.getFlagCode() + "is not &DTFS " + dtfs
						+ " return valfrom of queued.");
				}
			}
		} else {
			t2DTS = getNow();
			addDebug("getT2Time for ADSABRSTATUS changedHistoryGroup has no history,set t2 getNow().");
		}
	}
	
	/*
	 * Get the history of the ABR (ADSABRSTATUS) in VALFROM order The current
	 * value should be “In Process�? (0050) The prior value should be “Queued�?
	 * (0020) Find the prior value for &DTFS if it is not null TQ = VALFROM of
	 * this row T2 = TQ look into the STATUS history find the value at T2.
	 * return STATUS at T2
	 */
	private String getT2Status(AttributeChangeHistoryGroup statusHistory)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		String status = "";
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
			// last chghistory is the current one
			for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					addDebug("getT2Status [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					// Because the time stamp of Status will be prior to T2,
					// find the first item which time stamp less than T2.
					if (achi.getChangeDate().compareTo(t2DTS) < 0) {
						// If Status is not Final or RFR, then addError and
						// break.
						if (!STATUS_FINAL.equals(achi.getFlagCode()) && !STATUS_R4REVIEW.equals(achi.getFlagCode())) {
							addDebug(rootEntity.getKey() + " is not Final or R4R");
							addError(rsBundle.getString("NOT_R4RFINAL"));
							break;
						} else {
							curStatus = achi.getAttributeValue();
							status = achi.getFlagCode();
							achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i - 1);
							// set prior Status at the xml header.
							if (achi != null) {
								priorStatus = achi.getAttributeValue();
								addDebug("getT2Status [" + (i - 1) + "] chgdate: " + achi.getChangeDate() + " flagcode: "
									+ achi.getFlagCode());
							}
							break;
						}
					}
				}
			}
		} else {
			addDebug("getT2Status for " + rootEntity.getKey() + " getChangeHistoryItemCount less than 0.");
		}
		return status;
	}

	/*
	 * when T2Status == Final Find the time stamp to use for 'Passed' status by
	 * looking back from "Passed" to its "Queued" and then checking to see if
	 * there is a preceding "&DTFS". This is TQ. This time stamp is the one used
	 * to find TQSTATUS. If TQSTATUS is Final, T1 = TQ If TQSTATUS is not Final,
	 * repeat the process looking back for any prior "Passed" with its
	 * TQSTATUS=Final. If no TQSTATUS=Final is found, T1 is the 1980.....
	 */

         private String getTQFinal(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String dtfs)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		String tq = m_strEpoch;
		if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 1) {
			boolean nextQueued = false;
			// last chghistory is the current one(in process), -1 is queued, -2 is &DTFS or Pass\faild\Error
			for (int i = adsStatusHistory.getChangeHistoryItemCount() - 3; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					addDebug("getTQFinalDTS [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					if (achi.getFlagCode().equals(STATUS_PASSED)) { //Passed
						// get previous queued
						nextQueued = true;
					}
					if (nextQueued && achi.getFlagCode().equals(STATUS_QUEUE)) { //get preceding Queue.
						tq = achi.getChangeDate();
						//set time stamp to tq.
						achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i - 1);
						// get the preceding item, check whether it is &DTFS
						if (achi !=null && achi.getFlagCode().equals(dtfs)) { // if there is a preceding &DTFS, this is TQ.
							tq = achi.getChangeDate();
						} else {
							addDebug("getPreveTQFinalDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + dtfs);
						}
                        String status = getTQStatus(statusHistory, tq);
					    if (status.equals(STATUS_FINAL)) {
						    break;
				    	} else {
					    	nextQueued = false;
					    	tq = m_strEpoch;
				}
			}
		}
	}
		} else {
			addDebug("getTQFinalDTS for ADSABRSTATUS has no changed history");
		}
		return tq;
		}
		
	/*  
	 *   If T2STATUS = "Ready For Review" 
	 *   Find the time stamp to use for 'Passed' status by looking back from "Passed" to its "Queued" and then checking to see if there is a preceding "&DTFS".  This is TQ.
	 *   This time stamp is the one used to find TQSTATUS.  
	 *   If TQSTATUS is Final, then exit.
	 *   If TQSTATUS is not Final, repeat the process looking back for any prior "Passed" with its TQSTATUS=Final.  If no TQSTATUS=Final is found, T1 is the first TQ found.
	 *   
	 * */

         	private String getTQRFR(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String dtfs)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		String tq = m_strEpoch;
		String fistTQ = m_strEpoch;	
	
		if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 1) {
				boolean nextQueued = false;
			boolean fistTQfound = false;
			// last chghistory is the current one(in process), -2 is queued, -3 is &DTFS or Pass\failed\Error
			for (int i = adsStatusHistory.getChangeHistoryItemCount() - 3; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					addDebug("getTQRFRDTS [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					if (achi.getFlagCode().equals(STATUS_PASSED)) { //Passed
						// get previous queued
						nextQueued = true;
					}
					if (nextQueued && achi.getFlagCode().equals(STATUS_QUEUE)) { //get preceding Queue.

						tq = achi.getChangeDate();
						//set time stamp to tq.	
						achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i - 1);
						// get the preceding item, check whether it is &DTFS
						if (achi !=null && achi.getFlagCode().equals(dtfs)) { // if there is a preceding &DTFS, this is TQ.
							tq = achi.getChangeDate();
						} else {
							addDebug("getPreveTQRFRDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + dtfs);
						}
						if (fistTQfound == false) {
							fistTQfound = true;
							fistTQ = tq;
					}
                                        						String status = getTQStatus(statusHistory, tq);
						if (status.equals(STATUS_FINAL)) {
							RFRPassedFinal = true;
							actionTaken = rsBundle.getString("ACTION_R4R_PASSEDFINAL");
							return tq;
						} else {
							nextQueued = false;
						}

					}
				}
			}
		} else {
			addDebug("getTQRFRDTS for ADSABRSTATUS has no changed history");
		}
		return fistTQ;
		}

	// get valfrom of status Final or R4R at TQ time.
	private String getTQStatus(AttributeChangeHistoryGroup statusHistory, String tqtime)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
			// last chghistory is the current one
			for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					addDebug("getTQStatus [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					if (tqtime.compareTo(achi.getChangeDate()) > 0) {
						return achi.getFlagCode();
					}
	}
			}
		} else {
			addDebug("getTQStatus for STATUS has no changed history!");
		}
		return CHEAT;
	}

	// set instance variable ADSABRSTATUSHistory
	private AttributeChangeHistoryGroup getADSABRSTATUSHistory() throws MiddlewareException {
		String attCode = "ADSABRSTATUS";
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			return new AttributeChangeHistoryGroup(m_db, m_prof, att);
		} else {
			return null;
		}
	}

	// set instance variable ADSABRSTATUSHistory
	private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ mqAbr) throws MiddlewareException {
		String attCode = mqAbr.getStatusAttr();
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			return new AttributeChangeHistoryGroup(m_db, m_prof, att);
		} else {
			return null;
		}
	}
	
//	checking whether has passed final in Status AttributeHistoryGroup before.
	private boolean existPassedFinal(AttributeChangeHistoryGroup adsStatusHistory,AttributeChangeHistoryGroup statusHistory)
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException{
		boolean nextQueued = false;
		boolean existPassedFinal = false;
		// last chghistory is the current one(in process), -2 is queued, -3 is &DTFS / passed
		if (adsStatusHistory != null){
			for (int i=adsStatusHistory.getChangeHistoryItemCount()-3; i>=0; i--)
			{
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)adsStatusHistory.getChangeHistoryItem(i);
				if (achi != null){
				    addDebug("existPassedFinal ["+i+"] isActive: "+
							achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
							achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
				    if (achi.getFlagCode().equals(STATUS_PASSED)){ //Passed
					   // get previous queued
				    	nextQueued = true;
				}
				    if (nextQueued && achi.getFlagCode().equals(STATUS_QUEUE)){ //can't use &DTFS. you can not make sure it is &DTFS last time.
					    String status = getTQStatus(statusHistory,achi.getChangeDate());
					    if(status.equals(STATUS_FINAL)){
					    	existPassedFinal = true;
					    	break;
			} else {
					        nextQueued = false;
					    }					
				   }
			    }
			}		
		}		
		return existPassedFinal;
	}
	/**********************************************************************************
	 * Checks if the current VE is subject to filtering.
	 */
	private boolean isVEFiltered(String name) {
		for (int i = 0; i < VE_Filter_Array.length; i++) {
			if (VE_Filter_Array[i][0].equals(name))
				return true;
		}
		return false;

	}

	// role must have access to all attributes
	private Profile switchRole(String roleCode)
	throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{
		Profile profile2 = m_prof.getProfileForRoleCode(m_db, roleCode, roleCode, 1);
		if (profile2==null) {
			addError("Could not switch to "+roleCode+" role");
		}else {
			addDebug("Switched role from "+m_prof.getRoleCode()+" to "+profile2.getRoleCode());

			String nlsids = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getNLSIDs(m_abri.getABRCode());
			addDebug("switchRole nlsids: "+nlsids);
			StringTokenizer st1 = new StringTokenizer(nlsids,",");
			while (st1.hasMoreTokens()) {
				String nlsid = st1.nextToken();
				NLSItem nlsitem = (NLSItem)READ_LANGS_TBL.get(nlsid);
				if (!profile2.getReadLanguages().contains(nlsitem)){
					profile2.getReadLanguages().addElement(nlsitem); // this is really cheating
					addDebug("added nlsitem "+nlsitem+" to new prof");
				}
			}
		}

		return profile2;
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

    /**********************************
    * get database
    */
    protected Database getDB() { return m_db; }

    /**********************************
    * get attributecode
    */
    protected String getABRAttrCode() { return m_abri.getABRCode(); }

	/**********************************
	 * add msg to report output
	 */
	protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		if (dbgPw != null){
			dbgLen+=msg.length();
			dbgPw.println(msg);
        	dbgPw.flush();
		}else{
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}

	/**********************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
        return rsBundle;
    }

	/**********************************
	 * feed the xml
	 */
	protected void notify(XMLMQ mqAbr, String rootInfo, String xml)
		throws MissingResourceException
	{
		MessageFormat msgf = null;
		Vector mqVct = mqAbr.getMQPropertiesFN();
		int sendCnt=0;
		boolean hasFailure = false;

		// write to each queue, only one now, but leave this just in case
		for (int i=0; i<mqVct.size(); i++){
			String mqProperties = (String)mqVct.elementAt(i);
			ResourceBundle rsBundleMQ = ResourceBundle.getBundle(mqProperties,
					getLocale(getProfile().getReadLanguage().getNLSID()));
			Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
			boolean bNotify = ((Boolean)ht.get(MQUsage.NOTIFY)).booleanValue();
			ht.put(MQUsage.MQCID,mqAbr.getMQCID()); //add to hashtable for CID to MQ
                        ht.put(MQUsage.XMLTYPE,"ADS"); //add to hashtable to indicate ADS msg
			if (bNotify) {
				try{
					MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml, ht);
					//SENT_SUCCESS = XML was generated and sent successfully for {0} {1}.
					msgf = new MessageFormat(rsBundle.getString("SENT_SUCCESS"));
					args[0] = mqProperties;
					args[1] = rootInfo;
					addOutput(msgf.format(args));
					sendCnt++;
					if (!hasFailure){  // dont overwrite a failed notice
						//xmlgen = rsBundle.getString("SUCCESS");//"Success";
						addXMLGenMsg("SUCCESS", rootInfo);
					}
				}catch (com.ibm.mq.MQException ex) {
					//MQ_ERROR = Error: An MQSeries error occurred for {0}: Completion code {1} Reason code {2}.
					//FAILED = Failed sending {0}
					addXMLGenMsg("FAILED", rootInfo);
					hasFailure = true;
					msgf = new MessageFormat(rsBundle.getString("MQ_ERROR"));
					args[0] = mqProperties+" "+rootInfo;
					args[1] = ""+ex.completionCode;
					args[2] = ""+ex.reasonCode;
					addError(msgf.format(args));
					ex.printStackTrace(System.out);
				} catch (java.io.IOException ex) {
					//MQIO_ERROR = Error: An error occurred when writing to the MQ message buffer for {0}: {1}
					addXMLGenMsg("FAILED", rootInfo);
					hasFailure = true;
					msgf = new MessageFormat(rsBundle.getString("MQIO_ERROR"));
					args[0] = mqProperties+" "+rootInfo;
					args[1] = ex.toString();
					addError(msgf.format(args));
					ex.printStackTrace(System.out);
				}
			}else{
				//NO_NOTIFY = XML was generated but NOTIFY was false in the {0} properties file.
				msgf = new MessageFormat(rsBundle.getString("NO_NOTIFY"));
				args[0] = mqProperties;
				addError(msgf.format(args));
				//{0} "Not sent";
				addXMLGenMsg("NOT_SENT", rootInfo);

			}
		} // end mq loop
		if (sendCnt>0 && sendCnt!=mqVct.size()){ // some went but not all
			addXMLGenMsg("ALL_NOT_SENT", rootInfo);// {0} "Not sent to all";
		}
	}

	/**********************************
	 * generate the xml
	 */
	protected String transformXML(XMLMQ mqAbr, Document document) throws
	ParserConfigurationException,
	javax.xml.transform.TransformerException
	{
		//set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		// OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "no");
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		//create string from xml tree
		java.io.StringWriter sw = new java.io.StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(document);
		trans.transform(source, result);
		String xmlString = XMLElem.removeCheat(sw.toString());

		// transform again for user to see in rpt
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		sw = new java.io.StringWriter();
		result = new StreamResult(sw);
		trans.transform(source, result);
		addUserXML(XMLElem.removeCheat(sw.toString()));

		return xmlString;
	}
	protected void addUserXML(String s){
		if (userxmlPw != null){
			userxmlLen+=s.length();
			userxmlPw.println(s);
			userxmlPw.flush();
		}else{
			userxmlSb.append(convertToHTML(s)+NEWLINE);
		}
	}

	/*************************************************************************************
	 * Check the PDHDOMAIN
	 * xseries and converged prod need DQ checks in the ABRs but the other domains like iseries don't
	 * because those Brands do not want any checking, they do not use STATUS, they want no process
	 * criteria apply if PDHDOMAIN = (0050) 'xSeries' or (0390) 'Converged Products'
	 *@param item    EntityItem
	 * domainInList set to true if matches one of these domains
	 */
	protected boolean domainNeedsChecks(EntityItem item)
	{
		boolean bdomainInList = false;
		String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
		addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
		if (domains.equals("all")){
			bdomainInList = true;
		}else{
			Set testSet = new HashSet();
			StringTokenizer st1 = new StringTokenizer(domains,",");
			while (st1.hasMoreTokens()) {
				testSet.add(st1.nextToken());
			}
			bdomainInList = PokUtils.contains(item, "PDHDOMAIN", testSet);
			testSet.clear();
		}

		if (!bdomainInList){
			addDebug("PDHDOMAIN for "+item.getKey()+" did not include "+domains+", execution is bypassed ["+
					PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"]");
		}
		return bdomainInList;
	}

	/**********************************************************************************
	 *  Get Locale based on NLSID
	 *
	 *@return java.util.Locale
	 */
	public static Locale getLocale(int nlsID)
	{
		Locale locale = null;
		switch (nlsID)
		{
		case 1:
			locale = Locale.US;
			break;
		case 2:
			locale = Locale.GERMAN;
			break;
		case 3:
			locale = Locale.ITALIAN;
			break;
		case 4:
			locale = Locale.JAPANESE;
			break;
		case 5:
			locale = Locale.FRENCH;
			break;
		case 6:
			locale = new Locale("es", "ES");
			break;
		case 7:
			locale = Locale.UK;
			break;
		default:
			locale = Locale.US;
		break;
		}
		return locale;
	}
	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		//return "1.8"; cvs failure
		return "1.12";//"1.10";
	}
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		return "ADSABRSTATUS";
	}

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @return String
    */
    protected static String convertToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<':
                    htmlSB.append("&lt;");
                    break;
                case '>':
                    htmlSB.append("&gt;");
                    break;
                case '"': // could be saved as &quot; also. this will be &#34;
                // this should be included too, but left out to be consistent with west coast
                //case '&': // ignore entity references such as &lt; if user typed it, user will see it
                          // could be saved as &amp; also. this will be &#38;
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                default:
                    htmlSB.append(ch);
                    break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

//========================================================================================
//== resend support
//========================================================================================

    /***********************************************
    *  Sets the specified Flag Attribute on the Root Entity
    *
    *@param    profile Profile
    *@param    strAttributeCode The Flag Attribute Code
    *@param    strAttributeValue The Flag Attribute Value
    */
    private void setFlagValue(String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logMessage(getDescription()+" ***** "+strAttributeCode+" set to: " + strAttributeValue);
        addDebug("setFlagValue entered for "+strAttributeCode+" set to: " + strAttributeValue);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
        	logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
        		rootEntity.getEntityType()+", nothing to do");
			return;
		}
        if(strAttributeValue != null)
        {
            if(strAttributeValue.equals(getAttributeFlagEnabledValue(rootEntity,strAttributeCode))){
                addDebug("setFlagValue "+rootEntity.getKey()+" "+strAttributeCode+
                    " already matches: " + strAttributeValue);
            }else{
                try
                {
                    if (m_cbOn==null){
                        setControlBlock(); // needed for attribute updates
                    }
                    ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);

                    SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
                        strAttributeCode, strAttributeValue, 1, m_cbOn);
                    Vector vctAtts = new Vector();
                    Vector vctReturnsEntityKeys = new Vector();
                    vctAtts.addElement(sf);
                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    addDebug(rootEntity.getKey()+" had "+strAttributeCode+" set to: " + strAttributeValue);
                }
                finally {
                    m_db.commit();
                    m_db.freeStatement();
                    m_db.isPending("finally after update in setflag value");
                }
            }
        }
    }
    private String sysFeedResendStatus(String _strABR,String key, String defvale){
    	  return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(_strABR,key,defvale);
      }

	protected String getQueuedValue(String abrcode) {
		String abrAttrCode = (String) ABR_ATTR_TBL.get(getEntityType());
		if (abrAttrCode == null) {
			addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
			return STATUS_QUEUE;
		} else {
			addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + abrAttrCode + "_" + abrcode);
			return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(abrAttrCode + "_" + abrcode);
		}
	}

	protected String getRFRQueuedValue(String abrcode) {
		String abrAttrCode = (String) ABR_ATTR_TBL.get(getEntityType());
		if (abrAttrCode == null) {
			addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
			return STATUS_QUEUE;
		} else {
			addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + abrAttrCode + "_" + abrcode);
			return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRRFRQueuedValue(abrAttrCode + "_" + abrcode);
		}
	}
	protected boolean fullMode(){
	        String result =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" , "_FullMode");
	        if (result.equals("true")||result.equals("True")||result.equals("TRUE")){
	        	addDebug("ADSABRSTATUS is running in FullMode!");
	        	return true;
	        }else{
	        	addDebug("ADSABRSTATUS is not running in FullMode!");
	        	return false;
	        }
	}
	protected boolean isPeriodicABR(){
		return isPeriodicABR;
	}
}
