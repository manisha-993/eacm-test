package COM.ibm.eannounce.abr.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;

public class XMLMAINTMFAVAILElem extends XMLElem {

	public XMLMAINTMFAVAILElem() {
		super("AVAILABILITYELEMENT");
	}
	
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent,
			DiffEntity parentItem, StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException, java.rmi.RemoteException, IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1:parentItem: " + parentItem.getKey() + NEWLINE);
		
		boolean compatModel = false;
	    boolean isExistfinal = false;
	    String m_strEpoch = "1980-01-01-00.00.00.000000";
	    boolean isDelta = true;
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
        
        EntityItem priorItem = parentItem.getPriorEntityItem();
	    //ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements priorItem is " + (priorItem == null?" is null":"not null")+ " profileT1 valOn: " + priorItem.getProfile().getValOn()+ NEWLINE);
		if (priorItem != null && m_strEpoch.equals(priorItem.getProfile().getValOn())){
			isDelta = false;
		}
		Vector plnAvlVct = getPlanAvails(table,debugSb);
		
		if (plnAvlVct.size() > 0) {
			TreeMap ctryAudElemMap = new TreeMap();
			for (int i = 0; i < plnAvlVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
				//if is Delta XML, then build Delete Countrylist.
				if(isDelta)	buildCtryAudRecs(ctryAudElemMap, availDiff, true, debugSb);
				//Begin build T2 country records change false to true 
				else buildCtryAudRecs(ctryAudElemMap, availDiff, false, debugSb);
			}
			
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			
			while (itr.hasNext()) {
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
				
				//DiffEntity catlgorDiff = getCatlgor(table, mdlAudVct, ctryAudRec.getCountry(), debugSb);
				// add other info now
				ctryAudRec.setAllFields(parentItem, table, isExistfinal,  compatModel, debugSb);
			
				if (ctryAudRec.isDisplayable() || ctryAudRec.isrfrDisplayable()) {
					createNodeSet(table, document, parent, parentItem, ctryAudRec, debugSb);
					
				} else {
					ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1.addElements no changes found for " + ctryAudRec + NEWLINE);
				}
				ctryAudRec.dereference();
			}
		}else {
			ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.addElements no planned AVAILs found" + NEWLINE);
		}
	}
	
	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, boolean T1, StringBuffer debugSb) {
		ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.buildCtryAudRecs build T1 country list " + T1 + " " + availDiff.getKey()+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (T1){
			if (!availDiff.isNew()) { // If the AVAIL was deleted, set Action = Delete
				// mark all records as delete
				EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,
						"XMLMAINTMFAVAILElembh1.buildCtryAudRecs for deleted / update avail at T1: ctryAtt "
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
								ABRUtil.append(debugSb,
										"WARNING buildCtryAudRecs for deleted / update " + availDiff.getKey() + " "
												+ mapkey + " already exists, keeping orig " + rec + NEWLINE);
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
						"XMLMAINTMFAVAILElembh1.buildCtryAudRecs for new /update avail:  ctryAtt "
								+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							String mapkey = ctryVal;
							if (ctryAudElemMap.containsKey(mapkey)) {
								CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
								if (DELETE_ACTIVITY.equals(rec.action)) {
									ABRUtil.append(debugSb,
											"WARNING buildCtryAudRecs for new /udpate" + availDiff.getKey() + " "
													+ mapkey + " already exists, replacing orig " + rec + NEWLINE);
									rec.setUpdateAvail(availDiff);
									rec.setAction(CHEAT);
								}
							} else {
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
								ABRUtil.append(debugSb, "XMLMAINTMFAVAILElembh1.buildCtryAudRecs for new:"
										+ availDiff.getKey() + " rec: " + ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
				}
			}
		}
	}
	
	private Vector getPlanAvails(Hashtable table,StringBuffer debugSb){
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("AVAIL");
		ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}
		
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			if (diffitem.isDeleted()) {
				EntityItem priorItem = diffitem.getPriorEntityItem();
				
				ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey()
					+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(priorItem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) priorItem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			} else {
				EntityItem currItem = diffitem.getCurrentEntityItem();
				ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.getPlannedAvails checking[" + i + "]:" + diffitem.getKey()
					+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(currItem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) currItem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			}
		}
		return avlVct;
	}
	
	private void createNodeSet(Hashtable table, Document document, Element parent, DiffEntity parentDiffEntity,
			CtryAudRecord ctryAudRec, StringBuffer debugSb) {

		if (ctryAudRec.isDisplayable()) {

			Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);

			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAction()));
			elem.appendChild(child);

			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPlannedavailability()));
			elem.appendChild(child);
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

			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);

			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrplannedavailability()));
			elem.appendChild(child);
		}
	}
	
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
		
		
		void setAllFields(DiffEntity parentDiffEntity, Hashtable table, boolean isExistfinal, boolean compatModel, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + availDiff.getKey() + " " + getKey() + NEWLINE);
			
			//new added 0904b
			availStatus = "0020";
            rfravailStatus = "0040";
		
            // PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
            String[] plannedavailabilitys = derivePlannedavailability(parentDiffEntity, false, debugSb);
            String[] plannedavailabilitysT1 = derivePlannedavailability(parentDiffEntity, true, debugSb);
            
            handleResults(plannedavailabilitys, plannedavailabilitysT1, country,  isExistfinal,  compatModel, debugSb);
		}
		
		boolean handleResults(String[] plannedavailabilitys, String[] plannedavailabilitysT1, String country,
				boolean isExistfinal, boolean isCompatmodel, StringBuffer debugSb) {
			// TODO Compare T1 and T2 set action and rfraction
			
			String plannedavailabilityT1 = CHEAT;
			String rfrplannedavailabilityT1 = CHEAT;
						
			plannedavailability = plannedavailabilitys[0];
			rfrplannedavailability = plannedavailabilitys[1];
			plannedavailabilityT1 = plannedavailabilitysT1[0];
			rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
			
			if (DELETE_ACTIVITY.equals(action)) {
				ABRUtil.append(debugSb, "setallfileds: coutry is delete:" + country);			
				rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
				
				if (existfinalT1) {
					ABRUtil.append(debugSb, "setallfileds: coutry is exist final T1:" + country + NEWLINE);
					setAction(DELETE_ACTIVITY);
					setrfrAction(DELETE_ACTIVITY);
					setAllfieldsEmpty();

				} else {
					ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T1:" + country + NEWLINE);
					setAction(CHEAT);
					setrfrAction(DELETE_ACTIVITY);
					setAllfieldsEmpty();

				}

			} else if (UPDATE_ACTIVITY.equals(action)) {
				ABRUtil.append(debugSb, "setallfileds: coutry is new:" + country + NEWLINE);				
				rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
				
				if (existfinalT2) {
					ABRUtil.append(debugSb, "setallfileds: coutry is  exist final T2:" + country + NEWLINE);
					setAction(UPDATE_ACTIVITY);
					setrfrAction(UPDATE_ACTIVITY);

				} else {
					ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T2:" + country + NEWLINE);
					setAction(CHEAT);
					setrfrAction(UPDATE_ACTIVITY);

				}

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);				
				rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);								
				rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
				
				if (existfinalT1 && !existfinalT2) {
					ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
					setAction(DELETE_ACTIVITY);
					setfinalAllfieldsEmpty();

				} else if (existfinalT2 && !existfinalT1) {
					ABRUtil.append(debugSb, "setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
					setAction(UPDATE_ACTIVITY);
					setrfrAction(UPDATE_ACTIVITY);

				} else if (existfinalT2 && existfinalT1) {
					ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
					compareT1vT2(plannedavailability, plannedavailabilityT1, false);
					ABRUtil.append(debugSb, "setallfileds: after compare action :" + action + NEWLINE);
				} else {
					// not existfinalT1 && not existfinalT2
					ABRUtil.append(debugSb, "setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
					setAction(CHEAT);
				}
				compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
				ABRUtil.append(debugSb, "setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
			}

			if (!isCompatmodel) {
				if (isExistfinal) {
					setrfrAction(CHEAT);
				} else {
					setAction(CHEAT);
				}
			}

			return existfinalT2;
		}
		
		private String[] derivePlannedavailability(DiffEntity parentDiffEntity, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLMAINTMFAVAILElembh1.derivePlannedavailability availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			//get it from AVAIL.EFFECTIVEDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
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

