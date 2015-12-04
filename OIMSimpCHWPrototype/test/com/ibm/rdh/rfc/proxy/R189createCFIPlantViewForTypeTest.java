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
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");

			assertNotNull("Material Master created/updated successfully",
					logdtlText);

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
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");

			assertNotNull("Material Master created/updated successfully",
					logdtlText);

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
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");

			assertNotNull("Material Master created/updated successfully",
					logdtlText);

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
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_MATM_CREATE", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");

			assertNotNull("Material Master created/updated successfully",
					logdtlText);

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
