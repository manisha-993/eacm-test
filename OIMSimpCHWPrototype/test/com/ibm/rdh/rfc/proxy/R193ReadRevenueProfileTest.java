package com.ibm.rdh.rfc.proxy;

import java.math.BigInteger;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.RevData;

public class R193ReadRevenueProfileTest extends RdhRestProxyTest {

	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_mast = "insert into sapr3.mast(mandt,matnr,werks,stlan,stlnr,stlal) values ('200','EACMNEW','1222','Y','10000001','10')";
		String sql_stko = "insert into sapr3.stko(mandt,stlnr,stlty,stlal,stkoz) values ('200','10000001','M','10','10000000')";
		String sql_stzu = "insert into sapr3.stzu(mandt,stlnr,stlty) values ('200','10000001','M')";
		String sql_stpo = "insert into sapr3.stpo(mandt,stlnr,stlty,stlkn,stpoz) values ('200','10000001','M','10000000','10000001')";

		int t1 = SqlHelper.runUpdateSql(sql_mast, conn);
		int t2 = SqlHelper.runUpdateSql(sql_stko, conn);
		int t3 = SqlHelper.runUpdateSql(sql_stzu, conn);
		int t4 = SqlHelper.runUpdateSql(sql_stpo, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r193() {
		try {
			String type = "EACM";
			String newFlag = "NEW";
			String _plant = "1222";

			Vector revProfileData = new Vector();
			RdhRestProxy rfcProxy = new RdhRestProxy();
			revProfileData = rfcProxy.r193(type, newFlag, _plant);
			RevData revData = (RevData) revProfileData.get(0);
			BigInteger itemNode = new BigInteger("10000000");
			BigInteger itemCount = new BigInteger("10000001");
			assertEquals(itemNode, revData.getItem_Node());
			assertEquals(itemCount, revData.getItem_Count());

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
		String delete_mast = "delete from sapr3.mast where mandt='200' and matnr='EACMNEW' and werks='1222' and stlan='Y' and stlnr='10000001' and stlal='10'";
		String delete_stko = "delete from sapr3.stko where mandt='200' and stlnr='10000001' and stlty='M' and stlal='10' and stkoz='10000000'";
		String delete_stzu = "delete from sapr3.stzu where mandt='200' and stlnr='10000001' and stlty='M'";
		String delete_stpo = "delete from sapr3.stpo where mandt='200' and stlnr='10000001' and stlty='M' and stlkn='10000000' and stpoz='10000001'";

		int t1 = SqlHelper.runUpdateSql(delete_mast, conn);
		int t2 = SqlHelper.runUpdateSql(delete_stko, conn);
		int t3 = SqlHelper.runUpdateSql(delete_stzu, conn);
		int t4 = SqlHelper.runUpdateSql(delete_stpo, conn);

		if (t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}
}
