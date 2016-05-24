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
import com.ibm.rdh.chw.entity.CHWAnnouncement;

public class R202_createConfigurationProfileForMTCMaterialTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_cukb_1 = "insert into sapr3.CUKB (KNNAM,KNNUM,MANDT,ADZHL) values ('MK_HW_PRICING','1101000001','200','1010')";
		String sql_cukb_2 = "insert into sapr3.CUKB (KNNAM,KNNUM,MANDT,ADZHL) values ('MK_HW_SUBLINE','1101000002','200','1010')";
		String del_cuco = "delete from sapr3.cuco where mandt='200' and obtab='MARA' and objek='EACMMTC'";

		int t1 = SqlHelper.runUpdateSql(sql_cukb_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_cukb_2, conn);
		int t3 = SqlHelper.runUpdateSql(del_cuco, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR202() {
		try {
			String typeStr = "EACM";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CONP_MAINTAIN", typeStr + "MTC");

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r202(typeStr, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CONP_MAINTAIN'");
			map.put("OBJECT_ID", "'" + typeStr + "MTC'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			map.put("ZDMOBJTYP", "'CNP'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT",
					"'Configuration profile created successfully for :  "
							+ typeStr + "MTC'");
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
		String del_cukb_1 = "delete from sapr3.cukb where mandt='200' and KNNUM='1101000001' and knnam='MK_HW_PRICING' and ADZHL='1010'";
		String del_cukb_2 = "delete from sapr3.cukb where mandt='200' and KNNUM='1101000002' and knnam='MK_HW_SUBLINE' and ADZHL='1010'";

		int t1 = SqlHelper.runUpdateSql(del_cukb_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_cukb_2, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
