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
import com.ibm.rdh.chw.entity.TypeModel;

public class R120maintainModelValueForTypeMODCharacteristicTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

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
	public void r120() {
		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();

			typeModel.setType("EACM");
			typeModel.setModel("NEW");
			typeModel.setDescription("EACM HW");
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			String charac = "MK_" + typeModel.getType() + "_MOD";
			String activeId = "Z_DM_SAP_CHAR_MAINTAIN";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r120(typeModel, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + charac + "'");
			map.put("ZDMOBJTYP", "'CHR'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + charac + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  " + charac
					+ " created/updated successfully.'");
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

		String del_cabn = "delete from SAPR3.CABN where mandt='200' and ATNAM='MK_EACM_MOD'";
		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";
		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(del_rdx, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
