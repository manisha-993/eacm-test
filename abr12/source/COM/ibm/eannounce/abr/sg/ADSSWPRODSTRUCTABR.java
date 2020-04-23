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
1	<SWPRODSTRUCT_UPDATE>		1	SWPRODSTRUCT
1	<PDHDOMAIN>	</PDHDOMAIN>	2	SWPRODSTRUCT	PDHDOMAIN
1	<DTSOFMSG>	</DTSOFMSG>		2	SWPRODSTRUCT	ABR Queued
1	<ACTIVITY>	</ACTIVITY>		2	SWPRODSTRUCT	'Update'
1	<STATUS>	</STATUS>		2	SWPRODSTRUCT	STATUS
1	<TMFENTITYTYPE>	</TMFENTITYTYPE>	2	SWPRODSTRUCT	ENTITYTYPE
1	<TMFENTITYID>	</TMFENTITYID>	2	SWPRODSTRUCT	ENTITYID
1	<PARENTID>	</PARENTID>		2	SWPRODSTRUCT	ENTITY2ID
1	<CHILDID>	</CHILDID>		2	SWPRODSTRUCT	ENTITY1ID
1	<ORDERCODE>	</ORDERCODE>	2	n/a	n/a
1	<SYSTEMMAX>	</SYSTEMMAX>	2	n/a	n/a
1	<SYSTEMMIN>	</SYSTEMMIN>	2	n/a	n/a
1	<CONFIGURATORFLAG>	</CONFIGURATORFLAG>	2	n/a	n/a
1	<FULFILLMENTSYSIND>	</FULFILLMENTSYSIND>	2	n/a	n/a
1	<OSLIST>					2	n/a	n/a
0..N	<OSELEMENT>				3
1	<OSACTION>	</OSACTION>		4	n/a	n/a
1	<OS>	</OS>				4	n/a	n/a
		</OSELEMENT>			3
		</OSLIST>				2
1	<COUNTRYLIST>				2	SWPRODSTRUCT
0..N	<COUNTRYELEMENT>		3
1	<COUNTRYACTION>	</COUNTRYACTION>	4	AVAIL	CountryAction
1	<COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST
1	<EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>	4		Shipdate
1	<PUBFROM>	</PUBFROM>		4		PubFrom
1	<PUBTO>	</PUBTO>			4		PubTo
1	<HIDE>	</HIDE>				4	CATLGOR	CATHIDE
		</COUNTRYELEMENT>		3
		</COUNTRYLIST>			2
		</SWPRODSTRUCT_UPDATE>	1
*
*/
// ADSSWPRODSTRUCTABR.java,v
// Revision 1.3  2008/05/28 13:46:09  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/07 19:28:40  wendy
// Allow for multiple up or downlinks when getting parent or childid
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
public class ADSSWPRODSTRUCTABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SWPRODSTRUCT_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("TMFENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("TMFENTITYID","ENTITYID"));

        XMLMAP.addChild(new XMLRelatorElem("PARENTID","ENTITY2ID","MODEL"));
        XMLMAP.addChild(new XMLRelatorElem("CHILDID","ENTITY1ID","SWFEATURE"));
//1	<PARENTID>	</PARENTID>			2	SWPRODSTRUCT	ENTITY2ID
//1	<CHILDID>	</CHILDID>			2	SWPRODSTRUCT	ENTITY1ID
        XMLMAP.addChild(new XMLElem("ORDERCODE"));
        XMLMAP.addChild(new XMLElem("SYSTEMMAX"));
        XMLMAP.addChild(new XMLElem("SYSTEMMIN"));
        XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
        XMLMAP.addChild(new XMLElem("FULFILLMENTSYSIND"));
        XMLMAP.addChild(new XMLElem("OSLIST"));

		XMLElem list = new XMLElem("COUNTRYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCtryTMFElem());
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
    public String getVeName() { return "ADSSWPRODSTRUCT";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SWPRODSTRUCT"; }

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
