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
import com.ibm.rdh.chw.caller.R166createSTPPlantViewForMaterial;
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

	public R106createTypeModelsClass getr106(TypeModel typeModel, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {

		return new R106createTypeModelsClass(typeModel, chwA, pimsIdentity);
	}

	public R166createSTPPlantViewForMaterial getr166(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String storageLocation,
			String newFlag) throws Exception {
		return new R166createSTPPlantViewForMaterial(chwA, typeModel, chwAg,
				storageLocation, newFlag);
	}

	public R189createCFIPlantViewForType getr189(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		return new R189createCFIPlantViewForType(chwA, typeModel, sapPlant,
				newFlag, tmUPGObj, FromToType, pimsIdentity);

	}

}
