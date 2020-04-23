//Licensed Materials -- Property of IBM
//XMLINVNameElem.java
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


//$Log: XMLMktgInvElem.java,v $
//Revision 1.2  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.1  2014/01/07 13:09:56  guobin
//Fix CR BH FS ABR XML System Feed Mapping 20131106b.doc , LANGUAGE Element NLSID MarketName and Invoice Name extract from FEATURE if no NLSID 1 .
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.*;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/*******************************************************************************
 * Base Class used to hold info and structure to be generated for the xml feed
 * for abrs. This acts on a particular entity.Tag INVNAME is derived from
 * SWPRODSTRUCT.INVNAME OR SWFEATURE.INVNAME
 * 
 */

public class XMLMktgInvElem extends XMLElem {
	private String destinationPath;

	private String att2code;

	/***************************************************************************
	 * Constructor - used when element does not have text nodes and is not root
	 * 
	 * @param nname
	 *            String with name of node to be created
	 */
	public XMLMktgInvElem(String nname, String attcode, String attcode2, String path) {
		super(nname, attcode);
		destinationPath = path;
		att2code = attcode2;
	}

	/***************************************************************************
	 * Get the content node for this attribute(s), if this is a F (multiflag)
	 * then create one parent and node for each value
	 * 
	 * @param document
	 *            Document
	 * @param item
	 *            EntityItem
	 * @param parent
	 *            Element
	 */
	protected Node getContentNode(Document document, EntityItem item, Element parent, StringBuffer debugSb) throws IOException {
		if (attrCode == null || item == null) {
			return null;
		}
		EntityGroup egrp = item.getEntityGroup();
		EntityList list1 = egrp.getEntityList();
		// use value from entity1 id
		// new added
		String attrCodes[] = PokUtils.convertToArray(attrCode);
		for (int a = 0; a < attrCodes.length; a++) {
			String value = CHEAT;
			String code = attrCodes[a];
			boolean ifTmf = false;
			EANMetaAttribute metaAttr = egrp.getMetaAttribute(code);
			// new end
			// String value = CHEAT;
			// EANMetaAttribute metaAttr = egrp.getMetaAttribute(attrCode);
			if (metaAttr == null) {
				if (isReq) {
					throw new IOException(nodeName + " is required but " + code + " is not in " + item.getEntityType()
						+ " META data");
				}

				value = "Error: Attribute " + code + " not found in " + item.getEntityType() + " META data.";
			} else { // meta exists for this attribute
				Profile profile = list1.getProfile();
				NLSItem nlsitem = profile.getReadLanguage();
				int nlsid = nlsitem.getNLSID();
				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				EANAttribute att = item.getAttribute(code);// attrCode
				EANAttribute atta = item.getAttribute(code);
				if (att instanceof EANTextAttribute && atta instanceof EANTextAttribute) {
					if (((EANTextAttribute) att).containsNLS(1)) {
						if (att != null && att.toString().length() > 0) {
							ifTmf = true;
							ABRUtil.append(debugSb,"use the TMF's" + NEWLINE);
							if (((EANTextAttribute) atta).containsNLS(nlsid)) {
								value = atta.toString();
								if (metaAttr.getAttributeType().equals("T")) { // TextAttribute
									if (value.length() > getTextLimit()) {
										value = value.substring(0, getTextLimit());
										ABRUtil.append(debugSb,"XMLElem.getContentNode node:" + nodeName + " " + item.getKey()
											+ " value was truncated for attr " + code + NEWLINE);
									}
								}
							}
						}
					}
				}
				if (!ifTmf) {
					Vector entityVct = new Vector();
					// if the INVNAME is CHEAT, then get the SWFEATURE.INVNAME
					if (destinationPath != null && item != null) {
						ABRUtil.append(debugSb,"use the feature's" + NEWLINE);
						EntityItem theitem = item;
						Vector overrideVct = new Vector(1);
						Vector parentitemsVct = new Vector(1);
						parentitemsVct.add(theitem);
						StringTokenizer st1 = new StringTokenizer(destinationPath, ":");
						while (st1.hasMoreTokens()) {
							String dir = st1.nextToken();
							String destination = null;
							if (st1.hasMoreTokens()) {
								destination = st1.nextToken();
							}
							ABRUtil.append(debugSb,"XMLMktgInvElem: node:" + nodeName + " attrbutecode:" + code + " path:"
								+ destinationPath + " dir:" + dir + " destination " + destination + NEWLINE);
							// know we know dir and type needed
							Vector tmp = new Vector();
							for (int p = 0; p < parentitemsVct.size(); p++) {
								EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
								ABRUtil.append(debugSb,"XMLMktgInvElem: loop pitem " + pitem.getKey() + NEWLINE);
								Vector linkVct = null;
								if (dir.equals("D")) {
									linkVct = pitem.getDownLink();
								} else {
									linkVct = pitem.getUpLink();
								}
								for (int i = 0; i < linkVct.size(); i++) {
									EntityItem entity = (EntityItem) linkVct.elementAt(i);
									ABRUtil.append(debugSb,"XMLMktgInvElem: linkloop entity " + entity.getKey() + NEWLINE);
									if (entity.getEntityType().equals(destination)) {
										if (st1.hasMoreTokens()) {
											// keep looking
											tmp.add(entity);
										} else {
											overrideVct.add(entity);
										}
									}
								}
							}// end parentloop
							parentitemsVct = tmp;
						}

						entityVct = overrideVct;
					}
					if (entityVct.size() == 0) {
						debugSb
							.append("XMLMktgInvElem: node:" + nodeName + " No entities found for " + destinationPath + NEWLINE);
						// add any children to the parent, not this node

						return null;
					}
					for (int i = 0; i < entityVct.size(); i++) {
						EntityItem item2 = (EntityItem) entityVct.elementAt(i);
						EntityGroup egrp2 = item2.getEntityGroup();
						EANMetaAttribute metaAttr2 = egrp2.getMetaAttribute(code);// att2code
						if (metaAttr2 != null) {
							EANAttribute att2 = item2.getAttribute(code);
							if (att2 instanceof EANTextAttribute) {
								// true if information for the given NLSID is
								// contained in the Text data
								if (((EANTextAttribute) att2).containsNLS(nlsid)) {
									value = att2.toString();
									if (metaAttr2.getAttributeType().equals("T")) { // TextAttribute
										if (value.length() > getTextLimit()) {
											value = value.substring(0, getTextLimit());
											ABRUtil.append(debugSb,"XMLElem.getContentNode node:" + nodeName + " " + item2.getKey()
												+ " value was truncated for attr " + code + NEWLINE);
										}
									}
								} // end attr has this language
							}

						} else {
							value = "Error: Attribute " + code + " not found in " + item2.getEntityType() + " META data.";
						}
					}
					entityVct.clear();
				}

				return document.createTextNode(value);
			}
		}
		return null;
	}

	/**********************************************************************************
	 * Check to see if this node has a changed value for this NLS, must be a Text attribute
	 *
	 *@param diffitem DiffEntity
	 *@param debugSb StringBuffer
	 *
	 *hasNodeValueChgForNLS(diffitem, debugSb))
	 */

	protected boolean hasNodeValueChgForNLS(DiffEntity diffitem, StringBuffer debugSb) {
		boolean hasValue = false;
		// check at both times if one existed or not
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
		NLSItem nlsitem = null;
		if (!diffitem.isDeleted()) {
			nlsitem = curritem.getProfile().getReadLanguage();
		} else {
			nlsitem = previtem.getProfile().getReadLanguage();
		}
		int nlsid = nlsitem.getNLSID(); 
		String currVal = CHEAT;
		String prevVal = CHEAT;
		boolean ifcTmf = false;
		boolean ifpTmf = false;
		//new added 
		String attrCodes[] = PokUtils.convertToArray(attrCode);
		for(int a=0; a<attrCodes.length; a++){
			String code = attrCodes[a];
			if (code.equals("ENTITYTYPE") || code.equals("ENTITYID") || code.equals("NLSID") ||
				code.equals("ENTITY1ID") || code.equals("ENTITY2ID")){
				continue;
			}
			//new added end
		if (curritem != null) {
			EntityGroup egrp = curritem.getEntityGroup();
			EANMetaAttribute metaAttr = egrp.getMetaAttribute(code);
			if (metaAttr != null) {
				EANAttribute att = curritem.getAttribute(code);
				EANAttribute atta = curritem.getAttribute(code);
				if (att instanceof EANTextAttribute && atta instanceof EANTextAttribute) {
				 if (((EANTextAttribute) att).containsNLS(1)) {
						if (att != null && att.toString().length() > 0) {
							ifcTmf = true;
							ABRUtil.append(debugSb,"check the TMF's " + NEWLINE);
							if(((EANTextAttribute) atta).containsNLS(nlsid)){								
								currVal = atta.toString();								
							} 
						}

					}
				}
			}
		//	if (CHEAT.equals(currVal)) {
			if(!ifcTmf) {
				ABRUtil.append(debugSb,"hasNodeValueChgForNLS: geting current 2nd priority attrCode:" + att2code + NEWLINE);
				Vector entityVct = new Vector();
				// if the INVNAME is CHEAT, then get the SWFEATURE.INVNAME
				if (destinationPath != null && curritem != null) {
					ABRUtil.append(debugSb,"check the feature's" + NEWLINE);
					EntityItem theitem = curritem;
					Vector overrideVct = new Vector(1);
					Vector parentitemsVct = new Vector(1);
					parentitemsVct.add(theitem);
					StringTokenizer st1 = new StringTokenizer(destinationPath, ":");
					while (st1.hasMoreTokens()) {
						String dir = st1.nextToken();
						String destination = null;
						if (st1.hasMoreTokens()) {
							destination = st1.nextToken();
						}
						ABRUtil.append(debugSb,"hasNodeValueChgForNLS: node:" + nodeName + "attrCode:" + code + " path:"
							+ destinationPath + " dir:" + dir + " destination " + destination + NEWLINE);
						// know we know dir and type needed
						Vector tmp = new Vector();
						for (int p = 0; p < parentitemsVct.size(); p++) {
							EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
							ABRUtil.append(debugSb,"hasNodeValueChgForNLS: loop pitem " + pitem.getKey() + NEWLINE);
							Vector linkVct = null;
							if (dir.equals("D")) {
								linkVct = pitem.getDownLink();
							} else {
								linkVct = pitem.getUpLink();
							}
							for (int i = 0; i < linkVct.size(); i++) {
								EntityItem entity = (EntityItem) linkVct.elementAt(i);
								ABRUtil.append(debugSb,"hasNodeValueChgForNLS: linkloop entity " + entity.getKey() + NEWLINE);
								if (entity.getEntityType().equals(destination)) {
									if (st1.hasMoreTokens()) {
										// keep looking
										tmp.add(entity);
									} else {
										overrideVct.add(entity);
									}
								}
							}
						}// end parentloop
						parentitemsVct = tmp;
					}

					entityVct = overrideVct;
				}
				if (entityVct.size() > 0) {
					for (int i = 0; i < entityVct.size(); i++) {
						EntityItem item2 = (EntityItem) entityVct.elementAt(i);
						EntityGroup egrp2 = item2.getEntityGroup();
						EANMetaAttribute metaAttr2 = egrp2.getMetaAttribute(code);//att2code
						if (metaAttr2 != null) {
							EANAttribute att2 = item2.getAttribute(code);//att2code
							if (att2 instanceof EANTextAttribute) {
								// true if information for the given NLSID is
								// contained in the Text data
								if (((EANTextAttribute) att2).containsNLS(nlsid)) {
							//		if (att2 != null && att2.toString().length() > 0) {
										currVal = att2.toString();
							//		}
								}
							}
						}
					}

				} else {
					ABRUtil.append(debugSb,"hasNodeValueChgForNLS: node:" + nodeName + " No entities found for " + destinationPath
						+ NEWLINE);
					// add any children to the parent, not this node
				}
				entityVct.clear();
			}
		}
		if (previtem != null) {
			EntityGroup egrp = previtem.getEntityGroup();
			EANMetaAttribute metaAttr = egrp.getMetaAttribute(code);
			if (metaAttr != null) {
				EANAttribute att = previtem.getAttribute(code);
				EANAttribute atta = previtem.getAttribute(code);
				if (att instanceof EANTextAttribute && atta instanceof EANTextAttribute) {
					// true if information for the given NLSID is contained in the
					// Text data
				 if (((EANTextAttribute) att).containsNLS(1)) {
						if (att != null && att.toString().length() > 0) {
							ifpTmf = true;
							ABRUtil.append(debugSb,"check the TMF's " + NEWLINE);
							if(((EANTextAttribute) atta).containsNLS(nlsid)){								
								prevVal = atta.toString();								
							} 
						}

					}
				}
			}
			if (!ifpTmf) {
				ABRUtil.append(debugSb,"hasNodeValueChgForNLS: geting preview 2nd priority attrCode:" + code + NEWLINE);
				Vector entityVct = new Vector();
				// if the INVNAME is CHEAT, then get the SWFEATURE.INVNAME
				if (destinationPath != null && previtem != null) {
					ABRUtil.append(debugSb,"check the feature's" + NEWLINE);
					EntityItem theitem = previtem;
					Vector overrideVct = new Vector(1);
					Vector parentitemsVct = new Vector(1);
					parentitemsVct.add(theitem);
					StringTokenizer st1 = new StringTokenizer(destinationPath, ":");
					while (st1.hasMoreTokens()) {
						String dir = st1.nextToken();
						String destination = null;
						if (st1.hasMoreTokens()) {
							destination = st1.nextToken();
						}
						ABRUtil.append(debugSb,"hasNodeValueChgForNLS: node:" + nodeName + " path:" + destinationPath + " dir:" + dir
							+ " destination " + destination + NEWLINE);
						// know we know dir and type needed
						Vector tmp = new Vector();
						for (int p = 0; p < parentitemsVct.size(); p++) {
							EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
							ABRUtil.append(debugSb,"hasNodeValueChgForNLS: loop pitem " + pitem.getKey() + NEWLINE);
							Vector linkVct = null;
							if (dir.equals("D")) {
								linkVct = pitem.getDownLink();
							} else {
								linkVct = pitem.getUpLink();
							}
							for (int i = 0; i < linkVct.size(); i++) {
								EntityItem entity = (EntityItem) linkVct.elementAt(i);
								ABRUtil.append(debugSb,"hasNodeValueChgForNLS: linkloop entity " + entity.getKey() + NEWLINE);
								if (entity.getEntityType().equals(destination)) {
									if (st1.hasMoreTokens()) {
										// keep looking
										tmp.add(entity);
									} else {
										overrideVct.add(entity);
									}
								}
							}
						}// end parentloop
						parentitemsVct = tmp;
					}

					entityVct = overrideVct;
				}
				if (entityVct.size() > 0) {
					for (int i = 0; i < entityVct.size(); i++) {
						EntityItem item2 = (EntityItem) entityVct.elementAt(i);
						EntityGroup egrp2 = item2.getEntityGroup();
						EANMetaAttribute metaAttr2 = egrp2.getMetaAttribute(code);//att2code
						if (metaAttr2 != null) {
							EANAttribute att2 = item2.getAttribute(code);
							if (att2 instanceof EANTextAttribute) {
								// true if information for the given NLSID is
								// contained in the Text data
								if (((EANTextAttribute) att2).containsNLS(nlsid)) {
								//	if (att2 != null && att2.toString().length() > 0) {
										prevVal = att2.toString();
								//	}
								}
							}
						}
					}

				} else {
					ABRUtil.append(debugSb,"hasNodeValueChgForNLS: node:" + nodeName + " No entities found for " + destinationPath
						+ NEWLINE);
					// add any children to the parent, not this node
				}
				entityVct.clear();
			}
		}
		}
		ABRUtil.append(debugSb,"hasNodeValueChgForNLS.hasNodeValueChgForNLS node:" + nodeName + " " + diffitem.getKey()
			+ " ReadLanguage " + nlsitem + " attr " + attrCode + "\n currVal: " + currVal + "\n prevVal: " + prevVal + NEWLINE);

		if (!currVal.equals(prevVal)) { // we only care if there was a change
			hasValue = true;
		}
		return hasValue;
	}
}
