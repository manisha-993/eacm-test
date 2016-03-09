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
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R175create001ClassificationForMMFieldsType extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String activeId = "Z_DM_SAP_CLASSIFICATION_MAINT";

	@Before
	public void prepareData() {
		String sql_ksml_1 = "insert into sapr3.ksml select '200',CLINT,POSNR,ADZHL,ABTEI,DINKB,HERKU,IMERK,OMERK,KLART,RELEV,DATUV,TECHV,AENNR,"
				+ "LKENZ,VMERK,DPLEN,OFFST,BLLIN,DPTXT,CUSTR,JUSTR,COLOR,INTSF,INVER,CKBOX,INPUT,AMERK,SHAD_UPDATE_TS,SHAD_UPDATE_IND,"
				+ "SAP_TS from SAPR3.KSML where CLINT='9999991925' and MANDT = '300' and IMERK='0000510804'";
		String sql_ksml_2 = "insert into sapr3.ksml select '200',CLINT,POSNR,ADZHL,ABTEI,DINKB,HERKU,IMERK,OMERK,KLART,RELEV,DATUV,TECHV,AENNR,"
				+ "LKENZ,VMERK,DPLEN,OFFST,BLLIN,DPTXT,CUSTR,JUSTR,COLOR,INTSF,INVER,CKBOX,INPUT,AMERK,SHAD_UPDATE_TS,SHAD_UPDATE_IND,"
				+ "SAP_TS from SAPR3.KSML where CLINT='9999991925' and MANDT = '300' and IMERK='0000510790'";

		String sql_cabn_1 = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,"
				+ "ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,"
				+ "ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,"
				+ "ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,DOKAR,DOKNR,"
				+ "DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,"
				+ "ATVPL,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from SAPR3.CABN where MANDT='300' AND ATINN='0000510804' and ATNAM='REMARKETER_REPORTER'";

		String sql_cabn_2 = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,"
				+ "ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,"
				+ "ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,"
				+ "ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,DOKAR,DOKNR,"
				+ "DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,"
				+ "ATVPL,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from SAPR3.CABN where MANDT='300' AND ATINN='0000510790' and ATNAM='MM_TYPE_MOD'";

		String sql_mara_1 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMT1MTC')";
		String sql_mara_2 = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EACMF1MTC')";

		int t1 = SqlHelper.runUpdateSql(sql_ksml_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_ksml_2, conn);
		int t3 = SqlHelper.runUpdateSql(sql_cabn_1, conn);
		int t4 = SqlHelper.runUpdateSql(sql_cabn_2, conn);
		int t5 = SqlHelper.runUpdateSql(sql_mara_1, conn);
		int t6 = SqlHelper.runUpdateSql(sql_mara_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r175New() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "NEW";
			String objectId = "001" + typeModel.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r175(typeModel, tmUPGObj, chwA, newFlag, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
	public void r175Upg() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "UPG";
			String objectId = "001" + typeModel.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(typeModel
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r175(typeModel, tmUPGObj, chwA, newFlag, FromToType,
					pimsIdentity);
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
	public void r175MtcToType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			chwA.setAnnDocNo("123401");
			tmUPGObj.setType("EACMT1");
			String pimsIdentity = "C";

			String FromToType = "MTCTOTYPE";
			String newFlag = "MTC";
			String objectId = "001" + tmUPGObj.getType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r175(typeModel, tmUPGObj, chwA, newFlag, FromToType,
					pimsIdentity);

			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + objectId + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

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
	public void r175MtcFromType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("EACM");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";
			tmUPGObj.setFromType("EACMF1");

			String FromToType = "MTCFROMTYPE";
			String newFlag = "MTC";
			String objectId = "001" + tmUPGObj.getFromType() + newFlag;

			int deleteDataResult = deleteDataClassicationMaint(tmUPGObj
					.getFromType() + newFlag);
			assertEquals(deleteDataResult, 0);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r175(typeModel, tmUPGObj, chwA, newFlag, FromToType,
					pimsIdentity);
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + objectId + "'");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String activeId = (String) rowDetails.get("ACTIV_ID");
			assertEquals("Z_DM_SAP_CLASSIFICATION_MAINT", activeId);
			String sessionId = (String) rowDetails.get("ZSESSION");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			String logdtlText = (String) rowDetails.get("TEXT");

			rowDetails = selectTableRow(map, "ZDM_LOGHDR");

			assertNotNull("Material Master created/updated successfully",
					logdtlText);

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
		String del_ksml_1 = "delete from SAPR3.KSML where mandt='200'and CLINT='9999991925' and IMERK='0000510804'";
		String del_ksml_2 = "delete from SAPR3.KSML where mandt='200'and CLINT='9999991925' and IMERK='0000510790'";
		String del_cabn_1 = "delete from SAPR3.CABN where mandt='200'and ATNAM ='REMARKETER_REPORTER'";
		String del_cabn_2 = "delete from SAPR3.CABN where mandt='200'and ATNAM ='MM_TYPE_MOD'";
		String del_mara_1 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMT1MTC'";
		String del_mara_2 = "delete from SAPR3.MARA where MANDT='200' and MATNR='EACMF1MTC'";

		int t1 = SqlHelper.runUpdateSql(del_ksml_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_ksml_2, conn);
		int t3 = SqlHelper.runUpdateSql(del_cabn_1, conn);
		int t4 = SqlHelper.runUpdateSql(del_cabn_2, conn);
		int t5 = SqlHelper.runUpdateSql(del_mara_1, conn);
		int t6 = SqlHelper.runUpdateSql(del_mara_2, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0 && t5 >= 0 && t6 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
