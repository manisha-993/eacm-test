// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;



/**********************************************************************************
*
*
<?XML VERSION="1.0" ENCODING="UTF-8"?>		1		Always																				
<MODEL_UPDATE>		        1	SVCMOD		        Always		XML_Begin																		
<PDHDOMAIN>	</PDHDOMAIN>	2	SVCMOD	PDHDOMAIN	Always																				
<DTSOFMSG>	</DTSOFMSG>	2	SVCMOD	ABR Queued	Always																				
<ACTIVITY>	</ACTIVITY>	2	SVCMOD	Activity	Always																				
<MODELENTITYTYPE>	</MODELENTITYTYPE> 	2 SVCMOD	ENTITYTYPE	DB Key for SVCMOD						 			 					 			
<MODELENTITYID>	</MODELENTITYID>2	SVCMOD	ENTITYID	DB Key for SVCMOD						 			 					 			
<MACHTYPE>	</MACHTYPE>	2	SVCMOD	SMACHTYPEATR	Always - does not change
<MODEL>	</MODEL>	        2	SVCMOD	MODELATR	Always - does not change
<STATUS>	</STATUS>	2	SVCMOD	STATUS	        Always
<CATEGORY>	</CATEGORY>	2	SVCMOD	SVCMODCATG	Always - does not change
<SUBCATEGORY>	</SUBCATEGORY>	2	SVCMOD	SVCMODSUBCATG	Always - does not change
<GROUP>	</GROUP>	        2	SVCMOD	SVCMODGRP	Always - does not change
<SUBGROUP>	</SUBGROUP>	2	SVCMOD	SVCMODSUBGRP	Always - does not change
<PRFTCTR>	</PRFTCTR>	2	SVCMOD	PRFTCTR	        Always	
<ANNOUNCEDATE>	</ANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE	Always	
<ANNOUNCENUMBER>	</ANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER	Always
<WITHDRAWANNOUNCEDATE>	</WITHDRAWANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE	        Always
<WITHDRAWANNOUNCENUMBER>	</WITHDRAWANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER	Always
<PRODHIERCD>	</PRODHIERCD>	2	SVCMOD	BHPRODHIERCD	Always
<ACCTASGNGRP>	</ACCTASGNGRP>		SVCMOD	BHACCTASGNGRP	Always
<LANGUAGELIST>		        2					Always
<LANGUAGEELEMENT>		3	if anything within Element is changed, then Element is passed
<NLSID>	</NLSID>	        4	SVCMOD	NLSID	DB Key
<INVNAME	</INVNAME>	4	SVCMOD	INVNAME	Always
</LANGUAGEELEMENT>	        3
</LANGUAGELIST>	                2
<AVAILABILITYLIST>		2
<AVIAILABILITYELEMENT>		3	if anything within Element is changed, then Element is passed
<AVAILABILITYACTION>	</AVAILABILITYACTION>	4 Always	
<STATUS>	</STATUS>	4	AVAIL	STATUS	Always
<COUNTRY_FC>	</COUNTRY_FC>	4	AVAIL	COUNTRYLIST	DB Key
<PUBFROM>	</PUBFROM>	4	Derived	PubFrom	Always
<PUBTO>	</PUBTO>	        4	Derived	PubTo	Always
<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>	4	AVAIL	EFFECTIVEDATE	Always
</AVAILABILTYELEMENT>	3
</AVAILABILITYLIST>	2
<TAXCATEGORYLIST>		2	Always
<TAXCATEGORYELEMENT>		3	if anything within Element is changed, then Element is passed
<TAXCATEGORYACTION>	</TAXCATEGGORYACTION>	4 Always
<TAXCATEGORYCOUNTRY>	</TAXCATEGORYCOUNTRY>	4 TAXCATG	CNTRY	DB Key
<TAXCATEGORYSALESORG>	</TAXCATEGORYSALESORG>	4 TAXCATG	SLEORG	DB Key
<TAXCATEGORYVALUE>	</TAXCATEGORYVALUE>	4 TAXCATG	TAXCATGATR	Always
<TAXCLASSIFICATION	</TAXCLASSIFICATION>	4 SVCMOD TAXRELEVANCE	TAXCLS	Always
</TAXCATEGORYELEMENT>	3
</TAXCATEGORYLIST>	2
</MODEL_UPDATE>	1						 	XML_End	No																	


*/
// ADSSVCMODABR.java,v
//$Log: ADSSVCMODABR.java,v $
//Revision 1.1  2015/02/04 14:55:49  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.41  2014/01/07 13:00:29  guobin
//delete XML - Avails RFR Defect: BH 185136 Doc BH FS ABR XML System Feed Mapping 20131106b
//
//Revision 1.40  2012/12/05 14:54:31  wangyulo
//Fix the defect 848608 to correct the mapping for DIVISION
//
//Revision 1.39  2012/03/15 08:13:44  liuweimi
//Add ALTID to SVCMOD_UPDATE
//
//Revision 1.38  2012/01/18 15:56:34  guobin
//Fix the issue 635138 --SVCMOD:
//1. HGHLIMT should be HIGHLIMT tag name in SVCMOD xml.
//2. It should show EFFECTIVEDATE and ENDDATE once for each PRCPTELEMENT tag in SVCMOD xml.
//3. BHPRODHIERCD should be BHPRDHIERCD tag name in SVCMOD xml.
//4.  INVNAME tag and MKTGNAME tag reversed in LANGUAGELIST of SVCMOD xml
//
//Revision 1.37  2011/12/14 02:26:01  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.36  2011/10/13 09:02:45  liuweimi
//CQ 54126
//
//Revision 1.35  2011/08/23 12:34:33  guobin
// //If CVMTYPE of the parent CVM entity is Characteristic(flag value C1) then map CVMSPEC.CHARVAL to the attribute.
//  //If CVMTYPE of the parent CVM entity is Value Metric(flag value C2) then map CMVSPEC.VMSPECID to the attribute.
//
//Revision 1.34  2011/08/12 11:27:53  guobin
//correcnt to DAATTRIBUTECODE and DAATTRIBUTEVALUE
//
//Revision 1.33  2011/07/13 08:22:50  guobin
// //Defect BHALM00057306 Change Mapping to TAXCNTRY
//
//Revision 1.32  2011/07/05 13:32:28  liuweimi
//use PCTOFCMPLTINDC insteadof PCROFCMPLTINDC
//
//Revision 1.31  2011/05/25 04:00:16  guobin
//CQ 41622	Add PCROFCMPLTINDC
//CQ 31982	Add UNSPSC and UNSPSCUOM
//Defect	Add CNTRYEFF keys for middleware mapping
//Defect	Add CVM keys for middleware mapping
//Defect	Add CHARACID for CVM
//
//Revision 1.30  2011/03/14 09:00:48  guobin
//Change mapping and add derivation rule
// If CVM.CVMTYPE = 'C1' (Characteristic) set to CVMSPEC.CHARACID, else set to CVMSPEC.VMSPECID
//
//Revision 1.29  2011/03/04 08:58:34  guobin
//change the attribute of REFCVMSPECID tag from PRCPTID to CVMSPECID 20110304
//
//Revision 1.28  2011/02/22 02:44:11  guobin
//add XMLGroupElem(null, entity,path,false,level) for the PRCPTCVMSPEC
//
//Revision 1.27  2011/02/17 15:22:59  guobin
//change the path of new XMLGroupElem(null,entity,path)
//
//Revision 1.26  2011/02/16 03:22:44  guobin
//CQ 31962	22.30, 22.40	Correct mapping for UNSPSC and UNUOM
//
//Revision 1.25  2011/02/15 12:32:47  guobin
//change CQ 26972	191.20 - 191.40	Add Effective and End date to SVCSEO to PRCPT relationship
//
//Revision 1.24  2011/02/15 10:59:49  lucasrg
//Applied mapping updates for DM Cycle 2
//
//Revision 1.23  2011/02/12 09:57:11  guobin
//change CQ 26972
//
//Revision 1.22  2011/01/20 16:29:38  guobin
//CQ 26972
//
//Revision 1.21  2011/01/13 08:41:43  guobin
//the countrylist was deleted from PRCPT
//
//Revision 1.20  2011/01/10 11:47:48  guobin
//SVCMODUNBUNDCOMP changed to SVCMODUNBUND
//
//Revision 1.19  2010/12/29 06:11:15  guobin
//New added <SLEORGGRPLIST> in <TAXCODELIST>
//
//Revision 1.18  2010/12/15 04:22:29  guobin
//Change SVCSEOLIST  with new constructor isMultUse
//
//Revision 1.17  2010/12/13 07:59:56  guobin
//Change SVCSEOLIST  with new constructor  isMultUse
//
//Revision 1.16  2010/12/07 09:58:25  guobin
//update the meta and the tag
//
//Revision 1.15  2010/12/03 13:27:46  guobin
//fix the multiUsed entity
//
//Revision 1.14  2010/11/30 05:58:01  guobin
//add the missing action and add the nesting list check
//
//Revision 1.13  2010/11/26 07:19:57  guobin
//Add the missing action
//
//Revision 1.12  2010/11/25 06:16:01  guobin
//change DIVISION using the path
//
//Revision 1.11  2010/11/25 02:21:17  guobin
//update the TAXCATEGORYCOUNTRY and TAXCODECOUNTRY to COUNTRYLIST tag
//
//Revision 1.10  2010/11/24 08:15:30  guobin
// replace TAXCATEGORYCOUNTRY and COUNTRY_FC in TAXCATEGORYLIST/TAXCATEGORYELEMENT with COUNTRYLIST/COUNTRYELEMENT
//
//Revision 1.9  2010/11/22 13:26:03  guobin
//need the path to find the child entity
//
//Revision 1.8  2010/11/18 09:40:16  guobin
//update REFPRCPTID
//
//Revision 1.7  2010/11/18 09:25:13  guobin
//update the SGMNTACRNYM
//
//Revision 1.6  2010/11/17 09:30:08  guobin
//update the CVMSPECELEMENT
//
//Revision 1.5  2010/11/15 07:53:52  guobin
//change the meta BHPRDHIERCD to BHPRODHIERCD on SVCSEO
//
//Revision 1.4  2010/11/11 15:08:55  guobin
//the new update on 20101027
//
//Revision 1.3  2010/10/29 15:18:05  rick
//changing MQCID again.
//
//Revision 1.2  2010/10/12 19:24:56  rick
//setting new MQCID value
//
//Revision 1.1  2010/05/19 22:25:20  rick
//adding adsxmlbh1 folder for BH 1.0.
//
//Revision 1.3  2010/04/26 14:05:53  rick
//change machtypeatr to smachtypeatr.
//
//Revision 1.2  2010/03/15 15:47:07  rick
//changing BHACCTASGNGRP and TAXCLS to shortdesc.
//
//Revision 1.1  2010/03/02 02:17:56  rick
//changes for new wave2 package
//
//Revision 1.9  2010/02/18 18:58:25  rick
//changing category, sub-category, group, sub-group and
//prodhiercd to use long description as opposed to flagval.
//
//Revision 1.8  2010/02/01 16:52:03  rick
//adding taxcategorylist for BH .5
//
//Revision 1.7  2010/01/28 00:31:29  rick
//change to use BHACCTASGNGRP instead of
//ACCTASGNGRP.
//
//Revision 1.6  2010/01/14 19:23:29  rick
//change MQCID from MODEL to SVCMOD.
//
//Revision 1.5  2010/01/07 18:01:28  wendy
//cvs failure again
//
public class ADSSVCMODABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
	    XMLMAP = new XMLGroupElem("SVCMOD_UPDATE");
	    XMLMAP.addChild(new XMLVMElem("SVCMOD_UPDATE","1"));
	    // level2
	    XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
	    XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday 
	    XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
	    XMLMAP.addChild(new XMLElem("MODELENTITYTYPE","ENTITYTYPE"));
	    XMLMAP.addChild(new XMLElem("MODELENTITYID","ENTITYID"));
	    XMLMAP.addChild(new XMLElem("MACHTYPE","SMACHTYPEATR"));
	    XMLMAP.addChild(new XMLElem("MODEL","MODELATR"));
	    //XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));//Flag Description Class
	    XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
	    XMLMAP.addChild(new XMLElem("CATEGORY","SVCMODCATG"));//Long Description
	    XMLMAP.addChild(new XMLElem("SUBCATEGORY","SVCMODSUBCATG"));//Long Description
	    XMLMAP.addChild(new XMLElem("GROUP","SVCMODGRP"));//Long Description
	    XMLMAP.addChild(new XMLElem("SUBGROUP","SVCMODSUBGRP"));//Long Description
	    // level 2 PROJECT
	    XMLMAP.addChild(new XMLElem("PROJECT","PROJCDNAM",XMLElem.FLAGVAL));//Flag Description Class
	    // level 2 DIVISION 
	    //ENTERPRISE LINKTYPE                         LINKTYPE1                        LINKTYPE2                        LINKCODE                         LINKVALUE
	    //---------- -------------------------------- -------------------------------- -------------------------------- -------------------------------- ------------
	    //SG         SVCMODSGMTACRONYMA               SGMNTACRNYM                      SVCMOD                           Assoc                            PRFTCTR
	    //Defect 848608 to correct the mapping for DIVISION
	    //XMLElem elem = new XMLGroupElem(null,"SGMNTACRNYM","U:SVCMODSGMTACRONYMA:U");
	    //TODO RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo 
	    //TODO there is no meta link
	    XMLElem elemPROJ = new XMLDistinctGroupElem(null,"PROJ","D:SVCMODPROJA:D",true,true);
	    //XMLElem elemPROJ = new XMLDistinctGroupLnElem(null,"PROJ",null,true,true);
        XMLMAP.addChild(elemPROJ);
        elemPROJ.addChild(new XMLElem("DIVISION","DIV",XMLElem.FLAGVAL));
	    
	    //level 2 PRFTCTR
	    XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR",XMLElem.FLAGVAL));
	    //the same with the model, comment the following 2 lines code
	    //XMLElem annceelem = new XMLANNElem();
	    //XMLMAP.addChild(annceelem); 
	    //level 2 PRODHIERCD
	    XMLMAP.addChild(new XMLElem("PRODHIERCD","BHPRODHIERCD"));
		//level 2 ACCTASGNGRP
		XMLMAP.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));//Flag Short Description
		
		//level 2 NEC
		XMLMAP.addChild(new XMLElem("NEC","NEC"));
		//level 2 TOS
		XMLMAP.addChild(new XMLElem("TOS","TOS"));
		XMLMAP.addChild(new XMLElem("SVCFFTYPE","SVCFFTYPE"));//Long Description
		//level 2 ENTITY - Services	ATTRIBUTE - Services		Value        
		XMLMAP.addChild(new XMLElem("OFERCONFIGTYPE","OFERCONFIGTYPE"));//Long Description
		XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ","ENDCUSTIDREQ"));
		XMLMAP.addChild(new XMLElem("FIXTERMPRIOD","FIXTERMPRIOD"));
		XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW","PRORATEDOTCALLOW"));
		XMLMAP.addChild(new XMLElem("SNGLLNEITEM","SNGLLNEITEM"));
		XMLMAP.addChild(new XMLElem("SVCCHRGOPT","SVCCHRGOPT",XMLElem.SHORTDESC));//Short Description
		XMLMAP.addChild(new XMLElem("TYPEOFWRK","TYPEOFWRK"));
		XMLMAP.addChild(new XMLElem("UOMSI","UOMSI",XMLElem.SHORTDESC));//Short Description
		XMLMAP.addChild(new XMLElem("UPGRADEYN","UPGRADEYN"));
		XMLMAP.addChild(new XMLElem("WWOCCODE","WWOCCODE")); 
		
		//Change	CQ 31962 - update mapping	22.30		1	1.0	<UNSPSC>	</UNSPSC>	2	SVCMOD_UPDATE /UNSPSC		ADSSVCMOD	Root Entity	SVCMOD	UNSPSCCD
		//Change	CQ 31962 - update mapping	22.40		1	1.0	<UNUOM>	</UNUOM>	2	SVCMOD_UPDATE /UNUOM		ADSSVCMOD	Root Entity	SVCMOD	UNSPSCCDUOM
		XMLMAP.addChild(new XMLElem("UNSPSC","UNSPSCCD"));
		XMLMAP.addChild(new XMLElem("UNUOM","UNSPSCCDUOM"));	
//		Add	CQ 41622 - update mapping	22.50		1	1.0	<PCTOFCMPLTINDC>	</PCTOFCMPLTINDC>	2	SVCMOD_UPDATE /PCTOFCMPLTINDC		ADSSVCMOD	Root Entity	SVCMOD	PCTOFCMPLTINDC		Long Description	"Yes", "No"	Always																																
		XMLMAP.addChild(new XMLElem("PCTOFCMPLTINDC","PCTOFCMPLTINDC"));
		//Add	CQ 54126 - Service Optimization Attributes	22.56		1	1.0	<SOPRELEVANT>	</SOPRELEVANT>	2	SVCMOD_UPDATE /SOPRELEVANT		ADSSVCMOD	Root Entity	SVCMOD	SOPRELEVANT		Long Description	"Yes", "No"	Always																																
		//Add	CQ 54126 - Service Optimization Attributes	22.57		1	1.0	<SOPTASKTYPE>	</SOPTASKTYPE>	2	SVCMOD_UPDATE /SOPTASKTYPE		ADSSVCMOD	Root Entity	SVCMOD	SOPTASKTYPE		Text	NLSID=1 only	Always																																
		XMLMAP.addChild(new XMLElem("SOPRELEVANT","SOPRELEVANT"));
		XMLMAP.addChild(new XMLElem("SOPTASKTYPE","SOPTASKTYPE"));
		//Add	BUSREQ BMSIW ?	22.58	1.0		1	1.0	<ALTID>	</ALTID>	2	SVCMOD_UPDATE /ALTID		ADSSVCMOD	Root Entity	SVCMOD	ALTID		Text	NLSID=1 only	Always																																
		XMLMAP.addChild(new XMLElem("ALTID","ALTID"));
		
		//level 2 <LANGUAGELIST>
		XMLElem list = new XMLElem("LANGUAGELIST");
		XMLMAP.addChild(list);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
		//level 4
		langelem.addChild(new XMLElem("NLSID","NLSID"));
		//change INVNAME tag and MKTGNAME tag reversed in LANGUAGELIST of SVCMOD xml(2012-01-10)
		langelem.addChild(new XMLElem("INVNAME","INVNAME"));
		langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME"));        
		
		//level 2 </LANGUAGELIST>
		
	    //level 2 <AVAILABILITYLIST>
		//check the special availability of the SVCMOD 
		list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		//new availabilitylist
		list.addChild(new XMLSVCMODAVAILElembh1());
		//level 2 </AVAILABILITYLIST>

		//level 2 <TAXCATEGORYLIST> structure
		//case 1 about XMLGroupElem
		//ENTERPRISE LINKTYPE                         LINKTYPE1                        LINKTYPE2                        
		//---------- -------------------------------- -------------------------------- -------------------------------- 
		//SG         SVCMODCATDATA                    SVCMOD                           TAXCATG
		list = new XMLGroupElem("TAXCATEGORYLIST","TAXCATG");
		XMLMAP.addChild(list);
		// level 3
		XMLElem eTAXCATEGORYELEMENT = new XMLElem("TAXCATEGORYELEMENT");//check for chgs is controlled by XMLGroupElem
		list.addChild(eTAXCATEGORYELEMENT);
        // level 4  the xml type is XMLActivityElem
		eTAXCATEGORYELEMENT.addChild(new XMLActivityElem("TAXCATEGORYACTION"));		
        
        //new add on 20101027   COUNTRYACTION
        //<COUNTRY_FC> change to 
        //<COUNTRYLISTLIST>
    	//	<COUNTRYLISTELEMENT>
    	//		<COUNTRYACTION>Update</COUNTRYACTION>
    	//		<COUNTRY_FC></COUNTRY_FC>
    	//	</COUNTRYLISTELEMENT>
        //</COUNTRYLISTLIST>
        list = new XMLElem("COUNTRYLIST");
        eTAXCATEGORYELEMENT.addChild(list);
        // level 5
        XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 6 
        //Defect BHALM00057306 Change Mapping to TAXCNTRY
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","TAXCNTRY","COUNTRYACTION",XMLElem.FLAGVAL));
        //Leve 4 TAXCATEGORYVALUE
        eTAXCATEGORYELEMENT.addChild(new XMLElem("TAXCATEGORYVALUE","TAXCATGATR",XMLElem.FLAGVAL));
		//removed and replaced with COUNTRYLIST
		//eTAXCATEGORYELEMENT.addChild(new XMLElem("TAXCATEGORYCOUNTRY","CNTRY",XMLElem.FLAGVAL));
        //Level 4 XMLGroupElem SVCMODTAXRELEVANCE relator connect between SVCMOD and  TAXCATG
		//case 2 about XMLGroupElem
		//ENTERPRISE LINKTYPE                         LINKTYPE1                        LINKTYPE2                        
		//---------- -------------------------------- -------------------------------- -------------------------------- 
		//SG         SVCMODTAXRELEVANCE               SVCMOD                           TAXCATG 
		// not down, but up
        XMLElem elemSVCMODTAXRELEVANCE = new XMLGroupElem(null,"SVCMODTAXRELEVANCE","U:SVCMODTAXRELEVANCE");       
        eTAXCATEGORYELEMENT.addChild(elemSVCMODTAXRELEVANCE);
        elemSVCMODTAXRELEVANCE.addChild(new XMLElem("TAXCLASSIFICATION","TAXCLS",XMLElem.SHORTDESC));
        //level 4 new added <SLEORGGRPLIST>
        eTAXCATEGORYELEMENT.addChild(new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
        
        //Level 2 </TAXCATEGORYLIST>
        
        
        //level 2 <TAXCODELIST>
        list = new XMLGroupElem("TAXCODELIST","TAXGRP");
        XMLMAP.addChild(list);
        // level 3 
        XMLElem eTAXCODEELEMENT = new XMLElem("TAXCODEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eTAXCODEELEMENT);
        // level 4
        eTAXCODEELEMENT.addChild(new XMLActivityElem("TAXCODEACTION"));
        eTAXCODEELEMENT.addChild(new XMLElem("TAXCODEDESCRIPTION","DESC"));
        //delete on 20101027
        //eTAXCODEELEMENT.addChild(new XMLElem("DISTRIBUTIONCHANNEL","DISTRBCHANL"));
        //add on 20101027 
        list = new XMLElem("COUNTRYLIST");
        eTAXCODEELEMENT.addChild(list);
        // level 5
        XMLElem codelistelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(codelistelem);
        //level 6
        codelistelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        //level 4
        eTAXCODEELEMENT.addChild(new XMLElem("TAXCODE","TAXCD"));
        //level 4 new added <SLEORGGRPLIST>
        eTAXCODEELEMENT.addChild(new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
        //level 2 </TAXCODELIST>
        
        //level 2 <CATATTRIBUTELIST> confirm and change the doc SVCMOD  -->  CATDATA
        //SG         SVCMODCATDATA                    SVCMOD                           CATDATA
        list = new XMLGroupElem("CATATTRIBUTELIST","CATDATA"); //CATDATA from the doc of meta
        XMLMAP.addChild(list);
        // level 3 when there is NLSID element, use the XMLNLSElem type
        XMLElem eCATATTRIBUTEELEMENT = new XMLNLSElem("CATATTRIBUTEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eCATATTRIBUTEELEMENT);
        // level 4 <CATATTRIBUTEACTION>
        eCATATTRIBUTEELEMENT.addChild(new XMLActivityElem("CATATTRIBUTEACTION"));
        // correcnt to DAATTRIBUTECODE and DAATTRIBUTEVALUE
        eCATATTRIBUTEELEMENT.addChild(new XMLElem("CATATTRIBUTE","DAATTRIBUTECODE"));
        eCATATTRIBUTEELEMENT.addChild(new XMLElem("NLSID","NLSID"));
        eCATATTRIBUTEELEMENT.addChild(new XMLElem("CATATTRIUBTEVALUE","DAATTRIBUTEVALUE"));
        //level 2 </CATATTRIBUTELIST>
        
        //level 2 <UNBUNDCOMPLIST> confirm and change the doc SVCMOD -->  REVUNBUNDCOMP
        //SG         SVCMODUNBUND                     SVCMOD                           REVUNBUNDCOMP
        list = new XMLGroupElem("UNBUNDCOMPLIST","REVUNBUNDCOMP"); //UNBUNDCOMP is referenced from the model
        XMLMAP.addChild(list);
        // level 3
        XMLElem unbundelem = new XMLElem("UNBUNDCOMPELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(unbundelem);
        // level 4
        unbundelem.addChild(new XMLActivityElem("UNBUNDCOMPACTION"));
        //new add on 2010-10-27
        unbundelem.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        unbundelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
        unbundelem.addChild(new XMLElem("UNBUNDCOMPID","UNBUNDCOMPID"));
        //case 2 about XMLGroupElem    
        //change the doc
        //SG         SVCMODUNBUND                     SVCMOD                           REVUNBUNDCOMP
        XMLElem eSVCMODUNBUND = new XMLGroupElem(null,"SVCMODUNBUND","U:SVCMODUNBUND");
        unbundelem.addChild(eSVCMODUNBUND);
        eSVCMODUNBUND.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        eSVCMODUNBUND.addChild(new XMLElem("ENDDATE","ENDDATE"));
        eSVCMODUNBUND.addChild(new XMLElem("UNBUNDTYPE","UNBUNDTYPE"));
        //level 2 </UNBUNDCOMPLIST>
        
        //level 2 <CHRGCOMPLIST>  big
        list = new XMLGroupElem("CHRGCOMPLIST","CHRGCOMP","D:SVCMODCHRGCOMP:D"); 
        XMLMAP.addChild(list);
        // level 3
        XMLElem eCHRGCOMPELEMENT = new XMLElem("CHRGCOMPELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eCHRGCOMPELEMENT);
        //level 4
        //Add the missing action of CHRGCOMPLIST
        //eCHRGCOMPELEMENT.addChild(new XMLActivityElem("CHRGCOMPACTION"));
        eCHRGCOMPELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        eCHRGCOMPELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        eCHRGCOMPELEMENT.addChild(new XMLElem("CHRGCOMPID","CHRGCOMPID"));
        eCHRGCOMPELEMENT.addChild(new XMLElem("BHACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));
        eCHRGCOMPELEMENT.addChild(new XMLElem("REFSELCONDTN","REFSELCONDTN"));        
        eCHRGCOMPELEMENT.addChild(new XMLElem("SVCCHRGOPT","SVCCHRGOPT",XMLElem.SHORTDESC));
        //CQ 26792	87.02 - 87.04	Add Effective and End date to Chargeable Component
        eCHRGCOMPELEMENT.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        eCHRGCOMPELEMENT.addChild(new XMLElem("ENDDATE","ENDDATE"));
        
        //level 4 LANGUAGELIST
        list = new XMLElem("LANGUAGELIST");
        eCHRGCOMPELEMENT.addChild(list);

        // level 5
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
        langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME"));        
        
        //level 4 <PRICEPOINTLIST>  PRCPT
        list = new XMLGroupElem("PRICEPOINTLIST","PRCPT","D:CHRGCOMPPRCPT:D",true); //CATATTR from the doc of meta
        eCHRGCOMPELEMENT.addChild(list);
        // level 5
        XMLElem ePRICEPOINTELEMENT = new XMLElem("PRICEPOINTELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(ePRICEPOINTELEMENT);
        // level 6
        // Add the missing action of PRICEPOINTLIST
        //ePRICEPOINTELEMENT.addChild(new XMLActivityElem("PRICEPOINTACTION"));
        ePRICEPOINTELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        ePRICEPOINTELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        ePRICEPOINTELEMENT.addChild(new XMLElem("PRCPTID","PRCPTID"));
        ePRICEPOINTELEMENT.addChild(new XMLElem("PRCMETH","PRCMETH",XMLElem.SHORTDESC));//Short Description
        
        list = new XMLElem("LANGUAGELIST");
        ePRICEPOINTELEMENT.addChild(list);

        // level 7
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 8
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
        langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME")); 
        
        //level 6 
        //At "SVCMOD" sheet, Line ID 97.50, PRCPT use the countrylist, but in the meta Excel, the countrylist was deleted  
        //Answer: yes. countrylist is at CNTRYEFF now which is child of PRCPT
//        list = new XMLElem("COUNTRYLISTLIST");
//        ePRICEPOINTELEMENT.addChild(list);
//        // level 7 XMLChgSetElem
//        XMLElem eCOUNTRYLISTELEMENT = new XMLChgSetElem("COUNTRYLISTELEMENT");
//        list.addChild(eCOUNTRYLISTELEMENT);
//        //level 8
//        eCOUNTRYLISTELEMENT.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        //CQ 26972 94.20 - 94.80 Add linkage to CNTRYEFF with Effective and End date (Country for Price Point)
        //Level 6 <CNTRYEFFLIST>
        list = new XMLGroupElem("CNTRYEFFLIST","CNTRYEFF","D:PRCPTCNTRYEFF:D");
        ePRICEPOINTELEMENT.addChild(list);
        XMLElem eCNTRYEFFELEMENT = new XMLElem("CNTRYEFFELEMENT");
        list.addChild(eCNTRYEFFELEMENT);
        eCNTRYEFFELEMENT.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        eCNTRYEFFELEMENT.addChild(new XMLElem("ENDDATE","ENDDATE"));
//        Add	Keys for middleware	94.85		1	1.0	<ENTITYTYPE>	</ENTITYTYPE>	8	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /PRICEPOINTLIST /PRICEPOINTELEMENT /CNTRYEFFLIST /CNTRYEFFELEMENT /ENTITYTYPE				CNTRYEFF	ENTITYTYPE		"CNTRYEFF"		Always																																
//        Add	Keys for middleware	94.90		1	1.0	<ENTITYID>	</ENTITYID>	8	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /PRICEPOINTLIST /PRICEPOINTELEMENT /CNTRYEFFLIST /CNTRYEFFELEMENT /ENTITYID				CNTRYEFF	ENTITYID		Integer	 	Always																																
        eCNTRYEFFELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        eCNTRYEFFELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        
        //CQ 26972 97.50 Add linkage to CNTRYEFF with Effective and End date (Country for Price Point)
        list = new XMLElem("COUNTRYLISTLIST");
        eCNTRYEFFELEMENT.addChild(list);
        // level 7 XMLChgSetElem
        XMLElem eCOUNTRYLISTELEMENT = new XMLChgSetElem("COUNTRYLISTELEMENT");
        list.addChild(eCOUNTRYLISTELEMENT);
        //level 8
        eCOUNTRYLISTELEMENT.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","COUNTRYACTION",XMLElem.FLAGVAL));
        //</CNTRYEFFLIST>  
        //Level 6 <REFCVMSPECLIST>
        //100.00 - 106.00	Add REFCVMSPECLIST from PRCPTELEMENT (reverse reference)
        list = new XMLGroupElem("REFCVMSPECLIST","CVMSPEC","D:PRCPTCVMSPEC:D",true);
        ePRICEPOINTELEMENT.addChild(list);
        XMLElem eREFCVMSPECELEMENT = new XMLElem("REFCVMSPECELEMENT");
        list.addChild(eREFCVMSPECELEMENT);
         
        //CQ 26972	101.02 - 101.04	Add Effective and End date to Price Point - CVM Spec relationship 
        //table:key=PRCPTCVMSPEC105;value=5:2:R:PRCPTCVMSPEC:105:PRCPT:64:D:New chgdAttrCnt:2   
        //path:PRCPT64[D][E]:CHRGCOMPPRCPT64[D][R]:CHRGCOMP36[D][E]:SVCMODCHRGCOMP36[D][R]:SVCMOD36[Root][E]
        // from the value=5:2, get the level = 2
        XMLElem ePRCPTCVMSPEC = new XMLGroupElem(null,"PRCPTCVMSPEC","U:PRCPTCVMSPEC",false,2);
        eREFCVMSPECELEMENT.addChild(ePRCPTCVMSPEC);
        ePRCPTCVMSPEC.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        ePRCPTCVMSPEC.addChild(new XMLElem("ENDDATE","ENDDATE"));
        eREFCVMSPECELEMENT.addChild(new XMLElem("REFCVMSPECENTITYTYPE","ENTITYTYPE"));
        eREFCVMSPECELEMENT.addChild(new XMLElem("REFCVMSPECENTITYID","ENTITYID"));
        //Add 20110310	Moved Reference to Here since PRCPT referenes CVMSPEC
        eREFCVMSPECELEMENT.addChild(new XMLElem("REFCVMSPECTYPE","SPECTYPE"));
        //Change mapping and add derivation rule
        //If CVM.CVMTYPE = 'C1' (Characteristic) set to CVMSPEC.CHARACID, else set to CVMSPEC.VMSPECID  
        eREFCVMSPECELEMENT.addChild(new XMLSVCMODCVMSPECElem("REFCVMSPECID"));

        //</REFCVMSPECLIST>
        // end of <PRICEPOINTLIST>
        
        //level 4 <CHARVALUEMETRICLIST>
        //if it need the CVM of CHRGCOMP itself, it need the path to find it
        list = new XMLGroupElem("CHARVALUEMETRICLIST","CVM","D:CHRGCOMPCVM:D");
        eCHRGCOMPELEMENT.addChild(list);  
        //lelve 5
        XMLElem eCHARVALUEMETRICELEMENT = new XMLElem("CHARVALUEMETRICELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eCHARVALUEMETRICELEMENT);
        //level 6
        //Add the missing action
        //eCHARVALUEMETRICELEMENT.addChild(new XMLActivityElem("CHARVALUEMETRICACTION"));
//        Add	CQ 26972	112.02		1	1.0	<EFFECTIVEDATE>	</EFFECTIVEDATE>	6	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /CHARVALUEMETRICLIST /CHARVALUEMETRICELEMENT /EFFECTIVEDATE				CVM	EFFECTIVEDATE		Text	Date YYYY-MM-DD	Always									"ZMMPSV411
//        ZMMPSV42"	"VM
//        VM"																						
//        Add	CQ 26972	112.04		1	1.0	<ENDDATE>	</ENDDATE>	6	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /CHARVALUEMETRICLIST /CHARVALUEMETRICELEMENT /ENDDATE				CVM	ENDDATE		Text	Date YYYY-MM-DD	Always									"ZMMPSV411
//        ZMMPSV42"	"VM
//        VM"																						
       eCHARVALUEMETRICELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("CVMTYPE","CVMTYPE"));
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("VMID","VMID"));
//        Add	Add CHARACID	112.01		1	1.0	<CHARACID>	</CHARACID>	6	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /CHARVALUEMETRICLIST /CHARVALUEMETRICELEMENT /CHARACID				CVM	CHARACID		Short Text	 	Always									"ZMMPSV411
//        ZMMPSV42"	"VM
//        VM"																						
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("CHARACID","CHARACID"));
        //CQ 26972	112.02 - 112.04	Add Effective and End date to CVM
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("ENDDATE","ENDDATE"));
        list = new XMLElem("LANGUAGELIST");
        eCHARVALUEMETRICELEMENT.addChild(list);
        
        //level 7
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 8
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("SHRTNAM","SHRTNAM"));
        //level 6
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("CVMDATATYPE","CVMDATATYPE",XMLElem.SHORTDESC));
        eCHARVALUEMETRICELEMENT.addChild(new XMLElem("CVMSELTYPE","CVMSELTYPE",XMLElem.SHORTDESC));
        //lelve 6 need the path to find the child
        list = new XMLGroupElem("CVMSPECLIST","CVMSPEC","D:CVMCVMSPEC:D");
        eCHARVALUEMETRICELEMENT.addChild(list);
        //level 7
        XMLElem eCVMSPECELEMENT = new XMLElem("CVMSPECELEMENT");//check for chgs is controlled by XMLGroupElem
        //missing add it
        list.addChild(eCVMSPECELEMENT);
        //lelel 8
        //Add the missing action
        //eCVMSPECELEMENT.addChild(new XMLActivityElem("CVMSPECACTION"));
        eCVMSPECELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        eCVMSPECELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        //special CHARACID|VMSPECID
        //If CVMTYPE of the parent CVM entity is Characteristic(flag value C1) then map CVMSPEC.CHARVAL to the attribute. 
        //If CVMTYPE of the parent CVM entity is Value Metric(flag value C2) then map CMVSPEC.VMSPECID to the attribute.
        eCVMSPECELEMENT.addChild(new XMLSVCMODCVMSPECElem("CVMSPECID","CVMTYPE","CVM","U:CVMCVMSPEC:U"));
        //CQ 26972	119.02 - 119.04	Add Effective and End date to CVM Spec
        eCVMSPECELEMENT.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        eCVMSPECELEMENT.addChild(new XMLElem("ENDDATE","ENDDATE"));
        
        //level 8
        list = new XMLNLSElem("LANGUAGELIST");
        eCVMSPECELEMENT.addChild(list);
        //level 9
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 10
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("SHRTNAM","SHRTNAM"));
        
        //level 8
        eCVMSPECELEMENT.addChild(new XMLElem("LOWLIMT","LOWLIMT"));
        //change the Tag Name from HGHLIMT to HIGHLIMT  (2012-01-09)
        eCVMSPECELEMENT.addChild(new XMLElem("HIGHLIMT","HGHLIMT"));
        eCVMSPECELEMENT.addChild(new XMLElem("SPECTYPE","SPECTYPE",XMLElem.SHORTDESC));//Short Description
        eCVMSPECELEMENT.addChild(new XMLElem("RNGETBLOPT","RNGETBLOPT",XMLElem.SHORTDESC));//Short Description
        //124.10 - 124.70	Remove REFPPRPTLIST from CVMSPECELEMENT (reverse reference)
//        //level 8
//        list = new XMLGroupElem("REFCPRCPTLIST","PRCPT","U:PRCPTCVMSPEC:U",true);
//        eCVMSPECELEMENT.addChild(list);
//        //level 9
//        XMLElem eREFPRCPTELEMENT = new XMLElem("REFPRCPTELEMENT");//check for chgs is controlled by XMLGroupElem
//        list.addChild(eREFPRCPTELEMENT);
//        //level 10
//        //Add the missing action
//        //eREFPRCPTELEMENT.addChild(new XMLActivityElem("REFPRCPTACTION"));
//        eREFPRCPTELEMENT.addChild(new XMLElem("REFPRCPTENTITYTYPE","ENTITYTYPE"));
//        eREFPRCPTELEMENT.addChild(new XMLElem("REFPRCPTENTITYID","ENTITYID"));
//        eREFPRCPTELEMENT.addChild(new XMLElem("REFPRCPTID","PRCPTID"));
        //level 2 </CHRGCOMPLIST>
        
        //level 2 start of <SVCEXECTMELIST> structure
        list = new XMLGroupElem("SVCEXECTMELIST","SVCEXECTME"); 
        XMLMAP.addChild(list);
        //level 3
        XMLElem eSVCEXECTMEELEMENT = new XMLElem("SVCEXECTMEELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eSVCEXECTMEELEMENT);
        //level 4
        //Add the missing action
        //eSVCEXECTMEELEMENT.addChild(new XMLActivityElem("SVCEXECTMEACTION"));
        eSVCEXECTMEELEMENT.addChild(new XMLElem("COMPETENCECD","COMPETENCECD",XMLElem.SHORTDESC));//Short Description
        eSVCEXECTMEELEMENT.addChild(new XMLElem("HR","HR"));
        eSVCEXECTMEELEMENT.addChild(new XMLElem("IMPLEMENTRBN","IMPLEMENTRBN",XMLElem.SHORTDESC));//Short Description
        eSVCEXECTMEELEMENT.addChild(new XMLElem("MACHTYPEATR","SMACHTYPEATR"));
        eSVCEXECTMEELEMENT.addChild(new XMLElem("MODELATR","MODELATR"));
        //level 2 </SVCEXECTMELIST>
        
        //level 2 <SVCSEOREFLIST>
        //if there are two relators link to SVCSEO, it need the path to link to the SVCSEO
        list = new XMLGroupElem("SVCSEOREFLIST","SVCSEO","D:SVCMODSVCSEOREF:D",true);
        XMLMAP.addChild(list);
        //level 3
        XMLElem eSVCSEOREFELEMENT = new XMLElem("SVCSEOREFELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eSVCSEOREFELEMENT);
        //level 4
        //Add the missing action
        //eSVCSEOREFELEMENT.addChild(new XMLActivityElem("SVCSEOREFACTION"));
        eSVCSEOREFELEMENT.addChild(new XMLElem("SVCSEOENTITYTYPE","ENTITYTYPE"));
        eSVCSEOREFELEMENT.addChild(new XMLElem("SVCSEOENTITYID","ENTITYID"));
        eSVCSEOREFELEMENT.addChild(new XMLElem("SEOID","SEOID"));
        //level 2 </SVCSEOREFLIST>
        
        // level 2 <SVCMODREFLIST> structure
        list = new XMLGroupElem("SVCMODREFLIST","SVCMOD","D:SVCMODSVCMOD:D");  
        XMLMAP.addChild(list);
        // level 3
        XMLElem eSVCMODREFELEMENT = new XMLElem("SVCMODREFELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(eSVCMODREFELEMENT);
        //level 4
        //Add the missing action
        //eSVCMODREFELEMENT.addChild(new XMLActivityElem("SVCMODREFACTION"));
        eSVCMODREFELEMENT.addChild(new XMLElem("SVCMODEENTITYTYPE","ENTITYTYPE"));
        eSVCMODREFELEMENT.addChild(new XMLElem("SVCMODENTITYID","ENTITYID"));
        eSVCMODREFELEMENT.addChild(new XMLElem("MACHTYPEATR","SMACHTYPEATR"));
        eSVCMODREFELEMENT.addChild(new XMLElem("MODELATR","MODELATR"));
        // level 2 </SVCMODREFLIST>
        
        //new add on 2010-10-27
        // level 2 <SVCSEOLIST>
        //if there are two relators link to SVCSEO, it need the path to link to the SVCSEO
        list = new XMLGroupElem("SVCSEOLIST", "SVCSEO","D:SVCMODSVCSEO:D",true);
        XMLMAP.addChild(list);
        //level 3
        XMLElem eSVCSEOELEMENT = new XMLElem("SVCSEOELEMENT");
        list.addChild(eSVCSEOELEMENT);
        //level 4
        //Add the missing action
        //eSVCSEOELEMENT.addChild(new XMLActivityElem("SVCSEOACTION"));
        eSVCSEOELEMENT.addChild(new XMLActivityElem("ACTIVITY"));
        eSVCSEOELEMENT.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));        
        eSVCSEOELEMENT.addChild(new XMLElem("ENTITYID","ENTITYID"));
        eSVCSEOELEMENT.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));//Flag Description Class
        eSVCSEOELEMENT.addChild(new XMLElem("SEOID","SEOID"));
        //change: BHPRODHIERCD should be BHPRDHIERCD tag name in SVCMOD xml (2012-01-10)
        eSVCSEOELEMENT.addChild(new XMLElem("BHPRDHIERCD","BHPRODHIERCD"));
        eSVCSEOELEMENT.addChild(new XMLElem("PRFTCTR","PRFTCTR"));
        eSVCSEOELEMENT.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));//Short Description

    	//level 4 LANGUAGELIST
        list = new XMLElem("LANGUAGELIST");
        eSVCSEOELEMENT.addChild(list);

        // level 5
        langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
        langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME"));
        
        //level 4 AVAILABILITYLIST
        list = new XMLElem("AVAILABILITYLIST");
        eSVCSEOELEMENT.addChild(list);
        //new availabilitylist
		list.addChild(new XMLSVCSEOAVAILElem());
		//</AVAILABILITYLIST>
        
		//The change of the prcpt start (2012-01-09)
		//level 4 PRCPTLIST
		list = new XMLElem("PRCPTLIST");
		eSVCSEOELEMENT.addChild(list);
		list.addChild(new XMLSVCMODPRCPTElem());
        //The change of the prcpt end  (2012-01-09)       
        //</SVCSEOLIST>
        //end       
        
        
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
    public String getVeName() { return "ADSSVCMOD"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SVCMOD_UPDATE"; }

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
