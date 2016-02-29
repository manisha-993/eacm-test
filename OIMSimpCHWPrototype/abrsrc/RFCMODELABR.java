package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.SalesOrgToPlantMapper;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.transform.oim.eacm.util.PokUtils;

public class RFCMODELABR extends RfcAbrAdapter {
	
	public RFCMODELABR(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException {
		super(rfcAbrStatus);
	}
	
	public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
		abr.addDebug("RFCMODELABR start processThis()");
		// Entity from EACM
		EntityItem modelItem = getRooEntityItem();
		EntityItem[] annItems = getEntityItems("ANNOUNCEMENT");
		
		Vector machtypeVct = PokUtils.getAllLinkedEntities(modelItem, "MODELMACHINETYPEA", "MACHTYPE");
		
		// Taxcatg
		EntityItem[] modTaxRelatorItems = getEntityItems("MODTAXRELEVANCE");
		Vector vectTaxList = new Vector();
		// MODTAXRELEVANCE -> TAXCATAGE　－>　SALEORG
		if (modTaxRelatorItems != null && modTaxRelatorItems.length > 0) {
			abr.addDebug("TAXCATG size:" + modTaxRelatorItems.length);
			for(EntityItem modTaxRelatorItem : modTaxRelatorItems) {
				String classification = getAttributeValue(modTaxRelatorItem, "TAXCLS");
				Vector taxLinkVct = modTaxRelatorItem.getDownLink();
				if(taxLinkVct != null && taxLinkVct.size() > 0) {
					EntityItem taxEntityItem = (EntityItem)taxLinkVct.get(0);
					Vector taxCountryList = getMultiAttributeValue(taxEntityItem, "COUNTRYLIST");
					abr.addDebug("Country size：" + taxCountryList.size() + " for TAXCATG：" + taxEntityItem.getKey());
					for(int i = 0; i < taxCountryList.size(); i++) {
						String country = (String)taxCountryList.get(i);
						CntryTax cntryTax = new CntryTax();
						cntryTax.setClassification(classification);
						cntryTax.setCountry(country); // what kind type for country， US? 1652? or long desc
						cntryTax.setTaxCategory(getAttributeValue(taxEntityItem, "TAXCATGATR"));
						vectTaxList.add(cntryTax);
					}
				}
			}
		}
		
		String machineTypeAtr = getAttributeValue(modelItem, "MACHTYPEATR");
		// Type Model
		TypeModel typeModel = new TypeModel();
		typeModel.setType(machineTypeAtr);
		typeModel.setDiv(getDiv());
		typeModel.setDescription(getAttributeValue(modelItem, "MKTGNAME"));
		typeModel.setProductHierarchy(getAttributeValue(modelItem, "PRODHIERCD"));
		typeModel.setFlfilCol(getMultiAttributeValue(modelItem, "FLFILSYSINDC"));
		typeModel.setLoadingGroup(getAttributeValue(modelItem, "MODELORDERCODE")); // WS logic:if "return plant" then Set to "RETN" else Set to "B001", then this value will always B001 in RDH
		abr.addDebug("TypeModel:" + typeModel.toString());
		
		// Old code， only use chwAg for LA 
		
		String flfilcd = typeModel.getFlfilCd(typeModel.getFlfilCol());
		abr.addDebug("Flfilcd:" + flfilcd);
		
		// XCC=X， else C
		String pimsIdentity = "C";
//		if (isXccOnlyDiv(typeModel.getDiv())) { // old code logic, but as Praveen mapping, it will always set C
//			pimsIdentity = "X";
//		} else {
//			pimsIdentity = "C";
//		}
		abr.addDebug("PimsIdentity:" + pimsIdentity);
		
		if (annItems != null && annItems.length > 0) {
			for (int i = 0; i < annItems.length; i++) {
				EntityItem annItem = annItems[i];
				CHWAnnouncement chwA = new CHWAnnouncement();
				CHWGeoAnn chwAg = new CHWGeoAnn();
				
				chwA.setAnnDocNo(getAttributeValue(annItem, "ANNNUMBER"));
				chwA.setAnnouncementType(getAttributeValue(annItem, "ANNTYPE")); //  WS logic:if feed is designated as "ePIMS/SW Migration", then set to "MIG" else set to "RFA", so flag or desc are all fine.
						
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
				chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, "ANNDATE")));
				
				abr.addDebug("CHWAnnouncement:" + chwA.toString());
				abr.addDebug("CHWAnnouncementGEO:" + chwAg.toString());
				
				// If Type not promoted (exists in TYPE table)
				if (!isTypeExist(machtypeVct)) {
					abr.addDebug("Type:" + typeModel.getType() + " not promoted");
					// Create type NEW material basic view [R100]
					rdhRestProxy.r100(chwA, typeModel, chwAg, "NEW", null, null, pimsIdentity);
					// Create type UPG material basic view [R100]
					rdhRestProxy.r100(chwA, typeModel, chwAg, "UPG", null, null, pimsIdentity);
					abr.addDebug("Call R100 NEW and UPG successfully");
					
					// SalesOrg, mapping is not ready， hard code 0147
//					String geography = chwAg.getGeography(); // ? LGORT PLANT.StorageLocationX
					String geography = "0147";
					String[] plantsBySalesOrg = SalesOrgToPlantMapper.getPlantBySalesOrg(geography);
					abr.addDebug("R101 Plant size:" + plantsBySalesOrg.length);
					for (int j = 0; j < plantsBySalesOrg.length; j++) {
						String plantValue = plantsBySalesOrg[j];
						abr.addDebug("R101 Plant value:" + plantValue);
						// Create Generic Plant 1222 View for NEW material [R101]
						rdhRestProxy.r101(chwA, typeModel, chwAg, "NEW", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //NEW
						// Create Generic Plant 1222 View for UPG material [R101]
						rdhRestProxy.r101(chwA, typeModel, chwAg, "UPG", typeModel.getLoadingGroup(), null, null, pimsIdentity, plantValue); //UPG
						abr.addDebug("Call R101 NEW and UPG successfully");
					}
					
					// Extra Plants (CFI Plants ) reading from Properties File
					String extraplant = configManager.getString(PropertyKeys.KEY_EXTRA_PLANT, true); // WERKS Plant
					StringTokenizer stoken = new StringTokenizer(extraplant, ",");
					// For each CFI Plant
					while (stoken.hasMoreElements()) {
						String sPlant = stoken.nextToken();
						abr.addDebug("R189 Plant :" + sPlant);
						// Create Plant View For NEW Material [R189]
						rdhRestProxy.r189(chwA, typeModel, sPlant, "NEW", null, null, pimsIdentity);
						// Create Plant View For UPG Material [R189]
						rdhRestProxy.r189(chwA, typeModel, sPlant, "UPG", null, null, pimsIdentity);
						abr.addDebug("Call R189 NEW and UPG successfully");
					}
					
					// Create type NEW material sales view [R102]
					// Create type UPG material sales view [R102]
					String _plant = "1222"; // mapping not ready
					String salesOrg = "US"; // mapping not ready
					// no OIMDS role, can't create TAXCATG at BHDEV, hard code here
					vectTaxList = new Vector();
					CntryTax cntryTax = new CntryTax();
					cntryTax.setClassification("2");
					cntryTax.setCountry("US");
					cntryTax.setTaxCategory("ZTXD");
					vectTaxList.add(cntryTax);
					rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //NEW
					rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //UPG
					abr.addDebug("Call R102 NEW and UPG successfully");
								
					// Create 001 Classification for MG_COMMON for NEW [R103]
					rdhRestProxy.r103(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
					// Create 001 Classification for MG_COMMON for UPG [R103]
					rdhRestProxy.r103(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
					abr.addDebug("Call R103 NEW and UPG successfully");
					// Create ZDM Classification for NEW [R104]
					rdhRestProxy.r104(typeModel, "NEW", chwA, null, null, pimsIdentity); //NEW
					// Create ZDM Classification for UPG [R104]
					rdhRestProxy.r104(typeModel, "UPG", chwA, null, null, pimsIdentity); //UPG
					abr.addDebug("Call R104 NEW and UPG successfully");
					// Create type MODELS class [R106]
					rdhRestProxy.r106(typeModel, chwA, pimsIdentity);
					abr.addDebug("Call R106 successfully");
					
					// get ranges for type from promoted type table
					// Vector Ranges = (Vector) promotedTypeclasses.get(tmObj.getType());
					
					// For each class range (0000, 1000 , . . . 9000)
					for (int fRanges = 0; fRanges <= 9; fRanges++) {
						String featRanges = fRanges + "000";
						String type = typeModel.getType();
						// Create Type FEAT Range class [R130]
						rdhRestProxy.r130(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R130 successfully");
						// Create 300 Classification for Type FEAT for NEW [R176]
						rdhRestProxy.r176(type, featRanges, "NEW", chwA, pimsIdentity);
						// Create 300 Classification for Type FEAT for UPG [R176]
						rdhRestProxy.r176(type, featRanges, "UPG", chwA, pimsIdentity);
						abr.addDebug("Call R176 NEW and UPG successfully");
						// Create Type UF class [R131]
						rdhRestProxy.r131(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R131 NEW and UPG successfully");
						// Create 300 Classification for Type UF for UPG [R177]
						rdhRestProxy.r177(type, featRanges, chwA, pimsIdentity);
						abr.addDebug("Call R177 NEW and UPG successfully");
						
						// Insert Type/Feature class row in PROMOTED_TYPECLASS (delayed)
						// type not promoted, save type and range to table
//						Hashtable promotedTypes = new Hashtable();
//						if (!promotedTypes.containsKey(typeModel.getType())) {
//							ptypClassCol.addPromotedType(typeModel.getType());
//							ptypClassCol.addRange(typeModel.getType(), featRanges);
//						}
//						else {
//							if (!Ranges.contains(featRanges)) {
//								ptypClassCol.addRange(typeModel.getType(), featRanges);
//							}
//						}
//						// PromotedTypeCollection , get all promoted type from table
//						Hashtable nowPromotedTypeClasses = new Hashtable();
//						if (!nowPromotedTypeClasses.containsKey(typeModel.getType())) {
//							Vector nowPromotedRanges = new Vector();
//							nowPromotedRanges.addElement(featRanges);
//							nowPromotedTypeClasses.put(typeModel.getType(), nowPromotedRanges);
//						} else {
//							Vector nowPromotedRanges = (Vector) nowPromotedTypeClasses.get(typeModel.getType());
//							if (!nowPromotedRanges.contains(featRanges)) {
//								nowPromotedRanges.addElement(featRanges);
//							}
//						}
						
//						
						
					} // end for For loop
					// Create type MOD characteristic [R108]
					rdhRestProxy.r108(typeModel, chwA, pimsIdentity);
					// Assign MOD characteristic to MODELS class [R110]
					rdhRestProxy.r110(typeModel, chwA, pimsIdentity);
					// Create 012 classification for MOD [R150]
					rdhRestProxy.r150(typeModel, chwA, pimsIdentity);
					rdhRestProxy.r123(typeModel.getType(), null, "NEW", chwA, null, pimsIdentity);
					rdhRestProxy.r123(typeModel.getType(), null, "UPG", chwA, null, pimsIdentity);
					// Assign Char to Class FEAT_0000 [R160]
					rdhRestProxy.r160(typeModel, chwA, pimsIdentity);			
					// Create 001 Classifcation for MM_FIELDS For NEW [175]
					rdhRestProxy.r175(typeModel, null, chwA, "NEW", null, pimsIdentity);
					// Create 001 Classifcation for MM_FIELDS For UPG [175]
					rdhRestProxy.r175(typeModel, null, chwA, "UPG", null, pimsIdentity);
					// Add to newBom Hashtable (key is Type+"||"+SAPPlant, value is empty vector)
					// Insert type into TYPE table (delayed)
				
					setPromotedMachtypes(machtypeVct);
				} // End if Type not promoted
			}
			abr.addDebug("RFCMODELABR end processThis()");
		} else {
			throw new RfcAbrException("Not found ANNOUNCEMENT, will not promote this MODEL");
		}
	}
	
	private boolean isTypeExist(Vector machtypeVct) throws RfcAbrException {
		if(machtypeVct != null && machtypeVct.size() > 0) {
			for (int i = 0; i < machtypeVct.size(); i++) {
				EntityItem machTypeItem = (EntityItem)machtypeVct.elementAt(i);
				String promoted = getAttributeValue(machTypeItem, "");
				if (MACHTYPE_PROMOTED.equals(promoted)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void setPromotedMachtypes(Vector machtypeVct) {
		if(machtypeVct != null && machtypeVct.size() > 0) {
			for (int i = 0; i < machtypeVct.size(); i++) {
				EntityItem machTypeItem = (EntityItem)machtypeVct.elementAt(i);
				setFlagValue("xxxx", MACHTYPE_PROMOTED, machTypeItem);
			}
		}
	}
	
	private String getDiv() throws RfcAbrException {
		String div = "";
		EntityItem[] sgmItems = getEntityItems("SGMNTACRNYM");
		if(sgmItems != null && sgmItems.length > 0) {
			EntityItem sgmItem = sgmItems[0];
			div = getAttributeFlagValue(sgmItem, "DIV");
		}
		// No OIMDS role on bhdev, can not create SGMNTACRNYM, hard code
		// uncomment when FVT
//		if("".equals(div)){
//			throw new RfcAbrException("DIV is empty");
//		}
		return "4B";
	}
	
	public String getVeName() {
		return "RFCMODEL";
	}

}
