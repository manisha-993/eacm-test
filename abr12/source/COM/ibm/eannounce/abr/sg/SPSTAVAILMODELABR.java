/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: SPSTAVAILMODELABR.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2013
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
 *   Date of Creation: Nov 22, 2013
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   Nov 22, 2013     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

//$Log: SPSTAVAILMODELABR.java,v $
//Revision 1.8  2014/07/09 03:05:14  liuweimi
//Fix out of memory issue
//
//Revision 1.7  2014/02/18 07:48:59  liuweimi
//change based on BH FS Inbound Feed SPST20140120.doc.
//Mapping updates for a few items and default values.
//Add TAXCATG relator to service pacs.Check mapping for more details.
//Create new AVAIL existing SEOs/MODELs for the different set of countries.
//Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID.
//Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//
//Revision 1.6  2014/01/07 14:55:15  liuweimi
//3 Open issues - 1. If the first avail fails, continue to process other avails in the xml. This doesn't refer to invalid flag codes or invalid xml format
//2. Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID
//3. Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//

/**
 * 1.	Create AVAIL
	The attributes of the AVAIL  including defaults values are supplied via the XML shown in the SS.Use the derive COMNAME value based in the SS.
	
	The AVAIL’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML.
	
	2.	Search for MODEL using :
	•	<MODEL.MACHTYPEATR> and <MODEL.MODELATR>
	
	If found, then this is an error:
	Date/Time = <DTSOFDATA>
	Fact Sheet = < SPSTSHEETNUM >
	Machine Type = <MT>
	Model = <MODEL>
	Message = “MODEL” already exists”
	SPSTABRSTATUS = “Failed”
	
	3.	Create MODEL and Link them to AVAIL
	The attributes of the MODEL including default values are supplied via the XML shown in the SS. Use the AVAIL.entityid from step 1  and link them to the newly created Service pac MODELs .
	
	
	4.	Search for WWSEO using:
	•	<SEOID>
	
	If found, then this is an error:
	Date/Time = <DTSOFDATA>
	Fact Sheet = < SPSTSHEETNUM >
	Machine Type = <MT>
	Model = <MODEL>
	WWSEO = <SEOID>
	Message = “WWSEO already exists”
	SPSTABRSTATUS = “Failed”
	
	5.	Create WWSEO 
	The parent  is the MODEL created in Step 3.
	The attributes of the WWSEO including default values are supplied via the XML shown in the SS.
	
	
	6.	Search for LSEO using:
	•	<SEOID>
	
	If found, then this is an error:
	Date/Time = <DTSOFDATA>
	Fact Sheet = < SPSTSHEETNUM >
	Machine Type = <MT>
	Model = <MODEL>
	LSEO = <SEOID>
	Message = “LSEO already exists”
	SPSTABRSTATUS = “Failed”
	
	7.	Create LSEO
	The parent is the WWSEO created in step 4.
	The attributes of the LSEO including default values are supplied via the XML shown in the SS. Use the AVAIL.entityid from step 1  and link them to the newly created LSEOs .
	
	
	8.	Create Report
	A report is created and submitted to Subscription/Notification as follows:
	
	Service Pacs from SPST created (SPSTABRSTATUS)
	
	Userid:
	Role:
	Workgroup:
	Date:
	Description: ServicePac Created – MODEL, SEO
	Return code:
	<MT><MODEL> created
	<SEOID> created for 
	
	<COUNTRY>

 * @author Will
 *
 */
public class SPSTAVAILMODELABR extends SPSTABR {	
	
//	private Hashtable seo_model = new Hashtable();
	private static Hashtable SPACTERM_TAB;
//	9.	Create Reference  to TAXGRP(Create relator MODELTAXGRP)
//	The parent is the MODEL created in step 2.
//	Link all MODELS created in Step 2 to the TAXGRP entityid 3
	private static final int TAXGRP_ENTITYID = 3;
	
	static{
//		8.	Create Reference to SVC(Create relator MODELSVC)
//		The parent is the MODEL created in step 2.
//		Look at the value for  XML element SPACTERM in XML and map that to the following SVC entity to create the relationships. 
//		If there is any other value that following ignore creating relationship(like N/A)

		SPACTERM_TAB = new Hashtable();
//		SPACTERM =1 to SVC entityid 1265461
//		SPACTERM =2 to SVC entityid 1265460
//		SPACTERM =3 to SVC entityid 1265459
//		SPACTERM =4 to SVC entityid 1265458
//		SPACTERM =5 to SVC entityid 1265457
		SPACTERM_TAB.put("1", new Integer(1265461));
		SPACTERM_TAB.put("2", new Integer(1265460));
		SPACTERM_TAB.put("3", new Integer(1265459));
		SPACTERM_TAB.put("4", new Integer(1265458));
		SPACTERM_TAB.put("5", new Integer(1265457));		
	}

	public void validateData(SPSTABRSTATUS theAbr, Element rootElem)
			throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		super.validateData(theAbr, rootElem);

	}

//	2.	Search for MODEL using :
//		•	<MODEL.MACHTYPEATR> and <MODEL.MODELATR>
//
//		If found, then this is an error:
//		Date/Time = <DTSOFDATA>
//		Fact Sheet = < SPSTSHEETNUM >
//		Machine Type = <MT>
//		Model = <MODEL>
//		Message = “MODEL” already exists”
//		SPSTABRSTATUS = “Failed”
//
//		4.	Search for WWSEO using:
//		•	<SEOID>
//
//		If found, then this is an error:
//		Date/Time = <DTSOFDATA>
//		Fact Sheet = < SPSTSHEETNUM >
//		Machine Type = <MT>
//		Model = <MODEL>
//		WWSEO = <SEOID>
//		Message = “WWSEO already exists”
//		SPSTABRSTATUS = “Failed”
//
//
//		6.	Search for LSEO using:
//		•	<SEOID>
//
//		If found, then this is an error:
//		Date/Time = <DTSOFDATA>
//		Fact Sheet = < SPSTSHEETNUM >
//		Machine Type = <MT>
//		Model = <MODEL>
//		LSEO = <SEOID>
//		Message = “LSEO already exists”
//		SPSTABRSTATUS = “Failed”
	protected void checkRelatedEntities(Element availElem) throws SQLException,MiddlewareException, MiddlewareShutdownInProgressException {
//					1	<MODELLIST>		4	
		NodeList modelList =availElem.getElementsByTagName("MODELLIST");//MODELIST
//					<1..N>	<MODELELEMENT>		5		
		spstAbr.verifyChildNodes(availElem, "MODELLIST", "MODELELEMENT", 1);	
//		Vector prods = new Vector();
		for (int f=0; f<modelList.getLength(); f++){
			Node modelNode = modelList.item(f);
			if (modelNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList modelElems = modelNode.getChildNodes();//MODELELEMENT
			for(int g=0;g<modelElems.getLength();g++){
				Node modelelemNode = modelElems.item(g);
				if(modelelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element modelElem = (Element)modelelemNode;
//				String machtype = spstAbr.getNodeValue(modelElem, "MACHTYPE", true);
//				String modelatr = spstAbr.getNodeValue(modelElem,"MODELATR", true);
//							check if the below flag description is valid for model
//							1	<MACHTYPE>	</MACHTYPE>	6	M/T	4 Characters; Integer	MODEL	MACHTYPEATR
//							1	<COFSUBCAT>	</COFSUBCAT>	6	Servicepac Type		MODEL	COFSUBCAT
//							1	<BHACCTASGNGRP>	</BHACCTASGNGRP>	6	BH AAG		MODEL, LSEO	BHACCTASGNGRP
//							1	<ACCTASGNGRP>	</ACCTASGNGRP>	6	AAG		MODEL,  LSEO	ACCTASGNGRP//							
//							1	<PRFTCTR>	</PRFTCTR>	6	Profit Center		MODEL, LSEO	PRFTCTR
//							1	<SVCTYPEINDC>	</SVCTYPEINDC>	6	Services Indicator		MODEL	SVCTYPEINDC
//							1	<BHPRODHIERCD>	</BHPRODHIERCD>	6	Model Product Hierarchy 		MODEL, LSEO	BHPRODHIERCD
//							1	<AMRTZTNLNGTH>	</AMRTZTNLNGTH>	6	Amort. Length 		MODEL	AMRTZTNLNGTH
//							1	<AMRTZTNSTRT>	</AMRTZTNSTRT>	6	Amort. Start 		MODEL	AMRTZTNSTRT
//							1	<SVCLEVCD>	</SVCLEVCD>	6	SLC		MODEL	SVCLEVCD
				checkSingleFlagAttr(modelElem, "MACHTYPE", "MACHTYPEATR", FLAG_COL_DESC_LONG);
				checkSingleFlagAttr(modelElem, "COFSUBCAT", "COFSUBCAT", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(modelElem, "BHACCTASGNGRP", "BHACCTASGNGRP", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(modelElem, "ACCTASGNGRP", "ACCTASGNGRP", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(modelElem, "PRFTCTR", "PRFTCTR", FLAG_COL_DESC_CLASS);
				checkSingleFlagAttr(modelElem, "SVCTYPEINDC", "SVCTYPEINDC", FLAG_COL_DESC_LONG);
				checkSingleFlagAttr(modelElem, "BHPRODHIERCD", "BHPRODHIERCD", FLAG_COL_DESC_LONG);
				checkSingleFlagAttr(modelElem, "AMRTZTNLNGTH", "AMRTZTNLNGTH", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(modelElem, "AMRTZTNSTRT", "AMRTZTNSTRT", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(modelElem, "SVCLEVCD", "SVCLEVCD", FLAG_COL_DESC_SHORT);
//				checkDuplicatedProd("MODEL", modelatr+"-"+modelatr, prods);
				//TODO test this case for duplicated prod
				NodeList seoElems = modelElem.getElementsByTagName("SEOELEMENT");//checked for wwseo&lseo based on seoid
				for(int h=0;h<seoElems.getLength();h++){
					Node seoNode = seoElems.item(h);
					if(seoNode.getNodeType()!=Node.ELEMENT_NODE){
						continue;
					}
					Element seoElem = (Element)seoNode;
//					String seoid = spstAbr.getNodeValue(seoElem, "SEOID", true);
//					String mt_model = machtype+modelatr;
//					String mtmodel = (String) seo_model.get(seoid);
//					if(mtmodel != null && !mt_model.equals(mtmodel)){
////						ERROR_SEOID_MODEL = One SEOID({0}) has different MODELs in XML.
//						spstAbr.addError("ERROR_SEOID_MODEL",new Object[]{seoid});
//					}else{
//						seo_model.put(seoid, mt_model);
//					}
//					checkDuplicatedProd("SEO", seoid, prods);
					checkSingleFlagAttr(seoElem, "SEOBHPRODHIERCD", "BHPRODHIERCD", FLAG_COL_DESC_LONG);
					
//								1	<AUDLIST>		7				
//								<1..N>	<AUDIENCE>	</AUDIENCE>	8	Seo Audience		LSEO	AUDIEN
//										</AUDLIST>	7				
//								1	<COUNTRYLIST>		7	Countr(ies)		LSEO	COUNTRYLIST
//								<1..N>	<COUNTRY>	</COUNTRY>	8
					spstAbr.verifyChildNodes(seoElem, "AUDLIST", "AUDIENCE", 1);//check audience for LSEO
					checkMultiFlagAttr(seoElem,"AUDLIST","AUDIENCE","AUDIEN");//check flag code for audience
					spstAbr.verifyChildNodes(seoElem, "COUNTRYLIST", "COUNTRY", 1);//check country for LSEO
					checkMultiFlagAttr(seoElem,"COUNTRYLIST","COUNTRY","COUNTRYLIST");//check flag code for country
				}
//							1	<AUDLIST>		6				
//							<1..N>	<AUDIENCE>	</AUDIENCE>	7	MTM Audience		MODEL 	AUDIEN
//									</AUDLIST>	6	
				spstAbr.verifyChildNodes(modelElem, "AUDLIST", "AUDIENCE", 1);//check audience for MODEL
				checkMultiFlagAttr(modelElem,"AUDLIST","AUDIENCE","AUDIEN");//check flag code for country				
			}			
		}
//		prods.clear();		
	}	

	/**
	 * 3.	Create MODEL and Link them to AVAIL
		The attributes of the MODEL including default values are supplied via the XML shown in the SS. Use the AVAIL.entityid from step 1  and link them to the newly created Service pac MODELs .
		
		5.	Create WWSEO 
		The parent  is the MODEL created in Step 3.
		The attributes of the WWSEO including default values are supplied via the XML shown in the SS.
		
		7.	Create LSEO
		The parent is the WWSEO created in step 4.
		The attributes of the LSEO including default values are supplied via the XML shown in the SS. Use the AVAIL.entityid from step 1  and link them to the newly created LSEOs .

	 */
	protected void generateEntities(Vector availattcodeVct,
			Hashtable availattValtab, Element availElem, StringBuffer availCntySb)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException {
		EntityItem availitem = null;
//		1	<MODELLIST>		4	
		NodeList modelList =availElem.getElementsByTagName("MODELLIST");//MODELIST
		//					<1..N>	<MODELELEMENT>		5	
		for (int f=0; f<modelList.getLength(); f++){
			Node modelNode = modelList.item(f);
			if (modelNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList modelElems = modelNode.getChildNodes();//MODELELEMENT
			for(int g=0;g<modelElems.getLength();g++){
				Node modelelemNode = modelElems.item(g);
				if(modelelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element modelElem = (Element)modelelemNode;
				String machtype = getSingleFlag(modelElem, "MACHTYPE");
				String modelatr = spstAbr.getNodeValue(modelElem, "MODELATR", false);
				String mktgname = spstAbr.getNodeValue(modelElem, "MKTGNAME", false);
				String bhacc = getSingleFlag(modelElem, "BHACCTASGNGRP");
				String acc = spstAbr.getNodeValue(modelElem, "ACCTASGNGRP", false);
				String prftctr = getSingleFlag(modelElem, "PRFTCTR");
				String anndate = spstAbr.getNodeValue(modelElem, "ANNDATE", false);
				String withdrawdate = spstAbr.getNodeValue(modelElem, "WITHDRAWDATE", false);
				System.gc();
		        long start = Runtime.getRuntime().freeMemory();
		        spstAbr.addDebug("memory before add to map:" + (start));
				EntityItem modelitem = this.searchForModel(machtype, modelatr);
				//ERROR_MODEL = MODEL already exist.
				boolean isModelExist = false;
				if(modelitem !=null){
					isModelExist = true;
					if(!isValidNewAvail(modelitem,(Vector) availattValtab.get("COUNTRYLIST"), "146")){
						spstAbr.addError("ERROR_MODEL",new Object[]{machtype,modelatr});
						continue;
					}else{
						spstAbr.addOutput("LINK_MODEL",new String[]{machtype,modelatr,(String)availattValtab.get("COMNAME"),availCntySb.toString()});
					}
				}else{
					Vector modelattcodeVct = new Vector();
					Hashtable modelattValtab = new Hashtable();
	//						13.0	130	1	<MACHTYPE>	</MACHTYPE>	6	M/T	4 Characters; Integer	MODEL	MACHTYPEATR			
	//						14.0	140	1	<MODELATR>	</MODELATR>	6	Model	3 Characters; Upper Alpha | Integer	MODEL	MODELATR
					modelattcodeVct.addElement("MACHTYPEATR");
					modelattValtab.put("MACHTYPEATR",machtype);
					modelattcodeVct.addElement("MODELATR");
					modelattValtab.put("MODELATR",modelatr);
					modelattcodeVct.addElement("COFCAT");
					modelattValtab.put("COFCAT", "102");
	//				17.0	170	1	<COFSUBCAT>	</COFSUBCAT>	6	Servicepac Type		MODEL	COFSUBCAT
					modelattcodeVct.addElement("COFSUBCAT");
					modelattValtab.put("COFSUBCAT","194");//set it and change it later
					modelattcodeVct.addElement("COFGRP");
					modelattValtab.put("COFGRP", "150");//set it and change it later
					modelattcodeVct.addElement("COFSUBGRP");
					modelattValtab.put("COFSUBGRP","010");
	//						15.0	150	1	<MKTGNAME>	</MKTGNAME>	6	Marketing Description		MODEL, WWSEO, LSEO	MKTGNAME SEOTECHDESC, LSEOMKTGDESC
					modelattcodeVct.addElement("MKTGNAME");

					if(mktgname != null && mktgname.length()>254){
						spstAbr.addError("MKTGNAME's length > 254, failed this model: " + mktgname);
						continue;
					}
					modelattValtab.put("MKTGNAME",mktgname);
	//						16.0	160	1	<INVNAME>	</INVNAME>	6	Part Description		MODEL	INVNAME
					modelattcodeVct.addElement("INVNAME");
					modelattValtab.put("INVNAME",spstAbr.getNodeValue(modelElem, "INVNAME", false));
	
	//						18.0	180	1	<BHACCTASGNGRP>	</BHACCTASGNGRP>	6	BH AAG		MODEL, LSEO	BHACCTASGNGRP
					modelattcodeVct.addElement("BHACCTASGNGRP");
//					String bhacc = getSingleFlag(modelElem, "BHACCTASGNGRP");
					modelattValtab.put("BHACCTASGNGRP",bhacc);
	//						19.0	190	1	<ACCTASGNGRP>	</ACCTASGNGRP>	6	AAG		MODEL,  LSEO	ACCTASGNGRP
					modelattcodeVct.addElement("ACCTASGNGRP");
//					String acc = spstAbr.getNodeValue(modelElem, "ACCTASGNGRP", false);
					modelattValtab.put("ACCTASGNGRP",acc);
	//						20.0	200	1	<ACCTGMACHTYPE>	</ACCTGMACHTYPE>	6	Acc. Mtype		MODEL	ACCTGMACHTYPE
					modelattcodeVct.addElement("ACCTGMACHTYPE");
					modelattValtab.put("ACCTGMACHTYPE",spstAbr.getNodeValue(modelElem, "ACCTGMACHTYPE", false));
	//						21.0	210	1	<PRFTCTR>	</PRFTCTR>	6	Profit Center		MODEL, LSEO	PRFTCTR
					modelattcodeVct.addElement("PRFTCTR");
//					String prftctr = getSingleFlag(modelElem, "PRFTCTR");
					modelattValtab.put("PRFTCTR",prftctr);
	//						22.0	220	1	<SVCTYPEINDC>	</SVCTYPEINDC>	6	Services Indicator		MODEL	SVCTYPEINDC
					modelattcodeVct.addElement("SVCTYPEINDC");
					modelattValtab.put("SVCTYPEINDC",getSingleFlag(modelElem, "SVCTYPEINDC"));
	//						23.0	230	1	<SAPASSORTMODULE>	</SAPASSORTMODULE>	6	TMF ASM - We need a value, not N/A		MODEL	SAPASSORTMODULE
					modelattcodeVct.addElement("SAPASSORTMODULE");
					modelattValtab.put("SAPASSORTMODULE",spstAbr.getNodeValue(modelElem, "SAPASSORTMODULE", false));
	//						24.0	240	1	<BHPRODHIERCD>	</BHPRODHIERCD>	6	Model Product Hierarchy 		MODEL	BHPRODHIERCD
					modelattcodeVct.addElement("BHPRODHIERCD");
					modelattValtab.put("BHPRODHIERCD",getSingleFlag(modelElem, "BHPRODHIERCD"));
	//						25.0	250	1	<AMRTZTNLNGTH>	</AMRTZTNLNGTH>	6	Amort. Length 		MODEL	AMRTZTNLNGTH
					modelattcodeVct.addElement("AMRTZTNLNGTH");
					modelattValtab.put("AMRTZTNLNGTH",getSingleFlag(modelElem, "AMRTZTNLNGTH"));
	//						26.0	260	1	<AMRTZTNSTRT>	</AMRTZTNSTRT>	6	Amort. Start 		MODEL	AMRTZTNSTRT
					modelattcodeVct.addElement("AMRTZTNSTRT");
					modelattValtab.put("AMRTZTNSTRT",getSingleFlag(modelElem, "AMRTZTNSTRT"));
	//						27.0	270	1	<SVCLEVCD>	</SVCLEVCD>	6	SLC		MODEL	SVCLEVCD
					modelattcodeVct.addElement("SVCLEVCD");
					modelattValtab.put("SVCLEVCD",getSingleFlag(modelElem, "SVCLEVCD"));
	//						28.0	280	1	<SDFCD>	</SDFCD>	6	SDF		MODEL	SDFCD
					modelattcodeVct.addElement("SDFCD");
					modelattValtab.put("SDFCD",spstAbr.getNodeValue(modelElem, "SDFCD", false));
	//						29.0	290	1	<ANNDATE>	</ANNDATE>	6	Announcement Date		MODEL, LSEO	ANNDATE, LSEOPUBDATEMTRGT
					modelattcodeVct.addElement("ANNDATE");
//					String anndate = spstAbr.getNodeValue(modelElem, "ANNDATE", false);
					modelattValtab.put("ANNDATE",anndate);
	//						30.0	300	1	<GENAVAILDATE>	</GENAVAILDATE>	6	General Avail Date		MODEL	GENAVAILDATE
					modelattcodeVct.addElement("GENAVAILDATE");
					modelattValtab.put("GENAVAILDATE",spstAbr.getNodeValue(modelElem, "GENAVAILDATE", false));
	//						31.0	310	1	<WITHDRAWDATE>	</WITHDRAWDATE>	6	Withdraw Date		MODEL, LSEO	WITHDRAWDATE, LSEOUNPUBDATEMTRGT
					modelattcodeVct.addElement("WITHDRAWDATE");
//					String withdrawdate = spstAbr.getNodeValue(modelElem, "WITHDRAWDATE", false);
					modelattValtab.put("WITHDRAWDATE",withdrawdate);
	//						32.0	320	1	<WTHDRWEFFCTVDATE>	</WTHDRWEFFCTVDATE>	6	Withdraw Date		MODEL	WTHDRWEFFCTVDATE
					modelattcodeVct.addElement("WTHDRWEFFCTVDATE");
					modelattValtab.put("WTHDRWEFFCTVDATE",spstAbr.getNodeValue(modelElem, "WTHDRWEFFCTVDATE", false));
	//						1	<AUDLIST>		6				
	//						<1..N>	<AUDIENCE>	</AUDIENCE>	7	MTM Audience		MODEL 	AUDIEN
	//								</AUDLIST>	6				
	//				NodeList audList = modelElem.getElementsByTagName("AUDLIST");						
					modelattcodeVct.addElement("AUDIEN");
					modelattValtab.put("AUDIEN",generateMultiFlags(modelElem, "AUDLIST","AUDIENCE",new StringBuffer()));
					
					//create the model
					modelitem = createMODEL(spstAbr, modelattcodeVct, modelattValtab);
	//				8.	Create Reference to SVC(Create relator MODELSVC)
	//				The parent is the MODEL created in step 2.
	//				Look at the value for  XML element SPACTERM in XML and map that to the following SVC entity 
	//				to create the relationships. 
	//				If there is any other value that following ignore creating relationship(like N/A)
					String spacterm = spstAbr.getNodeValue(modelElem, "SPACTERM", false);
					int svcid = getSVCEntityId(spacterm);
					if(svcid != -1){
						link("MODELSVC", "MODEL", modelitem.getEntityID(), "SVC",svcid);
					}else{
						spstAbr.addDebug("SPACTERM element does not exist or invalid value for it, just skip it");
					}
	//				9.	Create Reference  to TAXGRP(Create relator MODELTAXGRP)
	//				The parent is the MODEL created in step 2.
	//				Link all MODELS created in Step 2 to the TAXGRP entityid 3
	//				private static final int TAXGRP = 3;
					link("MODELTAXGRP", "MODEL", modelitem.getEntityID(), "TAXGRP",TAXGRP_ENTITYID);
//					doReferenceModelTaxCatg(modelitem,(Vector) availattValtab.get("COUNTRYLIST"));
					
					//change some attributes to correct value: subcatgory and group
					//update flag for some values that need bypass rule
					spstAbr.setFlagValue(spstAbr.getProfile(), "COFGRP", "010", modelitem);//change to default value N/A
					spstAbr.setFlagValue(spstAbr.getProfile(), "COFSUBCAT", getSingleFlag(modelElem, "COFSUBCAT"), modelitem);	//change it to correct one
					spstAbr.setTextValue(spstAbr.getProfile(), "MODMKTGDESC", mktgname, modelitem);//need to bypass the rule。
					spstAbr.setTextValue(spstAbr.getProfile(), "INTERNALNAME", mktgname, modelitem);//need to bypass the rule。
					spstAbr.updatePDH(spstAbr.getProfile());
					modelattcodeVct.clear();
					modelattcodeVct = null;
					modelattValtab.clear();
					modelattValtab = null;
				}
				
				LinkActionItem modelavaillink = new LinkActionItem(null, spstAbr.getDatabase(),spstAbr.getProfile(),MODELAVAIL_LINK_ACTION);
				//create avail based on model, if the avail has been created, just do the reference
				availitem = createOrRefAvail(modelitem, availitem, MODELAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, 
						modelavaillink);
//				10.	Create Reference  to TAXGRP(Create relator MODTAXRELEVANCE)
//				The parent is the MODEL created in step 2.
//				Link all MODELS created in Step 2 to the TAXCATG  defined in a mapping SS.
				doReferenceModelTaxCatg(modelitem,(Vector) availattValtab.get("COUNTRYLIST"));
				
				NodeList seoElems = modelElem.getElementsByTagName("SEOELEMENT");//checked for wwseo&lseo based on seoid
				StringBuffer msgSeoid =  new StringBuffer();
				StringBuffer countrySb = new StringBuffer();
				for(int h=0;h<seoElems.getLength();h++){
					Node seoNode = seoElems.item(h);
					if(seoNode.getNodeType()!=Node.ELEMENT_NODE){
						continue;
					}
					Element seoElem = (Element)seoNode;				

					String seoid = spstAbr.getNodeValue(seoElem, "SEOID", false);
					EntityItem bundleItem = searchForLSEOBUNDLE(seoid);//continue to check this, that is a violation
					if(bundleItem !=null){
						spstAbr.addError("ERROR_LSEO_EXIST_BUNLDE",new Object[]{machtype,modelatr,seoid,seoid});
						continue;
					}	
					EntityItem wwseoItem = searchForWWSEO(seoid);
					if(wwseoItem != null){
						if(!isModelExist){
							spstAbr.addError("WWSEO exist, but MODEL does not exist. Please check");	
						}
						spstAbr.addError("ERROR_WWSEO",new Object[]{machtype,modelatr,seoid});
						//continue to check lseo
					}
					EntityItem lseoItem = searchForLSEO(seoid);
					if(lseoItem !=null){
						if(!isModelExist){
							spstAbr.addError("LSEO exist, but MODEL does not exist. Please check");	
							continue;
						}else if(!isValidNewAvail(lseoItem,(Vector) availattValtab.get("COUNTRYLIST"), "146")){
							spstAbr.addError("ERROR_LSEO",new Object[]{machtype,modelatr,seoid});
							continue;
						}else{
							spstAbr.addOutput("LINK_LSEO",new String[]{machtype,modelatr,seoid,(String)availattValtab.get("COMNAME"),availCntySb.toString()});
						}
					}else{	
						String prodhiercd = spstAbr.getNodeValue(seoElem, "PRODHIERCD", false);
						if(wwseoItem == null){			
							Vector wwseoattrcodes = new Vector();
							Hashtable wwseoattrvals = new Hashtable();
							msgSeoid = msgSeoid.append(seoid + " ");
		//							36.0	360	1	<SEOID>	</SEOID>	7	SEO Id		WWSEO & LSEO	SEOID, COMNAME
							wwseoattrcodes.addElement("SEOID");
							wwseoattrvals.put("SEOID", seoid);
							wwseoattrcodes.addElement("COMNAME");
							wwseoattrvals.put("COMNAME", seoid);
		//							37.0	370	1	<PRODHIERCD>	</PRODHIERCD>	7	Product Hierarchy		WWSEO, LSEO	PRODHIERCD
//							String prodhiercd = spstAbr.getNodeValue(seoElem, "PRODHIERCD", false);
							wwseoattrcodes.addElement("PRODHIERCD");
							wwseoattrvals.put("PRODHIERCD", prodhiercd);
		//							15.0	150	1	<MKTGNAME>	</MKTGNAME>	6	Marketing Description		MODEL, WWSEO, LSEO	MKTGNAME SEOTECHDESC, LSEOMKTGDESC
							wwseoattrcodes.addElement("SEOTECHDESC");
							wwseoattrvals.put("SEOTECHDESC", mktgname);
							
							wwseoItem = createWWSEO(spstAbr, modelitem, wwseoattrcodes, wwseoattrvals);//create wwseo
							wwseoattrcodes.clear();
							wwseoattrcodes = null;
							wwseoattrvals.clear();
							wwseoattrvals = null;
						}else{					
							spstAbr.addError("LSEO does not existed, but WWSEO existed, please check : SEOID = "+seoid);
						}
	
						msgSeoid = msgSeoid.append(seoid + " ");
						Vector lseoattrcodes = new Vector();
						Hashtable lseoattrvals = new Hashtable();
						lseoattrcodes.addElement("SEOID");
						lseoattrvals.put("SEOID", seoid);
						lseoattrcodes.addElement("COMNAME");
						lseoattrvals.put("COMNAME", seoid);
						lseoattrcodes.addElement("LSEOMKTGDESC");
						lseoattrvals.put("LSEOMKTGDESC", "LSEO MARKET INFOR temp");		
						lseoattrcodes.addElement("PRODHIERCD");
						lseoattrvals.put("PRODHIERCD", prodhiercd);
	//							18.0	180	1	<BHACCTASGNGRP>	</BHACCTASGNGRP>	6	BH AAG		MODEL, LSEO	BHACCTASGNGRP
						lseoattrcodes.addElement("BHACCTASGNGRP");
						lseoattrvals.put("BHACCTASGNGRP", bhacc);
	//							19.0	190	1	<ACCTASGNGRP>	</ACCTASGNGRP>	6	AAG		MODEL,  LSEO	ACCTASGNGRP
						lseoattrcodes.addElement("ACCTASGNGRP");
						lseoattrvals.put("ACCTASGNGRP", acc);
	//							21.0	210	1	<PRFTCTR>	</PRFTCTR>	6	Profit Center		MODEL, LSEO	PRFTCTR
						lseoattrcodes.addElement("PRFTCTR");
						lseoattrvals.put("PRFTCTR", prftctr);
	//							29.0	290	1	<ANNDATE>	</ANNDATE>	6	Announcement Date		MODEL, LSEO	ANNDATE, LSEOPUBDATEMTRGT
						lseoattrcodes.addElement("LSEOPUBDATEMTRGT");
						lseoattrvals.put("LSEOPUBDATEMTRGT", anndate);
	//							31.0	310	1	<WITHDRAWDATE>	</WITHDRAWDATE>	6	Withdraw Date		MODEL, LSEO	WITHDRAWDATE, LSEOUNPUBDATEMTRGT
						lseoattrcodes.addElement("LSEOUNPUBDATEMTRGT");
						lseoattrvals.put("LSEOUNPUBDATEMTRGT", withdrawdate);
	
	//							38.0	380	1	<SEOBHPRODHIERCD>	</SEOBHPRODHIERCD>	7	SEO Product Hierarchy		LSEO	BHPRODHIERCD
						lseoattrcodes.addElement("BHPRODHIERCD");
						lseoattrvals.put("BHPRODHIERCD",getSingleFlag(seoElem, "SEOBHPRODHIERCD"));
	//							39.0	390	1	<SAPASSORTMODULE>	</SAPASSORTMODULE>	7	SEO ASM		LSEO	SAPASSORTMODULE
						lseoattrcodes.addElement("SAPASSORTMODULE");
						lseoattrvals.put("SAPASSORTMODULE",spstAbr.getNodeValue(seoElem, "SAPASSORTMODULE", false));
	//							40.0	400	1	<AUDLIST>		7				
	//							41.0	410	<1..N>	<AUDIENCE>	</AUDIENCE>	8	Seo Audience	ALL =  the following: Catalog - Business Partner, Catalog - Indirect/Reseller, LE Direct, Public. If not ALL, SPST will send specific Audience	LSEO	AUDIEN
	//											</AUDLIST>	7	
	//					NodeList seoaudList = seoElem.getElementsByTagName("AUDLIST");						
						lseoattrcodes.addElement("AUDIEN");
						StringBuffer buffer = new StringBuffer();
						lseoattrvals.put("AUDIEN",generateMultiFlags(seoElem, "AUDLIST","AUDIENCE",buffer));
	//							42.0	420	1	<COUNTRYLIST>		7				
	//							43.0	430	<1..N>	<COUNTRY>	</COUNTRY>	8	Countr(ies)		LSEO	COUNTRYLIST
	//											</COUNTRYLIST>	7	
	//					NodeList seocntyList = seoElem.getElementsByTagName("COUNTRYLIST");
						Vector seoCntyV = generateMultiFlags(seoElem,"COUNTRYLIST","COUNTRY",countrySb);
						lseoattrcodes.addElement("COUNTRYLIST");
						lseoattrvals.put("COUNTRYLIST",seoCntyV);
						
						lseoItem = createLSEO(spstAbr, wwseoItem, lseoattrcodes, lseoattrvals);
						spstAbr.setTextValue(spstAbr.getProfile(), "LSEOMKTGDESC", mktgname, lseoItem);//need to bypass the rule。
						spstAbr.updatePDH(spstAbr.getProfile());
						lseoattrcodes.clear();
						lseoattrcodes = null;
						lseoattrvals.clear();
						lseoattrvals = null;
					}
					
					LinkActionItem lseolavaillink = new LinkActionItem(null, spstAbr.getDatabase(),spstAbr.getProfile(),LSEOAVAIL_LINK_ACTION);
					availitem = createOrRefAvail(lseoItem, availitem, LSEOAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, lseolavaillink);
					wwseoItem = null;
					lseoItem = null;
				}
				
				//CREATED_MODEL_LSEO = AVAIL - {0} created for <br />  &nbsp;&nbsp;&nbsp;Country:{1}<br />&nbsp;&nbsp;&nbsp;
//				And linked to New MODEL|SEO<br />&nbsp;&nbsp;&nbsp;MODEL - {2} {3} created.
//				<br />&nbsp;&nbsp;&nbsp;WWSEO&LSEO - {4} created for <br /> &nbsp;&nbsp;&nbsp;Country:{5}<br />
				if(!isModelExist){
					spstAbr.addOutput("CREATED_MODEL_LSEO", new String[]{(String)availattValtab.get("COMNAME"),availCntySb.toString(),
							machtype,modelatr,
							msgSeoid.toString(),countrySb.toString()});
				}else if(msgSeoid.toString().length()>0){
					spstAbr.addOutput("CREATED_LSEO", new String[]{(String)availattValtab.get("COMNAME"),availCntySb.toString(),
							msgSeoid.toString(),countrySb.toString()});
				}
				modelitem = null;
				System.gc();
		        long end = Runtime.getRuntime().freeMemory();
		        spstAbr.addDebug("memory after add to map:" + (end));
		        spstAbr.addDebug("memory add:" + (end - start));
			}			
		}
		availitem = null;
	}
	private int getSVCEntityId(String spacterm) {
		int svcid = -1;
		if(spacterm != null){
			Object svcidObj = SPACTERM_TAB.get(spacterm);
			if(svcidObj != null){
				svcid = ((Integer)svcidObj).intValue();
			}						
		}
		return svcid;
	}

	public String getVersion() {
		return "1.0";
	}

	public String getTitle() {
		return SPSTABRSTATUS.TITLE+(spstAbr.getReturnCode()==PokBaseABR.PASS?" created":"" + " - MODEL, SEO");
	}


	public String getDescription() {
		return "ServicePac Created – MODEL, SEO";
	}
}
