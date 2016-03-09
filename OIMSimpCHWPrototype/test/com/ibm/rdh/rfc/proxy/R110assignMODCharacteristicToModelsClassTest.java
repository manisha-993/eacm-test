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

public class R110assignMODCharacteristicToModelsClassTest extends
		RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_cabn = "insert into SAPR3.CABN select '200',ATINN,ADZHL,'MK_EACM_MOD',ATIDN,ATFOR,ANZST,"
				+ "ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,"
				+ "ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,"
				+ "ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,DOKAR,DOKNR,"
				+ "DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,"
				+ "ATVPL,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from sapr3.cabn where MANDT='300' AND ATNAM='MK_0000_MOD' AND ATFOR='CHAR'";

		String sql_klah = "insert into SAPR3.KLAH select '200',CLINT,KLART,'MK_EACM_MODELS',STATU,KLAGR,BGRSE,BGRKL,BGRKP,ANAME,"
				+ "ADATU,VNAME,VDATU,VONDT,BISDT,ANZUO,PRAUS,SICHT,DOKNR,DOKAR,DOKTL,"
				+ "DOKVR,DINKZ,NNORM,NORMN,NORMB,NRMT1,NRMT2,AUSGD,VERSD,VERSI,LEIST,VERWE,SPART,LREF3,WWSKZ,"
				+ "WWSSI,POTPR,CLOBK,CLMUL,CVIEW,DISST,MEINS,CLMOD,VWSTL,VWPLA,CLALT,"
				+ "LBREI,BNAME,MAXBL,KNOBJ,SHAD_UPDATE_TS,SHAD_UPDATE_IND,SAP_TS from SAPR3.KLAH "
				+ "where mandt='300' and KLART='300' and CLASS='MK_0000_MODELS'";

		int t1 = SqlHelper.runUpdateSql(sql_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(sql_klah, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR110QueryFound() {
		try {

			TypeModel typeModel = new TypeModel();
			typeModel.setType("EACM");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123401");
			String pimsIdentity = "C";

			String jklart = "300";
			String jclass = "MK_" + typeModel.getType() + "_MODELS";
			String merkma = "MK_" + typeModel.getType() + "_MOD";
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_ASSIGN_CHAR_TO_CLASS", jklart + "/" + jclass);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r110(typeModel, chwA, pimsIdentity);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + jklart + jclass + "'");
			map.put("ZDMOBJTYP", "'CLS'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_ASSIGN_CHAR_TO_CLASS'");
			map.put("OBJECT_ID", "'" + jklart + "/" + jclass + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  " + merkma
					+ ", successfully assigned to classification  " + jklart
					+ "/" + jclass +"'");
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
		String del_cabn = "delete from SAPR3.CABN where mandt='200' and ATNAM='MK_EACM_MOD' AND ATFOR='CHAR'";
		String del_klah = "delete from sapr3.klah where mandt='200' and klart='300' and class='MK_EACM_MODELS'";

		int t1 = SqlHelper.runUpdateSql(del_cabn, conn);
		int t2 = SqlHelper.runUpdateSql(del_klah, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
