package com.ibm.rdh.rfc.proxy;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;

public class R209ReadBasicViewOfMaterialTest extends RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_mara = "insert into sapr3.mara (mandt,matnr) values ('200','EACMR2N')";
		String sql_makt = "insert into sapr3.makt (mandt,matnr,spras) values ('200','EACMR2N','E')";
		int t1 = SqlHelper.runUpdateSql(sql_mara, conn);
		int t2 = SqlHelper.runUpdateSql(sql_makt, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void testR209QueryFound() {
		try {
			String material = "EACMR2N";
			RdhRestProxy rfcProxy = new RdhRestProxy();
			rfcProxy.r209(material);

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
		String del_mara = "delete from sapr3.mara where mandt='200' and matnr='EACMR2N'";
		String del_makt = "delete from sapr3.makt where mandt='200' and matnr='EACMR2N' and spras='E'";
		int t1 = SqlHelper.runUpdateSql(del_mara, conn);
		int t2 = SqlHelper.runUpdateSql(del_makt, conn);
		if (t1 >= 0 && t2 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
