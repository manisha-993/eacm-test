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

public class R152create012ClassificationForRPQTypeFeatureTest extends RdhRestProxyTest{
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH (mandt, clint, KLART, CLASS) values ('200','0000000000','012','MK_PRODUCT_CHARS')";
		String sql_cabn_1 = "insert into SAPR3.CABN (mandt, ATNAM, atinn) values('200','MK_CHARACTERISTIC_TYPE','0000000001')";
		String sql_cabn_2 = "insert into SAPR3.CABN (mandt, ATNAM, atinn) values('200','MK_SALES_RELEVANCY','0000000002')";
		String sql_ksml_1 = "insert into sapr3.ksml (mandt, clint, posnr, imerk) values ('200', '0000000000', '001', '0000000001')";
		String sql_ksml_2 = "insert into sapr3.ksml (mandt, clint, posnr, imerk) values ('200', '0000000000', '002', '0000000002')";
		String sql_rdx = "insert into sapr3.zdm_rdxcustmodel(mandt,zdm_class,zdm_syst_default) values ('200','MD_CHW_NA','X')";

		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_cabn_1, conn);
		int t4 = SqlHelper.runUpdateSql(sql_ksml_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_cabn_2, conn);
		int t5 = SqlHelper.runUpdateSql(sql_ksml_2, conn);
		int t6 = SqlHelper.runUpdateSql(sql_rdx, conn);
		if (t1 >= 0 && t2>=0 && t4>=0 &&t3>=0 &&t5>=0 && t6>=0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR152() {
		try {
			TypeFeature typeFeature=new TypeFeature();
			typeFeature.setType("EACF");
			typeFeature.setFeature("1000");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String objectId = "012MK_" + typeFeature.getType() + "_"+typeFeature.getFeature();
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", objectId);
			deleteDataClassicationMaint("MK_" + typeFeature.getType() + "_"+typeFeature.getFeature());

			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r152(typeFeature, chwA, pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
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
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='012' and CLASS='MK_PRODUCT_CHARS'";
		String del_cabn_1 = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MK_CHARACTERISTIC_TYPE'";
		String del_cabn_2 = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MK_SALES_RELEVANCY'";
		String del_ksml_1 = "delete from sapr3.ksml where clint='0000000000' and mandt ='200' and imerk='0000000001'";
		String del_ksml_2 = "delete from sapr3.ksml where clint='0000000000' and mandt ='200' and imerk='0000000002'";
		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";

		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_cabn_1, conn);
		int t3 = SqlHelper.runUpdateSql(del_cabn_2, conn);
		int t4 = SqlHelper.runUpdateSql(del_ksml_1, conn);
		int t5 = SqlHelper.runUpdateSql(del_ksml_2, conn);
		int t6 = SqlHelper.runUpdateSql(del_rdx, conn);

		if (t1 >= 0&& t2>=0 && t4>=0 && t3>=0 && t5>=0&& t6>=0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}

	}
}
