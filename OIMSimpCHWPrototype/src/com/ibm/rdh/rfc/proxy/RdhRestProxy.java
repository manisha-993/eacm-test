package com.ibm.rdh.rfc.proxy;

import java.util.Date;
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
import com.ibm.rdh.chw.caller.R108createTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R110assignMODCharacteristicToModelsClass;
import com.ibm.rdh.chw.caller.R115createTypeModelMaterialBasicView;
import com.ibm.rdh.chw.caller.R116createPlantViewForTypeModelMaterial;
import com.ibm.rdh.chw.caller.R117createTypeModelMaterialSalesView;
import com.ibm.rdh.chw.caller.R118create001ClassificationForMMFieldsTypeModel;
import com.ibm.rdh.chw.caller.R119create001ClassificationForMGCommonTypeModel;
import com.ibm.rdh.chw.caller.R120maintainModelValueForTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R121createModelSelectionDependency;
import com.ibm.rdh.chw.caller.R123create300ClassificationForTypeModels;
import com.ibm.rdh.chw.caller.R130createTypeFEATClass;
import com.ibm.rdh.chw.caller.R131createTypeUFClass;
import com.ibm.rdh.chw.caller.R133updateMaterialBasicViewForTypeModel;
import com.ibm.rdh.chw.caller.R150create012ClassificationForMOD;
import com.ibm.rdh.chw.caller.R156createZDMClassificationForTypeModel;
import com.ibm.rdh.chw.caller.R160assignChartoClassFEAT_0000;
import com.ibm.rdh.chw.caller.R166createSTPPlantViewForMaterial;
import com.ibm.rdh.chw.caller.R171markTypeModelMaterialForDeletion;
import com.ibm.rdh.chw.caller.R172deleteModelValueFromTypeMODCharacteristic;
import com.ibm.rdh.chw.caller.R175create001ClassificationForMMFieldsType;
import com.ibm.rdh.chw.caller.R176create300ClassificationForTypeFEAT;
import com.ibm.rdh.chw.caller.R177create300ClassificationForTypeUFForUPG;
import com.ibm.rdh.chw.caller.R182deleteModelSelectionDependency;
import com.ibm.rdh.chw.caller.R183createCFIPlantViewForTypeModelMaterial;
import com.ibm.rdh.chw.caller.R189createCFIPlantViewForType;
import com.ibm.rdh.chw.caller.R197createLifecycleRow;
import com.ibm.rdh.chw.caller.R198updateLifecycleRow;
import com.ibm.rdh.chw.caller.R207ReadPlantViewMaterial;
import com.ibm.rdh.chw.caller.R209ReadBasicViewOfMaterial;
import com.ibm.rdh.chw.caller.R261PlantViewMaterial;
import com.ibm.rdh.chw.caller.Rfc;
import com.ibm.rdh.chw.caller.RfcReturnSeverityCodes;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.BapireturnStructure;

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
			String salesOrg, Vector taxCntryList) throws Exception {
		R102createSalesViewforMaterial r = getFactory().getr102(chwA,
				typeModel, sapPlant, newFlag, tmUPGObj, fromToType,
				pimsIdentity, flfilcd, salesOrg, taxCntryList);
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
	public void r108(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		R108createTypeMODCharacteristic r = getFactory().getr108(typeModel,
				chwA, pimsIdentity);
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
			String productHierarchy, Vector VectTaxList, String plantValue)
			throws Exception {
		R117createTypeModelMaterialSalesView r = getFactory().getr117(chwA,
				typemod, div, acctAsgnGrp, ps, bumpCtr, pimsIdentity, flfil,
				salesOrg1, productHierarchy, VectTaxList, plantValue);
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
			String pimsIdentity) throws Exception {
		R133updateMaterialBasicViewForTypeModel r = getFactory().getr133(
				typeModel, chwA, pimsIdentity);
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
	public void r156(String typemod, String div, CHWAnnouncement chwA,
			String pimsIdentity, boolean seoFlag) throws Exception {
		R156createZDMClassificationForTypeModel r = getFactory().getr156(
				typemod, div, chwA, pimsIdentity, seoFlag);
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
			String pimsIdentity, String profitCenter) throws Exception {
		R183createCFIPlantViewForTypeModelMaterial r = getFactory().getr183(
				annDocNo, typemod, sapPlant, pimsIdentity, profitCenter);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public void r189(CHWAnnouncement chwA, TypeModel typeModel,
			String sapPlant, String newFlag, TypeModelUPGGeo tmUPGObj,
			String FromToType, String pimsIdentity) throws Exception {
		R189createCFIPlantViewForType r = getFactory().getr189(chwA, typeModel,
				sapPlant, newFlag, tmUPGObj, FromToType, pimsIdentity);
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
	public void r207(String type, String model, String plant) throws Exception {
		R207ReadPlantViewMaterial r = getFactory().getr207(type, model, plant);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
	}

	@Override
	public BapireturnStructure r209(String material) throws Exception {
		R209ReadBasicViewOfMaterial r = getFactory().getr209(material);
		logPromoteInfoMessage(r);
		r.evaluate();
		logPromoteResultMessage(r);
		return r.evaluate();
	}

	@Override
	public void r261(String material) throws Exception {
		R261PlantViewMaterial r = getFactory().getr261(material);
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
