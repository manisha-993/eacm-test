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

public class R161createZDMClassificationForMKFEXTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH (mandt, klart, class,clint) values ('200', '300', 'MK_FEX','1000000000')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMUPG')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMMTC')";
		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_mara_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r161Upg() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			String type = "EACM";
			String newFlag = "UPG";
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			String objectId = "300" + type + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(type + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r161(type, newFlag, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
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
	public void r161Mtc() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			String type = "EACM";
			String newFlag = "MTC";
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			String objectId = "300" + type + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(type + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r161(type, newFlag, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;
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

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
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
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='300' and CLASS='MK_FEX' and clint='1000000000'";
		String del_mara = "delete from SAPR3.MARA where mandt='200' and MATNR IN ('EACMMTC','EACMUPG')";
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_mara, conn);
		if (t1 >= 0 && t2 >= 0) {

			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
