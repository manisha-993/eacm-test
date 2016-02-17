package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R207ReadPlantViewMaterialTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_marc = "insert into SAPR3.MARC (MANDT,MATNR,WERKS) values('200','1234AC1','1222')";
		int t1 = SqlHelper.runUpdateSql(sql_marc, conn);
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r207() {
		try {

			String type = "1234";
			String model = "AC1";
			String plant = "1222";

			RdhRestProxy rfcProxy = new RdhRestProxy();

			rfcProxy.r207(type, model, plant);

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
		String del_marc = "delete from SAPR3.MARC where MANDT='200'and MATNR='1234AC1' and WERKS='1222'";
		int t1 = SqlHelper.runUpdateSql(del_marc, conn);
		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}

	}
}
