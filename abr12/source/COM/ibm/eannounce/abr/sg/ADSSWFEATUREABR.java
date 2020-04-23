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
1 <SWFEATURE_UPDATE>
1	<PDHDOMAIN>	</PDHDOMAIN>	2	PDHDOMAIN
1	<DTSOFMSG>	</DTSOFMSG>	2	ABR Queued
1	<ACTIVITY>	</ACTIVITY>	2	"Update"
1	<FEATUREENTITYTYPE>	</FEATUREENTITYTYPE>	2	"SWFEATURE"
1	<FEATUREENTITYID>	</FEATUREENTITYID>	2	ENTITYID
1	<FEATURECODE>	</FEATURECODE>	2	FEATURECODE
1	<FCTYPE>	</FCTYPE>	2	FCTYPE
1	<STATUS>	</STATUS>	2	STATUS
1	<PRICEDFEATURE>	</PRICEDFEATURE>	2	PRICEDFEATURE
1	<ZEROPRICE>	</ZEROPRICE>	2	ZEROPRICE
1	<TANDC>	</TANDC>	2	CHARGEOPTION
1	<FCCAT>	</FCCAT>	2	SWFCCAT
1	<FCSUBCAT>	</FCSUBCAT>	2	SWFCSUBCAT
1	<FCGRP>	</FCGRP>	2	SWFCGRP
1	<CONFIGURATORFLAG>	</CONFIGURATORFLAG> 2 N/A
1	<LANGUAGELIST>		2
0..N	<LANGUAGEELEMENT>		3
1	<NLSID>	</NLSID>	4	NLSID
1	<FCMKTGNAME>	</FCMKTGNAME>	4	SWFEATDESC
		</LANGUAGEELEMENT>	3
		</LANGUAGELIST>	2
		</SWFEATURE_UPDATE>	1
*/
// ADSSWFEATUREABR.java,v
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
        XMLMAP = new XMLGroupElem("SWFEATURE_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("FEATUREENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FEATURECODE","FEATURECODE"));
        XMLMAP.addChild(new XMLElem("FCTYPE","FCTYPE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("PRICEDFEATURE","PRICEDFEATURE"));
        XMLMAP.addChild(new XMLElem("ZEROPRICE","ZEROPRICE"));
        XMLMAP.addChild(new XMLElem("TANDC","CHARGEOPTION"));
        XMLMAP.addChild(new XMLElem("FCCAT","SWFCCAT"));
        XMLMAP.addChild(new XMLElem("FCSUBCAT","SWFCSUBCAT"));
        XMLMAP.addChild(new XMLElem("FCGRP","SWFCGRP"));
        XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));

        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("FCMKTGNAME","SWFEATDESC"));
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
    public String getMQCID() { return "SWFEATURE"; }

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
