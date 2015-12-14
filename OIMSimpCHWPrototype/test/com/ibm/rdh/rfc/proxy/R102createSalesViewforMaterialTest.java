package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R102createSalesViewforMaterialTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void testR102QueryFound01() {
		try {
			logger.info("newFlag:NEW,FromToType:MTCTOTYPE,SegmentAcronym:RSS");
			String type = "EACM";
			String newFlag = "NEW";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RSS");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "fl1";
			String salesOrg = "0147";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			 map.clear();
			 map.put("ZSESSION", "'" + sessionId + "'");
			 map.put("TEXT", "'Created records in parking table: 2'");
			 rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			 assertNotNull(rowDetails);

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);

		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}

	@Test
	public void testR102QueryFound02() {
		try {
			logger.info("newFlag:UPG,FromToType:MTCTOTYPE,SegmentAcronym:AS4");
			String type = "EACM";
			String newFlag = "UPG";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("AS4");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "fl1";
			String salesOrg = "so1";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			 map.clear();
			 map.put("ZSESSION", "'" + sessionId + "'");
			 map.put("TEXT", "'Created records in parking table: 2'");
			 rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			 assertNotNull(rowDetails);

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}

	@Test
	public void testR102QueryFound03() {
		try {
			logger.info("newFlag:UPG,FromToType:MTCTOTYPE,SegmentAcronym:RS6");
			String type = "EACMT1";
			String newFlag = "MTC";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RS6");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType(type);
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "fl1";
			String salesOrg = "so1";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			 map.clear();
			 map.put("ZSESSION", "'" + sessionId + "'");
			 map.put("TEXT", "'Created records in parking table: 2'");
			 rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			 assertNotNull(rowDetails);

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}

	@Test
	public void testR102QueryFound04() {
		try {
			logger.info("newFlag:UPG,FromToType:MTCFROMTYPE,SegmentAcronym:LSC");
			String type = "EACMF1";
			String newFlag = "MTC";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("LSC");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType(type);
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			String flfilcd = "EMPTY";
			String salesOrg = "so1";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Created records in parking table: 2'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ type + newFlag + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
		} catch (HWPIMSAbnormalException ex) {
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {

		}
	}
}
