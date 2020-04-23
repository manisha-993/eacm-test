// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**********************************************************************************
* Base Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs
* If a value is not available in the desired NLSID, then the TAGs should still be
* generated with an empty data value.
*
*/
// $Log: SAPLElem.java,v $
// Revision 1.6  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2007/05/04 17:31:39  wendy
// Only generate tabs for nls section if values exist in that nls
//
// Revision 1.4  2007/04/30 19:32:26  wendy
// More OIDH empty tag workaround, again
//
// Revision 1.3  2007/04/20 21:36:09  wendy
// More OIDH empty tag workaround
//
// Revision 1.2  2007/04/20 14:58:33  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLElem
{
	public static final int ATTRVAL = 0; // get value from attribute
	public static final int FLAGVAL = 1; // get value from flag code

	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	protected static final String CHEAT = "@@";

	protected final static String AVAIL_ORDER[] = new String[]{"146","143","149","AVT220"};

	protected boolean isRoot=false, isReq=false;
	protected String nodeName;
	protected String etype =null;
	protected String attrCode =null; // could be attr1|attr2 for concatenated attrs
	protected Vector childVct = new Vector(1);
	protected Hashtable xmlAttrTbl = new Hashtable();
	protected int attrSrc = ATTRVAL;

	/**********************************************************************************
	* Constructor - used when element does not have text nodes and is not root
	*
	*@param nname String with name of node to be created
	*/
	public SAPLElem(String nname)
	{
		this(nname,null,null,false,ATTRVAL);
	}
	/**********************************************************************************
	* Constructor - used when element has a text node
	*
	*@param nname String with name of node to be created
	*@param type String with entity type
	*@param code String with attribute code
	*/
	public SAPLElem(String nname, String type, String code)
	{
		this(nname,type,code,false,ATTRVAL);
	}

	/**********************************************************************************
	* Constructor - used when element has a text node
	*
	*@param nname String with name of node to be created
	*@param type String with entity type
	*@param code String with attribute code
	*@param src int for flag attributes
	*/
	public SAPLElem(String nname, String type, String code, int src)
	{
		this(nname,type,code,false,src);
	}

	/**********************************************************************************
	* Constructor - used when element has a text node
	*
	*@param nname String with name of node to be created
	*@param type String with entity type
	*@param code String with attribute code
	*@param isroot boolean if true, entity is root
	*/
	public SAPLElem(String nname, String type, String code, boolean isroot)
	{
		this(nname, type, code, isroot, ATTRVAL);
	}

	/**********************************************************************************
	* Constructor - used when element has a text node
	*
	*@param nname String with name of node to be created
	*@param type String with entity type
	*@param code String with attribute code
	*@param isroot boolean if true, entity is root
	*@param src int for flag attributes
	*/
	public SAPLElem(String nname, String type, String code, boolean isroot, int src)
	{
		nodeName = nname;
		etype = type;
		attrCode = code;
		isRoot = isroot;
		attrSrc = src;
	}

	/**********************************************************************************
	* string rep
	*
	*@return String
	*/
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Node:"+nodeName+" type:"+etype+" attr:"+attrCode+" root:"+
			isRoot+" req:"+isReq+" childCnt:"+childVct.size());
		for(int i=0; i<childVct.size(); i++){
			sb.append(NEWLINE+"  "+childVct.elementAt(i).toString());
		}
		return sb.toString();
	}

	/**********************************************************************************
	* Add a child element to this 'node'
	*
	*@param sap SAPLElem with node to be added
	*/
	public void addChild(SAPLElem sap) {childVct.add(sap);}

	/**********************************************************************************
	* OIDH can't handle a standard empty XML tag <info/>, must have <info></info>
	* cheat is to always have a value so the document creates the open and close tags
	* then remove the cheat value
	*
	*@param xmltoFix String with xml that needs cheat value removed
	*@return String with cheat values removed
	*/
    public static String removeCheat(String xmltoFix)
    {
		StringBuffer outputXml = new StringBuffer(xmltoFix);
        int id = 0;
        while(id != -1)
        {
            id = outputXml.toString().indexOf(CHEAT,id);
            if (id != -1) {
                outputXml.replace(id, id+CHEAT.length(),"");
            }
        }
        return outputXml.toString();
    }
	/**********************************************************************************
	* add an XML tag attribute
	*
	*@param a String with name of xml tag attr
	*@param v String with value
	*/
	public void addXMLAttribute(String a, String v){
		xmlAttrTbl.put(a,v);
	}

	/**********************************************************************************
	* set this as required content
	*
	*/
	public void setRequired(){ isReq=true;}

	/**********************************************************************************
	* Get entities to output, overridden by derived classes when filtering is needed
	*
	*@param egrp EntityGroup
	*/
	protected Vector getEntities(EntityGroup egrp)
	{
		Vector vct = new Vector();
		if(egrp!=null){  // should not be the case if extract is properly defined
			for(int i=0; i<egrp.getEntityItemCount(); i++){
				vct.addElement(egrp.getEntityItem(i));
			}
		}
		return vct;
	}

	/**********************************************************************************
	* Parses a string containing XML and returns a DocumentFragment
	* containing the nodes of the parsed XML.
	*@param doc Document
	*@param fragment String
	*/
	private DocumentFragment parseXml(Document doc, String fragment) {
		DocumentFragment docfrag = null;
		// Wrap the fragment in an arbitrary element
		fragment = "<fragment>"+fragment+"</fragment>";
		try {
			// Create a DOM builder and parse the fragment
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			Document d = factory.newDocumentBuilder().parse(
				new InputSource(new StringReader(fragment)));

			// Import the nodes of the new document into doc so that they
			// will be compatible with doc
			Node node = doc.importNode(d.getDocumentElement(), true);

			// Create the document fragment node to hold the new nodes
			docfrag = doc.createDocumentFragment();

			// Move the nodes into the fragment
			while (node.hasChildNodes()) {
				docfrag.appendChild(node.removeChild(node.getFirstChild()));
			}
		} catch (Exception e) {
			// A parsing error occurred; the xml input is not valid
			e.printStackTrace(System.out);
		}

		// Return the fragment
		return docfrag;
	}

	/**********************************************************************************
	* Add attributes to this node
	*
	*@param elem Element
	*/
	protected void addXMLAttrs(Element elem){
		for (Enumeration e = xmlAttrTbl.keys(); e.hasMoreElements();)
		{
			String attr = (String)e.nextElement();
			String value = (String)xmlAttrTbl.get(attr);
			elem.setAttribute(attr,value);
		}
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
		StringBuffer debugSb)
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
		if (etype==null){  // just create element and its children, can't be required if no type or attr
			if (parent ==null){ // create the root
				Element root = (Element) document.createElement(nodeName);
				addXMLAttrs(root);
				document.appendChild(root);
				for (int c=0; c<childVct.size(); c++){
					SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
					childElem.addElements(dbCurrent,list, document,root,debugSb);
				}
			}else{ // create a node with no content
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				parent.appendChild(elem);
				for (int c=0; c<childVct.size(); c++){
					SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
					childElem.addElements(dbCurrent,list, document,elem,debugSb);
				}
			}
		}else{
			// get all entitys of etype
			EntityGroup egrp = null;
			if (isRoot) {
				egrp = list.getParentEntityGroup();
			} else {
				egrp = list.getEntityGroup(etype);
			}
			if (egrp==null){
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				parent.appendChild(elem);
				elem.appendChild(document.createTextNode("Error: "+etype+" not found in extract!"));

				if(isReq){
					throw new IOException(nodeName+" is required but "+etype+" is not in extract");
				}
				// add any children
				for (int c=0; c<childVct.size(); c++){
					SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
					childElem.addElements(dbCurrent,list, document,elem,debugSb);
				}
			}else{
				// get list of entities to look at, filtering may have been done
				Vector entityVct = getEntities(egrp);
				if (entityVct.size()==0){	// create an empty node and children
					Element elem = (Element) document.createElement(nodeName);
					addXMLAttrs(elem);
					parent.appendChild(elem);
					if (attrCode!=null){ // a value is expected, prevent a normal empty tag, OIDH cant handle it
						elem.appendChild(document.createTextNode(CHEAT));
					}
					if(isReq){
						throw new IOException(nodeName+" is required but "+etype+" is not in the data");
					}
					// add any children
					for (int c=0; c<childVct.size(); c++){
						SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,elem,debugSb);
					}
				}

				for(int i=0; i<entityVct.size(); i++){
					EntityItem item = (EntityItem)entityVct.elementAt(i);
					// create one element for each entity, all will be at same level
					Element elem = (Element) document.createElement(nodeName);
					addXMLAttrs(elem);
					parent.appendChild(elem);
					Node contentElem = getContentNode(document, item, parent);
					if (contentElem!=null){
						elem.appendChild(contentElem);
					}
					// add any children
					for (int c=0; c<childVct.size(); c++){
						SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
						childElem.addElements(dbCurrent,list, document,elem,debugSb);
					}
				}
				entityVct.clear();
			}
		}
	}

	/**********************************************************************************
	* Create a node for this element add to the parent using the set of items passed in
	*
	*@param itemVct Vector of EntityItem
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param debugSb StringBuffer for debug output
	*/
	protected void addGEOElements(Vector itemVct, Document document, Element parent,StringBuffer debugSb)
	throws COM.ibm.eannounce.objects.EANBusinessRuleException,
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException,
		IOException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{
		// itemvct is AVAIL or GEODATE called from SAPLGEOElem..
		// this method is not abstract because all classes would need it then
		throw new IOException("SAPLElem addElements(Vector..) needs to be overridden by derived class");
		/*if (itemVct !=null && itemVct.size()>0){
			for (int i=0; i<itemVct.size(); i++){
				EntityItem item = (EntityItem)itemVct.elementAt(i);
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				parent.appendChild(elem);
				Node contentElem = getContentNode(document, item,parent);
				if (contentElem!=null){
					elem.appendChild(contentElem);
				}
			}
		}else{
			debugSb.append("SAPLElem: No "+etype+" passed in for node:"+nodeName+NEWLINE);
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			parent.appendChild(elem);
		}*/
	}

	/**********************************************************************************
	* Check to see if this node has a value
	*
	*@param document Document
	*@param item EntityItem
	*@param parent Element
	*/
	protected boolean hasNodeValueForNLS(EntityItem item)
	{
		String attrCodes[] = PokUtils.convertToArray(attrCode); // if more than one, concatenate them
		StringBuffer sbb = new StringBuffer();

		EntityGroup egrp = item.getEntityGroup();
		EntityList list = egrp.getEntityList();
		for(int a=0; a<attrCodes.length; a++){
			String code = attrCodes[a];
			EANMetaAttribute metaAttr = egrp.getMetaAttribute(code);
			if (metaAttr!=null) {  // meta exists for this attribute
				Profile profile = list.getProfile();
				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				EANAttribute att = item.getAttribute(code);
				if (att instanceof EANTextAttribute){
					NLSItem nlsitem = profile.getReadLanguage();
					int nlsid = nlsitem.getNLSID();
					//true if information for the given NLSID is contained in the Text data
					if (((EANTextAttribute)att).containsNLS(nlsid)) {
						sbb.append(att.toString());
					} // end attr has this language
				}else{
					sbb.append(PokUtils.getAttributeValue(item, code,", ", "", false));
				}
			}
		}
		return (sbb.length()>0);
	}

	/**********************************************************************************
	* Check if this node will have any values or its children will, this is only needed from
	* SAPNLSElem node generation
	*
	*@param list EntityList
	*@param debugSb StringBuffer for debug output
	*/
	protected boolean hasNodeValueForNLS(EntityList list, StringBuffer debugSb)
	{
		boolean hasvalue=false;
		if (etype==null){  // just check its children
			for (int c=0; c<childVct.size() && !hasvalue; c++){
				SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
				hasvalue =childElem.hasNodeValueForNLS(list, debugSb);
			}
		}else{
			// get all entitys of etype
			EntityGroup egrp = null;
			if (isRoot) {
				egrp = list.getParentEntityGroup();
			} else {
				egrp = list.getEntityGroup(etype);
			}
			if (egrp!=null){
				// get list of entities to look at, filtering may have been done
				Vector entityVct = getEntities(egrp);
				for(int i=0; i<entityVct.size() && !hasvalue; i++){
					EntityItem item = (EntityItem)entityVct.elementAt(i);
					hasvalue = hasNodeValueForNLS(item);
					if (hasvalue){
						break;
					}
					// check any children
					for (int c=0; c<childVct.size() && !hasvalue; c++){
						SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
						hasvalue =childElem.hasNodeValueForNLS(list, debugSb);
					}
				}
				entityVct.clear();
			}
		}
		return hasvalue;
	}

	/**********************************************************************************
	* Get the content node for this attribute(s), if this is a F (multiflag) then
	* create one parent and node for each value
	*
	*@param document Document
	*@param item EntityItem
	*@param parent Element
	*/
	protected Node getContentNode(Document document, EntityItem item, Element parent)
	throws IOException
	{
		String attrCodes[] = PokUtils.convertToArray(attrCode); // if more than one, concatenate them

		EntityGroup egrp = item.getEntityGroup();
		EntityList list = egrp.getEntityList();
		Node contentElem = null;
		StringBuffer sbb = new StringBuffer();
		for(int a=0; a<attrCodes.length; a++){
			String value="";
			String code = attrCodes[a];
			EANMetaAttribute metaAttr = egrp.getMetaAttribute(code);
			if (metaAttr==null) {
				if(isReq){
					throw new IOException(nodeName+" is required but "+
						code+" is not in "+item.getEntityType()+" META data");
				}

				value= "Error: Attribute "+code+" not found in "+
					item.getEntityType()+" META data.";
				sbb.append(value);
			}else{ // meta exists for this attribute
				Profile profile = list.getProfile();
				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				EANAttribute att = item.getAttribute(code);
				if (att instanceof EANTextAttribute){
					NLSItem nlsitem = profile.getReadLanguage();
					int nlsid = nlsitem.getNLSID();
					//true if information for the given NLSID is contained in the Text data
					if (((EANTextAttribute)att).containsNLS(nlsid)) {
						value = att.toString();
					} // end attr has this language
					else{
						value = CHEAT;
					}
				}else{
					value = PokUtils.getAttributeValue(item, code,", ", CHEAT, false);
				}

				if(isReq && value.equals(CHEAT)){
					throw new IOException(nodeName+" is required but "+
						code+" is not set in "+item.getKey());
				}
				if (metaAttr.getAttributeType().equals("X")){ // XML attribute
					// xml attr must be parsed and added to doc, they will not be concatenated
					// Create a fragment
					contentElem = parseXml(document, value);
				}else{
					if(metaAttr.getAttributeType().equals("U") && attrSrc == FLAGVAL){ //Unique Flag and flagcode needed
            			EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
						if (fAtt!=null && fAtt.toString().length()>0){
							// Get the selected Flag code
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i = 0; i < mfArray.length; i++){
								// get selection
								if (mfArray[i].isSelected()){
									sbb.append(mfArray[i].getFlagCode());
									break;
								}  // metaflag is selected
							}// end of flagcodes
						}else{ //OIDH workaround
							sbb.append(CHEAT);
						}
					}else if(metaAttr.getAttributeType().equals("F")){ //MultiFlagAttribute
						Element grandparent = (Element)parent.getParentNode();

            			// get countrylist attr, it is F
            			EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
						if (fAtt!=null && fAtt.toString().length()>0){
							Vector selectedVct = new Vector(1);
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i = 0; i < mfArray.length; i++){
								// get selection
								if (mfArray[i].isSelected()){
									// may need to get flagcode instead of flag value here
									if (attrSrc == FLAGVAL){
										selectedVct.addElement(mfArray[i].getFlagCode());
									}else{
										selectedVct.addElement(mfArray[i].toString());
									}
								}  // metaflag is selected
							}// end of flagcodes
							// add all but last element as new parent nodes
							for (int x=0; x<selectedVct.size()-1; x++){
            					Element newParent = (Element) document.createElement(parent.getTagName());
            					Element newElem = (Element) document.createElement(nodeName);
								grandparent.insertBefore(newParent, parent);
								newParent.appendChild(newElem);
								newElem.appendChild(document.createTextNode(selectedVct.elementAt(x).toString()));
							}
							// return last to caller to add to parent
 							sbb.append(selectedVct.lastElement().toString());
 							selectedVct.clear();
						}else{ //OIDH workaround
							sbb.append(CHEAT);
						}
					}
					else{
						sbb.append(value);
					}
				} // not an X type attr
			} // end meta ok
		} // end each attrcode
		if (contentElem==null && sbb.length()>0){
			contentElem = document.createTextNode(sbb.toString());
		}
		return contentElem;
	}

    /**********************************************************************************
    * Get the countrycodes from the GENERALAREA
    * Use the instance of COUNTRYLIST via an Association to GENERALAREA where GENERALAREA.GENAREATYPE = 2452.
    *
    *@param list EntityList
    *@param itemVct Vector of EntityItem to use for FROM association
    *@param assocName String association name
    *@param debugSb StringBuffer used for debug output
    */
	protected String getCountryCodes(EntityList list, Vector itemVct, String assocName, StringBuffer debugSb){
		StringBuffer sb = new StringBuffer();
		Vector ctryCodeVct = new Vector(1);
		// the set of FROM entities may be filtered.. some only want countrylist from a subset of AVAILs
		for (int i=0; i<itemVct.size(); i++){
			EntityItem item = (EntityItem)itemVct.elementAt(i);
			Vector genareaVector = PokUtils.getAllLinkedEntities(item, assocName, "GENERALAREA");
			//debugSb.append("SAPLElem:getCountryCodes: "+item.getKey()+" has "+genareaVector.size()+" GENERALAREA thru "+assocName+NEWLINE);
			// find those of GENAREATYPE = 2452.
			Vector ctryVector = PokUtils.getEntitiesWithMatchedAttr(genareaVector, "GENAREATYPE", "2452");
			debugSb.append("SAPLElem:getCountryCodes: "+item.getKey()+" has "+ctryVector.size()+" GENERALAREA.GENAREATYPE = 2452 "+NEWLINE);
			for (int ii=0; ii<ctryVector.size(); ii++){
				EntityItem genAreaItem = (EntityItem) ctryVector.elementAt(ii);
				String ctryCode = PokUtils.getAttributeValue(genAreaItem, "GENAREACODE",", ", "", false);
				// avoid duplicates
				if (!ctryCodeVct.contains(ctryCode)){
					ctryCodeVct.add(ctryCode);
				}
			}
		}
		Collections.sort(ctryCodeVct); // sort alphabetically

		for (int i=0; i<ctryCodeVct.size(); i++){
			String ctryCode = ctryCodeVct.elementAt(i).toString();
			sb.append("/"+ctryCode);
		}
		if (sb.length()==0){
			sb.append("/ ");
		}

		return sb.toString();
	}
}
