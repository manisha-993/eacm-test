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
* for SAPLABRSTATUS abrs.  values are fixed for these nodes
*/
// $Log: SAPLFixedElem.java,v $
// Revision 1.2  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLFixedElem extends SAPLElem
{
    private String value;
    /**********************************************************************************
    * Constructor for Fixed value elements
    *
    *4  <NativeCodePage>    0   </NativeCodePage>
    *4  <PayloadFormat  Type | Material | EACM  </PayloadFormat>  how to know which one
    *4  <ebi:priority>  "Normal"    <ebi:priority>
    *
    *@param nname String with name of node to be created
    *@param val String with value
    */
    public SAPLFixedElem(String nname, String val)
    {
        super(nname,null,null,false);
        value = val;
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
        // use value from constructor
        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }
}
