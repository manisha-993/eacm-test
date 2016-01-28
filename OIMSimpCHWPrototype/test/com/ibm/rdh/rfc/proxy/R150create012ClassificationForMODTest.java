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

public class R150create012ClassificationForMODTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	
	@Before
	public void prepareData() {
		String sql_klah = "insert into SAPR3.KLAH select '200',CLINT,KLART,CLASS,STATU,KLAGR,BGRSE,BGRKL,BGRKP,ANAME,"
				+ "ADATU,VNAME,VDATU,VONDT,BISDT,ANZUO,PRAUS,SICHT,DOKNR,DOKAR,DOKTL,"
				+ "DOKVR,DINKZ,NNORM,NORMN,NORMB,NRMT1,NRMT2,AUSGD,VERSD,VERSI,LEIST,VERWE,SPART,LREF3,WWSKZ,"
				+ "WWSSI,POTPR,CLOBK,CLMUL,CVIEW,DISST,MEINS,CLMOD,VWSTL,VWPLA,CLALT,"
				+ "LBREI,BNAME,MAXBL,KNOBJ,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from SAPR3.KLAH "
				+ "where mandt='300' and KLART='012' and CLASS='MK_PRODUCT_CHARS'";

		String sql_cabn_1 = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,"
				+ "ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,"
				+ "ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,"
				+ "ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,DOKAR,DOKNR,"
				+ "DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,"
				+ "ATVPL,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from sapr3.cabn where MANDT='300' AND ATNAM='MK_CHARACTERISTIC_TYPE' AND ATFOR='CHAR'";
		String sql_cabn_2 = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,"
				+ "ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,"
				+ "ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,"
				+ "ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,DOKAR,DOKNR,"
				+ "DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,"
				+ "ATVPL,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from sapr3.cabn where MANDT='300' AND ATNAM='MK_SALES_RELEVANCY' AND ATFOR='CHAR'";

		String sql_ksml_1 = "insert into sapr3.ksml select '200',CLINT,POSNR,ADZHL,ABTEI,DINKB,HERKU,IMERK,OMERK,KLART,RELEV,DATUV,TECHV,AENNR,"
				+ "LKENZ,VMERK,DPLEN,OFFST,BLLIN,DPTXT,CUSTR,JUSTR,COLOR,INTSF,INVER,CKBOX,INPUT,AMERK,SHAD_UPDATE_TS,SHAD_UPDATE_IND,"
				+ "SAP_TS from sapr3.ksml where clint='0000000425' and mandt = '300' and imerk='0000077466'";
		String sql_ksml_2 = "insert into sapr3.ksml select '200',CLINT,POSNR,ADZHL,ABTEI,DINKB,HERKU,IMERK,OMERK,KLART,RELEV,DATUV,TECHV,AENNR,"
				+ "LKENZ,VMERK,DPLEN,OFFST,BLLIN,DPTXT,CUSTR,JUSTR,COLOR,INTSF,INVER,CKBOX,INPUT,AMERK,SHAD_UPDATE_TS,SHAD_UPDATE_IND,"
				+ "SAP_TS from sapr3.ksml where clint='0000000425' and mandt = '300' and imerk='0000077467'";

		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		int t2 = SqlHelper.runUpdateSql(sql_cabn_1, conn);
		int t3 = SqlHelper.runUpdateSql(sql_cabn_2, conn);
		int t4 = SqlHelper.runUpdateSql(sql_ksml_1, conn);
		int t5 = SqlHelper.runUpdateSql(sql_ksml_2, conn);
		if (t1 >= 0 && t2 >=0 && t3 >=0 && t4 >=0 && t5 >=0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR150QueryFound() {
		try {
			
			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";
			
			deleteDataClassicationMaint("MK_" + typeModel.getType() + "_MOD");
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_CLASSIFICATION_MAINT", "012"+"MK_" + typeModel.getType() + "_MOD");
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r150(typeModel, chwA, pimsIdentity);

			String activeId="Z_DM_SAP_CLASSIFICATION_MAINT";
			String klart="012";
			String kpara="MK_" + typeModel.getType() + "_MOD";
			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + klart + kpara + "'");
			map.put("ZDMOBJTYP", "'CLF'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + klart + kpara + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Created records in parking table: 1'");
			rowDetails = selectTableRow(map, "ZDM_LOGDTL");
			assertNotNull(rowDetails);

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
		String del_klah = "delete from sapr3.klah where mandt='200' and klart='012' and class='MK_PRODUCT_CHARS'";
		String del_cabn = "delete from SAPR3.CABN where mandt='200' AND ATNAM in ('MK_CHARACTERISTIC_TYPE','MK_SALES_RELEVANCY')";
		String del_ksml = "delete from sapr3.ksml where clint='0000000425' and mandt ='200' and imerk in ('0000077466','0000077467')";
		
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		int t2=SqlHelper.runUpdateSql(del_cabn, conn);
		int t3=SqlHelper.runUpdateSql(del_ksml, conn);
		if (t1 >= 0 && t2 >=0 && t3 >=0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
