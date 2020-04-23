package COM.ibm.eannounce.abr.util;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.Database;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

public class XMLFCTRANSMODElem extends XMLElem{

	 private String defvalue;
	 private String attrcode;
	    /**********************************************************************************
	    * Constructor for Fixed value elements
	    *
	    *@param nname String with name of node to be created
	    *@param val String with value
	    */
	    public XMLFCTRANSMODElem(String nname, String attcode, String val)
	    {
	        super(nname);
	        attrcode = attcode;
	        defvalue = val;
	        
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
	        String value = CHEAT;
	        String model = PokUtils.getAttributeValue(parentItem,attrcode, ", ", CHEAT, false);   			
			if (CHEAT.equals(model)){
				value = defvalue; 
			} else{
				value = model;				
			}
	        // use value from constructor
	        elem.appendChild(document.createTextNode(value));
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
	        EntityItem item = parentItem.getCurrentEntityItem();
	        if (parentItem.isDeleted()){
	            item = parentItem.getPriorEntityItem();
	        }
	        Element elem = (Element) document.createElement(nodeName);
	        String value = CHEAT;
	        String model = PokUtils.getAttributeValue(item,attrcode, ", ", CHEAT, false);   			
			if (CHEAT.equals(model)){
				value = defvalue; 
			} else{
				value = model;				
			}
	        // use value from constructor
	        elem.appendChild(document.createTextNode(value));
	        parent.appendChild(elem);
	        // add any children
	        for (int c=0; c<childVct.size(); c++){
	            XMLElem childElem = (XMLElem)childVct.elementAt(c);
	            childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
	        }
		}
	}

