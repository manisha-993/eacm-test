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
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.  search must be used to find values for these nodes
*/
// $Log: XMLSearchElem.java,v $
// Revision 1.2  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
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

public class XMLSearchElem extends XMLElem
{
	private String searchAction;
    private Vector srcVct = new Vector(1);
    private Vector sinkVct = new Vector(1);
    private String srchType;
    private boolean useListSrch = false;
    /**********************************************************************************
    * Constructor for search value elements
    *
    *@param nname String with name of node to be created
    *@param srchAct String with search action name
    *@param stype String with entitytype to find
    */
    public XMLSearchElem(String nname, String srchAct, String stype)
    {
		this(nname, srchAct, stype,false);
        /*super(nname);
        searchAction = srchAct;
        srchType = stype;*/
    }
    protected XMLSearchElem(String nname, String srchAct, String stype, boolean uselist)
    {
        super(nname);
        searchAction = srchAct;
        srchType = stype;
        useListSrch = uselist;
    }
    /**********************************************************************************
    * set search attributes src and sink
    *
    *@param srcAttr String with name of attribute used for value to search on
    *@param sinkAttr String with name of attribute used in search
    */
	public void addSearchAttr(String srcAttr, String sinkAttr) {
		srcVct.addElement(srcAttr);
		sinkVct.addElement(sinkAttr);
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
        // search to find entity needed for entityid
        String value = CHEAT;

		try{
			EntityItem[] aei = doSearch(dbCurrent, parentItem, debugSb);
			if (aei!= null && aei.length >0){
				value = ""+aei[0].getEntityID();
			}
		}catch(COM.ibm.eannounce.objects.SBRException sbre){
			throw new MiddlewareException(sbre.toString());
		}

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
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        // search to find entity needed for entityid
        String value = CHEAT;
		EntityItem item = parentItem.getCurrentEntityItem();
		if (parentItem.isDeleted()){
			item = parentItem.getPriorEntityItem();
		}

		try{
			EntityItem[] aei = doSearch(dbCurrent, item, debugSb);
			if (aei!= null && aei.length >0){
				value = ""+aei[0].getEntityID();
			}
		}catch(COM.ibm.eannounce.objects.SBRException sbre){
			throw new MiddlewareException(sbre.toString());
		}

        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            XMLElem childElem = (XMLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
        }
    }

    /**********************************************************************************
    * search for entity to get entityid for xml output
    *
    *@param dbCurrent Database
    *@param item EntityItem
    *@param debugSb StringBuffer for debug output
    */
    protected EntityItem[] doSearch(Database dbCurrent, EntityItem item, StringBuffer debugSb)
    throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.SBRException
    {
		PDGUtility pdgUtility = new PDGUtility();

        StringBuffer sb = new StringBuffer();
        for (int i=0; i<srcVct.size(); i++){
			if (sb.length()>0){
				sb.append(";");
			}
			String code = srcVct.elementAt(i).toString();
			String sink = sinkVct.elementAt(i).toString();
			sb.append("map_"+sink+"="+PokUtils.getAttributeValue(item, code,", ", "", false));
		}

        ABRUtil.append(debugSb,"XMLSearchElem.doSearch Using "+searchAction+", useListSrch: "+useListSrch+" to search for "+srchType+" using "+sb.toString()+NEWLINE);

        EntityItem[] aei = null;
        if (!useListSrch){
        	aei = pdgUtility.dynaSearch(dbCurrent, item.getProfile(), null, searchAction, srchType, sb.toString());
		}else{
            EntityList list = pdgUtility.dynaSearchIIForEntityList(dbCurrent, item.getProfile(), null, searchAction,
            	srchType, sb.toString());
            // group will be null if no matches are found
            EntityGroup psgrp = list.getEntityGroup(srchType);
            if (psgrp !=null && psgrp.getEntityItemCount()>0){
               	aei = psgrp.getEntityItemsAsArray();
		   	}else{
        		ABRUtil.append(debugSb,"XMLSearchElem.doSearch No "+srchType+" found"+NEWLINE);
			}
		}
		return aei;
	}
}
