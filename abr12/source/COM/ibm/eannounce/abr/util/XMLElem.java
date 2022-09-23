// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;
import com.ibm.transform.oim.eacm.diff.*;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

/**********************************************************************************
* Base Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a particular entity.
*
Attribute Types
1.	FLAG (F or U)
These are allowed value.

Description Class is a foreign key to the Long Description. Description Class is not Language (NLSID) sensitive whereas Long Description is. The spreadsheet identifies if the Flag Description Class should be passed instead of the Long Description.

Multi-value Flag (F), values are either add or delete. If the value was add, then this is "Update". If the value was delete, thin this is "Delete".

Unique or single value Flags (U) will be passed if any part of the element is changed.

2.	TEXT (T)

A maximum of 254 bytes and may be NLSID sensitive.

3.	LONG TEXT (L)

A maximum of 32K bytes, may be NLSID sensitive, and may contain the new line (paragraph) character.

4.	XML (X)

A sub-type of Long Text (L) and includes XML tags that are well formed HTML tags.

*
*/
// $Log: XMLElem.java,v $
// Revision 1.19  2020/02/05 13:29:08  xujianbo
// Add debug info to investigate   performance issue
//
// Revision 1.18  2015/02/04 14:52:44  wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.17  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.16  2014/12/12 16:20:10  wangyul
// fix the OutOfMemoryError of the stringbuffer
//
// Revision 1.15  2013/08/16 05:11:10  wangyulo
// fix the issue RCQ00222829 for the BHCATLGOR which change the CATLGOR to BHCATLGOR
//
// Revision 1.14  2012/06/08 07:32:50  wangyulo
// Build Request for the wwprt pricexml of the outbound price - defect 737778
//
// Revision 1.13  2011/04/11 12:20:38  guobin
// not convert HTML special characters for the attribute of the tag
//
// Revision 1.12  2011/04/07 13:36:14  guobin
// defect 463888 for HTML special characters
//
// Revision 1.11  2010/12/10 09:10:21  guobin
// recover the prior version
//
// Revision 1.8  2010/12/06 13:10:53  guobin
// Add mutiuse  in hasChanges() method
//
// Revision 1.7  2010/12/06 08:46:47  guobin
// change the method of hasChanges to fix the connect with multiUse entity
//
// Revision 1.6  2010/12/03 13:24:55  guobin
// add the hasChanges method to fix the multiUse entity
//
// Revision 1.5  2010/02/05 11:55:11  wendy
// use path to find changes in GroupElem
//
// Revision 1.4  2008/05/28 13:43:22  wendy
// Support flagcode for S type attributes
//
// Revision 1.3  2008/04/29 14:25:55  wendy
// Support short desc
//
// Revision 1.2  2008/04/20 00:41:46  wendy
// Multiflag behavior cant duplicate parent if grandparent is the document
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

public class XMLElem
{
	//add STATUS_FINAL and BHCOUNTRYLIST from the RCQ00222829 change  CATLGOR to BHCATLGOR
	// base on the doc BH FS ABR XML System Feed Mapping 20130214.doc
	public static final String STATUS_FINAL = "0020";
	public static final String BHCOUNTRYLIST = "COUNTRYLIST";
    public static final int ATTRVAL = 0; // get value from attribute - long description
    public static final int FLAGVAL = 1; // get value from flag code
    public static final int SHORTDESC = 2; // get value from attribute - short description
    public static final String UPDATE_ACTIVITY = "Update";
    public static final String DELETE_ACTIVITY = "Delete";

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    public static final String CHEAT = "@@";
    public static final String NEWCHEAT = "@amp;";

    protected final static String AVAIL_ORDER[] = new String[]{"146","143","149","AVT220"};

    protected boolean isReq=false;
    protected String nodeName;
    protected String attrCode =null; // could be attr1|attr2 for concatenated attrs
    protected Vector childVct = new Vector(1);
    protected Hashtable xmlAttrTbl = new Hashtable();
    protected int attrSrc = ATTRVAL;

    private static int TEXT_LIMIT = 254;
    protected static int getTextLimit() { return TEXT_LIMIT; } // only derived classes need to access this
    public static void setTextLimit(int t) { TEXT_LIMIT = t; } // allow override
    private static int LONGTEXT_LIMIT = 32000;
    protected static int getLongTextLimit() { return LONGTEXT_LIMIT; } // only derived classes need to access this
    public static void setLongTextLimit(int t) { LONGTEXT_LIMIT = t; } // allow override

    /**********************************************************************************
    * Constructor - used when element does not have text nodes and is not root
    *
    *@param nname String with name of node to be created
    */
    public XMLElem(String nname)
    {
        this(nname,null,ATTRVAL);
    }

    /**********************************************************************************
    * Constructor - used when element has a text node
    *
    *@param nname String with name of node to be created
    *@param code String with attribute code
    */
    public XMLElem(String nname, String code)
    {
        this(nname,code,ATTRVAL);
    }
    /**********************************************************************************
    * Constructor - used when element has a text node
    *
    *@param nname String with name of node to be created
    *@param code String with attribute code
    *@param src int for flag attributes
    */
    public XMLElem(String nname, String code, int src)
    {
        nodeName = nname;
        attrCode = code;
        attrSrc = src;
    }

    /**********************************************************************************
    * string rep
    *
    *@return String
    */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Node:"+nodeName+" attr:"+attrCode+" req:"+isReq+" childCnt:"+childVct.size());
        for(int i=0; i<childVct.size(); i++){
            sb.append(NEWLINE+"  "+childVct.elementAt(i).toString());
        }
        return sb.toString();
    }

    /**********************************************************************************
    * Add a child element to this 'node'
    *
    *@param sap XMLElem with node to be added
    */
    public void addChild(XMLElem sap) {childVct.add(sap);}

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
        id = 0;
        while(id != -1)
        {
            id = outputXml.toString().indexOf(NEWCHEAT,id);
            if (id != -1) {
                outputXml.replace(id, id+NEWCHEAT.length(),"&");
            }
        }
        return outputXml.toString();
    }
    
    /**********************************************************************************
     * OIDH can't handle a standard empty XML tag <info/>, must have <info></info>
     * cheat is to always have a value so the document creates the open and close tags
     * then remove the cheat value
     *
     *@param xmltoFix String with xml that needs cheat value removed
     *@return String with cheat values removed
     */
     public static String removeQuota(String xmltoFix)
     {
    	 String QuoteLT ="&lt;";
    	 String QuoteGT ="&gt;";
         StringBuffer outputXml = new StringBuffer(xmltoFix);
         int id = 0;
         while(id != -1)
         {
             id = outputXml.toString().indexOf(QuoteLT,id);
             if (id != -1) {
                 outputXml.replace(id, id+QuoteLT.length(),"<");
             }
         }
         id = 0;
         while(id != -1)
         {
             id = outputXml.toString().indexOf(QuoteGT,id);
             if (id != -1) {
                 outputXml.replace(id, id+QuoteGT.length(),">");
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
    * Get entities to output, overridden by derived classes when filtering is needed
    *
    *@param vct Vector of DiffEntities
    */
    protected Vector getEntities(Vector vct)
    {
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
        if(nodeName.contains("WARR")){
            D.ebug(D.EBUG_ERR,"entityid="+parentItem.getEntityID());
            if(nodeName.contains("WARRDESC")) {
                D.ebug(D.EBUG_ERR, "attrvalue=" + PokUtils.getAttributeValue(parentItem, "INVNAME", "", ""));
            }
            else {
                D.ebug(D.EBUG_ERR, "attrvalue=" + PokUtils.getAttributeValue(parentItem, nodeName, "", ""));
            }

        }
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);

		if (parent ==null){ // create the root
			document.appendChild(elem);
		}else{ // create a node
			parent.appendChild(elem);
		}

		Node contentElem = getContentNode(document, parentItem, parent,debugSb);
		if (contentElem!=null){
			elem.appendChild(contentElem);
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
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
        if(nodeName.contains("MKTGNAME")) {
            D.ebug(D.EBUG_ERR, "attrvalue=" + PokUtils.getAttributeValue(parentItem.getCurrentEntityItem(), "MKTGNAME", "", ""));
            debugSb.append("<!-- " + "entityid="+parentItem.getEntityID() + " -->" + NEWLINE);
        }
        if(nodeName.contains("WARR")){
            D.ebug(D.EBUG_ERR,"entityid="+parentItem.getEntityID());
            debugSb.append("<!-- " + "entityid="+parentItem.getEntityID() + " -->" + NEWLINE);
            if(nodeName.contains("WARRDESC")) {
                D.ebug(D.EBUG_ERR, "attrvalue=" + PokUtils.getAttributeValue(parentItem.getCurrentEntityItem(), "INVNAME", "", ""));
            }
            else {
                D.ebug(D.EBUG_ERR, "attrvalue=" + PokUtils.getAttributeValue(parentItem.getCurrentEntityItem(), nodeName, "", ""));
            }
        }
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		if (parent ==null){ // create the root
			document.appendChild(elem);
		}else{ // create a node
			parent.appendChild(elem);
		}

		Node contentElem = getContentNode(document, parentItem, parent,debugSb);
		if (contentElem!=null){
			elem.appendChild(contentElem);
		}
		// add any children
		//StringBuffer debugSb1 = null;
		for (int c=0; c<childVct.size(); c++){
			XMLElem childElem = (XMLElem)childVct.elementAt(c);
			childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
			
		}

		if (!elem.hasChildNodes()){
			// a value is expected, prevent a normal empty tag, OIDH cant handle it
			elem.appendChild(document.createTextNode(CHEAT));
		}
    }

    /**********************************************************************************
    * Check to see if this node has a value for this NLS, must be a Text attribute
    *
    *@param item EntityItem
    *@param debugSb StringBuffer
    */
    protected boolean hasNodeValueForNLS(EntityItem item, StringBuffer debugSb)
    {
		boolean hasValue=false;
		if (attrCode==null){  // just check its children
			for (int c=0; c<childVct.size() && !hasValue; c++){
				XMLElem childElem = (XMLElem)childVct.elementAt(c);
				if (childElem.hasNodeValueForNLS(item, debugSb)){
					hasValue = true; // one valid one is enough
					break;
				}
			}
		}else{
			String attrCodes[] = PokUtils.convertToArray(attrCode); // if more than one, concatenate them
			Profile profile = item.getProfile();
			NLSItem nlsitem = profile.getReadLanguage();

			for(int a=0; a<attrCodes.length; a++){
				String code = attrCodes[a];
				if (code.equals("ENTITYTYPE") || code.equals("ENTITYID") || code.equals("NLSID") ||
					code.equals("ENTITY1ID") || code.equals("ENTITY2ID")){
					continue;
				}

				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				EANAttribute att = item.getAttribute(code);
				if (att instanceof EANTextAttribute){
					int nlsid = nlsitem.getNLSID();
					//true if information for the given NLSID is contained in the Text data
					if (((EANTextAttribute)att).containsNLS(nlsid)) {
						if (att.toString().length()>0){
							hasValue = true;
							break;
						}
					} // end attr has this language
				}
			}
			ABRUtil.append(debugSb,"XMLElem.hasNodeValueForNLS node:"+nodeName+" "+item.getKey()+" ReadLanguage "+nlsitem+
				" attr "+attrCode+" hasValue:"+hasValue+NEWLINE);
		}
        return hasValue;
    }

    /**********************************************************************************
    * Check to see if this node has a changed value for this NLS, must be a Text attribute
    *
    *@param diffitem DiffEntity
    *@param debugSb StringBuffer
    */
    protected boolean hasNodeValueChgForNLS(DiffEntity diffitem, StringBuffer debugSb)
    {
		boolean hasValue=false;
		if (attrCode==null){  // just check its children
			for (int c=0; c<childVct.size() && !hasValue; c++){
				XMLElem childElem = (XMLElem)childVct.elementAt(c);
				if (childElem.hasNodeValueChgForNLS(diffitem, debugSb)){
					hasValue = true; // one valid one is enough
					break;
				}
			}
		}else{
			String attrCodes[] = PokUtils.convertToArray(attrCode); // if more than one, concatenate them
			// check at both times if one existed or not
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem previtem = diffitem.getPriorEntityItem();
			NLSItem nlsitem = null;
			if (!diffitem.isDeleted()){
				nlsitem = curritem.getProfile().getReadLanguage();
			}else{
				nlsitem = previtem.getProfile().getReadLanguage();
			}

			for(int a=0; a<attrCodes.length; a++){
				String code = attrCodes[a];
				if (code.equals("ENTITYTYPE") || code.equals("ENTITYID") || code.equals("NLSID") ||
					code.equals("ENTITY1ID") || code.equals("ENTITY2ID")){
					continue;
				}

				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				String currVal = "";
				String prevVal = "";
				if (curritem != null){
					EANAttribute att = curritem.getAttribute(code);
					if (att instanceof EANTextAttribute){
						int nlsid = nlsitem.getNLSID();
						//true if information for the given NLSID is contained in the Text data
						if (((EANTextAttribute)att).containsNLS(nlsid)) {
							currVal = att.toString();
						} // end attr has this language
					}
				}
				if (previtem != null){
					EANAttribute att = previtem.getAttribute(code);
					if (att instanceof EANTextAttribute){
						int nlsid = nlsitem.getNLSID();
						//true if information for the given NLSID is contained in the Text data
						if (((EANTextAttribute)att).containsNLS(nlsid)) {
							prevVal = att.toString();
						} // end attr has this language
					}
				}
				ABRUtil.append(debugSb,"XMLElem.hasNodeValueChgForNLS node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage "+nlsitem+
					" attr "+code+"\n currVal: "+currVal+"\n prevVal: "+prevVal+NEWLINE);

				if (!currVal.equals(prevVal)){ // we only care if there was a change
					hasValue = true;
					break;
				}

			}
		}
        return hasValue;
    }

    /**********************************************************************************
    * Get the content node for this attribute(s), if this is a F (multiflag) then
    * create one parent and node for each value
    *
    *@param document Document
    *@param diffitem DiffEntity
    *@param parent Element
    */
    protected Node getContentNode(Document document, DiffEntity diffitem, Element parent,
    	StringBuffer debugSb)
    throws IOException
    {
        if (attrCode==null || diffitem==null){
			return null;
		}

        EntityItem curritem = diffitem.getCurrentEntityItem();
        EntityItem prioritem = diffitem.getPriorEntityItem();
        EntityItem item = curritem;
        if (diffitem.isDeleted()){
            item = prioritem;
        }

		// nothing to compare, just output values
		return getContentNode(document, item, parent, debugSb);
    }

    /**********************************************************************************
    * Get the content node for this attribute(s), if this is a F (multiflag) then
    * create one parent and node for each value
    *
    *@param document Document
    *@param item EntityItem
    *@param parent Element
    */
    protected Node getContentNode(Document document, EntityItem item, Element parent,
    	StringBuffer debugSb)
    throws IOException
    {
        if (attrCode==null || item==null){
			return null;
		}

		if (attrCode.equals("ENTITYTYPE")){
			// use value from entity id
			return document.createTextNode(item.getEntityType());
		}

		if (attrCode.equals("ENTITYID")){
			// use value from entity id
			return document.createTextNode(""+item.getEntityID());
		}
		if (attrCode.equals("ENTITY1ID")){
			// use value from entity1 id
			String value = CHEAT;
			if (item.hasUpLinks()){
				value = ""+item.getUpLink(0).getEntityID();
				ABRUtil.append(debugSb,"XMLElem getting "+attrCode+" from "+item.getUpLink(0).getKey()+NEWLINE);
			}
			return document.createTextNode(value);
		}
		if (attrCode.equals("ENTITY2ID")){
			// use value from entity2 id
			String value = CHEAT;
			if (item.hasDownLinks()){
				value = ""+item.getDownLink(0).getEntityID();
				ABRUtil.append(debugSb,"XMLElem getting "+attrCode+" from "+item.getDownLink(0).getKey()+NEWLINE);
			}
			return document.createTextNode(value);
		}

		if (attrCode.equals("NLSID")){
			// use value from profile.readlanguage
			return document.createTextNode(""+item.getProfile().getReadLanguage().getNLSID());
		}

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
                        value = att.toString().trim();
                    } // end attr has this language
                    else{
                        value = CHEAT;
                    }
                }else{
                    value = PokUtils.getAttributeValue(item, code,", ", CHEAT, false).trim();
                }

                if(isReq && value.equals(CHEAT)){
                    throw new IOException(nodeName+" is required but "+
                        code+" is not set in "+item.getKey());
                }
                if (metaAttr.getAttributeType().equals("X")){ // XML attribute
                    // xml attr must be parsed and added to doc, they will not be concatenated
                    // Create a fragment
                    if (!value.equals(CHEAT)){
                    	//contentElem = parseXml(document, value);
                    	contentElem = document.createTextNode(value);
					}else{
						sbb.append(CHEAT);
					}
                }else{
                    if(metaAttr.getAttributeType().equals("U") || metaAttr.getAttributeType().equals("S")){
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
						if (fAtt!=null && fAtt.toString().length()>0){
							// Get the selected Flag code
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i = 0; i < mfArray.length; i++){
								// get selection
								if (mfArray[i].isSelected()){
									if (attrSrc == FLAGVAL){
										sbb.append(mfArray[i].getFlagCode());
									}else if (attrSrc == SHORTDESC){
										sbb.append(mfArray[i].getShortDescription());
									}else{
										sbb.append(mfArray[i].toString());
									}
									break;
								}  // metaflag is selected
							}// end of flagcodes
						}else{ //OIDH workaround
							sbb.append(CHEAT);
						}
                    }else if(metaAttr.getAttributeType().equals("F")){ //MultiFlagAttribute
                        // get attr, it is F
                        EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
                        if (fAtt!=null && fAtt.toString().length()>0){
                            Vector selectedVct = new Vector(1);
                            // Get the selected Flag codes.
                            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                            for (int i = 0; i < mfArray.length; i++){
                                // get selection
                                if (mfArray[i].isSelected())
                                {
                                    // may need to get flagcode instead of flag value here
                                    if (attrSrc == FLAGVAL){
                                        selectedVct.addElement(mfArray[i].getFlagCode());
                                    } else if (attrSrc == SHORTDESC){
										selectedVct.addElement(mfArray[i].getShortDescription());
									} else{
                                        selectedVct.addElement(mfArray[i].toString());
                                    }
                                }  // metaflag is selected
                            }// end of flagcodes
                            // add all but last element as new parent nodes
                            for (int x=0; x<selectedVct.size()-1; x++){
                               // Element newParent = (Element) document.createElement(parent.getTagName());
                                Element newElem = (Element) document.createElement(nodeName);
                                //grandparent.insertBefore(newParent, parent);
                                //newParent.appendChild(newElem);
                                parent.appendChild(newElem);
                                newElem.appendChild(document.createTextNode(selectedVct.elementAt(x).toString()));
                            }
                            // return last to caller to add to parent
                            sbb.append(selectedVct.lastElement().toString());
                            selectedVct.clear();
                        }else{ //OIDH workaround
                            sbb.append(CHEAT);
                        }
                    }
                    else{ // must be T or L - check length
                    	if(metaAttr.getAttributeType().equals("T")){ //TextAttribute
                    		if (value.length()>getTextLimit()){
								value = value.substring(0,getTextLimit());
								ABRUtil.append(debugSb,"XMLElem.getContentNode node:"+nodeName+" "+item.getKey()+
									" value was truncated for attr "+code+NEWLINE);
							}
						}else if(metaAttr.getAttributeType().equals("L")){ //LongText
                    		if (value.length()>getLongTextLimit()){
								value = value.substring(0,getLongTextLimit());
								ABRUtil.append(debugSb,"XMLElem.getContentNode node:"+nodeName+" "+item.getKey()+
									" value was truncated for attr "+code+NEWLINE);
							}
						}

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
    * Check to see if there are any changes in this node or in the children
    *
    *@param table Hashtable
    *@param diffitem DiffEntity
    *@param debugSb StringBuffer
    * @return
    */
    protected boolean hasChanges(Hashtable table, DiffEntity diffitem, StringBuffer debugSb)
    {
		boolean changed=false;
		//ABRUtil.append(debugSb,"XMLElem.hasChanges entered for node:"+nodeName+" "+diffitem.getKey()+NEWLINE);
		if (attrCode==null){  // just check its children
			for (int c=0; c<childVct.size() && !changed; c++){
				XMLElem childElem = (XMLElem)childVct.elementAt(c);
				if (childElem.hasChanges(table, diffitem, debugSb)){
					changed = true; // one change one is enough
					break;
				}
			}
		}else{
			String attrCodes[] = PokUtils.convertToArray(attrCode); // may be more than one
			// check at both times if one existed or not
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem previtem = diffitem.getPriorEntityItem();
			NLSItem nlsitem = null;
			if (!diffitem.isDeleted()){
				nlsitem = curritem.getProfile().getReadLanguage();
			}else{
				nlsitem = previtem.getProfile().getReadLanguage();
			}

			for(int a=0; a<attrCodes.length; a++){
				String code = attrCodes[a];
				if (code.equals("ENTITYTYPE") || code.equals("ENTITYID") || code.equals("NLSID")){
					continue;
				}

				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				String currVal = "";
				String prevVal = "";
				if (curritem != null){
					EANAttribute att = curritem.getAttribute(code);
					if (att instanceof EANTextAttribute){
						int nlsid = nlsitem.getNLSID();
						//true if information for the given NLSID is contained in the Text data
						if (((EANTextAttribute)att).containsNLS(nlsid)) {
							currVal = att.toString();
						} // end attr has this language
					}else{
						currVal = PokUtils.getAttributeValue(curritem, code,", ", "", false);
					}
				}
				if (previtem != null){
					EANAttribute att = previtem.getAttribute(code);
					if (att instanceof EANTextAttribute){
						int nlsid = nlsitem.getNLSID();
						//true if information for the given NLSID is contained in the Text data
						if (((EANTextAttribute)att).containsNLS(nlsid)) {
							prevVal = att.toString();
						} // end attr has this language
					}else{
						prevVal = PokUtils.getAttributeValue(previtem, code,", ", "", false);
					}
				}
				ABRUtil.append(debugSb,"XMLElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage "+nlsitem+
					" attr "+code+"\n currVal: "+currVal+"\n prevVal: "+prevVal+NEWLINE);

				if (!currVal.equals(prevVal)){ // we only care if there was a change
					changed = true;
					break;
				}
			}
		}

		//ABRUtil.append(debugSb,"XMLElem.hasChanges exiting for node:"+nodeName+" "+diffitem.getKey()+NEWLINE);
        return changed;
    }  

}
