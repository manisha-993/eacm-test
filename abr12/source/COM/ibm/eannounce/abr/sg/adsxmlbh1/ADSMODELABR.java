// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
 *
 * <?XML VERSION="1.0" ENCODING="UTF-8"?> 1 <MODEL_UPDATE> 1 MODEL <PDHDOMAIN>
 * </PDHDOMAIN> 2 MODEL PDHDOMAIN <DTSOFMSG> </DTSOFMSG> 2 MODEL ABR Queued
 * <ACTIVITY> </ACTIVITY> 2 MODEL Activity <MODELENTITYTYPE> </MODELENTITYTYPE>
 * 2 MODEL ENTITYTYPE <MODELENTITYID> </MODELENTITYID> 2 MODEL ENTITYID
 * <MACHTYPE> </MACHTYPE> 2 MODEL MACHTYPEATR <MODEL> </MODEL> 2 MODEL MODELATR
 * <STATUS> </STATUS> 2 MODEL STATUS <CATEGORY> </CATEGORY> 2 MODEL COFCAT
 * <SUBCATEGORY> </SUBCATEGORY> 2 MODEL COFSUBCAT <GROUP> </GROUP> 2 MODEL
 * COFGRP <SUBGROUP> </SUBGROUP> 2 MODEL COFSUBGRP <APPLICATIONTYPE>
 * </APPLICATIONTYPE> 2 MODEL APPLICATIONTYPE <ORDERCODE> </ORDERCODE> 2 MODEL
 * MODELORDERCODE <SARINDC> </SARINDC> 2 MODEL SARINDC <PROJECT> </PROJECT> 2
 * MODEL PROJCDNAM <DIVISION> </DIVISION> 2 SGMNTACRNYM DIV <PRFTCTR> </PRFTCTR>
 * 2 MODEL PRFTCTR <RATECARD> </RATECARD> 2 MODEL RATECARDCD <UNITCLASS>
 * </UNITCLASS> 2 MODEL SYSIDUNIT <PRICEDIND> </PRICEDIND> 2 MODEL PRCINDC
 * <INSTALL> </INSTALL> 2 MODEL INSTALL <ZEROPRICE> </ZEROPRICE> 2 MODEL
 * ZEROPRICE <UNSPSC> </UNSPSC> 2 MODEL UNSPSCCD <UNSPSCSECONDARY>
 * </UNSPSCSECONDARY> 2 MODEL UNSPSCCDSECONDRY <UNUOM> </UNUOM> 2 MODEL
 * UNSPSCCDUOM <FULFILLMENTSYSIND> </FULFILLMENTSYSIND> 2 MODEL FLFILSYSINDC
 * <MEASUREMETRIC> </MEASUREMETRIC> 2 WEIGHTNDIMN WGHTMTRIC <PRODHIERCD>
 * </PRODHIERCD> 2 MODEL BHPRODHIERCD <ACCTASGNGRP> </ACCTASGNGRP> MODEL
 * BHACCTASGNGRP <LANGUAGELIST> 2 <LANGUAGEELEMENT> 3 <NLSID> </NLSID> 4 MODEL
 * NLSID <MKTGNAME> </MKTGNAME> 4 MODEL MKTGNAME <INVNAME </INVNAME> 4 MODEL
 * INVNAME </LANGUAGEELEMENT> 3 </LANGUAGELIST> 2 <AVAILABILITYLIST> 2
 * <AVIAILABILITYELEMENT> 3 <AVAILABILITYACTION> </AVAILABILITYACTION> 4 ?
 * <STATUS> </STATUS> 4 AVAIL STATUS <COUNTRY_FC> </COUNTRY_FC> 4 AVAIL
 * COUNTRYLIST <EARLIESTSHIPDATE> </EARLIESTSHIPDATE> 4 Derived ShipDate
 * <PUBFROM> </PUBFROM> 4 Derived PubFrom <PUBTO> </PUBTO> 4 Derived PubTo
 * <ENDOFSERVICEDATE> </ENDOFSERVICEDATE> 4 AVAIL EFFECTIVEDATE
 * </AVAILABILTYELEMENT> 3 </AVAILABILITYLIST> 2 <TAXCATEGORYLIST> 2
 * <TAXCATEGORYELEMENT> 3 <TAXCATEGORYACTION> </TAXCATEGGORYACTION> 4
 * <TAXCATEGORYCOUNTRY> </TAXCATEGORYCOUNTRY> 4 <TAXCATEGORYSALESORG>
 * </TAXCATEGORYSALESORG> 4 <TAXCATEGORYVALUE> </TAXCATEGORYVALUE> 4
 * <TAXCLASSIFICATION </TAXCLASSIFICATION> 4 </TAXCATEGORYELEMENT> 3
 * </TAXCATEGORYLIST> 2 <TAXCODELIST> 2 <TAXCODEELEMENT> 3 <TAXCODEACTION>
 * </TAXCODEACTION> 4 <TAXCODEDESCRIPTION> </TAXCODEDESCRIPTION> 4
 * <TAXCODESALESORG> </TAXCODESALESORG> 4 <DISTRIBUTIONCHANNEL>
 * </DISTRIBUTIONCHANNEL> 4 <TAXCODE> </TAXCODE> 4 </TAXCODEELEMENT> 3
 * </TAXCODELIST> 2 <AUDIENCELIST> 2 <AUDIENCEELEMENT> 3 <AUDIENCEACTION>
 * </AUDIENCEACTION> 4 <AUDIENCE> </AUDIENCE> 4 MODEL AUDIEN <AUDIENCEELEMENT> 3
 * <AUDIENCELIST> 2 <CATALOGOVERRIDELIST> 2 <CATALOGOVERRIDEELEMENT> 3
 * <CATALOGOVERRIDEACTION> </CATALOGOVERRIDEACTION> 4 CountryAudienceAction
 * <AUDIENCE> </AUDIENCE> 4 MODEL AUDIEN <COUNTRY_FC> </COUNTRY_FC> 4 CATLGOR
 * COUNTRYLIST <PUBFROM> </PUBFROM> 4 <PUBTO> </PUBTO> 4 <ADDTOCART>
 * </ADDTOCART> 4 CATLGOR CATADDTOCART <BUYABLE> </BUYABLE> 4 CATLGOR CATBUYABLE
 * <PUBLISH> </PUBLISH> 4 CATLGOR CATPUBLISH <CUSTOMIZEABLE> </CUSTOMIZEABLE> 4
 * CATLGOR CATCUSTIMIZE <HIDE> </HIDE> 4 CATLGOR CATHIDE
 * </CATALOGOVERRIDEELEMENT> 3 </CATALOGOVERRIDELIST> 2 <OSLIST> 2 MODEL
 * <OSELEMENT> 3 <OSACTION> </OSACTION> 4 MODEL OSAction <OSLEVEL> </OSLEVEL> 4
 * MODEL OSLEVEL </OSELEMENT> 3 </OSLIST> 2 <MMLIST> 2 MM <MMELEMENT> 3
 * <MMACTION> </MMACTION> 4 MM MMAction <MMENTITYID> </MMENTITYID> 4 MM ENTITYID
 * <PUBFROM> </PUBFROM> 4 MM PUBFROM <PUBTO> </PUBTO> 4 MM PUBTO <STATUS>
 * </STATUS> 4 MM MMSTATUS <LANGUAGELIST> 4 <LANGUAGEELEMENT> 5 <NLSID> </NLSID>
 * 6 MM NLSID <LONGMM> </LONGMM> 6 MM LONGMKTGMSG </LANGUAGEELEMENT> 5
 * </LANGUAGELIST> 4 <COUNTRYLIST> 4 <COUNTRYELEMENT> 5 <COUNTRYACTION>
 * </COUNTRYACTION> 6 MM CountryAction <COUNTRY_FC> </COUNTRY_FC> 6 MM
 * COUNTRYLIST </COUNTRYELEMENT> 5 </COUNTRYLIST> 4 <AUDIENCELIST> 4
 * <AUDIENCEELEMENT> 5 <AUDIENCEACTION> </AUDIENCEACTION> 6 MM AudienceAction
 * <AUDIENCE> </AUDIENCE> 6 MM CATAUDIENCE </AUDIENCEELEMENT> 5 </AUDIENCELIST>
 * 4 <PAGETYPELIST> 4 <PAGETYPEELEMENT> 5 <PAGETYPEACTION> </PAGETYPEACTION> 6
 * MM PageTypeAction <PAGETYPE> </PAGETYPE> 6 MM CATPAGETYPE </PAGETYPEELEMENT>
 * 5 <PAGETYPELIST> 4 </MMELEMENT> 3 </MMLIST> 2 <WARRLIST> 2 WARR <WARRELEMENT>
 * 3 <WARRACTION> </WARRACTION> 4 WARR WarrantyAction <WARRENTITYID>
 * </WARRENTITYID> 4 WARR WARRENTITYID <PUBFROM> </PUBFROM> 4 WARR PUBFROM
 * <PUBTO> </PUBTO> 4 WARR PUBTO <WARRPRIOD> </WARRPRIOD> 4 WARR WARRPRIOD
 * <WARRTYPE> </WARRTYPE> 4 WARR WARRTYPE <COUNTRYLIST> 4 <COUNTRYELEMENT> 5
 * <COUNTRYACTION> </COUNTRYACTION> 6 WARR CountryAction <COUNTRY_FC>
 * </COUNTRY_FC> 6 WARR COUNTRYLIST </COUNTRYELEMENT> 5 </COUNTRYLIST> 4
 * </WARRELEMENT> 3 </WARRLIST> 2 <IMAGELIST> 2 IMG <IMAGEELEMENT> 3
 * <IMAGEACTION> </IMAGEACTION> 4 IMG ImageAction <IMAGEENTITYID>
 * </IMAGEENTITYID> 4 IMG IMAGEENTITYID <STATUS> </STATUS> 4 IMG STATUS
 * <PUBFROM> </PUBFROM> 4 IMG PUBFROM <PUBTO> </PUBTO> 4 IMG PUBTO
 * <IMAGEDESCRIPTION> </IMAGEDESCRIPTION> 4 IMG IMGDESC <MARKETINGIMAGEFILENAME>
 * </MARKETINGIMAGEFILENAME> 4 IMG MKTGIMGFILENAM <COUNTRYLIST> 4
 * <COUNTRYELEMENT> 5 <COUNTRYACTION> </COUNTRYACTION> 6 IMG CountryAction
 * <COUNTRY_FC> </COUNTRY_FC> 6 IMG COUNTRYLIST </COUNTRYELEMENT> 5
 * </COUNTRYLIST> 4 </IMAGEELEMENT> 3 </IMAGELIST> 2 </MODEL_UPDATE> 1
 * 
 * 
 */
// $Log: ADSMODELABR.java,v $
// Revision 1.49  2019/12/17 08:19:42  xujianbo
// Synchronize the codes between the old and new servers
//
// Revision 1.48  2019/10/23 08:33:01  xujianbo
// Add MT description to Fedcat
//
// Revision 1.47 2017/09/26 11:45:18 wangyul
// Story 1739529 - EACM - Add New Attributes to SW Model for PEP - roll back
//
// Revision 1.45 2017/05/02 09:41:32 wangyul
// 1615427: EACM SPF Feed to PEP - XML Update Activity and fedcat
//
// Revision 1.44 2017/04/12 09:50:29 wangyul
// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
//
// Revision 1.43 2013/11/12 16:03:17 guobin
// delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN-
// 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.42 2013/07/23 07:02:33 wangyulo
// For the RCQ246579 - EACM SWMA Indicator that Add mapping for MODEL.SWMAIND to
// MODEL_UPDATE XML
//
// Revision 1.41 2012/12/13 02:27:36 wangyulo
// Fix the defect 853887 --There are multiple <EFFECTIVEDATE>,<ENDDATE>and
// <UNBUNDTYPE> in MODEL XML
//
// Revision 1.40 2012/12/05 14:54:11 wangyulo
// Fix the defect 848608 to correct the mapping for DIVISION
//
// Revision 1.39 2012/09/20 06:21:12 guobin
// [Work Item 805205] New: Incorrect derivation of MODEL.ANNDATE
//
// Revision 1.38 2012/03/15 08:14:10 liuweimi
// Remove MKTGDESC from MODEL_UPDATE
//
// Revision 1.37 2012/01/17 08:08:36 guobin
// Fixed the CQ 197268 -- map MODEL_UPDATE/SUBCATEGORY from COFSUBCAT short
// description rather than long description
// base on the new document BH FS ABR Data Transformation System Feed Mapping -
// DM4 20120112.xls
// And also fix the part of issues 635138 for the MODEL abr -- Code
// issue,mismatch with XSD,it should show <DIVISION> tag once in xml report for
// the MODEL ABR
//
// Revision 1.36 2011/12/14 02:23:12 guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.35 2011/11/10 10:13:56 liuweimi
// CR 56561 Add Phantom Indicator to model
//
// Revision 1.34 2011/09/19 13:58:36 guobin
// update Mapping of MODEL_UPDATE /DIVISION
//
// Revision 1.33 2011/08/30 08:03:56 guobin
// MODEL TYPEOFWRK Short Description
//
// Revision 1.32 2011/08/23 12:31:31 guobin
// Correct defect - add Default Warranty attr
//
// Revision 1.31 2011/08/08 12:24:47 guobin
// TYPEOFWRK attribute change to Long Description
//
// Revision 1.30 2011/07/15 13:07:21 guobin
// Add deletion Action for Replacement offer
//
// Revision 1.29 2011/07/13 11:52:09 liuweimi
// add replacement offer to MODEL
//
// Revision 1.28 2011/07/13 08:20:05 guobin
// //Defect BHALM00057306 Change Mapping to TAXCNTRY
//
// Revision 1.27 2011/05/25 03:59:32 guobin
// CQ 49247 Add SYSTEMTYPE
// CQ 36618 Add WARRSVCCOVR
// CQ ????? Add PRODSUPRTCD
// CHIS/CEDS Add MACHRATECATG, CECSPRODKEY, MAINTANNBILLELIGINDC,
// NOCHRGMAINTINDC, RETANINDC
//
// Revision 1.26 2011/03/18 06:23:38 guobin
// correction 238.00 Correct attr name to DAATTRIBUTEVALUE
//
// Revision 1.25 2011/02/15 10:59:49 lucasrg
// Applied mapping updates for DM Cycle 2
//
// Revision 1.24 2011/02/12 09:57:35 guobin
// Add MDM 099, MDM 116
//
// Revision 1.23 2011/01/20 07:42:44 guobin
// Add CQ 28348
//
// Revision 1.22 2011/01/17 13:59:16 guobin
// change ZCONF
//
// Revision 1.21 2011/01/14 09:43:04 guobin
// change the ZCONF of model
//
// Revision 1.20 2010/12/29 06:09:08 guobin
// New added <SLEORGGRPLIST> in <TAXCODELIST>
//
// Revision 1.19 2010/12/20 10:53:50 guobin
// add Catalog Override Defaults
//
// Revision 1.18 2010/11/30 02:57:11 guobin
// put the code back in to generate the UNBUNDCOMPLIST
//
// Revision 1.17 2010/11/26 07:45:39 guobin
// commented out <UNBUNDCOMPLIST> which is not ready to test
//
// Revision 1.16 2010/11/02 22:29:21 rick
// making some updates for latest change SS
//
// Revision 1.15 2010/10/29 20:33:53 rick
// adding DUALPIPE element
//
// Revision 1.14 2010/10/29 15:18:05 rick
// changing MQCID again.
//
// Revision 1.13 2010/10/12 19:24:55 rick
// setting new MQCID value
//
// Revision 1.12 2010/09/02 07:23:10 yang
// Commented out DISTRBCHANL on TAXGRP. Add COUNTRYLIST on TAXCATEGORYELEMENT.
//
// Revision 1.11 2010/09/01 02:58:49 yang
// commented out TAXCATEGORYLIST
//
// Revision 1.10 2010/08/18 18:29:59 rick
// removing FULFILLMENTSYSIND.
//
// Revision 1.9 2010/08/06 07:00:28 yang
// Change <MEASUREMETRIC> , get value through Path.
//
// Revision 1.8 2010/08/05 09:37:54 yang
// Change <DIVISION> to find Element through Path.
//
// Revision 1.7 2010/08/03 17:32:47 rick
// removing TAXGRP SLEORG.
//
// Revision 1.6 2010/07/29 14:08:56 yang
// comment out 1.0 <UPCCD> </UPCCD>
//
// Revision 1.5 2010/07/22 09:40:51 yang
// comment <CATALOGOVERRIDELIST>
//
// Revision 1.4 2010/07/13 20:25:37 rick
// changes for BH 1.0 - change STATUS to FBSTATUS,
// comment out UNBUNDCOMPLIST and fix logging
//
// ADSMODELABR.java,v
// Revision 1.3 2009/12/10 14:54:52 yang
// ADSMODELABR update for BH
//
// Revision 1.2 2008/05/28 13:46:08 wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.1 2008/04/29 14:31:38 wendy
// Init for
// - CQ00003539-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// - CQ00005096-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add
// Category MM and Images
// - CQ00005046-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Support CRAD in BHC
// - CQ00005045-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Upgrade/Conversion Support
// - CQ00006862-WI - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSMODELABR extends XMLMQRoot {
	private static final XMLElem XMLMAP;

	static {
		XMLMAP = new XMLGroupElem("MODEL_UPDATE");
		XMLMAP.addChild(new XMLVMElem("MODEL_UPDATE", "1"));
		// level2
		XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
		XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from
																// profile.endofday
		XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
		XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
		XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
		XMLMAP.addChild(new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
		XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
		// XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLElem("CATEGORY", "COFCAT"));
		// Change for CQ 197268 - change mapping of MODEL_UPDATE /SUBCATEGORY to
		// short description 2012-1-13
		XMLMAP.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("GROUP", "COFGRP"));
		XMLMAP.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
		XMLMAP.addChild(new XMLElem("APPLICATIONTYPE", "APPLICATIONTYPE"));
		XMLMAP.addChild(new XMLElem("ORDERCODE", "MODELORDERCODE", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("SARINDC", "SARINDC"));
		XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", XMLElem.FLAGVAL));

		// Defect 635138 It should show <DIVISION> tag once in xml report
		// Defect 848608 to correct the mapping for DIVISION
		XMLElem elem = new XMLDistinctGroupElem(null, "SGMNTACRNYM", "D:MODELSGMTACRONYMA:D", true, true); // <DIVISION>
																											// </DIVISION>
																											// 2
																											// SGMNTACRNYM
																											// DIV
		XMLMAP.addChild(elem);
		elem.addChild(new XMLElem("DIVISION", "DIV", XMLElem.FLAGVAL));
		// BH PRFTCTR
		XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", XMLElem.FLAGVAL));
		// BH <ANNOUNCEDATE> <ANNOUNCENUMBER> 2 ANNOUNCEMENT ANNDATE ANNNUMBER
		// BH <WITHDRAWANNOUNCEDATE> <WITHDRAWANNOUNCENUMBER> 2 ANNOUNCEMENT
		// ANNDATE ANNNUMBER
		// XMLElem annceelem = new XMLANNElem();
		// XMLMAP.addChild(annceelem);

		XMLMAP.addChild(new XMLElem("RATECARD", "RATECARDCD", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLElem("UNITCLASS", "SYSIDUNIT"));
		XMLMAP.addChild(new XMLElem("PRICEDIND", "PRCINDC"));
		XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL"));
		XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
		XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLElem("UNSPSCSECONDARY", "UNSPSCCDSECONDRY", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
		// BH <MEASUREMETRIC> </MEASUREMETRIC> 2 MEASUREMETRIC WGHTMTRIC
		// SG MODELWEIGHTNDIMN MODEL WEIGHTNDIMN Relator
		XMLElem meaelem = new XMLGroupElem(null, "WEIGHTNDIMN", "D:MODELWEIGHTNDIMN:D");
		XMLMAP.addChild(meaelem);
		meaelem.addChild(new XMLElem("MEASUREMETRIC", "WGHTMTRIC|WGHTMTRICUNIT"));

		// 1.0 BH <WARRSVCCOVR_FC> </WARRSVCCOVR_FC> 2 MODEL WARRSVCCOVR
		// Change Change attribute name - CQ 36618 41.20 1 1.0 <WARRSVCCOVR>
		// </WARRSVCCOVR> 2 MODEL_UPDATE /WARRSVCCOVR MODEL WARRSVCCOVR Short
		// Description "None
		// National
		// International" Always
		XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", XMLElem.SHORTDESC));
		// BH <PRODHIERCD> </PRODHIERCD> 2 MODEL BHPRODHIERCD
		XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));

		// 1.0 BH <ACCTASGNGRP> </ACCTASGNGRP> 2 MODEL BHACCTASGNGRP flag
		XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", XMLElem.SHORTDESC));

		// 1.0 BH <AMRTZTNLNGTH> </AMRTZTNLNGTH> 2 MODEL AMRTZTNLNGTH
		XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH", "AMRTZTNLNGTH"));

		// 1.0 BH <AMRTZTNSTRT> </AMRTZTNSTRT> 2 MODEL AMRTZTNLNGTH
		XMLMAP.addChild(new XMLElem("AMRTZTNSTRT", "AMRTZTNSTRT"));

		// 1.0 BH <PRODID> </PRODID> 2 MODEL PRODID Long Description
		XMLMAP.addChild(new XMLElem("PRODID", "PRODID"));

		// 1.0 <SWROYALBEARING> </SWROYALBEARING> 2 MODEL SWROYALBEARING
		XMLMAP.addChild(new XMLElem("SWROYALBEARING", "SWROYALBEARING"));

		// 1.0 <SOMFAMILY> </SOMFAMILY> 2 MODEL SOMFMLY
		XMLMAP.addChild(new XMLElem("SOMFAMILY", "SOMFMLY", XMLElem.FLAGVAL));

		// 1.0 <LIC> </LIC> 2 MODEL LICNSINTERCD
		XMLMAP.addChild(new XMLElem("LIC", "LICNSINTERCD"));

		// 1.0 <BPCERTSPECBID> </BPCERTSPECBID> 2 MODEL BPSPECBIDCERTREQ
		XMLMAP.addChild(new XMLElem("BPCERTSPECBID", "BPSPECBIDCERTREQ"));

		// 1.0 <WWOCCODE> </WWOCCODE> 2 MODEL WWOCCODE
		XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));

		// 1.0 <PRPQINDC> </PRPQINDC> 2 MODEL SPECBID Long Description
		// XMLMAP.addChild(new XMLElem("PRPQINDC","SPECBID")); Replaced by
		// SPECBID + PRPQAPPRVTYPE
		XMLMAP.addChild(new XMLElem("SPECBID", "SPECBID"));
		XMLMAP.addChild(new XMLElem("PRPQAPPRVTYPE", "PRPQAPPRVTYPE"));

		// 1.0 <DUALPIPE> </DUALPIPE> 2 MODEL DUALPIPE Long Description
		XMLMAP.addChild(new XMLElem("DUALPIPE", "DUALPIPE"));
		// Add CQ 28348 56.00 1 1.0 <SVCLEVCD> </SVCLEVCD> 2 MODEL_UPDATE
		// /SVCLEVCD MODEL SVCLEVCD Text
		// Add CQ 28348 56.50 1 1.0 <ENDCUSTIDREQ> </ENDCUSTIDREQ> 2
		// MODEL_UPDATE /ENDCUSTIDREQ MODEL ENDCUSTIDREQ Long Description
		// Add CQ 28348 57.00 1 1.0 <ENTITLMENTSCOPE> </ENTITLMENTSCOPE> 2
		// MODEL_UPDATE /ENTITLMENTSCOPE MODEL ENTITLMENTSCOPE Short Description
		// Add CQ 28348 57.50 1 1.0 <PRORATEDOTCALLOW> </PRORATEDOTCALLOW> 2
		// MODEL_UPDATE /PRORATEDOTCALLOW MODEL PRORATEDOTCALLOW Long
		// Description
		// Add CQ 28348 58.00 1 1.0 <SVCGOODSENTITL> </SVCGOODSENTITL> 2
		// MODEL_UPDATE /SVCGOODSENTITL MODEL SVCGOODSENTITL Short Description
		// Add CQ 28348 58.50 1 1.0 <TYPEOFWRK> </TYPEOFWRK> 2 MODEL_UPDATE
		// /TYPEOFWRK MODEL TYPEOFWRK Short Description
		XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
		XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ", "ENDCUSTIDREQ"));
		XMLMAP.addChild(new XMLElem("ENTITLMENTSCOPE", "ENTITLMENTSCOPE", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW", "PRORATEDOTCALLOW"));
		XMLMAP.addChild(new XMLElem("SVCGOODSENTITL", "SVCGOODSENTITL", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("TYPEOFWRK", "TYPEOFWRK", XMLElem.SHORTDESC));
		// Add MDM 099, MDM 116 56.00 1 1.0 <SDFCD> </SDFCD> 2 MODEL_UPDATE
		// /SDFCD MODEL SDFCD TBD (new attribute) Always
		// Add MDM 099, MDM 116 56.50 1 1.0 <SVCPACMACHBRAND> </SVCPACMACHBRAND>
		// 2 MODEL_UPDATE /SVCPACMACHBRAND MODELSVC -d SVC SVCPACMACHBRAND TBD
		// (new attribute) Always
		// Add MDM 099, MDM 116 57.00 1 1.0 <COVRPRIOD> </COVRPRIOD> 2
		// MODEL_UPDATE /COVRPRIOD MODELSVC -d SVC COVRPRIOD TBD (new attribute)
		// Always
		XMLMAP.addChild(new XMLElem("SDFCD", "SDFCD"));
		XMLElem SVCelem = new XMLGroupElem(null, "SVC", "D:MODELSVC:D");
		XMLMAP.addChild(SVCelem);
		SVCelem.addChild(new XMLElem("SVCPACMACHBRAND", "SVCPACMACHBRAND"));
		SVCelem.addChild(new XMLElem("COVRPRIOD", "COVRPRIOD"));

		// 1.0
		// Add CEDS/CHIS 59.00 1 1.0 <MACHRATECATG> </MACHRATECATG> 2
		// MODEL_UPDATE /MACHRATECATG MODEL MACHRATECATG Text Always
		// Add CEDS/CHIS 59.10 1 1.0 <CECSPRODKEY> </CECSPRODKEY> 2 MODEL_UPDATE
		// /CECSPRODKEY MODEL CECSPRODKEY Long Description? Always
		// Add CEDS/CHIS + ZIPRSX CR 59.20 1 1.0 <PRODSUPRTCD> </PRODSUPRTCD> 2
		// MODEL_UPDATE /PRODSUPRTCD MODEL PRODSUPRTCD Text Always
		// Add CEDS/CHIS 59.30 1 1.0 <MAINTANNBILLELIGINDC>
		// </MAINTANNBILLELIGINDC> 2 MODEL_UPDATE /MAINTANNBILLELIGINDC MODEL
		// MAINTANNBILLELIGINDC Long Description? Always
		// Add CEDS/CHIS 59.40 1 1.0 <NOCHRGMAINTINDC> </NOCHRGMAINTINDC> 2
		// MODEL_UPDATE /NOCHRGMAINTINDC MODEL NOCHRGMAINTINDC Long Desciption?
		// Always
		// Add CEDS/CHIS 59.50 1 1.0 <RETANINDC> </RETANINDC> 2 MODEL_UPDATE
		// /RETANINDC MODEL RETANINDC Short Description? Always
		// Add CR 49247 59.60 1 1.0 <SYSTEMTYPE> </SYSTEMTYPE> 2 MODEL_UPDATE
		// /SYSTEMTYPE MODEL SYSTEMTYPE Text NLSID=1 only Always

		XMLMAP.addChild(new XMLElem("MACHRATECATG", "MACHRATECATG"));
		XMLMAP.addChild(new XMLElem("CECSPRODKEY", "CECSPRODKEY"));
		XMLMAP.addChild(new XMLElem("PRODSUPRTCD", "PRODSUPRTCD"));
		XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC", "MAINTANNBILLELIGINDC"));
		XMLMAP.addChild(new XMLElem("NOCHRGMAINTINDC", "NOCHRGMAINTINDC"));
		XMLMAP.addChild(new XMLElem("RETANINDC", "RETANINDC", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("SYSTEMTYPE", "SYSTEMTYPE"));
		// Add CR 56561 59.62 1.1 1 1.0 <PHANTOMMODINDC> </PHANTOMMODINDC> 2
		// MODEL_UPDATE /PHANTOMMODINDC MODEL PHANTOMMODINDC Short Desc NORM,
		// REACH Always
		XMLMAP.addChild(new XMLElem("PHANTOMMODINDC", "PHANTOMMODINDC", XMLElem.SHORTDESC));
		// Add RCQ246579 - EACM SWMA Indication 59.64 1.1 1 1.0 <SWMAIND>
		// </SWMAIND> 2 MODEL_UPDATE /SWMAIND MODEL SWMAIND Short Desc
		// change the meta from SWMAIND to SWMAINDC
		XMLMAP.addChild(new XMLElem("SWMAIND", "SWMAINDC", XMLElem.SHORTDESC));

		// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
		// <VOLUMEDISCOUNTELIG> </VOLUMEDISCOUNTELIG> 2 MODEL VOLUMEDISCOUNTELIG
		// Long Desciption
		// <IBMCREDIT> </IBMCREDIT> 2 MODEL IBMCREDIT Long Desciption
		XMLMAP.addChild(new XMLElem("VOLUMEDISCOUNTELIG", "VOLUMEDISCOUNTELIG"));
		XMLMAP.addChild(new XMLElem("IBMCREDIT", "IBMCREDIT"));
		
		XMLMAP.addChild(new XMLElem("VENDNAME", "VENDNAME"));
		// 1.0 <UPCCD> </UPCCD> 2 MODEL UPCCD
		// XMLMAP.addChild(new XMLElem("UPCCD","UPCCD"));

		// 1.0 <LENOVOTRNCD> </LENOVOTRNCD> 2 need to confirm entity and
		// attribute Delete noly for SVCMOD
		// .addChild(new XMLElem("LENOVOTRNCD","LENOVOTRNCD"));
		// 0.5 <LANGUAGELIST> 2

		XMLElem list = new XMLElem("LANGUAGELIST");
		XMLMAP.addChild(list);

		// level 3
		XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
		list.addChild(langelem);
		// level 4
		langelem.addChild(new XMLElem("NLSID", "NLSID"));

		// 1.0 <MKTGDESC> </MKTGDESC> 4 MODEL MODMKTGDESC - 68 char
		// langelem.addChild(new XMLElem("MKTGDESC","MODMKTGDESC"));
		// 1.0 <MKTGNAME> </MKTGNAME> 4 MODEL MKTGNAME - 128 char
		langelem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));

		// BH <INVNAME></INVNAME> 4 MODEL INVNAME
		langelem.addChild(new XMLElem("INVNAME", "INVNAME"));
		// langelem.addChild(new XMLMachtypeElem("MTDESCRIPTION",
		// "INTERNALNAME"));
		/*
		 * list = new XMLGroupElem(null, "MACHTYPE"); langelem.addChild(list);
		 * list.addChild(new XMLMachtypeElem("MTDESCRIPTION", "INTERNALNAME"));
		 * list.addChild(new XMLElem("MTDESCRIPTION", "INTERNALNAME"));
		 */
		XMLElem elemMachtype = new XMLMTDGroupElem(null,"MACHTYPE","D:MODELMACHINETYPEA:D");
        langelem.addChild(elemMachtype);
        elemMachtype.addChild(new XMLElem("MTDESCRIPTION", "INTERNALNAME")); 
/*
		XMLElem machtype = new XMLDistinctGroupElem(null, "MACHTYPE", "D:MODELMACHINETYPEA:D", true, true);
		langelem.addChild(machtype);
		machtype.addChild(new XMLElem("MTDESCRIPTION", "INTERNALNAME"));*/

		// Story 2022762 Add MT description to Fedcat Bob
		// langelem.addChild(new
		// XMLMachtypeElem("MTDESCRIPTION","INTERNALNAME"));
		// list=new XMLGroupElem(null,"MACHTYPE");
		// langelem.addChild(list);
		// list.addChild(new XMLMachtypeElem("MTDESCRIPTION","INTERNALNAME"));
		/*
		 * XMLElem emach = new
		 * XMLDistinctGroupElem(null,"MACHTYPE","D:MODELMACHINETYPEA:D",true,
		 * true); //<DIVISION> </DIVISION> 2 SGMNTACRNYM DIV
		 * langelem.addChild(emach); emach.addChild(new
		 * XMLElem("MTDESCRIPTION","INTERNALNAME"));
		 */
		// langelem.addChild(new XMLElem("MTDESCRIPTION","INTERNALNAME"));
		// XMLMAP.addChild(new XMLMachtypeElem("MTDESCRIPTION","INTERNALNAME"));
		// BH AVAILABILITYLIST Structure
		/*list = new XMLGroupElem(null,"MACHTYPE","D:MODELMACHINETYPEA:D");
		XMLMAP.addChild(list);
		//new XML
		list.addChild(new XMLElem("MTDESCRIPTION", "INTERNALNAME"));*/
		list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLAVAILElembh1());

		// 0.5 <TAXCATEGORYLIST> 2
		list = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
		XMLMAP.addChild(list);
		// level 3
		XMLElem taxelem = new XMLElem("TAXCATEGORYELEMENT");// check for chgs is
															// controlled by
															// XMLGroupElem
		list.addChild(taxelem);
		// level 4
		taxelem.addChild(new XMLActivityElem("TAXCATEGORYACTION"));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		taxelem.addChild(list);
		// level 5
		XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		// Defect BHALM00057306 Change Mapping to TAXCNTRY
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", XMLElem.FLAGVAL));
		// leve 4
		taxelem.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", XMLElem.FLAGVAL));
		XMLElem elemSVCMODTAXRELEVANCE = new XMLGroupElem(null, "MODTAXRELEVANCE", "U:MODTAXRELEVANCE");
		taxelem.addChild(elemSVCMODTAXRELEVANCE);
		elemSVCMODTAXRELEVANCE.addChild(new XMLMODELTAXElem("TAXCLASSIFICATION", "TAXCLS", XMLElem.SHORTDESC));
		// level 4 new added <SLEORGGRPLIST>
		taxelem.addChild(new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));

		// 1.0 <TAXCODELIST> 2
		list = new XMLGroupElem("TAXCODELIST", "TAXGRP");
		XMLMAP.addChild(list);
		// 1.0 <TAXCODEELEMENT> level 3
		XMLElem taxcodeelem = new XMLElem("TAXCODEELEMENT");
		list.addChild(taxcodeelem);
		// 1.0 <TAXCODEACTION> </TAXCODEACTION> 4
		taxcodeelem.addChild(new XMLActivityElem("TAXCODEACTION"));
		// 1.0 <TAXCODEDESCRIPTION> </TAXCODEDESCRIPTION>
		taxcodeelem.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
		// 1.0 <DISTRIBUTIONCHANNEL> </DISTRIBUTIONCHANNEL> 4
		// taxcodeelem.addChild(new
		// XMLElem("DISTRIBUTIONCHANNEL","DISTRBCHANL"));
		// 1.0 <TAXCODE> </TAXCODE> 4
		taxcodeelem.addChild(new XMLElem("TAXCODE", "TAXCD"));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		taxcodeelem.addChild(list);
		// level 5
		XMLElem cntryelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(cntryelem);
		// level 6
		cntryelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
		// TODO level 4 new added <SLEORGGRPLIST>
		taxcodeelem.addChild(new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));

		// 1.0 <RELEXPCAMTLIST> 2
				list = new XMLElem("RELEXPCAMTLIST");
				XMLMAP.addChild(list);
				// 1.0 <RELEXPCAMTELEMENT> 3
				XMLElem relexelem = new XMLChgSetElem("RELEXPCAMTELEMENT");
				list.addChild(relexelem);
				// 1.0 <RELEXPCAMTACTION> </RELEXPCAMTACTION> 4
				// 1.0 <RELEXPCAMT> </RELEXPCAMT> 4 MODEL AUDIEN
				relexelem.addChild(new XMLMultiFlagElem("RELEXPCAMT", "RELEXPCAMT", "RELEXPCAMTACTION", XMLElem.ATTRVAL));

		
		// 1.0 <AUDIENCELIST> 2
		list = new XMLElem("AUDIENCELIST");
		XMLMAP.addChild(list);
		// 1.0 <AUDIENCEELEMENT> 3
		XMLElem audielem = new XMLChgSetElem("AUDIENCEELEMENT");
		list.addChild(audielem);
		// 1.0 <AUDIENCEACTION> </AUDIENCEACTION> 4
		// 1.0 <AUDIENCE> </AUDIENCE> 4 MODEL AUDIEN
		audielem.addChild(new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", XMLElem.ATTRVAL));

		// add 20101217 Level 2 Catalog Override Defaults
		XMLMAP.addChild(new XMLZCONFElem());

		// BH <CATALOGOVERRIDELIST> Structure
		list = new XMLElem("CATALOGOVERRIDELIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCATAElem());

		// start of OSLIST structure
		list = new XMLElem("OSLIST");
		XMLMAP.addChild(list);
		elem = new XMLChgSetElem("OSELEMENT");
		list.addChild(elem);
		elem.addChild(new XMLMultiFlagElem("OSLEVEL", "OSLEVEL", "OSACTION", XMLElem.FLAGVAL));

		// start of MMLIST structure
		list = new XMLGroupElem("MMLIST", "MM");
		XMLMAP.addChild(list);
		// level 3
		XMLElem mmelem = new XMLElem("MMELEMENT");// check for chgs is
													// controlled by
													// XMLGroupElem
		list.addChild(mmelem);
		// level 4
		mmelem.addChild(new XMLActivityElem("MMACTION"));
		mmelem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		mmelem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		mmelem.addChild(new XMLElem("PUBFROM", "PUBFROM"));
		mmelem.addChild(new XMLElem("PUBTO", "PUBTO"));
		mmelem.addChild(new XMLElem("STATUS", "MMSTATUS", XMLElem.FLAGVAL));

		list = new XMLElem("LANGUAGELIST");
		mmelem.addChild(list);
		// level 5
		langelem = new XMLNLSElem("LANGUAGEELEMENT");
		list.addChild(langelem);
		// level 6
		langelem.addChild(new XMLElem("NLSID", "NLSID"));
		langelem.addChild(new XMLElem("LONGMM", "LONGMKTGMSG"));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		mmelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
		// level 4
		list = new XMLElem("AUDIENCELIST");
		mmelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("AUDIENCEELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", XMLElem.ATTRVAL));
		// level 4
		list = new XMLElem("PAGETYPELIST");
		mmelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("PAGETYPEELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("PAGETYPE", "CATPAGETYPE", "PAGETYPEACTION", XMLElem.ATTRVAL));
		// end of MMLIST structure

		// start of WARRLIST structure
		list = new XMLGroupElem("WARRLIST", "WARR");
		XMLMAP.addChild(list);
		// level 3
		XMLElem warrelem = new XMLElem("WARRELEMENT");// check for chgs is
														// controlled by
														// XMLGroupElem
		list.addChild(warrelem);
		// level 4
		warrelem.addChild(new XMLActivityElem("WARRACTION"));
		warrelem.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
		warrelem.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
		warrelem.addChild(new XMLElem("WARRID", "WARRID"));

		// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
		// <WARRPRIOD> </WARRPRIOD> 4 WARR WARRPRIOD Long Description
		warrelem.addChild(new XMLElem("WARRPRIOD", "WARRPRIOD"));

		XMLElem modelwarrelem = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
		warrelem.addChild(modelwarrelem);
		modelwarrelem.addChild(new XMLElem("WARRDESC", "INVNAME"));
		modelwarrelem.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
		modelwarrelem.addChild(new XMLElem("PUBTO", "ENDDATE"));
		// TODO Correct defect - add Default Warranty attr
		modelwarrelem.addChild(new XMLElem("DEFWARR", "DEFWARR"));
		list = new XMLElem("COUNTRYLIST");
		warrelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		XMLElem modelwarr2elem = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
		listelem.addChild(modelwarr2elem);
		// Need to confirm whether exist this attribute <COUNTRY_FC>
		// </COUNTRY_FC> 6 MODELWARR COUNTRYLIST
		modelwarr2elem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
		// end of WARRLIST structure

		// start of IMAGELIST structure
		list = new XMLGroupElem("IMAGELIST", "IMG");
		XMLMAP.addChild(list);
		// level 3
		XMLElem imgelem = new XMLElem("IMAGEELEMENT");// check for chgs is
														// controlled by
														// XMLGroupElem
		list.addChild(imgelem);
		// level 4
		imgelem.addChild(new XMLActivityElem("IMAGEACTION"));
		imgelem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		imgelem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		imgelem.addChild(new XMLElem("STATUS", "STATUS", XMLElem.FLAGVAL));
		imgelem.addChild(new XMLElem("PUBFROM", "PUBFROM"));
		imgelem.addChild(new XMLElem("PUBTO", "PUBTO"));
		imgelem.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
		imgelem.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));

		// level 4
		list = new XMLElem("COUNTRYLIST");
		imgelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
		// end of IMAGELIST structure

		// start of FBLIST structure
		list = new XMLGroupElem("FBLIST", "FB");
		XMLMAP.addChild(list);
		// level 3
		XMLElem fbelem = new XMLElem("FBELEMENT");// check for chgs is
													// controlled by
													// XMLGroupElem
		list.addChild(fbelem);
		// level 4
		// need to confirm that the FBACTION is derived from FBACTION or
		// ImageAction?
		fbelem.addChild(new XMLActivityElem("FBACTION"));
		fbelem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		fbelem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		fbelem.addChild(new XMLElem("STATUS", "FBSTATUS", XMLElem.FLAGVAL));
		fbelem.addChild(new XMLElem("PUBFROM", "PUBFROM"));
		fbelem.addChild(new XMLElem("PUBTO", "PUBTO"));
		// <FBSTMTLIST> 4
		XMLElem fbstmtelem = new XMLElem("FBSTMTLIST");
		fbelem.addChild(fbstmtelem);
		// level 5 <FBSTMTELEMENT>
		listelem = new XMLNLSElem("FBSTMTELEMENT");
		fbstmtelem.addChild(listelem);
		// level 6
		listelem.addChild(new XMLElem("NLSID", "NLSID"));
		// Need to confirm this duplicated element derived from FBACTION
		// listelem.addChild(new XMLActivityElem("ACTIVITY"));
		// Need to confirm attribute FBSTMT X
		listelem.addChild(new XMLElem("FBSTMT", "FBSTMT"));
		// <AUDIENCELIST> 4
		XMLElem fbauditelem = new XMLElem("AUDIENCELIST");
		fbelem.addChild(fbauditelem);
		listelem = new XMLChgSetElem("AUDIENCEELEMENT");
		fbauditelem.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", XMLElem.ATTRVAL));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		fbelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
		// end of FBLIST structure

		// <CATATTRIBUTELIST> 2
		list = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
		XMLMAP.addChild(list);
		// level 3
		XMLElem cataelem = new XMLNLSElem("CATATTRIBUTEELEMENT");// check for
																	// chgs is
																	// controlled
																	// by
																	// XMLGroupElem
		list.addChild(cataelem);
		// level 4 <CATATTRIBUTEACTION>
		cataelem.addChild(new XMLActivityElem("CATATTRIBUTEACTION"));
		// level 4 <CATATTRIBUTE> </CATATTRIBUTE> 4 CATDATA DAATTRIBUTECODE
		// Need to confirm the type of this attribute
		// <NLSID> </NLSID> 4 CATDATA NLSID
		// <CATATTRIUBTEVALUE> </CATATTRIUBTEVALUE> 4 CATDATA DAATATTRIBUTEVALUE

		cataelem.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
		cataelem.addChild(new XMLElem("NLSID", "NLSID"));
		// correction 238.00 Correct attr name to DAATTRIBUTEVALUE
		cataelem.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
		// end of <CATATTRIBUTELIST>

		// start of <UNBUNDCOMPLIST> structure
		list = new XMLGroupElem("UNBUNDCOMPLIST", "REVUNBUNDCOMP");
		XMLMAP.addChild(list);
		// level 3
		XMLElem unbundelem = new XMLElem("UNBUNDCOMPELEMENT");// check for chgs
																// is controlled
																// by
																// XMLGroupElem
		list.addChild(unbundelem);
		// level 4
		unbundelem.addChild(new XMLActivityElem("UNBUNDCOMPACTION"));
		unbundelem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		unbundelem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		unbundelem.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
		// XMLElem modbunbundelem = new
		// XMLGroupElem(null,"MODUNBUND","U:MODUNBUND");
		XMLElem modbunbundelem = new XMLDistinctGroupElem(null, "MODUNBUND", "U:MODUNBUND", true, true);
		unbundelem.addChild(modbunbundelem);
		modbunbundelem.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
		modbunbundelem.addChild(new XMLElem("ENDDATE", "ENDDATE"));
		modbunbundelem.addChild(new XMLElem("UNBUNDTYPE", "UNBUNDTYPE"));
		// commented out <UNBUNDCOMPLIST>, this tag is not ready to test.
		// list = new XMLElem("UNBUNDCOMPLIST");
		// XMLMAP.addChild(list);

		// start of <REPLMODELLIST>
		// <REPLMODELLIST> 2 MODEL_UPDATE /REPLMODELLIST MODELREPLMODEL -d
		list = new XMLGroupElem("REPLMODELLIST", "MODEL", "D:MODELREPLMODEL:D", true);
		XMLMAP.addChild(list);
		// <REPLMODELELEMENT> 3 MODEL_UPDATE /REPLMODELLIST /REPLMODELELEMENT
		XMLElem replmodelelem = new XMLElem("REPLMODELELEMENT");
		list.addChild(replmodelelem);
		// <ACTIVITY> </ACTIVITY> 3 MODEL_UPDATE /REPLMODELLIST /ACTIVITY MODEL
		// ENTITYTYPE Text
		replmodelelem.addChild(new XMLActivityElem("ACTIVITY"));// TODO should
																// be changed to
																// another value
		// <MODELENTITYTYPE> </MODELENTITYTYPE> 4 MODEL_UPDATE /REPLMODELLIST
		// /REPLMODELELEMENT /MODELENTITYTYPE MODEL ENTITYTYPE Text
		replmodelelem.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
		// <MODELENTITYID> </MODELENTITYID> 4 MODEL_UPDATE /REPLMODELLIST
		// /REPLMODELELEMENT /MODELENTITYID MODEL ENTITYID Text
		replmodelelem.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
		// <MACHTYPE> </MACHTYPE> 4 MODEL_UPDATE /REPLMODELLIST
		// /REPLMODELELEMENT /MACHTYPE MODEL MACHTYPEATR Flag value
		replmodelelem.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
		// <MODEL> </MODEL> 4 MODEL_UPDATE /REPLMODELLIST /REPLMODELELEMENT
		// /MODEL MODEL MODELATR Text
		replmodelelem.addChild(new XMLElem("MODEL", "MODELATR"));
		// </REPLMODELELEMENT> 3 MODEL_UPDATE /REPLMODELLIST /REPLMODELELEMENT
		// </REPLMODELLIST> 2 MODEL_UPDATE /REPLMODELLIST

		// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
		// <GEOMODLIST> 2 MODELGEOMOD -d
		// <GEOMODELEMENT> 3
		// <GEOMODACTION> </GEOMODACTION> 4
		// <ENTITYTYPE> </ENTITYTYPE> 4 GEOMOD ENTITYTYPE
		// <ENTITYID> </ENTITYID> 4 GEOMOD ENTITYID
		// <ANNUALMAINT> </ANNUALMAINT> 4 GEOMOD ANNUALMAINT Long Description
		// <EDUCALLOWMHGHSCH> </EDUCALLOWMHGHSCH> 4 GEOMOD EDUCALLOWMHGHSCH text
		// <EDUCALLOWMSECONDRYSCH> </EDUCALLOWMSECONDRYSCH> 4 GEOMOD
		// EDUCALLOWMSECONDRYSCH text
		// <EDUCALLOWMUNVRSTY> </EDUCALLOWMUNVRSTY> 4 GEOMOD EDUCALLOWMUNVRSTY
		// text
		// <EDUCPURCHELIG> </EDUCPURCHELIG> 4 GEOMOD EDUCPURCHELIG Long
		// Description
		// <EMEABRANDCD> </EMEABRANDCD> 4 GEOMOD EMEABRANDCD text
		// <NOCHRGRENT> </NOCHRGRENT> 4 GEOMOD NOCHRGRENT Long Description
		// <PERCALLCLS> </PERCALLCLS> 4 GEOMOD PERCALLCLS Long Description
		// <PLNTOFMFR> </PLNTOFMFR> 4 GEOMOD PLNTOFMFR Short Desc?
		// <PURCHONLY> </PURCHONLY> 4 GEOMOD PURCHONLY Long Description
		// <SYSTYPE> </SYSTYPE> 4 GEOMOD SYSTYPE
		// <INTEGRATEDMODEL> </INTEGRATEDMODEL> 4 GEOMOD INTEGRATEDMODEL Long
		// Description
		// <GRADUATEDCHARGE> </GRADUATEDCHARGE> 4 GEOMOD GRADUATEDCHARGE Short
		// Desc?
		// <METHODPROD> </METHODPROD> 4 GEOMOD METHODPROD Long Description
		// <COUNTRYLIST> 4
		// <COUNTRYELEMENT> 5
		// <COUNTRYACTION> </COUNTRYACTION> 6
		// <COUNTRY_FC> </COUNTRY_FC> 6 GEOMOD COUNTRYLIST Flag Description
		// Class
		// </COUNTRYELEMENT> 5
		// </COUNTRYLIST> 4
		// </GEOMODELEMENT> 3
		// </GEOMODLIST> 2
		list = new XMLGroupElem("GEOMODLIST", "GEOMOD");
		XMLMAP.addChild(list);
		XMLElem geoModElem = new XMLElem("GEOMODELEMENT");
		list.addChild(geoModElem);
		geoModElem.addChild(new XMLActivityElem("GEOMODACTION"));
		geoModElem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		geoModElem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		geoModElem.addChild(new XMLElem("ANNUALMAINT", "ANNUALMAINT"));
		geoModElem.addChild(new XMLElem("EDUCALLOWMHGHSCH", "EDUCALLOWMHGHSCH"));
		geoModElem.addChild(new XMLElem("EDUCALLOWMSECONDRYSCH", "EDUCALLOWMSECONDRYSCH"));
		geoModElem.addChild(new XMLElem("EDUCALLOWMUNVRSTY", "EDUCALLOWMUNVRSTY"));
		geoModElem.addChild(new XMLElem("EDUCPURCHELIG", "EDUCPURCHELIG"));
		geoModElem.addChild(new XMLElem("EMEABRANDCD", "EMEABRANDCD"));
		geoModElem.addChild(new XMLElem("NOCHRGRENT", "NOCHRGRENT"));
		geoModElem.addChild(new XMLElem("PERCALLCLS", "PERCALLCLS"));
		geoModElem.addChild(new XMLElem("PLNTOFMFR", "PLNTOFMFR", XMLElem.SHORTDESC));
		geoModElem.addChild(new XMLElem("PURCHONLY", "PURCHONLY"));
		geoModElem.addChild(new XMLElem("SYSTYPE", "SYSTYPE"));
		geoModElem.addChild(new XMLElem("INTEGRATEDMODEL", "INTEGRATEDMODEL"));
		geoModElem.addChild(new XMLElem("GRADUATEDCHARGE", "GRADUATEDCHARGE", XMLElem.SHORTDESC));
		geoModElem.addChild(new XMLElem("METHODPROD", "METHODPROD"));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		geoModElem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));

		// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
		// <COMPATMODELLIST> 2 MDLCGMDL -u MDLCGMDLCGOS -d MDLCGOSMDL-d
		// <COMPATMODELELEMENT> 3
		// <COMPATMODELACTION> </COMPATMODELACTION> 4
		// <ENTITYTYPE> </ENTITYTYPE> 4 MODEL ENTITYTYPE
		// <ENTITYID> </ENTITYID> 4 MODEL ENTITYID
		// <MACHTYPE> </MACHTYPE> 4 MODEL MACHTYPEATR Flag Description Class
		// <MODEL> </MODEL> 4 MODEL MODELATR Text
		// </COMPATMODELELEMENT> 3
		// </COMPATMODELLIST> 2
		list = new XMLGroupElem("COMPATMODELLIST", "MODEL",
				"U:MDLCGMDL:U:MODELCG:D:MDLCGMDLCGOS:D:MODELCGOS:D:MDLCGOSMDL:D");
		XMLMAP.addChild(list);
		XMLElem compatModelElem = new XMLElem("COMPATMODELELEMENT");
		list.addChild(compatModelElem);
		compatModelElem.addChild(new XMLActivityElem("COMPATMODELACTION"));
		compatModelElem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		compatModelElem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		compatModelElem.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
		compatModelElem.addChild(new XMLElem("MODEL", "MODELATR"));

		list = new XMLGroupElem("STDMAINTLIST", "STDMAINT");
		XMLMAP.addChild(list);
		XMLElem stdmaintElem = new XMLElem("STDMAINTELEMENT");
		list.addChild(stdmaintElem);
		stdmaintElem.addChild(new XMLActivityElem("STDMAINTACTION"));
		stdmaintElem.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		stdmaintElem.addChild(new XMLElem("ENTITYID", "ENTITYID"));
		stdmaintElem.addChild(new XMLElem("MAINTELIG", "MAINTELIG"));
		stdmaintElem.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
		stdmaintElem.addChild(new XMLElem("COMNAME", "COMNAME"));
		// level 4
		list = new XMLElem("COUNTRYLIST");
		stdmaintElem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));
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
	public String getVeName() {
		return "ADSMODEL";
	}

	/**********************************
	 * get the status attribute to use for this ABR
	 */
	public String getStatusAttr() {
		return "STATUS";
	}

	/**********************************
	 *
	 * A. MQ-Series CID
	 */
	public String getMQCID() {
		return "MODEL_UPDATE";
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getVersion() {
		return "1.3";
	}
}
