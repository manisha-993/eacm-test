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

public class R104createZDMClassificationTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String XEIxx_R001 = "K";
	String Insert_ProductID = "1234W11";
	String Notmara_ProductID = "1234W12";
	String Notmakt_ProductID = "1234W13";
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

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

			String delete_kssk_sql = "delete from SAPR3.KSSK where mandt = '"
					+ Constants.MANDT + "' and KLART = 'ZDM' and OBJEK ='"
					+ typeModel.getType() + "NEW'";

			SqlHelper.runUpdateSql(delete_kssk_sql, conn);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
		    String objectId = "ZDM"+typeModel.getType()+newFlag;
			

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASSIFICATION_MAINT", activeId);
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
			String delete_kssk_sql = "delete from SAPR3.KSSK where mandt = '"
					+ Constants.MANDT + "' and KLART = 'ZDM' and OBJEK ='"
					+ typeModel.getType() + "UPG'";

			SqlHelper.runUpdateSql(delete_kssk_sql, conn);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = "ZDM"+typeModel.getType()+newFlag;
			

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASSIFICATION_MAINT", activeId);
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
			String delete_kssk_sql = "delete from SAPR3.KSSK where mandt = '"
					+ Constants.MANDT + "' and KLART = 'ZDM' and OBJEK ='"
					+ tmUPGObj.getType() + "MTC'";

			SqlHelper.runUpdateSql(delete_kssk_sql, conn);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = "ZDM"+tmUPGObj.getType()+newFlag;
			

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASSIFICATION_MAINT", activeId);
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

			String delete_kssk_sql = "delete from SAPR3.KSSK where mandt = '"
					+ Constants.MANDT + "' and KLART = 'ZDM' and OBJEK ='"
					+ tmUPGObj.getFromType() + "MTC'";

			SqlHelper.runUpdateSql(delete_kssk_sql, conn);
			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = "ZDM"+tmUPGObj.getFromType()+newFlag;
			

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASSIFICATION_MAINT", activeId);
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

}
