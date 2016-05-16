package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R143assignTypeModelAsSalesBOMItemWithDependenciesTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_t001W = "insert into sapr3.T001W select IBMSNAP_INTENTSEQ,IBMSNAP_OPERATION,IBMSNAP_COMMITSEQ,"
				+ "IBMSNAP_LOGMARKER,'200','0010',NAME1,NAME2,STRAS,PFACH,PSTLZ,ORT01,EKORG,BWKEY,VKORG,CHAZV,FABKL,KKOWK,"
				+ "KORDB,BEDPL,LAND1,REGIO,COUNC,CITYC,ADRNR,IWERK,TXJCD,VTWEG,SPART,KUNNR,SPRAS,WKSOP,AWSLS,CHAZV_OLD,VLFKZ,"
				+ "LIFNR,BZIRK,ZONE1,TAXIW,BZQHL,LET01,LET02,LET03,TXNAM_MA1,TXNAM_MA2,TXNAM_MA3,BETOL,ACHVM,DVSART,FPRFW,J_1BBRANCH,"
				+ "MGVUPD,MISCH,NODETYPE,NSCHEMA,PKOSA,VTBFI from sapr3.T001W where mandt='300' and werks='0001'";
		String sql_marc_1 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMNEW','0010')";
		String sql_marc_2 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMUPG','0010')";
		String sql_marc_3 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMMTC','0010')";
		String sql_mast_1 = "insert into sapr3.MAST (mandt,matnr,werks,stlan) values ('200','EACMNEW','0010','5')";
		String sql_mast_2 = "insert into sapr3.MAST (mandt,matnr,werks,stlan) values ('200','EACMUPG','0010','5')";
		String sql_mast_3 = "insert into sapr3.MAST (mandt,matnr,werks,stlan) values ('200','EACMMTC','0010','5')";
		String sql_mara_1 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMNEW')";
		String sql_mara_2 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMUPG')";
		String sql_mara_3 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMMTC')";

		int t1 = SqlHelper.runUpdateSql(sql_t001W, conn);
		int t2 = SqlHelper.runUpdateSql(sql_marc_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_marc_2, conn);
		int t4 = SqlHelper.runUpdateSql(sql_marc_3, conn);
		int t5 = SqlHelper.runUpdateSql(sql_mast_1, conn);
		int t6 = SqlHelper.runUpdateSql(sql_mast_2, conn);
		int t7 = SqlHelper.runUpdateSql(sql_mast_3, conn);
		int t8 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t9 = SqlHelper.runUpdateSql(sql_mara_2, conn);
		int t10 = SqlHelper.runUpdateSql(sql_mara_3, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0 && t8 >= 0 && t9 >= 0 && t10 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR14301() {
		try {
			logger.info("newFlag:NEW");
			String type = "EACM";
			String sapPlant = "0010";
			Vector geoV = new Vector();
			TypeModel typeModel1 = new TypeModel();
			typeModel1.setType("EACM");
			typeModel1.setModel("NEW");
			TypeModel typeModel2 = new TypeModel();
			typeModel2.setType("EACM");
			typeModel2.setModel("UPG");
			geoV.add(typeModel1);
			geoV.add(typeModel2);
			String newFlag = "NEW";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			Hashtable spItem_Categ = new Hashtable();
			spItem_Categ.put("EAT1EAM1", "");
			String pimsIdentity = "C";

			String object_id = type + "NEW/" + sapPlant + "/5";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_BOM_MAINTAIN", object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r143(type, sapPlant, geoV, newFlag, chwA, spItem_Categ,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'EACMNEW           00105'");
			map.put("ZDMOBJTYP", "'BEC'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_MAINTAIN'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Success'");
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
	public void testR14302() {
		try {
			logger.info("newFlag:UPG");
			String type = "EACM";
			String sapPlant = "0010";
			Vector geoV = new Vector();
			TypeModel typeModel1 = new TypeModel();
			typeModel1.setType("EACM");
			typeModel1.setModel("NEW");
			TypeModel typeModel2 = new TypeModel();
			typeModel2.setType("EACM");
			typeModel2.setModel("UPG");
			geoV.add(typeModel1);
			geoV.add(typeModel2);
			String newFlag = "UPG";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			Hashtable spItem_Categ = new Hashtable();
			spItem_Categ.put("EAT1EAM1", "");
			String pimsIdentity = "C";

			String object_id = type + "UPG/" + sapPlant + "/5";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_BOM_MAINTAIN", object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r143(type, sapPlant, geoV, newFlag, chwA, spItem_Categ,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'EACMUPG           00105'");
			map.put("ZDMOBJTYP", "'BEC'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_MAINTAIN'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Success'");
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
	public void testR14303() {
		try {
			logger.info("newFlag:MTC");
			String type = "EACM";
			String sapPlant = "0010";
			Vector geoV = new Vector();
			TypeModelUPGGeo typeModelUpg1 = new TypeModelUPGGeo();
			typeModelUpg1.setType("EACM");
			typeModelUpg1.setModel("NEW");
			TypeModelUPGGeo typeModelUpg2 = new TypeModelUPGGeo();
			typeModelUpg2.setType("EACM");
			typeModelUpg2.setModel("UPG");
			geoV.add(typeModelUpg1);
			geoV.add(typeModelUpg2);
			String newFlag = "MTC";
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			Hashtable spItem_Categ = new Hashtable();
			spItem_Categ.put("EAT1EAM1", "");
			String pimsIdentity = "C";

			String object_id = type + "MTC/" + sapPlant + "/5";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_BOM_MAINTAIN", object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r143(type, sapPlant, geoV, newFlag, chwA, spItem_Categ,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'EACMMTC           00105'");
			map.put("ZDMOBJTYP", "'BEC'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_MAINTAIN'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Success'");
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
		String del_t001W = "delete from sapr3.T001W where mandt='200' and werks='0010'";
		String del_marc = "delete from sapr3.marc where mandt='200' and matnr in ('EACMNEW','EACMUPG','EACMMTC') and werks='0010'";
		String del_mast = "delete from sapr3.mast where mandt='200' and matnr in ('EACMNEW','EACMUPG','EACMMTC') and werks='0010' and stlan='5'";
		String del_mara = "delete from sapr3.mara where mandt='200' and MATNR in ('EACMNEW','EACMUPG','EACMMTC')";
		int t1 = SqlHelper.runUpdateSql(del_t001W, conn);
		int t2 = SqlHelper.runUpdateSql(del_marc, conn);
		int t3 = SqlHelper.runUpdateSql(del_mast, conn);
		int t4 = SqlHelper.runUpdateSql(del_mara, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
