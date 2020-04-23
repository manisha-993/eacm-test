// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
* used for BH feed and ADSABRSTATUS
*
*/
//$Log: ADSSVCLEVABR.java,v $
//Revision 1.1  2015/02/04 14:55:48  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.15  2012/04/17 12:47:36  liuweimi
//CQ 95374	1.0	10.00	Change FIXTMEOBJIVE to Short Description
//
//Revision 1.14  2011/12/14 02:25:40  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.13  2011/03/21 12:46:05  guobin
//change SLCNTRYCOND entity to multiple
//
//Revision 1.12  2011/03/21 07:29:07  guobin
//change tag and attribute EFFECTIVEDATE of SVCLEV
//
//Revision 1.11  2011/03/18 07:37:15  guobin
//correction	26.00	Correct attr name to NCKNME
//correction	11.00 - 13.00	Correct attrnames to ONSITERESPTME*
//correction	40.00 - 53.00	Correct SLCNTRYCOND attribute names
//
//Revision 1.10  2011/03/18 06:18:06  guobin
//Some tags and attributes are changed from TIME to TME
//
//Revision 1.9  2011/03/16 01:49:47  guobin
//change the meta attribute and tag
//
//Revision 1.8  2011/02/24 09:43:54  guobin
//02/24/2011	SVCLEV_UPDATE	N/A	33.10, 33.20	Add DB Keys for SLCNTRYCONDELEMENT
//
//02/23/2011	SVCLEV_UPDATE	N/A	2.10	Add DTSOFMSG to XML
//			11.00 - 13.00	Update attribute names
//			32.00	Update cardinality for <SLCNTRYCONDELEMENT>
//
//Revision 1.7  2011/02/23 05:14:48  guobin
//change some attribute of the tag
//
//Revision 1.6  2011/02/17 06:39:23  guobin
//change for some meta to tag
//
//Revision 1.5  2011/01/21 12:29:37  guobin
//add comments log
//
// Init for
//  -   BH FS ABR Data Transformation System Feed Mapping - DM Cycle 2 20101217.xls

public class ADSSVCLEVABR extends XMLMQRoot {
	private static final XMLElem XMLMAP;

	static {
		XMLMAP = new XMLGroupElem("SVCLEV_UPDATE");
		XMLMAP.addChild(new XMLVMElem("SVCLEV_UPDATE","1"));
		// level2
		//2.10	Add DTSOFMSG to XML
		XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG"));
		XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
		XMLMAP.addChild(new XMLElem("COVRSHRTDESC", "COVRSHRTDESC"));
		//XML report error: Attribute EFFECTIVEDATE not found in SVCLEV META data.Dave to change XML
		XMLMAP.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
		XMLMAP.addChild(new XMLElem("ENDDATE", "ENDDATE"));
		XMLMAP.addChild(new XMLElem("SVCDELIVMETH", "SVCDELIVMETH"));
		XMLMAP.addChild(new XMLElem("FIXTME", "FIXTME"));
		XMLMAP.addChild(new XMLElem("FIXTMEUOM", "FIXTMEUOM"));
		//SVCLEV_UPDATE	CQ 95374	1.0	10.00	Change FIXTMEOBJIVE to Short Description
		XMLMAP.addChild(new XMLElem("FIXTMEOBJIVE", "FIXTMEOBJIVE",XMLElem.SHORTDESC));
//change:  11.00 - 13.00	Update attribute names
//		The SVCLEV tab needs the following changes which I made and highlighted in the attached SS
//		ONSITERESP       ==> ONSITERESPTME
//		ONSITERESPOBJIVE ==> ONSITERESPTMEOBJIVE
//		ONSITERESPUOM    ==> ONSITERESPTMEUOM
//      tag and attribute are all changed from TIME to TME
		XMLMAP.addChild(new XMLElem("ONSITERESPTME", "ONSITERESPTME"));
		XMLMAP.addChild(new XMLElem("ONSITERESPTMEUOM", "ONSITERESPTMEUOM"));
		XMLMAP.addChild(new XMLElem("ONSITERESPTMEOBJIVE", "ONSITERESPTMEOBJIVE"));
		
		XMLMAP.addChild(new XMLElem("CONTTME", "CONTTME"));
		XMLMAP.addChild(new XMLElem("CONTTMEUOM", "CONTTMEUOM"));
		XMLMAP.addChild(new XMLElem("CONTTMEOBJIVE", "CONTTMEOBJIVE"));
		XMLMAP.addChild(new XMLElem("TRNARNDTME", "TRNARNDTME"));
		XMLMAP.addChild(new XMLElem("TRNARNDTMEUOM", "TRNARNDTMEUOM"));
		XMLMAP.addChild(new XMLElem("TRNARNDTMEOBJIVE", "TRNARNDTMEOBJIVE"));
		XMLMAP.addChild(new XMLElem("PARTARRVTME", "PARTARRVTME"));
		XMLMAP.addChild(new XMLElem("PARTARRVTMEUOM", "PARTARRVTMEUOM"));
		XMLMAP.addChild(new XMLElem("PARTARRVTMEOBJIVE", "PARTARRVTMEOBJIVE"));		
		// level 2 <LANGUAGELIST>
		XMLElem list = new XMLElem("LANGUAGELIST");
		XMLMAP.addChild(list);
		// level 3
		XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
		list.addChild(langelem);
		// level 4
		langelem.addChild(new XMLElem("NLSID", "NLSID"));
		//tag and attribute are change from NCKNAME to NCKNME
		langelem.addChild(new XMLElem("NCKNME", "NCKNME"));
		langelem.addChild(new XMLElem("INVNAME", "INVNAME"));
		langelem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
		// level 2 </LANGUAGELIST>
		
		//<SLCNTRYCONDLIST>
		list = new XMLGroupElem("SLCNTRYCONDLIST","SLCNTRYCOND","D:SVCLEVSLCNTRYCOND:D",true);
		XMLMAP.addChild(list);
        // level 3
        XMLElem eSLCNTRYCONDELEMENT = new XMLElem("SLCNTRYCONDELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eSLCNTRYCONDELEMENT);
        //level 4
        eSLCNTRYCONDELEMENT.addChild(new XMLActivityElem("SLCNTRYCONDACTION"));
        //33.10, 33.20	Add DB Keys for SLCNTRYCONDELEMENT
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        list = new XMLElem("COUNTRYLIST");
        eSLCNTRYCONDELEMENT.addChild(list);
        // level 5
        XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        //level 4
        // The tag and attribute are changed from TIME to TME
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("COVRLNGDESC","COVRLNGDESC"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("MONSTRTTME","MONSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("MONENDTME","MONENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("TUESDYSTRTTME","TUESDYSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("TUESDYENDTME","TUESDYENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("WEDSTRTTME","WEDSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("WEDENDTME","WEDENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("THURSSTRTTME","THURSSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("THURSENDTME","THURSENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("FRISTRTTME","FRISTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("FRIENDTME","FRIENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("SATSTRTTME","SATSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("SATENDTME","SATENDTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("SUNSTRTTME","SUNSTRTTME"));
        eSLCNTRYCONDELEMENT.addChild(new XMLElem("SUNENDTME","SUNENDTME"));
		
	}

	/***************************************************************************
	 * get xml object mapping
	 */
	public XMLElem getXMLMap() {
		return XMLMAP;
	}

	/***************************************************************************
	 * get the name of the VE to use
	 */
	public String getVeName() {
		return "ADSSVCLEV";
	}

	/***************************************************************************
	 * get the status attribute to use for this ABR
	 */
	public String getStatusAttr() {
		return "STATUS";
	}

	/***************************************************************************
	 * 
	 * A. MQ-Series CID
	 */
	public String getMQCID() {
		return "SVCLEV_UPDATE";
	}

	/***************************************************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	public String getVersion() {
		return "$Revision: 1.1 $";
	}
}
