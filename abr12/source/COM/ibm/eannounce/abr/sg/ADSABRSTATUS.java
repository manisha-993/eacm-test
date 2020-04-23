//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg;

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
	private ResourceBundle rsBundle = null;
	private String priorStatus = "&nbsp;";
	private String curStatus = "&nbsp;";
	private boolean isPeriodicABR = false;
	private boolean isSystemResend = false;
	private String actionTaken="";

	private static final Hashtable READ_LANGS_TBL;  // tbl of NLSitems, 1 for each profile language
	private static final Hashtable ABR_TBL;
	protected static final Hashtable ADSTYPES_TBL;
	
	protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
	static{
		ABR_TBL = new Hashtable();
		ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.ADSMODELABR");
		ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.sg.ADSFEATUREABR");
		ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.sg.ADSSWFEATUREABR");
		ABR_TBL.put("SVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSSVCFEATUREABR");
		ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSFCTRANSABR");
		ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSMODELCONVERTABR");
		ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSPRODSTRUCTABR");
		ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSWPRODSTRUCTABR");
		ABR_TBL.put("SVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSVCPRODSTRUCTABR");
		ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.ADSLSEOABR");
                ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.ADSSVCMODABR");

		ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.sg.ADSCATNAVABR");
		ABR_TBL.put("XLATE", "COM.ibm.eannounce.abr.sg.ADSXLATEABR");
		ABR_TBL.put("MODELCG", "COM.ibm.eannounce.abr.sg.ADSWWCOMPATABR"); // MODELCG is root for WWCOMPAT ve
		ABR_TBL.put("GENAREA", "COM.ibm.eannounce.abr.sg.ADSGENAREAABR");

		ABR_TBL.put("DELFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELFEATUREABR");
		ABR_TBL.put("DELFCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSDELFCTRANSABR");
		ABR_TBL.put("DELMODEL", "COM.ibm.eannounce.abr.sg.ADSDELMODELABR");
		ABR_TBL.put("DELMODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSDELMODELCONVERTABR");
		ABR_TBL.put("DELPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELPRODSTRUCTABR");
		ABR_TBL.put("DELSVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSVCFEATUREABR");
		ABR_TBL.put("DELSVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSVCPRODSTRUCTABR");
		ABR_TBL.put("DELSWFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSWFEATUREABR");
		ABR_TBL.put("DELSWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSWPRODSTRUCTABR");

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

	private Object[] args = new String[10];
    private String abrversion="";
    private String t2DTS = null;  // T2
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
				if (clsname!=null){
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
							if(sysResendStatus.equals(STATUS_FINAL)){
								if (statusFlag.equals(STATUS_FINAL)){
								    actionTaken = rsBundle.getString("ACTION_FINAL_RESEND");
								}else{
									addDebug(rootEntity.getKey()+" is not Final");
									//RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
									addError(rsBundle.getString("RESEND_ONLY_FINAL"));								
								}
							}else if(sysResendStatus.equals(STATUS_R4REVIEW)){
								if (statusFlag.equals(STATUS_R4REVIEW)){
									actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
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
											actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
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

					t2DTS = getABRQueuedTime(); // T2 timestamp

					if (getReturnCode()==PASS && shouldExecute){
						// get the T1 timestamp
						t1DTS = getT1(mqAbr); // previous change- from date

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
										mqAbr.processThis(this, profileT1, profileT2, rootEntity);
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
					addError(getShortClassName(getClass())+" does not support "+etype);
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

		return list;
	}

	/**********************************************************************************
	 * For those queued from DQ ABRs
	 * If SYSFEEDRESEND = 'Yes' (Yes) THEN
	 * 	If Status (STATUS) = 'Ready for Review' (0040) or 'Final' THEN
	 * 		T1 = �980-01-01�
	 * 	ELSE
	 * 		Set SYSFEEDRESEND = 'No' (No)
	 * 		ErrorMessage LD(of the Root Entity Type) NDN(of the Root Entity) 'was queued to resend data; however,
	 * 		the data is not Ready for Review or Final'.
	 * 	Done
	 * 	END IF
	 * ELSE
	 * 	If this is the first time that STATUS (STATUS) = 'Ready for Review' (0040) or 'Final' (0020) THEN
	 * 		T1 = �980-01-01�
	 * 	ELSE
	 * 		T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of Queued prior to the last
	 *		value of Success.
	 * 	END IF
	 * END IF
	 *
	 * For Periodic ABRs
	 * 	T1 is the current "Last Ran Date Time Stamp" for this ABR / data. If there is no value, then default
	 *	the DTS to �980-01-01�
	 *
	 * For Both:
	 * T2 is the Date/Time Stamp (DTS) that the ABR was Queued (0020).
	 */
	private String getT1(XMLMQ mqAbr)
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException, MiddlewareException
	{
		String dtsT1 = m_strEpoch;
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		if (isPeriodicABR){
			addDebug("getT1 entered for Periodic ABR "+rootEntity.getKey());
			// get it from the attribute
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("ADSDTS");
        	if (metaAttr==null) {
				throw new MiddlewareException("ADSDTS not in meta for Periodic ABR "+rootEntity.getKey());
			}

			dtsT1 = PokUtils.getAttributeValue(rootEntity, "ADSDTS",", ", m_strEpoch, false);
		}else{
			String attCode = mqAbr.getStatusAttr();
			addDebug("getT1 entered for DQ ABR "+rootEntity.getKey()+" "+attCode+" isSystemResend:"+isSystemResend);
			if (!isSystemResend){
				EANAttribute att = rootEntity.getAttribute(attCode);
				if (att != null) {
					AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db, m_prof, att);
					if (achg.getChangeHistoryItemCount()>0){
						// last chghistory is the current one
						int i=achg.getChangeHistoryItemCount()-1;
						AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
						addDebug("getT1 ["+i+"] isActive: "+
								achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
								achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

						curStatus = achi.getAttributeValue();

						if (achi.getFlagCode().equals(STATUS_FINAL)){
							// get r4r timestamp
							achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i-1);
							if (achi!=null){
								priorStatus = achi.getAttributeValue();
								addDebug("getT1 ["+(i-1)+"] chgdate: "+
											achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

								//If this is the first time that Status = 0020(final), then T1 = '1980-01-01'
								if(!firstTimeFinalR4R(achg,STATUS_FINAL)){
									//T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of
									//Queued prior to the last value of Success.
									dtsT1 = getPrevQueuedDTS();
									actionTaken = rsBundle.getString("ACTION_FINAL_CHANGES");
								}else{
									actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
								}
							}else{
								addDebug("getT1: WARNING NO changehistory found prior to Final");
							}
						}else if (achi.getFlagCode().equals(STATUS_R4REVIEW)){
							achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i-1);
							if (achi!=null){
								addDebug("getT1 ["+(i-1)+"] chgdate: "+
										achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

								//If this is the first time that Status = 0040 (Ready for Review), then T1 = '1980-01-01'
								if(!firstTimeFinalR4R(achg,STATUS_R4REVIEW)){
									//T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of
									//Queued prior to the last value of Success.
									dtsT1 = getPrevQueuedDTS();
									actionTaken = rsBundle.getString("ACTION_R4R_CHANGES");
								}else{
									actionTaken = rsBundle.getString("ACTION_R4R_FIRSTTIME");
								}
							}else{
								addDebug("getT1: WARNING NO changehistory found prior to R4R");
							}
						}
					} // has history items

					addDebug("getT1 for "+rootEntity.getKey()+" "+dtsT1);
				} else {// status attr !=null
					addDebug("getT1 for "+rootEntity.getKey()+" "+attCode+"  was null");
				}
			}
		}

		return dtsT1;
	}

	/**********************************************************************************
	 * T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of
	 * Queued prior to the last value of Success.
	 */
	private String getPrevQueuedDTS()
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		String dtsT1 = m_strEpoch;
		String attCode = "ADSABRSTATUS";
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		addDebug("getPrevQueuedDTS entered for "+rootEntity.getKey()+" "+attCode);
		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db, m_prof, att);
			if (achg.getChangeHistoryItemCount()>1){
				boolean nextQueued = false;
				// last chghistory is the current one(in process), -2 is queued
				for (int i=achg.getChangeHistoryItemCount()-2; i>=0; i--)
				{
					AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
					addDebug("getPrevQueuedDTS ["+i+"] isActive: "+
						achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
						achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
					if (achi.getFlagCode().equals("0030")){ //Passed
						// get previous queued
						nextQueued = true;
					}
					if (nextQueued && achi.getFlagCode().equals("0020")){ //Queued
						// get previous queued date
						return achi.getChangeDate();
					}
				}
			} // has history items
			else {
				addDebug("getPrevQueuedDTS for "+rootEntity.getKey()+" "+attCode+" has no history");
			}
		} else {
			addDebug("getPrevQueuedDTS for "+rootEntity.getKey()+" "+attCode+" was null");
		}

		return dtsT1;
	}

	/**********************************************************************************
	 * check to see if first time final or r4r
	 */
	private boolean firstTimeFinalR4R(AttributeChangeHistoryGroup achg, String value){
		int cnt=0;
		for (int i=achg.getChangeHistoryItemCount()-1; i>=0; i--)
		{
			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
			if (achi.getFlagCode().equals(value)){
				cnt++;
			}
		}
		return cnt==1;
	}

	/**********************************************************************************
	 * get timestamp for queueing this ABR
	 */
	private String getABRQueuedTime()
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		String attCode = "ADSABRSTATUS";
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		addDebug("getABRQueuedTime entered for "+rootEntity.getKey()+" "+attCode);
		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db, m_prof, att);
			if (achg.getChangeHistoryItemCount()>1){
				// last chghistory is the current one(in process), -2 is queued
				int i=achg.getChangeHistoryItemCount()-2;
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
				addDebug("getABRQueuedTime ["+i+"] isActive: "+
						achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
						achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

				return achi.getChangeDate();
			} // has history items
			else {
				addDebug("getABRQueuedTime for "+rootEntity.getKey()+" "+attCode+" has no history");
			}
		} else {
			addDebug("getABRQueuedTime for "+rootEntity.getKey()+" "+attCode+" was null");
		}

		return getNow();
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
}
