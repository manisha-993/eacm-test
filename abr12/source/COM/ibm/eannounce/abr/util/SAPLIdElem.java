// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs.
*/
// $Log: SAPLIdElem.java,v $
// Revision 1.3  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/12/13 19:55:01  wendy
// Add support for attribute
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLIdElem extends SAPLElem
{
	private String attrName = null;
	private String nodeVal = null;
    /**********************************************************************************
    * Constructor for Id value elements
    *
    * <EntityType id="%1">%2</EntityType>
    *Where
	*	%1 is the entity id for the MODEL
	*	%2 is the text string: MODEL
    *@param nname String with name of node to be created
    *@param nval String with value of node to be created
    *@param aname String with name of attribute to be created, id will be a tag attribute
    */
    public SAPLIdElem(String nname, String nval, String aname)
    {
        super(nname,null,null,false);
        attrName = aname;
        nodeVal = nval;
    }

    /**********************************************************************************
    * Constructor for Id value elements
    *
    *2   <EACMEntityId>  EntityId of EACM EntityType
    *@param nname String with name of node to be created
    */
    public SAPLIdElem(String nname)
    {
        super(nname,null,null,false);
    }

    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
    *
    *@param dbCurrent Database
    *@param list EntityList
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
        StringBuffer debugSb)
    throws
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        java.io.IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        String value = ""+list.getParentEntityGroup().getEntityItem(0).getEntityID();
        if (attrName!=null){
			elem.setAttribute(attrName,value); // use id here
			value = nodeVal; // chg node name
		}
        // use value from root entity id
        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }
}
