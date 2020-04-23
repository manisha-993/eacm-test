// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs.  value is taken from the specified entityitem
*/
// $Log: SAPLItemElem.java,v $
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

// this class will use the entity item specified
public class SAPLItemElem extends SAPLElem
{
    /**********************************************************************************
    * Constructor- this class will use the entity item specified
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    *@param code String with attribute code
    */
    public SAPLItemElem(String nname, String type, String code)
    {
        super(nname,type,code,false);
    }
    /**********************************************************************************
    * Create a node for this element add to the parent
    *
    *@param item EntityItem
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param debugSb StringBuffer for debug output
    */
    protected void addElements(EntityItem item, Document document, Element parent,StringBuffer debugSb)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        parent.appendChild(elem);

        if (item != null){
            Node contentElem = getContentNode(document, item, parent);
            if (contentElem!=null){
                elem.appendChild(contentElem);
            }
        }else{
			debugSb.append("SAPLItemElem: EntityItem was null for "+etype+" for node:"+
						nodeName+NEWLINE);
		}
    }
    /**********************************************************************************
    * prevent default behavior
    *
	*@param dbCurrent Database
	*@param list EntityList
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent, EntityList list, Document document, Element parent,
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
        addElements(((EntityItem)null), document, parent,debugSb);
    }
}
