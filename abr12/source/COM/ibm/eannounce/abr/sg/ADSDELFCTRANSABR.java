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
<FCTRANSACTION_DELETE>		1
<ACTIVITY>	</ACTIVITY>	2	FCTRANSACTION	Activity
<DTSOFABR>	</DTSOFABR>	2	FCTRANSACTION	ABR Queued
<FCTRANSACTIONENTITYTYPE>	</FCTRANSACTIONENTITYTYPE>	2	FCTRANSACTION	ENTITYTYPE
<FCTRANSACTIONENTITYID>	</FCTRANSACTIONENTITYID>	2	FCTRANSACTION	ENTITYID
<DTSOFUPDATE>	</DTSOFUPDATE>	2	FCTRANSACTION	VALFROM
<FROMMACHTYPE>	</FROMMACHTYPE>	2	FCTRANSACTION	FROMMACHTYPE
<FROMMODEL>	</FROMMODEL>	2	FCTRANSACTION	FROMMODEL
<FROMFEATURECODE>	</FROMFEATURECODE>	2	FCTRANSACTION	FROMFEATURECODE
<TOMACHTYPE>	</TOMACHTYPE>	2	FCTRANSACTION	TOMACHTYPE
<TOMODEL>	</TOMODEL>	2	FCTRANSACTION	TOMODEL
<TOFEATURECODE>	</TOFEATURECODE>	2	FCTRANSACTION	TOFEATURECODE
<PDHDOMAIN>	</PDHDOMAIN>	2	FCTRANSACTION	PDHDOMAIN
	</FCTRANSACTION_DELETE>	1

*
*/
// ADSDELFCTRANSABR.java,v
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
public class ADSDELFCTRANSABR extends XMLMQDelete
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("FCTRANSACTION_DELETE");
         // level2
        XMLMAP.addChild(new XMLFixedElem("ACTIVITY", "Delete"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFABR")); // pull from profile.endofday
        XMLMAP.addChild(new XMLElem("FCTRANSACTIONENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("FCTRANSACTIONENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLValFromElem("DTSOFUPDATE"));
        XMLMAP.addChild(new XMLElem("FROMMACHTYPE","FROMMACHTYPE"));
        XMLMAP.addChild(new XMLElem("FROMMODEL","FROMMODEL"));
        XMLMAP.addChild(new XMLElem("FROMFEATURECODE","FROMFEATURECODE"));
        XMLMAP.addChild(new XMLElem("TOMACHTYPE","TOMACHTYPE"));
        XMLMAP.addChild(new XMLElem("TOMODEL","TOMODEL"));
        XMLMAP.addChild(new XMLElem("TOFEATURECODE","TOFEATURECODE"));
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
    public String getMQCID() { return "FCTRANSACTION"; }

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
