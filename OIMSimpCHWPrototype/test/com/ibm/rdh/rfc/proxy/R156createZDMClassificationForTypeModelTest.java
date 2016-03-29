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

public class R156createZDMClassificationForTypeModelTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_klah_1 = "insert into SAPR3.KLAH (mandt, clint, klart, class) values ('200', '1000000001', 'ZDM', 'MD_XHW_NA')";
		String sql_klah_2 = "insert into SAPR3.KLAH (mandt, clint, klart, class) values ('200', '1000000002', 'ZDM', 'MD_CHW_NA')";
		String sql_cabn = "insert into SAPR3.CABN (mandt, ATNAM) values('200','MG_PRODUCTTYPE')";
		String sql_ksml = "insert into sapr3.KSML (mandt, clint, imerk) values ('200', '0000000000', '0000000000')";
		String sql_mara = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMNew')";

		int t1 = SqlHelper.runUpdateSql(sql_klah_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah_2, conn);
		int t3 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t4 = SqlHelper.runUpdateSql(sql_ksml, conn);
		int t5 = SqlHelper.runUpdateSql(sql_mara, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR156QueryFound01() {
		try {
			logger.info("typemod:EACMNew,seoFlag:true");
			String typemod = "EACMNew";
			deleteDataClassicationMaint(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "ZDM" + typemod);

			String div = "B1";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			boolean seoFlag = true;

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r156(typemod, div, chwA, pimsIdentity, seoFlag);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'ZDM" + typemod + "'");
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
	public void testR156QueryFound02() {
		try {
			logger.info("typemod:EACMNew,seoFlag:false");
			String typemod = "EACMNew";
			deleteDataClassicationMaint(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "ZDM" + typemod);

			String div = "B1";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			boolean seoFlag = false;

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r156(typemod, div, chwA, pimsIdentity, seoFlag);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'ZDM" + typemod + "'");
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
		String del_klah_1 = "delete from SAPR3.KLAH where mandt='200' and KLART='ZDM' and CLASS='MD_XHW_NA'";
		String del_klah_2 = "delete from SAPR3.KLAH where mandt='200' and KLART='ZDM' and CLASS='MD_CHW_NA'";
		String del_cabn = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MG_PRODUCTTYPE'";
		String del_ksml = "delete from sapr3.ksml where clint='0000000000' and mandt ='200' and imerk='0000000000'";
		String del_mara = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMNew'";

		int t1 = SqlHelper.runUpdateSql(del_klah_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_klah_2, conn);
		int t3 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t4 = SqlHelper.runUpdateSql(del_ksml, conn);
		int t5 = SqlHelper.runUpdateSql(del_mara, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
