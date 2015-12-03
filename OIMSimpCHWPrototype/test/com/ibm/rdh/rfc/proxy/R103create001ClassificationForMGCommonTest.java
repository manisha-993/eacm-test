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

public class R103create001ClassificationForMGCommonTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH select '200',CLINT,KLART, CLASS,STATU,KLAGR,BGRSE,BGRKL,BGRKP,ANAME,"
				+ "ADATU,VNAME,VDATU,VONDT,BISDT,ANZUO,PRAUS,SICHT,DOKNR,DOKAR,DOKTL,"
				+ "DOKVR,DINKZ,NNORM,NORMN,NORMB,NRMT1,NRMT2,AUSGD,VERSD,VERSI,LEIST,VERWE,SPART,LREF3,WWSKZ,"
				+ "WWSSI,POTPR,CLOBK,CLMUL,CVIEW,DISST,MEINS,CLMOD,VWSTL,VWPLA,CLALT,"
				+ "LBREI,BNAME,MAXBL,KNOBJ,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from SAPR3.KLAH "
				+ "where mandt='300' and KLART='001' and CLASS='MG_COMMON'";

		String sql_cabn = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,"
				+ "ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,"
				+ "ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,"
				+ "DOKAR,DOKNR,DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,ATVPL,SHAD_UPDATE_TS,"
				+ "SHAD_UPDATE_IND,SAP_TS FROM SAPR3.CABN where mandt='300' AND ATNAM='MG_PRODUCTTYPE'";

		String sql_ksml = "insert into sapr3.ksml select '200',CLINT,POSNR,ADZHL,ABTEI,DINKB,HERKU,IMERK,OMERK,KLART,RELEV,DATUV,TECHV,AENNR,"
				+ "LKENZ,VMERK,DPLEN,OFFST,BLLIN,DPTXT,CUSTR,JUSTR,COLOR,INTSF,INVER,CKBOX,INPUT,AMERK,SHAD_UPDATE_TS,SHAD_UPDATE_IND,"
				+ "SAP_TS from sapr3.ksml where clint='0000000429' and mandt = '300' and imerk='0000510805'";

		int t1=SqlHelper.runUpdateSql(sql_klah, conn);
		int t2=SqlHelper.runUpdateSql(sql_cabn, conn);
		int t3=SqlHelper.runUpdateSql(sql_ksml, conn);
		if(t1>=0&&t2>=0&&t3>=0){
			System.out.println("insert success");
		}else{
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR103QueryFound01() {
		try {
			String newFlag = "NEW";
			String type = "EACM";
			deleteDataClassicationMaint(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + type + newFlag);

			logger.info("newFlag:NEW,FromToType:MTCTOTYPE");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + type + newFlag + "'");
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
	public void testR103QueryFound02() {
		try {
			String newFlag = "UPG";
			String type = "EACM";
			deleteDataClassicationMaint(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + type + newFlag);

			logger.info("newFlag:UPG,FromToType:MTCTOTYPE");
			TypeModel typeModel = new TypeModel();
			typeModel.setType(type);
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + type + newFlag + "'");
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
	public void testR103QueryFound03() {
		try {
			String newFlag = "MTC";
			String type = "EACMT1";
			deleteDataClassicationMaint(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + type + newFlag);

			logger.info("newFlag:MTC,FromToType:MTCTOTYPE");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType(type);
			tmUPGObj.setFromType("EACMF1");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + type + newFlag + "'");
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
	public void testR103QueryFound04() {
		try {
			String newFlag = "MTC";
			String type = "EACMF1";
			deleteDataClassicationMaint(type + newFlag);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "001" + type + newFlag);

			logger.info("newFlag:MTC,FromToType:MTCFROMTYPE");

			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("EACMT1");
			tmUPGObj.setFromType(type);
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'001" + type + newFlag + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_CLASSIFICATION_MAINT'");
			map.put("OBJECT_ID", "'001" + type + newFlag + "'");
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
	public void deleteData(){
		String del_klah = "delete from SAPR3.KLAH where mandt='200' and KLART='001' and CLASS='MG_COMMON'";
		String del_cabn = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MG_PRODUCTTYPE'";
		String del_ksml = "delete from sapr3.ksml where clint='0000000429' and mandt ='200' and imerk='0000510805'";

		int t1=SqlHelper.runUpdateSql(del_klah, conn);
		int t2=SqlHelper.runUpdateSql(del_cabn, conn);
		int t3=SqlHelper.runUpdateSql(del_ksml, conn);
		if(t1>=0&&t2>=0&&t3>=0){
			System.out.println("delete success");
		}else{
			System.out.println("delete failed");
		}
	}
}
