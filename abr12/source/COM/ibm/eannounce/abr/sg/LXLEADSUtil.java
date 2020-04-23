//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.ABRUtil;

import com.ibm.transform.oim.eacm.util.PokUtils;
/**********************************************************************************
 * From "SG FS Inbound Feed Leads 20090423.doc"
 * Utilities for LEADS SG ABRs
 */
//LXLEADSUtil.java,v
//Revision 1.7  2010/03/01 17:15:32  wendy
//Expanded to support Blue Harmony (BH) IDLs.
//
//Revision 1.6  2009/05/20 19:09:47  wendy
//spec updates
//
//Revision 1.5  2009/05/20 01:03:18  wendy
//Add filter for multiple SW Model, replace modelItem from search with edit version
//
//Revision 1.4  2009/05/06 12:38:52  wendy
//RCQ00028947 - LEADS to EACM Feed - Update Mapping for Audience, Rate Card Code, UNSPSC Code
//RCQ00029975 - LEADS inbound feed Workgroup Selection
//
//Revision 1.3  2009/04/07 12:19:36  wendy
//Enhance error msgs
//
//Revision 1.2  2009/04/02 19:42:16  wendy
//Change projcdnam flag code
//
//Revision 1.1  2009/01/20 19:39:08  wendy
//CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
//CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//
public class LXLEADSUtil {
	
	//COFCAT	100	Hardware
	//COFCAT	101	Software
	//COFCAT	102	Service
	protected static final String COFCAT_HW="100";
	protected static final String COFCAT_SW="101";
	protected static final String COFCAT_SVC="102";
	//COFSUBCAT	125		HIPO
	//COFSUBCAT	127		Application
	//COFSUBCAT	133		Subscription
	protected static final String COFSUBCAT_HIPO="125";
	protected static final String COFSUBCAT_Application="127";
	protected static final String COFSUBCAT_Subscription="133";
	protected static final HashSet SW_COFSUBCAT_SET; 
	
	//COFGRP	150		Base
	protected static final String COFGRP_BASE="150";

	private static final String MODEL_SRCHACTION_NAME = "LDSRDMODEL";//"SRDMODEL4";
	private static final String WWSEO_SRCHACTION_NAME = "LDSRDWWSEO";//"SRDWWSEO5";
	private static final String LSEO_SRCHACTION_NAME = "LDSRDLSEO";//"SRDLSEO3";
	private static final String WWSEO_CREATEACTION_NAME = "LDCRWWSEO";//"CRWWSEO";
	private static final String LSEO_CREATEACTION_NAME = "LDCRLSEO";//"CRPEERLSEO"; 
	private static final String WWSEOPS_LINKACTION_NAME = "LDLINKPRODSTWWSEO";//"LINKPRODSTRUCTWWSEO"; // for WWSEOPRODSTRUCT
	private static final String WWSEOSWPS_LINKACTION_NAME = "LDLINKSWPRODWWSEO";//"LINKSWPRODSTRUCTWWSEO"; // for WWSEOSWPRODSTRUCT	
	private static final String GENERALAREA_SRCHACTION_NAME = "LDSRDGENAREA";//"SRDGENERALAREA"; 

	protected static final String PS_SRCHACTION_NAME = "LDSRDPRODSTRUCT";//"SRDPRODSTRUCT03";// wont accept model attr->"SRDPRODSTRUCT01";
	protected static final String SWPS_SRCHACTION_NAME = "LDSRDSWPRODSTRUCT";//"SRDSWPRODSTRUCT03";// wont accept model attr->"SRDSWPRODSTRUCT2";

	private static Hashtable SG_GENAREACODE_TBL = null;
	private static final Hashtable AUDIEN_TBL;
	/*
AUDIEN	100		SDI Channel
AUDIEN	10046	Catalog - Business Partner	Catalog - Business Partner
AUDIEN	10048	Catalog - Indirect/Reseller	Catalog - Indirect/Reseller
AUDIEN	10054	Public	Public
AUDIEN	10055	None	None
AUDIEN	10062	LE Direct	LE Direct
AUDIEN	10067	DAC/MAX	DAC/MAX

ACCOUNTTYPE					CHW								HVEC
							AUDIEN							CATALOGNAME_CVAR
Null (i.e. not supplied)	LE Direct (10062)				Default from meta data
Enterprise Direct			LE Direct (10062)				LE Direct (10062)
Odyssey						LE Direct (10062)				LE Direct (10062) +DAC/MAX (10067)
Indirect					LE Direct (10062) +				LE Direct (10062) +
			Catalog - Indirect/Reseller (10048) +			Catalog - Indirect/Reseller (10048) +
			Catalog - Business Partner (10046)				Catalog - Business Partner (10046)		
	 */
	static{
		AUDIEN_TBL = new Hashtable();
		AUDIEN_TBL.put("Enterprise Direct", "10062");
		AUDIEN_TBL.put("Odyssey", "10062");
		Vector vct = new Vector(3);
		vct.add("10062");
		vct.add("10046");
		vct.add("10048");
		AUDIEN_TBL.put("Indirect", vct);
		SW_COFSUBCAT_SET = new HashSet();
		SW_COFSUBCAT_SET.add(COFSUBCAT_HIPO);
		SW_COFSUBCAT_SET.add(COFSUBCAT_Application);
		SW_COFSUBCAT_SET.add(COFSUBCAT_Subscription);
	}
	
	/****************
	 * 1.	Search for MODEL using: 
	 * -	MACHTYPEATR = <MT>
	 * -	MODELATR = <MODEL>
	 * 
	 * 1.	Search (restricted to PDHDOMAIN) for the Model using: 
•	MACHTYPEATR = <MT>
•	MODELATR = <MODEL>
2.	If more than one MODEL, then:
•	If COFCAT = Hardware (100), choose the one where COFGRP = Base (150)
•	If COFCAT = Software(101), chose the one where COFGRP = Base (150) and COFSUBCAT = {Application (127) or HIPO (125) or Subscription (133)}.
3.	Use the PDHDOMAIN of this MODEL for all data created. 

	 * @param lxAbr
	 * @param mt
	 * @param model
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected static EntityItem searchForModel(LXABRSTATUS lxAbr,String mt, String model) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem modelItem = null;
		if (mt != null && model!= null){
			Vector attrVct = new Vector(2);
			attrVct.addElement("MACHTYPEATR");
			attrVct.addElement("MODELATR");
			Vector valVct = new Vector(2);
			valVct.addElement(mt);
			valVct.addElement(model);

			EntityItem eia[]= null;
			
			try{
				StringBuffer debugSb = new StringBuffer();
				eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
						MODEL_SRCHACTION_NAME, "MODEL", false, attrVct, valVct, debugSb);
				if (debugSb.length()>0){
					lxAbr.addDebug(debugSb.toString());
				}
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				lxAbr.addDebug("searchForModel SBRException: "+exBuf.getBuffer().toString());
			}
			if (eia!=null && eia.length >0){
				if (eia.length==1){
					modelItem = eia[0];
					lxAbr.addDebug("LXLEADSUtil.searchForModel found single "+modelItem.getKey()+
							" for mt:"+mt+" model:"+model);
				}else{
					boolean isOneMdl=true;
					for (int i=0; i<eia.length; i++){
						EntityItem item = eia[i];
						// be filter based on cofcat.. mt:mdl is not unique
						String cofcatflag = PokUtils.getAttributeFlagValue(item, "COFCAT");
						if (COFCAT_HW.equals(cofcatflag)){
							//cofcat = Hardware and cofgrp = Base ----- that ensures only 1
							String cofgrp = PokUtils.getAttributeFlagValue(item, "COFGRP");
							lxAbr.addDebug("LXLEADSUtil.searchForModel found HW "+item.getKey()+
									" cofcatflag:"+cofcatflag+
									" cofgrp:"+cofgrp+" for mt:"+mt+" model:"+model);
							if (COFGRP_BASE.equals(cofgrp)){
								if (modelItem!=null){
									isOneMdl = false;
									break;
								}
								modelItem = item;
							}
						}else if (COFCAT_SW.equals(cofcatflag)){
							//if more than one SW model for mt+mdl must find one with:
							/*Cofsubcat		Cofgrp	
						Application		Base OR
						HIPO			Base OR
						Subscription	Base
							 */					
							String cofgrp = PokUtils.getAttributeFlagValue(item, "COFGRP");
							String cofsubcatflag = PokUtils.getAttributeFlagValue(item, "COFSUBCAT");
							lxAbr.addDebug("LXLEADSUtil.searchForModel found SW "+item.getKey()+" cofgrp:"+
									cofgrp+" cofsubcatflag: "+cofsubcatflag);
							if (COFGRP_BASE.equals(cofgrp) &&
								SW_COFSUBCAT_SET.contains(cofsubcatflag))
							{
								if (modelItem!=null){
									isOneMdl = false;
									break;
								}
								modelItem = item;
							}
						}else if (COFCAT_SVC.equals(cofcatflag)){
							lxAbr.addDebug("LXLEADSUtil.searchForModel found SVC "+item.getKey()+" cofcatflag:"+cofcatflag+
									" for mt:"+mt+" model:"+model);
							if (modelItem!=null){
								isOneMdl = false;
								break;
							}
							modelItem = item;
						}else{
							lxAbr.addDebug("LXLEADSUtil.searchForModel found UNKNOWN "+item.getKey()+" cofcatflag:"+cofcatflag+
									" for mt:"+mt+" model:"+model);
						}
					}
					if (!isOneMdl){
						StringBuffer sb = new StringBuffer();
						//ERROR_MODEL_MORETHAN_1 = More than one MODEL found for {0}
						sb.append(lxAbr.getResourceMsg("ERROR_MODEL_MORETHAN_1", new Object[]{ mt+":"+model}));
						//sb.append("More than one MODEL found for "+mt+":"+model);
						for (int i=0; i<eia.length; i++){
							sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
						}
						lxAbr.addError(sb.toString());
						modelItem = null;
					}
				} // end more than one found
				if (modelItem!=null){
					// must pull this again, only nav attributes come back with search
					EntityGroup eg =  new EntityGroup(null,lxAbr.getDatabase(),lxAbr.getProfile(),"MODEL", "Edit");
					modelItem = new EntityItem(eg, lxAbr.getProfile(), lxAbr.getDatabase(), "MODEL", modelItem.getEntityID());
					eg.putEntityItem(modelItem);
				}else{
					modelItem = eia[0]; // give it something to prevent not found msg
				}
			} // end something found
			attrVct.clear();
			valVct.clear();
		}
		
		return modelItem;
	}	
	/*********************************
	 * 2.	Search for WWSEO using:
	 * -	<SEOID>
	 * 
	 * @param lxAbr
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected static EntityItem searchForWWSEO(LXABRSTATUS lxAbr, String seoid) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem wwseo = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia = ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					WWSEO_SRCHACTION_NAME, "WWSEO", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForWWSEO SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length >0){
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("LXLEADSUtil.searchForWWSEO found "+eia[i].getKey());
			}
			if (eia.length>1){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one WWSEO found for "+seoid);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				lxAbr.addError(sb.toString());
			}
			wwseo = eia[0];
		}

		attrVct.clear();
		valVct.clear();
		return wwseo;
	}	

	/*****************************************
	 * 3.	Search for LSEO using:
	 * -	<SEOID>
	 * 
	 * @param lxAbr
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected static EntityItem searchForLSEO(LXABRSTATUS lxAbr, String seoid) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem lseo = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					LSEO_SRCHACTION_NAME, "LSEO", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForLSEO SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("LXLEADSUtil.searchForLSEO found "+eia[i].getKey());
			}
			if (eia.length>1){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one LSEO found for "+seoid);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				lxAbr.addError(sb.toString());
			}
			lseo = eia[0];
		}
		attrVct.clear();
		valVct.clear();
		return lseo;
	}

	/*****************************************************
	 * 4.	Create WWSEO
	 * The parent MODEL is the MODEL found in step 1.
	 * The attributes of the WWSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with
	 * details provided on the SG Notes tab.
	 * COMNAME	T	Common Name	<SEOID>
	 * PROJCDNAM	U	Project Code Name	"LEADS FEED SPECIAL BID" =>'722'
	 * . If the LEADSXML has a value for PROJCDNAM, then use it; otherwise, use the default specified in the SS.
	 * PDHDOMAIN	F	Domains	Derived from MODEL
	 * SEOID	T	SEO ID	<SEOID>
	 * SEOORDERCODE	U	SEO Ordercode	"Initial"
	 * SEOTECHDESC	L	SEO Technical Description	<SEOTECHDESC>
	 * SPECBID	U	Special Bid	"Yes"
	 * UNSPSCCD	U	UN SPSC Code	RCQ00028947
	 * UNSPSCCDSECONDRYUOM	U	UN Unit of Measure	Default
	 * RATECARDCD	U	Rate Card Code	RCQ00028947 use value from Model
	 * 
	 * @param lxAbr
	 * @param modelItem
	 * @param seoid
	 * @param seoTechDesc
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected static EntityItem createWWSEO(LXABRSTATUS lxAbr, EntityItem modelItem, String seoid, 
			String seoTechDesc, Object domain) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		EntityItem wwseo = null;
		
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("SEOID");
		attrValTbl.put("SEOID", seoid);
		attrCodeVct.addElement("COMNAME");
		attrValTbl.put("COMNAME", seoid); //COMNAME	T	Common Name	<SEOID>
		attrCodeVct.addElement("SEOTECHDESC");
		attrValTbl.put("SEOTECHDESC", seoTechDesc); //SEOTECHDESC	L	SEO Technical Description	<SEOTECHDESC>
		attrCodeVct.addElement("PROJCDNAM");
		// get the PROJCDNAM - expanded to support Blue Harmony (BH) IDLs.
		String prjname = "722";
		if(lxAbr.getPROJCDNAME() !=null){
			prjname = lxAbr.getPROJCDNAME();
		}
		attrValTbl.put("PROJCDNAM", prjname); //PROJCDNAM	U	Project Code Name	"LEADS FEED SPECIAL BID" =>'722'
		attrCodeVct.addElement("SEOORDERCODE");
		attrValTbl.put("SEOORDERCODE", "10");//SEOORDERCODE	U	SEO Ordercode	"Initial" =>SEOORDERCODE	10		Initial
		attrCodeVct.addElement("SPECBID");
		attrValTbl.put("SPECBID","11458");//SPECBID	U	Special Bid	"Yes" =>SPECBID	11458	Y	Yes
		attrCodeVct.addElement("UNSPSCCD");
		String unspsccd = PokUtils.getAttributeFlagValue(modelItem, "UNSPSCCD");//RCQ00028947
		lxAbr.addDebug(modelItem.getKey()+" has UNSPSCCD "+unspsccd);
		if (unspsccd==null){
			lxAbr.addDebug(modelItem.getKey()+" did not have UNSPSCCD attr meta: "+
					modelItem.getEntityGroup().getMetaAttribute("UNSPSCCD"));
			unspsccd = "43171806";
		}
		attrValTbl.put("UNSPSCCD",unspsccd);//UNSPSCCD	U	UN SPSC Code use Model value	"43171806 - Servers"=>43171806	43171806 - Servers
		attrCodeVct.addElement("RATECARDCD");
		String ratecardcd = PokUtils.getAttributeFlagValue(modelItem, "RATECARDCD");//RCQ00028947
		lxAbr.addDebug(modelItem.getKey()+" has RATECARDCD "+ratecardcd);
		if (ratecardcd==null){
			lxAbr.addDebug(modelItem.getKey()+" did not have RATECARDCD attr meta: "+
					modelItem.getEntityGroup().getMetaAttribute("RATECARDCD"));
			ratecardcd = "SSM-0080";
		}
		attrValTbl.put("RATECARDCD",ratecardcd);//RATECARDCD	U	Rate Card Code	use Model value
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		attrValTbl.put("PDHDOMAIN", domain);
				
		StringBuffer debugSb = new StringBuffer();
		try{
			wwseo = ABRUtil.createEntity(lxAbr.getDatabase(), lxAbr.getProfile(), WWSEO_CREATEACTION_NAME, modelItem,  
				"WWSEO", attrCodeVct, attrValTbl, debugSb); 
		}catch(EANBusinessRuleException ere){
			throw ere;
		}finally{
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
			if (wwseo==null){
				lxAbr.addError("ERROR: Can not create WWSEO entity for seoid: "+seoid);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
		
		return wwseo;
	}
	/*****************************************************
	 * 5.	Create LSEO
	 * The parent WWSEO is the WWSEO created in step 4.
	 * The attributes of the LSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with 
	 * details provided on the SG Notes tab.
	 * ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
	 * COMNAME	T	Common Name	<SEOID>
	 * COUNTRYLIST	F	Country List	<COUNTRY>
	 * DATAQUALITY	S	DataQuality	Default
	 * PDHDOMAIN	F	Domains	Derived from MODEL
	 * SEOID	T	SEO ID	SEOID
	 * TRANSLATIONWATCH	U	Translation Watch	Default
	 * LSEOPUBDATEMTRGT	T <PUBFROM>	
	 *
	 * @param lxAbr
	 * @param wwseoItem
	 * @param seoid
	 * @param pubfrom
	 * @param countryVct
	 * @param domain - comes from Model now
	 * @param audien - derived from XML msg now
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected static EntityItem createLSEO(LXABRSTATUS lxAbr, EntityItem wwseoItem, String seoid, String pubfrom, 
			Vector countryVct, Object domain, Object audien) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		EntityItem lseo = null;
		
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("SEOID");
		attrValTbl.put("SEOID", seoid);
		attrCodeVct.addElement("COMNAME");
		attrValTbl.put("COMNAME", seoid); //COMNAME	T	Common Name	<SEOID>
		attrCodeVct.addElement("LSEOPUBDATEMTRGT");
		attrValTbl.put("LSEOPUBDATEMTRGT", pubfrom); //LSEOPUBDATEMTRGT	<PUBFROM>
		attrCodeVct.addElement("AUDIEN");
		attrValTbl.put("AUDIEN", audien);//"10062"); //AUDIEN	F	Audience	"LE Direct"	flag code = 10062
		attrCodeVct.addElement("ACCTASGNGRP");
		attrValTbl.put("ACCTASGNGRP", "01"); //ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
		attrCodeVct.addElement("COUNTRYLIST");
		attrValTbl.put("COUNTRYLIST", countryVct); //COUNTRYLIST	F	Country List	<COUNTRY>
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		attrValTbl.put("PDHDOMAIN", domain);
		
		StringBuffer debugSb = new StringBuffer();
		try{
			lseo = ABRUtil.createEntity(lxAbr.getDatabase(), lxAbr.getProfile(), LSEO_CREATEACTION_NAME, wwseoItem,  
				"LSEO", attrCodeVct, attrValTbl, debugSb); 
		}catch(EANBusinessRuleException ere){
			throw ere;
		}finally{
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
			if (lseo==null){
				lxAbr.addError("ERROR: Can not create LSEO entity for seoid: "+seoid);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
				
		return lseo;
	}

	/************************************
	 * 6.	Create references to features 
	 * For each instance of <FEATUREELEMENT> and based on the value of COFCAT for the MODEL found in step 1:
	 * 
	 * 1.	Hardware
	 * Search for PRODSTRUCT using <MT> <MODEL> <FEATURECODE> and create WWSEOPRODSTRUCT from the 
	 * WWSEO created in step 5 to the PRODSTRUCT.
	 * 
	 * 2.	Software 
	 * Search for SWPRODSTRUCT using <MT> <MODEL> <FEATURECODE> and create WWSEOSWPRODSTRUCT from the 
	 * WWSEO created in step 5 to the SWPRODSTRUCT. 
	 * 
	 * 3.	Service 
	 * Service WWSEOs do not have Service Features and therefore there is nothing to do at this step.
	 * 
	 * @param lxAbr
	 * @param wwseoItem
	 * @param modelItem
	 * @param tmfVct
	 * @param psQtyTbl
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	protected static void createFeatureRefs(LXABRSTATUS lxAbr, EntityItem wwseoItem, EntityItem modelItem,
			Vector tmfVct, Hashtable psQtyTbl) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 
	{
		// get create action based on model.cofcat
		String linkAction = "";
		String pstype="";
		String qtyAttr="";
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");

		if (COFCAT_HW.equals(cofcatflag)){
			linkAction = WWSEOPS_LINKACTION_NAME; //WWSEOPRODSTRUCT
			pstype = "PRODSTRUCT";
			qtyAttr = "CONFQTY";
		}else if (COFCAT_SW.equals(cofcatflag)){
			linkAction = WWSEOSWPS_LINKACTION_NAME; //WWSEOSWPRODSTRUCT
			pstype = "SWPRODSTRUCT";
			qtyAttr = "SWCONFQTY";
		}else if (COFCAT_SVC.equals(cofcatflag)){
			lxAbr.addDebug("LXLEADSUtil.createFeatureRefs Model is Service, no references created");
			return;
		}

		LinkActionItem lai = new LinkActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(),linkAction);
		EntityItem parentArray[] = new EntityItem[]{wwseoItem};
		EntityItem childArray[] = new EntityItem[tmfVct.size()];

		// get each prodstruct
		tmfVct.copyInto(childArray);

		// do the link	
		lai.setParentEntityItems(parentArray);     
		lai.setChildEntityItems(childArray);
		lxAbr.getDatabase().executeAction(lxAbr.getProfile(), lai);

		// extract and update QTY
		//update dts in profile
		Profile profile = lxAbr.getProfile().getNewInstance(lxAbr.getDatabase());
		String now = lxAbr.getDatabase().getDates().getNow();
		profile.setValOnEffOn(now, now);
		// VE for wwseo to each ps EXRPT3WWSEO3
		EntityList list = lxAbr.getDatabase().getEntityList(profile, 
				new ExtractActionItem(null, lxAbr.getDatabase(),profile, "EXRPT3WWSEO3"), 
				parentArray);

		lxAbr.addDebug("LXLEADSUtil.createFeatureRefs list using VE EXRPT3WWSEO3 after linkaction: "+
				linkAction+"\n"+PokUtils.outputList(list));
		EntityGroup psGrp = list.getEntityGroup(pstype);
		
		for (int x=0; x<psGrp.getEntityItemCount(); x++){
			EntityItem psitem = psGrp.getEntityItem(x);
			
			String qty = (String)psQtyTbl.get(psitem.getKey());
			EntityItem wspsitem = (EntityItem)psitem.getUpLink(0);  // get the new relator
			lxAbr.addDebug("LXLEADSUtil "+psitem.getKey()+" use qty: "+qty+" on "+wspsitem.getKey());
			if (qty!= null && !qty.equals("1")){  // 1 is default so nothing needed
				StringBuffer debugSb = new StringBuffer();
				// save the qty attribute
				ABRUtil.setText(wspsitem,qtyAttr, qty, debugSb); 
				if (debugSb.length()>0){
					lxAbr.addDebug(debugSb.toString());
				}
				// must commit changed entity to the PDH 
				wspsitem.commit(lxAbr.getDatabase(), null);	
			}
		}
		list.dereference();
	}
	
	/***************************************
	 * Find the flag code for this flag description for the GENAREACODE attribute
	 * @param dbCurrent
	 * @param profile
	 * @param ctry
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private static String getGenAreaCodeForCtry(Database dbCurrent, Profile profile,String ctry) throws
		MiddlewareRequestException, SQLException, MiddlewareException
	{
		if (SG_GENAREACODE_TBL==null){
			SG_GENAREACODE_TBL = new Hashtable();
			// get meta info
			EntityGroup eg = new EntityGroup(null,dbCurrent, profile, "GENERALAREA", "Edit", false);
			EANMetaFlagAttribute fma =(EANMetaFlagAttribute)eg.getMetaAttribute("GENAREACODE");
			if (fma!=null) {
                for (int i=0; i<fma.getMetaFlagCount(); i++)
                {
                    MetaFlag omf = fma.getMetaFlag(i);
                    if (omf.isExpired()){
                    	dbCurrent.debug(D.EBUG_SPEW, "LXLEADSUtil.getGenAreaCodeForCtry skipping expired flag: "+omf+"["+
                    			omf.getFlagCode()+"] for GENERALAREA.GENAREACODE");
                    	continue;
                    }
                    SG_GENAREACODE_TBL.put(omf.toString(), omf.getFlagCode());
                }
            }
		}

		return (String)SG_GENAREACODE_TBL.get(ctry);
	}

	/***********
	 * search GENERALAREA using GENAREACODE. 
	 * 2 character Country Code found in GENERALAREA.GENAREACODE and used to get
	 *  GENAREANAME which is the same as COUNTRYLIST
	 * @param dbCurrent
	 * @param profile
	 * @param country
	 * @param debugSb
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	public static EntityItem searchForGENERALAREA(Database dbCurrent, Profile profile, 
			String country,StringBuffer debugSb) throws MiddlewareRequestException, 
			SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem genarea = null;
		
		// HVEC and SG meta is different for GENAREACODE
		String genAreaCodeFlg = getGenAreaCodeForCtry(dbCurrent, profile,country);
		debugSb.append("LXLEADSUtil.searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg+"\n");
		dbCurrent.debug(D.EBUG_SPEW, "LXLEADSUtil.searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg);
		if (genAreaCodeFlg!= null){
			Vector attrVct = new Vector(1);
			attrVct.addElement("GENAREACODE");
			Vector valVct = new Vector(1);
			valVct.addElement(genAreaCodeFlg);
			EntityItem eia[]= null;
			try{
				eia= ABRUtil.doSearch(dbCurrent, profile, 
						GENERALAREA_SRCHACTION_NAME, "GENERALAREA", false, attrVct, valVct, debugSb);
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				debugSb.append("searchForGENERALAREA SBRException: "+exBuf.getBuffer().toString()+"\n");
			}
			if (eia!=null && eia.length > 0){			
				for (int i=0; i<eia.length; i++){
					debugSb.append("LXLEADSUtil.searchForGENERALAREA found "+eia[i].getKey()+"\n");
				}
				genarea = eia[0];
			}
			attrVct.clear();
			valVct.clear();
		}else{
			debugSb.append("LXLEADSUtil.searchForGENERALAREA GENAREACODE table: "+SG_GENAREACODE_TBL+"\n");
		}
		
		return genarea;
	}	
	
	/**
	 * RCQ00028947
	 * C.	Derivation of Audience (AUDIEN)

The XML message provides <ACCOUNTTYPE> which is used to determine AUDIEN as follows:

ACCOUNTTYPE					CHW								HVEC
							AUDIEN							CATALOGNAME_CVAR
Null (i.e. not supplied)	LE Direct (10062)				Default from meta data
Enterprise Direct			LE Direct (10062)				LE Direct (10062)
Odyssey						LE Direct (10062)				LE Direct (10062) +DAC/MAX (10067)
Indirect					LE Direct (10062) +				LE Direct (10062) +
			Catalog - Indirect/Reseller (10048) +			Catalog - Indirect/Reseller (10048) +
			Catalog - Business Partner (10046)				Catalog - Business Partner (10046)			

	 * @param msgAudien
	 * @return
	 */
	protected static Object deriveAudien(String XFSAccountType){
		//AUDIEN	F	Audience	"LE Direct"	flag code = 10062
		Object audien = AUDIEN_TBL.get(XFSAccountType);
		if (audien==null){
			audien = "10062";
		}
		return audien;
	}
}
