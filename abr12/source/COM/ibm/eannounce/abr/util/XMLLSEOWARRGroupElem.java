// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

/**********************************************************************************
* The LSEOs parent WWSEO.SEOORDERCODE=’MES’ attribute value indicates an option. The MODEL is found by first finding the WWSEO via the WWSEOLSEO relator and then using the MODELWWSEO relator.
* The PRODSTRUCT of the WWSEO is found via the WWSEOPRODSTRUCT relator. Any WARRs found will be subject to filtering where either MODELWARR or PRODSTRUCTWARR COUNTRYLIST has a country that is in the <AVAILABILITYLIST>. 
*  
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a set or group of entities.  A node will not be output
* if no changes exist in the entity (but root is always output)
*/

// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLLSEOWARRGroupElem extends XMLElem
{
	private String path = null;
    private String etype =null;
    private String special = null;
    private String prekey = "WARRRELATOR";
    private String countryKey = "AVAILCOUNTRYLIST";
    /**********************************************************************************
    * Constructor - used when element is part of a group with child elements that are
    * attributes for the entity or structure based on the entity
    * <MMLIST>						2	MM
    * <MMELEMENT>					3
    * <MMACTION>	</MMACTION>		4	MM	MMAction
    * ...
    * MMLIST is the group, one MMELEMENT for each MM
    *@param nname String with name of node to be created
    *@param type String with entity type
    *@param _path String with path to use to get to this group D:MODELAVAIL:D where type = AVAIL
    */
    public XMLLSEOWARRGroupElem(String nname, String type, String _path)
    {
        super(nname);
        etype = type;
        path = _path;
	}
    
    public XMLLSEOWARRGroupElem(String nname, String type, String _path, String _special)
    {
        super(nname);
        etype = type;
        path = _path;
        special = _special;
	}
    
    
    /**********************************************************************************
    * Constructor - used when element is part of a group with child elements that are
    * attributes for the entity - no path will be used, just get all entities of this type
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    */
    public XMLLSEOWARRGroupElem(String nname, String type)
    {
        super(nname);
        etype = type;
	}
    /**********************************************************************************
    * Constructor - used when element is part of a group with child elements that are
    * attributes for the root entity
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    *@param isroot boolean if true, entity is root
    */
    public XMLLSEOWARRGroupElem(String nname)
    {
        super(nname);
        etype = "ROOT";
	}

	/**********************************************************************************
	* Create a node for this element and add to the parent and any children this node has
	*
	*@param dbCurrent Database
	*@param table Hashtable of Vectors of DiffEntity
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param parentItem EntityItem - parent to use if path is specified
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
		//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.addElements: entered node:"+nodeName+" etype:"+etype+" "+
	    //	(parentItem==null?" null parent":parentItem.getKey())+" path:"+path+NEWLINE);
		EntityItem parentEntityitem = null;
		EntityItem priorEntityitem = null;
		Vector parentvct = new Vector();
		Vector vct  = null;
		if (parentItem.isDeleted()){
			parentEntityitem = parentItem.getPriorEntityItem();
			parentvct.add(parentEntityitem);
		}else if (parentItem.isNew()){
			parentEntityitem = parentItem.getCurrentEntityItem();
			parentvct.add(parentEntityitem);
		}else {
			parentEntityitem = parentItem.getCurrentEntityItem();
			parentvct.add(parentEntityitem);
			priorEntityitem = parentItem.getPriorEntityItem();
			parentvct.add(priorEntityitem);
			
		}
		if ("MODELWARR|PRODSTRUCTWARR".equals(etype)){
			vct = getWARRRelatorVect(table, parentEntityitem,debugSb );
		} else if ("WARR".equals(etype)){
		    vct = getWWARvct(table, parentvct, debugSb);
		}
		
//		Vector vct = getItems(parentEntityitem, path, debugSb);
	
		if (vct==null){
			
			if("SPECIAL".equals(special)){
				ABRUtil.append(debugSb,"@@@@XMLLSEOWARRGroupElem: node is null:" + nodeName + " path:" + path);
				
				Element child = (Element) document.createElement("PUBFROM");
				child.appendChild(document.createTextNode(""));
				parent.appendChild(child);
				
				child = (Element) document.createElement("PUBTO");
				child.appendChild(document.createTextNode(""));
				parent.appendChild(child);
				
				child = (Element) document.createElement("DEFWARR");
				child.appendChild(document.createTextNode("ERRORNULL"));
				parent.appendChild(child);
				
			} else {			
				
				if (nodeName==null) {nodeName="ERROR";}
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
	
				if (parent ==null){ // create the root element of the document
					document.appendChild(elem);
				}else{
					parent.appendChild(elem);
				}
	
				elem.appendChild(document.createTextNode("Error: "+etype+" not found in extract!"));
	
				if(isReq){
					throw new IOException(nodeName+" is required but "+etype+" is not in extract");
				}
				// add any children
				for (int c=0; c<childVct.size(); c++){
					XMLElem childElem = (XMLElem)childVct.elementAt(c);
					childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
				}
			}

		}else{
			// get list of entities to look at, filtering may have been done
			Vector entityVct = getEntities(vct);
			
			if (nodeName != null){
				String xmlns = null;
				if (parent == null) 
					xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;
								
				// create node for this element
				Element elem = (Element) document.createElementNS(xmlns,nodeName);
				addXMLAttrs(elem);

				if (parent ==null){ // create the root
					ABRUtil.append(debugSb,"create root1: " + xmlns + " " + nodeName);
					document.appendChild(elem);
					elem.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);
				}else{
					parent.appendChild(elem);
				}

				// if no entities exist for this type, return
				if (entityVct.size()==0){
					// create the node but dont output children
					// a value is expected, prevent a normal empty tag, OIDH cant handle it
					elem.appendChild(document.createTextNode(CHEAT));
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" path:"+path+
						" No entities found for "+etype+NEWLINE);
					return;
				}

				// use this entity for children elements
				for(int i=0; i<entityVct.size(); i++){
					// it may exist at current or prior time or both
					DiffEntity de = (DiffEntity)entityVct.elementAt(i);
//						 only output if changed or is root
						if (//!de.isChanged() cant do it this way because child structure may use other entities
							!de.isRoot() && !hasChanges(table, de, debugSb)){
							ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" path:"+path+
								" No Changes found in "+de.getKey()+NEWLINE);
							continue;
						}

						// add any children
						for (int c=0; c<childVct.size(); c++){
							XMLElem childElem = (XMLElem)childVct.elementAt(c);
							childElem.addElements(dbCurrent,table, document,elem,de,debugSb);
						}
						
					}
				entityVct.clear();

				if (!elem.hasChildNodes()){
					// a value is expected, prevent a normal empty tag, OIDH cant handle it
					elem.appendChild(document.createTextNode(CHEAT));
				}
			} // nodename !=null
			else{
				// used where a different entity is needed on the same level as current entity
				// like MODEL output needs
				//<DIVISION>	</DIVISION>			2	PROJ	DIV
				// node is null, so dont create one for this, just do children..
				if (entityVct.size()==0){
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" path:"+path+
						" No entities found for "+etype+NEWLINE);
					// add any children to the parent, not this node
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,table, document,parent,null,debugSb);
					}

					return;
				}

				for(int i=0; i<entityVct.size(); i++){
					// it may exist at current or prior time or both
					DiffEntity de = (DiffEntity)entityVct.elementAt(i);

					// add any children to the parent, not this node
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,table, document,parent,de,debugSb);
					}
				}
				entityVct.clear();
			} // nodename is null
			vct.clear();
		}//end vct !=null
		//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.addElements: exiting node:"+nodeName+" etype:"+etype+"  "+
			//	(parentItem==null?" null parent":parentItem.getKey())+" path:"+path+NEWLINE);
	}

    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
    *
    *@param dbCurrent Database
    *@param list EntityList
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
	*@param parentItem EntityItem - parent to use if path is specified
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
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		// get all entitys of etype
		EntityGroup egrp = null;
		if ("ROOT".equals(etype)) {
			egrp = list.getParentEntityGroup();
		} else {
			egrp = list.getEntityGroup(etype);
		}

		if (egrp==null){
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			if (parent ==null){ // create the root
				document.appendChild(elem);
			}else{
				parent.appendChild(elem);
			}

			elem.appendChild(document.createTextNode("Error: "+etype+" not found in extract!"));

			if(isReq){
				throw new IOException(nodeName+" is required but "+etype+" is not in extract");
			}
			// add any children
			for (int c=0; c<childVct.size(); c++){
				XMLElem childElem = (XMLElem)childVct.elementAt(c);
				childElem.addElements(dbCurrent,list, document,elem,parentItem,debugSb);
			}
		}else{
			// get list of entities to look at, filtering may have been done
			Vector entityVct = getEntities(egrp);

			// if path is not null then use it to go from parentItem to children via that path
			// else just get all of that particular type
			if (path != null && parentItem!= null){
				EntityItem theitem = parentItem;
				Vector overrideVct = new Vector(1);
				Vector parentitemsVct = new Vector(1);
				parentitemsVct.add(theitem);
				StringTokenizer st1 = new StringTokenizer(path,":");
				while (st1.hasMoreTokens()) {
					String dir = st1.nextToken();
					String destination = etype;
					if (st1.hasMoreTokens()){
						destination = st1.nextToken();
					}
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" path:"+path+
						" dir:"+dir+" destination "+destination+NEWLINE);
					// know we know dir and type needed
					Vector tmp = new Vector();
					for (int p=0; p<parentitemsVct.size(); p++){
						EntityItem pitem = (EntityItem)parentitemsVct.elementAt(p);
						ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: loop pitem "+pitem.getKey()+NEWLINE);
						Vector linkVct = null;
						if (dir.equals("D")){
							linkVct = pitem.getDownLink();
						}else{
							linkVct = pitem.getUpLink();
						}
						for (int i=0; i<linkVct.size(); i++){
							EntityItem entity = (EntityItem)linkVct.elementAt(i);
							ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: linkloop entity "+entity.getKey()+NEWLINE);
							if (entity.getEntityType().equals(destination)){
								if (st1.hasMoreTokens()){
									//keep looking
									tmp.add(entity);
								}else{
									overrideVct.add(entity);
								}
							}
						}// end linkloop
					}// end parentloop
					parentitemsVct = tmp;
				}
				entityVct = overrideVct;
			}

			if (nodeName != null){
				// create node for this element
				String xmlns = null;
				if (parent == null) 
					xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;
							    
				// create node for this element
				Element elem = (Element) document.createElementNS(xmlns,nodeName);
				addXMLAttrs(elem);

				if (parent ==null){ // create the root
					ABRUtil.append(debugSb,"create root2: " + xmlns + " " + nodeName);
					document.appendChild(elem);
					elem.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);
				}else{
					parent.appendChild(elem);
				}

				// if no entities exist for this type, return
				if (entityVct.size()==0){
					// create the node but dont output children
					// a value is expected, prevent a normal empty tag, OIDH cant handle it
					elem.appendChild(document.createTextNode(CHEAT));
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" No entities found for "+etype+NEWLINE);
					// add any children
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,elem,null,debugSb);
					}
					return;
				}

				// use this entity for children elements
				for(int i=0; i<entityVct.size(); i++){
					EntityItem item = (EntityItem)entityVct.elementAt(i);

					// add any children
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,elem,item,debugSb);
					}
				}
				entityVct.clear();

				if (!elem.hasChildNodes()){
					// a value is expected, prevent a normal empty tag, OIDH cant handle it
					elem.appendChild(document.createTextNode(CHEAT));
				}
			} // nodename !=null
			else{
				// used where a different entity is needed on the same level as current entity
				// like MODEL output needs
				//<DIVISION>	</DIVISION>			2	PROJ	DIV
				// node is null, so dont create one for this, just do children..
				// if no entities exist for this type, return
				if (entityVct.size()==0){
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem: node:"+nodeName+" No entities found for "+etype+NEWLINE);
					// add any children to the parent, not this node
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,parent,null,debugSb);
					}
					return;
				}

				for(int i=0; i<entityVct.size(); i++){
					EntityItem item = (EntityItem)entityVct.elementAt(i);

					// add any children to the parent, not this node
					for (int c=0; c<childVct.size(); c++){
						XMLElem childElem = (XMLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,parent,item,debugSb);
					}
				}
				entityVct.clear();
			} // nodename is null
		}
    }
    /**********************************************************************************
     * Check to see if there are any changes in this node or in the children
     *
     *@param table Hashtable
     *@param diffitem DiffEntity
     *@param debugSb StringBuffer
     */
     protected boolean hasChanges(Hashtable table, DiffEntity diffitem, StringBuffer debugSb)
     {
    	 boolean changed=false;
    	 ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.hasChanges entered for node:"+nodeName+" "+diffitem.getKey()+NEWLINE);
    	 // if item matches the etype then this is looking at an item within the group, no path needed
         // etype may be mutliple entitytype as is the case for FEATURELIST in ADSLSEOABR where
    	 // FEATURE|SWFEATURE is used to include both types in the list.
    	 String types[] = PokUtils.convertToArray(etype);
         boolean found_etype=false;
         for(int a=0; a<types.length; a++){
    		 if (types[a].equals(diffitem.getEntityType())) found_etype = true;
         }
         
    	 if (diffitem!=null && found_etype){    		
			 for (int c=0; c<childVct.size() && !changed; c++){
    			 XMLElem childElem = (XMLElem)childVct.elementAt(c);    			
				 if (childElem.hasChanges(table, diffitem, debugSb)){
    				 changed = true; // one change one is enough
    				 break;
    			 }    			     			 
    		 }     		 	 
    	 }else{
    		 Vector parentvct = new Vector();
    		 EntityItem parentEntityitem = null;
    		 EntityItem priorItem = null;
    		 Vector vct = null;
    		 if (diffitem.isDeleted()){
    			 parentEntityitem = diffitem.getPriorEntityItem();
    				parentvct.add(parentEntityitem);
    			}else if (diffitem.isNew()){
    				parentEntityitem = diffitem.getCurrentEntityItem();
    				parentvct.add(parentEntityitem);
    			}else {
    				parentEntityitem = diffitem.getCurrentEntityItem();
    				parentvct.add(parentEntityitem);
    				priorItem = diffitem.getPriorEntityItem();
    				parentvct.add(priorItem);
    				
    			}
    		 if ("MODELWARR|PRODSTRUCTWARR".equals(etype)){
    				vct = getWARRRelatorVect(table, parentEntityitem,debugSb );
    			} else if ("WARR".equals(etype)){
    			    vct = getWWARvct(table, parentvct, debugSb);
    			}
    		 if (vct !=null){
    			 // check its children
    			 outerloop: for (int i=0; i<vct.size(); i++){
    				 DiffEntity de = (DiffEntity)vct.elementAt(i);
    				 for (int c=0; c<childVct.size() && !changed; c++){
    					 XMLElem childElem = (XMLElem)childVct.elementAt(c);
    						 if (childElem.hasChanges(table, de,debugSb)){
	    						 changed = true; // one change one is enough
	    						 break outerloop;
    					 }
    				 }
    			 }
    		 	vct.clear();
    		 }
    	 }
    	 return changed;
     }
     
//     private void printTable(Hashtable table, StringBuffer debugSb) {
// 		ABRUtil.append(debugSb,"XMLCtryAudElem.printTable for new lseo:" + NEWLINE);
// 		Iterator it = table.keySet().iterator();
// 		while (it.hasNext()){
// 			String key =(String)it.next();
// 			ABRUtil.append(debugSb,"table:key=" + key + ";value=" + table.get(key)+NEWLINE);
// 		}
// 		
// 	}
     
		// Path = U:WWSEOLSEO:U
		// etype = WWSEO
		// For LSEOs which are not options, the WARR will be inherited from the MODEL. 
		// For LSEOs which are options the WARR will be taken from the PRODSTRUCT of the WWSEO if the WARR exists there. 
		// If it does not exist then it will be taken from the WWSEO’s parent MODEL
		/**
		 * through the path get the destinaiton entity.
		 * @param parentitem
		 * @param path
		 * @param debugSb
		 * @return
		 */
     private Vector getItems(EntityItem parentitem, String path, String destype, StringBuffer debugSb) {
			Vector parentitemsVct = new Vector(1);
			Vector overrideVct = new Vector(1);
			parentitemsVct.add(parentitem);
			StringTokenizer st1 = new StringTokenizer(path, ":");
			while (st1.hasMoreTokens()) {
				String dir = st1.nextToken();
				String destination = destype;
				if (st1.hasMoreTokens()) {
					destination = st1.nextToken();
				}
				//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.getItems: node:" + nodeName + " path:" + path + " dir:" + dir + " destination "
				//	+ destination + NEWLINE);
				// know we know dir and type needed
				Vector tmp = new Vector();
				for (int p = 0; p < parentitemsVct.size(); p++) {
					EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
					//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.getItems: loop pitem " + pitem.getKey() + NEWLINE);
					Vector linkVct = null;
					if (dir.equals("D")) {
						linkVct = pitem.getDownLink();
					} else {
						linkVct = pitem.getUpLink();
					}
					for (int i = 0; i < linkVct.size(); i++) {
						EntityItem entity = (EntityItem) linkVct.elementAt(i);
						//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.getItems: linkloop entity " + entity.getKey() + NEWLINE);
						if (entity.getEntityType().equals(destination)) {
							if (st1.hasMoreTokens()) {
								//keep looking
								tmp.add(entity);
							} else {
								//find diffitem in table
								overrideVct.add(entity);
								ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.getItems: find entity key=" + entity.getKey() + NEWLINE);
							}
						}
					}// end linkloop
				}// end parentloop
				parentitemsVct.clear();// remove all
				parentitemsVct = tmp;
			}

			parentitemsVct.clear();
			return overrideVct;
		}
     
 	/**
 	 *    put WARR, MODELWARR|PRODSTRUCTWARR into table , String[] warrarry = new String[] {entity.getKey(),pitem.getKey()};
		 * through the path get the destinaiton entity. and store the relator to the Hashtable for child elements to use.
		 * @param parentitem
		 * @param path
		 * @param debugSb
		 * @return
		 */
  private boolean putWarrToHb(Hashtable table, EntityItem parentitem, String path, String destype, StringBuffer debugSb) {
	        boolean isExist = false;
			Vector parentitemsVct = new Vector(1);
			parentitemsVct.add(parentitem);
			StringTokenizer st1 = new StringTokenizer(path, ":");
			while (st1.hasMoreTokens()) {
				String dir = st1.nextToken();
				String destination = destype;
				if (st1.hasMoreTokens()) {
					destination = st1.nextToken();
				}
				ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.putWarrToHb: node:" + nodeName + " path:" + path + " dir:" + dir + " destination "
					+ destination + NEWLINE);
				// know we know dir and type needed
				Vector tmp = new Vector();
				for (int p = 0; p < parentitemsVct.size(); p++) {
					EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
					ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.putWarrToHb: loop pitem " + pitem.getKey() + NEWLINE);
					Vector linkVct = null;
					if (dir.equals("D")) {
						linkVct = pitem.getDownLink();
					} else {
						linkVct = pitem.getUpLink();
					}
					for (int i = 0; i < linkVct.size(); i++) {
						EntityItem entity = (EntityItem) linkVct.elementAt(i);
						//ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.putWarrToHb: linkloop entity " + entity.getKey() + NEWLINE);
						if (entity.getEntityType().equals(destination)) {
							if (st1.hasMoreTokens()) {
								//keep looking
								tmp.add(entity);
							} else {
								//new added(check)
								int check = 0;								
								String activity = CHEAT;
								//find diffitem in table
								DiffEntity relorEntity = (DiffEntity)table.get(pitem.getKey());{
									if (relorEntity.isDeleted()){										
										boolean priorCheck = checkMODELWARRcntry(table,relorEntity.getPriorEntityItem(),debugSb);
										if (priorCheck){
										activity = DELETE_ACTIVITY;										
										}	
										check=1;
									}else if (relorEntity.isNew()){
										boolean curCheck = checkMODELWARRcntry(table,relorEntity.getCurrentEntityItem(),debugSb);
										if (curCheck){
											activity = UPDATE_ACTIVITY;
										}
										check=2;
									}else{
										boolean curCheck = checkMODELWARRcntry(table,relorEntity.getCurrentEntityItem(),debugSb);
										boolean priorCheck = checkMODELWARRcntry(table,relorEntity.getPriorEntityItem(),debugSb);
										if (priorCheck==true&&curCheck==true){
											activity = UPDATE_ACTIVITY;
											check=3;
										}else if (priorCheck==false&&curCheck==true){
											activity = UPDATE_ACTIVITY;
											check=4;
										}else if (priorCheck==true&&curCheck==false){
											activity = DELETE_ACTIVITY;
											check=5;
										}
									}
								}
								if (!CHEAT.equals(activity)){
									isExist = true;
									//new added(print check)
									ABRUtil.append(debugSb,"check is "+ check + NEWLINE);
								
									//overrideVct.add(entity);
									String key = prekey + entity.getKey();
									if (!table.containsKey(key)){
										String[] warrarry = new String[] {entity.getKey(),pitem.getKey(),activity};
										table.put(key, warrarry);
									}
								} 
								ABRUtil.append(debugSb,"XMLLSEOWARRGroupElem.putWarrToHb: find entity key=" + entity.getKey() + " Relator :" + pitem.getKey() + " has country in <AVAILIBILITYLIST> countrylist :" + activity + NEWLINE);
							}
						}
					}// end linkloop
				}// end parentloop
				parentitemsVct.clear();// remove all
				parentitemsVct = tmp;
			}

			parentitemsVct.clear();
			
			return isExist;
		}
     /**
      * For LSEOs which are not options, the WARR will be inherited from the MODEL.  
      * For LSEOs which are options the WARR will be taken from the PRODSTRUCT of the WWSEO 
      * if the WARR exists there. If it does not exist then it will be taken from the WWSEO’s parent MODEL
      * The LSEOs parent WWSEO.SEOORDERCODE=’MES’ attribute value indicates an option. 
      * The MODEL is found by first finding the WWSEO via the WWSEOLSEO relator and then using the MODELWWSEO relator.
      * The PRODSTRUCT of the WWSEO is found via the WWSEOPRODSTRUCT relator. 
      * Any WARRs found will be subject to filtering where either MODELWARR or PRODSTRUCTWARR 
      * COUNTRYLIST has a country that is in the <AVAILABILITYLIST>.
      * @param parentitem
      * @param debugSb
      * @return
      */
     private Vector getWWARvct(Hashtable table, Vector parentvct, StringBuffer debugSb) {
		// get WWSEO item through path U:WWSEOLSEO:U
		Vector overrideVct = new Vector();
		for (int i = 0; i < parentvct.size(); i++) {
			EntityItem parentitem = (EntityItem) parentvct.elementAt(i);
			Vector wwseovct = getItems(parentitem, "U:WWSEOLSEO:U", "WWSEO", debugSb);
			if (wwseovct == null || wwseovct.size() == 0) {
				return null;
			} else {
				boolean option = false;
				boolean isexist = false;
				EntityItem wwseo = (EntityItem) wwseovct.elementAt(0);
				// WWSEO.SEOORDERCODE=’MES’ attribute value indicates an option
				// TODO check SEOORDERCODE is Text or flag
				// SEOORDERCODE 20 longdescription MES
				String seoordercode = PokUtils.getAttributeFlagValue(wwseo, "SEOORDERCODE");
				if (seoordercode != null && "20".equals(seoordercode)) {
					option = true;
				}
				if (option) {
					isexist = putWarrToHb(table, wwseo, "D:WWSEOPRODSTRUCT:D:PRODSTRUCT:D:PRODSTRUCTWARR:D", "WARR", debugSb);
					
					ABRUtil.append(debugSb,"XMLLSEOWARRGroup.getWWARvct: derive from PRODSTRUCT, return values: " + isexist + NEWLINE);
				}
				// If it does not exist then it will be taken from the WWSEO’s
				// parent MODEL
				if (!isexist) {
					putWarrToHb(table, wwseo, "U:MODELWWSEO:U:MODEL:D:MODELWARR:D", "WARR", debugSb);
				}
			}

		}
		 Iterator it= (Iterator)table.keys();
			while(it.hasNext()){
				String key = (String)it.next();
				if (key.startsWith(prekey)){
					 String[] warrarry = (String[])table.get(key);
					 if (warrarry != null){
						 DiffEntity de = (DiffEntity) table.get(warrarry[0]);
							if (de != null) {	
								overrideVct.add(de);
							}	 
					 }	
				}
				
			}
		return overrideVct;
	}
     /**
		 * get MODELWARR or PRODSTRUCTWARR , depends on the key which pre-stored
		 * in the Hashtable.
		 * 
		 * @param table
		 * @param parentitem
		 * @param debugSb
		 * @return
		 */
     private Vector getWARRRelatorVect(Hashtable table, EntityItem parentitem, StringBuffer debugSb){
    	 Vector overrideVct = new Vector();
    	 Vector uplinks = parentitem.getUpLink();
    	 if(uplinks!=null){
			for (int i = 0; i < uplinks.size(); i++) {
				EntityItem oneUplink = (EntityItem) uplinks.elementAt(i);
				String uplinkkey = oneUplink.getKey();
				ABRUtil.append(debugSb,"XMLLSEOWARRGroup.getWARRRelator: get from hashtable: key="+ uplinkkey + NEWLINE);
				DiffEntity de = (DiffEntity) table.get(uplinkkey);				
				if (de!=null) {
					overrideVct.add(de);
				}
			}
    	 }else{
    		 return null;
    	 }
//    	 String key = prekey + parentitem.getKey();
//    	 String[] warrarry = (String[])table.get(key);
//    	  if (warrarry!=null){
//    		  ABRUtil.append(debugSb,"XMLLSEOWARRGroup.getWARRRelator: get from hashtable: "+ key + " relator :" + warrarry[1]+ NEWLINE);
//    		  DiffEntity de = (DiffEntity) table.get(warrarry[1]);
//				if (de != null) {	
//					overrideVct.add(de);
//				}
//    	  }else{
//    		  return null;
//    	  }
    	  return overrideVct;
     }
     /**
      * Any WARRs found will be subject to filtering where either MODELWARR 
      * or PRODSTRUCTWARR COUNTRYLIST has a country that is in the <AVAILABILITYLIST>.
      * @param table contain the contrylist of AVAILABILITYLIST
      * @param pitem
      * @return
      */
     private boolean checkMODELWARRcntry(Hashtable table, EntityItem pitem, StringBuffer debugSb){
    	String cntrylist = (String) table.get(countryKey);
    	ABRUtil.append(debugSb,"XMLLSEOWARRGroup.checkMODELWARRcntry countrylist from AVAILBILITYLSIT: " +cntrylist + NEWLINE);
    	boolean isexist  = false;
    	if (cntrylist != null){
    		if (cntrylist.equalsIgnoreCase("ALL")){
    			return true;
    		}else{		
	            Vector vct = new Vector();
	            StringTokenizer st = new StringTokenizer(cntrylist,"|");
	            while(st.hasMoreTokens())
	            {
	                vct.addElement(st.nextToken());
	            }		
    			EANFlagAttribute ctryAtt = (EANFlagAttribute) pitem.getAttribute("COUNTRYLIST");
    			ABRUtil.append(debugSb,"XMLLSEOWARRGroup.checkMODELWARRcntry for " +pitem.getKey() + " : ctryAtt "
    				+ PokUtils.getAttributeFlagValue(pitem, "COUNTRYLIST") + NEWLINE);
    			if (ctryAtt != null) {
    				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
    				for (int im = 0; im < mfArray.length; im++) {
    					// get selection
    					if (mfArray[im].isSelected()) {
    						String ctryVal = mfArray[im].getFlagCode();
    						if (vct.contains(ctryVal)){
    							isexist = true;
    							break;
    						}
    					}
    					
    		        }
    	        }
    		}
    	}
    	return isexist;
     }
}