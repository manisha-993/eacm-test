// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.util.Hashtable;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.EntityGroup;


/**********************************************************************************
*/ 
//$Log: ADSLSEOABR.java,v $
//Revision 1.39  2013/12/04 01:44:42  guobin
//delete XML Spec sent to dev - Avails RFR Defect: BH 185136 , fix LSEOABR AVAILIST
//
//Revision 1.38  2013/11/18 15:14:05  guobin
//middleware.jar part of fix for CQ 227988
//
//Revision 1.37  2013/04/12 07:32:19  wangyulo
//Fix the Work Item 923416: There is a tag <ERROR/> instead <PUBFROM> in LSEO xml
//
//Revision 1.36  2012/12/05 14:53:44  wangyulo
//Fix the defect 848608 to correct the mapping for DIVISION
//
//Revision 1.35  2012/04/17 12:46:58  liuweimi
//CQ 97850	1.0	32.00	Change SUBCATEGORY to Short Description
//
//Revision 1.34  2012/02/24 07:05:50  guobin
//[Work Item 663013] Tax sections should be removed from SEO_UPDATE XML
//
//Revision 1.33  2012/01/18 15:54:09  guobin
//Fix the issue 635138 --there should be <ACTIVITY> tag before FBSTMT tag in LSEO xml
//
//Revision 1.32  2012/01/10 13:16:36  guobin
//The defect 634524 for DM4 - SEO_UPDATE has incorrect structure mutiple Models.
//
//Revision 1.31  2011/12/14 02:22:27  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.30  2011/08/30 07:58:08  guobin
//Add WARRLIST for LSEO  change XMLActivityElem  to XMLLSEOActivityElem
//
//Revision 1.29  2011/08/26 01:44:48  guobin
//add WARRLIST
//
//Revision 1.28  2011/07/15 13:07:36  guobin
//Add deletion Action for Replacement offer
//
//Revision 1.27  2011/07/13 11:51:29  liuweimi
//add replacement offer to LSEO
//
//Revision 1.26  2011/06/27 19:27:16  wendy
//Correct mergelists, was dropping EntityGroups and causing memory leaks
//
//Revision 1.25  2011/05/25 03:59:59  guobin
//CQ 31982	Add UNSPSC and UNSPSCUOM (mapped from WWSEO)
//
//Revision 1.24  2011/03/18 06:27:40  guobin
//correction	161.00	Correct attr name to DAATTRIBUTEVALUE
//
//Revision 1.23  2011/02/23 02:07:02  guobin
//Add the association between SLEORGNPLNTCODE and TAXCATG,TAXGRP for special bid LSEO
//  
//Revision 1.22  2011/02/18 13:45:54  guobin
//add the ADSLSEO2 VE for the LSEO
//
//Revision 1.21  2011/02/16 03:25:47  guobin
//CQ 31962	44.20, 44.30	Correct mapping for UNSPSC and UNUOM
//
//Revision 1.20  2011/02/15 10:59:49  lucasrg
//Applied mapping updates for DM Cycle 2
//
//Revision 1.19  2011/02/14 05:34:15  guobin
//change for speicbid LSEO
//
//Revision 1.18  2011/02/11 11:25:16  guobin
//add CATLGOR to MODEL
//
//Revision 1.17  2011/02/10 15:12:34  guobin
//add 20110210 VE for SLEORGNPLNTCODE of specialbid avail of LSEO
//
//Revision 1.16  2011/01/17 13:57:13  guobin
//change ZCONF
//
//Revision 1.15  2010/12/20 10:53:18  guobin
//add Catalog Override Defaults
//
//Revision 1.14  2010/12/06 12:59:02  guobin
//change tag name COUNTRY_FC
//
//Revision 1.13  2010/12/06 12:14:52  guobin
//change Tag name AUDIENCEACATION
//
//Revision 1.12  2010/12/06 07:39:00  guobin
//Updated Tag names
//
//Revision 1.11  2010/11/26 07:47:46  guobin
//Fixes to SEO_UPDATE -- tag names and tag position in XML only.
//
//Revision 1.10  2010/10/29 15:18:05  rick
//changing MQCID again.
//
//Revision 1.9  2010/10/12 19:24:55  rick
//setting new MQCID value
//
//Revision 1.8  2010/08/12 05:04:01  rick
//change to support languagelist with attributecodes from
//LSEO and WWSEO.
//
//Revision 1.7  2010/08/03 17:33:17  rick
//changing WHITEBOXINDC to long description
//
//Revision 1.6  2010/07/21 22:44:50  rick
//change for SWROYALBEARING.
//
//Revision 1.5  2010/07/19 14:45:54  rick
//changes to support FEATURE and SWFEATURE in
//FEATURELIST for LSEO XML.
//
//Revision 1.4  2010/06/24 20:28:31  rick
//BH 1.0 changes
//
//Revision 1.3  2010/06/22 15:43:10  rick
//BH 1.0 interface changes
//
//Revision 1.1  2010/05/19 22:25:20  rick
//adding adsxmlbh1 folder for BH 1.0.
//
//Revision 1.2  2010/03/15 15:47:07  rick
//changing BHACCTASGNGRP and TAXCLS to shortdesc.
//
//Revision 1.1  2010/03/02 02:17:56  rick
//changes for new wave2 package
//
//Revision 1.5  2010/01/28 00:29:47  rick
//change to use BHACCTASGNGRP instead of
// ACCTASGNGRP and LSEO for MQCID.
//
//Revision 1.4  2010/01/07 18:03:03  wendy
//cvs failure again
//
public class ADSLSEOABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SEO_UPDATE");
        XMLMAP.addChild(new XMLVMElem("SEO_UPDATE","1"));
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
        XMLMAP.addChild(new XMLElem("PRICEDIND","PRCINDC"));
        XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));
        XMLMAP.addChild(new XMLElem("PRDHIERCD","BHPRODHIERCD"));
        XMLMAP.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));
        XMLMAP.addChild(new XMLElem("UPCCD","UPCCD"));
 
        XMLElem elemwwseo = new XMLGroupElem(null,"WWSEO");
        XMLMAP.addChild(elemwwseo);
        elemwwseo.addChild(new XMLElem("SWROYALBEARING","SWROYALBEARING"));
        elemwwseo.addChild(new XMLElem("SPECIALBID","SPECBID"));
        elemwwseo.addChild(new XMLElem("SEOORDERCODE","SEOORDERCODE"));
        elemwwseo.addChild(new XMLElem("WWENTITYTYPE","ENTITYTYPE"));
        elemwwseo.addChild(new XMLElem("WWENTITYID","ENTITYID"));
        elemwwseo.addChild(new XMLElem("WWSEOID","SEOID"));
        elemwwseo.addChild(new XMLElem("PROJECT","PROJCDNAM",XMLElem.FLAGVAL));
        

        XMLElem elemweightndimn = new XMLGroupElem(null,"WEIGHTNDIMN");
        elemwwseo.addChild(elemweightndimn);
        elemweightndimn.addChild(new XMLElem("WGHTMTRIC","WGHTMTRIC|WGHTMTRICUNIT"));
        
        //Defect 635138 It should show <DIVISION> tag once in xml report
        //Defect 848608 to correct the mapping for DIVISION                      
        XMLElem elemSGMNTACRNYM = new XMLDistinctGroupElem(null,"SGMNTACRNYM",null,true,true);
        elemwwseo.addChild(elemSGMNTACRNYM);
        elemSGMNTACRNYM.addChild(new XMLElem("DIVISION","DIV",XMLElem.FLAGVAL));

        XMLElem elem = new XMLGroupElem(null,"MODEL","U:WWSEOLSEO:U:WWSEO:U:MODELWWSEO:U");
        XMLMAP.addChild(elem);
        elem.addChild(new XMLElem("PARENTENTITYTYPE","ENTITYTYPE"));
        elem.addChild(new XMLElem("PARENTENTITYID","ENTITYID"));
        elem.addChild(new XMLElem("PARENTMODEL","MODELATR"));
        elem.addChild(new XMLElem("PARENTMACHTYPE","MACHTYPEATR",XMLElem.FLAGVAL));

        elem.addChild(new XMLElem("CATEGORY","COFCAT"));
//        SEO_UPDATE	CQ 97850	1.0	32.00	Change SUBCATEGORY to Short Description
        elem.addChild(new XMLElem("SUBCATEGORY","COFSUBCAT",XMLElem.SHORTDESC));
        elem.addChild(new XMLElem("GROUP","COFGRP"));
        elem.addChild(new XMLElem("SUBGROUP","COFSUBGRP")); 
        //asked by Dave, line 34 in spreadsheet - code puts <WHITEBOXINDC> after <PROJECT>, it should be after <SUBGROUP>
        elemwwseo = new XMLGroupElem(null,"WWSEO");
        XMLMAP.addChild(elemwwseo);
        elemwwseo.addChild(new XMLElem("WHITEBOXINDC","WHITEBOXINDC"));
        
        //Change	CQ 31962 - fix mapping	44.20		1	1.0	<UNSPSC>	</UNSPSC>	2	SEO_UPDATE  /UNSPSC		WWSEOLSEO-u:	WWSEO	UNSPSCCD
        //Change	CQ 31962 - fix mapping	44.30		1	1.0	<UNUOM>	</UNUOM>	2	SEO_UPDATE  /UNUOM		WWSEOLSEO-u:	WWSEO	UNSPSCCDUOM
        elemwwseo.addChild(new XMLElem("UNSPSC","UNSPSCCD"));
        elemwwseo.addChild(new XMLElem("UNUOM","UNSPSCCDUOM"));
        
        XMLElem langlist = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(langlist);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        langlist.addChild(langelem);
        // level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("MKTGDESC","LSEOMKTGDESC"));
        
        elem = new XMLGroupElem(null,"WWSEO");
        langelem.addChild(elem);
        elem.addChild(new XMLLSEOWWSEOLangElem("MKTGNAME","MKTGNAME"));
        elem.addChild(new XMLLSEOWWSEOLangElem("INVNAME","PRCFILENAM"));

        // BH AVAILABILITYLIST Structure       
        XMLElem availlist = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(availlist);
		
		availlist.addChild(new XMLLSEOAVAILElembh1());
		
        // start of IMAGELIST structure
        XMLElem list = new XMLGroupElem("IMAGELIST","IMG");
        XMLMAP.addChild(list);
        // level 3
        XMLElem imgelem = new XMLElem("IMAGEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(imgelem);
        // level 4
        imgelem.addChild(new XMLActivityElem("IMAGEACTION"));
        imgelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        imgelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
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
        mmelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        mmelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
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
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","AUDIENCEACTION"));
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
        fbelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        fbelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
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
        //change There should be <ACTIVITY> tag before FBSTMT tag in LSEO xml(2012-01-12)
        langelem.addChild(new XMLActivityElem("ACTIVITY"));
        langelem.addChild(new XMLElem("FBSTMT","FBSTMT"));
        //level 4
        list = new XMLElem("AUDIENCELIST");
        fbelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","AUDIENCEACTION"));
	    //level 4
        list = new XMLElem("COUNTRYLIST");
        fbelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of FBLIST structure
        
        // AudienceList
        list = new XMLElem("AUDIENCELIST");
        XMLMAP.addChild(list);
        XMLElem audielem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(audielem);
        audielem.addChild(new XMLMultiFlagElem("AUDIENCE","AUDIEN","AUDIENCEACTION"));
        
        //add 20101217 Level 2 Catalog Override Defaults
        XMLMAP.addChild(new XMLZCONFElem());
        
        // BH <CATALOGOVERRIDELIST> Structure       
    	list = new XMLElem("CATALOGOVERRIDELIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCATAElem());
		
		// <CATATTRIBUTELIST>		
        list = new XMLGroupElem("CATATTRIBUTELIST","CATDATA");
        XMLMAP.addChild(list);
        XMLElem cataelem = new XMLElem("CATATTRIBUTEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(cataelem);
        cataelem.addChild(new XMLActivityElem("CATATTRIBUTEACTION"));
        cataelem.addChild(new XMLElem("CATATTRIBUTE","DAATTRIBUTECODE"));
        cataelem.addChild(new XMLElem("NLSID","NLSID"));
        //correction	161.00	Correct attr name to DAATTRIBUTEVALUE
        cataelem.addChild(new XMLElem("CATATTRIUBTEVALUE","DAATTRIBUTEVALUE"));
        // end of <CATATTRIBUTELIST>
        
        // Featurelist 
        list = new XMLDistinctGroupElem("FEATURELIST","FEATURE|SWFEATURE",null,true);
        XMLMAP.addChild(list);
        XMLElem featelem = new XMLElem("FEATUREELEMENT");
        list.addChild(featelem);
        featelem.addChild(new XMLActivityElem("ACTIVITY"));
        featelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        featelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
        featelem.addChild(new XMLElem("FEATURECODE","FEATURECODE"));
        featelem.addChild(new XMLFEATQTYElem("QTY","CONFQTY"));
        // end of Featurelist

        
        /*
         * TAXCATEGORYLIST and TAXCODELIST copied from ADSMODELABR
         * 
         * According to "BH FS ABR Data Transformation System Feed 20110124.doc"
         * 
         * Find the parent MODEL as follows:
         * WWSEOLSEO-u: MODELWWSEO-u
         */
        //XMLElem modelElem = new XMLGroupElem(null,"MODEL");
        //XMLMAP.addChild(modelElem);
        
        //TAXCATEGORYLIST
        
//		list = new XMLGroupElem("TAXCATEGORYLIST","TAXCATG");
//		XMLMAP.addChild(list);
//		// level 3
//        XMLElem taxelem = new XMLElem("TAXCATEGORYELEMENT");//check for chgs is controlled by XMLGroupElem
//        list.addChild(taxelem);
//        // level 4
//        taxelem.addChild(new XMLActivityElem("TAXCATEGORYACTION"));
//        //level 4
//        list = new XMLElem("COUNTRYLIST");
//        taxelem.addChild(list);
//        // level 5
//        listelem = new XMLChgSetElem("COUNTRYELEMENT");
//        list.addChild(listelem);
//        //level 6
//        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
//        //level 4
//        taxelem.addChild(new XMLElem("TAXCATEGORYVALUE","TAXCATGATR",XMLElem.FLAGVAL));
//        XMLElem elemSVCMODTAXRELEVANCE = new XMLGroupElem(null,"MODTAXRELEVANCE","U:MODTAXRELEVANCE");
//        taxelem.addChild(elemSVCMODTAXRELEVANCE);
//        elemSVCMODTAXRELEVANCE.addChild(new XMLElem("TAXCLASSIFICATION","TAXCLS",XMLElem.SHORTDESC));        
//        taxelem.addChild(new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
        //End of TAXCATEGORYLIST
        
        //TAXCODELIST
//        list = new XMLGroupElem("TAXCODELIST","TAXGRP");
//        XMLMAP.addChild(list);
//		//1.0 <TAXCODEELEMENT> level 3
//        XMLElem taxcodeelem = new XMLElem("TAXCODEELEMENT");
//        list.addChild(taxcodeelem);
//        //1.0	<TAXCODEACTION>	</TAXCODEACTION>	4
//        taxcodeelem.addChild(new XMLActivityElem("TAXCODEACTION"));
//        //1.0	<TAXCODEDESCRIPTION>	</TAXCODEDESCRIPTION>
//        taxcodeelem.addChild(new XMLElem("TAXCODEDESCRIPTION","DESC"));
//        //1.0	<DISTRIBUTIONCHANNEL>	</DISTRIBUTIONCHANNEL>	4
//        //taxcodeelem.addChild(new XMLElem("DISTRIBUTIONCHANNEL","DISTRBCHANL"));
//        //1.0	<TAXCODE>	</TAXCODE>	4
//        taxcodeelem.addChild(new XMLElem("TAXCODE","TAXCD"));
//        //level 4
//        list = new XMLElem("COUNTRYLIST");
//        taxcodeelem.addChild(list);
//        // level 5
//        XMLElem cntryelem = new XMLChgSetElem("COUNTRYELEMENT");
//        list.addChild(cntryelem);
//        //level 6
//        cntryelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
//        //level 4 new added <SLEORGGRPLIST>
//        taxcodeelem.addChild(new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
        //End of TAXCODELIST

        // start of WARRLIST structure
        list = new XMLLSEOWARRGroupElem("WARRLIST","WARR");
        XMLMAP.addChild(list);
        // level 3
        XMLElem warrelem = new XMLElem("WARRELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(warrelem);
        // level 4
        warrelem.addChild(new XMLLSEOActivityElem("WARRACTION"));
        warrelem.addChild(new XMLElem("WARRENTITYTYPE","ENTITYTYPE"));
        warrelem.addChild(new XMLElem("WARRENTITYID","ENTITYID"));
        warrelem.addChild(new XMLElem("WARRID","WARRID"));
        //TODO need change 
        XMLElem modelwarrelem = new XMLLSEOWARRGroupElem(null,"MODELWARR|PRODSTRUCTWARR","U:MODELWARR|U:PRODSTRUCTWARR","SPECIAL");
        warrelem.addChild(modelwarrelem);
        modelwarrelem.addChild(new XMLElem("PUBFROM","EFFECTIVEDATE"));
        modelwarrelem.addChild(new XMLElem("PUBTO","ENDDATE"));
        modelwarrelem.addChild(new XMLElem("DEFWARR","DEFWARR"));
        list = new XMLElem("COUNTRYLIST");
        warrelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        XMLElem modelwarr2elem = new XMLLSEOWARRGroupElem(null,"MODELWARR|PRODSTRUCTWARR","U:MODELWARR|U:PRODSTRUCTWARR");
        listelem.addChild(modelwarr2elem);
        //Need to confirm whether exist this attribute <COUNTRY_FC>	</COUNTRY_FC>	6			MODELWARR	COUNTRYLIST
        modelwarr2elem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));     
        // end of WARRLIST structure
       
        //start of <REPLLSEOLIST>
//        Add	CQ 32374	224.00		1	1.0	<REPLLSEOLIST>		2	SEO_UPDATE  /REPLLSEOLIST	
        list = new XMLGroupElem("REPLLSEOLIST","LSEO","D:LSEOREPLLSEO:D",true);
        XMLMAP.addChild(list);
//        Add	CQ 32374 	225.00		0..1	1.0	<REPLLSEOELEMENT>		3	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT		LSEOREPLLSEO-d
        XMLElem repllseoelem = new XMLElem("REPLLSEOELEMENT");
        list.addChild(repllseoelem);
//        Add	CQ 32374 	226.00		1	1.0	<ACTIVITY>	</ACTIVITY>	4	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT /ACTIVITY			derived
        repllseoelem.addChild(new XMLActivityElem("ACTIVITY"));//TODO should be changed to another value
//        Add	CQ 32374 	227.00		1	1.0	<SEOENTITYTYPE>	</SEOENTITYTYPE>	4	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT /SEOENTITYTYPE			LSEO	ENTITYTYPE		Text	"LSEO"
        repllseoelem.addChild(new XMLElem("SEOENTITYTYPE","ENTITYTYPE"));
//        Add	CQ 32374 	228.00		1	1.0	<SEOENTITYID>	</SEOENTITYID>	4	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT /SEOENTITYID			LSEO	ENTITYID		Text	Integer
        repllseoelem.addChild(new XMLElem("SEOENTITYID","ENTITYID"));
//        Add	CQ 32374 	229.00		1	1.0	<SEOID>	</SEOID>	4	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT /SEOID		 	LSEO	SEOID		Text
        repllseoelem.addChild(new XMLElem("SEOID","SEOID"));
//        Add	CQ 32374 	230.00			1.0		</REPLLSEOELEMENT>	3	SEO_UPDATE  /REPLLSEOLIST /REPLLSEOELEMENT																																			
//        Add	CQ 32374 	231.00			1.0		</REPLLSEOLIST>	2	SEO_UPDATE  /REPLLSEOLIST																																			

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
    public String getVeName() { return "ADSLSEO"; }
    
    /**********************************
     * get the name of the VE to use
     */
     public String getVeName2() { return "ADSLSEO2"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SEO_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
    	return "$Revision: 1.39 $";//"1.0";
    }
    /*protected void mergeLists(ADSABRSTATUS abr, EntityList list1, EntityList list2) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    
    {
    	abr.addDebug("Entered ADSLSEOABRSTATUS.mergeLists");
        // we want to merge the VEs ADSLSEO and ADSLSEO2
        // This means moving the MODEL, MODELWWSEO and WWSEO from list2 into list1
    	
    	// get the MODEL group from list2
    	EntityGroup mdlGrp2 = list2.getEntityGroup("MODEL");
    	
    	// get the MODELWWSEO group from list2
    	EntityGroup mdlwwseoGrp2 = list2.getEntityGroup("MODELWWSEO");
    	
    	// get the WWSEO group and item from list1
    	EntityGroup wwseoGrp1 = list1.getEntityGroup("WWSEO");
    	EntityItem wwseo1 = wwseoGrp1.getEntityItem(0); 
    	    	    	    	
        // get the WWSEO group and item from list2
    	EntityGroup wwseoGrp2 = list2.getEntityGroup("WWSEO");
    	EntityItem wwseo2 = wwseoGrp2.getEntityItem(0); 
    	
    	//add 20110210 VE for SLEORGNPLNTCODE of specialbid avail of LSEO
    	//not only merge in the LSEO ABR, but also need add the corresponding in the XMLMQAdapter.java
    	EntityGroup MODELAVAILGrp2 = list2.getEntityGroup("MODELAVAIL");
    	EntityGroup AVAILGrp2 = list2.getEntityGroup("AVAIL");
    	EntityGroup AVAILANNAGrp2 = list2.getEntityGroup("AVAILANNA");
    	EntityGroup ANNOUNCEMENTGrp2 = list2.getEntityGroup("ANNOUNCEMENT");
    	EntityGroup AVAILSLEORGAGrp2 = list2.getEntityGroup("AVAILSLEORGA");
    	EntityGroup SLEORGNPLNTCODEGrp2 = list2.getEntityGroup("SLEORGNPLNTCODE");
    	EntityGroup MDLCATLGORGrp2 = list2.getEntityGroup("MDLCATLGOR");
    	EntityGroup CATLGORGrp2 = list2.getEntityGroup("CATLGOR");
    	EntityGroup MODTAXRELEVANCEGrp2 = list2.getEntityGroup("MODTAXRELEVANCE");
    	EntityGroup TAXCATGGrp2 = list2.getEntityGroup("TAXCATG");
    	EntityGroup MODELTAXGRPGrp2 = list2.getEntityGroup("MODELTAXGRP");
    	EntityGroup TAXGRPGrp2 = list2.getEntityGroup("TAXGRP");
    	
    	EntityGroup TAXCATGSLEORGAGrp2 = list2.getEntityGroup("TAXCATGSLEORGA");
    	EntityGroup TAXGRPSLEORGAGrp2 = list2.getEntityGroup("TAXGRPSLEORGA");
    	//add 20110210 end    	
    	    	
    	// put the MODEL group in list1
    	list1.putEntityGroup(mdlGrp2);
    	
    	// remove the MODEL group from list2
    	list2.removeEntityGroup(mdlGrp2);
    	
    	// put the MODELWWSEO group in list1
    	list1.putEntityGroup(mdlwwseoGrp2);
    	
    	// remove the MODELWWSEO group in list2
    	list2.removeEntityGroup(mdlwwseoGrp2);
    	
    	//add 20110210 put and remove group
    	//add the null condition to avoid nullpointer exception
    	if(MODELAVAILGrp2!=null) list1.putEntityGroup(MODELAVAILGrp2);    	
    	if(AVAILGrp2!=null) list1.putEntityGroup(AVAILGrp2);    	
    	if(AVAILANNAGrp2!=null)	list1.putEntityGroup(AVAILANNAGrp2);    	
    	if(ANNOUNCEMENTGrp2!=null) list1.putEntityGroup(ANNOUNCEMENTGrp2);    	
    	if(AVAILSLEORGAGrp2!=null) list1.putEntityGroup(AVAILSLEORGAGrp2);    	
    	if(SLEORGNPLNTCODEGrp2!=null) list1.putEntityGroup(SLEORGNPLNTCODEGrp2);
    	if(MDLCATLGORGrp2!=null) list1.putEntityGroup(MDLCATLGORGrp2);
    	if(CATLGORGrp2!=null) list1.putEntityGroup(CATLGORGrp2);   	
    	if(MODTAXRELEVANCEGrp2!=null) list1.putEntityGroup(MODTAXRELEVANCEGrp2);
    	if(TAXCATGGrp2!=null) list1.putEntityGroup(TAXCATGGrp2);
    	if(MODELTAXGRPGrp2!=null) list1.putEntityGroup(MODELTAXGRPGrp2);
    	if(TAXGRPGrp2!=null) list1.putEntityGroup(TAXGRPGrp2);
    	if(TAXCATGSLEORGAGrp2!=null) list1.putEntityGroup(TAXCATGSLEORGAGrp2);
    	if(TAXGRPSLEORGAGrp2!=null) list1.putEntityGroup(TAXGRPSLEORGAGrp2);
    	
    	if(MODELAVAILGrp2!=null) list2.removeEntityGroup(MODELAVAILGrp2);
    	if(AVAILGrp2!=null) list2.removeEntityGroup(AVAILGrp2);
    	if(AVAILANNAGrp2!=null) list2.removeEntityGroup(AVAILANNAGrp2);
    	if(ANNOUNCEMENTGrp2!=null) list2.removeEntityGroup(ANNOUNCEMENTGrp2);
    	if(AVAILSLEORGAGrp2!=null) list2.removeEntityGroup(AVAILSLEORGAGrp2);
    	if(SLEORGNPLNTCODEGrp2!=null) list2.removeEntityGroup(SLEORGNPLNTCODEGrp2); 
    	if(MDLCATLGORGrp2!=null) list2.removeEntityGroup(MDLCATLGORGrp2);
    	if(CATLGORGrp2!=null) list2.removeEntityGroup(CATLGORGrp2);
    	if(MODTAXRELEVANCEGrp2!=null) list2.removeEntityGroup(MODTAXRELEVANCEGrp2);
    	if(TAXCATGGrp2!=null) list2.removeEntityGroup(TAXCATGGrp2);
    	if(MODELTAXGRPGrp2!=null) list2.removeEntityGroup(MODELTAXGRPGrp2);
    	if(TAXGRPGrp2!=null) list2.removeEntityGroup(TAXGRPGrp2);
    	if(TAXCATGSLEORGAGrp2!=null) list2.removeEntityGroup(TAXCATGSLEORGAGrp2);
    	if(TAXGRPSLEORGAGrp2!=null) list2.removeEntityGroup(TAXGRPSLEORGAGrp2);
    	//add 20110210 end
    	
    	// remove the WWSEO group from list1
    	list1.removeEntityGroup(wwseoGrp1);
    	
    	// remove all downlinks from wwseo2
    	if (wwseo2 != null) {
    	EntityItem down_item_array[] = new EntityItem[wwseo2.getDownLinkCount()];
    	wwseo2.getDownLink().copyInto(down_item_array);
    	for (int j=0; j<down_item_array.length; j++){
        	// Get the entity on the other side of the downlink
			EntityItem down_item = down_item_array[j];
			wwseo2.removeDownLink(down_item);
			down_item_array[j] = null;
    	}
    	down_item_array = null;
    	}
    	
        // Remove downlinks of wwseo1 and put onto wwseo2
    	// also Remove uplinks to wwseo1 and put them onto wwseo2
    	if (wwseo1 != null && wwseo2 != null) {
    	EntityItem down_item_array2[] = new EntityItem[wwseo1.getDownLinkCount()];
    	wwseo1.getDownLink().copyInto(down_item_array2);
    	for (int j=0; j<down_item_array2.length; j++){
        	// Get the entity on the other side of the downlink
			EntityItem down_item = down_item_array2[j];
			wwseo1.removeDownLink(down_item);
			wwseo2.putDownLink(down_item);
			down_item.removeUpLink(wwseo1);
			down_item.putUpLink(wwseo2);
			down_item_array2[j] = null;
		}
    	down_item_array2 = null;
    	}
    	    	    	
    	// put the WWSEO group from list2 into list1
    	list1.putEntityGroup(wwseoGrp2);
    	
    	// remove the WWSEO group from list2
    	list2.removeEntityGroup(wwseoGrp2); 
    	    	
    	// put the WWSEO group from list1 into list2 so that it will be dereferenced
    	list2.putEntityGroup(wwseoGrp1); 
    	
    	// release memory
        list2.dereference();
        

        abr.addDebug("mergeLists:: after merge Extract "+PokUtils.outputList(list1));
                
    }*/
    
    /* (non-Javadoc)
     * @see COM.ibm.eannounce.abr.sg.adsxmlbh1.XMLMQAdapter#mergeLists(COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSABRSTATUS, COM.ibm.eannounce.objects.EntityList, COM.ibm.eannounce.objects.EntityList)
     */
    protected void mergeLists(ADSABRSTATUS abr, EntityList list1, EntityList list2) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	abr.addDebug("Entered ADSLSEOABR call COM.ibm.eannounce.objects.EntityList.mergeLists");

    	COM.ibm.eannounce.objects.EntityList.mergeLists(list1,list2);
    	abr.addDebug("mergeLists:: after merge Extract "+PokUtils.outputList(list1));
    	
    }
//        //loop thru list1 and list2 and collect any items that have the same key
//        Hashtable existTbl = new Hashtable();
//        EntityItem lseoparent = list1.getParentEntityGroup().getEntityItem(0);
//    	existTbl.put(lseoparent.getKey(), lseoparent);
//    	
//        for(int i=0; i<list1.getEntityGroupCount(); i++){
//        	EntityGroup eg = list1.getEntityGroup(i);
//        	EntityGroup eg2= list2.getEntityGroup(eg.getEntityType());
//        	if(eg2!=null){
//        		for(int e=0; e<eg2.getEntityItemCount(); e++){
//        			EntityItem item = eg2.getEntityItem(e);
//        			EntityItem item1 = eg.getEntityItem(item.getKey());
//        			if(item1!=null){
//        				existTbl.put(item1.getKey(), item1);
//        			}
//        		}
//        	}
//        }
//        
//        EntityGroup egArray[] = new EntityGroup[list2.getEntityGroupCount()];
//        for(int i=0; i<list2.getEntityGroupCount(); i++){
//        	EntityGroup eg = list2.getEntityGroup(i);
//        	egArray[i]=eg;
//        }
//        // merge lists by moving entitygroups or entityitems or up and downlinks to existing items
//        for(int i=0; i<egArray.length; i++){
//        	EntityGroup eg = egArray[i];
//        	mergeItems(abr,list1, list2, eg.getEntityType(),existTbl);
//        	egArray[i] = null;
//        }
//       	
//       	// release memory
//        egArray = null;
//       	existTbl.clear();
//       	existTbl = null;
//        list2.dereference();
//        
//        abr.addDebug("mergeLists:: after merge Extract "+PokUtils.outputList(list1));
//    }
//    
//    /**
//     * merge items for specified entitygroup type
//     * @param abr
//     * @param list1
//     * @param list2
//     * @param groupname
//     * @param existTbl
//     */
//    private void mergeItems(ADSABRSTATUS abr,EntityList list1, EntityList list2, String groupname, Hashtable existTbl){
//        // add all entities from new list2 to first pull list1
//        EntityGroup origGrp = list1.getEntityGroup(groupname);
//        EntityGroup newGrp = list2.getEntityGroup(groupname);
//
//        if(origGrp!=null){
//        	//list1 entitygroup existed 
//        	EntityItem eiArray[] = newGrp.getEntityItemsAsArray();
//        	for (int i=0;i<eiArray.length; i++) {
//        		EntityItem item = eiArray[i];
//        		EntityItem list1Item = origGrp.getEntityItem(item.getKey());
//        		if(list1Item==null){
//        			// put it in the orig list group
//        			origGrp.putEntityItem(item);
//        			// must move metaattr to new group too
//        			item.reassign(origGrp); 
//        			// remove it from new list
//        			newGrp.removeEntityItem(item);
//        			//move any links to existing items
//        			moveLinks(abr,list1Item, item, existTbl);
//        		}else{ 
//        			//entityitem already existed
//        			moveLinks(abr,list1Item, item, existTbl);	
//        		}
//        	}
//        }else{ 
//        	// group did not exist in list1
//        	list2.removeEntityGroup(newGrp);
//        	list1.putEntityGroup(newGrp);
//        	for (int i=0;i<newGrp.getEntityItemCount(); i++) {
//        		EntityItem item = newGrp.getEntityItem(i);
//        		//move any links to existing items
//        		moveLinks(abr,null, item, existTbl);
//        	}
//        }
//    }
//
//    /**
//     * move any links from item2 to item1
//     * @param abr
//     * @param list1Item
//     * @param item2
//     * @param existTbl
//     */
//    private void moveLinks(ADSABRSTATUS abr,EntityItem list1Item, EntityItem item2, Hashtable existTbl)
//    { 
//    	if(list1Item==null){
//        	//item did not exist in list1, the item2 was added to list1 entitygroup
//    		//move any links to list1 items if they already existed
//        	// does any of the item2 links refer to an existing key in list1
//        	if(item2.getUpLinkCount()>0){
//    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
//    			item2.getUpLink().copyInto(upArray);
//    			for (int j=0; j<upArray.length; j++){
//    				EntityItem upItem = upArray[j]; 
//    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
//    				if(upitem1!=null){ // entity existed like MODEL, move up link to it from list2
//    					// remove from list2 item and add to the list1 item
//        				item2.removeUpLink(upItem);
//        				item2.putUpLink(upitem1);
//    				}
//
//    				upArray[j]=null;
//    			}
//    			upArray = null;
//    		}
//    		if(item2.getDownLinkCount()>0){  
//    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
//    			item2.getDownLink().copyInto(downArray);
//    			for (int j=0; j<downArray.length; j++){
//    				EntityItem dnItem = downArray[j]; 
//    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
//    				if(dnitem1!=null){ // entity existed like MODEL, move down link to it from list2
//    					// remove from list2 item and add to the list1 item
//        				item2.removeDownLink(dnItem);
//        				item2.putDownLink(dnitem1);
//    				}
//    				
//    				downArray[j]=null;
//    			}
//    			downArray = null;
//    		}
//    	}else{
//    		//entity already exists in list1, move up and downlinks to list1 from list2 if they dont exist already
//    		if(item2.getUpLinkCount()>0){
//    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
//    			item2.getUpLink().copyInto(upArray);
//    			for (int j=0; j<upArray.length; j++){
//    				EntityItem upItem = upArray[j]; 
//    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
//    				if(upitem1==null){
//    					// remove  from list2  item and add to list1 item
//        				item2.removeUpLink(upItem);
//        				list1Item.putUpLink(upItem);
//    				}
//    	
//    				upArray[j]=null;
//    			}
//    			upArray = null;
//    		}
//    		if(item2.getDownLinkCount()>0){  
//    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
//    			item2.getDownLink().copyInto(downArray);
//    			for (int j=0; j<downArray.length; j++){
//    				EntityItem dnItem = downArray[j]; 
//    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
//    				if(dnitem1==null){
//    					// remove  from list2  item and add to list1 item
//    					item2.removeDownLink(dnItem);
//    					list1Item.putDownLink(dnItem);
//    				}
//    				downArray[j]=null;
//    			}
//    			downArray = null;
//    		}
//    	}
//   }
}
