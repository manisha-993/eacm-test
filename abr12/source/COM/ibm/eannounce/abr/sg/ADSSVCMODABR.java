// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

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
<MACHTYPE>	</MACHTYPE>	2	SVCMOD	MACHTYPEATR	Always - does not change
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
//Revision 1.10  2010/03/15 15:28:01  rick
//changing to shortdesc for BHACCTASGNGRP and TAXCLS.
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
        XMLMAP.addChild(new XMLElem("CATEGORY","SVCMODCATG"));
        XMLMAP.addChild(new XMLElem("SUBCATEGORY","SVCMODSUBCATG"));
        XMLMAP.addChild(new XMLElem("GROUP","SVCMODGRP"));
        XMLMAP.addChild(new XMLElem("SUBGROUP","SVCMODSUBGRP"));
        XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR",XMLElem.FLAGVAL));

        XMLElem annceelem = new XMLANNElem();
        XMLMAP.addChild(annceelem); 

        XMLMAP.addChild(new XMLElem("PRODHIERCD","BHPRODHIERCD"));
        XMLMAP.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));
        
        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);

        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));

        list = new XMLElem("AVAILABILITYLIST");
        XMLMAP.addChild(list);
        list.addChild(new XMLAVAILElem());

        // start of TAXCATEGORYLIST structure
        list = new XMLGroupElem("TAXCATEGORYLIST","TAXCATG");
        XMLMAP.addChild(list);
        // level 3
        XMLElem taxelem = new XMLElem("TAXCATEGORYELEMENT");//check for chgs is controlled by XMLGroupElem
        list.addChild(taxelem);
        // level 4
        taxelem.addChild(new XMLActivityElem("TAXCATEGORYACTION"));
        taxelem.addChild(new XMLElem("TAXCATEGORYCOUNTRY","CNTRY",XMLElem.FLAGVAL));
        taxelem.addChild(new XMLElem("TAXCATEGORYSALESORG","SLEORG"));
        taxelem.addChild(new XMLElem("TAXCATEGORYVALUE","TAXCATGATR",XMLElem.FLAGVAL));
        XMLElem elemSVCMODTAXRELEVANCE = new XMLGroupElem(null,"SVCMODTAXRELEVANCE","U:SVCMODTAXRELEVANCE");
        taxelem.addChild(elemSVCMODTAXRELEVANCE);
        elemSVCMODTAXRELEVANCE.addChild(new XMLElem("TAXCLASSIFICATION","TAXCLS",XMLElem.SHORTDESC));

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
    public String getMQCID() { return "SVCMOD"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.10 $";//"1.0";
    }
}
