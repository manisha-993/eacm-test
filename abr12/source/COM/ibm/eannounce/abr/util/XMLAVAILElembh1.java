//Licensed Materials -- Property of IBM
// 
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * Class used to hold info and structure to be generated for the xml feed for
 * abrs. * Constructor for <AVAILABILITYLIST> elements <AVAILABILITYLIST> 2
 * AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned
 * Availability) <AVIAILABILITYELEMENT> 3 <AVAILABILITYACTION> 4 AVAIL
 * CountryAction <STATUS> <COUNTRY> </COUNTRY> 4 AVAIL COUNTRYLIST - Flag
 * Description Class
 * 
 * <EARLIESTSHIPDATE> </EARLIESTSHIPDATE> 4 AVAIL/ <PUBFROM> </PUBFROM> 4 AVAIL/
 * PubFrom <PUBTO> </PUBTO> 4 AVAIL/ PubTo <ENDOFSERVICEDATE>
 * </ENDOFSERVICEDATE> 4 AVAIL/ Endofservice </AVIAILABILITYELEMENT> 3
 * </AVAILABILITYLIST> 2
 *
 */
// $Log: XMLAVAILElembh1.java,v $
// Revision 1.35  2019/05/17 08:58:28  dlwlan
// Story 1980580 Create new announcement type End of Development
//
// Revision 1.34  2018/08/22 08:33:03  dlwlan
// Story 1865979 Withdrawal RFA Number generation
//
// Revision 1.33  2018/08/13 09:50:52  dlwlan
// remove tag RFAREFNUMBER from MODEL
//
// Revision 1.32  2018/07/27 12:24:07  dlwlan
// Unique ID Code Roll back
// Add RFAREFNUMBER to MODEL
//
// Revision 1.31 2017/04/12 09:50:28 wangyul
// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
//
// Revision 1.30 2015/01/26 15:53:39 wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.29 2014/03/25 14:36:24 guobin
// flows to BH prof srv - multi status change - more broadly then we needed.
// data not in final sent as final- Dave to investigate w Rupal
//
// Revision 1.28 2013/12/03 13:18:21 guobin
// HALM00231247 - update for TMF.<PUBFROM> that add "IF". should I make the
// change on <ANNDATE>,<FIRSTORDER>,<PUBFROM> etc that the applicable countries
// are in the FEATURE.COUNTRYLIST if MODEL is old data which have no Planed
// avail.
//
// Revision 1.27 2013/11/12 16:04:37 guobin
// delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN-
// 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.26 2013/08/16 05:20:03 guobin
// fix End of service part 2 dependent on catlgor BHALM00193159 and BHCatlgor
//
// Revision 1.25 2013/08/16 05:10:36 wangyulo
// fix the issue RCQ00222829 for the BHCATLGOR which change the CATLGOR to
// BHCATLGOR
//
// Revision 1.24 2013/07/09 15:03:36 guobin
// End of service part 1 -- Defect BHALM00193159 in BH FS ABR XML System Feed
// Mapping 20130614.doc
//
// Revision 1.23 2013/05/29 07:49:45 guobin
// Re: WI 945852 -IN3766131 handle delta when old offering data changes from no
// AVAIL to having AVAIL
//
// Revision 1.22 2012/09/20 06:26:54 guobin
// Work Item 805205 New: Incorrect derivation of MODEL.ANNDATE
// <AVAILBILITY><ANNDATE>The second applicable / available date is used
// MODEL.ANNDATE
//
// Revision 1.21 2012/08/24 15:28:22 wangyulo
// fix the Defects( 123011 and 123362) -- CATLGOR PUBFROM/TO not in XML
//
// Revision 1.20 2012/08/24 15:27:25 wangyulo
// fix the Defects( 123011 and 123362) -- CATLGOR PUBFROM/TO not in XML
//
// Revision 1.19 2012/07/23 11:54:41 liuweimi
// Update XML design to use draft AVAILs other than Planned Availability
//
// Revision 1.18 2012/01/22 05:37:30 guobin
// RTM work item number on the change is 643541 / BHCQ 81991 Update to XML
// System Feed Mapping 20120117.doc - correct design for PUBFROM, FIRSTORDER,
// PUBTO
//
// Revision 1.17 2011/11/17 13:17:32 liuweimi
// Fix Defect - 591202 based on BH FS ABR Data Transformation System Feed
// 20111031.doc
//
// Revision 1.16 2011/10/31 14:12:35 guobin
// comment out the release momery AVAIL and ANNOUNCE from HashTable
//
// Revision 1.15 2011/03/30 07:44:45 guobin
// add the world wide avail of SLEORGGRP/SLEORGNPLNTCD
//
// Revision 1.14 2011/03/14 14:39:50 guobin
// remove print the table information
//
// Revision 1.13 2011/02/15 13:00:49 guobin
// add debug information for the table
//
// Revision 1.12 2011/01/14 09:32:21 guobin
// Set the Values in <SLEORGGRPLIST> and <SLEORGNPLNTCODELIST> when there isn't
// an AVAIL where AVAILTYPE='Planed Avail' and ANNDATE< '20100301'.
//
// Revision 1.11 2011/01/05 08:01:00 guobin
// add <SLEORGGPLIST> and <SLEORGPLNTCODELIST>
//
// Revision 1.10 2010/10/19 09:39:01 guobin
// Last Ordre is different from Pubto, add deriveLastOrder()
//
// Revision 1.9 2010/09/26 08:21:25 guobin
// // check annVct and availVct is not null
//
// Revision 1.8 2010/09/17 04:27:38 yang
// When geting CATLGOR.PUBFROM and PUBTO need to check CATLGOR.OFFCOUNTRY =
// country
//
// Revision 1.7 2010/09/03 09:49:38 yang
// check annVct and availVct is not null
//
// Revision 1.6 2010/08/26 09:42:45 yang
// Derive Pubfrom and Pubto from CATLGOR
//
// Revision 1.5 2010/07/29 08:46:24 yang
// fix code
//
// Revision 1.4 2010/07/22 09:47:30 yang
// changed log output format
//
// Revision 1.3 2010/07/06 09:49:28 yang
// Complete all elements for 1.0
//
// Revision 1.2 2010/06/30 08:10:23 yang
// add <anndate><firstorder><lastorder> in setallfields()
//
// Revision 1.1 2010/06/12 06:57:18 yang
// XMLAVAILElem for BH 1.0
//
// Revision 1.15 2010/04/15 01:21:18 yang
// when derive from Model set AVAILABILITYACTION = Update
//
// Revision 1.14 2010/03/22 15:03:35 yang
// Add derivefromModel where there is no Planned Avail and Modle.Anndate less
// than 2010-03-31.
//
// Revision 1.13 2010/02/05 20:14:46 rick
// format prob take 4
//
// Revision 1.12 2010/02/05 20:12:15 rick
// format prob take 3
//
// Revision 1.11 2010/02/05 20:06:43 rick
// format prob take 2
//
// Revision 1.10 2010/02/05 19:37:07 rick
// possible format problem.
//
// Revision 1.9 2010/02/05 19:25:05 rick
// change <ENDOFSERVICE> to <ENDOFSERVICEDATE>
//
// Revision 1.8 2010/01/29 01:19:24 yang
// change isNewCountry().
//
// Revision 1.7 2010/01/28 08:46:27 yang
// comment out <EARLIESTSHIPDATE>, it is not for wave1
//
// Revision 1.6 2010/01/11 16:30:14 yang
// Use the first one of the results of AVAILANNA getDownLink() .
//
// Revision 1.5 2009/12/24 12:57:24 yang
// BH get<PUBFROM> from Avail downlink() Announcement.
//
// Revision 1.4 2009/12/17 11:36:11 yang
// *** empty log message ***
//
// Revision 1.3 2009/12/15 08:46:08 yang
// BH
//
// Revision 1.2 2009/12/10 14:31:58 yang
// BH
//
// Revision 1.1 2009/12/09 09:52:17 yang
// For BH <AVAILABILITYLIST>
//
// Revision 1.3 2008/05/28 13:44:23 wendy
// Added STATUS to output for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2 2008/04/29 14:26:12 wendy
// Add defaults
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

public class XMLAVAILElembh1 extends XMLElem {
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();

	/**********************************************************************************
	 * Constructor for <AVAILABILITYLIST> elements <AVAILABILITYLIST> 2 AVAIL - for
	 * each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
	 * <AVIAILABILITYELEMENT> 3 <AVAILABILITYACTION> 4 AVAIL CountryAction <STATUS>
	 * <COUNTRY> </COUNTRY> 4 AVAIL COUNTRYLIST - Flag Description Class
	 * 
	 * <EARLIESTSHIPDATE> </EARLIESTSHIPDATE> 4 AVAIL/ <PUBFROM> </PUBFROM> 4 AVAIL/
	 * PubFrom <PUBTO> </PUBTO> 4 AVAIL/ PubTo <ENDOFSERVICEDATE>
	 * </ENDOFSERVICEDATE> 4 AVAIL/ Endofservice </AVIAILABILITYELEMENT> 3
	 * </AVAILABILITYLIST> 2
	 *
	 *
	 */
	public XMLAVAILElembh1() {
		super("AVAILABILITYELEMENT");
	}

	/**********************************************************************************
	 * @param dbCurrent
	 *            Database
	 * @param table
	 *            Hashtable of Vectors of DiffEntity
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param parentItem
	 *            DiffEntity - parent to use if path is specified in XMLGroupElem,
	 *            item to use otherwise
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent,
			DiffEntity parentItem, StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException, java.rmi.RemoteException, java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		// add debug information for the table
		// printTable(table, debugSb);

		String path = "D:MODELAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
		String m_strEpoch = "1980-01-01-00.00.00.000000";
		boolean isDelta = true;
		Vector allVct = (Vector) table.get("AVAIL");

		boolean compatModel = false;
		boolean isExistfinal = false;
		compatModel = AvailUtil.iscompatmodel();
		if (!compatModel) {
			// isExistfinal = AvailUtil.isExistFinal(dbCurrent, parentItem, "STATUS",
			// debugSb);
			// new added(xml status)
			String status = null;
			status = (String) table.get("_chSTATUS");
			ABRUtil.append(debugSb, "the status is" + status + NEWLINE);
			if (STATUS_FINAL.equals(status)) {
				isExistfinal = true;
			} else {
				isExistfinal = false;
			}
		}

		TreeMap ctryAudElemMap = new TreeMap();
		boolean isfromModel = isDerivefromModel(table, parentItem, debugSb, true);
		if (isfromModel == true) {
			EntityItem priorItem = parentItem.getPriorEntityItem();
			// ABRUtil.append(debugSb,"XMLAVAILElem.addElements priorItem is " +
			// (priorItem == null?" is null":"not null")+ " profileT1 valOn: " +
			// priorItem.getProfile().getValOn()+ NEWLINE);
			if (priorItem != null && m_strEpoch.equals(priorItem.getProfile().getValOn())) {
				isDelta = false;
			}
			boolean oldData = isDerivefromModel(table, parentItem, debugSb, false);
			if (isDelta && oldData) {
				buildDeleteCtry(ctryAudElemMap, dbCurrent, parentItem, debugSb);
			} else if (isDelta) {
				buildBHCatlgorRecs(table, ctryAudElemMap, true, debugSb);
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity availDiff = (DiffEntity) allVct.elementAt(i);
					buildCtryAudRecs(ctryAudElemMap, availDiff, true, debugSb);
				} // end each planned avail
			}
			buildWorldWideCountryAud(ctryAudElemMap, table, dbCurrent, document, parent, parentItem, path, debugSb);

		} else {
			// get all AVAILs where AVAILTYPE="Planned Availability" (146) -
			// some may be deleted
			// Vector plnAvlVct = getPlannedAvails(table, debugSb);

			if (allVct.size() > 0) { // must have planned avail for any of this,
										// wayne said there will always be at
										// least 1
				// get model audience values, t2[0] current, t1[1] prior
				// must account for AVAILa to have had US, CANADA at T1, and
				// just CANADA at T2 and a new
				// AVAILb to have US at T2
				// TODO make a change according to BH FS ABR XML System Feed
				// Mapping 20130508.doc
				// Consider the following scenario:
				// 1. A MODEL was IDLed as old data (ANNDATA <20010301 and hence
				// does not have aPlanned Availability . When it is moved
				// toFinal, XML is generated with an < AVAILABILITYELEMENT> for
				// every country in countrylist.
				// 2. A year later, the MODEL is moved fromFinal toChange
				// Request. Then aPlanned Availability with a STATUS =Draft is
				// added to the MODEL with a Countrylist ofGermany.
				// 3. The MODEL is returned to a STATUS ofReady for Review;
				// however, since it was onceFinal, no data is generated.
				// 4. The MODEL is returned to a STATUS ofFinal. Since there is
				// an AVAIL with a STATUS =Draft, then XML is NOT generated
				// since there is not an AVAIL with a STATUS =Final.
				// 5. The AVAIL is moved to a STATUS ofFinal. Delta XML will be
				// generated. Since at T1, the Countrylist is defined to be
				// World Wide and at T2 the Countrylist isGermany, then an
				// <AVAILABILITYELEMENT> has to be generated for the World Wide
				// list of Countries. All Countries exceptGermany will have
				// <AVAILABILITYACTION> set toDelete. <AVAILABILITYACTION> will
				// be set toUpdate forGermany.
				// 6. If there is a BHCATLGOR with Countrylist ofGermany
				// andChina, onlyGermany will be considered since it is the only
				// valid Country at T2.

				EntityItem priorItem = parentItem.getPriorEntityItem();
				// ABRUtil.append(debugSb,"XMLAVAILElem.addElements priorItem is " +
				// (priorItem == null?" is null":"not null")+
				// " profileT1 valOn: " + priorItem.getProfile().getValOn()+
				// NEWLINE);
				if (priorItem != null && m_strEpoch.equals(priorItem.getProfile().getValOn())) {
					isDelta = false;
				}
				boolean oldData = isDerivefromModel(table, parentItem, debugSb, false);
				if (isDelta && oldData) {
					// buildDeleteCtry(TreeMap ctryAudElemMap, Database
					// dbCurrent, DiffEntity parentItem, StringBuffer debugSb)
					buildDeleteCtry(ctryAudElemMap, dbCurrent, parentItem, debugSb);
				} else if (isDelta) {
					buildBHCatlgorRecs(table, ctryAudElemMap, true, debugSb);
					for (int i = 0; i < allVct.size(); i++) {
						DiffEntity availDiff = (DiffEntity) allVct.elementAt(i);
						buildCtryAudRecs(ctryAudElemMap, availDiff, true, debugSb);
					} // end each planned avail
				}
				// according to BH FS ABR XML System Feed Mapping 20130614.doc
				// Bing add
				// 2 .The aggregated (UNION) of the countries found
				// forAvailability(AVAIL)Country List(COUNTRYLIST)for all
				// Availability Type(AVAILTYPE) that matches the earlier STATUS
				// filtering criteria and the UNION of anyCatalog
				// Overrides(BHCATLGOR)Country List(COUNTRYLIST).
				buildBHCatlgorRecs(table, ctryAudElemMap, false, debugSb);
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity availDiff = (DiffEntity) allVct.elementAt(i);
					buildCtryAudRecs(ctryAudElemMap, availDiff, false, debugSb);
				} // end each planned avail
			} else {
				ABRUtil.append(debugSb, "XMLAVAILElem.addElements no planned AVAILs found" + NEWLINE);
			}
		}
		// Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
		Vector mdlAudVct[] = null;
		// output the elements
		Collection ctryrecs = ctryAudElemMap.values();
		Iterator itr = ctryrecs.iterator();
		Hashtable XMLTable = null;
		while (itr.hasNext()) {
			CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
			// Rows marked as Delete do not need further updating and the Action
			// should not be changed by further updating.
			DiffEntity plaAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "146",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			DiffEntity plaAvailDiffT1 = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "146",
					"COUNTRYLIST", ctryAudRec.getCountry(), true, debugSb);
			// find firstorder avail for this country
			DiffEntity foAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "143",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			// find lastorder avail for this country
			DiffEntity loAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "149",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			DiffEntity endDevAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "201",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			DiffEntity endAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "151",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			DiffEntity endMktAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILTYPE", "200",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);
			// find catlgor for this country and audience
			DiffEntity epicAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", allVct, "AVAILANNTYPE", "EPIC",
					"COUNTRYLIST", ctryAudRec.getCountry(), false, debugSb);

			DiffEntity catlgorDiff = getCatlgor(table, mdlAudVct, ctryAudRec.getCountry(), debugSb);
			// add other info now
			DiffEntity[] plaAvailDiffs = new DiffEntity[2];
			plaAvailDiffs[0] = plaAvailDiffT1;
			plaAvailDiffs[1] = plaAvailDiff;

			ctryAudRec.setAllFields(parentItem, catlgorDiff, foAvailDiff, loAvailDiff, endDevAvailDiff, endAvailDiff, endMktAvailDiff,
					epicAvailDiff, table, plaAvailDiffs, path, isExistfinal, compatModel, debugSb);

			if (ctryAudRec.isDisplayable() || ctryAudRec.isrfrDisplayable()) {

				if (isPlannedAvail(ctryAudRec.availDiff)) {
					createNodeSet(table, document, parent, parentItem.getCurrentEntityItem(), ctryAudRec, plaAvailDiff,
							path, debugSb);
				} else {
					// 1If there isn’t an AVAIL where AVAILTYPE=Planned
					// Availability(146) then retrieve all SLEORGNPLNTCODE
					// entities where SLEORGNPLNTCODE.MODCATG equals
					// a.MODEL.COFCAT if MODEL.COFCAT =Hardware or Software
					// .....
					if (XMLTable == null) {
						XMLTable = searchSLORGNPLNTCODE(dbCurrent, parentItem.getCurrentEntityItem(), debugSb);
					}
					createNodeSet(table, document, parent, parentItem.getCurrentEntityItem(), ctryAudRec, parentItem,
							path, XMLTable, debugSb);

				}
			} else {
				ABRUtil.append(debugSb,
						"XMLAVAILElem.addElements no changes found for " + ctryAudRec.getKey() + NEWLINE);
			}
			ctryAudRec.dereference();
		}

		if (XMLTable != null) {
			XMLTable.clear();
		}
		// release memory
		ctryAudElemMap.clear();
	}

	private Hashtable searchSLORGNPLNTCODE(Database dbCurrent, EntityItem item, StringBuffer debugSb)
			throws SQLException, MiddlewareException {
		XMLSLEORGNPLNTCODESearchElem search = new XMLSLEORGNPLNTCODESearchElem();
		Hashtable xmltable = new Hashtable();
		try {
			xmltable = search.doSearch(dbCurrent, item, debugSb);
		} catch (MiddlewareShutdownInProgressException e) {
			e.printStackTrace();
		} catch (SBRException exc) {
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			ABRUtil.append(debugSb,
					"XMLAVAILElem.addElements search!! SBRException: " + exBuf.getBuffer().toString() + NEWLINE);
			exc.printStackTrace();
		}
		ABRUtil.append(debugSb, "XMLAVAILElem.addElements search!! " + NEWLINE);

		return xmltable;
	}

	/**
	 * BH FS ABR XML System Feed Mapping 20130614.doc build BHCATLGOR.countrylist in
	 * AVAILBILITYLIST Country_fc. AVAIL.COUNTRYLIST. In other words, the list of
	 * countries is the UNION of COUNTRLIST for all AVAILs and the UNION of
	 * anyCatalog Overrides(BHCATLGOR Country List(COUNTRYLIST
	 * 
	 * @param ctryAudElemMap
	 * @param T1
	 * @param debugSb
	 */

	private void buildBHCatlgorRecs(Hashtable table, TreeMap ctryAudElemMap, boolean T1, StringBuffer debugSb) {

		Vector allVct = (Vector) table.get("BHCATLGOR");
		String attrCode2 = BHCOUNTRYLIST; // need flag code
		ABRUtil.append(debugSb, "buildBHCatlgorRecs looking for " + attrCode2 + " in CATLGOR allVct.size:"
				+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct != null) {
			if (T1) {
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem item = diffitem.getPriorEntityItem();
					// add the check of the status of BHCATLGOR
					if (!diffitem.isNew()) {
						String BHStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
						if (!STATUS_FINAL.equals(BHStatus)) {
							continue;
						}
						EANFlagAttribute ctryAtt = (EANFlagAttribute) item.getAttribute(attrCode2);
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
										ABRUtil.append(debugSb, "WARNING buildBHCatlgorRecs for delete/udpate" + mapkey
												+ " already exists, keeping orig " + rec + NEWLINE);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
										ctryAudRec.setAction(DELETE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb, "buildBHCatlgorRecs for delete/udpate rec: "
												+ ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}
				}
			} else {
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem item = diffitem.getCurrentEntityItem();
					// add the check of the status of BHCATLGOR
					if (!diffitem.isDeleted()) {
						String BHStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
						if (!STATUS_FINAL.equals(BHStatus)) {
							continue;
						}
						EANFlagAttribute ctryAtt = (EANFlagAttribute) item.getAttribute(attrCode2);
						if (ctryAtt != null) {
							MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
							for (int im = 0; im < mfArray.length; im++) {
								// get selection
								if (mfArray[im].isSelected()) {
									String ctryVal = mfArray[im].getFlagCode();
									String mapkey = ctryVal;
									if (ctryAudElemMap.containsKey(mapkey)) {
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										rec.setUpdateAvail(null);
										rec.setAction(CHEAT);
										ABRUtil.append(debugSb, "WARNING buildBHCatlgorRecs for new/update" + mapkey
												+ " already exists, replace orig with Update action" + rec + NEWLINE);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
										// ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb, "buildBHCatlgorRecs for new/udpate rec: "
												+ ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/*
	 * build ElemMap for old data that world wide countries at T1 ,but delete Aciton
	 * at T2.
	 */
	private void buildDeleteCtry(TreeMap ctryAudElemMap, Database dbCurrent, DiffEntity parentItem,
			StringBuffer debugSb) throws SQLException, MiddlewareException {
		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String strEnterprise = "SG";

		if (parentItem != null) {
			EntityItem curritem = parentItem.getCurrentEntityItem();
			if (curritem != null) {
				strEnterprise = curritem.getProfile().getEnterprise();
			}
		}

		try {
			// for all World Wide Countries
			rs = dbCurrent.callGBL9999A(returnStatus, strEnterprise, "GENAREASELECTION", "1999", "COUNTRYLIST");
			rdrs = new ReturnDataResultSet(rs);
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			dbCurrent.commit();
			dbCurrent.freeStatement();
			dbCurrent.isPending();
		}
		for (int iii = 0; iii < rdrs.size(); iii++) {
			String country = rdrs.getColumn(iii, 0).trim();
			ABRUtil.append(debugSb, "derivefrommodel world wide counry " + country + NEWLINE);

			String mapkey = country;
			if (!ctryAudElemMap.containsKey(mapkey)) {
				CtryAudRecord ctryAudRec = new CtryAudRecord(null, country);
				// For the the case of - If there isn't an AVAIL where AVAILTYPE="Planned
				// Availability" (146) and ANNDATE < '20100301' -
				// ANNNUMBER is empty
				// PLANNEDAVAILABILITY is MODEL.ANNDATE
				ctryAudRec.action = DELETE_ACTIVITY;
				ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
			}
		}
	}

	/**
	 * Bing create new method 6/27/2013
	 * 
	 * @param dbCurrent
	 * @param document
	 * @param parent
	 * @param parentItem
	 * @param debugSb
	 * @param returnStatus
	 * @param anndate
	 * @param withdrawanndate
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void buildWorldWideCountryAud(TreeMap ctryAudElemMap, Hashtable table, Database dbCurrent,
			Document document, Element parent, DiffEntity parentItem, String path, StringBuffer debugSb)
			throws SQLException, MiddlewareException {
		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String strEnterprise = "SG";
		EntityItem curritem = null;
		// TreeMap ctryAudElemMap = new TreeMap();
		if (parentItem != null) {
			curritem = parentItem.getCurrentEntityItem();
			if (curritem != null) {
				strEnterprise = curritem.getProfile().getEnterprise();
			}
		}
		try {
			// for all World Wide Countries GENAREASELECTION
			rs = dbCurrent.callGBL9999A(returnStatus, strEnterprise, "GENAREASELECTION", "1999", "COUNTRYLIST");
			rdrs = new ReturnDataResultSet(rs);
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			dbCurrent.commit();
			dbCurrent.freeStatement();
			dbCurrent.isPending();
		}
		for (int iii = 0; iii < rdrs.size(); iii++) {
			String country = rdrs.getColumn(iii, 0).trim();
			ABRUtil.append(debugSb, "derivefrommodel world wide counry " + country + NEWLINE);

			String mapkey = country;
			if (ctryAudElemMap.containsKey(mapkey)) {
				CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
				rec.setUpdateAvail(null);
				rec.setAction(CHEAT);

			} else {
				CtryAudRecord ctryAudRec = new CtryAudRecord(null, country);
				ctryAudRec.action = UPDATE_ACTIVITY;
				ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
			}
		}
	}

	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, EntityItem parentItem,
			CtryAudRecord ctryAudRec, DiffEntity relatedSLEORGGRP, String path, StringBuffer debugSb) {
		if (ctryAudRec.isDisplayable()) {
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
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			
			child = (Element) document.createElement("ENDOFDEVELOPMENTDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEodavaildate()));
			elem.appendChild(child);
			child = (Element) document.createElement("EODANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEodanndate()));
			elem.appendChild(child);
			
			SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, relatedSLEORGGRP, path, ctryAudRec.country,
					ctryAudRec.action, debugSb);

			// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
			child = (Element) document.createElement("ORDERSYSNAME");
			child.appendChild(document.createTextNode("" + ctryAudRec.getOrderSysName()));
			elem.appendChild(child);
			/*child = (Element) document.createElement("RFAREFNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrRfaRefNumber()));
			elem.appendChild(child);*/
		}
		if (ctryAudRec.isrfrDisplayable()) {
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
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getRfreosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
			
			child = (Element) document.createElement("ENDOFDEVELOPMENTDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreodavaildate()));
			elem.appendChild(child);
			child = (Element) document.createElement("EODANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreodanndate()));
			elem.appendChild(child);
			// add SLEORGGRP
			// if("SWPRODSTRUCT".equals(parentItem.getEntityType())){
			// SLEORGGRP.displayAVAILSLEORG(table, document, elem,
			// parentItem.getCurrentEntityItem(), ctryAudRec.availDiff,
			// "D:SWPRODSTRUCTAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country,
			// ctryAudRec.action, debugSb);
			// }
			// else{
			// SLEORGGRP.displayAVAILSLEORG(table, document, elem,
			// parentItem.getCurrentEntityItem(), ctryAudRec.availDiff,
			// "D:OOFAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action,
			// debugSb);
			// }
			SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem, relatedSLEORGGRP, path, ctryAudRec.country,
					ctryAudRec.action, debugSb);

			// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
			child = (Element) document.createElement("ORDERSYSNAME");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrOrderSysName()));
			elem.appendChild(child);
			/*child = (Element) document.createElement("RFAREFNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrRfaRefNumber()));
			elem.appendChild(child);*/
		}

	}

	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, EntityItem parentItem,
			CtryAudRecord ctryAudRec, DiffEntity relatedSLEORGGRP, String path, Hashtable xmltable,
			StringBuffer debugSb) {
		if (ctryAudRec.isDisplayable()) {
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
			//Story 1865979 Withdrawal RFA Number generation
	 		child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
	 		child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			
			child = (Element) document.createElement("ENDOFDEVELOPMENTDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEodavaildate()));
			elem.appendChild(child);
			child = (Element) document.createElement("EODANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEodanndate()));
			elem.appendChild(child);
			
			// add createWorldWideAvilNote
			createWorldWideAvilNote(document, ctryAudRec, xmltable, elem, debugSb);

			// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
			child = (Element) document.createElement("ORDERSYSNAME");
			child.appendChild(document.createTextNode("" + ctryAudRec.getOrderSysName()));
			elem.appendChild(child);
			/*child = (Element) document.createElement("RFAREFNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrRfaRefNumber()));
			elem.appendChild(child);*/
		}
		if (ctryAudRec.isrfrDisplayable()) {
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
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getRfreomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode(""+ctryAudRec.getRfreosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
			
			child = (Element) document.createElement("ENDOFDEVELOPMENTDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreodavaildate()));
			elem.appendChild(child);
			child = (Element) document.createElement("EODANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreodanndate()));
			elem.appendChild(child);
			
			createWorldWideAvilNote(document, ctryAudRec, xmltable, elem, debugSb);

			// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
			child = (Element) document.createElement("ORDERSYSNAME");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrOrderSysName()));
			elem.appendChild(child);
/*
			child = (Element) document.createElement("RFAREFNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrRfaRefNumber()));
			elem.appendChild(child);*/
		}

	}

	/**
	 * @param document
	 * @param ctryAudRec
	 * @param xmltable
	 * @param elem
	 * @throws DOMException
	 */
	private void createWorldWideAvilNote(Document document, CtryAudRecord ctryAudRec, Hashtable xmltable, Element elem,
			StringBuffer debugSb) throws DOMException {
		Element child;
		String wwcry = ctryAudRec.getCountry();
		Object sop_cry = (Object) xmltable.get(wwcry);
		if (sop_cry != null && !DELETE_ACTIVITY.equals(ctryAudRec.action)) {
			// SLEORGGRPLIST
			Element SLEORGGRPLISTElement = (Element) document.createElement("SLEORGGRPLIST");
			elem.appendChild(SLEORGGRPLISTElement);
			Vector vct[] = (Vector[]) xmltable.get(wwcry);
			Hashtable SLEORGGRP = (Hashtable) vct[0].get(0);
			Enumeration egroup = SLEORGGRP.keys();
			while (egroup.hasMoreElements()) {
				String key = (String) egroup.nextElement();
				Element childElement = (Element) document.createElement("SLEORGGRPELEMENT");
				SLEORGGRPLISTElement.appendChild(childElement);
				child = (Element) document.createElement("SLEOORGGRPACTION");
				child.appendChild(document.createTextNode("" + UPDATE_ACTIVITY));
				childElement.appendChild(child);
				child = (Element) document.createElement("SLEORGGRP");
				child.appendChild(document.createTextNode("" + key));
				childElement.appendChild(child);
			}
			// SLEORGNPLNTCODELIST
			Element SLEORGNPLNTCODELISTElement = (Element) document.createElement("SLEORGNPLNTCODELIST");
			elem.appendChild(SLEORGNPLNTCODELISTElement);
			Vector entityVector = vct[1];
			Vector duplVector = new Vector();
			for (int i = 0; i < entityVector.size(); i++) {
				EntityItem entity = (EntityItem) entityVector.get(i);
				String sleorg = PokUtils.getAttributeValue(entity, "SLEORG", ", ", "", false);
				String plntcd = PokUtils.getAttributeValue(entity, "PLNTCD", ", ", "", false);
				String key = sleorg + plntcd;
				if (duplVector.contains(key)) {
					continue;
				}
				duplVector.add(key);
				Element childElement = (Element) document.createElement("SLEORGNPLNTCODEELEMENT");
				SLEORGNPLNTCODELISTElement.appendChild(childElement);
				child = (Element) document.createElement("SLEORGNPLNTCODEACTION");
				child.appendChild(document.createTextNode("" + UPDATE_ACTIVITY));
				childElement.appendChild(child);
				child = (Element) document.createElement("SLEORG");
				child.appendChild(document.createTextNode("" + sleorg));
				childElement.appendChild(child);
				child = (Element) document.createElement("PLNTCD");
				child.appendChild(document.createTextNode("" + plntcd));
				childElement.appendChild(child);
			}
			duplVector.clear();
		} else {
			// SLEORGGRPLIST
			Element SLEORGGRPLISTElement = (Element) document.createElement("SLEORGGRPLIST");
			elem.appendChild(SLEORGGRPLISTElement);
			// SLEORGNPLNTCODELIST
			Element SLEORGNPLNTCODELISTElement = (Element) document.createElement("SLEORGNPLNTCODELIST");
			elem.appendChild(SLEORGNPLNTCODELISTElement);
		}
	}

	// /********************
	// * get audience values from t1 and t2 for this model, do it once and use for
	// each avail
	// */
	// private Vector[] getModelAudience(DiffEntity modelDiff, StringBuffer
	// debugSb){
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience for
	// "+modelDiff.getKey()+NEWLINE);
	//
	// EANFlagAttribute audienceAtt =
	// (EANFlagAttribute)modelDiff.getCurrentEntityItem().getAttribute("AUDIEN");
	// Vector currAudVct = new Vector(1);
	// Vector prevAudVct = new Vector(1);
	// Vector vct[] = new Vector[2];
	// vct[0] = currAudVct;
	// vct[1] = prevAudVct;
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience cur audienceAtt
	// "+audienceAtt+NEWLINE);
	// if (audienceAtt!=null){
	// MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
	// for (int i = 0; i < mfArray.length; i++){
	// // get selection
	// if (mfArray[i].isSelected()) {
	// currAudVct.addElement(mfArray[i].toString()); // this is long desc
	// }
	// }
	// }
	//
	// if (!modelDiff.isNew()){
	// audienceAtt =
	// (EANFlagAttribute)modelDiff.getPriorEntityItem().getAttribute("AUDIEN");
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience new audienceAtt
	// "+audienceAtt+NEWLINE);
	// if (audienceAtt!=null){
	// MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
	// for (int i = 0; i < mfArray.length; i++){
	// // get selection
	// if (mfArray[i].isSelected()) {
	// prevAudVct.addElement(mfArray[i].toString()); // this is long desc
	// }
	// }
	// }
	// }
	//
	// return vct;
	// }

	/********************
	 * this method has changed for BH. Create rows in the table as follows: Insert
	 * one row for each Audience in MODEL.AUDIEN & each Country in AVAIL.COUNTRYLIST
	 * where AVAILTYPE = 146 If the AVAIL was deleted, set Action = Delete If the
	 * AVAIL was added or updated, set Action = Update
	 * 
	 * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update If
	 * AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
	 *
	 * Note: Rows marked as Delete do not need further updating and the Action
	 * should not be changed by further updating. If any of the following steps have
	 * data that do not match an existing row in this table, ignore that data.
	 */

	// private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff,
	// StringBuffer debugSb){
	//
	//
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs
	// "+availDiff.getKey()+NEWLINE);
	//
	// // must account for AVAILa to have had US, CANADA at T1, and just CANADA at
	// T2 and a new
	// // AVAILb to have US at T2
	// // only delete action if ctry or aud was removed at t2!!! allow update to
	// override it
	//
	// EntityItem curritem = availDiff.getCurrentEntityItem();
	// EntityItem prioritem = availDiff.getPriorEntityItem();
	// if (availDiff.isDeleted()){ // If the AVAIL was deleted, set Action = Delete
	// // mark all records as delete
	// EANFlagAttribute ctryAtt =
	// (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for deleted avail:
	// ctryAtt "+
	// PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") +NEWLINE);
	// if (ctryAtt!=null){
	// MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
	// for (int im = 0; im < mfArray.length; im++){
	// // get selection
	// if (mfArray[im].isSelected()) {
	// String ctryVal = mfArray[im].getFlagCode();
	// String mapkey = ctryVal;
	// if (ctryAudElemMap.containsKey(mapkey)){
	// // dont overwrite
	// CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
	// ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted
	// "+availDiff.getKey()+
	// " "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
	// }else{
	// CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
	// ctryAudRec.setAction(DELETE_ACTIVITY);
	// ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
	// }
	// }
	// }
	// }
	// }else if (availDiff.isNew()){ //If the AVAIL was added or updated, set Action
	// = Update
	// // mark all records as update
	// EANFlagAttribute ctryAtt =
	// (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for new avail: ctryAtt
	// and anncodeAtt "+
	// PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+
	// PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") +NEWLINE);
	// if (ctryAtt!=null){
	// MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
	// for (int im = 0; im < mfArray.length; im++){
	// // get selection
	// if (mfArray[im].isSelected()) {
	// String ctryVal = mfArray[im].getFlagCode();
	// String mapkey = ctryVal;
	// if (ctryAudElemMap.containsKey(mapkey)){
	// CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
	// if (isPlannedAvail(availDiff) || DELETE_ACTIVITY.equals(rec.action)){
	// ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new
	// "+availDiff.getKey()+
	// " "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
	// rec.setUpdateAvail(availDiff);
	// }
	// }else{
	// CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
	// ctryAudRec.setAction(UPDATE_ACTIVITY);
	// ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for
	// new:"+availDiff.getKey()+" rec: "+
	// ctryAudRec.getKey() + NEWLINE);
	// }
	// }
	// }
	// }
	// }else{// else if one country in the countrylist has changed, update this row
	// to update!
	// HashSet prevSet = new HashSet();
	// HashSet currSet = new HashSet();
	// // get current set of countries
	// EANFlagAttribute fAtt =
	// (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for curr avail: fAtt
	// and curranncodeAtt "+
	// PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+
	// PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") +NEWLINE);
	// if (fAtt!=null && fAtt.toString().length()>0){
	// // Get the selected Flag codes.
	// MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
	// for (int i = 0; i < mfArray.length; i++){
	// // get selection
	// if (mfArray[i].isSelected()){
	// currSet.add(mfArray[i].getFlagCode());
	// } // metaflag is selected
	// }// end of flagcodes
	// }
	//
	// // get prev set of countries
	// fAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for prev avail: fAtt
	// and prevanncodeAtt " +
	// PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+
	// PokUtils.getAttributeFlagValue(prioritem, "ANNCODENAME") +NEWLINE);
	// if (fAtt!=null && fAtt.toString().length()>0){
	// // Get the selected Flag codes.
	// MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
	// for (int i = 0; i < mfArray.length; i++){
	// // get selection
	// if (mfArray[i].isSelected()){
	// prevSet.add(mfArray[i].getFlagCode());
	// } // metaflag is selected
	// }// end of flagcodes
	// }
	//
	// // look for changes in country
	// Iterator itr = currSet.iterator();
	// while(itr.hasNext()) {
	// String ctryVal = (String) itr.next();
	// if(!prevSet.contains(ctryVal)) { // If AVAIL.COUNTRYLIST has a country added,
	// set that row's Action = Update
	//
	// String mapkey = ctryVal;
	// if (ctryAudElemMap.containsKey(mapkey)){
	// CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
	// if (isPlannedAvail(availDiff) || DELETE_ACTIVITY.equals(rec.action)){
	// ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on
	// "+availDiff.getKey()+
	// " "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
	// rec.setUpdateAvail(availDiff);
	// }
	// }else{
	// CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
	// ctryAudRec.setAction(UPDATE_ACTIVITY);
	// ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for added
	// ctry:"+availDiff.getKey()+" rec: "+
	// ctryAudRec.getKey() + NEWLINE);
	// }
	// }else{
	// // BH this country has already exist, put into ctryaudrec, but don't udpate
	// Action to 'update'/'delete'.
	// String mapkey = ctryVal;
	// if (ctryAudElemMap.containsKey(mapkey)){
	// CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
	// if (isPlannedAvail(availDiff) || DELETE_ACTIVITY.equals(rec.action)){
	// ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry but new
	// aud on "+availDiff.getKey()+
	// " "+mapkey+" already exists, replace the orig "+rec+NEWLINE);
	// rec.setUpdateAvail(availDiff);
	// rec.setAction(CHEAT);
	// }
	// }else{
	// CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
	// ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for existing
	// ctry:"+availDiff.getKey()+" rec: "+
	// ctryAudRec.getKey() + NEWLINE);
	// }
	// }
	// }//end of currset while(itr.hasNext())
	// itr = prevSet.iterator();
	// while(itr.hasNext()) {
	// String ctryVal = (String) itr.next();
	// if(!currSet.contains(ctryVal)) { //If AVAIL.COUNTRYLIST has a country
	// deleted, set that row's Action = Delete
	// //create crossproduct between deleted ctry and previous audience for this
	// item
	// String mapkey = ctryVal;
	// if (ctryAudElemMap.containsKey(mapkey)){
	// CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
	// ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on
	// "+availDiff.getKey()+
	// " "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
	// }else{
	// CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
	// ctryAudRec.setAction(DELETE_ACTIVITY);
	// ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
	// ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for delete
	// ctry:"+availDiff.getKey()+" rec: "+
	// ctryAudRec.getKey() + NEWLINE);
	// }
	// }
	//
	// }
	// } // end avail existed at both t1 and t2
	// }

	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, boolean T1, StringBuffer debugSb) {

		ABRUtil.append(debugSb, "XMLAVAILElem.buildCtryAudRecs " + availDiff.getKey() + NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2
		// and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to
		// override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (T1) {
			if (!availDiff.isNew()) { // If the AVAIL was deleted, set Action = Delete
				// mark all records as delete
				EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb, "XMLAVAILElem.buildCtryAudRecs for deleted avail at T1: ctryAtt "
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
								ABRUtil.append(debugSb, "WARNING buildCtryAudRecs for deleted " + availDiff.getKey()
										+ " " + mapkey + " already exists, keeping orig " + rec + NEWLINE);
							} else {
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(DELETE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							}
						}
					}
				}
			}
		} else {
			if (!availDiff.isDeleted()) { // If the AVAIL was added or updated, set Action = Update
				// mark all records as update
				EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,
						"XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt "
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
								if (isPlannedAvail(availDiff) || DELETE_ACTIVITY.equals(rec.action)) {
									ABRUtil.append(debugSb,
											"WARNING buildCtryAudRecs for new /udpate" + availDiff.getKey() + " "
													+ mapkey + " already exists, replacing orig " + rec + NEWLINE);
									if (UPDATE_ACTIVITY.equals(rec.action)) {
										rec.setUpdateAvail(availDiff);
									} else {
										rec.setUpdateAvail(availDiff);
										rec.setAction(CHEAT);
									}
								}
							} else {
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
								ABRUtil.append(debugSb, "XMLAVAILElem.buildCtryAudRecs for new:" + availDiff.getKey()
										+ " rec: " + ctryAudRec.getKey() + NEWLINE);
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
	private boolean isPlannedAvail(DiffEntity diffitem) {

		boolean result = false;
		if (diffitem == null) {
			return false;
		}
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem prioritem = diffitem.getPriorEntityItem();
		if (diffitem.isDeleted()) {
			EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
			if (fAtt != null && fAtt.isSelected("146")) {
				result = true;
			}
		} else {
			EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
			if (fAtt != null && fAtt.isSelected("146")) {
				result = true;
			}
		}

		return result;
	}

	// /********************
	// * get entity with specified values - should only be one
	// * could be two if one was deleted and one was added, but the added one will
	// override and be an 'update'
	// */
	// private DiffEntity getEntityForAttrs(Hashtable table, String etype, String
	// attrCode, String attrVal,
	// String attrCode2, String attrVal2,StringBuffer debugSb)
	// {
	// DiffEntity diffEntity = null;
	// Vector allVct = (Vector)table.get(etype);
	//
	// ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs looking for
	// "+attrCode+":"+attrVal+" and "+
	// attrCode2+":"+attrVal2+" in "+etype+"
	// allVct.size:"+(allVct==null?"null":""+allVct.size())+NEWLINE);
	// if (allVct==null){
	// return diffEntity;
	// }
	// // find those of specified type
	// for (int i=0; i< allVct.size(); i++){
	// DiffEntity diffitem = (DiffEntity)allVct.elementAt(i);
	// EntityItem curritem = diffitem.getCurrentEntityItem();
	// EntityItem prioritem = diffitem.getPriorEntityItem();
	// if (diffitem.isDeleted()){
	// ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking["+i+"]:
	// deleted "+diffitem.getKey()+
	// " "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
	// if (fAtt!= null && fAtt.isSelected(attrVal)){
	// fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
	// if (fAtt!= null && fAtt.isSelected(attrVal2)){
	// diffEntity = diffitem; // keep looking for one that is not deleted
	// }
	// }
	// }else{
	// if (diffitem.isNew()){
	// ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking["+i+"]: new
	// "+diffitem.getKey()+
	// " "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
	// if (fAtt!= null && fAtt.isSelected(attrVal)){
	// fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode2);
	// if (fAtt!= null && fAtt.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// break;
	// }
	// }
	// }else{
	// // must check to see if the prior item had a match too
	// ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking["+i+"]:
	// current "+diffitem.getKey()+
	// " "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
	// if (fAtt!= null && fAtt.isSelected(attrVal)){
	// fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode2);
	// if (fAtt!= null && fAtt.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// break;
	// }
	// }
	// ABRUtil.append(debugSb,"XMLAVAILElem.getEntityForAttrs checking["+i+"]: prior
	// "+diffitem.getKey()+
	// " "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem,
	// attrCode2)+NEWLINE);
	// fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
	// if (fAtt!= null && fAtt.isSelected(attrVal)){
	// fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
	// if (fAtt!= null && fAtt.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// //break; see if there is another that is current
	// }
	// }
	// }
	// }
	// }
	//
	// return diffEntity;
	// }
	/********************
	 * return Boolean check If there isn't an AVAIL where AVAILTYPE="Planned
	 * Availability" (146), and ANNDATE less than "20100301"
	 * 
	 * @param table
	 *            Hashtable that contain all the entities.
	 * @param availtype
	 *            AVAIL.AVAILTYPE
	 * @param debugSb
	 *            StringBuffer logo output.
	 * 
	 *            If there isn't an AVAIL where AVAILTYPE="Planned Availability"
	 *            (146), and ANNDATE < 0100301 then from MODEL attributes Note:
	 *            Condition1 is there isn't an Planned Avail. Condition2 is
	 *            Model.ANNDATE < 0100301 - removed based on BH FS ABR XML System
	 *            Feed Mapping 20120612.doc
	 **/
	private boolean isDerivefromModel(Hashtable table, DiffEntity parentItem, StringBuffer debugSb, boolean T2) {
		boolean condition1 = true;
		// boolean condition2 = false;
		if (T2) {
			if (parentItem != null) {
				if (parentItem.getEntityType().equals("MODEL")) {
					// String ANNDATE_Fix = "2010-03-01";
					Vector allVct = (Vector) table.get("AVAIL");
					ABRUtil.append(debugSb, "T2 DerivefromModel.getAvails looking for AVAILTYPE: 146 " + "in AVAIL"
							+ " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
					if (allVct != null) {
						// find those of specified type
						for (int i = 0; i < allVct.size(); i++) {
							DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
							EntityItem curritem = diffitem.getCurrentEntityItem();
							if (!diffitem.isDeleted()) {
								ABRUtil.append(debugSb,
										"T2 XMLANNElem.DerivefromModel.getAvails checking[" + i + "]:New or Update"
												+ diffitem.getKey() + " AVAILTYPE: "
												+ PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
								EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
								if (fAtt != null && fAtt.isSelected("146")) {
									condition1 = false;
									break;
								}
							}
						}
					}
					// //BH FS ABR XML System Feed Mapping 20130426.doc add For GA offerings,
					// normally an Availability (AVAIL) of type Planned Availability is required;
					// however, if it is is Old Data (i.e. MODEL.ANNDATE <20100301), then it will be
					// considered World Wide (i.e. all of the Countries in COUNTRYLIST).
					// EntityItem curritem = parentItem.getCurrentEntityItem();
					// if (curritem != null) {
					// String anndate = PokUtils.getAttributeValue(curritem, "ANNDATE", ", ", CHEAT,
					// false);
					// condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
					// ABRUtil.append(debugSb,"T2 XMLANNElem.DerivefromModel.get model ANNDATE" +
					// curritem.getKey() + " ANNDATE: " + anndate
					// + NEWLINE);
					// }
				}

			}
		} else {
			if (parentItem != null) {
				if (parentItem.getEntityType().equals("MODEL")) {
					// String ANNDATE_Fix = "2010-03-01";
					Vector allVct = (Vector) table.get("AVAIL");
					ABRUtil.append(debugSb, "T1 DerivefromModel.getAvails looking for AVAILTYPE: 146 " + "in AVAIL"
							+ " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
					if (allVct != null) {
						// find those of specified type
						for (int i = 0; i < allVct.size(); i++) {
							DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
							EntityItem prioritem = diffitem.getPriorEntityItem();
							if (!diffitem.isNew()) {
								ABRUtil.append(debugSb,
										"T1 XMLANNElem.DerivefromModel.getAvails checking[" + i + "]:New or Update"
												+ diffitem.getKey() + " AVAILTYPE: "
												+ PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
								EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
								if (fAtt != null && fAtt.isSelected("146")) {
									condition1 = false;
									break;
								}
							}
						}
					}
					// //BH FS ABR XML System Feed Mapping 20130426.doc add For GA offerings,
					// normally an Availability (AVAIL) of type Planned Availability is required;
					// however, if it is is Old Data (i.e. MODEL.ANNDATE <20100301), then it will be
					// considered World Wide (i.e. all of the Countries in COUNTRYLIST).
					// EntityItem priorItem = parentItem.getPriorEntityItem();
					// if (priorItem != null) {
					// String anndate = PokUtils.getAttributeValue(priorItem, "ANNDATE", ", ",
					// CHEAT, false);
					// condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
					// ABRUtil.append(debugSb,"T1 XMLANNElem.DerivefromModel.get model ANNDATE" +
					// priorItem.getKey() + " ANNDATE: " + anndate
					// + NEWLINE);
					// }
				}

			}
		}
		return condition1;
		// && condition2;
	}

	/********************
	 * new change base on the BH FS ABR XML System Feed Mapping 20130214.doc for the
	 * RCQ00222829 - BHCATLGOR
	 * 
	 * 
	 * CATAUDIENCE CBP Catalog - Business Partner Catalog - Business Partner
	 * CATAUDIENCE CIR Catalog - Indirect/Reseller Catalog - Indirect/Reseller
	 * CATAUDIENCE LE LE Direct LE Direct CATAUDIENCE None None None CATAUDIENCE
	 * Shop Public Public
	 * 
	 * AUDIEN 100 SDI Channel AUDIEN 10046 Catalog - Business Partner Catalog -
	 * Business Partner AUDIEN 10048 Catalog - Indirect/Reseller Catalog -
	 * Indirect/Reseller AUDIEN 10054 Public Public AUDIEN 10055 None None AUDIEN
	 * 10062 LE Direct LE Direct AUDIEN 10067 DAC/MAX DAC/MAX get entity with
	 * specified values - should only be one could be two if one was deleted and one
	 * was added, but the added one will override and be an 'update'
	 */
	private DiffEntity getCatlgor(Hashtable table, Vector mdlAudVct[], String attrVal2, StringBuffer debugSb) {
		// remove the check of the CATLGOR Audience 2012-08-24
		DiffEntity diffEntity = null;
		// RCQ00222829 change CATLGOR to BHCATLGOR base on the doc BH FS ABR XML System
		// Feed Mapping 20130214.doc
		Vector allVct = (Vector) table.get("BHCATLGOR");
		// String attrCode2 = "OFFCOUNTRY"; // need flag code
		String attrCode2 = BHCOUNTRYLIST; // need flag code
		ABRUtil.append(debugSb, "XMLCtryAudElem.getCatlgor looking for " + attrCode2 + ":" + attrVal2
				+ " in CATLGOR allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}

		// find those of specified type
		diffloop: for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			// add the check of the status of BHCATLGOR
			String BHStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
			if (!STATUS_FINAL.equals(BHStatus)) {
				continue;
			}
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb, "XMLCtryAudElem.getCatlgor checking[" + i + "]: deleted " + diffitem.getKey()
						+ " " + attrCode2 + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
				EANFlagAttribute fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
				if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
					diffEntity = diffitem; // keep looking for one that is not deleted
					break;
				}
			} else {
				if (diffitem.isNew()) {
					ABRUtil.append(debugSb, "XMLCtryAudElem.getCatlgor checking[" + i + "]: new " + diffitem.getKey()
							+ " " + attrCode2 + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt2 = (EANFlagAttribute) curritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break diffloop;
					}
				} else {
					// must check to see if the prior item had a match too
					ABRUtil.append(debugSb,
							"XMLCtryAudElem.getCatlgor checking[" + i + "]: current " + diffitem.getKey() + " "
									+ attrCode2 + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt2 = (EANFlagAttribute) curritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break diffloop;
					}

					ABRUtil.append(debugSb, "XMLCtryAudElem.getCatlgor checking[" + i + "]: prior " + diffitem.getKey()
							+ " " + attrCode2 + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break; // see if there is another that is current
					}
				}
			}
		}

		return diffEntity;
	}

	// private DiffEntity getCatlgor(Hashtable table, Vector mdlAudVct[], String
	// attrVal2,StringBuffer debugSb)
	// {
	// // remove the check of the CATLGOR Audience 2012-08-24
	// DiffEntity diffEntity = null;
	// Vector allVct = (Vector)table.get("CATLGOR");
	// String attrCode2 = "OFFCOUNTRY"; // need flag code
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor looking for " +
	// attrCode2+":"+attrVal2+" in CATLGOR
	// allVct.size:"+(allVct==null?"null":""+allVct.size())+NEWLINE);
	// if (allVct==null){
	// return diffEntity;
	// }
	//
	// // find those of specified type
	// diffloop:for (int i=0; i< allVct.size(); i++){
	// DiffEntity diffitem = (DiffEntity)allVct.elementAt(i);
	// EntityItem curritem = diffitem.getCurrentEntityItem();
	// EntityItem prioritem = diffitem.getPriorEntityItem();
	// if (diffitem.isDeleted()){
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking["+i+"]: deleted
	// "+diffitem.getKey()+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt2 = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
	// if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
	// diffEntity = diffitem; // keep looking for one that is not deleted
	// break;
	// }
	// }else{
	// if (diffitem.isNew()){
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking["+i+"]: new
	// "+diffitem.getKey()+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt2 = (EANFlagAttribute)curritem.getAttribute(attrCode2);
	// if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// break diffloop;
	// }
	// }else{
	// // must check to see if the prior item had a match too
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking["+i+"]: current
	// "+diffitem.getKey()+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem,
	// attrCode2)+NEWLINE);
	// EANFlagAttribute fAtt2 = (EANFlagAttribute)curritem.getAttribute(attrCode2);
	// if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// break diffloop;
	// }
	//
	// ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking["+i+"]: prior
	// "+diffitem.getKey()+
	// " "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem,
	// attrCode2)+NEWLINE);
	// fAtt2 = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
	// if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
	// diffEntity = diffitem;
	// break; //see if there is another that is current
	// }
	// }
	// }
	// }
	//
	// return diffEntity;
	// }
	/*******************************
	 * one for every AVAIL.COUNTRYLIST where availtype='planned availbility(146)
	 * include the avails (delete,new and update)
	 *
	 */
	private static class CtryAudRecord extends CtryRecord {

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

		/*********************
		 * 3. <EARLIESTSHIPDATE> AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned
		 * Availability" (146) this avail cannot be deleted at this point
		 * 
		 * * <PUBFROM> The first applicable / available date is used. 1. MDLCATLGOR-d:
		 * CATLGOR.PUBFROM 2. AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order"
		 * (143) 3. ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned
		 * Availability (146). 4. Empty (aka Null) * <PUBTO> The first applicable /
		 * available date is used. 1. MDLCATLGOR-d: CATLGOR.PUBTO 2. AVAIL.EFFECTIVEDATE
		 * where AVAIL.AVAILTYPE = "Last Order" (149) 3 . Empty (aka Null)
		 * 
		 * * <ENDOFSERVICEDATE> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = End of Service(151) 2. Empty (aka
		 * Null)
		 */
		void setAllFields(DiffEntity parentDiffEntity, DiffEntity catlgorDiff, DiffEntity foAvailDiff,
				DiffEntity loAvailDiff, DiffEntity endDevAvailDiff, DiffEntity endAvailDiff, DiffEntity endMktAvailDiff, DiffEntity epicAvailDiff,
				Hashtable table, DiffEntity[] plAvailDiff, String path, boolean isExistfinal, boolean compatModel,
				StringBuffer debugSb) {
			// EntityItem parentItem = parentDiffEntity.getCurrentEntityItem();

			availStatus = "0020";
			rfravailStatus = "0040";

			// ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = lanned
			// Availability (146).
			String[] anndates = deriveAnnDate(plAvailDiff[1], parentDiffEntity, false, debugSb);
			String[] anndatesT1 = deriveAnnDate(plAvailDiff[0], parentDiffEntity, true, debugSb);

			// ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE =
			// lanned Availability (146).
			String[] annnumbers = deriveAnnNumber(plAvailDiff[1], false, debugSb);
			String[] annnumbersT1 = deriveAnnNumber(plAvailDiff[0], true, debugSb);

			// FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143 or null.
			String[] firstorders = deriveFIRSTORDER(plAvailDiff[1], parentDiffEntity, foAvailDiff, false, debugSb);
			String[] firstordersT1 = deriveFIRSTORDER(plAvailDiff[0], parentDiffEntity, foAvailDiff, true, debugSb);

			// LASTORDER is equal to PUBTO.
			// PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned
			// Availability" (146)
			// get current value

			// Bing new added 6/27/2013
			String[] plannedavailabilitys = deriveplannedavailability(plAvailDiff[1], parentDiffEntity, false, debugSb);
			String[] plannedavailabilitysT1 = deriveplannedavailability(plAvailDiff[0], parentDiffEntity, true,
					debugSb);

			// If the country in AVAIL.COUNTRYLIST was newly added or the AVAIL(first order)
			// is newly added, then set Action UPDATE_ACTIVITY
			// If the country in AVAIL.COUNTRYLIST was deleted or AVAIL was deleted, but get
			// the current pubfrom is equal to the prior one, then don't set Action
			// UPDATE_ACTIVITY
			// If the AVAIL was updated, but get the current pubfrom is equal to the prior
			// one, then don't set Action UPDATE_ACTIVITY

			// set PUBFROM
			String[] pubfroms = derivePubFrom(plAvailDiff[1], parentDiffEntity, catlgorDiff, foAvailDiff, false,
					debugSb);
			String[] pubfromsT1 = derivePubFrom(plAvailDiff[0], parentDiffEntity, catlgorDiff, foAvailDiff, true,
					debugSb);

			// set PUBTO
			String[] pubtos = derivePubTo(parentDiffEntity, catlgorDiff, loAvailDiff, false, debugSb);
			String[] pubtosT1 = derivePubTo(parentDiffEntity, catlgorDiff, loAvailDiff, true, debugSb);

			// set WDANNDATE
			String[] wdanndates = deriveWDANNDATE(parentDiffEntity, endMktAvailDiff, false, debugSb);
			String[] wdanndatesT1 = deriveWDANNDATE(parentDiffEntity, endMktAvailDiff, true, debugSb);
			
			// set ENDOFMARKETANNNUMBER Story 1865979 Withdrawal RFA Number generation
            String[] eomannnums = deriveENDOFMARKETANNNUMBER(endMktAvailDiff, false, debugSb);
            String[] eomannnumsT1 = deriveENDOFMARKETANNNUMBER(endMktAvailDiff, true, debugSb);

			// set LASTORDER
			String[] lastorders = deriveLastOrder(parentDiffEntity, loAvailDiff, false, debugSb);
			String[] lastordersT1 = deriveLastOrder(parentDiffEntity, loAvailDiff, true, debugSb);

			// set EOSANNDATE
			String[] eosanndates = deriveEOSANNDATE(endAvailDiff, false, debugSb);
			String[] eosanndatesT1 = deriveEOSANNDATE(endAvailDiff, true, debugSb);
			
			// set ENDOFDEVELOPMENTDATE
			String[] eodavaildate = deriveENDOFDEVELOPMENTDATE(endDevAvailDiff, false, debugSb);
			String[] eodavaildateT1 = deriveENDOFDEVELOPMENTDATE(endDevAvailDiff, true, debugSb);
						
			// set EODANNDATE
			String[] eodanndates = deriveEODANNDATE(endDevAvailDiff, false, debugSb);
			String[] eodanndatesT1 = deriveEODANNDATE(endDevAvailDiff, true, debugSb);
						
			//set ENDOFSERVICEANNNUMBER Story 1865979 Withdrawal RFA Number generation
            String[] eosannnums = deriveENDOFSERVICEANNNUMBER(endAvailDiff, false, debugSb);
            String[] eosannnumsT1 = deriveENDOFSERVICEANNNUMBER(endAvailDiff, true, debugSb);
            
			// BH set ENDOFSERVICE
			String[] endofservices = deriveENDOFSERVICE(endAvailDiff, false, debugSb);
			String[] endofservicesT1 = deriveENDOFSERVICE(endAvailDiff, true, debugSb);

			// set ORDERSYSNAME
			String[] ordersysnames = deriveORDERSYSNAME(plAvailDiff[1], false, debugSb);
			String[] ordersysnamesT1 = deriveORDERSYSNAME(plAvailDiff[0], true, debugSb);

			String[] rfaRefNumbers = deriveRFAREFNUMBER(epicAvailDiff, false, debugSb);
			String[] rfaRefNumbersT1 = deriveRFAREFNUMBER(epicAvailDiff, true, debugSb);
			ABRUtil.append(debugSb, "ordersysnames:" + ordersysnames[0] + "," + ordersysnames[1] + ",ordersysnamesT1:"
					+ ordersysnamesT1[0] + "," + ordersysnamesT1[1] + NEWLINE);
			ABRUtil.append(debugSb, "rfaRefNumbers:" + rfaRefNumbers[0] + "," + rfaRefNumbers[1] + ",rfaRefNumbersT1:"
					+ rfaRefNumbersT1[0] + "," + rfaRefNumbersT1[1] + NEWLINE);
			ABRUtil.append(debugSb, "epicAvailDiff:" + epicAvailDiff + ",plAvailDiff:" + plAvailDiff + NEWLINE);
			// RFAREFNUMBER
			handleResults(anndates, anndatesT1, annnumbers, annnumbersT1, firstorders, firstordersT1,
					plannedavailabilitys, plannedavailabilitysT1, pubfroms, pubfromsT1, pubtos, pubtosT1, wdanndates,
					wdanndatesT1, eomannnums, eomannnumsT1, lastorders, lastordersT1, endofservices, endofservicesT1,
					eodanndates, eodanndatesT1, eodavaildate, eodavaildateT1, eosanndates, eosanndatesT1, eosannnums,
					eosannnumsT1, ordersysnames, ordersysnamesT1, country, isExistfinal, compatModel, debugSb);
			// String cofsubcat =
			// parentDiffEntity.getAttribute("COFSUBCAT")[0].getShortDescription();
			
			// boolean SLEORGGRPChaned = SLEORGGRP.hasChanges(table, parentItem,
			// plAvailDiff[1], path, country, debugSb);
			// if (SLEORGGRPChaned)
			// if(existfnialT2){
			// setAction(UPDATE_ACTIVITY);
			// setrfrAction(UPDATE_ACTIVITY);
			// }else{
			// setrfrAction(UPDATE_ACTIVITY);
			// }
		}
		
		/****************************
		 * <ENDOFDEVELOPMENTDATE>
		 * The first applicable / available date is used.
		 *	1.	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = “End of Development” ()
		 *	2.	Empty (aka Null)
		 * 
		 */
		private String[] deriveENDOFDEVELOPMENTDATE(DiffEntity endDevAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveENDOFDEVELOPMENTDATE endDevAvailDiff: "
					+ (endDevAvailDiff == null ? "null" : endDevAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// try to get it from the lastorder avail
				temps = AvailUtil.getAvailAttributeDate(findT1, endDevAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}
		
		/****************************
		 * <EODANNDATE>
		 * The first applicable / available date is used.
		 * 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = “End of Development” () and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13)
		 * 2.	Empty (aka Null)
		 */
		private String[] deriveEODANNDATE(DiffEntity endDevAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveEODANNDATE endDevAvailDiff: "
					+ (endDevAvailDiff == null ? "null" : endDevAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the AVAIL(146)--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endDevAvailDiff, thedate, rfrthedate, country,
						"ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/****************************
		 * <ENDOFSERVICEDATE> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = End of Service (151) 2. Empty
		 * (aka Null)
		 * 
		 */
		private String[] deriveENDOFSERVICE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveENDOFSERVICE endAvailDiff: "
					+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// try to get it from the lastorder avail
				temps = AvailUtil.getAvailAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}

		/****************************
		 * <PUBTO> The first applicable / available date is used. 1. MDLCATLGOR-d:
		 * CATLGOR.PUBTO 2. AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order"
		 * (149) // 3. MODEL. WTHDRWEFFCTVDATE 3 . Empty (aka Null)
		 * 
		 */
		private String[] derivePubTo(DiffEntity parentDiff, DiffEntity catlgorDiff, DiffEntity loAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			ABRUtil.append(debugSb,
					"XMLAVAILElem.derivePubTo catlgorDiff: " + (catlgorDiff == null ? "null" : catlgorDiff.getKey())
							+ " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey()) + "findT1:"
							+ findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the catlgorDiff PUBTO date
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate,
						country, "PUBTO", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the loAvailDiff EFFECTIVEDATE date
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the WTHDRWEFFCTVDATE attrubte value of the parent entity
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "WTHDRWEFFCTVDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;

		}

		/****************************
		 * <PUBFROM> 1. MDLCATLGOR-d: CATLGOR.PUBFROM The first applicable / available
		 * date is used. 2. AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order"
		 * (143) 3. ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned
		 * Availability (146).
		 * 
		 * 4. MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE =Planned
		 * Availability 5. MODEL.ANNDATE
		 * 
		 * 4. Empty (aka Null)
		 * 
		 */
		private String[] derivePubFrom(DiffEntity plAvailDiff, DiffEntity parentDiff, DiffEntity catlgorDiff,
				DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			ABRUtil.append(debugSb,
					"XMLAVAILElem.derivePubFrom plAvailDiff: " + (plAvailDiff == null ? "null" : plAvailDiff.getKey())
							+ " foAvailDiff: " + (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:"
							+ findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 1 get the catlgorDiff.PUBFROM
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate,
						country, "PUBFROM", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2 get the foAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 3 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL
				// where AVAIL.AVAILTYPE = "Planned Availability" (146).
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 4 get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 5. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}

		/****************************
		 * <ANNNUMBER> 1. ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where
		 * AVAIL.AVAILTYPE = lanned Availability (146). 2. Empty (aka Null)
		 */
		private String[] deriveAnnNumber(DiffEntity plAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveAnnNumber plAvailDiff: "
					+ (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the AVAIL(146)--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country,
						"ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/****************************
		 * <ANNDATE> 1. ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where
		 * AVAIL.AVAILTYPE = lanned Availability (146). 2. Empty (aka Null) //TODO 2.
		 * MODEL.ANNDATE BH ABR XML System Feed Mapping 20120220.doc
		 */
		private String[] deriveAnnDate(DiffEntity plAvailDiff, DiffEntity parentDiffEntity, boolean findT1,
				StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];

			String temps[] = new String[2];

			ABRUtil.append(debugSb, "XMLAVAILElem.deriveAnnDate plAvailDiff: "
					+ (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			// 1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE
			// = Planned Availability (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) && CHEAT.equals(rfrthedate)) {
				// 5. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/****************************
		 * <FIRSTORDER> 1. MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where
		 * AVAIL.AVAILTYPE =First Order 2. <ANNDATE> // UDPATE as following sequence 1.
		 * MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE =First Order 2.
		 * MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE
		 * =Planned Availability 3. MODELAVAIL-d: AVAIL.EFFECTIVEDATE where
		 * AVAIL.AVAILTYPE =Planned Availability 4. <ANNDATE>
		 */
		private String[] deriveFIRSTORDER(DiffEntity plAvailDiff, DiffEntity parentDiffEntity, DiffEntity foAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,
					"XMLAVAILElem.deriveFIRSTORDER plAvailDiff: "
							+ (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + " foAvailDiff: "
							+ (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 1.get the first order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2.get the plan avail--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 3.get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 4. get parentDiff.ANNDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/*
		 * BH FS ABR XML System Feed Mapping 20130614 f) <PLANNEDAVAILABILITY> The first
		 * applicable / available date is used: AVAIL.EFFECTIVEDATE where
		 * AVAIL.AVAILTYPE =Planned Availability(146) MODEL. GENAVAILDATE MODEL.ANNDATE
		 */
		private String[] deriveplannedavailability(DiffEntity plAvailDiff, DiffEntity parentDiffEntity, boolean findT1,
				StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveplannedavailability plAvailDiff: "
					+ (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 1. get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, plAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2. get MODEL. GENAVAILDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "GENAVAILDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2. get MODEL. GENAVAILDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "ANNDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		
		/****************************
		 * <ENDOFSERVICEANNNUMBER>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "End of Service" (151) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13)
		 2. Empty (aka Null)
		 */
		private String[] deriveENDOFSERVICEANNNUMBER(DiffEntity endAvailDiff,boolean findT1, StringBuffer debugSb){
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveENDOFSERVICEANNNUMBER endAvailDiff: " + (endAvailDiff == null ? "null" : endAvailDiff.getKey())
                + "findT1:" + findT1 + NEWLINE);
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNNUMBER where ANNTYPE =13
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;	
		}
		
		/****************************
		 * <EOSANNDATE> 1. MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where
		 * AVAIL.AVAILTYPE =End of Service (151) and ANNOUNCEMENT.ANNTYPE = "End Of Life
		 * - Discontinuance of service" (13) 2. Empty (aka Null)
		 * 
		 */
		private String[] deriveEOSANNDATE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveEOSANNDATE endAvailDiff: "
					+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =13
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/****************************
		 * <WDANNDATE> delete 1. MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where
		 * AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life
		 * - Withdrawal from mktg" (14) 2. Empty (aka Null) i) <WDANNDATE> The first
		 * applicable / available date is used. 1. MODELAVAIL-d: AVAILANNA-d:
		 * ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "End of Marketing" (149) and
		 * ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14) 2.
		 * MODEL.WITHDRAWDATE 3. Empty (aka Null)
		 * 
		 * 
		 */
		private String[] deriveWDANNDATE(DiffEntity parentDiffEntity, DiffEntity endMktAvailDiff, boolean findT1,
				StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveWDANNDATE endMktAvailDiff: "
					+ (endMktAvailDiff == null ? "null" : endMktAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endMktAvailDiff, thedate, rfrthedate, country, "14",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2. get MODEL. WITHDRAWDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "WITHDRAWDATE",
						debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		
		/****************************
		 * <ENDOFMARKETANNNUMBER>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "End of Marketing" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		 2. Empty (aka Null)
		 */
		private String[] deriveENDOFMARKETANNNUMBER(DiffEntity endMktAvailDiff, boolean findT1, StringBuffer debugSb){
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveEndOfMarketAnnNumber endMktAvailDiff: " + (endMktAvailDiff == null ? "null" : endMktAvailDiff.getKey())
                + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(149)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endMktAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}		
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		/***********************************************************************
		 * <LASTORDER> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149) 2 . Empty (aka
		 * Null)
		 * 
		 * 2. MODEL.WTHDRWEFFCTVDATE
		 */
		private String[] deriveLastOrder(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1,
				StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveLastOrder loAvailDiff: "
					+ (loAvailDiff == null ? "null" : loAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get AVAIL.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country,
						"EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2. get MODEL.WTHDRWEFFCTVDATE
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate,
						"WTHDRWEFFCTVDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		private String[] deriveORDERSYSNAME(DiffEntity plAvailDiff, boolean findT1, StringBuffer debugSb) {
			String orderSysName = CHEAT;
			String rfrOrderSysName = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveORDERSYSNAME plAvailDiff: "
					+ (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(orderSysName) || CHEAT.equals(rfrOrderSysName)) {
				// 1. get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, plAvailDiff, orderSysName, rfrOrderSysName, country,
						"ORDERSYSNAME", debugSb);
				orderSysName = temps[0];
				rfrOrderSysName = temps[1];
			}
			returns[0] = orderSysName;
			returns[1] = rfrOrderSysName;
			return returns;
		}

		/*
		 * private String[] deriveUSDOCNO(DiffEntity plAvailDiff, boolean findT1,
		 * StringBuffer debugSb) { String usdocNo = CHEAT; String rfrUsdicNo = CHEAT;
		 * String returns[] = new String[2]; String temps[] = new String[2];
		 * ABRUtil.append(debugSb, "XMLAVAILElem.deriveUSDOCNO plAvailDiff: " +
		 * (plAvailDiff == null ? "null" : plAvailDiff.getKey()) + "findT1:" + findT1 +
		 * NEWLINE); if (CHEAT.equals(usdocNo) || CHEAT.equals(rfrUsdicNo)) { // 1. get
		 * the plAvailDiff.EFFECTIVEDATE temps = AvailUtil.getAvailAttributeDate(findT1,
		 * plAvailDiff, usdocNo, rfrUsdicNo, country, "USDOCNO", debugSb); usdocNo =
		 * temps[0]; rfrUsdicNo = temps[1]; } returns[0] = usdocNo; returns[1] =
		 * rfrUsdicNo; return returns; }
		 */

		private String[] deriveRFAREFNUMBER(DiffEntity epicAvailDiff, boolean findT1, StringBuffer debugSb) {
			String rfaRefNumber = CHEAT;
			String rfrRfaRefNumber = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb, "XMLAVAILElem.deriveRFAREFNUMBER plAvailDiff: "
					+ (epicAvailDiff == null ? "null" : epicAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(rfaRefNumber) || CHEAT.equals(rfrRfaRefNumber)) {
				// 1. get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, epicAvailDiff, rfaRefNumber, rfrRfaRefNumber, country,
						"RFAREFNUMBER", debugSb);
				rfaRefNumber = temps[0];
				rfrRfaRefNumber = temps[1];
				ABRUtil.append(debugSb, "deriveRFAREFNUMBER(),temps[0]:" + temps[0] + ",temps[1]:" + temps[1] + NEWLINE);
			}
			ABRUtil.append(debugSb, "deriveRFAREFNUMBER()" + rfaRefNumber + "," + rfrRfaRefNumber + NEWLINE);
			returns[0] = rfaRefNumber;
			returns[1] = rfrRfaRefNumber;
			return returns;
		}

		String getCountry() {
			return country;
		}

		String getKey() {
			return country;
		}

		public String toString() {
			return (availDiff == null ? "This is no avail." : availDiff.getKey()) + " " + getKey() + " action:"
					+ action;
		}
	}
}
