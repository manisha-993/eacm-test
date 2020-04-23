//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.SBRException;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/**********************************************************************************
 * From "SG FS Inbound Feed Leads 20090423.doc"
 * 
 * Card	XML_Begin	XML_End					Level
 * 1	<?XML VERSION="1.0" ENCODING="UTF-8"?>	1
 * 1	<LEADSADDBUNDLE>						1
 * 1		<DTSOFDATA>	</DTSOFDATA>			2
 * 1		<FACTSHEETNUM>	</FACTSHEETNUM>		2
 * 1		<BDLSEOID>	</BDLSEOID>				2
 * 1		<BUNDLMKTGDESC>	</BUNDLMKTGDESC>	2
 * 1		<BUNDLPUBDATEMTRGT>	</BUNDLPUBDATEMTRGT>	2
 * 1		<COUNTRYLIST>						2
 * <1..N>		<COUNTRYELEMENT>				3
 * 1				<COUNTRY>	</COUNTRY>		4
 * 				</COUNTRYELEMENT>				3
 * 			</COUNTRYLIST>						2
 * 1		<SEOLIST>							2
 * <2..N>		<SEOELEMENT>					3
 * 1				<SEOID>	</SEOID>			4
 * 1				<MT>	</MT>				4
 * 1				<MODEL>	</MODEL>			4
 * 1				<SEOTECHDESC>	</SEOTECHDESC>	4
 * 1				<PUBFROM>	</PUBFROM>		4
 * 1				<FEATURELIST>				4
 * <1..N>				<FEATUREELEMENT>		5
 * 1						<FEATURECODE>	</FEATURECODE>	6
 * 1						<QTY>	</QTY>		6
 * 						</FEATUREELEMENT>		5
 * 					</FEATURELIST>				4
 * 1				<COUNTRYLIST>				4
 * <1..N>				<COUNTRYELEMENT>		5
 * 1						<COUNTRY>	</COUNTRY>	6
 * 						</COUNTRYELEMENT>		5
 * 					</COUNTRYLIST>				4
 * 				</SEOELEMENT>					3
 * 			</SEOLIST>							2
 * 		</LEADSADDBUNDLE>						1
 * 
 * VII.	<LEADSADDBUNDLE>

The attached spreadsheet has the definition of this XML message on the tab named "SG_XML_Bundle".

1.	Search for LSEOBUNDLE using:
•	<SEOID>

If found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
LSEOBUNDLE = <BDLSEOID>
Message = "LSEOBUNDLE already exists"
LXABRSTATUS = "Failed"

2.	Create LSEOBUNDLE
The attributes of the LSEOBUNDLE are supplied via the XML shown in the SS on the SG_XML_Bundle 
tab with details provided on the SG Notes tab.

The LSEOBUNDLE’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  Id = 9.

3.	Create references to LSEOs
For each <SEOELEMENT>, search for LSEO using <SEOID> Id 15 in the XML. If found, then create 
Relator LSEOBUNDLELSEO between the LSEOBUNDLE created in step 2 and the LSEO found via this search. 
If not found, then create the LSEO as described in the prior section for <LEADSADDSEO>. 
If a feature is not found, then the error message needs to include the LSEOBUNDLE identification. For example:
Fact Sheet = <FACTSHEETNUM>
Lseobundle = <BDLSEOID>
Machine Type = <MT>
Model = <MODEL>
SEO = <SEOID>
Feature Code = <FEATURECODE>
Message = "TMF does not exist"
LXABRSTATUS = "Failed"

 
4.	Create Report
A report is created and submitted to Subscription/Notification as follows:

LSEOBUNDLE Special Bid from LEADS created (LXABRSTATUS)

Userid:
Role:
Workgroup:
Date:
Description: Special Bid Created
Return code:

<BDLSEOID> created for
<COUNTRY>

with

<SEOID> referenced
"logical or" 
<SEOID> created for <MT> <MODEL>
<FEATURECODE> <QTY>
for
<COUNTRY>
 */
//LXLEADSADDBUNDLEABR.java,v
//Revision 1.11  2010/03/01 17:15:32  wendy
//Expanded to support Blue Harmony (BH) IDLs.
//
//Revision 1.10  2009/05/20 19:09:47  wendy
//spec updates
//
//Revision 1.9  2009/05/06 12:38:52  wendy
//RCQ00028947 - LEADS to EACM Feed - Update Mapping for Audience, Rate Card Code, UNSPSC Code
//RCQ00029975 - LEADS inbound feed Workgroup Selection
//
//Revision 1.8  2009/04/20 18:40:31  wendy
//Use VE instead of search to improve performance
//
//Revision 1.7  2009/04/09 18:14:28  wendy
//Add check for existing LSEO with bundle SEOID
//
//Revision 1.6  2009/04/09 15:24:28  wendy
//Enhance error msgs
//
public class LXLEADSADDBUNDLEABR implements LXABRInterface {
	private static final String LSEOBDL_SRCHACTION_NAME = "LDSRDLSEOBUNDLE";//"SRDLSEOBUNDLE1";
	private static final String LSEOBDL_CREATEACTION_NAME = "LDCRLSEOBUNDLE";//"CRLSEOBUNDLE"; 
	private static final String LSEOBDL_LINKACTION_NAME = "LDLINKLSEOLSEOB";//"LINKLSEOLSEOBUNDLE";
	
	private LXABRSTATUS lxAbr=null;
	private String dtsOfData=null;
	private Element rootElem = null;
	private String factSheetNum = null;
	private String bdlseoid = null;
	private String bdlPubfrom =null;

	private String bdlMktgDesc = null;
	private EntityItem lseobdlItem = null;

	private Hashtable tmfTbl = new Hashtable(); //  mt+":"+model+":"+seoid is key, vector of ps is value
	private Hashtable psQtyTbl = new Hashtable(); // ps.key is key, qty is value
	private Hashtable mdlTbl = new Hashtable(); // mt:model is key, modelitem is value
	private Hashtable ctryTbl = new Hashtable(); //<COUNTRY> is key, COUNTRYLIST flagcode is value
	private Object domain = null; // must be derived from MODELs
	private String domainStr = null;
	private Object audien = null;
	
	/**********************************************************************************
	 * Make sure the xml has the specified set of tags
	 * 
	 * Card	XML_Begin	XML_End					Level
	 * 1	<?XML VERSION="1.0" ENCODING="UTF-8"?>	1
	 * 1	<LEADSADDBUNDLE>						1
	 * 1		<DTSOFDATA>	</DTSOFDATA>			2
	 * 1		<FACTSHEETNUM>	</FACTSHEETNUM>		2
	 * 1		<BDLSEOID>	</BDLSEOID>				2
	 * 1		<BUNDLMKTGDESC>	</BUNDLMKTGDESC>	2
	 * 1		<BUNDLPUBDATEMTRGT>	</BUNDLPUBDATEMTRGT>	2
	 * 1		<COUNTRYLIST>						2
	 * <1..N>		<COUNTRYELEMENT>				3
	 * 1				<COUNTRY>	</COUNTRY>		4
	 * 				</COUNTRYELEMENT>				3
	 * 			</COUNTRYLIST>						2
	 * 1		<SEOLIST>							2
	 * <2..N>		<SEOELEMENT>					3
	 * 1				<SEOID>	</SEOID>			4
	 * 1				<MT>	</MT>				4
	 * 1				<MODEL>	</MODEL>			4
	 * 1				<SEOTECHDESC>	</SEOTECHDESC>	4
	 * 1				<PUBFROM>	</PUBFROM>		4
	 * 1				<FEATURELIST>				4
	 * <1..N>				<FEATUREELEMENT>		5
	 * 1						<FEATURECODE>	</FEATURECODE>	6
	 * 1						<QTY>	</QTY>		6
	 * 						</FEATUREELEMENT>		5
	 * 					</FEATURELIST>				4
	 * 1				<COUNTRYLIST>				4
	 * <1..N>				<COUNTRYELEMENT>		5
	 * 1						<COUNTRY>	</COUNTRY>	6
	 * 						</COUNTRYELEMENT>		5
	 * 					</COUNTRYLIST>				4
	 * 				</SEOELEMENT>					3
	 * 			</SEOLIST>							2
	 * 		</LEADSADDBUNDLE>						1 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private void validateXML() throws MiddlewareRequestException, SQLException, 
		MiddlewareException, MiddlewareShutdownInProgressException 
	{
		// check for specific elements
		dtsOfData = lxAbr.getNodeValue(rootElem,"DTSOFDATA");
		factSheetNum = lxAbr.getNodeValue(rootElem,"FACTSHEETNUM");
		bdlseoid = lxAbr.getNodeValue(rootElem,"BDLSEOID");
		bdlPubfrom = lxAbr.getNodeValue(rootElem,"BUNDLPUBDATEMTRGT","");
		bdlMktgDesc = lxAbr.getNodeValue(rootElem,"BUNDLMKTGDESC","");	
		// derive audience value
		audien = LXLEADSUtil.deriveAudien(lxAbr.getNodeValue(rootElem,"ACCOUNTTYPE",""));
		// build countrylist vector
		//1	<COUNTRYLIST>	
		//<1..N>	<COUNTRYELEMENT>	
		//1				<COUNTRY>	</COUNTRY>
		//			</COUNTRYELEMENT>
		//	</COUNTRYLIST>
		
		NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
		if (ctrylist==null){
			// ERROR_MISSINGXML = Missing xml tag {0}.
			lxAbr.addError("ERROR_MISSINGXML", "COUNTRYLIST");
		}else{
			lxAbr.verifyChildNodes(ctrylist, "COUNTRYLIST", "COUNTRYELEMENT", 1);
			
			Element ctryListElem = null;
			for (int x=0; x<ctrylist.getLength(); x++){
				Node node = ctrylist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				Element elem = (Element)ctrylist.item(x);
				if ((elem.getParentNode()==rootElem)){
					if (ctryListElem !=null){ // must be only one at this level
						//ERROR_INVALIDXML = Invalid xml tag: {0}.
						lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
						break;
					}else{
						ctryListElem = (Element)ctrylist.item(x);
					}
				}
				// check each country
				findCountry(elem);
			}	

			if (ctryListElem == null){
				// ERROR_MISSINGXML = Missing xml tag {0}.
				lxAbr.addError("ERROR_MISSINGXML", "COUNTRYLIST");
			}
		}
		
		NodeList seolist = rootElem.getElementsByTagName("SEOLIST");

		//ERROR_INVALIDXML = Invalid xml tag: {0}.
		if (seolist==null || seolist.getLength()!=1){
			lxAbr.addError("ERROR_INVALIDXML", "SEOLIST");
		}else {
			lxAbr.verifyChildNodes(seolist, "SEOLIST", "SEOELEMENT", 2);
		}
	}

	/***********************************************
	 *  Get the header
	 *
	 *@return java.lang.String
	 */
	public String getHeader(){
		if (dtsOfData==null||factSheetNum==null||bdlseoid==null){
			return "";
		}
		// LSEOBDL_HEADER = Date/Time = {0}<br />Fact Sheet = {1}<br />Lseobundle = {2}
		String header = lxAbr.getResourceMsg("LSEOBDL_HEADER", new Object[]{dtsOfData,factSheetNum,bdlseoid});
		if (domainStr!= null){
			header = header+"<br />Domain = "+domainStr;
		}
		return header;
	}	
	/*********************
	 * find the country and add flagcode for COUNTRYLIST to the hashtable
	 * COUNTRY is a 2 character Country Code found in GENERALAREA.GENAREACODE and used to get 
	 * GENAREANAME which is the same as COUNTRYLIST
	 * 
	 * @param elem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void findCountry(Element elem) throws MiddlewareRequestException, 
		SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		// check each country
		NodeList ctryelemlist = elem.getChildNodes(); // get COUNTRYELEMENT
		for (int e=0; e<ctryelemlist.getLength(); e++){	
			Node node = ctryelemlist.item(e);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			Element ctryElem = (Element)ctryelemlist.item(e);
			String ctryDesc = lxAbr.getNodeValue(ctryElem, "COUNTRY");
			if (ctryDesc != null){
				lxAbr.addDebug("findCountry checking "+ctryDesc);
				String ctryflagcode = (String)ctryTbl.get(ctryDesc);
				if (ctryflagcode==null){
					StringBuffer debugSb = new StringBuffer();
					// GENAREACODE
					EntityItem genarea = LXLEADSUtil.searchForGENERALAREA(lxAbr.getDatabase(), 
							lxAbr.getProfile(), ctryDesc,debugSb);
					if (debugSb.length()>0){
						lxAbr.addDebug(debugSb.toString());
					}
					if (genarea!= null){
						// get genareaname attr flag value
						ctryflagcode = PokUtils.getAttributeFlagValue(genarea, "GENAREANAME");
						if (ctryflagcode!= null) {
							ctryTbl.put(ctryDesc, ctryflagcode);
						}else{
							String seoid = bdlseoid;
							if ((elem.getParentNode()!=rootElem)){
								// get seoid
								seoid= lxAbr.getNodeValue((Element)elem.getParentNode(), "SEOID");
							}

							//ERROR_SEO_COUNTRY = SEO= {0} <br />Country = {1}<br />Country Code not found.
							lxAbr.addError("ERROR_SEO_COUNTRY",new Object[]{seoid,ctryDesc});
						}
					}else{
						lxAbr.addError("GENERALAREA item not found for COUNTRY: "+ctryDesc);
					}	
				}				
			}
		}
	}
	/******************************************************
	 * release memory
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#dereference()
	 */
	public void dereference(){
		lxAbr = null;
		dtsOfData = null;
		rootElem = null;
		factSheetNum = null;
		bdlseoid=null;
		bdlPubfrom=null;
		bdlMktgDesc = null;
		if (lseobdlItem!= null){
			lseobdlItem.getEntityGroup().getEntityList().dereference();
			lseobdlItem = null;
		}

		if (tmfTbl != null){
			for (Enumeration eNum = tmfTbl.elements(); eNum.hasMoreElements();)  {
				Vector tmfVct = (Vector)eNum.nextElement();
				for (int i=0; i<tmfVct.size(); i++){
					EntityItem ei = (EntityItem)tmfVct.elementAt(i);
					try{
						EntityList list = ei.getEntityGroup().getEntityList();
						if (list!=null){ // may have already been deref
							list.dereference();
						}
					}catch(Exception e){}
				}	
				tmfVct.clear();
			}
			tmfTbl.clear();
			tmfTbl = null;
		}

		psQtyTbl.clear();
		psQtyTbl = null;
		
		ctryTbl.clear();
		ctryTbl = null;
		mdlTbl.clear();
	}

	/***********************
	 * 1.	Search for LSEOBUNDLE using:
	 * 			<BDLSEOID>
	 * 
	 * If found, then this is an error:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM>
	 * LSEOBUNDLE = <BDLSEOID>
	 * Message = 'LSEOBUNDLE already exists'
	 * LXABRSTATUS = 'Failed'
	 * 
	 * Search for Model using mt and model, 
	 * search for prodstructs using featurecode, mt and model , 
	 * 
	 * If not found, then this is an error:
	 * 	Date/Time = <DTSOFDATA>
	 * 	Fact Sheet = <FACTSHEETNUM>
	 * Lseobundle = <BDLSEOID>
	 * Machine Type = <MT>
	 * Model = <MODEL>
	 * SEO = <SEOID>
	 * Feature Code = <FEATURECODE>
	 * Message = "TMF does not exist"
	 * LXABRSTATUS = "Failed"
	 * 
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#validateData(COM.ibm.eannounce.abr.sg.LXABRSTATUS, org.w3c.dom.Element)
	 */
	public void validateData(LXABRSTATUS theAbr, Element root) throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException 
	{
		lxAbr = theAbr;
		rootElem = root;
		
		validateXML();
		
		if (lxAbr.getReturnCode()!= PokBaseABR.PASS){
			return;
		}		
		// search for entities
		searchForLseoBundle();

		if (PokBaseABR.PASS==lxAbr.getReturnCode()){ // no errors yet
			// loop thru each SEOELEMENT
			NodeList seolist = rootElem.getElementsByTagName("SEOLIST");
			for (int x=0; x<seolist.getLength(); x++){
				Node node = seolist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("SEOLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList seoelemlist = seolist.item(x).getChildNodes(); // SEOELEMENT
				for (int e=0; e<seoelemlist.getLength(); e++){		
					node = seoelemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("SEOELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element seoElem = (Element)seoelemlist.item(e);
					String mt = lxAbr.getNodeValue(seoElem,"MT");
					String model = lxAbr.getNodeValue(seoElem,"MODEL");	
					String seoid = lxAbr.getNodeValue(seoElem,"SEOID");
					lxAbr.addDebug("validateData looking for seoElem["+e+"] seoid: "+seoid+" mt: "+mt+" model: "+model);
					// get all feature codes
					NodeList flist = seoElem.getElementsByTagName("FEATURELIST");
					//ERROR_INVALIDXML = Invalid xml tag: {0}.
					if (flist==null || flist.getLength()!=1){
						lxAbr.addError("ERROR_INVALIDXML", "FEATURELIST");
					}else{
						lxAbr.verifyChildNodes(flist, "FEATURELIST", "FEATUREELEMENT", 1);
						EntityItem modelItem  = LXLEADSUtil.searchForModel(lxAbr,mt, model);
						if (modelItem != null){
							// find MODEL, determine what cofcat is
							// search for TMF using this info
							//searchForProdstructs(mt, model,seoid, modelItem,flist);
							matchProdstructs(mt, model,seoid, modelItem,flist);
							mdlTbl.put(mt+":"+model, modelItem);
						}else{
							//ERROR_LSEOBDL_MODEL = Machine Type = {0}<br />Model = {1}<br />MODEL does not exist.
							lxAbr.addError("ERROR_LSEOBDL_MODEL", new Object[]{mt,model});
						}
					}
				}
			}
		}
	}
	/****************
	 * 1.	Search for LSEOBUNDLE using:
	 * 			<BDLSEOID>
	 * If found, then this is an error:
	 * 	Date/Time = <DTSOFDATA>
	 * 	Fact Sheet = <FACTSHEETNUM>
	 * 	LSEOBUNDLE = <BDLSEOID>
	 * 	Message = 'LSEOBUNDLE already exists'
	 * 	LXABRSTATUS = 'Failed' 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void searchForLseoBundle() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(bdlseoid);
		
		EntityItem eia[]=null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					LSEOBDL_SRCHACTION_NAME, "LSEOBUNDLE", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForLseoBundle SBRException: "+exBuf.getBuffer().toString());
		}
		
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("searchForLseoBundle found "+eia[i].getKey());
			}
			//ERROR_LSEOBDL = LSEOBUNDLE already exists.
			lxAbr.addError("ERROR_LSEOBDL",null);
		}else{
			lxAbr.addDebug("searchForLseoBundle NO LSEOBUNDLE found for "+bdlseoid);
			EntityItem lseo = LXLEADSUtil.searchForLSEO(lxAbr, bdlseoid);
			if (lseo != null){
				lxAbr.addDebug("searchForLseo found "+lseo.getKey());
				// ERROR_LSEO_LSEOBDL = LSEO already exists for {0}.  LSEOBUNDLE can not be created with this seoid.
				lxAbr.addError("ERROR_LSEO_LSEOBDL", bdlseoid);
			}else{
				lxAbr.addDebug("searchForLseoBundle NO LSEO found for bundleseoid: "+bdlseoid);
			}
		}
		
		attrVct.clear();
		valVct.clear();
	}

	/***********************
	 * search for prodstructs using featurecode, mt and model
	 * 
	 * If not found, then this is an error:
	 * 	Date/Time = <DTSOFDATA>
	 * 	Fact Sheet = <FACTSHEETNUM>
	 * Lseobundle = <BDLSEOID>
	 * Machine Type = <MT>
	 * Model = <MODEL>
	 * SEO = <SEOID>
	 * Feature Code = <FEATURECODE>
	 * Message = "TMF does not exist"
	 * LXABRSTATUS = "Failed"
	 * 
	 * @param mt
	 * @param model
	 * @param seoid
	 * @param modelItem
	 * @param flist
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * /
	private void searchForProdstructs(String mt, String model, String seoid, 
			EntityItem modelItem, NodeList flist) throws SQLException, MiddlewareException, 
			MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(3);
		attrVct.addElement("MODEL:MACHTYPEATR");
		attrVct.addElement("MODEL:MODELATR");
		
		Vector valVct = new Vector(2);
		valVct.addElement(mt);
		valVct.addElement(model);

		String searchAction = "";
		String searchType = "";
		String cofcat = PokUtils.getAttributeValue(modelItem, "COFCAT", "", "",false);
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
		lxAbr.addDebug("searchForProdstructs using "+modelItem.getKey()+" cofcatflag:"+cofcatflag);
		
		if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
			searchAction = LXLEADSUtil.PS_SRCHACTION_NAME;
			searchType = "PRODSTRUCT";
			attrVct.addElement("FEATURE:FEATURECODE");
		}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
			searchAction = LXLEADSUtil.SWPS_SRCHACTION_NAME;
			searchType = "SWPRODSTRUCT";	
			attrVct.addElement("SWFEATURE:FEATURECODE");
		}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
			lxAbr.addDebug("searchForProdstructs Model is Service, no references needed");
			attrVct.clear();
			valVct.clear();
			return;
		}
		for (int x=0; x<flist.getLength(); x++){
			Node node = flist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("FEATURELIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			NodeList felemlist = flist.item(x).getChildNodes(); // FEATUREELEMENTS
			for (int e=0; e<felemlist.getLength(); e++){
				node = felemlist.item(e);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("FEATUREELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				Element fcElem = (Element)felemlist.item(e);
				String fcode = lxAbr.getNodeValue(fcElem, "FEATURECODE");
				String qty = lxAbr.getNodeValue(fcElem, "QTY","1");
				if (fcode!= null){
					Vector valTmp = new Vector(valVct);
					valTmp.add(fcode);

					EntityItem eia[]= null;
					try{
						// srch should be domain restricted!!
						StringBuffer debugSb = new StringBuffer();
						eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
								searchAction, searchType, true, attrVct, valTmp, debugSb);
						if (debugSb.length()>0){
							lxAbr.addDebug(debugSb.toString());
						}
					}catch(SBRException exc){
						// these exceptions are for missing flagcodes or failed business rules, dont pass back
						java.io.StringWriter exBuf = new java.io.StringWriter();
						exc.printStackTrace(new java.io.PrintWriter(exBuf));
						lxAbr.addDebug("searchForProdstructs SBRException: "+exBuf.getBuffer().toString());
					}

					valTmp.clear();

					if (eia==null || eia.length == 0){
						//ERROR_LSEOBDL_TMF = Machine Type = {0}<br />{1} Model = {2}<br />SEO = {3}<br />Feature Code = {4}<br />TMF does not exist.
						lxAbr.addError("ERROR_LSEOBDL_TMF", new Object[]{mt, cofcat, model, seoid, fcode});
					}else{
						String key = mt+":"+model+":"+seoid;
						Vector tmfVct = (Vector)tmfTbl.get(key);
						if (tmfVct==null){
							tmfVct = new Vector();
							tmfTbl.put(key, tmfVct);
						}

						for (int i=0; i<eia.length; i++){
							psQtyTbl.put(eia[i].getKey(),qty);
							tmfVct.addElement(eia[i]);
						}
						lxAbr.addDebug("searchForProdstructs found:\n"+PokUtils.outputList(eia[0].getEntityGroup().getEntityList()));
					}	
				} // end valid FEATURECODE and QTY
			} // end FEATUREELEMENTS loop
		}	// end FEATURELIST loop		
	}*/
	private void matchProdstructs(String mt, String model, String seoid, 
			EntityItem modelItem, NodeList flist) throws SQLException, MiddlewareException, 
			MiddlewareShutdownInProgressException
	{
		String vename = null;
		String featureType = "";
		String cofcat = PokUtils.getAttributeValue(modelItem, "COFCAT", "", "",false);
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
		lxAbr.addDebug("matchProdstructs using "+modelItem.getKey()+" cofcatflag:"+cofcatflag);
		
		if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
			featureType = "FEATURE";
			vename = "EXRPT3LEADS";
		}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
			featureType = "SWFEATURE";
			vename = "EXRPT3LEADS2";
		}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
			lxAbr.addDebug("matchProdstructs Model is Service, no references needed");
			return;
		}
		// pull VE to improve performance 
		EntityList psList = lxAbr.getDatabase().getEntityList(lxAbr.getProfile(), 
				new ExtractActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(), vename), 
				new EntityItem[]{modelItem});
		lxAbr.addDebug("matchProdstructs VE:"+vename+"\n"+PokUtils.outputList(psList));
		Hashtable fcTbl = new Hashtable();  // easy lookup
		EntityGroup featGrp = psList.getEntityGroup(featureType);
		for (int i=0; i<featGrp.getEntityItemCount(); i++){
			EntityItem featItem = featGrp.getEntityItem(i);
			String fc = PokUtils.getAttributeValue(featItem, "FEATURECODE", "", "",false);	
			Vector vct = featItem.getDownLink();
			for (int d=0; d<vct.size(); d++){
				lxAbr.addDebug("matchProdstructs featurecode: "+fc+" on "+featItem.getKey()+
					" dnlink["+d+"] "+featItem.getDownLink(d).getKey());
			}
			fcTbl.put(fc, vct);
		}
		
		for (int x=0; x<flist.getLength(); x++){
			Node node = flist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("FEATURELIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			NodeList felemlist = flist.item(x).getChildNodes(); // FEATUREELEMENTS
			for (int e=0; e<felemlist.getLength(); e++){
				node = felemlist.item(e);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("FEATUREELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				Element fcElem = (Element)felemlist.item(e);
				String fcode = lxAbr.getNodeValue(fcElem, "FEATURECODE");
				String qty = lxAbr.getNodeValue(fcElem, "QTY","1");
				if (fcode!= null){
					Vector dnlinks = (Vector)fcTbl.get(fcode);
					if (dnlinks==null || dnlinks.size() == 0){
						// ERROR_LSEOBDL_TMF = Machine Type = {0}<br />{1} Model = {2}<br />SEO = {3}<br />Feature Code = {4}<br />TMF does not exist.
						lxAbr.addError("ERROR_LSEOBDL_TMF", new Object[]{mt, cofcat, model, seoid, fcode});
					}else{
						String key = mt+":"+model+":"+seoid;
						Vector tmfVct = (Vector)tmfTbl.get(key);
						if (tmfVct==null){
							tmfVct = new Vector();
							tmfTbl.put(key, tmfVct);
						}						
						
						for (int i=0; i<dnlinks.size(); i++){
							EntityItem psitem = (EntityItem)dnlinks.elementAt(i);
							lxAbr.addDebug("matchProdstructs fc "+fcode+" using: "+psitem.getKey());
							psQtyTbl.put(psitem.getKey(),qty);
							tmfVct.add(psitem);
						}
					}	
				} // end valid FEATURECODE and QTY
			} // end FEATUREELEMENTS loop
		}	// end FEATURELIST loop		
	}
	/********************************************************
	2.	Create LSEOBUNDLE
	The attributes of the LSEOBUNDLE are supplied via the XML shown in the SS on the SG_XML_Bundle 
	tab with details provided on the SG Notes tab.

	The LSEOBUNDLE’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  Id = 9.

	3.	Create references to LSEOs
	For each <SEOELEMENT>, search for LSEO using <SEOID> Id 15 in the XML. If found, then create 
	Relator LSEOBUNDLELSEO between the LSEOBUNDLE created in step 2 and the LSEO found via this search. 
	If not found, then create the LSEO as described in the prior section for <LEADSADDSEO>. 
		 
	4.	Create Report
	A report is created and submitted to Subscription/Notification as follows:

	LSEOBUNDLE Special Bid from LEADS created (LXABRSTATUS)

	Userid:
	Role:
	Workgroup:
	Date:
	Description: Special Bid Created
	Return code:

	<BDLSEOID> created for
	<COUNTRY>

	with

	<SEOID> referenced
	"logical or" 
	<SEOID> created for <MT> <MODEL>
	<FEATURECODE> <QTY>
	for
	<COUNTRY>

	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#execute()
	 */
	public void execute() throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException 
	{
		// find the model to use for determining PDHDOMAIN
		derivePDHDOMAIN(); //	RCQ00029975
		if (lxAbr.getReturnCode()!=PokBaseABR.PASS){
			return;
		}
		
		// create the lseobundle
		createLSEOBDL();
		if (lseobdlItem != null){
			// loop thru each SEOELEMENT
			NodeList seolist = rootElem.getElementsByTagName("SEOLIST");
			Vector lseoVct = new Vector(1);
			StringBuffer lseoSb = new StringBuffer();
			StringBuffer lseo2link = new StringBuffer();

			outerloop:for (int x=0; x<seolist.getLength(); x++){
				Node node = seolist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("SEOLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList seoelemlist = seolist.item(x).getChildNodes(); // SEOELEMENT
				for (int e=0; e<seoelemlist.getLength(); e++){							
					if (lxAbr.getReturnCode()!=PokBaseABR.PASS){
						break outerloop;
					}
					node = seoelemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("SEOELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					StringBuffer sb = new StringBuffer();
					Element seoElem = (Element)seoelemlist.item(e);
					String mt = lxAbr.getNodeValue(seoElem,"MT");
					String model = lxAbr.getNodeValue(seoElem,"MODEL");	
					String seoid = lxAbr.getNodeValue(seoElem,"SEOID");
					// get MODEL
					EntityItem modelItem  = (EntityItem)mdlTbl.get(mt+":"+model);

					lxAbr.addDebug("execute looking at seoid["+e+"]: "+seoid+" mt: "+mt+" model: "+model+" "+modelItem.getKey());
					// search for LSEO, create if necessary
					EntityItem lseo = getLSEO(modelItem, seoElem, mt, model, seoid, sb);
					if (lseo==null){
						break outerloop;
					}
					lseoVct.add(lseo);
					lseo2link.append(" "+lseo.getKey());
					if (lseoSb.length()>0){
						lseoSb.append("<br />");
					}
					lseoSb.append(sb.toString());
				}
			}
			if (lxAbr.getReturnCode()==PokBaseABR.PASS){
				// link this lseo to the new lseobundle
				LinkActionItem lai = new LinkActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(),LSEOBDL_LINKACTION_NAME);
				EntityItem parentArray[] = new EntityItem[]{lseobdlItem};
				EntityItem childArray[] = new EntityItem[lseoVct.size()];

				// get each prodstruct
				lseoVct.copyInto(childArray);

				lxAbr.addDebug("Link "+lseobdlItem.getKey()+" to "+lseo2link.toString());
				// do the link	
				lai.setParentEntityItems(parentArray);     
				lai.setChildEntityItems(childArray);
				lxAbr.getDatabase().executeAction(lxAbr.getProfile(), lai);

				lseoVct.clear();

				if (lxAbr.getReturnCode()==PokBaseABR.PASS){
					// CREATED_SEOBDL_MSG = {0} {1} created for <br />{2} <br />with<br /> {3}
					StringBuffer sb = new StringBuffer();
					NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
					for (int x=0; x<ctrylist.getLength(); x++){
						Node node = ctrylist.item(x);
						if (node.getNodeType()!=Node.ELEMENT_NODE){
							//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
							continue;
						}
						Element elem = (Element)ctrylist.item(x);
						if ((elem.getParentNode()==rootElem)){
							Element	ctryListElem = (Element)ctrylist.item(x);
							NodeList ctryelemlist = ctryListElem.getChildNodes(); // COUNTRYELEMENT
							for (int e=0; e<ctryelemlist.getLength(); e++){	
								node = ctryelemlist.item(e);
								if (node.getNodeType()!=Node.ELEMENT_NODE){
									//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
									continue;
								}
								Element ctryElem = (Element)ctryelemlist.item(e);
								String ctryDesc = lxAbr.getNodeValue(ctryElem, "COUNTRY");
								sb.append(ctryDesc+" ");
							}
							break;
						}
					}	
					lxAbr.addOutput("CREATED_SEOBDL_MSG", new Object[]{
							lseobdlItem.getEntityGroup().getLongDescription(),
							bdlseoid,sb.toString(),lseoSb.toString()});
				}
			}
		}
	}
	/******
	 * 	RCQ00029975
	 * Find the pdhdomain to use from the first HW model in the XML, if no HW use first SW model
	 * For the CHW system, PDHDOMAIN is determined as follows:
1.	Search (restricted to PDHDOMAIN) for the Model using: 
•	MACHTYPEATR = <MT>
•	MODELATR = <MODEL>
2.	If more than one MODEL, then:
•	If COFCAT = Hardware (100), choose the one where COFGRP = Base (150)
b)	If COFCAT = Software(101), choose the one where COFGRP = Base (150) and 
i.	COFSUBCAT = HIPO (125) 
ii.	COFSUBCAT = Application (127)
iii.COFSUBCAT = Subscription (133)
c)	If COFCAT = Service (102), choose the first one.
3.	Use the PDHDOMAIN of this MODEL for all data created. 

Notes:
When processing XML <LEADSADDSEO>, only one MODEL should be found based on the criteria in step 2. There should be only one match or else there is an error. For example, only one of the criteria a) thru c) should find a match including criteria b) i. thru iii.

When processing XML <LEADSADDBUNDLE>, a <SEOELEMENT> should meet the same criteria as <LEADSADDSEO>. The priority of <SEOELEMENT> is:
1.	2.a)
2.	2.b).i
3.	2.b).ii
4.	2.b).iii
5.	2.c)

If more than one <SEOELEMENT> matches the same criterion, then choose the first. 


If there is an error, see the message found in the sections below when searching for MODEL.

priority =
1) Hardware
2) Software
3) Service
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	
	private void derivePDHDOMAIN() throws SQLException, MiddlewareException{
		// find the model to use for determining PDHDOMAIN and finding WG
		EntityItem hwMdlItem = null;
		Collection mdlCol = mdlTbl.values();
		
		Iterator itr = mdlCol.iterator();
		if (mdlCol.size()==1){
			hwMdlItem = (EntityItem) itr.next();
		}else{
			EntityItem swMdlItem = null;
			EntityItem svcMdlItem = null;
			while (itr.hasNext()) {
				EntityItem mdlitem = (EntityItem) itr.next();
				String cofcatflag = PokUtils.getAttributeFlagValue(mdlitem, "COFCAT");
				lxAbr.addDebug("derivePDHDOMAIN checking cofcat:"+cofcatflag+" "+mdlitem.getKey());
				// find hw model to use for PDHDOMAIN
				if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
					hwMdlItem = mdlitem;
					break;
				}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
					if (swMdlItem!=null){ //found one, see if this overrides it
						String cofsubcatflag = PokUtils.getAttributeFlagValue(mdlitem, "COFSUBCAT");
						String curcofsubcatflag = PokUtils.getAttributeFlagValue(swMdlItem, "COFSUBCAT");
						lxAbr.addDebug("derivePDHDOMAIN SW "+mdlitem.getKey()+" cofsubcatflag "+cofsubcatflag+
								" curSW "+swMdlItem.getKey()+" curcofsubcatflag "+curcofsubcatflag);
						if (LXLEADSUtil.SW_COFSUBCAT_SET.contains(curcofsubcatflag)){
							if (LXLEADSUtil.SW_COFSUBCAT_SET.contains(cofsubcatflag)){ // this one may override
								// priority happens to be in compare order
								//i.	COFSUBCAT = HIPO (125) 
								//ii.	COFSUBCAT = Application (127)
								//iii.	COFSUBCAT = Subscription (133)
								if (cofsubcatflag.compareTo(curcofsubcatflag)<0){
									lxAbr.addDebug("derivePDHDOMAIN SW "+mdlitem.getKey()+" overrides "+
											swMdlItem.getKey());
									swMdlItem = mdlitem;
								}
							}
						}else{ // current sw model was not in set so use this one.. it may not be in set either
							swMdlItem = mdlitem;
						}
					}else{
						swMdlItem = mdlitem;
					}			
				}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
					svcMdlItem = mdlitem;
				}
			}
	        
	        if(hwMdlItem==null){
	        	hwMdlItem = swMdlItem;
	        }
	        if(hwMdlItem==null){
	        	hwMdlItem = svcMdlItem;
	        }
		}
		
        // PDHDOMAIN must be derived from the MODEL
		EANAttribute attr = hwMdlItem.getAttribute("PDHDOMAIN");
		HashSet mdlSet = new HashSet();
        if (attr!=null){ // really should never happen
            EANMetaAttribute ma = attr.getMetaAttribute();
            String domainAttr = PokUtils.getAttributeFlagValue(hwMdlItem, "PDHDOMAIN");
            domainStr = PokUtils.getAttributeValue(hwMdlItem, "PDHDOMAIN", ", ", "", false);
            lxAbr.addDebug("derivePDHDOMAIN using "+hwMdlItem.getKey()+" PDHDOMAIN "+domainAttr);
            if (ma.getAttributeType().equals("U")){
            	domain = domainAttr;
            }else{ // must be F
                String[] domainArray = PokUtils.convertToArray(domainAttr);
                Vector tmp = new Vector(domainArray.length);
                for (int k=0; k<domainArray.length; k++){
                    tmp.add(domainArray[k]);
                    mdlSet.add(domainArray[k]);
                }
                domain = tmp;
            }
        }
	}
	/*****************************************************
	 * 2.	Create LSEOBUNDLE
	 * The attributes of the LSEOBUNDLE are supplied via the XML shown in the SS on the SG_XML_Bundle 
	tab with details provided on the SG Notes tab.

	The LSEOBUNDLE’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  Id = 9.
	 * ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
	 * COMNAME	T	Common Name	<SEOID>
	 * COUNTRYLIST	F	Country List	<COUNTRY>
	 * DATAQUALITY	S	DataQuality	Default
	 * PDHDOMAIN	F	Domains	Derived from MODEL
	 * SEOID	T	SEO ID	SEOID
	 * SPECBID	U	Special Bid	"Yes"
	 * TRANSLATIONWATCH	U	Translation Watch	Default
	 * BUNDLMKTGDESC	T	Bundle Marketing Description <BUNDLMKTGDESC>
	 * BUNDLPUBDATEMTRGT	T	Bundle Publish Date - Target <BUNDLPUBDATEMTRGT>
	 * PROJCDNAM	U	Project Code Name	"LEADS FEED SPECIAL BID"
	 * If the LEADSXML has a value for PROJCDNAM, then use it; otherwise, use the default specified in the SS.
	 * 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws RemoteException 
	 */
	private void createLSEOBDL() 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		// create the entity
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("SEOID");
		attrValTbl.put("SEOID", bdlseoid);
		attrCodeVct.addElement("COMNAME");
		attrValTbl.put("COMNAME", bdlseoid);  //COMNAME	T	Common Name	<SEOID>
		attrCodeVct.addElement("BUNDLPUBDATEMTRGT");
		attrValTbl.put("BUNDLPUBDATEMTRGT", bdlPubfrom);  //BUNDLPUBDATEMTRGT	<BUNDLPUBDATEMTRGT>
		attrCodeVct.addElement("BUNDLMKTGDESC");
		attrValTbl.put("BUNDLMKTGDESC", bdlMktgDesc);  //BUNDLMKTGDESC	<BUNDLMKTGDESC>		
			 
		// get the PROJCDNAM - expanded to support Blue Harmony (BH) IDLs.
		String prjname = "722";
		if(lxAbr.getPROJCDNAME() !=null){
			prjname = lxAbr.getPROJCDNAME();
		}
		
		// write the flags
		attrCodeVct.addElement("PROJCDNAM");
		attrValTbl.put("PROJCDNAM",prjname);//PROJCDNAM	U	Project Code Name	"LEADS FEED SPECIAL BID" =>'722'
		
		attrCodeVct.addElement("ACCTASGNGRP");
		attrValTbl.put("ACCTASGNGRP","01");//ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
		attrCodeVct.addElement("SPECBID");
		attrValTbl.put("SPECBID","11458");//SPECBID	U	Special Bid	"Yes" =>SPECBID	11458	Y	Yes
		attrCodeVct.addElement("AUDIEN");
		attrValTbl.put("AUDIEN",audien); //AUDIEN	F	Audience	"LE Direct"	flag code = 10062
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		attrValTbl.put("PDHDOMAIN", domain);
		attrCodeVct.addElement("COUNTRYLIST");
		Vector ctryVct = new Vector();

		NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
		for (int x=0; x<ctrylist.getLength(); x++){
			Node node = ctrylist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			Element elem = (Element)ctrylist.item(x);
			if ((elem.getParentNode()==rootElem)){
				Element	ctryListElem = (Element)ctrylist.item(x);
				NodeList ctryelemlist = ctryListElem.getChildNodes(); // COUNTRYELEMENT
				for (int e=0; e<ctryelemlist.getLength(); e++){	
					node = ctryelemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element ctryElem = (Element)ctryelemlist.item(e);
					String ctryDesc = lxAbr.getNodeValue(ctryElem, "COUNTRY");
					String ctryflagcode = (String)ctryTbl.get(ctryDesc);
					ctryVct.addElement(ctryflagcode);
				}
				break;
			}
		}	
		
		attrValTbl.put("COUNTRYLIST",ctryVct);//COUNTRYLIST	F	Country List	<COUNTRY>
		
		StringBuffer debugSb = new StringBuffer();
		try{
			// use WG but it will not be linked to this now
			EntityItem wgItem = new EntityItem(null, lxAbr.getProfile(), "WG", lxAbr.getProfile().getWGID());
			lseobdlItem = ABRUtil.createEntity(lxAbr.getDatabase(), lxAbr.getProfile(), LSEOBDL_CREATEACTION_NAME, wgItem,  
					"LSEOBUNDLE", attrCodeVct, attrValTbl, debugSb); 
		}catch(EANBusinessRuleException ere){
			throw ere;
		}finally{
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
			if (lseobdlItem==null){
				lxAbr.addError("ERROR: Can not create LSEOBUNDLE entity for seoid: "+bdlseoid);
			}	
			attrCodeVct.clear();
			attrValTbl.clear();
			ctryVct.clear();
		}
	}
	
	/*****************************************
	 * 3.	Search for LSEO using:
	 * -	<SEOID>
	 * If found, build msg and return.
	 * If not found, create LSEO, WWSEO and feature references, build msg and return.
	 * 
	 * 	<SEOID> referenced
	 * 'logical or'
	 * 	<SEOID> created for <MT> <MODEL>
	 * 	<FEATURECODE> <QTY>
	 * 	for <COUNTRY>
	 * 
	 * If <COUNTRY> is not found, then this is an error for the instance of <COUNTRYELEMENT>:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM>
	 * LSEOBUNDLE = <BDLSEOID>
	 * Country = <COUNTRY>
	 * Message = Country Code not found
	 * LXABRSTATUS = Failed
	 * 
	 * @param modelItem
	 * @param seoElem
	 * @param mt
	 * @param model
	 * @param seoid
	 * @param sb
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException
	 * @throws EANBusinessRuleException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private EntityItem getLSEO(EntityItem modelItem, Element seoElem, String mt, String model, 
			String seoid, StringBuffer sb) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,
	RemoteException, EANBusinessRuleException, LockException, WorkflowException
	{
		EntityItem lseo = LXLEADSUtil.searchForLSEO(lxAbr, seoid);
		if (lseo != null){
			// no other checks needed
			lxAbr.addDebug("getLSEO found "+lseo.getKey());
			// REF_SEO_MSG = {0} {1} referenced
			sb.append(lxAbr.getResourceMsg("REF_SEO_MSG", 
					new String []{lseo.getEntityGroup().getLongDescription(),seoid}));
		}else{
			// must create an lseo
			//find or create wwseo first and the feature references
			EntityItem wwseo = getWWSEO(modelItem, seoElem,seoid,mt,model,sb);
			if (wwseo != null){
				String pubfrom = lxAbr.getNodeValue(seoElem,"PUBFROM");
				NodeList ctrylist = seoElem.getElementsByTagName("COUNTRYLIST");
				// ERROR_INVALIDXML = Invalid xml tag: {0}.
				if (ctrylist==null || ctrylist.getLength()!=1){
					lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
				}

				if (lxAbr.getReturnCode()==PokBaseABR.PASS){
					Vector countryVct = new Vector(1);
					for (int x=0; x<ctrylist.getLength(); x++){	
						Node node = ctrylist.item(x);
						if (node.getNodeType()!=Node.ELEMENT_NODE){
							//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
							continue;
						}
						NodeList ctryelemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
						for (int e=0; e<ctryelemlist.getLength(); e++){		
							node = ctryelemlist.item(e);
							if (node.getNodeType()!=Node.ELEMENT_NODE){
								//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
								continue;
							}
							Element ctryElem = (Element)ctryelemlist.item(e);
							String ctryDesc = lxAbr.getNodeValue(ctryElem, "COUNTRY");
							String ctryflagcode = (String)ctryTbl.get(ctryDesc);
							countryVct.addElement(ctryflagcode);
						}
					}				

					lseo = LXLEADSUtil.createLSEO(lxAbr, wwseo, seoid, pubfrom, countryVct,domain, audien);

					if (lseo != null){
						lxAbr.addDebug("getLSEO created "+lseo.getKey());

						String cofcat = PokUtils.getAttributeValue(modelItem, "COFCAT", "", "",false);
						// CREATED_SEO_MSG = {0} {1} created for {2} Model: {3} {4}
						sb.append(lxAbr.getResourceMsg("CREATED_SEO_MSG", new Object[]{lseo.getEntityGroup().getLongDescription(),
								seoid,cofcat,mt,model}));
						
						String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
						if (!LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
							//	list all feature codes and qty
							NodeList flist = seoElem.getElementsByTagName("FEATURELIST");
							for (int x=0; x<flist.getLength(); x++){
								Node node = flist.item(x);
								if (node.getNodeType()!=Node.ELEMENT_NODE){
									//lxAbr.addDebug("FEATURELIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
									continue;
								}
								NodeList felemlist = flist.item(x).getChildNodes(); // FEATUREELEMENTS
								for (int e=0; e<felemlist.getLength(); e++){
									node = felemlist.item(e);
									if (node.getNodeType()!=Node.ELEMENT_NODE){
										//lxAbr.addDebug("FEATUREELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
										continue;
									}
									Element fcElem = (Element)felemlist.item(e);
									String fcode = lxAbr.getNodeValue(fcElem, "FEATURECODE");
									String qty = lxAbr.getNodeValue(fcElem, "QTY","1");
									//REF_FEATURE_MSG = <br /> Feature Code: {0} Quantity: {1} 
									sb.append(lxAbr.getResourceMsg("REF_FEATURE_MSG", new Object[]{fcode,qty}));
								} // end FEATUREELEMENTS loop
							}	// end FEATURELIST loop		
						}

						// REF_COUNTRY_MSG= <br />&nbsp;&nbsp;&nbsp;for Country:&nbsp;
						sb.append(lxAbr.getResourceMsg("REF_COUNTRY_MSG", null));
						for (int x=0; x<ctrylist.getLength(); x++){	
							Node node = ctrylist.item(x);
							if (node.getNodeType()!=Node.ELEMENT_NODE){
								//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
								continue;
							}
							NodeList ctryelemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
							for (int e=0; e<ctryelemlist.getLength(); e++){	
								node = ctryelemlist.item(e);
								if (node.getNodeType()!=Node.ELEMENT_NODE){
									//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
									continue;
								}
								Element ctryElem = (Element)ctryelemlist.item(e);
								String ctryDesc = lxAbr.getNodeValue(ctryElem, "COUNTRY");
								sb.append(ctryDesc+" ");
							}
						}		

						countryVct.clear();		
					}
				} // end all ok so far
			} // end wwseo != null
		}
	
		return lseo;
	}
	/*****************************************
	 * 3.	Search for WWSEO using:
	 * -	<SEOID>
	 * 
	 * @param modelItem
	 * @param seoElem
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException
	 * @throws EANBusinessRuleException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private EntityItem getWWSEO(EntityItem modelItem, Element seoElem, String seoid,String mt, String model,
			StringBuffer sb) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, 
	 RemoteException, EANBusinessRuleException, LockException, WorkflowException
	{
		EntityItem wwseo = LXLEADSUtil.searchForWWSEO(lxAbr, seoid);
		if (wwseo != null){
			lxAbr.addDebug("getWWSEO found "+wwseo.getKey());
		}else{
			// must create a wwseo
			String seoTechDesc = lxAbr.getNodeValue(seoElem,"SEOTECHDESC");
			wwseo = LXLEADSUtil.createWWSEO(lxAbr, modelItem, seoid, seoTechDesc,domain);
			if (wwseo!= null){
				lxAbr.addDebug("getWWSEO created "+wwseo.getKey());
				String cofcat = PokUtils.getAttributeValue(modelItem, "COFCAT", "", "",false);
				// CREATED_SEO_MSG = {0} {1} created for {2} Model: {3} {4}
				sb.append(lxAbr.getResourceMsg("CREATED_SEO_MSG", new Object[]{
						wwseo.getEntityGroup().getLongDescription(),
						seoid,cofcat,mt,model})+"<br />");
				// add references
				String key = mt+":"+model+":"+seoid;
				Vector tmfVct = (Vector)tmfTbl.get(key);
				if (tmfVct != null){ // SVC models will not have TMF
					lxAbr.addDebug("getWWSEO: tmf key: "+key+" size: "+tmfVct.size());
					for (int i=0; i<tmfVct.size(); i++){
						lxAbr.addDebug("tmf["+i+"]"+((EntityItem)tmfVct.elementAt(i)).getKey());
					}
					LXLEADSUtil.createFeatureRefs(lxAbr, wwseo, modelItem, tmfVct,  psQtyTbl);
				}
			}
		}
	
		return wwseo;
	}	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#getDescription()
	 */
	public String getDescription() {
		return "Create Special Bid - LSEOBUNDLE";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#getTitle()
	 */
	public String getTitle() {
		return "LSEOBUNDLE "+LXABRSTATUS.TITLE+(lxAbr.getReturnCode()==PokBaseABR.PASS?" created":"");
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#getVersion()
	 */
	public String getVersion() {
		return "1.11";
	}

}
