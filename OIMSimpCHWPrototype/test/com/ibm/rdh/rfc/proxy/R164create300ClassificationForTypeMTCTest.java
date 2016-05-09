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
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R164create300ClassificationForTypeMTCTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {

		String sql_klah_1 = "insert into SAPR3.KLAH (mandt, klart, class,clint) values ('200', '300', 'MK_EACMT1_MTC','1000000000')";
		String sql_klah_2 = "insert into SAPR3.KLAH (mandt, klart, class,clint) values ('200', '300', 'MK_EACMF1_MTC','1100000000')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMT1MTC')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMF1MTC')";
		String sql_ksml_1 = "insert into SAPR3.KSML(MANDT,CLINT,IMERK, POSNR, ADZHL) values('200','1000000000','1000000000','101','1001') ";
		String sql_ksml_2 = "insert into SAPR3.KSML(MANDT,CLINT,IMERK, POSNR, ADZHL) values('200','1100000000','1000000001','101','1001') ";
		String sql_cabn_1 = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM,ADZHL) values('200','1000000000','MK_EACMT1_MTC','1001')";
		String sql_cabn_2 = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM,ADZHL) values('200','1000000001','MK_EACMF1_MTC','1001')";

		int t1 = SqlHelper.runUpdateSql(sql_klah_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah_2, conn);
		int t3 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t4 = SqlHelper.runUpdateSql(sql_mara_2, conn);
		int t5 = SqlHelper.runUpdateSql(sql_ksml_1, conn);
		int t6 = SqlHelper.runUpdateSql(sql_ksml_2, conn);
		int t7 = SqlHelper.runUpdateSql(sql_cabn_1, conn);
		int t8 = SqlHelper.runUpdateSql(sql_cabn_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0 && t8 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r164MtcToType() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String pimsIdentity = "C";
			String FromToType = "MTCTOTYPE";
			String objectId = "300" + tmUPGObj.getType() + "MTC";

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getType() + "MTC");
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r164(tmUPGObj, chwA, FromToType, pimsIdentity);

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
	public void r164MtcFromType() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String pimsIdentity = "C";
			String FromToType = "MTCFROMTYPE";
			String objectId = "300" + tmUPGObj.getFromType() + "MTC";

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getFromType() + "MTC");
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r164(tmUPGObj, chwA, FromToType, pimsIdentity);

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

		String del_klah_1 = "delete from SAPR3.KLAH where mandt='200' and KLART='300' and CLASS='MK_EACMT1_MTC' and clint='1000000000'";
		String del_klah_2 = "delete from SAPR3.KLAH where mandt='200' and KLART='300' and CLASS='MK_EACMF1_MTC' and clint='1100000000'";
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMT1MTC'";
		String del_mara_2 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMF1MTC'";
		String del_ksml_1 = "delete from SAPR3.KSML where MANDT='200' and CLINT='1000000000' and IMERK='1000000000' and POSNR='101' and ADZHL='1001'";
		String del_ksml_2 = "delete from SAPR3.KSML where MANDT='200' and CLINT='1100000000' and IMERK='1000000001' and POSNR='101' and ADZHL='1001'";
		String del_cabn_1 = "delete from SAPR3.CABN where MANDT='200' and ATINN='1000000000' AND ATNAM ='MK_EACMT1_MTC' and ADZHL='1001'";
		String del_cabn_2 = "delete from SAPR3.CABN where MANDT='200' and ATINN='1000000001' AND ATNAM ='MK_EACMF1_MTC' and ADZHL='1001'";

		int t1 = SqlHelper.runUpdateSql(del_klah_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_klah_2, conn);
		int t3 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t4 = SqlHelper.runUpdateSql(del_mara_2, conn);
		int t5 = SqlHelper.runUpdateSql(del_ksml_1, conn);
		int t6 = SqlHelper.runUpdateSql(del_ksml_2, conn);
		int t7 = SqlHelper.runUpdateSql(del_cabn_1, conn);
		int t8 = SqlHelper.runUpdateSql(del_cabn_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0 && t8 >= 0) {

			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
