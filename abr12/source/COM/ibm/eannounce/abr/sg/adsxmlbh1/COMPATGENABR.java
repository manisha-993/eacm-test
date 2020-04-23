//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.objects.*;
import java.sql.SQLException;
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
 A single ABR supports this functionality based on the Entity Type that queues this function 
  	utilizing AttributeCode COMPATGENABR of Type A.
 
 B.	Populate Table GBLI.WWTECHCOMPATGEN 
 The Virtual Entity (VE) extract will be performed two times:
	 1.	T1 is the DTS determined by the prior section
	 2.	T2 is the Date/Time Stamp (DTS) that the ABR was Queued (0020).

 C. Deactivate MODELCG ,MDLCGOSMDL... in the following cases.
	1. In the spec XIII. MODELCG.  
	For MODELCG.OKTOPUB = Delete (Delete) as a change, then
	Set  Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	If MODELCG.OKTOPUB = Delete (Delete) then after the preceding is processed, deactivate MODELCG in the PDH.
	2. XV. MDLCGOSMDL.
	If MDLCGOSMDL.COMPATPUBFLG = Delete (Delete) as a change, then 
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	f MDLCGOSMDL.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate MDLCGOSMDL in the PDH.
	
	3. XVII SEOCG.
	For this SEOCG.OKTOPUB = Delete (Delete) as a change, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	If SEOCG.OKTOPUB = Delete (Delete) then after the preceding is processed, deactivate SEOCG in the PDH.
	
	3. XIX SEOCGOSSEO
	If SEOCGOSSEO.COMPATPUBFLG = Delete (Delete) as a change, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	If SEOCGOSSEO.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate SEOCGOSSEO in the PDH..
	
	
	4. XIX SEOCGOSBDL
	If SEOCGOSBDL.COMPATPUBFLG = Delete (Delete) added, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	If SEOCGOSBDL.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate SEOCGOSBDL in the PDH.
	
	
	5. XXII SEOCGOSSVCSEO
	
	
	If SEOCGOSSVCSEO.COMPATPUBFLG = Delete (Delete) added, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	If SEOCGOSSVCSEO.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate SEOCGOSSVCSEO in the PDH.


	actionTaken
	ACTION_R4R_FIRSTTIME = Ready for Review - First Time
	ACTION_R4R_CHANGES = Ready for Review - Changes
	ACTION_R4R_RESEND = Ready for Review - Resend
	ACTION_FINAL_FIRSTTIME = Final - First Time
	ACTION_FINAL_CHANGES = Final - Changes
	ACTION_FINAL_RESEND = Final - Resend
 *
 */


public class COMPATGENABR extends ADSABRSTATUS
{
	private StringBuffer rptSb = new StringBuffer();

	private StringBuffer xmlgenSb = new StringBuffer();
	private PrintWriter dbgPw=null;
	private PrintWriter userxmlPw=null;
	private String dbgfn = null;
	private String userxmlfn = null;
	private int userxmlLen=0;
	private int dbgLen=0;
	private ResourceBundle rsBundle = null;
	private String priorStatus = "&nbsp;";
	private String curStatus = "&nbsp;";
	private boolean isPeriodicABR = false;
	//private boolean isSystemResend = false;
	private boolean isXMLIDLABR = false;
        // when Status goes to RFR, but it has been Final before,it is True; Status never been Final, it is False. 
	private boolean RFRPassedFinal = false;
	private String actionTaken="";
	private static Hashtable CompatABR_TBL = new Hashtable();
	private static final String MODELCG_DELETEACTION_NAME  = "DELMODELCG"; 
	private static final String SEOCG_DELETEACTION_NAME = "DELSEOCG";
	private static final String MDLCGOSMDL_DELETEACTION_NAME = "DELMDLCGOSMDL";
	private static final String SEOCGOSSEO_DELETEACTION_NAME = "DELSEOCGOSSEO";
	private static final String SEOCGOSBDL_DELETEACTION_NAME = "DELSEOCGOSBDL";
	private static final String SEOCGOSSVCSEO_DELETEACTION_NAME = "DELSEOCGOSSVCSEO";
	
	static
	{
		CompatABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODABR");
	    CompatABR_TBL.put("MODELCG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODCGABR");
	    CompatABR_TBL.put("MODELCGOS", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODCGOSABR");
	    CompatABR_TBL.put("MDLCGOSMDL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMDLCGOSMDLABR");
	    CompatABR_TBL.put("SEOCG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGABR");
	    CompatABR_TBL.put("SEOCGOS", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSABR");
	    CompatABR_TBL.put("SEOCGOSBDL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSBDLABR");
	    CompatABR_TBL.put("SEOCGOSSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSSEOABR");
	    CompatABR_TBL.put("SEOCGOSSVCSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSSVCSEOABR");
	    CompatABR_TBL.put("WWSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATWWSEOABR");
	    CompatABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATLSEOBUNDLEABR");    
	}
	//TODO
	protected static final String[][] COMPATVE_Filter_Array = 
	{
	{"ADSWWCOMPATMOD","MODELCG","RFR Final"},
	{"ADSWWCOMPATMOD","MODELCGOS","RFR Final"},
	{"ADSWWCOMPATMOD","MDLCGOSMDL","RFR Final"},
	{"ADSWWCOMPATMOD","MODEL","RFR Final"},
    {"ADSWWCOMPATMOD","SEOCG","RFR Final"},
    {"ADSWWCOMPATMOD","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATMOD","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATMOD","SEOCGOSBDL","RFR Final"},
    {"ADSWWCOMPATMOD","SEOCGOSSVCSEO","RFR Final"},
    {"ADSWWCOMPATMOD","WWSEO","RFR Final"},
    {"ADSWWCOMPATMOD","LSEOBUNDLE","RFR Final"},
    {"ADSWWCOMPATMOD","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATMODCG","MODEL","RFR Final"},
    {"ADSWWCOMPATMODCG","MODELCGOS","RFR Final"},
    {"ADSWWCOMPATMODCG","MDLCGOSMDL","RFR Final"},
     
    {"ADSWWCOMPATMODCGOS","MODEL","RFR Final"},
    {"ADSWWCOMPATMODCGOS","MODELCG","RFR Final"},
    {"ADSWWCOMPATMODCGOS","MDLCGOSMDL","RFR Final"},
    
    {"ADSWWCOMPATMDLCGOSMDL1","MODELCGOS","RFR Final"},
    {"ADSWWCOMPATMDLCGOSMDL1","MODEL","RFR Final"},
    
    {"ADSWWCOMPATMDLCGOSMDL2","MODEL","RFR Final"},
    {"ADSWWCOMPATMDLCGOSMDL2","MODELCG","RFR Final"},
    
    {"ADSWWCOMPATWWSEO1","SEOCG","RFR Final"},
    {"ADSWWCOMPATWWSEO1","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATWWSEO1","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATWWSEO1","SEOCGOSBDL","RFR Final"},
    {"ADSWWCOMPATWWSEO1","SEOCGOSSVCSEO","RFR Final"},
    {"ADSWWCOMPATWWSEO1","WWSEO","RFR Final"},
    {"ADSWWCOMPATWWSEO1","LSEOBUNDEL","RFR Final"},
    {"ADSWWCOMPATWWSEO1","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATWWSEO2","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATWWSEO2","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATWWSEO2","SEOCG","RFR Final"},
    {"ADSWWCOMPATWWSEO2","WWSEO","RFR Final"},
    {"ADSWWCOMPATWWSEO2","MODEL","RFR Final"},
    {"ADSWWCOMPATWWSEO2","LSEOBUNDEL","RFR Final"},
    
    {"ADSWWCOMPATLSEOBUNDLE1","SEOCG","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","SEOCGOSBDL","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","SEOCGOSSVCSEO","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","WWSEO","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","LSEOBUNDEL","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE1","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATLSEOBUNDLE2","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE2","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE2","SEOCG","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE2","WWSEO","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE2","MODEL","RFR Final"},
    {"ADSWWCOMPATLSEOBUNDLE2","LSEOBUNDEL","RFR Final"},
    
    {"ADSWWCOMPATSEOCG","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATSEOCG","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATSEOCG","SEOCGOSBDL","RFR Final"},
    {"ADSWWCOMPATSEOCG","SEOCGOSSVCSEO","RFR Final"},
    {"ADSWWCOMPATSEOCG","MODEL","RFR Final"},
    {"ADSWWCOMPATSEOCG","LSEOBUNDEL","RFR Final"},
    {"ADSWWCOMPATSEOCG","WWSEO","RFR Final"},
    {"ADSWWCOMPATSEOCG","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATSEOCGOS","SEOCG","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","SEOCGOSSEO","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","SEOCGOSBDL","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","SEOCGOSSVCSEO","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","MODEL","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","LSEOBUNDEL","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","WWSEO","RFR Final"},
    {"ADSWWCOMPATSEOCGOS","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATSEOCGOSBDL","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATSEOCGOSBDL","LSEOBUNDEL","RFR Final"},
    
    {"ADSWWCOMPATSEOCGOSSEO","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATSEOCGOSSEO","WWSEO","RFR Final"},
    
    {"ADSWWCOMPATSEOCGOSSVCSEO","SEOCGOS","RFR Final"},
    {"ADSWWCOMPATSEOCGOSSVCSEO","SVCSEO","RFR Final"},
    
    {"ADSWWCOMPATSEOCGOS2","SEOCG","RFR Final"},
    {"ADSWWCOMPATSEOCGOS2","WWSEO","RFR Final"},
    {"ADSWWCOMPATSEOCGOS2","MODEL","RFR Final"},
    {"ADSWWCOMPATSEOCGOS2","LSEOBUNDEL","RFR Final"},
	};
//	TODO
	protected static final Hashtable COMPATITEM_STATUS_ATTR_TBL;
	static {
		COMPATITEM_STATUS_ATTR_TBL = new Hashtable();
		COMPATITEM_STATUS_ATTR_TBL.put("MODEL","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("MODELCG","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("MODELCGOS","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("MDLCGOSMDL","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("WWSEO","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("LSEOBUNDLE","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("SEOCG","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOS","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSSEO","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSBDL","STATUS");
		COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSSVCSEO","STATUS");					
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
		String clsname = (String) CompatABR_TBL.get(type);
		addDebug("creating instance of CompatABR_TBL  = '" + clsname + "' for "+type);
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
				if (clsname!=null){
					boolean shouldExecute = true;
					XMLMQ mqAbr = (XMLMQ) Class.forName(clsname).newInstance();

					abrversion = getShortClassName(mqAbr.getClass())+" "+mqAbr.getVersion();
//					 RQK check to see if MQ propfile name exists on root entity
					// if it exists then set isXMLIDLABR true
					// else set it to false
					// call new checkIDLMQPropertiesFN()
					//isXMLIDLABR = mqAbr.checkIDLMQPropertiesFN(rootEntity);

					if (!isPeriodicABR){
						String statusAttr = mqAbr.getStatusAttr();
						//String sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
						String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
						//isSystemResend = SYSFEEDRESEND_YES.equals(sysfeedFlag);
						//addDebug("execute: "+rootEntity.getKey()+" "+statusAttr+": "+
						//	PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false)+" ["+statusFlag+"] sysfeedFlag: "+
						//	sysfeedFlag + " is XMLIDLABR: " + isXMLIDLABR);
						
						// running in ADSABR
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
				
					}else{
						addDebug("execute: periodic "+rootEntity.getKey());
					}
                                        //get COMPATGENABRSTATUS changed history group
					AttributeChangeHistoryGroup adsStatusHistoy = null;
					// For the IDL case COMPATGENABRSTATUS may have never been set
					//if (!isXMLIDLABR)
					adsStatusHistoy = getADSABRSTATUSHistory();
					//get STATUS changed history group
					AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
					setT2DTS(rootEntity, mqAbr, adsStatusHistoy, statusHistory, CHEAT); // T2 timestamp
					setT1DTS(mqAbr, adsStatusHistoy, statusHistory, CHEAT); // T1 timestamp

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
										    // Deactivate in PDH
										    if (isDeactivatedEntity(rootEntity))
										    	deactivateEntities(rootEntity);
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
					addError(getShortClassName(getClass())+" does not support "+etype);
				}
				//NAME is navigate attributes
				navName = getNavigationName(rootEntity);

// fixme remove
//	setCreateDGEntity(false);

				// update lastran date
				if (isPeriodicABR && !isXMLIDLABR && getReturnCode()==PASS) {
					PDGUtility pdgUtility = new PDGUtility();
					OPICMList attList = new OPICMList();
					attList.put("ADSDTS","ADSDTS=" + t2DTS);
					pdgUtility.updateAttribute(m_db, m_prof, rootEntity, attList);
				}

				addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
			}catch(Exception e){
				throw e;
			}finally{
//				if (isSystemResend){
//					setFlagValue("SYSFEEDRESEND", SYSFEEDRESEND_NO); // reset the flag
//				}
				if (isXMLIDLABR){
					deactivateMultiFlagValue("XMLABRPROPFILE");
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
		
		// check if this VE is subject to status filtering
		if (isVEFiltered(veName)) {
		// Get the status for the root	
		  EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0); 
		  String root_status = PokUtils.getAttributeFlagValue(root_ei,"STATUS");
		  addDebug("The status of the root for VE " + veName + " is: "+ root_status);
		  
		  // For each entity type for the VE in VE_Filter_Array, get the group and 
		  // check the entities in the group for filtering.
		  for (int i=0; i<COMPATVE_Filter_Array.length; i++) {
			  //addDebug("Looking at VE_filter_Array" + COMPATVE_Filter_Array[i][0]+" "
			//		  + COMPATVE_Filter_Array[i][1] + " " + COMPATVE_Filter_Array[i][2]); 
			  if (COMPATVE_Filter_Array[i][0].equals(veName)) {
				EntityGroup eg = list.getEntityGroup(COMPATVE_Filter_Array[i][1]);
				addDebug("Found "+ list.getEntityGroup(COMPATVE_Filter_Array[i][1]));
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
                 String item_filter_status = COMPATVE_Filter_Array[i][2];
                 
                 // Get the status of the item
                 item_status = PokUtils.getAttributeFlagValue(ei,(String)COMPATITEM_STATUS_ATTR_TBL.get(item_type));
                 // Now determine if the entity should be removed from the list (ie - filtered).
                 // If the status is null then it is, assumed to be 'old' data that is FINAL and will not filtered. 
                 // If it is FINAL, then it will never be filtered. 
                 // If it is RFR then it will not be filtered if the root is also RFR or the filer criteria is 'RFR Final'.
                 // Therefore - entity IS filtered for the following cases -
                 // - the status is DRAFT or CR
                 // - the status is RFR and the root status is FINAL and 'Final' is epcified as the criteria
                 addDebug((String)COMPATITEM_STATUS_ATTR_TBL.get(item_type)+ " is "+item_status);
                 if (item_status == null) remove_item=false;
                 else if (item_status.equals(STATUS_FINAL)) remove_item=false;
                 else if (item_status.equals(STATUS_R4REVIEW) && (root_status.equals(STATUS_R4REVIEW) || 
                		 item_filter_status.equals("RFR Final"))) remove_item=false;
                 
                 if (remove_item == true) 
                  {
                	addDebug("Removing "+ item_type+" "+ ei.getEntityID() + " " + item_status + " from list");
                 	addDebug("Filter criteria is " + item_filter_status);
                 	removeItem(eg,ei); 
                  } // end if remove_item is true
                } // end for loop for Entity Items
              } // end if eg != null
		      } // end if (VE_Filter_Array[i][0].equals(veName))  
            } // end loop through VE_Filter_Array 
			
//			 debug display list of groups after filtering
			addDebug("EntityList after filtering for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
					PokUtils.outputList(list));
			
		} // end if VE is subject to filtering

            // check if this VE is subject to country filtering
		if (isVECountryFiltered(veName)) {
		
		  EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0);
          String root_type = root_ei.getEntityType(); 
          String root_country_attr = (String)ITEM_COUNTRY_ATTR_TBL.get(root_type);
          
          // get hashset of country flagcodes for the root
          HashSet rootSet = getCountry(root_ei, root_country_attr);
 
          // For each entity type for the VE in VE_Country_Filter_Array, get the group and 
		  // check the entities in the group for filtering.
		  for (int i=0; i<VE_Country_Filter_Array.length; i++) {
			  addDebug("Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[i][0]+" "
					  + VE_Country_Filter_Array[i][1]); 
			  if (VE_Country_Filter_Array[i][0].equals(veName)) {
				EntityGroup eg = list.getEntityGroup(VE_Country_Filter_Array[i][1]);
				addDebug("Found "+ list.getEntityGroup(VE_Country_Filter_Array[i][1]));
				// There should always be a group, unless the VE has been changed and the 
				// VE_Country_Filter_Array has not been updated. 
			  if (eg != null){					  
				            
                // Load the EntityItem ojects into an array for processing.
				EntityItem ei_array[] = eg.getEntityItemsAsArray();
                
                // Loop through the entity items in the group.
                for (int e=0; e<ei_array.length; e++)
                {
                 boolean remove_item = true;
                 EntityItem ei = ei_array[e]; 
                 String item_type = ei.getEntityType(); 
                 
                 addDebug("Looking at entity "+ei.getEntityType()+" "+ei.getEntityID());

                 String item_country_attr = (String)ITEM_COUNTRY_ATTR_TBL.get(item_type);
                                                   
                 // get hashset of country flagcodes for the item        
                 // set remove_item to false if there is an overlap between the root country codes and the item country
                 // codes
                 HashSet itemSet = getCountry(ei, item_country_attr);
                 Iterator rootSet_itr = rootSet.iterator();
                 while(rootSet_itr.hasNext() && remove_item == true) {
                	 String ctryVal = (String) rootSet_itr.next();
                	 if (itemSet.contains(ctryVal)) {
                		 remove_item = false;
                	 }
                	 
                 }
                 
                 if (remove_item == true) 
                  {                	
                	removeItem(eg,ei); 
                  } // end if remove_item is true
                } // end for loop for Entity Items
              } // end if eg != null
		      } // end if (VE_Country_Filter_Array[i][0].equals(veName))  
            } // end loop through VE_Country_Filter_Array 
			
//			 debug display list of groups after filtering
			addDebug("EntityList after filtering for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
					PokUtils.outputList(list));
			
		} // end if VE is subject to country filtering
             
		
		return list;
        }

	/**********************************************************************************

	 * Get the history of the ABR (COMPATGENABRSTATUS) in VALFROM order
	 * T2STATUS = the active value of STATUS for TQ 
	 * T2 = VALFROM of T2STATUS
	 * Is there a prior value of “Passed�? (0030) for COMPATGENABRSTATUS
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
	 *          Note: TQ is the current time for looking at COMPATGENABRSTATUS
	 *          Is there a prior value of “Passed�? for COMPATGENABRSTATUS?
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
	 *  Note: TQ is the current time for looking at COMPATGENABRSTATUS
	 *  Is there a prior value of “Passed�? for COMPATGENABRSTATUS?
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
		if (isPeriodicABR && !isXMLIDLABR){
			addDebug("getT1 entered for Periodic ABR "+rootEntity.getKey());
			// get it from the attribute
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("ADSDTS");
        	if (metaAttr==null) {
				throw new MiddlewareException("ADSDTS not in meta for Periodic ABR "+rootEntity.getKey());
			}

			t1DTS = PokUtils.getAttributeValue(rootEntity, "ADSDTS", ", ", m_strEpoch, false);
		}else{
			//String attCode = mqAbr.getStatusAttr();
			//addDebug("getT1 entered for DQ ABR "+rootEntity.getKey()+" "+attCode+" isSystemResend:"+isSystemResend + " isIDLABR:" + isXMLIDLABR);
			if (!isXMLIDLABR){
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
							//t1DTS = adjustTimeSecond(t1DTS, 40);
						}
						// get valfrom of "RFR(0040)" for prior &DTFS.		
					} else if (t2Status.equals(STATUS_FINAL)) {
						t1DTS = getTQFinal(adsStatusHistoy, statusHistoy, dtfs);
						if (t1DTS.equals(m_strEpoch)) {
							actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
						} else {
							actionTaken = rsBundle.getString("ACTION_FINAL_CHANGES");
							//t1DTS = adjustTimeSecond(t1DTS, 40);
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

	/***************************************************************************
	 * checking whether has passed queue in COMPATGENABRSTATUS
	 */
	private boolean existBefore(AttributeChangeHistoryGroup achg, String value) {
		if (achg != null) {
			for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);
				if (achi.getFlagCode().equals(value)) {
					return true;
				}
			}

		}
		return false;
	}

	/**********************************************************************************
	 * Get the history of the ABR (COMPATGENABRSTATUS) in VALFROM order
	 *  The current value should be “In Process�? (0050)
	 *  The prior value should be “Queued�? (0020)
	 *  Find the prior value for &DTFS if it is not null
	 *  TQ = VALFROM of this row.
	 *  T2 = TQ
	 * @throws MiddlewareException 
	 */
	private void setT2DTS(EntityItem rootEntity, XMLMQ mqAbr, AttributeChangeHistoryGroup adsStatusHistoy,
		AttributeChangeHistoryGroup statusHistory, String dtfs) throws MiddlewareException {

		addDebug("getT2 entered for The ADS ABR handles this as an IDL:" + isXMLIDLABR);
		if (!isXMLIDLABR) {
			if (adsStatusHistoy != null && adsStatusHistoy.getChangeHistoryItemCount() > 1) {
				// get the historyitem count.
				int i = adsStatusHistoy.getChangeHistoryItemCount();
				// Find the time stamp for "Queued" Status. Notic: last
				// chghistory is the current one(in process),-2 is queued.
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistoy.getChangeHistoryItem(i - 2);
				if (achi != null) {
					addDebug("getT2Time [" + (i - 2) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					if (achi.getFlagCode().equals(STATUS_QUEUE)) {
						t2DTS = achi.getChangeDate();
						//t2DTS = adjustTimeSecond(t2DTS, 40);
					} else {
						addDebug("getT2Time for the value of " + achi.getFlagCode()
							+ "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
						t2DTS = getNow();
						//t2DTS = adjustTimeSecond(t2DTS, 40);
					}
				}
				// Continue find time stamp for &DTFS Status. Notic: last
				// chghistory is the current one(in process),-2 is queued, -3
				// Maybe is &DTFS
				achi = (AttributeChangeHistoryItem) adsStatusHistoy.getChangeHistoryItem(i - 3);
				if (achi != null) {
					addDebug("getT2Time [" + (i - 3) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					if (achi.getFlagCode().equals(dtfs)) {
						t2DTS = achi.getChangeDate();
						//t2DTS = adjustTimeSecond(t2DTS, 40);
					} else {
						addDebug("getT2Time for the value of " + achi.getFlagCode() + "is not &DTFS " + dtfs
							+ " return valfrom of queued.");
					}
				}
			} else {
				t2DTS = getNow();
				addDebug("getT2Time for COMPATGENABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
			}
		} else {
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("STATUS");
			if (metaAttr != null) {
				if (existBefore(statusHistory, STATUS_FINAL)) {
					for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
						AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
						if (achi.getFlagCode().equals(STATUS_FINAL)) {
							t2DTS = achi.getChangeDate();
							curStatus = achi.getAttributeValue();
							AttributeChangeHistoryItem priorachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i - 1);
							// set prior Status at the xml header. 
							if (priorachi != null) {
								priorStatus = priorachi.getAttributeValue();
								addDebug("priorStatus [" + (i - 1) + "] chgdate: " + priorachi.getChangeDate() + " flagcode: "
									+ priorachi.getFlagCode());
							}
							break;
						}
					}
				} else if (existBefore(statusHistory, STATUS_R4REVIEW)) {
							for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
								AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
								if (achi.getFlagCode().equals(STATUS_R4REVIEW)) {
									t2DTS = achi.getChangeDate();
									curStatus = achi.getAttributeValue();
									AttributeChangeHistoryItem priorachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i - 1);
									// set prior Status at the xml header. 
									if (priorachi != null) {
										priorStatus = priorachi.getAttributeValue();
										addDebug("priorStatus [" + (i - 1) + "] chgdate: " + priorachi.getChangeDate() + " flagcode: "
											+ priorachi.getFlagCode());
									}
									break;
								}
							}
				}else{
					  addError(rsBundle.getString("IDL_NOT_R4RFINAL"));
					  addDebug("getT2Time for IDL ABR, the Status never being RFR or Final");
			    }			
			} else {
				t2DTS = getNow();
				addDebug(rootEntity.getKey() + " , There is not such attribute STATUS, set t2DTS is getNow()." );
			}
		}
	}
	
	/*
	 * Get the history of the ABR (COMPATGENABRSTATUS) in VALFROM order The current
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
							if (achi!=null){
			//addDebug("getT2Status [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
			//			+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
					// Because the time stamp of Status will be prior to T2, find the first item which time stamp less than T2. 
					if (achi.getChangeDate().compareTo(t2DTS) < 0) {
						// If Status is not Final or RFR, then addError and break.
						if (!STATUS_FINAL.equals(achi.getFlagCode()) && !STATUS_R4REVIEW.equals(achi.getFlagCode())) {
							addDebug(rootEntity.getKey() + " is not Final or R4R");
							addError(rsBundle.getString("NOT_R4RFINAL"));
							break;
								}else{
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
							}else{
		                                        addDebug("getT2Status for " + rootEntity.getKey() + " getChangeHistoryItemCount less than 0.");
							}
                                                        return status;
						}

	/*   when T2Status == Final
	 *   Find the time stamp to use for 'Passed' status by looking back from "Passed" to its "Queued" 
	 *   and then checking to see if there is a preceding "&DTFS".  
	 *   This is TQ.
	 *   This time stamp is the one used to find TQSTATUS.  
	 *   If TQSTATUS is Final, T1 = TQ
	 *   If TQSTATUS is not Final, repeat the process looking back for any prior "Passed" with its TQSTATUS=Final.  
	 *   If no TQSTATUS=Final is found, T1 is the 1980.....
	 * */

         private String getTQFinal(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String dtfs)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		String tq = m_strEpoch;
		if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 1) {
			boolean nextQueued = false;
			// last chghistory is the current one(in process), -1 is queued, -2 is &DTFS or Pass\faild\Error
			for (int i = adsStatusHistory.getChangeHistoryItemCount() - 3; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					//addDebug("getTQFinalDTS [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
					//	+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
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
//						 In case of it was queued before status was set to RFR. 
						//tq = adjustTimeSecond(tq,1);
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
			addDebug("getTQFinalDTS for COMPATGENABRSTATUS has no changed history");
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
					//addDebug("getTQRFRDTS [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
					//	+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
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
						// In case of it was queued before status was set to RFR. 
						//tq = adjustTimeSecond(tq,1);
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
			addDebug("getTQRFRDTS for COMPATGENABRSTATUS has no changed history");
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
					//addDebug("getTQStatus [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
					//	+ " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
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
		String attCode = "COMPATGENABR";
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			return new AttributeChangeHistoryGroup(m_db, m_prof, att);
		} else {
			addDebug(" COMPATGENABR of "+rootEntity.getKey()+ "  was null");
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
			addDebug(" STATUS of "+rootEntity.getKey()+ "  was null");
			return null;
		}
	}
	
	/**********************************************************************************
	 * Checks if the current VE is subject to filtering.
	 */
	private boolean isVEFiltered(String name) {
		for (int i = 0; i < COMPATVE_Filter_Array.length; i++) {
			if (COMPATVE_Filter_Array[i][0].equals(name))
				return true;
		}
		return false;

	}
        /**********************************************************************************
	 * Checks if the current VE is subject to country filtering.
	 */
	private boolean isVECountryFiltered(String name) {
		for (int i = 0; i < VE_Country_Filter_Array.length; i++) {
			if (VE_Country_Filter_Array[i][0].equals(name))
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
		return "COMPATGENABR";
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

    /***************************************************************************
	 * deactivate the specified MultiFlag Attribute on the Root Entity
	 * 
	 * @param strAttributeCode
	 *            The Flag Attribute Code
	 */
	private void deactivateMultiFlagValue(String strAttributeCode) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException {
		logMessage(getDescription() + " ***** " + strAttributeCode);
		addDebug("deactivateFlagValue entered for " + strAttributeCode);
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (metaAttr == null) {
			addDebug("deactivateFlagValue: " + strAttributeCode + " was not in meta for " + rootEntity.getEntityType()
				+ ", nothing to do");
			logMessage(getDescription() + " ***** " + strAttributeCode + " was not in meta for " + rootEntity.getEntityType()
				+ ", nothing to do");
			return;
		}
		String strAttributeValue = PokUtils.getAttributeFlagValue(rootEntity, strAttributeCode);
		if (strAttributeValue != null) {
			try {
				ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);
				Vector vctReturnsEntityKeys = new Vector();
				Vector vctAtts = new Vector();
				EANAttribute att = rootEntity.getAttribute(strAttributeCode);
				if (att != null) {
					String efffrom = att.getEffFrom();
					ControlBlock cbOff = new ControlBlock(efffrom, efffrom, efffrom, efffrom, m_prof.getOPWGID());
					StringTokenizer st = new StringTokenizer(strAttributeValue, "|");
					while (st.hasMoreTokens()) {
						String s = st.nextToken();
						MultipleFlag mf = new MultipleFlag(m_prof.getEnterprise(), rootEntity.getEntityType(), rootEntity
							.getEntityID(), strAttributeCode, s, 1, cbOff);
						vctAtts.addElement(mf);
					}
					rek.m_vctAttributes = vctAtts;
					vctReturnsEntityKeys.addElement(rek);
					m_db.update(m_prof, vctReturnsEntityKeys, false, false);
					addDebug(rootEntity.getKey() + " had " + strAttributeCode + " deactivated");
				}

			} finally {
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after deactivate value");
			}
		}

	}
    
 
	protected boolean isPeriodicABR(){
		return isPeriodicABR;
	}
       
        private  void removeItem(EntityGroup eg, EntityItem ei) {
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
                                        // make sure the downside group is not already empty (they may have 
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
        }
        private HashSet getCountry(EntityItem ei, String attr) {
        	HashSet Set = new HashSet();
            // get current set of countries
            EANFlagAttribute fAtt = (EANFlagAttribute)ei.getAttribute(attr);
            addDebug("COMPATGENABRSTATUS.getCountry for entity "+ei.getKey()+" fAtt "+
                PokUtils.getAttributeFlagValue(ei, attr)+NEWLINE);
            if (fAtt!=null && fAtt.toString().length()>0){
                // Get the selected Flag codes.
                MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                for (int i = 0; i < mfArray.length; i++){
                    // get selection
                    if (mfArray[i].isSelected()){
                        Set.add(mfArray[i].getFlagCode());
                    }  // metaflag is selected
                }// end of flagcodes
            }
            return Set;
        }
        /**
         * Deactivate MODELCG MODELCGOS ...
         * @param deleteVct
         * @param deleteActionName
         * @throws MiddlewareRequestException
         * @throws SQLException
         * @throws MiddlewareException
         * @throws LockException
         * @throws MiddlewareShutdownInProgressException
         * @throws EANBusinessRuleException
         */
        private void deactivateEntities(EntityItem deleteItem) 
        throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
        MiddlewareShutdownInProgressException, EANBusinessRuleException
        {
        	String rootType = deleteItem.getEntityType();
        	String DeleteActionName = null;
        	if ("MODELCG".equals(rootType)){
        		DeleteActionName = MODELCG_DELETEACTION_NAME;
        	}else if ("SEOCG".equals(rootType)){
        		DeleteActionName = SEOCG_DELETEACTION_NAME;
        	}else if ("MDLCGOSMDL".equals(rootType)){
        		DeleteActionName = MDLCGOSMDL_DELETEACTION_NAME;
        	}else if ("SEOCGOSSEO".equals(rootType)){
        		DeleteActionName = SEOCGOSSEO_DELETEACTION_NAME;
        	}else if ("SEOCGOSBDL".equals(rootType)){
        		DeleteActionName = SEOCGOSBDL_DELETEACTION_NAME;
        	}else if ("SEOCGOSSVCSEO".equals(rootType)){
        		DeleteActionName = SEOCGOSSVCSEO_DELETEACTION_NAME;
        	}
     	
        	DeleteActionItem dai = new DeleteActionItem(null, m_db,m_prof, DeleteActionName);

        	EntityItem childArray[] = new EntityItem[1];
       
        	childArray[0] = deleteItem;
        	addDebug("deleteEntity "+deleteItem.getKey()+" "+childArray[0].getKey());
        	long startTime = System.currentTimeMillis();

        	// do the delete
        	dai.setEntityItems(childArray);
        	m_db.executeAction(m_prof, dai);

    	    addDebug("Time to delete : " + Stopwatch.format(System.currentTimeMillis()-startTime));
        }
        /**
         * Check the rootEntity whether in the case of Delete.
         * @param rootEntity
         * @return
         */
        private boolean isDeactivatedEntity(EntityItem rootEntity) {
        	boolean result = false;
        	String rootType = rootEntity.getEntityType();
        	if ("MODELCG".equals(rootType) || "SEOCG".equals(rootType) ){
        		String OKTOPUB = PokUtils.getAttributeValue(rootEntity, "OKTOPUB",", ", XMLElem.CHEAT, false);
        		if ("Delete".equals(OKTOPUB)){
        			result = true;
        		}
        	}else if ("MDLCGOSMDL".equals(rootType) || "SEOCGOSSEO".equals(rootType) || "SEOCGOSBDL".equals(rootType) || "SEOCGOSSVCSEO".equals(rootType)){
        		String COMPATPUBFLG = PokUtils.getAttributeValue(rootEntity, "COMPATPUBFLG",", ", XMLElem.CHEAT, false);
        		if ("Delete".equals(COMPATPUBFLG)){
        			result = true;
        		}
        	}
        	return result;
        	
        }
}
