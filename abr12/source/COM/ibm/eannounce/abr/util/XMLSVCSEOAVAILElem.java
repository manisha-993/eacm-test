//Licensed Materials -- Property of IBM
// 
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
 * Class used to hold info and structure to be generated for the xml feed
 * for abrs.
 *    * Constructor for <AVAILABILITYLIST> elements
 * <AVAILABILITYLIST> 2	AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
 * <AVIAILABILITYELEMENT>	 3		
 *   <AVAILABILITYACTION>		    4  AVAIL	CountryAction
 *   <STATUS>	                    
 *   <COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST - Flag Description Class
 
 *   <EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>		4	AVAIL/
 * 	<PUBFROM>	</PUBFROM>		4	AVAIL/	PubFrom
 * 	<PUBTO>	</PUBTO>			4	AVAIL/	PubTo
 * 	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>			4	AVAIL/	Endofservice	
 * </AVIAILABILITYELEMENT>		3
 * </AVAILABILITYLIST>		2
 *
 */
// $Log: XMLSVCSEOAVAILElem.java,v $
// Revision 1.2  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.1  2014/01/07 12:46:57  guobin
// delete XML - Avails RFR Defect: BH 185136 Doc BH FS ABR XML System Feed Mapping 20131106b.doc
//
// Revision 1.15  2012/01/22 05:39:29  guobin
// RTM work item number on the change is 643541 / BHCQ 81991 Update to XML System Feed Mapping 20120117.doc - correct design for PUBFROM, FIRSTORDER, PUBTO
//
// Revision 1.14  2011/02/26 02:38:05  guobin
// update the case that SVCSEO is new
//
// Revision 1.13  2011/01/26 07:22:28  guobin
// change the avail of SVCSEO for Sales Org Group
//
// Revision 1.12  2011/01/26 06:48:37  guobin
// change the avail path of SVCSEO
//
// Revision 1.11  2011/01/05 02:55:42  guobin
// Add AVAIL action is DELETE_ACTIVITY case
//
// Revision 1.10  2010/12/23 10:00:23  guobin
// add <SLEORGGRPLIST> <SLEORGNPLNTCODELIST>
//
// Revision 1.9  2010/12/10 07:40:31  guobin
// get  AVAILs from the Table then filtering them according to  the path
//
// Revision 1.8  2010/12/08 01:54:48  guobin
// fix the multiUsed entity and clear the cache
//
// Revision 1.7  2010/12/03 13:26:21  guobin
// fix the multiUse entity
//
// Revision 1.6  2010/11/30 05:56:48  guobin
// add hasChanges method for the svcmod avail
//
// Revision 1.5  2010/11/26 08:16:51  guobin
// find diffitem in table
//
// Revision 1.4  2010/11/26 07:21:27  guobin
// check the status of the avail with SVCMOD
//
// Revision 1.3  2010/11/25 02:45:32  guobin
// update the method of derivePubFrom
//
// Revision 1.2  2010/11/11 15:13:47  guobin
// update the version information
//
// Revision 1.1  2010/11/11 15:12:03  guobin
// special avail for the SVCMOD
//
public class XMLSVCSEOAVAILElem extends XMLElem {
	
	protected Vector childVct = new Vector(1);
	private static String availRelator = "";
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
	
	protected boolean hasChanges(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		boolean changed = false;
		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.hasChanges parentItem: " + parentItem.getKey() + NEWLINE);
		if (parentItem.getEntityType().equals("SVCMOD")) {
			availRelator = "SVCMODAVAIL";
		} else {
			availRelator = "SVCSEOAVAIL";
		}
		Vector plnAvlVct = getPlannedAvails(table, parentItem, debugSb);

		if (plnAvlVct.size() > 0) { // must have planned avail for any of this, wayne said there will always be at least 1
			// get model audience values, t2[0] current, t1[1] prior
			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
			// AVAILb to have US at T2
			TreeMap ctryAudElemMap = new TreeMap();
			for (int i = 0; i < plnAvlVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
				buildCtryAudRecs(ctryAudElemMap, availDiff, debugSb);
			}// end each planned avail
			//Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
			// output the elements
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			while (itr.hasNext()) {
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
				if (!ctryAudRec.isDeleted()) {
					// find firstorder avail for this country
					DiffEntity foAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					// find lastorder avail for this country
					DiffEntity loAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					DiffEntity endAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
//					TODO 20120117.doc
					DiffEntity endMktAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "200",  "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					// add other info now
					ctryAudRec.setAllFields(parentItem, foAvailDiff, loAvailDiff, endAvailDiff, endMktAvailDiff, table, debugSb);
				}
				if (ctryAudRec.isDisplayable()) {
					changed = true;
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.hasChanges is true" + NEWLINE);
					break;
				} else {
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.hasChanges no changes found for " + ctryAudRec + NEWLINE);
				}
				ctryAudRec.dereference();
			}

			// release memory
			ctryAudElemMap.clear();
		} else {
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.hasChanges no planned AVAILs found" + NEWLINE);
		}

		return changed;
	}

	/**********************************************************************************
	 * Constructor for <AVAILABILITYLIST> elements
	 * <AVAILABILITYLIST> 2	AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
	 * <AVIAILABILITYELEMENT>	 3		
	 *   <AVAILABILITYACTION>		    4  AVAIL	CountryAction
	 *   <STATUS>	                    
	 *   <COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST - Flag Description Class
	 
	 *   <EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>		4	AVAIL/
	 * 	<PUBFROM>	</PUBFROM>		4	AVAIL/	PubFrom
	 * 	<PUBTO>	</PUBTO>			4	AVAIL/	PubTo
	 * 	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>			4	AVAIL/	Endofservice	
	 * </AVIAILABILITYELEMENT>		3
	 * </AVAILABILITYLIST>		2
	 *
	 *
	 */

	public XMLSVCSEOAVAILElem() {
		super("AVAILABILITYELEMENT");
	}

	/**********************************************************************************
	 *@param dbCurrent Database
	 *@param table Hashtable of Vectors of DiffEntity
	 *@param document Document needed to create nodes
	 *@param parent Element node to add this node too
	 *@param parentItem DiffEntity - parent to use if path is specified in XMLGroupElem, item to use otherwise
	 *@param debugSb StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {

		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem:parentItem: " + parentItem.getKey() + NEWLINE);
		if (parentItem.getEntityType().equals("SVCMOD")) {
			availRelator = "SVCMODAVAIL";
		} else {
			availRelator = "SVCSEOAVAIL";
		}

		Vector plnAvlVct = getPlannedAvails(table, parentItem, debugSb);

		if (plnAvlVct.size() > 0) { // must have planned avail for any of this, wayne said there will always be at least 1
			// get model audience values, t2[0] current, t1[1] prior
			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
			// AVAILb to have US at T2
			TreeMap ctryAudElemMap = new TreeMap();
			for (int i = 0; i < plnAvlVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
				buildCtryAudRecs(ctryAudElemMap, availDiff, debugSb);
			}// end each planned avail
			// there is no such attribute AUDIEN on SVCMOD, so do need to get Catelog infor.
			//Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
			// output the elements
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			while (itr.hasNext()) {
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
				if (!ctryAudRec.isDeleted()) {
					// find firstorder avail for this country
					DiffEntity foAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					// find lastorder avail for this country
					DiffEntity loAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					DiffEntity endAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					//TODO 20120117.doc
					DiffEntity endMktAvailDiff = getEntityForAttrs(table, parentItem, "AVAIL", "AVAILTYPE", "200",  "COUNTRYLIST",
						ctryAudRec.getCountry(), debugSb);
					//    					find catlgor for this country and audience

					//DiffEntity catlgorDiff = getCatlgor(table, mdlAudVct, ctryAudRec.getCountry(), debugSb);
					// add other info now
					ctryAudRec.setAllFields(parentItem, foAvailDiff, loAvailDiff, endAvailDiff, endMktAvailDiff, table, debugSb);
				}
				if (ctryAudRec.isDisplayable()) {
					createNodeSet(table, document, parent, parentItem, ctryAudRec, debugSb);
					
				} else {
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.addElements no changes found for " + ctryAudRec + NEWLINE);
				}
				ctryAudRec.dereference();
			}

			// release memory
			ctryAudElemMap.clear();
//			Vector annVct = (Vector) table.get("ANNOUNCEMENT");
//			if (annVct != null) {
//				annVct.clear();
//			}
//			//remove availVct
//			if (availVct != null) {
//				availVct.clear();
//			}
		} else {
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.addElements no planned AVAILs found" + NEWLINE);
		}
		//      	}
	}

	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, DiffEntity parentDiffEntity, CtryAudRecord ctryAudRec, StringBuffer debugSb) {
		Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
		addXMLAttrs(elem);
		parent.appendChild(elem);

		// add child nodes
		Element child = (Element) document.createElement("AVAILABILITYACTION");
		child.appendChild(document.createTextNode("" + ctryAudRec.getAction()));
		elem.appendChild(child);
		child = (Element) document.createElement("STATUS");
		child.appendChild(document.createTextNode("" + ctryAudRec.getAvailStatus()));
		elem.appendChild(child);
		child = (Element) document.createElement("COUNTRY_FC");
		child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
		elem.appendChild(child);
		child = (Element) document.createElement("ANNDATE");
		child.appendChild(document.createTextNode("" + ctryAudRec.getAnndate()));
		elem.appendChild(child);
		child = (Element) document.createElement("ANNNUMBER");
		child.appendChild(document.createTextNode("" + ctryAudRec.getAnnnumber()));
		elem.appendChild(child);
		child = (Element) document.createElement("FIRSTORDER");
		child.appendChild(document.createTextNode("" + ctryAudRec.getFirstorder()));
		elem.appendChild(child);
		child = (Element) document.createElement("PLANNEDAVAILABILITY");
		child.appendChild(document.createTextNode("" + ctryAudRec.getPlannedavailability()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBFROM");
		child.appendChild(document.createTextNode("" + ctryAudRec.getPubFrom()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBTO");
		child.appendChild(document.createTextNode("" + ctryAudRec.getPubTo()));
		elem.appendChild(child);
		child = (Element) document.createElement("WDANNDATE");
		child.appendChild(document.createTextNode("" + ctryAudRec.getWdanndate()));
		elem.appendChild(child);
		child = (Element) document.createElement("LASTORDER");
		child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
		elem.appendChild(child);
		child = (Element) document.createElement("EOSANNDATE");
		child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
		elem.appendChild(child);
		child = (Element) document.createElement("ENDOFSERVICEDATE");
		child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
		elem.appendChild(child);
		EntityItem parentItem = parentDiffEntity.getCurrentEntityItem();
		if(availRelator.equals("SVCMODAVAIL")){
			SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, ctryAudRec.availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
		}
		else{
			if(parentDiffEntity.isNew()){
				//SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, ctryAudRec.availDiff, "NEWD:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, ctryAudRec.availDiff, "NEWD:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			}else{
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, ctryAudRec.availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			}
			
		}
		
	}

	/******************** this method has changed for BH. 
	 * Create rows in the table as follows:
	 * Insert one row for each Audience in MODEL.AUDIEN & each Country in AVAIL.COUNTRYLIST where AVAILTYPE = 146
	 * If the AVAIL was deleted, set Action = Delete
	 * If the AVAIL was added or updated, set Action = Update
	 * 
	 * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
	 * If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
	 *
	 * Note:
	 * Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
	 * If any of the following steps have data that do not match an existing row in this table, ignore that data.
	 */

	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, StringBuffer debugSb) {

		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs " + availDiff.getKey() + NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();

		if (availDiff.isDeleted()) { // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for deleted avail: ctryAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
			if (ctryAtt != null) {
				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < mfArray.length; im++) {
					// get selection
					if (mfArray[im].isSelected()) {
						String ctryVal = mfArray[im].getFlagCode();
						String mapkey = ctryVal;
						if (ctryAudElemMap.containsKey(mapkey)) {
							// dont overwrite
							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted " + availDiff.getKey() + " " + mapkey
								+ " already exists, keeping orig " + rec + NEWLINE);
						} else {
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for deleted:" + availDiff.getKey() + " rec: "
								+ ctryAudRec.getKey() + NEWLINE);
						}
					}
				}
			}
		} else if (availDiff.isNew()) { //If the AVAIL was added or updated, set Action = Update
			// mark all records as update
			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt "
				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")
				+ PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") + NEWLINE);
			if (ctryAtt != null) {
				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < mfArray.length; im++) {
					// get selection
					if (mfArray[im].isSelected()) {
						String ctryVal = mfArray[im].getFlagCode();
						String mapkey = ctryVal;
						if (ctryAudElemMap.containsKey(mapkey)) {
							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new " + availDiff.getKey() + " " + mapkey
								+ " already exists, replacing orig " + rec + NEWLINE);
							rec.setUpdateAvail(availDiff);
						} else {
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
							ctryAudRec.setAction(UPDATE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for new:" + availDiff.getKey() + " rec: "
								+ ctryAudRec.getKey() + NEWLINE);
						}
					}
				}
			}
		} else{// else if one country in the countrylist has changed, update this row to update!
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
			// get current set of countries
			EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt "
				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")
				+ PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") + NEWLINE);
			if (fAtt != null && fAtt.toString().length() > 0) {
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						currSet.add(mfArray[i].getFlagCode());
					} // metaflag is selected
				}// end of flagcodes
			}

			// get prev set of countries
			fAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")
				+ PokUtils.getAttributeFlagValue(prioritem, "ANNCODENAME") + NEWLINE);
			if (fAtt != null && fAtt.toString().length() > 0) {
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						prevSet.add(mfArray[i].getFlagCode());
					} // metaflag is selected
				}// end of flagcodes
			}

			// look for changes in country
			Iterator itr = currSet.iterator();
			while (itr.hasNext()) {
				String ctryVal = (String) itr.next();
				if (!prevSet.contains(ctryVal)) { // If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update

					String mapkey = ctryVal;
					if (ctryAudElemMap.containsKey(mapkey)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on " + availDiff.getKey() + " " + mapkey
							+ " already exists, replacing orig " + rec + NEWLINE);
						rec.setUpdateAvail(availDiff);
					} else {
						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
						ctryAudRec.setAction(UPDATE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for added ctry:" + availDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}
				} else {
					// BH this country has already exist, put into ctryaudrec, but don't udpate Action to 'update'/'delete'.
					String mapkey = ctryVal;
					if (ctryAudElemMap.containsKey(mapkey)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry but new aud on " + availDiff.getKey() + " "
							+ mapkey + " already exists, keeping orig " + rec + NEWLINE);
					} else {
						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for existing ctry:" + availDiff.getKey()
							+ " rec: " + ctryAudRec.getKey() + NEWLINE);
					}
				}
			}//end of currset while(itr.hasNext())
			itr = prevSet.iterator();
			while (itr.hasNext()) {
				String ctryVal = (String) itr.next();
				if (!currSet.contains(ctryVal)) { //If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
					//create crossproduct between deleted ctry and previous audience for this item	
					String mapkey = ctryVal;
					if (ctryAudElemMap.containsKey(mapkey)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on " + availDiff.getKey() + " " + mapkey
							+ " already exists, keeping orig " + rec + NEWLINE);
					} else {
						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
						ctryAudRec.setAction(DELETE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.buildCtryAudRecs for delete ctry:" + availDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}
				}

			}
		} // end avail existed at both t1 and t2
	}

	/********************
	 * get planned avails - availtype cant be changed
	 */
	private Vector getPlannedAvails(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("AVAIL");
		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}

		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (deriveTheSameEntry(diffitem, parentItem, debugSb)) {
				if (diffitem.isDeleted()) {
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey()
						+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
					if (fAtt != null && fAtt.isSelected("146")) {
						avlVct.add(diffitem);
					}
				} else {
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getPlannedAvails checking[" + i + "]:" + diffitem.getKey()
						+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
					if (fAtt != null && fAtt.isSelected("146")) {
						avlVct.add(diffitem);
					}
				}
			}
		}

		return avlVct;
	}

	/**
	 * check the item is from the same relator that it connect from the root entity
	 * @param item
	 * @param debugSb
	 * @return
	 */
	private boolean deriveTheSameEntry(DiffEntity item, DiffEntity parentItem, StringBuffer debugSb) {
		boolean isFromTheSame = false;
		if (item != null) {
			if (availRelator.equals("SVCMODAVAIL")) {
				isFromTheSame = item.getParentKey().startsWith(availRelator);
			} else {
				StringTokenizer st1 = new StringTokenizer(item.toString(), " ");
				String path = CHEAT;
				while (st1.hasMoreTokens()) {
					path = st1.nextToken();
					if (path.startsWith("path:"))
						break;
				}
				if (!CHEAT.equals(path)) {
					StringTokenizer st2 = new StringTokenizer(path, ":");
					while (st2.hasMoreTokens()) {
						String dir = st2.nextToken();
						if (dir.startsWith(availRelator)) {
							if (st2.hasMoreTokens()) {
								if (st2.nextToken().startsWith(parentItem.getKey()))
									isFromTheSame = true;
								break;
							}
						}
					}

				}
			}
		}
		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveTheSameEntry is " + isFromTheSame + " availrelator: " + availRelator
			+ " ParentKey: " + item.getParentKey() + " Key: " + item.toString() + NEWLINE);
		return isFromTheSame;
	}

	/********************
	 * get entity with specified values - should only be one
	 * could be two if one was deleted and one was added, but the added one will override and be an 'update'
	 */
	private DiffEntity getEntityForAttrs(Hashtable table, DiffEntity parentItem, String etype, String attrCode, String attrVal,
		String attrCode2, String attrVal2, StringBuffer debugSb) {
		DiffEntity diffEntity = null;
		Vector allVct = (Vector) table.get(etype);
		ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getEntityForAttrs looking for " + attrCode + ":" + attrVal + " and " + attrCode2
			+ ":" + attrVal2 + " in " + etype + " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}
		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (deriveTheSameEntry(diffitem, parentItem, debugSb)) {
				if (diffitem.isDeleted()) {
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey()
						+ " " + attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem; // keep looking for one that is not deleted
						}
					}
				} else {
					if (diffitem.isNew()) {
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey()
							+ " " + attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								break;
							}
						}
					} else {
						// must check to see if the prior item had a match too
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + i + "]: current "
							+ diffitem.getKey() + " " + attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " "
							+ attrCode2 + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								break;
							}
						}
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey()
							+ " " + attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								//break; see if there is another that is current
							}
						}
					}
				}
			}
		}

		return diffEntity;
	}

	/*******************************
	 * one for every  AVAIL.COUNTRYLIST where availtype='planned availbility(146)  include the avails (delete,new and update) 
	 *
	 */
	private static class CtryAudRecord {
		private String action = CHEAT;

		private String country;

		//private String earliestshipdate = CHEAT;// AVAIL
		private String availStatus = CHEAT; //AVAIL

		private String pubfrom = CHEAT; //AVAIL/	PubFrom

		private String pubto = CHEAT; //AVAIL/	PubTo

		private String endofservice = CHEAT; //ENDOFSERVICE

		private String anndate = CHEAT;

		private String firstorder = CHEAT;

		private String plannedavailability = CHEAT;

		private String wdanndate = CHEAT;

		private String lastorder = CHEAT;

		private String eosanndate = CHEAT;

		private String annnumber = CHEAT;

		private DiffEntity availDiff;

		boolean isDisplayable() {
			return !action.equals(CHEAT);
		} // only display those with filled in actions

		CtryAudRecord(DiffEntity diffitem, String ctry) {
			country = ctry;
			availDiff = diffitem;
		}

		void setAction(String s) {
			action = s;
		}

		void setUpdateAvail(DiffEntity avl) {
			availDiff = avl;// allow replacement
			setAction(UPDATE_ACTIVITY);
		}

		/*********************
		 * 3.	<EARLIESTSHIPDATE>
		 * 	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		 * 	this avail cannot be deleted at this point
		 * 
		 *  * 	<PUBFROM>
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBFROM
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
		 4.	Empty (aka Null)
		 *  * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBTO
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 3 .	Empty (aka Null)
		 
		 * 	 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 锟斤拷End of Service(151)
		 2.	Empty (aka Null)
		 */
		void setAllFields(DiffEntity parentDiffEntity, DiffEntity foAvailDiff, DiffEntity loAvailDiff, DiffEntity endAvailDiff, DiffEntity endmktAvailDiff, Hashtable table, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + availDiff.getKey() + " " + getKey() + NEWLINE);
			EntityItem parentItem = parentDiffEntity.getCurrentEntityItem();
			boolean SLEORGGRPChaned = false;
			EntityItem curritem = availDiff.getCurrentEntityItem();
			EntityItem previtem = availDiff.getPriorEntityItem();

			//ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability锟� (146).
			anndate = deriveAnnDate(false, debugSb);
			String anndateT1 = deriveAnnDate(true, debugSb);

			if (!anndate.equals(anndateT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			//ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability锟� (146).
			annnumber = deriveAnnNumber(false, debugSb);
			String annnumberT1 = deriveAnnNumber(true, debugSb);

			if (!annnumber.equals(annnumberT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			//FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143 or null.
			firstorder = deriveFIRSTORDER(foAvailDiff, false, debugSb);
			String firstorderT1 = deriveFIRSTORDER(foAvailDiff, true, debugSb);

			if (!firstorder.equals(firstorderT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			//LASTORDER is equal to PUBTO. 			
			// PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
			// get current value
			if (curritem != null) {
				plannedavailability = PokUtils.getAttributeValue(curritem, "EFFECTIVEDATE", ", ", CHEAT, false);
			}
			// get priorvalue if it exists
			String prevdate = CHEAT;
			if (previtem != null) {
				prevdate = PokUtils.getAttributeValue(previtem, "EFFECTIVEDATE", ", ", CHEAT, false);
			}
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.setAllFields plannedavailability: " + plannedavailability + " prevdate: "
				+ prevdate + NEWLINE);

			//			// if diff, set action
			if (!prevdate.equals(plannedavailability)) {
				setAction(UPDATE_ACTIVITY);
			}
			// get STATUS
			if (curritem != null) {
				availStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
				if (availStatus == null) {
					availStatus = CHEAT;
				}
			}
			// get priorvalue if it exists
			String prevStatus = CHEAT;
			if (previtem != null) {
				prevStatus = PokUtils.getAttributeFlagValue(previtem, "STATUS");
				if (prevStatus == null) {
					prevStatus = CHEAT;
				}
			}
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.setAllFields curstatus: " + availStatus + " prevstatus: " + prevStatus
				+ NEWLINE);

			// if diff, set action
			if (!prevStatus.equals(availStatus)) {
				setAction(UPDATE_ACTIVITY);
			}

			//If the country in AVAIL.COUNTRYLIST was newly added or the AVAIL(first order) is newly added, then set Action UPDATE_ACTIVITY
			//If the country in AVAIL.COUNTRYLIST was deleted or AVAIL was deleted, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			//If the AVAIL was updated, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			if (isNewCountry(foAvailDiff, debugSb)) {
				setAction(UPDATE_ACTIVITY);
			}
			//set PUBFROM
			pubfrom = derivePubFrom(parentDiffEntity, foAvailDiff, false, debugSb);
			String pubfromT1 = derivePubFrom(parentDiffEntity, foAvailDiff, true, debugSb);

			if (!pubfrom.equals(pubfromT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			// set PUBTO
			pubto = derivePubTo(parentDiffEntity, loAvailDiff, false, debugSb);
			String pubtoT1 = derivePubTo(parentDiffEntity, loAvailDiff, true, debugSb);
			if (!pubto.equals(pubtoT1)) {
				setAction(UPDATE_ACTIVITY);
			}

			// set WDANNDATE
			wdanndate = deriveWDANNDATE(parentDiffEntity, endmktAvailDiff, false, debugSb);
			String wdanndateT1 = deriveWDANNDATE(parentDiffEntity, endmktAvailDiff, true, debugSb);

			if (!wdanndate.equals(wdanndateT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			//			 set LASTORDER
			lastorder = deriveLastOrder(parentDiffEntity, loAvailDiff, false, debugSb);
			String lastorderT1 = deriveLastOrder(parentDiffEntity, loAvailDiff, true, debugSb);
			if (!lastorder.equals(lastorderT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			//			 set EOSANNDATE
			eosanndate = deriveEOSANNDATE(endAvailDiff, false, debugSb);
			String eosanndateT1 = deriveEOSANNDATE(endAvailDiff, true, debugSb);

			if (!eosanndate.equals(eosanndateT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			// BH set ENDOFSERVICE
			endofservice = deriveENDOFSERVICE(endAvailDiff, false, debugSb);
			String endofserviceT1 = deriveENDOFSERVICE(endAvailDiff, true, debugSb);
			if (!endofservice.equals(endofserviceT1)) {
				setAction(UPDATE_ACTIVITY);
			}
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.setAllFields pubfrom: " + pubfrom + " pubto: " + pubto + " endofservice:"
				+ endofservice + NEWLINE);
			
			if(availRelator.equals("SVCMODAVAIL")){
				
				SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, parentItem, availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", country, debugSb);			    
			}
			else{
				//SLEORGGRPChaned = SLEORGGRP.hasChanges(table, parentItem, availDiff, "D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", country, debugSb);
				SLEORGGRPChaned = SLEORGGRP.hasChanges(table, parentItem, availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", country, debugSb);
			}		
			if (SLEORGGRPChaned){
				setAction(UPDATE_ACTIVITY);
			}
		}

		/****************************
		 * all the new added country that in First order Avail set the action is update.
		 return whether the country is new.
		 */

		private boolean isNewCountry(DiffEntity diffEntity, StringBuffer debugSb) {

			boolean isNew = false;
			if (diffEntity != null && diffEntity.isNew()) {
				isNew = true;
				ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.setAllFields isNewAvail" + diffEntity.getKey() + NEWLINE);
			} else if (diffEntity != null && !diffEntity.isDeleted()) {
				EANFlagAttribute fAtt2 = null;
				EANFlagAttribute fAtt1 = null;
				EntityItem currentitem = diffEntity.getCurrentEntityItem();
				EntityItem prioritem = diffEntity.getPriorEntityItem();
				if (currentitem != null) {
					fAtt2 = (EANFlagAttribute) currentitem.getAttribute("COUNTRYLIST");
				}
				if (prioritem != null) {
					fAtt1 = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				}
				if (fAtt1 != null && !fAtt1.isSelected(country) && fAtt2 != null && fAtt2.isSelected(country)) {
					isNew = true;
					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.setAllFields isNewCountry" + diffEntity.getKey() + NEWLINE);
				}
			}
			return isNew;

		}

		/****************************
		 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 锟斤拷End of Service (151)
		 2.	Empty (aka Null)

		 */
		private String deriveENDOFSERVICE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEndOfService " + " eofAvailDiff: "
				+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + " findT1:" + findT1 + NEWLINE);

			String thedate = CHEAT;
			if (findT1) { // find previous derivation
				// try to get it from the lastorder avail
				if (endAvailDiff != null && !endAvailDiff.isNew()) {
					EntityItem item = endAvailDiff.getPriorEntityItem();
					//add the entry check
					if (item != null) {
						EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);

//							ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEndOfService eofavail thedate: " + thedate
//								+ " COUNTRYLIST: " + PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
						}
					} else {
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEndOfService eofAvail priorEnityitem: " + item + NEWLINE);
					}
				}

			} else { //find current derivation

				// try to get it from the lastorder avail
				if (endAvailDiff != null && !endAvailDiff.isDeleted()) {
					EntityItem item = endAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					//add the entry check

					if (fAtt != null && fAtt.isSelected(country)) {
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
					}
//					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEndOfService eofavail thedate: " + thedate + " COUNTRYLIST: "
//						+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);

				}
			}

			return thedate;
		}

		/****************************
		 * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBTO
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 3 .	Empty (aka Null)
		 
		 */
		private String derivePubTo(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.derivePubTo " + " loAvailDiff: "
				+ (loAvailDiff == null ? "null" : loAvailDiff.getKey()) + " findT1:" + findT1 + NEWLINE);

			String thedate = CHEAT;
			if (findT1){
				// find previous derivation
				
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isNew()){
					EntityItem item = loAvailDiff.getPriorEntityItem();
					if (item!=null){
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
//					ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo loavail thedate: "+thedate+
//						" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				    }else{
				    	ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo loavail priorEnityitem: " + item +NEWLINE);
				    }
				}
				//TODO 3.	MODEL. WTHDRWEFFCTVDATE
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isNew()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem prioritem = parentDiffEntity.getPriorEntityItem();
							thedate = PokUtils.getAttributeValue(prioritem, "WTHDRWEFFCTVDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo WTHDRWEFFCTVDATE of prior SVCMODEL thedate: "+thedate +NEWLINE);	
						}
					}
				}
			}else{ //find current derivation			
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isDeleted()){
					EntityItem item = loAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
//					ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo loavail thedate: "+thedate+
//						" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}	
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem currentitem = parentDiffEntity.getCurrentEntityItem();
							thedate = PokUtils.getAttributeValue(currentitem, "WTHDRWEFFCTVDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo WTHDRWEFFCTVDATE of current SVCMODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}
			return thedate;
		}

		/****************************
		 * 	<PUBFROM>
		 1.	MDLCATLGOR-d: CATLGOR.PUBFROM  because there is no ANDIEN on SVCMOD , so CTLGOR will not be consider .
		 The first applicable / available date is used.
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability (146).
		 4.	Empty (aka Null)

		 */
		private String derivePubFrom(DiffEntity parentDiffEntity, DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.derivePubFrom availDiff: " + availDiff.getKey() + " foAvailDiff: "
				+ (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
            //TODO 20120117.doc 
			//2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
			if (findT1){
			    if (foAvailDiff != null && !foAvailDiff.isNew()){
				    EntityItem item = foAvailDiff.getPriorEntityItem();
				    EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
				    if (fAtt!= null && fAtt.isSelected(country)){
					    thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);				
				    }
				    ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom foavail thedate: "+thedate +NEWLINE);
				}	
			    //3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�(146).
				if (CHEAT.equals(thedate)){
					// try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
					if( availDiff != null && !availDiff.isNew()){
						EntityItem item = availDiff.getPriorEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
						Vector relatorVec = item.getDownLink();
						 for (int ii=0; ii<relatorVec.size(); ii++){
	                        	EntityItem availanna = (EntityItem)relatorVec.elementAt(ii);
	                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
	                        		Vector annVct = availanna.getDownLink();
	                        		EntityItem anna = (EntityItem)annVct.elementAt(0);
	                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
//	                                ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom looking for downlink of AVAILANNA : Announcement "
//	 	                        	+ (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);	
	                        		}                  	   
	                        	}
						 }
					}
				}
				//4.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){// try to get it from the firstorder avail
				    if (availDiff != null && !availDiff.isNew()){
					    EntityItem item = availDiff.getPriorEntityItem();
					    EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					    if (fAtt!= null && fAtt.isSelected(country)){
						    thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);				
					    }
					    ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom effectivedate of planedavail thedate: "+thedate +NEWLINE);
					    }	
				}
				//5.	MODEL.ANNDATE only SVCMOD has ANNDATE
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isNew()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem prioritem = parentDiffEntity.getPriorEntityItem();
							thedate = PokUtils.getAttributeValue(prioritem, "ANNDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom anndate of prior MODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}else{
//	          2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
			    if (foAvailDiff != null && !foAvailDiff.isDeleted()){
				    EntityItem item = foAvailDiff.getCurrentEntityItem();
				    EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
				    if (fAtt!= null && fAtt.isSelected(country)){
					    thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);	
				    }
//				    ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom foavail thedate: "+thedate+
//					    " COUNTRYLIST: " + PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}
			    //3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�(146).
				if (CHEAT.equals(thedate)){
					if(availDiff != null && !availDiff.isDeleted()){
						EntityItem item = availDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
						Vector annVct = item.getDownLink();
						 for (int ii=0; ii<annVct.size(); ii++){
	                        	EntityItem availanna = (EntityItem)annVct.elementAt(ii);
	                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
	                        		Vector annceVct = availanna.getDownLink();
	                        		EntityItem anna = (EntityItem)annceVct.elementAt(0);
	                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
//	                                ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom looking for downlink of AVAILANNA : Announcement "
//	                                + (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);
	                        	}
						 }
						}
					}
				}
//				4.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){// try to get it from the firstorder avail
				    if (availDiff != null && !availDiff.isDeleted()){
					    EntityItem item = availDiff.getCurrentEntityItem();
					    EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					    if (fAtt!= null && fAtt.isSelected(country)){
						    thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);				
					    }
					    ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom effectivedate of current planedavail thedate: "+thedate +NEWLINE);
					    }	
				}
				//5.	MODEL.ANNDATE only SVCMOD has ANNDATE
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem currentitem = parentDiffEntity.getCurrentEntityItem();
							thedate = PokUtils.getAttributeValue(currentitem, "ANNDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom anndate of current MODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}
			return thedate;
		}

		/****************************
		 * 	<ANNNUMBER>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability锟� (146).
		 2.	Empty (aka Null)

		 */
		private String deriveAnnNumber(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			if (findT1) { // find previous derivation
				//try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
				if (availDiff != null && !availDiff.isNew()) {
					EntityItem item = availDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector relatorVec = item.getDownLink();
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annVct.elementAt(0);
								thedate = PokUtils.getAttributeValue(anna, "ANNNUMBER", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveAnnNumber looking for downlink of AVAILANNA : Announcement "
										+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
											+ anna.getKey() : anna.getKey()) + NEWLINE);
							}
						}
					}
				}
			} else {
				if (availDiff != null && !availDiff.isDeleted()) {
					EntityItem item = availDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector annVct = item.getDownLink();
						for (int ii = 0; ii < annVct.size(); ii++) {
							EntityItem availanna = (EntityItem) annVct.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annceVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annceVct.elementAt(0);
								thedate = PokUtils.getAttributeValue(anna, "ANNNUMBER", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveAnnNumber looking for downlink of AVAILANNA : Announcement "
										+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
											+ anna.getKey() : anna.getKey()) + NEWLINE);
							}
						}
					}
				}

			}
			return thedate;
		}

		/****************************
		 * 	<ANNDATE>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability锟� (146).
		 2.	Empty (aka Null)

		 */
		private String deriveAnnDate(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			if (findT1) { // find previous derivation
				//try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
				if (availDiff != null && !availDiff.isNew()) {
					EntityItem item = availDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector relatorVec = item.getDownLink();
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annVct.elementAt(0);
								thedate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveANNDATE looking for downlink of AVAILANNA : Announcement "
										+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
											+ anna.getKey() : anna.getKey()) + NEWLINE);
							}
						}
					}
				}
			} else {
				if (availDiff != null && !availDiff.isDeleted()) {
					EntityItem item = availDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector annVct = item.getDownLink();
						for (int ii = 0; ii < annVct.size(); ii++) {
							EntityItem availanna = (EntityItem) annVct.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annceVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annceVct.elementAt(0);
								thedate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveANNDATE looking for downlink of AVAILANNA : Announcement "
										+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
											+ anna.getKey() : anna.getKey()) + NEWLINE);
							}
						}
					}
				}
			}
			return thedate;
		}

		/****************************
		 * 	<FIRSTORDER>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淔irst Order锟�
		 2.	<ANNDATE>
		 //TODO UDPATE as following sequence
           1.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淔irst Order鈥�
           2.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
           3.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
           4.	<ANNDATE>

		 */
		private String deriveFIRSTORDER(DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveFIRSTORDER availDiff: " + availDiff.getKey() + " foAvailDiff: "
				+ (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (findT1){ // find previous derivation
				 //find current derivation
				//1.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淔irst Order鈥�
				if (foAvailDiff != null && !foAvailDiff.isNew()) {
					EntityItem item = foAvailDiff.getPriorEntityItem();
					if (item != null) {
						EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
						}
//						ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER effectivedate of prior loavail thedate: " + thedate + " COUNTRYLIST: "
//							+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
					} else {
						ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER loavail priorEnityitem: " + item + NEWLINE);
					}
				}
				//2.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){
					// try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
					if( availDiff != null && !availDiff.isNew()){
						EntityItem item = availDiff.getPriorEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
						     Vector relatorVec = item.getDownLink();
							 for (int ii=0; ii<relatorVec.size(); ii++){
		                        	EntityItem availanna = (EntityItem)relatorVec.elementAt(ii);
		                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
		                        		Vector annVct = availanna.getDownLink();
		                        		EntityItem anna = (EntityItem)annVct.elementAt(0);
		                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
//		                                ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER looking for downlink of AVAILANNA : Announcement "
//		 	                        	+ (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);	
		                        	}                  	   
		                      }
						}
					}
				}
				//3.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){
					if (availDiff != null && !availDiff.isNew()) {
						EntityItem item = availDiff.getPriorEntityItem();
						if (item != null) {
							EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
							if (fAtt != null && fAtt.isSelected(country)) {
								thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
							}
//							ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER prior planedavail effectivedate thedate: " + thedate + " COUNTRYLIST: "
//								+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
						} else {
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER effectivedate of planedavail priorEnityitem: " + item + NEWLINE);
						}
					}
				}
			    ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER  thedate: "+thedate +NEWLINE); 
			}else{
				//1.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淔irst Order鈥�
				if (foAvailDiff != null && !foAvailDiff.isDeleted()){
					EntityItem item = foAvailDiff.getCurrentEntityItem();
					if (item != null) {
						EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
						}
//						ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER effectivedate of current foavail thedate: " + thedate + " COUNTRYLIST: "
//							+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
					} else {
						ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER foavail priorEnityitem: " + item + NEWLINE);
					}
				}
				//2.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){
					if(availDiff != null  && !availDiff.isDeleted()){
						EntityItem item = availDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
						     Vector annVct = item.getDownLink();
							 for (int ii=0; ii<annVct.size(); ii++){
		                        	EntityItem availanna = (EntityItem)annVct.elementAt(ii);
		                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
		                        		Vector annceVct = availanna.getDownLink();
		                        		EntityItem anna = (EntityItem)annceVct.elementAt(0);
		                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
//		                                ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER looking for downlink of AVAILANNA : Announcement "
//		                                + (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);
		                        	}
							 }
					    }
				    }
				}
				//3.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
				if (CHEAT.equals(thedate)){
					if (availDiff != null && !availDiff.isDeleted()) {
						EntityItem item = availDiff.getCurrentEntityItem();
						if (item != null) {
							EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
							if (fAtt != null && fAtt.isSelected(country)) {
								thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
							}
//							ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER current planedavail effectivedate thedate: " + thedate + " COUNTRYLIST: "
//								+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
						} else {
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER planedavail current Enityitem: " + item + NEWLINE);
						}
					}
				}
				
				ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER foavail thedate: "+thedate + NEWLINE);
			}
			return thedate;
		}

		/****************************
		 * 	<EOSANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淓nd of Service锟� (151) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13)
		 2.	Empty (aka Null)

		 */
		private String deriveEOSANNDATE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE availDiff: " + availDiff.getKey() + " endAvailDiff: "
				+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (findT1) { // find previous derivation
				//find current derivation
				// try to get it from the endAvailDiff avail
				if (endAvailDiff != null && !endAvailDiff.isNew()) {
					EntityItem item = endAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					//add the entry check
					if (fAtt != null && fAtt.isSelected(country)) {

						Vector relatorVec = item.getDownLink();

//						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAIL" + " annVct.size: "
//							+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
//							+ item.getDownLinkCount() + NEWLINE);
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
//
//							ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAIL "
//								+ availanna.getKey() + "entitytype is: " + availanna.getEntityType() + NEWLINE);

							if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
								// get get Announcement. it could return multiple results. Either check that you have the right one.
								Vector annVct = availanna.getDownLink();
								for (int iii = 0; iii < annVct.size(); iii++) {
									EntityItem anna = (EntityItem) annVct.elementAt(iii);
//									ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAILANNA "
//										+ anna.getKey() + "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
//										+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
									EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
									if (fANNAtt != null && fANNAtt.isSelected("13")) {
										thedate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
									} else {
										ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE ANNTYPE: "
											+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal "
											+ "End Of Life - Discontinuance of service(13)" + NEWLINE);
									}
								}
							} else {
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE no downlink of AVAILANNA was found"
									+ NEWLINE);
							}

						}

					}
				}

			} else {
				if (endAvailDiff != null && !endAvailDiff.isDeleted()) {
					EntityItem item = endAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					//add the entry check
					if (fAtt != null && fAtt.isSelected(country)) {

						Vector relatorVec = item.getDownLink();

//						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAIL" + " annVct.size: "
//							+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
//							+ item.getDownLinkCount() + NEWLINE);
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
//
//							ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAIL "
//								+ availanna.getKey() + "entitytype is: " + availanna.getEntityType() + NEWLINE);

							if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
								// get get Announcement. it could return multiple results. Either check that you have the right one.
								Vector annVct = availanna.getDownLink();
								for (int iii = 0; iii < annVct.size(); iii++) {
									EntityItem anna = (EntityItem) annVct.elementAt(iii);
//									ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE looking for downlink of AVAILANNA "
//										+ anna.getKey() + "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
//										+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
									EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
									if (fANNAtt != null && fANNAtt.isSelected("13")) {
										thedate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
									} else {
										ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE ANNTYPE: "
											+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal "
											+ "End Of Life - Discontinuance of service(13)" + NEWLINE);
									}
								}
							} else {
								ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveEOSANNDATE no downlink of AVAILANNA was found"
									+ NEWLINE);
							}

						}

					}
				}

			}
			return thedate;
		}

		/****************************
		 * 	<WDANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		 2.	Empty (aka Null)

		 */
		private String deriveWDANNDATE(DiffEntity parentDiffEntity, DiffEntity endmktAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveWDANNDATE availDiff: " + availDiff.getKey() + " loAvailDiff: "
				+ (endmktAvailDiff == null ? "null" : endmktAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (findT1){ // find previous derivation
				 //find current derivation
				// try to get it from the endmktAvailDiff avail
				if (endmktAvailDiff != null && !endmktAvailDiff.isNew()){
					EntityItem item = endmktAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){

						Vector relatorVec = item.getDownLink();

//						ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAIL" + " annVct.size: "
//							+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
//							+ item.getDownLinkCount() + NEWLINE);
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);

//							ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAIL " + availanna.getKey()
//								+ "entitytype is: " + availanna.getEntityType() + NEWLINE);

							if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
								// get get Announcement. it could return multiple results. Either check that you have the right one.
								Vector annVct = availanna.getDownLink();
								for (int iii = 0; iii < annVct.size(); iii++) {
									EntityItem anna = (EntityItem) annVct.elementAt(iii);
//									ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAILANNA " + anna.getKey()
//										+ "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
//										+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
									EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
									if (fANNAtt != null && fANNAtt.isSelected("14")) {
										thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
									} else {
										ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE ANNTYPE: "
											+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal " + "14(End Of Life - Withdrawal from mktg)" + NEWLINE);
									}
								}
							} else {
								ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE no downlink of AVAILANNA was found" + NEWLINE);
							}

						}

					}
				}
				//TODO 2.	MODEL.WITHDRAWDATE 2O120117.doc
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isNew()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem prioritem = parentDiffEntity.getPriorEntityItem();
							thedate = PokUtils.getAttributeValue(prioritem, "WITHDRAWDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE WITHDRAWDATE of prior MODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}else{
				if (endmktAvailDiff != null && !endmktAvailDiff.isDeleted()){
					EntityItem item = endmktAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){

						Vector relatorVec = item.getDownLink();

//						ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAIL" + " annVct.size: "
//							+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
//							+ item.getDownLinkCount() + NEWLINE);
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);

//							ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAIL " + availanna.getKey()
//								+ "entitytype is: " + availanna.getEntityType() + NEWLINE);

							if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
								// get get Announcement. it could return multiple results. Either check that you have the right one.
								Vector annVct = availanna.getDownLink();
								for (int iii = 0; iii < annVct.size(); iii++) {
									EntityItem anna = (EntityItem) annVct.elementAt(iii);
//									ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE looking for downlink of AVAILANNA " + anna.getKey()
//										+ "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
//										+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
									EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
									if (fANNAtt != null && fANNAtt.isSelected("14")) {
										thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
									} else {
										ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE ANNTYPE: "
											+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal " + "14(End Of Life - Withdrawal from mktg)" + NEWLINE);
									}
								}
							} else {
								ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE no downlink of AVAILANNA was found" + NEWLINE);
							}

						}

					}
				}
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem curritem = parentDiffEntity.getCurrentEntityItem();
							thedate = PokUtils.getAttributeValue(curritem, "WITHDRAWDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE WITHDRAWDATE of current MODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}		
			return thedate;
		}

		/***********************************************************************
		 * <LASTORDER> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149) 2 .
		 * Empty (aka Null)
		 * 
		 */
		private String deriveLastOrder(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveLastOrder " + " loAvailDiff: "
				+ (loAvailDiff == null ? "null" : loAvailDiff.getKey()) + " findT1:" + findT1 + NEWLINE);

			String thedate = CHEAT;
			if (findT1) {
				// find previous derivation
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isNew()) {
					EntityItem item = loAvailDiff.getPriorEntityItem();
					//add the entry check
					if (item != null) {
						EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
						}
//						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveLastOrder loavail thedate: " + thedate + " COUNTRYLIST: "
//							+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
					} else {
						ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveLastOrder loavail priorEnityitem: " + item + NEWLINE);
					}
				}
				//TODO 20120117 2.	MODEL.WTHDRWEFFCTVDATE
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isNew()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem prioritem = parentDiffEntity.getPriorEntityItem();
							thedate = PokUtils.getAttributeValue(prioritem, "WTHDRWEFFCTVDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveLastOrder WTHDRWEFFCTVDATE of prior MODEL thedate: "+thedate +NEWLINE);
						}	
					}
				}
			} else { // find current derivation
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isDeleted()) {
					EntityItem item = loAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					//add the entry check
					if (fAtt != null && fAtt.isSelected(country)) {
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
					}
//					ABRUtil.append(debugSb,"XMLSVCSEOAVAILElem.deriveLastOrder loavail thedate: " + thedate + " COUNTRYLIST: "
//						+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST") + NEWLINE);
				}
				if (CHEAT.equals(thedate)){
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
							EntityItem curritem = parentDiffEntity.getCurrentEntityItem();
							thedate = PokUtils.getAttributeValue(curritem, "WTHDRWEFFCTVDATE", "", CHEAT, false);
							ABRUtil.append(debugSb,"XMLAVAILElem.deriveLastOrder WTHDRWEFFCTVDATE of current MODEL thedate: "+thedate +NEWLINE);
						}
					}
				}
			}
			return thedate;
		}

		String getAction() {
			return action;
		}

		String getCountry() {
			return country;
		}

		//String getShipDate() { return earliestshipdate;}
		String getPubFrom() {
			return pubfrom;
		}

		String getPubTo() {
			return pubto;
		}

		String getEndOfService() {
			return endofservice;
		}

		String getAvailStatus() {
			return availStatus;
		}

		String getAnndate() {
			return anndate;
		}

		String getFirstorder() {
			return firstorder;
		}

		String getPlannedavailability() {
			return plannedavailability;
		}

		String getWdanndate() {
			return wdanndate;
		}

		String getLastorder() {
			return lastorder;
		}

		String getEosanndate() {
			return eosanndate;
		}

		String getAnnnumber() {
			return annnumber;
		}

		boolean isDeleted() {
			return DELETE_ACTIVITY.equals(action);
		}

		String getKey() {
			return country;
		}

		void dereference() {
			availDiff = null;
			action = null;
			country = null;
			availStatus = null;
			pubfrom = null;
			pubto = null;
			endofservice = null;
			anndate = null;
			firstorder = null;
			plannedavailability = null;
			wdanndate = null;
			lastorder = null;
			eosanndate = null;
			annnumber = null;
		}

		public String toString() {
			return availDiff.getKey() + " " + getKey() + " action:" + action;
		}
	}
}
