package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.*;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.HWPIMSException;
import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.*;
import com.ibm.transform.oim.eacm.util.PokUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

//$Log: RFCMODELABR.java,v $
//Revision 1.66  2019/07/09 12:09:41  dlwlan
//Story 1985938? Add withdraw process for caller 102 117.
//
//Revision 1.65  2019/04/03 12:23:45  xujianbo
//Rollback All the Unique ID Code
//
//Revision 1.60  2018/04/02 08:13:45  dlwlan
//Story 1830670-Revenue Profile issue
//
//Revision 1.59  2018/02/21 13:26:29  wangyul
//Defect 1815788 - 9009NEW urgent issue
//
//Revision 1.58  2018/01/17 13:05:48  wangyul
//Story 1796482: Fix MM_BTPRODUCTS field for RFCABR and caller of Z_DM_SAP_CLASSIFICATION_MAINT
//
//Revision 1.57  2017/08/04 09:41:38  wangyul
//Defect 1735504	 - RPQ TMF RFCABR support old MODEL without plan avail
//
//Revision 1.56  2017/08/01 13:35:16  wangyul
//Defect1734655 - Defect MODEL RFCABR created duplicate components of sales BOM in RDH
//
//Revision 1.55  2017/08/01 12:48:45  wangyul
//Defect 1733387 - MODEL RFCABR can not process error Material exists in Mast table but not defined to Stpo table
//
//Revision 1.54  2017/07/28 09:22:38  wangyul
//Work Item 1733074 - Promote full MACHTYPE when resend MODEL RFCABRSTATUS
//
//Revision 1.53  2017/06/01 12:31:14  wangyul
//Defect 1704595 EACM Prod: unable to resend model data
//
//Revision 1.52  2017/04/18 07:49:58  wangyul
//[Work Item 1681790] New: ESW - unique CLASS (range) and featurenaming support needed for CHW EACM HIPO materials
//
//Revision 1.51  2017/03/16 12:39:04  wangyul
//1642763: Tax support for US when product is set for ZA(south Africa)
//
//Revision 1.50  2017/01/23 06:37:15  wangyul
//[Work Item 1642728] CHW Simplification - Need to support Non RFA Avail while deriving the announcement dates
//
//Revision 1.49  2017/01/16 06:58:15  wangyul
//Defect 1634558 - a problem in RFCABR on how to check on return back from BOM read RFC to determine if a BOM exists or not
//
//Revision 1.48  2017/01/05 14:08:04  wangyul
//Story1635023 - OIMS CHW Simplification - RESEND function for RFC ABRs- MODEL,MODELCONVERT,TMFs
//
//Revision 1.47  2016/12/01 13:40:55  wangyul
//[Work Item 1627842] New: pre-existing parking records not being set to H status by EACM RFCABR
//
//Revision 1.46  2016/11/09 13:34:38  wangyul
//[Work Item 1617881] New: Configurable option to move parking table records to R status or leave them in H status
//
//Revision 1.45  2016/10/20 15:19:54  wangyul
//Defect 1610154	-R209  Material <283184E> exists in MARA table but not defined to MAKT table.
//
//Revision 1.44  2016/09/27 12:58:38  guobin
//fix Defect 1594193 MODEL failed to update BOM for TYPE+"MTC"
//
//Revision 1.43  2016/09/24 13:02:16  wangyul
//fix Defect 1597541 Profit center update
//
//Revision 1.42  2016/09/24 12:29:25  wangyul
//Defect1595141-Profit centers not updating all Plant codes.
//
//Revision 1.41  2016/09/24 12:20:56  wangyul
//Defect1595141-Profit centers not updating all Plant codes.
//
//Revision 1.40  2016/09/24 11:18:18  wangyul
//Defect1595141-Profit centers not updating all Plant codes.
//
//Revision 1.39  2016/09/24 03:33:16  guobin
//fix Defect 1597541 Profit center update
//
//Revision 1.38  2016/09/23 11:46:32  wangyul
//Defect1595141-Profit centers not updating all Plant codes.
//
//Revision 1.37  2016/09/20 12:41:58  wangyul
//defect1595143 - CPU flag is not getting updated
//
//Revision 1.36  2016/09/06 13:15:59  wangyul
//Defect1568133 - AUSP table has an extra attribute being generated than in production
//
//Revision 1.35  2016/08/30 12:33:07  wangyul
//defect 1585371 - parking records for zdmrelnum 2965_REV being left in Hold status
//
//Revision 1.34  2016/08/23 13:47:43  wangyul
//Defect 1580000 - 3573L2U ZDMNCH failure - value B1 not allowed for MARA-SPART
//
//Revision 1.33  2016/08/18 08:03:56  guobin
//fix issud in createAndMaintainBom
//
//Revision 1.32  2016/08/18 07:52:36  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date - isModelWithdrawChanged
//
//Revision 1.31  2016/08/17 09:11:32  guobin
//Fix issue in hasMatchComponent(), replace RevDate with DepDate
//
//Revision 1.30  2016/08/17 07:13:00  guobin
//comment out r143 , because there is no need to update Sale component  BOM when the Component exist in RDH STPO table .
//
//Revision 1.29  2016/08/17 07:06:11  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date
//
//Revision 1.28  2016/08/15 07:45:53  wangyul
//Defect 1568131 - MLAN table not populated like production MMLC
//
//Revision 1.27  2016/08/10 09:33:13  wangyul
//Move r144 to update parking table at last
//
//Revision 1.26  2016/08/10 08:43:12  wangyul
//1. Task 1574444 - RFCMODELABR update for r134 caller
//2. Move r144 to last
//
//Revision 1.25  2016/08/09 12:36:28  wangyul
//Replace INVNAME to TypeModel description value
//
//Revision 1.24  2016/08/04 07:35:32  wangyul
//replace chinese ? to :
//
//Revision 1.23  2016/08/02 08:10:58  guobin
//Fix [Work Item 1568085] MVKE-KTGRM - required field not populated in ZDMNCH IDOC [o] by set AAG value not null
//
//Revision 1.22  2016/07/29 13:45:47  wangyul
//material master for 5146GL2 missing mandatory MAKT segment - update flfilcd AAS to ZIP
//
//Revision 1.21  2016/07/27 15:53:01  wangyul
//Update extract LoadingGroup value
//
//Revision 1.20  2016/07/27 15:50:14  wangyul
//change the log for the RFCMODELABR.java
//
//Revision 1.19  2016/07/18 08:59:12  wangyul
//add the check for Update Revenue Profile BOM for ToType refer to 4.3.4
//
//Revision 1.18  2016/07/15 11:57:00  wangyul
//update check for isTMGeoChanged and isTMGeoPromoted
//
//Revision 1.17  2016/07/15 09:19:24  wangyul
//Story 1545764 - OIM Simplification- WDFM Announcement  promote - fix RFCMODEL and isProductHierarchyDifferent
//
//Revision 1.16  2016/07/14 09:17:19  wangyul
//add the method isRevProfOrAuoMtrlChanged to set isTMchanged
//
//Revision 1.15  2016/07/14 07:40:13  wangyul
//add the delta plant
//
//Revision 1.14  2016/07/12 08:02:23  wangyul
//update the method of the update revenue profile bom and sales bom
//
//Revision 1.13  2016/07/11 07:27:08  wangyul
//Story1545764 - OIM Simplification- WDFM Announcement  promote- Code promote
//
//Revision 1.12  2016/07/06 09:14:17  wangyul
//update the change  for RFCMODEL
//
//Revision 1.11  2016/06/27 07:49:32  wangyul
//change the code for the announcement promote
//
//Revision 1.10  2016/06/20 08:42:28  wangyul
//update the code for the  announcement promote in the type of model
//
//Revision 1.9  2016/06/20 07:58:12  wangyul
//update RFCMODELABR
//
//Revision 1.8  2016/06/17 10:42:53  wangyul
//update the code for the  announcement promote in the type of model
//
//Revision 1.7  2016/06/15 13:57:14  wangyul
//update the code for the  announcement promote in the type of model
//
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RFCMODELABR extends RfcAbrAdapter {
	
	private boolean isTMPromoted = false;
	private boolean isTMChanged = false;
	private boolean isTMGeoPromoted = false;
	private boolean isTMGeoChanged = false;
	private boolean needUpdateParkingTable = false;

	private Hashtable spItemCateg = new Hashtable();
	
	// only r118 use this value锛� and only xcc will set warrPeriod value, so set it to null
	// epims : if ((flfilcd != null && flfilcd.equalsIgnoreCase("XCC")) && (warrantyPeriod != null))
	private String warrPeriod = null;
	private Vector configProfTypes = new Vector();
	
	private static List<String> modelMarkChangedAttrs;
	
	static {
		modelMarkChangedAttrs = new ArrayList<>();
		modelMarkChangedAttrs.add(MODEL_INVNAME);
		modelMarkChangedAttrs.add(MODEL_INSTALL);
		modelMarkChangedAttrs.add(MODEL_LICNSINTERCD);
	}
	
	public RFCMODELABR(RFCABRSTATUS rfcAbrStatus)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, RemoteException, EANBusinessRuleException,
			IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}

	public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
		abr.addDebug("Start processThis()");
		EntityList t1EntityList = null;
		EntityItem modelItem = null;
		boolean isResendFull = false;
		try {
			// ----------------------- Get values from EACM entities -----------------------
			// MODEL
			modelItem = getRooEntityItem();
			// Check status
			if (!STATUS_FINAL.equals(getAttributeFlagValue(modelItem, ATTR_STATUS))) {
				throw new RfcAbrException("The status is not final, it will not promote this " + modelItem.getKey());
			}
			// AVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(modelItem, "MODELAVAIL", AVAIL);
			Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
			Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
			abr.addDebug("MODELAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size() + " Last Order avail " + lastOrderAvailVct.size());
			// MACHTYPE
			Vector machtypeVct = PokUtils.getAllLinkedEntities(modelItem, "MODELMACHINETYPEA", MACHTYPE);	
			if (machtypeVct.size() == 0) {
				throw new RfcAbrException("There is no MACHTYPE entity for " + modelItem.getKey());
			}		
			String machTypeValue = getAttributeFlagValue(modelItem, MODEL_MACHTYPEATR);	
			String modelAtr = getAttributeValue(modelItem, MODEL_MODELATR);
			String annDocNo = machTypeValue + STRING_SEPARATOR + modelAtr;
			// ----------------------- Create RFC input entities -----------------------
			// Type Model
			TypeModel typeModel = new TypeModel();
			typeModel.setType(machTypeValue);
			typeModel.setModel(modelAtr);
			String div = getDiv(modelItem);
			typeModel.setDiv(div);
			// This field stores the barcode equivalent of the UPC - Code. It is a unique valid Number assigned by Vienna, 
			// equivalent to EAN-Code in Europe and JAN-Code in Japan. This field can be left blank.
			typeModel.setEanUPCCode(""); 
			typeModel.setProductHierarchy(getProdHireCd(modelItem));
			typeModel.setDescription(getAttributeValue(modelItem, MODEL_INVNAME)); 
			// Re: blockers of CHW Promote Type Model logic 05/10/2016 12:27 AM
			// Set it to Null. Not being used in EACM
			typeModel.setVendorID("");
			typeModel.setAcctAsgnGrp(getAttributeFlagValue(modelItem, MODEL_ACCTASGNGRP));
			//String profitCenter = getAttributeFlagValue(modelItem, MODEL_PRFTCTR);
			String profitCenter = getProfitCenter(div);		
			typeModel.setProfitCenter(profitCenter);		
			/*
			 * TYPE_MODEL	CUSTOMERSETUP	Y	Y		This flag indicates whether the customer has to install the machine on his own or if a customer engineer installs the machine.  Input according to RFA.	MODEL.INSTALL
			 * CE means NO which means model is installed by IBM (customer set up = no) 
			 * : and CIF = yes = model installed by customer
			 * IF "CE" than =no and CIF =Yes 
			 */
			typeModel.setCustomerSetup("CIF".equals(getAttributeValue(modelItem, MODEL_INSTALL)));
			typeModel.setLicenseCode("Yes".equals(getAttributeValue(modelItem, MODEL_LICNSINTERCD))); 
			typeModel.setSystemType(getAttributeValue(modelItem, MODEL_SYSTEMTYPE));		
			typeModel.setCPU("S00010".equals(getAttributeFlagValue(modelItem, MODEL_SYSIDUNIT))); // When MODEL.SYSIDUNIT = "SIU - CPU"
			typeModel.setLoadingGroup(getLoadingGroup(modelItem)); //
			typeModel.setHasRevProfile(hasRevProfile(modelItem));
			typeModel.setRevProfile(getRevProfile(modelItem));
			abr.addDebug("TypeModel:" + typeModel.toString());
	
			// epims code, when both XCC and ZIP then BTH, when XCC then XCC, when ZIP then ZIP, when no value then EMPTY
			// we don't have XCC any more, so always set it to ZIP
			// but checked with Rupal we used AVAIL,ORDERSYSNAME as flfilcd, and always AAS
			String flfilcd = "ZIP";
			abr.addDebug("Flfilcd: " + flfilcd);

			// XCC=X锛� else C,銆�we don't have XCC so always C now
			String pimsIdentity = "C";
			abr.addDebug("PimsIdentity: " + pimsIdentity);
			abr.addDebug("WarrPeriod: " + warrPeriod);

			/*
			 * Mark MACHTYPE promoted, when there are more than 1 avail, this value
			 * will be set to true when the first promote done, avoid the MACHTYPE
			 * promote more than 1 time
			 */
//			boolean isTypePromoted = false;

			// ----------------------- Check Resend full -----------------------
			isResendFull = SYSFEEDRESEND_YES.equals(getAttributeFlagValue(modelItem, ATTR_SYSFEEDRESEND));
			abr.addDebug("Resend full: " + isResendFull);
			
			// ----------------------- Check TypeModel promoted or changed -----------------------
			AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(modelItem, RFCABRSTATUS);
			boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
			abr.addDebug("Passed RFCABRSTATUS exist before: " + isPassedAbrExist);
			EntityItem t1ModelItem = null;
			Vector deletedAuoMaterails = new Vector();
			if (!isResendFull && isPassedAbrExist) {
				isTMPromoted = true;
				String t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
				if (t1DTS == null || "".equals(t1DTS)) { // not found passed value, all value thought change
					abr.addDebug("t1DTS is null");
				} else {
					String t2DTS = abr.getCurrentTime();
					Profile profileT1 = abr.switchRole(ROLE_CODE);
		            profileT1.setValOnEffOn(t1DTS, t1DTS);
		            profileT1.setEndOfDay(t2DTS); 
		            profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
		            profileT1.setLoginTime(t2DTS);
		            abr.addDebug("Get t1 entity list for t1DTS: " + t1DTS + " t2DTS: " + t2DTS);
		            t1EntityList = getEntityList(profileT1);
		            abr.addDebug("EntityList for T1 " + profileT1.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + PokUtils.outputList(t1EntityList));
		            t1ModelItem = t1EntityList.getParentEntityGroup().getEntityItem(0);
		            
		            Vector t1AuoMaterials = getRevProfile(t1ModelItem).getAuoMaterials();
		            deletedAuoMaterails = getDeletedAuoMaterials(t1AuoMaterials, typeModel.getRevProfile().getAuoMaterials());
		            abr.addDebug("Delete AUO Materials size:" + deletedAuoMaterails.size());
		            
		            isTMChanged = isTypeModelChanged(t1ModelItem, modelItem);
				}			
			}
			
			// SAP Ledger
			boolean isProfitCenterChanged = false;
			if (t1ModelItem != null) {					
				isProfitCenterChanged = isProfitCenterChanged(t1ModelItem, modelItem);							
			} else {
				isProfitCenterChanged = true;
				abr.addDebug("checkAndUpdateProfitCenter T1 model item is null");
			}
			abr.addDebug("isProfitCenterChanged: " + isProfitCenterChanged);
			
			//if RevProfile is existed
			boolean isRevProfOrAuoChanged = false;
			if (t1ModelItem != null ) {					
				isRevProfOrAuoChanged = isRevProfOrAuoMtrlChanged(t1ModelItem, modelItem);							
			} else {
				isRevProfOrAuoChanged = true;
			}
			abr.addDebug("isRevProfOrAuoChanged: " + isRevProfOrAuoChanged);
			
			if (planAvailVct.size() > 0) {
				// ----------------------- Promote Type Model for each AVAIL -----------------------
				for (int i = 0; i < planAvailVct.size(); i++) {
					EntityItem availItem = (EntityItem)planAvailVct.get(i);
					abr.addDebug("Promote MODEL for " + availItem.getKey());

					// Get all salesorg and plants from GENERALAREA linked to AVAIL
					Vector t2GaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(t2GaVct);
					Set<String> plants = getAllPlant(salesOrgPlantList);
					if (plants.size() == 0) {
						abr.addDebug("There is no plant, bypass " + availItem.getKey());
						continue;
					}
					Set<String> deltaPlants = new HashSet<String>();
					// ----------------------- Check TypeModelGeo promoted or changed -----------------------
					if (isTMPromoted) {
						if (t1EntityList != null && t1ModelItem != null) {
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1ModelItem, "MODELAVAIL", AVAIL);
							Vector t1PlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
							
							isTMGeoChanged = isTypeModelGeoChanged(t1PlanAvailVct, availItem, deltaPlants);
							List<String> t2NewCountries = getNewCountries(t1PlanAvailVct, availItem);
							isTMGeoPromoted = t2NewCountries.size() == 0;
							
							if (!isTMGeoChanged && !isTMGeoPromoted) {								
								salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList, t2NewCountries);						
							}
						} else {
							abr.addDebug("t1EntityList not null:" + (t1EntityList != null) + " t1ModelItem not null:" + (t1ModelItem != null));
						}						
					} else {
						isTMGeoPromoted = false;
					}
					abr.addDebug("isTMPromoted: " + isTMPromoted + " isTMChanged: " + isTMChanged + " isTMGeoPromoted: " + isTMGeoPromoted + " isTMGeoChanged: " + isTMGeoChanged);
					
					// ----------------------- Create RFC input entity CHWAnnouncement and CHWGeoAnn -----------------------
					CHWAnnouncement chwA = new CHWAnnouncement();
					chwA.setAnnDocNo(typeModel.getType()); // Set to 鈥楳ACHTYPE鈥� when first TYPE promote ; set to 鈥淢ACHTYPE|MODELATR when MODEL_TYPE promote
					chwA.setDiv(div);
					chwA.setSegmentAcronym(getSegmentAcronymForAnn(modelItem));

					CHWGeoAnn chwAg = new CHWGeoAnn();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// <ANNDATE>
					// The first applicable / available date is used
					// 1.聽聽聽聽 MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
					// 2.聽聽聽聽 MODEL.ANNDATE
					Vector annVect = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", ANNOUNCEMENT);
					if (annVect != null && annVect.size() > 0) {
						EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
						chwA.setAnnouncementType(getAttributeValue(annItem, ANNOUNCEMENT_ANNTYPE));
						chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, ANNDATE)));
					} else {
						chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
						chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(modelItem, ANNDATE)));
					}
					abr.addDebug("CHWAnnouncement: " + chwA.toString());
					abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

					// Promote Type
					long startTime = System.currentTimeMillis();
					promoteType(isTMPromoted, isResendFull, machtypeVct,
							typeModel, chwA, chwAg, pimsIdentity, flfilcd,
							salesOrgPlantList, plants, t2GaVct);
					abr.addDebug("promote type took " + Stopwatch.format(System.currentTimeMillis() - startTime));

					// Promote TypeModel
					startTime = System.currentTimeMillis();
					promoteTypeModel(isProfitCenterChanged,
							isRevProfOrAuoChanged, typeModel, chwA, chwAg,
							salesOrgPlantList, plants, pimsIdentity, flfilcd,
							t2GaVct, deltaPlants, deletedAuoMaterails);
					abr.addDebug("promote type model took " + Stopwatch.format(System.currentTimeMillis() - startTime));
					
				} // End plan avail
			} else {
				// no plan avail, country for WW
				Vector gaVct = searchAllGeneralAreas();
				List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(gaVct);
				Set<String> plants = getAllPlant(salesOrgPlantList);
				if (plants.size() > 0) {
					// ----------------------- Check TypeModelGeo promoted or changed -----------------------
					// always send full for GEO
					Set<String> deltaPlants = plants;
					isTMGeoPromoted = false;
					isTMGeoChanged = true;
					abr.addDebug("isTMPromoted: " + isTMPromoted + " isTMChanged: " + isTMChanged + " isTMGeoPromoted: " + isTMGeoPromoted + " isTMGeoChanged: " + isTMGeoChanged);

					// ----------------------- Create RFC input entity CHWAnnouncement and CHWGeoAnn -----------------------
					CHWAnnouncement chwA = new CHWAnnouncement();
					chwA.setAnnDocNo(typeModel.getType()); // Set to 鈥楳ACHTYPE鈥� when first TYPE promote ; set to 鈥淢ACHTYPE|MODELATR when MODEL_TYPE promote
					chwA.setDiv(div);
					chwA.setSegmentAcronym(getSegmentAcronymForAnn(modelItem));

					CHWGeoAnn chwAg = new CHWGeoAnn();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// MODEL.ANNDATE
					chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
					chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(modelItem, ANNDATE)));
					abr.addDebug("CHWAnnouncement: " + chwA.toString());
					abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());

					// Promote Type
					long startTime = System.currentTimeMillis();
					promoteType(isTMPromoted, isResendFull, machtypeVct,
							typeModel, chwA, chwAg, pimsIdentity, flfilcd,
							salesOrgPlantList, plants, gaVct);
					abr.addDebug("promote type took " + Stopwatch.format(System.currentTimeMillis() - startTime));

					// Promote TypeModel
					startTime = System.currentTimeMillis();
					promoteTypeModel(isProfitCenterChanged,
							isRevProfOrAuoChanged, typeModel, chwA, chwAg,
							salesOrgPlantList, plants, pimsIdentity, flfilcd,
							gaVct, deltaPlants, deletedAuoMaterails);
					abr.addDebug("promote type model took " + Stopwatch.format(System.currentTimeMillis() - startTime));

					// Promote WDFM Announcement
					String wthdrwEffctvDate = getAttributeValue(modelItem, WTHDRWEFFCTVDATE);
					abr.addDebug("MODEL.WTHDRWEFFCTVDATE " + wthdrwEffctvDate);
					if (wthdrwEffctvDate != null && !"".equals(wthdrwEffctvDate.trim())) {
						Date wdfmDate = sdf.parse(wthdrwEffctvDate);						
						promoteWDFMAnnouncement(wdfmDate, typeModel, salesOrgPlantList, annDocNo, pimsIdentity, flfilcd, chwA, chwAg);
					}

				}
			}
			
			
			// Promote WDFM Announcement for lastorder avail
			boolean isTmwPromoted = false;
			boolean isTmwChanged = false;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo(typeModel.getType()); // Set to ‘MACHTYPE’ when first TYPE promote ; set to “MACHTYPE|MODELATR when MODEL_TYPE promote
			chwA.setDiv(div);
			chwA.setSegmentAcronym(getSegmentAcronymForAnn(modelItem));

			CHWGeoAnn chwAg = new CHWGeoAnn();			
			// MODEL.ANNDATE
			chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
			chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(modelItem, ANNDATE)));
			abr.addDebug("CHWAnnouncement: " + chwA.toString());
			abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
	
			if (lastOrderAvailVct.size() > 0) {
				for (int i = 0; i < lastOrderAvailVct.size(); i++) {
					EntityItem lastOrderAvailItem = (EntityItem)lastOrderAvailVct.get(i);
					abr.addDebug("Promote WDFM Announcement for " + lastOrderAvailItem.getKey());
					Date wdfmDate = sdf.parse(getAttributeValue(lastOrderAvailItem, AVAIL_EFFECTIVEDATE));
					
					Vector generalareaVct = PokUtils.getAllLinkedEntities(lastOrderAvailItem, "AVAILGAA", GENERALAREA);
					List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalareaVct);
					
					if (!isResendFull && isPassedAbrExist) {
						if (t1EntityList != null && t1ModelItem != null) {
							Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1ModelItem, "MODELAVAIL", AVAIL);
							Vector t1LastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
							isTmwChanged = isTypeModelWithdrawChanged(t1LastOrderAvailVct, lastOrderAvailItem);
							List<String> t2NewCountries = getNewCountries(t1LastOrderAvailVct, lastOrderAvailItem);
							isTmwPromoted = t2NewCountries.size() == 0;			
							if (!isTmwChanged && !isTmwPromoted) {							
								salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList, t2NewCountries);							
							}					
						}
					}
					abr.addDebug("isTmwPromoted: " + isTmwPromoted + " isTmwChanged: " + isTmwChanged);	

					if (!isTmwPromoted || isTmwChanged) {
						chwAg.setAnnouncementDate(wdfmDate);
						abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
						promoteWDFMAnnouncement(wdfmDate, typeModel, salesOrgPlantList, annDocNo, pimsIdentity, flfilcd, chwA, chwAg);									
					}
				}
			} 

			if (needReleaseParkingTable()) {
				rdhRestProxy.r144(annDocNo, "R", pimsIdentity);
				abr.addOutput("[R144] Update park status R");
			} else {
				rdhRestProxy.r144(annDocNo, "H", pimsIdentity);
				abr.addOutput("[R144] Update park status H");
			}
		} finally {
			// Set SYSFEEDRESEND to No
			if (isResendFull && modelItem != null) {
				setFlagValue(ATTR_SYSFEEDRESEND, SYSFEEDRESEND_NO, modelItem);
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
	}

	/**
	 * Promote machtype
	 * 
	 * @param isTMPromoted - is model RFCABRSTATUS passed before
	 * @param isResendFull - is resend action
	 * @param machtypeVct - MACHTYPE entities for machtypeatr of MODEL
	 * @param typeModel
	 * @param chwA
	 * @param chwAg
	 * @param pimsIdentity
	 * @param flfilcd
	 * @param salesOrgPlantList
	 * @param plants - Set<String>
	 * @param t2GaVct - GENERALAREA vector
	 * @throws RfcAbrException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws Exception
	 */
	private void promoteType(boolean isTMPromoted, boolean isResendFull,
			Vector machtypeVct, TypeModel typeModel, CHWAnnouncement chwA,
			CHWGeoAnn chwAg, String pimsIdentity, String flfilcd,
			List<SalesOrgPlants> salesOrgPlantList, Set<String> plants,
			Vector t2GaVct) throws RfcAbrException, SQLException,
			MiddlewareException, Exception {
		abr.addOutputHeader("Promote Type " + typeModel.getType() + ":");
		/**
		 * 1. if MODEL RFCABRSTATUS didn't pass before, promote full type
		 * 2. or if resend MODEL RFCABRSTATUS, promote full type
		 * 3. or if MACHTYPE entities has PROMOTED equal Yes, promote full type
		 * 4. else promote delta type
		 */
		if (!isTMPromoted || isResendFull || !isTypePromoted(machtypeVct)) {
			abr.addDebug("Start Type:" + typeModel.getType() + " full promoted");

			rdhRestProxy.r100(chwA, typeModel, chwAg, "NEW", null, null, pimsIdentity);
			rdhRestProxy.r100(chwA, typeModel, chwAg, "UPG", null, null, pimsIdentity);
			abr.addOutput("[R100] Create basic view for type NEW/UPG material");
			
			abr.addDebug("R101 Plant size:" + plants.size());
			for (String plantValue : plants) {
				rdhRestProxy.r101(chwA, typeModel, chwAg, "NEW", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //NEW
				rdhRestProxy.r101(chwA, typeModel, chwAg, "UPG", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //UPG
				abr.addDebug("[R101] Create generic plant " + plantValue + " view for type NEW/UPG material");
			}
			
			// Extra Plants (CFI Plants ) reading from Properties File
			String extraplant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true); // WERKS Plant
			StringTokenizer stoken = new StringTokenizer(extraplant, ",");
			// For each CFI Plant
			while (stoken.hasMoreElements()) {
				String sPlant = stoken.nextToken();
				rdhRestProxy.r189(chwA, typeModel, sPlant, "NEW", null, null, pimsIdentity, chwAg);
				rdhRestProxy.r189(chwA, typeModel, sPlant, "UPG", null, null, pimsIdentity, chwAg);
				abr.addOutput("[R189] Create CFI plant " + sPlant + " view for type NEW/UPG material");
			}					
			
			abr.addDebug("R102 SalesOrgPlants size: " + salesOrgPlantList.size());
			for (SalesOrgPlants salesorgPlants : salesOrgPlantList) {
				String salesOrg = salesorgPlants.getSalesorg();
				Vector vectTaxList = getTaxListBySalesOrgPlants(salesorgPlants);
				Vector<String> _plants = salesorgPlants.getPlants();
				if (_plants != null && _plants.size() > 0) {
					for (String _plant : _plants) {
						if(_plant.equals("1999")){
							abr.addDebug("Skip plant for 1999");
							continue;
						}
						rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAg, null); //NEW
						rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAg, null); //UPG
						abr.addOutput("[R102] Create sales " + salesOrg + " view for plant " + _plant + " for type NEW/UPG material");
					}
				} else {
					abr.addDebug("R102 no plant for salesorg:" + salesOrg);
				}
			}					
			// 1642763: Tax support for US when product is set for ZA(south Africa) 
			if (isGENERALAREAContainsZAButNoUS(t2GaVct)) {
				processTaxSupportForUSWhenProductIsSetForZA(typeModel, chwA, chwAg, pimsIdentity, flfilcd, false, null, null, null, null);
			}
			
			rdhRestProxy.r103(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
			rdhRestProxy.r103(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
			abr.addOutput("[R103] Create 001 classification for MG_COMMON for type NEW/UPG material");
			rdhRestProxy.r104(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
			rdhRestProxy.r104(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
			abr.addOutput("[R104] Create ZDM classification for type NEW/UPG material");
			rdhRestProxy.r106(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R106] Create type models class");
			
			// For each class range (0000, 1000 , . . . 9000)
			for (int fRanges = 0; fRanges <= 9; fRanges++) {
				String featRanges = fRanges + "000";
				String type = typeModel.getType();
				String model = typeModel.getModel();
				rdhRestProxy.r130(type, model, featRanges, chwA, pimsIdentity);
				abr.addOutput("[R130] Create type FEAT range " + featRanges + " class");
				rdhRestProxy.r176(type, model, featRanges, "NEW", chwA, pimsIdentity);
				rdhRestProxy.r176(type, model, featRanges, "UPG", chwA, pimsIdentity);
				abr.addOutput("[R176] Create 300 classification for type FEAT for type NEW/UPG material for range " + featRanges);

				// HIPO don't need UF classes
				if (isHIPOModel(type, model)) {
					abr.addDebug("HIPO model don't need to call R131 for range " + featRanges);
				} else {
					rdhRestProxy.r131(type, featRanges, chwA, pimsIdentity);
					abr.addOutput("[R131] Create type UF class for range " + featRanges);
					rdhRestProxy.r177(type, featRanges, chwA, pimsIdentity);
					abr.addOutput("[R177] Create 300 classification for type UF for UPG for range " + featRanges);
				}
				
				// Insert Type/Feature class row in PROMOTED_TYPECLASS (delayed)
				// type not promoted, save type and range to table
				// epims: ptypClassCol PromotedTypeClassCollection save promoted type, no more handler. we use promoted attribute of MACHTYPE
				// epims: nowPromotedTypeClasses will insert result to PROMOTED_TYPECLASS, we use promoted attribute of MACHTYPE
				// End of type not promoted will call setPromotedMachtypes(machtypeVct) to update the promoted attribute of MACHTYPE
				
			} // end for For loop
			// Defect 1815788 - 9009NEW urgent issue
			rdhRestProxy.r123(typeModel, null, "NEW", chwA, null, pimsIdentity);
			rdhRestProxy.r123(typeModel, null, "UPG", chwA, null, pimsIdentity);
			abr.addOutput("[R123] Create 300 classification for type models for NEW/UPG");
			rdhRestProxy.r108(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R108] Create type MOD characteristic");
			rdhRestProxy.r110(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R110] Assign MOD characteristic to models class");
			rdhRestProxy.r150(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R150] Create 012 classification for MOD");
			rdhRestProxy.r160(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R160] Assign char to class FEAT_0000");
//			rdhRestProxy.r175(typeModel, null, chwA, "NEW", null, pimsIdentity);
//			rdhRestProxy.r175(typeModel, null, chwA, "UPG", null, pimsIdentity);
//			abr.addDebug("Call R175 NEW and UPG successfully");

			// Set MACHTYPE PROMOTED attribute value to Yes
			setPromotedMachtypes(machtypeVct);
			
			if (needReleaseParkingTable()) {
				rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
				abr.addOutput("[R144] Update park status R");
			} else {
				rdhRestProxy.r144(chwA.getAnnDocNo(), "H", pimsIdentity);
				abr.addOutput("[R144] Update park status H");
			}

			abr.addDebug("End Type:" + typeModel.getType() + " full promoted");
			// End if Type full promoted
		} else {
			abr.addDebug("Start Type:" + typeModel.getType() + " already promoted");
			// each TypeModelGeo
			if (!isTMGeoPromoted) {
				boolean needUpdateParkingTableForType = false;
				abr.addDebug("R102 SalesOrgPlants size: " + salesOrgPlantList.size());
				for (SalesOrgPlants salesorgPlants : salesOrgPlantList) {
					String salesOrg = salesorgPlants.getSalesorg();
					Vector vectTaxList = getTaxListBySalesOrgPlants(salesorgPlants);																				
					Vector<String> _plants = salesorgPlants.getPlants();
					if (_plants != null && _plants.size() > 0) {
						for (String _plant : _plants) {
							if(_plant.equals("1999")){
								abr.addDebug("Skip plant for 1999");
								continue;
							}
							rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAg, null); //NEW
							rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAg, null); //UPG
							abr.addOutput("[R102] Create sales " + salesOrg + " view for type NEW/UPG material");
							if (!needUpdateParkingTableForType) {
								needUpdateParkingTableForType = true;
								abr.addDebug("Set needUpdateParkingTableForType to " + needUpdateParkingTableForType);
							}									
						}
					} else {
						abr.addDebug("R102 no plant for salesorg:" + salesOrg);
					}
				}
				// 1642763: Tax support for US when product is set for ZA(south Africa) 
				if (isGENERALAREAContainsZAButNoUS(t2GaVct)) {
					processTaxSupportForUSWhenProductIsSetForZA(typeModel, chwA, chwAg, pimsIdentity, flfilcd, false, null, null, null, null);
				}
				if (needUpdateParkingTableForType) {
					if (needReleaseParkingTable()) {
						rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
						abr.addOutput("[R144] Update park status R");
					} else {
						rdhRestProxy.r144(chwA.getAnnDocNo(), "H", pimsIdentity);
						abr.addOutput("[R144] Update park status H");
					}
				}
			} else {
				abr.addDebug("isTMGeoPromoted is: " + isTMGeoPromoted + ", will not call r102");
			}						
			abr.addDebug("End Type:" + typeModel.getType() + " already promoted");
		}				
	}

	private void promoteTypeModel(boolean isProfitCenterChanged,
			boolean isRevProfOrAuoChanged, TypeModel typeModel,
			CHWAnnouncement chwA, CHWGeoAnn chwAg,
			List<SalesOrgPlants> salesOrgPlantList, Set<String> plants,
			String pimsIdentity, String flfilcd, Vector t2GaVct,
			Set<String> deltaPlants, Vector deletedAuoMaterails)
			throws Exception {
		abr.addOutputHeader("Promote TypeModel " + typeModel.getType() + typeModel.getModel() + ":");
		// ----------------------- Type Model Promote -----------------------
		chwA.setAnnDocNo(typeModel.getType() + STRING_SEPARATOR + typeModel.getModel());
		abr.addDebug("Set chwA annDocNo: " + chwA.getAnnDocNo());
		SharedProductComponents spComponents = new SharedProductComponents();
		PlannedSalesStatus ps = new PlannedSalesStatus();
		
		String material = typeModel.getType() + typeModel.getModel();
		BasicMaterialFromSAP basicMaterialFromSAP = null;
		try {
			basicMaterialFromSAP = rdhRestProxy.r209(material);
			abr.addDebug("Call R209 successfully, Read Basic View of Material from RDH, BasicMaterialFromSAP: MatlType=" + basicMaterialFromSAP.getMatlType() + " prodHierarchy=" + basicMaterialFromSAP.getProdHier());
		} catch (HWPIMSAbnormalException e) {
			abr.addDebug("Get exception: " + e.getMessage() + " from RFC, create a empty BasicMaterialFromSAP");
			basicMaterialFromSAP = new BasicMaterialFromSAP();
			basicMaterialFromSAP.setMatlType(null);
			basicMaterialFromSAP.setProdHier(null);
		}
		abr.addOutput("[R209] Read basic view of material");
		String materialType = basicMaterialFromSAP.getMatlType();
		String productHierarchy = typeModel.getProductHierarchy();
		abr.addDebug("ProductHierarchy from EACM is " + productHierarchy);
		if ((materialType != null && !materialType.equals("")) && (isProductHierarchyDifferent(productHierarchy, basicMaterialFromSAP.getProdHier()))) {
			abr.addDebug("Material exists and ProductHierarchy is different");
			Vector supportedSalesOrgV = getSupportedSalesOrgCol(salesOrgPlantList);
			abr.addDebug("All supported salesOrg size:" + supportedSalesOrgV.size() + " values:" + supportedSalesOrgV);
			callR260updateProdHierarchyOnSalesView(supportedSalesOrgV, typeModel, chwA, productHierarchy, pimsIdentity, chwAg, typeModel.getAcctAsgnGrp());
		} else {
			abr.addDebug("R260 will not be called");
		}
		
		// Check shared product
		if (!isTMGeoPromoted || isTMGeoChanged) {
			abr.addDebug("Type Model Promote : Check shared product");
			checkSharedProductInfo(typeModel.getType(), typeModel.getModel(), chwA.getAnnDocNo(), spComponents, pimsIdentity, "", false, materialType);
		}

		// ----------------------- Type Model Promote : TypeModel is new -----------------------
		if (!isTMPromoted) {
			abr.addDebug("Start Type Model Promote : TypeModel not promoted");
			if (!spComponents.getSharedProduct()) {
				abr.addDebug("R115 plant size: " + plants.size());
				for (String plantValue : plants) {
					rdhRestProxy.r115(chwA, typeModel, chwAg, pimsIdentity, plantValue );
					abr.addOutput("[R115] Create Type/Model material basic view for plant " + plantValue);
				}
			}
			
			// epims : spItemCateg
			if (spComponents.getSharedProduct() && spComponents.getSharedProductMaterialType().equalsIgnoreCase("ZSEL")) {
				String spmat = typeModel.getType() + typeModel.getModel();
				if (!(spItemCateg.containsKey(spmat))) {
					spItemCateg.put(spmat, "spitem_cat");
				}
			}
			
			abr.addDebug("R116 plant size: " + plants.size());
			for (String _plant : plants) {
				rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW1", pimsIdentity);
				abr.addOutput("[R116] Create CHW1 generic plant " + _plant + " view for Type/Model material");
				if (_plant.equals("1999")) {
					abr.addDebug("Skip plant for 1999");
					continue;	
				}
				rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW2", pimsIdentity);
				abr.addOutput("[R116] Create CHW2 generic plant " + _plant + " view for Type/Model material");
			}
			// For More Plants .
			// Extra Plants (CFI Plants ) reading from Properties File .
			String extraplant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true);
			StringTokenizer stoken = new StringTokenizer(extraplant, ",");
			while (stoken.hasMoreElements()) {
				String sPlant = stoken.nextToken();
				boolean plantcheck = false;
//				if (spComponents.getSharedProduct()) {
//					plantcheck = rdhRestProxy.r207(typeModel.getType(), typeModel.getModel(), sPlant);
//					abr.addDebug("Call R207 successfully for Extra plant:" + sPlant);
//				}
				// SAP Ledger
				if ((!spComponents.getSharedProduct()) || ((spComponents.getSharedProduct()) && plantcheck == false)) {
					rdhRestProxy.r183(chwA.getAnnDocNo(), typeModel.getType() + typeModel.getModel(), sPlant, pimsIdentity, typeModel.getProfitCenter(), chwAg, typeModel.getDiv());
					abr.addOutput("[R183] Create CFI plant " + sPlant + " view for Type/Model material");
				}
			}
			rdhRestProxy.r156(typeModel.getType() + typeModel.getModel(), typeModel.getDiv(), chwA, pimsIdentity, false);
			abr.addOutput("[R156] Create ZDM classification for Type/Model material");
			
			// for each tmGeo
			abr.addDebug("salesorgPlantsVect size: " + salesOrgPlantList.size());
			ps = checkForNewPlannedSalesStatus(chwAg.getAnnouncementDate(), false); 
			// 1642763: Tax support for US when product is set for ZA(south Africa) 
			if (isGENERALAREAContainsZAButNoUS(t2GaVct)) {
				processTaxSupportForUSWhenProductIsSetForZA(typeModel, chwA, chwAg, pimsIdentity, flfilcd, false, null, null, "r117", ps);
			}
			for (SalesOrgPlants salesorgPlants : salesOrgPlantList) {
				String salesOrg = salesorgPlants.getSalesorg();
				if ((salesOrg.equalsIgnoreCase("0147") && (!spComponents.getSharedProductIn0147()))|| !salesOrg.equalsIgnoreCase("0147")) {
					abr.addDebug("salesOrg equals 0147 and (spComponents not in 0147 or salesOrg not equals 0147)");
					Vector vectTaxList = getTaxListBySalesOrgPlants(salesorgPlants);								
					Vector<String> _plants = salesorgPlants.getPlants();										
					if (_plants != null && _plants.size() > 0) {
						for (String plantValue : _plants) {
							if (plantValue.equals("1999")) {
								abr.addDebug("Skip plant for 1999");
								continue;
							}
							rdhRestProxy.r117(chwA, typeModel.getType()+ typeModel.getModel(), typeModel.getDiv(), typeModel.getAcctAsgnGrp(), ps,true, pimsIdentity, flfilcd, salesOrg, typeModel.getProductHierarchy(),	vectTaxList, plantValue, chwAg);
							abr.addOutput("[R177] Create sales " + salesOrg + " view for Type/Model material");
						}
					} else {
						abr.addDebug("Not call R117 no plant for salesOrg: " + salesOrg);
					}						
				}
				LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(typeModel);
				Date annDate = chwAg.getAnnouncementDate(); // epims: tmGeoObj.getAnnouncementDate(); confirmed with Bin we use ANNDATE of ANNOUNCEMENT
				this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), annDate, chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
				abr.addDebug("updateAnnLifecyle successfully for salesOrg: " + salesOrg);
			}
				// ipims : promotedTMGs, it will update TYPE_MODEL_GEO. we use t1 and t2 to handle if promoted
			 // End each tmGeo		
					
			if (spComponents.getSharedProduct()) {
				rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, true, pimsIdentity);
				abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
				rdhRestProxy.r119(typeModel.getType() + typeModel.getModel(), chwA, true, true, pimsIdentity);
				abr.addOutput("[R119] Create 001 classification for MG_COMMON for Type/Model material");
			} else {
				rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, false, pimsIdentity);
				abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
				rdhRestProxy.r119(typeModel.getType() + typeModel.getModel(), chwA, false, true, pimsIdentity);
				abr.addOutput("[R119] Create 001 classification for MG_COMMON for Type/Model material");
			}

			rdhRestProxy.r120(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R120] Maintain model value for type MOD characteristic");
			rdhRestProxy.r121(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R121] Create model selection dependency");

			//4.11  luis done  r262					
			// epims : typeModelChgd, it will update TYPE_MODEL SET CHANGED=0, we use t1 and t2 to handle the change		

			// SAP Ledger
			if (ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SAP_LEDGER).equals("Y")) {
				//boolean isProfitCenterChanged = false;
//				if (t1ModelItem != null) {
//					String t1ProfitCenter = getAttributeFlagValue(t1ModelItem, MODEL_PRFTCTR);
//					if (!profitCenter.equals(t1ProfitCenter)) {
//						abr.addDebug("checkAndUpdateProfitCenter T1 ProfitCenter " + t1ProfitCenter + " T2 " + profitCenter);
//						isProfitCenterChanged = true;
//					}
//				} else {
//					isProfitCenterChanged = true;
//					abr.addDebug("checkAndUpdateProfitCenter T1 model item is null");
//				}
//				abr.addDebug("checkAndUpdateProfitCenter isProfitCenterChanged: " + isProfitCenterChanged);
//				MaterailPlantData matplantData = new MaterailPlantData();
//				if (isProfitCenterChanged) {
				abr.addDebug("Start check and update profit center");
				for (String plant : plants) {
					rdhRestProxy.r262(chwA, material, plant, pimsIdentity, typeModel.getProfitCenter(), chwAg);
				}							
				abr.addDebug("End check and update profit center");
//				}						
//				checkAndUpdateProfitCenter(matplantData, typeModel.getType() + typeModel.getModel(), pimsIdentity, typeModel.getProfitCenter(), chwA);						
			}
			
			//4.12 Create Configuration Profile for this MachType  R148 R149
			configProfTypes.addElement(typeModel.getType());
			createConfigurationProfiles(configProfTypes, chwA, pimsIdentity);
			
			
			abr.addDebug("End Type Model Promote : TypeModel not promoted");
		}// End for the if condition Type model is not promoted in any
		  // geo's and not promoted in any other announcements
		else {
			abr.addDebug("The MODEL already promoted");
		}
		// ----------------------- Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted -----------------------
		if (isTMChanged || isTMGeoChanged || (isTMPromoted && !isTMGeoPromoted)) {
			abr.addDebug("Start Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted");
			// each TypeModelGeo
			if (isTMGeoChanged || !isTMGeoPromoted) {
				// epims: changedTMGs promotedTMGs		
				abr.addDebug("salesorgPlantsVect size: " + salesOrgPlantList.size());
				for (SalesOrgPlants salesorgPlants : salesOrgPlantList) {
					String salesOrg = salesorgPlants.getSalesorg();
					LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(typeModel);
					Date annDate = chwAg.getAnnouncementDate(); // epims: tmGeoObj.getAnnouncementDate(); confirmed with Bin we use ANNDATE of ANNOUNCEMENT
					this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen .getMaterial(), annDate, chwA.getAnnDocNo(), chwA .getAnnouncementType(), pimsIdentity, salesOrg);
					abr.addDebug("updateAnnLifecyle successfully for salesOrg: " + salesOrg);
				}// End each TypeModelGeo						
			}
			
			abr.addDebug("R116 plant size: " + plants.size());
			for (String _plant : plants) {
				rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW1", pimsIdentity);
				abr.addOutput("[R116] Create CHW1 generic plant " + _plant + " view for Type/Model material");
				if (_plant.equals("1999")) {
					abr.addDebug("Skip plant for 1999");
					continue;
				}
				rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW2", pimsIdentity);
				abr.addOutput("[R116] Create CHW2 generic plant " + _plant + " view for Type/Model material");
			}
			
			rdhRestProxy.r156(typeModel.getType() + typeModel.getModel(), typeModel.getDiv(), chwA, pimsIdentity, false);
			abr.addOutput("[R156] Create ZDM classification for Type/Model material");
			
			abr.addDebug("checkSharedProductInfo");
			checkSharedProductInfo(typeModel.getType(), typeModel.getModel(), chwA.getAnnDocNo(), spComponents, pimsIdentity, "", false, basicMaterialFromSAP.getMatlType());
			
			if (!(spComponents.getSharedProduct())) {
				rdhRestProxy.r133(typeModel, chwA, pimsIdentity, chwAg);
				abr.addOutput("[R133] Update material basic view for Type/Model material");
			}
			
			// epims : typeModelChgd, it will update TYPE_MODEL SET CHANGED=0, we use t1 and t2 to handle the change
			
			rdhRestProxy.r120(typeModel, chwA, pimsIdentity);
			abr.addOutput("[R120] Maintain model value for type MOD characteristic");
			if ((spComponents.getSharedProduct())) {
				rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, true, pimsIdentity);
				abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
			} else {
				rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, false, pimsIdentity);
				abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model");
			}

			// typemodel update profit center
			if (configManager.getString(PropertyKeys.KEY_SAP_LEDGER).equals("Y")) {						
//				MaterailPlantData matplantData = new MaterailPlantData();
				if (isProfitCenterChanged) {
					for (String plant : plants) {
						rdhRestProxy.r262(chwA, material, plant, pimsIdentity, typeModel.getProfitCenter(), chwAg);
						abr.addOutput("[R262] Create plant " + plant + " view profit center for material");
					}
				}
			}
			if (isProfitCenterChanged) {
				// Extra Plants (CFI Plants ) reading from Properties File .
				String extraCfiPlant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true);
				abr.addDebug("Extra CFI Plant " + extraCfiPlant);		
				StringTokenizer cfiTtoken = new StringTokenizer(extraCfiPlant, ",");
				while (cfiTtoken.hasMoreElements()) {
					String sPlant = cfiTtoken.nextToken();				
					rdhRestProxy.r183(chwA.getAnnDocNo(), typeModel.getType() + typeModel.getModel(), sPlant, pimsIdentity, typeModel.getProfitCenter(), chwAg, typeModel.getDiv());
					abr.addOutput("[R183] Create CFI plant " + sPlant + " view for Type/Model material");
				}
			}					
			abr.addDebug("End Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted");
		}

		// Story 1796482: Question about Z_DM_SAP_CLASSIFICATION_MAINT - do some update for RFCABR of MODEL.
		// Fix missing MM_BTPRODUCTS in AUSP issue
		rdhRestProxy.r175(typeModel, null, chwA, "NEW", null, pimsIdentity);
		rdhRestProxy.r175(typeModel, null, chwA, "UPG", null, pimsIdentity);
		abr.addOutput("[R175] Create 001 classification for MM_FIELDS for Type NEW/UPG");

		if (typeModel.isHasRevProfile()) {
			rdhRestProxy.r205(typeModel, null, "NEW", null, null, null, null, pimsIdentity, chwA.getAnnDocNo());
			rdhRestProxy.r205(typeModel, null, "UPG", null, null, null, null, pimsIdentity, chwA.getAnnDocNo());
			abr.addOutput("[R205] Update BTPRODUCTS classification for MM_FIELDS for Type NEW/UPG");
		} else {
			boolean hasRevprofile = isTypeHasRevProfile(typeModel.getType());
			if (hasRevprofile) {
				boolean preModelHasRevProfile = typeModel.isHasRevProfile();
				typeModel.setHasRevProfile(true);

				rdhRestProxy.r205(typeModel, null, "NEW", null, null, null, null, pimsIdentity, chwA.getAnnDocNo());
				rdhRestProxy.r205(typeModel, null, "UPG", null, null, null, null, pimsIdentity, chwA.getAnnDocNo());
				abr.addOutput("[R205] Update BTPRODUCTS classification for MM_FIELDS for Type NEW/UPG");

				typeModel.setHasRevProfile(preModelHasRevProfile);
			}
		}

		/**
		 *  For each plants 
		 *	Update Revenue Profile BOM for Type refer to 4.3.4
		 *	End For each plant
		 *  
		 *  if model didn't promote before and model has Revenue Profile(REFPROF)
		 *  or model promoted before and there is a change of Revenue Profile(REFPROF)
		 */
		if ((!isTMPromoted && typeModel.isHasRevProfile()) || (isTMPromoted && isRevProfOrAuoChanged)) {
			for (String _plant : plants) {
				updateRevenueProfileBom(
						typeModel.getType(),
						chwA.getAnnDocNo(),
						typeModel.getRevProfile().getAuoMaterials(),
						null,
						typeModel.getRevProfile().getRevenueProfile(),
						"NEW",
						getOpwgId(),
						pimsIdentity,
						_plant,
						deletedAuoMaterails);
				updateRevenueProfileBom(
						typeModel.getType(),
						chwA.getAnnDocNo(),
						typeModel.getRevProfile().getAuoMaterials(),
						null,
						typeModel.getRevProfile().getRevenueProfile(),
						"UPG",
						getOpwgId(),
						pimsIdentity,
						_plant,
						deletedAuoMaterails);
				updateRevenueProfileBom(
						typeModel.getType(),
						chwA.getAnnDocNo(),
						typeModel.getRevProfile().getAuoMaterials(),
						null,
						typeModel.getRevProfile().getRevenueProfile(),
						"MOD"+typeModel.getModel(),
						getOpwgId(),
						pimsIdentity,
						_plant,
						deletedAuoMaterails);
			}
		}					
		abr.addDebug("End Update Revenue Profile BOM for Type");

		abr.addDebug("Start Update Sales BOM");
		/**
		 * 1. if model didn't promote before then full plants
		 * 2. or if model promoted before and model GEO didn't promoted before then delta plants
		 */
		if (!isTMPromoted || (isTMPromoted && !isTMGeoPromoted)) {
			Set<String> salesBomPlants = isTMPromoted ? deltaPlants : plants;
			if (salesBomPlants != null && salesBomPlants.size() > 0) {
				for (String plant : salesBomPlants) {
					updateSalesBom(typeModel, chwA, spItemCateg, "NEW", plant, pimsIdentity);
					updateSalesBom(typeModel, chwA, spItemCateg, "UPG", plant, pimsIdentity);
				}
			}
		}
		abr.addDebug("End Update Sales BOM");

		if (!isTMPromoted || isTMChanged || isTMGeoChanged || !isTMGeoPromoted) {
			needUpdateParkingTable = true;
			abr.addDebug("set needUpdateParkingTable to " + needUpdateParkingTable);
		}
	}

	private void promoteWDFMAnnouncement(Date wdfmDate, TypeModel typeModel, List<SalesOrgPlants> salesOrgPlantList,
			String annDocNo, String pimsIdentity, String flfilcd, CHWAnnouncement chwA, CHWGeoAnn chwAg) throws Exception {

		abr.addOutputHeader("Promote WDFM Announcement:");
		LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(typeModel);
		
		PlannedSalesStatus ps = new PlannedSalesStatus();
		java.util.Date today = new java.util.Date();
		
		if (wdfmDate.before(today)||wdfmDate.equals(today)) {			
			ps.setCurrentSalesStatus("ZJ");
			ps.setCurrentEffectiveDate(wdfmDate);
		} 
		
		for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
			String salesOrg = salesOrgPlants.getSalesorg();	
			LifecycleData lcd = rdhRestProxy.r200(lcdGen.getMaterial(), lcdGen.getVarCond(), annDocNo, "wdfm", pimsIdentity, salesOrg);
			abr.addOutput("[R200] Read lifecycle row WDFM for sales " + salesOrg);

			updateWDFMLifecyle(lcd, lcdGen.getVarCond(), lcdGen.getMaterial(), wdfmDate, annDocNo, pimsIdentity,
					salesOrg);
			
			Vector vectTaxList = getTaxListBySalesOrgPlants(salesOrgPlants);
			Vector<String> _plants = salesOrgPlants.getPlants();			
			if (_plants != null && _plants.size() > 0) {
				for (String plantValue : _plants) {
					if (plantValue.equals("1999")) {
						abr.addDebug("Skip plant for 1999");
						continue;
					}
					rdhRestProxy.r117(chwA, typeModel.getType() + typeModel.getModel(), typeModel.getDiv(),
							typeModel.getAcctAsgnGrp(), ps, true, pimsIdentity, flfilcd, salesOrg,
							typeModel.getProductHierarchy(), vectTaxList, plantValue, chwAg);
					abr.addOutput("[R177] Create sales " + salesOrg + " view for Type/Model material");
				}
			} else {
				abr.addDebug("Not call R117 no plant for salesOrg: " + salesOrg);
			}
			
		}
	}

	public Vector getComponentsintypeNEW(Vector typeModelDeleted,
			Vector hashtypemodelbom)
	{

		Vector updatemtc = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = typeModelDeleted.elements();
		Enumeration dapiter = hashtypemodelbom.elements();
		//for (int k=0;k<=typeModelDeleted.size();k++){
		while (sapiter.hasMoreElements())
		{
			DepData dDataMTC = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataMTC.getComponent();
			vect1.addElement(newtypecomponet);
			abr.addDebug("vector typeMTC component value"
					+ dDataMTC.getComponent());
		}
		abr.addDebug("vector size of vect1" + vect1.size());

		while (dapiter.hasMoreElements())
		{
			DepData dDataNEW = (DepData) dapiter.nextElement();
			String component = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value"
					+ dDataNEW.getComponent());
			//for (int k=0;k<=hashtypemodelbom.size();k++){
			//DepData dDataNEW = (DepData)typeModelDeleted.elementAt(k);
			//String newtypecomponet = dDataNEW.getComponent();
			//vect1.addElement(newtypecomponet);
			if ((vect1.contains(component)))
			{
				if (updatemtc != null && updatemtc.size() == 0)
					updatemtc = null;
			}
			else
			{
				//if(!(updatemtc.contains(component))){
				if (updatemtc == null)
				{
					updatemtc = new Vector();
				}
				updatemtc.add(dDataNEW);

				//logger.debug("vector size of Update MTC"+updatemtc.size());
			}
		}
		return updatemtc;

		//}
	}

	public Vector getComponentsintypeMTCwithtypeNEW(Vector typeModelDeleted,
			Vector hashtypemodelbom)
	{

		Vector notmatch = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = hashtypemodelbom.elements();
		Enumeration dapiter = typeModelDeleted.elements();
		abr.addDebug("Vecor Size for typeMTC" + typeModelDeleted.size());
		abr.addDebug("Vecor Size for typeNEW" + hashtypemodelbom.size());

		DepData dDataNEW = new DepData();
		while (sapiter.hasMoreElements())
		{
			dDataNEW = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value"
					+ dDataNEW.getComponent());
			vect1.addElement(newtypecomponet);
		}

		//for (int i=0;i<=typeModelDeleted.size();i++)
		//{
		while (dapiter.hasMoreElements())
		{
			DepData dDataMTC = (DepData) dapiter.nextElement();
			String component = dDataMTC.getComponent();
			abr.addDebug("vector typeMTC component value"
					+ dDataMTC.getComponent());
			if ((vect1.contains(component)))
			{
				if (notmatch != null && notmatch.size() == 0)
					notmatch = null;
			}
			else
			{
				if (notmatch == null)
				{
					notmatch = new Vector();
				}
				notmatch.add(dDataMTC);
			}
		}
		return notmatch;
	}

	private boolean isTypePromoted(Vector machtypeVct) throws RfcAbrException {
		if(machtypeVct != null && machtypeVct.size() > 0) {
			for (int i = 0; i < machtypeVct.size(); i++) {
				EntityItem machTypeItem = (EntityItem)machtypeVct.elementAt(i);
				String promoted = getAttributeFlagValue(machTypeItem, MACHTYPE_PROMOTED);
				if (MACHTYPE_PROMOTED_YES.equals(promoted)) {
					abr.addDebug("Entity " + machTypeItem.getKey() + " PROMOTED attr value: " + promoted);
					return true;
				}
			}
		}
		abr.addDebug("Not found promoted MACHTYPE");
		return false;
	}

	private void setPromotedMachtypes(Vector machtypeVct) throws SQLException, MiddlewareException {
		if(machtypeVct != null && machtypeVct.size() > 0) {
			for (int i = 0; i < machtypeVct.size(); i++) {
				EntityItem machTypeItem = (EntityItem)machtypeVct.elementAt(i);
				setFlagValue(MACHTYPE_PROMOTED, MACHTYPE_PROMOTED_YES, machTypeItem);
			}
		}
	}

	private boolean isProductHierarchyDifferent(String productHierarchyFromDB, String productHierarchyFromMMLC) {
		if ((productHierarchyFromDB != null) && (productHierarchyFromMMLC != null)) {
			if (!(productHierarchyFromDB.trim().equalsIgnoreCase(productHierarchyFromMMLC.trim()))) {
				abr.addDebug("ProductHierarchy is different " +  productHierarchyFromDB + " from db and " + productHierarchyFromMMLC + " from MMLC");
				return true;
			}
		}
		return false;
	}
	
	private void checkSharedProductInfo(String type, String model,
			String annDocNo, SharedProductComponents spComponents,
			String pimsIdentity, String prodTypeCd, boolean bundleFlag,
			String sharedMaterialType) throws HWPIMSException {
		// Re: RFC caller R206 checkSharedProductInfo 05/03/2016 05:37 AM
		// Checked with Rick, the model promote logic assume that it is NOT a PCD product. ie) sharedProduct = false.
		spComponents.setSharedProduct(false);
		spComponents.setSharedProductIn0147(false);
		abr.addDebug("Shared Product in Check Shared Product Info : " + spComponents.getSharedProduct());
		abr.addDebug("This is NOT Sales Org with 0147 *****: " + spComponents.getSharedProductIn0147());
//		boolean SharedProductExists = false;
//		boolean SharedProductExists1 = false;
//		Vector spVec = new Vector();
//		if (sharedMaterialType != null && !sharedMaterialType.equals("")) {
//			SharedProductExists = rdhRestProxy.r206(type + model); // 鏈疄鐜�
//			if (SharedProductExists) {
//				spComponents.setSharedProduct(true);
//				abr.addDebug("Shared Product in Check Shared Product Info : " + spComponents.getSharedProduct());
//				spComponents.setSharedProductMaterailType(sharedMaterialType);
//				Vector vt = rdhRestProxy.r179(type + model, annDocNo, false, "ann", pimsIdentity, prodTypeCd, bundleFlag, "0147"); // 鏈疄鐜�
//				if (vt != null) {
//					spComponents.setSharedProductIn0147(true);
//				} else if (vt == null) {
//					spComponents.setSharedProductIn0147(false);
//					abr.addDebug("This is NOT Sales Org with 0147 *****" + spComponents.getSharedProductIn0147());
//				}
//			}
//		}
	}
	
	
	
	private PlannedSalesStatus checkForNewPlannedSalesStatus(Date annDate, boolean CustomSEO) throws HWPIMSException {

		PlannedSalesStatus ps = new PlannedSalesStatus();
		ps.setCurrentSalesStatus("");
		ps.setPlannedChangeSalesStatus("");
		ps.setOrigPlannedSalesStatus("");
		ps.setOrigSalesStatus("");
		ps.setOrigPlannedSalesStatus(null);

		java.util.Date today = new java.util.Date();
		if (annDate.after(today)) {
			ps.setPlannedEffectiveDate(annDate);
			if (CustomSEO) {
				ps.setPlannedChangeSalesStatus("ZN");
			} else {
				ps.setPlannedChangeSalesStatus("Z0");
			}
			ps.setCurrentSalesStatus("YA");
			ps.setCurrentEffectiveDate(today);
		} else {
			if (annDate.before(today) || annDate.equals(today)) {
				ps.setCurrentSalesStatus("Z0");
				ps.setCurrentEffectiveDate(annDate);
			}
		}
		return ps;
	}

	private void callR260updateProdHierarchyOnSalesView(
			Vector supportedSalesOrgV, Object material, CHWAnnouncement chwA,
			String productHierarchy, String pimsIdentity, CHWGeoAnn chwAg, String acctAsgnGrp)
			throws HWPIMSAbnormalException {
		try {
			if (supportedSalesOrgV != null) {
				Vector salesOrgV = supportedSalesOrgV;
				abr.addDebug("CR2020 : saleOrgVector size : " + salesOrgV.size());
				Enumeration salesOrgE = salesOrgV.elements();
				while (salesOrgE.hasMoreElements()) {
					String salesOrg = (String) salesOrgE.nextElement();
					abr.addDebug("CR2020 : saleOrgVector contents : " + salesOrg);
					rdhRestProxy.r260(chwA, material, pimsIdentity, salesOrg, productHierarchy, chwAg, acctAsgnGrp);
					abr.addOutput("[R260] Update product hierarchy on sales " + salesOrg + " view");
				}
			}
		} catch (Exception e) {
			String msg = "Error - Updating L3593 : UpdateProdHierarchyOnSalesView : ";
			throw new HWPIMSAbnormalException(msg, e);
		}

	}
	
	/**
	 * Update Sales BOM
	 * 1. call r210 to read componments for sales BOM
	 * 2. if not exist BOM in MAST, then call r142 BOM create
	 * 3. else if the componment(type+model) exist in returned componments, do nothing
	 * 4. else if the componment(type+model) not exist in returned componments, then call r143 BOM maintain
	 * 
	 * @param typeModel
	 * @param announcement - just use the AnnDocNo
	 * @param newFlag - NEW or UPG
	 * @param plant
	 * @param pimsIdentity
	 * @throws Exception
	 */
	private void updateSalesBom(TypeModel typeModel, CHWAnnouncement announcement, Hashtable spItemCateg, String newFlag, String plant, String pimsIdentity) throws Exception {
		String type = typeModel.getType();
		String componment = typeModel.getType() + typeModel.getModel();
		Vector geoV = new Vector();
		geoV.add(typeModel);
		try {
			Vector rdhComponmentVector = rdhRestProxy.r210(type, newFlag, plant);
			abr.addOutput("[R210] Read sales BOM for type " + newFlag + " plant " + plant);
			if (rdhComponmentVector != null && rdhComponmentVector.size() > 0) {
				if (hasMatchComponent(rdhComponmentVector, componment)) {
			        abr.addDebug("updateSalesBom exist component " + componment + " in RDH STPO table, do nothing!");	
				} else {
					abr.addDebug("updateSalesBom not exist component " + componment + " in RDH STPO table, create it!");
					// R143assignTypeModelAsSalesBOMItemWithDependencies Z_DM_SAP_BOM_MAINTAIN
					rdhRestProxy.r143(type, plant, geoV, newFlag, announcement, spItemCateg, pimsIdentity);
					abr.addOutput("[R143] Assign TypeModel as SalesBOM item with dependencies " + newFlag);
				}
			} else {
				abr.addDebug("updateSalesBom there is no component return");
				// R143assignTypeModelAsSalesBOMItemWithDependencies Z_DM_SAP_BOM_MAINTAIN
				rdhRestProxy.r143(type, plant, geoV, newFlag, announcement, spItemCateg, pimsIdentity);
				abr.addOutput("[R143] Assign TypeModel as SalesBOM item with dependencies " + newFlag);
			}
		} catch (HWPIMSNotFoundInMastException e) {
			abr.addDebug("updateSalesBom not found in MAST table");
			// R142_createSalesBOM Z_DM_SAP_BOM_CREATE
			rdhRestProxy.r142(type, plant, geoV, newFlag, announcement, spItemCateg, pimsIdentity);
			abr.addOutput("[R142] Create sales BOM " + newFlag);
		}
	}

	private void createConfigurationProfiles(Vector configProfTypes,
			CHWAnnouncement announcement, String pimsIdentity)
			throws Exception
	{
		int cptSize = configProfTypes.size(); //All new "types"
		for (int t = 0; t < cptSize; t++)
		{
			//checkStopRequest();
			rdhRestProxy.r148((String) configProfTypes.elementAt(t), announcement,	pimsIdentity);
			rdhRestProxy.r149((String) configProfTypes.elementAt(t), announcement,	pimsIdentity);
		}
	}

	protected Set<String> getAllPlant(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> plants = new HashSet<>();			
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			Vector<String> tmpPlants = salesorgPlants.getPlants();			
			for (String plant : tmpPlants) {
				plants.add(plant);
			}
			if (tmpPlants.size() == 0) {
				abr.addDebug("getAllPlants No plant found for country: " + salesorgPlants.getGenAreaCode());
			}				
		}			
		abr.addDebug("getAllPlants All plants size: " + plants.size() + " values: " + plants);
		return plants;		
	}
	
	@SuppressWarnings("unused")
	private Set<String> getAllSalesOrg(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> salesOrgSet = new HashSet<>();			
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			String salesOrg = salesorgPlants.getSalesorg();			
			salesOrgSet.add(salesOrg);	
		}			
		abr.addDebug("getAllSalesOrg All salesOrg size: " + salesOrgSet.size() + " values: " + salesOrgSet);
		return salesOrgSet;		
	}

	private Vector getSupportedSalesOrgCol(List<SalesOrgPlants> salesorgPlantsVect) {		
		Vector salesOrgs = new Vector();
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			String salesOrg = salesorgPlants.getSalesorg();
			if (!"".equals(salesOrg)) {
				salesOrgs.add(salesOrg);
			}
		}		
		return salesOrgs;		
	}

	private boolean hasRevProfile(EntityItem mdlItem) throws RfcAbrException {
		Vector revProfile = PokUtils.getAllLinkedEntities(mdlItem, "MODREVPROFILE", REVPROF);
		if (revProfile.size() > 0) {
			return true;
		}
		return false;
	}
	

	private boolean isTypeModelChanged(EntityItem t1ModelItem, EntityItem t2ModelItem) throws RfcAbrException {
		boolean isTypeModelChanged = false;
		if (isDiff(t1ModelItem, t2ModelItem, modelMarkChangedAttrs)) {
			isTypeModelChanged = true;
		} else if (isProfitCenterChanged(t1ModelItem, t2ModelItem)) {
			isTypeModelChanged = true;
		} else {
			String t1ProdHireCd = getProdHireCd(t1ModelItem);
			String t2ProdHireCd = getProdHireCd(t2ModelItem);
			if (!t1ProdHireCd.equals(t2ProdHireCd)) {
				abr.addDebug("PRODHIRECODE value " + t1ProdHireCd + " at t1 is different with " + t2ProdHireCd + " at t2");
				isTypeModelChanged = true;
			}
			if(!isTypeModelChanged){
				isTypeModelChanged = isRevProfOrAuoMtrlChanged(t1ModelItem,t2ModelItem);
				abr.addDebug("isRevProfOrAuoMtrlChanged= " + isTypeModelChanged );
			}
		}
		return isTypeModelChanged;
	}
	
	private boolean isProfitCenterChanged(EntityItem t1ModelItem, EntityItem t2ModelItem) throws RfcAbrException {
		String profitCenter1 = getProfitCenter(getDiv(t1ModelItem));
		String profitCenter2 = getProfitCenter(getDiv(t2ModelItem));
		abr.addDebug("isProfitCenterChanged ProfitCenter T1 " + profitCenter1 + " T2 " + profitCenter2);
		return !profitCenter1.equals(profitCenter2);
	}
	
	@SuppressWarnings("unused")
	private boolean isRevProfileChanged(EntityItem t1ModelItem, EntityItem t2ModelItem) throws RfcAbrException {
		RevProfile rev1 = getRevProfile(t1ModelItem);
		RevProfile rev2 = getRevProfile(t2ModelItem);
		String revProFile1 = rev1.getRevenueProfile();
		String revProFile2 = rev2.getRevenueProfile();
		return false;
	}
	
	/**
	 * 
	 * @param t1AvailItemVct
	 * @param t2AvailItem
	 * @return
	 * @throws RfcAbrException
	 */
	private boolean isTypeModelGeoChanged(Vector t1AvailItemVct, EntityItem t2AvailItem, Set<String> deltaPlants) throws RfcAbrException {
		if(isAnnDateChanged(t1AvailItemVct, t2AvailItem)) {
			return true;
		}		
		// Check plant
		// Get all plants at T1
		Set<String> t1Plants = new HashSet<>();
		for (int i = 0; i < t1AvailItemVct.size(); i++) {
			EntityItem t1AvailItem = (EntityItem)t1AvailItemVct.get(i);
			Vector t1GenAreaVct = PokUtils.getAllLinkedEntities(t1AvailItem, "AVAILGAA", GENERALAREA);
			List<SalesOrgPlants> t1SalesOrgPlantsVct = getAllSalesOrgPlant(t1GenAreaVct);
			t1Plants.addAll(getAllPlant(t1SalesOrgPlantsVct));
		}
		// Get all plants for the avail at T2
		Vector t2GenAreaVct = PokUtils.getAllLinkedEntities(t2AvailItem, "AVAILGAA", GENERALAREA);
		List<SalesOrgPlants> t2SalesOrgPlantsVct = getAllSalesOrgPlant(t2GenAreaVct);
		Set<String> t2Plants = getAllPlant(t2SalesOrgPlantsVct);
		abr.addDebug("isTypeModelGeoChanged T1 all plant size: " + t1Plants.size() + " values: " + t1Plants);
		abr.addDebug("isTypeModelGeoChanged T2 for " + t2AvailItem.getKey()  + " plant size: " + t2Plants.size() + " values: " + t2Plants);
		
		for(String t2:t2Plants){ 
			if(!t1Plants.contains(t2)){ 
				deltaPlants.add(t2);
			} 
		} 	
		abr.addDebug("isTypeModelGeoChanged T2 for delta plant size: " + deltaPlants.size() + " values: " + deltaPlants);
		
		if (!t1Plants.containsAll(t2Plants)) {
			return true;
		}
		return false;
	}

	private boolean isTypeModelWithdrawChanged(Vector t1AvailItemVct, EntityItem t2AvailItem) throws RfcAbrException {
		EntityItem t1Avail = getEntityItemAtT1(t1AvailItemVct, t2AvailItem);
		if (t1Avail != null) {
			String t1Date = getAttributeValue(t1Avail, AVAIL_EFFECTIVEDATE);
			String t2Date = getAttributeValue(t2AvailItem, AVAIL_EFFECTIVEDATE);
			if (!t2Date.equals(t1Date)) {
				abr.addDebug("isTypeModelWithdrawChanged true T1 Date " + t1Date + " T2 Date " + t2Date);
				return true;
			}
		} else {
			abr.addDebug("isTypeModelWithdrawChanged true AVAIL is null at T1 for " + t2AvailItem.getKey());
			return true;
		}
		abr.addDebug("isTypeModelWithdrawChanged false");
		return false;
	}

	private boolean isAnnDateChanged(Vector t1AvailItemVct, EntityItem t2AvailItem) throws RfcAbrException {
		// Check ANNDATE
		// Get ANNDATE at T1
		String t1AnnDate = "";
		EntityItem t1Avail = getEntityItemAtT1(t1AvailItemVct, t2AvailItem);
		if (t1Avail != null) {
			Vector t1AnnVct = PokUtils.getAllLinkedEntities(t1Avail, "AVAILANNA", ANNOUNCEMENT);
			if (t1AnnVct.size() > 0) {
				EntityItem t1AnnItem = (EntityItem)t1AnnVct.get(0); // AVAIL must only link one ANNOUNCEMENT
				t1AnnDate = getAttributeValue(t1AnnItem, ANNDATE);
			} else {
				abr.addDebug("isAnnDateChanged Not found ANNOUNCEMENT for " + t2AvailItem.getKey() + " at T1 but at T2");
				return true;
			}
		} else {
			abr.addDebug("isAnnDateChanged Not found " + t2AvailItem.getKey() + " at T1 but at T2");
			return true;
		}
		// Get ANNDATE at T2
		String t2AnnDate = "";
		Vector t2AnnVct = PokUtils.getAllLinkedEntities(t2AvailItem, "AVAILANNA", ANNOUNCEMENT);
		if (t2AnnVct.size() > 0) {
			EntityItem t2AnnItem = (EntityItem)t2AnnVct.get(0); // AVAIL must only link one ANNOUNCEMENT
			t2AnnDate = getAttributeValue(t2AnnItem, ANNDATE);
		} else {
			abr.addDebug("isAnnDateChanged Not found ANNOUNCEMENT for " + t2AvailItem.getKey() + " at T2");
		}
		abr.addDebug("isAnnDateChanged ANNDATE " + t1AnnDate + " at T1 " + t2AnnDate + " at T2");
		if (!t1AnnDate.equals(t2AnnDate)) {
			return true;
		}
		return false;
	}

	private boolean hasMatchComponent(Vector typeNEW, String componment){
		for (int i = 0; i < typeNEW.size(); i++) {
			DepData rev = (DepData)typeNEW.get(i);
			// Defect1734655 - Defect MODEL RFCABR created duplicate components of sales BOM in RDH
			// trim the RDH returned result, we met an issue because the component will append blank spaces
			if (rev.getComponent().trim().equals(componment)){				
			  return true;
			}
		}
		return false;
	}
	
	private String getProfitCenter(String div){
		String profitCenter;
		if (isAlphaNumeric(div)){
			profitCenter= div;
		}else{
			profitCenter = "00000000" + div;
			
		}
		return profitCenter;
	}
	
	private boolean isAlphaNumeric(String str){
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isLetter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check all models for the type, if any model has linked REVPROFILE
	 * @param type MACHTYPE
	 * @return isMTHasRevProfile
	 */
	private boolean isTypeHasRevProfile(String type) {
		boolean hasRevprofile = false;
		String sql = "SELECT f.entityid FROM opicm.flag f \n" +
				"JOIN opicm.relator r \n" +
				"ON f.entitytype=r.entity1type AND f.entityid=r.entity1id AND r.entitytype='MODREVPROFILE' AND f.entitytype='MODEL' AND f.attributecode='MACHTYPEATR' AND f.valto>current timestamp and f.effto > current timestamp AND r.valto>current timestamp and r.effto > current timestamp\n" +
				"WHERE f.attributevalue=?";
//		abr.addDebug("isMTHasRevProfile sql: " + sql);
		try (PreparedStatement ps = abr.getDatabase().getPDHConnection().prepareStatement(sql)) {
			ps.setString(1, type);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				int entityId = result.getInt(1);
				abr.addDebug("isMTHasRevProfile MODEL" + entityId);
				hasRevprofile = true;
				break;
			}
		} catch (MiddlewareException | SQLException e) {
			e.printStackTrace();
			abr.addDebug("isMTHasRevProfile Exception on " + e.getMessage());
		}
		abr.addDebug("isMTHasRevProfile=" + hasRevprofile);
		return hasRevprofile;
	}

	public String getVeName() {
		return "RFCMODEL";
	}
}
