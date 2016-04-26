package com.ibm.rdh.rfc.proxy;

import java.util.Date;
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
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;

public class R115createTypeModelMaterialBasicViewTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_rdx = "insert into SAPR3.ZDM_RDXCUSTMODEL (MANDT,ZDM_CLASS,ZDM_SYST_DEFAULT) values('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_rdx, conn);
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR115QueryFound() {
		try {
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
				"Z_DM_SAP_MATM_CREATE", "EACMnewCreate");
			deleteDataMatmCreate("EACMnewCreate");
			
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setModel("newCreate");
			typeModel.setDiv("B1");
			typeModel.setEanUPCCode("");
			typeModel.setProductHierarchy("ph");
			typeModel.setDescription("new create model");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnDocNo("123402");
			chwAg.setAnnouncementDate(new Date());
			String pimsIdentity = "C";
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r115(chwA, typeModel, chwAg, pimsIdentity, plantValue);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'EACMnewCreate'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'EACMnewCreate'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: EACMnewCreate'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);
		}catch (HWPIMSAbnormalException ex) {
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
		String del_rdx = "delete from SAPR3.ZDM_RDXCUSTMODEL where mandt='200' and ZDM_CLASS='MD_CHW_NA' and ZDM_SYST_DEFAULT='X'";

		int t1 = SqlHelper.runUpdateSql(del_rdx, conn);
		if (t1 >= 0 ) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
