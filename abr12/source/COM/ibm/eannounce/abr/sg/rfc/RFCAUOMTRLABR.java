package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: RFCAUOMTRLABR.java,v $
//Revision 1.9  2019/04/03 12:23:45  xujianbo
//Rollback All the Unique ID Code
//
//Revision 1.5  2016/12/01 13:40:56  wangyul
//[Work Item 1627842] New: pre-existing parking records not being set to H status by EACM RFCABR
//
//Revision 1.4  2016/11/09 13:34:38  wangyul
//[Work Item 1617881] New: Configurable option to move parking table records to R status or leave them in H status
//
//Revision 1.3  2016/08/10 09:00:15  wangyul
//Move r144 to update parking table at last
//
//Revision 1.2  2016/08/10 08:18:01  wangyul
//Story1556953 - OIM CHW Simplifications : AUO Material and REV profile process
//
//Revision 1.1  2016/08/04 07:58:22  wangyul
//Story1556953 - OIM CHW Simplifications : AUO Material and REV profile process
//
public class RFCAUOMTRLABR extends RfcAbrAdapter {

	public RFCAUOMTRLABR(RFCABRSTATUS rfcAbrStatus)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, RemoteException, EANBusinessRuleException,
			IOException, MiddlewareShutdownInProgressException {
		super(rfcAbrStatus);
	}

	@Override
	public void processThis() throws Exception {
		abr.addDebug("Start processThis()");
		// ----------------------- Get values from EACM entities -----------------------
		// AUOMTRL
		EntityItem auoItem = getRooEntityItem();
		// GENERALAREA
		Vector gaVct = PokUtils.getAllLinkedEntities(auoItem, "AUOMTRLGENAA", GENERALAREA);
		
		List<SalesOrgPlants> salesOrgPlantList = getAllSalesOrgPlant(gaVct);
		Set<String> plants = getAllPlant(salesOrgPlantList);
		Set<String> returnPlants = getAllReturnPlant(salesOrgPlantList);
		
		String material = getAttributeValue(auoItem, "MATERIAL");
		String annDocNo = material;
		String pimsIdentity = "C";
		
		AUOMaterial auo = new AUOMaterial();
		auo.setMaterial(material);
		auo.setAmrtztlnstrt(getAttributeValue(auoItem, "AMRTZTNSTRT"));
		auo.setAmrtztlnlngth(getAttributeValue(auoItem, "AMRTZTNLNGTH")); // flag AL01	long 01
		auo.setDiv(getAttributeFlagValue(auoItem, "DIV"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		auo.setEffectiveDate(sdf.parse(getAttributeValue(auoItem, "EFFECTIVEDATE")));
		auo.setAcctAsgnGrp(getAttributeFlagValue(auoItem, "ACCTASGNGRP"));
		auo.setMaterialGroup1(AUOMaterial.SAP_MATERIAL_GRP);
		auo.setCHWProdHierarchy(getAttributeValue(auoItem, "PRODHIERCD"));
		auo.setCountryList(getCountryList(salesOrgPlantList));
		auo.setShortName(getAttributeValue(auoItem, "AUODESC"));
		abr.addDebug(auo.toString());
		
		rdhRestProxy.r062(auo, pimsIdentity);
		for (String plant : plants) {
			rdhRestProxy.r005(auo, plant, pimsIdentity);
			abr.addDebug("Call r005 successfully for plant " + plant);
		}
		for (SalesOrgPlants salesOrgPlant : salesOrgPlantList) {
			String salesOrg = salesOrgPlant.getSalesorg();
			rdhRestProxy.r006(auo, salesOrg, "Z0", new Date(), pimsIdentity);
			abr.addDebug("Call r006 successfully for salesOrg " + salesOrg);
		}
		for (String returnPlant : returnPlants) {
			rdhRestProxy.r057(auo, returnPlant, pimsIdentity);
			abr.addDebug("Call r057 successfully for returnPlant " + returnPlant);
		}
		rdhRestProxy.r009(auo, pimsIdentity);
		rdhRestProxy.r039(auo, pimsIdentity);
		
		if (needReleaseParkingTable()) {
			rdhRestProxy.r144(annDocNo, "R", pimsIdentity);
		} else {
			rdhRestProxy.r144(annDocNo, "H", pimsIdentity);
		}
		
		// release memory
		if (entityList != null) {
			entityList.dereference();
			entityList = null;
		}	
		abr.addDebug("End processThis()");		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector getCountryList(List<SalesOrgPlants> salesOrgPlantList) throws RfcAbrException {
		Vector coutryVct = new Vector();
		for (SalesOrgPlants salesOrgPlant : salesOrgPlantList) {
			CntryTax cnTryTax = new CntryTax();
			cnTryTax.setCountry(salesOrgPlant.getGenAreaCode());
			coutryVct.add(cnTryTax);
		}
		return coutryVct;
	}
	
	protected Set<String> getAllReturnPlant(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> plants = new HashSet<>();			
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			Vector<String> tmpPlants = salesorgPlants.getReturnPlants();			
			for (String plant : tmpPlants) {
				plants.add(plant);
			}
			if (tmpPlants.size() == 0) {
				abr.addDebug("getAllReturnPlant No return plant found for country code: " + salesorgPlants.getGenAreaCode());
			}				
		}			
		abr.addDebug("getAllReturnPlant return plants size: " + plants.size() + " values: " + plants);
		return plants;		
	}
	
	public String getVeName() {
		return "RFCAUOMTRLABR";
	}
	
}
