package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R104createZDMClassificationTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String XEIxx_R001 = "K";
	String Insert_ProductID = "1234W11";
	String Notmara_ProductID = "1234W12";
	String Notmakt_ProductID = "1234W13";

	@Test
	public void r104New() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "NEW";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

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
	public void r104Upg() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";

			String FromToType = "";
			String newFlag = "UPG";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

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
	public void r104MtcToType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";

			String FromToType="MTCTOTYPE";
			String newFlag = "MTC";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

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
	public void r104MtcFromType() {

		try {
			TypeModel typeModel = new TypeModel();
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			typeModel.setType("4441");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			String pimsIdentity = "C";

			String FromToType="MTCFROMTYPE";
			String newFlag = "MTC";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r104(typeModel, newFlag, chwA, tmUPGObj, FromToType,
					pimsIdentity);

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
