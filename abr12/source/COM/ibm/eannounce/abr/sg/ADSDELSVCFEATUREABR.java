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
<SVCFEATURE_DELETE>	1
<ACTIVITY>	</ACTIVITY>	2	SVCFEATURE	Activity
<DTSOFABR>	</DTSOFABR>	2	SVCFEATURE	ABR Queued
<FEATUREENTITYTYPE>	</FEATUREENTITYTYPE>	2	SVCFEATURE	ENTITYTYPE
<FEATUREENTITYID>	</FEATUREENTITYID>	2	SVCFEATURE	ENTITYID
<DTSOFUPDATE>	</DTSOFUPDATE>	2	SVCFEATURE	VALFROM
<FEATURECODE>	</FEATURECODE>	2	SVCFEATURE	FEATURECODE
<PDHDOMAIN>	</PDHDOMAIN>	2	SVCFEATURE	PDHDOMAIN
	</SVCFEATURE_DELETE>	1
*
*/
// ADSDELSVCFEATUREABR.java,v
// Revision 1.3  2008/05/28 13:46:07  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/01 13:04:53  wendy
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
public class ADSDELSVCFEATUREABR extends XMLMQDelete
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SVCFEATURE_DELETE");
         // level2
        XMLMAP.addChild(new XMLFixedElem("ACTIVITY", "Delete"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFABR")); // pull from profile.endofday
        XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("FEATUREENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLValFromElem("DTSOFUPDATE"));
        XMLMAP.addChild(new XMLElem("FEATURECODE","FEATURECODE"));
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
    }


    /**********************************
    * get xml object mapping
    */
    public XMLElem getXMLMap() {
        return XMLMAP;
    }

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
        return "1.3";
    }
}
