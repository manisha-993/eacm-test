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
import com.ibm.rdh.chw.entity.RevProfile;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R205ClassificationForBTProductsTypeMaterialsTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMNEW')";

		int t1 = SqlHelper.runUpdateSql(sql_mara_1, conn);

		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r205a() {

		try {
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmupg = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			boolean hasRevProfile = true;
			typeModel.setHasRevProfile(hasRevProfile);
			RevProfile revProfile = new RevProfile();
			revProfile.setRevenueProfile("12");
			typeModel.setRevProfile(revProfile);
			String pimsIdentity = "C";
			String fromtotype = "";
			String typeProfRefresh = "TYPENEW";
			String type = "EACM";
			String profile = "eacm";
			String newFlag = "NEW";
			String objectId = "001" + type + newFlag;
			String rfaNumber = "123401";

			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r205(typeModel, null, newFlag, null, null, null, null,
					pimsIdentity, rfaNumber);

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
	public void r205b() {

		try {
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmupg = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			boolean hasRevProfile = true;
			typeModel.setHasRevProfile(hasRevProfile);
			RevProfile revProfile = new RevProfile();
			revProfile.setRevenueProfile("12");
			typeModel.setRevProfile(revProfile);

			String pimsIdentity = "C";
			String fromtotype = "";
			String typeProfRefresh = null;
			String type = "EACM";
			String profile = "eacm";
			String newFlag = "NEW";
			String objectId = "001" + typeModel.getType() + newFlag;
			String rfaNumber = "123401";

			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r205(typeModel, tmupg, newFlag, fromtotype,
					typeProfRefresh, type, profile, pimsIdentity, rfaNumber);

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
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMNEW'";

		int t1 = SqlHelper.runUpdateSql(del_mara_1, conn);
		// int t2 = SqlHelper.runUpdateSql(del_mara_2, conn);
		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
