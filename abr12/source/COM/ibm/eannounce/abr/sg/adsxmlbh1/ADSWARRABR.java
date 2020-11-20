// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************

*/
// $Log: ADSWARRABR.java,v $
// Revision 1.13  2020/04/28 07:00:25  xujianbo
// Recover code for EACM-2949 Development - Additional warranty attributes to support oem in iERP
//
// Revision 1.11  2020/03/31 10:18:08  xujianbo
//     EACM-2949 Development - Additional warranty attributes to support oem in iERP
//
// Revision 1.10  2013/11/05 13:35:54  guobin
// New RTC item 105978 - Add BHWARRCATEGORY to WARR_UPDATE
//
// Revision 1.9  2011/12/14 02:26:42  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.8  2011/05/24 13:44:06  lucasrg
// Added <WARRCATG> (CHIS/CEDS)
//
// Revision 1.7  2011/03/11 02:06:46  guobin
// Change	2011-03-10 correct attribute Name
//
// Revision 1.6  2011/01/20 08:09:22  guobin
//  Add	CQ 32156  and Move out of LANGUAGE ELEMENT
//
// Revision 1.5  2010/11/08 05:52:21  rick
// Misc changes from last mapping SS.
//
// Revision 1.4  2010/10/29 15:18:05  rick
// changing MQCID again.
//
// Revision 1.3  2010/10/12 19:24:56  rick
// setting new MQCID value
//
// Revision 1.2  2010/08/12 14:33:24  rick
// correcting RESPPROF
//
// Revision 1.1  2010/08/03 16:53:17  rick
// Adding WARR.
//

public class ADSWARRABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("WARR_UPDATE");
        XMLMAP.addChild(new XMLVMElem("WARR_UPDATE","1"));

        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("WARRENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("WARRENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("WARRID","WARRID"));
//        Add	CQ 32156	8.50		1	1.0	<WARRDATERULEKEY>	</WARRDATERULEKEY>	2	WARR_UPDATE /WARRDATERULEKEY		WARR	WARRDATERULEKEY
//        Change	Move out of LANGUAGE ELEMENT	9.00		1	1.0	<COVRHR>	</COVRHR>	2	WARR_UPDATE /COVRHR		WARR	COVRHR
//        Change	Move out of LANGUAGE ELEMENT	10.00		1	1.0	<RESPROF>	</RESPROF>	2	WARR_UPDATE /RESPROF		WARR	RESPROF
//        Change	Move out of LANGUAGE ELEMENT	11.00		1	1.0	<WARRPRIOD>	</WARRPRIOD>	2	WARR_UPDATE /WARRPRIOD		WARR	WARRPRIOD
//        Change	Move out of LANGUAGE ELEMENT	12.00		1	1.0	<WARRPRIODUOM>	</WARRPRIODUOM>	2	WARR_UPDATE /WARRPRIODUOM		WARR	WARRPRIODUOM
//        Change	Move out of LANGUAGE ELEMENT	13.00		1	1.0	<WARRTYPE>	</WARRTYPE>	2	WARR_UPDATE /WARRTYPE		WARR	WARRTYPE
        XMLMAP.addChild(new XMLElem("WARRDATERULEKEY","WARRDATERULEKEY"));
        XMLMAP.addChild(new XMLElem("COVRHR","COVRHR"));
        //Change	2011-03-10 correct attribute Name
        //XMLMAP.addChild(new XMLElem("RESPROF","RESPROF"));
        XMLMAP.addChild(new XMLElem("RESPPROF","RESPPROF"));
        XMLMAP.addChild(new XMLElem("WARRPRIOD","WARRPRIOD"));
        XMLMAP.addChild(new XMLElem("WARRPRIODUOM","WARRPRIODUOM"));
        XMLMAP.addChild(new XMLElem("WARRTYPE","WARRTYPE"));
       //Add	CEDS/CHIS	13,10		1	1,0	<WARRCATG>	</WARRCATG>
        XMLMAP.addChild(new XMLElem("WARRCATG","WARRCATG"));
       //Add    BHALM00221724  WARR	BHWARRCATEGORY		
        XMLMAP.addChild(new XMLElem("BHWARRCATEGORY","BHWARRCATEGORY",XMLElem.SHORTDESC));
        XMLMAP.addChild(new XMLElem("OEMESAPRTSLBR","OEMESAPRTSLBR"));
        XMLMAP.addChild(new XMLElem("OEMESAPRTSONY","OEMESAPRTSONY"));
        XMLMAP.addChild(new XMLElem("TECHADV","TECHADV"));
        XMLMAP.addChild(new XMLElem("REMCODLOAD","REMCODLOAD"));
        XMLMAP.addChild(new XMLElem("ENHCOMRES","ENHCOMRES"));
        XMLMAP.addChild(new XMLElem("PREDSUPP","PREDSUPP"));
        XMLMAP.addChild(new XMLElem("TIERMAIN","TIERMAIN"));
        XMLElem list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);
         
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
         
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","INVNAME"));
        langelem.addChild(new XMLElem("MKTGNAME","MKTGNAME"));
        langelem.addChild(new XMLElem("WARRDESC","WARRDESC"));

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
    public String getMQCID() { return "WARR_UPDATE"; }

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
