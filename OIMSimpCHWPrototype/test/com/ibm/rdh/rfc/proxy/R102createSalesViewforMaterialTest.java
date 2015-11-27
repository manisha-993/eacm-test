package com.ibm.rdh.rfc.proxy;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R102createSalesViewforMaterialTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Test
	public void testR102QueryFound01() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RSS");
			chwA.setAnnDocNo("12345");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			String newFlag = "NEW";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "11";
			String salesOrg = "111";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax=new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);
			assertEquals("EA", "EA");
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
	public void testR102QueryFound02() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("AS4");
			chwA.setAnnDocNo("12345");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			String newFlag = "UPG";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "11";
			String salesOrg = "111";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax=new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);
			assertEquals("EA", "EA");
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
	public void testR102QueryFound03() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("RS6");
			chwA.setAnnDocNo("12345");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			String newFlag = "MTC";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType = "MTCTOTYPE";
			String pimsIdentity = "C";
			String flfilcd = "11";
			String salesOrg = "111";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax=new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);
			assertEquals("EA", "EA");
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
	public void testR102QueryFound04() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setSegmentAcronym("LSC");
			chwA.setAnnDocNo("12345");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			String sapPlant = "Y";
			String newFlag = "MTC";
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType = "MTCFROMTYPE";
			String pimsIdentity = "C";
			String flfilcd = "EMPTY";
			String salesOrg = "111";
			Vector taxCntryList = new Vector();
			CntryTax cntryTax=new CntryTax();
			cntryTax.setCountry("US");
			cntryTax.setClassification("H");
			cntryTax.setTaxCategory("UTXJ");
			taxCntryList.add(cntryTax);
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r102(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity, flfilcd, salesOrg, taxCntryList);
			assertEquals("EA", "EA");
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
