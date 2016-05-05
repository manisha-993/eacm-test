package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R124createUpgradeValueForTypeMCCharacteristicTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void r124() {
		try {

			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo typeModelUpg = new TypeModelUPGGeo();
			TypeModelUPGGeo typeModelUpg1 = new TypeModelUPGGeo();
			typeModelUpg.setType("EACM");
			typeModelUpg.setModel("MODEL");
			typeModelUpg.setFromModel("EACMF");

			typeModelUpg1.setType("EACM");
			typeModelUpg1.setModel("MODEL");
			typeModelUpg1.setFromModel("EACMF");

			Vector tmugV = new Vector();
			tmugV.add(typeModelUpg);
			tmugV.add(typeModelUpg1);
			chwA.setAnnDocNo("123401");

			String pimsIdentity = "C";
			String charac = "MK_" + typeModelUpg.getType() + "_MC";

			String activeId = "Z_DM_SAP_CHAR_MAINTAIN";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CHAR_MAINTAIN", charac);

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r124(tmugV, chwA, pimsIdentity);

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

		String del_cabn = "delete from SAPR3.CABN where mandt='200' and ATNAM='MK_EACM_MC'";
		String del_cabnt = "delete from SAPR3.CABNT where mandt='200' and ATBEZ='Models'";
		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(del_cabnt, conn);

		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
