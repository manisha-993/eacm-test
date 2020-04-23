// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
*/
//$Log: ADSLSEOBUNDLEABR.java,v $
//Revision 1.1  2015/02/04 14:55:48  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.25  2013/12/04 01:46:36  guobin
//delete XML Spec sent to dev - Avails RFR Defect: BH 185136 Fix LSEOBUNDEL <STATUS> flag
//
//Revision 1.24  2013/05/14 12:24:58  wangyulo
//WI 945853 - Change mapping for BUNDLETYPE in LSEOBUNDLE_UPDATE XML
//
//Revision 1.23  2013/03/26 14:20:06  wangyulo
//fix defect 910327 -- BH CQ 175643 - Update mapping for BHPRODHIERCD and BHACCTASGNGRP for LSEOBUNDLE_UPDATE xml
//
//Revision 1.22  2012/12/05 14:54:00  wangyulo
//Fix the defect 848608 to correct the mapping for DIVISION
//
//Revision 1.21  2012/06/04 08:26:44  wangyulo
//Fix the Defect 733108 -- Can not get STATUS value of IMAGELIST in LSEOBUNDLE xml
//
//Revision 1.20  2012/01/22 05:32:06  guobin
//RTM work item number on the change is 643541 / BHCQ 81991 Update to XML System Feed Mapping 20120117.doc - correct design for PUBFROM, FIRSTORDER, PUBTO
//
//Revision 1.19  2012/01/18 15:55:29  guobin
//Fix the issue 635138 -- LSEOBUNDLE:
//1. Tag name ACTIVTY should be ACTIVITY for AUDIENCEELEMENT of FBELEMENT tag.
//2. Tag name ACTIVTY should be ACTIVITY for AUDIENCEELEMENT of MMELEMENT tag.
//3. It should show <DIVISION> tag once in xml report.
//
//Revision 1.18  2011/12/14 02:22:45  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.16  2011/07/13 08:20:59  guobin
// //Defect BHALM00057306 Change Mapping to TAXCNTRY
//
//Revision 1.15  2011/03/18 06:13:32  guobin
//correction	118.00	Correct attr name to FBSTATUS
//correction	162.00	Correct attr name to OFFCOUNTRY
//correction	177.00	Correct attr name to DAATTRIBUTEVALUE
//
//Revision 1.14  2011/02/16 03:31:00  guobin
//CQ 31962	15.50. 15.55	Correct mapping for UNSPSC and UNUOM
//
//Revision 1.13  2011/02/15 10:59:49  lucasrg
//Applied mapping updates for DM Cycle 2
//
//Revision 1.12  2011/01/17 13:57:35  guobin
//change ZCONF
//
//Revision 1.11  2010/12/29 06:05:42  guobin
//New added <SLEORGGRPLIST> in <TAXCODE> and  <TAXCATEGORYVALUE>
//
//Revision 1.10  2010/12/20 10:53:35  guobin
//add Catalog Override Defaults
//
//Revision 1.9  2010/11/24 08:25:15  guobin
//Change <TAXCODELIST>  add <COUNTRYLIST>
//
//Revision 1.8  2010/11/22 01:41:41  rick
//changing taxcategorylist to be like it is on MODEL code.
//
//Revision 1.7  2010/11/19 16:30:34  rick
//fix for div
//
//Revision 1.6  2010/11/17 00:20:39  rick
//fixing audience
//
//Revision 1.5  2010/11/08 05:52:21  rick
//Misc changes from last mapping SS.
//
//Revision 1.4  2010/10/29 15:18:05  rick
//changing MQCID again.
//
//Revision 1.3  2010/10/27 22:57:45  rick
//making INSTALL and ORDERCODE empty elements
//the derivation is designed.
//
//Revision 1.2  2010/10/18 23:20:16  rick
//various fixes
//
//Revision 1.1  2010/10/18 18:43:01  rick
//ADS XML ABR for LSEOBUNDLE
//

public class ADSLSEOBUNDLEABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("LSEOBUNDLE_UPDATE");
        XMLMAP.addChild(new XMLVMElem("LSEOBUNDLE_UPDATE","1"));
        // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("SEOID","SEOID"));
        //XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL)); BH FS ABR XML System Feed Mapping 20131106b.doc
        XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("PRFCNTR","PRFTCTR",XMLElem.FLAGVAL));
        //2013-03-13 defect 910317 -- BH CQ 175643 - Update mapping for BHPRODHIERCD and BHACCTASGNGRP for LSEOBUNDLE_UPDATE xml
        XMLMAP.addChild(new XMLElem("BHPRODHIERCD","BHPRODHIERCD"));//flag->Long Description
        XMLMAP.addChild(new XMLElem("BHACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));//flag ->Short Description
        XMLMAP.addChild(new XMLElem("UPCCD","UPCCD"));
        XMLMAP.addChild(new XMLElem("SPECIALBID","SPECBID"));
        XMLMAP.addChild(new XMLElem("PROJECT","PROJCDNAM",XMLElem.FLAGVAL));
        //8/25/2011	LSEOBUNDLE_UPDATE	CR 63555	15.05	Remove OFERCONFIGTYPE attribute        
        //XMLMAP.addChild(new XMLElem("OFERCONFIGTYPE","OFERCONFIGTYPE",XMLElem.FLAGVAL));
        /**
         * CR 63555	15.10--15.25, 18.00	Update mapping to Long Description
         * Change	Map to Long Description	15.10		1	1.0	<WWOCCODE>	</WWOCCODE>	2	LSEOBUNDLE_UPDATE  /WWOCCODE			LSEOBUNDLE	WWOCCODE
         * Change	Map to Long Description	15.15		1	1.0	<SOMFMLY>	</SOMFMLY>	2	LSEOBUNDLE_UPDATE  /SOMFMLY			LSEOBUNDLE	SOMFLMY
         * Change	Map to Long Description	15.20		1	1.0	<PRCINDC>	</PRCINDC>	2	LSEOBUNDLE_UPDATE  /PRCINDC			LSEOBUNDLE	PRCDINC
         * Change	Map to Long Description	15.25		1	1.0	<ZEROPRICE>	</ZEROPRICE>	2	LSEOBUNDLE_UPDATE  /ZEROPRICE			LSEOBUNDLE	ZEROPRICE
         * Change	Solutions CR	15.30		1	1.0	<BPSPECBIDCERTREQ>	</BPSPECBIDCERTREQ>	2	LSEOBUNDLE_UPDATE  /BPSPECBIDCERTREQ			LSEOBUNDLE	BPSPECBIDCERTREQ
        */
        XMLMAP.addChild(new XMLElem("WWOCCODE","WWOCCODE"));//Long Description //the long description of the attribute flag or text value
        XMLMAP.addChild(new XMLElem("SOMFMLY","SOMFMLY"));//Long Description
        XMLMAP.addChild(new XMLElem("PRCINDC","PRCINDC"));//Long Description
        XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));//Long Description        
        XMLMAP.addChild(new XMLElem("BPSPECBIDCERTREQ","BPSPECBIDCERTREQ"));//Long Description
        XMLMAP.addChild(new XMLElem("SVCPACBNDLTYPE","SVCPACBNDLTYPE",XMLElem.FLAGVAL));
        
        // A derivation is needed for ORDERCODE and INSTALL fields
        //XMLElem elemMODEL = new XMLGroupElem(null,"MODEL");
        //XMLMAP.addChild(elemMODEL);
        //elemMODEL.addChild(new XMLElem("ORDERCODE","MODELORDERCODE",XMLElem.FLAGVAL));
        //elemMODEL.addChild(new XMLElem("INSTALL","INSTALL",XMLElem.FLAGVAL));        
        
        //8/18/2011	LSEOBUNDLE_UPDATE	CR 63555	15.40	Remove ORDERCODE attribute
        //Delete	Rescope to Fixed Solutions only	15.40		1	1.0	<ORDERCODE>	</ORDERCODE>	2	LSEOBUNDLE_UPDATE  /ORDERCODE
        //XMLMAP.addChild(new XMLElem("ORDERCODE",""));
        //XMLMAP.addChild(new XMLElem("INSTALL","")); //changed
        /**
         * <INSTALL>
         *  If BUNDLETYPE=’Hardware’ exists, then derive INSTALL as follows:
         *  		Navigate each LSEOBUNDLELSEO –d, WWSEOLSEO –u and MODELWWSEO –u to find parent MODEL.
         *  		If MODEL.COFCAT = “Hardware” (100), set <INSTALL> to MODEL.INSTALL Long Description 
         *     	for NLSID=1 if it exists (else set to null)
         *  Else <INSTALL> is null 
         */
        XMLMAP.addChild(new XMLLSEOBUNDLEINSTALLElem());
        //Change	CQ 31962 - correct mapping	15.50		1	1.0	<UNSPSC>	</UNSPSC>	2	LSEOBUNDLE_UPDATE  /UNSPSC			LSEOBUNDLE	UNSPSCCD
        //Change	CQ 31962 - correct mapping	15.55		1	1.0	<UNUOM>	</UNUOM>	2	LSEOBUNDLE_UPDATE  /UNUOM			LSEOBUNDLE	UNSPSCCDUOM
        XMLMAP.addChild(new XMLElem("UNSPSC","UNSPSCCD"));
        XMLMAP.addChild(new XMLElem("UNUOM","UNSPSCCDUOM"));
        //TODO Defect 945853 Change mapping for BUNDLETYPE in LSEOBUNDLE_UPDATE XML in BH FS ABR XML System Feed Mapping 20130508.doc
        //XMLMAP.addChild(new XMLElem("BUNDLETYPE","BUNDLETYPE"));//Long Description
        XMLMAP.addChild(new XMLBundleTypeElem());
        
        
        
        //Defect 635138 It should show <DIVISION> tag once in xml report
        //Defect 848608 to correct the mapping for DIVISION
        //XMLElem elemSGMNTACRNYM = new XMLGroupElem(null,"SGMNTACRNYM","D:LSEOBUNDLEPROJA:D:PROJ:D:PROJSGMNTACRNYMA:D"); 	
        //TODO RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
        XMLElem elemPROJ = new XMLDistinctGroupElem(null,"PROJ","D:LSEOBUNDLEPROJA:D",true,true);
        XMLMAP.addChild(elemPROJ);
        elemPROJ.addChild(new XMLElem("DIVISION","DIV",XMLElem.FLAGVAL));

        XMLElem langlist = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(langlist);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        langlist.addChild(langelem);
        // level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("MKTGDESC","BUNDLMKTGDESC"));
        langelem.addChild(new XMLElem("INVNAME","PRCFILENAM"));

        // Availabilitylist goes here
        //BH FS ABR Data Transformation System Feed 20110916.doc >> new change
        //1. If LSEOBUNDLE.SPECBID = 11458 (Yes)
        //The description class of LSEO.COUNTRYLIST 
        //change to The description class of LSEOBUNDLE.COUNTRYLIST
        XMLElem availlist = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(availlist);
		availlist.addChild(new XMLLSEOBUNDELAVAILElembh1());

        // start of IMAGELIST structure
        XMLElem list = new XMLGroupElem("IMAGELIST","IMG");
        XMLMAP.addChild(list);
        // level 3
        XMLElem imgelem = new XMLElem("IMAGEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(imgelem);
        // level 4
        imgelem.addChild(new XMLActivityElem("IMAGEACTION"));
        imgelem.addChild(new XMLElem("IMAGEENTITYTYPE","ENTITYTYPE"));
        imgelem.addChild(new XMLElem("IMAGEENTITYID","ENTITYID"));
        //correction	118.00 Correct attr name from STATUS to FBSTATUS
        //correct       55.00  Correct attr name from FBSTATUS to STATUS, it is not FBLIST but IMAGELIST
        imgelem.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        imgelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        imgelem.addChild(new XMLElem("PUBTO","PUBTO"));
        imgelem.addChild(new XMLElem("IMAGEDESCRIPTION","IMGDESC"));
        imgelem.addChild(new XMLElem("MARKETINGIMAGEFILENAME","MKTGIMGFILENAM"));

        //level 4
        list = new XMLElem("COUNTRYLIST");
        imgelem.addChild(list);
        // level 5
        XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of IMAGELIST structure

        // start of MMLIST structure
        list = new XMLGroupElem("MMLIST","MM");
        XMLMAP.addChild(list);
        // level 3
        XMLElem mmelem = new XMLElem("MMELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(mmelem);
        // level 4
        mmelem.addChild(new XMLActivityElem("MMACTION"));
        mmelem.addChild(new XMLElem("MMENTITYTYPE","ENTITYTYPE"));
        mmelem.addChild(new XMLElem("MMENTITYID","ENTITYID"));
        mmelem.addChild(new XMLElem("STATUS","MMSTATUS",XMLElem.FLAGVAL));
        mmelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        mmelem.addChild(new XMLElem("PUBTO","PUBTO"));
        
        list = new XMLElem("MSGLIST");
        mmelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("MSGELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("SHRTMKTGMSG","SHRTMKTGMSG"));
        langelem.addChild(new XMLElem("LONGMKTGMSG","LONGMKTGMSG"));
        //level 4
        list = new XMLElem("AUDIENCELIST");
        mmelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        //level 6
        //Defect 635138 tag name ACTIVTY should be ACTIVITY for AUDIENCEELEMENT of MMELEMENT tag
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","ACTIVITY",XMLElem.ATTRVAL));
	    //level 4
        list = new XMLElem("COUNTRYLIST");
        mmelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of MMLIST structure

        // start of FBLIST structure
        list = new XMLGroupElem("FBLIST","FB");
        XMLMAP.addChild(list);
        // level 3
        XMLElem fbelem = new XMLElem("FBELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(fbelem);
        // level 4
        fbelem.addChild(new XMLActivityElem("FBACTION"));
        fbelem.addChild(new XMLElem("FBENTITYTYPE","ENTITYTYPE"));
        fbelem.addChild(new XMLElem("FBENTITYID","ENTITYID"));
        //      correction	118.00 Correct attr name from STATUS to FBSTATUS
        fbelem.addChild(new XMLElem("STATUS","FBSTATUS",XMLElem.FLAGVAL));
        fbelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        fbelem.addChild(new XMLElem("PUBTO","PUBTO"));
        

        list = new XMLElem("FBSTMTLIST");
        fbelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("FBSTMTELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("FBSTMT","FBSTMT"));
        //level 4
        list = new XMLElem("AUDIENCELIST");
        fbelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        //level 6
        //Defect 635138 tag name ACTIVTY should be ACTIVITY for AUDIENCEELEMENT of FBELEMENT tag
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","ACTIVITY",XMLElem.ATTRVAL));
	    //level 4
        list = new XMLElem("COUNTRYLIST");
        fbelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of FBLIST structure

        // start of TAXCATEGORYLIST structure
        list = new XMLGroupElem("TAXCATEGORYLIST","TAXCATG");
        XMLMAP.addChild(list);
        // level 3
        XMLElem taxelem = new XMLElem("TAXCATEGORYELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(taxelem);
        // level 4
        taxelem.addChild(new XMLActivityElem("TAXCATEGORYACTION"));
        list = new XMLElem("COUNTRYLIST");
        taxelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6 
        //Defect BHALM00057306 Change Mapping to TAXCNTRY
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","TAXCNTRY","COUNTRYACTION",XMLElem.FLAGVAL));
        //level 4
        taxelem.addChild(new XMLElem("TAXCATEGORYVALUE","TAXCATGATR",XMLElem.FLAGVAL));
        XMLElem elemSLNTAXRELEVANCE = new XMLGroupElem(null,"SLNTAXRELEVANCE","U:SLNTAXRELEVANCE");
        taxelem.addChild(elemSLNTAXRELEVANCE);
        elemSLNTAXRELEVANCE.addChild(new XMLElem("TAXCLASSIFICATION","TAXCLS",XMLElem.SHORTDESC));
        //level 4 new added <SLEORGGRPLIST>
        taxelem.addChild(new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));

        list = new XMLGroupElem("TAXCODELIST","TAXGRP");
	    XMLMAP.addChild(list);
	    XMLElem taxcodeelem = new XMLElem("TAXCODEELEMENT");
        list.addChild(taxcodeelem);
        taxcodeelem.addChild(new XMLActivityElem("TAXCODEACTION"));
        taxcodeelem.addChild(new XMLElem("TAXCODEDESCRIPTION","DESC"));
        list = new XMLElem("COUNTRYLIST");
        taxcodeelem.addChild(list);
        XMLElem cntryelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(cntryelem);
        cntryelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        taxcodeelem.addChild(new XMLElem("TAXCODE","TAXCD"));
        //level 4 new added <SLEORGGRPLIST>
        taxcodeelem.addChild(new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
        
        // start of AUDIENCELIST structure
        list = new XMLElem("AUDIENCELIST");
        XMLMAP.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","AUDIEN","AUDIENCEACTION",XMLElem.ATTRVAL));
        // end of AUDIENCELIST structure
        
        //add 20101217 Level 2 Catalog Override Defaults
        XMLMAP.addChild(new XMLZCONFElem());
        
        // start of CATALOGOVERRIDELIST
        list = new XMLElem("CATALOGOVERRIDELIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCATAElem());
        // end of   CATALOGOVERRIDELIST

        // start of CATATTRIBUTELIST
        list = new XMLGroupElem("CATATTRIBUTELIST","CATDATA");
        XMLMAP.addChild(list);
        // level 3
        listelem = new XMLNLSElem("CATATTRIBUTEELEMENT");
        list.addChild(listelem);
        // level 4
        listelem.addChild(new XMLActivityElem("CATATTRIBUTEACTION"));
        listelem.addChild(new XMLElem("CATATTRIBUTE","DAATTRIBUTECODE"));
        listelem.addChild(new XMLElem("NLSID","NLSID"));
        //change from DAATATTRIBUTEVALUE to DAATTRIBUTEVALUE
        listelem.addChild(new XMLElem("CATATTRIBUTEVALUE","DAATTRIBUTEVALUE"));    
        // end of   CATATTRIBUTELIST

        // start of LSEOLIST
        /**
         * CR 63555	181.00, 183.00, 184.00, 185.02, 185.04	Update mapping details for COMPONENTELEMENT attributes 
		 * CR 63555	184.10, 184.20	Remove MACHTYPE and MODEL from COMPONENTELEMENT
         */
         list = new XMLGroupElem("COMPONENTLIST","LSEO");
        XMLMAP.addChild(list);
        // level 3
        XMLElem lseoelem = new XMLElem("COMPONENTELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(lseoelem);
        // level 4
        lseoelem.addChild(new XMLActivityElem("ACTIVITY"));
        lseoelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        lseoelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
        //lseoelem.addChild(new XMLElem("MACHTYPE",""));
        //lseoelem.addChild(new XMLElem("MODEL",""));
        lseoelem.addChild(new XMLElem("SEOID","SEOID"));
        XMLElem elemLSEOBUNDLELSEO = new XMLGroupElem(null,"LSEOBUNDLELSEO","U:LSEOBUNDLELSEO");
        lseoelem.addChild(elemLSEOBUNDLELSEO);
        elemLSEOBUNDLELSEO.addChild(new XMLElem("QTY","LSEOQTY"));
        elemLSEOBUNDLELSEO.addChild(new XMLElem("SEQ","LSEOSEQ"));
        // end of LSEOLIST


    }
        

    /**********************************
    * get xml object mapping
    */
    public XMLElem getXMLMap() {
        return XMLMAP;
    }

	/**********************************
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSLSEOBUNDLE"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "LSEOBUNDLE_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.1 $";//"1.0";
    }
}
