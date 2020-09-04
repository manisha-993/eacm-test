// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;


import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.Constants;
import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.SBRException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
* base class for ADS feeds in ADSABRSTATUS abr
*
*/
//$Log: XMLMQAdapter.java,v $
//Revision 1.29  2019/03/08 06:53:58  xujianbo
//Roll back again for the code for  Story 1909631 EACM XML version control by XSLT
//
//Revision 1.27  2019/02/26 07:18:09  xujianbo
//roll back code for Story 1909631 EACM XML version control by XSLT
//
//Revision 1.23  2017/06/21 13:05:52  wangyul
//1693242: EACM SPF Feed to PEP - Meta and Process Updates for Feature conversions and filtering support for shadow products
//
//Revision 1.22  2015/02/04 14:48:54  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.21  2014/01/07 13:03:00  guobin
//fix throw null error , add more debug infor.
//
//Revision 1.20  2013/08/13 08:31:02  wangyulo
//buid request for the RTC WI 986855 -- DIVISION FILTER FOR PRODUCT FEED
//
//Revision 1.19  2012/11/08 14:12:36  guobin
//Ticket Number IN3037198 has been opened for the EACM Data Issue  add PDHDOMAIN as search condition
//
//Revision 1.18  2012/08/31 16:01:40  wangyulo
//Fix the defect of  the New Sev 1 (126457) where another few FC sent only to WWPRT queue
//
//Revision 1.17  2011/12/14 02:27:31  guobin
//update country filter for service LSEO
//
//Revision 1.16  2011/10/26 08:05:36  guobin
// Final é–¿ç‡‚æ‹· support for old data é–¿ç‡‚æ‹· CQ 67890  Changed to handle offerings that have an AVAIL left in Draft where the data is older than é–¿ç‡‚æ‹·2010-03-01é–¿ç‡‚æ‹·.
//
//Revision 1.15  2011/10/17 13:45:39  guobin
//Support both 0.5 and 1.0 XML together  (BH FS ABR Data Transformation System Feed 20110914.doc)
//
//Revision 1.14  2011/09/26 08:33:05  guobin
//update the filter for REFOFER and REFOFERFEAT entity
//
//Revision 1.13  2011/09/08 07:45:16  guobin
//add for REFOFER & REFOFERFEAT ADS ABR
//
//Revision 1.12  2011/07/06 13:35:37  guobin
//Expand XMLSTATUS filter to other entities - pages 12 and 19
//some entitys need to add the XMLSTATUS for the country filter.
//XMLMQAdapter.java has undated and need to check into CVS.
//Nancy approved.
//
//Revision 1.11  2011/07/05 14:04:55  guobin
//Expand XMLSTATUS filter to other entities
//XMLMQAdapter.java has undated and need to check into CVS
//Nancy approved
//
//Revision 1.10  2011/06/28 07:58:56  guobin
// Build request - update country filter according to latest spec
//1. Expand FCTYPE filter to other entities - pages 12 and 18
//XMLMQAdapter.java has undated and need to check into CVS
//Approved by Fred
//
//Revision 1.9  2011/06/13 14:08:22  guobin
//add the MOELATR for MODELCONVERT and FCTRANSACTION
//
//Revision 1.8  2011/06/01 02:19:08  guobin
//change the country filter
//
// Revision 1.2  2008/04/29 14:29:11  wendy
// Add CID support
//
// Revision 1.1  2008/04/25 12:11:37  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQAdapter implements XMLMQ, Constants
{
	protected static final Hashtable ADSTYPES_TBL;	
	/**
	 * ADSTYPE for PeriodicABR
	 */
	private static final boolean isDebug = true;
	private boolean isVaildREFOFERFEAT = true;
	private boolean isService = false;
	protected static final String CHEAT ="@@@";
	protected static boolean isFilterWWCOMPAT = false;
	protected Hashtable wwcompMQTable = new Hashtable();
	private String attrXMLABRPROPFILE ="XMLABRPROPFILE";
	private Profile _swProfile = null;
	protected static final String KEY_SETUPArry = "SETUPARRAY";
	private EntityList mf_elist = null;
	static {
		ADSTYPES_TBL = new Hashtable();
		ADSTYPES_TBL.put("20","GENERALAREA");//need use 'GENERALAREA' to compare with XMLENTITYTYPE attribute of EXTXMLFEED 
		ADSTYPES_TBL.put("30","Deletes");
		ADSTYPES_TBL.put("40","XLATE");
		ADSTYPES_TBL.put("50","WWCOMPAT");
	}
	/********************************************************
	 * Do for CR 32199 - Country Filter for EACM product data
	 * ******************************************************
	 */
	private static final Hashtable FILTER_TBL;	//Entity and filter Attributes for ADSIDLSTATUS
	static{
		FILTER_TBL = new Hashtable();
		
        //they must be in ATTRCODE format 
		FILTER_TBL.put("FEATURE", new String[]{"STATUS","FCTYPE","COUNTRYLIST","PDHDOMAIN"});
		FILTER_TBL.put("MODEL", new String[]{"STATUS","SPECBID","COFCAT","COFSUBCAT","COFGRP","COFSUBGRP","COUNTRYLIST","FLFILSYSINDC","PDHDOMAIN","DIVTEXT"});
		FILTER_TBL.put("SVCMOD", new String[]{"STATUS","SVCMODCATG","SVCMODGRP","SVCMODSUBCATG","SVCMODSUBGRP","COUNTRYLIST","PDHDOMAIN","DIVTEXT"});
		FILTER_TBL.put("LSEOBUNDLE", new String[]{"STATUS","SPECBID","BUNDLETYPE","COUNTRYLIST","FLFILSYSINDC","PDHDOMAIN","DIVTEXT"});
		FILTER_TBL.put("LSEO", new String[]{"STATUS","SPECBID","COFCAT","COFSUBCAT","COFGRP","COFSUBGRP","COUNTRYLIST","FLFILSYSINDC","PDHDOMAIN","DIVTEXT"});
		
		FILTER_TBL.put("PRODSTRUCT",   new String[]{"STATUS","FCTYPE","MACHTYPEATR","MODELATR","COUNTRYLIST","FLFILSYSINDC","PDHDOMAIN"});
		FILTER_TBL.put("SWPRODSTRUCT", new String[]{"STATUS","FCTYPE","MACHTYPEATR","MODELATR","COUNTRYLIST","PDHDOMAIN"});
		//MACHTYPEATR used for TOMACHTYPE T, WTHDRWEFFCTVDATE will be on the MODEL
		FILTER_TBL.put("MODELCONVERT",  new String[]{"STATUS","MACHTYPEATR","MODELATR","COUNTRYLIST","PDHDOMAIN"});
		FILTER_TBL.put("FCTRANSACTION", new String[]{"STATUS","MACHTYPEATR","MODELATR","PDHDOMAIN"});		
		FILTER_TBL.put("IMG", new String[]{"STATUS","COUNTRYLIST","PDHDOMAIN"});
		
		// need add the FLFILSYSINDC attribute of CATNAV
		FILTER_TBL.put("CATNAV", new String[]{"STATUS","FLFILSYSINDC"});//no domains no FLFILSYSINDC		
		FILTER_TBL.put("SWFEATURE", new String[]{"STATUS","FCTYPE","PDHDOMAIN"});		
		FILTER_TBL.put("GBT", new String[]{"STATUS"});
		FILTER_TBL.put("REVUNBUNDCOMP", new String[]{"STATUS"});
		FILTER_TBL.put("SLEORGNPLNTCODE", new String[]{"STATUS"});
		
		
		FILTER_TBL.put("SVCLEV", new String[]{"STATUS"});
		FILTER_TBL.put("WARR", new String[]{"STATUS"});
		//isPeriodicABR
		FILTER_TBL.put("WWCOMPAT", 	new String[]{"BRANDCD"}); //WWCOMPAT doesnt exist
		//XLATE
		//GENAREA
		
		//new add REFOFER and REFOFERFEAT
		FILTER_TBL.put("REFOFER",new String[]{"STATUS","COUNTRYLIST","ENDOFSVC"});
		FILTER_TBL.put("REFOFERFEAT",new String[]{"STATUS","COUNTRYLIST","ENDOFSVC"});
		//new add REFOFER and REFOFERFEAT end
		
		FILTER_TBL.put("SWSPRODSTRUCT", new String[]{"STATUS"});
	}
	private static final String XMLSTATUS = "XMLSTATUS";
	
	/**********************************
    * get the name(s) of the MQ properties file to use
    */
	// RQK Change this to pass in root entity
	// change to get mq propfile names from attribute on root entity
	// if attribute does not exist then use ADSMQSERIES. Return Vector 
	// *** it will not call by other java class ***
    public Vector getMQPropertiesFN(EntityItem rootEntity,ADSABRSTATUS abr) { 
    	abr.addDebug("countryfilter start");
    	String val = PokUtils.getAttributeFlagValue(rootEntity, attrXMLABRPROPFILE);
    	Vector vct = new Vector();    	
    	if (val != null) {
    		// parse the string into substrings    	        
    	    StringTokenizer st = new StringTokenizer(val,PokUtils.DELIMITER);    	           
    	    while(st.hasMoreTokens())
            {
    	        vct.addElement(st.nextToken());
            }                       	        
    	} 	
    	
    	Vector mqVctFilter = new Vector();
    	try {
			mqVctFilter = getMQPropertiesFilter(rootEntity,abr);
		} catch (Exception e) {
			abr.addDebug("getMQPropertiesFN error="+e.getMessage());
			e.printStackTrace();
		}
		/**
		 * If after processing all of the referenced REFOFER 
		 * there are no instances of <RELATEDREFOFERELEMENT>,
		 * then do not send the XML
		 * use isVaildREFOFERFEAT to check it
		 */
		if(isVaildREFOFERFEAT == false){
			return new Vector();
		}
		
		addAllMq(vct,mqVctFilter,abr);
		
		if(vct.size()==0)
		{
			//default queue
			vct.add(ADSABRSTATUS.ADSMQSERIES);	
		}
		
		abr.addDebug("countryfilter end");
        return vct;
    }
    /**
     * get the MQ properties file to use for Version0.5 and Version1.0
     * @throws SQLException 
     * @throws Exception 
     */
    public Hashtable getMQPropertiesVN(EntityItem rootEntity,ADSABRSTATUS abr) throws SQLException ,MiddlewareException{ 
    	abr.addDebug("countryfilter start");
    	String val = PokUtils.getAttributeFlagValue(rootEntity, attrXMLABRPROPFILE);
    	Vector vct = new Vector();    	
    	if (val != null && rootEntity.getEntityType().equals("ADSXMLSETUP")) {
    		// parse the string into substrings    	        
    	    StringTokenizer st = new StringTokenizer(val,PokUtils.DELIMITER);    	           
    	    while(st.hasMoreTokens())
            {
    	        vct.addElement(st.nextToken());
            }                       	        
    	}    	
    	Hashtable mqTable = new Hashtable();
    	//start to check the special filtering of MODEL based on classification attributes.
    	boolean isVaildModel = true;
    	try {
			isVaildModel = checkModelVaild(rootEntity,abr);
		} catch (SQLException e1) {
			abr.addDebug("getMQPropertiesVN error="+e1.getMessage());
			e1.printStackTrace();
			throw e1;
		}    	
    	if(!isVaildModel) {
    		abr.addOutput("Data is not valid for filter of MODEL based on classification attributes (COFCAT,COFSUBCAT,COFGRP) ");
    		return mqTable;
    	} 
    	//End the check for model
    	
    	try {
    		mqTable = getMQPropertiesFilterVN(rootEntity,abr,vct);
    		
    		// shadow filter
    		mqTable = shadowFilter(rootEntity, abr, mqTable);
		} catch (Exception e) {
			if(e instanceof SBRException){
				SBRException sbrException = (SBRException) e;
				abr.addError("getMQPropertiesVN error=" + sbrException.toString());				
				e.printStackTrace();
				MiddlewareException ex=  new MiddlewareException("getMQPropertiesVN error="+sbrException.toString());
				throw ex;
			} else{
				abr.addError("getMQPropertiesVN error="+e.getMessage());				
				e.printStackTrace();
				MiddlewareException ex=  new MiddlewareException("getMQPropertiesVN error="+e.getMessage());
				throw ex;
			}
		} finally{
			if (mf_elist != null){				
				mf_elist = null;
			}
		}
		return mqTable;
    }
    
    /**
     * 1693242: EACM SPF Feed to PEP - Meta and Process Updates for Feature conversions and filtering support for shadow products
     * When we send xml to downstream,
     * for MODEL if MODEL.COFSUBCAT=Shadow
     * for FCTRANSACTION if FCTRANSACTION.FTSUBCAT=Shadow
     * then we don't send xml to any downstream except PEP
     * 
     * @param rootEntity - Root entity for the ADSABRSATUS
     * @param mqTable - MQ map for version 1.0
     * return - Matched MQ map
     */
	private Hashtable shadowFilter(EntityItem rootEntity, ADSABRSTATUS abr, Hashtable mqTable) {
		boolean isShadowFilter = false;
		// we just send 10 version currently
		String version10 = "10";
		Vector mqVct = (Vector)mqTable.get(version10);
		Hashtable matchedMqTable = new Hashtable();
		Vector mq10Vct = new Vector();
		matchedMqTable.put(version10, mq10Vct);
		matchedMqTable.put(KEY_SETUPArry, mqTable.get(KEY_SETUPArry));
		String attrValue = null;
		if (rootEntity.getEntityType().equals("MODEL")) {
			attrValue = PokUtils.getAttributeValue(rootEntity, "COFSUBCAT", "|", "");
			abr.addDebug("shadowFilter MODEL.COFSUBCAT=" + attrValue);
		} else if (rootEntity.getEntityType().equals("FCTRANSACTION")) {
			attrValue = PokUtils.getAttributeValue(rootEntity, "FTSUBCAT", "|", "");
			abr.addDebug("shadowFilter FCTRANSACTION.FTSUBCAT=" + attrValue);
		}
		if ("Shadow".equals(attrValue)) {
			isShadowFilter = true;
			abr.addDebug("shadowFilter isShadowFilter=" + isShadowFilter);
			String shadowFilterStr = ABRServerProperties.getValue("ADSABRSTATUS", "_shadow_filter");
			if (shadowFilterStr != null && shadowFilterStr.length() > 0) {
				abr.addDebug("shadowFilter ADSABRSTATUS_shadow_filter=" + shadowFilterStr);
	    		String[] shadowFilterMQs = shadowFilterStr.trim().split(",");
	    		abr.addDebug("shadowFilter found " + shadowFilterMQs.length + " configs from file");
	    		for (int i = 0; i < shadowFilterMQs.length; i++) {
	    			String mq = shadowFilterMQs[i].trim();
	    			if (mqVct != null && mqVct.contains(mq)) {
	    				mq10Vct.add(mq);
	    			}
	    		}
	    	} else {
	    		abr.addDebug("shadowFilter not found in abr.server.properties for ADSABRSTATUS");
	    	}
		}

    	if (isShadowFilter) {
    		Vector matchedMqVct = (Vector)matchedMqTable.get(version10);
    		if (matchedMqVct.size() > 0) {
        		// generate a message show in report, tell the user shadow filter info
        		StringBuffer sb = new StringBuffer();
        		sb.append("There is a shadow filter matched, the original MQ:");
        		for (int i = 0; i < mqVct.size(); i++) {
        			sb.append(" ");
        			sb.append((String)mqVct.get(i));
        		}
        		sb.append(", we will only send to MQ:");
        		for (int i = 0; i < matchedMqVct.size(); i++) {
        			sb.append(" ");
        			sb.append((String)matchedMqVct.get(i));
        		}
        		abr.addOutput(sb.toString());
    		} else {
    			abr.addOutput("The " + rootEntity.getKey() + " matched shadow filter, but not found matched MQ.");
    		}
    		return matchedMqTable;
    	}
    	return mqTable;
    }
    
    /**
     * add the mq and the filter mq together
     * @param mqVct
     * @param mqVctFilter
     * @param abr
     */
	private void addAllMq(Vector vct, Vector mqVctFilter,ADSABRSTATUS abr) {
		String mqFileName = "";
    	for(int i=0;i<mqVctFilter.size();i++){
    		mqFileName = (String)mqVctFilter.get(i);
    		if(!vct.contains(mqFileName)){
    			vct.add(mqFileName);
    		}
    	}
    	if(XMLMQAdapter.isDebug){
    		for(int i=0;i<vct.size();i++){
    			abr.addDebug("Print MQ["+i+"]= "+vct.get(i));
    		}
    	}
	}
    /**
     * get the entity according the entityid and entitytype
     * @param dbCurrent
     * @param item
     * @return
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
    private EntityItem getEntityItem(Database dbCurrent, EntityItem item) throws MiddlewareRequestException, SQLException, MiddlewareException {
	   	 EntityList m_elist = dbCurrent.getEntityList(_swProfile,
	                new ExtractActionItem(null, dbCurrent, _swProfile,"dummy"),
	                new EntityItem[] { new EntityItem(null, _swProfile, item.getEntityType(), item.getEntityID()) });
	   	 EntityItem rootEntity  = m_elist.getParentEntityGroup().getEntityItem(0);	   	    	 
	   	 return rootEntity;
	} 
    /**
     * convert the null to blank to escape the nullpointer exception
     * @param fromValue
     * @return
     */
    private String convertValue(String fromValue){
    	return fromValue==null?"":fromValue;
    }
    /**
     * set the attribute value from the root entity to the rootTable
     * setRoot entity
     * @param rootItem
     * @param rootTable
     * @param abr
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void setRootEntity(EntityItem rootItem,Hashtable rootTable,ADSABRSTATUS abr) 
    throws MiddlewareRequestException, SQLException, MiddlewareException{
    	String rootEntityType = rootItem.getEntityType();
    	String filters[] = (String[])FILTER_TBL.get(rootEntityType);
    	
		String attrvalue ="";
    	for (int i=0; i<filters.length; i++){
			String attrcode = filters[i];			
			if(attrcode.equals("FCTYPE")){
				if(rootEntityType.equals("PRODSTRUCT")||rootEntityType.equals("SWPRODSTRUCT")){
					attrvalue = getFCTYPE(rootItem, rootEntityType, attrcode, abr);
				}else{
					attrvalue = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));
				}				
				rootTable.put(attrcode, attrvalue);
			}else if(attrcode.equals("STATUS")||attrcode.equals("BUNDLETYPE")
					 ||attrcode.equals("SVCMODCATG")||attrcode.equals("SVCMODGRP")
					 ||attrcode.equals("SVCMODSUBCATG")||attrcode.equals("SVCMODSUBGRP")
					 ||attrcode.equals("FLFILSYSINDC")||attrcode.equals("PDHDOMAIN")){
				attrvalue = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));
				rootTable.put(attrcode, attrvalue);
			}else if(attrcode.equals("COFCAT")||attrcode.equals("COFSUBCAT")
					||attrcode.equals("COFGRP")||attrcode.equals("COFSUBGRP")){
				if(rootEntityType.equals("MODEL")){
					attrvalue = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));					
				}else{
					//LSEO WWSEOLSEO-u: MODELWWESO-u	MODEL	COFCAT
					attrvalue = getCOFMODEL(rootItem, rootEntityType, attrcode, abr);
				}
				if(attrcode.equals("COFCAT")){
					if(attrvalue.equals("102")){
						isService = true;
					}
				}
				rootTable.put(attrcode, attrvalue);
			} else if(attrcode.equals("SPECBID")){
				attrvalue = convertValue(getSPECBID(rootItem, rootEntityType, attrcode, abr));
				rootTable.put(attrcode, attrvalue);
			} else if(attrcode.equals("MACHTYPEATR")){
				//MACHTYPEATR is used to filter on TOMACHTYPE for MODELCONVERT and FCTRANSACTION
				if(rootEntityType.equals("MODELCONVERT")||rootEntityType.equals("FCTRANSACTION")){
					attrvalue = convertValue(PokUtils.getAttributeValue(rootItem, "TOMACHTYPE", "", null, false));
				}else{
					//PRODSTRUCT-d	MODEL	MACHTYPEATR
					attrvalue = getMACHTYPEATR(rootItem, rootEntityType, attrcode,abr);									
				}
				rootTable.put(attrcode, attrvalue);
			} else if(attrcode.equals("MODELATR")){
				//MODELATR is used to filter on TOMODEL for MODELCONVERT and FCTRANSACTION
				//Does MODELATR attribute support to filter on MODELCONVERT and FCTRANSACTION entity ? 
				//Wayne: No
				//attrvalue = PokUtils.getAttributeValue(rootItem, attrcode, "", null, false);//TEXT
				if(rootEntityType.equals("MODELCONVERT")||rootEntityType.equals("FCTRANSACTION")){
					attrvalue = getMACHTYPEATR(rootItem, rootEntityType, "TOMODEL",abr);
				}else{
					attrvalue = getMACHTYPEATR(rootItem, rootEntityType, attrcode,abr);
				}
				
				rootTable.put(attrcode, attrvalue);
			} else if(attrcode.equals("COUNTRYLIST")){
				attrvalue = convertValue(getCOUNTRYLIST(rootItem, rootEntityType, attrcode,abr));
				rootTable.put(attrcode, attrvalue);
			} else if(attrcode.equals("ENDOFSVC")){
				if(rootEntityType.equals("REFOFER")){
					attrvalue = convertValue(PokUtils.getAttributeValue(rootItem, attrcode, "", null, false));
				}else if(rootEntityType.equals("REFOFERFEAT")){
					attrvalue = getENDOFSVC(rootItem, rootEntityType, attrcode, abr);
				}
				rootTable.put(attrcode, attrvalue);
			}
			// 2013-07-29 RCQ00255719 - filter feed by Division
			else if(attrcode.equals("DIVTEXT")){
				attrvalue = getDIVISION(rootItem, rootEntityType, "DIV", abr);				
				rootTable.put(attrcode, attrvalue);				
			}
		} 
    	//print the rootItme filter information
 		Iterator it = rootTable.keySet().iterator();
 		while (it.hasNext()){
 			String key =(String)it.next();
 			abr.addDebug("rootTable:key=" + key + ";value=" + rootTable.get(key));
 		}
    }
    // add pdhdomain as search attribute
    
    /**
     * do search setup entity
     * @param rootEntityType
     * @param abr
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws MiddlewareBusinessRuleException
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws RemoteException
     * @throws EANBusinessRuleException
     * @throws IOException
     */
    
    private EntityItem[] doSearch(String rootEntityType, String pdhdomain, ADSABRSTATUS abr) 
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
           MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, 
           MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, 
           EANBusinessRuleException, IOException{
        /////////////////////////////////////////////////////////////////////////////////////////////////////
		// get attribute of EXTXMLFEED Entitys
		// search for the EXTXMLFEED by XMLSETUPTYPE='Production' and XMLENTITYTYPE='Root Entity Type'
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		EntityItem[] EXTXMLFEEDArray = null;
		//DOne need check with Rupal and Wayne
		StringBuffer debugSb = new StringBuffer();
		Database m_db = abr.getDB();
		
		String clsname = abr.getSimpleABRName(rootEntityType);
		XMLMQ mqAbr = (XMLMQ) Class.forName(clsname).newInstance();
		
		Profile m_prof = null;
				
		m_prof = abr.switchRoles(mqAbr.getRoleCode());
		setSwitchProfile(m_prof);
		String searchAction ="SRDEXTXMLFEED";//Done need rupal set up it
		String srchType = "EXTXMLFEED";
		Vector attrVct = new Vector(); 
		Vector valVct  = new Vector();
		
		attrVct.add("XMLENTITYTYPE");
		attrVct.add("XMLSETUPTYPE");
		attrVct.add("PDHDOMAIN");
		valVct.add(rootEntityType);
		valVct.add("Production");
		valVct.add(pdhdomain);
		
		abr.addDebug("XMLENTITYTYPE2="+rootEntityType);
		abr.addDebug("XMLSETUPTYPE2=Production");
		try {
			EXTXMLFEEDArray = ABRUtil.doSearch(m_db, m_prof, searchAction,srchType, false, attrVct, valVct, debugSb);
				//doSearch(m_db, m_prof, searchAction,srchType, false, attrVct, valVct, debugSb,abr);
			abr.addDebug("ABRUtil.doSearch with domain message:"+debugSb.toString());	
		} catch (Exception e) {
			abr.addDebug("ABRUtil.doSearch with domain error:"+debugSb.toString());
			abr.addDebug("doSearch error="+e.getMessage());
			e.printStackTrace();
		}
		abr.addDebug("EXTXMLFEEDArray="+EXTXMLFEEDArray.length);
		return EXTXMLFEEDArray;
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// search end
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    

	/**
     * do search setup entity
     * @param rootEntityType
     * @param abr
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws MiddlewareBusinessRuleException
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws RemoteException
     * @throws EANBusinessRuleException
     * @throws IOException
     */
    private EntityItem[] doSearch(String rootEntityType, ADSABRSTATUS abr) 
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
           MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, 
           MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, 
           EANBusinessRuleException, IOException{
        /////////////////////////////////////////////////////////////////////////////////////////////////////
		// get attribute of EXTXMLFEED Entitys
		// search for the EXTXMLFEED by XMLSETUPTYPE='Production' and XMLENTITYTYPE='Root Entity Type'
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		EntityItem[] EXTXMLFEEDArray = null;
		//DOne need check with Rupal and Wayne
		StringBuffer debugSb = new StringBuffer();
		Database m_db = abr.getDB();
		
		String clsname = abr.getSimpleABRName(rootEntityType);
		XMLMQ mqAbr = (XMLMQ) Class.forName(clsname).newInstance();
		
		Profile m_prof = null;
				
		m_prof = abr.switchRoles(mqAbr.getRoleCode());
		setSwitchProfile(m_prof);
		String searchAction ="SRDEXTXMLFEED";//Done need rupal set up it
		String srchType = "EXTXMLFEED";
		Vector attrVct = new Vector(); 
		Vector valVct  = new Vector();
		
		attrVct.add("XMLENTITYTYPE");
		attrVct.add("XMLSETUPTYPE");
		valVct.add(rootEntityType);
		valVct.add("Production");
		
		abr.addDebug("XMLENTITYTYPE2="+rootEntityType);
		abr.addDebug("XMLSETUPTYPE2=Production");
		try {
			EXTXMLFEEDArray = ABRUtil.doSearch(m_db, m_prof, searchAction,srchType, false, attrVct, valVct, debugSb);
				//doSearch(m_db, m_prof, searchAction,srchType, false, attrVct, valVct, debugSb,abr);
			abr.addDebug("ABRUtil.doSearch no domain message:"+debugSb.toString());	
		} catch (Exception e) {
			abr.addDebug("ABRUtil.doSearch no domain error:"+debugSb.toString());
			abr.addDebug("doSearch error="+e.getMessage());
			e.printStackTrace();
		}
		abr.addDebug("EXTXMLFEEDArray="+EXTXMLFEEDArray.length);
		return EXTXMLFEEDArray;
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// search end
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    
    public void setSwitchProfile(Profile swProfile){
    	_swProfile = swProfile;
    }
    
    /**
     * setFilter 
     * @param rootEntityType
     * @param filterTable (This is the filter table for the setup entity)
     * @param EXTXMLFEEDItem
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
     */
    private void setFilterTable(String rootEntityType,  Hashtable filterTable, EntityItem EXTXMLFEEDItem, ADSABRSTATUS abr) 
    throws MiddlewareRequestException, MiddlewareException{
    	String filters[] = (String[])FILTER_TBL.get(rootEntityType);
    	String attrcode ="";
    	String attrvalue ="";
		String fCOFCAT = "";
		String fCOFSUBCAT = "";
		String fCOFGRP = "";
		String fCOFSUBGRP = "";
		
		for (int i=0; i<filters.length; i++){
    		attrcode = filters[i];
    		abr.addDebug("attrcode="+attrcode);
    		//2013-07-29 RCQ00255719 - filter feed by Division
    		if(attrcode.equals("MODELATR")||attrcode.equals("ENDOFSVC")||attrcode.equals("DIVTEXT")){
    			attrvalue = convertValue(PokUtils.getAttributeValue(EXTXMLFEEDItem, attrcode, "", null, false));
    			filterTable.put(attrcode, attrvalue);
    		}else if(attrcode.equals("STATUS")){
    			//need put the same attributevalue and attributecode into the table for the comparation. 
    			attrvalue = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,XMLSTATUS));
    			if(attrvalue.equals("XSTATUS02")){
    				attrvalue = "0020";
    			}else{
    				attrvalue = "";
    			}
    			filterTable.put(attrcode, attrvalue);
    		}else{    			
    			attrvalue = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,attrcode));
    			filterTable.put(attrcode, attrvalue);
    		}
    	}
		if(rootEntityType.equals("MODEL")||rootEntityType.equals("LSEO")){
					
			fCOFCAT    = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"COFCAT"));
			fCOFSUBCAT = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"COFSUBCAT"));
			fCOFGRP    = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"COFGRP"));
			fCOFSUBGRP = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"COFSUBGRP"));
			//If COFCAT = é–³ãƒ¦è—©erviceé–³ãƒ¯æ‹· (102), then the following are not applicable: COFGRP, and COFSUBGRP
			//check COFCAT from MODEL or LSEO with isService
			if(isService){
				fCOFGRP = "";
				fCOFSUBGRP = "";
			}
//			if(fCOFCAT.equals("102")){
//				fCOFGRP = "";
//				fCOFSUBGRP = "";
//			}
			else{
				//If COFGRP is applicable, then it defaults to é–³ãƒ¦çª‚aseé–³ãƒ¯æ‹· (150) if not specified
				if("".equals(fCOFGRP)){
					fCOFGRP = "150";
				}
			}
			filterTable.put("COFCAT", fCOFCAT);
			filterTable.put("COFSUBCAT", fCOFSUBCAT);
			filterTable.put("COFGRP", fCOFGRP);
			filterTable.put("COFSUBGRP", fCOFSUBGRP);
		}
		if(isDebug){
     		Iterator it = filterTable.keySet().iterator();
     		while (it.hasNext()){
     			String key =(String)it.next();
     			abr.addDebug("EXTXMLFEED SetupEntity filterTable:key=" + key + ";value=" + filterTable.get(key));
     		}
    	}
    }
    
    /**
     * Get the MQ from the XMLABRPROPFILE of the EXTXMLFEED Entity by filtering the queue entity
     * @param rootItem
     * @return
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
     * @throws SBRException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     * @throws EANBusinessRuleException 
     * @throws RemoteException 
     */
    private Vector getMQPropertiesFilter(EntityItem rootItem, ADSABRSTATUS abr) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, 
    InstantiationException, IllegalAccessException, ClassNotFoundException, RemoteException, EANBusinessRuleException, 
    IOException {
		Vector MQ = new Vector();
    	//get filters
    	String rootEntityType = rootItem.getEntityType();
		String filters[] = (String[])FILTER_TBL.get(rootEntityType);
		Hashtable rootTable = new Hashtable();
		String XMLABRPROPFILE = "";
		Hashtable filterTable = new Hashtable();
		// add any filters
//		DO search and get the EntityItems
		
		Database m_db = abr.getDB();
		if (filters!=null){
			EntityItem[] EXTXMLFEEDArray = doSearch(rootEntityType, abr);
			// get each attribute of rootItem
			setRootEntity(rootItem, rootTable, abr);			
			for(int i=0;i<EXTXMLFEEDArray.length;i++){
				EntityItem  EXTXMLFEEDItem = EXTXMLFEEDArray[i];
				EXTXMLFEEDItem = getEntityItem(m_db,EXTXMLFEEDItem);
				
				//step 1 get the attribute of EXTXMLFEED Entity[i]
				//F: LSEOBUNDLE COUNTRYLIST FLFILSYSINDC PDHDOMAIN
				setFilterTable(rootEntityType, filterTable, EXTXMLFEEDItem,abr);
				XMLABRPROPFILE = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,attrXMLABRPROPFILE));
				
				
				//step 2 compare between rootItem and EXTXMLFEEDItem
				boolean isvaild = false;
				////FILTER_TBL.put("FEATURE", new String[]{"FCTYPE","COUNTRYLIST","PDHDOMAIN"});
				String attrcode ="";
				boolean isEquals = true;
				boolean isMatch = true;
		    	for (int j=0; j<filters.length; j++){
		    		attrcode = filters[j];
		    		if(attrcode.equals("BUNDLETYPE")||attrcode.equals("COUNTRYLIST")
		    		   || attrcode.equals("FLFILSYSINDC")||attrcode.equals("PDHDOMAIN")||attrcode.equals("DIVTEXT")){
		    			isEquals = false;
		    		}
		    		isvaild = isVailidCompare((String)rootTable.get(attrcode), (String)filterTable.get(attrcode),attrcode, isEquals);
					if(!isvaild){
						isMatch = false;
						break;
					}
		    	}
		    	if(isMatch){
		    		addXMLPropfile(MQ, XMLABRPROPFILE);
		    	}
			}//end for
		}else{
			//when the entity is not in filter list
			//check for the PeriodicABR 
			//new add for the XMLPRODPRICESETUP
			EntityItem[] EXTXMLFEEDArray = null;
			if(rootEntityType.equals("ADSXMLSETUP")){
	    		String ADSTYPE = PokUtils.getAttributeFlagValue(rootItem, "ADSTYPE");
	    		if (ADSTYPE != null){
	    			rootEntityType = (String)ADSTYPES_TBL.get(ADSTYPE);
	    			if(rootEntityType==null) {
	    				rootEntityType =CHEAT;
	    			}
	    		}
	    	}else if(rootEntityType.equals("XMLPRODPRICESETUP")){
	    		rootEntityType =CHEAT;
	    	}
			if(rootEntityType.equals(CHEAT)){
				EXTXMLFEEDArray = null;
			}else{
				EXTXMLFEEDArray = doSearch(rootEntityType, abr);
			}
			//new add for the XMLPRODPRICESETUP end
			
			//for the WWCOMPAT filter
			if(rootEntityType.equals("WWCOMPAT")){
				wwcompMQTable = new Hashtable();
				if(EXTXMLFEEDArray.length>0){
					isFilterWWCOMPAT = true;	
					for(int i=0;i<EXTXMLFEEDArray.length;i++){
						EntityItem  EXTXMLFEEDItem = EXTXMLFEEDArray[i];
						EXTXMLFEEDItem =getEntityItem(m_db,EXTXMLFEEDItem);
						
						XMLABRPROPFILE = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,attrXMLABRPROPFILE));
						String fBRANDCD = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"BRANDCD"));
						abr.addDebug("fBRANDCD="+fBRANDCD);
						abr.addDebug("wwcompat role code="+_swProfile.getRoleCode());
						
						
						if(fBRANDCD.equals("")) fBRANDCD = CHEAT;
						
						//add wwcompat filer MQ of the EXTXMLFEED
						Vector wwcompMQ = (Vector)wwcompMQTable.get(fBRANDCD);
						if(wwcompMQ==null) wwcompMQ = new Vector();
						StringTokenizer str = new StringTokenizer(XMLABRPROPFILE, PokUtils.DELIMITER);
						String MQfile ="";
						while (str.hasMoreTokens()) {
							MQfile = str.nextToken();
							if(!wwcompMQ.contains(MQfile)){
								wwcompMQ.add(MQfile);
							}
						}
						//add ADSXMLSETUP entity itself MQ of the XMLABRPROPFILE attribute and ADSTYPE = 'WWCOMPAT'
						String val = PokUtils.getAttributeFlagValue(rootItem, attrXMLABRPROPFILE);
				    	if (val != null) {
				    		// parse the string into substrings    	        
				    	    StringTokenizer st = new StringTokenizer(val,PokUtils.DELIMITER);    	           
				    	    while(st.hasMoreTokens())
				            {
				    	        MQfile = st.nextToken();
				    	        if(!wwcompMQ.contains(MQfile)){
									wwcompMQ.add(MQfile);
								}
				            }                       	        
				    	} 
				    	//if null, sent to default MQ
				    	if(wwcompMQ.size()==0) {
				    		wwcompMQ.add(ADSABRSTATUS.ADSMQSERIES);				    	
				    	}
						wwcompMQTable.put(fBRANDCD, wwcompMQ);						
					}
					
				}else{
					isFilterWWCOMPAT = false;					
				}				
			}else{	
				if(EXTXMLFEEDArray!=null){
					for(int i=0;i<EXTXMLFEEDArray.length;i++){
						EntityItem  EXTXMLFEEDItem2 = EXTXMLFEEDArray[i];
						EXTXMLFEEDItem2 =getEntityItem(m_db,EXTXMLFEEDItem2);				
						String XMLABRPROPFILE2 = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem2,attrXMLABRPROPFILE));
						addXMLPropfile(MQ, XMLABRPROPFILE2);
					}
				}
			}
		}
		abr.addDebug("MQ size ="+MQ.size());
		return MQ;
		
	}   
    
    private Hashtable getMQPropertiesFilterVN(EntityItem rootItem, ADSABRSTATUS abr, Vector MQKEY) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, 
    InstantiationException, IllegalAccessException, ClassNotFoundException, RemoteException, EANBusinessRuleException, 
    IOException {
    	Hashtable mqTable = new Hashtable();
    	//temp vector for the MQ by the key
		//Vector MQKEY = new Vector();
    	//get filters
    	String rootEntityType = rootItem.getEntityType();
		String filters[] = (String[])FILTER_TBL.get(rootEntityType);
		Hashtable rootTable = new Hashtable();
		String XMLABRPROPFILE = "";
		Hashtable filterTable = new Hashtable();
		//R 0.5 ==> XMLVERSION = 0 & XMLMOD = 5
		//R 1.0 ==> XMLVERSION = 1 & XMLMOD = 0
		String XMLVERSION = "";
		String XMLMOD  = "";
		String VERSIONKEY = "";
		// add any filters
//		DO search and get the EntityItems
		EntityItem[] EXTXMLFEEDArray = null;
		Vector validEXTXMLFEEDArray  = new Vector();
		
		Database m_db = abr.getDB();
		if (filters!=null){
			// add pdhdomain as search attribute 
			String pdhdomain = PokUtils.getAttributeFlagValue(rootItem, "PDHDOMAIN");
			abr.addDebug(rootItem.getKey()+" pdhdomain: "+pdhdomain);
			if(pdhdomain!=null){
			    EXTXMLFEEDArray = doSearch(rootEntityType, pdhdomain, abr);
			}else {
				EXTXMLFEEDArray = doSearch(rootEntityType, abr);
			}
			// get each attribute of rootItem
			setRootEntity(rootItem, rootTable, abr);
			if(EXTXMLFEEDArray!=null){
				for(int i=0;i<EXTXMLFEEDArray.length;i++){
					EntityItem  EXTXMLFEEDItem = EXTXMLFEEDArray[i];
					EXTXMLFEEDItem = getEntityItem(m_db,EXTXMLFEEDItem);
					
					//step 1 get the attribute of EXTXMLFEED Entity[i]
					//F: LSEOBUNDLE COUNTRYLIST FLFILSYSINDC PDHDOMAIN
					setFilterTable(rootEntityType, filterTable, EXTXMLFEEDItem,abr);
					XMLABRPROPFILE = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,attrXMLABRPROPFILE));
					
					
					//step 2 compare between rootItem and EXTXMLFEEDItem
					boolean isvaild = false;
					////FILTER_TBL.put("FEATURE", new String[]{"FCTYPE","COUNTRYLIST","PDHDOMAIN"});
					String attrcode ="";
					boolean isEquals = true;
					boolean isMatch = true;
			    	for (int j=0; j<filters.length; j++){
			    		attrcode = filters[j];
			    		if(attrcode.equals("BUNDLETYPE")||attrcode.equals("COUNTRYLIST")
			    		|| attrcode.equals("FLFILSYSINDC")||attrcode.equals("PDHDOMAIN")||attrcode.equals("DIVTEXT")){
			    			isEquals = false;
			    		}
			    		isvaild = isVailidCompare((String)rootTable.get(attrcode), (String)filterTable.get(attrcode),attrcode, isEquals);
						if(!isvaild){
							isMatch = false;
							break;
						}
			    	}
			    	if(isMatch){
			    		XMLVERSION = convertValue(PokUtils.getAttributeValue(EXTXMLFEEDItem, "XMLVERSION", "", null, false));
			    		XMLMOD     = convertValue(PokUtils.getAttributeValue(EXTXMLFEEDItem, "XMLMOD", "", null, false));
			    		if(!"".equals(XMLVERSION) && !"".equals(XMLMOD) && isVaildREFOFERFEAT){
			    			VERSIONKEY = XMLVERSION + XMLMOD;
			    			if(mqTable.containsKey(VERSIONKEY)){
			    				MQKEY = (Vector)mqTable.get(VERSIONKEY);
			    				addXMLPropfile(MQKEY, XMLABRPROPFILE);	
			    				mqTable.put(VERSIONKEY, MQKEY);
			    			}else{
			    				MQKEY = new Vector();
			    				addXMLPropfile(MQKEY, XMLABRPROPFILE);
			    				mqTable.put(VERSIONKEY, MQKEY);		    				
			    			}
			    			// reconciliation report
			    			validEXTXMLFEEDArray.add(EXTXMLFEEDItem);
			    			
			    		}
			    	    
			    	}
				}//end for
			}
		}else{
			//when the entity is not in filter list
			//check for the PeriodicABR 
			//new add for the XMLPRODPRICESETUP
			if(rootEntityType.equals("ADSXMLSETUP")){
	    		String ADSTYPE = PokUtils.getAttributeFlagValue(rootItem, "ADSTYPE");
	    		if (ADSTYPE != null){
	    			rootEntityType = (String)ADSTYPES_TBL.get(ADSTYPE);
	    			if(rootEntityType==null) {
	    				rootEntityType =CHEAT;
	    			}
	    		}
	    	}else if(rootEntityType.equals("XMLPRODPRICESETUP")){
	    		rootEntityType =CHEAT;
	    	}
			if(rootEntityType.equals(CHEAT)){
				EXTXMLFEEDArray = null;
			}else{
				EXTXMLFEEDArray = doSearch(rootEntityType, abr);
			}
			//new add for the XMLPRODPRICESETUP end
			
			//for the WWCOMPAT filter
			// need check with Wayne for the wwcompat and PeriodicABR
			
			if(rootEntityType.equals("WWCOMPAT")){
				wwcompMQTable = new Hashtable();
				if(EXTXMLFEEDArray!=null && EXTXMLFEEDArray.length>0){
					isFilterWWCOMPAT = true;	
					for(int i=0;i<EXTXMLFEEDArray.length;i++){
						EntityItem  EXTXMLFEEDItem = EXTXMLFEEDArray[i];
						EXTXMLFEEDItem =getEntityItem(m_db,EXTXMLFEEDItem);
						
						XMLABRPROPFILE = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,attrXMLABRPROPFILE));
						String fBRANDCD = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem,"BRANDCD"));
						abr.addDebug("fBRANDCD="+fBRANDCD);
						abr.addDebug("wwcompat role code="+_swProfile.getRoleCode());
						
						
						if(fBRANDCD.equals("")) fBRANDCD = CHEAT;
						
						//add wwcompat filer MQ of the EXTXMLFEED
						Vector wwcompMQ = (Vector)wwcompMQTable.get(fBRANDCD);
						if(wwcompMQ==null) wwcompMQ = new Vector();
						StringTokenizer str = new StringTokenizer(XMLABRPROPFILE, PokUtils.DELIMITER);
						String MQfile ="";
						while (str.hasMoreTokens()) {
							MQfile = str.nextToken();
							if(!wwcompMQ.contains(MQfile)){
								wwcompMQ.add(MQfile);
							}
						}
						//add ADSXMLSETUP entity itself MQ of the XMLABRPROPFILE attribute and ADSTYPE = 'WWCOMPAT'
						String val = PokUtils.getAttributeFlagValue(rootItem, attrXMLABRPROPFILE);
				    	if (val != null) {
				    		// parse the string into substrings    	        
				    	    StringTokenizer st = new StringTokenizer(val,PokUtils.DELIMITER);    	           
				    	    while(st.hasMoreTokens())
				            {
				    	        MQfile = st.nextToken();
				    	        if(!wwcompMQ.contains(MQfile)){
									wwcompMQ.add(MQfile);
								}
				            }                       	        
				    	} 
				    	//if null, sent to default MQ
				    	if(wwcompMQ.size()==0) {
				    		wwcompMQ.add(ADSABRSTATUS.ADSMQSERIES);				    	
				    	}
						wwcompMQTable.put(fBRANDCD, wwcompMQ);						
					}
					
				}else{
					isFilterWWCOMPAT = false;					
				}				
			}else{	
				if(EXTXMLFEEDArray!=null){
					for(int i=0;i<EXTXMLFEEDArray.length;i++){
						EntityItem  EXTXMLFEEDItem2 = EXTXMLFEEDArray[i];
						EXTXMLFEEDItem2 =getEntityItem(m_db,EXTXMLFEEDItem2);				
						String XMLABRPROPFILE2 = convertValue(PokUtils.getAttributeFlagValue(EXTXMLFEEDItem2,attrXMLABRPROPFILE));
						XMLVERSION = convertValue(PokUtils.getAttributeValue(EXTXMLFEEDItem2, "XMLVERSION", "", null, false));
						XMLMOD     = convertValue(PokUtils.getAttributeValue(EXTXMLFEEDItem2, "XMLMOD", "", null, false));
			    		if(!"".equals(XMLVERSION) && !"".equals(XMLMOD) && isVaildREFOFERFEAT){
			    			VERSIONKEY = XMLVERSION + XMLMOD;
			    			if(mqTable.containsKey(VERSIONKEY)){
			    				MQKEY = (Vector)mqTable.get(VERSIONKEY);
			    				addXMLPropfile(MQKEY, XMLABRPROPFILE2);	
			    				mqTable.put(VERSIONKEY, MQKEY);
			    			}else{
			    				//MQKEY = new Vector(); //add the XMLABRPROPFILE of ADSXMLSETUP
			    				addXMLPropfile(MQKEY, XMLABRPROPFILE2);
			    				mqTable.put(VERSIONKEY, MQKEY);		    				
			    			}
			    		}
					}
				}
				
			}
		}
		// reconciliation report put EXTXMLFEED infor to HashTable
		mqTable.put(KEY_SETUPArry, validEXTXMLFEEDArray);
		
		//only for the debug test
		abr.addDebug("MQ Table ="+mqTable.size());
		if(mqTable.containsKey("10")) {
			abr.addDebug("MQ Table 10 ="+mqTable.get("10").toString());
		} else{
			abr.addDebug("MQ Table 10 is null ");
		}
		if(mqTable.containsKey("05")) {
			abr.addDebug("MQ Table 05 ="+mqTable.get("05").toString());
		} else{
			abr.addDebug("MQ Table 05 is null ");
		}		
		return mqTable;
		
	}  
    
	/**
	 * @param MQ
	 * @param XMLABRPROPFILE
	 */
	private void addXMLPropfile(Vector MQ, String XMLABRPROPFILE) {		
		StringTokenizer str = new StringTokenizer(XMLABRPROPFILE, PokUtils.DELIMITER);
		String MQfile ="";
		while (str.hasMoreTokens()) {
			MQfile = str.nextToken();
			if(!MQ.contains(MQfile)){
				MQ.add(MQfile);
			}
		}
	}
	/**
	 * @param FCTYPE
	 * @param fFCTYPE
	 */
	private boolean isVailidCompare(String rootItemAttributeValue, String EXTXMLFEEDItemAttributeValue, String attrcode,boolean isEquals) {
		boolean isvaild =false;
		if(rootItemAttributeValue==null) rootItemAttributeValue="";
		//for  If MODEL, MODELCONVERT, SVCMOD, SWPRODSTRUCT 
		//do not have an AVAIL of this type, then assume "World Wide" and hence this data is NOT filtered out
		if(rootItemAttributeValue.equals(CHEAT)) return true;
		if(EXTXMLFEEDItemAttributeValue!=null && !"".equals(EXTXMLFEEDItemAttributeValue)){
			if("ENDOFSVC".equals(attrcode)){
				//its ENDOFSVC is greater than the value specified in the EXTXMLFEED setup entity
				if("".equals(rootItemAttributeValue)) {
					return true;
				}else if(rootItemAttributeValue.compareTo(EXTXMLFEEDItemAttributeValue)>=0){
					return true;					
				}
			}else{
				if(isEquals){
					if(EXTXMLFEEDItemAttributeValue.equals(rootItemAttributeValue)){
						isvaild = true;
					}else{
						isvaild = false;
					}
				}else{
					//check the multi values of the EXTXMLFEED entity
					String separator = "";
					if("DIVTEXT".equals(attrcode)){
						separator = ",";
					}else{
						separator = PokUtils.DELIMITER;
					}
					StringTokenizer str = new StringTokenizer(EXTXMLFEEDItemAttributeValue, separator);
					String EXTXMLFEEDValue = "";
					String rootItemValue = "";										
					while (str.hasMoreTokens()) {						
						EXTXMLFEEDValue = str.nextToken();
						if("".equals(EXTXMLFEEDValue)) return true;
						StringTokenizer rootstr = new StringTokenizer(rootItemAttributeValue, PokUtils.DELIMITER);
						while (rootstr.hasMoreTokens()) {
							rootItemValue = rootstr.nextToken();
							if(rootItemValue.equals(EXTXMLFEEDValue)){
								return true;
							}
						}
					}
				}
			}
			
		}else{
			isvaild = true;
		}
		
		return isvaild;
	}

	/**
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private String getCOUNTRYLIST(EntityItem rootItem, String rootEntityType, String attrcode, ADSABRSTATUS abr) 
	throws MiddlewareRequestException, SQLException, MiddlewareException {
		String COUNTRYLIST ="";
		if(rootEntityType.equals("FEATURE")||rootEntityType.equals("IMG")
		   ||rootEntityType.equals("LSEOBUNDLE")||rootEntityType.equals("LSEO")){
			COUNTRYLIST = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));//F multi
		} else if(rootEntityType.equals("REFOFER")){
			COUNTRYLIST = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));//F multi
			//when no country, considering the data to be World Wide
			if(COUNTRYLIST.equals("")) {
				COUNTRYLIST = CHEAT;
			}
		}
		//new add REFOFER and REFOFERFEAT
		else if(rootEntityType.equals("REFOFERFEAT")){
			EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);			
			EntityGroup mdlGrp = m_elist.getEntityGroup("REFOFER");
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			//1(REFOFERFEAT)--->0...N(REFOFER)
			if(itemArray!=null){
				for(int i=0;i<itemArray.length;i++){
					EntityItem relator = (EntityItem)itemArray[i];
					if(relator != null && "REFOFER".equals(relator.getEntityType())){
						String countrytemp = convertValue(PokUtils.getAttributeFlagValue(relator, attrcode));
						//when no country, considering the data to be World Wide
						if(countrytemp.equals("")){							
							COUNTRYLIST =CHEAT;
							break;							
						}else{
							if("".equals(COUNTRYLIST)){
								COUNTRYLIST = countrytemp;
							}else{
								COUNTRYLIST = COUNTRYLIST + PokUtils.DELIMITER + countrytemp;
							}
						}
					}
				}
			}						
		}//new add REFOFER and REFOFERFEAT end
		else if(rootEntityType.equals("PRODSTRUCT")){
			//PRODSTRUCT: AVAIL then FEATURE
			//Orange - PRODSTRUCT only has AVAILs for GA products. If it is a RPQ, then the COUNTRYLIST is obtained from the FEATURE.
			//FEATURE	PRODSTRUCT-u		
			// WHEN		FCTYPE	=	Primary FC (100) | "Secondary FC" (110) 	 Then  GA  Logic
			// WHEN		FCTYPE	<>	Primary FC (100) | "Secondary FC" (110)      Then  RPQ Logic
			//XMLElem feature = new XMLGroupElem(null, "FEATURE", "U:FEATURE");
			//Step1 get the feature
			EntityItem featureItem = null;
            EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
			
			EntityGroup mdlGrp = m_elist.getEntityGroup("FEATURE");
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			if(itemArray!=null){
				for(int i=0;i<itemArray.length;i++){
					EntityItem relator = (EntityItem)itemArray[i];
					if(relator != null && "FEATURE".equals(relator.getEntityType())){
						featureItem= relator;
						break;
					}
				}
			}
			//step2 check GA or RPQ 
			// if GA get the COUNTRYLIST of AVAILs
			// if RPQ get the COUNTRYLIST of FEATURE
			String pFCTYPE ="";
			if(featureItem!=null){
				pFCTYPE = convertValue(PokUtils.getAttributeFlagValue(featureItem, "FCTYPE"));
				if(pFCTYPE.equals("100") || pFCTYPE.equals("110")){
					COUNTRYLIST = getCountryList(rootItem, rootEntityType, attrcode, abr);
				}else{
					COUNTRYLIST = convertValue(PokUtils.getAttributeFlagValue(featureItem, attrcode));
				}
			}
			
		} else {
			//add the VEs
			COUNTRYLIST = getCountryList(rootItem, rootEntityType, attrcode, abr);
		}
		return COUNTRYLIST;
	}
	/**
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @param abr
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	private String getCountryList(EntityItem rootItem, String rootEntityType, String attrcode, ADSABRSTATUS abr) throws SQLException, MiddlewareException, MiddlewareRequestException {
		//AVAIL: MODEL,      MODELCONVERT     SVCMOD      SWPRODSTRUCT      PRODSTRUCT
		//VE:    ADFMODEL    ADFMODELCONVERT  ADFSVCMOD   ADFSWPRODSTRUCT   ADFPRODSTRUCT
		//AVAILTYPE = "Planned Availability" (146)
		//COUNTRYLIST é–³ãƒ¯æ‹· The é–³ãƒ¦ç©¾vailabilityé–³ãƒ¯æ‹· (AVAIL) of type é–³ãƒ¦é˜€lanned Availabilityé–³ãƒ¯æ‹· (146) is used for this filter. 
		//If MODEL, MODELCONVERT, SVCMOD, SWPRODSTRUCT do not have an AVAIL of this type, 
		//then assume "World Wide" and hence this data is NOT filtered out (i.e. it is sent).
		//Add AVAIL's status must be ready for review and final
		String COUNTRYLIST="";
		EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
		
		EntityGroup mdlGrp = m_elist.getEntityGroup("AVAIL");
		EntityItem itemArray[] = null;
		if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
		if(itemArray==null || itemArray.length==0){
			abr.addDebug("avail is null");
			COUNTRYLIST = CHEAT;
		}else{
			//check avail type? yes (146)
			abr.addDebug("avail is not null");
			EntityItem availItem = null;
			StringBuffer buffer = new StringBuffer();
			String availType ="";
			String availStatus ="";
			String anndate = "";
			boolean isOldAvail = false;
			boolean isAvailCountry = false;
			int j =0;
			for(int i=0;i<itemArray.length;i++){
				availItem = itemArray[i];
				//add the check that AVAILTYPE = "Planned Availability" (146)
				availType= convertValue(PokUtils.getAttributeFlagValue(availItem, "AVAILTYPE"));
				//add status check
				availStatus = convertValue(PokUtils.getAttributeFlagValue(availItem, "STATUS"));
				
				if(availType.equals("146")){
					// add the check of the old data of 
					anndate = PokUtils.getAttributeValue(availItem, "EFFECTIVEDATE", ",", CHEAT, false);
					if(anndate.compareTo(ADSABRSTATUS.OLDEFFECTDATE) <= 0){
						isOldAvail = true;
					}else{
						isOldAvail = false;
					}
					if(isOldAvail){
						isAvailCountry = true;						
					}else if("0020".equals(availStatus)|| "0040".equals(availStatus)){
						isAvailCountry = true;						
					}else{
						isAvailCountry = false;
					}
					if(isAvailCountry){
						if(j!=0) buffer.append(PokUtils.DELIMITER);
						buffer.append(PokUtils.getAttributeFlagValue(availItem, attrcode));
						j++;
					}					
				}
			}
			COUNTRYLIST = buffer.toString();
			if(COUNTRYLIST.equals("")) {
				COUNTRYLIST = CHEAT;
			}
		}
		return COUNTRYLIST;
	}
	
	/**
	 * get DIVISION of LSEO | LSEOBUNDLE | MODEL | SVCMOD
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @return
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */	
    private String getDIVISION(EntityItem rootItem, String rootEntityType, String attrcode,ADSABRSTATUS abr) throws MiddlewareRequestException, SQLException, MiddlewareException {
    	
    	String attrvalue="";
		//LSEO | LSEOBUNDLE | MODEL | SVCMOD
		EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
		
		abr.addDebug("getDIVISION: m_elist=" +m_elist);
		
		EntityGroup mdlGrp = m_elist.getEntityGroup("SGMNTACRNYM");
		
		EntityItem itemArray[] = null;
		if(mdlGrp!=null) {
			itemArray = mdlGrp.getEntityItemsAsArray();
			abr.addDebug("getDIVISIONé–¿æ¶³æ‹· itemArray=" +itemArray.length);
		}
		int k=0;
		for(int i=0;i<itemArray.length;i++){
			EntityItem entityItem = (EntityItem)itemArray[i];
			if(entityItem != null){
				k++;
				if(k==1){
					attrvalue = convertValue(PokUtils.getAttributeFlagValue(entityItem, attrcode));
				}else{
					attrvalue = attrvalue + PokUtils.DELIMITER + convertValue(PokUtils.getAttributeFlagValue(entityItem, attrcode));
				}
			}
		}
		abr.addDebug("getDIVISIONé–¿æ¶™ç“±nd");
		return attrvalue;
	}
	
	/**
	 * get MACHTYPEATR of PRODSTRUCT and SWPRODSTRUCT
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @return
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */	
    private String getMACHTYPEATR(EntityItem rootItem, String rootEntityType, String attrcode,ADSABRSTATUS abr) throws MiddlewareRequestException, SQLException, MiddlewareException {
    	
    	String attrvalue="";
		if(rootEntityType.equals("PRODSTRUCT")||rootEntityType.equals("SWPRODSTRUCT")){
			//PRODSTRUCT-d	MODEL	MACHTYPEATR
			EntityItem modelItem = null;
			EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
			
			EntityGroup mdlGrp = m_elist.getEntityGroup("MODEL");
			abr.addDebug("mdlGrp="+mdlGrp);
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			for(int i=0;i<itemArray.length;i++){
				EntityItem relator = (EntityItem)itemArray[i];
				if(relator != null && "MODEL".equals(relator.getEntityType())){
					modelItem= relator;
					break;
				}
			}
		    if(modelItem!=null){
		    	if(attrcode.equals("MODELATR")){
		    		attrvalue = convertValue(PokUtils.getAttributeValue(modelItem, attrcode, "", null, false));
		    	}else{
		    		attrvalue = convertValue(PokUtils.getAttributeFlagValue(modelItem, attrcode));
		    	}
		    	
		    	abr.addDebug("get model attrcode="+attrcode +"modelItem ="+modelItem.getEntityID()+";attrvalue="+attrvalue);
		    }
		}else{
			attrvalue = "";
		}
		return attrvalue;
	}
    /**
     * get ENDOFSVC
     * @param rootItem
     * @param rootEntityType
     * @param attrcode
     * @param abr
     * @return
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
     private String getENDOFSVC(EntityItem rootItem, String rootEntityType, String attrcode,ADSABRSTATUS abr) throws MiddlewareRequestException, SQLException, MiddlewareException {
    	
    	String attrvalue=CHEAT;
		if(rootEntityType.equals("REFOFERFEAT")){
			//REFOFERFEAT-U	REFOFER	ENDOFSVC
			EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
			EntityGroup mdlGrp = m_elist.getEntityGroup("REFOFER");
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			//1(REFOFERFEAT)--->0...N(REFOFER)
			/**
			 * If after processing all of the referenced REFOFER 
			 * there are no instances of <RELATEDREFOFERELEMENT>,
			 * then do not send the XML
			 * use isVaildREFOFERFEAT to check it
			 * 
			 * If there are muitiple records of thereferenced REFOFER
			 * should be set to the maximum value from all referenced parent REFOFER ENDOFSVC.
			 * 
			 */
			if(itemArray!=null){
				isVaildREFOFERFEAT = true;
				String ENDOFSVC ="";
				for(int i=0;i<itemArray.length;i++){
					EntityItem relator = (EntityItem)itemArray[i];
					if(relator != null && "REFOFER".equals(relator.getEntityType())){
						ENDOFSVC = convertValue(PokUtils.getAttributeValue(relator, "ENDOFSVC", "", null, false));
						if(attrvalue.equals(CHEAT)){
							attrvalue = ENDOFSVC;
						} 
						if("".equals(attrvalue)||"".equals(ENDOFSVC)){
							attrvalue = CHEAT;
							break;
						} else if(attrvalue.compareTo(ENDOFSVC)>0){							
							//attrvalue = attrvalue;// do nothing
						} else if(attrvalue.compareTo(ENDOFSVC)<=0){
							attrvalue = ENDOFSVC;
						}
					}
				}
			}else{
				attrvalue = "";
				isVaildREFOFERFEAT = false;
			}	
		}else{
			attrvalue = "";
		}
		return attrvalue;
	}

	/**
	 * @param rootItem
	 * @param rootEntityType
	 * @param abr
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	private EntityList getEntityList(EntityItem rootItem, String rootEntityType, ADSABRSTATUS abr) throws SQLException, MiddlewareException, MiddlewareRequestException {
		if (mf_elist != null){
			return mf_elist;
		}else{
			String veName ="ADF"+rootEntityType; //Need to add the VEs
			Database m_db = abr.getDB();
			Profile m_prof = abr.getProfile();
			mf_elist = m_db.getEntityList(m_prof,
			        new ExtractActionItem(null, m_db, m_prof, veName),
			        new EntityItem[] { new EntityItem(null, m_prof, rootItem.getEntityType(), rootItem.getEntityID()) });
		}
		return mf_elist;		
	}
	/**
	 * get FCTYPE of PRODSTRUCT and SWPRODSTRUCT
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @param abr
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getFCTYPE(EntityItem rootItem, String rootEntityType, String attrcode, ADSABRSTATUS abr) 
	throws MiddlewareRequestException, SQLException, MiddlewareException {
		String FCTYPE="";
		EntityItem featureItem = null;
		EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
		
		String sGroup = rootEntityType.equals("PRODSTRUCT")?"FEATURE":"SWFEATURE";
		
		EntityGroup featureGrp = m_elist.getEntityGroup(sGroup);
		EntityItem itemArray[] = null;
		if(featureGrp!=null) itemArray = featureGrp.getEntityItemsAsArray();
		for(int i=0;i<itemArray.length;i++){
			EntityItem relator = (EntityItem)itemArray[i];
			if(relator != null && sGroup.equals(relator.getEntityType())){
				featureItem= relator;
				break;
			}
		}
		if(featureItem!=null){
			FCTYPE = convertValue(PokUtils.getAttributeFlagValue(featureItem, "FCTYPE"));			
		}		
		return FCTYPE;
	}
    
    /**
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
	 */
	private String getCOFMODEL(EntityItem rootItem, String rootEntityType, String attrcode, ADSABRSTATUS abr) 
	throws MiddlewareRequestException, SQLException, MiddlewareException {
		String COF="";
		EntityItem modelentity = null ;
		if(rootEntityType.equals("LSEO")){
			EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
			
			EntityGroup mdlGrp = m_elist.getEntityGroup("MODEL");
			abr.addDebug("mdlGrp="+mdlGrp);
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			for(int i=0;i<itemArray.length;i++){
				EntityItem relator = (EntityItem)itemArray[i];
				if(relator != null && "MODEL".equals(relator.getEntityType())){
					modelentity= relator;
					break;
				}
			}			
		    if(modelentity!=null){
		    	COF = convertValue(PokUtils.getAttributeFlagValue(modelentity, attrcode));
		    }
		}else{
			//WWSEO.SPECBID
			COF = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));
		}
		return COF;
	}
	
	/**
	 * @param rootItem
	 * @param rootEntityType
	 * @param attrcode
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private String getSPECBID(EntityItem rootItem, String rootEntityType, String attrcode, ADSABRSTATUS abr) 
	throws MiddlewareRequestException, SQLException, MiddlewareException {
		String SPECBID="";
		if(rootEntityType.equals("LSEO")){
			//LSEO: WWSEOLSEO-u
			EntityItem wwseoItem = null;
            EntityList m_elist = getEntityList(rootItem, rootEntityType, abr);
			
			EntityGroup mdlGrp = m_elist.getEntityGroup("WWSEO");
			abr.addDebug("mdlGrp="+mdlGrp);
			EntityItem itemArray[] = null;
			if(mdlGrp!=null) itemArray = mdlGrp.getEntityItemsAsArray();
			for(int i=0;i<itemArray.length;i++){
				EntityItem relator = (EntityItem)itemArray[i];
				if(relator != null && "WWSEO".equals(relator.getEntityType())){
					wwseoItem= relator;
					break;
				}
			}		
		    if(wwseoItem!=null){
		    	SPECBID = convertValue(PokUtils.getAttributeFlagValue(wwseoItem, attrcode));
		    }
		}else{
			//WWSEO.SPECBID
			SPECBID = convertValue(PokUtils.getAttributeFlagValue(rootItem, attrcode));
		}
		return SPECBID;
	}
    //  RQK add new method checkMQPropertiesFN to check if mq propfile name attribute exists
    // on root entity. this will indicate an IDL and pass back true, else pass false
    public boolean checkIDLMQPropertiesFN(EntityItem rootEntity) {
    	String val = PokUtils.getAttributeFlagValue(rootEntity, attrXMLABRPROPFILE);
        return !(val == null);
    }
    
    /**
	 * get the XMLABRPROPFILE attribute of the setup entity(XMLCOMPATSETUP, XMLXLATESETUP, GA and price)
	 * @param rootEntity
	 * @return
	 */
	public Vector getPeriodicMQ(EntityItem rootEntity){
		Vector wwcompMQ = new Vector();
		String XMLABRPROPFILE = convertValue(PokUtils.getAttributeFlagValue(rootEntity,attrXMLABRPROPFILE));
		StringTokenizer str = new StringTokenizer(XMLABRPROPFILE, PokUtils.DELIMITER);
		String MQfile ="";
		while (str.hasMoreTokens()) {
			MQfile = str.nextToken();
			wwcompMQ.add(MQfile);
		}
		if(wwcompMQ.size()==0) wwcompMQ = null;
		return wwcompMQ;
	}
    
    // RQK add new method checkMQPropertiesFN to check if mq propfile name attribute exists
    // on root entity. this will indicate an IDL and pass back true, else pass false

    /**********************************
    * check if xml should be created for this
    */
    public boolean createXML(EntityItem rootItem) { return true;}

    /**********************************
    * get xml object mapping
    */
    public XMLElem getXMLMap() {return null;}

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() {return "dummy";}
    
    /**********************************
    * get the name of the VE to use
    */
    public String getVeName2() {return "dummy";}


    /**********************************
    * get the role code to use for this ABR
    */
    public String getRoleCode() { return "BHFEED"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr(){ return "";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public abstract String getMQCID();

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion(){ return "";}

    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {}

    /********************************************************************************
    * setup the connection and preparedstatements
    */
    protected Connection setupConnection()
        throws java.sql.SQLException
    {
        Connection connection =
            DriverManager.getConnection(
                MiddlewareServerProperties.getPDHDatabaseURL(),
                MiddlewareServerProperties.getPDHDatabaseUser(),
                MiddlewareServerProperties.getPDHDatabasePassword());

        connection.setAutoCommit(false);

        return connection;
    }
    /********************************************************************************
    * close the connection and preparedstatements
    */
    protected void closeConnection(Connection connection) throws java.sql.SQLException
    {
        if(connection != null) {
            try {
                connection.rollback();
            }
            catch (Throwable ex) {
                System.err.println("XMLMQAdapter.closeConnection(), unable to rollback. "+ ex);
            }
            finally {
                connection.close();
                connection = null;
            }
        }
    }
    protected void mergeLists(ADSABRSTATUS abr, EntityList list1, EntityList list2) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {}
    
    /**
     * There is special filtering of MODEL based on classification attributes. 
	  Only MODELs that match the filtering are to flow. The functional specification 
	  é–³ãƒ¦çª‚H FS ABR XML IDL 2011mmdd.docé–³ãƒ¯æ‹· describes the é–³ãƒ¦å��ML Setup Entityé–³ãƒ¯æ‹· (EXTXMLFEED) 
	  which may be used to further filter data for downstream systems 
	  (i.e. a subset of the data that matches the following criteria).
	    COFCAT   COFSUBCAT COFGRP  
	    -------- --------- --------
	    101      125       150     
	    100      *         150     
	    102      *         *       
	    101      127       150     
	    101      128       150     
	    101      129       150     
	    101      131       150     
	    101      133       150     
	    101      134       150     
	    101      194       150
     * @param rootEntity
     * @param abr
     * @return
     * @throws SQLException
     */
    private boolean checkModelVaild(EntityItem rootEntity, ADSABRSTATUS abr) throws SQLException {
    	boolean bReturn = false;
    	String entitytype = rootEntity.getEntityType();
    	if(!"MODEL".equals(entitytype)){ 
    		return true; 
    	}    	
		String querysql = 
			" select count(*) as count from opicm.filter_model where \r\n"+
			" (cofcat=? or cofcat='*') and                         \r\n"+
			" (cofsubcat=? or cofsubcat='*') and                   \r\n"+
			" (cofgrp=? or cofgrp='*') with ur                     \r\n";
		ResultSet result=null;
		Connection connection=null;
		PreparedStatement pstatement = null;
		int iCount =0;
		try {
            connection = setupConnection();
            pstatement = connection.prepareStatement(querysql);
            pstatement.setString(1, convertValue(PokUtils.getAttributeFlagValue(rootEntity, "COFCAT")));//"COFCAT"
            pstatement.setString(2, convertValue(PokUtils.getAttributeFlagValue(rootEntity, "COFSUBCAT")));//"COFCAT"
            pstatement.setString(3, convertValue(PokUtils.getAttributeFlagValue(rootEntity, "COFGRP")));//"COFCAT"
            result = pstatement.executeQuery();
            if(result.next()) {
            	iCount = result.getInt("count");
            	if(iCount>0) {
            		bReturn = true;
            	} else {
            		bReturn = false;            	
            	}
            }            
		}finally{
			try {
				if (pstatement!=null) {
					pstatement.close();
					pstatement=null;
				}
			}catch(Exception e){
				abr.addDebug("getPriced unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            closeConnection(connection);
        }
		return bReturn;
		
		
	}
}
