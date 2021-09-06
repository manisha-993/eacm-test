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

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
 * Base Class used to hold info and structure to be generated for the xml feed
 * for abrs. This acts on a particular entity.
 *
 * Attribute Types 1. FLAG (F or U) These are allowed value.
 * 
 * Description Class is a foreign key to the Long Description. Description Class
 * is not Language (NLSID) sensitive whereas Long Description is. The
 * spreadsheet identifies if the Flag Description Class should be passed instead
 * of the Long Description.
 * 
 * Multi-value Flag (F), values are either add or delete. If the value was add,
 * then this is "Update". If the value was delete, thin this is "Delete".
 * 
 * Unique or single value Flags (U) will be passed if any part of the element is
 * changed.
 * 
 * 2. TEXT (T)
 * 
 * A maximum of 254 bytes and may be NLSID sensitive.
 * 
 * 3. LONG TEXT (L)
 * 
 * A maximum of 32K bytes, may be NLSID sensitive, and may contain the new line
 * (paragraph) character.
 * 
 * 4. XML (X)
 * 
 * A sub-type of Long Text (L) and includes XML tags that are well formed HTML
 * tags.
 *
 * 
 */
// $Log: XMLTAXElem.java,v $
// Revision 1.1  2019/12/17 08:19:43  xujianbo
// Synchronize the codes between the old and new servers
//
// Revision 1.5  2019/11/26 09:01:06  xujianbo
// Recover code for stroy Svc Prof- Prdt Tax Clsf reqt (CHIS/TSS only)
//
// Revision 1.3  2019/11/19 08:31:25  xujianbo
// Add RFAGeo for those three countries- Development
// Svc Prof- Prdt Tax Clsf reqt (CHIS/TSS only)- Development
//
// Revision 1.2  2019/11/19 06:03:56  xujianbo
// Add RFAGeo for those three countries- Development
// Svc Prof- Prdt Tax Clsf reqt (CHIS/TSS only)- Development
//
// Revision 1.1  2019/11/18 09:54:34  xujianbo
// Add RFAGeo for those three countries- Development
// Svc Prof- Prdt Tax Clsf reqt (CHIS/TSS only)- Development
//
// Revision 1.18 2015/02/04 14:52:44 wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.17 2015/01/26 15:53:39 wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.16 2014/12/12 16:20:10 wangyul
// fix the OutOfMemoryError of the stringbuffer
//
// Revision 1.15 2013/08/16 05:11:10 wangyulo
// fix the issue RCQ00222829 for the BHCATLGOR which change the CATLGOR to
// BHCATLGOR
//
// Revision 1.14 2012/06/08 07:32:50 wangyulo
// Build Request for the wwprt pricexml of the outbound price - defect 737778
//
// Revision 1.13 2011/04/11 12:20:38 guobin
// not convert HTML special characters for the attribute of the tag
//
// Revision 1.12 2011/04/07 13:36:14 guobin
// defect 463888 for HTML special characters
//
// Revision 1.11 2010/12/10 09:10:21 guobin
// recover the prior version
//
// Revision 1.8 2010/12/06 13:10:53 guobin
// Add mutiuse in hasChanges() method
//
// Revision 1.7 2010/12/06 08:46:47 guobin
// change the method of hasChanges to fix the connect with multiUse entity
//
// Revision 1.6 2010/12/03 13:24:55 guobin
// add the hasChanges method to fix the multiUse entity
//
// Revision 1.5 2010/02/05 11:55:11 wendy
// use path to find changes in GroupElem
//
// Revision 1.4 2008/05/28 13:43:22 wendy
// Support flagcode for S type attributes
//
// Revision 1.3 2008/04/29 14:25:55 wendy
// Support short desc
//
// Revision 1.2 2008/04/20 00:41:46 wendy
// Multiflag behavior cant duplicate parent if grandparent is the document
//
// Revision 1.1 2008/04/17 19:37:53 wendy
// Init for
// - CQ00003539-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// - CQ00005096-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add
// Category MM and Images
// - CQ00005046-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Support CRAD in BHC
// - CQ00005045-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Upgrade/Conversion Support
// - CQ00006862-WI - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLMODELTAXElem extends XMLElem {
	public static Map map;
	static {
		map = new HashMap();
		//map.put("MSWH", "a");
		map.put("MAIN", "b");
		//map.put("CABL", "c");
		//map.put("PMG", "d");
		//map.put("MANL", "e");
		//map.put("MATM", "f");
		//map.put("PLA", "g");
		//map.put("ALP", "h");
		//map.put("CSW", "i");
		//map.put("EDUC", "j");
		//map.put("MNPM", "k");
		//map.put("***", "l");

	}

	/**********************************************************************************
	 * Constructor - used when element has a text node
	 *
	 * @param nname
	 *            String with name of node to be created
	 * @param code
	 *            String with attribute code
	 */
	public XMLMODELTAXElem(String nname, String code, int src) {
		super(nname, code, src);
	}

	/**********************************************************************************
	 * Create a node for this element and add to the parent and any children
	 * this node has
	 *
	 * @param dbCurrent
	 *            Database
	 * @param table
	 *            Hashtable of Vectors of DiffEntity
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param parentItem
	 *            DiffEntity - parent to use if path is specified in
	 *            XMLGroupElem, item to use otherwise
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent,
			DiffEntity parentItem, StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException, java.rmi.RemoteException, IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		if (parent == null) { // create the root
			document.appendChild(elem);
		} else { // create a node
			parent.appendChild(elem);
		}

		EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem prioritem = parentItem.getPriorEntityItem();
		EntityItem item = curritem;
		if (parentItem.isDeleted()) {
			item = prioritem;
		}
		EntityItem modItem = (EntityItem) item.getUpLink().get(0);
		
		//ABRUtil.append(debugSb, "up link="+modItem.getEntityID()+":"+modItem.getEntityType());

		//ABRUtil.append(debugSb, "prioritem="+item.getEntityID()+":"+item.getEntityType());
		NodeList nodeList = parent.getChildNodes();
		int l = nodeList.getLength();
		boolean needTransfer = false;
		
		String COFCAT = PokUtils.getAttributeValue(modItem, "COFCAT", "", "");
		
		//if(PokUtils.getAttributeFlagValue(root, ""))
		//ABRUtil.append(debugSb,"COFCAT =  "+COFCAT);
	
		//.append(debugSb,"root =  "+root.getEntityType());
		for (int i = 0; i < l; i++) {
			if(!COFCAT.equals("Hardware")){
				break;
			}
			String name = nodeList.item(i).getNodeName();
			if ("COUNTRYLIST".equals(name)) {

				Node countryList = nodeList.item(i);
				NodeList list = countryList.getChildNodes();
				for (int j = 0; j < list.getLength(); j++) {

					if (list.item(j).getNodeName().equals("COUNTRYELEMENT")) {
						Node element = list.item(j);
						NodeList fcList = element.getChildNodes();
						
						for (int k = 0; k < fcList.getLength(); k++) {
							if (fcList.item(k).getNodeName().equals("COUNTRY_FC")) {
								String value = fcList.item(k).getFirstChild().getNodeValue();
								if ("1464".equals(value)) {
									needTransfer = true;
								}

							}
						}
					}
				}

			}
		}

		Node contentElem = getContentNode(document, parentItem, parent, debugSb);

		if (contentElem != null) {
//			ABRUtil.append(debugSb,
//					"map.get(contentElem.getNodeValue()).toString():" + map.get(contentElem.getNodeValue()) + NEWLINE);

			//ABRUtil.append(debugSb, "map.get(contentElem.getNodeValue()).toString():" + map + NEWLINE);
			// ABRUtil.append(debugSb, "contentElem.getNodeValue():" +
			// contentElem.getNodeValue() + NEWLINE);
			if (needTransfer) {
				//ABRUtil.append(debugSb, "contentElem.getTextContent():" + contentElem.getNodeValue() + NEWLINE);
				/*if (contentElem.getNodeValue() == null || CHEAT.equals(contentElem.getNodeValue().trim())) {
					contentElem = document.createTextNode("1");
				} else {*/
					if (map.get(contentElem.getNodeValue()) != null){
						contentElem = document.createTextNode(map.get(contentElem.getNodeValue()).toString());
				}

			}
			// contentElem.getNodeValue()
			elem.appendChild(contentElem);
		}
		// add any children
		// StringBuffer debugSb1 = null;
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, table, document, elem, parentItem, debugSb);

		}

		if (!elem.hasChildNodes()) {
			// a value is expected, prevent a normal empty tag, OIDH cant handle
			// it
			elem.appendChild(document.createTextNode(CHEAT));
		}
	}
	
	public EntityItem getRoot(Hashtable table ,StringBuffer debugSb){
		/*Iterator iterator = table.entrySet().iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			ABRUtil.append(debugSb,"object =  "+object);
		}*/
		/*Vector tmp = (Vector) table.get("MODEL");
		DiffEntity diffitem = (DiffEntity) tmp.get(0);
		EntityItem theitem = diffitem.getCurrentEntityItem();				
		if (diffitem.isDeleted()) {
			theitem = diffitem.getPriorEntityItem();
		}
		return theitem;*/
		return null;
	}

}
