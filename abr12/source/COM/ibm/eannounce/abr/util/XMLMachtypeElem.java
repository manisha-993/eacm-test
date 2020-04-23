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
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.
*/

public class XMLMachtypeElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for DTS value elements - uses getEndOfDay() in profile
    *
    *2  <NotificationTime>(Timestamp of the notification)</NotificationTime>
    *@param nname String with name of node to be created
    */
    public XMLMachtypeElem(String nname, String code)
    {  	
    	 super(nname,code,ATTRVAL);
    	    
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
        EntityItem parentItem, StringBuffer debugSb)
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

		if (parent ==null){ // create the root
			document.appendChild(elem);
		}else{ // create a node
			parent.appendChild(elem);
		}

		Node contentElem = getContentNode(document, parentItem, parent,debugSb);
		if (!CHEAT.equals(contentElem.getNodeValue())){
			D.ebug(D.EBUG_ERR,"Bing MACHTYPEART is not empty");
			elem.appendChild(contentElem);
		}else{
			D.ebug(D.EBUG_ERR,"Bing MACHTYPEART is empty, try to re-create it");
			Profile m_prof =  parentItem.getEntityGroup().getEntityList().getProfile();
			D.ebug(D.EBUG_ERR,"Bing profile valon : effon : " + m_prof.getValOn() + " : " + m_prof.getEffOn() );
			EntityList m_elist = dbCurrent.getEntityList(m_prof,
                  new ExtractActionItem(null, dbCurrent, m_prof,"dummy"),
                 
                  new EntityItem[] { new EntityItem(null, m_prof, parentItem.getEntityType(), parentItem.getEntityID()) });
			
			 EntityItem modelEntity  = m_elist.getParentEntityGroup().getEntityItem(0);
			 D.ebug(D.EBUG_ERR,"Bing get modelEntity " + modelEntity.getKey());
			 D.ebug(D.EBUG_ERR,"Bing get machtype value : " + PokUtils.getAttributeValue(modelEntity, "MACHTYPEATR",", ", "", false));
			 contentElem = getContentNode(document, modelEntity, parent, debugSb);
			 if (contentElem!=null){
			     elem.appendChild(contentElem);
			 }
		}
		// add any children
		for (int c=0; c<childVct.size(); c++){
			XMLElem childElem = (XMLElem)childVct.elementAt(c);
			childElem.addElements(dbCurrent,list, document,elem,parentItem,debugSb);
		}

		if (!elem.hasChildNodes()){
			// a value is expected, prevent a normal empty tag, OIDH cant handle it
			elem.appendChild(document.createTextNode(CHEAT));
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
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		if (parent ==null){ // create the root
			document.appendChild(elem);
		}else{ // create a node
			parent.appendChild(elem);
		}

		Node contentElem = getContentNode(document, parentItem, parent,debugSb);
		
		if (!CHEAT.equals(contentElem.getNodeValue())){
			D.ebug(D.EBUG_ERR,"Bing MACHTYPEART is not empty");
			elem.appendChild(contentElem);
		}else{
			D.ebug(D.EBUG_ERR,"Bing MACHTYPEART is empty, try to re-create it");
			Profile m_prof =  parentItem.getCurrentEntityItem().getEntityGroup().getEntityList().getProfile();
			D.ebug(D.EBUG_ERR,"Bing profile valon : effon : " + m_prof.getValOn() + " : " + m_prof.getEffOn() + " key:" + parentItem.getKey() );
			EntityList m_elist = dbCurrent.getEntityList(m_prof,
                  new ExtractActionItem(null, dbCurrent, m_prof,"dummy"),
                  new EntityItem[] { new EntityItem(null, m_prof, parentItem.getEntityType(), parentItem.getEntityID()) });
			
			 EntityItem modelEntity  = m_elist.getParentEntityGroup().getEntityItem(0);
			 D.ebug(D.EBUG_ERR,"Bing get modelEntity " + modelEntity.getKey());
			 D.ebug(D.EBUG_ERR,"Bing get machtype value : " + PokUtils.getAttributeValue(modelEntity, "MACHTYPEATR",", ", "", false));
			 contentElem = getContentNode(document, modelEntity, parent, debugSb);
			 if (contentElem!=null){
			     elem.appendChild(contentElem);
			 }
		}
		// add any children
		for (int c=0; c<childVct.size(); c++){
			XMLElem childElem = (XMLElem)childVct.elementAt(c);
			childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
		}

		if (!elem.hasChildNodes()){
			// a value is expected, prevent a normal empty tag, OIDH cant handle it
			elem.appendChild(document.createTextNode(CHEAT));
		}
	}
	
	
	
}

