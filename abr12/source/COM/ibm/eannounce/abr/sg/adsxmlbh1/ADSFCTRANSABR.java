// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
*/
//$Log: ADSFCTRANSABR.java,v $
//Revision 1.15  2020/03/17 08:21:58  xujianbo
//Finished story for EACM-2579
//WWPRT request for FeatureUpgrades & Model Conversions Development part
//
//Revision 1.14  2016/12/22 07:30:53  wangyul
//Story 1613916 Add  a New Attribute to Feature Conversion Entity
//
//Revision 1.10  2016/11/30 08:41:06  wangyul
//Story 1613916 Add  a New Attribute to Feature Conversion Entity
//
//Revision 1.9  2013/06/27 12:53:12  guobin
//Fixed CQ-227442 BH - reduce number of FC transaction records required, was approved by BH W1 CCB via BH W1 CQ-152329
//
//Revision 1.8  2011/12/14 02:21:01  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.7  2011/01/26 15:19:23  rick
//commenting out the availabilitylist from fctransaction as
//there will be no avails for fctransactions.
//
//Revision 1.6  2010/10/29 15:18:05  rick
//changing MQCID again.
//
//Revision 1.5  2010/10/12 19:24:55  rick
//setting new MQCID value
//
//Revision 1.4  2010/09/24 01:13:36  rick
//adding availabilitylist to call XMLFCTRANSAVAILElem.
//
//Revision 1.3  2010/09/03 17:57:46  rick
//fixing fixed elements and adding empty availability list.
//
//Revision 1.2  2010/09/02 21:07:10  rick
//misc changes for BH 1.0
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
public class ADSFCTRANSABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("FCTRANSACTION_UPDATE");
        XMLMAP.addChild(new XMLVMElem("FCTRANSACTION_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FROMMACHTYPE","FROMMACHTYPE"));
        XMLMAP.addChild(new XMLFCTRANSMODElem("FROMMODEL","FROMMODEL","***"));
        XMLMAP.addChild(new XMLElem("FROMFEATURECODE","FROMFEATURECODE"));
        XMLMAP.addChild(new XMLFixedElem("FROMMODELENTITYTYPE","MODEL"));

/*D.  <FROMMODELENTITYID> and <FROMFEATUREENTITYID>

Search for PRODSTRUCT using
<FROMMACHTYPE>
<FROMMODEL>
<FROMFEATURECODE>
then FROMMODELENTITYID = PRODSTRUCT.ENTITY2ID and FROMFEATUREENTITYID = PRODSTRUCT.ENTITY1ID
*/
		XMLRelatorSearchElem srchElem = new XMLRelatorSearchElem("FROMMODELENTITYID", "FROMFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
		srchElem.addSearchAttr("FROMMACHTYPE", "MODEL:MACHTYPEATR");
		srchElem.addSearchAttr("FROMMODEL", "MODEL:MODELATR");
		srchElem.addSearchAttr("FROMFEATURECODE", "FEATURE:FEATURECODE");
		XMLMAP.addChild(srchElem);


        XMLMAP.addChild(new XMLFixedElem("FROMFEATUREENTITYTYPE","FEATURE"));
        XMLMAP.addChild(new XMLElem("TOMACHTYPE","TOMACHTYPE"));
        XMLMAP.addChild(new XMLFCTRANSMODElem("TOMODEL","TOMODEL","***"));
        XMLMAP.addChild(new XMLElem("TOFEATURECODE","TOFEATURECODE"));
        XMLMAP.addChild(new XMLFixedElem("TOMODELENTITYTYPE","MODEL"));

/*E.  <TOMODELENTITYID> and <TOFEATUREENTITYID>

Search for PRODSTRUCT using
<TOMACHTYPE>
<TOMODEL>
<TOFEATURECODE>
then TOMODELENTITYID = PRODSTRUCT.ENTITY2ID and TOFEATUREENTITYID = PRODSTRUCT.ENTITY1ID
*/
		srchElem = new XMLRelatorSearchElem("TOMODELENTITYID", "TOFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
		srchElem.addSearchAttr("TOMACHTYPE", "MODEL:MACHTYPEATR");
		srchElem.addSearchAttr("TOMODEL", "MODEL:MODELATR");
		srchElem.addSearchAttr("TOFEATURECODE", "FEATURE:FEATURECODE");
		XMLMAP.addChild(srchElem);

        XMLMAP.addChild(new XMLFixedElem("TOFEATUREENTITYTYPE","FEATURE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));  
        XMLMAP.addChild(new XMLElem("ANNRFANUMBER","ANNRFANUMBER"));
        XMLMAP.addChild(new XMLElem("WDRFANUMBER","WDRFANUMBER"));
        XMLMAP.addChild(new XMLElem("ANNDATE","ANNDATE"));
        XMLMAP.addChild(new XMLElem("WTHDRWEFFCTVDATE","WTHDRWEFFCTVDATE"));          
        XMLMAP.addChild(new XMLElem("BOXSWAPREQUIREDFORUPGRADES","BOXSWAP"));
        XMLMAP.addChild(new XMLElem("CUSTOMERSETUP","INSTALL"));
        XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONCATEGORY","FTCAT"));
        XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONSUBCATEGORY","FTSUBCAT"));
        XMLMAP.addChild(new XMLElem("INSTALLABILITY","INSTALLABILITY"));
        XMLMAP.addChild(new XMLElem("INTERNALNOTES","INTERNALNOTES"));
        XMLMAP.addChild(new XMLElem("PARTSSHIPPEDINDICATOR","PARTSSHIPPED"));
        XMLMAP.addChild(new XMLElem("QUANTITY","TRANSACTQTY"));
        XMLMAP.addChild(new XMLElem("REMOVEQUANTITY","TRANSACTREMOVEQTY"));
        XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES","RETURNEDPARTS"));
        XMLMAP.addChild(new XMLElem("UPGRADETYPE","UPGRADETYPE"));
        XMLMAP.addChild(new XMLElem("ZEROPRICEDINDICATOR","ZEROPRICE"));

        XMLMAP.addChild(new XMLAVAILElemFCT());
       /* XMLElem list = new XMLGroupElem("COUNTRYLIST","AVAIL");
        XMLMAP.addChild(list);
		// level 5
		XMLElem listelem = new XMLChgSetPlantAvailElem("COUNTRYELEMENT");
		list.addChild(listelem);
		// level 6
		// Defect BHALM00057306 Change Mapping to TAXCNTRY
		listelem.addChild(new XMLMultiFlagAvailElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));*/
		// leve 4
	// removing availabilitylist from the fctransaction xml per change communicated 1-25-2011
        // BH AVAILABILITYLIST Structure       
    	//XMLElem list = new XMLElem("AVAILABILITYLIST");
	//	XMLMAP.addChild(list);
	//	list.addChild(new XMLFCTRANSAVAILElem());
	
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
    public String getVeName() { return "ADSFCTRANSACTION";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "FCTRANSACTION_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.2";
    }
}
