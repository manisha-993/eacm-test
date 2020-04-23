package COM.ibm.eannounce.abr.sg.rfc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.SBRException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.DepData;
import com.ibm.rdh.chw.entity.LifecycleData;
import com.ibm.rdh.chw.entity.RevProfile;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: RFCMODELCONVERTABR.java,v $
//Revision 1.33  2019/07/09 12:09:41  dlwlan
//Story 1985938? Add withdraw process for caller 102 117.
//
//Revision 1.32  2019/04/03 12:23:45  xujianbo
//Rollback All the Unique ID Code
//
//Revision 1.27  2018/06/01 12:13:54  dlwlan
//Defect 1856545 Error: add break
//
//Revision 1.26  2018/05/31 15:27:01  dlwlan
//Defect 1856545 Error: Material <9831MTC> not found in MAST table when promote modelconvert 9831 R07 9833 S07
//
//Revision 1.25  2018/05/30 14:39:45  dlwlan
//Defect 1855454 change R165 after R123
//
//Revision 1.24  2018/05/30 09:16:15  dlwlan
//Defect 1855454 RFC caller order adjustment for RPQ feature and modelconvert promotion.
//
//Revision 1.23  2017/03/16 12:39:04  wangyul
//1642763: Tax support for US when product is set for ZA(south Africa)
//
//Revision 1.22  2017/01/23 06:37:15  wangyul
//[Work Item 1642728] CHW Simplification - Need to support Non RFA Avail while deriving the announcement dates
//
//Revision 1.21  2017/01/16 07:00:47  wangyul
//[Work Item 1642180] CHW - BOM create (Z_DM_SAP_BOM_CREATE) RFC needs to create BOM dependency link if required
//
//Revision 1.20  2017/01/16 06:58:15  wangyul
//Defect 1634558 - a problem in RFCABR on how to check on return back from BOM read RFC to determine if a BOM exists or not
//
//Revision 1.19  2016/12/28 09:34:25  wangyul
//[Work Item 1612648] Update to Z_DM_SAP_BOM_MAINTAIN RFC to prevent BOM update issue for CHW and ESW products
//
//Revision 1.18  2016/12/01 13:40:56  wangyul
//[Work Item 1627842] New: pre-existing parking records not being set to H status by EACM RFCABR
//
//Revision 1.17  2016/11/09 13:34:38  wangyul
//[Work Item 1617881] New: Configurable option to move parking table records to R status or leave them in H status
//
//Revision 1.16  2016/09/27 13:41:37  wangyul
//Defect1598964-MODEL convert 2097 E12 2827 H20  failed to update  Sales BOM  typeMTC
//
//Revision 1.15  2016/09/26 14:28:27  wangyul
//Defect1598964-MODEL convert 2097 E12 2827 H20  failed to update  Sales BOM  typeMTC
//
//Revision 1.14  2016/08/30 12:33:07  wangyul
//defect 1585371 - parking records for zdmrelnum 2965_REV being left in Hold status
//
//Revision 1.13  2016/08/18 07:56:56  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date - isWithdrawChanged
//
//Revision 1.12  2016/08/17 07:06:11  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date
//
//Revision 1.11  2016/08/15 07:45:53  wangyul
//Defect 1568131 - MLAN table not populated like production MMLC
//
//Revision 1.10  2016/08/10 09:00:15  wangyul
//Move r144 to update parking table at last
//
//Revision 1.9  2016/07/29 13:45:47  wangyul
//material master for 5146GL2 missing mandatory MAKT segment - update flfilcd AAS to ZIP
//
//Revision 1.8  2016/07/27 15:51:06  wangyul
//Update extract LoadingGroup value
//
//Revision 1.7  2016/07/22 12:24:23  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types - update the code to check if from and to type exist RPQ feature
//
//Revision 1.6  2016/07/15 08:19:26  wangyul
//Story 1463325 - * OIM CHW Simplification new ABR for update revenue profile BOM, update Ann LifeCycle, Pland sale
//
//Revision 1.5  2016/07/04 08:56:22  wangyul
//1. Promote WDFM Announcement for MODELCONVERT Withdraw From Market
//2. Use all salesOrg instead of 0147
//
//Revision 1.4  2016/06/23 08:29:12  wangyul
//update modelcovnert RFCABR
//

public class RFCMODELCONVERTABR extends RfcAbrAdapter {
	
	public static final String MTCFROMTYPE = "MTCFROMTYPE";
	public static final String MTCTOTYPE = "MTCTOTYPE";
	public static final String FROMTYPE = "FROMTYPE";
	public static final String TOTYPE = "TOTYPE";
	
	private boolean isRPQFromType = false;
	private boolean isRPQToType = false;

	public RFCMODELCONVERTABR(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "static-access", "unchecked" })
	public void processThis() throws Exception {
		abr.addDebug("Start processThis()");
		EntityList t1EntityList = null;
		EntityItem mdlCvtItem = null;
		boolean isResendFull = false;
		try {
			// ----------------------- Get values from EACM entities -----------------------
			// MODELCONVERT
			mdlCvtItem = getRooEntityItem();
			// Check status
			if (!STATUS_FINAL.equals(getAttributeFlagValue(mdlCvtItem, ATTR_STATUS))) {
				throw new RfcAbrException("The status is not final, it will not promote this MODELCONVERT");
			}
			// AVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(mdlCvtItem, "MODELCONVERTAVAIL", AVAIL);
			Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
			Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
			abr.addDebug("MODELCONVERTAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size() +
					" LastOrder avail " + lastOrderAvailVct.size());
			if (planAvailVct.size() == 0) {
				throw new RfcAbrException("There is no plan avail for this MODELCONVERT, it will not promote this MODELCONVERT");
			}
			
			String fromMachType = getAttributeValue(mdlCvtItem, MODELCONVERT_FROMMACHTYPE);
			String fromModel = getAttributeValue(mdlCvtItem, MODELCONVERT_FROMMODEL);
			String toMachType = getAttributeValue(mdlCvtItem, MODELCONVERT_TOMACHTYPE);
			String toModel = getAttributeValue(mdlCvtItem, MODELCONVERT_TOMODEL);
			String annDocNo = fromMachType + STRING_SEPARATOR + fromModel + STRING_SEPARATOR + toMachType + STRING_SEPARATOR + toModel;
			
			// FROM and TO MODEL
			EntityItem fromModelItem = getModelByMachTypeAndModel(fromMachType, fromModel);
			fromModelItem = getModelByVe2(fromModelItem.getEntityType(), fromModelItem.getEntityID());
			EntityItem toModelItem = getModelByMachTypeAndModel(toMachType, toModel);
			toModelItem = getModelByVe2(toModelItem.getEntityType(), toModelItem.getEntityID());
			// FROM and TO MACHTYPE
			Vector fromMachTypeVct = PokUtils.getAllLinkedEntities(fromModelItem, "MODELMACHINETYPEA", MACHTYPE);	
			if (fromMachTypeVct.size() == 0) {
				throw new RfcAbrException("There is no MACHTYPE entity for FROMMACHTYPE " + fromMachType);
			}
			Vector toMachTypeVct = PokUtils.getAllLinkedEntities(toModelItem, "MODELMACHINETYPEA", MACHTYPE);
			if (toMachTypeVct.size() == 0) {
				throw new RfcAbrException("There is no MACHTYPE entity for TOMACHTYPE " + toMachType);
			}
			if (!isMachTypePromoted(toMachTypeVct)) {
				throw new RfcAbrException("The MACHTYPE was not promoted, please promote the MACHTYPE first. type " + toMachType);
			}
			if (!isMachTypePromoted(fromMachTypeVct)) {
				throw new RfcAbrException("The MACHTYPE was not promoted, please promote the MACHTYPE first. type " + fromMachType);
			}
			if (isRPQType(fromMachTypeVct)) {
				isRPQFromType = true;
			}
			if (isRPQType(toMachTypeVct)) {
				isRPQToType = true;
			}
			abr.addDebug("isRPQFromType " + isRPQFromType + " isRPQToType " + isRPQToType);
			
			// ----------------------- Create RFC input entities -----------------------
			// TypeModelUPGGeo
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setFromType(fromMachType);
			tmUPGObj.setFromModel(fromModel);
			tmUPGObj.setType(toMachType);
			tmUPGObj.setModel(toModel);
			
			String pimsIdentity = "C";
			abr.addDebug("PimsIdentity:" + pimsIdentity);
			
			String flfilcd = "ZIP";
			abr.addDebug("Flfilcd: " + flfilcd);
			
			boolean isTMUpgPromoted = false;
			boolean isTMUpgChanged = false;
			boolean needUpdateParkingTable = false;
			
			EntityItem t1MdlCvtItem = null;
			String t1DTS = null;
			
			// ----------------------- Check Resend full -----------------------
			isResendFull = SYSFEEDRESEND_YES.equals(getAttributeFlagValue(mdlCvtItem, ATTR_SYSFEEDRESEND));
			abr.addDebug("Resend full: " + isResendFull);
			
			AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(mdlCvtItem, RFCABRSTATUS);
			boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
			abr.addDebug("Exist passed RFCABRSTATUS before: " + isPassedAbrExist);
			if (!isResendFull && isPassedAbrExist) {
				t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
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
		            t1MdlCvtItem = t1EntityList.getParentEntityGroup().getEntityItem(0);
				}
			}
			
			for (int i = 0; i < planAvailVct.size(); i++) {
				EntityItem availItem = (EntityItem)planAvailVct.get(i);
				abr.addDebug("Promote MODELCONVERT for " + availItem.getKey());
				
				// Get all salesorg and plants from GENERALAREA linked to AVAIL
				Vector generalareaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", GENERALAREA);
				List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalareaVct);
				Set<String> plants = getAllPlant(salesOrgPlantList);
				
				if (!isResendFull && isPassedAbrExist) {
					if (t1EntityList != null && t1MdlCvtItem != null) {
						Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1MdlCvtItem, "MODELCONVERTAVAIL", AVAIL);
						Vector t1PlanAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE, PLANNEDAVAIL);
						// isTMUpgChanged will update all country, isTMUpgPromoted only update new country, check isTMUpgChanged first
						isTMUpgChanged = isTypeModelUpgrateChanged(t1PlanAvailVct, availItem);
						List<String> t2NewCountries = getNewCountries(t1PlanAvailVct, availItem);
						isTMUpgPromoted = t2NewCountries.size() == 0;
						if (!isTMUpgChanged) {
							if (!isTMUpgPromoted) {
								salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList, t2NewCountries);
							}
						}					
					}
				}
				abr.addDebug("isTMUpgPromoted: " + isTMUpgPromoted + " isTMUpgChanged: " + isTMUpgChanged);	
				
//				<ANNDATE>
//				MODELCONVERTAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
//				If there is no ANNOUNCEMENT, then use MODELCONVERT.ANNDATE
				CHWAnnouncement chwA = new CHWAnnouncement();
				chwA.setAnnDocNo(annDocNo);
				chwA.setSegmentAcronym(getSegmentAcronymForAnn(mdlCvtItem));

				CHWGeoAnn chwAg = new CHWGeoAnn();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
				Vector annVect = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", ANNOUNCEMENT);
				if (annVect != null && annVect.size() > 0) {
					EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
					chwA.setAnnouncementType(getAttributeValue(annItem, ANNOUNCEMENT_ANNTYPE)); //  WS logic:if feed is designated as "ePIMS/SW Migration", then set to "MIG" else set to "RFA", so flag or desc are all fine.
					chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, ANNDATE)));
				} else {
					chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
					chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(mdlCvtItem, ANNDATE)));
				}
				abr.addDebug("CHWAnnouncement: " + chwA.toString());
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
				
				if (!isTMUpgPromoted && (tmUPGObj.getType().equals(tmUPGObj.getFromType()))) {
					abr.addDebug("----------------------- Start Type Model Upgrate not promoted with same type -----------------------");
					String type = tmUPGObj.getType();
					rdhRestProxy.r107(type, chwA, pimsIdentity);
					abr.addDebug("Call R107 successfully");
					rdhRestProxy.r109(type, chwA, pimsIdentity);
					abr.addDebug("Call R109 successfully");
					//Defect 1855454 at 20180530
					rdhRestProxy.r125(type, chwA, "UPG", pimsIdentity);
					abr.addDebug("Call R125 successfully for UPG");
					rdhRestProxy.r111(type,	chwA, pimsIdentity);
					abr.addDebug("Call R111 successfully");
					//end
					boolean typeMTCExists = rdhRestProxy.r204(tmUPGObj.getType() + "MTC");
					abr.addDebug("Call R204 successfully typeMTCExists: " + typeMTCExists);
					if (typeMTCExists) {
						boolean typeMCclassExists = rdhRestProxy.r214(tmUPGObj.getType());
						abr.addDebug("Call R214 successfully typeMCclassExists: " + typeMCclassExists);
						if (typeMCclassExists) {
							rdhRestProxy.r125(tmUPGObj.getType(), chwA, "MTC", pimsIdentity);
							abr.addDebug("Call R125 successfully for MTC");
						}
					}
					rdhRestProxy.r151(type, chwA, pimsIdentity);
					abr.addDebug("Call R151 successfully");
	                LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmUPGObj);
	                for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
	                	String salesOrg = salesOrgPlants.getSalesorg();
	                	updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
	 	                abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + salesOrg);		
	                }	                           
					abr.addDebug("----------------------- End Type Model Upgrate not promoted with same type -----------------------");
				} else if (isTMUpgChanged && (tmUPGObj.getType().equals(tmUPGObj.getFromType()))) {		
					abr.addDebug("----------------------- Start Type Model Upgrate already promoted and changed with same type -----------------------");
		            LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmUPGObj);
		            for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
	                	String salesOrg = salesOrgPlants.getSalesorg();
	                	updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
	                	abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + salesOrg);	
		            }		            
		            abr.addDebug("----------------------- End Type Model Upgrate already promoted and changed with same type -----------------------");
				}
				Vector tmugValuesToCreate = new Vector();
				tmugValuesToCreate.add(tmUPGObj);
				rdhRestProxy.r124(tmugValuesToCreate, chwA, pimsIdentity);
				abr.addDebug("Call R124 successfully");
				
				if (!tmUPGObj.getType().equals(tmUPGObj.getFromType())) {
					abr.addDebug("----------------------- Start Type Model Upgrate for different type -----------------------");
					
					if (!isTMUpgPromoted) {
						abr.addDebug("----------------------- Start Type Model Upgrate not promoted for different type -----------------------");
						String extraplant = configManager.getConfigManager().getString(PropertyKeys.KEY_EXTRA_PLANT, true);
//						if (isMachTypePromoted(toMachTypeVct)) {
							abr.addDebug("Start createMTC for type " + tmUPGObj.getType());
							createMTCForFromToType(tmUPGObj.getType(),
									tmUPGObj.getModel(), toModelItem, MTCTOTYPE,
									salesOrgPlantList, chwA, chwAg, tmUPGObj,
									extraplant, pimsIdentity, plants, flfilcd, generalareaVct);
//						}
//						if (isMachTypePromoted(fromMachTypeVct)) {
							abr.addDebug("Start createMTC for type " + tmUPGObj.getFromType());
							createMTCForFromToType(tmUPGObj.getFromType(),
									tmUPGObj.getFromModel(), fromModelItem, MTCFROMTYPE,
									salesOrgPlantList, chwA, chwAg, tmUPGObj,
									extraplant, pimsIdentity, plants, flfilcd, generalareaVct);
//						}						
						LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmUPGObj);
						for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
		                	String salesOrg = salesOrgPlants.getSalesorg();		                	
							this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);							
							abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + salesOrg);	
			            }	
						
						rdhRestProxy.r163(tmUPGObj.getType(), tmUPGObj, chwA, pimsIdentity);
						abr.addDebug("Call R163 successfully for type " + tmUPGObj.getType());
						rdhRestProxy.r163(tmUPGObj.getFromType(), tmUPGObj, chwA, pimsIdentity);
						abr.addDebug("Call R163 successfully for type " + tmUPGObj.getFromType());

						// 6.8.FromTmRev is existed
						updateFromToRevenueProfileBom(fromModelItem, FROMTYPE, tmUPGObj, chwA, plants, pimsIdentity, null);						
						// 6.9.if ToTmRev is existed
						updateFromToRevenueProfileBom(toModelItem, TOTYPE, tmUPGObj, chwA, plants, pimsIdentity, null);
						
						abr.addDebug("----------------------- End Type Model Upgrate not promoted for different type -----------------------");
					} else {
						if (isTMUpgChanged) {
							abr.addDebug("----------------------- Start Type Model Upgrate changed for different type -----------------------");
							LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmUPGObj);
							for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
			                	String salesOrg = salesOrgPlants.getSalesorg();	
			                	this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), chwAg.getAnnouncementDate(), chwA.getAnnDocNo(), chwA.getAnnouncementType(), pimsIdentity, salesOrg);
			                	abr.addDebug("updateAnnLifecyle successfully for isTMUpgChanged " + isTMUpgChanged + " SalesOrg " + salesOrg);	
				            }	
							abr.addDebug("----------------------- End Type Model Upgrate changed for different type -----------------------");
						}
						
						// RevProf and AuoMtrl change
						if (!isResendFull && isPassedAbrExist && t1DTS != null) {
							String t2DTS = abr.getCurrentTime();
							EntityItem t1FromModelItem = getModelByVe2(fromModelItem.getEntityType(), fromModelItem.getEntityID(), t1DTS, t2DTS);
							if (isRevProfOrAuoMtrlChanged(t1FromModelItem, fromModelItem)) {
								Vector deletedAuoMaterails = new Vector();
								if (t1FromModelItem != null) {
									deletedAuoMaterails = getDeletedAuoMaterials(getRevProfile(t1FromModelItem).getAuoMaterials(), getRevProfile(fromModelItem).getAuoMaterials());
									abr.addDebug("Delete AUO Materials of from model size:" + deletedAuoMaterails.size());
								}
								// 6.8.FromTmRev is existed
								updateFromToRevenueProfileBom(fromModelItem, FROMTYPE, tmUPGObj, chwA, plants, pimsIdentity, deletedAuoMaterails);
							}
							EntityItem t1ToModelItem = getModelByVe2(toModelItem.getEntityType(), toModelItem.getEntityID(), t1DTS, t2DTS);
							if (isRevProfOrAuoMtrlChanged(t1ToModelItem, toModelItem)) {
								Vector deletedAuoMaterails = new Vector();
								if (t1ToModelItem != null) {
									deletedAuoMaterails = getDeletedAuoMaterials(getRevProfile(t1ToModelItem).getAuoMaterials(), getRevProfile(toModelItem).getAuoMaterials());
									abr.addDebug("Delete AUO Materials of to MODEL size:" + deletedAuoMaterails.size());
								}
								// 6.9.if ToTmRev is existed
								updateFromToRevenueProfileBom(toModelItem, TOTYPE, tmUPGObj, chwA, plants, pimsIdentity, deletedAuoMaterails);
							}
						} else {
							abr.addDebug("WARN: RevProf and AuoMtrl change isPassedAbrExist " + isPassedAbrExist + " t1DTS " + t1DTS);
						}						
					}
					
					createSalesBOMforTypeMTC(tmUPGObj.getFromType(), tmUPGObj.getFromModel(), chwA, pimsIdentity);
					createSalesBOMforTypeMTC(tmUPGObj.getType(), tmUPGObj.getModel(), chwA, pimsIdentity);
					
					abr.addDebug("----------------------- End Type Model Upgrate for different type -----------------------");
				}
				
				if (!isTMUpgPromoted || isTMUpgChanged) {					
					needUpdateParkingTable = true;
					abr.addDebug("Set needUpdateParkingTable to " + needUpdateParkingTable);
				}
			}
			// 4.4 Promote WDFM Announcement for MODELCONVERT Withdraw From Market
			abr.addDebug("----------------------- Start Promote WDFM Announcement for MODELCONVERT Withdraw From Market -----------------------");
			boolean isTmuwPromoted = false;
			boolean isTmuwChanged = false;
			
			TypeModel tmObj = getTypeModelForCreateMTC(toModelItem);
			for (int i = 0; i < lastOrderAvailVct.size(); i++) {
				
				EntityItem lastOrderAvailItem = (EntityItem)lastOrderAvailVct.get(i);

				abr.addDebug("Promote WDFM Announcement for " + lastOrderAvailItem.getKey());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date wdfmDate = sdf.parse(getAttributeValue(lastOrderAvailItem, AVAIL_EFFECTIVEDATE));
				
				Vector generalareaVct = PokUtils.getAllLinkedEntities(lastOrderAvailItem, "AVAILGAA", GENERALAREA);
				List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(generalareaVct);
				
				if (!isResendFull && isPassedAbrExist) {
					if (t1EntityList != null && t1MdlCvtItem != null) {
						Vector t1AvailVct = PokUtils.getAllLinkedEntities(t1MdlCvtItem, "MODELCONVERTAVAIL", AVAIL);
						Vector t1LastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(t1AvailVct, AVAIL_AVAILTYPE, LASTORDERAVAIL);
						// isTmuwChanged will update all country, isTmuwPromoted only update new country, check isTmuwChanged first
						isTmuwChanged = isTypeModelUpgrateChanged(t1LastOrderAvailVct, lastOrderAvailItem);
						if (!isTmuwChanged) {
							List<String> t2NewCountries = getNewCountries(t1LastOrderAvailVct, lastOrderAvailItem);
							if (t2NewCountries.size() > 0) {
								isTmuwPromoted = false;
								salesOrgPlantList = getAllSalesOrgPlantByCountryList(salesOrgPlantList, t2NewCountries);
							} else {
								isTMUpgPromoted = true;
							}
						}					
					}
				}
				abr.addDebug("isTmuwPromoted: " + isTmuwPromoted + " isTmuwChanged: " + isTmuwChanged);	
				
				CHWAnnouncement chwA = new CHWAnnouncement();
				chwA.setAnnDocNo(annDocNo);
				chwA.setSegmentAcronym(getSegmentAcronymForAnn(mdlCvtItem));

				CHWGeoAnn chwAg = new CHWGeoAnn();
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Vector annVect = PokUtils.getAllLinkedEntities(lastOrderAvailItem, "AVAILANNA", ANNOUNCEMENT);
				if (annVect != null && annVect.size() > 0) {
					EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
					chwA.setAnnouncementType(getAttributeValue(annItem, ANNOUNCEMENT_ANNTYPE)); //  WS logic:if feed is designated as "ePIMS/SW Migration", then set to "MIG" else set to "RFA", so flag or desc are all fine.
					chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, ANNDATE)));
				} else {
					chwA.setAnnouncementType(ANNTYPE_lONG_NEW);
					chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(mdlCvtItem, ANNDATE)));
				}
				abr.addDebug("CHWAnnouncement: " + chwA.toString());
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
				
				
				if (!isTmuwPromoted || isTmuwChanged) {
					LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(tmUPGObj);
					for (SalesOrgPlants salesOrgPlants : salesOrgPlantList) {
						String salesOrg = salesOrgPlants.getSalesorg();	
						LifecycleData lcd = rdhRestProxy.r200(lcdGen.getMaterial(), lcdGen.getVarCond(), annDocNo, "wdfm", pimsIdentity, salesOrg);
						abr.addDebug("Call r200 successfully for SalesOrg " + salesOrg + " " + lcd);
						updateWDFMLifecyle(lcd, lcdGen.getVarCond(), lcdGen.getMaterial(), wdfmDate, annDocNo, pimsIdentity, salesOrg);
						abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + salesOrg);
						
						//new 
						Vector<String> plantValues = salesOrgPlants.getPlants();					
						Vector vectTaxList = getTaxListBySalesOrgPlants(salesOrgPlants);
						for (String plantValue : plantValues) {
							if(plantValue.equals("1999")){
								abr.addDebug("skip plant " + plantValue);
								continue;
							}
							rdhRestProxy.r102(chwA, tmObj, plantValue, "MTC", tmUPGObj, MTCTOTYPE, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAg, wdfmDate); // MTC
							abr.addDebug("Call R102 MTC successfully for plant " + plantValue);
						}
					}
				}
			}
			abr.addDebug("----------------------- End Promote WDFM Announcement for MODELCONVERT Withdraw From Market -----------------------");
			
			if (needReleaseParkingTable()) {
				rdhRestProxy.r144(annDocNo, "R", pimsIdentity);
				abr.addDebug("Call R144 update parking table successfully");
			} else {
				rdhRestProxy.r144(annDocNo, "H", pimsIdentity);
			}
		} finally {
			// Set SYSFEEDRESEND to No
			if (isResendFull && mdlCvtItem != null) {
				setFlagValue(ATTR_SYSFEEDRESEND, SYSFEEDRESEND_NO, mdlCvtItem);
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
	
	@SuppressWarnings("rawtypes")
	private void updateFromToRevenueProfileBom(EntityItem mdlItem, String fromToType, TypeModelUPGGeo tmUPGObj, CHWAnnouncement chwA, Set<String> plants, String pimsIdentity, Vector deletedAuoMaterails) throws Exception {
		RevProfile revProfile = getRevProfile(mdlItem);
		if (revProfile.getRevenueProfile() != null) {
			String type = "";
			if (FROMTYPE.equals(fromToType)) {
				tmUPGObj.setFromTMRevProfileExist(true);
				tmUPGObj.setFromTMRevProfile(revProfile);
				type = tmUPGObj.getFromType();
			} else if (TOTYPE.equals(fromToType)) {
				tmUPGObj.setToTMRevProfileExist(true);
				tmUPGObj.setToTMRevProfile(revProfile);
				type = tmUPGObj.getType();
			} else {
				throw new RfcAbrException("updateFromToRevenueProfileBom not found the fromToType " + fromToType);
			}
			boolean typeMTCExists = rdhRestProxy.r204(type + "MTC");
			abr.addDebug("Call R204 successfully for " + fromToType + " typeMTCExists " + typeMTCExists);
			if (typeMTCExists) {
				rdhRestProxy.r205(null, tmUPGObj, "MTC", fromToType, null, null, null, pimsIdentity, chwA.getAnnDocNo());
				abr.addDebug("Call R205 MTC successfully for " + fromToType);
				for (String plant : plants) {
					updateRevenueProfileBom(type,
							chwA.getAnnDocNo(),
							revProfile.getAuoMaterials(), null,
							revProfile.getRevenueProfile(),
							"MTC", getOpwgId(), pimsIdentity, plant, deletedAuoMaterails);
					abr.addDebug("updateRevenueProfileBom successfully for plant " + plant);
				}
			}
		} else {
			abr.addDebug("updateFromToRevenueProfileBom not found RevProfile for " + fromToType + " " + mdlItem.getKey());
		}
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isTypeModelUpgrateChanged(Vector t1AvailItemVct, EntityItem t2AvailItem) throws RfcAbrException {
		EntityItem t1Avail = getEntityItemAtT1(t1AvailItemVct, t2AvailItem);
		if (t1Avail != null) {
			String t1Date = getAttributeValue(t1Avail, AVAIL_EFFECTIVEDATE);
			String t2Date = getAttributeValue(t2AvailItem, AVAIL_EFFECTIVEDATE);
			if (!t2Date.equals(t1Date)) {
				abr.addDebug("isTypeModelUpgrateChanged true T1 Date " + t1Date + " T2 Date " + t2Date);
				return true;
			}
		} else {
			abr.addDebug("isTypeModelUpgrateChanged true AVAIL is null at T1 for " + t2AvailItem.getKey());
			return true;
		}
		abr.addDebug("isTypeModelUpgrateChanged false");
		return false;
	}

	private void createMTCForFromToType(String type, String model, EntityItem modelItem, String fromToType,
			List<SalesOrgPlants> salesOrgPlantList, CHWAnnouncement chwA,
			CHWGeoAnn chwAg, TypeModelUPGGeo tmUPGObj, String extraplant,
			String pimsIdentity, Set<String> plants, String flfilcd, Vector generalareaVct) throws RfcAbrException, Exception {
		TypeModel tmObj = getTypeModelForCreateMTC(modelItem);
		tmUPGObj.setProductHierarchy(tmObj.getProductHierarchy());
		tmUPGObj.setLoadingGroup(tmObj.getLoadingGroup());
		abr.addDebug("TypeModelUPGGeo ProductHierarchy " + tmUPGObj.getProductHierarchy() + " LoadingGroup " + tmUPGObj.getLoadingGroup());
		abr.addDebug("TypeModel " + tmObj.toString());
		createMTC(chwA, tmObj, chwAg, tmUPGObj, extraplant,
				tmObj.getLoadingGroup(), fromToType, pimsIdentity,
				salesOrgPlantList, plants, flfilcd, generalareaVct);
		rdhRestProxy.r202(type, chwA, pimsIdentity);
		abr.addDebug("Call R202 successfully");
	}
	
	private TypeModel getTypeModelForCreateMTC(EntityItem modelItem) throws HWPIMSAbnormalException, RfcAbrException {
		TypeModel tm = new TypeModel();
		tm.setType(getAttributeFlagValue(modelItem, MODEL_MACHTYPEATR));
		tm.setModel(getAttributeValue(modelItem, MODEL_MODELATR));
		tm.setLoadingGroup(getLoadingGroup(modelItem));
		tm.setDiv(getDiv(modelItem));
		tm.setProductHierarchy(getProdHireCd(modelItem));
		tm.setProfitCenter(getAttributeFlagValue(modelItem, MODEL_PRFTCTR));
		tm.setVendorID("");
		return tm;
	}
	
	@SuppressWarnings("rawtypes")
	private void createMTC(CHWAnnouncement announcement, TypeModel tmObj,
			CHWGeoAnn chwAnnGeoObj, TypeModelUPGGeo tmUPGObj,
			String extraplant, String loadingGrp, String fromToType,
			String pimsIdentity, List<SalesOrgPlants> salesOrgPlants,
			Set<String> plants, String flfilcd, Vector generalareaVct)
			throws RfcAbrException, Exception {
		abr.addDebug("----------------------- Start createMTC for " + fromToType + " -----------------------");
		rdhRestProxy.r100(announcement, tmObj, chwAnnGeoObj, "MTC", tmUPGObj, fromToType, pimsIdentity); // MTC
		abr.addDebug("Call R100 MTC successfully");
		for (String plantValue : plants) {
			rdhRestProxy.r101(announcement, tmObj, chwAnnGeoObj, "MTC", loadingGrp, tmUPGObj, fromToType, pimsIdentity, plantValue); // MTC
			abr.addDebug("Call R101 MTC successfully for plant " + plantValue);
		}
		StringTokenizer stoken = new StringTokenizer(extraplant, ",");
		while (stoken.hasMoreElements()) {
			String sPlant = stoken.nextToken();
			rdhRestProxy.r189(announcement, tmObj, sPlant, "MTC", tmUPGObj, fromToType, pimsIdentity, chwAnnGeoObj);
			abr.addDebug("Call R189 MTC successfully for plant " + sPlant);
		}

		for (SalesOrgPlants salesOrgPlant : salesOrgPlants) {
			Vector<String> plantValues = salesOrgPlant.getPlants();
			String salesOrg = salesOrgPlant.getSalesorg();
			Vector vectTaxList = getTaxListBySalesOrgPlants(salesOrgPlant);
			for (String plantValue : plantValues) {
				if(plantValue.equals("1999")){
					abr.addDebug("skip plant " + plantValue);
					continue;
				}
				rdhRestProxy.r102(announcement, tmObj, plantValue, "MTC", tmUPGObj, fromToType, pimsIdentity, flfilcd, salesOrg, vectTaxList, chwAnnGeoObj, null); // MTC
				abr.addDebug("Call R102 MTC successfully for plant " + plantValue);
			}
		}
		// 1642763: Tax support for US when product is set for ZA(south Africa) 
		if (isGENERALAREAContainsZAButNoUS(generalareaVct)) {
			processTaxSupportForUSWhenProductIsSetForZA(tmObj, announcement, chwAnnGeoObj, pimsIdentity, flfilcd, true, tmUPGObj, fromToType, null, null);
		}
		rdhRestProxy.r103(tmObj, "MTC", announcement, tmUPGObj, fromToType, pimsIdentity); // MTC
		abr.addDebug("Call R103 MTC successfully");
		rdhRestProxy.r104(tmObj, "MTC", announcement, tmUPGObj, fromToType, pimsIdentity); // MTC
		abr.addDebug("Call R104 MTC successfully");
		rdhRestProxy.r157(announcement, tmUPGObj, fromToType, pimsIdentity);
		abr.addDebug("Call R157 successfully");
		rdhRestProxy.r159(announcement, tmUPGObj, fromToType, pimsIdentity);
		abr.addDebug("Call R159 successfully");
		//Defect 1855454 at 20180530
		rdhRestProxy.r162(null, tmUPGObj, "MTC", announcement, fromToType, pimsIdentity); // MTC
		abr.addDebug("Call R162 MTC successfully");
		rdhRestProxy.r168(tmUPGObj, announcement, fromToType, pimsIdentity);
		abr.addDebug("Call R168 successfully");
		rdhRestProxy.r175(null, tmUPGObj, announcement, "MTC", fromToType, pimsIdentity);
		abr.addDebug("Call R175 MTC successfully");
		rdhRestProxy.r164(tmUPGObj, announcement, fromToType, pimsIdentity);
		abr.addDebug("Call R164 successfully");
		rdhRestProxy.r123(null, tmUPGObj, "MTC", announcement, fromToType, pimsIdentity);
		abr.addDebug("Call R123 MTC successfully");
		rdhRestProxy.r165(announcement, tmUPGObj, fromToType, pimsIdentity);
		abr.addDebug("Call R165 successfully");
		//end
		
		// MC Stuff.
		if (fromToType.equals(MTCTOTYPE)) {
			boolean typeMCclassExists = rdhRestProxy.r214(tmUPGObj.getType());
			abr.addDebug("Call R214 successfully for MTCTOTYPE typeMCclassExists " + typeMCclassExists);
			if (typeMCclassExists) {
				rdhRestProxy.r125(tmUPGObj.getType(), announcement, "MTC", pimsIdentity);
				abr.addDebug("Call R125 MTC successfully for MTCTOTYPE");
			}
		}
		if (fromToType.equals(MTCFROMTYPE)) {
			boolean typeMCclassExists = rdhRestProxy.r214(tmUPGObj.getFromType());
			abr.addDebug("Call R214 successfully for MTCFROMTYPE typeMCclassExists " + typeMCclassExists);
			if (typeMCclassExists) {
				rdhRestProxy.r125(tmUPGObj.getFromType(), announcement, "MTC", pimsIdentity);
				abr.addDebug("Call R125 MTC successfully for MTCFROMTYPE");
			}
		}
		if (fromToType.equals(MTCTOTYPE)) {
			if (isRPQToType) {
				rdhRestProxy.r138(tmUPGObj, "MTC", announcement, fromToType, pimsIdentity);
				abr.addDebug("Call R138 MTC successfully for MTCTOTYPE");
			}
		}
		if (fromToType.equals(MTCFROMTYPE)) {
			if (isRPQFromType) {
				rdhRestProxy.r138(tmUPGObj, "MTC", announcement, fromToType, pimsIdentity);
				abr.addDebug("Call R138 MTC successfully for MTCFROMTYPE");
			}
		}
		for (int fRanges = 0; fRanges <= 9; fRanges++) {
			String featRanges = fRanges + "000";
			rdhRestProxy.r136(tmUPGObj, featRanges, announcement, "MTC", fromToType, pimsIdentity);
			abr.addDebug("Call R136 MTC successfully for range " + featRanges);
			rdhRestProxy.r137(tmUPGObj, featRanges, announcement, "MTC", fromToType, pimsIdentity);
			abr.addDebug("Call R137 MTC successfully for range " + featRanges);
		}
		abr.addDebug("----------------------- End createMTC for " + fromToType + " -----------------------");
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isRPQType(Vector items) throws RfcAbrException {
		for (int i = 0; i < items.size(); i++) {
			EntityItem MACHTYPEItem = (EntityItem)items.get(i);
			if (MACHTYPE_RPQRANGE_YES.equals(getAttributeValue(MACHTYPEItem, MACHTYPE_RPQRANGE))) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private EntityItem getModelByMachTypeAndModel(String type, String model) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, RfcAbrException {
		String searchAction = "SRDMODEL4";
		String srchType = "MODEL";
		StringBuffer debugSb = new StringBuffer();
		Vector attrVct = new Vector();
		Vector valVct = new Vector();
		attrVct.add(MODEL_MACHTYPEATR);
		valVct.add(type);
		attrVct.add(MODEL_MODELATR);
		valVct.add(model);
		attrVct.add(MODEL_COFCAT);
		valVct.add(HARDWARE);
		abr.addDebug("getModelsByMachTypeAndModel searchAction " + searchAction + " srchType " + srchType 
				+ " with role=" + abr.getProfile().getRoleCode());
		EntityItem[] items = ABRUtil.doSearch(abr.getDatabase(), abr.getProfile(), searchAction, srchType, false, attrVct, valVct, debugSb);
		abr.addDebug("getModelsByMachTypeAndModel " + debugSb.toString());
		if (items != null && items.length > 0) {
			abr.addDebug("getModelsByMachTypeAndModel MODEL size " + items.length);
			return items[0];			
		}
		throw new RfcAbrException("There is no hardware MODEL Entity for MACHTYPEATR " + type + " MODELATR " + model);
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isMachTypePromoted(Vector machTypeItems) throws RfcAbrException {
		for (int i = 0; i < machTypeItems.size(); i++) {
			EntityItem item = (EntityItem)machTypeItems.get(i);
			String promoted = getAttributeFlagValue(item, MACHTYPE_PROMOTED);
			if (MACHTYPE_PROMOTED_YES.equals(promoted)) {
				abr.addDebug("isMachTypePromoted " + item.getKey() + " PROMOTED attr value " + promoted);
				return true;
			}
		}
		abr.addDebug("isMachTypePromoted not found promoted MACHTYPE");
		return false;
	}
	
	private EntityItem getModelByVe2(String entityType, int entityId) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		String dts = abr.getCurrentTime();
		return getModelByVe2(entityType, entityId, dts, dts);
	}
	
	private EntityItem getModelByVe2(String entityType, int entityId, String t1DTS, String t2DTS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		Profile profileT2 = abr.switchRole(ROLE_CODE);
		profileT2.setValOnEffOn(t1DTS, t1DTS);
        profileT2.setEndOfDay(t2DTS); 
        profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
        profileT2.setLoginTime(t2DTS); 
		EntityList entityList = getEntityList(abr.getDatabase(), profileT2, getVeName2(), entityType, entityId);
		abr.addDebug("getModelByVe2 EntityList for " + profileT2.getValOn() + " extract " + getVeName2() + " contains the following entities: \n" +
                PokUtils.outputList(entityList));
		return entityList.getParentEntityGroup().getEntityItem(0);
	}
	
	@SuppressWarnings("rawtypes")
	private void createSalesBOMforTypeMTC(String mtctype, String mtcmodel, CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		abr.addDebug("----------------------- Start createSalesBOMforTypeMTC for " + mtctype + " -----------------------");
		boolean typeMTCExists = rdhRestProxy.r204(mtctype + "MTC");
		abr.addDebug("Call R204 successfully typeMTCExists " + typeMTCExists);
		if (typeMTCExists) {
			List<String> plantList = new ArrayList<>();
			plantList.add("1222");
			plantList.add("1999");
			for (String _plant : plantList) {
				// start lock
				String fileName = "./locks/MODELCONVERT" + mtctype + _plant + ".lock";
				File file = new File(fileName);
				new File(file.getParent()).mkdirs();	
				try (FileOutputStream fos = new FileOutputStream(file); FileChannel fileChannel = fos.getChannel()) {
					while (true) {
						try {
							FileLock fileLock = fileChannel.tryLock();
							if (fileLock != null) {
								abr.addDebug("Start lock, lock file " + fileName);
								// lock content
								Vector typeNEW = rdhRestProxy.r210(mtctype, "NEW", _plant);
								abr.addDebug("Call R210 NEW successfully for plant " + _plant);
								if(typeNEW == null || typeNEW.size() == 0){
									abr.addDebug("Call R210 NEW successfully typeNEW is null ");
									break;
								}					
								abr.addDebug("Reading Sales Bom for TypeNEW: " + typeNEW.size());
								try{
									Vector typeMTC = rdhRestProxy.r210(mtctype, "MTC", _plant);
									abr.addDebug("Call R210 MTC successfully");
									if (typeMTC != null) {
										abr.addDebug("Reading Sales Bom for TypeMTC: " + typeMTC.size());
									}
									if (typeMTC == null || typeMTC.size() == 0) {
										// Set Dep_Intern value
										Vector newMTCComponents = getComponentByTypeModel(mtctype, mtcmodel, typeNEW);
										rdhRestProxy.r211(mtctype, _plant, newMTCComponents, "MTC", chwA, pimsIdentity);
										abr.addDebug("createSalesBOMforTypeMTC no row returned for typeMTC, R211 create MTC components size: " + newMTCComponents.size());
									} else {
										Vector componentstoDeleteTypeMTC = getComponentsintypeMTCwithtypeNEW(typeMTC, typeNEW);
										if (componentstoDeleteTypeMTC != null) {
											rdhRestProxy.r212(mtctype, _plant, componentstoDeleteTypeMTC, "MTC", chwA, pimsIdentity);
											abr.addDebug("createSalesBOMforTypeMTC r212 delete typeMTC size: " + componentstoDeleteTypeMTC.size());
										}
										Vector updatecomponentsforTypeMTC = getComponentsintypeNEW(typeMTC, typeNEW);
										if (updatecomponentsforTypeMTC != null) {
											Vector updateMTC = getComponentByTypeModel(mtctype, mtcmodel, updatecomponentsforTypeMTC);
											abr.addDebug("createSalesBOMforTypeMTC updateMTC size:" + updateMTC.size());
											System.out.println("createSalesBOMforTypeMTC updateMTC size:" + updateMTC.size());
											if (updateMTC.size() > 0) {
												rdhRestProxy.r213(mtctype, _plant, updateMTC, "MTC", chwA, pimsIdentity);
												abr.addDebug("Call R213 MTC successfully");
											}
										}
									}
									break;
								}catch (HWPIMSNotFoundInMastException e){
									abr.addDebug("Read Sales Bom has met an error: " + e);
									Vector newMTCComponents = getComponentByTypeModel(mtctype, mtcmodel, typeNEW);
									rdhRestProxy.r211(mtctype, _plant, newMTCComponents, "MTC", chwA, pimsIdentity);
									abr.addDebug("createSalesBOMforTypeMTC no row returned for typeMTC, R211 create MTC components size: " + newMTCComponents.size());	
									break;
								}
								// end lock content
							} else {
								abr.addDebug("fileLock == null");
								Thread.sleep(5000);
							}
						} catch (OverlappingFileLockException e1) {
							abr.addDebug("other abr is running createSalesBOMforTypeMTC");
							Thread.sleep(5000);
						}
					}			
				}
				abr.addDebug("End lock");
				// end lock				
			}		
		}
		abr.addDebug("----------------------- End createSalesBOMforTypeMTC for " + mtctype + " -----------------------");
	}
	
	/**
	 * Set Dep_Intern value which is required by caller
	 * @param type
	 * @param model
	 * @param updateComponents
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector getComponentByTypeModel(String type, String model, Vector updateComponents) {
		Vector updatemtc = new Vector();
		String component = type + model;
		for (Object obj : updateComponents) {
			DepData tmg = (DepData)obj;
			if (component.equals(tmg.getComponent())) {
				tmg.setDep_Intern("SC_MK_" + type + "_MODEL_" + model);
				updatemtc.add(tmg);
			}
		}
		return updatemtc;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getComponentsintypeMTCwithtypeNEW(Vector typeModelDeleted,
			Vector hashtypemodelbom) {

		Vector notmatch = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = hashtypemodelbom.elements();
		Enumeration dapiter = typeModelDeleted.elements();
		abr.addDebug("Vecor Size for typeMTC" + typeModelDeleted.size());
		abr.addDebug("Vecor Size for typeNEW" + hashtypemodelbom.size());

		DepData dDataNEW = new DepData();
		while (sapiter.hasMoreElements()) {
			dDataNEW = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataNEW.getComponent();
			abr.addDebug("vector typeMTC component value"
					+ dDataNEW.getComponent());
			vect1.addElement(newtypecomponet);
		}
		while (dapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) dapiter.nextElement();
			String component = dDataMTC.getComponent();
			abr.addDebug("vector typeNEW component value"
					+ dDataMTC.getComponent());
			if ((vect1.contains(component))) {
				if (notmatch != null && notmatch.size() == 0)
					notmatch = null;
			} else {
				if (notmatch == null) {
					notmatch = new Vector();
				}
				notmatch.add(dDataMTC);
			}
		}
		return notmatch;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector getComponentsintypeNEW(Vector typeModelDeleted, Vector hashtypemodelbom) {
		Vector updatemtc = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = typeModelDeleted.elements();
		Enumeration dapiter = hashtypemodelbom.elements();
		while (sapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataMTC.getComponent();
			vect1.addElement(newtypecomponet);
			abr.addDebug("vector typeMTC component value" + dDataMTC.getComponent());
		}
		abr.addDebug("vector size of vect1" + vect1.size());

		while (dapiter.hasMoreElements()) {
			DepData dDataNEW = (DepData) dapiter.nextElement();
			String component = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value" + dDataNEW.getComponent());
			if ((vect1.contains(component))) {
				if (updatemtc != null && updatemtc.size() == 0)
					updatemtc = null;
			} else {
				if (updatemtc == null) {
					updatemtc = new Vector();
				}
				updatemtc.add(dDataNEW);
			}
		}
		return updatemtc;
	}

	public String getVeName() {
		return "RFCMODELCONVERT";
	}
	
	private String getVeName2() {
		return "RFCMODELCONVERT2";
	}
}
