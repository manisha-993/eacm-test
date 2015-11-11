package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R189createCFIPlantViewForTypeTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String XEIxx_R001 = "K";
	String Insert_ProductID = "1234W11";
	String Notmara_ProductID = "1234W12";
	String Notmakt_ProductID = "1234W13";

	@Test
	public void r189() {
		try {
			CHWAnnouncement chwA = new CHWAnnouncement();
			TypeModel typeModel = new TypeModel();
			TypeModelUPGGeo tmUPGObj = new TypeModelUPGGeo();
			

			typeModel.setType("4441");
			typeModel.setDiv("B1");
			chwA.setAnnDocNo("123456");
			chwA.setAnnouncementType("New");
			
			String newFlag = "New";
			String FromToType = "";
			String pimsIdentity = "C";
			String sapPlant = "1999";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r189(chwA, typeModel, sapPlant, newFlag, tmUPGObj,
					FromToType, pimsIdentity);

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