/*
 * Created on Jun 3, 2003
 */
package com.ibm.rdh.rfc.proxy;

import java.util.Vector;

import com.ibm.rdh.chw.caller.R100createTypeMaterialBasicView;
import com.ibm.rdh.chw.caller.R101createGenericPlantViewforMaterial;
import com.ibm.rdh.chw.caller.R102createSalesViewforMaterial;
import com.ibm.rdh.chw.caller.R103create001ClassificationForMGCommon;
import com.ibm.rdh.chw.caller.R104createZDMClassification;
import com.ibm.rdh.chw.caller.R106createTypeModelsClass;
import com.ibm.rdh.chw.caller.R108createTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R110assignMODCharacteristicToModelsClass;
import com.ibm.rdh.chw.caller.R123create300ClassificationForTypeModels;
import com.ibm.rdh.chw.caller.R130createTypeFEATClass;
import com.ibm.rdh.chw.caller.R131createTypeUFClass;
import com.ibm.rdh.chw.caller.R150create012ClassificationForMOD;
import com.ibm.rdh.chw.caller.R160assignChartoClassFEAT_0000;
import com.ibm.rdh.chw.caller.R166createSTPPlantViewForMaterial;
import com.ibm.rdh.chw.caller.R175create001ClassificationForMMFieldsType;
import com.ibm.rdh.chw.caller.R176create300ClassificationForTypeFEAT;
import com.ibm.rdh.chw.caller.R177create300ClassificationForTypeUFForUPG;
import com.ibm.rdh.chw.caller.R189createCFIPlantViewForType;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

/**
 * @author bobc
 *
 *         rjc - this factory will only create the rfc class, but NOT execute
 *         it.
 */
public class RfcFactory extends Object {

	/*
	 * @see
	 * com.ibm.pprds.swpims.AbstractrRfcFactory#getR001(com.ibm.pprds.swpims
	 * .domain.SWO)
	 */
	public R100createTypeMaterialBasicView getr100(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R100createTypeMaterialBasicView(chwA, typeModel, chwAg,
				newFlag, tmUPGObj, FromToType, pimsIdentity);
	}

	public R101createGenericPlantViewforMaterial getr101(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			String loadingGrp, TypeModelUPGGeo tmUPGObj, String fromToType,
			String pimsIdentity, String plantValue) throws Exception {
		return new R101createGenericPlantViewforMaterial(chwA, typeModel,
				chwAg, newFlag, loadingGrp, tmUPGObj, fromToType, pimsIdentity,
				plantValue);
	}

	public R102createSalesViewforMaterial getr102(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String fromToType, String pimsIdentity,
			String flfilcd, String salesOrg, Vector taxCntryList)
			throws Exception {
		return new R102createSalesViewforMaterial(chwA, typeModel, sapPlant,
				newFlag, tmUPGObj, fromToType, pimsIdentity, flfilcd, salesOrg,
				taxCntryList);
	}

	public R103create001ClassificationForMGCommon getr103(TypeModel typeModel,
			String newFlag, CHWAnnouncement chwA, TypeModelUPGGeo tmUPGObj,
			String fromToType, String pimsIdentity) throws Exception {
		return new R103create001ClassificationForMGCommon(typeModel, newFlag,
				chwA, tmUPGObj, fromToType, pimsIdentity);

	}

	public R104createZDMClassification getr104(TypeModel typeModel,
			String newFlag, CHWAnnouncement chwA, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		return new R104createZDMClassification(typeModel, newFlag, chwA,
				tmUPGObj, FromToType, pimsIdentity);

	}

	public R106createTypeModelsClass getr106(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		return new R106createTypeModelsClass(typeModel, chwA, pimsIdentity);
	}

	public R108createTypeMODCharacteristic getr108(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		return new R108createTypeMODCharacteristic(typeModel, chwA,
				pimsIdentity);
	}

	public R110assignMODCharacteristicToModelsClass getr110(
			TypeModel typeModel, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {

		return new R110assignMODCharacteristicToModelsClass(typeModel, chwA,
				pimsIdentity);
	}

	public R123create300ClassificationForTypeModels getr123(String type,
			TypeModelUPGGeo tmUPGObj, String newFlag, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {

		return new R123create300ClassificationForTypeModels(type, tmUPGObj,
				newFlag, chwA, FromToType, pimsIdentity);
	}

	public R130createTypeFEATClass getr130(String type, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		return new R130createTypeFEATClass(type, featRanges, chwA, pimsIdentity);

	}

	public R131createTypeUFClass getr131(String type, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		return new R131createTypeUFClass(type, featRanges, chwA, pimsIdentity);
	}

	public R150create012ClassificationForMOD getr150(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception{
		return new R150create012ClassificationForMOD(typeModel, chwA, pimsIdentity);
	}
	
	public R160assignChartoClassFEAT_0000 getr160(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		return new R160assignChartoClassFEAT_0000(typeModel, chwA, pimsIdentity);
	}

	public R166createSTPPlantViewForMaterial getr166(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String storageLocation,
			String newFlag) throws Exception {
		return new R166createSTPPlantViewForMaterial(chwA, typeModel, chwAg,
				storageLocation, newFlag);
	}

	public R175create001ClassificationForMMFieldsType getr175(
			TypeModel typemodel, TypeModelUPGGeo tmUPGObj,
			CHWAnnouncement chwA, String newFlag, String FromToType,
			String pimsIdentity) throws Exception {
		return new R175create001ClassificationForMMFieldsType(typemodel,
				tmUPGObj, chwA, newFlag, FromToType, pimsIdentity);
	}

	public R176create300ClassificationForTypeFEAT getr176(String type,
			String range, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R176create300ClassificationForTypeFEAT(type, range, newFlag,
				chwA, pimsIdentity);
	}

	public R177create300ClassificationForTypeUFForUPG getr177(String type,
			String range, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R177create300ClassificationForTypeUFForUPG(type, range,
				chwA, pimsIdentity);
	}

	public R189createCFIPlantViewForType getr189(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R189createCFIPlantViewForType(chwA, typeModel, sapPlant,
				newFlag, tmUPGObj, FromToType, pimsIdentity);

	}

}
