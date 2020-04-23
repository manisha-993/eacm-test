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
// $Log: ADSUNBUNDCOMPABR.java,v $
// Revision 1.9  2012/02/29 14:57:12  wangyulo
// Defect 670525 Correct mapping for BHACCTASGNGRP and PRFTCTR
//
// Revision 1.8  2011/12/14 02:26:31  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.7  2011/09/19 14:02:31  guobin
// Change mapping of <AMRTZTNLNGTH> to Long Description
//
// Revision 1.6  2011/02/24 13:23:54  guobin
// UNBUNDCOMP_UPDATE	Fix for BHALM00042866	8.00	Change BHPRODHIERCD to map from Long Description
//
// Revision 1.5  2010/10/29 15:18:05  rick
// changing MQCID again.
//
// Revision 1.4  2010/10/12 19:24:56  rick
// setting new MQCID value
//
// Revision 1.3  2010/09/16 19:23:54  rick
// fixing paren.
//
// Revision 1.2  2010/09/16 19:16:50  rick
// changing AMRTZTNLNGTH from short description
// to long description.
//
// Revision 1.1  2010/08/05 20:29:18  rick
// adding UNBUNDCOMP XML for BH 1
//
//

public class ADSUNBUNDCOMPABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("UNBUNDCOMP_UPDATE");
        XMLMAP.addChild(new XMLVMElem("UNBUNDCOMP_UPDATE","1"));
        // level2
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("UNBUNDCOMPID","UNBUNDCOMPID"));
        XMLMAP.addChild(new XMLElem("SHRTNAM","SHRTNAM"));
        //UNBUNDCOMP_UPDATE	Fix for BHALM00042866	8.00	Change BHPRODHIERCD to map from Long Description
        XMLMAP.addChild(new XMLElem("BHPRODHIERCD","BHPRODHIERCD"));
        
        /**
         * Defect 670525 Correct mapping for BHACCTASGNGRP and PRFTCTR
         * BHACCTASGNGRP   Short Description NLSID=1
         * PRFTCTR   	   Flag Description Class
         */
        //XMLMAP.addChild(new XMLElem("BHACCTASGNGRP","BHACCTASGNGRP"));
        XMLMAP.addChild(new XMLElem("BHACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));
        XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH","AMRTZTNLNGTH"));
        XMLMAP.addChild(new XMLElem("AMRTZTNSTRT","AMRTZTNSTRT"));
        //XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR"));
        XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR",XMLElem.FLAGVAL));
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
    public String getMQCID() { return "UNBUNDCOMP_UPDATE"; }

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
