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

public class R121createModelSelectionDependencyTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {

		String sql_cabn = "insert into SAPR3.CABN(MANDT,ATINN,ATNAM) values('200','1000000002','MK_EACM_MOD')";
		int t1 = SqlHelper.runUpdateSql(sql_cabn, conn);

		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}

	}

	@Test
	public void testR121() {
		try {

			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setModel("MODEL");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r121(typeModel, chwA, pimsIdentity);

			String depend = "SC_MK_" + typeModel.getType() + "_MODEL_"
					+ typeModel.getModel();
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + depend + "'");
			map.put("ZDMOBJTYP", "'KNO'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_DEPD_MAINTAIN'");
			map.put("OBJECT_ID", "'" + depend + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Object dependency  " + depend
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
		String del_cabn = "delete from SAPR3.CABN where MANDT='200' and ATINN='1000000002' AND ATNAM ='MK_EACM_MOD'";

		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		if (t1 >= 0 ) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
