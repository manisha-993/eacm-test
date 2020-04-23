/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: SPSTAVAILBUNDLEABR.java
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

//$Log: SPSTAVAILBUNDLEABR.java,v $
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
	
	1.	Search for LSEOBUNDLE using:
	•	<SEOID>
	
	If found, then this is an error:
	Date/Time = <DTSOFDATA>
	Fact Sheet = <SPSTSHEETNUM>
	LSEOBUNDLE = <BDLSEOID>
	Message = “LSEOBUNDLE already exists”
	SPSTABRSTATUS = “Failed”
	
	2.	Create LSEOBUNDLE and Link AVAIL
	The attributes of the LSEOBUNDLE including default values are supplied via the XML shown in the SS.
	
	The LSEOBUNDLE’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML. 
	
	 Use the AVAIL.entityid from step 1  and link them to the newly created LSEOBUNDLEs .
	
	The attributes of the AVAIL including default values are supplied via the XML shown in the SS. Use the derive COMNAME value based in the SS.
	
	The AVAIL’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML.
	
	
	3.	Create references to LSEOs
	For each <SEOELEMENT>, search for LSEO using <SEOID>  in the XML. If found, then create Relator LSEOBUNDLELSEO between the LSEOBUNDLE created in step 2 and the LSEO found via this search. If not found, then create the LSEO as described in the prior section for <SPSTADDSEO> fail the abr.
	
	4.	Create Report
	A report is created and submitted to Subscription/Notification as follows:
	
	LSEOBUNDLE Service Pacs from SPSTS created (SPSTABRSTATUS)
	
	Userid:
	Role:
	Workgroup:
	Date:
	Description: ServicePac Created - LSEOBUNDLE
	Return code:
	
	<BDLSEOID> created for
	<COUNTRY>
	
	with
	
	<SEOID> referenced

 * @author Will
 *
 */
public class SPSTAVAILBUNDLEABR extends SPSTABR {

	/**
	 * 1.	Search for LSEOBUNDLE using:
			• <SEOID>

		If found, then this is an error:
		Date/Time = <DTSOFDATA>
		Fact Sheet = <SPSTSHEETNUM>
		LSEOBUNDLE = <BDLSEOID>
		Message = “LSEOBUNDLE already exists”
		SPSTABRSTATUS = “Failed”

	 */
	protected void checkRelatedEntities(Element availElem) throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		//1	<BUNDLELIST>		4
		NodeList bundleList =availElem.getElementsByTagName("BUNDLELIST");//BUNDLELIST
		//<1..N>	<BUNDLEELEMENT>		5		
		spstAbr.verifyChildNodes(availElem, "BUNDLELIST", "BUNDLEELEMENT", 1);	

		for (int f=0; f<bundleList.getLength(); f++){
			Node bundleNode = bundleList.item(f);
			if (bundleNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList bundleElems = bundleNode.getChildNodes();
			for(int g=0;g<bundleElems.getLength();g++){
				Node bundleelemNode = bundleElems.item(g);
				if(bundleelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element bundleElem = (Element)bundleelemNode;
//				1	<BDLSEOID>	</BDLSEOID>	6	Bundle SEOID		LSEOBUNDLE	SEOID
//				String bdlseoid = spstAbr.getNodeValue(bundleElem, "BDLSEOID", true);
//				checkDuplicatedProd("LSEOBUNDLE", bdlseoid, prods);// for differnt avail, it may be correct, disable it

//				check if the below flag description is valid for lseobundle
//				BHACCTASGNGRP BH Account Assignment Group
//				ACCTASGNGRP Account Assignment Group
//				PRFTCTR Profit Center
//				BHPRODHIERCD BH Product Hierarchy Code
//				AUDIEN  
				checkSingleFlagAttr(bundleElem, "BHACCTASGNGRP", "BHACCTASGNGRP", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(bundleElem, "ACCTASGNGRP", "ACCTASGNGRP", FLAG_COL_DESC_SHORT);
				checkSingleFlagAttr(bundleElem, "PRFTCTR", "PRFTCTR", FLAG_COL_DESC_CLASS);
				checkSingleFlagAttr(bundleElem, "SEOBHPRODHIERCD", "BHPRODHIERCD", FLAG_COL_DESC_LONG);
				
//				NodeList seoElems = bundleElem.getElementsByTagName("SEOLIST");//checked for lseobundle based on seoid
				spstAbr.verifyChildNodes(bundleElem, "SEOLIST", "SEOID", 1);						
			//				1	<AUDLIST>		6				
			//				<1..N>	<AUDIENCE>	</AUDIENCE>	7	MTM Audience		MODEL 	AUDIEN
			//						</AUDLIST>	6		
//				1	<COUNTRYLIST>		6				
//				<1..N>	<COUNTRY>	</COUNTRY>	7	Countr(ies)		LSEOBUNDLE	COUNTRYLIST
//						</COUNTRYLIST>	6				
//				NodeList audList = bundleElem.getElementsByTagName("AUDLIST");
				spstAbr.verifyChildNodes(bundleElem, "AUDLIST", "AUDIENCE", 1);//check audience for bundle
				checkMultiFlagAttr(bundleElem,"AUDLIST","AUDIENCE","AUDIEN");//check flag code for country
//				from Rupal: You would need countrylist to create AVAIL as well so fail the ABR
//				NodeList bundlecntyList = bundleElem.getElementsByTagName("COUNTRYLIST");
				spstAbr.verifyChildNodes(bundleElem, "COUNTRYLIST", "COUNTRY", 1);//check country for bundle
				checkMultiFlagAttr(bundleElem,"COUNTRYLIST","COUNTRY","COUNTRYLIST");//check flag code for country
			}			
		}	

	}

	public String getVersion() {
		return "1.0";
	}

	public String getTitle() {
		return SPSTABRSTATUS.TITLE+(spstAbr.getReturnCode()==PokBaseABR.PASS?" created":"" + " - LSEOBUNDLE");
	}

	public String getDescription() {
		return "ServicePac Created - LSEOBUNDLE";
	}

	/**
	 * 2.	Create LSEOBUNDLE and Link AVAIL
		The attributes of the LSEOBUNDLE including default values are supplied via the XML shown in the SS.
		
		The LSEOBUNDLE’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML. 
		
		 Use the AVAIL.entityid from step 1  and link them to the newly created LSEOBUNDLEs .
		
		The attributes of the AVAIL including default values are supplied via the XML shown in the SS. Use the derive COMNAME value based in the SS.
		
		The AVAIL’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML.
		
		
		3.	Create references to LSEOs
		For each <SEOELEMENT>, search for LSEO using <SEOID>  in the XML. If found, then create Relator LSEOBUNDLELSEO between the LSEOBUNDLE created in step 2 and the LSEO found via this search. If not found, fail the abr.

	 */
	protected void generateEntities(Vector availattcodeVct,
			Hashtable availattValtab, Element availElem, StringBuffer availCntySb)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException {
		EntityItem availitem = null;
//		1	<BUNDLELIST>		4	
		NodeList bundleList =availElem.getElementsByTagName("BUNDLELIST");//BUNDLELIST

		for (int f=0; f<bundleList.getLength(); f++){
			Node bundlelNode = bundleList.item(f);
			if (bundlelNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList bundleElems = bundlelNode.getChildNodes();
			//					<1..N>	<BUNDLEELELEMENT>		5	
			for(int g=0;g<bundleElems.getLength();g++){
				Node bundleelemNode = bundleElems.item(g);
				if(bundleelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element bundleElem = (Element)bundleelemNode;
				String bundleseoid = spstAbr.getNodeValue(bundleElem, "BDLSEOID", false);
				EntityItem wwseoitem = this.searchForWWSEO(bundleseoid);
				EntityItem lseoitem = this.searchForLSEO(bundleseoid);
				EntityItem bundle = this.searchForLSEOBUNDLE(bundleseoid);
				StringBuffer bundleCntySb = new StringBuffer();
//				Check if a WWSEO/LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID
				if(wwseoitem!=null){
					spstAbr.addError("ERROR_LSEOBUNDLE_EXIST_WWSEO",new String[]{bundleseoid,bundleseoid});
					continue;
				}
				if(lseoitem!=null){
					spstAbr.addError("ERROR_LSEOBUNDLE_EXIST_LSEO",new String[]{bundleseoid,bundleseoid});
					continue;
				}//TOOD check wwseo & lseo seoid  = bundle seoid
				if(bundle !=null){					
					if(!isValidNewAvail(bundle,(Vector) availattValtab.get("COUNTRYLIST"), "146")){
						spstAbr.addError("ERROR_LSEOBUNDLE_EXIST",new String[]{bundleseoid});
						continue;
					}else{
						spstAbr.addOutput("LINK_BUNDLE",new String[]{bundleseoid,(String)availattValtab.get("COMNAME"),availCntySb.toString()});
					}

				}else{
					Vector bundleattcodeVct = new Vector();
					Hashtable bundleattValtab = new Hashtable();
					
	//				1	<BDLSEOID>	</BDLSEOID>	6	Bundle SEOID		LSEOBUNDLE	SEOID
					bundleattcodeVct.addElement("SEOID");				
	
					bundleattValtab.put("SEOID",bundleseoid);
					bundleattcodeVct.addElement("COMNAME");
					bundleattValtab.put("COMNAME",bundleseoid);
	//				1	<BUNDLMKTGDESC>	</BUNDLMKTGDESC>	6	Marketing Description		LSEOBUNDLE	MKTGNAME
					bundleattcodeVct.addElement("BUNDLMKTGDESC");				
					String mktgname = spstAbr.getNodeValue(bundleElem, "BUNDLMKTGDESC", false);
					if(mktgname != null && mktgname.length()>254){
						spstAbr.addError("BUNDLMKTGDESC's length > 254, failed this BUNDLE: " + mktgname);
						continue;
					}
					bundleattValtab.put("BUNDLMKTGDESC","LSEOBUNDLE MARKET temp");				
	//				1	<BHACCTASGNGRP>	</BHACCTASGNGRP>	6	BH AAG		LSEOBUNDLE	BHACCTASGNGRP
	
					bundleattcodeVct.addElement("BHACCTASGNGRP");				
					bundleattValtab.put("BHACCTASGNGRP",getSingleFlag(bundleElem, "BHACCTASGNGRP"));
	//				1	<ACCTASGNGRP>	</ACCTASGNGRP>	6	AAG		LSEOBUNDLE	ACCTASGNGRP
					bundleattcodeVct.addElement("ACCTASGNGRP");				
					bundleattValtab.put("ACCTASGNGRP",getSingleFlag(bundleElem, "ACCTASGNGRP"));
	//				1	<PRFTCTR>	</PRFTCTR>	6	Profit Center		LSEOBUNDLE	PRFTCTR
					bundleattcodeVct.addElement("PRFTCTR");				
					bundleattValtab.put("PRFTCTR",getSingleFlag(bundleElem, "PRFTCTR"));
	//				1	<SAPASSORTMODULE>	</SAPASSORTMODULE>	6	SEO ASM		LSEOBUNDLE	SAPASSORTMODULE
					bundleattcodeVct.addElement("SAPASSORTMODULE");				
					bundleattValtab.put("SAPASSORTMODULE",spstAbr.getNodeValue(bundleElem, "SAPASSORTMODULE", false));
	//				1	<PRCFILENAM>	</PRCFILENAM>	6	Part Description		LSEOBUNDLE	PRCFILENAM
					bundleattcodeVct.addElement("PRCFILENAM");				
					bundleattValtab.put("PRCFILENAM",spstAbr.getNodeValue(bundleElem, "PRCFILENAM", false));
	//				1	<BUNDLPUBDATEMTRGT>	</BUNDLPUBDATEMTRGT>	6	Announcement date		LSEOBUNDLE	BUNDLPUBDATEMTRGT
					bundleattcodeVct.addElement("BUNDLPUBDATEMTRGT");				
					bundleattValtab.put("BUNDLPUBDATEMTRGT",spstAbr.getNodeValue(bundleElem, "BUNDLPUBDATEMTRGT", false));
	//				1	<BUNDLUNPUBDATEMTRGT>	</BUNDLUNPUBDATEMTRGT>	6	Withdraw date		LSEOBUNDLE	BUNDLUNPUBDATEMTRGT
					bundleattcodeVct.addElement("BUNDLUNPUBDATEMTRGT");				
					bundleattValtab.put("BUNDLUNPUBDATEMTRGT",spstAbr.getNodeValue(bundleElem, "BUNDLUNPUBDATEMTRGT", false));
	//				1	<PRODHIERCD>	</PRODHIERCD>	6	Product Hierarchy		LSEOBUNDLE	PRODHIERCD
					bundleattcodeVct.addElement("PRODHIERCD");				
					bundleattValtab.put("PRODHIERCD",spstAbr.getNodeValue(bundleElem, "PRODHIERCD", false));
	//				1	<SEOBHPRODHIERCD>	</SEOBHPRODHIERCD>	6	SEO Product Hierarchy		LSEOBUNDLE	BHPRODHIERCD
					bundleattcodeVct.addElement("BHPRODHIERCD");				
					bundleattValtab.put("BHPRODHIERCD",getSingleFlag(bundleElem, "SEOBHPRODHIERCD"));
					
	//				1	<AUDLIST>		6				
	//				<1..N>	<AUDIENCE>	</AUDIENCE>	7	MTM Audience		MODEL 	AUDIEN
	//						</AUDLIST>	6				
	//				NodeList audList = bundleElem.getElementsByTagName("AUDLIST");						
					bundleattcodeVct.addElement("AUDIEN");
					bundleattValtab.put("AUDIEN",generateMultiFlags(bundleElem, "AUDLIST","AUDIENCE",new StringBuffer()));
	//				1	<COUNTRYLIST>		6				
	//				<1..N>	<COUNTRY>	</COUNTRY>	7	Countr(ies)		LSEOBUNDLE	COUNTRYLIST
	//						</COUNTRYLIST>	6		
	//				NodeList bundlecntyList = bundleElem.getElementsByTagName("COUNTRYLIST");
//					StringBuffer bundleCntySb = new StringBuffer();
					Vector bundleCntyV = generateMultiFlags(bundleElem,"COUNTRYLIST","COUNTRY",bundleCntySb);
					bundleattcodeVct.addElement("COUNTRYLIST");
					bundleattValtab.put("COUNTRYLIST",bundleCntyV);
					
					bundle = this.createLSEOBUNDLE(spstAbr, bundleattcodeVct, bundleattValtab);
					spstAbr.setTextValue(spstAbr.getProfile(), "BUNDLMKTGDESC", mktgname, bundle);//need to bypass the rule。
					spstAbr.updatePDH(spstAbr.getProfile());
					bundleattcodeVct.clear();
					bundleattValtab.clear();
					bundleattcodeVct = null;
					bundleattValtab = null;
				}
				LinkActionItem lai = new LinkActionItem(null,spstAbr.getDatabase(),spstAbr.getProfile(),LSEOBUNDLEAVAIL_LINK_ACTION);
				availitem = this.createOrRefAvail(bundle, availitem, LSEOBUNDLEAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, lai);
				NodeList seoElems = bundleElem.getElementsByTagName("SEOLIST");//checked for wwseo&lseo based on seoid
				StringBuffer seosb = new StringBuffer();
				for(int h=0;h<seoElems.getLength();h++){
					Node seoNode = seoElems.item(h);
					if(seoNode.getNodeType()!=Node.ELEMENT_NODE){
						continue;
					}
					Element seoElem = (Element)seoNode;
					NodeList seoidList = seoElem.getChildNodes();
					for (int i = 0; i < seoidList.getLength(); i++) {//SEOID
						Node seoidnode = seoidList.item(i);
						if(seoidnode.getNodeType()!=Node.ELEMENT_NODE){
							continue;
						}						
						String seoid = spstAbr.getNodeValue((Element)seoidnode, "SEOID", false);						
						EntityItem lseo = this.searchForLSEO(seoid);
//						For each <SEOELEMENT>, search for LSEO using <SEOID>  in the XML. 
//						If found, then create Relator LSEOBUNDLELSEO between the LSEOBUNDLE created in step 2 
//						and the LSEO found via this search. 
//						If not found, then  fail the abr.
						EntityItem lseoItem = searchForLSEO(seoid);
						if(lseoItem ==null){
							spstAbr.addError("ERROR_LSEO_NOT_FOUND",new Object[]{seoid});
							continue;//TODO make sure if we will skip it in the futher, Need Linda's confirmation
						}else{
							seosb.append(" " + seoid +" ");
							LinkActionItem bundlelseolink = new LinkActionItem(null, spstAbr.getDatabase(),spstAbr.getProfile(),LSEOBUNDLELSEO_LINK_ACTION);
							doReference(bundle, bundlelseolink, lseo);
//							LSEOBUNDLELSEO                   LSEOQTY
//							we have to set LSEOQTY to 1 on LSEOBUNDLELSEO, doReference can do it
						}
						lseo = null;
					}
				}
				wwseoitem = null;
				lseoitem = null;
				bundle = null;
//				CREATED_LSEOBUNDLE = AVAIL - {0} created for <br />  &nbsp;&nbsp;&nbsp;Country:{1}<br />&nbsp;&nbsp;&nbsp;And linked to New LSEOBUNDLE<br />&nbsp;&nbsp;&nbsp;
//				LSEOBUNDLE - {2} created for <br />  &nbsp;&nbsp;&nbsp;Country:{3}<br /> with LSEO - {4} referenced
				if(bundleCntySb.toString().length() > 0)
					spstAbr.addOutput("CREATED_LSEOBUNDLE", new String[]{(String)availattValtab.get("COMNAME"),availCntySb.toString(),
							bundleseoid,bundleCntySb.toString(),
							seosb.toString()});
			}					
		}		
		availitem = null;
	}
}
