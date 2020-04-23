package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import org.w3c.dom.*;
import java.util.*;
import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 *  Class used to hold info and structure to be generated for the xml feed
 * for abrs.
 * A.	<CATALOGOVERRIDELIST>
 * 1.	<CATALOGOVERRIDEELEMENT>
 * 	There is one instance of this for each unique pair of values <AUDIENCE_FC> and <COUNTRY_FC> for all related instances of CATLGOR
 * 2.	<CATALOGOVERRIDEACTION>
 * 	If either value in the pair <AUDIENCE_FC> or <COUNTRY_FC> was deleted, then this value is “Delete�?. If neither was deleted and at least one of the values is new, then this value is “Update�?.
 */
//	 $Log: XMLCATAElem.java,v $
//	 Revision 1.13  2015/01/26 15:53:40  wangyul
//	 fix the issue PR24222 -- SPF ADS abr string buffer
//	
//	 Revision 1.12  2013/08/16 05:10:53  wangyulo
//	 fix the issue RCQ00222829 for the BHCATLGOR which change the CATLGOR to BHCATLGOR
//	
//	 Revision 1.11  2012/08/02 13:47:21  wangyulo
//	 fix the defect 770704- BH Defect BHALM109267 - correction to SWPRODSTRUCT for old data
//	
//	 Revision 1.10  2010/09/01 09:21:58  yang
//	 build CtryAudRecs for SWPRODSTRUCT. there is no AUDIEN to match,  just do all CATLGOR audiences for the matching countries.
//	
//	 Revision 1.9  2010/08/26 08:52:42  yang
//	 Add CATLGOR vertor is null check.
//	
//	 Revision 1.8  2010/07/29 14:11:45  yang
//	 Changed, when the Action is 'Delete', the rest element are null, except the OFFCOUNTRY_FC and ANDIENCE.
//	
//	 Revision 1.7  2010/07/29 09:16:08  yang
//	 fix the defect.
//	
//	 Revision 1.6  2010/07/29 08:28:44  yang
//	 change Method getKey(){ return coutnry+"|"+audience}
//	
//	 Revision 1.5  2010/07/29 03:41:04  yang
//	 fix defect at  line 417. change String coutryaudience[] = null  to new String[2].
//	
//	 Revision 1.4  2010/07/22 09:47:09  yang
//	 changed log output format.
//	
//	 Revision 1.3  2010/06/30 08:07:05  yang
//	 change for Catalog overider
//	
//	 Revision 1.2  2010/06/15 19:11:47  rick
//	 changing split method to use tokenizer instead to be
//	 compatible with 1.3 compiler.
//	
//	 Revision 1.1  2010/06/12 06:58:22  yang
//	 Catlog overrider for BH 1.0
//	

//Blue Harmony 1.0 Functional Specification SG ABR, Data Transformation System Feed SG ABRs ADS System Feed
//
public class XMLCATAElem extends XMLElem {
	private static final String DEFAULT_NO = "";

	private static final String DEFAULT_YES = "";

	/**********************************************************************************
	 * Constructor for CATALOGOVERRIDELIST elements
	 *1.0	<CATALOGOVERRIDELIST>		2
	 *1.0	<CATALOGOVERRIDEELEMENT>		3
	 *1.0	<CATALOGOVERRIDEACTION>	</CATALOGOVERRIDEACTION>	4
	 *1.0	<AUDIENCE>	</AUDIENCE>	4
	 *1.0	<COUNTRY_FC>	</COUNTRY_FC>	4
	 *1.0	<PUBFROM>	</PUBFROM>	4
	 *1.0	<PUBTO>	</PUBTO>	4
	 *1.0	<ADDTOCART>	</ADDTOCART>	4
	 *1.0	<BUYABLE>	</BUYABLE>	4
	 *1.0	<PUBLISH>	</PUBLISH>	4
	 *1.0	<CUSTOMIZEABLE>	</CUSTOMIZEABLE>	4
	 *1.0	<HIDE>	</HIDE>	4
	 *1.0		</CATALOGOVERRIDEELEMENT>	3
	 *1.0		</CATALOGOVERRIDELIST>	2
	 *
	 */
	public XMLCATAElem() {
		super("CATALOGOVERRIDEELEMENT");
	}

	/**********************************************************************************
	 *
	 *
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
		    //RCQ00222829 change  CATLGOR to BHCATLGOR base on the doc BH FS ABR XML System Feed Mapping 20130214.doc
			Vector allVct = (Vector) table.get("BHCATLGOR");
			if (allVct != null && allVct.size() > 0) {
				// get model audience values, t2[0] current, t1[1] prior
				Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
				TreeMap ctryAudElemMap = new TreeMap();
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity catlgorlDiff = (DiffEntity) allVct.elementAt(i);
					//add the check of the status of BHCATLGOR
					EntityItem curritem = catlgorlDiff.getCurrentEntityItem();
					String BHStatus  = PokUtils.getAttributeFlagValue(curritem,"STATUS");
					if(!STATUS_FINAL.equals(BHStatus)){
						continue;
					}
	                if (parentItem.getEntityType().equals("SWPRODSTRUCT")){
	                	// there is no AUDIEN to match to. You will just do all CATLGOR audiences for the matching countries.
	                	buildSWCtryAudRecs(ctryAudElemMap, catlgorlDiff, debugSb);
	                }else{
	                    //get all possible combinations of MODEL.AUDIEN crossproduct Catlgor.OFFCOUNTRY
	    				buildCtryAudRecs(ctryAudElemMap, catlgorlDiff, mdlAudVct, debugSb);
	                }
					
				}// end each planned avail

				// output the elements
				Collection ctryrecs = ctryAudElemMap.values();
				Iterator itr = ctryrecs.iterator();
				while (itr.hasNext()) {
					CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
					if (!ctryAudRec.isDeleted()) {
						//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
						// add other info now
						ctryAudRec.setAllFields(debugSb);
					}else{
						//If the Action is Delete, set the rest elements to null.
						ctryAudRec.addtocart = CHEAT;
						ctryAudRec.buyable = CHEAT;
						ctryAudRec.publish = CHEAT;
						ctryAudRec.customizeable = CHEAT;
						ctryAudRec.hide = CHEAT;
					}

					if (ctryAudRec.isDisplayable()) {
						createNodeSet(document, parent, ctryAudRec, debugSb);
					} else {
						ABRUtil.append(debugSb,"XMLCATAElem.addElements no changes found for " + ctryAudRec + NEWLINE);
					}
					ctryAudRec.dereference();
				}

				// release memory
				ctryAudElemMap.clear();
				mdlAudVct = null;
			} else {
				ABRUtil.append(debugSb,"XMLCATAElem.addElements no catlogor found" + NEWLINE);
			}

		}
//	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
//		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
//		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
//		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
//		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
//		Vector allVct = (Vector) table.get("CATLGOR");
//		if (allVct != null && allVct.size() > 0) {
//			// get model audience values, t2[0] current, t1[1] prior
//			Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
//			TreeMap ctryAudElemMap = new TreeMap();
//			for (int i = 0; i < allVct.size(); i++) {
//				DiffEntity catlgorlDiff = (DiffEntity) allVct.elementAt(i);
//                if (parentItem.getEntityType().equals("SWPRODSTRUCT")){
//                	// there is no AUDIEN to match to. You will just do all CATLGOR audiences for the matching countries.
//                	buildSWCtryAudRecs(ctryAudElemMap, catlgorlDiff, debugSb);
//                }else{
//                    //get all possible combinations of MODEL.AUDIEN crossproduct Catlgor.OFFCOUNTRY
//    				buildCtryAudRecs(ctryAudElemMap, catlgorlDiff, mdlAudVct, debugSb);
//                }
//				
//			}// end each planned avail
//
//			// output the elements
//			Collection ctryrecs = ctryAudElemMap.values();
//			Iterator itr = ctryrecs.iterator();
//			while (itr.hasNext()) {
//				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
//				if (!ctryAudRec.isDeleted()) {
//					//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
//					// add other info now
//					ctryAudRec.setAllFields(debugSb);
//				}else{
//					//If the Action is Delete, set the rest elements to null.
//					ctryAudRec.addtocart = CHEAT;
//					ctryAudRec.buyable = CHEAT;
//					ctryAudRec.publish = CHEAT;
//					ctryAudRec.customizeable = CHEAT;
//					ctryAudRec.hide = CHEAT;
//				}
//
//				if (ctryAudRec.isDisplayable()) {
//					createNodeSet(document, parent, ctryAudRec, debugSb);
//				} else {
//					ABRUtil.append(debugSb,"XMLCATAElem.addElements no changes found for " + ctryAudRec + NEWLINE);
//				}
//				ctryAudRec.dereference();
//			}
//
//			// release memory
//			ctryAudElemMap.clear();
//			mdlAudVct = null;
//		} else {
//			ABRUtil.append(debugSb,"XMLCATAElem.addElements no catlogor found" + NEWLINE);
//		}
//
//	}

	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Document document, Element parent, CtryAudRecord ctryAudRec, StringBuffer debugSb) {
		Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
		addXMLAttrs(elem);
		parent.appendChild(elem);

		// add child nodes
		Element child = (Element) document.createElement("CATALOGOVERRIDEACTION");
		child.appendChild(document.createTextNode(ctryAudRec.getAction()));
		elem.appendChild(child);
		child = (Element) document.createElement("AUDIENCE");
		child.appendChild(document.createTextNode(ctryAudRec.getAudience()));
		elem.appendChild(child);
		child = (Element) document.createElement("COUNTRY_FC");
		child.appendChild(document.createTextNode(ctryAudRec.getCountry()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBFROM");
		child.appendChild(document.createTextNode(ctryAudRec.getPubFrom()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBTO");
		child.appendChild(document.createTextNode(ctryAudRec.getPubTo()));
		elem.appendChild(child);
		child = (Element) document.createElement("ADDTOCART");
		child.appendChild(document.createTextNode(ctryAudRec.getAddToCart()));
		elem.appendChild(child);
		child = (Element) document.createElement("BUYABLE");
		child.appendChild(document.createTextNode(ctryAudRec.getBuyable()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBLISH");
		child.appendChild(document.createTextNode(ctryAudRec.getPublish()));
		elem.appendChild(child);
		child = (Element) document.createElement("CUSTOMIZEABLE");
		child.appendChild(document.createTextNode(ctryAudRec.getCustomizeable()));
		elem.appendChild(child);
		child = (Element) document.createElement("HIDE");
		child.appendChild(document.createTextNode(ctryAudRec.getHide()));
		elem.appendChild(child);
	}

	/********************
	 * get audience values from t1 and t2 for this model, do it once 
	 */
	private Vector[] getModelAudience(DiffEntity modelDiff, StringBuffer debugSb) {
		ABRUtil.append(debugSb,"XMLCATAElem.getModelAudience for " + modelDiff.getKey() + NEWLINE);

		EANFlagAttribute audienceAtt = (EANFlagAttribute) modelDiff.getCurrentEntityItem().getAttribute("AUDIEN");
		Vector currAudVct = new Vector(1);
		Vector prevAudVct = new Vector(1);
		Vector vct[] = new Vector[2];
		vct[0] = currAudVct;
		vct[1] = prevAudVct;
		ABRUtil.append(debugSb,"XMLCATAElem.getModelAudience cur audienceAtt " + audienceAtt + NEWLINE);
		if (audienceAtt != null) {
			MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					currAudVct.addElement(mfArray[i].toString()); // this is long desc
				}
			}
		}

		if (!modelDiff.isNew()) {
			audienceAtt = (EANFlagAttribute) modelDiff.getPriorEntityItem().getAttribute("AUDIEN");
			ABRUtil.append(debugSb,"XMLCATAElem.getModelAudience new audienceAtt " + audienceAtt + NEWLINE);
			if (audienceAtt != null) {
				MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						prevAudVct.addElement(mfArray[i].toString()); // this is long desc
					}
				}
			}
		}

		return vct;
	}

	/********************
	 * Create rows in the table as follows:
	 * Insert one row for each Audience in MODEL.AUDIEN & each Country in CATLGOR.OFFCOUNTRY where CATLGOR.CATAUDIENCE = MODEL.AUDIEN
	 * If the CATLGOR was deleted, set Action = Delete
	 * If the CATLGOR was added, set Action = Update
	 * If the CATLGOR was changed, put all current countryaudience pairs into currSet. put all prior countryaudience pairs into prevSet
	 * If currSet().values are not in the prevSet, set that row's Action = Update 
	 * If prevSet().values are not in the currSet, set that row's Action = Delete, Else to check other Elements.
	 * 
	 *
	 * Note:
	 * Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
	 */
	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity catlgorDiff, Vector mdlAudVct[], StringBuffer debugSb) {

		String attrCode = "CATAUDIENCE"; // need flag desc
//		String attrCode2 = "OFFCOUNTRY"; // need flag code
		String attrCode2 = BHCOUNTRYLIST; // need flag code
		Vector currAudVct = mdlAudVct[0];
		Vector prevAudVct = mdlAudVct[1];

		ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs " + catlgorDiff.getKey() + " currAudVct:" + currAudVct + " prevAudVct:"
			+ prevAudVct + NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = catlgorDiff.getCurrentEntityItem();
		EntityItem prioritem = catlgorDiff.getPriorEntityItem();

		if (catlgorDiff.isDeleted()) { // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String ctryVal = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected() && prevAudVct.contains(audience)) {
									String mapkey = ctryVal + "|" + audience;
									if (ctryAudElemMap.containsKey(mapkey)) {
										// dont overwrite
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted " + catlgorDiff.getKey() + " "
											+ mapkey + " already exists, keeping orig " + rec + NEWLINE);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, ctryVal, audience);
										ctryAudRec.setAction(DELETE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted:" + catlgorDiff.getKey()
											+ " rec: " + ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}

				}
			}
		} else if (catlgorDiff.isNew()) {
			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for new catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String ctryVal = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for new catlgor: audience=" + audience + NEWLINE);
								if (mfArray[i2].isSelected() && currAudVct.contains(audience)) {
									String mapkey = ctryVal + "|" + audience;
									if (ctryAudElemMap.containsKey(mapkey)) {
										// dont overwrite
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new " + catlgorDiff.getKey() + " " + mapkey
											+ " already exists, keeping orig " + rec + NEWLINE);
										rec.setUpdateCatlgor(catlgorDiff);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, ctryVal, audience);
										ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for new:" + catlgorDiff.getKey() + " rec: "
											+ ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}
				}

			}

		} else {
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
			//put all current countryAudience into currvSet.
			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for current catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String currctry = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected() && currAudVct.contains(audience)) {
									String mapkey = currctry + "|" + audience;
									currSet.add(mapkey);
								}
							}
						}

					}
				}
			}
			//put all prior countryAudience into prevSet
			ctryAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for prior catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String priorctry = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected() && prevAudVct.contains(audience)) {
									String mapkey = priorctry + "|" + audience;
									prevSet.add(mapkey);
								}
							}
						}
					}
				}
			}

			// look for changes in countryaudience pairs
			Iterator itr = currSet.iterator();
			while (itr.hasNext()) {
				String ctryaudiVal = (String) itr.next();
				if (!prevSet.contains(ctryaudiVal)) { // If a pair of CountryAudience was added, set that row's Action = Update
					//create crossproduct between new ctry and current audience for this item
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctryAudience on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, replacing orig " + rec + NEWLINE);
						rec.setUpdateCatlgor(catlgorDiff);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudRec.setAction(UPDATE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for added ctryAudience:" + catlgorDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}
				} else {
					// ctryaudience already existed but something else may have changed
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctryAudience on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, keeping orig " + rec + NEWLINE);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for existing ctryAudience:" + catlgorDiff.getKey()
							+ " rec: " + ctryAudRec.getKey() + NEWLINE);
					}

				}
			}
			itr = prevSet.iterator();
			while (itr.hasNext()) {
				String ctryaudiVal = (String) itr.next();
				if (!currSet.contains(ctryaudiVal)) { //If a pair of countryaudience was deleted, set that row's Action = Delete
					//create crossproduct between deleted ctry and previous audience for this item
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctryaudi on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, keeping orig " + rec + NEWLINE);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudRec.setAction(DELETE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted ctryAudience:" + catlgorDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}

				}
			}
		}

	}
	
	/********************
	 * This method is only for SWPRODSTRUCT. Because SWPRODSTRUCT don't have AUDIENCE attributes
	 * Create rows in the table as follows:
	 * Insert one row for each Country in CATLGOR.OFFCOUNTRY 
	 * If the CATLGOR was deleted, set Action = Delete
	 * If the CATLGOR was added, set Action = Update
	 * If the CATLGOR was changed, put all current countryaudience pairs into currSet. put all prior countryaudience pairs into prevSet
	 * If currSet().values are not in the prevSet, set that row's Action = Update 
	 * If prevSet().values are not in the currSet, set that row's Action = Delete, Else to check other Elements.
	 * 
	 *
	 * Note:
	 * Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
	 */
	private void buildSWCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity catlgorDiff, StringBuffer debugSb) {

		String attrCode = "CATAUDIENCE"; // need flag desc
		//String attrCode2 = "OFFCOUNTRY"; // need flag code
		String attrCode2 = BHCOUNTRYLIST; // need flag code


		ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs " + catlgorDiff.getKey() + NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = catlgorDiff.getCurrentEntityItem();
		EntityItem prioritem = catlgorDiff.getPriorEntityItem();

		if (catlgorDiff.isDeleted()) { // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String ctryVal = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected()) {
									String mapkey = ctryVal + "|" + audience;
									if (ctryAudElemMap.containsKey(mapkey)) {
										// dont overwrite
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted " + catlgorDiff.getKey() + " "
											+ mapkey + " already exists, keeping orig " + rec + NEWLINE);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, ctryVal, audience);
										ctryAudRec.setAction(DELETE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted:" + catlgorDiff.getKey()
											+ " rec: " + ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}

				}
			}
		} else if (catlgorDiff.isNew()) {
			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for new catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String ctryVal = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected()) {
									String mapkey = ctryVal + "|" + audience;
									if (ctryAudElemMap.containsKey(mapkey)) {
										// dont overwrite
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new " + catlgorDiff.getKey() + " " + mapkey
											+ " already exists, keeping orig " + rec + NEWLINE);
										rec.setUpdateCatlgor(catlgorDiff);
									} else {
										CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, ctryVal, audience);
										ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for new:" + catlgorDiff.getKey() + " rec: "
											+ ctryAudRec.getKey() + NEWLINE);
									}
								}
							}
						}
					}
				}

			}

		} else {
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
			//put all current countryAudience into currvSet.
			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for current catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String currctry = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected()) {
									String mapkey = currctry + "|" + audience;
									currSet.add(mapkey);
								}
							}
						}

					}
				}
			}
			//put all prior countryAudience into prevSet
			ctryAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
			ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for prior catlgor: ctryAtt "
				+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
			// Catlgor.OFFCOUNTRY is U flag value. there is should be one.
			if (ctryAtt != null) {
				MetaFlag[] ctryArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < ctryArray.length; im++) {
					// get selection
					if (ctryArray[im].isSelected()) {
						String priorctry = ctryArray[im].getFlagCode();
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.toString().length() > 0) {
							// Get the selected Flag codes.
							MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
							for (int i2 = 0; i2 < mfArray.length; i2++) {
								String audience = mfArray[i2].toString();
								if (mfArray[i2].isSelected()) {
									String mapkey = priorctry + "|" + audience;
									prevSet.add(mapkey);
								}
							}
						}
					}
				}
			}

			// look for changes in countryaudience pairs
			Iterator itr = currSet.iterator();
			while (itr.hasNext()) {
				String ctryaudiVal = (String) itr.next();
				if (!prevSet.contains(ctryaudiVal)) { // If a pair of CountryAudience was added, set that row's Action = Update
					//create crossproduct between new ctry and current audience for this item
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctryAudience on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, replacing orig " + rec + NEWLINE);
						rec.setUpdateCatlgor(catlgorDiff);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudRec.setAction(UPDATE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for added ctryAudience:" + catlgorDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}
				} else {
					// ctryaudience already existed but something else may have changed
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctryAudience on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, keeping orig " + rec + NEWLINE);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for existing ctryAudience:" + catlgorDiff.getKey()
							+ " rec: " + ctryAudRec.getKey() + NEWLINE);
					}

				}
			}
			itr = prevSet.iterator();
			while (itr.hasNext()) {
				String ctryaudiVal = (String) itr.next();
				if (!currSet.contains(ctryaudiVal)) { //If a pair of countryaudience was deleted, set that row's Action = Delete
					//create crossproduct between deleted ctry and previous audience for this item
					if (ctryAudElemMap.containsKey(ctryaudiVal)) {
						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryaudiVal);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctryaudi on " + catlgorDiff.getKey() + " "
							+ ctryaudiVal + " already exists, keeping orig " + rec + NEWLINE);
					} else {
						StringTokenizer stCtryAud = new StringTokenizer(ctryaudiVal, "|");
						String countryaudience[] = new String[2];
						countryaudience[0] = stCtryAud.nextToken();
						countryaudience[1] = stCtryAud.nextToken();
						CtryAudRecord ctryAudRec = new CtryAudRecord(catlgorDiff, countryaudience[0], countryaudience[1]);
						ctryAudRec.setAction(DELETE_ACTIVITY);
						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
						ABRUtil.append(debugSb,"XMLCATAElem.buildCtryAudRecs for deleted ctryAudience:" + catlgorDiff.getKey() + " rec: "
							+ ctryAudRec.getKey() + NEWLINE);
					}

				}
			}
		}

	}


	/*******************************
	 * one for every MODEL.AUDIEN crossproduct CATLGOR.OFFCOUNTRY
	 Several tokens have default values if there is not a value in CATLGOR. The defaults are:
	 -	<ADDTOCART>  = 'No'
	 -	<BUYABLE>  = 'No'
	 -	<PUBLISH> = 'No'
	 -	<CUSTOMIZEABLE> = 'Yes'
	 -	<HIDE> = 'No'
	    update the default value
	 */
	private static class CtryAudRecord {
		private String action = null;

		private String audience;

		private String country;

		private String pubfrom = CHEAT; //AVAIL/CATLGOR	PubFrom

		private String pubto = CHEAT; //AVAIL/CATLGOR	PubTo
		/**
		 * If the Attribute Value in CATLGOR is empty, do not generate a “default” value since a downstream system should use
			<DEFAULTADDTOCART>
			<DEFAULTBUYABLE>
			<DEFAULTCUSTOMIZEABLE>
			<DEFAULTHIDE>			
			If <PUBLISH> is not overridden, the downstream should always default the value to “Yes”
		 */

		private String addtocart = DEFAULT_NO; //CATLGOR	CATADDTOCART

		private String buyable = DEFAULT_NO; //CATLGOR	CATBUYABLE

		private String publish = DEFAULT_NO; //CATLGOR	CATPUBLISH

		private String customizeable = DEFAULT_YES; //CATLGOR	CATCUSTIMIZE

		private String hide = DEFAULT_NO; //CATLGOR	CATHIDE
		
		private DiffEntity catlgorDiff;

		boolean isDisplayable() {
			return action != null;
		} // only display those with filled in actions

		CtryAudRecord(DiffEntity catDiff, String ctryVal, String aud) {
			audience = aud;
			catlgorDiff = catDiff;
			country = ctryVal;
		}

		void setAction(String s) {
			action = s;
		}

		void setUpdateCatlgor(DiffEntity avl) {
			catlgorDiff = avl;// allow replacement
			setAction(UPDATE_ACTIVITY);
		}

		/*********************
		 * <AUDIENCE>	</AUDIENCE>	4			MODEL	AUDIEN
		 * <COUNTRY_FC>	</COUNTRY_FC>	4			CATLGOR	COUNTRYLIST
		 * <PUBFROM>	</PUBFROM>	4				
		 * <PUBTO>	</PUBTO>	4				
		 * <ADDTOCART>	</ADDTOCART>	4			CATLGOR	CATADDTOCART
		 * <BUYABLE>	</BUYABLE>	4			CATLGOR	CATBUYABLE
		 * <PUBLISH>	</PUBLISH>	4			CATLGOR	CATPUBLISH
		 * <CUSTOMIZEABLE>	</CUSTOMIZEABLE>	4			CATLGOR	CATCUSTIMIZE
		 * <HIDE>	</HIDE>	4			CATLGOR	CATHIDE
		 */
		void setAllFields(StringBuffer debugSb) {

			if (catlgorDiff != null) {
				//If any value was changed, then set <CATALOGOVERRIDEACTION> to 'Update' if it is not set to 'Delete'.
				ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + catlgorDiff.getKey() + NEWLINE);
				EntityItem curritem = catlgorDiff.getCurrentEntityItem();
				EntityItem previtem = catlgorDiff.getPriorEntityItem();
				//set pubfrom
				if (curritem != null) {
					pubfrom = PokUtils.getAttributeValue(curritem, "PUBFROM", "", CHEAT, false);
				}
				String prevpubfrom = CHEAT;
				if (previtem != null) {
					prevpubfrom = PokUtils.getAttributeValue(previtem, "PUBFROM", ", ", CHEAT, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields pubfrom: " + pubfrom + " prevdate: " + prevpubfrom + NEWLINE);

				// if diff, set action
				if (!prevpubfrom.equals(pubfrom)) {
					setAction(UPDATE_ACTIVITY);
				}
				// set pubto
				if (curritem != null) {
					pubto = PokUtils.getAttributeValue(curritem, "PUBTO", "", CHEAT, false);
				}
				String prevpubto = CHEAT;
				if (previtem != null) {
					prevpubto = PokUtils.getAttributeValue(previtem, "PUBTO", ", ", CHEAT, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields pubto: " + pubto + " prevdate: " + prevpubto + NEWLINE);

				// if diff, set action
				if (!prevpubto.equals(pubto)) {
					setAction(UPDATE_ACTIVITY);
				}
				// set addtocart
				if (curritem != null) {
					addtocart = PokUtils.getAttributeValue(curritem, "CATADDTOCART", ", ", DEFAULT_NO, false);
				}
				String prevaddtocart = DEFAULT_NO;
				if (previtem != null) {
					prevaddtocart = PokUtils.getAttributeValue(previtem, "CATADDTOCART", ", ", DEFAULT_NO, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields addtocart: " + addtocart + " prevaddtocart: " + prevaddtocart
					+ NEWLINE);

				// if diff, set action
				if (!prevaddtocart.equals(addtocart)) {
					setAction(UPDATE_ACTIVITY);
				}
				// set CATBUYABLE
				if (curritem != null) {
					buyable = PokUtils.getAttributeValue(curritem, "CATBUYABLE", ", ", DEFAULT_NO, false);
				}
				String prevbuyable = DEFAULT_NO;
				if (previtem != null) {
					prevbuyable = PokUtils.getAttributeValue(previtem, "CATBUYABLE", ", ", DEFAULT_NO, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields buyable: " + buyable + " prevbuyable: " + prevbuyable + NEWLINE);

				// if diff, set action
				if (!prevbuyable.equals(buyable)) {
					setAction(UPDATE_ACTIVITY);
				}
				//set CATPUBLISH
				if (curritem != null) {
					publish = PokUtils.getAttributeValue(curritem, "CATPUBLISH", ", ", DEFAULT_NO, false);
				}
				String prevpublish = DEFAULT_NO;
				if (previtem != null) {
					prevpublish = PokUtils.getAttributeValue(previtem, "CATPUBLISH", ", ", DEFAULT_NO, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields publish: " + publish + " prevpublish: " + prevpublish + NEWLINE);

				// if diff, set action
				if (!prevpublish.equals(publish)) {
					setAction(UPDATE_ACTIVITY);
				}
				//set customizeable
				if (curritem != null) {
					customizeable = PokUtils.getAttributeValue(curritem, "CATCUSTIMIZE", ", ", DEFAULT_YES, false);
				}
				String prevcustomizeable = DEFAULT_YES;
				if (previtem != null) {
					prevcustomizeable = PokUtils.getAttributeValue(previtem, "CATCUSTIMIZE", ", ", DEFAULT_YES, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields customizeable: " + customizeable + " prevcustomizeable: "
					+ prevcustomizeable + NEWLINE);

				// if diff, set action
				if (!prevcustomizeable.equals(customizeable)) {
					setAction(UPDATE_ACTIVITY);
				}
				//set hide  CATHIDE
				if (curritem != null) {
					hide = PokUtils.getAttributeValue(curritem, "CATHIDE", ", ", DEFAULT_NO, false);
				}
				String prevhide = DEFAULT_NO;
				if (previtem != null) {
					prevhide = PokUtils.getAttributeValue(previtem, "CATHIDE", ", ", DEFAULT_NO, false);
				}
				ABRUtil.append(debugSb,"XMLCATAElem.setAllFields hide: " + hide + " prevhide: " + prevhide + NEWLINE);

				// if diff, set action
				if (!prevhide.equals(hide)) {
					setAction(UPDATE_ACTIVITY);
				}
			}
		}

		String getAction() {
			return action;
		}

		String getAudience() {
			return audience;
		}

		String getCountry() {
			return country;
		}

		String getPubFrom() {
			return pubfrom;
		}

		String getPubTo() {
			return pubto;
		}

		String getAddToCart() {
			return addtocart;
		}

		String getBuyable() {
			return buyable;
		}

		String getPublish() {
			return publish;
		}

		String getCustomizeable() {
			return customizeable;
		}

		String getHide() {
			return hide;
		}

		boolean isDeleted() {
			return DELETE_ACTIVITY.equals(action);
		}

		String getKey() {
			return country + "|" + audience;
		}

		void dereference() {
			action = null;
			audience = null;
			country = null;
			pubfrom = null;
			pubto = null;
			addtocart = null;
			buyable = null;
			publish = null;
			customizeable = null;
			hide = null;
		}

		public String toString() {
			return catlgorDiff.getKey() + " " + getKey() + " action:" + action;
		}
	}
}
