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
*
1	<CATNAV_UPDATE>					1	CATNAV
1	<PDHDOMAIN>	</PDHDOMAIN>		2	CATNAV	PDHDOMAIN
1	<DTSOFMSG>	</DTSOFMSG>			2	CATNAV	ABR Queued	DTS of ABR Queued
1	<ACTIVITY>	</ACTIVITY>			2	CATNAV	Activity	Derived
1	<ENTITYTYPE>	</ENTITYTYPE>	2	CATNAV	ENTITYTYPE	"CATNAV"
1	<ENTITYID>	</ENTITYID>			2	CATNAV	ENTITYID
1	<TYPE>	</TYPE>					2	CATNAV	CATNAVTYPE	"Master" | "Navigational"
1	<PUBLISH>	</PUBLISH>			2	CATNAV	CATPUBLISH
1	<PRICEDISCLAIMER>	</PRICEDISCLAIMER>	2	CATNAV	PRICEDISCLAIMER
1	<IMAGEDISCLAIMER>	</IMAGEDISCLAIMER>	2	CATNAV	IMGDISCLAIMER
1	<FEATUREBENEFIT>	</FEATUREBENEFIT>	2	CATNAV	FBSTMT
1	<FAMILYDESCOVERRIDE>	</FAMILYDESCOVERRIDE>	2	CATNAV	CATFMLYDESC
1	<SERIESDESCOVERRIDE>	</SERIESDESCOVERRIDE>	2	CATNAV	CATSERDESC
1	<SERIESHEADING>	</SERIESHEADING>	2	CATNAV	CATSERHEAD
1	<PROJECTLIST>					2	CATNAV
0..N	<PROJECTELEMENT>			3	CATNAV
1	<PROJECTACTIVITY>	</PROJECTACTIVITY>	4	CATNAV	ProjectActivity	Derived
1	<PROJECT>	</PROJECT>			4	CATNAV	PROJCDNAMF	Flag Description Class
		</PROJECTELEMENT>			3
		</PROJECTLIST>				2
1	<AUDIENCELIST>					2	CATNAV
0..N	<AUDIENCEELEMENT>			3	CATNAV
1	<AUDIENCEACTIVITY>	</AUDIENCEACTIVITY>	4	CATNAV	AudienceActivity	Derived
1	<AUDIENCE>	</AUDIENCE>			4	CATNAV	CATAUDIENCE	Flag Description Class
		</AUDIENCEELEMENT>			3
		</AUDIENCELIST>				2
1	<COUNTRYLIST>					2	CATNAV
0..N	<COUNTRYELEMENT>			3	CATNAV/IMG
1	<COUNTRYLISTACTION>	</COUNTRYLISTACTION>	4	CATNAV/IMG	CountryListAction	Derived
1	<COUNTRYCODE>	</COUNTRYCODE>	4	CATNAV	COUNTRYLIST	Flag Description Class
1	<IMAGELIST>						4	IMG
0..N	<IMAGEELEMENT>				5	IMG
1	<IMAGEENTITYID>	</IMAGEENTITYID>	6	IMG	IMAGEENTITYID	IMG.ENTITYID
1	<IMAGEACTION>	</IMAGEACTION>	6	IMG	ImageAction	Derived
1	<PUBFROM>	</PUBFROM>			6	IMG	PUBFROM
	<PUBTO>	</PUBTO>				6	IMG	PUBTO
1	<STATUS>	</STATUS>			6	IMG	STATUS	Flag Description Class
1	<IMAGEDESCRIPTION>	</IMAGEDESCRIPTION>	6	IMG	IMGDESC
1	<MARKETINGIMAGEFILENAME>	</MARKETINGIMAGEFILENAME>	6	IMG	MKTGIMGFILENAM
		</IMAGEELEMENT>				5
		</IMAGELIST>				4
		</COUNTRYELEMENT>			3
		</COUNTRYLIST>				2
1	<LANGUAGELIST>					2	CATNAV
0..N	<LANGUAGEELEMENT>			3	CATNAV
1	<NLSID>	</NLSID>				4	CATNAV	NLSID
1	<CATBR>	</CATBR>				4	CATNAV	CATBR
1	<CATFMLY>	</CATFMLY>			4	CATNAV	CATFMLY
1	<CATSER>	</CATSER>			4	CATNAV	CATSER
1	<CATOPTGRPNAM>	</CATOPTGRPNAM>	4	CATNAV	CATOPTGRPNAM
1	<CATNAME>	</CATNAME>			4	CATNAV	CATNAME
1	<LONGMKTGMESSAGE>	</LONGMKTGMESSAGE>	4	CATNAV	LONGMKTGMSG
		</LANGUAGEELEMENT>			3
		</LANGUAGELIST>				2
		</CATNAV_UPDATE>			1

C.	<COUNTRYLIST>
the Country List (COUNTRYLIST) of Catalog Category Navigation (CATNAV) is used as the "master"
for selecting the subset of related Images (IMG).
D.	<IMAGELIST>
Image (IMG) entity data is passed only if:
Include Image (IMG) where AnyValueOf(CATNAVIMG: IMG.COUNTRYLIST) IN (CATNAV.COUNTRYLIST)

*/
// ADSCATNAVABR.java,v
// Revision 1.3  2008/05/28 13:46:09  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/27 14:28:59  wendy
// Clean up RSA warnings
//
// Revision 1.1  2008/04/30 11:50:47  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSCATNAVABR extends XMLMQChanges
{
    private static final XMLElem XMLMAP;

    static {
        XMLMAP = new XMLGroupElem("CATNAV_UPDATE");
         // level2
        XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
        XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
        XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
        XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
        XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
        XMLMAP.addChild(new XMLElem("TYPE","CATNAVTYPE"));

        XMLMAP.addChild(new XMLElem("PUBLISH","CATPUBLISH"));
        XMLMAP.addChild(new XMLElem("PRICEDISCLAIMER","PRICEDISCLAIMER"));
        XMLMAP.addChild(new XMLElem("IMAGEDISCLAIMER","IMGDISCLAIMER"));
        XMLMAP.addChild(new XMLElem("FEATUREBENEFIT","FBSTMT"));
        XMLMAP.addChild(new XMLElem("FAMILYDESCOVERRIDE","CATFMLYDESC"));
        XMLMAP.addChild(new XMLElem("SERIESDESCOVERRIDE","CATSERDESC"));
        XMLMAP.addChild(new XMLElem("SERIESHEADING","CATSERHEAD"));

        XMLElem list = new XMLElem("PROJECTLIST");
        XMLMAP.addChild(list);
        XMLElem listelem = new XMLChgSetElem("PROJECTELEMENT");
        list.addChild(listelem);
        listelem.addChild(new XMLMultiFlagElem("PROJECT","PROJCDNAMF","PROJECTACTIVITY",XMLElem.FLAGVAL));

        list = new XMLElem("AUDIENCELIST");
        XMLMAP.addChild(list);
        listelem = new XMLChgSetElem("AUDIENCEELEMENT");
        list.addChild(listelem);
        listelem.addChild(new XMLMultiFlagElem("AUDIENCE","CATAUDIENCE","AUDIENCEACTIVITY",XMLElem.FLAGVAL));

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
        langelem.addChild(new XMLElem("CATBR","CATBR"));
        langelem.addChild(new XMLElem("CATFMLY","CATFMLY"));
        langelem.addChild(new XMLElem("CATSER","CATSER"));
        langelem.addChild(new XMLElem("CATOPTGRPNAM","CATOPTGRPNAM"));
        langelem.addChild(new XMLElem("CATNAME","CATNAME"));
        langelem.addChild(new XMLElem("LONGMKTGMESSAGE","LONGMKTGMSG"));
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
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "CATNAV"; }

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
