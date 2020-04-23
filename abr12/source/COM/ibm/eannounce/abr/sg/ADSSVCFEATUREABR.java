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
*/
// ADSSVCFEATUREABR.java,v
// Revision 1.6  2008/05/28 13:46:08  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.5  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.4  2008/05/27 12:33:36  wendy
// implement spec chgs for "SG FS ABR ADS System Feed 20080508.doc"
//
// Revision 1.3  2008/05/07 14:12:10  wendy
// extend XMLMQRoot
//
// Revision 1.2  2008/05/01 12:50:09  wendy
// updated for SG FS ABR ADS System Feed 20080430.doc
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
public class ADSSVCFEATUREABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SVCFEATURE_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("FEATUREENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FEATURECODE","FEATURECODE"));
        XMLMAP.addChild(new XMLElem("FCTYPE","SVCFCTYPE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("PRICEDFEATURE","PRICEDFEATURE"));
        XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));
        XMLMAP.addChild(new XMLElem("TANDC"));
        XMLMAP.addChild(new XMLElem("FCCAT","SVCFCCAT"));
        XMLMAP.addChild(new XMLElem("FCSUBCAT","SVCFCSUBCAT"));
        XMLMAP.addChild(new XMLElem("FCGRP"));
        XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG","CONFIGURATORFLAG"));

        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("FCMKTGNAME","FCMKTGDESC"));
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
    public String getVeName() { return "ADSSVCFEATURE";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SVCFEATURE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.6";
    }
}
