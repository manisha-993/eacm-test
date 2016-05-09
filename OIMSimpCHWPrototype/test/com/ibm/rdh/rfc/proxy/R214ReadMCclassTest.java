package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R214ReadMCclassTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_klah = "insert into sapr3.klah (mandt,klart,class,clint) values ('200','300','MK_EACM_MC','0000000003')";
		int t1 = SqlHelper.runUpdateSql(sql_klah, conn);
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR214() {
		try {
			String type = "EACM";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			Boolean ba = rfcProxy.r214(type);
			assertEquals(true, ba);
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
		String del_klah = "delete from sapr3.klah where mandt='200' and klart='300' and class='MK_EACM_MC' and clint='0000000003'";
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);
		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
