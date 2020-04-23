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
/**********************************************************************************
 * ADSIMGABR.java
 * 
 */
//$Log: ADSIMGABR.java,v $
//Revision 1.5  2011/12/14 02:21:58  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.4  2011/01/21 12:15:26  guobin
//add comments log
//
public class ADSIMGABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("IMAGE_UPDATE");
        XMLMAP.addChild(new XMLVMElem("IMAGE_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        
        XMLMAP.addChild(new XMLElem("MARKETINGIMAGEFILENAME","MKTGIMGFILENAM"));
        //get the attribute of the IMAGECONTENTS from opicm.blob.ATTRIBUTEVALUE
        XMLMAP.addChild(new XMLImageElem());
        
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
    public String getMQCID() { return "IMAGE_UPDATE"; }

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
