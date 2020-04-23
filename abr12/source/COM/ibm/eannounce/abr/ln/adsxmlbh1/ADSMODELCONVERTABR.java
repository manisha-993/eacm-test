// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
*/
//$Log: ADSMODELCONVERTABR.java,v $
//Revision 1.1  2015/02/04 14:55:49  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.8  2014/01/07 12:43:08  guobin
//Update Status tag value . delete XML - Avails RFR Defect: BH 185136
//
//Revision 1.7  2011/12/14 02:23:22  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.6  2010/10/29 15:18:05  rick
//changing MQCID again.
//
//Revision 1.5  2010/10/12 19:24:56  rick
//setting new MQCID value
//
//Revision 1.4  2010/09/24 01:13:36  rick
//adding availabilitylist to call XMLFCTRANSAVAILElem.
//
//Revision 1.3  2010/09/03 17:57:47  rick
//fixing fixed elements and adding empty availability list.
//
//Revision 1.2  2010/09/02 20:59:18  rick
//misc changes for BH 1.0.
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
public class ADSMODELCONVERTABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("MODELCONVERT_UPDATE");
        XMLMAP.addChild(new XMLVMElem("MODELCONVERT_UPDATE","1"));
        // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FROMMACHTYPE","FROMMACHTYPE"));
        XMLMAP.addChild(new XMLElem("FROMMODEL","FROMMODEL"));
        XMLMAP.addChild(new XMLFixedElem("FROMMODELTYPE","MODEL"));

		XMLSearchElem srchElem = new XMLSearchElem("FROMMODELENTITYID", "SRDMODEL4", "MODEL");
		srchElem.addSearchAttr("FROMMACHTYPE", "MACHTYPEATR");
		srchElem.addSearchAttr("FROMMODEL", "MODELATR");
		XMLMAP.addChild(srchElem);

        XMLMAP.addChild(new XMLElem("TOMACHTYPE","TOMACHTYPE"));
        XMLMAP.addChild(new XMLElem("TOMODEL","TOMODEL"));
        XMLMAP.addChild(new XMLFixedElem("TOMODELTYPE","MODEL"));

		srchElem = new XMLSearchElem("TOMODELENTITYID", "SRDMODEL4", "MODEL");
		srchElem.addSearchAttr("TOMACHTYPE", "MACHTYPEATR");
		srchElem.addSearchAttr("TOMODEL", "MODELATR");
		XMLMAP.addChild(srchElem);

        //XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("CUSTOMERSETUP","INSTALL"));
        XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES","RETURNEDPARTS"));
        XMLMAP.addChild(new XMLElem("UPGRADETYPE","UPGRADETYPE"));

        // BH AVAILABILITYLIST Structure       
    	XMLElem list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLFCTRANSAVAILElem());
	
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
    public String getVeName() { return "ADSMODELCONVERT";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "MODELCONVERT_UPDATE"; }

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
