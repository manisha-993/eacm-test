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
<SWPRODSTRUCT_DELETE>		1
<ACTIVITY>	</ACTIVITY>	2	SWPRODSTRUCT	Activity
<DTSOFABR>	</DTSOFABR>	2	SWPRODSTRUCT	ABR Queued
<SWTMFENTITYTYPE>	</SWTMFENTITYTYPE>	2	SWPRODSTRUCT	ENTITYTYPE
<SWTMFENTITYID>	</SWTMFENTITYID>	2	SWPRODSTRUCT	ENTITYID
<DTSOFUPDATE>	</DTSOFUPDATE>	2	SWPRODSTRUCT	VALFROM
<PARENTID>	</PARENTID>	2	SWPRODSTRUCT	ENTITY2ID
<CHILDID>	</CHILDID>	2	SWPRODSTRUCT	ENTITY1ID
<PDHDOMAIN>	</PDHDOMAIN>	2	SWPRODSTRUCT	PDHDOMAIN
	</SWPRODSTRUCT_DELETE>	1
*
*/
// ADSDELSWPRODSTRUCTABR.java,v
// Revision 1.3  2008/05/28 13:46:07  wendy
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
public class ADSDELSWPRODSTRUCTABR extends XMLMQDelete
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SWPRODSTRUCT_DELETE");
         // level2
        XMLMAP.addChild(new XMLFixedElem("ACTIVITY", "Delete"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFABR")); // pull from profile.endofday
        XMLMAP.addChild(new XMLElem("SWTMFENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("SWTMFENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLValFromElem("DTSOFUPDATE"));
        XMLMAP.addChild(new XMLRelatorElem("PARENTID","ENTITY2ID","MODEL"));
        XMLMAP.addChild(new XMLRelatorElem("CHILDID","ENTITY1ID","SWFEATURE"));
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
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
    public String getVeName() { return "DQVESWPRODSTRUCT2";}// just get parent and child "ADSSWPRODSTRUCT";}

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
