package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.AUOMaterial;

public class R062CreateRevPartMasterTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Test
	public void r062() {
		try {
			AUOMaterial auoMaterial = new AUOMaterial("EACMCHW", "57");
			auoMaterial.setDiv("B1");
			auoMaterial.setEffectiveDate(new Date());
			auoMaterial.setCHWProdHierarchy("0900B00002");
			auoMaterial.setShortName("EC");
			String pimsIdentity = "C";
			String objectId = auoMaterial.getMaterial();
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", objectId);
			int deleteDataResult = deleteDataMatmCreate(objectId);
			assertEquals(deleteDataResult, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r062(auoMaterial, pimsIdentity);

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
}
