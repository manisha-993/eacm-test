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
import com.ibm.rdh.chw.entity.TypeFeature;

public class R128assignRPQTypeFeatureCharacteristicToTypeClassTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_cabn = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM,ADZHL) values('200','0000000003','MK_EACF_1000','0000')";
		String sql_klah_1 = "insert into SAPR3.KLAH (MANDT,CLINT,KLART,CLASS) values('200','0000000003','300','MK_EACF_RPQ')";
		String sql_klah_2 = "insert into SAPR3.KLAH (MANDT,CLINT,KLART,CLASS) values('200','0000000004','300','MK_EACF_RPQ_EE')";

		int t1 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_klah_2, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}

	}

	@Test
	public void testR12801() {
		try {
			logger.info("featRanges: RPQ");
			TypeFeature typeFeature = new TypeFeature();
			typeFeature.setType("EACF");
			typeFeature.setFeature("1000");
			String featRanges = "RPQ";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String j_class = "MK_" + typeFeature.getType() + "_RPQ";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_ASSIGN_CHAR_TO_CLASS", "300/" + j_class);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r128(typeFeature, featRanges, chwA, pimsIdentity);

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
			map.put("TEXT", "'Characteristic  MK_" + typeFeature.getType()
					+ "_" + typeFeature.getFeature()
					+ ", successfully assigned to classification  300/"
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

	@Test
	public void testR12802() {
		try {
			logger.info("featRanges: EE");
			TypeFeature typeFeature = new TypeFeature();
			typeFeature.setType("EACF");
			typeFeature.setFeature("1000");
			String featRanges = "EE";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String j_class = "MK_" + typeFeature.getType() + "_RPQ" + "_"
					+ featRanges;
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_ASSIGN_CHAR_TO_CLASS", "300/" + j_class);
			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r128(typeFeature, featRanges, chwA, pimsIdentity);

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
			map.put("TEXT", "'Characteristic  MK_" + typeFeature.getType()
					+ "_" + typeFeature.getFeature()
					+ ", successfully assigned to classification  300/"
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
		String del_cabn = "delete from SAPR3.CABN where MANDT='200' and ATINN='0000000003' AND ATNAM ='MK_EACF_1000' and ADZHL='0000'";
		String del_klah_1 = "delete from SAPR3.KLAH where MANDT='200' and KLART='300' and CLASS='MK_EACF_RPQ' and CLINT='0000000003'";
		String del_klah_2 = "delete from SAPR3.KLAH where MANDT='200' and KLART='300' and CLASS='MK_EACF_RPQ_EE' and CLINT='0000000004'";

		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(del_klah_1, conn);
		int t3 = SqlHelper.runUpdateSql(del_klah_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
