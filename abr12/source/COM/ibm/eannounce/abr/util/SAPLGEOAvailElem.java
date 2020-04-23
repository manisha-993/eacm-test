// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs
* This class will look at AVAILs in order for a particular country and use first one found
* The EACM XML Payloads specify AVAIL that do not specify an AVAILTYPE use the
* 	instance of AVAIL based on the following priority of AVAILTYPE:
* 	1.	146 (Planned Availability)
* 	2.	143 (First Order)
* 	3.	149 (Last Order)
* 	4.	AVT220 (Lease Rental Withdrawal)
*
*/
// $Log: SAPLGEOAvailElem.java,v $
// Revision 1.3  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/04/20 14:58:33  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLGEOAvailElem extends SAPLElem
{
    /**********************************************************************************
    * Constructor for filtered AVAIL entities
    * look in order (the first one you find) from AVAILTYPE
    *
    *@param nname String with name of node to be created
    *@param code String with attribute code to retrieve
    */
    public SAPLGEOAvailElem(String nname, String code)
    {
        super(nname,"AVAIL",code,false);
    }

    /**********************************************************************************
    * Create a node for this element add to the parent
    *
    *@param itemVct Vector of AVAIL EntityItem for a country, find the one that matches this filter
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param debugSb StringBuffer used for debug output
    */
    protected void addGEOElements(Vector itemVct, Document document, Element parent,StringBuffer debugSb)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		if (itemVct !=null && itemVct.size()>0){
			Vector matchVct = new Vector();
			for(int t=0; t<AVAIL_ORDER.length; t++){
            	matchVct = PokUtils.getEntitiesWithMatchedAttr(itemVct, "AVAILTYPE", AVAIL_ORDER[t]);
				if (matchVct.size()>0){
					break;
				}else{
					debugSb.append("SAPLGEOAvailElem: No AVAIL of AVAILTYPE["+AVAIL_ORDER[t]+"] found for node:"+
						nodeName+NEWLINE);
				}
			}
			if (matchVct.size()==0){
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				parent.appendChild(elem);
				if (attrCode!=null){ // a value is expected, prevent a normal empty tag, OIDH cant handle it
					elem.appendChild(document.createTextNode(CHEAT));
				}
			}
			for (int i=0; i<matchVct.size(); i++){
				EntityItem item = (EntityItem)matchVct.elementAt(i);
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				parent.appendChild(elem);
				Node contentElem = getContentNode(document, item,parent);
				if (contentElem!=null){
					elem.appendChild(contentElem);
				}
			}
			matchVct.clear();
		}else{
			debugSb.append("SAPLGEOAvailElem: No AVAIL passed in for node:"+nodeName+NEWLINE);
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			parent.appendChild(elem);
			if (attrCode!=null){ // a value is expected, prevent a normal empty tag, OIDH cant handle it
				elem.appendChild(document.createTextNode(CHEAT));
			}
		}
    }
}
