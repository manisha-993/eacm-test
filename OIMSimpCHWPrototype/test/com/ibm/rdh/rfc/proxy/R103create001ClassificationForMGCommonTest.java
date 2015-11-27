package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class R103create001ClassificationForMGCommonTest extends RdhRestProxy {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	
	@Test
	public  void testR103QueryFound01(){
		try{
			logger.info("newFlag:NEW,FromToType:MTCTOTYPE");
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4441");
			String newFlag="NEW";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("12345");
			TypeModelUPGGeo tmUPGObj=new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType="MTCTOTYPE";
			String pimsIdentity="C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj,
					FromToType, pimsIdentity);
			//assertEquals("EA", "EA");
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
	public  void testR103QueryFound02(){
		try{
			logger.info("newFlag:NEW,FromToType:MTCTOTYPE");
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			String newFlag="UPG";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("12345");
			TypeModelUPGGeo tmUPGObj=new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType="MTCTOTYPE";
			String pimsIdentity="C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj,
					FromToType, pimsIdentity);
			//assertEquals("EA", "EA");
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
	public  void testR103QueryFound03(){
		try{
			logger.info("newFlag:MTC,FromToType:MTCTOTYPE");
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			String newFlag="MTC";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("12345");
			TypeModelUPGGeo tmUPGObj=new TypeModelUPGGeo();
			tmUPGObj.setType("4442");
			tmUPGObj.setFromType("4441");
			String FromToType="MTCTOTYPE";
			String pimsIdentity="C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj,
					FromToType, pimsIdentity);
			//assertEquals("EA", "EA");
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
	public  void testR103QueryFound04(){
		try{
			logger.info("newFlag:MTC,FromToType:MTCFROMTYPE");
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			String newFlag="MTC";
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnDocNo("12345");
			TypeModelUPGGeo tmUPGObj=new TypeModelUPGGeo();
			tmUPGObj.setType("4443");
			tmUPGObj.setFromType("4441");
			String FromToType="MTCFROMTYPE";
			String pimsIdentity="C";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r103(typeModel, newFlag, chwA, tmUPGObj,
					FromToType, pimsIdentity);
			//assertEquals("EA", "EA");
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

