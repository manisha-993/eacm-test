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

public class R157createTypeClassTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_CLASS_MAINTAIN";

	@Before
	public void prepareData() {
		String sql_rdx = "insert into sapr3.zdm_rdxcustmodel(mandt,zdm_class,zdm_syst_default) values ('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_rdx, conn);

		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r157a() {
		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACMT");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String class_id = "MK_" + tmUPGObj.getType() + "_MTC";
			String objectId = "300" + "MK_" + tmUPGObj.getType() + "_MTC";

			deleteKLAHRow("300", class_id, Constants.MANDT);
			deleteSWORRow("300", class_id, Constants.MANDT);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r157(chwA, tmUPGObj, FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLS'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASS_MAINTAIN", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic "
					+ " successfully assigned to classification  " + class_id
					+ "'");
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
	public void r157b() {
		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			chwA.setAnnDocNo("123401");
			tmUPGObj.setFromType("EACMF");
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			String class_id = "MK_" + tmUPGObj.getFromType() + "_MTC";
			String objectId = "300" + "MK_" + tmUPGObj.getFromType() + "_MTC";

			deleteKLAHRow("300", class_id, Constants.MANDT);
			deleteSWORRow("300", class_id, Constants.MANDT);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r157(chwA, tmUPGObj, FromToType, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLS'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASS_MAINTAIN", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic "
					+ " successfully assigned to classification  " + class_id
					+ "'");
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

		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";

		int t1 = SqlHelper.runUpdateSql(del_rdx, conn);

		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}