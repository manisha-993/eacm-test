package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.HWPIMSException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.rdh.chw.entity.BasicMaterialFromSAP;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.transform.oim.eacm.util.PokUtils;

public class RFCMODELABR extends RfcAbrAdapter {
	
	private static List<String> modelMarkChangedAttrs;
	
	static {
		modelMarkChangedAttrs = new ArrayList<>();
		modelMarkChangedAttrs.add("MKTGNAME");
		modelMarkChangedAttrs.add("LICNSINTERCD");
		modelMarkChangedAttrs.add("BHPRODHIERCD");
	}
	
	public RFCMODELABR(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}
	
	public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
		abr.addDebug("Start processThis()");
		// ----------------------- Get values from EACM entities -----------------------
		// MODEL
		EntityItem modelItem = getRooEntityItem();
		// AVAIL
		Vector availVct = PokUtils.getAllLinkedEntities(modelItem, "MODELAVAIL", "AVAIL");
		Vector planAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
		abr.addDebug("MODELAVAIL all availVct: " + availVct.size() + " plannedavail: " + planAvailVct.size());
		if (planAvailVct.size() == 0) {
			throw new RfcAbrException("There is no avail linked to this MODEL, it will not promote this MODEL");
		}
		// ANNOUNCEMENT
		EntityItem[] annItems = getEntityItems("ANNOUNCEMENT");
		if (annItems == null || annItems.length == 0) {
			throw new RfcAbrException("There is no ANNOUNCEMENT for this MODEL, it will not promote this MODEL");
		}		
		// MACHTYPE
		Vector machtypeVct = PokUtils.getAllLinkedEntities(modelItem, "MODELMACHINETYPEA", "MACHTYPE");		
		// TAXCATG
		Vector<CntryTax> taxList = getAllTaxList(modelItem);
		abr.addDebug("CntryTax size: " + taxList.size());
		String machTypeValue = getAttributeFlagValue(modelItem, "MACHTYPEATR");
		
		// ----------------------- Create RFC input entities -----------------------
		// Type Model
		TypeModel typeModel = new TypeModel();
		typeModel.setType(machTypeValue);
		typeModel.setModel(getAttributeValue(modelItem, "MODELATR"));
		typeModel.setDiv(getDiv());
		// This field stores the barcode equivalent of the UPC - Code. It is a unique valid Number assigned by Vienna, 
		// equivalent to EAN-Code in Europe and JAN-Code in Japan. This field can be left blank.
		typeModel.setEanUPCCode(""); 
		typeModel.setProductHierarchy(getProdHireCd(modelItem));
		typeModel.setDescription(getAttributeValue(modelItem, "MKTGNAME")); 
		// Re: blockers of CHW Promote Type Model logic 05/10/2016 12:27 AM
		// Set it to Null. Not being used in EACM
		typeModel.setVendorID("");
		typeModel.setAcctAsgnGrp(getAttributeFlagValue(modelItem, "ACCTASGNGRP"));
		String profitCenter = getAttributeFlagValue(modelItem, "PRFTCTR");
		typeModel.setProfitCenter(profitCenter);
		// if "CE" yes rest is "N"  
		typeModel.setCustomerSetup("CE".equals(getAttributeValue(modelItem, "INSTALL")));
		typeModel.setLicenseCode("Yes".equals(getAttributeValue(modelItem, "LICNSINTERCD"))); 
		typeModel.setSystemType(getAttributeFlagValue(modelItem, "SYSTEMTYPE"));		
		typeModel.setCPU("S00010".equals(getAttributeFlagValue(modelItem, "SYSIDUNIT"))); // When MODEL.SYSIDUNIT = "SIU - CPU"
		typeModel.setLoadingGroup(getAttributeFlagValue(modelItem, "MODELORDERCODE"));
		abr.addDebug("TypeModel:" + typeModel.toString());
		
		// epims code, when both XCC and ZIP then BTH, when XCC then XCC, when ZIP then ZIP, when no value then EMPTY
		// we don't have XCC any more, so always set it to ZIP
		// but checked with Rupal we used AVAIL,ORDERSYSNAME as flfilcd, and always AAS
		String flfilcd = "AAS";
		abr.addDebug("Flfilcd: " + flfilcd);
		
		// XCC=X， else C,　we don't have XCC so always C now
		String pimsIdentity = "C";
		abr.addDebug("PimsIdentity: " + pimsIdentity);
		
		// only r118 use this value， and only xcc will set warrPeriod value, so set it to null
		// epims ： if ((flfilcd != null && flfilcd.equalsIgnoreCase("XCC")) && (warrantyPeriod != null))
		String warrPeriod = null;
		abr.addDebug("WarrPeriod: " + warrPeriod);
		

		boolean isTMPromoted = false;
		boolean isTMChanged = true;
		boolean isTMGeoPromoted = false;
		boolean isTMGeoChanged = true;
		// ----------------------- Check TypeModel promoted or changed -----------------------
		AttributeChangeHistoryGroup rfcAbrHistory = getAttributeHistory(modelItem, RFCABRSTATUS);
		boolean isPassedAbrExist = existBefore(rfcAbrHistory, STATUS_PASSED);
		abr.addDebug("Is passed RFCABRSTATUS exist before: " + isPassedAbrExist);
		EntityList t1EntityList = null;
		EntityItem t1ModelItem = null;
		if (isPassedAbrExist) {
			isTMPromoted = true;
			String t1DTS = getLatestValFromForAttributeValue(rfcAbrHistory, STATUS_PASSED);
			if (t1DTS == null || "".equals(t1DTS)) { // not found passed value, all value thought change
				abr.addDebug("t1DTS is null");
			} else {
				String t2DTS = abr.getCurrentTime();
				Profile profileT1 = switchRole(ROLE_CODE);
	            profileT1.setValOnEffOn(t1DTS, t1DTS);
	            profileT1.setEndOfDay(t2DTS); 
	            profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
	            profileT1.setLoginTime(t2DTS);
	            abr.addDebug("Get t1 entity list for t1DTS: " + t1DTS + " t2DTS: " + t2DTS);
	            t1EntityList = getEntityList(profileT1);
	            abr.addDebug("EntityList for T1 " + profileT1.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + PokUtils.outputList(t1EntityList));
	            
	            t1ModelItem = t1EntityList.getParentEntityGroup().getEntityItem(0);
	            isTMChanged = isTypeModelChanged(modelItem, t1ModelItem, modelMarkChangedAttrs);
			}			
		}		
		
		// ----------------------- Promote Type Model for each ANNOUNCEMENT -----------------------
		for (int i = 0; i < planAvailVct.size(); i++) {
			EntityItem availItem = (EntityItem)planAvailVct.get(i);
			Vector annVect = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", "ANNOUNCEMENT");
			if (annVect != null && annVect.size() > 0) {
				EntityItem annItem = (EntityItem) annVect.get(0); // AVAIL must only link one ANNOUNCEMENT
				abr.addDebug("Promote MODEL for " + annItem.getKey() + " " + availItem.getKey());
				
				// Get all salesorg and plants from GENERALAREA linked to AVAIL
				Vector generalareaVct = PokUtils.getAllLinkedEntities(availItem, "AVAILGAA", "GENERALAREA");
				List<SalesOrgPlants> salesorgPlantsVect = getAllSalesorgPlants(generalareaVct);
				abr.addDebug("GENERALAREA size: " + generalareaVct.size() + " SalesorgPlants size: " + salesorgPlantsVect.size());
				Set<String> plants = getAllPlants(salesorgPlantsVect);
				abr.addDebug("All plants size: " + plants.size() + " values: " + plants);
				
				// ----------------------- Check TypeModelGeo promoted or changed -----------------------
				if (isTMPromoted) {
					if (t1EntityList != null) {
						EntityItem[] t1Avails = getEntityItems(t1EntityList, "AVAIL");							
						isTMGeoPromoted = isTypeModelGeoPromoted(t1Avails, availItem);
						if (isTMGeoPromoted) {
							EntityItem[] t1Anns = getEntityItems(t1EntityList, "ANNOUNCEMENT");
							EntityItem t1AnnItem = getEntityItemAtT1(t1Anns, annItem);
							if (t1AnnItem != null) {
								EntityItem t1AvailItem = getEntityItemAtT1(t1Avails, availItem);
								Vector gaVct = PokUtils.getAllLinkedEntities(t1AvailItem, "AVAILGAA", "GENERALAREA");
								List<SalesOrgPlants> salesOrgPlantsVct = getAllSalesorgPlants(generalareaVct);
								Set<String> t1Palnts = getAllPlants(salesOrgPlantsVct);
								abr.addDebug("T1 plants size: " + t1Palnts.size() + " values: " + t1Palnts);
								
								isTMGeoChanged = isTypeModelGeoChanged(t1AnnItem, annItem, t1Palnts, plants);
							} else {
								abr.addDebug("There is no ann " + annItem.getKey() + " at T1");
							}							
						} else {
							isTMGeoChanged = true;
						}
					}						
				} else {
					isTMGeoChanged = true;
					isTMGeoPromoted = false;
				}
				abr.addDebug("isTMPromoted: " + isTMPromoted + " isTMChanged: " + isTMChanged + " isTMGeoPromoted: " + isTMGeoPromoted + " isTMGeoChanged: " + isTMGeoChanged);
				
				// ----------------------- Create RFC input entitie CHWAnnouncement and CHWGeoAnn -----------------------
				CHWAnnouncement chwA = new CHWAnnouncement();
				chwA.setAnnDocNo(typeModel.getType()); // Set to ‘MACHTYPE’ when first TYPE promote ; set to “MACHTYPE|MODELATR when MODEL_TYPE promote
				chwA.setAnnouncementType(getAttributeFlagValue(annItem, "ANNTYPE")); //  WS logic:if feed is designated as "ePIMS/SW Migration", then set to "MIG" else set to "RFA", so flag or desc are all fine.
				chwA.setSegmentAcronym(getSegmentAcronymForAnn(annItem));
				abr.addDebug("CHWAnnouncement: " + chwA.toString());
				
				CHWGeoAnn chwAg = new CHWGeoAnn();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, "ANNDATE")));
				abr.addDebug("CHWAnnouncementGEO: " + chwAg.toString());
				
				Hashtable newBom = new Hashtable();
				Hashtable addToBom = new Hashtable();
				Hashtable spItemCateg = new Hashtable();
				
				// ----------------------- First Model for Type Promote -----------------------
				// If Type not promoted (exists in TYPE table)
				if (!isTypePromoted(machtypeVct)) {
					abr.addDebug("----------------------- Start Type:" + typeModel.getType() + " not promoted -----------------------");

					// Create type NEW material basic view [R100]
					rdhRestProxy.r100(chwA, typeModel, chwAg, "NEW", null, null, pimsIdentity);
					// Create type UPG material basic view [R100]
					rdhRestProxy.r100(chwA, typeModel, chwAg, "UPG", null, null, pimsIdentity);
					abr.addDebug("Call R100 NEW and UPG successfully");					
					
					abr.addDebug("R101 Plant size:" + plants.size());
					for (String plantValue : plants) {
						// Create Generic Plant 1222 View for NEW material [R101]
						rdhRestProxy.r101(chwA, typeModel, chwAg, "NEW", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //NEW
						// Create Generic Plant 1222 View for UPG material [R101]
						rdhRestProxy.r101(chwA, typeModel, chwAg, "UPG", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //UPG
						abr.addDebug("Call R101 NEW and UPG successfully for plant " + plantValue);
					}
					
					// Extra Plants (CFI Plants ) reading from Properties File
					String extraplant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true); // WERKS Plant
					StringTokenizer stoken = new StringTokenizer(extraplant, ",");
					// For each CFI Plant
					while (stoken.hasMoreElements()) {
						String sPlant = stoken.nextToken();
						abr.addDebug("R189 Extra Plant :" + sPlant);
						// Create Plant View For NEW Material [R189]
						rdhRestProxy.r189(chwA, typeModel, sPlant, "NEW", null, null, pimsIdentity);
						// Create Plant View For UPG Material [R189]
						rdhRestProxy.r189(chwA, typeModel, sPlant, "UPG", null, null, pimsIdentity);
						abr.addDebug("Call R189 NEW and UPG successfully");
					}					
					
					abr.addDebug("R102 SalesOrgPlants size： " + salesorgPlantsVect.size());
					for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
						String salesOrg = salesorgPlants.getSalesorg();
						Vector vectTaxList = getTaxListByCountryName(taxList, salesorgPlants.getCountryList());
						Vector<String> _plants = salesorgPlants.getPlants();
						if (_plants != null && _plants.size() > 0) {
							for (String _plant : _plants) {
								if(_plant.equals("1999")){
									continue;
								}
								rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //NEW
								rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //UPG
								abr.addDebug("Call R102 NEW and UPG successfully for salesOrg:" + salesOrg + " plant:" + _plant);
							}
						} else {
							abr.addDebug("R102 no plant for salesorg:" + salesOrg);
						}
					}					

					rdhRestProxy.r103(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
					rdhRestProxy.r103(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
					abr.addDebug("Call R103 NEW and UPG successfully");
					rdhRestProxy.r104(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
					rdhRestProxy.r104(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
					abr.addDebug("Call R104 NEW and UPG successfully");
					rdhRestProxy.r106(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R106 successfully");
					
					// For each class range (0000, 1000 , . . . 9000)
					for (int fRanges = 0; fRanges <= 9; fRanges++) {
						String featRanges = fRanges + "000";
						String type = typeModel.getType();
						rdhRestProxy.r130(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R130 successfully for fRange:" + featRanges);
						rdhRestProxy.r176(type, featRanges, "NEW", chwA, pimsIdentity);
						rdhRestProxy.r176(type, featRanges, "UPG", chwA, pimsIdentity);
						abr.addDebug("Call R176 NEW and UPG successfully for fRange:" + featRanges);
						rdhRestProxy.r131(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R131 NEW and UPG successfully for fRange:" + featRanges);
						rdhRestProxy.r177(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R177 NEW and UPG successfully for fRange:" + featRanges);
						
						// Insert Type/Feature class row in PROMOTED_TYPECLASS (delayed)
						// type not promoted, save type and range to table
						// epims: ptypClassCol PromotedTypeClassCollection save promoted type, no more handler. we use promoted attribute of MACHTYPE
						// epims: nowPromotedTypeClasses will insert result to PROMOTED_TYPECLASS, we use promoted attribute of MACHTYPE
						// End of type not promoted will call setPromotedMachtypes(machtypeVct) to update the promoted attribute of MACHTYPE
						
					} // end for For loop
					rdhRestProxy.r108(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R108 successfully");
					rdhRestProxy.r110(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R110 successfully");
					rdhRestProxy.r150(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R150 successfully");
					rdhRestProxy.r123(typeModel.getType(), null, "NEW", chwA, null, pimsIdentity);
					rdhRestProxy.r123(typeModel.getType(), null, "UPG", chwA, null, pimsIdentity);
					abr.addDebug("Call R123 NEW and UPG successfully");
					rdhRestProxy.r160(typeModel, chwA, pimsIdentity);			
					abr.addDebug("Call R160 successfully");
					rdhRestProxy.r175(typeModel, null, chwA, "NEW", null, pimsIdentity);
					rdhRestProxy.r175(typeModel, null, chwA, "UPG", null, pimsIdentity);
					abr.addDebug("Call R175 NEW and UPG successfully");
					
					// Add to newBom Hashtable (key is Type+"||"+SAPPlant, value is empty vector)
					// Insert type into TYPE table (delayed)
				
					setPromotedMachtypes(machtypeVct);
					
					rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
					abr.addDebug("Call R144 update parking table successfully");
					abr.addDebug("----------------------- End Type:" + typeModel.getType() + " not promoted -----------------------");
					// End if Type not promoted
				} else {
					abr.addDebug("----------------------- Start Type:" + typeModel.getType() + " already promoted -----------------------");
					// each TypeModelGeo
					if (!isTMGeoPromoted) {		
						abr.addDebug("R102 SalesOrgPlants size： " + salesorgPlantsVect.size());
						for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
							String salesOrg = salesorgPlants.getSalesorg();
							Vector vectTaxList = getTaxListByCountryName(taxList, salesorgPlants.getCountryList());
							Vector<String> _plants = salesorgPlants.getPlants();
							if (_plants != null && _plants.size() > 0) {
								for (String _plant : _plants) {
									if(_plant.equals("1999")){
										continue;
									}
									rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //NEW
									rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //UPG
									abr.addDebug("Call R102 NEW and UPG successfully for salesOrg: " + salesOrg + " plant:" + _plant);
								}
							} else {
								abr.addDebug("R102 no plant for salesorg:" + salesOrg);
							}
						}
					} else {
						abr.addDebug("isTMGeoPromoted is: " + isTMGeoPromoted + ", will not call r102");
					}						
					abr.addDebug("----------------------- End Type:" + typeModel.getType() + " already promoted -----------------------");
				}				
				// epims : beforePromotedTypes
				
				// ----------------------- Type Model Promote -----------------------
				chwA.setAnnDocNo(typeModel.getType() + STRING_SEPARATOR + typeModel.getModel());
				abr.addDebug("Set chwA annDocNo: " + chwA.getAnnDocNo());
				SharedProductComponents spComponents = new SharedProductComponents();
				PlannedSalesStatus ps = new PlannedSalesStatus();
				
				String material = typeModel.getType() + typeModel.getModel();
				BasicMaterialFromSAP basicMaterialFromSAP = null;
//				try {
					basicMaterialFromSAP = rdhRestProxy.r209(material);
					abr.addDebug("Call R209 successfully, Read Basic View of Material from RDH, BasicMaterialFromSAP: MatlType=" + basicMaterialFromSAP.getMatlType() + " prodHierarchy=" + basicMaterialFromSAP.getProdHier());
//				} catch (HWPIMSAbnormalException e) {
//					abr.addDebug("Get exception: " + e.getMessage() + " from RFC, create a empty BasicMaterialFromSAP");
//					basicMaterialFromSAP = new BasicMaterialFromSAP();
//					basicMaterialFromSAP.setMatlType(null);
//					basicMaterialFromSAP.setProdHier(null);
//				}					
				String materialType = basicMaterialFromSAP.getMatlType();
				String productHierarchy = typeModel.getProductHierarchy();
				abr.addDebug("ProductHierarchy from EACM is " + productHierarchy);
				if ((materialType != null && !materialType.equals("")) && (isProductHierarchyDifferent(productHierarchy, basicMaterialFromSAP.getProdHier()))) {
					abr.addDebug("Material exists and ProductHierarchy is different");
					Vector supportedSalesOrgV = getSupportedSalesOrgCol(salesorgPlantsVect);
					abr.addDebug("All supported salesOrg size:" + supportedSalesOrgV.size() + " values:" + supportedSalesOrgV);
					callR260updateProdHierarchyOnSalesView(supportedSalesOrgV, typeModel, chwA, productHierarchy, pimsIdentity);
				} else {
					abr.addDebug("R260 will not be called");
				}
				
				// Check shared product
				if (!isTMGeoPromoted || isTMGeoChanged) {
					abr.addDebug("Type Model Promote : Check shared product");
					checkSharedProductInfo(typeModel.getType(), typeModel.getModel(), chwA.getAnnDocNo(), spComponents, pimsIdentity, "", false, materialType);
				}
				
				// epims : nowPromotingMTC
				// ----------------------- Type Model Promote : TypeModel is new -----------------------
				if (!isTMPromoted) {
					abr.addDebug("----------------------- Start Type Model Promote : TypeModel not promoted -----------------------");
					if (!spComponents.getSharedProduct()) {
						abr.addDebug("R115 plant size： " + plants.size());
						for (String plantValue : plants) {
							rdhRestProxy.r115(chwA, typeModel, chwAg, pimsIdentity, plantValue );
							abr.addDebug("Call R115 successfully for plant:" + plantValue);
						}
					}
					// epims : spItemCateg
					if (spComponents.getSharedProduct() && spComponents.getSharedProductMaterialType().equalsIgnoreCase("ZSEL")) {
						String spmat = typeModel.getType() + typeModel.getModel();
						if (!(spItemCateg.containsKey(spmat))) {
							spItemCateg.put(spmat, "spitem_cat");
						}
					}
					
					abr.addDebug("R116 plant size： " + plants.size());
					for (String _plant : plants) {
						// epims : type+plant = key >> addToBom，value = typemodel
						updateTypeNewUpgSalesBomInfo(addToBom, typeModel, _plant); 
						rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW1", pimsIdentity);
						abr.addDebug("Call R116 CHW1 successfully for plant:" + _plant);
						if(_plant.equals("1999")){
							continue;	
						}
						rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW2", pimsIdentity);
						abr.addDebug("Call R116 CHW2 successfully for plant:" + _plant);
					}
					// For More Plants .
					// Extra Plants (CFI Plants ) reading from Properties File .
					String extraplant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true);
					StringTokenizer stoken = new StringTokenizer(extraplant, ",");
					while (stoken.hasMoreElements()) {
						String sPlant = stoken.nextToken();
						boolean plantcheck = false;
//						if (spComponents.getSharedProduct()) {
//							plantcheck = rdhRestProxy.r207(typeModel.getType(), typeModel.getModel(), sPlant);
//							abr.addDebug("Call R207 successfully for Extra plant:" + sPlant);
//						}
						// SAP Ledger
						if ((!spComponents.getSharedProduct()) || ((spComponents.getSharedProduct()) && plantcheck == false)) {
							rdhRestProxy.r183(chwA.getAnnDocNo(), typeModel.getType() + typeModel.getModel(), sPlant, pimsIdentity, typeModel.getProfitCenter());
							abr.addDebug("Call R183 successfully");
						}
					}
					rdhRestProxy.r156(typeModel.getType() + typeModel.getModel(), typeModel.getDiv(), chwA, pimsIdentity, false);
					abr.addDebug("Call R156 successfully");
					
					// for each tmGeo
					abr.addDebug("salesorgPlantsVect size： " + salesorgPlantsVect.size());
					for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
						String salesOrg = salesorgPlants.getSalesorg();
						ps = checkForNewPlannedSalesStatus(chwAg.getAnnouncementDate(), false); 
						if ((salesOrg.equalsIgnoreCase("0147") && (!spComponents.getSharedProductIn0147()))|| !salesOrg.equalsIgnoreCase("0147")) {
							abr.addDebug("salesOrg equals 0147 and (spComponents not in 0147 or salesOrg not equals 0147)");
							Vector vectTaxList = getTaxListByCountryName(taxList, salesorgPlants.getCountryList());									
							Vector<String> _plants = salesorgPlants.getPlants();										
							if (_plants != null && _plants.size() > 0) {
								for (String plantValue : _plants) {
									if(plantValue.equals("1999")){
										continue;
									}
									rdhRestProxy.r117(chwA, typeModel.getType()+ typeModel.getModel(), typeModel.getDiv(), typeModel.getAcctAsgnGrp(), ps,true, pimsIdentity, flfilcd, salesOrg, typeModel.getProductHierarchy(),	vectTaxList, plantValue);
									abr.addDebug("Call R117 successfully for plant：" + plantValue);
								}
							}								
						}
						LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(typeModel);
						Date annDate = chwAg.getAnnouncementDate(); // epims: tmGeoObj.getAnnouncementDate(); confirmed with Bin we use ANNDATE of ANNOUNCEMENT
						String annDocNo = typeModel.getType() + STRING_SEPARATOR + typeModel.getModel(); // epims： tmGeoObj.getAnnDocNo(); Bin confirmed with Praveen we use MACHTYPE|MODELATR
						abr.addDebug("Start updateAnnLifecyle for salesOrg " + salesOrg);
						this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen.getMaterial(), annDate, annDocNo, chwA.getAnnouncementType(), pimsIdentity, salesOrg);
						abr.addDebug("End updateAnnLifecyle for salesOrg " + salesOrg);
					}
						// ipims : promotedTMGs, it will update TYPE_MODEL_GEO. we use t1 and t2 to handle if promoted
					 // End each tmGeo		
							
					if (spComponents.getSharedProduct()) {
//						rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, true, pimsIdentity);
//						abr.addDebug("Call R118 successfully for SharedProduct：" + true);
						rdhRestProxy.r119(typeModel.getType() + typeModel.getModel(), chwA, true, true, pimsIdentity);
						abr.addDebug("Call R119 successfully for SharedProduct：" + true);
					} else {
//						rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, false, pimsIdentity);
//						abr.addDebug("Call R118 successfully for SharedProduct：" + false);
						rdhRestProxy.r119(typeModel.getType() + typeModel.getModel(), chwA, false, true, pimsIdentity);
						abr.addDebug("Call R119 successfully for SharedProduct：" + false);
					}

					rdhRestProxy.r120(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R120 successfully");
					rdhRestProxy.r121(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R121 successfully");
					
					/**
					 * Following change is made for Return Plant--Begin
					 */
					for (String plnt  : plants) {						
						updateTypeNewUpgSalesBomInfo(addToBom, typeModel, plnt);
					}
					
					// epims : typeModelChgd, it will update TYPE_MODEL SET CHANGED=0, we use t1 and t2 to handle the change		

					// SAP Ledger
					if (configManager.getConfigManager().getString(PropertyKeys.KEY_SAP_LEDGER).equals("Y")) {
						boolean isProfitCenterChanged = false;
						if (t1ModelItem != null) {
							String t1ProfitCenter = getAttributeFlagValue(t1ModelItem, "PRFTCTR");
							if (!profitCenter.equals(t1ProfitCenter)) {
								isProfitCenterChanged = true;
							}
						} else {
							isProfitCenterChanged = true;
						}
						abr.addDebug("checkAndUpdateProfitCenter isProfitCenterChanged: " + isProfitCenterChanged);
//						MaterailPlantData matplantData = new MaterailPlantData();
						if (isProfitCenterChanged) {
							abr.addDebug("Start check and update profit center");
							for (String plant : plants) {
								rdhRestProxy.r262(chwA, material, plant, pimsIdentity, profitCenter);
							}
							abr.addDebug("End check and update profit center");
						}						
//						checkAndUpdateProfitCenter(matplantData, typeModel.getType() + typeModel.getModel(), pimsIdentity, typeModel.getProfitCenter(), chwA);						
					}
					abr.addDebug("----------------------- End Type Model Promote : TypeModel not promoted -----------------------");
				}// End for the if condition Type model is not promoted in any
				  // geo's and not promoted in any other announcements
				else {
					abr.addDebug("The MODEL already promoted");
				}
				// ----------------------- Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted -----------------------
				if (isTMChanged || isTMGeoChanged || !isTMGeoPromoted) {
					abr.addDebug("----------------------- Start Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted -----------------------");
					// each TypeModelGeo
					if (isTMGeoChanged || !isTMGeoPromoted) {
						// epims: changedTMGs promotedTMGs		
						abr.addDebug("salesorgPlantsVect size： " + salesorgPlantsVect.size());
						for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
							String salesOrg = salesorgPlants.getSalesorg();
							LifecycleDataGenerator lcdGen = new LifecycleDataGenerator(typeModel);
							Date annDate = chwAg.getAnnouncementDate(); // epims: tmGeoObj.getAnnouncementDate(); confirmed with Bin we use ANNDATE of ANNOUNCEMENT
							String annDocNo = typeModel.getType() + STRING_SEPARATOR + typeModel.getModel(); // epims： tmGeoObj.getAnnDocNo(); Bin confirmed with Praveen we use MACHTYPE|MODELATR
							abr.addDebug("Start updateAnnLifecyle");
							this.updateAnnLifecyle(lcdGen.getVarCond(), lcdGen .getMaterial(), annDate, annDocNo, chwA .getAnnouncementType(), pimsIdentity, salesOrg);
							abr.addDebug("End updateAnnLifecyle");
						}// End each TypeModelGeo						
					}
					
					abr.addDebug("R116 plant size： " + plants.size());
					for (String _plant : plants) {
						rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW1", pimsIdentity);
						abr.addDebug("Call R116 CHW1 successfully for plant:" + _plant);
						if(_plant.equals("1999")){
							continue;
						}
						rdhRestProxy.r116(chwA, typeModel, _plant, typeModel.getLoadingGroup(), chwAg, "CHW2", pimsIdentity);
						abr.addDebug("Call R116 CHW2 successfully for plant:" + _plant);
					}
					
					rdhRestProxy.r156(typeModel.getType() + typeModel.getModel(), typeModel.getDiv(), chwA, pimsIdentity, false);
					abr.addDebug("Call R156 successfully");
					
					abr.addDebug("checkSharedProductInfo");
					checkSharedProductInfo(typeModel.getType(), typeModel.getModel(), chwA.getAnnDocNo(), spComponents, pimsIdentity, "", false, basicMaterialFromSAP.getMatlType());
					
					if (!(spComponents.getSharedProduct())) {
						rdhRestProxy.r133(typeModel, chwA, pimsIdentity);
						abr.addDebug("Call R133 successfully for SharedProduct:" + false);
					}
					
					// epims : typeModelChgd, it will update TYPE_MODEL SET CHANGED=0, we use t1 and t2 to handle the change
					
					rdhRestProxy.r120(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R120 successfully");
					if ((spComponents.getSharedProduct())) {
//						rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, true, pimsIdentity);
//						abr.addDebug("Call R118 successfully for SharedProduct:" + true);
					} else {
//						rdhRestProxy.r118(typeModel, chwA, flfilcd, warrPeriod, false, pimsIdentity);
//						abr.addDebug("Call R118 successfully for SharedProduct:" + false);
					}
					
					// SAP Ledger
					// typemodel update profitcenter
					if (configManager.getString(PropertyKeys.KEY_SAP_LEDGER).equals("Y")) {
						boolean isProfitCenterChanged = false;
						if (t1ModelItem != null) {
							String t1ProfitCenter = getAttributeFlagValue(t1ModelItem, "PRFTCTR");
							if (!profitCenter.equals(t1ProfitCenter)) {
								isProfitCenterChanged = true;
							}
						} else {
							isProfitCenterChanged = true;
						}
						abr.addDebug("checkAndUpdateProfitCenter isProfitCenterChanged: " + isProfitCenterChanged);
//						MaterailPlantData matplantData = new MaterailPlantData();
						if (isProfitCenterChanged) {
							abr.addDebug("Start check and update profit center");
							for (String plant : plants) {
								rdhRestProxy.r262(chwA, material, plant, pimsIdentity, profitCenter);
							}
							abr.addDebug("End check and update profit center");
						}		
//						MaterailPlantData matplantData = new MaterailPlantData();
//						abr.addDebug("Start Profit Center");
//						checkAndUpdateProfitCenter(matplantData, typeModel.getType() + typeModel.getModel(), pimsIdentity, typeModel.getProfitCenter(), chwA);
//						abr.addDebug("End Profit Center");
					}
					abr.addDebug("----------------------- End Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted -----------------------");
				}
				
//					createAndMaintainBom(newBom, addToBom, spItemCateg, chwA, pimsIdentity); // do it later
						
				// epims code : mtcBomTypeCollection will be handle after promote type model
//					updateMtcBomType(typeModel.getType(), chwA, pimsIdentity); // do it later
//					abr.addDebug("udpate mtcBomType complete");
				if (isTMPromoted == false || isTMChanged == true || isTMGeoChanged== true || isTMGeoPromoted == false) {
					rdhRestProxy.r144(chwA.getAnnDocNo(), "R", pimsIdentity);
					abr.addDebug("Call R144 update parking table successfully");
				}
				
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
	
	private boolean isTypePromoted(Vector machtypeVct) throws RfcAbrException {
		if(machtypeVct != null && machtypeVct.size() > 0) {
			for (int i = 0; i < machtypeVct.size(); i++) {
				EntityItem machTypeItem = (EntityItem)machtypeVct.elementAt(i);
				String promoted = getAttributeFlagValue(machTypeItem, "PROMOTED");
				if (MACHTYPE_PROMOTED.equals(promoted)) {
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
				setFlagValue("PROMOTED", MACHTYPE_PROMOTED, machTypeItem);
			}
		}
	}
	
	private String getDiv() throws RfcAbrException {
		String div = null;
		EntityItem[] sgmItems = getEntityItems("SGMNTACRNYM");
		if(sgmItems != null && sgmItems.length > 0) {
			for (EntityItem sgmItem : sgmItems) {
				div = getAttributeFlagValue(sgmItem, "DIV");
				if (div != null && !"".equals(div)) {
					break;
				}
			}			
		}
		// div is required
		if (div == null || "".equals(div)) {
			throw new RfcAbrException("Can not extract DIV value, but it is a required value for web service");
		}
		return div;
	}
	
	private boolean isProductHierarchyDifferent(String productHierarchyFromDB, String productHierarchyFromMMLC) {
		if ((productHierarchyFromDB != null) && (productHierarchyFromMMLC != null)) {
			if (!(productHierarchyFromDB.equalsIgnoreCase(productHierarchyFromMMLC))) {
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
//			SharedProductExists = rdhRestProxy.r206(type + model); // 未实现
//			if (SharedProductExists) {
//				spComponents.setSharedProduct(true);
//				abr.addDebug("Shared Product in Check Shared Product Info : " + spComponents.getSharedProduct());
//				spComponents.setSharedProductMaterailType(sharedMaterialType);
//				Vector vt = rdhRestProxy.r179(type + model, annDocNo, false, "ann", pimsIdentity, prodTypeCd, bundleFlag, "0147"); // 未实现
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
			String productHierarchy, String pimsIdentity)
			throws HWPIMSAbnormalException {
		try {
			if (supportedSalesOrgV != null) {
				Vector salesOrgV = supportedSalesOrgV;
				abr.addDebug("CR2020 : saleOrgVector size : " + salesOrgV.size());
				Enumeration salesOrgE = salesOrgV.elements();
				while (salesOrgE.hasMoreElements()) {
					String salesOrg = (String) salesOrgE.nextElement();
					abr.addDebug("CR2020 : saleOrgVector contents : " + salesOrg);
					rdhRestProxy.r260(chwA, material, pimsIdentity, salesOrg, productHierarchy);
					abr.addDebug("Call R260 successfully for salesOrg: " + salesOrg);
				}
			}
		} catch (Exception e) {
			String msg = "Error - Updating L3593 : UpdateProdHierarchyOnSalesView : ";
			throw new HWPIMSAbnormalException(msg, e);
		}

	}
	
//	private void createAndMaintainBom(Hashtable newBom, Hashtable addToBom,
//			Hashtable spItemCateg, CHWAnnouncement announcement,
//			String pimsIdentity) throws HWPIMSException,
//			HWPIMSAbnormalException {
//
//		Enumeration addToBomEnum = addToBom.keys();
//		while (addToBomEnum.hasMoreElements()) {
//			String tmpKey = (String) addToBomEnum.nextElement();
//			StringTokenizer st = new StringTokenizer(tmpKey, "_");
//			String type = st.nextToken();
//			String sapPlant = st.nextToken();
//			Vector geoV = (Vector) addToBom.get(tmpKey);
//
//			/**
//			 * Following change is made for returnplants
//			 */
//			Vector typeNEW = rdhRestProxy.r210(type, "NEW", sapPlant);
//			if (typeNEW != null) {
//				rdhRestProxy.r143(type, sapPlant, geoV, "NEW", announcement, null, spItemCateg, pimsIdentity);
//			} else {
//				rdhRestProxy.r142(type, sapPlant, geoV, "NEW", announcement, null, spItemCateg, pimsIdentity);
//			}
//
//			Vector typeUPG = rdhRestProxy.r210(type, "UPG", sapPlant);
//			if (typeUPG != null) {
//				rdhRestProxy.r143(type, sapPlant, geoV, "UPG", announcement, null, spItemCateg, pimsIdentity);
//			} else {
//				rdhRestProxy.r142(type, sapPlant, geoV, "UPG", announcement, null, spItemCateg, pimsIdentity);
//			}
//		}
//	}
	
//	private void checkAndUpdateProfitCenter(MaterailPlantData matplantData,
//			String material, String pimsIdentity, String profitCenter,
//			CHWAnnouncement announcement) throws HWPIMSException, Exception {
//		Vector plantVec = rdhRestProxy.r261(material);
//		Enumeration enumPlantVec = plantVec.elements();
//		while (enumPlantVec.hasMoreElements()) {
//			MaterailPlantData matpData = (MaterailPlantData) enumPlantVec.nextElement();
//			String strMatPlant = matpData.getPlant();
//			String strMatProfitCenter = matpData.getProfitCenter();
//			abr.addDebug("strMatPlant   :" + strMatPlant);
//			abr.addDebug("SAP's strMatProfitCenter   :" + strMatProfitCenter);
//			abr.addDebug("DB's ProfitCenter   :" + profitCenter);
//			if (!strMatProfitCenter.equalsIgnoreCase(profitCenter)) {
//				rdhRestProxy.r262(announcement, material, strMatPlant, pimsIdentity, profitCenter);
//			}
//		}
//	}
	
	private void updateTypeNewUpgSalesBomInfo(Hashtable addToBom,
			TypeModel tmObj, String plant) {
		Vector _valV = new Vector();
		String _key = tmObj.getType() + "_" + plant;
		if (!addToBom.containsKey(_key)) {
			Vector _addEmptyV = new Vector();
			_addEmptyV.addElement(tmObj);
			addToBom.put(_key, _addEmptyV);
		} else {
			_valV = (Vector) addToBom.get(_key);
			if (_valV.contains(tmObj)) {
				return;
			}
			_valV.addElement(tmObj);
		}
	}
	
	private Vector<CntryTax> getAllTaxList(EntityItem modelItem) throws RfcAbrException {
		List<EntityItem> modTaxRelators = getLinkedRelator(modelItem, "MODTAXRELEVANCE");
		Vector<CntryTax> taxVect = new Vector<>();
		for (EntityItem modTaxRelator : modTaxRelators) {
			List<EntityItem> taxcatgs = getLinkedRelator(modTaxRelator, "TAXCATG");
			for (EntityItem taxcatg : taxcatgs) {
				CntryTax cntryTax = new CntryTax();
				cntryTax.setClassification(getAttributeValue(modTaxRelator, "TAXCLS"));
				cntryTax.setCountry(getAttributeFlagValue(taxcatg, "COUNTRYLIST")); // what kind type for country， US? 1652? or long desc
				cntryTax.setTaxCategory(getAttributeValue(taxcatg, "TAXCATGATR"));
				taxVect.add(cntryTax);
			}			
		}
		return taxVect;
	}
	
	protected Set<String> getAllPlants(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> plants = new HashSet<>();			
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			Vector<String> tmpPlants = salesorgPlants.getPlants();			
			for (String plant : tmpPlants) {
				plants.add(plant);
			}
			if (tmpPlants.size() == 0) {
				abr.addDebug("No plant found for country code： " + salesorgPlants.getCountryList());
			}				
		}			
		return plants;		
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
	
	private Vector getTaxListByCountryName(Vector<CntryTax> taxList, String countryList) {
//		Vector tmpTax = new Vector();
//		if (taxList != null && taxList.size() > 0) {
//			tmpTax.add(taxList.get(0));
//			for (CntryTax cntryTax : taxList) {
//				if (cntryTax.getCountry().equals(countryList)) {
//					tmpTax.add(cntryTax);
//				}
//			}
//		}
		return null;	
	}
	
	/**
	 * Re: blockers of CHW Promote Type Model logic 05/10/2016 12:27 AM
	 * PRODHIERCD -deviation logic changed 
	   We have to use MODEL.WWOCCODE --> GBT.WWOCCODE-->GBT.SAPPRIMBRANDCD+GBT.SAPPRODFMLYCD
	 * @param mdlItem
	 * @return
	 * @throws RfcAbrException 
	 */
	private String getProdHireCd(EntityItem mdlItem) throws RfcAbrException {
		String prodHireCd = "";
		Vector gbts = PokUtils.getAllLinkedEntities(mdlItem, "MODELGBTA", "GBT");
		for (int i = 0; i < gbts.size(); i++) {
			EntityItem gbtItem = (EntityItem)gbts.get(i);
			prodHireCd = getAttributeValue(gbtItem, "SAPPRIMBRANDCD") + getAttributeValue(gbtItem, "SAPPRODFMLYCD");
			break;
		}
		return prodHireCd;
	}
	
	
	private boolean isTypeModelChanged(EntityItem t1ModelItem, EntityItem t2ModelItem, List<String> attrList) throws RfcAbrException {
		return isDiff(t1ModelItem, t2ModelItem, attrList);
	}
	
	private boolean isTypeModelGeoPromoted(EntityItem[] t1Avails, EntityItem t2Avail) throws RfcAbrException {		
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
	
	/**
	 * 
	 * @param t1AnnItem
	 * @param t2AnnItem
	 * @param t1Palnts
	 * @param t2Plants
	 * @return
	 * @throws RfcAbrException
	 */
	private boolean isTypeModelGeoChanged(EntityItem t1AnnItem, EntityItem t2AnnItem, Set<String> t1Palnts, Set<String> t2Plants) throws RfcAbrException {
		Vector<String> attrs = new Vector<>();
		attrs.add("ANNDATE");
		if (isDiff(t1AnnItem, t2AnnItem, attrs)) {
			return true;
		}
		for (String plant : t2Plants) {
			if (!t1Palnts.contains(plant)) {
				abr.addDebug("Plant " + plant + " is new at T2");
				return true;
			}
		}
		return false;
	}
	
	public String getVeName() {
		return "RFCMODEL";
	}

}
