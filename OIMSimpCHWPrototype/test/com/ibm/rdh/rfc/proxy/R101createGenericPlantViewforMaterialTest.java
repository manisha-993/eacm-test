package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R101createGenericPlantViewforMaterialTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	//@Before
	public void prepareData() {
		String sql_rdx = "insert into sapr3.zdm_rdxcustmodel(mandt,zdm_class,zdm_syst_default) values ('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_rdx, conn);

		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR101queryFound01() {
		try {
			logger.info("newFlag:NEW");
			String type = "EACM";
			String newFlag = "NEW";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123402");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("EACMT1");

			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "C";
			String plantValue = "1222";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag, loadingGrp,
					tmUPGObj, FromToType, pimsIdentity, plantValue);

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
	public void testR101queryFound02() {
		try {
			logger.info("newFlag:UPG");
			String type = "EACM";
			String newFlag = "UPG";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123402");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("EACMT1");

			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "C";
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag, loadingGrp,
					tmUPGObj, FromToType, pimsIdentity, plantValue);

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
	public void testR101queryFound03() {
		try {
			logger.info("newFlag:MTC,FromToType:MTCTOTYPE");
			String type = "EACMT1";
			String newFlag = "MTC";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123402");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType(type);

			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "C";
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag, loadingGrp,
					tmUPGObj, FromToType, pimsIdentity, plantValue);

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
	public void testR101queryFound04() {
		try {
			logger.info("newFlag:MTC,FromToType:MTCFROMTYPE");
			String type = "EACMF1";
			String newFlag = "MTC";
			deleteDataMatmCreate(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", type + newFlag);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123402");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType(type);

			String FromToType = "MTCFROMTYPE";
			String loadingGrp = "";
			String pimsIdentity = "C";
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag, loadingGrp,
					tmUPGObj, FromToType, pimsIdentity, plantValue);

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

	//@After
	public void deleteData() {

		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";

		int t1 = SqlHelper.runUpdateSql(del_rdx, conn);

		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

	
}
