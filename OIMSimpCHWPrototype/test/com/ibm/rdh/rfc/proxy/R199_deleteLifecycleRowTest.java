package com.ibm.rdh.rfc.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R199_deleteLifecycleRowTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_CHW_PRODUCT_CYCLE";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Before
	public void prepareData() {
		int t1 = deleteZdmchwplcRow(Constants.MANDT, "NEACMNEW");
		String sql_insertplc = "insert into SAPR3.ZDMCHWPLC (MANDT,MATNR,VKORG,VARCOND,DATBI,DATAB,VMSTA,ZDM_RFANUM,ZDM_CREATE_DATE,ZDM_CREATE_TIME,ZDM_CREATE_USER,ZDM_CREATE_IUSER,ZDM_UPDATE_DATE,ZDM_UPDATE_TIME,ZDM_UPDATE_USER,ZDM_UPDATE_IUSER) values('200','NEACMNEW','0147','FEATURE:1002','99991231','','','','','','','','','','','')";
		int t2 = SqlHelper.runUpdateSql(sql_insertplc, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r199() {
		try {
			String material = "NEACMNEW";
			String varCond = "FEATURE:1002";
			String salesStatus = "Z0";
			Date validTo = sdf.parse("99991231");
			String user = "0000000001";
			String annDocNo = "123401";
			String check = "wdfm";
			String pimsIdentity = "C";
			String salesOrg = "0147";

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CHW_PRODUCT_CYCLE", "ZDMCHWPLC");

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r199(material, varCond, salesStatus, validTo, user,
					annDocNo, check, pimsIdentity, salesOrg);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
			String objectId = "ZDMCHWPLC";

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CHW_PRODUCT_CYCLE'");
			map.put("OBJECT_ID", "'ZDMCHWPLC'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT",
					"'Successful ZDMCHWPLC action.  <1> + rows deleted'");
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
