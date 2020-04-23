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
* for EPIMS abrs.
*/
// $Log: SAPLEnterpriseElem.java,v $
// Revision 1.2  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/10/09 20:56:29  wendy
// Init for GX EPIMS outbound ABRs
//
//

public class SAPLEnterpriseElem extends SAPLElem
{
    /**********************************************************************************
    * Constructor for Enterprise value elements
    *
    *2   <Enterprise>(enterprise value)</Enterprise>
    *@param nname String with name of node to be created
    */
    public SAPLEnterpriseElem(String nname)
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
        String value = list.getProfile().getEnterprise();
        // use value from profile
        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }
}
