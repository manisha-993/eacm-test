// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;

import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs this class will generate one child for each nlsitem in the profile
* There are two sections that are NLSID sensitive: Description Section and Feature Description Section.
*
* For each Read NLSID of the Profile that has a 'Description' in a matching NLSID, create an
* instance of the Description. To obtain the 'DescriptionLanguage', use the NLSID to match
* CHQISONLSIDMAP.CHQNLSID and then use CHQISONLSID.CHQISOLANG from the same instance as the
* 'DescriptionLanguage'.
*
*/
// $Log: SAPLNLSElem.java,v $
// Revision 1.3  2007/05/04 17:31:39  wendy
// Only generate tabs for nls section if values exist in that nls
//
// Revision 1.2  2007/04/20 14:58:33  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLNLSElem extends SAPLElem
{
    /**********************************************************************************
    * Constructor for nls sensitive elements
    *
    *one descriptiondata child per nlsid
    *<DescriptionDataList>
    *	<DescriptionData>
    *		<MaterialDescription>	ACCTGUSEONLYMATRL.PRCFILENAM
    *		<DescriptionLanguage>	NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSIDMAP.CHQISOLANG
    *	</DescriptionData>
    *</DescriptionDataList>
    *
    * and this section
    *<FeatureDescriptionDataList>
    *	<FeatureDescriptionData>
    *		<FeatureDescription>	FEATURE.MKTGNAME
    *		<FeatureDescriptionLanguage>	NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSIDMAP.CHQISOLANG
    *	</FeatureDescriptionData>
    *</FeatureDescriptionDataList>
    *
    *
    *@param nname String with name of node to be created
    */
    public SAPLNLSElem(String nname)
    {
        super(nname,null,null,false);
    }

    /**********************************************************************************
    * Create a node for this element for each nlsid and add to the parent and any children this node has

Wayne Kehrli	the current part that is NLSID sensitive will get changed as follows
assume that there are multiple attributes that vary by NLSID (today there is only 1)
Wayne Kehrli	the deal is - if none of the attributes have a value for that NLSID, then skip it
Wayne Kehrli	otherwise - all attributes get the tags
Wayne Kehrli	for that NLSID
Wayne Kehrli	there is no change to the rest of the mapping
Wendy	ok.. assumption is that there will always be a value for nlsid=1?
Wayne Kehrli	yes
Wayne Kehrli	and if none - then still create the section
Wendy	k
Wayne Kehrli	I write it for nlsid<>1

    *
	*@param dbCurrent Database
	*@param list EntityList
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
        StringBuffer debugSb)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    java.rmi.RemoteException,
    IOException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Profile profile = list.getProfile();
        // always do nlsid=1, english even if no values exist for these nodes
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
		debugSb.append("SAPLNLSElem.addElements profile.getReadLanguage() "+profile.getReadLanguage()+NEWLINE);
        // create this node and its children for each nlsid
        super.addElements(dbCurrent,list, document, parent,debugSb);

        // this is NLS sensitive, do each one
        for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
            NLSItem nlsitem = profile.getReadLanguage(ix);
            if (nlsitem.getNLSID()==1){  // already did this one
				//debugSb.append("SAPLNLSElem.addElements already handled profile.getReadLanguage("+ix+") "+nlsitem+NEWLINE);
				continue;
			}
			debugSb.append("SAPLNLSElem.addElements checking profile.getReadLanguage("+ix+") "+nlsitem+NEWLINE);
            profile.setReadLanguage(ix);
            // check to see if this nlsid has any values before adding the nodeset
            if (hasNodeValueForNLS(list, debugSb)){
        	    // create this node and its children for each nlsid
        	    super.addElements(dbCurrent,list, document, parent,debugSb);
			}else{
				debugSb.append("SAPLNLSElem.addElements profile.getReadLanguage("+ix+") "+nlsitem+" does not have any node values"+NEWLINE);
			}
        } // end each read language
        // restore to nlsid=1
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
    }
}
