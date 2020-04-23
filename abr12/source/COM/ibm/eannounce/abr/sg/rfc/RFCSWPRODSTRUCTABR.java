package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import com.ibm.rdh.chw.entity.*;
import com.ibm.transform.oim.eacm.util.PokUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

//$Log: RFCSWPRODSTRUCTABR.java,v $
//Revision 1.7  2018/07/27 12:24:07  dlwlan
//Unique ID Code Roll back
//Add RFAREFNUMBER to MODEL
//
//Revision 1.6  2018/06/21 01:42:30  dlwlan
//Finished changes regards Unique RFA number
//
//Revision 1.5  2017/11/28 07:58:23  wangyul
//defect 1783205: 锘縈illennium Partners ESS -- plant order numbers
//
//Revision 1.4  2017/08/04 09:41:38  wangyul
//Defect 1735504	 - RPQ TMF RFCABR support old MODEL without plan avail
//
//Revision 1.3  2017/05/18 12:23:41  wangyul
//1701370: TMF and SWPRODSTRUCT RFCABR performance issue
//
//Revision 1.2  2017/04/18 07:49:58  wangyul
//[Work Item 1681790] New: ESW - unique CLASS (range) and featurenaming support needed for CHW EACM HIPO materials
//
//Revision 1.1  2017/03/09 09:26:44  wangyul
// [Work Item 1658567] RFCABR support HIPO
//

@SuppressWarnings({ "rawtypes" })
public class RFCSWPRODSTRUCTABR extends RFCPRODSTRUCTABR {

	private boolean isTFPromoted = false;
	private boolean isTFChanged = false;
	private boolean isTFGeoPromoted = false;
	private boolean isTFGeoChanged = false;
	private boolean needUpdatParkingTable = false;
	private static List<String> tmfMarkChangedAttrs;
	private static List<String> feaMarkChangedAttrs;

	static {
		tmfMarkChangedAttrs = new ArrayList<>();
		tmfMarkChangedAttrs.add(TMF_INSTALL);
		feaMarkChangedAttrs = new ArrayList<>();
		feaMarkChangedAttrs.add(FEATURE_INVNAME);
	}

	public RFCSWPRODSTRUCTABR(RFCABRSTATUS rfcAbrStatus)
			throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException,
			EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}

	@Override
	public void processThis() throws RfcAbrException, Exception {
		abr.addDebug("Start processThis()");
		EntityList t1EntityList = null;
		EntityItem t1TmfItem = null;
		EntityList entityList2 = null;
		EntityList t1EntityList2 = null;
		EntityItem tmfItem = null;
		boolean isResendFull = false;
		try {
			// ----------------------- Get values from EACM entities -----------------------
			// SWPRODSTRUCT
			tmfItem = getRooEntityItem();
			// Check status
			if (!STATUS_FINAL.equals(getAttributeFlagValue(tmfItem, ATTR_STATUS))) {
				throw new RfcAbrException("The status is not final, it will not promote this SWPRODSTRUCT");
			}
			// MODEL
			EntityItem mdlItem = entityList.getEntityGroup(MODEL).getEntityItem(0); // has to exist
			// SWFEATURE
			EntityItem feaItem = entityList.getEntityGroup(SWFEATURE).getEntityItem(0); // has to exist
			// AVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(tmfItem, "SWPRODSTRUCTAVAIL", AVAIL);
			Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
			Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
			abr.addDebug("SWPRODSTRUCTAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size()
					+ " Last Order avail " + lastOrderAvailVct.size());

			String type = getAttributeValue(mdlItem, MODEL_MACHTYPEATR);
			String model = getAttributeValue(mdlItem, MODEL_MODELATR);
			String feaCode = getAttributeValue(feaItem, FEATURE_FEATURECODE);
			// String annDocNo = type + STRING_SEPARATOR + model + STRING_SEPARATOR +
			// feaCode;
			String annDocNo = type + STRING_SEPARATOR + model;
			// MACHTYPE
			List<EntityItem> machTypeItems = getMACHTYPEsByType(type);
			EntityItem machTypeItem = null;
			if (machTypeItems.size() > 0) {
				machTypeItem = machTypeItems.get(0);
				EntityList entityList = getEntityList(abr.getDatabase(), abr.getProfile(), "dummy",
						machTypeItem.getEntityType(), machTypeItem.getEntityID());
				abr.addDebug("getModelByVe2 EntityList for " + abr.getProfile()
						+ " extract dummy contains the following entities: \n" + PokUtils.outputList(entityList));
				machTypeItem = entityList.getParentEntityGroup().getEntityItem(0);
			} else {
				throw new RfcAbrException(
						"There is no MACHTYPE for type " + type + ", it will not promote this SWPRODSTRUCT");
			}

			// ----------------------- Create RFC input entities -----------------------
			// Type Feature
			TypeFeature tfObj = new TypeFeature();
			boolean isRPQ = isRPQ(feaItem);
			if (isRPQ) {
				tfObj.setFeatureID("Q"); // RPQ = Q
			} else {
				tfObj.setFeatureID("R");
			}

			tfObj.setFeature(feaCode);
			tfObj.setType(type);
			tfObj.setDescription(getAttributeValue(feaItem, FEATURE_INVNAME));
			tfObj.setNoChargePurchase(getNoChargePurchase(feaItem));
			tfObj.setNetPriceMES(isNetPriceMES(mdlItem));
			// Linda and Rupal confirmed ITEMRETURN, REMOVALCHARGE, CUSTOMERSETUP,
			// CAPONDEMAND They all need to be set to NO.
			tfObj.setItemReturned(false);
			tfObj.setRemovalCharge(false);
			tfObj.setCustomerSetup(false);
			tfObj.setCapOnDemand(false);
			tfObj.setApprovalRPQ(isApprovalRPQ(feaItem));
			tfObj.setUFLinked(false);// from Rupal UFLink not required for EACM.
			String thisRange = calculateRange(machTypeItem, feaItem, tfObj);
			abr.addDebug("thisRange: " + thisRange);
			tfObj.setFeatureRange(thisRange);
			abr.addDebug("TypeFeature: " + tfObj.toString());
			abr.addDebug("TypeFeature  ApprovalRPQ:" + tfObj.getApprovalRPQ());

			TypeModelFeature tmfObj = new TypeModelFeature();
			tmfObj.setType(type);
			tmfObj.setModel(model);
			tmfObj.setFeature(feaCode);
			tmfObj.setAnnDocNo(annDocNo);
			tmfObj.setFeatureID(tfObj.getFeatureID());
			abr.addDebug("TypeModelFeature: " + tmfObj.toString());

			String pimsIdentity = "C";
			abr.addDebug("PimsIdentity:" + pimsIdentity);

			// ----------------------- Check Resend full -----------------------
			isResendFull = SYSFEEDRESEND_YES.equals(getAttributeFlagValue(tmfItem, ATTR_SYSFEEDRESEND));
			abr.addDebug("Resend full: " + isResendFull);

			// Check isTFPromoted and isTFChanged
			AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(tmfItem, RFCABRSTATUS);
			boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
			abr.addDebug("Exist passed RFCABRSTATUS before: " + isPassedAbrExist);
			if (!isResendFull && isPassedAbrExist) {
				isTFPromoted = true;
				String t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
				String t2DTS = abr.getCurrentTime();
				Profile profileT1 = abr.switchRole(ROLE_CODE);
				profileT1.setValOnEffOn(t1DTS, t1DTS);
				profileT1.setEndOfDay(t2DTS);
				profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
				profileT1.setLoginTime(t2DTS);
				t1EntityList = getEntityList(profileT1);
				abr.addDebug("EntityList for T1 " + profileT1.getValOn() + " extract " + getVeName()
						+ " contains the following entities: \n" + PokUtils.outputList(t1EntityList));

				// TMF at T1
				t1TmfItem = t1EntityList.getParentEntityGroup().getEntityItem(0);

				isTFChanged = isTypeFeatureChanged(t1TmfItem, tmfItem);
			}

			if (planAvailVct.size() == 0) {
				// SWPRODSTRUCT no RPQ, must have a Planned Availability
				throw new RfcAbrException("There is no Planned Availability for " + tmfItem.getKey()
						+ ", it will not promote this SWPRODSTRUCT");
			} else {
				for (int i = 0; i < planAvailVct.size(); i++) {
					EntityItem availItem = (EntityItem) planAvailVct.get(i);
					abr.addDebug("Promote Type Feature for " + availItem.getKey());

					String annDate = getTMFAnnDate(tmfItem, mdlItem, feaItem, availItem);
					abr.addDebug("T2 ANNDATE: " + annDate);
					// Get all salesorg and plants from GENERALAREA linked to AVAIL
					Vector generalAreaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalAreaVct);

					if (isTFPromoted) {
						if (t1EntityList != null && t1TmfItem != null) {
							EntityItem t1FeaItem = t1EntityList.getEntityGroup(SWFEATURE).getEntityItem(0);
							EntityItem t1MdlItem = t1EntityList.getEntityGroup(MODEL).getEntityItem(0);
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1TmfItem, "SWPRODSTRUCTAVAIL", AVAIL);
							Vector t1PlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE,
									PLANNEDAVAIL);
							// Check isTFGeoChanged
							// 1. ANNDATE change
							// 2. New country
							EntityItem t1Avail = getEntityItemAtT1(t1PlanAvailVct, availItem);
							String t1AnnDate = getTMFAnnDate(t1TmfItem, t1MdlItem, t1FeaItem, t1Avail);
							abr.addDebug("T1 ANNDATE: " + t1AnnDate);
							isTFGeoChanged = !annDate.equals(t1AnnDate);
							// isTFGeoChanged = isTypeFeatureGeoChanged(t1PlanAvailVct, availItem);
							if (!isTFGeoChanged) {
								List<String> t2NewCountries = getNewCountries(t1PlanAvailVct, availItem);
								isTFGeoPromoted = t2NewCountries.size() == 0;
								if (!isTFGeoPromoted) {
									salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList,
											t2NewCountries);
								}
							}
						} else {
							abr.addDebug("t1EntityList not null:" + (t1EntityList != null) + " t1TmfItem not null:"
									+ (t1TmfItem != null));
						}
					}
					abr.addDebug("isTFPromoted: " + isTFPromoted + " isTFChanged: " + isTFChanged + " isTFGeoPromoted: "
							+ isTFGeoPromoted + " isTFGeoChanged: " + isTFGeoChanged);

					// ----------------------- Create RFC input entitie CHWAnnouncement and
					// CHWGeoAnn -----------------------
					CHWAnnouncement chwA = new CHWAnnouncement();
					chwA.setAnnDocNo(annDocNo); // MACHTYPEATR|MODELATR|FEATURECODE
					chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
					chwA.setSegmentAcronym(getSegmentAcronymForAnn(tmfItem));
					abr.addDebug("CHWAnnouncement: " + chwA.toString());

					CHWGeoAnn chwAg = new CHWGeoAnn();
					chwAg.setAnnouncementDate(new SimpleDateFormat("yyyy-MM-dd").parse(annDate));
					abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

					promoteTypeModelFeature(tfObj, tmfObj, chwA, chwAg, salesOrgPlantList, pimsIdentity, thisRange);

				}
			}

			abr.addDebug("Start promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
			boolean isTfwPromoted = false;
			boolean isTfwChanged = false;
			if (lastOrderAvailVct.size() > 0) {
				for (int i = 0; i < lastOrderAvailVct.size(); i++) {
					EntityItem lastOrderAvailItem = (EntityItem) lastOrderAvailVct.get(i);

					abr.addDebug("Promote WDFM Announcement for " + lastOrderAvailItem.getKey());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date wdfmDate = sdf.parse(getAttributeValue(lastOrderAvailItem, AVAIL_EFFECTIVEDATE));

					Vector generalareaVct = PokUtils.getAllLinkedEntities(lastOrderAvailItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalareaVct);

					if (!isResendFull && isPassedAbrExist) {
						if (t1EntityList != null && t1TmfItem != null) {
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1TmfItem, "SWPRODSTRUCTAVAIL", AVAIL);
							Vector t1LastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct,
									AVAIL_AVAILTYPE, LASTORDERAVAIL);
							// Check isTfwChanged
							// 1. AVAIL effective date change
							// 2. New country
							isTfwChanged = isSameEffectiveDate(t1LastOrderAvailVct, lastOrderAvailItem);
							if (!isTfwChanged) {
								List<String> t2NewCountries = getNewCountries(t1LastOrderAvailVct, lastOrderAvailItem);
								if (t2NewCountries.size() > 0) {
									isTfwPromoted = false;
									salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList,
											t2NewCountries);
								} else {
									isTfwPromoted = true;
								}
							}
						}
					}
					abr.addDebug("isTfwPromoted: " + isTfwPromoted + " isTfwChanged: " + isTfwChanged);
					if (!isTfwPromoted || isTfwChanged) {
						LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmfObj);
						for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
							String salesOrg = salesOrgPlants.getSalesorg();
							LifecycleData lcd = rdhRestProxy.r200(lcdGen.getMaterial(), lcdGen.getVarCond(),
									tmfObj.getAnnDocNo(), "wdfm", pimsIdentity, salesOrg);
							abr.addDebug("Call r200 successfully for SalesOrg " + salesOrg + " " + lcd);
							updateWDFMLifecyle(lcd, lcdGen.getVarCond(), lcdGen.getMaterial(), wdfmDate,
									tmfObj.getAnnDocNo(), pimsIdentity, salesOrg);
							abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + salesOrg);
						}
					}
				}
			}
			abr.addDebug("End promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");

			if (needUpdatParkingTable) {
				if (needReleaseParkingTable()) {
					rdhRestProxy.r144(annDocNo, "R", pimsIdentity);
				} else {
					rdhRestProxy.r144(annDocNo, "H", pimsIdentity);
				}
			}
		} finally {
			// Set SYSFEEDRESEND to No
			if (isResendFull && tmfItem != null) {
				setFlagValue(ATTR_SYSFEEDRESEND, SYSFEEDRESEND_NO, tmfItem);
			}
			// release memory
			if (t1EntityList != null) {
				t1EntityList.dereference();
				t1EntityList = null;
			}
			if (t1EntityList2 != null) {
				t1EntityList2.dereference();
				t1EntityList2 = null;
			}
			if (entityList != null) {
				entityList.dereference();
				entityList = null;
			}
			if (entityList2 != null) {
				entityList2.dereference();
				entityList2 = null;
			}
			abr.addDebug("End processThis()");
		}
	}

	public String getVeName() {
		return "RFCSWPRODSTRUCT";
	}

	private void promoteTypeModelFeature(TypeFeature tfObj, TypeModelFeature tmfObj, CHWAnnouncement chwA,
			CHWGeoAnn chwAg, List<SalesOrgPlants> salesOrgPlantList, String pimsIdentity, String thisRange)
			throws RfcAbrException, Exception {
		abr.addDebug("Start promote Type Feature");
		if (!isTFPromoted || isTFChanged || !isTFGeoPromoted || isTFGeoChanged) {
			if ("Q".equals(tfObj.getFeatureID())) {
				abr.addDebug("Start RPQ Feature");
				rdhRestProxy.r126(tfObj, chwA, pimsIdentity);
				if (!isTFPromoted) {
					callr127WithAccessAuthority(tfObj, thisRange, chwA, pimsIdentity);
				}
				rdhRestProxy.r128(tfObj, thisRange, chwA, pimsIdentity);
				rdhRestProxy.r152(tfObj, chwA, pimsIdentity);
				abr.addDebug("End RPQ Feature");
			} else {
				abr.addDebug("Start not RPQ Feature");
				rdhRestProxy.r129(tfObj, tmfObj.getModel(), chwA, pimsIdentity);
				if (tfObj.ifAlphaNumeric()) {
					callr130WithAccessAuthority(tfObj.getType(), tmfObj.getModel(), thisRange, chwA, pimsIdentity);
					rdhRestProxy.r176(tfObj.getType(), tmfObj.getModel(), thisRange, "NEW", chwA, pimsIdentity);
					rdhRestProxy.r176(tfObj.getType(), tmfObj.getModel(), thisRange, "UPG", chwA, pimsIdentity);
				}
				rdhRestProxy.r134(tfObj, tmfObj.getModel(), chwA, pimsIdentity);
				rdhRestProxy.r153(tfObj, tmfObj.getModel(), chwA, pimsIdentity);
				abr.addDebug("End not RPQ Feature");
			}

			abr.addDebug("Start updateAnnLifecyle for Type Feature");
			for (SalesOrgPlants saleOrgPalnts : salesOrgPlantList) {
				String salesOrg = saleOrgPalnts.getSalesorg();
				LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tfObj);
				this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(),
						chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
			}
			abr.addDebug("End updateAnnLifecyle for Type Feature");
		}

		if ("Q".equals(tfObj.getFeatureID())) {
			abr.addDebug("Start RPQ Feature");
			rdhRestProxy.r138(tfObj, "NEW", chwA, pimsIdentity);
			rdhRestProxy.r138(tfObj, "UPG", chwA, pimsIdentity);
			boolean featureTypeMTCExists = rdhRestProxy.r204(tfObj.getType() + "MTC");
			if (featureTypeMTCExists) {
				rdhRestProxy.r138(tfObj, "MTC", chwA, pimsIdentity);
			}
			abr.addDebug("End RPQ Feature");
		}
		abr.addDebug("End promote Type Feature");

		// Type Model Feature
		if (!isTFPromoted || !isTFGeoPromoted || isTFGeoChanged) {
			abr.addDebug("Start updateAnnLifecyle for Type Model Feature");
			for (SalesOrgPlants saleOrgPalnts : salesOrgPlantList) {
				String salesOrg = saleOrgPalnts.getSalesorg();
				LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmfObj);
				this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(),
						tmfObj.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
			}
			abr.addDebug("End updateAnnLifecyle for Type Model Feature");
		}

		needUpdatParkingTable = true;
		abr.addDebug("set needUpdatParkingTable to " + needUpdatParkingTable);
	}

	private boolean isTypeFeatureChanged(EntityItem t1TmfItem, EntityItem t2TmfItem) throws RfcAbrException {
		// TMF
		if (isDiff(t1TmfItem, t2TmfItem, tmfMarkChangedAttrs)) {
			return true;
		}

		// MODEL
		EntityItem t1MdlItem = null;
		EntityItem t2MdlItem = null;
		List<EntityItem> t1MdlVct = getLinkedRelator(t1TmfItem, MODEL);
		if (t1MdlVct.size() > 0) {
			t1MdlItem = t1MdlVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T1 for " + t1TmfItem.getKey());
		}
		List<EntityItem> t2MdlVct = getLinkedRelator(t2TmfItem, MODEL);
		if (t2MdlVct.size() > 0) {
			t2MdlItem = t2MdlVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T2 for " + t1TmfItem.getKey());
		}
		boolean t1NoPriceMES = isNetPriceMES(t1MdlItem);
		boolean t2NoPriceMES = isNetPriceMES(t2MdlItem);
		abr.addDebug("isTypeFeatureChanged NetPriceMES T1: " + t1NoPriceMES + " T2: " + t2NoPriceMES);
		if (t1NoPriceMES != t2NoPriceMES) {
			return true;
		}

		// FEATURE
		EntityItem t1FeaItem = null;
		EntityItem t2FeaItem = null;
		List<EntityItem> t1FeaVct = getLinkedRelator(t1TmfItem, SWFEATURE);
		if (t1FeaVct.size() > 0) {
			t1FeaItem = t1FeaVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found SWFEATURE at T1 for " + t1TmfItem.getKey());
		}
		List<EntityItem> t2FeaVct = getLinkedRelator(t2TmfItem, SWFEATURE);
		if (t2FeaVct.size() > 0) {
			t2FeaItem = t2FeaVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found SWFEATURE at T2 for " + t1TmfItem.getKey());
		}
		if (isDiff(t1FeaItem, t2FeaItem, feaMarkChangedAttrs)) {
			return true;
		}
		boolean t1IsRPQ = isRPQ(t1FeaItem);
		boolean t2IsRPQ = isRPQ(t2FeaItem);
		if (t1IsRPQ != t2IsRPQ) {
			abr.addDebug("isTypeFeatureChanged isRPQ T1: " + t1IsRPQ + " T2: " + t2IsRPQ);
			return true;
		}
		boolean t1NoCharge = getNoChargePurchase(t1FeaItem);
		boolean t2NoCharge = getNoChargePurchase(t2FeaItem);
		if (t1NoCharge != t2NoCharge) {
			abr.addDebug("isTypeFeatureChanged NoChargePurchase T1: " + t1NoCharge + " T2: " + t2NoCharge);
			return true;
		}
		return false;
	}

	private boolean isSameEffectiveDate(Vector t1AvailItemVct, EntityItem t2AvailItem) throws RfcAbrException {
		EntityItem t1Avail = getEntityItemAtT1(t1AvailItemVct, t2AvailItem);
		if (t1Avail != null) {
			String t1Date = getAttributeValue(t1Avail, AVAIL_EFFECTIVEDATE);
			String t2Date = getAttributeValue(t2AvailItem, AVAIL_EFFECTIVEDATE);
			if (!t2Date.equals(t1Date)) {
				abr.addDebug("isTypeFeatureGeoChanged true T1 Date " + t1Date + " T2 Date " + t2Date);
				return true;
			}
		} else {
			abr.addDebug("isTypeFeatureGeoChanged true AVAIL is null at T1 for " + t2AvailItem.getKey());
			return true;
		}
		abr.addDebug("isTypeFeatureGeoChanged false");
		return false;
	}

	// <ANNDATE>
	// 1.聽聽聽聽 SWPRODSTRUCTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE for the AVAIL
	// where AVAILTYPE = 鈥淧lanned Availability鈥� (146).
	// Countries found in ANNOUNCEMENT.COUNTRYLIST.
	// 2.聽聽聽聽 IF SWPRODSTRUCT, THEN MODEL.ANNDATE
	private String getTMFAnnDate(EntityItem tmfItem, EntityItem mdlItem, EntityItem feaItem, EntityItem availItem)
			throws RfcAbrException {
		String annDate = null;
		if (availItem != null) {
			Vector annVect = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", ANNOUNCEMENT);
			if (annVect != null && annVect.size() > 0) {
				EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
				annDate = getAttributeValue(annItem, ANNDATE);
			}
		}
		if (annDate == null || "".equals(annDate)) {
			annDate = getAttributeValue(mdlItem, ANNDATE);
		}
		if (annDate == null || "".equals(annDate)) {
			throw new RfcAbrException("ANNDATE is null, it will not promote this PRODSTRUCT");
		}
		return annDate;
	}
}
