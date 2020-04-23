//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//<?xml version="1.0" encoding="UTF-8" ?>	
/**********************************************************************************
*
<SLEORGNPLNTCODE_UPDATE xmlns="http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/SLEORGNPLNTCODE_UPDATE">	
<DTSOFMSG>	</DTSOFMSG>         Entity          Attribute
<ACTIVITY>	</ACTIVITY>         SLEORGNPLNTCODE Activity Update or "Delete"
<STATUS>	</STATUS>           SLEORGNPLNTCODE Status
<ENTITYTYPE>    </ENTITYTYPE>       SLEORGNPLNTCODE ENTITYTYPE
<ENTITYID>      </ENTITYID>         SLEORGNPLNTCODE ENTITYID
<COUNTRY_FC>	</COUNTRY_FC>       SLEORGNPLNTCODE COUNTRYLIST
<MODCATG>	</MODCATG>          SLEORGNPLNTCODE MODCATG
<PLNTCD>	</PLNTCD>           SLEORGNPLNTCODE PLNTCD
<SLEORG>	</SLEORG>           SLEORGNPLNTCODE SLEORG
</SLEORGNPLNTCODE_UPDATE>
*/
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

public class ADSSLEORGNPLNTCODEABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SLEORGNPLNTCODE_UPDATE");
        XMLMAP.addChild(new XMLVMElem("SLEORGNPLNTCODE_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));

        XMLElem list = new XMLElem("COUNTRY_FC","COUNTRYLIST",XMLElem.FLAGVAL);
        XMLMAP.addChild(list);
        XMLMAP.addChild(new XMLElem("MODCATG","MODCATG",XMLElem.ATTRVAL));
        XMLMAP.addChild(new XMLElem("PLNTCD","PLNTCD",XMLElem.SHORTDESC));
        XMLMAP.addChild(new XMLElem("SLEORG","SLEORG"));
//        Add	New attribute -CQ 28230	10.00		1	1.0	<SLEORGGRP>	</SLEORGGRP>	2	SLEORGNPLNTCODE_UPDATE /SLEORGGRP		SLEORGNPLNTCODE	SLEORGGRP		"Long Description
//        NLSID = 1"	 	Always		 	 				 				
//        Add	New attribute -CQ 28230	11.00		1	1.0	<BHRELNO>	</BHRELNO>	2	SLEORGNPLNTCODE_UPDATE /BHRELNO		SLEORGNPLNTCODE	BHRELNO		???	????	Always		 	 				 				
//        Add	New attribute -CQ 28230	12.00		1	1.0	<LEGACYSLEORG>	</LEGACYSLEORG>	2	SLEORGNPLNTCODE_UPDATE /LEGACYSLEORG		SLEORGNPLNTCODE	LEGACYSLEORG		???	????	Always		 	 				 				
//        Add	New attribute -CQ 28230	13.00		1	1.0	<LEGACYPLNTCD>	</LEGACYPLNTCD>	2	SLEORGNPLNTCODE_UPDATE /LEGACYPLNTCD		SLEORGNPLNTCODE	LEGACYPLNTCD		???	????	Always		 	 				 				
        XMLMAP.addChild(new XMLElem("SLEORGGRP","SLEORGGRP"));
        XMLMAP.addChild(new XMLElem("BHRELNO","BHRELNO"));
        XMLMAP.addChild(new XMLElem("LEGACYSLEORG","LEGACYSLEORG"));
        XMLMAP.addChild(new XMLElem("LEGACYPLNTCD","LEGACYPLNTCD"));

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
    public String getVeName() { return "dummy";}

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "SLEORGNPLNTCODE_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.5 $";
    }
}
