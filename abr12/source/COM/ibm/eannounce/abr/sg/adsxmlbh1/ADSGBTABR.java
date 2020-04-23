//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//<?xml version="1.0" encoding="UTF-8" ?>	
/**********************************************************************************
*/
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

//$Log: ADSGBTABR.java,v $
//Revision 1.10  2017/03/07 09:29:49  wangyul
//Story 1660265 - EACM- GBT XML update to add LEVEL17
//
//Revision 1.9  2015/04/27 09:29:04  wangyul
//GBT updates to add STATUS. This change is for IBM EACM and it requires an update to our  XSD for SS "EACM IBM FS ABR XML System Feed Mapping 20150422.xls"
//
//Revision 1.8  2011/12/14 02:21:25  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.7  2011/07/13 12:16:06  guobin
// change WWOCBRAND to WWOCCODE in GBT_UPDATE
//
//Revision 1.6  2011/04/14 06:12:23  guobin
//change the WWOCBRAND attribute to WWOCCODE
//
//Revision 1.5  2011/01/21 12:27:13  guobin
//add comments log
//

public class ADSGBTABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("GBT_UPDATE");
        XMLMAP.addChild(new XMLVMElem("GBT_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        //XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));

        XMLMAP.addChild(new XMLElem("SIEBELPRODLEV","SIEBELPRODLEV"));
        XMLMAP.addChild(new XMLElem("SAPPRIMBRANDCD","SAPPRIMBRANDCD"));
        XMLMAP.addChild(new XMLElem("SAPPRODFMLYCD","SAPPRODFMLYCD"));
        XMLMAP.addChild(new XMLElem("LEVEL17","LEVEL17"));
        XMLMAP.addChild(new XMLElem("WWOCPARNTS","WWOCPARNTS"));
        /**
         * Carol - no, I didn't know that WWOCBRAND has been replaced by WWOCCODE.  
         * Yes, the <WWOCBRAND> attribute should be derived from WWOCCODE - 
         * I will update the mapping spreadsheet accordingly,
         * -- Dave
         */
        //XMLMAP.addChild(new XMLElem("WWOCBRAND","WWOCBRAND"));
        XMLMAP.addChild(new XMLElem("WWOCCODE","WWOCCODE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("GBTDESC","GBTDESC"));
        XMLMAP.addChild(new XMLElem("RECTYPE","RECTYPE"));
        XMLMAP.addChild(new XMLElem("PRODTYPE","PRODTYPE"));
        XMLMAP.addChild(new XMLElem("EFFECTIVEDATE","EFFECTIVEDATE"));
        XMLMAP.addChild(new XMLElem("DELDATE","DELDATE"));
        XMLMAP.addChild(new XMLElem("MAPTOCD","MAPTOCD"));
        XMLMAP.addChild(new XMLElem("OMBRANDCD","OMBRANDCD"));
        XMLMAP.addChild(new XMLElem("OMPRODFMLYCD","OMPRODFMLYCD"));
        XMLMAP.addChild(new XMLElem("BPDBBRANDCD","BPDBBRANDCD"));
        XMLMAP.addChild(new XMLElem("MKTGRPTCD","MKTGRPTCD"));
        XMLMAP.addChild(new XMLElem("IGSSHADOWLOB","IGSSHADOWLOB"));
        XMLMAP.addChild(new XMLElem("TPRSSREVDIV","TPRSSREVDIV"));
        XMLMAP.addChild(new XMLElem("INTERNALNOTES","INTERNALNOTES"));
        
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
    public String getMQCID() { return "GBT_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.10 $";
    }
}
