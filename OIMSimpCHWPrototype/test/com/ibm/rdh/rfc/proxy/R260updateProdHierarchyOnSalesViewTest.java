package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;

public class R260updateProdHierarchyOnSalesViewTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void testR260() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			Object material = new TypeModel();
			((TypeModel) material).setType("EACM");
			((TypeModel) material).setModel("Mod");
			String pimsIdentity = "C";
			String salesOrg = "so1";
			String productHierarchy = "ph";
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			
			deleteDataMatmCreate(((TypeModel) material).getType()
					+ ((TypeModel) material).getModel());
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", ((TypeModel) material).getType()
							+ ((TypeModel) material).getModel());

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r260(chwA, material, pimsIdentity, salesOrg,
					productHierarchy, chwAg);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + ((TypeModel) material).getType()
					+ ((TypeModel) material).getModel() + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + ((TypeModel) material).getType()
					+ ((TypeModel) material).getModel() + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT",
					"'Material Master created/updated successfully: "
							+ ((TypeModel) material).getType()
							+ ((TypeModel) material).getModel() + "'");
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
