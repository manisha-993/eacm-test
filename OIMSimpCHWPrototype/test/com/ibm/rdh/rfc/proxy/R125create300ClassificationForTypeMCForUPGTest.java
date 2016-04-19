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

public class R125create300ClassificationForTypeMCForUPGTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH (mandt, KLART, CLASS) values ('200','300','MK_EACF_MC')";
		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACFUPG')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACFMTC')";
		String sql_rdx = "insert into sapr3.zdm_rdxcustmodel(mandt,zdm_class,zdm_syst_default) values ('200','MD_CHW_NA','X')";
		String sql_cabn = "insert into SAPR3.CABN (mandt, ATNAM) values('200','MK_EACF_MC')";
		String sql_ksml = "insert into sapr3.ksml (mandt, clint, imerk) values ('200', '0000000000', '0000000000')";
		
		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_mara_2, conn);
		int t4 = SqlHelper.runUpdateSql(sql_rdx, conn);
		int t5 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t6 = SqlHelper.runUpdateSql(sql_ksml, conn);
		if (t1>=0&& t2 >= 0 && t3 >= 0 &&t4>=0&&t5>=0&&t6>=0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR12501(){
		try {
			logger.info("newFlag: UPG");
			String type="EACF";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String newFlag="UPG";
			String pimsIdentity="C";
			
			String objectId="300"+type + newFlag;
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", objectId);
//			deleteDataClassicationMaint(type+newFlag);
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r125(type, chwA, newFlag, pimsIdentity);

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
	
	@Test
	public void testR12502(){
		try {
			logger.info("newFlag: MTC");
			String type="EACF";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String newFlag="MTC";
			String pimsIdentity="C";
			
			String objectId="300"+type + newFlag;
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", objectId);
//			deleteDataClassicationMaint(type+newFlag);
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r125(type, chwA, newFlag, pimsIdentity);

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
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='300' and CLASS='MK_EACF_MC'";
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACFUPG'";
		String del_mara_2 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACFMTC'";
		String del_rdx = "delete from sapr3.zdm_rdxcustmodel where mandt='200' and zdm_class='MD_CHW_NA' and zdm_syst_default='X'";
		String del_cabn = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MK_EACF_MC'";
		String del_ksml = "delete from sapr3.ksml where clint='0000000000' and mandt ='200' and imerk='0000000000'";
		
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t3 = SqlHelper.runUpdateSql(del_mara_2, conn);
		int t4 = SqlHelper.runUpdateSql(del_rdx, conn);
		int t5 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t6 = SqlHelper.runUpdateSql(del_ksml, conn);
		
		if (t1>=0&&t2 >= 0 && t3 >= 0 &&t4>=0&&t5>=0&&t6>=0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
			
	}
}
