package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R189createCFIPlantViewForTypeTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Before
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
	public void r189New() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");

			String pimsIdentity = "C";
			String sapPlant = "Y";
			String FromToType = "";
			String newFlag = "NEW";

			int deleteDataResult = deleteDataMatmCreate(typeModel.getType()
					+ newFlag);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId,
					typeModel.getType() + newFlag);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = typeModel.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ objectId + "'");
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
	public void r189Upg() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");

			String pimsIdentity = "C";
			String sapPlant = "Y";
			String FromToType = "";
			String newFlag = "UPG";

			int deleteDataResult = deleteDataMatmCreate(typeModel.getType()
					+ newFlag);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId,
					typeModel.getType() + newFlag);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = typeModel.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ objectId + "'");
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
	public void r189MtcToType() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			tmUPGObj.setType("EACMT1");

			String pimsIdentity = "C";
			String sapPlant = "Y";
			String FromToType = "MTCTOTYPE";
			String newFlag = "MTC";

			int deleteDataResult = deleteDataMatmCreate(tmUPGObj.getType()
					+ newFlag);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId,
					tmUPGObj.getType() + newFlag);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = tmUPGObj.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ objectId + "'");
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
	public void r189MtcFromType() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			tmUPGObj.setFromType("EACMF1");

			String pimsIdentity = "C";
			String sapPlant = "Y";
			String FromToType = "MTCFROMTYPE";
			String newFlag = "MTC";

			int deleteDataResult = deleteDataMatmCreate(tmUPGObj.getFromType()
					+ newFlag);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId,
					tmUPGObj.getFromType() + newFlag);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = tmUPGObj.getFromType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ objectId + "'");
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

	@After
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
