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
import com.ibm.rdh.chw.entity.AUOMaterial;

public class R009Maintain001ClassificationTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMCHW')";
		int t1 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r009() {
		try {
			AUOMaterial auoMaterial = new AUOMaterial("EACMCHW", "57");
			auoMaterial.setAmrtztlnstrt("S");
			auoMaterial.setAmrtztlnlngth("5");
			String objectId = "001" + auoMaterial.getMaterial();
			deleteDataClassicationMaint(auoMaterial.getMaterial());
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, "001"
					+ auoMaterial.getMaterial());

			String pimsIdentity = "C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r009(auoMaterial, pimsIdentity);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + auoMaterial.getMaterial() + "'");
			map.put("ZDMOBJTYP", "'CLF'");
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
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMCHW'";
		int t1 = SqlHelper.runUpdateSql(del_mara_1, conn);
		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
