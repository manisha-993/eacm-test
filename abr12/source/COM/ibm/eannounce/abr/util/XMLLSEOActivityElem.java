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
// $Log: XMLLSEOActivityElem.java,v $
// Revision 1.2  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.1  2011/08/30 07:59:23  guobin
// inital class  for WARRLIST of  LSEO
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

public class XMLLSEOActivityElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for ACTIVITY elements
    *
    * <ACTIVITY>	</ACTIVITY>			2	MODEL	Activity "Delete":"Update"
    *
    *@param nname String with name of node to be created
    */
    public XMLLSEOActivityElem(String nname)
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
        String prekey = "WARRRELATOR";
        String activity = UPDATE_ACTIVITY;
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        // check if this was deleted or not
        String key = prekey + parentItem.getKey();
        String[] warrarry = (String[])table.get(key);
		if (warrarry!=null){
		  activity = warrarry[2];
		  ABRUtil.append(debugSb,"XMLLSEOActivityElem.addElements: get from hashtable: "+ key + " relator :" + warrarry[2]+ NEWLINE);
		}
        elem.appendChild(document.createTextNode(activity));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            XMLElem childElem = (XMLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
        }
    }
}

