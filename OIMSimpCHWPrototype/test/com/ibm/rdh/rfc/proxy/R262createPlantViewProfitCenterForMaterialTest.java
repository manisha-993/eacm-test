package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;

public class R262createPlantViewProfitCenterForMaterialTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Test
	public void r262() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			String pimsIdentity = "C";
			String material = "EACMnewCreate";
			String sapPlant = "Y";
			String profitCenter = "EA";

			int deleteDataResult = deleteDataMatmCreate(material);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, material);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r262(chwA, material, sapPlant, pimsIdentity, profitCenter);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = material;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + objectId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT",
					"'Material Master created/updated successfully: EACMnewCreate'");
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
