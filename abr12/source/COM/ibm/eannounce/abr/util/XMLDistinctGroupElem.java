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
this Class can mutilple used , you can add additional function if you like.
Current Function:
1
isMultUse is True, then get DiffEntity one entity ID only get once.
for example :  WWSEO->Prodtruct->Feature and LSEO-Prodstruct-Feature and MODEL->Prodstruct->Feature.
These Features are the same ones. so we need to distinct them.
2...

*/

public class XMLDistinctGroupElem extends XMLElem
{
	private String path = null;
    private String etype =null;
    private boolean isMultUse= false;
    private boolean isOnce = false;
    private int ilevel=0;
    
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
     *@param _isMultUse boolean with isNesting to check whether XMLGroupElem has a nesting list after leve 2
     */
    public XMLDistinctGroupElem(String nname, String type, String _path,boolean _isMultUse, boolean _isOnce)
    {
    	super(nname);
        etype = type;
        path = _path;
        isMultUse = _isMultUse;
        isOnce = _isOnce;
    }
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
     *@param _isMultUse boolean with isNesting to check whether XMLGroupElem has a nesting list after leve 2
     */
    public XMLDistinctGroupElem(String nname, String type, String _path,boolean _isMultUse)
    {
    	super(nname);
        etype = type;
        path = _path;
        isMultUse = _isMultUse;
    }
    public XMLDistinctGroupElem(String nname, String type, String _path,boolean _isMultUse,int _ilevel)
    {
    	super(nname);
        etype = type;
        path = _path;
        isMultUse = _isMultUse;
        ilevel = _ilevel;
    }
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
    public XMLDistinctGroupElem(String nname, String type, String _path)
    {
        super(nname);
        etype = type;
        path = _path;
	}
    /**********************************************************************************
    * Constructor - used when element is part of a group with child elements that are
    * attributes for the entity - no path will be used, just get all entities of this type
    *
    *@param nname String with name of node to be created
    *@param type String with entity type
    */
    public XMLDistinctGroupElem(String nname, String type)
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
    public XMLDistinctGroupElem(String nname)
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
		//ABRUtil.append(debugSb,"XMLGroupElem.addElements: entered node:"+nodeName+" etype:"+etype+" "+
			//	(parentItem==null?" null parent":parentItem.getKey())+" path:"+path+NEWLINE);
		
		Vector vct = getItems(table, parentItem, debugSb);

		if (vct==null){
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
					ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" path:"+path+
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
							ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" path:"+path+
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
					ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" path:"+path+
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
		//ABRUtil.append(debugSb,"XMLGroupElem.addElements: exiting node:"+nodeName+" etype:"+etype+"  "+
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
					ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" path:"+path+
						" dir:"+dir+" destination "+destination+NEWLINE);
					// know we know dir and type needed
					Vector tmp = new Vector();
					for (int p=0; p<parentitemsVct.size(); p++){
						EntityItem pitem = (EntityItem)parentitemsVct.elementAt(p);
						ABRUtil.append(debugSb,"XMLGroupElem: loop pitem "+pitem.getKey()+NEWLINE);
						Vector linkVct = null;
						if (dir.equals("D")){
							linkVct = pitem.getDownLink();
						}else{
							linkVct = pitem.getUpLink();
						}
						for (int i=0; i<linkVct.size(); i++){
							EntityItem entity = (EntityItem)linkVct.elementAt(i);
							ABRUtil.append(debugSb,"XMLGroupElem: linkloop entity "+entity.getKey()+NEWLINE);
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
					ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" No entities found for "+etype+NEWLINE);
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
					ABRUtil.append(debugSb,"XMLGroupElem: node:"+nodeName+" No entities found for "+etype+NEWLINE);
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
    	 ABRUtil.append(debugSb,"XMLGroupElem.hasChanges entered for node:"+nodeName+" "+diffitem.getKey()+NEWLINE);
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
    		 Vector vct = getItems(table, diffitem, debugSb);
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
     
     /********************
     * use path if necessary to find items for this group
     * @param table
     * @param diffitem
     * @param debugSb
     * @return
     */
     private Vector getItems(Hashtable table, DiffEntity diffitem, StringBuffer debugSb) {
		Vector vct = null;

		// if path is not null then use it to go from parentItem to children via that path
		// else just get all of that particular type
		// change 20110214 avoid add the same entity
		if (path != null && diffitem != null) {
				//only check the isMultUse= false and level>0
				ABRUtil.append(debugSb,"XMLGroupElem.getItems: path2="+ path + NEWLINE);
				EntityItem theitem = diffitem.getCurrentEntityItem();				
				if (diffitem.isDeleted()) {
					theitem = diffitem.getPriorEntityItem();
				}
				Vector overrideVct = new Vector(1);
				Vector parentitemsVct = new Vector(1);
				parentitemsVct.add(theitem);
				StringTokenizer st1 = new StringTokenizer(path, ":");
				while (st1.hasMoreTokens()) {
					String dir = st1.nextToken();
					String destination = etype;
					if (st1.hasMoreTokens()) {
						destination = st1.nextToken();
					}
					ABRUtil.append(debugSb,"XMLGroupElem.getItems: node:" + nodeName + " path:" + path + " dir:" + dir + " destination "
						+ destination + NEWLINE);
					// know we know dir and type needed
					Vector tmp = new Vector();
					for (int p = 0; p < parentitemsVct.size(); p++) {
						EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
						ABRUtil.append(debugSb,"XMLGroupElem.getItems: loop pitem " + pitem.getKey() + NEWLINE);
						Vector linkVct = null;
						if (dir.equals("D")) {
							linkVct = pitem.getDownLink();
						} else {
							linkVct = pitem.getUpLink();
						}
						for (int i = 0; i < linkVct.size(); i++) {
							EntityItem entity = (EntityItem) linkVct.elementAt(i);
							ABRUtil.append(debugSb,"XMLGroupElem.getItems: linkloop entity " + entity.getKey() + NEWLINE);
							if (entity.getEntityType().equals(destination)) {
								if (st1.hasMoreTokens()) {
									//keep looking
									tmp.add(entity);
								} else {
									//find diffitem in table
									DiffEntity de = (DiffEntity) table.get(entity.getKey());
									if (de != null) {										
										if(ilevel>0){
											if(de.getLevel()==ilevel){
												overrideVct.add(de);
												ABRUtil.append(debugSb,"XMLGroupElem.getItems: find entity key=" + entity.getKey()+"de.level="+de.getLevel()+"ilevel="+ ilevel + NEWLINE);
											}
										}else{
											overrideVct.add(de);
										}										
									}
								}
							}
							if(isOnce){
								if(overrideVct.size()==1){
									parentitemsVct.clear();
									return overrideVct;
								}
								
							}
						}// end linkloop
					}// end parentloop
					parentitemsVct.clear();// remove all
					parentitemsVct = tmp;
				}
				vct = overrideVct;
				parentitemsVct.clear();
			}else {
				
				// get all entitys of etype, root is "ROOT"
				// This code supports coding mutliple entity types for the group
				// like (FEATURE|SWFEATURE) for example.
				//ABRUtil.append(debugSb,"test XMLGroupElem.getItems: path3="+ path + NEWLINE);
					String types[] = PokUtils.convertToArray(etype);
					for (int a = 0; a < types.length; a++) {
						String type = types[a];
						Vector tmp = (Vector) table.get(type);
						if (tmp != null) {
							if (vct == null) {
								vct = new Vector(tmp);
							} else {
								vct.addAll(tmp);
							}
						}
					}
					//if isOnce is true, then get the first DiffEntity and get only one Diff.
					if(isOnce){
						Vector tmpvct = new Vector();
						if(vct.size()>0){
							DiffEntity diffEntity = (DiffEntity)vct.elementAt(0);
							tmpvct.add(diffEntity);
						}
						return tmpvct;						
					}
					//if isMultUse is true, then distinct DiffEntity one EntityID only get one Diff. 					
					if (isMultUse){	
						//ABRUtil.append(debugSb,"table: " + table.toString() + NEWLINE);
						HashMap keyHash = new HashMap();
						Vector tmpvct = new Vector();
						for (int jj=0; jj<vct.size(); jj++){
							DiffEntity dffFeature = (DiffEntity)vct.elementAt(jj);
							ABRUtil.append(debugSb,"XMLDistinctGroupElem.getItems: dffFeature="+ dffFeature.getKey()+ " isDelete"+dffFeature.isDeleted() + " isNew"+dffFeature.isNew() + " isUpdate"+dffFeature.isChanged() + NEWLINE);
							//[Work Item 1016651] FEATUREELEMENT in SEO_UPDATE is not derived correctly. dffEeature show delete, although it was not deleted.
							//ABRUtil.append(debugSb,"XMLDistinctGroupElem.getItems: dffFeature=" + dffFeature.toString()+ NEWLINE);
							if ("FEATURE|SWFEATURE".equals(etype)){
								if (dffFeature.toString().indexOf("LSEOPRODSTRUCT") > 0 || dffFeature.toString().indexOf("LSEOSWPRODSTRUCT") > 0 || dffFeature.toString().indexOf("WWSEOPRODSTRUCT") > 0  || dffFeature.toString().indexOf("WWSEOSWPRODSTRUCT") > 0) {
									String key = dffFeature.getKey();
									if (!keyHash.containsKey(key)){
										keyHash.put(key, dffFeature);
									}
								}		
							} else{ 
								String key = dffFeature.getKey();
								if (!keyHash.containsKey(key)){
									keyHash.put(key, dffFeature);
								}
							}
						}
						Collection ctryrecs = keyHash.values();
						tmpvct.addAll(ctryrecs);
						return tmpvct;
					}
			}			
			/* vct = (Vector)table.get(etype);
			 if (vct!=null){
			 vct = (Vector)vct.clone();

			 }*/

		return vct;
	}
}
