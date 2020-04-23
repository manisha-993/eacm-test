// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.wave2;

import COM.ibm.eannounce.abr.util.*;


/**********************************************************************************
*
<?XML VERSION="1.0" ENCODING="UTF-8"?>		1		
<MODEL_UPDATE>		1	MODEL	
<PDHDOMAIN>	</PDHDOMAIN>	2	MODEL	PDHDOMAIN
<DTSOFMSG>	</DTSOFMSG>	2	MODEL	ABR Queued
<ACTIVITY>	</ACTIVITY>	2	MODEL	Activity
<MODELENTITYTYPE>	</MODELENTITYTYPE>	2	MODEL	ENTITYTYPE
<MODELENTITYID>	</MODELENTITYID>	2	MODEL	ENTITYID
<MACHTYPE>	</MACHTYPE>	2	MODEL	MACHTYPEATR
<MODEL>	</MODEL>	2	MODEL	MODELATR
<STATUS>	</STATUS>	2	MODEL	STATUS
<CATEGORY>	</CATEGORY>	2	MODEL	COFCAT
<SUBCATEGORY>	</SUBCATEGORY>	2	MODEL	COFSUBCAT
<GROUP>	</GROUP>	2	MODEL	COFGRP
<SUBGROUP>	</SUBGROUP>	2	MODEL	COFSUBGRP
<APPLICATIONTYPE>	</APPLICATIONTYPE>	2	MODEL	APPLICATIONTYPE
<ORDERCODE>	</ORDERCODE>	2	MODEL	MODELORDERCODE
<SARINDC>	</SARINDC>	2	MODEL	SARINDC
<PROJECT>	</PROJECT>	2	MODEL	PROJCDNAM
<DIVISION>	</DIVISION>	2	SGMNTACRNYM	DIV
<PRFTCTR>	</PRFTCTR>	2	MODEL	PRFTCTR
<ANNOUNCEDATE>	</ANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE
<ANNOUNCENUMBER>	</ANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER
<WITHDRAWANNOUNCEDATE>	</WITHDRAWANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE
<WITHDRAWANNOUNCENUMBER>	</WITHDRAWANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER
<RATECARD>	</RATECARD>	2	MODEL	RATECARDCD
<UNITCLASS>	</UNITCLASS>	2	MODEL	SYSIDUNIT
<PRICEDIND>	</PRICEDIND>	2	MODEL	PRCINDC
<INSTALL>	</INSTALL>	2	MODEL	INSTALL
<ZEROPRICE>	</ZEROPRICE>	2	MODEL	ZEROPRICE
<UNSPSC>	</UNSPSC>	2	MODEL	UNSPSCCD
<UNSPSCSECONDARY>	</UNSPSCSECONDARY>	2	MODEL	UNSPSCCDSECONDRY
<UNUOM>	</UNUOM>	2	MODEL	UNSPSCCDUOM
<FULFILLMENTSYSIND>	</FULFILLMENTSYSIND>	2	MODEL	FLFILSYSINDC
<MEASUREMETRIC>	</MEASUREMETRIC>	2	WEIGHTNDIMN	WGHTMTRIC
<PRODHIERCD>	</PRODHIERCD>	2	MODEL	BHPRODHIERCD
<ACCTASGNGRP>	</ACCTASGNGRP>		MODEL	BHACCTASGNGRP
<LANGUAGELIST>		2		
<LANGUAGEELEMENT>		3		
<NLSID>	</NLSID>	4	MODEL	NLSID
<MKTGNAME>	</MKTGNAME>	4	MODEL	MKTGNAME
<INVNAME	</INVNAME>	4	MODEL	INVNAME
</LANGUAGEELEMENT>	3		
</LANGUAGELIST>	2		
<AVAILABILITYLIST>		2	 	
<AVIAILABILITYELEMENT>		3		
<AVAILABILITYACTION>	</AVAILABILITYACTION>	4		?
<STATUS>	</STATUS>	4	AVAIL	STATUS
<COUNTRY_FC>	</COUNTRY_FC>	4	AVAIL	COUNTRYLIST
<EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>	4	Derived	ShipDate
<PUBFROM>	</PUBFROM>	4	Derived	PubFrom
<PUBTO>	</PUBTO>	4	Derived	PubTo
<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>	4	AVAIL 	EFFECTIVEDATE
</AVAILABILTYELEMENT>	3		
</AVAILABILITYLIST>	2		
<TAXCATEGORYLIST>		2		
<TAXCATEGORYELEMENT>		3		
<TAXCATEGORYACTION>	</TAXCATEGGORYACTION>	4		
<TAXCATEGORYCOUNTRY>	</TAXCATEGORYCOUNTRY>	4		
<TAXCATEGORYSALESORG>	</TAXCATEGORYSALESORG>	4		
<TAXCATEGORYVALUE>	</TAXCATEGORYVALUE>	4		
<TAXCLASSIFICATION	</TAXCLASSIFICATION>	4		
	</TAXCATEGORYELEMENT>	3		
	</TAXCATEGORYLIST>	2		
<TAXCODELIST>		2		
<TAXCODEELEMENT>		3		
<TAXCODEACTION>	</TAXCODEACTION>	4		
<TAXCODEDESCRIPTION>	</TAXCODEDESCRIPTION>	4		
<TAXCODESALESORG>	</TAXCODESALESORG>	4		
<DISTRIBUTIONCHANNEL>	</DISTRIBUTIONCHANNEL>	4		
<TAXCODE>	</TAXCODE>	4		
	</TAXCODEELEMENT>	3		
	</TAXCODELIST>	2		
<AUDIENCELIST>		2		
<AUDIENCEELEMENT>		3		
<AUDIENCEACTION>	</AUDIENCEACTION>	4		
<AUDIENCE>	</AUDIENCE>	4	MODEL	AUDIEN
	<AUDIENCEELEMENT>	3		
	<AUDIENCELIST>	2		
<CATALOGOVERRIDELIST>		2	 	
<CATALOGOVERRIDEELEMENT>		3		
<CATALOGOVERRIDEACTION>	</CATALOGOVERRIDEACTION>	4		CountryAudienceAction
<AUDIENCE>	</AUDIENCE>	4	MODEL	AUDIEN
<COUNTRY_FC>	</COUNTRY_FC>	4	CATLGOR	COUNTRYLIST
<PUBFROM>	</PUBFROM>	4		
<PUBTO>	</PUBTO>	4		
<ADDTOCART>	</ADDTOCART>	4	CATLGOR	CATADDTOCART
<BUYABLE>	</BUYABLE>	4	CATLGOR	CATBUYABLE
<PUBLISH>	</PUBLISH>	4	CATLGOR	CATPUBLISH
<CUSTOMIZEABLE>	</CUSTOMIZEABLE>	4	CATLGOR	CATCUSTIMIZE
<HIDE>	</HIDE>	4	CATLGOR	CATHIDE
	</CATALOGOVERRIDEELEMENT>	3		
	</CATALOGOVERRIDELIST>	2		
<OSLIST>		2	MODEL	
<OSELEMENT>		3		
<OSACTION>	</OSACTION>	4	MODEL	OSAction
<OSLEVEL>	</OSLEVEL>	4	MODEL	OSLEVEL
	</OSELEMENT>	3		
	</OSLIST>	2		
<MMLIST>		2	MM	
<MMELEMENT>		3		
<MMACTION>	</MMACTION>	4	MM	MMAction
<MMENTITYID>	</MMENTITYID>	4	MM	ENTITYID
<PUBFROM>	</PUBFROM>	4	MM	PUBFROM
<PUBTO>	</PUBTO>	4	MM	PUBTO
<STATUS>	</STATUS>	4	MM	MMSTATUS
<LANGUAGELIST>		4		
<LANGUAGEELEMENT>		5		
<NLSID>	</NLSID>	6	MM	NLSID
<LONGMM>	</LONGMM>	6	MM	LONGMKTGMSG
	</LANGUAGEELEMENT>	5		
	</LANGUAGELIST>	4		
<COUNTRYLIST>		4		
<COUNTRYELEMENT>		5		
<COUNTRYACTION>	</COUNTRYACTION>	6	MM	CountryAction
<COUNTRY_FC>	</COUNTRY_FC>	6	MM	COUNTRYLIST
	</COUNTRYELEMENT>	5		
	</COUNTRYLIST>	4		
<AUDIENCELIST>		4		
<AUDIENCEELEMENT>		5		
<AUDIENCEACTION>	</AUDIENCEACTION>	6	MM	AudienceAction
<AUDIENCE>	</AUDIENCE>	6	MM	CATAUDIENCE
	</AUDIENCEELEMENT>	5		
	</AUDIENCELIST>	4		
<PAGETYPELIST>		4		
<PAGETYPEELEMENT>		5		
<PAGETYPEACTION>	</PAGETYPEACTION>	6	MM	PageTypeAction
<PAGETYPE>	</PAGETYPE>	6	MM	CATPAGETYPE
	</PAGETYPEELEMENT>	5		
	<PAGETYPELIST>	4		
	</MMELEMENT>	3		
	</MMLIST>	2		
<WARRLIST>		2	WARR	
<WARRELEMENT>		3		
<WARRACTION>	</WARRACTION>	4	WARR	WarrantyAction
<WARRENTITYID>	</WARRENTITYID>	4	WARR	WARRENTITYID
<PUBFROM>	</PUBFROM>	4	WARR	PUBFROM
<PUBTO>	</PUBTO>	4	WARR	PUBTO
<WARRPRIOD>	</WARRPRIOD>	4	WARR	WARRPRIOD
<WARRTYPE>	</WARRTYPE>	4	WARR	WARRTYPE
<COUNTRYLIST>		4		
<COUNTRYELEMENT>		5		
<COUNTRYACTION>	</COUNTRYACTION>	6	WARR	CountryAction
<COUNTRY_FC>	</COUNTRY_FC>	6	WARR	COUNTRYLIST
	</COUNTRYELEMENT>	5		
	</COUNTRYLIST>	4		
	</WARRELEMENT>	3		
	</WARRLIST>	2		
<IMAGELIST>		2	IMG	
<IMAGEELEMENT>		3		
<IMAGEACTION>	</IMAGEACTION>	4	IMG	ImageAction
<IMAGEENTITYID>	</IMAGEENTITYID>	4	IMG	IMAGEENTITYID
<STATUS>	</STATUS>	4	IMG	STATUS
<PUBFROM>	</PUBFROM>	4	IMG	PUBFROM
<PUBTO>	</PUBTO>	4	IMG	PUBTO
<IMAGEDESCRIPTION>	</IMAGEDESCRIPTION>	4	IMG	IMGDESC
<MARKETINGIMAGEFILENAME>	</MARKETINGIMAGEFILENAME>	4	IMG	MKTGIMGFILENAM
<COUNTRYLIST>		4		
<COUNTRYELEMENT>		5		
<COUNTRYACTION>	</COUNTRYACTION>	6	IMG	CountryAction
<COUNTRY_FC>	</COUNTRY_FC>	6	IMG	COUNTRYLIST
	</COUNTRYELEMENT>	5		
	</COUNTRYLIST>	4		
	</IMAGEELEMENT>	3		
	</IMAGELIST>	2		
	</MODEL_UPDATE>	1		


*/
// ADSMODELABR.java,v
// Revision 1.3  2009/12/10 14:54:52  yang
// ADSMODELABR update for BH
//
// Revision 1.2  2008/05/28 13:46:08  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.1  2008/04/29 14:31:38  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSMODELABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("MODEL_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("MODELENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("MODELENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("MACHTYPE","MACHTYPEATR"));
        XMLMAP.addChild(new XMLElem("MODEL","MODELATR"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("CATEGORY","COFCAT"));
        XMLMAP.addChild(new XMLElem("SUBCATEGORY","COFSUBCAT"));
        XMLMAP.addChild(new XMLElem("GROUP","COFGRP"));
        XMLMAP.addChild(new XMLElem("SUBGROUP","COFSUBGRP"));
        //XMLMAP.addChild(new XMLElem("APPLICATIONTYPE","APPLICATIONTYPE"));
        //XMLMAP.addChild(new XMLElem("ORDERCODE","MODELORDERCODE",XMLElem.SHORTDESC));
        //XMLMAP.addChild(new XMLElem("SARINDC","SARINDC"));
        //XMLMAP.addChild(new XMLElem("PROJECT","PROJCDNAM",XMLElem.FLAGVAL));
        //XMLElem elem = new XMLGroupElem(null,"PROJ"); //<DIVISION>	</DIVISION>			2	PROJ	DIV
        //XMLMAP.addChild(elem);
        //elem.addChild(new XMLElem("DIVISION","DIV",XMLElem.FLAGVAL));
        // BH PRFTCTR
        XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR",XMLElem.FLAGVAL));
        // BH <ANNOUNCEDATE> <ANNOUNCENUMBER>  2  ANNOUNCEMENT ANNDATE  ANNNUMBER
        // BH <WITHDRAWANNOUNCEDATE> <WITHDRAWANNOUNCENUMBER>  2  ANNOUNCEMENT ANNDATE  ANNNUMBER
        XMLElem annceelem = new XMLANNElem();
        XMLMAP.addChild(annceelem);
    
        
        //XMLMAP.addChild(new XMLElem("RATECARD","RATECARDCD",XMLElem.FLAGVAL));
        //XMLMAP.addChild(new XMLElem("UNITCLASS","SYSIDUNIT"));
        //XMLMAP.addChild(new XMLElem("PRICEDIND","PRCINDC",XMLElem.FLAGVAL));
        //XMLMAP.addChild(new XMLElem("INSTALL","INSTALL"));
        //XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));
        //XMLMAP.addChild(new XMLElem("UNSPSC","UNSPSCCD",XMLElem.FLAGVAL));
        //XMLMAP.addChild(new XMLElem("UNSPSCSECONDARY","UNSPSCCDSECONDRY",XMLElem.FLAGVAL));
        //XMLMAP.addChild(new XMLElem("UNUOM","UNSPSCCDUOM"));
        //XMLMAP.addChild(new XMLElem("FULFILLMENTSYSIND","FLFILSYSINDC"));
        // BH <MEASUREMETRIC> </MEASUREMETRIC> 2 MEASUREMETRIC WGHTMTRIC
        // SG         MODELWEIGHTNDIMN                 MODEL                            WEIGHTNDIMN                      Relator
        XMLElem meaelem = new XMLGroupElem(null,"WEIGHTNDIMN");
        XMLMAP.addChild(meaelem);
        meaelem.addChild(new XMLElem("MEASUREMETRIC","WGHTMTRIC|WGHTMTRICUNIT"));
        
        // BH <PRODHIERCD> </PRODHIERCD> 2 MODEL BHPRODHIERCD
        XMLMAP.addChild(new XMLElem("PRODHIERCD","BHPRODHIERCD"));
        
        // BH <ACCTASGNGRP> </ACCTASGNGRP> 2 MODEL BHACCTASGNGRP flag
        XMLMAP.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));
        
        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);

        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        //langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME"));
        // BH <INVNAME></INVNAME>  4 MODEL INVNAME
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
              
        // BH AVAILABILITYLIST Structure       
    	list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLAVAILElem());
        
		/*
		list = new XMLElem("OSLIST");
        XMLMAP.addChild(list);
        elem = new XMLChgSetElem("OSELEMENT");
        list.addChild(elem);
        elem.addChild(new XMLMultiFlagElem("OSLEVEL","OSLEVEL","OSACTION",XMLElem.FLAGVAL));

        // start of MMLIST structure
        list = new XMLGroupElem("MMLIST","MM");
        XMLMAP.addChild(list);
        // level 3
        XMLElem mmelem = new XMLElem("MMELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(mmelem);
        // level 4
        mmelem.addChild(new XMLActivityElem("MMACTION"));
        mmelem.addChild(new XMLElem("MMENTITYID","ENTITYID"));
        mmelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        mmelem.addChild(new XMLElem("PUBTO","PUBTO"));
        mmelem.addChild(new XMLElem("STATUS","MMSTATUS",XMLElem.FLAGVAL));

        list = new XMLElem("LANGUAGELIST");
        mmelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("LONGMM","LONGMKTGMSG"));
        //level 4
        list = new XMLElem("COUNTRYLIST");
        mmelem.addChild(list);
        // level 5
        XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
		//level 4
        list = new XMLElem("AUDIENCELIST");
        mmelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","AUDIENCEACTION",XMLElem.FLAGVAL));
		//level 4
        list = new XMLElem("PAGETYPELIST");
        mmelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("PAGETYPEELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("PAGETYPE","CATPAGETYPE","PAGETYPEACTION",XMLElem.FLAGVAL));
        // end of MMLIST structure

        // start of WARRLIST structure
        list = new XMLGroupElem("WARRLIST","WARR");
        XMLMAP.addChild(list);
        // level 3
        XMLElem warrelem = new XMLElem("WARRELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(warrelem);
        // level 4
        warrelem.addChild(new XMLActivityElem("WARRACTION"));
        warrelem.addChild(new XMLElem("WARRENTITYID","ENTITYID"));
        warrelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        warrelem.addChild(new XMLElem("PUBTO","PUBTO"));
        warrelem.addChild(new XMLElem("WARRPRIOD","WARRPRIOD",XMLElem.FLAGVAL));
        warrelem.addChild(new XMLElem("WARRTYPE","WARRTYPE",XMLElem.FLAGVAL));

        //level 4
        list = new XMLElem("COUNTRYLIST");
        warrelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of WARRLIST structure

        // start of IMAGELIST structure
        list = new XMLGroupElem("IMAGELIST","IMG");
        XMLMAP.addChild(list);
        // level 3
        XMLElem imgelem = new XMLElem("IMAGEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(imgelem);
        // level 4
        imgelem.addChild(new XMLActivityElem("IMAGEACTION"));
        imgelem.addChild(new XMLElem("IMAGEENTITYID","ENTITYID"));
        imgelem.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        imgelem.addChild(new XMLElem("PUBFROM","PUBFROM"));
        imgelem.addChild(new XMLElem("PUBTO","PUBTO"));
        imgelem.addChild(new XMLElem("IMAGEDESCRIPTION","IMGDESC"));
        imgelem.addChild(new XMLElem("MARKETINGIMAGEFILENAME","MKTGIMGFILENAM"));

        //level 4
        list = new XMLElem("COUNTRYLIST");
        imgelem.addChild(list);
        // level 5
        listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6
        listelem.addChild(new XMLMultiFlagElem("COUNTRY","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        // end of IMAGELIST structure
         * 
         */
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
    public String getVeName() { return "ADSMODEL"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "MODEL"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.3";
    }
}
