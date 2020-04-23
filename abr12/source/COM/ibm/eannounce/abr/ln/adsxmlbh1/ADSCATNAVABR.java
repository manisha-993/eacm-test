// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.eannounce.abr.util.*;

/**********************************************************************************
*/
//$Log: ADSCATNAVABR.java,v $
//Revision 1.1  2015/02/04 14:55:48  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.13  2011/12/14 02:20:44  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.12  2010/12/10 09:22:51  guobin
//Change  CATATTRLIST tag use multiUse structure of XMLGroupElem
//
//Revision 1.11  2010/12/08 07:52:04  guobin
//change tag CATATTRLIST
//
//Revision 1.10  2010/12/08 07:22:42  guobin
//change the list  of <CATATTRLIST>
//
//Revision 1.9  2010/12/07 06:36:00  guobin
//Remove the path of CATATTR
//
//Revision 1.8  2010/11/30 02:21:15  guobin
//change <AUDIENCE> to <CATAUDIENCE>
//
//Revision 1.7  2010/11/24 08:29:32  guobin
//add ENTITYID in <CATDETAILSLIST>
//
//Revision 1.6  2010/11/17 00:20:55  rick
//fixing audience
//
//Revision 1.5  2010/11/16 13:55:47  rick
//add path to CATATTR.
//
//Revision 1.4  2010/11/02 19:23:35  rick
//various changes /fixes for the CATNAV XML
//
//Revision 1.3  2010/10/29 15:18:04  rick
//changing MQCID again.
//
//Revision 1.2  2010/10/26 17:00:54  rick
//taking out CATENTITYID
//
//Revision 1.1  2010/10/18 23:21:55  rick
//Initial version of CATNAV XML ABR
//
public class ADSCATNAVABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("CATNAV_UPDATE");
        XMLMAP.addChild(new XMLVMElem("CATNAV_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("TYPE","CATNAVTYPE"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));

        XMLMAP.addChild(new XMLElem("PUBLISH","CATPUBLISH"));
        XMLMAP.addChild(new XMLElem("CATBR","CATBR"));
        XMLMAP.addChild(new XMLElem("CATNAME","CATNAME"));
        
        XMLElem list = new XMLElem("PROJECTLIST");
        XMLMAP.addChild(list);
        XMLElem listelem = new XMLChgSetElem("PROJECTELEMENT");
        list.addChild(listelem);
        listelem.addChild(new XMLMultiFlagElem("PROJECT","PROJCDNAMF","PROJECTACTIVITY",XMLElem.FLAGVAL));
             

        list = new XMLElem("COUNTRYLIST");
        XMLMAP.addChild(list);
        list.addChild(new XMLCtryImgElem());

        list = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(list);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        list.addChild(langelem);
        //level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("CATFMLY","CATFMLY"));
        langelem.addChild(new XMLElem("CATSER","CATSER"));
        langelem.addChild(new XMLElem("CATOPTGRPNAM","CATOPTGRPNAM"));
        langelem.addChild(new XMLElem("CATNAME","CATNAME"));
        langelem.addChild(new XMLElem("CATNAVL1","CATNAVL1"));
        langelem.addChild(new XMLElem("CATNAVL2","CATNAVL2"));
        langelem.addChild(new XMLElem("CATNAVL3","CATNAVL3"));
        langelem.addChild(new XMLElem("CATNAVL4","CATNAVL4"));
        langelem.addChild(new XMLElem("PRICEDISCLAIMER","PRICEDISCLAIMER"));
        langelem.addChild(new XMLElem("IMAGEDISCLAIMER","IMGDISCLAIMER"));
        langelem.addChild(new XMLElem("FEATUREBENEFIT","FBSTMT"));
        langelem.addChild(new XMLElem("FAMILYDESCOVERRIDE","CATFMLYDESC"));
        langelem.addChild(new XMLElem("SERIESDESCOVERRIDE","CATSERDESC"));
        langelem.addChild(new XMLElem("SERIESHEADING","CATSERHEAD"));
        langelem.addChild(new XMLElem("LONGMKTGMSG","LONGMKTGMSG"));

        list = new XMLElem("CATAUDIENCELIST");
        XMLMAP.addChild(list);
        listelem = new XMLChgSetElem("CATAUDIENCEELEMENT");
        list.addChild(listelem);
        listelem.addChild(new XMLMultiFlagElem("CATAUDIENCE","CATAUDIENCE","ACTIVITY",XMLElem.ATTRVAL));
        // level 2
        list = new XMLGroupElem("CATDETAILSLIST","CATGROUP");
	    XMLMAP.addChild(list);
	    XMLElem catdetelem = new XMLElem("CATDETAILSELEMENT");
        list.addChild(catdetelem);
        catdetelem.addChild(new XMLActivityElem("ACTIVITY"));
        catdetelem.addChild(new XMLElem("ENTITYID","ENTITYID"));
        catdetelem.addChild(new XMLElem("CATSEQ","CATSEQ"));
        list = new XMLElem("CATDESCLIST");
        catdetelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("CATDESCELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLActivityElem("ACTIVITY"));
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("GROUPDESCRIPTION","GROUPDESCRIPTION"));
        // level 4
        list = new XMLGroupElem("CATATTRLIST","CATATTR","D:CATGROUPATTR:D",true);
        //list = new XMLGroupElem("CATATTRLIST","CATATTR");
        catdetelem.addChild(list);
        // level 5
        XMLElem catattrelem = new XMLElem("CATATTRELEMENT");
        list.addChild(catattrelem);
        //level 6
        catattrelem.addChild(new XMLActivityElem("ACTIVITY"));
        catattrelem.addChild(new XMLElem("CATENTITYTYPE","DAENTITYTYPE"));
        catattrelem.addChild(new XMLElem("CATATTRIBUTECODE","DAATTRIBUTECODE"));
        catattrelem.addChild(new XMLElem("CATSEQ","CATSEQ"));
        list = new XMLElem("CATDESCLIST");
        catattrelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("CATDESCELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLActivityElem("ACTIVITY"));
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION","ATTRDESCRIPTION"));
        // level 2
        list = new XMLGroupElem("CATVAMLIST","CATVAMATTR");
	XMLMAP.addChild(list);
	XMLElem catvamelem = new XMLElem("CATVAMATTRELEMENT");
        list.addChild(catvamelem);
        catvamelem.addChild(new XMLActivityElem("ACTIVITY"));
        catvamelem.addChild(new XMLElem("CATENTITYTYPE","DAENTITYTYPE"));
        catvamelem.addChild(new XMLElem("CATATTRIBUTECODE","DAATTRIBUTECODE"));
        catvamelem.addChild(new XMLElem("CATCOLUMN","CATCOLUMN"));
        catvamelem.addChild(new XMLElem("CATSEQ","CATSEQ"));
        
        list = new XMLElem("CATDESCLIST");
        catvamelem.addChild(list);
        // level 5
        langelem = new XMLNLSElem("CATDESCELEMENT");
        list.addChild(langelem);
        //level 6
        langelem.addChild(new XMLActivityElem("ACTIVITY"));
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION","ATTRDESCRIPTION"));
        
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
    public String getVeName() { return "ADSCATNAV";}

     /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "CATNAV_UPDATE"; }

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
