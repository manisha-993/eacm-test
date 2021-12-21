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

//$Log: ADSREFOFERABR.java,v $
//Revision 1.3  2012/03/15 08:16:31  liuweimi
//Updated MIW mappings -- REFOFER_UPDATE, REFOFERFEAT_UPDATE
//REFOFER	CQ 197625	Delete MKTGLNGDESC, Add PRFTCTR
//REFOFERFEAT	CQ 197625	Add MFRFEATDESC and PRFTCTR, Change Attibute Names
//
//Revision 1.2  2011/12/14 02:25:06  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.1  2011/09/08 07:48:58  guobin
//REFOFER ADS ABR
//


public class ADSREFOFERABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
    	
        XMLMAP = new XMLGroupElem("REFOFER_UPDATE");
        XMLMAP.addChild(new XMLVMElem("REFOFER_UPDATE","1"));
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("PRODUCTID","PRODUCTID"));
        XMLMAP.addChild(new XMLElem("MFRPRODTYPE","MFRPRODTYPE"));
        XMLMAP.addChild(new XMLElem("MFRPRODID","MFRPRODID"));
        XMLMAP.addChild(new XMLElem("MKTGDIV","MKTGDIV"));
//        Add	CQ 197625 	14.10	1.00		1	1.0	<PRFTCTR>	</PRFTCTR>	2	REFOFER_UPDATE /PRFTCTR			REFOFER	PRFTCTR																										
        XMLMAP.addChild(new XMLElem("PRFTCTR","PRFTCTR"));
      
        XMLMAP.addChild(new XMLElem("CATGSHRTDESC","CATGSHRTDESC"));
        XMLMAP.addChild(new XMLElem("STRTOFSVC","STRTOFSVC"));//need to confirm STRTOSVC(doc) or STRTOFSVC(DB)
        XMLMAP.addChild(new XMLElem("ENDOFSVC","ENDOFSVC"));
        XMLMAP.addChild(new XMLElem("VENDNAM","VENDNAM"));
        XMLMAP.addChild(new XMLElem("MACHRATECATG","MACHRATECATG"));
        
        XMLMAP.addChild(new XMLElem("CECSPRODKEY","CECSPRODKEY"));
        XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC","MAINTANNBILLELIGINDC"));
        XMLMAP.addChild(new XMLElem("SYSIDUNIT","SYSIDUNIT"));//no this column on the GUI
        //Add	New requirements	22.05		1	1.0	<FSLMCPU>	</FSLMCPU>  	REFOFER	FSLMCPU
        //Add	New requirement	22.06		1	1.0	<PRODSUPRTCD>	</PRODSUPRTCD>	REFOFER	PRODSUPRTCD
        //Dave,Linda and I talked about this field FSLMCPU. It should be SYSIDUNIT.
        //Dave will update the mapping. FSLMCPU is their field not ours.
        //XMLMAP.addChild(new XMLElem("FSLMCPU","FSLMCPU"));//no this column on the GUI
        XMLMAP.addChild(new XMLElem("PRODSUPRTCD","PRODSUPRTCD"));//no this column on the GUI

        XMLMAP.addChild(new XMLElem("DOMAIN","DOMAIN"));
        //level 2 LANGUAGELIST
        XMLElem langlist = new XMLElem("LANGUAGELIST");
        XMLMAP.addChild(langlist);
        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        langlist.addChild(langelem);
        // level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("MFRPRODDESC","MFRPRODDESC"));
//        langelem.addChild(new XMLElem("MFRLNGPRODDESC","MFRLNGPRODDESC"));
        //level 2
        XMLElem list = new XMLElem("COUNTRYLIST");
        XMLMAP.addChild(list);
        // level 3
        XMLElem listelem = new XMLChgSetElem("COUNTRYELEMENT");
        list.addChild(listelem);
        //level 4
        listelem.addChild(new XMLMultiFlagElem("COUNTRY_FC","COUNTRYLIST","ACTION",XMLElem.FLAGVAL));        
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
    public String getMQCID() { return "REFOFER_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.3 $";
    }
}
