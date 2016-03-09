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

public class R160assignChartoClassFEAT_0000Test extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	String activeId = "Z_DM_SAP_ASSIGN_CHAR_TO_CLASS";
	
	@Before
	public void prepareData() {
		String sql_cabna = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,"
				+ "ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,"
				+ "ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,"
				+ "DOKAR,DOKNR,DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,ATVPL,SHAD_UPDATE_TS,"
				+ "SHAD_UPDATE_IND,SAP_TS FROM SAPR3.CABN where mandt='300' AND ATNAM='MK_SUBLINE'";
		String sql_cabnb = "insert into SAPR3.CABN select '200',ATINN,ADZHL,ATNAM,ATIDN,ATFOR,ANZST,ANZDZ,ATVOR,ATSCH,ATKLE,ATKON,ATEND,ATAEN,"
				+ "ATKLA,ATERF,ATEIN,ATAME,ATWME,MSEHI,ATDIM,ATGLO,ATGLA,ATINT,ATUNS,ATSON,ATTAB,ATFEL,ATTEI,ATPRT,ATPRR,ATPRF,ATWRD,"
				+ "ATFOD,ATHIE,ATDEX,ATFGA,ATVSC,ANAME,ADATU,VNAME,VDATU,ATXAC,ATYAC,ATMST,ATWSO,ATBSO,DATUV,TECHV,AENNR,LKENZ,ATWRI,"
				+ "DOKAR,DOKNR,DOKVR,DOKTL,KNOBJ,ATINP,ATVIE,WERKS,KATALOGART,AUSWAHLMGE,ATHKA,ATHKO,CLINT,ATTOL,ATZUS,ATVPL,SHAD_UPDATE_TS,"
				+ "SHAD_UPDATE_IND,SAP_TS FROM SAPR3.CABN where mandt='300' AND ATNAM='MK_RPQ_APPROVAL'";
		int t1 = SqlHelper.runUpdateSql(sql_cabna, conn);
		int t2 = SqlHelper.runUpdateSql(sql_cabnb, conn);
		if (t1 >= 0 && t2>=0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r160() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();

			typeModel.setType("EACM");
			chwA.setAnnDocNo("123401");

			String pimsIdentity = "C";
			
			String jklart = "300";
			String jclass = "MK_" + typeModel.getType() + "_FEAT_0000";
			String objectId = jklart +  "/" + jclass;

			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT, activeId, objectId);
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r160(typeModel, chwA, pimsIdentity);
			
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
			map.put("ACTIV_ID", "'" + activeId + "'");
			map.put("OBJECT_ID", "'" + jklart + "/" + jclass + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");
		
			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Characteristic  " + "MK_SUBLINE"+",MK_RPQ_APPROVAL"
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
		String del_cabna = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MK_SUBLINE'";
		String del_cabnb = "delete from SAPR3.CABN where mandt='200' AND ATNAM='MK_RPQ_APPROVAL'";
		int t1 = SqlHelper.runUpdateSql(del_cabna, conn);
		int t2 = SqlHelper.runUpdateSql(del_cabnb, conn);
		
		if (t1 == 1 && t2 ==1 ) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
