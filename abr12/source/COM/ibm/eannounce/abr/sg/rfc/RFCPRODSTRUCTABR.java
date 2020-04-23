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

//$Log: RFCPRODSTRUCTABR.java,v $
//Revision 1.33  2019/12/13 10:14:25  software
//Story 2021975 update
//
//Revision 1.32  2019/10/11 13:05:34  dlwlan
//Story 2021975? add catch Exception process of Type Range over max size.
//
//Revision 1.31  2018/07/27 12:24:07  dlwlan
//Unique ID Code Roll back
//Add RFAREFNUMBER to MODEL
//
//Revision 1.30  2018/06/21 01:42:30  dlwlan
//Finished changes regards Unique RFA number
//
//Revision 1.29  2018/05/30 14:56:29  dlwlan
//Defect 1855454 move R128 after R138
//
//Revision 1.28  2018/05/30 09:16:15  dlwlan
//Defect 1855454 RFC caller order adjustment for RPQ feature and modelconvert promotion.
//
//Revision 1.27  2018/03/06 07:39:21  wangyul
//Story 1816333 RFCABR report TYPERANGE value is too long than our column length
//
//Revision 1.26  2017/08/04 09:41:38  wangyul
//Defect 1735504	 - RPQ TMF RFCABR support old MODEL without plan avail
//
//Revision 1.25  2017/05/18 12:23:41  wangyul
//1701370: TMF and SWPRODSTRUCT RFCABR performance issue
//
//Revision 1.24  2017/01/23 06:37:15  wangyul
//[Work Item 1642728] CHW Simplification - Need to support Non RFA Avail while deriving the announcement dates
//
//Revision 1.23  2017/01/05 14:08:04  wangyul
//Story1635023 - OIMS CHW Simplification - RESEND function for RFC ABRs- MODEL,MODELCONVERT,TMFs
//
//Revision 1.22  2016/12/01 13:40:56  wangyul
//[Work Item 1627842] New: pre-existing parking records not being set to H status by EACM RFCABR
//
//Revision 1.21  2016/11/09 13:34:38  wangyul
//[Work Item 1617881] New: Configurable option to move parking table records to R status or leave them in H status
//
//Revision 1.20  2016/11/02 12:35:08  wangyul
//IN8581284 ==> Inconsistent RPQ approval setting for 8S1509 machine 2078/124 between CBS and AAS
//
//Revision 1.19  2016/09/14 12:22:15  guobin
//Add file  access lock on r130,r131 and r176 Defect 1590848	Multiple rows were created in KLAH table for the same pair of class and klart.
//
//Revision 1.18  2016/08/31 14:32:38  guobin
//Fix Defect 1586386 mass update for 2 models - 2964 and 2965. 1620 distinct zdmclass/zdmrelnum pairs.  Defect 1586381 mass update for 2 models - 2964 and 2965 cause duplicate IDOCs.
//
//Revision 1.17  2016/08/31 14:30:19  guobin
//Update RFANUM to MACHTYPE-MODELATR
//
//Revision 1.16  2016/08/18 07:56:56  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date - isWithdrawChanged
//
//Revision 1.15  2016/08/17 07:06:11  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date
//
//Revision 1.14  2016/08/10 09:00:15  wangyul
//Move r144 to update parking table at last
//
//Revision 1.13  2016/08/04 07:35:32  wangyul
//replace chinese ? to :
//
//Revision 1.12  2016/08/02 08:17:33  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types  - fix generate rangeName
//
//Revision 1.11  2016/08/01 09:02:34  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types - set feature range to TypeFeature
//
//Revision 1.10  2016/07/29 13:54:33  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types - comment set range
//
//Revision 1.9  2016/07/29 13:03:47  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types - set range to TypeFeature
//
//Revision 1.8  2016/07/21 15:44:04  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types
//
//Revision 1.7  2016/07/15 08:20:02  wangyul
//Fix log info
//
//Revision 1.6  2016/07/11 07:27:08  wangyul
//Story1545764 - OIM Simplification- WDFM Announcement  promote- Code promote
//
//Revision 1.5  2016/07/04 09:20:08  wangyul
//Task1549093 - promote engine logic updates for RPQ - pull dates,country,sales orgs and plant code etc - development
//

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RFCPRODSTRUCTABR extends RfcAbrAdapter {

	private static final String TYPERANGES_REX_SEPARATOR = "\\|"; // | means or in Rex,
	private static final String TYPERANGES_SEPARATOR = "|";
	private static final String TYPERANGE_SEPARATOR = "-";

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

	public RFCPRODSTRUCTABR(RFCABRSTATUS rfcAbrStatus)
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
		EntityItem machTypeItem = null;
		TypeFeature tfObj = null;
		try {
			// ----------------------- Get values from EACM entities -----------------------
			// TMF
			tmfItem = getRooEntityItem();
			// Check status
			if (!STATUS_FINAL.equals(getAttributeFlagValue(tmfItem, ATTR_STATUS))) {
				throw new RfcAbrException("The status is not final, it will not promote this " + tmfItem.getKey());
			}
			// MODEL
			EntityItem mdlItem = entityList.getEntityGroup(MODEL).getEntityItem(0); // has to exist
			// FEATURE
			EntityItem feaItem = entityList.getEntityGroup(FEATURE).getEntityItem(0); // has to exist
			// AVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(tmfItem, "OOFAVAIL", AVAIL);
			Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
			Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
			abr.addDebug("OOFAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size()
					+ " Last Order avail " + lastOrderAvailVct.size());

			String type = getAttributeValue(mdlItem, MODEL_MACHTYPEATR);
			String model = getAttributeValue(mdlItem, MODEL_MODELATR);
			String feaCode = getAttributeValue(feaItem, FEATURE_FEATURECODE);
			//+ getUniqueId(UniqueIdGenerator.TYPE_PRODSTRUCT)
			String annDocNo = type + STRING_SEPARATOR + model;
			// MACHTYPE
			List<EntityItem> machTypeItems = getMACHTYPEsByType(type);
			
			if (machTypeItems.size() > 0) {
				machTypeItem = machTypeItems.get(0);
				EntityList entityList = getEntityList(abr.getDatabase(), abr.getProfile(), "dummy",
						machTypeItem.getEntityType(), machTypeItem.getEntityID());
				abr.addDebug("getModelByVe2 EntityList for " + abr.getProfile()
						+ " extract dummy contains the following entities: \n" + PokUtils.outputList(entityList));
				machTypeItem = entityList.getParentEntityGroup().getEntityItem(0);
			} else {
				throw new RfcAbrException(
						"There is no MACHTYPE for type " + type + ", it will not promote this PRODSTRUCT");
			}

			// ----------------------- Create RFC input entities -----------------------
			// Type Feature
			tfObj = new TypeFeature();
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
			tfObj.setItemReturned(getItemReturn(tmfItem));
			tfObj.setRemovalCharge(getRemovalCharge(tmfItem));
			tfObj.setCustomerSetup("CE".equals(getAttributeValue(tmfItem, TMF_INSTALL)));
			tfObj.setCapOnDemand("202".equals(getAttributeFlagValue(feaItem, FEATURE_HWFCCAT)));
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

			List<SalesOrgPlants> salesOrgPlantsForRPQ = null;
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

			if (planAvailVct.size() == 0) { // RPQ check
				abr.addDebug("TMF doesn鈥檛 link OFFAVAIL (Planed AVAIL)");
				String t2DTS = abr.getCurrentTime();
				Profile profileT2 = abr.switchRole(ROLE_CODE);
				profileT2.setValOnEffOn(t2DTS, t2DTS);
				profileT2.setEndOfDay(t2DTS);
				profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
				profileT2.setLoginTime(t2DTS);
				entityList2 = getEntityList(abr.getDatabase(), profileT2, "RFCPRODSTRUCT2", mdlItem.getEntityType(),
						mdlItem.getEntityID());
				abr.addDebug("EntityList for " + profileT2.getValOn() + " extract " + "RFCPRODSTRUCT2"
						+ " contains the following entities: \n" + PokUtils.outputList(entityList2));

				mdlItem = entityList2.getParentEntityGroup().getEntityItem(0);
				Vector mdlAvailVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", AVAIL);
				Vector mdlPlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, AVAIL_AVAILTYPE,
						PLANNEDAVAIL);
				abr.addDebug(
						"MODELAVAIL all availVct: " + mdlAvailVct.size() + " plannedavail: " + mdlPlanAvailVct.size());

				// Get announcement date
				String annDate = getTMFAnnDate(tmfItem, mdlItem, feaItem, null);
				// Get salesOrg and plant
				List<SalesOrgPlants> salesOrgPlantList = null;
				if (mdlPlanAvailVct.size() == 0) {
					// old model锛� no plan avail, country for WW
					Vector gaVct = searchAllGeneralAreas();
					salesOrgPlantList = getAllSalesOrgPlant(gaVct);
					if (isTFPromoted) {
						isTFGeoPromoted = false;
						isTFGeoChanged = true;
					}
				} else {
					/**
					 * 1. get all GENERALAREAs link of MODEL's all plan avail 2. get all salesOrg
					 * and plant from step1 GENERALAREAs 3. get country list for TMF 4. get all
					 * matched salesOrg and plant from step2 with step3 countries
					 */
					Vector generalAreaVct = new Vector();
					for (int i = 0; i < mdlPlanAvailVct.size(); i++) {
						EntityItem mdlPlanAvailItem = (EntityItem) mdlPlanAvailVct.get(i);
						generalAreaVct.addAll(PokUtils.getAllLinkedEntities(mdlPlanAvailItem, "AVAILGAA", GENERALAREA));
					}
					salesOrgPlantList = getAllSalesOrgPlant(generalAreaVct);
					List<String> countryList = getCountryListForRPQ(mdlPlanAvailVct, feaItem);
					salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList, countryList);

					// Check isTFGeoPromoted and isTFGeoChanged
					if (isTFPromoted) {
						if (t1EntityList != null && t1TmfItem != null) {
							EntityItem t1FeaItem = t1EntityList.getEntityGroup(FEATURE).getEntityItem(0);
							EntityItem t1MdlItem = t1EntityList.getEntityGroup(MODEL).getEntityItem(0);
							t1EntityList2 = getEntityList(abr.getDatabase(), t1MdlItem.getProfile(), "RFCPRODSTRUCT2",
									t1MdlItem.getEntityType(), t1MdlItem.getEntityID());
							abr.addDebug("EntityList for " + t1MdlItem.getProfile().getValOn() + " extract "
									+ "RFCPRODSTRUCT2" + " contains the following entities: \n"
									+ PokUtils.outputList(entityList));
							t1MdlItem = t1EntityList2.getParentEntityGroup().getEntityItem(0);
							Vector t1MdlAvailVct = PokUtils.getAllLinkedEntities(t1MdlItem, "MODELAVAIL", AVAIL);
							Vector t1MdlPlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1MdlAvailVct,
									AVAIL_AVAILTYPE, PLANNEDAVAIL);
							abr.addDebug("MODELAVAIL T1 all availVct: " + t1MdlAvailVct.size() + " plannedavail: "
									+ t1MdlPlanAvailVct.size());
							List<String> t1CountryList = getCountryListForRPQ(t1MdlPlanAvailVct, t1FeaItem);
							countryList.removeAll(t1CountryList); // New Country
							// isTFGeoChanged will update all country, isTFGeoPromoted only update new
							// country, check isTFGeoChanged first
							String t1AnnDate = getTMFAnnDate(t1TmfItem, t1MdlItem, t1FeaItem, null);
							isTFGeoChanged = !annDate.equals(t1AnnDate);
							if (!isTFGeoChanged) {
								isTFGeoPromoted = countryList.size() == 0;
								// Get salesOrg for new countries
								if (!isTFGeoPromoted) {
									salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList,
											countryList);
								}
							}
						} else {
							abr.addDebug("t1EntityList not null:" + (t1EntityList != null) + " t1TmfItem not null:"
									+ (t1TmfItem != null));
						}
					}
				}
				salesOrgPlantsForRPQ = salesOrgPlantList;
				abr.addDebug("isTFPromoted: " + isTFPromoted + " isTFChanged: " + isTFChanged + " isTFGeoPromoted: "
						+ isTFGeoPromoted + " isTFGeoChanged: " + isTFGeoChanged);

				// ----------------------- Create RFC input entitie CHWAnnouncement and
				// CHWGeoAnn -----------------------
				CHWAnnouncement chwA = new CHWAnnouncement();
				chwA.setAnnDocNo(annDocNo);
				chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
				chwA.setSegmentAcronym(getSegmentAcronymForAnn(tmfItem));
				abr.addDebug("CHWAnnouncement: " + chwA.toString());

				CHWGeoAnn chwAg = new CHWGeoAnn();
				chwAg.setAnnouncementDate(new SimpleDateFormat("yyyy-MM-dd").parse(annDate));
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

				promoteTypeModelFeature(tfObj, tmfObj, chwA, chwAg, salesOrgPlantList, pimsIdentity, thisRange);
			} else {
				for (int i = 0; i < planAvailVct.size(); i++) {
					EntityItem availItem = (EntityItem) planAvailVct.get(i);
					abr.addDebug("Promote Type Feature for " + availItem.getKey());

					// Get all salesorg and plants from GENERALAREA linked to AVAIL
					Vector generalAreaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalAreaVct);
					if (isTFPromoted) {
						if (t1EntityList != null && t1TmfItem != null) {
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1TmfItem, "OOFAVAIL", AVAIL);
							Vector t1PlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE,
									PLANNEDAVAIL);

							List<String> t2NewCountries = getNewCountries(t1PlanAvailVct, availItem);
							// isTFGeoChanged will update all country, isTFGeoPromoted only update new
							// country, check isTFGeoChanged first
							isTFGeoChanged = isTypeFeatureGeoChanged(t1PlanAvailVct, availItem);
							if (!isTFGeoChanged) {
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

					// ----------------------- Create RFC CHWAnnouncement and CHWGeoAnn
					// -----------------------
					CHWAnnouncement chwA = new CHWAnnouncement();
					chwA.setAnnDocNo(annDocNo);
					chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
					chwA.setSegmentAcronym(getSegmentAcronymForAnn(tmfItem));
					abr.addDebug("CHWAnnouncement: " + chwA.toString());

					CHWGeoAnn chwAg = new CHWGeoAnn();
					chwAg.setAnnouncementDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(getTMFAnnDate(tmfItem, mdlItem, feaItem, availItem)));
					abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

					promoteTypeModelFeature(tfObj, tmfObj, chwA, chwAg, salesOrgPlantList, pimsIdentity, thisRange);
				}
			}

			abr.addDebug("Start promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
			boolean isTfwPromoted = false;
			boolean isTfwChanged = false;
			if (planAvailVct.size() > 0 && lastOrderAvailVct.size() > 0) {
				for (int i = 0; i < lastOrderAvailVct.size(); i++) {
					EntityItem lastOrderAvailItem = (EntityItem) lastOrderAvailVct.get(i);

					abr.addDebug("Promote WDFM Announcement for " + lastOrderAvailItem.getKey());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date wdfmDate = sdf.parse(getAttributeValue(lastOrderAvailItem, AVAIL_EFFECTIVEDATE));

					Vector generalareaVct = PokUtils.getAllLinkedEntities(lastOrderAvailItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalareaVct);

					if (!isResendFull && isPassedAbrExist) {
						if (t1EntityList != null && t1TmfItem != null) {
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1TmfItem, "OOFAVAIL", AVAIL);
							Vector t1LastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct,
									AVAIL_AVAILTYPE, LASTORDERAVAIL);
							// isTfwChanged will update all country, isTfwPromoted only update new country,
							// check isTfwChanged first
							isTfwChanged = isTypeFeatureGeoChanged(t1LastOrderAvailVct, lastOrderAvailItem);
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
			} else if (planAvailVct.size() == 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String withDrawDate = getAttributeValue(tmfItem, TMF_WITHDRAWDATE);
				if (withDrawDate != null && !"".equals(withDrawDate)) {
					if (!isResendFull && isPassedAbrExist) {
						String t1WithDrawDate = getAttributeValue(t1TmfItem, TMF_WITHDRAWDATE);
						isTfwChanged = !withDrawDate.equals(t1WithDrawDate);
						if (!isTfwChanged) {
							isTfwPromoted = isTFGeoPromoted;
						}
					}
					Date wdfmDate = sdf.parse(withDrawDate);
					abr.addDebug("isTfwPromoted: " + isTfwPromoted + " isTfwChanged: " + isTfwChanged
							+ " salesOrgPlantsForRPQ is null " + (salesOrgPlantsForRPQ == null));
					if ((!isTfwPromoted || isTfwChanged) && salesOrgPlantsForRPQ != null) {
						LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmfObj);
						for (SalesOrgPlants salesOrgPlants : salesOrgPlantsForRPQ) {
							String salesOrg = salesOrgPlants.getSalesorg();
							LifecycleData lcd = rdhRestProxy.r200(lcdGen.getMaterial(), lcdGen.getVarCond(),
									tmfObj.getAnnDocNo(), "wdfm", pimsIdentity, salesOrg);
							abr.addDebug("Call r200 successfully for SalesOrg " + salesOrg + " " + lcd);
							updateWDFMLifecyle(lcd, lcdGen.getVarCond(), lcdGen.getMaterial(), wdfmDate,
									tmfObj.getAnnDocNo(), pimsIdentity, salesOrg);
							abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + salesOrg);
						}
					}
				} else {
					abr.addDebug("TMF WITHDRAWDATE is empty");
				}
			}
			abr.addDebug("End promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");

			if (needUpdatParkingTable) {
				if (needReleaseParkingTable()) {
					rdhRestProxy.r144(annDocNo, "R", pimsIdentity);
					abr.addDebug("Call R144 update parking table successfully for TypeFeature");
				} else {
					rdhRestProxy.r144(annDocNo, "H", pimsIdentity);
				}
			}
			
		} catch (Exception ex){
			if (ex.getMessage().contains("Max number of characteristics to assign to classication")){
				if(isAlphaNumeric(tfObj.getFeature())){
					String rangeName = getAttributeValue(machTypeItem, MACHTYPE_RANGENAME);
					if(rangeName == null || "".equals(rangeName)) {
						String range = "A000";
						setTextValue(MACHTYPE_RANGENAME, range, machTypeItem);
					}else{
						setTextValue(MACHTYPE_RANGENAME, getRangePlusOne(getAttributeValue(machTypeItem, MACHTYPE_RANGENAME)), machTypeItem);
					}
					setTextValue(MACHTYPE_FEATURECOUNT, "1", machTypeItem);
					throw new RfcAbrException("RFC ABR failed, Please try again. " + ex);
				}else{
					throw ex;	
				}
			}else{
				throw ex;
			}
		}finally {
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
		return "RFCPRODSTRUCT";
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
				//Defect 1855454 at 20180530
				rdhRestProxy.r152(tfObj, chwA, pimsIdentity);
				abr.addDebug("End RPQ Feature");
			} else {
				abr.addDebug("Start not RPQ Feature");
				rdhRestProxy.r129(tfObj, chwA, pimsIdentity);
				if (tfObj.ifAlphaNumeric()) {
					callr130WithAccessAuthority(tfObj.getType(), thisRange, chwA, pimsIdentity);
					rdhRestProxy.r176(tfObj.getType(), thisRange, "NEW", chwA, pimsIdentity);
					rdhRestProxy.r176(tfObj.getType(), thisRange, "UPG", chwA, pimsIdentity);
				}
				rdhRestProxy.r134(tfObj, chwA, pimsIdentity);
				rdhRestProxy.r153(tfObj, chwA, pimsIdentity);
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

		if (!"Q".equals(tfObj.getFeatureID()) && tfObj.getNetPriceMES()) {
			abr.addDebug("Start not RPQ Feature and NetPriceMES");
			if (tfObj.ifAlphaNumeric()) {
				callr131WithAccessAuthority(tfObj.getType(), thisRange, chwA, pimsIdentity);
				rdhRestProxy.r177(tfObj.getType(), thisRange, chwA, pimsIdentity);
			}
			rdhRestProxy.r135(tfObj, chwA, pimsIdentity);
			abr.addDebug("End not RPQ Feature and NetPriceMES");
		}

		if ("Q".equals(tfObj.getFeatureID())) {
			abr.addDebug("Start RPQ Feature");
			rdhRestProxy.r138(tfObj, "NEW", chwA, pimsIdentity);
			rdhRestProxy.r138(tfObj, "UPG", chwA, pimsIdentity);
			boolean featureTypeMTCExists = rdhRestProxy.r204(tfObj.getType() + "MTC");
			if (featureTypeMTCExists) {
				rdhRestProxy.r138(tfObj, "MTC", chwA, pimsIdentity);
			}
			rdhRestProxy.r128(tfObj, thisRange, chwA, pimsIdentity);
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
		boolean t1ItemReturn = getItemReturn(t1TmfItem);
		boolean t2ItemReturn = getItemReturn(t2TmfItem);
		if (t1ItemReturn != t2ItemReturn) {
			abr.addDebug("isTypeFeatureChanged ItemReturn T1: " + t1ItemReturn + " T2: " + t2ItemReturn);
			return true;
		}
		boolean t1RemovalCharge = getRemovalCharge(t1TmfItem);
		boolean t2RemovalCharge = getRemovalCharge(t2TmfItem);
		if (t1RemovalCharge != t2RemovalCharge) {
			abr.addDebug("isTypeFeatureChanged RemovalCharge T1: " + t1RemovalCharge + " T2: " + t2RemovalCharge);
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
		List<EntityItem> t1FeaVct = getLinkedRelator(t1TmfItem, FEATURE);
		if (t1FeaVct.size() > 0) {
			t1FeaItem = t1FeaVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found FEATURE at T1 for " + t1TmfItem.getKey());
		}
		List<EntityItem> t2FeaVct = getLinkedRelator(t2TmfItem, FEATURE);
		if (t2FeaVct.size() > 0) {
			t2FeaItem = t2FeaVct.get(0);
		} else {
			throw new RfcAbrException("isTypeFeatureChanged not found FEATURE at T2 for " + t1TmfItem.getKey());
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

	private boolean isTypeFeatureGeoChanged(Vector t1AvailItemVct, EntityItem t2AvailItem) throws RfcAbrException {
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

	protected boolean isRPQ(EntityItem feaItem) {
		return !PokUtils.contains(feaItem, FEATURE_FCTYPE, FCTYPE_SET);
	}

	/**
	 * 1. if Featurecode is RPQ, then set range is 'RPQ' and set MACHTYPE.RPQRange =
	 * '1' 2. if featurecode is alphanumeric then if MACHTYPE.rangename is null set
	 * MACHTYPE.rangeName = 'A000', set range ='A000' else if MACHTYPE.rangename is
	 * not null if MACHTYPE.count <1000, set range = MACHTYPE.rangeName, set
	 * MACHTYPE.count = count +1 if MACHTYPE.count >=1000 update MACHTYPE.rangeName
	 * = MACHTYPE.rangeName + '1' clear MACHTYPE.count =0 set range =
	 * MACHTYPE.rangeName.
	 * 
	 * @param tfObj
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws RfcAbrException
	 */
	protected String calculateRange(EntityItem machTypeItem, EntityItem feaItem, TypeFeature tfObj)
			throws SQLException, MiddlewareException, RfcAbrException {
		String type = getAttributeValue(machTypeItem, MACHTYPE_MACHTYPEATR);
		String oldFeaTypeRange = getAttributeValue(feaItem, TYPERANGEL).trim();
		if (oldFeaTypeRange == null || "".equals(oldFeaTypeRange)) {
			oldFeaTypeRange = getAttributeValue(feaItem, FEATURE_TYPERANGE);
		}
		String range = "";
		if ("Q".equals(tfObj.getFeatureID())) { // RPQ
			range = "RPQ";
			setTextValue(MACHTYPE_RPQRANGE, MACHTYPE_RPQRANGE_YES, machTypeItem);
			abr.addDebug("calculateRange RPQ range");
		} else {
			if (isAlphaNumeric(tfObj.getFeature())) { // AlphaNumeric
				String rangeName = getAttributeValue(machTypeItem, MACHTYPE_RANGENAME);
				abr.addDebug("calculateRange AlphaNumeric rangeName " + rangeName);
				if (rangeName == null || "".equals(rangeName)) {
					range = "A000";
					setTextValue(MACHTYPE_RANGENAME, range, machTypeItem);
					setTextValue(MACHTYPE_FEATURECOUNT, "1", machTypeItem);
					setLongTextValue(TYPERANGEL, generateTypeRange(type, range, oldFeaTypeRange), feaItem);
				} else {
					range = rangeName;
					String feaCount = getAttributeValue(machTypeItem, MACHTYPE_FEATURECOUNT);
					int count = 0;
					try {
						count = Integer.valueOf(feaCount);
					} catch (NumberFormatException e) {
						abr.addDebug("calculateRange feaCount is not a number " + feaCount);
					}
					abr.addDebug("calculateRange feaCount " + feaCount);
					if (!isTypeRangeExistTheType(type, range, oldFeaTypeRange)) {
						if (count < 999) {
							setTextValue(MACHTYPE_FEATURECOUNT, String.valueOf(++count), machTypeItem);
							setLongTextValue(TYPERANGEL, generateTypeRange(type, range, oldFeaTypeRange), feaItem);
						} else {
							range = getRangePlusOne(rangeName);
							setTextValue(MACHTYPE_RANGENAME, range, machTypeItem);
							setTextValue(MACHTYPE_FEATURECOUNT, "1", machTypeItem);
							setLongTextValue(TYPERANGEL, generateTypeRange(type, range, oldFeaTypeRange), feaItem);
						}
					}
				}
			} else { // Number
				range = tfObj.getFeature().substring(0, 1) + "000";
			}
		}
		return range;
	}

	protected String getRangePlusOne(String rangeName) throws RfcAbrException {
		if (rangeName.length() == 4) {
			String firstLet = rangeName.substring(0, 1);
			String rangeNum = rangeName.substring(1, 4);
			int num = 0;
			try {
				num = Integer.valueOf(rangeNum) + 1;
				abr.addDebug("getRangePlusOne rangeNum " + rangeNum + " + 1 = " + num);
			} catch (NumberFormatException e) {
				abr.addDebug("getRangePlusOne last 3 letters are not all number. rangeNum " + rangeNum);
			}
			String newRange = String.valueOf(num);
			int addZeroCount = 3 - newRange.length();
			for (int i = 0; i < addZeroCount; i++) {
				newRange = "0" + newRange;
			}
			newRange = firstLet + newRange;
			abr.addDebug("getRangePlusOne newRange " + newRange);
			return newRange;
		} else {
			throw new RfcAbrException("getRangePlusOne lenght is not 4 of rangeName " + rangeName);
		}
	}

	protected boolean isAlphaNumeric(String str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isLetter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	protected String generateTypeRange(String type, String range, String oldTypeRanges) {
		abr.addDebug("generateTypeRange type " + type + " range " + range + " oldTypeRanges" + oldTypeRanges);
		if (isTypeRangeExistTheType(type, range, oldTypeRanges)) {
			return oldTypeRanges;
		} else {
			if ("".equals(oldTypeRanges)) {
				return type + TYPERANGE_SEPARATOR + range;
			} else {
				return oldTypeRanges + TYPERANGES_SEPARATOR + type + TYPERANGE_SEPARATOR + range;
			}
		}
	}

	protected boolean isTypeRangeExistTheType(String type, String range, String oldTypeRanges) {
		String[] typeRanges = oldTypeRanges.split(TYPERANGES_REX_SEPARATOR);
		for (String typeRange : typeRanges) {
			abr.addDebug("isTypeRangeExistTheType typeRange " + typeRange + " type " + type);
			String[] typeAndRange = typeRange.split(TYPERANGE_SEPARATOR);
			if (typeAndRange.length == 2) {
				if (typeAndRange[0].equals(type)&&typeAndRange[1].equals(range)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean getNoChargePurchase(EntityItem feaItem) throws RfcAbrException {
		boolean noChargePurchase = false;
		String pricedFeature = getAttributeValue(feaItem, FEATURE_PRICEDFEATURE);
		String zeroPrice = getAttributeValue(feaItem, FEATURE_ZEROPRICE);
		if ("No".equals(pricedFeature)) {
			noChargePurchase = true;
		} else if ("No".equals(zeroPrice)) {
			noChargePurchase = false;
		}
		abr.addDebug("getNoChargePurchase() PRICEDFEATURE " + pricedFeature + " ZEROPRICE " + zeroPrice
				+ " noChargePurchase " + noChargePurchase);
		return noChargePurchase;
	}

	/**
	 * applicable countries are the intersection of the MODEL鈥檚 AVAIL.COUNTRYLIST
	 * where AVAILTYPE = 鈥淧lanned Availability鈥� (146) and the FEATURE.COUNTRYLIST
	 * 
	 * @param mdlPlanAvailVct
	 * @param feaItem
	 * @return
	 * @throws RfcAbrException
	 */
	protected List<String> getCountryListForRPQ(Vector mdlPlanAvailVct, EntityItem feaItem) throws RfcAbrException {
		List<String> feaCountryList = getEntitiyAttributeValues(feaItem, "COUNTRYLIST", ATTR_MULTI_FLAG);
		abr.addDebug("getCountryListForRPQ " + feaItem.getKey() + " COUNTRYLIST size " + feaCountryList.size());
		List<String> mdlPlanAvailCountryList = new ArrayList<>();
		for (int i = 0; i < mdlPlanAvailVct.size(); i++) {
			EntityItem mdlPlanAvailItem = (EntityItem) mdlPlanAvailVct.get(i);
			List<String> countryList = getEntitiyAttributeValues(mdlPlanAvailItem, "COUNTRYLIST", ATTR_MULTI_FLAG);
			abr.addDebug(
					"getCountryListForRPQ " + mdlPlanAvailItem.getKey() + " COUNTRYLIST size " + countryList.size());
			mdlPlanAvailCountryList.addAll(countryList);
		}
		feaCountryList.retainAll(mdlPlanAvailCountryList);
		abr.addDebug("getCountryListForRPQ applicable COUNTRYLIST size " + feaCountryList.size() + " values "
				+ feaCountryList);
		return feaCountryList;
	}

	protected boolean isApprovalRPQ(EntityItem feaItem) throws RfcAbrException {
		String fcType = getAttributeFlagValue(feaItem, FEATURE_FCTYPE);
		abr.addDebug("isApprovalRPQ fcType" + fcType);
		if (FCTYPE_RPQ_ILISTED.equals(fcType)) {
			return true;
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

	// <ANNDATE>
	// 1.聽聽聽聽 OOFAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE for the AVAIL where
	// AVAILTYPE = 鈥淧lanned Availability鈥� (146).
	// Countries found in ANNOUNCEMENT.COUNTRYLIST.
	// 2.聽聽聽聽 PRODSTRUCT.ANNDATE
	// The applicable countries are the intersection of the MODEL鈥檚
	// AVAIL.COUNTRYLIST and the FEATURE.COUNTRYLIST.
	// 3.聽聽聽聽 IF PRODSTRUCT, THEN Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE}
	// The applicable countries are the intersection of the MODEL鈥檚
	// AVAIL.COUNTRYLIST and the FEATURE.COUNTRYLIST.
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
			annDate = getAttributeValue(tmfItem, ANNDATE);
		}
		if (annDate == null || "".equals(annDate)) {
			String mdlAnnDate = getAttributeValue(mdlItem, ANNDATE);
			String feaFirstAnnDate = getAttributeValue(feaItem, FIRSTANNDATE);
			if (mdlAnnDate.compareTo(feaFirstAnnDate) > 0) {
				annDate = mdlAnnDate;
			} else {
				annDate = feaFirstAnnDate;
			}
		}
		if (annDate == null || "".equals(annDate)) {
			throw new RfcAbrException("ANNDATE is null, it will not promote this PRODSTRUCT");
		}
		return annDate;
	}
}
