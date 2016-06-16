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
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R138_mtccreate300ClassificationForTypeRPQTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH (mandt, klart, class,clint) values ('200', '300', 'MK_EACM_RPQ','1000000000')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMMTC')";
		int t1 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah, conn);
		if (t1 >= 0 && t2 > 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r138MtcToType() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACM");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String newFlag = "MTC";
			String objectId = "300" + tmUPGObj.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r138(tmUPGObj, newFlag, chwA, FromToType, pimsIdentity);

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
	public void r138MtcFromType() {

		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();

			chwA.setAnnDocNo("123401");
			tmUPGObj.setFromType("EACM");
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			String newFlag = "MTC";
			String objectId = "300" + tmUPGObj.getFromType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getFromType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r138(tmUPGObj, newFlag, chwA, FromToType, pimsIdentity);

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
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='300' and CLASS='MK_EACM_RPQ' and clint='1000000000'";
		String del_mara = "delete from SAPR3.MARA where mandt='200' and MATNR ='EACMMTC'";
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_mara, conn);
		if (t1 >= 0 && t2 >= 0) {

			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
