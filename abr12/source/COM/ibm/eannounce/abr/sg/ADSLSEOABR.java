// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.abr.util.*;


/**********************************************************************************
*
*
<SEO_UPDATE>		1	LSEO
<PDHDOMAIN>	</PDHDOMAIN>	2	LSEO	PDHDOMAIN
<DTSOFMSG>	</DTSOFMSG>	2	LSEO	ABR Queued
<ACTIVITY>	</ACTIVITY>	2	LSEO	Activity	"Update"
<SEOENTITYTYPE>	</SEOENTITYTYPE>	2	LSEO	ENTITYTYPE
<SEOENTITYID>	</SEOENTITYID>	2	LSEO	ENTITYID
<PARENTENTITYTYPE>	<PARENTENTITYTYPE>	2	MODEL	ENTITYTYPE
<PARENTENTITYID>	<PARENTENTITYID>	2	MODEL	ENTITYID
<PARENTMODEL>	<PARENTMODEL>	2	MODEL	MODELATR
<PARENTMACHTPE>	<PARENTMACHTPE>	2	MODEL	MACHTYPEATR
<SEOID>	</SEOID>	2	LSEO	SEOID
<STATUS>	</STATUS>	2	LSEO	STATUS
<CATEGORY>	</CATEGORY>	2	MODEL	COFCAT
<SUBCATEGORY>	</SUBCATEGORY>	2	MODEL	COFSUBCAT
<GROIUP>	</GROUP>	2	MODEL	COFGRP
<SUBGROUP>	</SUBGROUP>	2	MODEL	COFSUBGRP
<PRFCNTR>	</PRFCNTR>	2	LSEO	PRFTCTR
<ANNOUNCEDATE>	</ANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE
<ANNOUNCENUMBER>	</ANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER
<WITHDRAWANNOUNCEDATE>	</WITHDRAWANNOUNCEDATE>	2	ANNOUNCEMENT	ANNDATE
<WITHDRAWANNOUNCENUMBER>	</WITHDRAWANNOUNCENUMBER> 	2	ANNOUNCEMENT	ANNNUMBER
<PRDHIERCD>	</PRDHIERCD>	2	LSEO	BHPRODHIERCD
<ACCTASGNGRP>	</ACCTASGNGRP>	2	LSEO	BHACCTASGNGRP
<SPECIALBID>	</SPECIALBID>	2	WWSEO	SPECBID
<LANGUAGELIST>
<LANGUAGEELEMENT>		3
<NLSID>	</NLSID>	4	MODEL	NLSID
<INVNAME	</INVNAME>	4	WWSEO	PRCFILENAM
</LANGUAGEELEMENT>	3
</LANGUAGELIST>	2
</SEO_UPDATE	1

*/
//$Log: ADSLSEOABR.java,v $
//Revision 1.6  2010/03/15 15:28:00  rick
//changing to shortdesc for BHACCTASGNGRP and TAXCLS.
//
//Revision 1.5  2010/01/28 00:29:47  rick
//change to use BHACCTASGNGRP instead of
// ACCTASGNGRP and LSEO for MQCID.
//
//Revision 1.4  2010/01/07 18:03:03  wendy
//cvs failure again
//
public class ADSLSEOABR extends XMLMQRoot
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("SEO_UPDATE");
        // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("SEOENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("SEOENTITYID","ENTITYID"));

        XMLElem elem = new XMLGroupElem(null,"MODEL");
        XMLMAP.addChild(elem);
        elem.addChild(new XMLElem("PARENTENTITYTYPE","ENTITYTYPE"));
        elem.addChild(new XMLElem("PARENTENTITYID","ENTITYID"));
        elem.addChild(new XMLElem("PARENTMODEL","MODELATR"));
        elem.addChild(new XMLElem("PARENTMACHTPE","MACHTYPEATR"));

        XMLMAP.addChild(new XMLElem("SEOID","SEOID"));
        XMLMAP.addChild(new XMLElem("STATUS","STATUS",XMLElem.FLAGVAL));

        elem.addChild(new XMLElem("CATEGORY","COFCAT"));
        elem.addChild(new XMLElem("SUBCATEGORY","COFSUBCAT"));
        elem.addChild(new XMLElem("GROUP","COFGRP"));
        elem.addChild(new XMLElem("SUBGROUP","COFSUBGRP"));
       
        XMLMAP.addChild(new XMLElem("PRFCNTR","PRFTCTR",XMLElem.FLAGVAL));

        XMLElem annceelem = new XMLANNElem();
        XMLMAP.addChild(annceelem);        

        XMLMAP.addChild(new XMLElem("PRDHIERCD","BHPRODHIERCD",XMLElem.FLAGVAL));
        XMLMAP.addChild(new XMLElem("ACCTASGNGRP","BHACCTASGNGRP",XMLElem.SHORTDESC));

        XMLElem elemwwseo = new XMLGroupElem(null,"WWSEO");
        XMLMAP.addChild(elemwwseo);
        elemwwseo.addChild(new XMLElem("SPECIALBID","SPECBID"));
        
        
        XMLElem langlist = new XMLElem("LANGUAGELIST");
        elemwwseo.addChild(langlist);

        // level 3
        XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
        langlist.addChild(langelem);
        // level 4
        langelem.addChild(new XMLElem("NLSID","NLSID"));
        langelem.addChild(new XMLElem("INVNAME","PRCFILENAM"));
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
    public String getVeName() { return "ADSLSEO"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "LSEO"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.6 $";//"1.0";
    }
}
