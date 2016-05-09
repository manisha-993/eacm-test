package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R204ReadMaterialTest extends RdhRestProxyTest{
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();
	@Before
	public void prepareData() {
		String sql_mara = "insert into SAPR3.MARA (MANDT,MATNR) values('200','EMAT')";
		String sql_makt = "insert into SAPR3.MAKT (MANDT,MATNR,SPRAS) values('200','EMAT','E')";
		
		int t1 = SqlHelper.runUpdateSql(sql_mara, conn);
		int t2 = SqlHelper.runUpdateSql(sql_makt, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}
	
	@Test
	public void testR204(){
		try{
			String material="EMAT";
			
			RdhRestProxy rfcProxy = new RdhRestProxy();
			Boolean ba=rfcProxy.r204(material);
			assertEquals(true,ba);
			
		}catch (HWPIMSAbnormalException ex) {
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
		String del_mara = "delete from SAPR3.MARA where MANDT='200' and MATNR='EMAT'";
		String del_makt = "delete from SAPR3.MAKT where MANDT='200' and MATNR='EMAT' and SPRAS='E'";
		
		int t1 = SqlHelper.runUpdateSql(del_mara, conn);
		int t2 = SqlHelper.runUpdateSql(del_makt, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
