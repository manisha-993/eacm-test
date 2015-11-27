package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.SalesOrgToPlantMapper;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.TypeModel;

public class RFCMODELABR extends RfcAbrAdapter {
	
	public RFCMODELABR(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException {
		super(rfcAbrStatus);
	}
	
	public void processThis() throws RfcAbrException, Exception {
		abr.addDebug("RFCMODELABR start processThis()");
		// Entity from EACM
		EntityItem modelItem = getRooEntityItem();
		EntityItem[] annItems = getEntityItems("ANNOUNCEMENT");
		
		// Taxcatg
		EntityItem[] modTaxRelatorItems = getEntityItems("MODTAXRELEVANCE");
		Vector vectTaxList = new Vector();
		// MODTAXRELEVANCE -> TAXCATAGE　－>　SALEORG
		if (modTaxRelatorItems != null && modTaxRelatorItems.length > 0) {
			Vector taxCntryList = new Vector();
			for(EntityItem modTaxRelatorItem : modTaxRelatorItems) {
				
				String classification = getAttributeValue(modTaxRelatorItem, "TAXCLS");
				
				Vector taxLinkVct = modTaxRelatorItem.getDownLink();
				if(taxLinkVct != null && taxLinkVct.size() > 0) {
					EntityItem taxEntityItem = (EntityItem)taxLinkVct.get(0);
					Vector taxCountryList = getMultiAttributeValue(taxEntityItem, "COUNTRYLIST");
					for(int i = 0; i < taxCountryList.size();) {
						String country = (String)taxCountryList.get(i);
						CntryTax cntryTax = new CntryTax();
						cntryTax.setClassification(classification);
						cntryTax.setCountry(country); // what kind type for country， US? 1652? or long desc
						cntryTax.setTaxCategory(getAttributeValue(taxEntityItem, "TAXCATGATR"));
					}
				}
			}
		}
		
		// Type Model
		TypeModel typeModel = new TypeModel();
		typeModel.setType(getAttributeValue(modelItem, "MACHTYPEATR"));
		typeModel.setDiv(getDiv());
		typeModel.setDescription(getAttributeValue(modelItem, "MKTGNAME"));
		typeModel.setProductHierarchy(getAttributeValue(modelItem, "BHPRODHIERCD"));
		typeModel.setFlfilCol(getMultiAttributeValue(modelItem, "FLFILSYSINDC"));
		String loadingGrp = "BH"; // ? LADGR PLANT.LoadingGroup PLANT.LoadingGroup
		typeModel.setLoadingGroup(loadingGrp);
		abr.addDebug("TypeModel:" + typeModel.toString());
		
		CHWAnnouncement chwA = new CHWAnnouncement();
		CHWGeoAnn chwAg = new CHWGeoAnn();
		
		
		// Old code， only use chwAg for LA 
		
		
		String flfilcd = typeModel.getFlfilCd(typeModel.getFlfilCol());
		abr.addDebug("Flfilcd:" + flfilcd);
		
		// XCC=X， else C
		String pimsIdentity = "";
		if (isXccOnlyDiv(typeModel.getDiv())) {
			pimsIdentity = "X";
		} else {
			pimsIdentity = "C";
		}
		abr.addDebug("PimsIdentity:" + pimsIdentity);
		
		if (annItems != null && annItems.length > 0) {
			for (int i = 0; i < annItems.length; i++) {
				EntityItem annItem = annItems[i];
				
				chwA.setAnnDocNo(getAttributeValue(annItem, "ANNNUMBER"));
				chwA.setAnnouncementType(getAttributeValue(annItem, "ANNTYPE")); //  flag value?
						
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
				chwAg.setAnnouncementDate(sdf.parse(getAttributeValue(annItem, "ANNDATE")));
				
				abr.addDebug(chwA.toString());
				abr.addDebug(chwAg.toString());
				
				// If Type not promoted (exists in TYPE table)
				if (!isTypeExist()) {
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
					rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //NEW
					rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg, vectTaxList); //UPG
										
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
				}
			}
		} else {
			throw new RfcAbrException("Not found ANNOUNCEMENT, will not promote this MODEL");
		}
	}
	
	
	
	private boolean isTypeExist() {
		return false;
	}
	
	// No OIMDS role on bhdev, can not create SGMNTACRNYM, hard code
	private String getDiv() throws RfcAbrException {
		String div = "";
		EntityItem[] sgmItems = getEntityItems("SGMNTACRNYM");
		if(sgmItems != null && sgmItems.length > 0) {
			EntityItem sgmItem = sgmItems[0];
			div = getAttributeFlagValue(sgmItem, "DIV");
		}
		return "4B";
	}
	
	public String getVeName() {
		return "RFCMODEL";
	}

}
