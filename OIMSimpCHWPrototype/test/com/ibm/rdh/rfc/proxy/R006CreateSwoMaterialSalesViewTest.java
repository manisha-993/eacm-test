package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.chw.entity.CntryTax;

public class R006CreateSwoMaterialSalesViewTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_MATM_CREATE";

	@Test
	public void r006() {
		try {

			AUOMaterial auoMaterial = new AUOMaterial("EACMCHW", "57");
			auoMaterial.setAcctAsgnGrp("EC");
			auoMaterial.setMaterialGroup1("I");
			auoMaterial.setCHWProdHierarchy("0900B00002");
			auoMaterial.setDiv("B1");
			auoMaterial.setEffectiveDate(new Date());
			Vector country = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			// cntryTax.setClassification("H");
			cntryTax.setTaxCategory("1");
			country.add(cntryTax);
			auoMaterial.setCountryList(country);

			String salesOrg = "so1";
			String currentSapSalesStatus = "Z0";
			Date currentEffectiveDate = new Date();
			// String productHierarchy = "ph";
			String pimsIdentity = "C";
			String objectId = auoMaterial.getMaterial();

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", objectId);
			int deleteDataResult = deleteDataMatmCreate(objectId);
			assertEquals(deleteDataResult, 0);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r006(auoMaterial, salesOrg, currentSapSalesStatus,
					currentEffectiveDate, pimsIdentity);

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
