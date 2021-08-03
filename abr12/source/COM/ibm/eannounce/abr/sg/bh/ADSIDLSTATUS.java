//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office. 

package COM.ibm.eannounce.abr.sg.bh;

import java.io.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.*;

import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

import com.ibm.transform.oim.eacm.util.PokUtils;
/****
 * BH FS ABR XML IDL 20101027.doc
 * need meta
 * 		missing XMLENTITYTYPE flags for Deletes and XLATE
 * 		ADSXMLSETUP missing XMLABRPROPFILE and XMLIDLABRSTATUS
 * 		ADSXMLSETUP.ADSTYPE missing flags for Deletes and XLATE
 * 
 * from BH FS ABR XML IDL 20101007.doc
 * This ABR queues the Data Transformation System Feed (aka XML feed) 
 *  
 * IV.	Background 
 * EACM feeds downstream systems via a set of XML messages. There exists a need to perform an Initial 
 * Data Load (IDL) for new downstream systems. There may also be a need to refresh a downstream system 
 * via an IDL based on a downstream system request.
 * 
 * V.	User Interface
 * 
 * An authorized user (Role = BHFEED) will setup an IDL request via a setup entity and then queue this 
 * ABR via a workflow action. This ABR will then queue XML message generation for offering information 
 * that meets the criteria specified via the setup entity.
 * 
 * VI.	Setup Entity
 * The XML IDL Setup Entity(EXTXMLIDL) is defined as follows:
 * 
 * Attribute Code	Type	Long Description		Applicable
 * XMLSETUPDESC		T		XML IDL Description	 
 * XMLABRPROPFILE	F		Name of ABR Properties File
 * XMLTARGETSYSTEM	L		Target System Name
 * XMLENTITYTYPE	U		Root Entity Type
 * FCTYPE*			U		Feature Type			FEATURE
 * COFCAT*			U		Model Category			MODEL
 * COFGRP			U		Model Group				MODEL |LSEO
 * COFSUBCAT		U		Model Subcategory		MODEL
 * MACHTYPEATR*		U		Machine Type			PRODSTRUCT |SWPRODSTRUCT |MODELCONVERT |FCTRANSACTION
 * MODELATR*		T		Model					PRODSTRUCT |SWPRODSTRUCT
 * SVCMODCATG*		U		Service Model Categoy	SVCMOD
 * SVCMODGRP*		U		Service Model Group		SVCMOD
 * SVCMODSUBCATG*	U		Service Model SubCategoy	SVCMOD
 * SVCMODSUBGRP*	U		Service Model SubGroup	SVCMOD
 * none				U	 	SWFEATURE |GENERALAREA |SVCLEV |SLEORGNPLNTCODE |REVUNBUNDCOMP |CATNAV | GBT |WARR | XLATE
 * WITHDRAWDATEEFF_T	T	Global Withdrawal Date Effective	FEATURE |SWFEATURE
 * LSEOUNPUBDATEMTRGT	T	LSEO Unpublish Date - Target	LSEO
 * BUNDLUNPUBDATEMTRGT	T	Bundle Unpublish Date - Target	LSEOBUNDLE
 * WTHDRWEFFCTVDATE	T	Withdrawal Effective Date	MODEL |PRODSTRUCT |SWPRODSTRUCT |MODELCONVERT |FCTRANSACTION
 * ADSIDLSTATUS		A		ADS IDL XML Feed ABR
 * BRANDCD			U		Brand Code				WWCOMPAT
 * PDHDOMAIN **		F		Domains


ADSIDLSTATUS_SUBSCRVE=WWDERDATASNVE
ADSIDLSTATUS_CAT1: RptClass. XMLIDLABR
ADSIDLSTATUS_CAT2: EXTXMLIDL.PDHDOMAIN
ADSIDLSTATUS_CAT3: RptStatus
ADSIDLSTATUS_CAT4: 
-----------------------------

ADSIDLSTATUS_class=COM.ibm.eannounce.abr.sg.bh.ADSIDLSTATUS
ADSIDLSTATUS_enabled=true
ADSIDLSTATUS_idler_class=D
ADSIDLSTATUS_keepfile=true
ADSIDLSTATUS_read_only=false
ADSIDLSTATUS_vename=dummy
ADSIDLSTATUS_CAT1=RPTCLASS.XMLIDLABR
ADSIDLSTATUS_CAT2=ROOTTYPE.PDHDOMAIN
ADSIDLSTATUS_CAT3=RPTSTATUS
ADSIDLSTATUS_CAT4=
ADSIDLSTATUS_SUBSCRVE=WWDERDATASNVE
ADSIDLSTATUS_splimit=200000
 */
//$Log: ADSIDLSTATUS.java,v $
//Revision 1.27  2019/02/26 07:18:09  xujianbo
//roll back code for Story 1909631 EACM XML version control by XSLT
//
//Revision 1.25  2015/08/13 08:43:27  wangyul
//Remove semicolon after "with ur;" of the query to fix the DB2 SQL Error: SQLCODE=-104, SQLSTATE=42601, SQLERRMC=;;
//
//Revision 1.24  2014/01/17 11:24:20  wangyulo
//the ABR changes needed to comply with V17 standards
//
//Revision 1.23  2014/01/07 13:06:11  guobin
//Fix CR of BH FS ABR XML System Feed 20130904.doc
//
//Revision 1.22  2013/08/12 13:35:30  liuweimi
// IDL - change to add Division filter to XML product feed
//
//Revision 1.21  2013/03/29 06:47:24  wangyulo
//support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
//Revision 1.20  2011/12/06 22:10:34  liuweimi
//fix - Errors encountered in Cache load
//
//Revision 1.19  2011/10/28 03:18:04  liuweimi
//Delta cache and special filter for MODEL
//
//Revision 1.18  2011/10/13 14:40:39  liuweimi
//*** empty log message ***
//
//Revision 1.15  2011/10/13 13:28:45  guobin
//fix the  code problem
//
//Revision 1.12  2011/09/27 01:29:25  guobin
//corrected filtering. Corrected REFOFER & REFOFERFEAT.
//
//Revision 1.11  2011/08/19 07:20:43  guobin
//filter for REFOFER  and REFOFERFEAT
//
//Revision 1.10  2011/07/20 13:27:37  guobin
//Change current IDL to support Delta IDL based on document - BH FS ABR XML IDL 20110707.doc.
//
//Revision 1.9  2011/07/07 13:28:20  guobin
//add XMLSTATUS as filter condition
//
//Revision 1.8  2011/07/06 13:46:13  guobin
//filter status <> 0010 when entitytype is SWPRODSTRUCT
//
//Revision 1.7  2011/06/23 12:34:13  guobin
//Changes to EXTXMLFEED to filter marked entities by STATUS value
//
//Revision 1.6  2011/06/13 14:39:03  guobin
//modelconvert and fctransaction add modelatr IDL filter
//
//Revision 1.5  2011/06/10 07:43:00  guobin
//update IDL function
//
//Revision 1.4  2011/06/07 14:16:02  guobin
//change to new IDL put and get xml to  Cache table
//
//Revision 1.3  2010/11/16 15:59:45  wendy
//add support for periodic types
//
//Revision 1.2  2010/10/27 20:02:42  wendy
//updates for BH FS ABR XML IDL 20101027.doc, still need meta to complete, added defer post
//
//Revision 1.1  2010/10/11 19:21:30  wendy
//Init for BH FS ABR XML IDL 20101007.doc
//
public class ADSIDLSTATUS extends PokBaseABR {
	private static final String ADSIDLSTATUS = "ADSIDLSTATUS";
	private static final String ADSIDLLASTRANDTS = "ADSIDLLASTRANDTS";
	private static final int MAXFILE_SIZE=5000000;
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];

	private static final String ADS_XMLEED_ATTR="XMLIDLABRSTATUS"; // attr to queue
	private static final String QUEUE_ATTR ="SYSFEEDRESEND";
	private static final String QUEUE_VALUE ="CUR";
	private static final String MQUEUE_ATTR="XMLABRPROPFILE";  // attr to put xml mq info into
	private static boolean IS_SYSFEEDRESEND_CUR = false;
    //private static final String COFGRP_Base= "150";//COFGRP defaults to(150) if not specified

	// these are used in the search sps and the trsnav table
	// entitytype will be appended to make each entity srch key unique
	//private static final String SEARCHREL_KEY = "ADSIDLREL"; 
	private static final String SEARCH_KEY = "ADSIDL";
	
	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";
	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private int dbgLen=0; 
    private int abr_debuglvl=D.EBUG_ERR;
	private Vector vctReturnsEntityKeys = new Vector();
	private Vector vctReturnsQueueKeys = new Vector();
	

	private static final Set TBD_SET;	//Entity types in spec but not yet handled
	private static final Hashtable FILTER_TBL;	//Entity and filter Attributes for IDL
	private static final Hashtable FILTERCACHE_TBL; //Entity and filter Attributes for Cache
	private static final Hashtable REQFILTER_TBL;	//Entity and required filter Attributes for ADSIDLSTATUS
	private static final Hashtable PERIODIC_TBL;	//Entity and ADSTYPE flag code needed for search
	static{
		FILTER_TBL = new Hashtable();
		FILTERCACHE_TBL = new Hashtable();
		REQFILTER_TBL = new Hashtable();
		PERIODIC_TBL = new Hashtable();
		TBD_SET = new HashSet();
	    
		//they must be in ATTRCODE|type format where type is T for text, U for flag and D for date 
		FILTER_TBL.put("FEATURE", new String[]{"FCTYPE|U","COUNTRYLIST|F","WITHDRAWDATEEFF_T|D","WITHDRAWDATEMIN|D"});
		FILTER_TBL.put("MODEL", new String[]{"SPECBID|U","COFCAT|U","COFSUBCAT|U","COFGRP|U","COFSUBGRP|U","COUNTRYLIST|F","FLFILSYSINDC|F","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D","DIVTEXT|T"});
		FILTER_TBL.put("SVCMOD", new String[]{"SVCMODCATG|U","SVCMODGRP|U","SVCMODSUBCATG|U","SVCMODSUBGRP|U","COUNTRYLIST|F","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D","DIVTEXT|T"});
		FILTER_TBL.put("LSEOBUNDLE", new String[]{"SPECBID|U","BUNDLETYPE|F","COUNTRYLIST|F","FLFILSYSINDC|F","BUNDLUNPUBDATEMTRGT|D","WITHDRAWDATEMIN|D","DIVTEXT|T"});
		FILTER_TBL.put("SWFEATURE", new String[]{"FCTYPE|U","WITHDRAWDATEEFF_T|D","WITHDRAWDATEMIN|D"});
		//FILTER_TBL.put("WWCOMPAT", 	new String[]{"BRANDCD|U"}); //WWCOMPAT doesnt exist

		//COFGRP will be on the MODEL
		FILTER_TBL.put("LSEO", new String[]{"SPECBID|U","COFCAT|U","COFSUBCAT|U","COFGRP|U","COFSUBGRP|U","COUNTRYLIST|F","FLFILSYSINDC|F","LSEOUNPUBDATEMTRGT|D","WITHDRAWDATEMIN|D","DIVTEXT|T"});

		//MACHTYPEATR used for TOMACHTYPE T, WTHDRWEFFCTVDATE will be on the MODEL
		FILTER_TBL.put("MODELCONVERT", new String[]{"MACHTYPEATR|U","MODELATR|T","COUNTRYLIST|F","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D"});
		FILTER_TBL.put("FCTRANSACTION", new String[]{"MACHTYPEATR|U","MODELATR|T","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D"});

		//these attributes will be on the MODEL  (SW)PRODSTRUCT-d: MODEL.WTHDRWEFFCTVDATE
		FILTER_TBL.put("PRODSTRUCT", new String[]{"FCTYPE|U","MACHTYPEATR|U","MODELATR|T","COUNTRYLIST|F","FLFILSYSINDC|F","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D"});
		FILTER_TBL.put("SWPRODSTRUCT", new String[]{"FCTYPE|U","MACHTYPEATR|U","MODELATR|T","COUNTRYLIST|F","WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|D"});
		
		FILTER_TBL.put("IMG", new String[]{"COUNTRYLIST|F"});
		FILTER_TBL.put("CATNAV", new String[]{"FLFILSYSINDC|F"});
		
		//elaborated on REFOFER filtering
		//FILTER_TBL.put("REFOFER", new String[]{"COUNTRYLIST|F","ENDOFSVC|T"});
		//FILTER_TBL.put("REFOFERFEAT", new String[]{"COUNTRYLIST|F","ENDOFSVC|T"});
		
		
		//Entity and filter Attributes for Cache,delete withdrawn date based on BH FS ABR XML IDL 20110929.doc, only EndofSvc need to be filtered
		FILTERCACHE_TBL.put("FEATURE", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("MODEL", new String[]{"ADSIDLLASTRANDTS|T"});
	    FILTERCACHE_TBL.put("SVCMOD", new String[]{"ADSIDLLASTRANDTS|T"});
	    FILTERCACHE_TBL.put("LSEOBUNDLE", new String[]{"ADSIDLLASTRANDTS|T"});
	    FILTERCACHE_TBL.put("SWFEATURE", new String[]{"ADSIDLLASTRANDTS|T"});
	    FILTERCACHE_TBL.put("LSEO", new String[]{"ADSIDLLASTRANDTS|T"});
	    FILTERCACHE_TBL.put("MODELCONVERT", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("FCTRANSACTION", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("PRODSTRUCT", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("SWPRODSTRUCT", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("SLEORGNPLNTCODE", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("CATNAV", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("GBT", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("REVUNBUNDCOMP", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("SVCLEV", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("WARR", new String[]{"ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("IMG", new String[]{"ADSIDLLASTRANDTS|T"});
		
		FILTERCACHE_TBL.put("REFOFER", new String[]{"ENDOFSVC|D","ADSIDLLASTRANDTS|T"});
		FILTERCACHE_TBL.put("REFOFERFEAT", new String[]{"ENDOFSVC|D","ADSIDLLASTRANDTS|T"});
	    				
	    
	    
		//COFGRP defaults to (150) if not specified
		//REQFILTER_TBL.put("FEATURE", new String[]{"FCTYPE"});
		//REQFILTER_TBL.put("MODEL", new String[]{"COFCAT"});
		//REQFILTER_TBL.put("SVCMOD", new String[]{"SVCMODCATG","SVCMODGRP","SVCMODSUBCATG","SVCMODSUBGRP"});
		//MODELCONVERT and FCTRANSACTION whether require MACHTYPEATR
		//REQFILTER_TBL.put("MODELCONVERT", new String[]{"MACHTYPEATR|U"});
		//REQFILTER_TBL.put("FCTRANSACTION", new String[]{"MACHTYPEATR|U"});
		//REQFILTER_TBL.put("PRODSTRUCT", new String[]{"MACHTYPEATR","MODELATR"});
		//REQFILTER_TBL.put("SWPRODSTRUCT", new String[]{"MACHTYPEATR","MODELATR"});
		
		// none of these are ready yet in the spec, others are TBD like GBT, IMG and WARR, but they are real entity types
		TBD_SET.add("WWCOMPAT");// this is derived
		// these are periodic
		//ADSTYPE	10	Service Model
		//ADSTYPE	20	GENERALAREA
		//ADSTYPE	30	Deletes
		//ADSTYPE	40	XLATE

		PERIODIC_TBL.put("GENERALAREA", "20");
		PERIODIC_TBL.put("Deletes", "30"); 
		PERIODIC_TBL.put("XLATE", "40");
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
	private void closePrintWriter() {
		if (dbgPw != null){
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
	}
	/**********************************
	 *  Execute ABR.
	 */
	public void execute_run()
	{
		/*
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
			Any errors or list LSEO created or changed
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
		"<tr><th>Return code: </th><td>{5}</td></tr>"+NEWLINE +
		"</table>"+NEWLINE+
		"<!-- {6} -->" + NEWLINE;

		MessageFormat msgf;
		String abrversion="";

		println(EACustom.getDocTypeHtml()); //Output the doctype and html

		try
		{
			long startTime = System.currentTimeMillis();
			start_ABRBuild(); // pull dummy VE

		    abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());
		       
			setupPrintWriter();

			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			// get root from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			// debug display list of groups
			addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
					" extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			//Default set to pass
			setReturnCode(PASS);
//			fixme remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			//NAME is navigate attributes
			navName = getNavigationName(rootEntity);

			// get entitytype, it is U but get the long description
			String entityType = PokUtils.getAttributeValue(rootEntity, "XMLENTITYTYPE", "", null, false);
			String setupType = PokUtils.getAttributeValue(rootEntity, "XMLSETUPTYPE", "", null, false);
			String mqueue = PokUtils.getAttributeFlagValue(rootEntity, MQUEUE_ATTR);
			
			addDebug("Executing for entityType: "+entityType+" mqueue: "+mqueue + " setupType: " + setupType);

			
			if (setupType==null){
				args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "XMLSETUPTYPE", "XMLSETUPTYPE");
				addError("INVALID_ATTR_ERR",args);
				
			}else if ("Production".equals(setupType)){
				args[0] = "Production";
				addError("INVALID_SETUPTYPE_ERR",args);
			}else if (entityType==null) {
				//INVALID_ATTR_ERR = {0} does not have a value
				args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "XMLENTITYTYPE", "XMLENTITYTYPE");
				addError("INVALID_ATTR_ERR",args);
			}else if (TBD_SET.contains(entityType)){
				//NOT_SUPPORTED = {0} is not supported at this time.
				args[0] = getLD_Value(rootEntity, "XMLENTITYTYPE"); 
				addError("NOT_SUPPORTED",args);
			}else{
				String periodicFlag = (String)PERIODIC_TBL.get(entityType);
				String tmptype = entityType;
				String arg0 = "";
				if(periodicFlag != null){ // these are periodic
					tmptype = "ADSXMLSETUP";
					arg0 = tmptype+" for "+entityType;
				}else{
					arg0 = getLD_Value(rootEntity, "XMLENTITYTYPE");
				}
				
				EntityGroup eg = new EntityGroup(null,m_db, m_prof, tmptype, "Edit", false);

				// check if it has an XMLIDLABRSTATUS attr
				// if meta does not have this attribute, there is nothing to do
				EANMetaAttribute metaAttr = eg.getMetaAttribute(ADS_XMLEED_ATTR);
				if (metaAttr==null) {
					//INVALID_META_ERR = {0} does not have {1} attribute in meta
					args[0] = arg0;
					args[1] = ADS_XMLEED_ATTR;
					addError("INVALID_META_ERR",args);
				}
				metaAttr = eg.getMetaAttribute(MQUEUE_ATTR);
				if (metaAttr==null) {
					//INVALID_META_ERR = {0} does not have {1} attribute in meta
					args[0] = arg0;
					args[1] = MQUEUE_ATTR;
					addError("INVALID_META_ERR",args);
				}
			}
			
			if ("IDL".equals(setupType) && mqueue==null) {
				//INVALID_ATTR_ERR = {0} does not have a value
				args[0] = getLD_Value(rootEntity,MQUEUE_ATTR); 
				addError("INVALID_ATTR_ERR",args);
			}
			
			if ("Cache".equals(setupType) && (String)PERIODIC_TBL.get(entityType) != null) {
				//INVALID_ATTR_ERR = {0} does not have a value
				args[0] = entityType; 
				addError("INVALID_PERIODIC_ERR",args);
			}
			
			if ("Cache current".equals(setupType) && (String)PERIODIC_TBL.get(entityType) != null) {
				//INVALID_ATTR_ERR = {0} does not have a value
				args[0] = entityType; 
				addError("INVALID_PERIODIC_ERR",args);
			}

			if (getReturnCode()==PASS){
                if ("Cache".equals(setupType) || "Cache current".equals(setupType)){
                	if("Cache current".equals(setupType)) {
                		IS_SYSFEEDRESEND_CUR = true;
                		addDebug("case 1 IS_SYSFEEDRESEND_CUR= "+IS_SYSFEEDRESEND_CUR+";setupType="+setupType);
                	}else{
                		IS_SYSFEEDRESEND_CUR = false;
                		addDebug("case 2 IS_SYSFEEDRESEND_CUR= "+IS_SYSFEEDRESEND_CUR + ";setupType=" + setupType);
                	}
                	
                	//addDebug("@@@@ IS_SYSFEEDRESEND_CUR= "+IS_SYSFEEDRESEND_CUR);
                	
                	long curtime = System.currentTimeMillis();
    				// find all ids for this root type and filters
    				Vector idsVct = getEntityIds(rootEntity,entityType);
    				if (getReturnCode()==PASS){
    					String periodicFlag = (String)PERIODIC_TBL.get(entityType);
    					String tmptype = entityType;
    					if(periodicFlag != null){ // these are periodic
    						tmptype = "ADSXMLSETUP with "+entityType;
    						entityType = "ADSXMLSETUP";
    					}
    					addDebug("Time to find entity ids: "+Stopwatch.format(System.currentTimeMillis()-curtime));
    					addDebug("Update these entity ids.cnt: "+(idsVct==null?"null":""+idsVct.size()));
    					addDebug(D.EBUG_INFO,"Update these ids: "+idsVct);
    					curtime = System.currentTimeMillis();
    					String mqFlags[] = PokUtils.convertToArray(mqueue);
    					// queue any ids found
    					if(idsVct !=null && idsVct.size()>0){
    						String queuedValue = getQueuedValue(ADS_XMLEED_ATTR);
    						for (int i=0; i<idsVct.size(); i++){
    							Integer eid = (Integer)idsVct.elementAt(i);  
    							//add for the SYSFEEDRESEND Current
    							if(IS_SYSFEEDRESEND_CUR) setQueueValues(entityType, eid.intValue());
    							setValues(mqFlags, queuedValue, entityType, eid.intValue());
    							
    							// this entity does not put it on the queue.. the ADS abr does
    							// so it must copy this mq info into the entity.. the ADS abr must clear this attr!!!!!
    							if(vctReturnsEntityKeys.size()>=500){
    								int cnt = vctReturnsEntityKeys.size();
    								//add for the SYSFEEDRESEND Current
    								if(IS_SYSFEEDRESEND_CUR) updatePDHQueue();
    								// update the pdh 
    								updatePDH();
    								
    								long curtime2 = System.currentTimeMillis();
    								addDebug("Time to update "+cnt+" "+entityType+": "+Stopwatch.format(curtime2-curtime));
    								curtime = curtime2;
    							}
    						}

    						if(vctReturnsEntityKeys.size()>0){
    							int cnt = vctReturnsEntityKeys.size();    							 
    							//add for the SYSFEEDRESEND Current
    							if(IS_SYSFEEDRESEND_CUR) updatePDHQueue();
    							// update the pdh
    							updatePDH();    							
    							addDebug("Time to update "+cnt+
    									" "+entityType+": "+Stopwatch.format(System.currentTimeMillis()-curtime));
    						}
    						idsVct.clear();
    					}else{
    						//NONE_FOUND = No {0} entities found meeting specified criteria
    						args[0] = tmptype;
    						addMessage("", "NONE_FOUND", args);
    					}
    					// set lastrunning time back to setup entity,must get the refresh date     						
						EANAttribute att = rootEntity.getAttribute(ADSIDLSTATUS);
						if(att !=null){
							AttributeChangeHistoryGroup attrHistory = new AttributeChangeHistoryGroup(m_db, m_prof, att);
							if(attrHistory != null && attrHistory.getChangeHistoryItemCount() > 0){
								int count = attrHistory.getChangeHistoryItemCount();
								AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) attrHistory.getChangeHistoryItem(count - 1);
								if(achi != null){
									String status = achi.getFlagCode();
		    						addDebug("checking ADSIDLSTATUS :" +status );
		    						if("0050".equals(status)){
		    							String valfrom = achi.getChangeDate();
		    							this.setTextValue(rootEntity, ADSIDLLASTRANDTS, valfrom);
		    							addDebug("set lastrunning time back to setup entity :" +valfrom );
		    						}
								}
							}
						}
    				}// no errors after parsing attributes
             }else if ("IDL".equals(setupType)){
            	Vector allowedVct = new Vector();
				allowedVct.add("PDHDOMAIN");  // always allowed
				allowedVct.add(ADSIDLSTATUS); 
				allowedVct.add("XMLENTITYTYPE"); 
				allowedVct.add("XMLABRPROPFILE");
				allowedVct.add("XMLSETUPTYPE"); // "Production" | "IDL" | "Cache" | "Cache current"
				allowedVct.add("XMLSETUPDESC"); 
				allowedVct.add("XMLTARGETSYSTEM");
				allowedVct.add("XMLVERSION");
				allowedVct.add("XMLMOD");
				allowedVct.add("XMLSTATUS");
				allowedVct.add("XMLIDLREQDTS");
				allowedVct.add("XMLIDLMAXMSG");
				allowedVct.add("OLDINDC");
				
				String filters[] = (String[])FILTER_TBL.get(entityType);
				if (filters!=null){
					// get each attribute and type
					for (int i=0; i<filters.length; i++){
						String codetype[] = PokUtils.convertToArray(filters[i]);
						String attrcode = codetype[0];
//						String attrtype = codetype[1];
//						String value=null;
						allowedVct.add(attrcode);
						
					}
					checkExtraAttrs(rootEntity, entityType, allowedVct);
				}
				if(getReturnCode()==PASS){
            	 //TODO
					XMLFiterMQIDL idl = new XMLFiterMQIDL(this);
					idl.getFullXmlAndSendToQue(rootEntity);
					String report = idl.getReport();
					addOutput(report);
					
				}
             }
				
			addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
		
			}
		}catch(Throwable exc) {
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
		finally	{
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriter();
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getDescription();
		args[1] = navName;
		String header1 = msgf.format(args);
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		args[4] = navName;
		args[5] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
		args[6] = abrversion+" "+getABRVersion();

		restoreXtraContent();

		rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		metaTbl.clear();
	}
	/**
	 * B.	Attributes for Filtering Data
	 * 
	 * The rest of the attributes are used as filters. The column labeled Classified Filter indicates the 
	 * entity type for which the filter attribute is applicable. For example, Feature Type (FCTYPE) is only 
	 * applicable for Feature (FEATURE).
	 * 
	 * The attributes FCTYPE through SVCMODSUBGRP are used to filter data if there is a value specified. 
	 * The value specified must match the value in the data in order for the data to be queued. If a value 
	 * is not specified (empty), then it does not participate in filtering the data.
	 * 
	 * Note: MACHTYPEATR is used to filter on TOMACHTYPE for MODELCONVERT and FCTRANSACTION.
	 * 
	 * Similarly, the date attributes WITHDRAWDATEEFF_T through WITHDRWEFFCTVDATE, if not empty, are used to 
	 * filter data that is withdrawn. If the data being considered does not have a value for this attribute, 
	 * then the data is not withdrawn and will be included. If the data being considered has a value, then 
	 * the setup entity date must be less than or equal to the date specified for the data in order to 
	 * be considered. This allows, for example, data to be sent even though it was withdrawn 90 days ago.
	 * If the setup entity date field is empty, then assume a value of NOW().
	 * 
	 * Note: There are entities (XMLENTITYTYPE) that do not have filtering attributes. For example, 
	 * GBT does not have any filtering other than PDHDOMAIN.
	 * 
	 * @param rootItem
	 * @param entityType
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws MiddlewareShutdownInProgressException 
	 * 
	 */
	private Vector getEntityIds(EntityItem rootItem,String entityType) throws MiddlewareException, 
	SQLException, MiddlewareShutdownInProgressException
	{
		Vector idVct = null;
		Vector allowedVct = new Vector();
		allowedVct.add("PDHDOMAIN");  // always allowed
		allowedVct.add(ADSIDLSTATUS); 
		allowedVct.add("XMLENTITYTYPE"); 
		allowedVct.add("XMLABRPROPFILE");
		allowedVct.add("XMLSETUPTYPE"); // "Production" | "IDL" | "Cache"
		allowedVct.add("XMLSETUPDESC"); 
		allowedVct.add("XMLTARGETSYSTEM");
		allowedVct.add("XMLVERSION");
		allowedVct.add("XMLMOD");

		Vector txtValuesVct = new Vector(); 
		Vector txtAttrVct = new Vector();
		Vector flagValuesVct = new Vector(); 
		Vector flagAttrVct = new Vector();
		
		// build a pdhdomain vct, filter sps need something
		Vector domainVct = new Vector();
		String domainStr = PokUtils.getAttributeFlagValue(rootItem, "PDHDOMAIN");
		if (domainStr != null){
			String domains[] = PokUtils.convertToArray(domainStr);
			for(int i=0; i<domains.length; i++){
				domainVct.add(domains[i]);
			}
		}
		

		addDebug("domainVct "+domainVct);
		
		String periodicFlag = (String)PERIODIC_TBL.get(entityType);
		if(periodicFlag != null){ // these are periodic
			// make sure other attrs are not specified
			checkExtraAttrs(rootItem, entityType, allowedVct);
			if(getReturnCode()==PASS){
				flagValuesVct.add(periodicFlag);
				flagAttrVct.add("ADSTYPE");
				addDebug("executing on entityType ADSXMLSETUP for "+entityType+" with flagAttrVct "+flagAttrVct+
						" flagValuesVct "+flagValuesVct);
				
				rptSb.append("<br /><h2>Looking for ADSXMLSETUP with the following filters:<br />");
				rptSb.append("ADSTYPE = "+periodicFlag+" for "+entityType+"<br />");
				String domain = PokUtils.getAttributeValue(rootItem, "PDHDOMAIN", " or ", null, false);
				rptSb.append(PokUtils.getAttributeDescription(rootItem.getEntityGroup(), "PDHDOMAIN", "PDHDOMAIN")+
						" = "+domain+"<br />");

				rptSb.append("</h2>"+NEWLINE);
				
				idVct = new Vector();
				// do one domain at a time
				for (int d=0; d<domainVct.size(); d++){
					getMatchingTextAndFlagIds("ADSXMLSETUP", txtValuesVct, txtAttrVct, 
							flagValuesVct, flagAttrVct, idVct, domainVct.elementAt(d).toString());
				}
			}
		}else{
			// get filters
			String filters[] = (String[])FILTERCACHE_TBL.get(entityType);

			String dateAttr = null;
			String dateValue = null;
	
			// add any filters
			if (filters!=null){
				// get each attribute and type
				for (int i=0; i<filters.length; i++){
					String codetype[] = PokUtils.convertToArray(filters[i]);
					String attrcode = codetype[0];
					String attrtype = codetype[1];
					String value=null;
					allowedVct.add(attrcode);
					
					if (attrtype.equals("T")){
						value = PokUtils.getAttributeValue(rootItem, attrcode, "", null, false);
						if (value!=null && value.length()>0){
							txtAttrVct.add(attrcode);
							txtValuesVct.add(value);
						}else if (isRequired(entityType, attrcode)){
							//INVALID_FILTER_ERR = {0} is a required filter for {1} but does not have a value
							args[0] = attrcode; 
							args[1] = entityType;
							addError("INVALID_FILTER_ERR",args);
						}
					}else if (attrtype.equals("D")){// there is only one
						// update through withdreweffectivedate to endofsvc, If the value in the EXTXMLFEED is empty, then all are used. updates of System Feed 20110908.doc
						value = PokUtils.getAttributeValue(rootItem, attrcode, "", null, false);
						dateAttr = attrcode;
						if (value==null || value.length()==0){
							value = m_strEpoch.substring(0,10);
						}
						dateValue = value;
					}else{
						value = PokUtils.getAttributeFlagValue(rootItem, attrcode);
						if (value!=null && value.length()>0){
							//MODELCONVERT = MACHTYPEATR|U are used as TOMACHTYPE|T
							//FCTRANSACTION = MACHTYPEATR|U
//							if(attrcode.equals("MACHTYPEATR")&&
//									(entityType.equals("MODELCONVERT") || entityType.equals("FCTRANSACTION"))){
//								txtAttrVct.add("TOMACHTYPE");
//								txtValuesVct.add(value);
//							}else{
								flagAttrVct.add(attrcode);
								flagValuesVct.add(value);
//							}
						}else if (isRequired(entityType, attrcode)){
							//INVALID_FILTER_ERR = {0} is a required filter for {1} but does not have a value
							args[0] = attrcode; 
							args[1] = entityType;
							addError("INVALID_FILTER_ERR",args);
						}
//						if(attrcode.equals("COFGRP") && entityType.equals("LSEO")){
//							// because of dependencies, these must be specified, so allow them
//							allowedVct.add("COFCAT");
//							allowedVct.add("COFSUBCAT");
//						}
					}

					addDebug("entitytype "+entityType+" attrcode "+attrcode+" value "+value);
				}
			}
			
			checkExtraAttrs(rootItem, entityType, allowedVct);

			if(getReturnCode()==PASS){

				addDebug("executing on entityType "+entityType+" with flagAttrVct "+flagAttrVct+
						" flagValuesVct "+flagValuesVct+" txtAttrVct "+txtAttrVct+" txtValuesVct "+
						txtValuesVct+" dateAttr "+dateAttr+" dateValue "+dateValue);
				
				addHeadingInfo(rootItem,entityType,txtAttrVct,flagAttrVct,dateAttr, dateValue);
				//add lastrunning time to support delta initial cache
				String lastrunningTime = PokUtils.getAttributeValue(rootItem, ADSIDLLASTRANDTS, "|", null, false);
				//check lastrunningTime format
				if(lastrunningTime != null && !Validate.isoDate(lastrunningTime)){
					//INVAILD_DATE_ERR = {0} is not allowed for this format {1}
					args[0] = ADSIDLLASTRANDTS; 
					addError("INVAILD_DATE_ERR",args);
				}
				//TODO change the getFilteredXXXSql
				/**
				 * base on the BH FS ABR XML System Feed 20130904.doc Page 60
				 * If the root entity being considered has STATUS <> 鈥淒raft鈥� (0010), then
						o	If ADSIDLLASTRANDTS is not empty, 
								then find VALFROM for the most recent value 鈥淪tatus鈥� (STATUS). 
								If the VALFROM of STATUS > ADSIDLLASTRANDTS 
						飩�			then set entitytype.XMLIDLABRSTATUS = 鈥淨ueued鈥� (0020) for the root entity being considered.
						飩�		Else, skip this entity (do not queue XMLIDLABRSTATUS)
						o	Else Sets entitytype.XMLIDLABRSTATUS = 鈥淨ueued鈥� (0020)
							where entitytype is the root entitytype
				 */
				long startTime = System.currentTimeMillis();
				String timetxt="Time to filter "+entityType+": ";
				if("MODEL".equals(entityType)){
					String sql = null;
					sql = getFilteredModelSql(entityType, domainVct, lastrunningTime);
					if (sql != null){
						idVct = getMatchingIds(sql);
					} 
				
					timetxt="Time to filter on "+entityType+" with MODEL text and flags: ";
				}else if (entityType.equals("REFOFER")){				
					if (dateAttr!=null){
						String sql = null;
						sql = getFilteredDateSql(entityType, dateAttr, dateValue, domainVct, lastrunningTime);
						if (sql != null){
							idVct = getMatchingIds(sql);
						} 
					}
					timetxt="Time to filter on "+entityType+" with REFOFER text and flags: ";
				}else if (entityType.equals("REFOFERFEAT")){
//					 must match attr on the MODEL
					if (dateAttr!=null){
						String sql = null;
						sql = getFilteredREFOFERFEATDateSql(entityType, dateAttr, dateValue, domainVct, lastrunningTime);
						if (sql != null){
							idVct = getMatchingIds(sql);
						} 
					}
					timetxt="Time to filter on "+entityType+" with reference REFOFER text and flags: ";
					
				}else{
//					// do one domain at a time
//					for (int d=0; d<domainVct.size(); d++){
//						getMatchingTextAndFlagIds(entityType, txtValuesVct, txtAttrVct, 
//								flagValuesVct, flagAttrVct, tmpidVct, domainVct.elementAt(d).toString());
//					}
					String sql = null;
					sql = getFilteredSql(entityType, domainVct, lastrunningTime);
					if (sql != null){
						idVct = getMatchingIds(sql);
					}
					timetxt="Time to filter on root "+entityType+" PDHDomain and WithdrawEffectiveDate: ";
				}

				long curtime = System.currentTimeMillis();
				addDebug(timetxt+Stopwatch.format(curtime-startTime));
			}// end all ok
		}
		
		//release memory
		txtValuesVct.clear();
		txtAttrVct.clear();
		flagValuesVct.clear();
		flagAttrVct.clear();
		domainVct.clear();
		
		return idVct;
	}

	/**
	 * make sure extra attributes are not specified for this entitytype
	 * 
	 * Classification is not supported by eAnnounce for attributes used on a second entity type. Hence the
	 *  ABR will have to handle thepplicable via code. Only an Attribute Code that is applicable for
	 *  the entity type indicated and has a singleust have a value for that entity type. If an Attribute
	 *  Code ispplicable does not have this indicator, then having a value is optional. If an Attribute
	 *  Code is not applicable, then it must not have a value.
	 *  
	 * @param rootItem
	 * @param entityType
	 * @param allowedVct
	 */
	private void checkExtraAttrs(EntityItem rootItem, String entityType, Vector allowedVct){
		for (int i=0; i<rootItem.getAttributeCount(); i++){
			EANAttribute attr = rootItem.getAttribute(i);
			String attrcode = attr.getAttributeCode();
			if (!allowedVct.contains(attrcode)){
				String value = PokUtils.getAttributeValue(rootItem, attrcode, "", null, false);
				addDebug("checkExtraAttrs attrcode "+attrcode+" value "+value);
				if (value!=null && value.length()>0){
					//EXTRA_FILTER_ERR = {0} is not allowed for {1}
					args[0] = attrcode; 
					args[1] = entityType;
					addError("EXTRA_FILTER_ERR",args);
				}
			}
		}
	}

	/**
	 * add information to the report about what this abr is looking for and its filters
	 * @param entityType
	 * @param txtAttrVct
	 * @param flagAttrVct
	 * @param dateAttr
	 * @param dateValue
	 */
	private void addHeadingInfo(EntityItem rootEntity, String entityType,Vector txtAttrVct, 
			Vector flagAttrVct,String dateAttr, String dateValue){
		
		rptSb.append("<br /><h2>Looking for "+entityType+" with the following filters:<br />");
//		if (entityType.equals("LSEO")){
//			// must match COFGRP on the MODEL, the date is on LSEO
//			//LSEO    = COFGRP,LSEOUNPUBDATEMTRGT
//			rptSb.append(entityType+" with "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), dateAttr, dateAttr)+
//					" &gt;= "+dateValue+" or not populated<br />");
//			String cofgrp = flagAttrVct.firstElement().toString();
//			String cofgrpValue = PokUtils.getAttributeValue(rootEntity, "COFGRP", "", null, false);
//			if(cofgrpValue==null){
//				cofgrpValue="Base";
//			}
//			rptSb.append("and has a Model with "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), cofgrp, cofgrp)+
//					" = "+cofgrpValue+"<br />");
//		}else if (entityType.equals("MODELCONVERT")||entityType.equals("FCTRANSACTION")){
//			//MODELCONVERT = MACHTYPEATR,WTHDRWEFFCTVDATE
//			//FCTRANSACTION = MACHTYPEATR,WTHDRWEFFCTVDATE
//			// must match WTHDRWEFFCTVDATE on the MODEL and MACHTYPEATR used for TOMACHTYPE on root
//			String value = PokUtils.getAttributeValue(rootEntity, "MACHTYPEATR", "", null, false);
//			rptSb.append(entityType+" with TOMACHTYPE ="+value+"<br />");
//			rptSb.append("and a To Model with "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR")+
//					" = "+value+"<br />");
//			rptSb.append(" and "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), dateAttr, dateAttr)+
//					" &gt;= "+dateValue+" or not populated<br />");
//		}else if (entityType.equals("PRODSTRUCT")||entityType.equals("SWPRODSTRUCT")){
//			// must match attr on the MODEL
//			//PRODSTRUCT   = MACHTYPEATR,MODELATR,WTHDRWEFFCTVDATE
//			//SWPRODSTRUCT = MACHTYPEATR,MODELATR,WTHDRWEFFCTVDATE
//			String value = PokUtils.getAttributeValue(rootEntity, "MACHTYPEATR", "", null, false);
//			rptSb.append(entityType+" linked to a Model with "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR")+
//					" = "+value+"<br />");
//			value = PokUtils.getAttributeValue(rootEntity, "MODELATR", "", null, false);
//			rptSb.append(" and "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "MODELATR", "MODELATR")+
//					" = "+value+"<br />");
//			rptSb.append(" and "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), dateAttr, dateAttr)+
//					" &gt;= "+dateValue+" or not populated<br />");
//		}else if (entityType.equals("MODEL")){
//			// must match COFGRP 
//			//MODEL   = COFCAT,COFGRP,COFSUBCAT,WTHDRWEFFCTVDATE
//			for (int i=0; i<flagAttrVct.size(); i++){
//				String attrcode = flagAttrVct.elementAt(i).toString();
//				String value = PokUtils.getAttributeValue(rootEntity, attrcode, "", null, false);
//				if(attrcode.equals("COFGRP") && value==null){
//					value="Base";
//				}
//				rptSb.append(entityType+" with "+
//						PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), attrcode, attrcode)+
//						" = "+value+"<br />");
//			}
//			rptSb.append(entityType+" with "+
//					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), dateAttr, dateAttr)+
//					" &gt;= "+dateValue+" or not populated<br />");
//
//		}else{
			//FEATURE = FCTYPE,WITHDRAWDATEEFF_T
			//SVCMOD  = SVCMODCATG,SVCMODGRP,SVCMODSUBCATG,SVCMODSUBGRP,WTHDRWEFFCTVDATE
			//LSEOBUNDLE = BUNDLUNPUBDATEMTRGT
			//SWFEATURE  = WITHDRAWDATEEFF_T
			//WWCOMPAT = BRANDCD
		for (int i=0; i<txtAttrVct.size(); i++){
			String attrcode = txtAttrVct.elementAt(i).toString();
			String value = PokUtils.getAttributeValue(rootEntity, attrcode, "", null, false);
			rptSb.append(entityType+" with "+
					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), attrcode, attrcode)+
					" = "+value+"<br />");
		}
		for (int i=0; i<flagAttrVct.size(); i++){
			String attrcode = flagAttrVct.elementAt(i).toString();
			String value = PokUtils.getAttributeValue(rootEntity, attrcode, "", null, false);
			rptSb.append(entityType+" with "+
					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), attrcode, attrcode)+
					" = "+value+"<br />");
		}
		if(dateAttr!=null){
			rptSb.append(entityType+" with "+
					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), dateAttr, dateAttr)+
					" &gt;= "+dateValue+" or not populated<br />");
		}
//		}

		String domain = PokUtils.getAttributeValue(rootEntity, "PDHDOMAIN", " or ", null, false);
		rptSb.append(PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "PDHDOMAIN", "PDHDOMAIN")+
				" = "+domain+"<br />");

		rptSb.append("</h2>"+NEWLINE);
		
	}
	/**
	 * is this attribute required as a filter, meta classifications were not working properly
	 * @param entityType
	 * @param attrcode
	 * @return
	 */
	private boolean isRequired(String entityType, String attrcode){
		String required[] = (String[])REQFILTER_TBL.get(entityType);
		boolean isreq = false;
		if(required!=null){
			for(int i=0; i<required.length; i++){
				if(attrcode.equals(required[i])){
					isreq=true;
					break;
				}
			}
		}
		return isreq;
	}
//	/**
//	 * find all relator entity ids with MODEL that meet the filter criteria one domain at a time
//	 * @param featType
//	 * @param relatorType
//	 * @param txtValuesVct
//	 * @param txtAttrVct
//	 * @param flagValuesVct
//	 * @param flagAttrVct
//	 * @param idVct
//	 * @param domain
//	 * @throws SQLException
//	 * @throws MiddlewareException
//	 */
//	private void getMatchingModelTextAndFlagIds(String featType, String relatorType, 
//			Vector txtValuesVct, Vector txtAttrVct, 
//			Vector flagValuesVct, Vector flagAttrVct, Vector idVct, String domain) throws SQLException, MiddlewareException{
//		// duplicate search like action with all but date attribute
//		ReturnStatus returnStatus = new ReturnStatus( -1);
//		int iSessionID = m_db.getNewSessionID();
//		int iNextSessionID = -1;
//
//		try{
//			// search for entities 1 first
//			int iStep1 = 1;
//			ResultSet rs = null;
//			ReturnDataResultSet rdrs = null;
//			ReturnDataResultSet rdrs1 = null;
//
//			// Now.. lets fill out the search table
//			//load pdhdomain here for features
//			m_db.callGBL8119(returnStatus, iSessionID, iStep1, m_prof.getEnterprise(), featType,
//					"PDHDOMAIN", domain);
//			m_db.commit();
//			m_db.freeStatement();
//			m_db.isPending();
//
//			// find entity 1 first
//			try {
//				rs =m_db.callGBL9200(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
//						0, m_prof.getValOn(), m_prof.getEffOn(), getSPLimit());
//				rdrs = new ReturnDataResultSet(rs);
//			}
//			finally {
//				if (rs!=null){
//					rs.close();
//					rs = null;
//				}
//				m_db.commit();
//				m_db.freeStatement();
//				m_db.isPending();
//			}
//
//			if (rdrs.size() > 0) {
//
//				iNextSessionID = m_db.getNewSessionID();
//
//				// Just for Debug here..
//				for (int i = 0; i < rdrs.size(); i++) {
//					String strEntityType = rdrs.getColumn(i, 0);
//					int iEntityID = rdrs.getColumnInt(i, 1);
//					m_db.debug(D.EBUG_SPEW, "ADSIDLSTATUS.getMatchingModelTextAndFlagIds gbl9200:answer:" + strEntityType + ":" + iEntityID);
//				}
//
//				// o.k.  let call one sp that moves the relator and all the info
//				// back into the trsNavigate  table.. with a complete image
//				try {
//					rs = m_db.callGBL2954(returnStatus, m_prof.getOPWGID(), iSessionID, iNextSessionID, 
//							m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
//							relatorType, m_prof.getValOn(), m_prof.getEffOn());
//					rdrs1 = new ReturnDataResultSet(rs);
//				}
//				finally {
//					if (rs !=null){
//						rs.close();
//						rs = null;
//					}
//					m_db.commit();
//					m_db.freeStatement();
//					m_db.isPending();
//				}
//				//
//				// OK.. we are now using a new session id
//				int tmp = iSessionID;
//				iSessionID = iNextSessionID;
//				iNextSessionID = tmp;
//
//				// if any e2's were found.. there is at least on potential relator out there
//				// and the  e2's are sitting there in the queue table.
//				//
//				if (rdrs1.size() > 0) {
//					int iStep2 = 0;
//
//					// Now.. lets fill out the search table
//					// D.W.B.  Do the Text first.. because they should yield quicker results than flags
//					for (int ii = 0; ii < txtAttrVct.size(); ii++) {
//						iStep2++;
//						m_db.callGBL8119(returnStatus, iSessionID, iStep2, m_prof.getEnterprise(), "MODEL", 
//								txtAttrVct.elementAt(ii).toString(),txtValuesVct.elementAt(ii).toString());
//						m_db.commit();
//						m_db.freeStatement();
//						m_db.isPending();
//					}
//
//					// D.W.B.  Do non Text second
//					for (int ii = 0; ii < flagAttrVct.size(); ii++) {
//						iStep2++;
//						m_db.callGBL8119(returnStatus, iSessionID, iStep2, m_prof.getEnterprise(), "MODEL", 
//								flagAttrVct.elementAt(ii).toString(),flagValuesVct.elementAt(ii).toString());
//						m_db.commit();
//						m_db.freeStatement();
//						m_db.isPending();
//					}
//
//					// Lets have an augmented search
//					try{
//						rs = m_db.callGBL9203(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
//								0, m_prof.getValOn(), m_prof.getEffOn());
//						rdrs = new ReturnDataResultSet(rs);
//					}
//					finally {
//						if (rs !=null){
//							rs.close();
//							rs = null;
//						}
//						m_db.commit();
//						m_db.freeStatement();
//						m_db.isPending();
//					}
//
//					for (int i = 0; i < rdrs.size(); i++) {
//
//						String strEntity1Type = rdrs.getColumn(i, 0);
//						int iEntity1ID = rdrs.getColumnInt(i, 1);
//						String strEntityType = rdrs.getColumn(i, 2);
//						int iEntityID = rdrs.getColumnInt(i, 3);
//						String strEntity2Type = rdrs.getColumn(i, 4);
//						int iEntity2ID = rdrs.getColumnInt(i, 5);
//						if(relatorType.equals(strEntityType) && iEntityID>0){ // bypass default entities
//							Integer eid = new Integer(iEntityID);
//							if (!idVct.contains(eid)){
//								idVct.add(eid);
//							}
//						}else{
//							addDebug("getMatchingModelTextAndFlagIds skipping strEntityType "+strEntityType+" iEntityID "+iEntityID);
//							addDebug(D.EBUG_SPEW,
//									"gbl9203:answer:" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType + ":" +
//									iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);
//						}
//			             m_db.debug(D.EBUG_SPEW,
//                                 "ADSIDLSTATUS.getMatchingModelTextAndFlagIds gbl9203:answer:" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType + ":" +
//                                 iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);
//					}
//				}
//			}
//		}finally{
//			m_db.commit();
//			m_db.freeStatement();
//			m_db.isPending();
//			// Now remove all the records to clean up after yourself
//			D.ebug(D.EBUG_SPEW, "ADSIDLSTATUS cleanup session id's: " + iSessionID + ", " + iNextSessionID);
//			int nTries = 3;
//			do {
//				returnStatus = new ReturnStatus(-1);
//				m_db.callGBL8105(returnStatus, iSessionID);
//				m_db.commit();
//				m_db.freeStatement();
//				m_db.isPending();
//				if (returnStatus.intValue() != 0) {
//					D.ebug(D.EBUG_DETAIL, "ADSIDLSTATUS - GBL8105 did not return SP_OK");
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						D.ebug(D.EBUG_DETAIL, e.getMessage());
//					}
//				}
//			} while (returnStatus.intValue() != 0 && nTries-- > 0);
//			nTries = 3;
//			do {
//				returnStatus = new ReturnStatus(-1);
//				m_db.callGBL8105(returnStatus, iNextSessionID);
//				m_db.commit();
//				m_db.freeStatement();
//				m_db.isPending();
//				if (returnStatus.intValue() != 0) {
//					D.ebug(D.EBUG_DETAIL, "ADSIDLSTATUS - GBL8105 did not return SP_OK");
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						D.ebug(D.EBUG_DETAIL, e.getMessage());
//					}
//				}
//			} while (returnStatus.intValue() != 0 && nTries-- > 0);
//		}
//
//		addDebug("getMatchingModelTextAndFlagIds domain "+domain+" relatorType "+relatorType+" idVct.cnt: "+idVct.size());
//		addDebug(D.EBUG_INFO,"getMatchingModelTextAndFlagIds relatorType "+relatorType+" idVct: "+idVct);
//	}
	/**
	 * find all relator entity ids with MODEL that meet the filter criteria
	 * 
	 * @param featType
	 * @param relatorType
	 * @param txtValuesVct
	 * @param txtAttrVct
	 * @param flagValuesVct
	 * @param flagAttrVct
	 * @param domainVct
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * /
	private Vector getMatchingModelTextAndFlagIds(String featType, String relatorType, 
			Vector txtValuesVct, Vector txtAttrVct, 
			Vector flagValuesVct, Vector flagAttrVct, Vector domainVct) throws SQLException, MiddlewareException{

		Vector idVct = new Vector();
		// duplicate search like action with all but date attribute
		ReturnStatus returnStatus = new ReturnStatus( -1);
		int iSessionID = m_db.getNewSessionID();
		int iNextSessionID = -1;

		try{
			// search for entities 1 first
			int iStep1 = 1;
			ResultSet rs = null;
			ReturnDataResultSet rdrs = null;
			ReturnDataResultSet rdrs1 = null;

			// Now.. lets fill out the search table
			//load pdhdomain here for features
			for (int ii = 0; ii < domainVct.size(); ii++) {
				m_db.callGBL8119(returnStatus, iSessionID, iStep1, m_prof.getEnterprise(), featType,
						"PDHDOMAIN", domainVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}

			// find entity 1 first
			try {
				rs =m_db.callGBL9200(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
						0, m_prof.getValOn(), m_prof.getEffOn(), getSPLimit());
				rdrs = new ReturnDataResultSet(rs);
			}
			finally {
				if (rs!=null){
					rs.close();
					rs = null;
				}
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}

			if (rdrs.size() > 0) {

				iNextSessionID = m_db.getNewSessionID();

				// Just for Debug here..
				/ *for (int i = 0; i < rdrs.size(); i++) {
					String strEntityType = rdrs.getColumn(i, 0);
					int iEntityID = rdrs.getColumnInt(i, 1);
					m_db.debug(D.EBUG_SPEW, "gbl9200:answer:" + strEntityType + ":" + iEntityID);
				}* /

				// o.k.  let call one sp that moves the relator and all the info
				// back into the trsNavigate  table.. with a complete image
				try {
					rs = m_db.callGBL2954(returnStatus, m_prof.getOPWGID(), iSessionID, iNextSessionID, 
							m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
							relatorType, m_prof.getValOn(), m_prof.getEffOn());
					rdrs1 = new ReturnDataResultSet(rs);
				}
				finally {
					if (rs !=null){
						rs.close();
						rs = null;
					}
					m_db.commit();
					m_db.freeStatement();
					m_db.isPending();
				}
				//
				// OK.. we are now using a new session id
				int tmp = iSessionID;
				iSessionID = iNextSessionID;
				iNextSessionID = tmp;

				// if any e2's were found.. there is at least on potential relator out there
				// and the  e2's are sitting there in the queue table.
				//
				if (rdrs1.size() > 0) {
					int iStep2 = 0;

					// Now.. lets fill out the search table
					// D.W.B.  Do the Text first.. because they should yield quicker results than flags
					for (int ii = 0; ii < txtAttrVct.size(); ii++) {
						iStep2++;
						m_db.callGBL8119(returnStatus, iSessionID, iStep2, m_prof.getEnterprise(), "MODEL", 
								txtAttrVct.elementAt(ii).toString(),txtValuesVct.elementAt(ii).toString());
						m_db.commit();
						m_db.freeStatement();
						m_db.isPending();
					}

					// D.W.B.  Do non Text second
					for (int ii = 0; ii < flagAttrVct.size(); ii++) {
						iStep2++;
						m_db.callGBL8119(returnStatus, iSessionID, iStep2, m_prof.getEnterprise(), "MODEL", 
								flagAttrVct.elementAt(ii).toString(),flagValuesVct.elementAt(ii).toString());
						m_db.commit();
						m_db.freeStatement();
						m_db.isPending();
					}

					// Lets have an augmented search
					try{
						rs = m_db.callGBL9203(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCHREL_KEY+relatorType,
								0, m_prof.getValOn(), m_prof.getEffOn());
						rdrs = new ReturnDataResultSet(rs);
					}
					finally {
						if (rs !=null){
							rs.close();
							rs = null;
						}
						m_db.commit();
						m_db.freeStatement();
						m_db.isPending();
					}

					for (int i = 0; i < rdrs.size(); i++) {

						String strEntity1Type = rdrs.getColumn(i, 0);
						int iEntity1ID = rdrs.getColumnInt(i, 1);
						String strEntityType = rdrs.getColumn(i, 2);
						int iEntityID = rdrs.getColumnInt(i, 3);
						String strEntity2Type = rdrs.getColumn(i, 4);
						int iEntity2ID = rdrs.getColumnInt(i, 5);
						if(relatorType.equals(strEntityType) && iEntityID>0){ // bypass default entities
							idVct.add(new Integer(iEntityID));
						}else{
							addDebug("getMatchingModelTextAndFlagIds skipping strEntityType "+strEntityType+" iEntityID "+iEntityID);
							addDebug(D.EBUG_SPEW,
									"gbl9203:answer:" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType + ":" +
									iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);
						}
					}
				}
			}
		}finally{
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
			// Now remove all the records to clean up after yourself
			D.ebug(D.EBUG_SPEW, "ADSIDLSTATUS cleanup session id's: " + iSessionID + ", " + iNextSessionID);
			int nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				m_db.callGBL8105(returnStatus, iSessionID);
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "ADSIDLSTATUS - GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
			nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				m_db.callGBL8105(returnStatus, iNextSessionID);
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "ADSIDLSTATUS - GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
		}

		addDebug("getMatchingModelTextAndFlagIds relatorType "+relatorType+" idVct.cnt: "+idVct.size());
		addDebug(D.EBUG_INFO,"getMatchingModelTextAndFlagIds relatorType "+relatorType+" idVct: "+idVct);
		return idVct;
	}*/
	
	
	/** 
	 * this uses the same sps that the searchaction uses to find entity ids for entities that meet these criteria
	 * 
	 * @param entityType
	 * @param txtValuesVct
	 * @param txtAttrVct
	 * @param flagValuesVct
	 * @param flagAttrVct
	 * @param domainVct
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * /
	private Vector getMatchingTextAndFlagIds(String entityType, Vector txtValuesVct, Vector txtAttrVct, 
			Vector flagValuesVct, Vector flagAttrVct, Vector domainVct) throws SQLException, MiddlewareException{
		Vector idVct = new Vector();

		// duplicate search like action with all but date attribute
		ReturnStatus returnStatus = new ReturnStatus( -1);
		int iSessionID = m_db.getNewSessionID();
		try{
			// Now.. lets fill out the search table
			int iStep = 0;
			ResultSet rs = null;
			ReturnDataResultSet rdrs = null;

			// D.W.B.  Do the Text first.. because they should yield quicker results than flags
			for (int ii = 0; ii < txtAttrVct.size(); ii++) {
				iStep++;
				m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType, 
						txtAttrVct.elementAt(ii).toString(),txtValuesVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}
			//load pdhdomain here for a filter, even those without a specified filter need something
			iStep++; // one step for all domain values - this requires a match on all
			for (int ii = 0; ii < domainVct.size(); ii++) {
				m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType,
						"PDHDOMAIN", domainVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}

			// D.W.B.  Do non Text second
			for (int ii = 0; ii < flagAttrVct.size(); ii++) {
				iStep++;

				m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType,
						flagAttrVct.elementAt(ii).toString(),flagValuesVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}

			try {
				rs = m_db.callGBL9200(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCH_KEY+entityType,
						0, m_prof.getValOn(), m_prof.getEffOn(), getSPLimit());
				rdrs = new ReturnDataResultSet(rs);
			}
			finally {
				if (rs!=null){
					rs.close();
					rs = null;
				}
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}
			for (int i = 0; i < rdrs.size(); i++) {
				String strEntityType = rdrs.getColumn(i, 0);
				int iEntityID = rdrs.getColumnInt(i, 1);
				if (strEntityType.equals(entityType) && iEntityID>0){ // bypass default entities
					idVct.add(new Integer(iEntityID));
				}else{
					addDebug("getMatchingTextAndFlagIds skipping strEntityType "+strEntityType+" iEntityID "+iEntityID);					
				}
			}
		}
		finally {
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
			// Now remove all the records to clean up after yourself
			D.ebug(D.EBUG_SPEW, "ADSIDLSTATUS cleanup session id's: " + iSessionID );
			int nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				m_db.callGBL8105(returnStatus, iSessionID);
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
		}

		addDebug("getMatchingTextAndFlagIds entitytype "+entityType+" idVct.cnt: "+idVct.size());
		addDebug(D.EBUG_INFO,"getMatchingTextAndFlagIds entitytype "+entityType+" idVct: "+idVct);
		return idVct;
	}*/

	/**
	 * do the search one domain at a time, otherwise it looks for entities that match all
	 * this uses the same sps that the searchaction uses to find entity ids for entities that meet these criteria
	 * 
	 * @param entityType
	 * @param txtValuesVct
	 * @param txtAttrVct
	 * @param flagValuesVct
	 * @param flagAttrVct
	 * @param idVct
	 * @param domain
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void getMatchingTextAndFlagIds(String entityType, Vector txtValuesVct, Vector txtAttrVct, 
			Vector flagValuesVct, Vector flagAttrVct, Vector idVct, String domain) throws SQLException, MiddlewareException{

		// duplicate search like action with all but date attribute
		ReturnStatus returnStatus = new ReturnStatus( -1);
		int iSessionID = m_db.getNewSessionID();
		try{
			// Now.. lets fill out the search table
			int iStep = 0;
			ResultSet rs = null;
			ReturnDataResultSet rdrs = null;

			// D.W.B.  Do the Text first.. because they should yield quicker results than flags
			for (int ii = 0; ii < txtAttrVct.size(); ii++) {
				iStep++;
				m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType, 
						txtAttrVct.elementAt(ii).toString(),txtValuesVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}
			//load pdhdomain here for a filter, even those without a specified filter need something
			// done one at a time because if all are loaded, the match is on all at once
			iStep++; 
			m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType,
					"PDHDOMAIN", domain);
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();

			// D.W.B.  Do non Text second
			for (int ii = 0; ii < flagAttrVct.size(); ii++) {
				iStep++;

				m_db.callGBL8119(returnStatus, iSessionID, iStep, m_prof.getEnterprise(), entityType,
						flagAttrVct.elementAt(ii).toString(),flagValuesVct.elementAt(ii).toString());
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}

			try {
				rs = m_db.callGBL9200(returnStatus, iSessionID, m_prof.getEnterprise(), SEARCH_KEY+entityType,
						0, m_prof.getValOn(), m_prof.getEffOn(), getSPLimit());
				rdrs = new ReturnDataResultSet(rs);
			}
			finally {
				if (rs!=null){
					rs.close();
					rs = null;
				}
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
			}
			for (int i = 0; i < rdrs.size(); i++) {
				String strEntityType = rdrs.getColumn(i, 0);
				int iEntityID = rdrs.getColumnInt(i, 1);
				if (strEntityType.equals(entityType) && iEntityID>0){ // bypass default entities
					Integer eid = new Integer(iEntityID);
					if (!idVct.contains(eid)){ // if one entity has multiple domains, only add it once
						idVct.add(eid);
					}
				}else{
					addDebug("getMatchingTextAndFlagIds skipping strEntityType "+strEntityType+" iEntityID "+iEntityID);					
				}
		        m_db.debug(D.EBUG_SPEW, "ADSIDLSTATUS.getMatchingTextAndFlagIds gbl9200:answer:" + strEntityType + ":" + iEntityID);
			}
		}
		finally {
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
			// Now remove all the records to clean up after yourself
			D.ebug(D.EBUG_SPEW, "ADSIDLSTATUS cleanup session id's: " + iSessionID );
			int nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				m_db.callGBL8105(returnStatus, iSessionID);
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
		}

		addDebug("getMatchingTextAndFlagIds domain "+domain+" entitytype "+entityType+" idVct.cnt: "+idVct.size());
		addDebug(D.EBUG_INFO,"getMatchingTextAndFlagIds entitytype "+entityType+" idVct: "+idVct);
	}
//	/**
//	 * get the LSEOs with MODEL that has a matching COFGRP and filter on the LSEO.LSEOUNPUBDATEMTRGT date
//	 * 
//	 * @param flagValuesVct
//	 * @param flagAttrVct
//	 * @param dateAttr
//	 * @param dateValue
//	 * @param domainVct
//	 * @return
//	 * @throws SQLException
//	 * @throws MiddlewareException
//	 */
//	private Vector getMatchingLSEOIds(
//			Vector flagValuesVct, Vector flagAttrVct, String dateAttr, String dateValue,Vector domainVct) 
//	throws SQLException, MiddlewareException
//	{
//		String sql = getFilteredLSEOSql(dateAttr, dateValue, flagAttrVct.firstElement().toString(), 
//				flagValuesVct.firstElement().toString(),domainVct);
//
//		Vector matchIdVct = new Vector();
//
//		addDebug("getMatchingLSEOIds executing with "+PokUtils.convertToHTML(sql));
//		PreparedStatement ps = null;
//		ResultSet result=null;
//
//		try{
//			ps = m_db.getPDHConnection().prepareStatement(sql);
//
//			result = ps.executeQuery();
//
//			while(result.next()) {   
//				int iEntityID = result.getInt(1);
//				if(iEntityID>0){ // bypass default entities
//					matchIdVct.add(new Integer(iEntityID));  
//				}
//			}
//			addDebug("getMatchingLSEOIds all matchIdVct.cnt "+matchIdVct.size());
//			//addDebug(D.EBUG_INFO,"getMatchingLSEOIds all matchIdVct "+matchIdVct);
//		}finally{
//			if (result!=null){
//				try {
//					result.close();
//				}catch(Exception e){
//					System.err.println("getMatchingLSEOIds(), unable to close result. "+ e);
//				}
//				result=null;
//			}
//
//			if (ps !=null) {
//				try {
//					ps.close();
//				}catch(Exception e){
//					System.err.println("getMatchingLSEOIds(), unable to close ps. "+ e);
//				}
//				ps=null;
//			}
//
//			m_db.commit();
//			m_db.freeStatement();
//			m_db.isPending();
//		}
//
//		return matchIdVct;
//	}

//	/**
//	 * get the FCTRANSACTIONs or MODELCONVERTs with MODEL that has a matching MACHTYPEATR 
//	 * and filter on the MODEL.wddate date
//	 * 
//	 * @param entitytype
//	 * @param textValuesVct
//	 * @param dateAttr
//	 * @param dateValue
//	 * @param domainVct
//	 * @return
//	 * @throws SQLException
//	 * @throws MiddlewareException
//	 */
//	private Vector getMatchingConversionIds(String entitytype,
//			Vector textValuesVct, String dateAttr, String dateValue,Vector domainVct) 
//	throws SQLException, MiddlewareException
//	{
//		String sql = getFilteredConversionSql(entitytype,dateAttr, dateValue,  
//				textValuesVct.firstElement().toString(), domainVct);
//
//		Vector matchIdVct = new Vector();
//
//		addDebug("getMatchingConversionIds executing with "+PokUtils.convertToHTML(sql));
//		PreparedStatement ps = null;
//		ResultSet result=null;
//
//		try{
//			ps = m_db.getPDHConnection().prepareStatement(sql);
//
//			result = ps.executeQuery();
//
//			while(result.next()) {                   
//				int iEntityID = result.getInt(1);
//				if(iEntityID>0){ // bypass default entities
//					matchIdVct.add(new Integer(iEntityID));  
//				}
//			}
//			addDebug("getMatchingConversionIds all matchIdVct.cnt "+matchIdVct.size());
//			//addDebug(D.EBUG_INFO,"getMatchingConversionIds all matchIdVct "+matchIdVct);
//		}finally{
//			if (result!=null){
//				try {
//					result.close();
//				}catch(Exception e){
//					System.err.println("getMatchingConversionIds(), unable to close result. "+ e);
//				}
//				result=null;
//			}
//
//			if (ps !=null) {
//				try {
//					ps.close();
//				}catch(Exception e){
//					System.err.println("getMatchingConversionIds(), unable to close ps. "+ e);
//				}
//				ps=null;
//			}
//
//			m_db.commit();
//			m_db.freeStatement();
//			m_db.isPending();
//		}
//
//		return matchIdVct;
//	}	
//			
	/** 
	 * get the query to use to find (sw)prodstructs with models that meet the date criteria
	 * 
	 * This query returns active (sw)prodstructs for models which either do not have an active WTHDRWEFFCTVDATE
	 * attribute or the value of this attribute is >= to the value of the parameter
	 *  
	 * @param entityType
	 * @param dateAttr
	 * @param dateValue
	 * @param domainVct
	 * @return
	 */
//	private String getFilteredModelDateSql(String entityType, String dateAttr, String dateValue, Vector domainVct){
//		StringBuffer domainsb  = new StringBuffer();
//		for (int i=0; i<domainVct.size(); i++){
//			if (domainsb.length()>0){
//				domainsb.append(',');
//			}
//			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
//		}
//		StringBuffer sb = new StringBuffer("select distinct ps.entityid from opicm.relator ps ");
//		sb.append("join opicm.flag f on ps.entitytype=f.entitytype and ps.entityid=f.entityid "); 
//		sb.append("join opicm.flag f1 on ps.entitytype=f1.entitytype and ps.entityid=f1.entityid "); 
//		sb.append("where ps.entitytype='"+entityType+"' ");
//		sb.append("and ps.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and ps.valto>current timestamp ");
//		sb.append("and ps.effto>current timestamp ");
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f.valto>current timestamp ");
//		sb.append("and f.effto>current timestamp ");
//		sb.append("and f.attributecode='PDHDOMAIN' ");
//		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
//		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f1.valto>current timestamp ");
//		sb.append("and f1.effto>current timestamp ");
//		sb.append("and f1.attributecode='STATUS' ");
//		sb.append("and f1.attributevalue <> '0010' ");
//		sb.append("and not exists ");
//		sb.append("(select t.entityid from opicm.text t where ");
//		sb.append("t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.entitytype='MODEL' ");
//		sb.append("and t.entityid=ps.entity2id ");
//		sb.append("and t.attributecode='"+dateAttr+"' ");
//		sb.append("and t.valto>current timestamp ");
//		sb.append("and t.effto>current timestamp) ");
//		sb.append("union ");
//		sb.append("select distinct ps.entityid from opicm.relator ps ");
//		sb.append("join opicm.text t on t.entitytype=ps.entity2type and t.entityid=ps.entity2id ");
//		sb.append("join opicm.flag f on ps.entitytype=f.entitytype and ps.entityid=f.entityid "); 
//		sb.append("join opicm.flag f1 on ps.entitytype=f1.entitytype and ps.entityid=f1.entityid ");
//		sb.append("where ps.entitytype='"+entityType+"' ");
//		sb.append("and ps.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and ps.valto>current timestamp ");
//		sb.append("and ps.effto>current timestamp ");
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f.valto>current timestamp ");
//		sb.append("and f.effto>current timestamp ");
//		sb.append("and f.attributecode='PDHDOMAIN' ");
//		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
//		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f1.valto>current timestamp ");
//		sb.append("and f1.effto>current timestamp ");
//		sb.append("and f1.attributecode='STATUS' ");
//		sb.append("and f1.attributevalue <> '0010' ");
//		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.attributecode='"+dateAttr+"' ");
//		sb.append("and t.valto>current timestamp ");
//		sb.append("and t.effto>current timestamp ");
//		sb.append("and t.attributevalue>='"+dateValue+"' with ur");
//		
//		return sb.toString();
//	}

	/** 
	 * get the query to use to find entities that meet the date criteria
	 * 
	 * This query returns active entities which either do not have an active withdrawdate
	 * attribute or the value of this attribute is >= to the value of the parameter 
	 * 
	 * @param entityType
	 * @param dateAttr
	 * @param dateValue
	 * @param domainVct
	 * @param lastRunningTime 
	 * @return
	 */
	private String getFilteredDateSql(String entityType, String dateAttr, String dateValue, Vector domainVct, String lastRunningTime){
		StringBuffer domainsb  = new StringBuffer();
		for (int i=0; i<domainVct.size(); i++){
			if (domainsb.length()>0){
				domainsb.append(',');
			}
			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
		}
		StringBuffer sb = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid "); 
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid "); 
		sb.append("where mdl.entitytype='"+entityType+"' ");
		sb.append("and mdl.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append("and not exists ");
		sb.append("(select t.entityid from opicm.text t where ");
		sb.append("t.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and t.entitytype='"+entityType+"' ");
		sb.append("and t.entityid=mdl.entityid ");
		sb.append("and t.attributecode='"+dateAttr+"' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp) ");
		sb.append("union ");
		sb.append("select distinct mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.text t on t.entitytype=mdl.entitytype and t.entityid=mdl.entityid ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
		sb.append("where mdl.entitytype='"+entityType+"' ");
		sb.append("and mdl.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and t.attributecode='"+dateAttr+"' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp ");
		sb.append("and t.attributevalue>='"+dateValue+"' with ur");

		return sb.toString();
	}
	
	/** 
	 * get the query to use to find entities that meet the date criteria
	 * 
	 * This query returns active entities which either do not have an active withdrawdate
	 * attribute or the value of this attribute is >= to the value of the parameter 
	 * 
	 * @param entityType
	 * @param dateAttr
	 * @param dateValue
	 * @param domainVct
	 * @param lastRunningTime 
	 * @return
	 */
	private String getFilteredSql(String entityType, Vector domainVct, String lastRunningTime){
		StringBuffer domainsb  = new StringBuffer();
		for (int i=0; i<domainVct.size(); i++){
			if (domainsb.length()>0){
				domainsb.append(',');
			}
			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
		}
		StringBuffer sb = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid "); 
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid "); 
		sb.append("where mdl.entitytype='"+entityType+"' ");
		sb.append("and mdl.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append(" with ur");
//		sb.append("and not exists ");
//		sb.append("(select t.entityid from opicm.text t where ");
//		sb.append("t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.entitytype='"+entityType+"' ");
//		sb.append("and t.entityid=mdl.entityid ");
//		sb.append("and t.attributecode='"+dateAttr+"' ");
//		sb.append("and t.valto>current timestamp ");
//		sb.append("and t.effto>current timestamp) ");
//		sb.append("union ");
//		sb.append("select mdl.entityid from opicm.entity mdl ");
//		sb.append("join opicm.text t on t.entitytype=mdl.entitytype and t.entityid=mdl.entityid ");
//		if(lastRunningTime != null){
//			sb.append("join opicm.text t1 on mdl.entitytype=t1.entitytype and mdl.entityid=t1.entityid ");
//		}
//		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid ");
//		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid ");
//		sb.append("where mdl.entitytype='"+entityType+"' ");
//		sb.append("and mdl.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and mdl.valto>current timestamp ");
//		sb.append("and mdl.effto>current timestamp ");
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f.valto>current timestamp ");
//		sb.append("and f.effto>current timestamp ");
//		sb.append("and f.attributecode='PDHDOMAIN' ");
//		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
//		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f1.valto>current timestamp ");
//		sb.append("and f1.effto>current timestamp ");
//		sb.append("and f1.attributecode='STATUS' ");
//		sb.append("and f1.attributevalue <> '0010' ");
//		if(lastRunningTime != null){
//			sb.append("and t1.enterprise='"+m_prof.getEnterprise()+"' ");
//			sb.append("and t1.valfrom>'" +lastRunningTime+"' ");
//			sb.append("and t1.attributecode='ADSABRSTATUS' ");
//			sb.append("and t1.attributevalue = '0030' ");
//		}
//		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.attributecode='"+dateAttr+"' ");
//		sb.append("and t.valto>current timestamp ");
//		sb.append("and t.effto>current timestamp ");
//		sb.append("and t.attributevalue>='"+dateValue+"' with ur");

		return sb.toString();
	}

	
	/** 
	 * get the query to use to find REFOFERFEAT with reference REFOFER that meet the date criteria
	 * 
	 * This query returns active REFOFERFEAT for REFOFER which either do not have an active ENDOFSVC
	 * attribute or the value of this attribute is >= to the value of the parameter
	 *  
	 * @param entityType
	 * @param dateAttr
	 * @param dateValue
	 * @param domainVct
	 * @param lastRunningTime 
	 * @return
	 */
	private String getFilteredREFOFERFEATDateSql(String entityType, String dateAttr, String dateValue, Vector domainVct, String lastRunningTime){
		StringBuffer domainsb  = new StringBuffer();
		for (int i=0; i<domainVct.size(); i++){
			if (domainsb.length()>0){
				domainsb.append(',');
			}
			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
		}
		StringBuffer sb = new StringBuffer("select distinct reffeature.entityid from opicm.entity reffeature ");
		sb.append("join opicm.flag f on reffeature.entitytype=f.entitytype and reffeature.entityid=f.entityid "); 
		sb.append("join opicm.flag f1 on reffeature.entitytype=f1.entitytype and reffeature.entityid=f1.entityid "); 
		sb.append("join opicm.relator r on reffeature.entitytype=r.entity2type and reffeature.entityid=r.entity2id ");
		sb.append("where reffeature.entitytype='"+entityType+"' ");
		sb.append("and reffeature.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and reffeature.valto>current timestamp ");
		sb.append("and reffeature.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		sb.append("and r.enterprise ='"+m_prof.getEnterprise()+"' ");
		sb.append("and r.entitytype='REFOFERREFOFERFEAT' ");
		sb.append("and r.valto>current timestamp ");
		sb.append("and r.effto>current timestamp ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append("and not exists ");
		sb.append("(select distinct t.entityid from opicm.text t where ");
		sb.append("t.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and t.entitytype=r.entity1type ");
		sb.append("and t.entityid=r.entity1id ");
		sb.append("and t.attributecode='"+dateAttr+"' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp) ");
		sb.append("union ");
		sb.append("select distinct reffeature.entityid from opicm.entity reffeature ");
		sb.append("join opicm.flag f on reffeature.entitytype=f.entitytype and reffeature.entityid=f.entityid "); 
		sb.append("join opicm.flag f1 on reffeature.entitytype=f1.entitytype and reffeature.entityid=f1.entityid "); 
		sb.append("join opicm.relator r on reffeature.entitytype=r.entity2type and reffeature.entityid=r.entity2id ");
		sb.append("join opicm.text t on t.entitytype=r.entity1type and t.entityid=r.entity1id ");
		sb.append("where reffeature.entitytype='"+entityType+"' ");
		sb.append("and reffeature.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and reffeature.valto>current timestamp ");
		sb.append("and reffeature.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		sb.append("and r.enterprise ='"+m_prof.getEnterprise()+"' ");
		sb.append("and r.entitytype='REFOFERREFOFERFEAT' ");
		sb.append("and r.valto>current timestamp ");
		sb.append("and r.effto>current timestamp ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and t.attributecode='"+dateAttr+"' ");
		sb.append("and t.valto>current timestamp ");
		sb.append("and t.effto>current timestamp ");
		sb.append("and t.attributevalue>='"+dateValue+"' with ur");	
		return sb.toString();
	}
	
	private String getFilteredModelSql(String entityType, Vector domainVct, String lastRunningTime ) {
		
		StringBuffer domainsb  = new StringBuffer();
		for (int i=0; i<domainVct.size(); i++){
			if (domainsb.length()>0){
				domainsb.append(',');
			}
			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
		}
		StringBuffer sb = new StringBuffer("select distinct mdl.entityid from opicm.entity mdl ");
		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid "); 
		sb.append("join opicm.flag f1 on mdl.entitytype=f1.entitytype and mdl.entityid=f1.entityid "); 
		sb.append("join opicm.flag f2 on mdl.entitytype=f2.entitytype and mdl.entityid=f2.entityid and f2.attributecode = 'COFCAT' "); 
		sb.append("join opicm.flag f3 on mdl.entitytype=f3.entitytype and mdl.entityid=f3.entityid and f3.attributecode = 'COFSUBCAT' "); 
		sb.append("join opicm.flag f4 on mdl.entitytype=f4.entitytype and mdl.entityid=f4.entityid and f4.attributecode = 'COFGRP' "); 
		sb.append("join opicm.filter_model filter on (f2.attributevalue = filter.cofcat or filter.cofcat = '*') " +
				"and (f3.attributevalue = filter.cofsubcat or filter.cofsubcat = '*') " +
				"and (f4.attributevalue = filter.cofgrp or filter.cofgrp = '*') ");
		sb.append("where mdl.entitytype='"+entityType+"' ");
		sb.append("and mdl.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and mdl.valto>current timestamp ");
		sb.append("and mdl.effto>current timestamp ");
		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f.valto>current timestamp ");
		sb.append("and f.effto>current timestamp ");
		sb.append("and f.attributecode='PDHDOMAIN' ");
		sb.append("and f.attributevalue in ("+domainsb.toString()+") ");
		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f1.valto>current timestamp ");
		sb.append("and f1.effto>current timestamp ");
		sb.append("and f1.attributecode='STATUS' ");
		sb.append("and f1.attributevalue <> '0010' ");
		sb.append("and f2.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f2.valto>current timestamp ");
		sb.append("and f2.effto>current timestamp ");
		sb.append("and f3.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f3.valto>current timestamp ");
		sb.append("and f3.effto>current timestamp ");
		sb.append("and f4.enterprise='"+m_prof.getEnterprise()+"' ");
		sb.append("and f4.valto>current timestamp ");
		sb.append("and f4.effto>current timestamp ");
		if(lastRunningTime != null){
			sb.append("and f1.valfrom>'" +lastRunningTime+"' ");
		}
		sb.append(" with ur");
		return sb.toString();
	}
//	/**
//	 * This query returns active LSEOs which either do not have an active LSEOUNPUBDATEMTRGT
//	 * attribute or the value of this attribute is >= to the value of the parameter
//	 * and
//	 * The LSEO's MODEL COFGRP attribute matches the parameter
//	 *  
//	 * @param dateAttr
//	 * @param dateValue
//	 * @param cofgrpAttr
//	 * @param cofgrpValue
//	 * @param domainVct
//	 * @return
//	 */
//	private String getFilteredLSEOSql(String dateAttr, String dateValue, String cofgrpAttr, 
//			String cofgrpValue, Vector domainVct){
//		StringBuffer domainsb  = new StringBuffer();
//		for (int i=0; i<domainVct.size(); i++){
//			if (domainsb.length()>0){
//				domainsb.append(',');
//			}
//			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
//		}
//		StringBuffer sb = new StringBuffer("select lseo.entityid from opicm.entity lseo ");
//		sb.append("join opicm.relator r1 on lseo.entitytype=r1.entity2type and lseo.entityid=r1.entity2id ");
//		sb.append("join opicm.relator r2 on r1.entity1type=r2.entity2type and r1.entity1id=r2.entity2id ");
//		sb.append("join opicm.flag f on r2.entity1type=f.entitytype and r2.entity1id=f.entityid ");
//		sb.append("join opicm.flag f1 on lseo.entitytype=f1.entitytype and lseo.entityid=f1.entityid ");
//		sb.append("where lseo.entitytype='LSEO' ");
//		sb.append("and lseo.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and lseo.valto>current timestamp "); 
//		sb.append("and lseo.effto>current timestamp ");
//		sb.append("and r1.entitytype='WWSEOLSEO' ");
//		sb.append("and r1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and r1.valto>current timestamp ");
//		sb.append("and r1.effto>current timestamp ");
//		sb.append("and r2.entitytype='MODELWWSEO' ");
//		sb.append("and r2.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and r2.valto>current timestamp ");
//		sb.append("and r2.effto>current timestamp ");
//		sb.append("and f.attributecode='"+cofgrpAttr+"' ");
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f.valto>current timestamp ");
//		sb.append("and f.effto>current timestamp ");
//		sb.append("and f.attributevalue='"+cofgrpValue+"' ");
//		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f1.valto>current timestamp ");
//		sb.append("and f1.effto>current timestamp ");
//		sb.append("and f1.attributecode='PDHDOMAIN' ");
//		sb.append("and f1.attributevalue in ("+domainsb.toString()+") ");
//		sb.append("and not exists  ");
//		sb.append("(select t.entityid from opicm.text t where "); 
//		sb.append("t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.entitytype='LSEO' "); 
//		sb.append("and t.entityid=lseo.entityid "); 
//		sb.append("and t.attributecode='"+dateAttr+"' "); 
//		sb.append("and t.valto>current timestamp  ");
//		sb.append("and t.effto>current timestamp) ");
//		sb.append("union  ");
//		sb.append("select lseo.entityid from opicm.entity lseo ");
//		sb.append("join opicm.text t on t.entitytype=lseo.entitytype and t.entityid=lseo.entityid ");
//		sb.append("join opicm.relator r1 on lseo.entitytype=r1.entity2type and lseo.entityid=r1.entity2id ");
//		sb.append("join opicm.relator r2 on r1.entity1type=r2.entity2type and r1.entity1id=r2.entity2id ");
//		sb.append("join opicm.flag f on r2.entity1type=f.entitytype and r2.entity1id=f.entityid ");
//		sb.append("join opicm.flag f1 on lseo.entitytype=f1.entitytype and lseo.entityid=f1.entityid ");
//		sb.append("where lseo.entitytype='LSEO' ");
//		sb.append("and lseo.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and lseo.valto>current timestamp "); 
//		sb.append("and lseo.effto>current timestamp ");
//		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and t.attributecode='"+dateAttr+"' ");
//		sb.append("and t.valto>current timestamp ");
//		sb.append("and t.effto>current timestamp ");
//		sb.append("and t.attributevalue>='"+dateValue+"' ");
//		sb.append("and r1.entitytype='WWSEOLSEO' ");
//		sb.append("and r1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and r1.valto>current timestamp ");
//		sb.append("and r1.effto>current timestamp ");
//		sb.append("and r2.entitytype='MODELWWSEO' ");
//		sb.append("and r2.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and r2.valto>current timestamp ");
//		sb.append("and r2.effto>current timestamp ");
//		sb.append("and f.attributecode='"+cofgrpAttr+"' ");
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and f.valto>current timestamp ");
//		sb.append("and f.effto>current timestamp ");
//		sb.append("and f.attributevalue='"+cofgrpValue+"' ");
//		sb.append("and f1.enterprise='"+m_prof.getEnterprise()+"' ");
//		sb.append("and f1.valto>current timestamp ");
//		sb.append("and f1.effto>current timestamp ");
//		sb.append("and f1.attributecode='PDHDOMAIN' ");
//		sb.append("and f1.attributevalue in ("+domainsb.toString()+") with ur; ");
//
//		return sb.toString();
//	}

//	/**
//	 * This query returns active FCTRANSACTION or MODELCONVERT which have TOMODELs which either 
//	 * do not have an active wddate attribute or the value of this attribute is >= to the value of the parameter
//	 * and
//	 * The MODEL MACHTYPEATR attribute matches the parameter
//	 * 
//	 * @param entitytype
//	 * @param dateAttr
//	 * @param dateValue
//	 * @param machtypeValue
//	 * @param domainVct
//	 * @return
//	 */
//	private String getFilteredConversionSql(String entitytype,String dateAttr, String dateValue,  
//			String machtypeValue, Vector domainVct){
//		StringBuffer domainsb  = new StringBuffer();
//		for (int i=0; i<domainVct.size(); i++){
//			if (domainsb.length()>0){
//				domainsb.append(',');
//			}
//			domainsb.append("'"+domainVct.elementAt(i).toString()+"'");
//		}
//		StringBuffer sb = new StringBuffer("select fcx.entityid from opicm.entity fcx "); 
//		sb.append("join opicm.text t on fcx.entitytype=t.entitytype and fcx.entityid=t.entityid "); 
//		sb.append("join opicm.text t1 on fcx.entitytype=t1.entitytype and fcx.entityid=t1.entityid "); 
//		sb.append("join opicm.flag fdom on fcx.entitytype=fdom.entitytype and fcx.entityid=fdom.entityid "); 
//		sb.append("where fcx.entitytype='"+entitytype+"' ");  
//		sb.append("and fcx.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and fcx.valto>current timestamp ");  
//		sb.append("and fcx.effto>current timestamp "); 
//		sb.append("and t.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and t.valto>current timestamp "); 
//		sb.append("and t.effto>current timestamp "); 
//		sb.append("and t.attributecode='TOMACHTYPE' "); 
//		sb.append("and t.attributevalue='"+machtypeValue+"' "); 
//		sb.append("and t1.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and t1.valto>current timestamp "); 
//		sb.append("and t1.effto>current timestamp "); 
//		sb.append("and t1.attributecode='TOMODEL' "); 
//		sb.append("and fdom.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and fdom.valto>current timestamp "); 
//		sb.append("and fdom.effto>current timestamp ");  
//		sb.append("and fdom.attributecode='PDHDOMAIN' ");   
//		sb.append("and fdom.attributevalue in ("+domainsb.toString()+") "); 
//		sb.append("and exists ( ");  
//		sb.append("select mdl.entityid from opicm.entity mdl "); 
//		sb.append("join opicm.flag f on mdl.entitytype=f.entitytype and mdl.entityid=f.entityid "); 
//		sb.append("join opicm.text t2 on mdl.entitytype=t2.entitytype and mdl.entityid=t2.entityid "); 
//		sb.append("join opicm.flag fdom1 on mdl.entitytype=fdom1.entitytype and mdl.entityid=fdom1.entityid "); 
//		sb.append("left join opicm.text t3 on mdl.entitytype=t3.entitytype and mdl.entityid=t3.entityid "); 
//		sb.append("and t3.enterprise='"+m_prof.getEnterprise()+"' and t3.valto>current timestamp and t3.effto>current timestamp "); 
//		sb.append("and t3.attributecode='"+dateAttr+"' "); 
//		sb.append("where mdl.entitytype='MODEL' "); 
//		sb.append("and f.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and f.valto>current timestamp ");  
//		sb.append("and f.effto>current timestamp "); 
//		sb.append("and f.attributecode='MACHTYPEATR' ");   
//		sb.append("and f.attributevalue='"+machtypeValue+"' ");  
//		sb.append("and t2.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and t2.valto>current timestamp "); 
//		sb.append("and t2.effto>current timestamp "); 
//		sb.append("and t2.attributecode='MODELATR' "); 
//		sb.append("and t2.attributevalue=t1.attributevalue "); 
//		sb.append("and fdom1.enterprise='"+m_prof.getEnterprise()+"' "); 
//		sb.append("and fdom1.valto>current timestamp "); 
//		sb.append("and fdom1.effto>current timestamp ");  
//		sb.append("and fdom1.attributecode='PDHDOMAIN' ");   
//		sb.append("and fdom1.attributevalue in ("+domainsb.toString()+") "); 
//		sb.append("and (t3.entityid is null or t3.attributevalue>='"+dateValue+"') ) with ur"); 
//
//		return sb.toString();
//	}
//	/**
//	 * find ids that meet the withdrawn date conditions
//	 * @param sql
//	 * @param idVct
//	 * @return
//	 * @throws SQLException
//	 * @throws MiddlewareException
//	 */
//	private Vector getMatchingDateIds(String sql, Vector idVct) throws SQLException, MiddlewareException{
//		Vector matchIdVct = new Vector();
//
//		addDebug("getMatchingDateIds executing with "+PokUtils.convertToHTML(sql));
//		PreparedStatement ps = null;
//		ResultSet result=null;
//
//		try{
//			ps = m_db.getPDHConnection().prepareStatement(sql);
//
//			result = ps.executeQuery();
//
//			while(result.next()) {                  
//				int iEntityID = result.getInt(1);
//				if(iEntityID>0){ // bypass default entities
//					matchIdVct.add(new Integer(iEntityID));  
//				}
//			}
//			addDebug("getMatchingDateIds all matchIdVct.cnt "+matchIdVct.size());
//			addDebug(D.EBUG_INFO,"getMatchingDateIds all matchIdVct "+matchIdVct);
//			//find the intersection of the 2 sets
//			matchIdVct.retainAll(idVct);
//			addDebug("getMatchingDateIds after retainall matchIdVct "+matchIdVct.size());
//		}finally{
//			if (result!=null){
//				try {
//					result.close();
//				}catch(Exception e){
//					System.err.println("getMatchingDateIds(), unable to close result. "+ e);
//				}
//				result=null;
//			}
//
//			if (ps !=null) {
//				try {
//					ps.close();
//				}catch(Exception e){
//					System.err.println("getMatchingDateIds(), unable to close ps. "+ e);
//				}
//				ps=null;
//			}
//
//			m_db.commit();
//			m_db.freeStatement();
//			m_db.isPending();
//		}
//
//		return matchIdVct;
//	}
	/**
	 * find ids that meet the withdrawn date conditions
	 * @param sql
	 * @param idVct
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private Vector getMatchingIds(String sql) throws SQLException, MiddlewareException{
		Vector matchIdVct = new Vector();

		addDebug("getMatchingDateIds executing with "+PokUtils.convertToHTML(sql));
		PreparedStatement ps = null;
		ResultSet result=null;

		try{
			ps = m_db.getPDHConnection().prepareStatement(sql);

			result = ps.executeQuery();
			Set allid = new HashSet();
			while(result.next()) {                  
				int iEntityID = result.getInt(1);
				if(iEntityID>0){ // bypass default entities
					allid.add(new Integer(iEntityID));  
				}
			}
			matchIdVct.addAll(allid);
			addDebug("getMatchingDateIds all matchIdVct.cnt "+matchIdVct.size());
			addDebug(D.EBUG_INFO,"getMatchingDateIds all matchIdVct "+matchIdVct);
		}finally{
			if (result!=null){
				try {
					result.close();
				}catch(Exception e){
					System.err.println("getMatchingDateIds(), unable to close result. "+ e);
				}
				result=null;
			}

			if (ps !=null) {
				try {
					ps.close();
				}catch(Exception e){
					System.err.println("getMatchingDateIds(), unable to close ps. "+ e);
				}
				ps=null;
			}

			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}

		return matchIdVct;
	}
	/**
	 * ADSIDLSTATUS_XMLIDLABRSTATUS_queuedValue=0090
	 * @param abrcode
	 * @return
	 */
	private String getQueuedValue(String abrcode){
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(
				m_abri.getABRCode()+"_"+abrcode);
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

	/************************************
	 * @param item
	 * @param attrCode
	 * @return
	 */
	private String getLD_Value(EntityItem item, String attrCode)   {
		return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
		PokUtils.getAttributeValue(item, attrCode, ",", PokUtils.DEFNOTPOPULATED, false);
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
		vctReturnsEntityKeys.clear();
		vctReturnsEntityKeys = null;
		
		vctReturnsQueueKeys.clear();
		vctReturnsQueueKeys = null;

		dbgPw=null;
		dbgfn = null;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.27 $";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return ADSIDLSTATUS;
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	protected void addDebug(String msg) { 
		dbgLen+=msg.length();
		dbgPw.println(msg);
		dbgPw.flush();
		//rptSb.append("<!-- "+msg+" -->"+NEWLINE);
	}
	/**********************
	 * support conditional msgs
	 * @param level
	 * @param msg
	 */
	protected void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			addDebug(msg);
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
	protected void addError(String errCode, Object args[]){
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error:  reduce size of output, do not prepend root info
		addMessage(rsBundle.getString("ERROR_PREFIX"), errCode, args);
	} 
    /**********************************
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }

	/**
	 * ADSIDLSTATUS_splimit=200000
	 * get the maximum limit to use for search sps
	 * @return
	 */
	private int getSPLimit(){
		String limit =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(m_abri.getABRCode(),
				"_splimit","200000");

		return Integer.parseInt(limit);
	}
	/**
	 * ADSIDLSTATUS_sampleMode=true
	 * only queue a sample set of entities, not all found
	 * @return
	 */
	private boolean isSampleMode(){
		 return Boolean.valueOf(COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(m_abri.getABRCode(),
				"_sampleMode","false")).booleanValue();
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

	/***********************************************
	 *  Sets the specified Attributes on the specified Entity
	 *
	 * @param mqFlags
	 * @param queuedValue
	 * @param etype
	 * @param eid
	 */
	private void setValues(String mqFlags[], String queuedValue, String etype, int eid)
	{
		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		ReturnEntityKey rek = new ReturnEntityKey(etype,eid, true);
		Vector vctAtts = new Vector();
		rek.m_vctAttributes = vctAtts;
		vctReturnsEntityKeys.addElement(rek);
	

		// queue the abr
		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), etype, eid,
				ADS_XMLEED_ATTR, queuedValue, 1, m_cbOn);
		// tm was picking it up before the propfile is set, so defer it
		sf.setDeferredPost(true);
		
		vctAtts.addElement(sf);
		
		// copy XMLABRPROPFILE into entity for downstream feed
//		for (int i=0; i<mqFlags.length; i++){
//			MultipleFlag mf = new MultipleFlag(m_prof.getEnterprise(),
//					etype, eid, MQUEUE_ATTR, mqFlags[i], 1, m_cbOn);
//			vctAtts.addElement(mf);
//		}
		
	}
	
	/***********************************************
	 *  Sets the specified Attributes on the specified Entity
	 *
	 * @param mqFlags
	 * @param queuedValue
	 * @param etype
	 * @param eid
	 */
	private void setQueueValues(String etype, int eid)
	{
		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		ReturnEntityKey rek = new ReturnEntityKey(etype,eid, true);
		Vector vctAtts = new Vector();
		rek.m_vctAttributes = vctAtts;
		vctReturnsQueueKeys.addElement(rek);
	

		// queue the abr
		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), etype, eid,
				QUEUE_ATTR, QUEUE_VALUE, 1, m_cbOn);
		// tm was picking it up before the propfile is set, so defer it
		//sf.setDeferredPost(true);
		
		vctAtts.addElement(sf);
		
		// copy XMLABRPROPFILE into entity for downstream feed
//		for (int i=0; i<mqFlags.length; i++){
//			MultipleFlag mf = new MultipleFlag(m_prof.getEnterprise(),
//					etype, eid, MQUEUE_ATTR, mqFlags[i], 1, m_cbOn);
//			vctAtts.addElement(mf);
//		}
		
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
		int iSize  = vctReturnsEntityKeys.size();
		logMessage(getDescription()+" updating PDH with "+vctReturnsEntityKeys.size()+" entitykeys");
		addDebug("updatePDH entered for vctReturnsEntityKeys: "+iSize);
		if(vctReturnsEntityKeys.size()>0) {
			try {
				if(isSampleMode()){
					addOutput("<b>WARNING: Running in sample mode, not queueing all entities!</b>");
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.firstElement();
					addOutput("<b>WARNING: Queued "+rek.getEntityType()+rek.getEntityID()+"</b>");
					Vector tmp = new Vector(1);
					tmp.add(rek);
					m_db.update(m_prof, tmp, false, false);
					tmp.clear();
				}else{
					m_db.update(m_prof, vctReturnsEntityKeys, false, false);			
				}

				try{  
					//build one msg for all set
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.firstElement();
					if (rek.m_vctAttributes.size() == 1){
						Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(0);
						//ATTRS_SET = {0} was set to {1} and {2} was set to {3} for {4} {5}
						args[0] = attr.getAttributeCode();
						args[1] = attr.getAttributeValue();
						args[2] = ""+iSize;
						args[3] = rek.getEntityType();
						addMessage("", "ATTRS_SET", args);
					} else {
						addDebug("no attribute value update!");
					}
					
				}catch(Exception exc){
					exc.printStackTrace();
					addDebug("exception trying to output msg "+exc);
				}
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
	 * Update the PDH with the values in the vector, do all at once
	 *
	 */
	private void updatePDHQueue()
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		int iSize  = vctReturnsQueueKeys.size();
		logMessage(getDescription()+" updating PDH with "+vctReturnsQueueKeys.size()+" entitykeys");
		addDebug("updatePDH entered for vctReturnsQueueKeys: "+iSize);
		if(vctReturnsQueueKeys.size()>0) {
			try {
				if(isSampleMode()){
					addOutput("<b>WARNING: Running in sample mode, not queueing all entities!</b>");
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsQueueKeys.firstElement();
					addOutput("<b>WARNING: Queued "+rek.getEntityType()+rek.getEntityID()+"</b>");
					Vector tmp = new Vector(1);
					tmp.add(rek);
					m_db.update(m_prof, tmp, false, false);
					tmp.clear();
				}else{
					m_db.update(m_prof, vctReturnsQueueKeys, false, false);			
				}

				try{  
					//build one msg for all set
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsQueueKeys.firstElement();
					if (rek.m_vctAttributes.size() == 1){
						Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(0);
						//ATTRS_SET = {0} was set to {1} and {2} was set to {3} for {4} {5}
						args[0] = attr.getAttributeCode();
						args[1] = attr.getAttributeValue();
						args[2] = ""+iSize;
						args[3] = rek.getEntityType();
						addMessage("", "ATTRS_SET", args);
					} else {
						addDebug("no attribute value update!");
					}
					
				}catch(Exception exc){
					exc.printStackTrace();
					addDebug("exception trying to output msg "+exc);
				}
			}
			finally {
				vctReturnsQueueKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}
	
	protected void setTextValue(EntityItem entity, String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        log(" ADSIDLSTATUS ***** "+strAttributeCode+" set to: " + strAttributeValue);
        log("setTextValue entered for "+strAttributeCode+" set to: " + strAttributeValue);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = entity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
			log("setTextValue: "+strAttributeCode+" was not in meta for "+entity.getEntityType()+", nothing to do");
        	log("ADSIDLSTATUS ***** "+strAttributeCode+" was not in meta for "+
        			entity.getEntityType()+", nothing to do");
			return;
		}
        if (strAttributeValue != null) {

			try {
				if (m_cbOn == null) {
					setControlBlock(); // needed for attribute updates
				}
				ReturnEntityKey rek = new ReturnEntityKey(getEntityType(),
						getEntityID(), true);

				Text t = new Text(m_prof.getEnterprise(), rek.getEntityType(),
						rek.getEntityID(), strAttributeCode, strAttributeValue,
						1, m_cbOn);
				//                    SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
				//                        strAttributeCode, strAttributeValue, 1, m_cbOn);
				Vector vctAtts = new Vector();
				Vector vctReturnsEntityKeys = new Vector();
				vctAtts.addElement(t);
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);

				m_db.update(m_prof, vctReturnsEntityKeys, false, false);
				addDebug(entity.getKey() + " had " + strAttributeCode
						+ " set to: " + strAttributeValue);
			} finally {
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after update in setTextValue ");
			}

		}
    }
	
}

