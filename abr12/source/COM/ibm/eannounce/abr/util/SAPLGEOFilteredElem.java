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
* This class will find entities based on a filter
* like AVAIL with AVAILTYPE= 149 (LastOrder)
*/
// $Log: SAPLGEOFilteredElem.java,v $
// Revision 1.4  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2007/12/12 15:48:27  wendy
// Add support for outputting a flagcode instead of desc.
//
// Revision 1.2  2007/04/20 14:58:33  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLGEOFilteredElem extends SAPLElem
{
    private String filterAttr;
    private String filterValue;

    /**********************************************************************************
    * Constructor for filtered entities and output flagcode
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    *@param att String with attribute code to filter on
    *@param val String with attribute value to filter on
	*@param src int for flag attributes
    */
    public SAPLGEOFilteredElem(String nname, String type, String code, String att, String val, int src)
    {
        super(nname,type,code,false, src);
        filterAttr = att;
        filterValue = val;
    }

    /**********************************************************************************
    * Constructor for filtered entities
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    *@param code String with attribute code
    *@param att String with attribute code to filter on
    *@param val String with attribute value to filter on
    */
    public SAPLGEOFilteredElem(String nname, String type, String code, String att, String val)
    {
        super(nname,type,code,false);
        filterAttr = att;
        filterValue = val;
    }

    /**********************************************************************************
    * Get entities to output
    *
    *@param egrp EntityGroup
    */
    protected Vector getEntities(EntityGroup egrp)
    {
        return PokUtils.getEntitiesWithMatchedAttr(egrp, filterAttr, filterValue);
    }
    /**********************************************************************************
    * Create a node for this element add to the parent
    *
    *@param itemVct Vector of EntityItem for a country, find the one that matches this filter
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
			Vector matchVct = PokUtils.getEntitiesWithMatchedAttr(itemVct, filterAttr, filterValue);
			// there should really only be 1 or 0 item returned
			if (matchVct.size()==0){
				debugSb.append("SAPLGEOFilteredElem: No "+etype+" items found for node:"+nodeName+" filterattr:"+
					filterAttr+" filterval:"+filterValue+NEWLINE);
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
			debugSb.append("SAPLGEOFilteredElem: No "+etype+" passed in for node:"+nodeName+NEWLINE);
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			parent.appendChild(elem);
			if (attrCode!=null){ // a value is expected, prevent a normal empty tag, OIDH cant handle it
				elem.appendChild(document.createTextNode(CHEAT));
			}
		}
	}
}
