package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.transform.oim.eacm.util.PokUtils;

public class RFCPRODSTRUCTABR extends RfcAbrAdapter {
	
private static List<String> tmfMarkChangedAttrs;
private static List<String> feaMarkChangedAttrs;
	
	static {
		tmfMarkChangedAttrs = new ArrayList<>();
		tmfMarkChangedAttrs.add(TMF_INSTALL);
		feaMarkChangedAttrs = new ArrayList<>();
		feaMarkChangedAttrs.add(FEATURE_INVNAME);
	}

	public RFCPRODSTRUCTABR(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}

	@Override
	public void processThis() throws RfcAbrException, Exception {
		abr.addDebug("Start processThis()");
		// ----------------------- Get values from EACM entities -----------------------
		// TMF
		EntityItem tmfItem = getRooEntityItem();
		// MODEL
		EntityItem[] mdlItems = getEntityItems(MODEL);
		if (mdlItems == null || mdlItems.length == 0) {
			throw new RfcAbrException("Can not find the linked MODEL for " + tmfItem.getKey());
		}
		EntityItem mdlItem = mdlItems[0];
		// FEATURE
		EntityItem[] feaItems = getEntityItems(FEATURE);
		if (feaItems == null || feaItems.length == 0) {
			throw new RfcAbrException("Can not find the linked FEATURE for " + tmfItem.getKey());
		}
		EntityItem feaItem = feaItems[0];
		// AVAIL
		Vector availVct = PokUtils.getAllLinkedEntities(tmfItem, "OOFAVAIL", AVAIL);
		Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
		abr.addDebug("getAvails OOFAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size());
		if (planAvailVct.size() == 0) {
			throw new RfcAbrException("There is no avail for this PRODSTRUCT, it will not promote this PRODSTRUCT");
		}
		// ANNOUNCEMENT
		EntityItem[] annItems = getEntityItems(ANNOUNCEMENT);
		if (annItems == null || annItems.length == 0) {
			throw new RfcAbrException("There is no ANNOUNCEMENT for this PRODSTRUCT, it will not promote this PRODSTRUCT");
		}	
		// XCC=X， else C,　we don't have XCC so always C now
		String pimsIdentity = "C";
		abr.addDebug("PimsIdentity:" + pimsIdentity);
		
		boolean isRPQ = isRPQ(feaItem);
		
		String machType = getAttributeValue(mdlItem, MODEL_MACHTYPEATR);
		String model = getAttributeValue(mdlItem, MODEL_MODELATR);
		String feaCode = getAttributeValue(feaItem, FEATURE_FEATURECODE);
		
		// ----------------------- Create RFC input entities -----------------------
		// Type Feature
		TypeFeature tfObj = new TypeFeature();
		if (isRPQ) {
			tfObj.setFeatureID("Q"); // RPQ = Q
		} else {
			tfObj.setFeatureID("R");
		}		
		tfObj.setFeature(feaCode);
		tfObj.setType(machType);
		tfObj.setDescription(getAttributeValue(feaItem, FEATURE_INVNAME));
		tfObj.setNoChargePurchase(getNoChargePurchase(feaItem));
		tfObj.setNetPriceMES(getNetPriceMES(mdlItem));
		tfObj.setItemReturned(getItemReturn(tmfItem));
		tfObj.setRemovalCharge(getRemovalCharge(tmfItem));
		tfObj.setCustomerSetup("CE".equals(getAttributeValue(tmfItem, TMF_INSTALL)));
		tfObj.setCapOnDemand("202".equals(getAttributeFlagValue(feaItem, FEATURE_HWFCCAT)));
		tfObj.setApprovalRPQ(isRPQ);
		tfObj.calculateRange100(); //?
		tfObj.setUFLinked(false);// from Rupal UFLink not required for EACM.
		abr.addDebug("TypeFeature: " + tfObj.toString());
				
		boolean isTFPromoted = false;
		boolean isTFChanged = true;
		boolean isTFGeoPromoted = false;
		boolean isTFGeoChanged = true;
		
		EntityList t1EntityList = null;		
		AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(tmfItem, RFCABRSTATUS);
		boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
		abr.addDebug("Exist passed RFCABRSTATUS before: " + isPassedAbrExist);
		if (isPassedAbrExist) {
			isTFPromoted = true;
			String t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
			if (t1DTS == null) {
				abr.addDebug("t1DTS is null");
			} else {
				String t2DTS = abr.getCurrentTime();
				Profile profileT1 = abr.getProfile().getNewInstance(abr.getDatabase());
	            profileT1.setValOnEffOn(t1DTS, t1DTS);
	            profileT1.setEndOfDay(t2DTS); 
	            profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
	            profileT1.setLoginTime(t2DTS);
	            abr.addDebug("Get t1 entity list for t1DTS:" + t1DTS + " t2DTS:" + t2DTS);
	            t1EntityList = getEntityList(profileT1);
	            // TMF at T1
	            EntityItem t1TmfItem = t1EntityList.getParentEntityGroup().getEntityItem(0);
	            // MODEL at T1
	            EntityItem[] t1MdlItems = getEntityItems(t1EntityList, MODEL);
	    		if (t1MdlItems == null || t1MdlItems.length == 0) {
	    			throw new RfcAbrException("Can not find the linked MODEL for T1" + tmfItem.getKey());
	    		}
	    		EntityItem t1MdlItem = t1MdlItems[0];
	            // FEATURE at T1
	            EntityItem[] t1FeaItems = getEntityItems(t1EntityList, FEATURE);
	    		if (t1FeaItems == null || t1FeaItems.length == 0) {
	    			throw new RfcAbrException("Can not find the linked FEATURE for T1 " + tmfItem.getKey());
	    		}
	    		EntityItem t1FeaItem = t1FeaItems[0];
	    		
	            isTFChanged = isTypeFeatureChanged(t1TmfItem, tmfItem, tmfMarkChangedAttrs);
	            if (!isTFChanged) {
	            	isTFChanged = isTypeFeatureChanged(t1FeaItem, feaItem, tmfMarkChangedAttrs);
	            	if (!isTFChanged) {
	            		boolean t1NoPriceMES = getNetPriceMES(t1MdlItem);
	    				boolean t2NoPriceMES = getNetPriceMES(mdlItem);
	    				if (t1NoPriceMES != t2NoPriceMES) {
	    					isTFChanged = true;
	    				}
	            	}
	            }
			}
		}
		
//		Vector<String> ranges = null;
		String thisRange = getAttributeValue(feaItem, FEATURE_FEATURECODE);
		
		for (int i = 0; i < planAvailVct.size(); i++) {
			EntityItem availItem = (EntityItem)planAvailVct.get(i);
			Vector annVect = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", ANNOUNCEMENT);
			if (annVect.size() > 0) {
				EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
				abr.addDebug("Promote Type Feature for " + annItem.getKey() + " " + availItem.getKey());
				
				// Get all salesorg and plants from GENERALAREA linked to AVAIL
				Vector generalareaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", GENERALAREA);
				List<SalesOrgPlants> salesorgPlantsVect = getAllSalesOrgPlant(generalareaVct);
				abr.addDebug("GENERALAREA size: " + generalareaVct.size() + " SalesorgPlants size: " + salesorgPlantsVect.size());
				Set<String> plants = getAllPlant(salesorgPlantsVect);
				abr.addDebug("All plants size: " + plants.size() + " values: " + plants);
				
				if (isTFPromoted) {
					if (t1EntityList != null) {
						EntityItem[] t1Avails = getEntityItems(t1EntityList, AVAIL);							
						isTFGeoPromoted = isTypeFeatureGeoPromoted(t1Avails, availItem);
						if (isTFGeoPromoted) {
							EntityItem[] t1Anns = getEntityItems(t1EntityList, "ANNOUNCEMENT");
							EntityItem t1AnnItem = getEntityItemAtT1(t1Anns, annItem);
							if (t1AnnItem != null) {
								isTFGeoChanged = isTypeFeatureGeoChanged(t1AnnItem, annItem);
							}							
						} 
					}		
				}
				
				// ----------------------- Create RFC input entitie CHWAnnouncement and CHWGeoAnn -----------------------
				CHWAnnouncement chwA = new CHWAnnouncement();
				chwA.setAnnDocNo(machType + STRING_SEPARATOR + model + STRING_SEPARATOR + feaCode); // MACHTYPEATR|MODELATR|FEATURECODE
				chwA.setAnnouncementType(getAttributeValue(annItem, ANNOUNCEMENT_ANNTYPE)); //  WS logic:if feed is designated as "ePIMS/SW Migration", then set to "MIG" else set to "RFA", so flag or desc are all fine.
				chwA.setSegmentAcronym(getSegmentAcronymForAnn(annItem));
				abr.addDebug("CHWAnnouncement: " + chwA.toString());
				
				CHWGeoAnn chwAg = new CHWGeoAnn();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, ANNOUNCEMENT_ANNDATE)));
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
				
				// ----------------------- Type Feature Promote -----------------------
				abr.addDebug("Start Type Feature Promote Logic");
				if (!isTFPromoted) {
					abr.addDebug("----------------------- Start Type Feature not promoted -----------------------");
					if ("Q".equals(tfObj.getFeatureID())) {
						rdhRestProxy.r126(tfObj, chwA, pimsIdentity);
						rdhRestProxy.r127(tfObj, thisRange, chwA, pimsIdentity);
						//inseret Type RPQ row in Promoted_Typeclass
						// epims: ptypClassCol
						// epims: nowPromotedTypeClasses						
						rdhRestProxy.r128(tfObj, thisRange, chwA, pimsIdentity);
						rdhRestProxy.r152(tfObj, chwA, pimsIdentity);
					} else { //if FeatureID != "Q"
						 //if FeatureID != "Q"
						rdhRestProxy.r129(tfObj, chwA, pimsIdentity);
						
						if (tfObj.ifAlphaNumeric()) {
							rdhRestProxy.r130(tfObj.getType(), thisRange, chwA, pimsIdentity);
							rdhRestProxy.r176(tfObj.getType(), thisRange, "NEW", chwA, pimsIdentity);
							rdhRestProxy.r176(tfObj.getType(), thisRange, "UPG", chwA, pimsIdentity);
						}				
						rdhRestProxy.r134(tfObj, chwA, pimsIdentity);
						rdhRestProxy.r153(tfObj, chwA, pimsIdentity);
					}
					// Each TypeFeatureGeo
					for (SalesOrgPlants saleOrgPalnts: salesorgPlantsVect) {
						String salesOrg = saleOrgPalnts.getSalesorg();
						LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfObj);
						this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), 
								chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
					}					
					// epims: typeFeatureChgd
					abr.addDebug("----------------------- End Type Feature not promoted -----------------------");
				} //End if Type_feature not promoted
				if (isTFChanged == true || isTFGeoChanged == true || isTFGeoPromoted == false) {
					abr.addDebug("----------------------- Start TypeFeature changed or TypeFeatureGeo not promoted or TypeFeatureGeo changed -----------------------");
					if ("Q".equals(tfObj.getFeatureID())) {
						rdhRestProxy.r126(tfObj, chwA, pimsIdentity);
						rdhRestProxy.r128(tfObj, thisRange, chwA, pimsIdentity);
						rdhRestProxy.r152(tfObj, chwA, pimsIdentity);
					} else {
						rdhRestProxy.r129(tfObj, chwA, pimsIdentity);				
						if (tfObj.ifAlphaNumeric()){
							rdhRestProxy.r130(tfObj.getType(), thisRange, chwA, pimsIdentity);
							rdhRestProxy.r176(tfObj.getType(), thisRange, "NEW", chwA, pimsIdentity);
							rdhRestProxy.r176(tfObj.getType(), thisRange, "UPG", chwA, pimsIdentity);
						}				
						rdhRestProxy.r134(tfObj, chwA, pimsIdentity);
						rdhRestProxy.r153(tfObj, chwA, pimsIdentity);
					}

					// Update Lifecycle UDT entry (note this handles both RPQ and normal features
					// each type feature geo
					if (isTFGeoPromoted == false) {
						for (SalesOrgPlants saleOrgPalnts: salesorgPlantsVect) {
							String salesOrg = saleOrgPalnts.getSalesorg();
							LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfObj);
							this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), 
									chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);							
						}
					} else if (isTFGeoChanged == true) {
						for (SalesOrgPlants saleOrgPalnts: salesorgPlantsVect) {
							String salesOrg = saleOrgPalnts.getSalesorg();
							LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfObj);
							this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), 
									chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
						}						
					}
					// epims： typeFeatureChgd
					abr.addDebug("----------------------- End TypeFeature changed or TypeFeatureGeo not promoted or TypeFeatureGeo changed -----------------------");
				}
				if (!"Q".equals(tfObj.getFeatureID()) && tfObj.getNetPriceMES()) {
					if (tfObj.ifAlphaNumeric()){
						rdhRestProxy.r131(tfObj.getType(), thisRange, chwA, pimsIdentity);
						rdhRestProxy.r177(tfObj.getType(), thisRange, chwA, pimsIdentity);
					}			
					rdhRestProxy.r135(tfObj, chwA, pimsIdentity);
//					tfObj.setUFLinked(true);
					// epims： typeFeatureUFLinked		
				}
				// epims： tfrangeTable		
				
				abr.addDebug("End Type Feature Promote Logic");
			} else {
				abr.addDebug(availItem.getKey() + " didn't link any ANNOUNCEMENT");
			}
		}	
		// release memory
		if (t1EntityList != null) {
			t1EntityList.dereference();
			t1EntityList = null;
		}
		if (entityList != null) {
			entityList.dereference();
			entityList = null;
		}
		abr.addDebug("End processThis()");
	}
	
	private boolean isTypeFeatureChanged(EntityItem t1Item, EntityItem t2Item, List<String> attrList) throws RfcAbrException {
		if(isDiff(t1Item, t2Item, attrList)) {
			return true;
		} else {
			if (FEATURE.equals(t2Item.getEntityType())) {
				boolean t1IsRPQ = isRPQ(t1Item);
				boolean t2IsRPQ = isRPQ(t2Item);
				if (t1IsRPQ != t2IsRPQ) {
					return true;
				}
				boolean t1NoCharge = getNoChargePurchase(t1Item);
				boolean t2NoCharge = getNoChargePurchase(t2Item);
				if (t1NoCharge != t2NoCharge) {
					return true;
				}
			}
			if (TMF.equals(t2Item.getEntityType())) {
				boolean t1ItemReturn = getItemReturn(t1Item);
				boolean t2ItemReturn = getItemReturn(t2Item);
				if (t1ItemReturn != t2ItemReturn) {
					return true;
				}
				boolean t1RemovalCharge = getRemovalCharge(t1Item);
				boolean t2RemovalCharge = getRemovalCharge(t2Item);
				if (t1RemovalCharge != t2RemovalCharge) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isTypeFeatureGeoPromoted(EntityItem[] t1Avails, EntityItem t2Avail) throws RfcAbrException {		
		if (t1Avails == null || t1Avails.length == 0) {
			return false;
		} else {
			for (int j = 0; j < t1Avails.length; j++) {
				EntityItem availT1 = t1Avails[j];
				if (availT1.getKey().equals(t2Avail.getKey())) {
					break;
				}
				if (j == t1Avails.length -1) {
					return false;
				}
			}
			return true;
		}	
	}
	
	private boolean isTypeFeatureGeoChanged(EntityItem t1AnnItem, EntityItem t2AnnItem) throws RfcAbrException {
		Vector<String> attrs = new Vector<>();
		attrs.add(ANNOUNCEMENT_ANNDATE);
		if (isDiff(t1AnnItem, t2AnnItem, attrs)) {
			return true;
		}
		return false;
	}
	
	private boolean isRPQ(EntityItem feaItem){
		return !PokUtils.contains(feaItem, FEATURE_FCTYPE, FCTYPE_SET);
	}
	
	private boolean getNoChargePurchase(EntityItem feaItem) throws RfcAbrException {
		boolean noChargePurchase = false;
		String pricedFeature = getAttributeValue(feaItem, FEATURE_PRICEDFEATURE);
		String zeroPrice = getAttributeValue(feaItem, FEATURE_ZEROPRICE);
		if ("Yes".equals(pricedFeature)) {
			noChargePurchase = true;
		} else if("No".equals(zeroPrice)) {
			noChargePurchase = false;
		}
		abr.addDebug("getNoChargePurchase() PRICEDFEATURE " + pricedFeature + " ZEROPRICE " + zeroPrice + " noChargePurchase " + noChargePurchase);
		return noChargePurchase;
	}

	/**
	 * NetPriceMES 05/03/2016 10:44 PM
	 * it is a Yes/No flag. "Yes" value is ticked when model does have Model conversion or Feature conversion. 
	 * If model does not have any conversion, the flag is not ticked in ePIMS and QSM value = "No".
	 * @param mdlItem
	 * @return
	 * @throws RfcAbrException
	 */
	private boolean getNetPriceMES(EntityItem mdlItem) throws RfcAbrException {
		String machType = getAttributeValue(mdlItem, MODEL_MACHTYPEATR);
		String model = getAttributeValue(mdlItem, MODEL_MODELATR);
		
		Profile m_prof = mdlItem.getProfile();
		Vector attrVct = new Vector();
		Vector valVct = new Vector();
		StringBuffer debugSb = new StringBuffer();
		
		attrVct.add("FROMMACHTYPE");
		attrVct.add("FROMMODEL");
		valVct.add(machType);
		valVct.add(model);
		// Search FCTRANSACTION with FROMMACHTYPE and FROMMODEL
		String searchAction = "SRDFCTRANSACTION1";
		String srchType = FCTRANSACTION;
		abr.addDebug("searchFCTRANSACTION from/to FROMMACHTYPE=" + machType + " and FROMMODEL" + model + " with role=" + m_prof.getRoleCode());
		if(isEntitySearched(searchAction, srchType, attrVct, valVct, debugSb)) {
			return true;
		}
		// Search MODELCONVERT with FROMMACHTYPE and FROMMODEL
		searchAction = "SRDMODELCONVERT";
		srchType = MODELCONVERT;
		abr.addDebug("searchMODELCONVERT from/to FROMMACHTYPE=" + machType + " and FROMMODEL" + model + " with role=" + m_prof.getRoleCode());
		if(isEntitySearched(searchAction, srchType, attrVct, valVct, debugSb)) {
			return true;
		}
		// Search FCTRANSACTION with TOMACHTYPE and TOMODEL
		searchAction = "SRDFCTRANSACTION1";
		srchType = FCTRANSACTION;
		attrVct.clear();
		attrVct.add("TOMACHTYPE");
		attrVct.add("TOMODEL");
		abr.addDebug("searchFCTRANSACTION from/to TOMACHTYPE=" + machType + " and TOMODEL" + model + " with role=" + m_prof.getRoleCode());
		if(isEntitySearched(searchAction, srchType, attrVct, valVct, debugSb)) {
			return true;
		}
		// Search MODELCONVERT with TOMACHTYPE and TOMODEL
		searchAction = "SRDMODELCONVERT";
		srchType = MODELCONVERT;
		abr.addDebug("searchMODELCONVERT from/to TOMACHTYPE=" + machType + " and TOMODEL" + model + " with role=" + m_prof.getRoleCode());
		if(isEntitySearched(searchAction, srchType, attrVct, valVct, debugSb)) {
			return true;
		}
		return false;
	}
	
	private boolean isEntitySearched(String searchAction, String searchType, Vector attrVct, Vector valVct, StringBuffer debugSb) {
		try {
			EntityItem[] items = ABRUtil.doSearch(abr.getDatabase(), abr.getProfile(), searchAction, searchType, false, attrVct, valVct, debugSb);
			if (items != null && items.length > 0) {
				return true;
			}
		} catch (Exception e) {
			abr.addDebug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean getItemReturn(EntityItem tmfItem) throws RfcAbrException {
		boolean itemReturn = false;
		String returndParts = getAttributeFlagValue(tmfItem, TMF_RETURNEDPARTS);
		if ("5100".equals(returndParts)) {
			itemReturn = true;
		} else if ("5101".equals(returndParts)) {
			itemReturn = false;
		}
		return itemReturn; 
	}
	
	private boolean getRemovalCharge(EntityItem tmfItem) throws RfcAbrException {
		boolean removalCharge = false;
		String returndParts = getAttributeFlagValue(tmfItem, TMF_REMOVEPRICE);
		if ("5082".equals(returndParts)) {
			removalCharge = true;
		} else if ("5083".equals(returndParts)) {
			removalCharge = false;
		}
		return removalCharge; 
	}
	
}
