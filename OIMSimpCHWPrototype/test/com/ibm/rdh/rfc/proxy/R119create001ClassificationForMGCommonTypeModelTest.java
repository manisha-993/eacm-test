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

public class R119create001ClassificationForMGCommonTypeModelTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH (mandt, klart, class) values ('200', '001', 'MG_COMMON')";
		String sql_cabn = "insert into SAPR3.CABN (mandt, ATNAM) values('200','MG_PRODUCTTYPE')";
		String sql_ksml = "insert into sapr3.KSML (mandt, clint, imerk) values ('200', '0000000000', '0000000000')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMNew')";
		String sql_rdx = "insert into SAPR3.ZDM_RDXCUSTMODEL (MANDT,ZDM_CLASS,ZDM_SYST_DEFAULT) values('200','MD_CHW_NA','X')";
		
		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t3 = SqlHelper.runUpdateSql(sql_ksml, conn);
		int t4 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t5 = SqlHelper.runUpdateSql(sql_rdx, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR119QueryFound01() {
		try {
			logger.info("typemod:EACMNew,mgCommon:true");

			String typemod = "EACMNew";
			deleteDataClassicationMaint(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			boolean mgCommon = true;
			boolean bumpctr = true;
			String pimsIdentity = "C";

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r119(typemod, chwA, mgCommon, bumpctr, pimsIdentity);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + typemod + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + typemod + "'");
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

	@Test
	public void testR119QueryFound02() {
		try {
			logger.info("typemod:EACMNew,mgCommon:false");

			String typemod = "EACMNew";
			deleteDataClassicationMaint(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			boolean mgCommon = false;
			boolean bumpctr = true;
			String pimsIdentity = "C";

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r119(typemod, chwA, mgCommon, bumpctr, pimsIdentity);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + typemod + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + typemod + "'");
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
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='001' and CLASS='MG_COMMON'";
		String del_cabn = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MG_PRODUCTTYPE'";
		String del_ksml = "delete from sapr3.ksml where clint='0000000000' and mandt ='200' and imerk='0000000000'";
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMNew'";
		String del_rdx = "delete from SAPR3.ZDM_RDXCUSTMODEL where mandt='200' and ZDM_CLASS='MD_CHW_NA' and ZDM_SYST_DEFAULT='X'";

		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t3 = SqlHelper.runUpdateSql(del_ksml, conn);
		int t4 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t5 = SqlHelper.runUpdateSql(del_rdx, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
