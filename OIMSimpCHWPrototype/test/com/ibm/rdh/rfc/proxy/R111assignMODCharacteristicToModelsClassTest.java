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

public class R111assignMODCharacteristicToModelsClassTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_cabn = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM) values('200','0000000001','MK_EACF_MC')";
		String sql_klah = "insert into SAPR3.KLAH (MANDT,CLINT,KLART,CLASS) values('200','0000000000','300','MK_EACF_MC')";
		String sql_rdx = "insert into SAPR3.ZDM_RDXCUSTMODEL (MANDT,ZDM_CLASS,ZDM_SYST_DEFAULT) values('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t3 = SqlHelper.runUpdateSql(sql_rdx, conn);
		if (t1 >= 0 && t2>=0&&t3>=0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}

	}

	@Test
	public void testR111() {
		try {
			String type = "EACF";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String j_class = "MK_" + type + "_MC";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_ASSIGN_CHAR_TO_CLASS", "300/" + j_class);
//			deleteKSMLRow("300", j_class, Constants.MANDT);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r111(type, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'300" + j_class + "'");
			map.put("ZDMOBJTYP", "'CLS'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_ASSIGN_CHAR_TO_CLASS'");
			map.put("OBJECT_ID", "'300/" + j_class + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  MK_" + type
					+ "_MC, successfully assigned to classification  300/"
					+ j_class + "'");
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
		String del_cabn = "delete from SAPR3.CABN where MANDT='200' and ATINN='0000000001' AND ATNAM ='MK_EACF_MC'";
		String del_klah = "delete from SAPR3.KLAH where MANDT='200' and KLART='300' and CLASS='MK_EACF_MC'";
		String del_rdx = "delete from SAPR3.ZDM_RDXCUSTMODEL where mandt='200' and ZDM_CLASS='MD_CHW_NA' and ZDM_SYST_DEFAULT='X'";

		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(del_klah, conn);
		int t3 = SqlHelper.runUpdateSql(del_rdx, conn);
		if (t1 >= 0 &&t2>=0&&t3>=0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}

	}
}
