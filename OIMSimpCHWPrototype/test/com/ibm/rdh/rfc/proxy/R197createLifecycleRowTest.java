package com.ibm.rdh.rfc.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R197createLifecycleRowTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void testR197() {
		try {
			String material = "EACMNEW";
			deleteZdmchwplcRow(Constants.MANDT, material);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CHW_PRODUCT_CYCLE", "ZDMCHWPLC");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String varCond = "FEATURE:1001";
			String salesStatus = "Z0";
			Date validFrom = new Date();
			Date validTo = sdf.parse("9999-12-31");
			String user = "0000000001";
			String annDocNo = "123401";
			String check = "wdfm";
			String pimsIdentity = "C";
			String salesOrg = "0147";

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r197(material, varCond, salesStatus, validFrom,
					validTo, user, annDocNo, check, pimsIdentity, salesOrg);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CHW_PRODUCT_CYCLE'");
			map.put("OBJECT_ID", "'ZDMCHWPLC'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");
			assertNotNull("Successful ZDMCHWPLC action.  <1> + rows inserted",
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
