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

public class R166createSTPPlantViewForMaterialTest extends RdhRestProxyTest{
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();
	
	@Test
	public void testR166QueryFound01() {				
		try {
			logger.info("newFlag:NEW");
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnouncementType("New");
			chwA.setAnnDocNo("123166");
			
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			
			CHWGeoAnn chwAg=new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			
			String storageLocation="sass";
			String newFlag="NEW";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r166(chwA, typeModel, chwAg, storageLocation, newFlag);
			assertEquals("EA","EA");
			
		} catch(HWPIMSAbnormalException ex){
			logger.info("error message= " +ex.getMessage());	
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {
			
		}		
	}
	@Test
	public void testR166QueryFound02() {				
		try {
			logger.info("newFlag:UPG");
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnouncementType("New");
			chwA.setAnnDocNo("123166");
			
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			
			CHWGeoAnn chwAg=new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			
			String storageLocation="sass";
			String newFlag="UPG";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r166(chwA, typeModel, chwAg, storageLocation, newFlag);
			assertEquals("EA","EA");
			
		} catch(HWPIMSAbnormalException ex){
			logger.info("error message= " +ex.getMessage());	
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {
			
		}		
	}
	@Test
	public void testR166QueryFound03() {				
		try {
			logger.info("newFlag:MTC");
			CHWAnnouncement chwA=new CHWAnnouncement();
			chwA.setAnnouncementType("New");
			chwA.setAnnDocNo("123166");
			
			TypeModel typeModel=new TypeModel();
			typeModel.setType("4442");
			typeModel.setDiv("B1");
			
			CHWGeoAnn chwAg=new CHWGeoAnn();
			chwAg.setAnnouncementDate(new Date());
			
			String storageLocation="sass";
			String newFlag="MTC";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r166(chwA, typeModel, chwAg, storageLocation, newFlag);
			assertEquals("EA","EA");
			
		} catch(HWPIMSAbnormalException ex){
			logger.info("error message= " +ex.getMessage());	
			Assert.fail("error message= " + ex.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		} finally {
			
		}		
	}
}
