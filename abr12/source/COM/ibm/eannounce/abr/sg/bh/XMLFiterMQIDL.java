/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: XMLFiterMQIDL.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2011
 *                LICENSED MATERIAL - PROGRAM PROPERTY OF IBM
 *                REFER TO COPYRIGHT INSTRUCTION FORM#G120-2083
 *                RESTRICTED MATERIALS OF IBM
 *                IBM CONFIDENTIAL
 *
 *   Version: 1.0
 *
 *   Functional Description: 
 *
 *   Component : 
 *   Author(s) Name(s): Will
 *   Date of Creation: 2011-5-16
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   2011-5-16     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg.bh;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;


//import COM.ibm.eannounce.abr.sg.adsxmlbh1.XMLMQ;
//import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
//import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
//import COM.ibm.eannounce.objects.EANAttribute;
//import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MQUsage;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
//import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
//import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.Stopwatch;
//import COM.ibm.opicmpdh.objects.SingleFlag;



import com.ibm.transform.oim.eacm.util.PokUtils;

public class XMLFiterMQIDL {

	private static final String XMLENTITYTYPE = "XMLENTITYTYPE";
	private static final String MQUEUE_ATTR = "XMLABRPROPFILE";

	private static final String UPDATE_SUFFIX = "_UPDATE";
	private static final String NAMESPACE_PREFIX = "\'declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/";
    //Bing added xmlcachedts
	private static final String FILTER_SQL_BASE = "select xmlentitytype,xmlentityid,xmlmessage,xmlcachedts from cache.XMLIDLCACHE where XMLCACHEVALIDTO > current timestamp";
	private static final String XML_TITLE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	
	private ADSIDLSTATUS abr; 
	private Profile profile;
	private Database m_db;
	private StringBuffer rptSb = new StringBuffer();// rpt string
	private static final Hashtable FILTER_TBL;
	private static final Hashtable ENTITY_ROOT_MAP;
	private static final Vector FLAGVALUE_COL;
	private Vector succQueueNameVct = new Vector();// for reconcilaition xml reprot

	static {
		FLAGVALUE_COL = new Vector();
		FILTER_TBL = new Hashtable();
		ENTITY_ROOT_MAP = new Hashtable();

		// they must be in ATTRCODE|type format where type is T for text, U for
		// flag,F for Multiple Flag , D for MAX date and A for MIN DATE
		// xml to filter, we have to use substring in sql
		FILTER_TBL.put("CATNAV", new String[] {"FLFILSYSINDC|F:FLFILSYSINDC|F" });//Confirm with Wayne, should be add the attribute (FLFILSYSINDC) to meta
		FILTER_TBL.put("FCTRANSACTION", new String[] {
				"PDHDOMAIN|F:PDHDOMAIN|X","MACHTYPEATR|U:TOMACHTYPE|X","MODELATR|T:TOMODEL|X",
				"WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
		FILTER_TBL
				.put("FEATURE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X",
						"FCTYPE|U:FCTYPE|X","COUNTRYLIST|F:COUNTRYLIST/COUNTRYELEMENT/COUNTRYCODE|X",
						"WITHDRAWDATEEFF_T|D:WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
		FILTER_TBL.put("SWFEATURE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X","FCTYPE|U:FCTYPE|X",
				"WITHDRAWDATEEFF_T|D:WTHDRWEFFCTVDATE|D" ,"WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D"});
		FILTER_TBL.put("GBT", new String[] { });
		FILTER_TBL.put("IMG", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X"});//no country infor in IMG xml
		FILTER_TBL.put("LSEOBUNDLE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X",
				"FLFILSYSINDC|F:FLFILSYSINDC|F", "BUNDLETYPE|F:BUNDLETYPE|X",
				"BUNDLUNPUBDATEMTRGT|D:WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D",
				"SPECBID|U:SPECIALBID|X","COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X",
				"DIVTEXT|F:DIVISION|X" });
		FILTER_TBL.put("MODEL", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X",
				"FLFILSYSINDC|F:FLFILSYSINDC|F","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D",
				"WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "SPECBID|U:SPECBID|X",
				"COFCAT|U:CATEGORY|X", "COFSUBCAT|U:SUBCATEGORY|X",
				"COFGRP|U:GROUP|X", "COFSUBGRP|U:SUBGROUP|X","COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X",
				"DIVTEXT|F:DIVISION|X"});
		FILTER_TBL.put("MODELCONVERT", new String[] {
				"PDHDOMAIN|F:PDHDOMAIN|X","MACHTYPEATR|U:TOMACHTYPE|X","MODELATR|T:TOMODEL|X","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D",
				"WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D" ,"COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X"});
		FILTER_TBL.put("LSEO", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X",
				"FLFILSYSINDC|F:FLFILSYSINDC|F",
				"LSEOUNPUBDATEMTRGT|D:WTHDRWEFFCTVDATE|D","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D",
				"SPECBID|U:SPECIALBID|X", "COFCAT|U:CATEGORY|X",
				"COFSUBCAT|U:SUBCATEGORY|X", "COFGRP|U:GROUP|X",
				"COFSUBGRP|U:SUBGROUP|X","COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X",
				"DIVTEXT|F:DIVISION|X" });
		FILTER_TBL.put("SLEORGNPLNTCODE",
				new String[] { //"COUNTRYLIST|F:COUNTRY_FC|X"
				
		});
		FILTER_TBL.put("SVCLEV", new String[] { });
		FILTER_TBL.put("SVCMOD", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X",
				"WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D","SVCMODCATG|U:CATEGORY|X",
				"SVCMODSUBCATG|U:SUBCATEGORY|X", "SVCMODGRP|U:GROUP|X","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D",
				"SVCMODSUBGRP|U:SUBGROUP|X","COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X",
				"DIVTEXT|F:DIVISION|X" });
		FILTER_TBL.put("PRODSTRUCT", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X","MACHTYPEATR|U:MACHTYPE|X","MODELATR|T:MODEL|X",
				"FCTYPE|U:FCTYPE|X", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D","FLFILSYSINDC|F:FLFILSYSINDC|F",
				"COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X","WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
		FILTER_TBL.put("SWPRODSTRUCT", new String[] {
				"PDHDOMAIN|F:PDHDOMAIN|X","MACHTYPEATR|U:MACHTYPE|X","MODELATR|T:MODEL|X","FCTYPE|U:FCTYPE|X",
				"WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D","COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X",
				"WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
		FILTER_TBL.put("WARR", new String[] { });
		FILTER_TBL.put("REVUNBUNDCOMP",
				new String[] {  });
		//REFOFER | REFOFERFEAT added based on document - BH FS ABR XML IDL 20110720.doc
		FILTER_TBL.put("REFOFER", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X" });
		FILTER_TBL.put("REFOFERFEAT", new String[] { });

		// only put the different between XML root and entitytype
		ENTITY_ROOT_MAP.put("LSEO", "SEO");
		ENTITY_ROOT_MAP.put("SWFEATURE", "FEATURE");
		ENTITY_ROOT_MAP.put("REVUNBUNDCOMP", "UNBUNDCOMP");
		ENTITY_ROOT_MAP.put("PRODSTRUCT", "TMF");
		ENTITY_ROOT_MAP.put("SWPRODSTRUCT", "TMF");
		ENTITY_ROOT_MAP.put("IMG", "IMAGE");

		// add all attribute in the vector that need to get flag value
		FLAGVALUE_COL.add("COUNTRYLIST/COUNTRYELEMENT/COUNTRYCODE");
		FLAGVALUE_COL.add("AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC");
//		FLAGVALUE_COL.add("COUNTRYLIST/COUNTRYELEMENT/COUNTRY_FC"); //for refofer
		FLAGVALUE_COL.add("COUNTRY_FC");
		FLAGVALUE_COL.add("FLFILSYSINDC");
	}

	public XMLFiterMQIDL(ADSIDLSTATUS abr) {
		this.abr = abr;
		profile = abr.getProfile();
		m_db = abr.getDatabase();
	}

	/**
	 * just for testing
	 */
	public XMLFiterMQIDL(Database db) {
		m_db = db;
	}

	private Vector getAllFilters(EntityItem setupEntity, String entitytype) {
		Vector vCond = new Vector();
		Vector vXmlCond = new Vector();

		String[] filters = (String[]) FILTER_TBL.get(entitytype);
		if (filters != null) {
			for (int i = 0; i < filters.length; i++) {
				String filterCond = getFilterSql(filters[i], setupEntity);
//				rptSb.append("<br/>"+replace(filterCond,">","&gt;") + "<br/>");
				if (isValidCond(filterCond)) {
					if (filters[i].endsWith("X")) {
						vXmlCond.add(filterCond);
					} else {
						vCond.add(filterCond);
					}
				}
			}
		}
		String xmlCond = compositeFilterCond(vXmlCond).toString();
		if (isValidCond(xmlCond)) {
			String xmlCondComp = compositeFilterforXml(xmlCond, entitytype);
			vCond.add(xmlCondComp);
		}
		return vCond;
	}

	// SOURCE : DIST
	// ATTRCODE|ATTRTYPE:ATTRCODE|ATTRTYPE
	private String getFilterSql(String filter, EntityItem setupEntity) {
		String[] srcDist = getArraybasedOnDelimiter(filter, ":");
		String[] srcCode = getArraybasedOnDelimiter(srcDist[0], "|");
		String[] distCode = getArraybasedOnDelimiter(srcDist[1], "|");
		String val = getAttributeValue(setupEntity, srcCode[0],distCode[0]);
		if(isValidCond(val)){	
			rptSb.append("<br/>"+srcCode[0] + " = " + val+"<br/>");			
//			rptSb.append("<br/>"+srcCode[0] + ("D".equals(srcCode[1])?" &lt;=":" = ") + val+"<br/>");

			if ("F".equals(srcCode[1])) {
				return getConditionforMultiFlag(setupEntity, val,
						distCode[0], "X".equals(distCode[1]));
			} else {
				return getEvaluationStr(distCode[0], val, "D".equals(srcCode[1]), "X".equals(distCode[1]), "A".equals(srcCode[1]));	
			}
		}
		//comment this based on document sep 8 -BH FS ABR XML IDL 20110908.doc
//		else if("D".equals(srcCode[1])){// withdrawdate is null, set it now()
//			rptSb.append("<br/>"+srcCode[0] + " &gt;= " + "current date" + "<br/>");
////			return getEvaluationStr(distCode[0], "current date", "D".equals(srcCode[1]), "X".equals(distCode[1]));
//			return "(" + distCode[0] +" is null or " + distCode[0] + " >= current date) ";
//		}
		return null;
	}
	
	
	private String getAttributeValue(EntityItem entity, String srcAttr, String distAttr){
		String result = null;
		if(distAttr != null && FLAGVALUE_COL.contains(distAttr)){
			result = PokUtils.getAttributeFlagValue(entity, srcAttr);
		}else{
			result =  PokUtils.getAttributeValue(entity, srcAttr,
					"|", null, false);			
		}

		if("COFGRP".equalsIgnoreCase(srcAttr) && !isValidCond(result)){
			result = "Base";// if is not specified, set it to 150(Base)
		}
		if(("COFGRP".equalsIgnoreCase(srcAttr) || "COFSUBGRP".equalsIgnoreCase(srcAttr))
				&&("Service".equalsIgnoreCase(PokUtils.getAttributeValue(entity, "COFCAT",
					"", null, false)))){
			return null;
		}
		if("DIVTEXT".equalsIgnoreCase(srcAttr)){
			result = this.replace(result, ",", "|");//change "," to "|", so that we can use the common function later
		}
		
		return result;
	}
	public String replace(String Text, String OldWord, String NewWord)
	{
		if(Text == null || OldWord == null || NewWord == null) return null;
		
		int intIndex;
		StringBuffer strResult = new StringBuffer(Text);
	        
		try {
			while ((intIndex = Text.indexOf(OldWord)) != -1) {			
				strResult.replace(intIndex, intIndex + OldWord.length(), NewWord);
				Text = strResult.toString();
			}
		}
		catch (StringIndexOutOfBoundsException ex){
			rptSb.append("<br />"+ex.getMessage()+"<br />");
			return null;
		}
		return Text;
	}

	// the rule was hard code in the code, so do not need to check if it is
	// valid.
	private String[] getArraybasedOnDelimiter(String str, String Delimiter) {
		int index = str.indexOf(Delimiter);
		String[] out = new String[2];
		out[0] = str.substring(0, index);
		out[1] = str.substring(index + 1);
		return out;
	}

	private String generateSqlBasedFilter(EntityItem setupEntity) {
		String entityType = PokUtils.getAttributeValue(setupEntity,
				"XMLENTITYTYPE", "", null, false);
		
		rptSb.append("<br /><h2>Looking for "+entityType+" with the following filters:</h2><br />");
		StringBuffer buffer = new StringBuffer(FILTER_SQL_BASE);
		// XMLENTITYTYPE
		if (isValidCond(entityType)) {
			buffer
					.append(" and "
							+ getEvaluationStr(XMLENTITYTYPE, entityType,
									false, false, false));
			String xmlStatus = PokUtils.getAttributeFlagValue(setupEntity, "XMLSTATUS");//put status checking here since it is special and for all entities
			if(isValidCond(xmlStatus) && "XSTATUS02".equalsIgnoreCase(xmlStatus)){
				buffer.append(" and STATUS = '0020' ");
				rptSb.append("<br/>STATUS = '0020'<br/>");
			}
//			XMLIDLREQDTS
			String reqDts = this.getAttributeValue(setupEntity, "XMLIDLREQDTS", null);
			if(reqDts == null){
				reqDts = "1980-01-01 00:00:00.000000";
			}else{
				rptSb.append("<br/>XMLIDLREQDTS = " + reqDts + "<br/>");
			}
//			reqDts = reqDts + " 00:00:00.000000";
			buffer.append(" and XMLCACHEDTS >= \'" + reqDts +"\' ");
			if("REFOFER".equals(entityType)) {
				String isDCG = PokUtils.getAttributeValue(setupEntity, "DCG", "", null, false);
				if("Y".equals(isDCG)) {
					buffer.append(" and length(trim(xmlcast(xmlquery('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/REFOFER_UPDATE\"; $i/REFOFER_UPDATE/PRODUCTID/text()' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") as varchar(8))))=7 ");
				}
			}
			
			String isold = PokUtils.getAttributeValue(setupEntity, "OLDINDC", "", null, false);
			if(isold == null || "".equals(isold) ) {
				buffer.append(" and OLDINDC != 'Y' ");
			}else if ("Y".equals(isold)) {
				buffer.append(" and (OLDINDC = 'Y' or OLDINDC is null) ");
			}
			
			Vector vCond = getAllFilters(setupEntity, entityType);
			StringBuffer filterBuf = compositeFilterCond(vCond);
			String appFilter = filterBuf.toString();
			if (isValidCond(appFilter))
				buffer.append(" and " + appFilter);
		}else{
			rptSb.append("<br />no entitytype was specified, " +
					"will get all records (XMLCACHEVALIDTO &gt; current timestamp in Cache.<br />");
		}
		buffer.append(" ORDER BY XMLCACHEDTS with ur");
		rptSb.append(NEWLINE);
//		rptSb.append("<br/> Request Sql: "+buffer.toString()+"<br/>");
		return buffer.toString();
	}

	private StringBuffer compositeFilterCond(Vector vCond) {
		StringBuffer filterBuf = new StringBuffer("");
		if (vCond != null && vCond.size() > 0) {
			filterBuf.append((String) vCond.get(0));
			if (vCond.size() > 1) {
				for (int i = 1; i < vCond.size(); i++) {
					filterBuf.append(" and " + (String) vCond.get(i));
				}
			}
		}
		return filterBuf;
	}

	private boolean isValidCond(String attr) {
		return attr != null && attr.trim().length() > 0;
	}

	private String getEvaluationStr(String key, String eVal, boolean isDate,
			boolean isXml, boolean isMinDate) {
		if(isMinDate){
			return " " + key + " >= \'" + eVal + "\'";
		} else if (isDate) {			
			return " " + key + " <= \'" + eVal + "\'";
		} else if (isXml) {
			return " " + key + "/text() = \"" + eVal + "\"";
		} else
			return " " + key + " = \'" + eVal + "\'";
	}

	private String getConditionforMultiFlag(EntityItem setupEntity,
			String multival, String attrCode, boolean isXmlFilter) {
		StringBuffer buffer = new StringBuffer("");		
		if (multival != null) {
			String[] vals = PokUtils.convertToArray(multival);
			buffer.append("(");
			for (int i = 0; i < vals.length; i++) {								
				if (i > 0)
					buffer.append(" or ");				
				if (isXmlFilter) {	
					buffer.append(attrCode);
					buffer.append("/text() = \"" + vals[i] + "\"");
				}else{
					buffer.append(attrCode);
					buffer.append(" like \'%|" + vals[i] + "|%\' or ");
					buffer.append(attrCode);
					buffer.append(" like \'" + vals[i] + "|%\' or ");
					buffer.append(attrCode);
					buffer.append(" like \'%|" + vals[i] + "\' or ");
					buffer.append(attrCode);
					buffer.append(" = \'" + vals[i] + "\'");
				}
			}
			buffer.append(")");
		}
		return buffer.toString();
	}

	public void getFullXmlAndSendToQue(EntityItem setupEntity)
			throws Exception {
				 
		String sql = generateSqlBasedFilter(setupEntity);
		log(sql);
		PreparedStatement ps = null;
		ResultSet result = null;
		succQueueNameVct.clear();
		// get the MQ information
		Vector queueName = new Vector();
		Vector rsbds = new Vector();
		Vector queues = getQueueInfors(setupEntity,queueName,rsbds);
		if(queues == null || queues.size() == 0){
			rptSb.append("<br/>no queue was set, will quit <br/>");
			log("no queue was set!");			
			log("ENTITYTYPE = " + setupEntity.getEntityType());
			log("ENTITYID = " + setupEntity.getEntityID());
			return;
		}
		String entityType = PokUtils.getAttributeValue(setupEntity,
				"XMLENTITYTYPE", "", null, false);
		String maxMsg = PokUtils.getAttributeValue(setupEntity,
				"XMLIDLMAXMSG", "", null, false);
		int max = -1;
		if(isValidCond(maxMsg)){
			try{
				max = Integer.parseInt(maxMsg);
				rptSb.append("<br/>XMLIDLMAXMSG = "+ maxMsg +" <br/>");
				log("XMLIDLMAXMSG = " + maxMsg);
			}catch(Exception e){
				log("XMLIDLMAXMSG can not be parsed to integer");
				rptSb.append("<br/>XMLIDLMAXMSG can not be parsed to integer<br/>");
				log(e.getMessage());
				throw e;
			}
		}
		try {
			ps = m_db.getODSConnection().prepareStatement(sql);
			result = ps.executeQuery();
			StringBuffer allEntityid = new StringBuffer(); 
			int count = 0;		  
			String setupDTS = ""; 
			//Bing add reconciliation 
			ArrayList MSGLOGList = new ArrayList();
			//TODO get SETUPDTS from valfrom of SETUP entity in Entity table
			EntityChangeHistoryGroup historygrop= setupEntity.getThisChangeHistoryGroup(m_db);
			if (historygrop != null){
				int ii = historygrop.getChangeHistoryItemCount();
    			abr.addDebug(setupEntity.getKey() + " getChangeHistoryItemCount: " + ii);
    			if (ii > 0){
    			    setupDTS = historygrop.getChangeHistoryItem(ii-1).getChangeDate();
    				abr.addDebug("setupDTS : " + setupDTS);
    			}
    			
			}else{
				abr.addDebug(setupEntity.getKey() + "can not find EntitychangeHistoryGroup! SETUPDTS is getnow().");
			}
			if ("".equals(setupDTS)){
				DatePackage dbNow = m_db.getDates();		  
				setupDTS = dbNow.getNow();
			}
//			AttributeChangeHistoryGroup adsStatusHistoy= getXMLIDLABRSTATUSHistory(setupEntity, "ADSIDLSTATUS");
//			String setupDTS = getSetupDTS(adsStatusHistoy);
			String setupType = setupEntity.getEntityType();
			int setupID = setupEntity.getEntityID();
			String msgType  = getMQCID(entityType);
			String MSGStatus = "";
			String reason = "";
			String MQPropFile = PokUtils.getAttributeFlagValue(setupEntity, MQUEUE_ATTR);
			if (MQPropFile == null){
				MQPropFile = "";
			}
			abr.addDebug("Total: "+ result.getFetchSize()+ " setupDTS: " + setupDTS + " setupType: " + setupType + " setupID: " + setupID + " entityType: " + entityType + " MQPropFile: " + MQPropFile );
			//end todo
			String endtime = null;
			while (result.next()) {			
				String xml = result.getString(3);//upgrade to jdk 6.0, we can use the XML type to deal with it(JDBC4), it should be easy
				int entityid = result.getInt(2);
				//get the time of the DTSofMSG from the xml				
				String DTSofMSG = "";
				if(xml == null){
					DTSofMSG = result.getString(4);
				}else{
					String dts_start = "<DTSOFMSG>";
					String dts_end   = "</DTSOFMSG>";
					int iPos_start   = xml.indexOf(dts_start);
					int iPos_end     = xml.indexOf(dts_end);
					if(iPos_start>-1 && iPos_end>-1 && iPos_end>iPos_start){
						DTSofMSG = xml.substring(iPos_start + dts_start.length(),iPos_end);
					}else{
						DTSofMSG = result.getString(4);
					}					
				} 
				if (xml == null) {
					log("Can not get the xml for " + entityid);
					//Bing added reconciliation
					MSGStatus = "F";
					reason = "Can not get the xml from xmlidlcache for " + entityid;
					
					StringTokenizer stString = new StringTokenizer(MQPropFile, "|" , false);
	       			while (stString.hasMoreElements()) {
	       				String sigleMQPropfile = stString.nextToken();
	       				putMSGList(MSGLOGList, setupType, setupID, setupDTS, "", msgType, entityType, entityid, DTSofMSG, MSGStatus, reason, sigleMQPropfile);
	       			}
					continue;
				}
				String cacheDTS = changeFormatOfTime(result.getString(4));
				endtime = cacheDTS;
				xml = removeIllString(xml);//because driver problem, we may have the string like ??
				for (int i = 0;i < queues.size();i++) {
					Hashtable ht = (Hashtable) queues.get(i);
					ResourceBundle resourceBundle = (ResourceBundle)rsbds.get(i);
					String queueN = (String)queueName.get(i);
					System.out.println(queueN);
					try {
						MSGStatus = "";
						reason = "";
						String mqcid = getMQCID(entityType);						
						Hashtable userProperties = MQUsage.getUserProperties(resourceBundle, mqcid);
						ht.put(MQUsage.MQCID,mqcid);
						ht.put(MQUsage.XMLTYPE,"ADS");
						MQUsage.putToMQQueueWithRFH2(XML_TITLE+xml, ht,userProperties);
						MSGStatus = "S";
					} catch (com.ibm.mq.MQException ex) {
						rptSb.append("<br/><h2>Error: An MQSeries error occurred for queue: "+ queueN + ".</h2><br/>");
						rptSb.append("<!--"+ex.getMessage()+"-->");
						log(ex.getMessage());
						System.out.println(ht);
						ex.printStackTrace();
						MSGStatus = "F";
						reason = "Error:MQ error occurred CompletionCode " + ex.completionCode + " ReasonCode"  + ex.reasonCode;
						abr.addError("Error: An MQSeries error occurred for queue: "+ queueN + "");
					} catch (java.io.IOException ex) {
						rptSb.append("<br/><h2>Error: An error occurred when writing to the MQ message buffer for queue: "+ queueN + ".</h2><br/>");
						rptSb.append("<!--"+ex.getMessage()+"-->");
						log(ex.getMessage());
						System.out.println(ht);
						ex.printStackTrace();
						MSGStatus = "F";
						reason = "Error: An error occurred when writing to the MQ message buffer.";
						abr.addError("Error: An error occurred when writing to the MQ message buffer. "+ queueN + "");
					} catch (Exception e){
						MSGStatus = "F";
						reason = "An error occurred when putMQ";
						abr.addError("An error occurred when putMQ" + queueN);
					}finally{
						reason =reason.length()<64?reason:reason.substring(0,63);
//						bing added reconciliation
					    DatePackage dbNow = m_db.getDates();
					    String sendMSGDTS = dbNow.getNow();
						//private void putMSGList(ArrayList MSGLOGList, String setupType, int setupid, String setupDTS, String sendMSGDTS, String msgType, String entityType, int entityID, String DTSofMSG, String MSGStatus, String reason, String MQPropFile){
						putMSGList(MSGLOGList, setupType, setupID, setupDTS, sendMSGDTS, msgType, entityType, entityid, DTSofMSG, MSGStatus, MSGStatus.equals("S")?"":reason, queueN);
					}
				}
				
				if (count > 0)
					allEntityid.append(",");
				allEntityid.append(entityid);
				count++;
				if(count%1000 == 0)
					abr.setTextValue(setupEntity, "XMLIDLREQDTS", cacheDTS);//After every 1000 XML messages successfully placed on the MQ Series Queue
				if(max >= 0  && count == max){
					abr.setTextValue(setupEntity, "XMLIDLREQDTS", cacheDTS);//// successfully place all message to the mq based on max
					log("The count of msg has reached the max msg definition in the setup entity: " + max);
					break;
				}			
			}
			if(endtime != null)
				abr.setTextValue(setupEntity, "XMLIDLREQDTS", endtime); // successfully place all message to the mq
			if (MSGLOGList.size()>0){
				//inertMSGLOG (ArrayList MSGLOGList, String setupType, int setupid,String setupDTS, String entityType, String MQPropFile){
				inertMSGLOG(MSGLOGList, setupType, setupID, setupDTS, entityType);
			}
			
		    //end bing added
			rptSb.append("<br/><br/><h3>" +count + " entities were sent. </h3><br/>");
//			rptSb.append("<br/>Testing, not send all entities.<br/>");
			rptSb.append("<!--");
			rptSb.append("<br/>"+allEntityid+"<br/>");
			rptSb.append("-->");
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
					System.err
							.println("getFullXmlAndSendToQue(), unable to close result. "
									+ e);
				}
				result = null;
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					System.err
							.println("getFullXmlAndSendToQue(), unable to close ps. "
									+ e);
				}
				ps = null;
			}
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();

		}
	}

	private String changeFormatOfTime(String cacheDTS) {
		String s1 = cacheDTS.substring(0,23);
		String s2 = cacheDTS.substring(23);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try{
			Date date = sdf.parse(s1);
			SimpleDateFormat sdf_new = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
			return sdf_new.format(date)+s2;
		}catch(Exception e){
			rptSb.append("<br/><h2>Error: Parse Date Error.</h2><br/>");
			rptSb.append("<!--"+e.getMessage()+"-->");
			log(e.getMessage());					
			e.printStackTrace();
		}
		return null;
	}

	private Vector getQueueInfors(EntityItem setupEntity,Vector queueNames,Vector Bundles) {
		Vector queueCol = new Vector();
		String mqueue = PokUtils
				.getAttributeFlagValue(setupEntity, MQUEUE_ATTR); 
		rptSb.append("<br/><h2>"+"Queue Information: "+"</h2><br/>");
		rptSb.append("<br/>"+mqueue+"<br/>");
		if (mqueue != null) {
			String[] queue = PokUtils.convertToArray(mqueue);
			for (int i = 0; i < queue.length; i++) {
				try{
					ResourceBundle rsBundleMQ = ResourceBundle.getBundle(queue[i],
	                        getLocale(profile.getReadLanguage().getNLSID()));
//					ResourceBundle rsBundleMQ = ResourceBundle.getBundle(queue[i]);//TODO commemt back
					Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
					queueCol.add(ht);
					queueNames.add(queue[i]);
					Bundles.add(rsBundleMQ);
				}catch(Exception e){
					rptSb.append("<br/>Can not find the Queue information for "+ queue[i] +"<br/>");
					rptSb.append("<!--"+e.getMessage()+"-->");
					abr.addError("Prop file "+queue[i] + " "+setupEntity.getKey() + " not found");
					continue;
				}				
			}
		}
		return queueCol;
	}
	
	private String compositeFilterforXml(String cond, String entitytype) {
		if (isValidCond(cond)) {
			StringBuffer buff = new StringBuffer("");
			String xmlroot = (String) ENTITY_ROOT_MAP.get(entitytype);
			if (xmlroot == null) {
				xmlroot = entitytype;
			}
			String root = xmlroot + UPDATE_SUFFIX;
			buff
					.append("xmlexists(")
					.append(NAMESPACE_PREFIX)
					.append(root)
					.append("\"; $i/")
					.append(root)
					.append("[")
					.append(cond)
					.append(
							"]\' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")");
			return buff.toString();
		}
		return null;
	}
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
	
	private String getMQCID(String entitytype) { 
		String cid = (String) ENTITY_ROOT_MAP.get(entitytype);
		if (cid == null) {
			cid = entitytype;
		}
		return cid + "_UPDATE"; 
	}
	
	private String removeIllString(String str){
		int index = str.indexOf("?>");
		if(index > 0){
			str = str.substring(index +2);
		}
		return str;
	}
	
	public String getReport(){
		return rptSb.toString();
	}
	private void log(String msg) {
//		System.out.println(msg);
		abr.addDebug(msg);//TODO comment back
	}
    //bing added reconciliation
	private void putMSGList(ArrayList MSGLOGList, String setupType, int setupid, String setupDTS, String sendMSGDTS, String msgType, String entityType, int entityID, String DTSofMSG, String MSGStatus, String reason, String MQPropFile) throws MiddlewareException, SQLException{
		MSGLOGList.add(new String[]{Integer.toString(entityID),sendMSGDTS,msgType,DTSofMSG, MSGStatus,reason,MQPropFile});
		//TODO change to 100
		if (MSGLOGList.size() >= 100){
			inertMSGLOG(MSGLOGList,setupType,setupid, setupDTS, entityType);
		}
	}
	private void inertMSGLOG(ArrayList MSGLOGList, String setupType,
			int setupid, String setupDTS, String entityType) throws MiddlewareException, SQLException {
		long startTime = System.currentTimeMillis();
		Iterator list = MSGLOGList.iterator();

		PreparedStatement ps = null;
		StringBuffer strbSQL = new StringBuffer();
		strbSQL
				.append("INSERT INTO cache.XMLMSGLOG (SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, SENDMSGDTS, MSGTYPE, MSGCOUNT, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGSTATUS, REASON, MQPROPFILE) ");
		strbSQL.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			//m_db.setAutoCommit(false);
			ps = m_db.getODSConnection().prepareStatement(new String(strbSQL));
			while (list.hasNext()) {
				String record[] = (String[]) list.next();
				int entityid = Integer.parseInt(record[0]);
				ps.setString(1, setupType);
				ps.setInt(2, setupid);
				ps.setString(3, setupDTS);
				ps.setString(4, (String) record[1]);
				ps.setString(5, (String) record[2]);
				ps.setInt(6, 1);
				ps.setString(7, entityType);
				ps.setInt(8, entityid);
				ps.setString(9, (String) record[3]);
				ps.setString(10, (String) record[4]);
				ps.setString(11, (String) record[5]);
				ps.setString(12, (String) record[6]);
				ps.addBatch();
			}
			ps.executeBatch();
			//TODO change to m_db.getODSConnection() accroding to DBA response
			if (m_db.getODSConnection() != null){
				m_db.getODSConnection().commit();
			}
			//m_db.commit();
			abr.addDebug(MSGLOGList.size()
					+ " records was inserted into table. Total Time: "
					+ Stopwatch.format(System.currentTimeMillis() - startTime));
		} catch (MiddlewareException e){
			abr.addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		    throw e;
		}catch (SQLException rx) {
			abr.addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		    throw rx;
		}finally {
			MSGLOGList.clear();
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}	
//	/**
//	 * get history of XMLIDLABRSTATUS
//	 * 
//	 * @param mqAbr
//	 * @return
//	 * @throws MiddlewareException
//	 */
//	
//	  private AttributeChangeHistoryGroup getXMLIDLABRSTATUSHistory(EntityItem setupEntity, String attCode) throws MiddlewareException {
//	        EANAttribute att = setupEntity.getAttribute(attCode);
//	        if (att != null) {
//	            return new AttributeChangeHistoryGroup(m_db, profile, att);
//	        } else {
//	        	abr.addDebug(" ADSIDLSTATUS of "+setupEntity.getKey()+ "  was null");
//	            return null;
//	        }
//	    }
//		/**
//		 * get setupDTS
//		 * @param adsStatusHistoy
//		 * @return
//		 * @throws MiddlewareException 
//		 */
//	  private String getSetupDTS (AttributeChangeHistoryGroup adsStatusHistoy) throws MiddlewareException{
//		  String setupDTS = null;
//		  if (adsStatusHistoy != null && adsStatusHistoy.getChangeHistoryItemCount() > 1) {	     
//              // get the historyitem count.
//              int i = adsStatusHistoy.getChangeHistoryItemCount();
//              // Find the time stamp for "Queued" Status. Notic: last
//              // chghistory is the current one(in process),-2 is queued.
//              AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistoy.getChangeHistoryItem(i - 2);
//              if (achi != null) {
//            	  abr.addDebug("getSetupDTS [" + (i - 2) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
//                          + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
//                  if (achi.getFlagCode().equals("0020")) {
//                	  setupDTS = achi.getChangeDate();
//                  } else {
//                	  abr.addDebug("getSetupDTS for the value of " + achi.getFlagCode()
//                              + "is not Queue. ");
//                	  throw new MiddlewareException("getSetupDTS for the value of " + achi.getFlagCode()
//                              + "is not Queue. ");
//                  }
//              }
//                
//	      }else {
//	    	  abr.addDebug("There is no ADSIDLSTATUS history!");
//	      }
//		  return setupDTS;
//	  }
	
}
