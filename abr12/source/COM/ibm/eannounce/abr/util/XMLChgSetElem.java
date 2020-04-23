// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import com.ibm.transform.oim.eacm.diff.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs. This class will generate this node and children if any changes exist in this node
* or any child
*
*/
// $Log: XMLChgSetElem.java,v $
// Revision 1.4  2020/02/05 13:29:08  xujianbo
// Add debug info to investigate   performance issue
//
// Revision 1.3  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.2  2010/02/05 11:55:11  wendy
// use path to find changes in GroupElem
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

public class XMLChgSetElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for change sensitive elements
    * 0..N <OSELEMENT> => only create this node if there was a change in <OSLEVEL> child(ren)
    *
    *@param nname String with name of node to be created
    */
    public XMLChgSetElem(String nname)
    {
        super(nname);
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
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
		// check to see if any changes before adding the nodeset
		// if one node is added, all must be added
		if (hasChanges(table, parentItem,debugSb)){
			// create this node and its children
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			if (parent ==null){ // create the root
				document.appendChild(elem);
			}else{ // create a node
				parent.appendChild(elem);
			}

			Node contentElem = getContentNode(document, parentItem, parent, debugSb);
			if (contentElem!=null){
				elem.appendChild(contentElem);
			}

			// all must be output if the parent element is output
			for (int c=0; c<childVct.size(); c++){
				XMLElem childElem = (XMLElem)childVct.elementAt(c);
				childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
			}
		}else{
			ABRUtil.append(debugSb,"XMLChgSetElem.addElements node:"+nodeName+" "+parentItem.getKey()+" does not have any changed node values"+NEWLINE);
		}
    }
}
