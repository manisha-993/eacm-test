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
// ADSSVCPRODSTRUCTABR.java,v
// Revision 1.5  2008/05/28 13:46:07  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.4  2008/05/07 19:28:40  wendy
// Allow for multiple up or downlinks when getting parent or childid
//
// Revision 1.3  2008/05/07 15:05:55  wendy
// extend XMLMQRoot
//
// Revision 1.2  2008/05/01 12:55:34  wendy
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
public class ADSSVCPRODSTRUCTABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SVCPRODSTRUCT_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("TMFENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("TMFENTITYID","ENTITYID"));

        XMLMAP.addChild(new XMLRelatorElem("PARENTID","ENTITY2ID","MODEL"));
        XMLMAP.addChild(new XMLRelatorElem("CHILDID","ENTITY1ID","SVCFEATURE"));
//1	<PARENTID>	</PARENTID>			2	SVCPRODSTRUCT	ENTITY2ID
//1	<CHILDID>	</CHILDID>			2	SVCPRODSTRUCT	ENTITY1ID

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
    public String getVeName() { return "ADSSVCPRODSTRUCT";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SVCPRODSTRUCT"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.5";
    }
}
