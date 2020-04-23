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
<MODEL_DELETE>		1
<ACTIVITY>	</ACTIVITY>	2	MODEL	Activity
<DTSOFABR>	</DTSOFABR>	2	MODEL	ABR Queued
<MODELENTITYTYPE>	</MODELENTITYTYPE>	2	MODEL	ENTITYTYPE
<MODELENTITYID>	</MODELENTITYID>	2	MODEL	ENTITYID
<DTSOFUPDATE>	</DTSOFUPDATE>	2	MODEL	VALFROM
<MACHTYPE>	</MACHTYPE>	2	MODEL	MACHTYPEATR
<MODEL>	</MODEL>	2	MODEL	MODELATR
<PDHDOMAIN>	</PDHDOMAIN>	2	MODEL	PDHDOMAIN
	</MODEL_DELETE>	1
*
*/
// ADSDELMODELABR.java,v
// Revision 1.3  2008/05/28 13:46:09  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/27 12:33:36  wendy
// implement spec chgs for "SG FS ABR ADS System Feed 20080508.doc"
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
public class ADSDELMODELABR extends XMLMQDelete
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("MODEL_DELETE");
         // level2
        XMLMAP.addChild(new XMLFixedElem("ACTIVITY", "Delete"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFABR")); // pull from profile.endofday
        XMLMAP.addChild(new XMLElem("MODELENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("MODELENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLValFromElem("DTSOFUPDATE")); //pull from profile.valon
        XMLMAP.addChild(new XMLElem("MACHTYPE","MACHTYPEATR"));
        XMLMAP.addChild(new XMLElem("MODEL","MODELATR"));
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
    public String getMQCID() { return "MODEL"; }

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
