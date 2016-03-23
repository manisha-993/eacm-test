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

public class R133updateMaterialBasicViewForTypeModelTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Test
	public void r133() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();

			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			typeModel.setType("EACM");
			typeModel.setModel("newCreate");
			typeModel.setDiv("B1");
			typeModel.setEanUPCCode("");
			typeModel.setProductHierarchy("ph");
			typeModel.setDescription("new create model");
			String pimsIdentity = "C";
			String objectId = typeModel.getType() + typeModel.getModel();

			int deleteDataResult = deleteDataMatmCreate(objectId);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r133(typeModel, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

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
