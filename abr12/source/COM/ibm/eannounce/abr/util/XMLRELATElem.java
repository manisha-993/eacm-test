package COM.ibm.eannounce.abr.util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;

public class XMLRELATElem extends XMLElem {

	private String attrcode1;
	private String attrcode2;


	public XMLRELATElem(String nname, String attcode1, String attcode2) {
		super(nname);
		attrcode1 = attcode1;
		attrcode2 = attcode2;
	}


	/**********************************************************************************
	 * Create a node for this element and add to the parent and any children this
	 * node has
	 *
	 * @param dbCurrent Database
	 * @param list      EntityList
	 * @param document  Document needed to create nodes
	 * @param parent    Element node to add this node too
	 * @param debugSb   StringBuffer for debug output
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
		
		if ("OFFERING_ID".equals(nodeName)) {

			EntityItem modItem = getModelEntityFromTmf(item);
			ABRUtil.append(debugSb, "SVCMOD Entity" + modItem.getKey()+ NEWLINE);
			String attvalue1 = PokUtils.getAttributeValue(modItem, attrcode1, ", ", CHEAT, false);
			String attvalue2 = PokUtils.getAttributeValue(modItem, attrcode2, ", ", CHEAT, false);

			// use value from constructor
			elem.appendChild(document.createTextNode(attvalue1 + attvalue2));
			parent.appendChild(elem);
		} 
		// add any children
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, table, document, elem, parentItem, debugSb);

		}

	}

	private EntityItem getModelEntityFromTmf(EntityItem tmfEntity) {
		EntityItem modelItem = null;
		Vector linkVct = tmfEntity.getDownLink();
		if (linkVct != null && linkVct.size() > 0) {
			for (int k = 0; k < linkVct.size(); k++) {
				EntityItem item = (EntityItem) linkVct.get(k);
				if ("SVCMOD".equals(item.getEntityType())) {
					modelItem = item;
					break;
				}
			}
		}
		return modelItem;
	}
}
