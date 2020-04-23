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

//$Log: ADSREFOFERFEATABR.java,v $
//Revision 1.5  2012/07/12 14:12:55  wangyulo
//Build request for defect 760385 Incorrect CID in MQ of REFOFERFEAT on OEM data feed to WWPRT SIT
//
//Revision 1.4  2012/04/26 13:29:13  wangyulo
//Defect 711214  MIW Outbound issue when remove a REFOFERFEAT from REFOFER
//
//Revision 1.3  2012/03/15 08:16:31  liuweimi
//Updated MIW mappings -- REFOFER_UPDATE, REFOFERFEAT_UPDATE
//REFOFER	CQ 197625	Delete MKTGLNGDESC, Add PRFTCTR
//REFOFERFEAT	CQ 197625	Add MFRFEATDESC and PRFTCTR, Change Attibute Names
//
//Revision 1.2  2011/12/14 02:25:19  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.1  2011/09/08 07:49:38  guobin
//REFOFERFEAT ADS ABR
//


public class ADSREFOFERFEATABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
    	
        XMLMAP = new XMLGroupElem("REFOFERFEAT_UPDATE");
        XMLMAP.addChild(new XMLVMElem("REFOFERFEAT_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("FEATID","FEATID"));
        XMLMAP.addChild(new XMLElem("MKTGDIV","MKTGDIV"));
//        Add	CQ 197625	12.02	1.0		1	1.0	<PRFTCTR>	</PRFTCTR>	2	REFOFERFEAT_UPDATE /PRFTCTR			REFOFERFEAT	PRFTCTR																										
        XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR"));
        
        //level 2 LANGUAGELIST
        XMLElem langlist = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(langlist);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        langlist.addChild(langelem);
        // level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
//        Change	CQ 197625  - Rename Attribute	12.40	1.0		1	1.0	<MFRPRODID MFRFEATID>	</MFRPRODID MFRFEATID>	4	REFOFERFEAT_UPDATE /LANGUAGELIST /LANGUAGEELEMENT /MFRPRODID MFRFEATID			REFOFERFEAT	MFRPRODDESC MFRFEATID					
//        Add	CQ 197625	12.42	1.0		1	1.0	<MFRFEATDESC>	</MFRFEATDESC>	4	REFOFERFEAT_UPDATE /LANGUAGELIST /NLSID /MFRFEATDESC			REFOFERFEAT	MFRFEATDESC					
//        Change	CQ 197625  - Rename Attribute	12.50	1.0		1	1.0	<MFRLNGPRODDESC MFRFEATLNGDESC>	</MFRLNGPRODDESC MFRFEATLNGDESC>	4	REFOFERFEAT_UPDATE /LANGUAGELIST /LANGUAGEELEMENT /MFRLNGPRODDESC MFRFEATLNGDESC			REFOFERFEAT	MFRLNGPRODDESC MFRFEATLNGDESC					
        langelem.addChild(new XMLElem("MFRFEATID","MFRFEATID"));
        langelem.addChild(new XMLElem("MFRFEATDESC","MFRFEATDESC"));        
        langelem.addChild(new XMLElem("MFRFEATLNGDESC","MFRFEATLNGDESC"));
        //end level 2 LANGUAGELIST
        
        //level 2 RELATEDREFOFERLIST
        XMLElem reflist = new XMLGroupElem("RELATEDREFOFERLIST","REFOFER","U:REFOFERREFOFERFEAT:U",true);//REFOFERREFOFERFEAT -u
        XMLMAP.addChild(reflist);
        // level 3
        XMLElem reflem = new XMLNLSElem("RELATEDREFOFERELEMENT");
        reflist.addChild(reflem);
        //level 4
        reflem.addChild(new XMLActivityElem("ACTIVITY"));
        reflem.addChild(new XMLElem("PRODUCTID","PRODUCTID"));
        //end level2 RELATEDREFOFERLIST
        
        
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
    public String getVeName() { return "ADSREFOFERFEAT";} //when no VE to use, it can return "dummy"

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "REFOFERFEAT_UPDATE"; }

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
