// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
* Class used to hold info and structure to be generated for the xml feed
* for abrs.  Checks for deleted or updated entity
*/
// $Log: XMLSVCMODPRCPTElem.java,v $
// Revision 1.3  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.2  2012/02/06 08:55:50  wangyulo
// Change attribute tag name from PRCPTACTION to ACTIVITY for SVCMOD abr
//
// Revision 1.1  2012/01/18 15:58:36  guobin
// Fix the issue 635138 for SVCMOD:
// show EFFECTIVEDATE and ENDDATE once for each PRCPTELEMENT tag in SVCMOD xml
//
// Revision 1.1  2011/12/14 02:30:03  guobin
// Update the Version V Mod M for the ADSABR
//
// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLSVCMODPRCPTElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for ACTIVITY elements
    *
    * <ACTIVITY>	</ACTIVITY>			2	MODEL	Activity "Delete":"Update"
    *
    *@param nname String with name of node to be created
    */
    public XMLSVCMODPRCPTElem()
    {
        super("PRCPTELEMENT");
    }
    /**
     * Check the change of the PRCPT
     */
    protected boolean hasChanges(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
    	boolean changed = false;
    	Vector prcptVct = getPRCPTs(table, parentItem, debugSb);
    	for (int i = 0; i < prcptVct.size(); i++) {
			DiffEntity prcptDiff = (DiffEntity) prcptVct.elementAt(i);			
			if (prcptDiff.isDeleted()) { 
				changed = true;				
			}else if (prcptDiff.isNew()) {
				changed = true;
			}else{				
				EntityItem curritem = prcptDiff.getCurrentEntityItem();	
				EntityItem previtem = prcptDiff.getPriorEntityItem();
				changed = hasChangedValue(changed, curritem, previtem, "PRCPTID");
				if(changed) break;	
				
				EntityItem currReltorItem = getRelator(parentItem, curritem, debugSb);
				EntityItem prevReltorItem = getRelator(parentItem, previtem, debugSb);				
				changed = hasChangedValue(changed, currReltorItem, prevReltorItem, "EFFECTIVEDATE");
				if(changed) break;				
				changed = hasChangedValue(changed, currReltorItem, prevReltorItem, "ENDDATE");
				if(changed) break;
			}    		
    	} 
    	
    	return changed;
    }

	/**
	 * @param changed
	 * @param curritem
	 * @param previtem
	 * @return check if there is some attriubte value that has changed
	 */
	private boolean hasChangedValue(boolean changed, EntityItem curritem, EntityItem previtem,String attributeCode) {
		String currValue = "";
		String prevValue = "";
		if (curritem != null) {
			currValue = PokUtils.getAttributeValue(curritem, attributeCode, ", ", CHEAT, false);
		}
		if (previtem != null) {
			prevValue = PokUtils.getAttributeValue(previtem, attributeCode, ", ", CHEAT, false);
		}
		if (!currValue.equals(prevValue)) {
			changed = true;
		}
		return changed;
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
    	//get all the prcpt from the root of the SVCSEO
    	// getPRCPTs();
    	// prcpt.uplink get the SVCSEOPRCPT and get uplink to find the parent and the parent must the same prcpt
    	// get the EFFECTIVEDATE,ENDDATE attribute value of the SVCSEOPRCPT entity
    	// createNodeSet();
    	ABRUtil.append(debugSb,"XMLSVCMODPRCPTElem:parentItem: " + parentItem.getKey() + NEWLINE);
    	Vector prcptVct = getPRCPTs(table, parentItem, debugSb);
    	for (int i = 0; i < prcptVct.size(); i++) {
			DiffEntity prcptDiff = (DiffEntity) prcptVct.elementAt(i);
			EntityItem curritem = prcptDiff.getCurrentEntityItem();	
			EntityItem previtem = prcptDiff.getPriorEntityItem();	
			String action = "";
			if (prcptDiff.isDeleted()) { 
				action = DELETE_ACTIVITY;
				createNodeSet(table, document, parent, parentItem,previtem, action, debugSb);				
			}else if (prcptDiff.isNew()) {
				action = UPDATE_ACTIVITY;
				createNodeSet(table, document, parent, parentItem,curritem, action, debugSb);
			}else{
				action = UPDATE_ACTIVITY;
				createNodeSet(table, document, parent, parentItem,curritem, action, debugSb);
			}    		
    	}
    	prcptVct.clear();
    }
    /**
     * create the nodeset for the PRCPT entity
     * @param table
     * @param document
     * @param parent
     * @param parentItem
     * @param prcptItem
     * @param action
     * @param debugSb
     */
	private void createNodeSet(Hashtable table, Document document, Element parent, DiffEntity parentItem,
			EntityItem prcptItem, String action, StringBuffer debugSb) {
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		parent.appendChild(elem);
		
		//add child nodes
		//change PRCPTACTION to ACTIVITY
		Element child = (Element) document.createElement("ACTIVITY");
		child.appendChild(document.createTextNode("" + action));
		elem.appendChild(child);		
		
		EntityItem SVCSEOPRCPTItem = getRelator(parentItem, prcptItem,debugSb);
		//add EFFECTIVEDATE of SVCSEOPRCPT
		child = (Element) document.createElement("EFFECTIVEDATE");
		child.appendChild(document.createTextNode("" + PokUtils.getAttributeValue(SVCSEOPRCPTItem, "EFFECTIVEDATE",", ", CHEAT, false)));
		elem.appendChild(child);
		
		//add ENDDATE of SVCSEOPRCPT
		child = (Element) document.createElement("ENDDATE");
		child.appendChild(document.createTextNode("" + PokUtils.getAttributeValue(SVCSEOPRCPTItem, "ENDDATE",", ", CHEAT, false)));
		elem.appendChild(child);
		
		child = (Element) document.createElement("ENTITYTYPE");
		child.appendChild(document.createTextNode("" + prcptItem.getEntityType()));
		elem.appendChild(child);
		
		child = (Element) document.createElement("ENTITYID");
		child.appendChild(document.createTextNode("" + prcptItem.getEntityID()));
		elem.appendChild(child);
		
		child = (Element) document.createElement("PRCPTID");
		child.appendChild(document.createTextNode("" + PokUtils.getAttributeValue(prcptItem, "PRCPTID",", ", CHEAT, false)));
		elem.appendChild(child);		
	}

	/**
	 * @param parentItem
	 * @param prctpItem
	 * @return
	 */
	private EntityItem getRelator(DiffEntity parentItem, EntityItem prctpItem,StringBuffer debugSb) {
		EntityItem relatorItem = null; 
		Vector uplinksVct = prctpItem.getUpLink();
		for (int i=0; i<uplinksVct.size(); i++){
			EntityItem relator = (EntityItem)uplinksVct.get(i);
			if (relator != null && "SVCSEOPRCPT".equals(relator.getEntityType())){
				EntityItem svcseo= (EntityItem)relator.getUpLink(0);
				if(svcseo.getKey().equals(parentItem.getKey())){
					relatorItem = relator;
					break;
				}
			}
		}
		return relatorItem;
	}
    /**
     * get all the vaild prcpt of the parent the SVCSEO
     * @param table
     * @param parentItem
     * @param debugSb
     * @return
     */
	private Vector getPRCPTs(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		Vector PRCTPVct = new Vector(1);
		Vector allVct = (Vector) table.get("PRCPT");
		if (allVct == null) {
			return PRCTPVct;
		}
		Vector keyVct = new Vector();
		for (int i = 0; i < allVct.size(); i++) {			
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			if(keyVct.contains(diffitem.getKey())){
				continue;
			}				
			if (deriveTheSameEntry(diffitem, parentItem, debugSb)) {				
				if(!keyVct.contains(diffitem.getKey())){
					ABRUtil.append(debugSb,"XMLSVCMODPRCPTElem.getPRCPTs find PRCPT key=" + diffitem.getKey() + NEWLINE);
					PRCTPVct.add(diffitem);
					keyVct.add(diffitem.getKey());
				}			
			}			
		}	
		keyVct.clear();
		return PRCTPVct;
	}
	/**
	 * the relator must relate to the parent
	 * @param diffitem
	 * @param parentItem
	 * @param debugSb
	 * @return
	 */
	private boolean deriveTheSameEntry(DiffEntity diffitem, DiffEntity parentItem, StringBuffer debugSb) {
		boolean isFromTheParent = false;
		String spath = "";
		if (diffitem != null) {
			spath = diffitem.toString();			
			if(spath.indexOf("SVCSEOPRCPT")>-1){
				ABRUtil.append(debugSb,"XMLSVCMODPRCPTElem.deriveTheSameEntry path="+ spath + NEWLINE);
				EntityItem prctpItem = null;
				if(diffitem.isDeleted()){
					prctpItem = diffitem.getPriorEntityItem();
				}else{
					prctpItem = diffitem.getCurrentEntityItem();
				}
				Vector uplinksVct = prctpItem.getUpLink();
				for (int i=0; i<uplinksVct.size(); i++){
					EntityItem relator = (EntityItem)uplinksVct.get(i);
					if (relator != null && "SVCSEOPRCPT".equals(relator.getEntityType())){
						EntityItem svcseo= (EntityItem)relator.getUpLink(0);
						if(svcseo.getKey().equals(parentItem.getKey())){
							isFromTheParent = true;
							ABRUtil.append(debugSb,"XMLSVCMODPRCPTElem.deriveTheSameEntry is true and path="+ spath + NEWLINE);
							break;
						}
					}
				}
			}
		}
		return isFromTheParent;
	}
}
