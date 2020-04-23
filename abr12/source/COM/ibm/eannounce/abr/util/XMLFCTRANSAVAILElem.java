//Licensed Materials -- Property of IBM
// 
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.PDGUtility;
import COM.ibm.eannounce.objects.SBRException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/*******************************************************************************
 * Constructor for <AVAILABILITYLIST> elements <AVAILABILITYLIST> 2
 * <AVAILABILITYELEMENT> 3 <AVAILABILITYACTION> </AVAILABILITYACTION> 4 derived
 * AvailabilityAction <STATUS> </STATUS> 4 AVAIL STATUS <COUNTRY_FC>
 * </COUNTRY_FC> 4 AVAIL COUNTRYLIST <ANNDATE> </ANNDATE> 4 <ANNNUMBER>
 * </ANNNUMBER> 4 <FIRSTORDER> </FIRSTORDER> 4 <PLANNEDAVAILABILITY>
 * </PLANNEDAVAILABILITY> 4 <PUBFROM> </PUBFROM> 4 <PUBTO> </PUBTO> 4
 * <WDANNDATE> </WDANNDATE> 4 <LASTORDER> </LASTORDER> 4 <EOSANNDATE>
 * </EOSANNDATE> 4 <ENDOFSERVICEDATE> </ENDOFSERVICEDATE> 4 AVAIL EFFECTIVEDATE
 * </AVAILABILITYELEMENT> 3 </AVAILABILITYLIST> 2
 */
public class XMLFCTRANSAVAILElem extends XMLElem {
	
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
	public XMLFCTRANSAVAILElem() {
		super("AVAILABILITYELEMENT");
	}

	/***************************************************************************
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
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {

		// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some
		// may be deleted
		EntityItem currItem = parentItem.getCurrentEntityItem();
		String parenttype = currItem.getEntityType();
		Vector plnAvlVct = getPlannedAvails(table, debugSb);
		if (plnAvlVct.size() > 0) { // must have planned avail for any of this
		// get parent Model for SLEORGGRPLIST.
			EntityItem COFCATentity = null;
			if ("MODELCONVERT".equals(parenttype) ) {
				try {
					EntityItem[] aei = doSearch(dbCurrent, currItem, debugSb);
					if (aei != null && aei.length > 0) {
						COFCATentity = aei[0];
						ABRUtil.append(debugSb,"XMLFCTRANSAVAILElem.addElements get FROMMODEL " + COFCATentity.getKey() + NEWLINE);
					}
				} catch (SBRException e) {
					throw new MiddlewareException("Exception occur when get FROMMODEL doSearch(). " + e.getMessage());
				}
			} else {
				// ParentItem type is FEATURETRANSACTION
				COFCATentity = currItem;
			}
	
			boolean compatModel = false;
		    boolean isExistfinal = false;
	        compatModel = AvailUtil.iscompatmodel();
			
	        if(!compatModel){
	        	isExistfinal = AvailUtil.isExistFinal(dbCurrent,  parentItem,   "STATUS", debugSb);
	        }	
			//build T1 country list
			TreeMap ctryAudElemMap = new TreeMap();
			for (int i = 0; i < plnAvlVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
				buildCtryAudRecs(ctryAudElemMap, availDiff, true, debugSb);
			}// end each planned avail

			//build T2 country list
			for (int i = 0; i < plnAvlVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
				buildCtryAudRecs(ctryAudElemMap, availDiff, false, debugSb);
			}// end each planned avail
			
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			while (itr.hasNext()) {
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				// Rows marked as Delete do not need further updating and the
				// Action should not be changed by further updating.
				//if (!ctryAudRec.isDeleted()) {
					// find firstorder avail for this country
				DiffEntity foAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRec
					.getCountry(), debugSb);
				// find lastorder avail for this country
				DiffEntity loAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRec
					.getCountry(), debugSb);
				DiffEntity endAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRec
					.getCountry(), debugSb);
				DiffEntity endmktAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRec
					.getCountry(), debugSb);
				//ctryAudRec.setAllFields(parentItem, foAvailDiff, loAvailDiff, endAvailDiff, table, debugSb);
				ctryAudRec.setAllFields(foAvailDiff, loAvailDiff, endAvailDiff, endmktAvailDiff, table, COFCATentity, parentItem, isExistfinal,  compatModel, debugSb);
				//}
				if (ctryAudRec.isDisplayable()||ctryAudRec.isrfrDisplayable()) {
					createNodeSet(table, document, parent, COFCATentity, ctryAudRec, debugSb);
				} else {
					ABRUtil.append(debugSb,"XMLAVAILElem.addElements no changes found for " + ctryAudRec + NEWLINE);
				}
				ctryAudRec.dereference();
			}

			// release memory
			ctryAudElemMap.clear();
			Vector annVct = (Vector) table.get("ANNOUNCEMENT");
			Vector availVct = (Vector) table.get("AVAIL");
			if (annVct != null) {
				annVct.clear();
			}
			if (availVct != null) {
				availVct.clear();
			}
		} else {
			ABRUtil.append(debugSb,"XMLAVAILElem.addElements no planned AVAILs found" + NEWLINE);
		}
	}
	 /**********************************************************************************
	    * search for entity to get entityid for xml output
	    *
	    *@param dbCurrent Database
	    *@param item EntityItem
	    *@param debugSb StringBuffer for debug output
	    */
	    protected EntityItem[] doSearch(Database dbCurrent, EntityItem item, StringBuffer debugSb)
	    throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
	    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	    COM.ibm.eannounce.objects.SBRException
	    {
	        String searchAction = "SRDMODEL4";
	        Vector srcVct = new Vector(1);
	        Vector sinkVct = new Vector(1);
	        String srchType = "MODEL";
	        
	        //srchElem.addSearchAttr("FROMMACHTYPE", "MACHTYPEATR");
			//srchElem.addSearchAttr("FROMMODEL", "MODELATR");
			srcVct.addElement("FROMMACHTYPE");
			sinkVct.addElement("MACHTYPEATR");			
			srcVct.addElement("FROMMODEL");
			sinkVct.addElement("MODELATR");	
	        			
	        PDGUtility pdgUtility = new PDGUtility();

	        StringBuffer sb = new StringBuffer();
	        for (int i=0; i<srcVct.size(); i++){
				if (sb.length()>0){
					sb.append(";");
				}
				String code = srcVct.elementAt(i).toString();
				String sink = sinkVct.elementAt(i).toString();
				sb.append("map_"+sink+"="+PokUtils.getAttributeValue(item, code,", ", "", false));
			}

	        ABRUtil.append(debugSb,"XMLFCTRANSAVAILElem.doSearch Using "+searchAction+" to search for "+srchType+" using "+sb.toString()+NEWLINE);

	        EntityItem[] aei = null;
	        aei = pdgUtility.dynaSearch(dbCurrent, item.getProfile(), null, searchAction, srchType, sb.toString());

			return aei;
		}
	/***************************************************************************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, EntityItem COFCATentity,  CtryAudRecord ctryAudRec, StringBuffer debugSb) {
		if(ctryAudRec.isDisplayable()){
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
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			
			if (COFCATentity!=null&&"MODEL".equals(COFCATentity.getEntityType())){
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATentity, ctryAudRec.availDiff, "D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			}else{
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATentity, ctryAudRec.availDiff, "D:FEATURETRNAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);	
			}
		}
		if(ctryAudRec.isrfrDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);
			
			// add child nodes
			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfraction()));
			elem.appendChild(child);
			child = (Element) document.createElement("STATUS");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfravailStatus()));
			elem.appendChild(child);
			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfranndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrannnumber()));
			elem.appendChild(child);
			child = (Element) document.createElement("FIRSTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrfirstorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrplannedavailability()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBFROM");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubfrom()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBTO");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubto()));
			elem.appendChild(child);
			child = (Element) document.createElement("WDANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrwdanndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
			
			if ("MODEL".equals(COFCATentity.getEntityType())){
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATentity, ctryAudRec.availDiff, "D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			}else{
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATentity, ctryAudRec.availDiff, "D:FEATURETRNAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);	
			}
		}
		
	}

	/**
	 * ****************** this method has changed for BH. Create rows in the
	 * table as follows: Insert one row for each Audience in MODEL.AUDIEN & each
	 * Country in AVAIL.COUNTRYLIST where AVAILTYPE = 146 If the AVAIL was
	 * deleted, set Action = Delete If the AVAIL was added or updated, set
	 * Action = Update
	 * 
	 * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
	 * If AVAIL.COUNTRYLIST has a country deleted, set that row's Action =
	 * Delete
	 * 
	 * Note: Rows marked as Delete do not need further updating and the Action
	 * should not be changed by further updating. If any of the following steps
	 * have data that do not match an existing row in this table, ignore that
	 * data.
	 */
	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, boolean T1, StringBuffer debugSb){


		ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs build T1 country list " + T1 + " " + availDiff.getKey()+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (T1){
			if (!availDiff.isNew()){ // If the AVAIL was deleted, set Action = Delete
				// mark all records as delete
				EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for deleted / update avail at T1: ctryAtt "+
							PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") +NEWLINE);
				if (ctryAtt!=null){
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++){
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							String mapkey = ctryVal;
							if (ctryAudElemMap.containsKey(mapkey)){
								// dont overwrite
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted / update "+availDiff.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(DELETE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							}
						}
					}				
				}
			}			
		}else {
			if (!availDiff.isDeleted()){ //If the AVAIL was added or updated, set Action = Update
				// mark all records as update			
				EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt "+
						PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+ 
				        PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") +NEWLINE);
				if (ctryAtt!=null){
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++){
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();					
							String mapkey = ctryVal;
							if (ctryAudElemMap.containsKey(mapkey)){
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								if (DELETE_ACTIVITY.equals(rec.action)){
									ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new /udpate"+availDiff.getKey()+
										" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
										rec.setUpdateAvail(availDiff);
										rec.setAction(CHEAT);
								}
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for new:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
				}
			}
		}
	}

	/***************************************************************************
	 * get planned avails - availtype cant be changed
	 */
	private Vector getPlannedAvails(Hashtable table, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("AVAIL");

		ABRUtil.append(debugSb,"XMLAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}

		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLAVAILElem.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey() + " AVAILTYPE: "
					+ PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			} else {
				ABRUtil.append(debugSb,"XMLAVAILElem.getPlannedAvails checking[" + i + "]:" + diffitem.getKey() + " AVAILTYPE: "
					+ PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			}
		}

		return avlVct;
	}

	/***************************************************************************
	 * get entity with specified values - should only be one could be two if one
	 * was deleted and one was added, but the added one will override and be an
	 * 'update'
	 */
	private DiffEntity getEntityForAttrs(Hashtable table, String etype, String attrCode, String attrVal, String attrCode2,
		String attrVal2, StringBuffer debugSb) {
		DiffEntity diffEntity = null;
		Vector allVct = (Vector) table.get(etype);

		ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs looking for " + attrCode + ":" + attrVal + " and " + attrCode2 + ":"
			+ attrVal2 + " in " + etype + " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}
		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey() + " "
					+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
					+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
				if (fAtt != null && fAtt.isSelected(attrVal)) {
					fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
					if (fAtt != null && fAtt.isSelected(attrVal2)) {
						diffEntity = diffitem; // keep looking for one that is
												// not deleted
					}
				}
			} else {
				if (diffitem.isNew()) {
					ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
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
					ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking[" + i + "]: current " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem;
							break;
						}
					}
					ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem;
							// break; see if there is another that is current
						}
					}
				}
			}
		}

		return diffEntity;
	}

	/***************************************************************************
	 * one for every AVAIL.COUNTRYLIST where availtype='planned availbility(146)
	 * include the avails (delete,new and update)
	 * 
	 */
	private static class CtryAudRecord extends CtryRecord{
		public String country;
		private DiffEntity availDiff;


		CtryAudRecord(DiffEntity diffitem, String ctry) {
			super(null);
			country = ctry;
			availDiff = diffitem;
		}

		void setUpdateAvail(DiffEntity avl) {
			availDiff = avl;// allow replacement
			setAction(UPDATE_ACTIVITY);
		}
		/***********************************************************************
		 * a) ID 42.00 <ANNDATE> 1. ANNOUNCEMENT.ANNDATE 2. PRODSTRUCT.ANNDATE
		 * 3. Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR MODEL.ANNDATE if
		 * SWFEATURE). b) ID 42.10 <ANNNUMBER> 1. ANNOUNCEMENT.ANNNUMBR from the
		 * ANNOUNCEMENT used for the preceding token <ANNDATE> 2. empty
		 * 
		 * c) ID 43.00 <FIRSTORDER> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE
		 * “First Order” 2. ANNOUNCEMENT.ANNDATE 3. PRODSTRUCT.GENAVAILDATE 4.
		 * MODEL.GENAVAILDATE d) ID 44.00 <PLANNEDAVAILABILITY> 1.
		 * AVAIL.EFFECTIVEDATE where AVAILTYPE “Planned Availability” 2.
		 * PRODSTRUCT.GENAVAILDATE 3. Max{FEATURE.GENAVAILDATE;
		 * MODEL.GENAVAILDATE} e) ID 45.00 <PUBFROM> 1. ANNOUNCEMENT.ANNDATE 2.
		 * PRODSTRUCT.ANNDATE 3. Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR
		 * MODEL.ANNDATE if SWFEATURE).
		 * 
		 * f) ID 46.00 <PUBTO> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE = “Last
		 * Order” 2. PRODSTRUCT. WTHDRWEFFCTVDATE 3. Min(MODEL.
		 * WTHDRWEFFCTVDATE; SWFEATURE. WITHDRAWDATEEFF_T; FEATURE.
		 * WITHDRAWDATEEFF_T} g) ID 47.00 <WDANNDATE> 1. ANNOUNCEMENT.ANNDATE
		 * where AVAIL.AVAILTYPE = “Last Order” 2. PRODSTRUCT. WITHDRAWDATE 3.
		 * Min{MODEL. WITHDRAWDATE; FEATURE. WITHDRAWANNDATE_T; SWFEATURE.
		 * WITHDRAWANNDATE_T} h) ID 48.00 <LASTORDER> Same as <PUBTO> i) ID
		 * 49.00 <EOSANNDATE> Empty
		 * 
		 * j) ID 50.00 <ENDOFSERVICEDATE> Empty
		 */
		void setAllFields(DiffEntity foAvailDiff, DiffEntity loAvailDiff, DiffEntity endAvailDiff, DiffEntity endmktAvailDiff, Hashtable table,EntityItem COFCATentity,DiffEntity parentItem,
				boolean isExistfinal, boolean compatModel, StringBuffer debugSb) {

			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for availDiff " + (availDiff == null ? "null" : availDiff.getKey())
				+ NEWLINE);
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for COFCATentity " + (COFCATentity == null ? "null " : COFCATentity.getKey())
					+ NEWLINE);
			
			availStatus = "0020";
            rfravailStatus = "0040";

            // ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where
			// AVAIL.AVAILTYPE = “Planned Availability” (146).
			String[] anndates = deriveAnnDate(false, parentItem, debugSb);
			String[] anndatesT1 = deriveAnnDate(true, parentItem, debugSb);

			// ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where
			// AVAIL.AVAILTYPE = “Planned Availability” (146).
			String[] annnumbers = deriveAnnNumber(false, debugSb);
			String[] annnumbersT1 = deriveAnnNumber(true, debugSb);

			// FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143
			// or null.
			String[] firstorders = deriveFIRSTORDER(false, parentItem, foAvailDiff, debugSb);
			String[] firstordersT1 = deriveFIRSTORDER(true, parentItem, foAvailDiff, debugSb);

			// PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE
			// = "Planned Availability" (146)
			String[] plannedavailabilitys = derivePLANNEDAVAILABILITY(false, parentItem, debugSb);
			String[] plannedavailabilitysT1 = derivePLANNEDAVAILABILITY(true, parentItem, debugSb);

			// set PUBFROM
			String[] pubfroms = derivePubFrom(false, foAvailDiff, parentItem, debugSb);
			String[] pubfromsT1 = derivePubFrom(true, foAvailDiff, parentItem, debugSb);

			// set PUBTO
			String[] pubtos = derivePubTo(false, parentItem, loAvailDiff, debugSb);
			String[] pubtosT1 = derivePubTo(true, parentItem, loAvailDiff, debugSb);

			// set WDANNDATE
			String[] wdanndates = deriveWDANNDATE(false, parentItem, loAvailDiff, endmktAvailDiff, debugSb);
			String[] wdanndatesT1 = deriveWDANNDATE(true, parentItem, loAvailDiff, endmktAvailDiff, debugSb);

			//eomannnum = CHEAT;
			String[] eomannnums = deriveENDOFMARKETANNNUMBER( parentItem, loAvailDiff, endmktAvailDiff,false, debugSb);
			String[] eomannnumsT1 = deriveENDOFMARKETANNNUMBER( parentItem, loAvailDiff, endmktAvailDiff ,false,debugSb);

			// set LASTORDER
			String[] lastorders = deriveLastOrder(parentItem, loAvailDiff, false, debugSb);
			String[] lastordersT1 = deriveLastOrder(parentItem, loAvailDiff, true, debugSb);

			// set EOSANNDATE
			String[] eosanndates = deriveEOSANNDATE(false, parentItem, endAvailDiff, debugSb);
			String[] eosanndatesT1 = deriveEOSANNDATE(true, parentItem, endAvailDiff, debugSb);

			
			// BH set ENDOFSERVICE
			String[] endofservices = deriveENDOFSERVICE(false, parentItem, endAvailDiff, debugSb);
			String[] endofservicesT1 = deriveENDOFSERVICE(true, parentItem, endAvailDiff, debugSb);
			
			//eosannnum = CHEAT;
			
			
			String[] eosannnums = deriveENDOFSERVICEANNNUMBER(parentItem, endAvailDiff,false,  debugSb);
			String[] eosannnumsT1 = deriveENDOFSERVICEANNNUMBER(parentItem, endAvailDiff, true, debugSb);
			handleResults(anndates, anndatesT1, annnumbers, annnumbersT1,firstorders, firstordersT1,
		              plannedavailabilitys, plannedavailabilitysT1, pubfroms,pubfromsT1, pubtos,  pubtosT1,
		              wdanndates,  wdanndatesT1, eomannnums, eomannnumsT1,  lastorders,  lastordersT1,  endofservices,  endofservicesT1,
		               eosanndates,  eosanndatesT1,eosannnums, eosannnumsT1, country,  isExistfinal,  compatModel, debugSb);
		}

		private String[] deriveENDOFSERVICEANNNUMBER( DiffEntity parentDiff, DiffEntity endAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String sReturn[] = new String[2];
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveENDOFSERVICEANNNUMBER endAvailDiff: " + (endAvailDiff == null ? "null" : endAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			String temps[] = new String[2];

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				/*temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13", debugSb);
				AvailUtil.getAvailAnnAttributeDate(findT1, endAvailDiff, rfrthedate, rfrthedate, thedate, "ANNNUMBER", debugSb);*/
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			
			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}
		private String[] deriveENDOFMARKETANNNUMBER(DiffEntity parentDiff, DiffEntity loAvailDiff, DiffEntity endMktAvailDiff, boolean findT1, StringBuffer debugSb){
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveENDOFMARKETANNNUMBER lastOrderAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
                + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(149)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(200)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endMktAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}	

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		/***********************************************************************
		 * ID 46.00 <PUBTO> 0. PRODSTRUCTCATLGOR-d: CATLGOR.PUBTO or
		 * SWPRODSTRCATLGOR-d. CATLGOR.PUBTO 1. AVAIL.EFFECTIVEDATE where
		 * AVAILTYPE = “Last Order” 2. PRODSTRUCT. WTHDRWEFFCTVDATE 3.
		 * Min(MODEL. WTHDRWEFFCTVDATE; SWFEATURE. WITHDRAWDATEEFF_T; FEATURE.
		 * WITHDRAWDATEEFF_T}
		 * 
		 */
		private String[] derivePubTo(boolean findT1, DiffEntity parentDiff, DiffEntity loAvailDiff, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo " + " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
				+ " findT1:" + findT1 + NEWLINE);
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];			
						
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the loAvailDiff EFFECTIVEDATE date
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the WTHDRWEFFCTVDATE attrubte value of the parent entity
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "WTHDRWEFFCTVDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/***********************************************************************
		 * 0. PRODSTRUCTCATLGOR-d: CATLGOR.PUBFROM or SWPRODSTRCATLGOR-d:
		 * CATLGOR.PUBFROM 1. ANNOUNCEMENT.ANNDATE 2. PRODSTRUCT.ANNDATE 3.
		 * Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR MODEL.ANNDATE if
		 * SWFEATURE).
		 */
		private String[] derivePubFrom(boolean findT1, DiffEntity foAvailDiff, DiffEntity parentDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1 get the foAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL
				// where AVAIL.AVAILTYPE = "Planned Availability" (146).
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3.	EFFECTIVEDATE MODELCONVERTAVAIL-d: where AVAIL.AVAILTYPE = “Planned Availability”
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//5. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/***********************************************************************
		 * <ANNNUMBER> 1. ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL
		 * where AVAIL.AVAILTYPE = “Planned Availability” (146). 2. Empty (aka
		 * Null)
		 * 
		 */
		private String[] deriveAnnNumber(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(146)--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}		
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
         //TODO 
		/***********************************************************************
		 * <ANNDATE> 1. ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where
		 * AVAIL.AVAILTYPE = “Planned Availability” (146). 2. Empty (aka Null)
		 * 2. If there is no ANNOUNCEMENT, then use MODELCONVERT.ANNDATE
		 */
		private String[] deriveAnnDate(boolean findT1, DiffEntity parentDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			//1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//5. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;	
		}

		/***********************************************************************
		 * <FIRSTORDER>The first applicable / available date is used. 1.
		 * MODELCONVERTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where
		 * AVAIL.AVAILTYPE = “First Order” 2. <ANNDATE>
		 */

		private String[] deriveFIRSTORDER(boolean findT1, DiffEntity parentDiff, DiffEntity foAvailDiff, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveFIRSTORDER availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				+ " foAvailDiff: " + (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1.get the first order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2.get the plan avail--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3. EFFECTIVEDATE MODELCONVERTAVAIL-d: where AVAIL.AVAILTYPE = “Planned Availability”
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//4. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/***********************************************************************
		 * <PLANNEDAVAILABILITY> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE “Planned
		 * Availability”
		 */
		private String[] derivePLANNEDAVAILABILITY(boolean findT1, DiffEntity parentItem, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePLANNEDAVAILABILITY availDiff: "
				+ (availDiff == null ? "null" : availDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1. get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. get MODEL. WITHDRAWDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentItem, thedate, rfrthedate, "GENAVAILDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		
		/***********************************************************************
		 * ID 47.00 <EOSANNDATE> 1. ANNOUNCEMENT.ANNDATE where AVAIL.ANNTYPE =13
		 * 'End of Service' 2. Empty (aka Null)
		 * 
		 */
		private String[] deriveEOSANNDATE(boolean findT1, DiffEntity parentItem, DiffEntity endAvailDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =13
				//temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13", debugSb);
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
		}


		/***********************************************************************
		 * ID 47.00 <WDANNDATE>
		 * 1.MODELCONVERTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "End of Marketing" (149) and ANNOUNCEMENT.ANNTYPE =14 
		 * 2.MODELCONVERT. WITHDRAWDATE
		 * 
		 * update at 20181105
		 * 1.MODELCONVERTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE =14
		 * 2.MODELCONVERTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "End of Marketing" (149) and ANNOUNCEMENT.ANNTYPE =14
		 * 3.MODELCONVERT. WITHDRAWDATE
		 */
		
		private String[] deriveWDANNDATE(boolean findT1, DiffEntity parentDiffEntity, DiffEntity loAvailDiff, DiffEntity endmktAvailDiff, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveWDANNDATE lastOrderAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, loAvailDiff, thedate, rfrthedate, country, "14", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endmktAvailDiff, thedate, rfrthedate, country, "14", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. get MODEL. WITHDRAWDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WITHDRAWDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
		}

		
		/***********************************************************************
		 * ID 46.00 <ENDOFSERVICE> 1. AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE =
		 * “End of Service” (151) 2. Empty (aka Null)
		 * 
		 */
		private String[] deriveENDOFSERVICE(boolean findT1, DiffEntity parentItem, DiffEntity endAvailDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];			
			String[] temps   = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// try to get it from the lastorder avail
				temps = AvailUtil.getAvailAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		
		/***********************************************************************
		 * <LASTORDER> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149) 2 .
		 * Empty (aka Null)
		 * 
		 */
		private String[] deriveLastOrder(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get AVAIL.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. get MODEL. WITHDRAWDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WTHDRWEFFCTVDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
		}
		
		String getCountry() {
			return country;
		}

		String getKey() {
			return country;
		}


		public String toString() {
			return (availDiff!=null?availDiff.getKey()+ " action:" + action:"There is no AVAIL! ");
		}
	}
}
