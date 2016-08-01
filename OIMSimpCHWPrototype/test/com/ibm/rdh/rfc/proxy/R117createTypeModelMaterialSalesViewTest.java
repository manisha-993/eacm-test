package com.ibm.rdh.rfc.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;

public class R117createTypeModelMaterialSalesViewTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void testR117QueryFound01() {
		try {
			logger.info("SegmentAcronym:RSS, flfil:XCC");
			String typemod = "EACM";
			deleteDataMatmCreate(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RSS");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			String div = "46";
			String acctAsgnGrp = "";
			PlannedSalesStatus ps = new PlannedSalesStatus();
			ps.setCurrentSalesStatus("YA");
			ps.setCurrentEffectiveDate(new Date());
			boolean bumpCtr = true;
			String pimsIdentity = "C";
			String flfil = "XCC";
			String salesOrg1 = "0147";
			String productHierarchy = "ph";
			Vector VectTaxList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			VectTaxList.add(cntryTax);
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r117(chwA, typemod, div, acctAsgnGrp, ps, bumpCtr,
					pimsIdentity, flfil, salesOrg1, productHierarchy,
					VectTaxList, plantValue, chwAg);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + typemod + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + typemod + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ typemod + "'");
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
	public void testR117QueryFound02() {
		try {
			logger.info("SegmentAcronym:AS4, flfil:BTH");
			String typemod = "EACM";
			deleteDataMatmCreate(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("AS4");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			String div = "46";
			String acctAsgnGrp = "06";
			PlannedSalesStatus ps = new PlannedSalesStatus();
			ps.setCurrentSalesStatus("YA");
			ps.setCurrentEffectiveDate(new Date());
			boolean bumpCtr = true;
			String pimsIdentity = "C";
			String flfil = "BTH";
			String salesOrg1 = "0147";
			String productHierarchy = "ph";
			Vector VectTaxList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			VectTaxList.add(cntryTax);
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r117(chwA, typemod, div, acctAsgnGrp, ps, bumpCtr,
					pimsIdentity, flfil, salesOrg1, productHierarchy,
					VectTaxList, plantValue, chwAg);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + typemod + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + typemod + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ typemod + "'");
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
	public void testR117QueryFound03() {
		try {
			logger.info("SegmentAcronym:RS6, flfil:ZIP");
			String typemod = "EACM";
			deleteDataMatmCreate(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RS6");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			String div = "46";
			String acctAsgnGrp = "01";
			PlannedSalesStatus ps = new PlannedSalesStatus();
			ps.setCurrentSalesStatus("YA");
			ps.setCurrentEffectiveDate(new Date());
			boolean bumpCtr = true;
			String pimsIdentity = "C";
			String flfil = "ZIP";
			String salesOrg1 = "0147";
			String productHierarchy = "ph";
			Vector VectTaxList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			VectTaxList.add(cntryTax);
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r117(chwA, typemod, div, acctAsgnGrp, ps, bumpCtr,
					pimsIdentity, flfil, salesOrg1, productHierarchy,
					VectTaxList, plantValue, chwAg);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + typemod + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + typemod + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ typemod + "'");
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
	public void testR117QueryFound04() {
		try {
			logger.info("SegmentAcronym:RS6, flfil:EMPTY");
			String typemod = "EACM";
			deleteDataMatmCreate(typemod);
			deletezdmLogHdrAndzdmLogDtl(Constants.MANDT,
					"Z_DM_SAP_MATM_CREATE", typemod);

			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RS6");
			chwA.setAnnDocNo("123401");
			chwA.setAnnouncementType("New");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			String div = "46";
			String acctAsgnGrp = "01";
			PlannedSalesStatus ps = new PlannedSalesStatus();
			ps.setCurrentSalesStatus("YA");
			ps.setCurrentEffectiveDate(new Date());
			boolean bumpCtr = true;
			String pimsIdentity = "C";
			String flfil = "EMPTY";
			String salesOrg1 = "0147";
			String productHierarchy = "ph";
			Vector VectTaxList = new Vector();
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			VectTaxList.add(cntryTax);
			String plantValue = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r117(chwA, typemod, div, acctAsgnGrp, ps, bumpCtr,
					pimsIdentity, flfil, salesOrg1, productHierarchy,
					VectTaxList, plantValue, chwAg);

			// Test function execute success
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> rowDetails;

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ZDMOBJKEY", "'" + typemod + "'");
			map.put("ZDMOBJTYP", "'MAT'");
			rowDetails = selectTableRow(map, "ZDM_PARKTABLE");
			assertNotNull(rowDetails);

			map.clear();
			map.put("MANDT", "'" + Constants.MANDT + "'");
			map.put("ACTIV_ID", "'Z_DM_SAP_MATM_CREATE'");
			map.put("OBJECT_ID", "'" + typemod + "'");
			rowDetails = selectTableRow(map, "ZDM_LOGHDR");
			assertNotNull(rowDetails);
			String sessionId = (String) rowDetails.get("ZSESSION");
			String status = (String) rowDetails.get("STATUS");
			assertEquals(status, "success");

			map.clear();
			map.put("ZSESSION", "'" + sessionId + "'");
			map.put("TEXT", "'Material Master created/updated successfully: "
					+ typemod + "'");
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
}
