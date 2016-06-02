package com.ibm.rdh.rfc.proxy;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.pprds.epimshw.util.RfcLogger;
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
import com.ibm.rdh.chw.caller.R209ReadBasicViewOfMaterial;
import com.ibm.rdh.chw.caller.R210ReadSalesBom;
import com.ibm.rdh.chw.caller.R211CreateSalesBOMfortypeMTC;
import com.ibm.rdh.chw.caller.R212DeleteSalesBOMfortypeMTC;
import com.ibm.rdh.chw.caller.R213UpdateSalesBOMItemWithtypeMTC;
import com.ibm.rdh.chw.caller.R214ReadMCclass;
import com.ibm.rdh.chw.caller.R260updateProdHierarchyOnSalesView;
import com.ibm.rdh.chw.caller.R262createPlantViewProfitCenterForMaterial;
import com.ibm.rdh.chw.caller.Rfc;
import com.ibm.rdh.chw.caller.RfcReturnSeverityCodes;
import com.ibm.rdh.chw.entity.BasicMaterialFromSAP;
import com.ibm.rdh.chw.entity.BomComponent;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.DepData;
import com.ibm.rdh.chw.entity.LifecycleData;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;
import com.ibm.rdh.chw.entity.RevData;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.BapimatdoaStructure;
import com.ibm.rdh.rfc.Stpo_api02Table;
import com.ibm.rdh.rfc.Stpo_api02TableRow;
import com.ibm.rdh.rfc.Zdm_mat_psales_statusTable;
import com.ibm.rdh.rfc.Zdm_mat_psales_statusTableRow;
import com.ibm.rdh.rfc.ZdmchwplcTable;
import com.ibm.rdh.rfc.ZdmchwplcTableRow;
import com.sap.rfc.IRfcConnection;
//import com.ibm.rdh.chw.caller.R161createZDMClassificationForMKFEX;

//import com.ibm.rdh.rfc.ReturnDataObjectR001;
/**
 * Read only Rfc proxy. Retrieves data via Rfcs but does not modify any SAP
 * objects. Proxy is responsible for logging rfc actions.
 * 
 * @author waltond
 */
public class RdhRestProxy extends RfcProxy implements RfcReturnSeverityCodes {

	private int _sapRetryCount;
	private long _sapRetrySleepMillis;
	private RfcLogger rfcLogger;

	protected int sapWaitOnRetry = 0;
	protected int sapRFCRetries = 0;
	protected IRfcConnection aConnection = null;
	public static String PREANNOUNCE = "YA";
	public static String ANNOUNCE = "Z0";
	public static String WDFM = "ZJ";

	protected static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	public RdhRestProxy() {
		super();
		setRfcFactory(new RfcFactory());
	}

	public RdhRestProxy(RfcLogger rfcLogger) {
		this();
		this.rfcLogger = rfcLogger;
	}

	public int getSapWaitOnRetry() {
		return sapWaitOnRetry;
	}

	public int getSapRFCRetries() {
		return sapRFCRetries;
	}

	@Override
	public void r100(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String newFlag, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R100createTypeMaterialBasicView r = getFactory().getr100(chwA,
				typeModel, chwAg, newFlag, tmUPGObj, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r101(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String newFlag, String loadingGrp,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity,
			String plantValue) throws Exception {
		R101createGenericPlantViewforMaterial r = getFactory().getr101(chwA,
				typeModel, chwAg, newFlag, loadingGrp, tmUPGObj, FromToType,
				pimsIdentity, plantValue);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r102(CHWAnnouncement chwA, TypeModel typeModel,
			String sapPlant, String newFlag, TypeModelUPGGeo tmUPGObj,
			String fromToType, String pimsIdentity, String flfilcd,
			String salesOrg, Vector taxCntryList, CHWGeoAnn chwAg)
			throws Exception {
		R102createSalesViewforMaterial r = getFactory().getr102(chwA,
				typeModel, sapPlant, newFlag, tmUPGObj, fromToType,
				pimsIdentity, flfilcd, salesOrg, taxCntryList, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r103(TypeModel typeModel, String newFlag, CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String fromToType, String pimsIdentity)
			throws Exception {
		R103create001ClassificationForMGCommon r = getFactory().getr103(
				typeModel, newFlag, chwA, tmUPGObj, fromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r104(TypeModel typeModel, String newFlag, CHWAnnouncement chwA,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {

		R104createZDMClassification r = getFactory().getr104(typeModel,
				newFlag, chwA, tmUPGObj, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r106(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {

		R106createTypeModelsClass r = getFactory().getr106(typeModel, chwA,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r107(String type, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R107createTypeMCClass r = getFactory()
				.getr107(type, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r108(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R108createTypeMODCharacteristic r = getFactory().getr108(typeModel,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r109(String type, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R109createTypeMCCharacteristic r = getFactory().getr109(type, chwA,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r110(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R110assignMODCharacteristicToModelsClass r = getFactory().getr110(
				typeModel, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r111(String type, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R111assignMODCharacteristicToModelsClass r = getFactory().getr111(type,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r115(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String pimsIdentity, String plantValue)
			throws Exception {
		R115createTypeModelMaterialBasicView r = getFactory().getr115(chwA,
				typeModel, chwAg, pimsIdentity, plantValue);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r116(CHWAnnouncement chwA, TypeModel typeModel,
			String sapPlant, String loadingGroup, CHWGeoAnn chwAg,
			String storageLocation, String pimsIdentity) throws Exception {
		R116createPlantViewForTypeModelMaterial r = getFactory().getr116(chwA,
				typeModel, sapPlant, loadingGroup, chwAg, storageLocation,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r117(CHWAnnouncement chwA, String typemod, String div,
			String acctAsgnGrp, PlannedSalesStatus ps, boolean bumpCtr,
			String pimsIdentity, String flfil, String salesOrg1,
			String productHierarchy, Vector VectTaxList, String plantValue,
			CHWGeoAnn chwAg) throws Exception {
		R117createTypeModelMaterialSalesView r = getFactory().getr117(chwA,
				typemod, div, acctAsgnGrp, ps, bumpCtr, pimsIdentity, flfil,
				salesOrg1, productHierarchy, VectTaxList, plantValue, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r118(TypeModel typeModel, CHWAnnouncement chwA, String flfilcd,
			String warrantyPeriod, boolean remarkable, String pimsIdentity)
			throws Exception {
		R118create001ClassificationForMMFieldsTypeModel r = getFactory()
				.getr118(typeModel, chwA, flfilcd, warrantyPeriod, remarkable,
						pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r119(String typemod, CHWAnnouncement chwA, boolean mgCommon,
			boolean bumpctr, String pimsIdentity) throws Exception {
		R119create001ClassificationForMGCommonTypeModel r = getFactory()
				.getr119(typemod, chwA, mgCommon, bumpctr, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r120(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {

		R120maintainModelValueForTypeMODCharacteristic r = getFactory()
				.getr120(typeModel, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r121(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R121createModelSelectionDependency r = getFactory().getr121(typeModel,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r123(String type, TypeModelUPGGeo tmUPGObj, String newFlag,
			CHWAnnouncement chwA, String FromToType, String pimsIdentity)
			throws Exception {
		R123create300ClassificationForTypeModels r = getFactory().getr123(type,
				tmUPGObj, newFlag, chwA, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r124(Vector tmugV, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R124createUpgradeValueForTypeMCCharacteristic r = getFactory().getr124(
				tmugV, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r125(String type, CHWAnnouncement chwA, String newFlag,
			String pimsIdentity) throws Exception {
		R125create300ClassificationForTypeMCForUPG r = getFactory().getr125(
				type, chwA, newFlag, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r126(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R126createRPQTypeFeatureCharacteristic r = getFactory().getr126(
				typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r127(TypeFeature typeFeature, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R127createRPQClass r = getFactory().getr127(typeFeature, featRanges,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r128(TypeFeature typeFeature, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R128assignRPQTypeFeatureCharacteristicToTypeClass r = getFactory()
				.getr128(typeFeature, featRanges, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r129(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {

		R129maintainTypeFeatureCharacteristic r = getFactory().getr129(
				typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r130(String type, String featRanges, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R130createTypeFEATClass r = getFactory().getr130(type, featRanges,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r131(String type, String featRanges, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {

		R131createTypeUFClass r = getFactory().getr131(type, featRanges, chwA,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r133(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity, CHWGeoAnn chwAg) throws Exception {
		R133updateMaterialBasicViewForTypeModel r = getFactory().getr133(
				typeModel, chwA, pimsIdentity, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r134(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R134assignRPQTypeFeatureCharacteristicToTypeClass r = getFactory()
				.getr134(typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r135(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R135assignTypeFeatureCharacteristicToTypeUFClass r = getFactory()
				.getr135(typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r136(TypeModelUPGGeo tmUPGObj, String range,
			CHWAnnouncement chwA, String newFlag, String FromToType,
			String pimsIdentity) throws Exception {
		R136create300ClassificationForTypeFEAT r = getFactory().getr136(
				tmUPGObj, range, chwA, newFlag, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r137(TypeModelUPGGeo tmUPGObj, String range,
			CHWAnnouncement chwA, String newFlag, String FromToType,
			String pimsIdentity) throws Exception {
		R137create300ClassificationForTypeUFForMTC r = getFactory().getr137(
				tmUPGObj, range, chwA, newFlag, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r138(TypeFeature tfc, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R138create300ClassificationForTypeRPQ r = getFactory().getr138(tfc,
				newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r142(String type, String sapPlant, Vector geoV, String newFlag,
			CHWAnnouncement chwA, Hashtable spItem_Categ, String pimsIdentity)
			throws Exception {
		R142_createSalesBOM r = getFactory().getr142(type, sapPlant, geoV,
				newFlag, chwA, spItem_Categ, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r143(String type, String sapPlant, Vector geoV, String newFlag,
			CHWAnnouncement chwA, Hashtable spItem_Categ, String pimsIdentity)
			throws Exception {
		R143assignTypeModelAsSalesBOMItemWithDependencies r = getFactory()
				.getr143(type, sapPlant, geoV, newFlag, chwA, spItem_Categ,
						pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r144(String annno, String zdmstatus, String pimsIdentity)
			throws Exception {
		R144updateParkStatus r = getFactory().getr144(annno, zdmstatus,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r148(String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R148_createConfigurationProfileForNewMaterial r = getFactory().getr148(
				typeStr, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r149(String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R149_createConfigurationProfileForUPGMaterial r = getFactory().getr149(
				typeStr, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r150(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R150create012ClassificationForMOD r = getFactory().getr150(typeModel,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r151(String type, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R151create012ClassificationForMC r = getFactory().getr151(type, chwA,
				pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r152(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R152create012ClassificationForRPQTypeFeature r = getFactory().getr152(
				typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r153(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R153create012ClassificationForTypeFeature r = getFactory().getr153(
				typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r156(String typemod, String div, CHWAnnouncement chwA,
			String pimsIdentity, boolean seoFlag) throws Exception {
		R156createZDMClassificationForTypeModel r = getFactory().getr156(
				typemod, div, chwA, pimsIdentity, seoFlag);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r157(CHWAnnouncement chwA, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R157createTypeClass r = getFactory().getr157(chwA, tmUPGObj,
				FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r159(CHWAnnouncement chwA, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R159createTypeMCCharacteristic r = getFactory().getr159(chwA, tmUPGObj,
				FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r160(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R160assignChartoClassFEAT_0000 r = getFactory().getr160(typeModel,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r161(String type, String newFlag, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R161createZDMClassificationForMKFEX r = getFactory().getr161(type,
				newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r162(TypeFeatureUPGGeo tfugObj, TypeModelUPGGeo tmUPGObj,
			String newFlag, CHWAnnouncement chwA, String FromToType,
			String pimsIdentity) throws Exception {
		R162createZDMClassificationForMKFEATCONV r = getFactory().getr162(
				tfugObj, tmUPGObj, newFlag, chwA, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r163(String type, TypeModelUPGGeo typeModelUpg,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R163createUpgradeValueForTypeMTCCharacteristic r = getFactory()
				.getr163(type, typeModelUpg, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r164(TypeModelUPGGeo tmUPGObj, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {

		R164create300ClassificationForTypeMTC r = getFactory().getr164(
				tmUPGObj, chwA, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r165(CHWAnnouncement chwA, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R165assignCharacteristicToMTCClass300 r = getFactory().getr165(chwA,
				tmUPGObj, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r166(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String storageLocation, String newFlag)
			throws Exception {
		R166createSTPPlantViewForMaterial r = getFactory().getr166(chwA,
				typeModel, chwAg, storageLocation, newFlag);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r168(TypeModelUPGGeo tmUPGObj, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {
		R168create012ClassificationForMTC r = getFactory().getr168(tmUPGObj,
				chwA, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public Hashtable r169(String type, BomComponent bCom, String plant,
			String newFlag) throws Exception {

		R169ReadTypeModelsFromBOM r = getFactory()
				.getr169(type, plant, newFlag);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
		com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc = r.getRfc();
		Hashtable bomComponents = new Hashtable();
		if (rfc != null) {
			logger.info("R169ReadTypeModelsFromBOM is not null.");
			Stpo_api02Table stpo = rfc.getTStpo();
			int rows = stpo.getRowCount();
			for (int i = 0; i < rows; i++) {
				Stpo_api02TableRow stpoRow = (Stpo_api02TableRow) stpo
						.getRow(i);

				bCom.setItem_Categ(stpoRow.getItemCateg());
				bCom.setItem_No(stpoRow.getItemNo());
				bCom.setComponent(stpoRow.getComponent());
				bCom.setItem_Node(stpoRow.getItemNode());
				bCom.setItem_Count(stpoRow.getItemCount());
				String comp = bCom.getComponent();
				bomComponents.put(comp, bCom);
			}
			return bomComponents;
		} else {
			logger.info("R169ReadTypeModelsFromBOM is null.");
		}
		return bomComponents;
	}

	@Override
	public void r170(String type, Hashtable matches, String sapPlant,
			String newFlag, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R170removeTypeModelsFromBOM r = getFactory().getr170(type, matches,
				sapPlant, newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r171(String typemod, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R171markTypeModelMaterialForDeletion r = getFactory().getr171(typemod,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r172(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R172deleteModelValueFromTypeMODCharacteristic r = getFactory().getr172(
				typeModel, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r175(TypeModel typemodel, TypeModelUPGGeo tmUPGObj,
			CHWAnnouncement chwA, String newFlag, String FromToType,
			String pimsIdentity) throws Exception {
		R175create001ClassificationForMMFieldsType r = getFactory().getr175(
				typemodel, tmUPGObj, chwA, newFlag, FromToType, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r176(String type, String range, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R176create300ClassificationForTypeFEAT r = getFactory().getr176(type,
				range, newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r177(String type, String range, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R177create300ClassificationForTypeUFForUPG r = getFactory().getr177(
				type, range, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public Vector r179(String typemod, String annDocNo, String check,
			String pimsIdentity, String salesOrg) throws Exception {

		R179ReadPlannedChangeForTypeModelMaterial r = getFactory().getr179(
				typemod, annDocNo, check, pimsIdentity, salesOrg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

		Zdm_mat_psales_statusTable temp = r.getRfc().getMaterialsTab();
		Vector Vecplannedsales = new Vector();
		for (int i = temp.getRowCount() - 1; i >= 0; i--) {
			PlannedSalesStatus ps = new PlannedSalesStatus();
			Zdm_mat_psales_statusTableRow row = temp.getRow(i);

			ps.setMatnr(row.getMatnr());
			ps.setVkorg(row.getVkorg());
			ps.setCurrentSalesStatus(row.getZdmCstatus());
			ps.setCurrentEffectiveDate(row.getZdmCdat());
			ps.setPlannedChangeSalesStatus(row.getZdmPstatus());
			ps.setOrigPlannedSalesStatus(row.getZdmPstatus());

			if (!(ps.getPlannedChangeSalesStatus() == null || ps
					.getPlannedChangeSalesStatus().trim().equals(""))) {
				ps.setPlannedEffectiveDate(row.getZdmFrdat());
			} else {
				ps.setPlannedEffectiveDate(null);
			}
			ps.setOrigSalesStatus(row.getZdmCstatus());

			ps.setOrigPlannedEffectiveDate(ps.getPlannedEffectiveDate());
			Vecplannedsales.add(ps);

		}
		return Vecplannedsales;
	}

	@Override
	public void r182(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R182deleteModelSelectionDependency r = getFactory().getr182(typeModel,
				chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r183(String annDocNo, String typemod, String sapPlant,
			String pimsIdentity, String profitCenter, CHWGeoAnn chwAg)
			throws Exception {
		R183createCFIPlantViewForTypeModelMaterial r = getFactory().getr183(
				annDocNo, typemod, sapPlant, pimsIdentity, profitCenter, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r185(TypeModelUPGGeo typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R185deleteupgradevaluefromMCcharacteristic r = getFactory().getr185(
				typeModel, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r186(TypeFeature typeFeature, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R186DeleteTypeFeatureCharacteristic r = getFactory().getr186(
				typeFeature, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	// @Override
	// public void r187(TypeFeature typeFeature, CHWAnnouncement chwA,
	// String pimsIdentity) throws Exception {
	// R187DeleteTypeFeatureCharacteristicClassificationtoFEAT r = getFactory()
	// .getr187(typeFeature, chwA, pimsIdentity);
	// logPromoteInfoMessage(r);
	// r.evaluate();
	// logPromoteResultMessage(r);
	//
	// }

	// @Override
	// public void r188(TypeFeature tfObj, CHWAnnouncement chwA,
	// String pimsIdentity) throws Exception {
	// R188DeleteTypeFeatureCharacteristicClassificationtoUF r = getFactory()
	// .getr188(tfObj, chwA, pimsIdentity);
	// logPromoteInfoMessage(r);
	// r.evaluate();
	// logPromoteResultMessage(r);
	// }

	@Override
	public void r189(CHWAnnouncement chwA, TypeModel typeModel,
			String sapPlant, String newFlag, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity, CHWGeoAnn chwAg)
			throws Exception {
		R189createCFIPlantViewForType r = getFactory().getr189(chwA, typeModel,
				sapPlant, newFlag, tmUPGObj, FromToType, pimsIdentity, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public Vector r193(String type, String newFlag, String _plant)
			throws Exception {
		R193ReadRevenueProfile r = getFactory().getr193(type, newFlag, _plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
		com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc = r.getRfc();

		Vector revProfileData = new Vector();
		if (rfc != null) {
			logger.info("R193ReadRevenueProfile is not null.");

			Stpo_api02Table stpo = rfc.getTStpo();
			int rows = stpo.getRowCount();

			for (int i = 0; i < rows; i++) {
				Stpo_api02TableRow stpoRow = (Stpo_api02TableRow) stpo
						.getRow(i);
				String itemCatalog = stpoRow.getItemCateg();
				String itemNo = stpoRow.getItemNo();
				String component = stpoRow.getComponent();
				BigInteger itemNode = stpoRow.getItemNode();
				BigInteger itemCount = stpoRow.getItemCount();
				String sortString = stpoRow.getSortstring();
				RevData revData = new RevData(itemCatalog, itemNo, component,
						sortString, itemNode, itemCount);
				revProfileData.add(revData);
			}
			return revProfileData;

		} else {
			logger.info("R193ReadRevenueProfile is null.");
			revProfileData = null;
		}
		return revProfileData;

	}

	@Override
	public void r194(String type, String annDocNo, Vector auoMaterials,
			Vector typeModelRevs, String revProfileName, String newFlag,
			String pimsIdentity, String plant) throws Exception {
		R194CreateRevenueProfile r = getFactory().getr194(type, annDocNo,
				auoMaterials, typeModelRevs, revProfileName, newFlag,
				pimsIdentity, plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public void r195(String type, String annDocNo, Vector revData,
			Vector typeModelRevs, String revProfileName, String newFlag,
			String pimsIdentity, String _plant) throws Exception {
		R195DeleteRevenueProfile r = getFactory().getr195(type, annDocNo,
				revData, typeModelRevs, revProfileName, newFlag, pimsIdentity,
				_plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r196(String type, String annDocNo, Vector auoMaterials,
			Vector TypeModelRevs, String revProfileName, String newFlag,
			String pimsIdentity, String _plant) throws Exception {
		R196UpdateRevenueProfile r = getFactory().getr196(type, annDocNo,
				auoMaterials, TypeModelRevs, revProfileName, newFlag,
				pimsIdentity, _plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r197(String material, String varCond, String salesStatus,
			Date validFrom, Date validTo, String user, String annDocNo,
			String check, String pimsIdentity, String salesOrg)
			throws Exception {
		R197createLifecycleRow r = getFactory().getr197(material, varCond,
				salesStatus, validFrom, validTo, user, annDocNo, check,
				pimsIdentity, salesOrg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r198(String material, String varCond, String salesStatus,
			Date validFrom, Date validTo, String user, String annDocNo,
			String check, String pimsIdentity, String salesOrg)
			throws Exception {
		R198updateLifecycleRow r = getFactory().getr198(material, varCond,
				salesStatus, validFrom, validTo, user, annDocNo, check,
				pimsIdentity, salesOrg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r199(String material, String varCond, String salesStatus,
			Date validTo, String user, String annDocNo, String check,
			String pimsIdentity, String salesOrg) throws Exception {
		R199_deleteLifecycleRow r = getFactory().getr199(material, varCond,
				salesStatus, validTo, user, annDocNo, check, pimsIdentity,
				salesOrg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	@Override
	public LifecycleData r200(String material, String varCond, String annDocNo,
			String check, String pimsIdentity, String salesOrg)
			throws Exception {
		R200_readLifecycleRow r = getFactory().getr200(material, varCond,
				annDocNo, check, pimsIdentity, salesOrg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

		ZdmchwplcTable tab;
		ZdmchwplcTableRow tRow;
		LifecycleData lcd = new LifecycleData();
		String vmsta;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		tab = r.getRfc().getZdmChwPlc();
		int rowCount = tab.getRowCount();
		for (int i = 0; i < rowCount; i++) {

			tRow = tab.getRow(i); // tab.getRow(2)
			vmsta = tRow.getVmsta();
			lcd.setVarCond(tRow.getVarcond());
			if (vmsta.equals(PREANNOUNCE)) {
				lcd.setPreAnnounceValidFrom(sdf.parse(tRow.getDatabString()));
				lcd.setPreAnnounceValidTo(sdf.parse(tRow.getDatbiString()));
			} else if (vmsta.equals(ANNOUNCE)) {
				lcd.setAnnounceValidFrom(sdf.parse(tRow.getDatabString()));
				lcd.setAnnounceValidTo(sdf.parse(tRow.getDatbiString()));
			} else if (vmsta.equals(WDFM)) {
				lcd.setWdfmValidFrom(sdf.parse(tRow.getDatabString()));
				lcd.setWdfmValidTo(sdf.parse(tRow.getDatbiString()));
			}

		} // end CheckUpdateMode()
		return lcd;
	}

	@Override
	public void r201(TypeModelUPGGeo typeModelUpg, CHWAnnouncement chwA,
			String FROMTOTYPE, String pimsIdentity) throws Exception {
		R201createUpgradeValueForTypeMCCharacteristic r = getFactory().getr201(
				typeModelUpg, chwA, FROMTOTYPE, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r202(String typeStr, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		R202_createConfigurationProfileForMTCMaterial r = getFactory().getr202(
				typeStr, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public boolean r204(String material) throws Exception {
		R204ReadMaterial r = getFactory().getr204(material);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

		boolean exists = false;
		if ((r.getRfc().getReturn().getType()).equalsIgnoreCase("S")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	// @Override
	// public boolean r207(String type, String model, String plant)
	// throws Exception {
	// R207ReadPlantViewMaterial r = getFactory().getr207(type, model, plant);
	// logPromoteInfoMessage(r);
	// r.evaluate();
	// logPromoteResultMessage(r);
	//
	// com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC rfc = r.getRfc();
	// boolean plantcheck = false;
	// if (rfc != null) {
	// logger.info("R207ReadPlantViewMaterial is not null.");
	// Zdm_marc_dataTable plantvalues = rfc.getZdmMarcData();
	// int rows = plantvalues.getRowCount();
	// if (rows > 0) {
	// plantcheck = true;
	// } else if (rows == 0) {
	// plantcheck = false;
	// }
	// } else {
	// logger.info("R207ReadPlantViewMaterial is null.");
	// }
	// return plantcheck;
	//
	// }

	@Override
	public BasicMaterialFromSAP r209(String material) throws Exception {
		R209ReadBasicViewOfMaterial r = getFactory().getr209(material);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

		BasicMaterialFromSAP basicMaterialFromSAP = new BasicMaterialFromSAP();
		BapimatdoaStructure sharedMat = r.getRfc().getMaterialGeneralData();
		basicMaterialFromSAP.setMatlType(sharedMat.getMatlType());
		basicMaterialFromSAP.setProdHier(sharedMat.getProdHier());
		return basicMaterialFromSAP;
	}

	@Override
	public Vector r210(String type, String newFlag, String _plant)
			throws Exception {
		R210ReadSalesBom r = getFactory().getr210(type, newFlag, _plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
		com.ibm.rdh.rfc.CSAP_MAT_BOM_READ rfc = r.getRfc();
		Vector salesBOM = new Vector();
		if (rfc != null) {
			logger.info("R210ReadSalesBom is not null.");
			Stpo_api02Table stpo = rfc.getTStpo();
			int rows = stpo.getRowCount();
			for (int i = 0; i < rows; i++) {

				Stpo_api02TableRow stpoRow = (Stpo_api02TableRow) stpo
						.getRow(i);
				String itemCatalog = stpoRow.getItemCateg();
				String itemNo = stpoRow.getItemNo();
				String component = stpoRow.getComponent();
				BigInteger itemNode = stpoRow.getItemNode();
				BigInteger itemCount = stpoRow.getItemCount();
				String sortString = stpoRow.getSortstring();
				DepData depData = new DepData(itemCatalog, itemNo, component,
						sortString, null, null, itemNode, itemCount);

				salesBOM.add(depData);
			}

			return salesBOM;
		} else {

			logger.info("R210ReadSalesBom is null.");
			salesBOM = null;

		}
		return salesBOM;
	}

	@Override
	public void r211(String type, String sapPlant, Vector geoV, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R211CreateSalesBOMfortypeMTC r = getFactory().getr211(type, sapPlant,
				geoV, newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r212(String type, String sapPlant,
			Vector componentstoDeleteTypeMTC, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R212DeleteSalesBOMfortypeMTC r = getFactory().getr212(type, sapPlant,
				componentstoDeleteTypeMTC, newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r213(String type, String sapPlant, Vector geoV, String newFlag,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		R213UpdateSalesBOMItemWithtypeMTC r = getFactory().getr213(type,
				sapPlant, geoV, newFlag, chwA, pimsIdentity);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public boolean r214(String type) throws Exception {
		R214ReadMCclass r = getFactory().getr214(type);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

		boolean exists = false;
		if ((r.getRfc().getReturn().getType()).equalsIgnoreCase("S")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	@Override
	public void r260(CHWAnnouncement chwA, Object material,
			String pimsIdentity, String salesOrg, String productHierarchy,
			CHWGeoAnn chwAg) throws Exception {
		R260updateProdHierarchyOnSalesView r = getFactory().getr260(chwA,
				material, pimsIdentity, salesOrg, productHierarchy, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	// @Override
	// public Vector r261(String material) throws Exception {
	// R261PlantViewMaterial r = getFactory().getr261(material);
	// logPromoteInfoMessage(r);
	// r.evaluate();
	// logPromoteResultMessage(r);
	// boolean plantcheck = false;
	// Vector plantData = new Vector();
	// boolean checkProfitCenterExists = false;
	//
	// com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC rfc = r.getRfc();
	// if (rfc != null) {
	// logger.info("R261PlantViewMaterial is not null.");
	// Zdm_marc_dataTable plantvalues = rfc.getZdmMarcData();
	// int rows = plantvalues.getRowCount();
	//
	// if (rows > 0) {
	// for (int i = 0; i < rows; i++) {
	// Zdm_marc_dataTableRow plant_row = (Zdm_marc_dataTableRow) plantvalues
	// .getRow(i);
	//
	// String checkPlant = plant_row.getWerks();
	//
	// checkProfitCenterExists = ProfitCenterPlantSelector
	// .checkProfitCenterPlants(checkPlant);
	//
	// System.out
	// .println("Printing the value of boolean exists in the rfc R261"
	// + checkProfitCenterExists);
	// if (checkProfitCenterExists) {
	// MaterailPlantData matplantData = new MaterailPlantData(
	// plant_row.getWerks(), plant_row.getPrctr());
	//
	// plantcheck = true;
	// logger.debug("R261's plant" + plant_row.getWerks());
	// logger.debug("R261's profitcenter"
	// + plant_row.getPrctr());
	// plantData.add(matplantData);
	// }
	// }
	// // return plants;
	// } else if (rows == 0) {
	// // plants = null ;
	// plantcheck = false;
	// }
	//
	// } else {
	// logger.info("R261PlantViewMaterial is null.");
	// }
	// return plantData;
	//
	// }

	@Override
	public void r262(CHWAnnouncement chwA, String material, String sapPlant,
			String pimsIdentity, String profitCenter, CHWGeoAnn chwAg)
			throws Exception {
		R262createPlantViewProfitCenterForMaterial r = getFactory().getr262(
				chwA, material, sapPlant, pimsIdentity, profitCenter, chwAg);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);

	}

	// public ArrayList r060( SWO swo, Announcement ann, Collection plants )
	// throws Exception {
	// R060ReadPlantViewForMaterial r = getRfcFactory().getR060( swo, ann,
	// plants ) ;
	// logPromoteInfoMessage(r);
	// ArrayList marcArray = r.evaluate();
	// return marcArray;
	// }
	//
	// public void r061( SWO swo, Announcement ann, String plant ) throws
	// Exception {
	// R061MaintainPlantViewForProfitCenter r = getRfcFactory().getR061( swo,
	// ann, plant ) ;
	// logPromoteInfoMessage(r);
	// }

	protected int getRetryCount() {
		return _sapRetryCount;
	}

	protected long getRetrySleepMillis() {
		return _sapRetrySleepMillis;
	}

	/**
	 * Log a warning message to the Promote Message log(s)
	 */
	protected void logPromoteWarnMessage(String msgNum, String msgText)
			throws HWPIMSAbnormalException {

		if (null != rfcLogger) {
			rfcLogger.warn(msgNum + msgText);
		}

		if (null != logger) {
			logger.warn(msgNum + msgText);
		}
	}

	/**
	 * Log an error message to the Promote Message log(s)
	 */
	protected void logPromoteErrorMessage(String msgNum, String msgText)
			throws HWPIMSAbnormalException {
		if (null != rfcLogger) {
			rfcLogger.error(msgNum + msgText);
		}
		if (null != logger) {
			logger.error(msgNum + msgText);
		}
	}

	protected void logPromoteInfoMessage(Rfc r) throws HWPIMSAbnormalException {
		logPromoteInfoMessage(r.getMsgNum(), r.getTaskDescription(),
				r.getRfcInfo());
	}

	/**
	 * Log an informational message to the Promote Message log(s)
	 */
	protected void logPromoteInfoMessage(String msgNum, String msgText)
			throws HWPIMSAbnormalException {
		if (null != rfcLogger) {
			rfcLogger.info(msgNum + msgText);
		}
		if (null != logger) {
			logger.info(msgNum + msgText);
		}
	}

	/**
	 * Log an informational message to the Promote Message log(s) Message can
	 * have a parameter list which may be logged differently from the message
	 * body. See PromoteMessageLogger for details.
	 */
	protected void logPromoteInfoMessage(String msgNum, String msgText,
			String parameters) throws HWPIMSAbnormalException {
		if (null != rfcLogger) {
			rfcLogger.info(msgNum + msgText + parameters);
		}
		if (null != logger) {
			logger.info(msgNum + msgText + parameters);
		}
	}

	protected void logPromoteResultMessage(Rfc r)
			throws HWPIMSAbnormalException {
		logPromoteResultMessage(r.getMsgNum(), r.getSeverity(),
				r.getExecutionResultMessage());
	};

	/**
	 * Log the results of an rfc call
	 */
	protected void logPromoteResultMessage(String msgNum, int severity,
			String msgText) throws HWPIMSAbnormalException {
		if (severity == ERROR) {
			this.logPromoteErrorMessage(msgNum, msgText);
		} else if (severity == WARNING) {
			this.logPromoteWarnMessage(msgNum, msgText);
		} else if (severity == INFO) {
			this.logPromoteInfoMessage(msgNum, msgText);
		}
	}

}
