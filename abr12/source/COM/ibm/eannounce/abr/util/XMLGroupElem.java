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
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a set or group of entities.  A node will not be output
* if no changes exist in the entity (but root is always output)
*/
// $Log: XMLGroupElem.java,v $
// Revision 1.19  2020/02/05 13:29:08  xujianbo
// Add debug info to investigate   performance issue
//
// Revision 1.18  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.17  2011/02/22 03:39:18  guobin
// add XMLGroupElem(null, entity,path,false,level)
//
// Revision 1.16  2011/02/21 09:27:23  guobin
// remove the print table information
//
// Revision 1.15  2011/02/18 06:51:56  guobin
// change the parse of the path
//
// Revision 1.14  2011/02/17 06:41:20  guobin
// change while (st3.hasMoreTokens()) { to if(st3.hasMoreTokens){  for the case that there are more PRCTPs
//
// Revision 1.13  2010/12/10 09:20:31  guobin
// add isMultUse in  getItem(). filtering the diffiEntity throught the path of diffEntity.toString()
//
// Revision 1.10  2010/12/07 10:01:13  guobin
// fixed the muitiUsed entity
//
// Revision 1.9  2010/12/06 08:46:28  guobin
// change the method of hasChanges to fix the connect with multiUse entity
//
// Revision 1.8  2010/12/03 13:27:03  guobin
// add the hasChanges method to fix the multiUse entity
//
// Revision 1.7  2010/11/30 05:55:52  guobin
// check whether XMLGroupElem has a nesting list after leve 2
//
// Revision 1.6  2010/11/26 07:19:01  guobin
// while multi relator connect to the entity, it can't check the isDeleted status when only one relator is deleted.
//
// Revision 1.5  2010/08/10 20:07:23  rick
// more changes to support multiple entity types in etype.
//
// Revision 1.4  2010/07/19 14:45:55  rick
// changes to support FEATURE and SWFEATURE in
// FEATURELIST for LSEO XML.
//
// Revision 1.3  2010/03/15 21:21:28  rick
// change to add namespace (xmlns) to xml.
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

public class XMLGroupElem extends XMLElem
{
	private String path = null;
    private String etype =null;
    private boolean isMultUse= false;
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
    public XMLGroupElem(String nname, String type, String _path,boolean _isMultUse)
    {
    	super(nname);
        etype = type;
        path = _path;
        isMultUse = _isMultUse;
    }
    public XMLGroupElem(String nname, String type, String _path,boolean _isMultUse,int _ilevel)
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
    public XMLGroupElem(String nname, String type, String _path)
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
    public XMLGroupElem(String nname, String type)
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
    public XMLGroupElem(String nname)
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
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
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
				//String domain = PokUtils.getAttributeFlagValue(ei, "PDHDOMAIN");
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
    	D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
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
			if (isMultUse){
//				add debug information for the table 
				ABRUtil.append(debugSb,"XMLGroupElem.getItems: path1="+ path + NEWLINE);
//		    	printTable(table, debugSb);  				
				
				Vector overrideVct = new Vector(1);
				Hashtable keyMap = new Hashtable();
				String key ="";
				Vector tmp = (Vector) table.get(etype);
				StringTokenizer st1 = new StringTokenizer(path, ":");
				String relator = CHEAT;
				while (st1.hasMoreTokens()) {
					String dir = st1.nextToken();
					if(st1.hasMoreTokens()){
					    relator = st1.nextToken();
						ABRUtil.append(debugSb,"XMLGroupElem.getItems: node:" + nodeName + " path:" + path + " dir:" + dir + " relator " + relator + NEWLINE);
						break;
					}
				}
				for(int i=0; i<tmp.size(); i++){
					DiffEntity de = (DiffEntity) tmp.elementAt(i);
					StringTokenizer st2 = new StringTokenizer(de.toString(), " ");
	 				String currentPath = CHEAT;
	 				while (st2.hasMoreTokens()) {
	 					currentPath = st2.nextToken();
	 					if (currentPath.startsWith("path:"))
	 						break;
	 				}
	 				if (!CHEAT.equals(currentPath)) {
	 					StringTokenizer st3 = new StringTokenizer(currentPath, ":");
	 					//while (st3.hasMoreTokens()) {
	 					if (st3.hasMoreTokens()) {
	 						String dir = st3.nextToken();
	 						if(dir.equals("path")){
	 							dir = st3.nextToken();
	 						}
	 						if (dir.startsWith(relator)) {
	 							if (st3.hasMoreTokens()) {
	 								if (st3.nextToken().startsWith(diffitem.getKey()))
	 								{	
	 									key = de.getKey();
	 									if(!keyMap.contains(key)){
	 										keyMap.put(key, key);
	 										overrideVct.add(de);
	 									}	 									
	 								}
	 								//break;
	 							}
	 						}
	 					}
	 				}	 				
				}
				vct = overrideVct;
			}else{
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
						}// end linkloop
					}// end parentloop
					parentitemsVct.clear();// remove all
					parentitemsVct = tmp;
				}
				vct = overrideVct;
				parentitemsVct.clear();
			}
		
		} else {
			// get all entitys of etype, root is "ROOT"
			// This code supports coding mutliple entity types for the group
			// like (FEATURE|SWFEATURE) for example.
			ABRUtil.append(debugSb,"test XMLGroupElem.getItems: path3="+ path + NEWLINE);
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
			}			
			/* vct = (Vector)table.get(etype);
			 if (vct!=null){
			 vct = (Vector)vct.clone();

			 }*/

		return vct;
	}
}
