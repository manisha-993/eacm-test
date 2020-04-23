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
<MODELCONVERT_UPDATE>						1	MODELCONVERT
<PDHDOMAIN>	</PDHDOMAIN>					2	MODELCONVERT	PDHDOMAIN
<DTSOFMSG>	</DTSOFMSG>						2	MODELCONVERT	ABR Queued	DTS of ABR Queued
<ACTIVITY>	</ACTIVITY>						2	MODELCONVERT	Activity	"Update"
<MODELUPGRADEENTITYTYPE></MODELUPGRADEENTITYTYPE>	2	MODELCONVERT	ENTITYTYPE	"MODELCONVERT"
<MODELUPGRADEENTITYID>	</MODELUPGRADEENTITYID>	2	MODELCONVERT	ENTITYID
<FROMMACHTYPE>	</FROMMACHTYPE>				2	MODELCONVERT	FROMMACHTYPE
<FROMMODEL>	</FROMMODEL>					2	MODELCONVERT	FROMMODEL
<FROMMODELTYPE>	</FROMMODELTYPE>			2	MODELCONVERT	ENTITYTYPE	"MODEL"
<FROMMODELENTITYID>	</FROMMODELENTITYID>	2	MODEL	entityid	Search - lookup
<TOMACHTYPE>	</TOMACHTYPE>				2	MODELCONVERT	TOMACHTYPE
<TOMODEL>	</TOMODEL>						2	MODELCONVERT	TOMODEL
<TOMODELTYPE>	</TOMODELTYPE>				2	MODELCONVERT	ENTITYTYPE	"MODEL"
<TOMODELENTITYID>	</TOMODELENTITYID>		2	MODEL	entityid	Search - lookup
<STATUS>	</STATUS>						2	MODELCONVERT	STATUS
<CUSTOMERSETUP>	</CUSTOMERSETUP>			2	MODELCONVERT	INSTALL
<RETURNEDPARTSMES>	</RETURNEDPARTSMES>		2	MODELCONVERT	RETURNEDPARTS
<UPGRADETYPE>	</UPGRADETYPE>				2	MODELCONVERT	UPGRADETYPE
<COUNTRYLIST>								2	AVAIL
<COUNTRYELEMENT>							3
<COUNTRYACTION>	</COUNTRYACTION>			4	AVAIL	CountryAction	derived
<COUNTRY>	</COUNTRY>						4	AVAIL	COUNTRYLIST
<STATUS>	</STATUS>	4	AVAIL	STATUS	Flag Description Class
<PUBFROM>	</PUBFROM>						4	AVAIL	PubFrom	derived
<PUBTO>	</PUBTO>							4	AVAIL	PubTo	derived
	</COUNTRYELEMENT>						3
	</COUNTRYLIST>							2
	</MODELCONVERT_UPDATE>					1

*/
// ADSMODELCONVERTABR.java,v
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

        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("CUSTOMERSETUP","INSTALL"));
        XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES","RETURNEDPARTS"));
        XMLMAP.addChild(new XMLElem("UPGRADETYPE","UPGRADETYPE"));
		XMLElem list = new XMLElem("COUNTRYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCtryElem());
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
    public String getMQCID() { return "MODELCONVERT"; }

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
