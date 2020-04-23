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
// $Log: ADSSWFEATUREABR.java,v $
// Revision 1.1  2015/02/04 14:55:48  wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.8  2013/03/26 14:19:38  wangyulo
// fix the Defect 910328 -- the Update mapping for FEATURE_UPDATE xml
//
// Revision 1.7  2011/12/14 02:26:10  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.6  2011/03/11 01:48:18  guobin
// Change 2011-03-10	CQ26599 - correct attribute name
//
// Revision 1.5  2011/02/15 10:59:49  lucasrg
// Applied mapping updates for DM Cycle 2
//
// Revision 1.4  2010/10/29 15:18:05  rick
// changing MQCID again.
//
// Revision 1.3  2010/10/12 19:24:56  rick
// setting new MQCID value
//
// Revision 1.2  2010/06/23 21:13:39  rick
// fixing cvs prologue logging.
//
// Revision 1.2  2008/05/28 13:46:09  wendy
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
public class ADSSWFEATUREABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("FEATURE_UPDATE");
        XMLMAP.addChild(new XMLVMElem("FEATURE_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FEATURECODE","FEATURECODE"));
        XMLMAP.addChild(new XMLElem("FCTYPE","FCTYPE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("PRICEDFEATURE","PRICEDFEATURE"));
        XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));
        XMLMAP.addChild(new XMLElem("CHARGEOPTION","CHARGEOPTION")); //Changed from TANDC to CHARGEOPTION
        XMLMAP.addChild(new XMLElem("FCCAT","SWFCCAT"));
        XMLMAP.addChild(new XMLElem("FCSUBCAT","SWFCSUBCAT"));
        XMLMAP.addChild(new XMLElem("FCGRP","SWFCGRP"));
        XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
        //Change 2011-03-10	CQ26599 - correct attribute name
        XMLMAP.addChild(new XMLElem("LICNSOPTTYPE", "LICNSOPTTYPE"));
        //2013-03-13 defect 910328 with the doc BH FS ABR XML System Feed Mapping 20130312.xls
        //Line id 18.20 - MAINTPRICE.  This seems to have been in the mapping but I do not see it in the XML.  
        //The element should be included for FEATURE (w value if any) and for SWFEATURE (no value)
        //Line id 18.30 - LICENSETYPE.  
        //The element should be included for FEATURE (no value) and for SWFEATURE (w value if any)
        XMLMAP.addChild(new XMLElem("MAINTPRICE"));
        XMLMAP.addChild(new XMLElem("LICENSETYPE","LICENSETYPE"));
        
        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("MKTGDESC","SWFEATDESC"));
        langelem.addChild(new XMLElem("MKTGNAME"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
        langelem.addChild(new XMLElem("BHINVNAME","BHINVNAME"));
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
    public String getVeName() { return "ADSSWFEATURE";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "FEATURE_UPDATE"; }

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
