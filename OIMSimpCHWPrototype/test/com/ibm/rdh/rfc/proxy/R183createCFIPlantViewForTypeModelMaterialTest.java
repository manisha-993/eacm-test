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

public class R183createCFIPlantViewForTypeModelMaterialTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Test
	public void r183() {
		try {

			String annDocNo = "123401";
			String typemod = "EACMnewCreate";
			String sapPlant = "Y";
			String pimsIdentity = "C";
			String profitCenter = "";

			int deleteDataResult = deleteDataMatmCreate(typemod);
			assertEquals(deleteDataResult, 0);

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, typemod);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r183(annDocNo, typemod, sapPlant, pimsIdentity,
					profitCenter);
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = typemod;

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
