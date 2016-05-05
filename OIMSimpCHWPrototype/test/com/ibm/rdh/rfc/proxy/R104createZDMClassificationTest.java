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

public class R104createZDMClassificationTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMT1MTC')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMF1MTC')";

		int t1 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_mara_2, conn);

		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r104New() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "NEW";
			String objectId = "ZDM" + typeModel.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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
	public void r104Upg() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "UPG";
			String objectId = "ZDM" + typeModel.getType() + newFlag;
			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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
	public void r104MtcToType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACMT1");
			String pimsIdentity = "C";

			String FromToType = "MTCTOTYPE";
			String newFlag = "MTC";
			String objectId = "ZDM" + tmUPGObj.getType() + newFlag;
			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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
	public void r104MtcFromType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";
			tmUPGObj.setFromType("EACMF1");

			String FromToType = "MTCFROMTYPE";
			String newFlag = "MTC";
			String objectId = "ZDM" + tmUPGObj.getFromType() + newFlag;
			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getFromType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMT1MTC'";
		String del_mara_2 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMF1MTC'";
		int t1 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_mara_2, conn);

		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
