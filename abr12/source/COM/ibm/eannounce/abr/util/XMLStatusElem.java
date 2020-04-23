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
import org.w3c.dom.Node;
import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANMetaTextAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

//$Log: XMLStatusElem.java,v $
//Revision 1.4  2020/02/05 13:29:08  xujianbo
//Add debug info to investigate   performance issue
//
//Revision 1.3  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.2  2014/03/25 14:55:17  guobin
//flows to BH prof srv - multi status change - more broadly then we needed. data not in final sent as final
//
/**********************************************************************************
 * Class used to get the entity status, when the status is ever final in the
 * history group, then the status show 0020, else show the status of the entity.
 */

public class XMLStatusElem extends XMLElem {
	/**********************************************************************************
	 * Constructor for DTS value elements - uses getEndOfDay() in profile
	 * 
	 * 2 <NotificationTime>(Timestamp of the notification)</NotificationTime>
	 * 
	 * @param nname
	 *            String with name of node to be created
	 */
	private static final String STATUS_RFR = "0040";

	private static final String m_strEpoch = "1980-01-01-00.00.00.000000";

	private static final String STATUS_PASSED = "0030";

	private static final String STATUS_QUEUE = "0020";

	private static final String STATUS_INPROCESS = "0050";

	private static Vector tarEntity;
	static {
		tarEntity = new Vector();
		tarEntity.add("MODEL");
		tarEntity.add("PRODSTRUCT");
		tarEntity.add("LSEO");
		tarEntity.add("SWPRODSTRUCT");
		tarEntity.add("LSEOBUNDLE");
		tarEntity.add("SVCMOD");
	}

	private static Hashtable RootEntity_Dates;
	static {
		RootEntity_Dates = new Hashtable();
		RootEntity_Dates.put("MODEL", new String[] { "ANNDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
		RootEntity_Dates.put("LSEO1", new String[] { "LSEOPUBDATEMTRGT" }); // SPECBID
																			// NO
		RootEntity_Dates.put("LSEO2", new String[] { "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT" });
		RootEntity_Dates.put("PRODSTRUCT", new String[] { "ANNDATE", "GENAVAILDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
		RootEntity_Dates.put("SWPRODSTRUCT", new String[] { "GENAVAILDATE" });
		RootEntity_Dates.put("LSEOBUNDLE", new String[] { "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT" });
		RootEntity_Dates.put("SVCMOD", new String[] { "ANNDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
	}

	public XMLStatusElem(String nname, String code, int src) {
		super(nname, code, src);

	}

	/**********************************************************************************
	 * Create a node for this element and add to the parent and any children
	 * this node has
	 * 
	 * @param dbCurrent
	 *            Database
	 * @param list
	 *            EntityList
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, EntityList list, Document document, Element parent, EntityItem parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);

		if (parent == null) { // create the root
			document.appendChild(elem);
		} else { // create a node
			parent.appendChild(elem);
		}
		Profile m_prof = parentItem.getProfile();
		AttributeChangeHistoryGroup statusHistory = null;
		statusHistory = getADSABRSTATUSHistory(m_prof, dbCurrent, parentItem, nodeName, debugSb);
		Node contentElem = null;
		if (isExistFinal(statusHistory, document, parentItem, parent, debugSb)) {
			contentElem = document.createTextNode(STATUS_FINAL);
		} else {
			contentElem = getContentNode(document, parentItem, parent, debugSb);
		}

		if (contentElem != null) {
			elem.appendChild(contentElem);
		}
		// add any children
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, list, document, elem, parentItem, debugSb);
		}

		if (!elem.hasChildNodes()) {
			// a value is expected, prevent a normal empty tag, OIDH cant handle
			// it
			elem.appendChild(document.createTextNode(CHEAT));
		}
		statusHistory = null;
	}


	public boolean isExistFinal(AttributeChangeHistoryGroup statusHistory, Document document, EntityItem item, Element parent,
		StringBuffer debugSb) throws MiddlewareRequestException {
		boolean existFinal = false;
		if (attrCode == null || item == null) {
			return false;
		}
		if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
			for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					String status = achi.getFlagCode();
					if (status != null && status.equals(STATUS_FINAL)) {
						existFinal = true;
						break;
					}
				}
			}
		} else {
			ABRUtil.append(debugSb,"XMLStatusElem.isExistFinal STATUS has no changed history!" + NEWLINE);
			existFinal = false;
		}
		statusHistory = null;
		return existFinal;
	}

	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentEntity,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
		Node contentElem = null;
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		if (parent == null) { // create the root
			document.appendChild(elem);
		} else { // create a node
			parent.appendChild(elem);
		}
		EntityItem curritem = parentEntity.getCurrentEntityItem();
		EntityItem prioritem = parentEntity.getPriorEntityItem();
		EntityItem item = curritem;
		if (parentEntity.isDeleted()) {
			item = prioritem;
		}
		Profile m_prof = item.getProfile();
		AttributeChangeHistoryGroup adsStatusHistory = null;
		AttributeChangeHistoryGroup statusHistory = null;
		adsStatusHistory = getADSABRSTATUSHistory(m_prof, dbCurrent, item, "ADSABRSTATUS", debugSb);
		statusHistory = getADSABRSTATUSHistory(m_prof, dbCurrent, item, nodeName, debugSb);
		boolean isFinal = STATUS_FINAL.equals(PokUtils.getAttributeFlagValue(item, "STATUS"));

		if (tarEntity.contains(parentEntity.getEntityType())) {
			ABRUtil.append(debugSb,"XMLStatusElem running" +  parentEntity.getEntityType() + NEWLINE);
			if (isDelta(prioritem)) {
				if (isFinal) {
					contentElem = document.createTextNode(STATUS_FINAL);
					table.put("_chSTATUS", STATUS_FINAL);
				} else if (isExistFinal(statusHistory, document, item, parent, debugSb)) {

					ABRUtil.append(debugSb,"XMLStatusElem.addElements it's delta and existfinal. "+ NEWLINE);
					Vector metaAttrVct = setMeta(item);
					Vector chgdAttrVct = checkChgdAttr(item, prioritem, metaAttrVct, debugSb);
					ABRUtil.append(debugSb,"XMLStatusElem.addElements checkChgdAttr: " + chgdAttrVct.toString() + NEWLINE);
					// isDatechange() check the chagAttrVct whether only have
					// Date attributes list below,
					// if true, then createTextNode(STATUS_FINAL) else
					// createTextNode(STATUS_RFR).
					if (chgdAttrVct != null) {
						if (isDatechange(item, parentEntity, chgdAttrVct, debugSb)) {
							ABRUtil.append(debugSb,"XMLStatusElem.addElements only avail date related attricodes changed." + NEWLINE);
							contentElem = document.createTextNode(STATUS_FINAL);
							table.put("_chSTATUS", STATUS_FINAL);
						} else {
							ABRUtil.append(debugSb,"XMLStatusElem.addElements not only avail date attricodes changed. " + NEWLINE);
							contentElem = document.createTextNode(STATUS_RFR);
							table.put("_chSTATUS", STATUS_RFR);
						}
					}
				} else {
					contentElem = document.createTextNode(STATUS_RFR);
					table.put("_chSTATUS", STATUS_RFR);
				}
			} else {
				if (isFinal) {
					contentElem = document.createTextNode(STATUS_FINAL);
					table.put("_chSTATUS", STATUS_FINAL);
				} else if (isExistFinal(statusHistory, document, item, parent, debugSb)) {
					/*
					 * for resend, if the status is RFR, it was final before. I
					 * need to check last Status is Final queued ADSABRSTATUS,
					 * if it is Pass, then set T1 = timestamp of ADSABRSTATUS ='
					 * in process'. compare attributes of Root between the T1
					 * and T2. if the any changes on Root expect date
					 * attributes, then set <STATUS> - RFR. else set <STATUS> -
					 * Final
					 */
					ABRUtil.append(debugSb,"XMLStatusElem.addElements it's full xml and existfinal. " + NEWLINE);
					String T1 = getT1(adsStatusHistory, statusHistory, debugSb);
					if (!m_strEpoch.equals(T1)) {

						Profile profile = m_prof.getNewInstance(dbCurrent);
						profile.setValOnEffOn(T1, T1);
						EntityList mm_elist = dbCurrent.getEntityList(profile, new ExtractActionItem(null, dbCurrent, profile,"dummy"),
							new EntityItem[] { new EntityItem(null, profile, item.getEntityType(), item.getEntityID()) });
						prioritem = mm_elist.getParentEntityGroup().getEntityItem(0);

						Vector metaAttrVct = setMeta(item);
						Vector chgdAttrVct = checkChgdAttr(item, prioritem, metaAttrVct, debugSb);
						// isDatechange() check the chagAttrVct whether only
						// have Date attributes list below,
						// if true, then createTextNode(STATUS_FINAL) else
						// createTextNode(STATUS_RFR).
						if (chgdAttrVct != null) {
							if (isDatechange(item, parentEntity, chgdAttrVct, debugSb)) {
								ABRUtil.append(debugSb,"XMLStatusElem.addElements only avail date related attricodes changed. " + NEWLINE);
								contentElem = document.createTextNode(STATUS_FINAL);
								table.put("_chSTATUS", STATUS_FINAL);
							} else {
								ABRUtil.append(debugSb,"XMLStatusElem.addElements not only avail date related attricodes changed. " + NEWLINE);
								contentElem = document.createTextNode(STATUS_RFR);
								table.put("_chSTATUS", STATUS_RFR);
							}
						}
						mm_elist = null;
					} else {
						ABRUtil.append(debugSb,"XMLStatusElem.addElements send the whole data, T1 is 1980. " + NEWLINE);
						contentElem = document.createTextNode(STATUS_RFR);
						table.put("_chSTATUS", STATUS_RFR);
					}
				} else {
					contentElem = document.createTextNode(STATUS_RFR);
					table.put("_chSTATUS", STATUS_RFR);
				}
			}
		} else {
			ABRUtil.append(debugSb,"XMLStatusElem.addElements " + parentEntity.getEntityType() + " is not in the tarEntity list. " + NEWLINE);
			if (isExistFinal(statusHistory, document, item, parent, debugSb)) {
				contentElem = document.createTextNode(STATUS_FINAL);
				table.put("_chSTATUS", STATUS_FINAL);
			} else {
				contentElem = getContentNode(document, item, parent, debugSb);
				table.put("_chSTATUS", STATUS_RFR);
			}
		}

		if (contentElem != null) {
			elem.appendChild(contentElem);
		}
		// add any children
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, table, document, elem, parentEntity, debugSb);
		}

		if (!elem.hasChildNodes()) {
			// a value is expected, prevent a normal empty tag, OIDH cant handle
			// it
			elem.appendChild(document.createTextNode(CHEAT));
		}
		adsStatusHistory = null;
		statusHistory = null;
	}

	private AttributeChangeHistoryGroup getADSABRSTATUSHistory(Profile m_prof, Database dbCurrent, EntityItem item, String attr,
		StringBuffer debugSb) throws MiddlewareException {

		EANAttribute att = item.getAttribute(attr);
		if (att != null) {
			return new AttributeChangeHistoryGroup(dbCurrent, m_prof, att);
		} else {
			ABRUtil.append(debugSb,attr + " of " + item.getKey() + "  was null");
			return null;
		}
	}

	/*
	 * for resend, if the status is RFR, it was final before. I need to check
	 * last Status is Final queued ADSABRSTATUS, if it is Pass, then set T1 =
	 * timestamp of ADSABRSTATUS =' in process'. compare attributes of Root
	 * between the T1 and T2. if the any changes on Root expect date attributes,
	 * then set <STATUS> - RFR. else set <STATUS> - Final
	 */
	private String getT1(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory,
		StringBuffer debugSb) throws MiddlewareRequestException {

		String T1 = m_strEpoch;
		boolean nextQueued = false;
		AttributeChangeHistoryItem achi, nextachi;
		if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 0) {
			for (int i = adsStatusHistory.getChangeHistoryItemCount() - 3; i >= 0; i--) {
				achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					if (achi.getFlagCode().equals(STATUS_PASSED)) { // Passed
						nextQueued = true;
					}
					if (nextQueued && achi.getFlagCode().equals(STATUS_QUEUE)) { 
						String status = getTQStatus(statusHistory, achi.getChangeDate(), debugSb);
						if (status.equals(STATUS_FINAL)) {
							nextachi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i + 1);
							if (nextachi.getFlagCode().equals(STATUS_INPROCESS)) {
								T1 = nextachi.getChangeDate();
								return T1;
							}
						}
					}
				}
			}
		} else {
			ABRUtil.append(debugSb,"getT1 for STATUS has no changed history!");
		}
		return T1;
	}

	private String getTQStatus(AttributeChangeHistoryGroup statusHistory, String tqtime, StringBuffer debugSb)
		throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
			// last chghistory is the current one
			for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if (achi != null) {
					if (tqtime.compareTo(achi.getChangeDate()) > 0) {
						return achi.getFlagCode();
					}
				}
			}
		} else {
			ABRUtil.append(debugSb,"getTQStatus for STATUS has no changed history!");
		}
		return CHEAT;
	}

	private boolean isDelta(EntityItem prioritem) {

		if (prioritem != null && m_strEpoch.equals(prioritem.getProfile().getValOn())) {
			return false;
		}
		return true;
	}

	private boolean isDatechange(EntityItem item, DiffEntity parentEntity, Vector chgdAttrVct, StringBuffer debugSb) {

		Vector tarAttrVct = new Vector();
		if (!"LSEO".equals(parentEntity.getEntityType())) {
			String[] attrs = (String[]) RootEntity_Dates.get(parentEntity.getEntityType());
			if (attrs != null && attrs.length > 0) {
				for (int i = 0; i < attrs.length; i++) {
					tarAttrVct.add(attrs[i]);
				}
			}
		} else {
			Vector wwseoVct = PokUtils.getAllLinkedEntities(item, "WWSEOLSEO", "WWSEO");
			for (int w = 0; w < wwseoVct.size(); w++) {
				EntityItem wwseoitem = (EntityItem) wwseoVct.elementAt(w);
				String specbid = PokUtils.getAttributeFlagValue(wwseoitem, "SPECBID");
				if ("11457".equals(specbid)) { // IS NO
					String[] attrs = (String[]) RootEntity_Dates.get("LSEO1");
					if (attrs != null && attrs.length > 0) {
						for (int i = 0; i < attrs.length; i++) {
							tarAttrVct.add(attrs[i]);
						}
					}
				} else {
					String[] attrs = (String[]) RootEntity_Dates.get("LSEO2");
					if (attrs != null && attrs.length > 0) {
						for (int i = 0; i < attrs.length; i++) {
							tarAttrVct.add(attrs[i]);
						}
					}
				}
			}
		}
		for (int i = 0; i < chgdAttrVct.size(); i++) {
			String chgdattr = (String) chgdAttrVct.elementAt(i);
			if (!tarAttrVct.contains(chgdattr)) {
				ABRUtil.append(debugSb,"chgdattr " + chgdattr + " is not in availdate.");
				return false;
			}
		}
		return true;
	}

	private Vector setMeta(EntityItem item) {
		Vector metaAttrVct = new Vector();
		EntityGroup eg = item.getEntityGroup();
		for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
			EANMetaAttribute ma = eg.getMetaAttribute(i);
			String code = ma.getAttributeCode();
			if (!ma.getAttributeType().equals("A") && !code.equals("STATUS") && !code.equals("SYSFEEDRESEND")) { // Filter out 'A' attrType																									 
				metaAttrVct.add(ma);
			}
		}
		return metaAttrVct;
	}

	/********************************************************************************
	 * count attribute changes so dont have to look over and over again
	 */

	private Vector checkChgdAttr(EntityItem currentItem, EntityItem priorItem, Vector metaAttrVct, StringBuffer debugSb) {
		Vector chgdAttr = new Vector();
		Profile currprofile = null;
		Profile prevprofile = null;
		if (currentItem != null) {
			currprofile = currentItem.getProfile();
		}
		if (priorItem != null) {
			prevprofile = priorItem.getProfile();
		}

		Profile profile = (currprofile == null ? prevprofile : currprofile); // both
		NLSItem origNls = profile.getReadLanguage();

		// check if attr changed or not
		for (int i = 0; i < metaAttrVct.size(); i++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaAttrVct.elementAt(i);
			String attrcode = ma.getAttributeCode();
			EANAttribute attr = null;
			String priorval = "";
			String curval = "";
			String eikey = "";

			if (priorItem != null) {
				eikey = priorItem.getKey();
				attr = priorItem.getAttribute(attrcode);
				if (attr != null) {
					priorval = attr.toString();
				}
			}
			if (currentItem != null) {
				eikey = currentItem.getKey();
				attr = currentItem.getAttribute(attrcode);
				if (attr != null) {
					curval = attr.toString();
				}
			}
			if (!curval.equals(priorval)) {
				chgdAttr.add(attrcode);
				ABRUtil.append(debugSb,"XMLStatusElem " + eikey + " has chgd " + attrcode + NEWLINE);
			} else { // make sure it wasnt changed in a different nls, if needed
				if (ma instanceof EANMetaTextAttribute) {
					// this is NLS sensitive, do each one
					for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
						NLSItem nlsitem = profile.getReadLanguage(ix);
						if (nlsitem == origNls) {
							continue; // already checked this one
						}
						if (currprofile != null) {
							currprofile.setReadLanguage(ix);
						}
						if (prevprofile != null) {
							prevprofile.setReadLanguage(ix);
						}

						priorval = "";
						curval = "";
						if (priorItem != null) {
							attr = priorItem.getAttribute(attrcode);
							if (attr instanceof EANTextAttribute) {
								int nlsid = nlsitem.getNLSID();
								// true if information for the given NLSID is
								// contained in the Text data
								if (((EANTextAttribute) attr).containsNLS(nlsid)) {
									priorval = attr.toString();
								} // end attr has this language
							}
						}
						if (currentItem != null) {
							attr = currentItem.getAttribute(attrcode);
							if (attr instanceof EANTextAttribute) {
								int nlsid = nlsitem.getNLSID();
								// true if information for the given NLSID is
								// contained in the Text data
								if (((EANTextAttribute) attr).containsNLS(nlsid)) {
									curval = attr.toString();
								} // end attr has this language
							}
						}
						if (!curval.equals(priorval)) {
							chgdAttr.add(attrcode);
							ABRUtil.append(debugSb,"XMLStatusElem " + eikey + " has chgd " + attrcode + NEWLINE);
						}
					} // end each nls
					if (currprofile != null) {
						currprofile.setReadLanguage(origNls);
					}
					if (prevprofile != null) {
						prevprofile.setReadLanguage(origNls);
					}
				} // end is meta text attr
			} // end need to check all nls
		}
		return chgdAttr;
	}
	/*
	 * XML <STATUS> 1.First time the Root Entity (e.g. MODEL) moved to RFR -
	 * full XML was created with the XML <STATUS> = RFR and the
	 * <AVAILABILITYELEMENT> <STATUS> = RFR. 2. 2nd thru Nth time the Root
	 * Entity moved to RFR but was never Final.- Delta XML was created with the
	 * XML <STATUS>=RFR and the <AVAILABILITYELEMENT> <STATUS> = RFR/Final. 3.
	 * First time to Final - full XML was created with the XML <STATUS> = Final
	 * and the <AVAILABILITYELEMENT> <STATUS> = Final/RFR 4. Root Entity moved
	 * from Final - CR - RFR, anything change on the Root Entity except date
	 * attributes(list in following RootEntity_Dates) - Delta XML was created
	 * with the XML<STATUS> = RFR and the <AVAILABILITYELEMENT> <STATUS> =
	 * Final/RFR. If only Date attributes change (list in following
	 * RootEntity_Dates) - Delta XML was created with the XML<STATUS> = FINAL
	 * and <AVAILABILITYELEMENT> <STATUS> = Final/RFR.
	 * 
	 * 5. 2nd thru Nth time the Root Entity moved from RFR –CR- RFR but was
	 * Final, anything change on the last RFR except date attributes - Delta XML
	 * The XML <STATUS> = RFR, if only Date attributes change - Delta XML the
	 * XML <STATUS> = FINAL. 6 3rd thru Nth time the Root Entity moved to Final
	 * – Delta XML was created with the XML<STATUS> = Final and the
	 * AVAILABILITYELEMENT> <STATUS> = Final/RFR
	 * 
	 * 
	 * 
	 * list of RootEntity_Dates:
	 * 
	 * MODEL <ANNDATE> MODEL.ANNDATE <FIRSTORDER> MODEL.ANNDATE
	 * <PLANNEDAVAILABILITY> MODEL.ANNDATE <PUBFROM> MODEL.ANNDATE <PUBTO>
	 * MODEL. WTHDRWEFFCTVDATE <WDANNDATE> MODEL.WITHDRAWDATE <LASTORDER>
	 * MODEL.WTHDRWEFFCTVDATE
	 * 
	 * LSEO SPECBID = yes <ANNDATE> LSEO.LSEOPUBDATEMTRGT <ANNNUMBER>
	 * LSEO.LSEOPUBDATEMTRGT <PLANNEDAVAILABILITY> LSEO. LSEOPUBDATEMTRGT
	 * <PUBFROM> LSEO. LSEOPUBDATEMTRGT <PUBTO> LSEO. LSEOUNPUBDATEMTRGT
	 * <WDANNDATE> LSEO.LSEOUNPUBDATEMTRGT <LASTORDER> LSEO.LSEOUNPUBDATEMTRGT
	 * 
	 * SPECBID = no <FIRSTORDER> LSEO. LSEOPUBDATEMTRGT <PUBFROM> LSEO.
	 * LSEOPUBDATEMTRGT
	 * 
	 * TMF <ANNDATE> PRODSTRUCT.ANNDATE <FIRSTORDER> PRODSTRUCT.ANNDATE
	 * <PLANNEDAVAILABILITY> RODSTRUCT.GENAVAILDATE <PUBFROM> PRODSTRUCT.ANNDATE
	 * <PUBTO> PRODSTRUCT. WTHDRWEFFCTVDATE <WDANNDATE> PRODSTRUCT. WITHDRAWDATE
	 * <LASTORDER> PRODSTRUCT. WTHDRWEFFCTVDATE
	 * 
	 * SWPRODSTRUCT <PLANNEDAVAILABILITY> RODSTRUCT.GENAVAILDATE
	 * 
	 * LSEOBUNDLE SPECBID = yes <ANNDATE> LSEOBUNDLE.BUNDLPUBDATEMTRGT
	 * <FIRSTORDER> LSEOBUNDLE.BUNDLPUBDATEMTRGT <PLANNEDAVAILABILITY>
	 * LSEOBUNDLE.BUNDLPUBDATEMTRGT <PUBFROM> LSEOBUNDLE.BUNDLPUBDATEMTRGT
	 * <PUBTO> LSEOBUNDLE.BUNDLUNPUBDATEMTRGT <WDANNDATE>
	 * LSEOBUNDLE.BUNDLUNPUBDATEMTRGT <LASTORDER> LSEOBUNDLE.BUNDLUNPUBDATEMTRGT
	 * 
	 * SPECBID = no <FIRSTORDER> LSEOBUNDLE.BUNDLPUBDATEMTRGT <PUBFROM>
	 * LSEOBUNDLE.BUNDLPUBDATEMTRGT <PUBTO> LSEOBUNDLE.BUNDLUNPUBDATEMTRGT
	 * <LASTORDER> LSEOBUNDLE.BUNDLUNPUBDATEMTRGT
	 * 
	 * 
	 * SVCMOD <ANNDATE> SVCMOD.ANNDATE <FIRSTORDER> SVCMOD.ANNDATE
	 * <PLANNEDAVAILABILITY> SVCMOD.ANNDATE <PUBFROM> SVCMOD.ANNDATE <PUBTO>
	 * SVCMOD.WTHDRWEFFCTVDATE <WDANNDATE> SVCMOD.WITHDRAWDATE <LASTORDER>
	 * SVCMOD.WTHDRWEFFCTVDATE
	 */

}
