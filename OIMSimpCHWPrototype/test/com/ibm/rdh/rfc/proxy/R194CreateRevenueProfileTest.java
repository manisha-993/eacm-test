package com.ibm.rdh.rfc.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.AUOMaterial;

public class R194CreateRevenueProfileTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_t001W = "insert into sapr3.T001W select IBMSNAP_INTENTSEQ,IBMSNAP_OPERATION,IBMSNAP_COMMITSEQ,"
				+ "IBMSNAP_LOGMARKER,'200','1222',NAME1,NAME2,STRAS,PFACH,PSTLZ,ORT01,EKORG,BWKEY,VKORG,CHAZV,FABKL,KKOWK,"
				+ "KORDB,BEDPL,LAND1,REGIO,COUNC,CITYC,ADRNR,IWERK,TXJCD,VTWEG,SPART,KUNNR,SPRAS,WKSOP,AWSLS,CHAZV_OLD,VLFKZ,"
				+ "LIFNR,BZIRK,ZONE1,TAXIW,BZQHL,LET01,LET02,LET03,TXNAM_MA1,TXNAM_MA2,TXNAM_MA3,BETOL,ACHVM,DVSART,FPRFW,J_1BBRANCH,"
				+ "MGVUPD,MISCH,NODETYPE,NSCHEMA,PKOSA,VTBFI from sapr3.T001W where mandt='300' and werks='0001'";
		String sql_mara_1 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMCHW')";
		String sql_mara_2 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMNEW')";
		String sql_mara_3 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMUPG')";
		String sql_mara_4 = "insert into sapr3.MARA (mandt,MATNR) values('200','EACMMTC')";
		String sql_marc_1 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMCHW','1222')";
		String sql_marc_2 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMMTC','1222')";
		String sql_marc_3 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMUPG','1222')";
		String sql_marc_4 = "insert into sapr3.MARC (mandt,matnr,werks) values('200','EACMNEW','1222')";

		int t1 = SqlHelper.runUpdateSql(sql_t001W, conn);
		int t2 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_mara_2, conn);
		int t4 = SqlHelper.runUpdateSql(sql_mara_3, conn);
		int t5 = SqlHelper.runUpdateSql(sql_mara_4, conn);
		int t6 = SqlHelper.runUpdateSql(sql_marc_1, conn);
		int t7 = SqlHelper.runUpdateSql(sql_marc_2, conn);
		int t8 = SqlHelper.runUpdateSql(sql_marc_3, conn);
		int t9 = SqlHelper.runUpdateSql(sql_marc_4, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0
				&& t7 >= 0 && t8 >= 0 && t9 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r194New() {
		try {

			String type = "EACM";
			String annDocNo = "123401";
			Vector auoMaterials = new Vector();
			Vector typeModelRevs = new Vector();
			String material = "EACMCHW";
			String percentage = "57";
			AUOMaterial auo = new AUOMaterial(material, percentage);
			auoMaterials.add(auo);
			String revProfileName = "123402";
			String newFlag = "NEW";
			String pimsIdentity = "C";
			String plant = "1222";

			String object_id = type + newFlag + "/" + plant + "/Y";

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, "Z_DM_SAP_BOM_CREATE",
					object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r194(type, annDocNo, auoMaterials, typeModelRevs,
					revProfileName, newFlag, pimsIdentity, plant);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_CREATE'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			map.put("ZDMOBJTYP", "'BMP'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
	public void r194Upg() {
		try {

			String type = "EACM";
			String annDocNo = "123401";
			Vector auoMaterials = new Vector();
			Vector typeModelRevs = new Vector();
			String material = "EACMCHW";
			String percentage = "57";
			AUOMaterial auo = new AUOMaterial(material, percentage);
			auoMaterials.add(auo);
			String revProfileName = "123402";
			String newFlag = "UPG";
			String pimsIdentity = "C";
			String plant = "1222";

			String object_id = type + newFlag + "/" + plant + "/Y";

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, "Z_DM_SAP_BOM_CREATE",
					object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r194(type, annDocNo, auoMaterials, typeModelRevs,
					revProfileName, newFlag, pimsIdentity, plant);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_CREATE'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			map.put("ZDMOBJTYP", "'BMP'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
	public void r194Mtc() {
		try {

			String type = "EACM";
			String annDocNo = "123401";
			Vector auoMaterials = new Vector();
			Vector typeModelRevs = new Vector();
			String material = "EACMCHW";
			String percentage = "57";
			AUOMaterial auo = new AUOMaterial(material, percentage);
			auoMaterials.add(auo);
			String revProfileName = "123402";
			String newFlag = "MTC";
			String pimsIdentity = "C";
			String plant = "1222";

			String object_id = type + newFlag + "/" + plant + "/Y";

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, "Z_DM_SAP_BOM_CREATE",
					object_id);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r194(type, annDocNo, auoMaterials, typeModelRevs,
					revProfileName, newFlag, pimsIdentity, plant);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_BOM_CREATE'");
			map.put("OBJECT_ID", "'" + object_id + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDM_SESSION", "'" + sessionId + "'");
			map.put("ZDMOBJTYP", "'BMP'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
		String del_t001W = "delete from sapr3.T001W where mandt='200' and werks='1222'";
		String del_mara = "delete from sapr3.mara where mandt='200' and MATNR in ('EACMCHW','EACMMTC','EACMUPG','EACMNEW')";
		String del_marc = "delete from sapr3.marc where mandt='200' and matnr in ('EACMCHW','EACMMTC','EACMUPG','EACMNEW') and werks='1222'";
		String del_mast = "delete from sapr3.mast where mandt='200' and matnr in ('EACMNEW','EACMUPG','EACMMTC') and werks='1222' and stlan='Y'";
		int t1 = SqlHelper.runUpdateSql(del_t001W, conn);
		int t2 = SqlHelper.runUpdateSql(del_mara, conn);
		int t3 = SqlHelper.runUpdateSql(del_marc, conn);
		int t4 = SqlHelper.runUpdateSql(del_mast, conn);
		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
