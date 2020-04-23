//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import org.w3c.dom.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import com.ibm.transform.oim.eacm.util.*;
/**********************************************************************************
 * From "SG FS Inbound Feed Leads 20090423.doc"
 * 
 *Card	XML_Begin	XML_End	Level
 *1	<?XML VERSION="1.0" ENCODING="UTF-8"?>		1
 *1		<LEADSADDSEO>						1
 *1			<DTSOFDATA>	</DTSOFDATA>		2
 *1			<FACTSHEETNUM>	</FACTSHEETNUM>	2
 *1			<SEOID>	</SEOID>				2
 *1			<SEOTECHDESC>	</SEOTECHDESC>	2
 *1			<PUBFROM>	</PUBFROM>			2
 *1			<MT>	</MT>					2
 *1			<MODEL>	</MODEL>				2
 *1			<FEATURELIST>					2
 *<1..N>		<FEATUREELEMENT>			3
 *1					<FEATURECODE>	</FEATURECODE>	4
 *1					<QTY>	</QTY>			4
 *				</FEATUREELEMENT>			3
 *			</FEATURELIST>					2
 *1			<COUNTRYLIST>					2
 *<1..N>		<COUNTRYELEMENT>			3
 *1					<COUNTRY>	</COUNTRY>	4
 *				</COUNTRYELEMENT>			3
 *			</COUNTRYLIST>					2
 *		</LEADSADDSEO>	1
 * 
 * VI.	<LEADSADDSEO>

The attached spreadsheet has the definition of this XML message on the tab named 'SG_XML_LSEO'.

1.	Search for MODEL using: 
-	MACHTYPEATR = <MT>
-	MODELATR = <MODEL>

If not found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
Machine Type = <MT>
Model = <MODEL>
Message = 'MODEL does not exist'
LXABRSTATUS = 'Failed'

2.	Search for WWSEO using:
-	<SEOID>

If found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
Machine Type = <MT>
Model = <MODEL>
WWSEO = <SEOID>
Message = 'WWSEO already exists'
LXABRSTATUS = 'Failed'

3.	Search for LSEO using:
-	<SEOID>

If found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
Machine Type = <MT>
Model = <MODEL>
LSEO = <SEOID>
Message = 'LSEO already exists'
LXABRSTATUS = 'Failed'

4.	Create WWSEO
The parent MODEL is the MODEL found in step 1.
The attributes of the WWSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with details provided on the SG Notes tab.

5.	Create LSEO
The parent WWSEO is the WWSEO created in step 4.
The attributes of the LSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with details provided on the SG Notes tab.

The LSEO’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT> .

6.	Create references to features
For each instance of <FEATUREELEMENT> and based on the value of COFCAT for the MODEL found in step 1:

1.	Hardware
Search for PRODSTRUCT using <MT> <MODEL> <FEATURECODE> and create WWSEOPRODSTRUCT from the WWSEO created in step 5 to the PRODSTRUCT.

If not found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
Machine Type = <MT>
Model = <MODEL>
SEO = <SEOID>
Feature Code = <FEATURECODE>
Message = 'TMF does not exist'
LXABRSTATUS = 'Failed'

2.	Software 
Search for SWPRODSTRUCT using <MT> <MODEL> <FEATURECODE> and create WWSEOSWPRODSTRUCT from the WWSEO created in step 5 to the SWPRODSTRUCT. 

If not found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
Machine Type = <MT>
Model = <MODEL>
SEO = <SEOID>
Feature Code = <FEATURECODE>
Message = 'TMF does not exist'
LXABRSTATUS = 'Failed'

3.	Service 
Service WWSEOs do not have Service Features and therefore there is nothing to do at this step.

7.	Create Report
A report is created and submitted to Subscription/Notification as follows:

LSEO Special Bid from LEADS created (LXABRSTATUS)

Userid:
Role:
Workgroup:
Date:
Description: Special Bid Created
Return code:

<SEOID> created for <MT> <MODEL>
<FEATURECODE> <QTY>

<COUNTRY>

 *
 */
//LXLEADSADDSEOABR.java,v
//Revision 1.7  2009/05/06 12:38:52  wendy
//RCQ00028947 - LEADS to EACM Feed - Update Mapping for Audience, Rate Card Code, UNSPSC Code
//RCQ00029975 - LEADS inbound feed Workgroup Selection
//
//Revision 1.6  2009/04/20 18:40:30  wendy
//Use VE instead of search to improve performance
//
//Revision 1.5  2009/04/20 15:09:30  wendy
//Use VE instead of search to improve performance
//
//Revision 1.4  2009/04/07 12:19:37  wendy
//Enhance error msgs
//
//Revision 1.3  2009/04/02 19:44:31  wendy
//Output factsheet header when possible
//
//Revision 1.2  2009/02/23 21:34:57  wendy
//Allow for whitespace in xml
//
//Revision 1.1  2009/01/20 19:39:52  wendy
//CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
//CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//
public class LXLEADSADDSEOABR implements LXABRInterface
{	
	private LXABRSTATUS lxAbr=null;
	private String dtsOfData=null;
	private Element rootElem = null;
	private String factSheetNum = null;
	private String seoid = null;
	private String pubfrom =null;
	private String mt = null;
	private String model = null;
	private String cofcat ="";
	private String seoTechDesc = null;
	private EntityItem modelItem = null;
	private Vector tmfVct = new Vector(); // prodstructs
	private Hashtable psQtyTbl = new Hashtable(); // ps.key is key, qty is value
	private Vector countryVct = new Vector(1);
	private EntityList psList = null;
	private Object audien = null;
	private String domainStr = null;

	/*******************************************************
	 * Make sure the xml has the specified set of tags
	 * 
	 * Card	XML_Begin	XML_End	Level	SG EntityType	AttributeCode
	 * 1	<?XML VERSION="1.0" ENCODING="UTF-8"?>		1		
	 * 1	<LEADSADDSEO>		1		
	 * 1	<DTSOFDATA>	</DTSOFDATA>	2		
	 * 1	<FACTSHEETNUM>	</FACTSHEETNUM>	2		
	 * 1	<SEOID>	</SEOID>	2	WWSEO & LSEO	SEOID
	 * 1	<SEOTECHDESC>	</SEOTECHDESC>	2	WWSEO	SEOTECHDESC
	 * 1	<PUBFROM>	</PUBFROM>	2	LSEO	LSEOPUBDATEMTRGT
	 * 1	<MT>	</MT>	2	MODEL	MACHTYPEATR
	 * 1	<MODEL>	</MODEL>	2	MODEL	MODELATR
	 * 1	<FEATURELIST>		2		
	 * <1..N>	<FEATUREELEMENT>		3		
	 * 1	<FEATURECODE>	</FEATURECODE>	4	FEATURE	FEATURECODE
	 * 1	<QTY>	</QTY>	4	WWSEOPRODSTRUCT.QTY,WWSEOSWPRODSTRUCT.QTY,WWSEOSVCPRODSTRUCT.?
	 * 		</FEATUREELEMENT>	3		
	 * 		</FEATURELIST>	2		
	 * 1	<COUNTRYLIST>		2		
	 * <1..N>	<COUNTRYELEMENT>		3		
	 * 1	<COUNTRY>	</COUNTRY>	4	LSEO	COUNTRYLIST
	 * 		</COUNTRYELEMENT>	3		
	 * 		</COUNTRYLIST>	2		
	 * 		</LEADSADDSEO>	1	
	 * If <COUNTRY> is not found, then this is an error for the instance of <COUNTRYELEMENT>:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM>
	 * Machine Type = <MT>
	 * Model = <MODEL>
	 * LSEO = <SEOID>
	 * Country = <COUNTRY>
	 * Message = 'Country Code not found'
	 * LXABRSTATUS = 'Failed'
	 * 	 
	 * A.	Handling <COUNTRY> from LEADS
	 * 
	 * The process is to use <COUNTRY> to search GENERALAREA using GENAREACODE. The instance that is found
	 * has an attribute GENAREANAME which is the same as COUNTRYLIST.
	 * 
	 * If <COUNTRY> is not found, produce an error using the value of <COUNTRY>. See each section for 
	 * the specific error.
	 * 
	 * If <COUNTRY> is found, then use the Flag Code (aka Description Type) found in GENAREANAME as COUNTRYLIST.
	 * 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */ 
	private void validateXML() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException 
	{
		// check for specific elements
		dtsOfData = lxAbr.getNodeValue(rootElem,"DTSOFDATA");
		factSheetNum = lxAbr.getNodeValue(rootElem,"FACTSHEETNUM");
		seoid = lxAbr.getNodeValue(rootElem,"SEOID");
		pubfrom = lxAbr.getNodeValue(rootElem,"PUBFROM","");
		mt = lxAbr.getNodeValue(rootElem,"MT");
		model = lxAbr.getNodeValue(rootElem,"MODEL");	
		seoTechDesc = lxAbr.getNodeValue(rootElem,"SEOTECHDESC","");	
		
		// derive audience value
		audien = LXLEADSUtil.deriveAudien(lxAbr.getNodeValue(rootElem,"ACCOUNTTYPE",""));

		// build countrylist vector
		//1	<COUNTRYLIST>	
		//<1..N>	<COUNTRYELEMENT>	
		//1				<COUNTRY>	</COUNTRY>
		//			</COUNTRYELEMENT>
		//	</COUNTRYLIST>
		NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");

		//ERROR_INVALIDXML = Invalid xml tag: {0}.
		if (ctrylist==null || ctrylist.getLength()!=1){
			lxAbr.addError("ERROR_INVALIDXML", "COUNTRYLIST");
		}else {
			lxAbr.verifyChildNodes(ctrylist, "COUNTRYLIST", "COUNTRYELEMENT", 1);
			for (int x=0; x<ctrylist.getLength(); x++){
				Node node =ctrylist.item(x);
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
					if (ctryDesc != null){
						StringBuffer debugSb = new StringBuffer();
						// GENAREACODE
						EntityItem genarea = LXLEADSUtil.searchForGENERALAREA(lxAbr.getDatabase(), 
								lxAbr.getProfile(), ctryDesc,debugSb);
						if (debugSb.length()>0){
							lxAbr.addDebug(debugSb.toString());
						}
						if (genarea!= null){
							// get genareaname attr flag value
							String ctryflagcode = PokUtils.getAttributeFlagValue(genarea, "GENAREANAME");
							if (ctryflagcode!= null) {
								countryVct.add(ctryflagcode);
							}else{
								//ERROR_COUNTRY = Country = {0}<br />Country Code not found.
								lxAbr.addError("ERROR_COUNTRY",ctryDesc);
							}
						}else{
							lxAbr.addError("GENERALAREA item not found for COUNTRY: "+ctryDesc);
						}						
					}									
				}
			}
		}
	}
	
	/***********************************************
	 *  Get the header
	 *
	 *@return java.lang.String
	 */
	public String getHeader(){
		if (dtsOfData==null||factSheetNum==null||mt==null||cofcat==null||model==null||seoid==null){
			return "";
		}
		// LSEO_HEADER = Date/Time = {0}<br />Fact Sheet = {1}<br />Machine Type = {2}<br />{3} Model = {4}<br />LSEO = {5}
		String header =  lxAbr.getResourceMsg("LSEO_HEADER", new Object[]{dtsOfData,factSheetNum,mt,cofcat,model,seoid});
		if (domainStr!= null){
			header = header+"<br />Domain = "+domainStr;
		}
		return header;
	}
	
	/***********************
	 * Search for Model using mt and model, 
	 * search for WWSEO using seoid, 
	 * search for LSEO using seoid
	 * 
	 * For each instance of <FEATUREELEMENT> and based on the value of COFCAT for the MODEL found in step 1:
	 * 1.	Hardware
	 * 	Search for PRODSTRUCT using <MT> <MODEL> <FEATURECODE> 
	 * 2.	Software
	 *  Search for SWPRODSTRUCT using <MT> <MODEL> <FEATURECODE>
	 * 3.	Service
	 * Service WWSEOs do not have Service Features and therefore there is nothing to do at this step. 
	 * 
	 * If not found, then this is an error:
	 * 	Date/Time = <DTSOFDATA>
	 * 	Fact Sheet = <FACTSHEETNUM>
	 * 	Machine Type = <MT>
	 * 	Model = <MODEL>
	 * 	SEO = <SEOID>
	 * 	Feature Code = <FEATURECODE>
	 * 	Message = 'TMF does not exist'
	 * 	LXABRSTATUS = 'Failed'
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#validateData(COM.ibm.eannounce.abr.sg.LXABRSTATUS, org.w3c.dom.Element)
	 */
	public void validateData(LXABRSTATUS theAbr, Element root) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		lxAbr = theAbr;
		rootElem = root;
		
		validateXML();
		
		if (lxAbr.getReturnCode()!= PokBaseABR.PASS){
			return;
		}
	
		// 1.	Search for MODEL using:	MACHTYPEATR = <MT>,	MODELATR = <MODEL>
		// If not found, then this is an error
		modelItem = LXLEADSUtil.searchForModel(lxAbr, mt, model);
		if (modelItem == null){
			//ERROR_MODEL = MODEL does not exist.
			lxAbr.addError("ERROR_MODEL",null);
		}else{
			cofcat = PokUtils.getAttributeValue(modelItem, "COFCAT", "", "",false);	
			String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
			lxAbr.addDebug("validateData Found "+cofcat+" "+modelItem.getKey());
			String vename = null;
			// pull VE to find all valid PRODSTRUCT
			if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
				vename = "EXRPT3LEADS";
			}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
				vename = "EXRPT3LEADS2";
			}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
				lxAbr.addDebug("validateData Model is Service, no prodstructs to pull");
			}
			if (vename !=null){
				psList = lxAbr.getDatabase().getEntityList(lxAbr.getProfile(), 
					new ExtractActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(), vename), 
					new EntityItem[]{modelItem});
				lxAbr.addDebug("validateData VE:"+vename+"\n"+PokUtils.outputList(psList));
			}
		}

		// 2.	Search for WWSEO using:	<SEOID>
		EntityItem wwseo = LXLEADSUtil.searchForWWSEO(lxAbr, seoid);
		//If found, then this is an error
		if (wwseo != null){
			//ERROR_WWSEO = WWSEO = {0}<br />WWSEO already exists.
			lxAbr.addError("ERROR_WWSEO", new Object[]{seoid});
		}

		//  3.	Search for LSEO using:	<SEOID>
		EntityItem lseo = LXLEADSUtil.searchForLSEO(lxAbr,seoid);
		//If found, then this is an error
		if (lseo != null){
			//ERROR_LSEO = LSEO already exists.
			lxAbr.addError("ERROR_LSEO", null);
		}
		
		// search for prodstructs based on model.cofcat
		if (modelItem != null){ // model was found
			//searchForProdstructs();
			matchProdstructs();
		}	
	}	

	/*****************************************
	 * For each instance of <FEATUREELEMENT> and based on the value of COFCAT for the MODEL found in step 1:
	 * 2	<FEATURELIST>	
	 * 3		<FEATUREELEMENT>	
	 * 4			<FEATURECODE>	</FEATURECODE>
	 * 4			<QTY>	</QTY>
	 * 3		</FEATUREELEMENT>
	 * 2	</FEATURELIST>
	 * 1.	Hardware
	 * Search for PRODSTRUCT using <MT> <MODEL> <FEATURECODE> 
	 * 
	 * 2.	Software 
	 * Search for SWPRODSTRUCT using <MT> <MODEL> <FEATURECODE>  
	 * 
	 * 3.	Service 
	 * Service WWSEOs do not have Service Features and therefore there is nothing to do at this step. 
	 * 
	 * If not found, then this is an error:
	 * 	Date/Time = <DTSOFDATA>
	 * 	Fact Sheet = <FACTSHEETNUM>
	 * 	Machine Type = <MT>
	 * 	Model = <MODEL>
	 * 	SEO = <SEOID>
	 * 	Feature Code = <FEATURECODE>
	 * 	Message = 'TMF does not exist'
	 * 	LXABRSTATUS = 'Failed'
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * /
	private void searchForProdstructs() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(3);
		attrVct.addElement("MODEL:MACHTYPEATR");
		attrVct.addElement("MODEL:MODELATR");
		
		Vector valVct = new Vector(2);
		valVct.addElement(mt);
		valVct.addElement(model);

		String searchAction = "";
		String searchType = "";
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
		lxAbr.addDebug("searchForProdstructs "+modelItem.getKey()+" cofcatflag:"+cofcatflag);
		if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
			searchAction = LXLEADSUtil.PS_SRCHACTION_NAME;
			searchType = "PRODSTRUCT";
			attrVct.addElement("FEATURE:FEATURECODE");
		}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
			searchAction = LXLEADSUtil.SWPS_SRCHACTION_NAME;
			searchType = "SWPRODSTRUCT";	
			attrVct.addElement("SWFEATURE:FEATURECODE");
		}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
			lxAbr.addDebug("searchForProdstructs Model is Service, nothing to do");
			attrVct.clear();
			valVct.clear();
			return;
			//searchAction = LXLEADSUtil.SVCPS_SRCHACTION_NAME; 
			//searchType = "SVCPRODSTRUCT";
			//attrVct.addElement("SVCFEATURE:FEATURECODE");
		}
		/ *
		 1	<FEATURELIST>	
		<1..N>	<FEATUREELEMENT>	
		1	<FEATURECODE>	</FEATURECODE>
		1	<QTY>	</QTY>
		</FEATUREELEMENT>
		</FEATURELIST>
		 * /
	
		// get all feature codes
		NodeList flist = rootElem.getElementsByTagName("FEATURELIST");

		//ERROR_INVALIDXML = Invalid xml tag: {0}.
		if (flist==null || flist.getLength()!=1){
			lxAbr.addError("ERROR_INVALIDXML", "FEATURELIST");
		}else {
			lxAbr.verifyChildNodes(flist, "FEATURELIST", "FEATUREELEMENT", 1);
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
						Vector tmpValVct = new Vector(valVct);
						tmpValVct.add(fcode);
						EntityItem eia[]=null;
						try{
							// srch should be domain restricted!!						
							StringBuffer debugSb = new StringBuffer();
							eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
									searchAction, searchType, true, attrVct, tmpValVct, debugSb);
							if (debugSb.length()>0){
								lxAbr.addDebug(debugSb.toString());
							}
						}catch(SBRException exc){
							// these exceptions are for missing flagcodes or failed business rules, dont pass back
							java.io.StringWriter exBuf = new java.io.StringWriter();
							exc.printStackTrace(new java.io.PrintWriter(exBuf));
							lxAbr.addDebug("searchForProdstructs SBRException: "+exBuf.getBuffer().toString());
						}

						tmpValVct.clear();

						if (eia==null || eia.length == 0){
							//ERROR_TMF = Feature Code = {0}<br />TMF does not exist.
							lxAbr.addError("ERROR_TMF", new Object[]{fcode});
						}else{
							for (int i=0; i<eia.length; i++){
								psQtyTbl.put(eia[i].getKey(),qty);
								tmfVct.add(eia[i]);
							}
							lxAbr.addDebug("searchForProdstructs found:\n"+PokUtils.outputList(eia[0].getEntityGroup().getEntityList()));
						}	
					} // end valid FEATURECODE and QTY
				} // end FEATUREELEMENTS loop
			}	// end FEATURELIST loop		
		}

		attrVct.clear();
		valVct.clear();
	}*/
	/**
	 * Use a VE instead of search to improve performance
	 */
	private void matchProdstructs()
	{
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
		String featureType = "";
		lxAbr.addDebug("matchProdstructs "+modelItem.getKey()+" cofcatflag:"+cofcatflag);
		if (LXLEADSUtil.COFCAT_HW.equals(cofcatflag)){
			featureType = "FEATURE";
		}else if (LXLEADSUtil.COFCAT_SW.equals(cofcatflag)){
			featureType = "SWFEATURE";	
		}else if (LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){
			lxAbr.addDebug("matchProdstructs Model is Service, nothing to do");
			return;
		}
		
		if (psList==null){
			return;
		}
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

		/*
		 1	<FEATURELIST>	
		<1..N>	<FEATUREELEMENT>	
		1	<FEATURECODE>	</FEATURECODE>
		1	<QTY>	</QTY>
		</FEATUREELEMENT>
		</FEATURELIST>
		 */
	
		// get all feature codes
		NodeList flist = rootElem.getElementsByTagName("FEATURELIST");

		//ERROR_INVALIDXML = Invalid xml tag: {0}.
		if (flist==null || flist.getLength()!=1){
			lxAbr.addError("ERROR_INVALIDXML", "FEATURELIST");
		}else {
			lxAbr.verifyChildNodes(flist, "FEATURELIST", "FEATUREELEMENT", 1);
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
							//ERROR_TMF = Feature Code = {0}<br />TMF does not exist.
							lxAbr.addError("ERROR_TMF", new Object[]{fcode});
						}else{
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
		fcTbl.clear();
	}
	/*****************************************************
	 * 4.	Create WWSEO
	 * The parent MODEL is the MODEL found in step 1.
	 * The attributes of the WWSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with 
	 * details provided on the SG Notes tab.
	 * 
	 * 5.	Create LSEO
	 * The parent WWSEO is the WWSEO created in step 4.
	 * The attributes of the LSEO are supplied via the XML shown in the SS on the SG_XML_LSEO tab with 
	 * details provided on the SG Notes tab.
	 * 
	 * The LSEO’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT> .
	 * 
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
	 * 7.	Create Report
	 * A report is created and submitted to Subscription/Notification as follows:
	 *  <SEOID> created for <MT> <MODEL>
	 *  
	 *  <FEATURECODE> <QTY>
	 *  <COUNTRY>
	 *  For the CHW system, PDHDOMAIN is determined as follows:
1.	Search (restricted to PDHDOMAIN) for the Model using: 
•	MACHTYPEATR = <MT>
•	MODELATR = <MODEL>
2.	If more than one MODEL, then choose the MODEL where COFCAT = “Hardware” (100) and COFGRP = “Base” (150).
3.	Use the PDHDOMAIN of this MODEL for all data created. 

If there is an error, see the message found in the sections below when searching for MODEL.

	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#execute()
	 */
	public void execute() 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException
	{
		String cofcatflag = PokUtils.getAttributeFlagValue(modelItem, "COFCAT");
		//PDHDOMAIN must be derived from the MODEL 	RCQ00029975
		EANAttribute attr = modelItem.getAttribute("PDHDOMAIN");
		Object domain = null;
        if (attr!=null){ // really should never happen
            EANMetaAttribute ma = attr.getMetaAttribute();
            String domainAttr = PokUtils.getAttributeFlagValue(modelItem, "PDHDOMAIN");
            lxAbr.addDebug("execute: using "+modelItem.getKey()+" PDHDOMAIN "+domainAttr);
            domainStr = PokUtils.getAttributeValue(modelItem, "PDHDOMAIN", ", ", "", false);
            if (ma.getAttributeType().equals("U")){
            	domain = domainAttr;
            }else{ // must be F
                String[] domainArray = PokUtils.convertToArray(domainAttr);
                Vector tmp = new Vector(domainArray.length);
                for (int k=0; k<domainArray.length; k++){
                    tmp.add(domainArray[k]);
                }
                domain = tmp;
            }
        }
		// create entities
		EntityItem wwseoItem = LXLEADSUtil.createWWSEO(lxAbr, modelItem, seoid, seoTechDesc,domain);
		EntityItem lseoItem=null;
		if (wwseoItem != null){
//			"10062"); //AUDIEN	F	Audience	"LE Direct"	flag code = 10062
			lseoItem = LXLEADSUtil.createLSEO(lxAbr, wwseoItem, seoid, pubfrom, countryVct,domain,audien);			
			if (lxAbr.getReturnCode()==PokBaseABR.PASS){
				LXLEADSUtil.createFeatureRefs(lxAbr, wwseoItem, modelItem, tmfVct, psQtyTbl);
			}
		}

		if (lxAbr.getReturnCode()==PokBaseABR.PASS){
			StringBuffer sb = new StringBuffer();
			// CREATED_SEOS_MSG = {0} and {1} {2} created for {3} Model: {4} {5}
			sb.append(lxAbr.getResourceMsg("CREATED_SEOS_MSG", 
					new Object[]{wwseoItem.getEntityGroup().getLongDescription(),
					lseoItem.getEntityGroup().getLongDescription(),
					seoid,cofcat,mt,model}));
			
			// list all feature codes and qty
			if (!LXLEADSUtil.COFCAT_SVC.equals(cofcatflag)){ // WWSEO SVC Models dont have features
				NodeList flist = rootElem.getElementsByTagName("FEATURELIST");
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

						// REF_FEATURE_MSG = <br /> Feature Code: {0} Quantity: {1} 
						sb.append(lxAbr.getResourceMsg("REF_FEATURE_MSG", new Object[]{fcode,qty}));
					} // end FEATUREELEMENTS loop
				}	// end FEATURELIST loop		
			}

			NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
			//REF_COUNTRY_MSG= <br />&nbsp;&nbsp;&nbsp;for Country:&nbsp;
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
			lxAbr.addOutput(sb.toString());
		}
		// release memory
		if (lseoItem!= null){
			lseoItem.getEntityGroup().getEntityList().dereference();
			lseoItem = null;
		}
		if (wwseoItem!= null){
			wwseoItem.getEntityGroup().getEntityList().dereference();
			wwseoItem = null;
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
		seoid=null;
		model=null;
		pubfrom=null;
		seoTechDesc = null;
		mt=null;
		modelItem = null;

		if (tmfVct != null){
			tmfVct.clear();
			tmfVct = null;
		}

		if (psList!=null){
			psList.dereference();
			psList = null;
		}
		psQtyTbl.clear();
		psQtyTbl = null;

		countryVct.clear();
		countryVct = null;
		cofcat = null;
	}
	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion(){
		return "1.7";
	}

	/***********************************************
	 *  Get the title
	 *
	 *@return java.lang.String
	 */
	public String getTitle() {
		return "LSEO "+LXABRSTATUS.TITLE+(lxAbr.getReturnCode()==PokBaseABR.PASS?" created":"");
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#getDescription()
	 */
	public String getDescription() {
		return "Create Special Bid - SEO";
	}
}
