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

public class R118create001ClassificationForMMFieldsTypeModelTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	
	@Before
	public void prepareData() {
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMMODEL')";

		int t1 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR118() {
		try {
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			typeModel.setModel("MODEL");
			typeModel.setCustomerSetup(false);
			typeModel.setHasRevProfile(true);
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String flfilcd = "fl1";
			String warrantyPeriod = "118";
			boolean remarkable = true;
			String pimsIdentity = "C";
			
			deleteDataClassicationMaint(typeModel.getType() + typeModel.getModel());
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + typeModel.getType() + typeModel.getModel());
			RdhRestProxy rdhRestProxy = new RdhRestProxy();
			rdhRestProxy.r118(typeModel, chwA, flfilcd, warrantyPeriod,
					remarkable, pimsIdentity);
			
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + typeModel.getType() + typeModel.getModel() + "'");
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
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMMODEL'";
		
		int t1 = SqlHelper.runUpdateSql(del_mara_1, conn);
		if (t1 >= 0 ) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
