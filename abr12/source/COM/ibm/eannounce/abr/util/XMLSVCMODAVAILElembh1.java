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
// $Log: XMLSVCMODAVAILElembh1.java,v $
// Revision 1.21  2018/09/04 08:55:31  dlwlan
// RollBack endofserviceannnumber for svcmod
//
// Revision 1.20  2018/08/22 08:33:03  dlwlan
// Story 1865979 Withdrawal RFA Number generation
//
// Revision 1.19  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.18  2014/03/25 14:53:53  guobin
// flows to BH prof srv - multi status change - more broadly then we needed. data not in final sent as final
//
// Revision 1.17  2014/01/07 12:58:55  guobin
// delete XML - Avails RFR Defect: BH 185136 Doc BH FS ABR XML System Feed Mapping 20131106b
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
public class XMLSVCMODAVAILElembh1 extends XMLElem {
	
	protected Vector childVct = new Vector(1);
	private static String availRelator = "";
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
	
	
	/*
	protected boolean hasChanges(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		boolean changed = false;
		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.hasChanges parentItem: " + parentItem.getKey() + NEWLINE);
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
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.hasChanges is true" + NEWLINE);
					break;
				} else {
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.hasChanges no changes found for " + ctryAudRec + NEWLINE);
				}
				ctryAudRec.dereference();
			}

			// release memory
			ctryAudElemMap.clear();
		} else {
			ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.hasChanges no planned AVAILs found" + NEWLINE);
		}

		return changed;
	} 
	*/
	

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

	public XMLSVCMODAVAILElembh1() {
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

		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1:parentItem: " + parentItem.getKey() + NEWLINE);
		if (parentItem.getEntityType().equals("SVCMOD")) {
			availRelator = "SVCMODAVAIL";
		} else {
			availRelator = "SVCSEOAVAIL";
		}
		
		//new added 0904b
		boolean compatModel = false;
	    boolean isExistfinal = false;
        compatModel = AvailUtil.iscompatmodel();
		ABRUtil.append(debugSb,"compatModel compatbility mode:" + compatModel);
        if(!compatModel){
        	//new added(xml status)
    		String status = null;
    		status = (String) table.get("_chSTATUS");
    		ABRUtil.append(debugSb,"the status is" + status + NEWLINE);
    		if(STATUS_FINAL.equals(status)){
    			isExistfinal = true;
    		}else{
    			isExistfinal = false;
    		}
        	ABRUtil.append(debugSb,"isExistfinal :" + isExistfinal);
        }
        //new added end

		Vector plnAvlVct = getPlannedAvails(table, parentItem, debugSb);

		if (plnAvlVct.size() > 0) { // must have planned avail for any of this, wayne said there will always be at least 1
			// get model audience values, t2[0] current, t1[1] prior
			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
			// AVAILb to have US at T2
			
			//new added 0904b
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
			//new added end
			
			// there is no such attribute AUDIEN on SVCMOD, so do need to get Catelog infor.
			//Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
			// output the elements
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			while (itr.hasNext()) {
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
				
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
				ctryAudRec.setAllFields(parentItem, foAvailDiff, loAvailDiff, endAvailDiff, endMktAvailDiff, table, isExistfinal,  compatModel, debugSb);
			
				if (ctryAudRec.isDisplayable() || ctryAudRec.isrfrDisplayable()) {
					createNodeSet(table, document, parent, parentItem, ctryAudRec, debugSb);
					
				} else {
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.addElements no changes found for " + ctryAudRec + NEWLINE);
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
			ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.addElements no planned AVAILs found" + NEWLINE);
		}
		//      	}
	}

	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, DiffEntity parentDiffEntity, CtryAudRecord ctryAudRec, StringBuffer debugSb) {
		
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
		child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
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
		if(ctryAudRec.isrfrDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create
			// COUNTRYAUDIENCEELEMENT
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
    		child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
    		elem.appendChild(child);
            child = (Element) document.createElement("LASTORDER");
            child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
            elem.appendChild(child);
            child = (Element) document.createElement("EOSANNDATE");
            child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
            elem.appendChild(child);
            child = (Element) document.createElement("ENDOFSERVICEDATE");
            child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
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

	//new updated 0904b
	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, boolean T1, StringBuffer debugSb) {
		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.buildCtryAudRecs build T1 country list " + T1 + " " + availDiff.getKey()+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (T1){
		if (!availDiff.isNew()){ // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.buildCtryAudRecs for deleted / update avail at T1: ctryAtt "+
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
			ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt "+
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
							ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.buildCtryAudRecs for new:"+availDiff.getKey()+" rec: "+
							ctryAudRec.getKey() + NEWLINE);
						}
					}
				}
			}
		}
	}
	}

	/********************
	 * get planned avails - availtype cant be changed
	 */
	private Vector getPlannedAvails(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("AVAIL");
		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
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
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey()
						+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
					if (fAtt != null && fAtt.isSelected("146")) {
						avlVct.add(diffitem);
					}
				} else {
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getPlannedAvails checking[" + i + "]:" + diffitem.getKey()
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
		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.deriveTheSameEntry is " + isFromTheSame + " availrelator: " + availRelator
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
		ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getEntityForAttrs looking for " + attrCode + ":" + attrVal + " and " + attrCode2
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
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey()
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
						ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey()
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
						ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + i + "]: current "
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
						ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey()
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
	
	//new updated 0904b
	private static class CtryAudRecord extends CtryRecord{
		
		
		private String country;
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

		/*********************
		 * 3.	<EARLIESTSHIPDATE>
		 * 	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		 * 	this avail cannot be deleted at this point
		 * 
		 *  * 	<PUBFROM>
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBFROM
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = ??Planned Availability?? (146).
		 4.	Empty (aka Null)
		 *  * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBTO
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 3 .	Empty (aka Null)
		 
		 * 	 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = ??End of Service(151)
		 2.	Empty (aka Null)
		 */
		void setAllFields(DiffEntity parentDiffEntity, DiffEntity foAvailDiff, DiffEntity loAvailDiff, DiffEntity endAvailDiff, DiffEntity endmktAvailDiff, Hashtable table, boolean isExistfinal, boolean compatModel, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + availDiff.getKey() + " " + getKey() + NEWLINE);
			
			//new added 0904b
			availStatus = "0020";
            rfravailStatus = "0040";
            
			//ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
            String[] anndates  = deriveAnnDate(parentDiffEntity,false, debugSb);
            String[] anndatesT1 = deriveAnnDate(parentDiffEntity,true, debugSb);

			
			//ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
            String[] annnumbers = deriveAnnNumber(false, debugSb);
            String[] annnumbersT1 = deriveAnnNumber(true, debugSb);

		
			//FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143 or null.
            String[] firstorders = deriveFIRSTORDER(parentDiffEntity, foAvailDiff, false, debugSb);
            String[] firstordersT1 = deriveFIRSTORDER(parentDiffEntity, foAvailDiff, true, debugSb);

         // PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
            String[] plannedavailabilitys = derivePlannedavailability(parentDiffEntity, false, debugSb);
            String[] plannedavailabilitysT1 = derivePlannedavailability(parentDiffEntity, true, debugSb);
            
			//LASTORDER is equal to PUBTO. 			
			
			
			//If the country in AVAIL.COUNTRYLIST was newly added or the AVAIL(first order) is newly added, then set Action UPDATE_ACTIVITY
			//If the country in AVAIL.COUNTRYLIST was deleted or AVAIL was deleted, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			//If the AVAIL was updated, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
//			if (isNewCountry(foAvailDiff, debugSb)) {
//				setAction(UPDATE_ACTIVITY);
//			}
			//set PUBFROM
			String[] pubfroms  = derivePubFrom(parentDiffEntity, foAvailDiff, false, debugSb);
			String[] pubfromsT1 = derivePubFrom(parentDiffEntity, foAvailDiff, true, debugSb);

			// set PUBTO
			String[] pubtos = derivePubTo(parentDiffEntity,  loAvailDiff, false, debugSb);
			String[] pubtosT1 = derivePubTo(parentDiffEntity,  loAvailDiff, true, debugSb);


			// set WDANNDATE
			String[] wdanndates = deriveWDANNDATE(parentDiffEntity, endmktAvailDiff, false, debugSb);
			String[] wdanndatesT1 = deriveWDANNDATE(parentDiffEntity, endmktAvailDiff, true, debugSb);

			// set ENDOFMARKETANNNUMBER Story 1865979 Withdrawal RFA Number generation
            String[] eomannnums = deriveENDOFMARKETANNNUMBER(endmktAvailDiff, false, debugSb);
            String[] eomannnumsT1 = deriveENDOFMARKETANNNUMBER(endmktAvailDiff, true, debugSb);

            //set LASTORDER
			String[] lastorders = deriveLastOrder(parentDiffEntity, loAvailDiff, false, debugSb);
			String[] lastordersT1 = deriveLastOrder(parentDiffEntity, loAvailDiff, true, debugSb);

        	//set EOSANNDATE
			String[] eosanndates = deriveEOSANNDATE(endAvailDiff, false, debugSb);
			String[] eosanndatesT1 = deriveEOSANNDATE(endAvailDiff, true, debugSb);
			
			// BH set ENDOFSERVICE
			String[] endofservices = deriveENDOFSERVICE(endAvailDiff, false, debugSb);
			String[] endofservicesT1 = deriveENDOFSERVICE(endAvailDiff, true, debugSb);
			
			
		    handleResults(anndates, anndatesT1, annnumbers, annnumbersT1,firstorders, firstordersT1,
	              plannedavailabilitys, plannedavailabilitysT1, pubfroms,pubfromsT1, pubtos,  pubtosT1,
	              wdanndates,  wdanndatesT1, eomannnums, eomannnumsT1, lastorders,  lastordersT1,  endofservices,  endofservicesT1,
	               eosanndates,  eosanndatesT1, country,  isExistfinal,  compatModel, debugSb);
			//new added end
	
		}

		

		/****************************
		 * all the new added country that in First order Avail set the action is update.
		 return whether the country is new.
		 */
		/*
		private boolean isNewCountry(DiffEntity diffEntity, StringBuffer debugSb) {

			boolean isNew = false;
			if (diffEntity != null && diffEntity.isNew()) {
				isNew = true;
				ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.setAllFields isNewAvail" + diffEntity.getKey() + NEWLINE);
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
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.setAllFields isNewCountry" + diffEntity.getKey() + NEWLINE);
				}
			}
			return isNew;

		}
		*/
		
		/****************************
		 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = ??End of Service (151)
		 2.	Empty (aka Null)

		 */
		private String[] deriveENDOFSERVICE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveEndOfService " + " eofAvailDiff: "
					+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + " findT1:" + findT1 + NEWLINE);
				
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

		/****************************
		 * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBTO
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 3 .	Empty (aka Null)
		 
		 */
		private String[] derivePubTo(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo " + " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
					+ " findT1:" + findT1 + NEWLINE);
				String thedate = CHEAT;
				String rfrthedate = CHEAT;
				String[] sReturn = new String[2];
				String[] temps = new String[2];			
				
				
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//2.AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
					temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
				}
				
				//new added 0904b
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//2. SVCMOD.WTHDRWEFFCTVDATE
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
					temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WTHDRWEFFCTVDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
						}			
					}
				}
				
				sReturn[0]= thedate;
				sReturn[1]= rfrthedate;
				return sReturn;
		}

		/****************************
		 * 	<PUBFROM>
		 1.	MDLCATLGOR-d: CATLGOR.PUBFROM  because there is no ANDIEN on SVCMOD , so CTLGOR will not be consider .
		 The first applicable / available date is used.
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = ??Planned Availability (146).
		 4.	Empty (aka Null)

		 */
		private String[] derivePubFrom(DiffEntity parentDiffEntity, DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom availDiff: " + availDiff.getKey() + " foAvailDiff: "
					+ (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
				
				String thedate = CHEAT;
				String rfrthedate = CHEAT;
				String[] sReturn = new String[2];
				String[] temps = new String[2];
				
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//2 get the foAvailDiff.EFFECTIVEDATE
					temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
				}			
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//3 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL
					// where AVAIL.AVAILTYPE = "Planned Availability" (146).
					temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
				}
				
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//4 get the plAvailDiff.EFFECTIVEDATE
					temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
				}
				
				//new added 0904b
				if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
					//2. SVCMOD.ANNDATE
					if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
						if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
					temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE", debugSb);
					thedate = temps[0];
					rfrthedate = temps[1];
						}			
					}
				}
				
				sReturn[0]= thedate;
				sReturn[1]= rfrthedate;
				return sReturn;
		}

		/****************************
		 * 	<ANNNUMBER>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability?? (146).
		 2.	Empty (aka Null)

		 */
		private String[] deriveAnnNumber(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveAnnNumber availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);	
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

		/****************************
		 * 	<ANNDATE>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 鈥淧lanned Availability?? (146).
		 2.	SVCMOD.ANNDATE

		 */
		private String[] deriveAnnDate(DiffEntity parentDiffEntity, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveAnnDate availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			//1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			//new added 0904b
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. SVCMOD.ANNDATE
				if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
					if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
					}			
				}
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;	
		}

		/****************************
		 * 	<FIRSTORDER>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淔irst Order??
		 2.	<ANNDATE>
		 //TODO UDPATE as following sequence
           1.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淔irst Order鈥�
           2.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
           3.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 鈥淧lanned Availability鈥�
           4.	<ANNDATE>

		 */
		private String[] deriveFIRSTORDER(DiffEntity parentDiffEntity,DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveFIRSTORDER availDiff: " + (availDiff == null ? "null" : availDiff.getKey()) 
				 + "foAvailDiff " + foAvailDiff + (foAvailDiff == null ? "null" : foAvailDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1.get the first order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Planned Availability"
				//find current derivation
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3.get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			//new added 0904b
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. SVCMOD.ANNDATE
				if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
					if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
					}			
				}
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		
		//new added 0904b
		/****************************
		 * 	<PLANNEDAVAILABILITY>
		 1.	derivePlannedavailability is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		 2.	Empty (aka Null)

		 */
		private String[] derivePlannedavailability(DiffEntity parentDiffEntity, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.derivePlannedavailability availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			//1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			//new added 0904b
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. SVCMOD.ANNDATE
				if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
					if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
					}			
				}
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;	
		}

		/****************************
		 * 	<EOSANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = 鈥淓nd of Service?? (151) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13)
		 2.	Empty (aka Null)

		 */
		private String[] deriveEOSANNDATE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
		String thedate = CHEAT;
		String rfrthedate = CHEAT;
		String returns[] = new String[2];
		String temps[] = new String[2];
//		
//		if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//			// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =13
//			temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13", debugSb);
//			thedate = temps[0];
//			rfrthedate = temps[1];
//		}
//		
		returns[0]= thedate;
		returns[1]= rfrthedate;
		return returns;}

		/****************************
		 * 	<WDANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		 2.	Empty (aka Null)

		 */
		private String[] deriveWDANNDATE(DiffEntity parentDiffEntity, DiffEntity endMktAvailDiff, boolean findT1, StringBuffer debugSb) {
		String thedate = CHEAT;
		String rfrthedate = CHEAT;
		String returns[] = new String[2];
		String temps[] = new String[2];
		
		if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
			// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
			temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endMktAvailDiff, thedate, rfrthedate, country, "14", debugSb);
			thedate = temps[0];
			rfrthedate = temps[1];
		}
		
		//new added 0904b
		if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
			//2. SVCMOD.WITHDRAWDATE
			if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
				if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
			temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WITHDRAWDATE", debugSb);
			thedate = temps[0];
			rfrthedate = temps[1];
				}			
			}
		}
		
		returns[0]= thedate;
		returns[1]= rfrthedate;
		return returns;}
		
		/****************************
		 * <ENDOFMARKETANNNUMBER>
		 1.	SVCMODAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNNUMBER where AVAIL.AVAILTYPE = " End Of Life - Withdrawal from mktg" (146) 
		 2. Empty (aka Null)
		 */
		private String[] deriveENDOFMARKETANNNUMBER(DiffEntity endmktAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveEndOfMarketAnnNumber endMktAvailDiff: " + (endmktAvailDiff == null ? "null" : endmktAvailDiff.getKey())
                + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(146)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endmktAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}		
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
			
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
			// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
			temps = AvailUtil.getAvailAnnDateByAnntype(findT1, loAvailDiff, thedate, rfrthedate, country, "14", debugSb);
			thedate = temps[0];
			rfrthedate = temps[1];
		}
		
		//new added 0904b
		if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
			//2. SVCMOD.WITHDRAWDATE
			if (parentDiffEntity != null && !parentDiffEntity.isDeleted()) {
				if ("SVCMOD".equals(parentDiffEntity.getEntityType())){
			temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WITHDRAWDATE", debugSb);
			thedate = temps[0];
			rfrthedate = temps[1];
				}			
			}
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
			return (availDiff == null)?" availDiff is null":availDiff.getKey();
		}
	}
}
