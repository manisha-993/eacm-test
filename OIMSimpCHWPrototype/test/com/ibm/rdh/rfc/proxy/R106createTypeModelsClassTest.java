package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;

public class R106createTypeModelsClassTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	String XEIxx_R001 = "K";
	String Insert_ProductID = "1234W11";
	String Notmara_ProductID = "1234W12";
	String Notmakt_ProductID = "1234W13";
	

	@Test
	
	public void r106(){
		try{
		CHWAnnouncement chwA = new CHWAnnouncement();
		TypeModel typeModel = new TypeModel();
		typeModel.setType("4441");
		chwA.setAnnDocNo("123456");
		String pimsIdentity="C";
			
		RdhRestProxy rfcProxy = new RdhRestProxy();
		rfcProxy.r106(typeModel, chwA, pimsIdentity);
		
		}catch(HWPIMSAbnormalException ex){
			logger.info("error message= " + ex.getMessage());
			Assert.fail("error message= " + ex.getMessage());
			assertEquals("EA", "EA");
			
		} catch (Exception e) {
			e.getStackTrace();
			Assert.fail("There is some error :" + e.getMessage());
		}finally{
			
		}
		
		
	}

}
