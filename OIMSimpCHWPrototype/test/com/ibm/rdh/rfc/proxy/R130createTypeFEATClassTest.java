package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;

public class R130createTypeFEATClassTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASS_MAINTAIN";

	@Test
	public void r130() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			String pimsIdentity = "C";
			String type = "1234";
			String featRanges = "F";
			chwA.setAnnDocNo("123401");

			String class_id = "MK_" + type + "_FEAT_" + featRanges;
			deleteKLAHRow("300", class_id, Constants.MANDT);
			deleteSWORRow("300", class_id, Constants.MANDT);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, "300"
					+ class_id);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r130(type, featRanges, chwA, pimsIdentity);
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = "300" + "MK_" + type + "_FEAT_" + featRanges;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASS_MAINTAIN", activeId);
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
