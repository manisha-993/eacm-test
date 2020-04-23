package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;
import java.util.*;
import org.w3c.dom.*;

public class XMLANNElem extends XMLElem {

	/***************************************************************************
	 * announce_type == ANNOUNCE
	 * ANNDATE = MIN(MODELAVAIL-d: AVAILANNA: ANNDATE) WHERE AVAILTYPE = (Planned Availability) and ANNTYPE = 19 (New)
	 * <ANNOUNCEDATE> </ANNOUNCEDATE> ANNDATE <ANNOUNCENUMBER>
	 * </ANNOUNCENUMBER>ANNNUMBER <WITHDRAWANNOUNCEDATE>
	 * 
	 * announce_type == WITHDRAWANNOUNCE
	 * ANNDATE = MAX(MODELAVAIL-d: AVAILANNA: ANNDATE) WHERE AVAILTYPE = 149 (Last Order) and ANNTYPE = 14 (End Of Life - Withdrawal from mktg)
	 * </WITHDRAWANNOUNCEDATE>ANNDATE <WITHDRAWANNOUNCENUMBER>
	 * </WITHDRAWANNOUNCENUMBER> ANNNUMBER
	 * 
	 */

	private static final String AVAIL_PA = "146"; //Planed Availbility

	private static final String AVAIL_LO = "149"; // last order

	private static final String ANNTYPE_NEW = "19"; // New

	private static final String ANNTYPE_EndOfLife = "14"; // End Of Life 

	public XMLANNElem() {
		super(null);
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
	 * @throws
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		String anndate = CHEAT;
		String annnumber = CHEAT;
		String withdrawanndate = CHEAT;
		String withdrawannnumber = CHEAT;
		boolean isfromModel = isDerivefromModel(table, parentItem, debugSb);
		if (isfromModel == true) {
			if (parentItem != null) {
				EntityItem curritem = parentItem.getCurrentEntityItem();
				if (curritem != null){
					anndate = PokUtils.getAttributeValue(curritem, "ANNDATE", ", ", CHEAT, false);
					withdrawanndate = PokUtils.getAttributeValue(curritem, "WTHDRWEFFCTVDATE", ", ", CHEAT, false);
					ABRUtil.append(debugSb,"XMLANNElem.addElements" + curritem.getKey() + " thedate: " + anndate + " withdrawanndate: "
						+ withdrawanndate + NEWLINE);			
				}				
			}
		} else {
			Vector annVct = getAnnouncement(table, AVAIL_PA, ANNTYPE_NEW, debugSb);
			if (annVct != null) {

				if (annVct.size() > 0) { // ANNOUNCEMENT of TYPE='NEW' WITH EARLIEST
					// ANNOUNCEMENT. ANNDATE
					// set announcedate and annnumber
					for (int i = 0; i < annVct.size(); i++) {

						EntityItem item = (EntityItem) annVct.elementAt(i);
						if (item != null) {
							String thedate = PokUtils.getAttributeValue(item, "ANNDATE", ", ", CHEAT, false);
							String thenumber = PokUtils.getAttributeValue(item, "ANNNUMBER", ", ", CHEAT, false);
							ABRUtil.append(debugSb,"XMLANNElem.addElements checking[" + i + "]:" + item.getKey() + " thedate: " + thedate
								+ " thenumber: " + thenumber + NEWLINE);

							if (!CHEAT.equals(thedate)) {
								// initial the date
								if (anndate.equals(CHEAT)) {

									anndate = thedate;
									annnumber = thenumber;
								} else {
									// find the minimum anndate.
									if (thedate.compareTo(anndate) < 0) {
										anndate = thedate;
										annnumber = thenumber;
									}
								}
							}

						}
					}
					ABRUtil.append(debugSb,"XMLANNElem.addElements new anndate: " + anndate + " annnumber: " + annnumber + NEWLINE);

				}
			} else {
				ABRUtil.append(debugSb,"XMLANNElem.addElements no NEW ANNOUNCEMENT(ANNTYPE =19) found." + " newAnnVct.size:"
					+ (annVct == null ? "null" : "" + annVct.size()) + NEWLINE);
			}

			Vector withdrewannVct = getAnnouncement(table, AVAIL_LO, ANNTYPE_EndOfLife, debugSb);
			if (withdrewannVct != null) {
				if (withdrewannVct.size() > 0) {

					// ANNOUNCEMENT of TYPE='END' WITH MAX
					// ANNOUNCEMENT. ANNDATE

					// set announcedate and annnumber
					for (int i = 0; i < withdrewannVct.size(); i++) {

						EntityItem item = (EntityItem) withdrewannVct.elementAt(i);
						if (item != null) {
							String thedate = PokUtils.getAttributeValue(item, "ANNDATE", ", ", CHEAT, false);
							String thenumber = PokUtils.getAttributeValue(item, "ANNNUMBER", ", ", CHEAT, false);
							ABRUtil.append(debugSb,"XMLANNElem.addElements latest checking[" + i + "]:" + item.getKey() + " thedate: "
								+ thedate + " thenumber: " + thenumber + NEWLINE);
							if (!CHEAT.equals(thedate)) {
								// initial the date
								if (withdrawanndate.equals(CHEAT)) {
									withdrawanndate = thedate;
									withdrawannnumber = thenumber;
								} else {
									// find the maximum anndate.
									if (thedate.compareTo(withdrawanndate) > 0) {
										withdrawanndate = thedate;
										withdrawannnumber = thenumber;
									}
								}
							}
							ABRUtil.append(debugSb,"XMLANNElem.addElements latest anndate: " + withdrawanndate + " annnumber: "
								+ withdrawannnumber + NEWLINE);
						}
					}
				}
			} else {
				ABRUtil.append(debugSb,"XMLANNElem.addElements no end ANNOUNCEMENT(ANNTYPE =14) found." + " AnnVct.size:"
					+ (withdrewannVct == null ? "null" : "" + withdrewannVct.size()) + NEWLINE);
			}

		}
		createNodeSet(document, parent, anndate, annnumber, withdrawanndate, withdrawannnumber, debugSb);
	}

	private void createNodeSet(Document document, Element parent, String anndate, String annnumber, String withdrawanndate,
		String withdrawannnumber, StringBuffer debugSb) {
		Element child = (Element) document.createElement("ANNOUNCEDATE"); // create COUNTRYAUDIENCEELEMENT
		child.appendChild(document.createTextNode("" + anndate));
		parent.appendChild(child);
		child = (Element) document.createElement("ANNOUNCENUMBER");
		child.appendChild(document.createTextNode("" + annnumber));
		parent.appendChild(child);
		child = (Element) document.createElement("WITHDRAWANNOUNCEDATE");
		child.appendChild(document.createTextNode("" + withdrawanndate));
		parent.appendChild(child);
		child = (Element) document.createElement("WITHDRAWANNOUNCENUMBER");
		child.appendChild(document.createTextNode("" + withdrawannnumber));
		parent.appendChild(child);

	}

	/********************
	 *  return ANNOUNCEMENT = AVAIL-d: AVAILANNA: ANNCEMENT) WHERE AVAILTYPE = availtype 
	 *         and ANNTYPE = anntype. 
	 *  @param table  
	 *         Hashtable that contain all the entities.
	 *  @param availtype 
	 *         AVAIL.AVAILTYPE 
	 *  @param anntype
	 *         ANNOUCEMENT.ANNTYPE 
	 *  @param debugSb
	 *         StringBuffer logo output.
	 ***********/
	private Vector getAnnouncement(Hashtable table, String availtype, String anntype, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("AVAIL");

		ABRUtil.append(debugSb,"getANNOUNCEMENT.getAvails looking for AVAILTYPE: " + availtype + "in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}

		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			if (!diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT.getAvails checking[" + i + "]:New or Update" + diffitem.getKey()
					+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected(availtype)) {
					Vector relatorVec = curritem.getDownLink();

					ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT looking for downlink of AVAIL" + " annVct.size: "
						+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
						+ curritem.getDownLinkCount() + NEWLINE);
					for (int ii = 0; ii < relatorVec.size(); ii++) {
						EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);

						ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT looking for downlink of AVAIL " + availanna.getKey()
							+ "entitytype is: " + availanna.getEntityType() + NEWLINE);

						if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
							// get get Announcement. it could return multiple results. Either check that you have the right one.
							Vector annVct = availanna.getDownLink();
							for (int iii = 0; iii < annVct.size(); iii++) {
								EntityItem anna = (EntityItem) annVct.elementAt(iii);
								ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT looking for downlink of AVAILANNA " + anna.getKey()
									+ "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
									+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
								EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
								if (fANNAtt != null && fANNAtt.isSelected(anntype)) {
									avlVct.add(anna);
								} else {
									ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT ANNTYPE: "
										+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal " + anntype + NEWLINE);
								}
							}
						} else {
							ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT no downlink of AVAILANNA was found" + NEWLINE);
						}

					}

				}
			}
		}
		ABRUtil.append(debugSb,"XMLANNElem.getANNOUNCEMENT return: " + " avlVct.size:" + (avlVct == null ? "null" : "" + avlVct.size())
			+ NEWLINE);
		return avlVct;
	}

	/********************
	 *  return Boolean check If there isn’t an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE less than "20100301"   
	 *  @param table  
	 *         Hashtable that contain all the entities.
	 *  @param availtype 
	 *         AVAIL.AVAILTYPE 
	 *  @param debugSb
	 *         StringBuffer logo output.
	 *         
	 *  If there isn’t an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE < “20100301” then
	 *  from MODEL attributes
	 *  Note:
	 *  Condition1 is there isn't an Planned Avail.  
	 *  Condition2 is Model.ANNDATE < “20100301” - will be removed based on BH FS ABR XML System Feed Mapping 20120612.doc
	 **/
	private boolean isDerivefromModel(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		boolean condition1 = true;
//		boolean condition2 = false;
		if (parentItem != null) {
			if (parentItem.getEntityType().equals("MODEL") || parentItem.getEntityType().equals("SVCMOD")) {
				String ANNDATE_Fix = "20100301";
				Vector allVct = (Vector) table.get("AVAIL");
				ABRUtil.append(debugSb,"DerivefromModel.getAvails looking for AVAILTYPE: " + AVAIL_PA + "in AVAIL" + " allVct.size:"
					+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
				if (allVct != null) {
					// find those of specified type
					for (int i = 0; i < allVct.size(); i++) {
						DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
						EntityItem curritem = diffitem.getCurrentEntityItem();
						if (!diffitem.isDeleted()) {
							ABRUtil.append(debugSb,"XMLANNElem.DerivefromModel.getAvails checking[" + i + "]:New or Update"
								+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")
								+ NEWLINE);
							EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
							if (fAtt != null && fAtt.isSelected(AVAIL_PA)) {
								condition1 = false;
								break;
							}
						}
					}
				}
//				removed based on BH FS ABR XML System Feed Mapping 20120612.doc
//				EntityItem curritem = parentItem.getCurrentEntityItem();
//				if (curritem != null) {
//					String anndate = PokUtils.getAttributeValue(curritem, "ANNDATE", ", ", CHEAT, false);
//					condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
//					ABRUtil.append(debugSb,"XMLANNElem.DerivefromModel.get model ANNDATE" + curritem.getKey() + " ANNDATE: " + anndate
//						+ NEWLINE);
//				}
			}

		}
		return condition1 ;
//		&& condition2;
	}
}
