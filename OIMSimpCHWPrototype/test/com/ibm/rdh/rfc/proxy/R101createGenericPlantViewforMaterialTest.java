package com.ibm.rdh.rfc.proxy;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R101createGenericPlantViewforMaterialTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	@Test
	public void testR101queryFound01() {
		try {
			logger.info("newFlag:NEW");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();		
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123456");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("4443");
			
			String newFlag = "NEW";
			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "S";
			String plantValue = "1222";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag,
								loadingGrp, tmUPGObj, FromToType,
								pimsIdentity, plantValue);
					
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
	public void testR101queryFound02() {
		try {
			logger.info("newFlag:UPG");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();		
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123456");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("4443");
			
			String newFlag = "UPG";
			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "S";
			String plantValue = "1222";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag,
								loadingGrp, tmUPGObj, FromToType,
								pimsIdentity, plantValue);
					
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
	public void testR101queryFound03() {
		try {
			logger.info("newFlag:MTC,FromToType:MTCTOTYPE");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();		
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123456");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("4443");
			
			String newFlag = "MTC";
			String FromToType = "MTCTOTYPE";
			String loadingGrp = "";
			String pimsIdentity = "S";
			String plantValue = "1222";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag,
								loadingGrp, tmUPGObj, FromToType,
								pimsIdentity, plantValue);
					
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
	public void testR101queryFound04() {
		try {
			logger.info("newFlag:MTC,FromToType:MTCFROMTYPE");
			CHWAnnouncement chwA = new CHWAnnouncement();
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			TypeModel typeModel = new TypeModel();		
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			typeModel.setProductHierarchy("ph");
			CHWGeoAnn chwAg = new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			chwAg.setAnnDocNo("123456");
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			tmUPGObj.setLoadingGroup("M");
			tmUPGObj.setType("4443");
			
			String newFlag = "MTC";
			String FromToType = "MTCFROMTYPE";
			String loadingGrp = "";
			String pimsIdentity = "S";
			String plantValue = "1222";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r101(chwA, typeModel, chwAg, newFlag,
								loadingGrp, tmUPGObj, FromToType,
								pimsIdentity, plantValue);
					
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
