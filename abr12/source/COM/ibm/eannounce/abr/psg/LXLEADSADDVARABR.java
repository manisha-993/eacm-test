//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.psg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import org.w3c.dom.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.*;

import COM.ibm.eannounce.abr.sg.*;
/**********************************************************************************
* From "SG FS Inbound Feed Leads 20090520b.doc"
* 
* Card	XML_Begin	XML_End				Level
* 1	<LEADSADDVAR>						1
* 1		<DTSOFDATA>	</DTSOFDATA>		2
* 1		<FACTSHEETNUM>	</FACTSHEETNUM>	2
* 1		<PNUMB>	</PNUMB>				2
* 1		<TECHDESC>	</TECHDESC>			2
* 1		<PUBFROM>	</PUBFROM>			2
* 1		<CTOMODEL>	</CTOMODEL>			2
* 1		<SBBLIST>						2
* <1..N>	<SBBELEMENT>				3
* 1				<SBB>	</SBB>			4
* 1				<QTY>	</QTY>			4
* 			</SBBELEMENT>				3
* 		</SBBLIST>						2
* 1		<COUNTRYLIST>					2
* <1..N>	<COUNTRYELEMENT>			3
* 1				<PNUMB_CT>	</PNUMB_CT>	4
* 1				<COUNTRY>	</COUNTRY>	4
* 			</COUNTRYELEMENT>			3
* 		</COUNTRYLIST>					2

VIII.	<LEADSADDVAR>

The attached spreadsheet has the definition of this XML message on the tab named "HVEC_XML".

1.	Search for CTO using: 
	COFPNUMB = <CTOMODEL>

If not found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
CTO Part Number = <CTOMODEL>
Message = "Configurable Offering (CTO) does not exist"
LXABRSTATUS = "Failed"

2.	Search for VAR using:
	OFFERINGPNUMB = <PNUMB>

If found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM> 
CTO Part Number = <CTOMODEL>
VAR Part Number= < PNUMB >
Message = "Variant Offering (VAR) already exists"
LXABRSTATUS = "Failed"

3.	Search for PR using:
	PROJECTNAME = "LEADS FEED SPECIAL BID"

If not found, then this is an error: 
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM> 
CTO Part Number = <CTOMODEL>
VAR Part Number= < PNUMB >
PROJECTNAME = "LEADS FEED SPECIAL BID"
Message = "Project does not exist"
LXABRSTATUS = "Failed"

4.	Create VAR
Create CTOVAR based on the parent CTO is the CTO found in step 1.
Create PRVAR based on the parent PR found in step 3.
The attributes of the VAR are supplied via the XML shown in the SS on the HVEC_XML tab with details provided on the HVEC Notes tab.

5.	Create references to SBBs
For each instance of <SBBELEMENT> 
Search for SBB using SBBPNUMB = <SBB> and create VARSBB from the VAR created in step 4 to the SBB.

If not found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM> 
CTO Part Number = <CTOMODEL>
VAR Part Number = < PNUMB >
SBB Part Number = <SBB>
Message = "SBB does not exist"
LXABRSTATUS = "Failed"

6.	Create CVAR
For each instance of <COUNTRYELEMENT>
Search for CVAR using:
-	PNUMB_CT = <PNUMB_CT>
-	GENAREANAME = <COUNTRY>

If found, then this is an error:
Date/Time = <DTSOFDATA>
Fact Sheet = <FACTSHEETNUM>
CTO Part Number = <CTOMODEL>
VAR Part Number = < PNUMB>
CVAR Part Number = <PNUMB_CT>
Country = <COUNTRY>
Message = "CVAR already exists"
LXABRSTATUS = "Failed"

If not found, then create a CVAR as a child of the VAR created in step 4 using relator VARCVAR.

The attributes of the CVAR are supplied via the XML shown in the SS on the HVEC_XML tab with 
details provided on the SG Notes tab.

The CVAR’s GENARENAME is equal to <COUNTRY> and PNUMB_CT is equal to <PNUMB_CT>.

7.	Create Report
A report is created and submitted to Subscription/Notification as follows:

Variant Offering Special Bid from LEADS created (LXABRSTATUS)

Userid:
Role:
Workgroup:
Date:
Description: Special Bid Created
Return code:

Variant <PNUMB> created for CTO <CTOMODEL> with
<SBB> <QTY>
in
<COUNTRY> as <PNUMB_CT> 

*
*/
//$Log: LXLEADSADDVARABR.java,v $
//Revision 1.10  2009/06/30 18:50:51  wendy
//SearchAction is shared in PDGUtils, do not dereference it now
//
//Revision 1.9  2009/05/20 22:53:17  wendy
//make sure pdhdomain=pcd
//
//Revision 1.8  2009/05/20 15:08:49  wendy
//Set VARSPECIALBID to Yes
//
//Revision 1.7  2009/05/20 00:43:39  wendy
//replace ctoItem with entity from extract to get all attributes
//
//Revision 1.6  2009/05/06 12:38:52  wendy
//RCQ00028947 - LEADS to EACM Feed - Update Mapping for Audience, Rate Card Code, UNSPSC Code
//RCQ00029975 - LEADS inbound feed Workgroup Selection
//
//Revision 1.5  2009/04/15 18:55:31  wendy
//revised to limit SBBs to parent CTO subset
//
//Revision 1.4  2009/04/07 12:19:37  wendy
//Enhance error msgs
//
//Revision 1.3  2009/04/02 19:44:31  wendy
//Output factsheet header when possible
//
//Revision 1.2  2009/02/24 13:13:57  wendy
//Allow for whitespace in xml
//
//Revision 1.1  2009/01/20 19:38:44  wendy
//CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
//CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//


public class LXLEADSADDVARABR implements LXABRInterface
{	
	private static final String CTO_SRCHACTION_NAME = "LDSRDCTO";//"SRDCTO1";
	private static final String VAR_SRCHACTION_NAME = "LDSRDVAR";//"SRDVAR1";
	private static final String PR_SRCHACTION_NAME = "LDSRDPR";//"SRDPR1";
	//private static final String SBB_SRCHACTION_NAME = "LDSRDSBB";//"SRDSBB1";
	private static final String CVAR_SRCHACTION_NAME = "LDSRDCVAR";//"SRDCVAR3";
	private static final String VAR_CREATEACTION_NAME= "LDCRPRVAR";//"CRPRVAR";//from project parent
	private static final String CTOVAR_LINKACTION_NAME= "LDLINKCTOVAR";//"LINKCTOVAR";
	private static final String VARSBB_LINKACTION_NAME="LDLINKSBBPNUMB";//"LINKVARSBB";
	private static final String CVAR_CREATEACTION_NAME="LDCRVARCVAR";//"CRVARCVAR";
	private static final String GENERALAREA_SRCHACTION_NAME="LDSRDGENAREA";//"SRDGENERALAREA"; 
	
	private static Hashtable GENAREACODE_TBL = null; // GENAREACODE flagdesc is key, flag code is value
	private Hashtable generalAreaTbl = new Hashtable(); // GENAREACODE flagdesc is key, GENERALAREA entity is value

	private LXABRSTATUS lxAbr=null;
	private String dtsOfData=null;
	private Element rootElem = null;
	private String factSheetNum = null;
	private String pnumb = null;
	private String pubfrom =null;
	private String ctomodel =null;
	private String techDesc = null;
	private EntityItem ctoItem = null;
	private EntityItem projItem = null;
	private EntityList ctoSbbList = null;
	private Vector sbbVct = new Vector(); // sbb
	private Object audien = null;
	private static final Hashtable AUDIEN_TBL;
	/*
CATALOGNAME_CVAR	F	Audience	10065	Affinity
CATALOGNAME_CVAR	F	Audience	10045	America Online
CATALOGNAME_CVAR	F	Audience	10058	Best Buys - EMEA
CATALOGNAME_CVAR	F	Audience	10059	Business Partner - Tier 1
CATALOGNAME_CVAR	F	Audience	10060	Business Partner - Tier 2
CATALOGNAME_CVAR	F	Audience	10046	Catalog - Business Partner
CATALOGNAME_CVAR	F	Audience	10047	Catalog - Direct
CATALOGNAME_CVAR	F	Audience	10049	Catalog - General
CATALOGNAME_CVAR	F	Audience	10048	Catalog - Indirect/Reseller
CATALOGNAME_CVAR	F	Audience	10050	Catalog - Repository Trial
CATALOGNAME_CVAR	F	Audience	10051	Catalog - US EPP
CATALOGNAME_CVAR	F	Audience	10052	Catalog - WWW
CATALOGNAME_CVAR	F	Audience	10067	DAC/MAX
CATALOGNAME_CVAR	F	Audience	10053	Extranet
CATALOGNAME_CVAR	F	Audience	10044	Faxback
CATALOGNAME_CVAR	F	Audience	10061	Indirect - Public Websites
CATALOGNAME_CVAR	F	Audience	10062	LE Direct
CATALOGNAME_CVAR	F	Audience	10055	None
CATALOGNAME_CVAR	F	Audience	10063	Partner Commerce
CATALOGNAME_CVAR	F	Audience	10056	Prodigy
CATALOGNAME_CVAR	F	Audience	10057	Product/Price Guide
CATALOGNAME_CVAR	F	Audience	10054	Public
CATALOGNAME_CVAR	F	Audience	10064	ShopIBM
CATALOGNAME_CVAR	F	Audience	10066	TopSeller - EMEA


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
		Vector vct = new Vector(3);
		vct.add("10062");
		vct.add("10067");
		AUDIEN_TBL.put("Odyssey", vct);
		vct = new Vector(3);
		vct.add("10062");
		vct.add("10046");
		vct.add("10048");
		AUDIEN_TBL.put("Indirect", vct);
	}
	private Hashtable sbbQtyTbl = new Hashtable(); // sbb.key is key, qty is value
	private static final Hashtable VARRATECARDCODE_TBL;
	/*
CVOFRATECARDCODE    U   Rate Card Code  0060    SCD-0042 All CDTs and Intellistations
CVOFRATECARDCODE    U   Rate Card Code  0050    SMN-0042 Monitors that weigh more than 20 pounds
CVOFRATECARDCODE    U   Rate Card Code  0010    SPR-0002 Options / Product up to 2 pounds
CVOFRATECARDCODE    U   Rate Card Code  0020    SPR-0009 Options / Product between 2 and 9 pounds
CVOFRATECARDCODE    U   Rate Card Code  0030    SPR-0020 Options / Product between 9 and 20 pounds
CVOFRATECARDCODE    U   Rate Card Code  0080    SSL-0300 Large Servers, any weight
CVOFRATECARDCODE    U   Rate Card Code  0070    SSM-0080 Medium Servers
CVOFRATECARDCODE    U   Rate Card Code  0100    STA-0014 Thinkpads sourced in Asia- will be expired
CVOFRATECARDCODE    U   Rate Card Code  0040    STP-0014 All Thinkpads except those sourced in Asia
CVOFRATECARDCODE    U   Rate Card Code  0090    ZZZ-0000 Cost of shipping included in price of product
VARRATECARDCODE     U   Rate Card Code  11452   SCD-0042 All CDTs and Intellistations
VARRATECARDCODE     U   Rate Card Code  11451   SMN-0042 Monitors that weigh more than 20 pounds
VARRATECARDCODE     U   Rate Card Code  11447   SPR-0002 Options / Product up to 2 pounds
VARRATECARDCODE     U   Rate Card Code  11448   SPR-0009 Options / Product between 2 and 9 pounds
VARRATECARDCODE     U   Rate Card Code  11449   SPR-0020 Options / Product between 9 and 20 pounds
VARRATECARDCODE     U   Rate Card Code  11454   SSL-0300 Large Servers, any weight
VARRATECARDCODE     U   Rate Card Code  11453   SSM-0080 Medium Servers
VARRATECARDCODE     U   Rate Card Code  11450   STP-0014 All Thinkpads except those sourced in Asia
VARRATECARDCODE     U   Rate Card Code  11456   STP-0014 All Thinkpads except those sourced in Asia - expired
VARRATECARDCODE     U   Rate Card Code  11455   ZZZ-0000 Cost of shipping included in price of product

	 */
	static{
		VARRATECARDCODE_TBL = new Hashtable();
		VARRATECARDCODE_TBL.put("0060", "11452"); //SCD-0042 All CDTs and Intellistations
		VARRATECARDCODE_TBL.put("0050", "11451"); //SMN-0042 Monitors that weigh more than 20 pounds
		VARRATECARDCODE_TBL.put("0010", "11447"); //SPR-0002 Options / Product up to 2 pounds
		VARRATECARDCODE_TBL.put("0020", "11448"); //SPR-0009 Options / Product between 2 and 9 pounds
		VARRATECARDCODE_TBL.put("0030", "11449"); //SPR-0020 Options / Product between 9 and 20 pounds
		VARRATECARDCODE_TBL.put("0080", "11454"); //SSL-0300 Large Servers, any weight
		VARRATECARDCODE_TBL.put("0070", "11453"); //SSM-0080 Medium Servers
		VARRATECARDCODE_TBL.put("0040", "11450"); //STP-0014 All Thinkpads except those sourced in Asia - 11456  has been expired
		VARRATECARDCODE_TBL.put("0090", "11455"); //ZZZ-0000 Cost of shipping included in price of product
	}

	/*******************************************************
	 * Make sure the xml has the specified set of tags
	 * 
	 * Card	XML_Begin	XML_End				Level
	 * 1	<LEADSADDVAR>						1
	 * 1		<DTSOFDATA>	</DTSOFDATA>		2
	 * 1		<FACTSHEETNUM>	</FACTSHEETNUM>	2
	 * 1		<PNUMB>	</PNUMB>				2
	 * 1		<TECHDESC>	</TECHDESC>			2
	 * 1		<PUBFROM>	</PUBFROM>			2
	 * 1		<CTOMODEL>	</CTOMODEL>			2
	 * 1		<SBBLIST>						2
	 * <1..N>		<SBBELEMENT>				3
	 * 1				<SBB>	</SBB>			4
	 * 1				<QTY>	</QTY>			4
	 * 				</SBBELEMENT>				3
	 * 			</SBBLIST>						2
	 * 1		<COUNTRYLIST>					2
	 * <1..N>		<COUNTRYELEMENT>			3
	 * 1				<PNUMB_CT>	</PNUMB_CT>	4
	 * 1				<COUNTRY>	</COUNTRY>	4
	 * 				</COUNTRYELEMENT>			3
	 * 			</COUNTRYLIST>					2 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private void validateXML() throws MiddlewareRequestException, SQLException, MiddlewareException,
		MiddlewareShutdownInProgressException 
	{
		// check for specific elements
		dtsOfData = lxAbr.getNodeValue(rootElem,"DTSOFDATA");
		factSheetNum = lxAbr.getNodeValue(rootElem,"FACTSHEETNUM");
		pnumb = lxAbr.getNodeValue(rootElem,"PNUMB");
		pubfrom = lxAbr.getNodeValue(rootElem,"PUBFROM","");	
		techDesc = lxAbr.getNodeValue(rootElem,"TECHDESC","");	
		ctomodel = lxAbr.getNodeValue(rootElem,"CTOMODEL");	
		
		// derive audience value
		deriveAudien(lxAbr.getNodeValue(rootElem,"ACCOUNTTYPE",""));
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
		}else{
			lxAbr.verifyChildNodes(ctrylist, "COUNTRYLIST", "COUNTRYELEMENT", 1);
			// find GENERALAREA entities for the COUNTRY elements
			// check each countryelement
			for (int x=0; x<ctrylist.getLength(); x++){
				Node node = ctrylist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList elemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
				for (int e=0; e<elemlist.getLength(); e++){
					node = elemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element elem = (Element)elemlist.item(e);
					String country = lxAbr.getNodeValue(elem, "COUNTRY");
					EntityItem genarea = searchForGENERALAREA(country);
					if (genarea==null){
						// ERROR_COUNTRY = Country = {0}<br />Country Code not found.
						lxAbr.addError("ERROR_COUNTRY",country);	
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
		if (dtsOfData==null||factSheetNum==null||ctomodel==null||pnumb==null){
			return "";
		}
		//CTO_HEADER = Date/Time = {0}<br />Fact Sheet = {1}<br />CTO Part Number = {2}<br />VAR Part Number = {3}
		return lxAbr.getResourceMsg("CTO_HEADER", new Object[]{dtsOfData,factSheetNum,ctomodel,pnumb});
	}
	
	/***********************
	 * Check the xml and verify COUNTRY is valid
	 * 1.	Search for CTO using: COFPNUMB = <CTOMODEL>
	 * 2.	Search for VAR using: OFFERINGPNUMB = <PNUMB>
	 * 3.	Search for PR using: PROJECTNAME = "LEADS FEED SPECIAL BID"
	 * 5.	For each instance of <SBBELEMENT> Search for SBB using SBBPNUMB = <SBB> 
	 * 6.	Search for CVAR using:	<PNUMB_CT> <COUNTRY>

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
			
		// 1.	Search for CTO using: COFPNUMB = <CTOMODEL>
		// If not found, then this is an error
		searchForCTO();

		//2.	Search for VAR using: OFFERINGPNUMB = <PNUMB>
		//If found, then this is an error:
		searchForVAR();
		
		//3.	Search for PR using: PROJECTNAME = "LEADS FEED SPECIAL BID"
		// If not found, then this is an error
		searchForPROJ();
		
		// 5.	For each instance of <SBBELEMENT> Search for SBB using SBBPNUMB = <SBB>
		// If not found, then this is an error:
		searchForSBB();

		// 6.	Search for CVAR using:	<PNUMB_CT> <COUNTRY>
		//If found, then this is an error:
		searchForCVARs();
	}	
	/****************
	 * 1.	Search for CTO using: 	COFPNUMB = <CTOMODEL>
	 * 
	 * If not found, then this is an error:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM>
	 * CTO Part Number = <CTOMODEL>
	 * Message = "Configurable Offering (CTO) does not exist"
	 * LXABRSTATUS = "Failed"
	 *  
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void searchForCTO() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		attrVct.addElement("COFPNUMB");
		Vector valVct = new Vector(1);
		valVct.addElement(ctomodel);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			lxAbr.addDebug("Searching for CTO for attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					CTO_SRCHACTION_NAME, "CTO", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForCTO SBRException: "+exBuf.getBuffer().toString());
		}

		if (eia!=null && eia.length >0){				
			ctoItem = eia[0];	
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("searchForCTO found "+eia[i].getKey());
			}
			if (eia.length>1){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one CTO found for "+ctomodel);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				lxAbr.addError(sb.toString());
			}else{
				// VE for CTO to each SBB EXRPT0CTO5
				ctoSbbList = lxAbr.getDatabase().getEntityList(lxAbr.getProfile(), 
					new ExtractActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(), "EXRPT0CTO5"), 
					eia);
				lxAbr.addDebug("searchForCTO SBB VE:\n"+PokUtils.outputList(ctoSbbList));
				// replace ctoitem with entity from extract to get all attributes
				ctoItem = ctoSbbList.getParentEntityGroup().getEntityItem(0);
			}
		}else{
			//ERROR_CTO = Configurable Offering (CTO) does not exist
			lxAbr.addError("ERROR_CTO",null);	
		}
		attrVct.clear();
		valVct.clear();
	}

	/****************
	 * 2.	Search for VAR using:	OFFERINGPNUMB = <PNUMB>
	 * 
	 * If found, then this is an error:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM> 
	 * CTO Part Number = <CTOMODEL>
	 * VAR Part Number= < PNUMB >
	 * Message = "Variant Offering (VAR) already exists"
	 * LXABRSTATUS = "Failed"
	 * 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void searchForVAR() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		attrVct.addElement("OFFERINGPNUMB");
		Vector valVct = new Vector(1);
		valVct.addElement(pnumb);
		
		EntityItem eia[] = null;
		try{
			StringBuffer debugSb = new StringBuffer();
			lxAbr.addDebug("Searching for VAR for attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					VAR_SRCHACTION_NAME,  "VAR", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForVAR SBRException: "+exBuf.getBuffer().toString());
		}

		if (eia!=null && eia.length >0){	
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("searchForVAR found "+eia[i].getKey());
			}
			//ERROR_VAR = Variant Offering (VAR) already exists
			lxAbr.addError("ERROR_VAR",null);	
		}
		
		attrVct.clear();
		valVct.clear();
	}
	/****************
	 * 3.	Search for PR using:	PROJECTNAME = "LEADS FEED SPECIAL BID"
	 * 
	 * If not found, then this is an error: 
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM> 
	 * CTO Part Number = <CTOMODEL>
	 * VAR Part Number= < PNUMB >
	 * PROJECTNAME = "LEADS FEED SPECIAL BID"
	 * Message = "Project does not exist"
	 * LXABRSTATUS = "Failed"
	 * 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void searchForPROJ() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		attrVct.addElement("PROJECTNAME");
		Vector valVct = new Vector(1);
		String projName = "LEADS FEED SPECIAL BID";  
		valVct.addElement(projName); 

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			lxAbr.addDebug("Searching for PR for attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
					PR_SRCHACTION_NAME,  "PR", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				lxAbr.addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			lxAbr.addDebug("searchForPROJ SBRException: "+exBuf.getBuffer().toString());
		}

		if (eia!=null && eia.length >0){
			for (int i=0; i<eia.length; i++){
				lxAbr.addDebug("searchForPROJ found "+eia[i].getKey());
			}
			if (eia.length>1){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one PR found for "+projName);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				lxAbr.addError(sb.toString());
			}
			projItem = eia[0];
		}else{
			// ERROR_CTO_HEADER = Date/Time = {0}<br />Fact Sheet = {1}<br />CTO Part Number = {2}<br />VAR Part Number = {3}
			//ERROR_PROJ = PROJECTNAME = &quot;LEADS FEED SPECIAL BID&quot;<br />Project does not exist
			lxAbr.addError("ERROR_PROJ",null);		
		}
		
		attrVct.clear();
		valVct.clear();
	}
	/*****************************************
	 * For each instance of <SBBELEMENT> 
	 * Check that the SBB is a child of the CTO (relator CTOSBB) using SBBPNUMB = <SBB> 
	 * 
	 * If the SBB is not found to be a child of the CTO, then this is an error:
	 * Date/Time = <DTSOFDATA>
	 * Fact Sheet = <FACTSHEETNUM> 
	 * CTO Part Number = <CTOMODEL>
	 * VAR Part Number = < PNUMB >
	 * SBB Part Number = <SBB>
	 * Message = "SBB does not exist"
	 * LXABRSTATUS = "Failed"
	 * 	
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void searchForSBB() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		//Vector attrVct = new Vector(1);
		//attrVct.addElement("SBBPNUMB");
		
		//Vector valVct = new Vector(1);
	
		// get all sbb
		NodeList sbblist = rootElem.getElementsByTagName("SBBLIST");

		//ERROR_INVALIDXML = Invalid xml tag: {0}.
		if (sbblist==null || sbblist.getLength()!=1){
			lxAbr.addError("ERROR_INVALIDXML", "SBBLIST");
		}else {
			lxAbr.verifyChildNodes(sbblist, "SBBLIST", "SBBELEMENT", 1);
			if (ctoSbbList==null){
				return;
			}
			EntityGroup sbbGrp = ctoSbbList.getEntityGroup("SBB");
			for (int x=0; x<sbblist.getLength(); x++){
				Node node = sbblist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("SBBLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList sbbelemlist = sbblist.item(x).getChildNodes(); // SBBELEMENT
				for (int e=0; e<sbbelemlist.getLength(); e++){
					node = sbbelemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("SBBELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element sbbElem = (Element)sbbelemlist.item(e);
					String sbb = lxAbr.getNodeValue(sbbElem, "SBB");
					String qty = lxAbr.getNodeValue(sbbElem, "QTY","1");
					if (sbb!= null){
						boolean found = false;
						//SBB must be a child of the CTO
						for (int s=0; s<sbbGrp.getEntityItemCount(); s++){
							EntityItem sbbItem = sbbGrp.getEntityItem(s);
							String pnumb = PokUtils.getAttributeValue(sbbItem, "SBBPNUMB", "", "", false);
							if (pnumb.equals(sbb)){
								sbbQtyTbl.put(sbbItem.getKey(),qty);
								sbbVct.add(sbbItem);
								lxAbr.addDebug("searchForSBB found: "+sbbItem.getKey()+" for "+sbb);
								found = true;
								break;
							}
						}
						if (!found){
							// ERROR_SBB = SBB {0} does not exist.  It is not linked to the CTO
							lxAbr.addError("ERROR_SBB",sbb);
						}
						
						/*valVct.clear();
						valVct.add(sbb);

						EntityItem eia[]= null;
						try{
							// srch should be domain restricted!!
							StringBuffer debugSb = new StringBuffer();
							lxAbr.addDebug("Searching for SBB for attrs: "+attrVct+" values: "+valVct);
							eia = ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
									SBB_SRCHACTION_NAME,  "SBB", false, attrVct, valVct, debugSb);
							if (debugSb.length()>0){
								lxAbr.addDebug(debugSb.toString());
							}
						}catch(SBRException exc){
							// these exceptions are for missing flagcodes or failed business rules, dont pass back
							java.io.StringWriter exBuf = new java.io.StringWriter();
							exc.printStackTrace(new java.io.PrintWriter(exBuf));
							lxAbr.addDebug("searchForSBB SBRException: "+exBuf.getBuffer().toString());
						}
						if (eia==null || eia.length == 0){
							//ERROR_SBB = SBB {0} does not exist
							lxAbr.addError("ERROR_SBB",sbb);	
						}else{
							for (int i=0; i<eia.length; i++){
								sbbQtyTbl.put(eia[i].getKey(),qty);
								sbbVct.add(eia[i]);
							}
							lxAbr.addDebug("searchForSBB found:\n"+PokUtils.outputList(eia[0].getEntityGroup().getEntityList()));
						}	*/
					} // end valid SBB and QTY
				} // end SBBELEMENT loop
			}	// end SBBLIST loop		
		}

		//attrVct.clear();
		//valVct.clear();
	}
	/****************
	 * 6.	Search for CVAR using:	<PNUMB_CT> and <COUNTRY>
	 * 
	 * If found, then this is an error:
	 * 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void searchForCVARs() 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(2);
		attrVct.addElement("PNUMB_CT");
		attrVct.addElement("GENAREANAMEREGION"); // order is important
		attrVct.addElement("GENAREANAME");
		
		// check each countryelement
		NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
		for (int x=0; x<ctrylist.getLength(); x++){
			Node node = ctrylist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			NodeList elemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
			for (int e=0; e<elemlist.getLength(); e++){
				node = elemlist.item(e);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				Element elem = (Element)elemlist.item(e);
				String pnumbct = lxAbr.getNodeValue(elem, "PNUMB_CT");
				String ctry = lxAbr.getNodeValue(elem, "COUNTRY");
				// get the GENERALAREA item for this COUNTRY
				EntityItem genarea = searchForGENERALAREA(ctry);

				Vector valVct = new Vector(2);
				valVct.addElement(pnumbct);
			
				//GENAREAPARENT=GENAREANAMEREGION controls GENAREANAME
				valVct.addElement(PokUtils.getAttributeFlagValue(genarea, "GENAREAPARENT"));
				valVct.addElement(PokUtils.getAttributeFlagValue(genarea, "GENAREANAME"));
				lxAbr.addDebug("Searching for CVAR for attrs: "+attrVct+" values: "+valVct);				
				EntityItem eia[]= null;
				try{
					StringBuffer debugSb = new StringBuffer();
					eia = ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
							CVAR_SRCHACTION_NAME, "CVAR", false, attrVct, valVct, debugSb);
					if (debugSb.length()>0){
						lxAbr.addDebug(debugSb.toString());
					}
				}catch(SBRException exc){
					// these exceptions are for missing flagcodes or failed business rules, dont pass back
					java.io.StringWriter exBuf = new java.io.StringWriter();
					exc.printStackTrace(new java.io.PrintWriter(exBuf));
					lxAbr.addDebug("searchForCVARs SBRException: "+exBuf.getBuffer().toString());
				}
				if (eia!=null && eia.length >0){	
					for (int i=0; i<eia.length; i++){
						lxAbr.addDebug("searchForCVARs found "+eia[i].getKey());
					}
					//ERROR_CVAR = CVAR {0} for {1} already exists
					lxAbr.addError("ERROR_CVAR",new Object[]{pnumbct,ctry});	
				}
				valVct.clear();
			} // end COUNTRYELEMENT loop
		}	// end COUNTRYLIST loop	

		attrVct.clear();
	}

	/*****************************************************
	 * 4.	Create VAR
	 * Create CTOVAR based on the parent CTO is the CTO found in step 1.
	 * Create PRVAR based on the parent PR found in step 3.
	 * The attributes of the VAR are supplied via the XML shown in the SS on the 
	 * HVEC_XML tab with details provided on the HVEC Notes tab.
	 * 
	 * 5.	Create references to SBBs
	 * For each instance of <SBBELEMENT> 
	 * Search for SBB using SBBPNUMB = <SBB> and create VARSBB from the VAR created in step 4 to the SBB.
	 * 
	 * 6.	Create CVAR
	 * For each instance of <COUNTRYELEMENT>
	 * Search for CVAR using:
	 * -	PNUMB_CT = <PNUMB_CT>
	 * -	GENAREANAME = <COUNTRY>
	 * If not found, then create a CVAR as a child of the VAR created in step 4 using relator VARCVAR.
	 * The attributes of the CVAR are supplied via the XML shown in the SS on the HVEC_XML tab with 
	 * details provided on the SG Notes tab.
	 *
	 * The CVAR’s GENARENAME is equal to <COUNTRY> and PNUMB_CT is equal to <PNUMB_CT>.
	 * 
	 * 8.	Create Report
	 * A report is created and submitted to Subscription/Notification as follows:
	 * 
	 * Variant Offering Special Bid from LEADS created (LXABRSTATUS)
	 * 
	 * Userid:
	 * Role:
	 * Workgroup:
	 * Date:
	 * Description: Special Bid Created
	 * Return code:
	 * 
	 * Variant <PNUMB> created for CTO <CTOMODEL> with
	 * <SBB> <QTY>
	 * in
	 * <COUNTRY> as <PNUMB_CT> 
	 * 
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#execute()
	 */
	public void execute() 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException
	{
		// 4. create var and link to cto and pr
		EntityItem varItem = createVAR();
		if (varItem != null){
			// 5.	Create references to SBBs
			createSBBRefs(varItem);
			// 6. create CVARs
			createCVARs(varItem);
		}

		if (lxAbr.getReturnCode()==PokBaseABR.PASS){
			// CREATED_VAR_MSG =Variant {0] created for CTO {1} with <br />
			StringBuffer varsb = new StringBuffer();
			varsb.append(lxAbr.getResourceMsg("CREATED_VAR_MSG", new Object[]{pnumb,ctomodel}));

			// list all sbb and qty
			NodeList sbblist = rootElem.getElementsByTagName("SBBLIST");
			for (int x=0; x<sbblist.getLength(); x++){
				Node node = sbblist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("SBBLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList elemlist = sbblist.item(x).getChildNodes(); // SBBELEMENT
				for (int e=0; e<elemlist.getLength(); e++){
					node = elemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("SBBLIST node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element elem = (Element)elemlist.item(e);
					String code = lxAbr.getNodeValue(elem, "SBB");
					String qty = lxAbr.getNodeValue(elem, "QTY","1");
					//REF_SBB_MSG = SBB: {0} Quantity: {1}<br />
					varsb.append(lxAbr.getResourceMsg("REF_SBB_MSG", new Object[]{code,qty}));
				} // end SBBELEMENT loop
			}	// end SBBLIST loop				

			// list all country and cvar
			/*<COUNTRYLIST>	
			<COUNTRYELEMENT>	
			<PNUMB_CT>	</PNUMB_CT>
			<COUNTRY>	</COUNTRY>
				</COUNTRYELEMENT>
				</COUNTRYLIST>
				*/

			varsb.append("&nbsp;&nbsp;&nbsp;in <br />");
			// list all country and pnumbct
			NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
			for (int x=0; x<ctrylist.getLength(); x++){
				Node node = ctrylist.item(x);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				NodeList elemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
				for (int e=0; e<elemlist.getLength(); e++){
					node = elemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
						continue;
					}
					Element elem = (Element)elemlist.item(e);
					String pnumbct = lxAbr.getNodeValue(elem, "PNUMB_CT");
					String ctry = lxAbr.getNodeValue(elem, "COUNTRY");
					//REF_PNUMBCT_MSG= &nbsp;&nbsp;&nbsp;Country: {0} as {1}<br />
					varsb.append(lxAbr.getResourceMsg("REF_PNUMBCT_MSG", new Object[]{ctry,pnumbct}));
				} // end COUNTRYELEMENT loop
			}	// end COUNTRYLIST loop		
			
			lxAbr.addOutput(varsb.toString());
		}
	}

	/*****************************************************
	 * 4.	Create VAR
	 * Create CTOVAR based on the parent CTO is the CTO found in step 1.
	 * Create PRVAR based on the parent PR found in step 3.
	 * The attributes of the VAR are supplied via the XML shown in the SS on the 
	 * HVEC_XML tab with details provided on the HVEC Notes tab.
	 * ALWRSTATUS	S	ALWR Status	Default
	 * OFFERINGPNUMB	T	MTM Part Number	<PNUMB>
	 * UNSPSC	U	UN SPSC Code	"43171806 - Servers" RCQ00028947 pull from CTO
	 * UNUOM	U	UN Unit of Measure	Default
	 * VARABR003	A	VAR ABR 003	Default
	 * VARABR004	A	VAR ABR 004	Default
	 * VARANNOUNCETGT	T	Publish - Target	<PUBFROM>
	 * VARDDABR001	A	Calculate Derived Data	Default
	 * VARDDWWMIABR002	A	Calculate WWMI and Contract Invoice Title	Default
	 * VARMODELNAME	T	Model Name	<TECHDESC>
	 * VARPNUMBDESC	T	Description	<TECHDESC>
	 * VARRATECARDCODE	U	Rate Card Code	"SSM-0080 Medium Servers" RCQ00028947 pull from CTO
	 * VARSPECIALBID	U	Special Bid	"11458" chgd in "SG FS Inbound Feed Leads 20090520b.doc"
	 * VARSTATUS	S	Status	Default
	 *
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private EntityItem createVAR() 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException
	{
		EntityItem var = null;
		// create the var entity with project parent
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("OFFERINGPNUMB");
		attrValTbl.put("OFFERINGPNUMB", pnumb); // OFFERINGPNUMB	T	MTM Part Number	<PNUMB>
		attrCodeVct.addElement("VARMODELNAME");
		attrValTbl.put("VARMODELNAME", techDesc);  //VARMODELNAME	T	Model Name	<TECHDESC>
		attrCodeVct.addElement("VARPNUMBDESC");
		attrValTbl.put("VARPNUMBDESC", techDesc);  //VARPNUMBDESC	T	Description	<TECHDESC>
		attrCodeVct.addElement("VARANNOUNCETGT");
		attrValTbl.put("VARANNOUNCETGT", pubfrom);  //VARANNOUNCETGT	T	Publish - Target	<PUBFROM>
		
		attrCodeVct.addElement("VARSPECIALBID");
		attrValTbl.put("VARSPECIALBID", "11458");  //VARSPECIALBID	U	Special Bid	"11458"
		
		attrCodeVct.addElement("PDHDOMAIN");
		attrValTbl.put("PDHDOMAIN", "PCD"); //PDHDOMAIN	F	eannounce Business Domains	PCD	HVEC
		
		attrCodeVct.addElement("UNSPSC");
		String unspsc = PokUtils.getAttributeFlagValue(ctoItem, "UNSPSC");//RCQ00028947
		if (unspsc==null){
			unspsc = "43171806";
			lxAbr.addDebug(ctoItem.getKey()+" did not have UNSPSC attr meta: "+
					ctoItem.getEntityGroup().getMetaAttribute("UNSPSC"));
		}
		attrValTbl.put("UNSPSC",unspsc);//UNSPSC	U	UN SPSC Code	"43171806 - Servers" =>43171806	43171806 - Servers
		attrCodeVct.addElement("VARRATECARDCODE");
		String rcc = PokUtils.getAttributeFlagValue(ctoItem, "CVOFRATECARDCODE");//RCQ00028947
		if (rcc!=null){			
			rcc = (String)VARRATECARDCODE_TBL.get(rcc);
			lxAbr.addDebug("Mapped CVOFRATECARDCODE "+PokUtils.getAttributeFlagValue(ctoItem, "CVOFRATECARDCODE")+
					" to "+rcc);
		}
		if (rcc==null){
			rcc = "11453";//VARRATECARDCODE	U	Rate Card Code	"SSM-0080 Medium Servers"=>11453	SSM-0080 Medium Servers
			lxAbr.addDebug(ctoItem.getKey()+" did not have CVOFRATECARDCODE attr meta: "+
					ctoItem.getEntityGroup().getMetaAttribute("CVOFRATECARDCODE"));
		}
			
		attrValTbl.put("VARRATECARDCODE",rcc);
		
		StringBuffer debugSb = new StringBuffer();
		var = ABRUtil.createEntity(lxAbr.getDatabase(), lxAbr.getProfile(), VAR_CREATEACTION_NAME, projItem,  
				"VAR", attrCodeVct, attrValTbl, debugSb); 
		if (debugSb.length()>0){
			lxAbr.addDebug(debugSb.toString());
		}
		if (var!=null){
			// link to CTO
			LinkActionItem lai = new LinkActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(),CTOVAR_LINKACTION_NAME);
			EntityItem parentArray[] = new EntityItem[]{ctoItem};
			EntityItem childArray[] = new EntityItem[]{var};
	
			lai.setParentEntityItems(parentArray);     
			lai.setChildEntityItems(childArray);
			lxAbr.getDatabase().executeAction(lxAbr.getProfile(), lai);
			lxAbr.addDebug("Linked "+var.getKey()+" to "+ctoItem.getKey());
		}else {
			lxAbr.addError("ERROR: Can not create VAR entity for pnumb: "+pnumb);
		}
		attrCodeVct.clear();
		attrValTbl.clear();
		
		return var;
	}
	/************************************
	 * 5.	Create references to SBBs
	 * For each instance of <SBBELEMENT> 
	 * Search for SBB using SBBPNUMB = <SBB> and create VARSBB from the VAR created in step 4 to the SBB.
	 * 
	 * @param varItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private void createSBBRefs(EntityItem varItem) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 
	{
		LinkActionItem lai = new LinkActionItem(null, lxAbr.getDatabase(), lxAbr.getProfile(),VARSBB_LINKACTION_NAME);
		EntityItem parentArray[] = new EntityItem[]{varItem};
		EntityItem childArray[] = new EntityItem[sbbVct.size()];

		// get each sbb
		sbbVct.copyInto(childArray);

		// do the link	
		lai.setParentEntityItems(parentArray);     
		lai.setChildEntityItems(childArray);
		lxAbr.getDatabase().executeAction(lxAbr.getProfile(), lai);

		//update dts in profile
		Profile profile = lxAbr.getProfile().getNewInstance(lxAbr.getDatabase());
		String now = lxAbr.getDatabase().getDates().getNow();
		profile.setValOnEffOn(now, now);
		
		// extract and update QTY
		// VE for var to each sbb EXRPT0VAR5
		EntityList list = lxAbr.getDatabase().getEntityList(profile, 
				new ExtractActionItem(null, lxAbr.getDatabase(), profile, "EXRPT0VAR5"), 
				parentArray);

		lxAbr.addDebug("createSBBRefs list using VE EXRPT0VAR5 after link\n"+PokUtils.outputList(list));
		EntityGroup sbbGrp = list.getEntityGroup("SBB");
		for (int x=0; x<sbbGrp.getEntityItemCount(); x++){
			EntityItem sbbitem = sbbGrp.getEntityItem(x);
			String qty = (String)sbbQtyTbl.get(sbbitem.getKey());
			EntityItem varsbbitem = (EntityItem)sbbitem.getUpLink(0);  // get the new relator
			lxAbr.addDebug(sbbitem.getKey()+" use qty: "+qty+" on "+varsbbitem.getKey());
			if (qty!= null && !qty.equals("1")){  // 1 is default so nothing needed
				// save the qty attribute
				StringBuffer sb = new StringBuffer();
				ABRUtil.setText(varsbbitem,"VARSBBQTY", qty,sb); 
				if (sb.length()>0){
					lxAbr.addDebug(sb.toString());
				}
				// must commit changed entity to the PDH 
				varsbbitem.commit(lxAbr.getDatabase(), null);	
			}
		}
		list.dereference();
	}
	
	/************************************
	 * 6.	Create CVAR
	 * For each instance of <COUNTRYELEMENT>
	 * Search for CVAR using:
	 * -	PNUMB_CT = <PNUMB_CT>
	 * -	GENAREANAME = <COUNTRY>
	 * 
	 * @param varItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException 
	 */
	private void createCVARs(EntityItem varItem) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 
	{
		// create a CVAR for each country
		NodeList ctrylist = rootElem.getElementsByTagName("COUNTRYLIST");
		outerloop:for (int x=0; x<ctrylist.getLength(); x++){
			Node node = ctrylist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				//lxAbr.addDebug("COUNTRYLIST node ["+x+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
				continue;
			}
			NodeList elemlist = ctrylist.item(x).getChildNodes(); // COUNTRYELEMENT
			for (int e=0; e<elemlist.getLength(); e++){
				node = elemlist.item(e);
				if (node.getNodeType()!=Node.ELEMENT_NODE){
					//lxAbr.addDebug("COUNTRYELEMENT node ["+e+"] is not an Node.ELEMENT_NODE, it is "+node.getNodeType());
					continue;
				}
				Element elem = (Element)elemlist.item(e);
				String pnumbct = lxAbr.getNodeValue(elem, "PNUMB_CT");
				String ctry = lxAbr.getNodeValue(elem, "COUNTRY");
				lxAbr.addDebug("Creating CVAR for pnumbct "+pnumbct+" ctry "+ctry);
				createCVAR(varItem, pnumbct, ctry);
				if (lxAbr.getReturnCode()!=PokBaseABR.PASS){
					break outerloop;
				}
			} // end COUNTRYELEMENT loop
		}	// end COUNTRYLIST loop		
	}
	/*****************************************************
	 * Create CVAR
	 * create a CVAR as a child of the VAR created in step 4 using relator VARCVAR.
	 * 	The attributes of the CVAR are supplied via the XML shown in the SS on the HVEC_XML tab
	 * with details provided on the SG Notes tab.
	 * 
	 * The CVAR’s GENARENAME is equal to <COUNTRY> and PNUMB_CT is equal to <PNUMB_CT>.
	 * ALWRCVARABR001	A	ALWR With CD ABR Status	Default
	 * ALWRSTATUS	S	ALWR Status	Default
	 * CATALOGNAME_CVAR	F	Audience	RCQ00028947 derive from msg
	 * CVARABR002	A	CVAR002 Check VAR Status	Default
	 * DEMANDFLAG_CVAR	U	Pass to Wisard	"Yes"
	 * GENAREANAME	U	General Area Name	<COUNTRY>
	 * GENAREANAMEREGION	U	Region Name	
	 * PNUMB_CT	T	Part Number	<PNUMB_CT>
	 * PNUMBDESC_CVAR	T	Part Number Description	<TECHDESC>
	 * STATUS_CVAR	S	Status	Default
	 * TARGANNDATE_CVAR	T	Publish - Target	<PUBFROM>
	 * For the HVEC system, PDHDOMAIN is always HVEC
	 *
	 * @param varItem
	 * @param pnumbct
	 * @param ctry
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void createCVAR(EntityItem varItem, String pnumbct, String ctry) throws 
	MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		EntityItem cvar = null;
		// create the cvar entity
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("PNUMB_CT");
		attrValTbl.put("PNUMB_CT", pnumbct); // PNUMB_CT	T	Part Number	<PNUMB_CT>
		attrCodeVct.addElement("PNUMBDESC_CVAR");
		attrValTbl.put("PNUMBDESC_CVAR", techDesc);  //PNUMBDESC_CVAR	T	Part Number Description	<TECHDESC>
		attrCodeVct.addElement("TARGANNDATE_CVAR");
		attrValTbl.put("TARGANNDATE_CVAR", pubfrom);  //TARGANNDATE_CVAR	T	Publish - Target	<PUBFROM>
		attrCodeVct.addElement("DEMANDFLAG_CVAR");
		attrValTbl.put("DEMANDFLAG_CVAR","10681");//DEMANDFLAG_CVAR	U	Pass to Wisard	"Yes"  =>10681
		attrCodeVct.addElement("PDHDOMAIN");
		attrValTbl.put("PDHDOMAIN", "PCD"); //PDHDOMAIN	F	eannounce Business Domains	PCD	HVEC
		
		//CATALOGNAME_CVAR - derived - if null use system defaults
		if (audien!=null){
			attrCodeVct.addElement("CATALOGNAME_CVAR");
			attrValTbl.put("CATALOGNAME_CVAR", audien);
		}
		
		// get the GENERALAREA item for this COUNTRY
		// use the value in <COUNTRY> and search for GENAREACODE, find the entity and use the GENAREANAME for 
		// CVAR.GENAREANAME and the GENAREAPARENT for the CVAR.GENAREANAMEREGION
		EntityItem genarea = searchForGENERALAREA(ctry);
		attrCodeVct.addElement("GENAREANAMEREGION"); // order is important
		attrValTbl.put("GENAREANAMEREGION",PokUtils.getAttributeFlagValue(genarea, "GENAREAPARENT"));//GENAREANAMEREGION	U	Region Name	derive 
		attrCodeVct.addElement("GENAREANAME");
		attrValTbl.put("GENAREANAME",PokUtils.getAttributeFlagValue(genarea, "GENAREANAME"));//GENAREANAME	U	General Area Name	<COUNTRY>  => match flag desc

		StringBuffer debugSb = new StringBuffer();
		cvar = ABRUtil.createEntity(lxAbr.getDatabase(), lxAbr.getProfile(), CVAR_CREATEACTION_NAME, varItem,  
				"CVAR", attrCodeVct, attrValTbl, debugSb); 
		if (debugSb.length()>0){
			lxAbr.addDebug(debugSb.toString());
		}
		if (cvar==null){
			lxAbr.addError("ERROR: Can not create CVAR entity for pnumb: "+pnumb+" ctry "+ctry);
		}else{
			cvar.getEntityGroup().getEntityList().dereference();
		}
		attrCodeVct.clear();
		attrValTbl.clear();		
	}	
	
	/***************************************
	 * Find the flag code for this flag description for the GENAREACODE attribute
	 * @param ctry
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getGenAreaCodeForCtry(String ctry) throws
		MiddlewareRequestException, SQLException, MiddlewareException
	{
		if (GENAREACODE_TBL==null){
			GENAREACODE_TBL = new Hashtable();
			// get meta info
			EntityGroup eg = new EntityGroup(null,lxAbr.getDatabase(), lxAbr.getProfile(), "GENERALAREA", "Edit", false);
			EANMetaFlagAttribute fma =(EANMetaFlagAttribute)eg.getMetaAttribute("GENAREACODE");
			if (fma!=null) {
                for (int i=0; i<fma.getMetaFlagCount(); i++)
                {
                    MetaFlag omf = fma.getMetaFlag(i);
                    if (omf.isExpired()){
                    	lxAbr.getDatabase().debug(D.EBUG_SPEW, "LXLEADSADDVARABR.getGenAreaCodeForCtry skipping expired flag: "+omf+"["+
                    			omf.getFlagCode()+"] for GENERALAREA.GENAREACODE");
                    	continue;
                    }
                    GENAREACODE_TBL.put(omf.toString(), omf.getFlagCode());
                }
            }
		}

		return (String)GENAREACODE_TBL.get(ctry);
	}

	/***********
	 * search GENERALAREA using GENAREACODE. 
	 * 2 character Country Code found in GENERALAREA.GENAREACODE and used to get
	 *  GENAREANAME and GENAREAPARENT
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
	private EntityItem searchForGENERALAREA(String country) throws MiddlewareRequestException, 
			SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem genarea = (EntityItem)generalAreaTbl.get(country);
		if (genarea!= null){
			return genarea;
		}
		
		// HVEC and SG meta is different for GENAREACODE
		String genAreaCodeFlg = getGenAreaCodeForCtry(country);
		lxAbr.addDebug("searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg+"\n");
		lxAbr.getDatabase().debug(D.EBUG_SPEW, "LXLEADSADDVARABR.searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg);
		if (genAreaCodeFlg!= null){
			Vector attrVct = new Vector(1);
			attrVct.addElement("GENAREACODE");
			Vector valVct = new Vector(1);
			valVct.addElement(genAreaCodeFlg);
			EntityItem eia[]= null;
			try{ 
				StringBuffer debugSb = new StringBuffer();
				eia= ABRUtil.doSearch(lxAbr.getDatabase(), lxAbr.getProfile(), 
						GENERALAREA_SRCHACTION_NAME, "GENERALAREA", false, attrVct, valVct, debugSb);
				if (debugSb.length()>0){
					lxAbr.addDebug(debugSb.toString());
				}
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				lxAbr.addDebug("searchForGENERALAREA SBRException: "+exBuf.getBuffer().toString());
			}

			if (eia!=null && eia.length > 0){			
				for (int i=0; i<eia.length; i++){
					lxAbr.addDebug("searchForGENERALAREA found["+i+"] "+eia[i].getKey()+"\n");
				}
				// bad data on lexington box, 2 GENERALAREA for US
				if (eia.length>1){
					StringBuffer sb = new StringBuffer();
					sb.append("More than one GENERALAREA found for "+country);
					for (int i=0; i<eia.length; i++){
						sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
					}
					lxAbr.addError(sb.toString());
				}
				genarea = eia[0];
				generalAreaTbl.put(country, genarea);
			}
			attrVct.clear();
			valVct.clear();
		}else{
			lxAbr.addDebug("searchForGENERALAREA GENAREACODE table: "+GENAREACODE_TBL+"\n");
		}
		
		return genarea;
	}	

	/**
	 * RCQ00028947
	 * Audience -- Use the same set of defaults/guidelines we currently in place for manual data entry:
    If XFS Account Type = Enterprise Direct then Special Bid Audience = LE Direct
    If XFS Account Type = Odyssey then Special Bid Audience = LE Direct (add DAC/MAX if HVEC)
    If XFS Account Type = Indirect then Special Bid Audience = LE Direct, Indirect Reseller, 
                                                               Business Partner
	 * @param msgAudien
	 * @return
	 */
	private void deriveAudien(String XFSAccountType){
		//AUDIEN	F	Audience	"LE Direct"	flag code = 10062
		audien = AUDIEN_TBL.get(XFSAccountType);
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
		pnumb=null;
		ctomodel=null;
		pubfrom=null;
		techDesc = null;
		ctoItem = null;
		projItem = null;

		if (sbbVct != null){
			/*for (int i=0; i<sbbVct.size(); i++)  {
				EntityItem ei = (EntityItem)sbbVct.elementAt(i);
				try{
					EntityList list = ei.getEntityGroup().getEntityList();
					list.dereference();
				}catch(Exception exc){}
			}*/
			sbbVct.clear();
			sbbVct = null;
		}
		// search action is shared in pdgutils, do not derefence the list for now
		/*Iterator itr = generalAreaTbl.values().iterator();
		while(itr.hasNext()){
			try{
				EntityItem ei = (EntityItem)itr.next();
				ei.getEntityGroup().getEntityList().dereference();
			}catch(Exception exc){}
		}*/
		generalAreaTbl.clear();
		generalAreaTbl = null;

		sbbQtyTbl.clear();
		sbbQtyTbl = null;
		if (ctoSbbList!=null){
			ctoSbbList.dereference();
		}
	}
	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion(){
		return "$Revision: 1.10 $";
	}

	/***********************************************
	 *  Get the title
	 *
	 *@return java.lang.String
	 */
	public String getTitle() {
		return "Variant Offering "+LXABRSTATUS.TITLE+(lxAbr.getReturnCode()==PokBaseABR.PASS?" created":"");
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.LXABRInterface#getDescription()
	 */
	public String getDescription() {
		return "Create Special Bid - Variant";
	}
}
