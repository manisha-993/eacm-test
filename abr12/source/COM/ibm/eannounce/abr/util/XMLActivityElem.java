// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;
import java.util.*;
import com.ibm.transform.oim.eacm.diff.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.  Checks for deleted or updated entity
*/
// $Log: XMLActivityElem.java,v $
// Revision 1.2  2020/02/05 13:29:08  xujianbo
// Add debug info to investigate   performance issue
//
// Revision 1.1  2008/04/17 19:37:53  wendy
// Init for
// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLActivityElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for ACTIVITY elements
    *
    * <ACTIVITY>	</ACTIVITY>			2	MODEL	Activity "Delete":"Update"
    *
    *@param nname String with name of node to be created
    */
    public XMLActivityElem(String nname)
    {
        super(nname);
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
        EntityItem parentItem,StringBuffer debugSb)
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
    	D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        // fixme more work needed if this method with list is used, must determine if entity was deleted
        elem.appendChild(document.createTextNode(UPDATE_ACTIVITY));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            XMLElem childElem = (XMLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,parentItem,debugSb);
        }
    }
    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
    *
    *@param dbCurrent Database
    *@param table Hashtable of Vectors of DiffEntity
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param parentItem DiffEntity - parent to use if path is specified in XMLGroupElem, item to use otherwise
    *@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,Hashtable table, Document document, Element parent,
        DiffEntity parentItem, StringBuffer debugSb)
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
    	D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        // check if this was deleted or not
        elem.appendChild(document.createTextNode(parentItem.isDeleted()?DELETE_ACTIVITY:UPDATE_ACTIVITY));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            XMLElem childElem = (XMLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
        }
    }
}
