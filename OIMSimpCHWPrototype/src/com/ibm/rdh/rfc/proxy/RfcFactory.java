/*
 * Created on Jun 3, 2003
 */
package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.rdh.chw.caller.R005CreatePlantViewForMaterial;
import com.ibm.rdh.chw.caller.R006CreateSwoMaterialSalesView;
import com.ibm.rdh.chw.caller.R009Maintain001Classification;
import com.ibm.rdh.chw.caller.R039Create011ClassificationForMgCommon;
import com.ibm.rdh.chw.caller.R057CreateReturnPlantView;
import com.ibm.rdh.chw.caller.R062CreateRevPartMaster;
import com.ibm.rdh.chw.caller.R100createTypeMaterialBasicView;
import com.ibm.rdh.chw.caller.R101createGenericPlantViewforMaterial;
import com.ibm.rdh.chw.caller.R102createSalesViewforMaterial;
import com.ibm.rdh.chw.caller.R103create001ClassificationForMGCommon;
import com.ibm.rdh.chw.caller.R104createZDMClassification;
import com.ibm.rdh.chw.caller.R106createTypeModelsClass;
import com.ibm.rdh.chw.caller.R107createTypeMCClass;
import com.ibm.rdh.chw.caller.R108createTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R109createTypeMCCharacteristic;
import com.ibm.rdh.chw.caller.R110assignMODCharacteristicToModelsClass;
import com.ibm.rdh.chw.caller.R111assignMODCharacteristicToModelsClass;
import com.ibm.rdh.chw.caller.R115createTypeModelMaterialBasicView;
import com.ibm.rdh.chw.caller.R116createPlantViewForTypeModelMaterial;
import com.ibm.rdh.chw.caller.R117createTypeModelMaterialSalesView;
import com.ibm.rdh.chw.caller.R118create001ClassificationForMMFieldsTypeModel;
import com.ibm.rdh.chw.caller.R119create001ClassificationForMGCommonTypeModel;
import com.ibm.rdh.chw.caller.R120maintainModelValueForTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R121createModelSelectionDependency;
import com.ibm.rdh.chw.caller.R123create300ClassificationForTypeModels;
import com.ibm.rdh.chw.caller.R124createUpgradeValueForTypeMCCharacteristic;
import com.ibm.rdh.chw.caller.R125create300ClassificationForTypeMCForUPG;
import com.ibm.rdh.chw.caller.R126createRPQTypeFeatureCharacteristic;
import com.ibm.rdh.chw.caller.R127createRPQClass;
import com.ibm.rdh.chw.caller.R128assignRPQTypeFeatureCharacteristicToTypeClass;
import com.ibm.rdh.chw.caller.R129maintainTypeFeatureCharacteristic;
import com.ibm.rdh.chw.caller.R130createTypeFEATClass;
import com.ibm.rdh.chw.caller.R131createTypeUFClass;
import com.ibm.rdh.chw.caller.R133updateMaterialBasicViewForTypeModel;
import com.ibm.rdh.chw.caller.R134assignRPQTypeFeatureCharacteristicToTypeClass;
import com.ibm.rdh.chw.caller.R135assignTypeFeatureCharacteristicToTypeUFClass;
import com.ibm.rdh.chw.caller.R136create300ClassificationForTypeFEAT;
import com.ibm.rdh.chw.caller.R137create300ClassificationForTypeUFForMTC;
import com.ibm.rdh.chw.caller.R138_mtccreate300ClassificationForTypeRPQ;
import com.ibm.rdh.chw.caller.R138create300ClassificationForTypeRPQ;
import com.ibm.rdh.chw.caller.R142_createSalesBOM;
import com.ibm.rdh.chw.caller.R143assignTypeModelAsSalesBOMItemWithDependencies;
import com.ibm.rdh.chw.caller.R144updateParkStatus;
import com.ibm.rdh.chw.caller.R148_createConfigurationProfileForNewMaterial;
import com.ibm.rdh.chw.caller.R149_createConfigurationProfileForUPGMaterial;
import com.ibm.rdh.chw.caller.R150create012ClassificationForMOD;
import com.ibm.rdh.chw.caller.R151create012ClassificationForMC;
import com.ibm.rdh.chw.caller.R152create012ClassificationForRPQTypeFeature;
import com.ibm.rdh.chw.caller.R153create012ClassificationForTypeFeature;
import com.ibm.rdh.chw.caller.R156createZDMClassificationForTypeModel;
import com.ibm.rdh.chw.caller.R157createTypeClass;
import com.ibm.rdh.chw.caller.R159createTypeMCCharacteristic;
import com.ibm.rdh.chw.caller.R160assignChartoClassFEAT_0000;
import com.ibm.rdh.chw.caller.R161createZDMClassificationForMKFEX;
import com.ibm.rdh.chw.caller.R162createZDMClassificationForMKFEATCONV;
import com.ibm.rdh.chw.caller.R163createUpgradeValueForTypeMTCCharacteristic;
import com.ibm.rdh.chw.caller.R164create300ClassificationForTypeMTC;
import com.ibm.rdh.chw.caller.R165assignCharacteristicToMTCClass300;
import com.ibm.rdh.chw.caller.R166createSTPPlantViewForMaterial;
import com.ibm.rdh.chw.caller.R168create012ClassificationForMTC;
import com.ibm.rdh.chw.caller.R169ReadTypeModelsFromBOM;
import com.ibm.rdh.chw.caller.R170removeTypeModelsFromBOM;
import com.ibm.rdh.chw.caller.R171markTypeModelMaterialForDeletion;
import com.ibm.rdh.chw.caller.R172deleteModelValueFromTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R175create001ClassificationForMMFieldsType;
import com.ibm.rdh.chw.caller.R176create300ClassificationForTypeFEAT;
import com.ibm.rdh.chw.caller.R177create300ClassificationForTypeUFForUPG;
import com.ibm.rdh.chw.caller.R179ReadPlannedChangeForTypeModelMaterial;
import com.ibm.rdh.chw.caller.R182deleteModelSelectionDependency;
import com.ibm.rdh.chw.caller.R183createCFIPlantViewForTypeModelMaterial;
import com.ibm.rdh.chw.caller.R185deleteupgradevaluefromMCcharacteristic;
import com.ibm.rdh.chw.caller.R186DeleteTypeFeatureCharacteristic;
import com.ibm.rdh.chw.caller.R189createCFIPlantViewForType;
import com.ibm.rdh.chw.caller.R193ReadRevenueProfile;
import com.ibm.rdh.chw.caller.R194CreateRevenueProfile;
import com.ibm.rdh.chw.caller.R195DeleteRevenueProfile;
import com.ibm.rdh.chw.caller.R196UpdateRevenueProfile;
import com.ibm.rdh.chw.caller.R197createLifecycleRow;
import com.ibm.rdh.chw.caller.R198updateLifecycleRow;
import com.ibm.rdh.chw.caller.R199_deleteLifecycleRow;
import com.ibm.rdh.chw.caller.R200_readLifecycleRow;
import com.ibm.rdh.chw.caller.R201createUpgradeValueForTypeMCCharacteristic;
import com.ibm.rdh.chw.caller.R202_createConfigurationProfileForMTCMaterial;
import com.ibm.rdh.chw.caller.R204ReadMaterial;
import com.ibm.rdh.chw.caller.R205ClassificationForBTProductsTypeMaterials;
import com.ibm.rdh.chw.caller.R209ReadBasicViewOfMaterial;
import com.ibm.rdh.chw.caller.R210ReadSalesBom;
import com.ibm.rdh.chw.caller.R211CreateSalesBOMfortypeMTC;
import com.ibm.rdh.chw.caller.R212DeleteSalesBOMfortypeMTC;
import com.ibm.rdh.chw.caller.R213UpdateSalesBOMItemWithtypeMTC;
import com.ibm.rdh.chw.caller.R214ReadMCclass;
import com.ibm.rdh.chw.caller.R260updateProdHierarchyOnSalesView;
import com.ibm.rdh.chw.caller.R262createPlantViewProfitCenterForMaterial;
import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

//import com.ibm.rdh.chw.caller.R161createZDMClassificationForMKFEX;

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

	public R005CreatePlantViewForMaterial getr005(AUOMaterial auoMaterial,
			String sapPlant, String pimsIdentity) throws Exception {
		return new R005CreatePlantViewForMaterial(auoMaterial, sapPlant,
				pimsIdentity);
	}

	public R006CreateSwoMaterialSalesView getr006(AUOMaterial auoMaterial,
			String salesOrg, String currentSapSalesStatus,
			Date currentEffectiveDate, String pimsIdentity) throws Exception {
		return new R006CreateSwoMaterialSalesView(auoMaterial, salesOrg,
				currentSapSalesStatus, currentEffectiveDate, pimsIdentity);
	}

	public R009Maintain001Classification getr009(AUOMaterial auoMaterial,
			String pimsIdentity) throws Exception {
		return new R009Maintain001Classification(auoMaterial, pimsIdentity);
	}

	public R039Create011ClassificationForMgCommon getr039(
			AUOMaterial auoMaterial, String pimsIdentity) throws Exception {
		return new R039Create011ClassificationForMgCommon(auoMaterial,
				pimsIdentity);
	}

	public R057CreateReturnPlantView getr057(AUOMaterial auoMaterial,
			String returnPlant, String pimsIdentity) throws Exception {
		return new R057CreateReturnPlantView(auoMaterial, returnPlant,
				pimsIdentity);
	}

	public R062CreateRevPartMaster getr062(AUOMaterial auoMaterial,
			String pimsIdentity) throws Exception {
		return new R062CreateRevPartMaster(auoMaterial, pimsIdentity);
	}

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
			String flfilcd, String salesOrg, Vector taxCntryList,
			CHWGeoAnn chwAg) throws Exception {
		return new R102createSalesViewforMaterial(chwA, typeModel, sapPlant,
				newFlag, tmUPGObj, fromToType, pimsIdentity, flfilcd, salesOrg,
				taxCntryList, chwAg);
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

	public R107createTypeMCClass getr107(String type, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R107createTypeMCClass(type, chwA, pimsIdentity);
	}

	public R108createTypeMODCharacteristic getr108(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R108createTypeMODCharacteristic(typeModel, chwA,
				pimsIdentity);
	}

	public R109createTypeMCCharacteristic getr109(String type,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R109createTypeMCCharacteristic(type, chwA, pimsIdentity);
	}

	public R110assignMODCharacteristicToModelsClass getr110(
			TypeModel typeModel, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R110assignMODCharacteristicToModelsClass(typeModel, chwA,
				pimsIdentity);
	}

	public R111assignMODCharacteristicToModelsClass getr111(String type,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R111assignMODCharacteristicToModelsClass(type, chwA,
				pimsIdentity);
	}

	public R115createTypeModelMaterialBasicView getr115(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String pimsIdentity,
			String plantValue) throws Exception {
		return new R115createTypeModelMaterialBasicView(chwA, typeModel, chwAg,
				pimsIdentity, plantValue);
	}

	public R116createPlantViewForTypeModelMaterial getr116(
			CHWAnnouncement chwA, TypeModel typeModel, String sapPlant,
			String loadingGroup, CHWGeoAnn chwAg, String storageLocation,
			String pimsIdentity) throws Exception {
		return new R116createPlantViewForTypeModelMaterial(chwA, typeModel,
				sapPlant, loadingGroup, chwAg, storageLocation, pimsIdentity);
	}

	public R117createTypeModelMaterialSalesView getr117(CHWAnnouncement chwA,
			String typemod, String div, String acctAsgnGrp,
			PlannedSalesStatus ps, boolean bumpCtr, String pimsIdentity,
			String flfil, String salesOrg1, String productHierarchy,
			Vector VectTaxList, String plantValue, CHWGeoAnn chwAg)
			throws Exception {
		return new R117createTypeModelMaterialSalesView(chwA, typemod, div,
				acctAsgnGrp, ps, bumpCtr, pimsIdentity, flfil, salesOrg1,
				productHierarchy, VectTaxList, plantValue, chwAg);
	}

	public R118create001ClassificationForMMFieldsTypeModel getr118(
			TypeModel typeModel, CHWAnnouncement chwA, String flfilcd,
			String warrantyPeriod, boolean remarkable, String pimsIdentity)
			throws Exception {
		return new R118create001ClassificationForMMFieldsTypeModel(typeModel,
				chwA, flfilcd, warrantyPeriod, remarkable, pimsIdentity);
	}

	public R119create001ClassificationForMGCommonTypeModel getr119(
			String typemod, CHWAnnouncement chwA, boolean mgCommon,
			boolean bumpctr, String pimsIdentity) throws Exception {
		return new R119create001ClassificationForMGCommonTypeModel(typemod,
				chwA, mgCommon, bumpctr, pimsIdentity);
	}

	public R120maintainModelValueForTypeMODCharacteristic getr120(
			TypeModel typeModel, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R120maintainModelValueForTypeMODCharacteristic(typeModel,
				chwA, pimsIdentity);
	}

	public R121createModelSelectionDependency getr121(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R121createModelSelectionDependency(typeModel, chwA,
				pimsIdentity);
	}

	public R123create300ClassificationForTypeModels getr123(TypeModel typeModel,
			TypeModelUPGGeo tmUPGObj, String newFlag, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {
		return new R123create300ClassificationForTypeModels(typeModel, tmUPGObj,
				newFlag, chwA, FromToType, pimsIdentity);
	}

	public R124createUpgradeValueForTypeMCCharacteristic getr124(Vector tmugV,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R124createUpgradeValueForTypeMCCharacteristic(tmugV, chwA,
				pimsIdentity);
	}

	public R125create300ClassificationForTypeMCForUPG getr125(String type,
			CHWAnnouncement chwA, String newFlag, String pimsIdentity)
			throws Exception {
		return new R125create300ClassificationForTypeMCForUPG(type, chwA,
				newFlag, pimsIdentity);
	}

	public R126createRPQTypeFeatureCharacteristic getr126(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R126createRPQTypeFeatureCharacteristic(typeFeature, chwA,
				pimsIdentity);
	}

	public R127createRPQClass getr127(TypeFeature typeFeature,
			String featRanges, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R127createRPQClass(typeFeature, featRanges, chwA,
				pimsIdentity);
	}

	public R128assignRPQTypeFeatureCharacteristicToTypeClass getr128(
			TypeFeature typeFeature, String featRanges, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R128assignRPQTypeFeatureCharacteristicToTypeClass(
				typeFeature, featRanges, chwA, pimsIdentity);
	}

	public R129maintainTypeFeatureCharacteristic getr129(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R129maintainTypeFeatureCharacteristic(typeFeature, chwA,
				pimsIdentity);

	}

	public R130createTypeFEATClass getr130(String type, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R130createTypeFEATClass(type, featRanges, chwA, pimsIdentity);

	}

	public R131createTypeUFClass getr131(String type, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R131createTypeUFClass(type, featRanges, chwA, pimsIdentity);
	}

	public R133updateMaterialBasicViewForTypeModel getr133(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity, CHWGeoAnn chwAg)
			throws Exception {
		return new R133updateMaterialBasicViewForTypeModel(typeModel, chwA,
				pimsIdentity, chwAg);
	}

	public R134assignRPQTypeFeatureCharacteristicToTypeClass getr134(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R134assignRPQTypeFeatureCharacteristicToTypeClass(
				typeFeature, chwA, pimsIdentity);
	}

	public R135assignTypeFeatureCharacteristicToTypeUFClass getr135(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R135assignTypeFeatureCharacteristicToTypeUFClass(
				typeFeature, chwA, pimsIdentity);
	}

	public R136create300ClassificationForTypeFEAT getr136(
			TypeModelUPGGeo tmUPGObj, String range, CHWAnnouncement chwA,
			String newFlag, String FromToType, String pimsIdentity)
			throws Exception {
		return new R136create300ClassificationForTypeFEAT(tmUPGObj, range,
				chwA, newFlag, FromToType, pimsIdentity);
	}

	public R137create300ClassificationForTypeUFForMTC getr137(
			TypeModelUPGGeo tmUPGObj, String range, CHWAnnouncement chwA,
			String newFlag, String FromToType, String pimsIdentity)
			throws Exception {
		return new R137create300ClassificationForTypeUFForMTC(tmUPGObj, range,
				chwA, newFlag, FromToType, pimsIdentity);
	}

	public R138create300ClassificationForTypeRPQ getr138(TypeFeature tfc,
			String newFlag, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R138create300ClassificationForTypeRPQ(tfc, newFlag, chwA,
				pimsIdentity);
	}

	public R138_mtccreate300ClassificationForTypeRPQ getr138(
			TypeModelUPGGeo tmUPGObj, String newFlag, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {
		return new R138_mtccreate300ClassificationForTypeRPQ(tmUPGObj, newFlag,
				chwA, FromToType, pimsIdentity);
	}

	public R142_createSalesBOM getr142(String type, String sapPlant,
			Vector geoV, String newFlag, CHWAnnouncement chwA,
			Hashtable spItem_Categ, String pimsIdentity) throws Exception {
		return new R142_createSalesBOM(type, sapPlant, geoV, newFlag, chwA,
				spItem_Categ, pimsIdentity);
	}

	public R143assignTypeModelAsSalesBOMItemWithDependencies getr143(
			String type, String sapPlant, Vector geoV, String newFlag,
			CHWAnnouncement chwA, Hashtable spItem_Categ, String pimsIdentity)
			throws Exception {
		return new R143assignTypeModelAsSalesBOMItemWithDependencies(type,
				sapPlant, geoV, newFlag, chwA, spItem_Categ, pimsIdentity);
	}

	public R144updateParkStatus getr144(String annno, String zdmstatus,
			String pimsIdentity) throws Exception {
		return new R144updateParkStatus(annno, zdmstatus, pimsIdentity);
	}

	public R148_createConfigurationProfileForNewMaterial getr148(
			String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R148_createConfigurationProfileForNewMaterial(typeStr, chwA,
				pimsIdentity);
	}

	public R149_createConfigurationProfileForUPGMaterial getr149(
			String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R149_createConfigurationProfileForUPGMaterial(typeStr, chwA,
				pimsIdentity);
	}

	public R150create012ClassificationForMOD getr150(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R150create012ClassificationForMOD(typeModel, chwA,
				pimsIdentity);
	}

	public R151create012ClassificationForMC getr151(String type,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R151create012ClassificationForMC(type, chwA, pimsIdentity);
	}

	public R152create012ClassificationForRPQTypeFeature getr152(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R152create012ClassificationForRPQTypeFeature(typeFeature,
				chwA, pimsIdentity);
	}

	public R153create012ClassificationForTypeFeature getr153(
			TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R153create012ClassificationForTypeFeature(typeFeature, chwA,
				pimsIdentity);
	}

	public R156createZDMClassificationForTypeModel getr156(String typemod,
			String div, CHWAnnouncement chwA, String pimsIdentity,
			boolean seoFlag) throws Exception {
		return new R156createZDMClassificationForTypeModel(typemod, div, chwA,
				pimsIdentity, seoFlag);
	}

	public R157createTypeClass getr157(CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R157createTypeClass(chwA, tmUPGObj, FromToType, pimsIdentity);
	}

	public R159createTypeMCCharacteristic getr159(CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R159createTypeMCCharacteristic(chwA, tmUPGObj, FromToType,
				pimsIdentity);
	}

	public R160assignChartoClassFEAT_0000 getr160(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R160assignChartoClassFEAT_0000(typeModel, chwA, pimsIdentity);
	}

	public R161createZDMClassificationForMKFEX getr161(String type,
			String newFlag, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R161createZDMClassificationForMKFEX(type, newFlag, chwA,
				pimsIdentity);
	}

	public R162createZDMClassificationForMKFEATCONV getr162(
			TypeFeatureUPGGeo tfugObj, TypeModelUPGGeo tmUPGObj,
			String newFlag, CHWAnnouncement chwA, String FromToType,
			String pimsIdentity) throws Exception {
		return new R162createZDMClassificationForMKFEATCONV(tfugObj, tmUPGObj,
				newFlag, chwA, FromToType, pimsIdentity);
	}

	public R163createUpgradeValueForTypeMTCCharacteristic getr163(String type,
			TypeModelUPGGeo typeModelUpg, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R163createUpgradeValueForTypeMTCCharacteristic(type,
				typeModelUpg, chwA, pimsIdentity);
	}

	public R164create300ClassificationForTypeMTC getr164(
			TypeModelUPGGeo tmUPGObj, CHWAnnouncement chwA, String FromToType,
			String pimsIdentity) throws Exception {
		return new R164create300ClassificationForTypeMTC(tmUPGObj, chwA,
				FromToType, pimsIdentity);
	}

	public R165assignCharacteristicToMTCClass300 getr165(CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R165assignCharacteristicToMTCClass300(chwA, tmUPGObj,
				FromToType, pimsIdentity);
	}

	public R166createSTPPlantViewForMaterial getr166(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String storageLocation,
			String newFlag) throws Exception {
		return new R166createSTPPlantViewForMaterial(chwA, typeModel, chwAg,
				storageLocation, newFlag);
	}

	public R168create012ClassificationForMTC getr168(TypeModelUPGGeo tmUPGObj,
			CHWAnnouncement chwA, String FromToType, String pimsIdentity)
			throws Exception {
		return new R168create012ClassificationForMTC(tmUPGObj, chwA,
				FromToType, pimsIdentity);
	}

	public R169ReadTypeModelsFromBOM getr169(String type, String plant,
			String newFlag) throws Exception {
		return new R169ReadTypeModelsFromBOM(type, plant, newFlag);
	}

	public R170removeTypeModelsFromBOM getr170(String type, Hashtable matches,
			String sapPlant, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R170removeTypeModelsFromBOM(type, matches, sapPlant,
				newFlag, chwA, pimsIdentity);
	}

	public R171markTypeModelMaterialForDeletion getr171(String typemod,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R171markTypeModelMaterialForDeletion(typemod, chwA,
				pimsIdentity);
	}

	public R172deleteModelValueFromTypeMODCharacteristic getr172(
			TypeModel typeModel, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R172deleteModelValueFromTypeMODCharacteristic(typeModel,
				chwA, pimsIdentity);
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

	public R179ReadPlannedChangeForTypeModelMaterial getr179(String typemod,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {
		return new R179ReadPlannedChangeForTypeModelMaterial(typemod, annDocNo,
				check, pimsIdentity, salesOrg);
	}

	public R182deleteModelSelectionDependency getr182(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R182deleteModelSelectionDependency(typeModel, chwA,
				pimsIdentity);
	}

	public R183createCFIPlantViewForTypeModelMaterial getr183(String annDocNo,
			String typemod, String sapPlant, String pimsIdentity,
			String profitCenter, CHWGeoAnn chwAg) throws Exception {
		return new R183createCFIPlantViewForTypeModelMaterial(annDocNo,
				typemod, sapPlant, pimsIdentity, profitCenter, chwAg);
	}

	public R185deleteupgradevaluefromMCcharacteristic getr185(
			TypeModelUPGGeo typeModel, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R185deleteupgradevaluefromMCcharacteristic(typeModel, chwA,
				pimsIdentity);
	}

	public R186DeleteTypeFeatureCharacteristic getr186(TypeFeature typeFeature,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R186DeleteTypeFeatureCharacteristic(typeFeature, chwA,
				pimsIdentity);
	}

	// public R187DeleteTypeFeatureCharacteristicClassificationtoFEAT getr187(
	// TypeFeature typeFeature, CHWAnnouncement chwA, String pimsIdentity)
	// throws Exception {
	// return new R187DeleteTypeFeatureCharacteristicClassificationtoFEAT(
	// typeFeature, chwA, pimsIdentity);
	// }
	//
	// public R188DeleteTypeFeatureCharacteristicClassificationtoUF getr188(
	// TypeFeature tfObj, CHWAnnouncement chwA, String pimsIdentity)
	// throws Exception {
	// return new R188DeleteTypeFeatureCharacteristicClassificationtoUF(tfObj,
	// chwA, pimsIdentity);
	// }

	public R189createCFIPlantViewForType getr189(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity,
			CHWGeoAnn chwAg) throws Exception {
		return new R189createCFIPlantViewForType(chwA, typeModel, sapPlant,
				newFlag, tmUPGObj, FromToType, pimsIdentity, chwAg);

	}

	public R193ReadRevenueProfile getr193(String type, String newFlag,
			String _plant) throws Exception {

		return new R193ReadRevenueProfile(type, newFlag, _plant);
	}

	public R194CreateRevenueProfile getr194(String type, String annDocNo,
			Vector auoMaterials, Vector typeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String plant) throws Exception {
		return new R194CreateRevenueProfile(type, annDocNo, auoMaterials,
				typeModelRevs, revProfileName, newFlag, pimsIdentity, plant);
	}

	public R195DeleteRevenueProfile getr195(String type, String annDocNo,
			Vector revData, Vector typeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String _plant)
			throws Exception {
		return new R195DeleteRevenueProfile(type, annDocNo, revData,
				typeModelRevs, revProfileName, newFlag, pimsIdentity, _plant);
	}

	public R196UpdateRevenueProfile getr196(String type, String annDocNo,
			Vector auoMaterials, Vector TypeModelRevs, String revProfileName,
			String newFlag, String pimsIdentity, String _plant)
			throws Exception {
		return new R196UpdateRevenueProfile(type, annDocNo, auoMaterials,
				TypeModelRevs, revProfileName, newFlag, pimsIdentity, _plant);
	}

	public R197createLifecycleRow getr197(String material, String varCond,
			String salesStatus, Date validFrom, Date validTo, String user,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {
		return new R197createLifecycleRow(material, varCond, salesStatus,
				validFrom, validTo, user, annDocNo, check, pimsIdentity,
				salesOrg);
	}

	public R198updateLifecycleRow getr198(String material, String varCond,
			String salesStatus, Date validFrom, Date validTo, String user,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {
		return new R198updateLifecycleRow(material, varCond, salesStatus,
				validFrom, validTo, user, annDocNo, check, pimsIdentity,
				salesOrg);
	}

	public R199_deleteLifecycleRow getr199(String material, String varCond,
			String salesStatus, Date validTo, String user, String annDocNo,
			String check, String pimsIdentity, String salesOrg)
			throws Exception {
		return new R199_deleteLifecycleRow(material, varCond, salesStatus,
				validTo, user, annDocNo, check, pimsIdentity, salesOrg);
	}

	public R200_readLifecycleRow getr200(String material, String varCond,
			String annDocNo, String check, String pimsIdentity, String salesOrg)
			throws Exception {
		return new R200_readLifecycleRow(material, varCond, annDocNo, check,
				pimsIdentity, salesOrg);
	}

	public R201createUpgradeValueForTypeMCCharacteristic getr201(
			TypeModelUPGGeo typeModelUpg, CHWAnnouncement chwA,
			String FROMTOTYPE, String pimsIdentity) throws Exception {
		return new R201createUpgradeValueForTypeMCCharacteristic(typeModelUpg,
				chwA, FROMTOTYPE, pimsIdentity);
	}

	public R202_createConfigurationProfileForMTCMaterial getr202(
			String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		return new R202_createConfigurationProfileForMTCMaterial(typeStr, chwA,
				pimsIdentity);
	}

	public R204ReadMaterial getr204(String material) throws Exception {
		return new R204ReadMaterial(material);
	}

	public R205ClassificationForBTProductsTypeMaterials getr205(
			TypeModel typeModel, TypeModelUPGGeo tmupg, String newFlag,
			String fromtotype, String typeProfRefresh, String type,
			String profile, String pimsIdentity) throws Exception {
		return new R205ClassificationForBTProductsTypeMaterials(typeModel,
				tmupg, newFlag, fromtotype, typeProfRefresh, type, profile,
				pimsIdentity);
	}

	// public R207ReadPlantViewMaterial getr207(String type, String model,
	// String plant) throws Exception {
	// return new R207ReadPlantViewMaterial(type, model, plant);
	// }

	public R209ReadBasicViewOfMaterial getr209(String material)
			throws Exception {
		return new R209ReadBasicViewOfMaterial(material);
	}

	public R210ReadSalesBom getr210(String type, String newFlag, String _plant)
			throws Exception {
		return new R210ReadSalesBom(type, newFlag, _plant);
	}

	public R211CreateSalesBOMfortypeMTC getr211(String type, String sapPlant,
			Vector geoV, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R211CreateSalesBOMfortypeMTC(type, sapPlant, geoV, newFlag,
				chwA, pimsIdentity);
	}

	public R212DeleteSalesBOMfortypeMTC getr212(String type, String sapPlant,
			Vector componentstoDeleteTypeMTC, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		return new R212DeleteSalesBOMfortypeMTC(type, sapPlant,
				componentstoDeleteTypeMTC, newFlag, chwA, pimsIdentity);
	}

	public R213UpdateSalesBOMItemWithtypeMTC getr213(String type,
			String sapPlant, Vector geoV, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		return new R213UpdateSalesBOMItemWithtypeMTC(type, sapPlant, geoV,
				newFlag, chwA, pimsIdentity);
	}

	public R214ReadMCclass getr214(String type) throws Exception {
		return new R214ReadMCclass(type);
	}

	public R260updateProdHierarchyOnSalesView getr260(CHWAnnouncement chwA,
			Object material, String pimsIdentity, String salesOrg,
			String productHierarchy, CHWGeoAnn chwAg, String acctAsgnGrp)
			throws Exception {
		return new R260updateProdHierarchyOnSalesView(chwA, material,
				pimsIdentity, salesOrg, productHierarchy, chwAg, acctAsgnGrp);
	}

	// public R261PlantViewMaterial getr261(String material) throws Exception {
	// return new R261PlantViewMaterial(material);
	// }

	public R262createPlantViewProfitCenterForMaterial getr262(
			CHWAnnouncement chwA, String material, String sapPlant,
			String pimsIdentity, String profitCenter, CHWGeoAnn chwAg)
			throws Exception {
		return new R262createPlantViewProfitCenterForMaterial(chwA, material,
				sapPlant, pimsIdentity, profitCenter, chwAg);
	}
}
