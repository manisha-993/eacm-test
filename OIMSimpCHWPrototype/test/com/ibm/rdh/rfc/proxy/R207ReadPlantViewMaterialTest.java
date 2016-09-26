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
		String sql_marc_1 = "insert into SAPR3.MARC (MANDT,MATNR,WERKS) values('200','1234NEW','1212')";
		String sql_marc_2 = "insert into SAPR3.MARC (MANDT,MATNR,WERKS) values('200','1234UPG','1212')";
		int t1 = SqlHelper.runUpdateSql(sql_marc_1, conn);
		int t2 = SqlHelper.runUpdateSql(sql_marc_2, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r207() {
		try {
			String type = "1234";
			String plant = "1212";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r207(type, plant);

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
		String del_marc_1 = "delete from SAPR3.MARC where MANDT='200'and MATNR='1234NEW' and WERKS='1212'";
		String del_marc_2 = "delete from SAPR3.MARC where MANDT='200'and MATNR='1234UPG' and WERKS='1212'";
		int t1 = SqlHelper.runUpdateSql(del_marc_1, conn);
		int t2 = SqlHelper.runUpdateSql(del_marc_2, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}

	}
}
