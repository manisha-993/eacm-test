package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R189createCFIPlantViewForTypeTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String XEIxx_R001 = "K";
	String Insert_ProductID = "1234W11";
	String Notmara_ProductID = "1234W12";
	String Notmakt_ProductID = "1234W13";
	String activeId = "Z_DM_SAP_MATM_CREATE";

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

			String type = typeModel.getType() + newFlag;

			int deleteRow = prepareData(type);
			assertEquals(deleteRow, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = typeModel.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("STATUS", "'success'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
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
			String type = typeModel.getType() + newFlag;

			int deleteRow = prepareData(type);
			assertEquals(deleteRow, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = typeModel.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("STATUS", "'success'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
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
			String type = tmUPGObj.getType() + newFlag;

			int deleteRow = prepareData(type);
			assertEquals(deleteRow, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = tmUPGObj.getType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("STATUS", "'success'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
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
			String type = tmUPGObj.getFromType() + newFlag;

			int deleteRow = prepareData(type);
			assertEquals(deleteRow, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = tmUPGObj.getFromType() + newFlag;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("STATUS", "'success'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
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

	public int prepareData(String type) {

		String delete_mara_sql = "delete from SAPR3.MARA where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		String delete_makt_sql = "delete from SAPR3.MAKT where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		String delete_marm_sql = "delete from SAPR3.MARM where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		String delete_marc_sql = "delete from SAPR3.MARC where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		String delete_mvke_sql = "delete from SAPR3.MVKE where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		String delete_mlan_sql = "delete from SAPR3.MLAN where mandt = '"
				+ Constants.MANDT + "'and MATNR = '" + type + "'";

		int t1 = SqlHelper.runUpdateSql(delete_mara_sql, conn);
		int t2 = SqlHelper.runUpdateSql(delete_makt_sql, conn);
		int t3 = SqlHelper.runUpdateSql(delete_marm_sql, conn);
		int t4 = SqlHelper.runUpdateSql(delete_marc_sql, conn);
		int t5 = SqlHelper.runUpdateSql(delete_mvke_sql, conn);
		int t6 = SqlHelper.runUpdateSql(delete_mlan_sql, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0) {

			return 0;

		} else {
			return -1;
		}

	}
}
