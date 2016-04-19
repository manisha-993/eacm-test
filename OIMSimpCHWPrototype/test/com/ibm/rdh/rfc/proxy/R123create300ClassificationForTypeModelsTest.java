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
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R123create300ClassificationForTypeModelsTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {

		String sql_klah = "insert into SAPR3.KLAH (MANDT,KLART,CLASS) values('200','300','MK_1234_MODELS')";
		String sql_ksml = "insert into SAPR3.KSML(MANDT,CLINT,IMERK) values('200','0000000000','0000000000') ";
		String sql_cabn = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM) values('200','0000000000','MK_1234_MOD')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','1234NEW')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','1234UPG')";
		String sql_mara_3 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','1234MTC')";
		String sql_rdx = "insert into sapr3.zdm_rdxcustmodel(mandt,zdm_class,zdm_syst_default) values ('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_ksml, conn);
		int t3 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t4 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t5 = SqlHelper.runUpdateSql(sql_mara_2, conn);
		int t6 = SqlHelper.runUpdateSql(sql_mara_3, conn);
		int t7 = SqlHelper.runUpdateSql(sql_rdx, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}

	}

	@Test
	public void r123New() {
		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			chwA.setAnnDocNo("123401");
			String type = "1234";
			String newFlag = "NEW";
			String FromToType = "";
			String pimsIdentity = "C";
			String objectId = "300" + type + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(type + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r123(type, tmUPGObj, newFlag, chwA, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
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
			map.put("TEXT", "'Classification created / updated successfully.'");
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
	public void r123Upg() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			chwA.setAnnDocNo("123401");
			String type = "1234";
			String newFlag = "UPG";
			String FromToType = "";
			String pimsIdentity = "C";
			String objectId = "300" + type + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(type + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r123(type, tmUPGObj, newFlag, chwA, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
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
			map.put("TEXT", "'Classification created / updated successfully.'");
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
	public void r123MtcToType() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("1234");
			chwA.setAnnDocNo("123401");
			String type = "1234";
			String newFlag = "MTC";
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String objectId = "300" + tmUPGObj.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r123(type, tmUPGObj, newFlag, chwA, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
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
			map.put("TEXT", "'Classification created / updated successfully.'");
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
	public void r123MtcFromType() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setFromType("1234");
			chwA.setAnnDocNo("123401");
			String type = "1234";
			String newFlag = "MTC";
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			String objectId = "300" + tmUPGObj.getFromType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(type + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r123(type, tmUPGObj, newFlag, chwA, FromToType,
					pimsIdentity);
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
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
			map.put("TEXT", "'Classification created / updated successfully.'");
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
		String del_klah = "delete from SAPR3.KLAH where MANDT='200' and KLART='300' and CLASS='MK_1234_MODELS'";
		String del_ksml = "delete from SAPR3.KSML where MANDT='200' and CLINT='0000000000' and IMERK='0000000000' ";
		String del_cabn = "delete from SAPR3.CABN where MANDT='200' and ATINN='0000000000' AND ATNAM ='MK_1234_MOD'";
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='1234NEW'";
		String del_mara_2 = "delete from SAPR3.MARA where MANDT='200' and MATNR='1234UPG'";
		String del_mara_3 = "delete from SAPR3.MARA where MANDT='200' and MATNR='1234MTC'";
		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";

		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_ksml, conn);
		int t3 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t4 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t5 = SqlHelper.runUpdateSql(del_mara_2, conn);
		int t6 = SqlHelper.runUpdateSql(del_mara_3, conn);
		int t7 = SqlHelper.runUpdateSql(del_rdx, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
