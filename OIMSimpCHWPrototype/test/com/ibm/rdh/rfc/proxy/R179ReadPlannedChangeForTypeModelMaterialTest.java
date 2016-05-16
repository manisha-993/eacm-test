package com.ibm.rdh.rfc.proxy;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;

public class R179ReadPlannedChangeForTypeModelMaterialTest extends
		RdhRestProxyTest {
	private static Logger logger = LogManager.getLogManager()
			.getPromoteLogger();

	@Before
	public void prepareData() {
		String sql_mvke = "insert into sapr3.mvke (MATNR,MANDT, VKORG, VTWEG,VMSTD) values('EACM','200','0147','00','00000000')";
		int t1 = SqlHelper.runUpdateSql(sql_mvke, conn);
		
		if (t1 >= 0) {
			System.out.println("insert success");
		} else {
			System.out.println("insert failed");
		}
	}

	@Test
	public void r179() {
		try {

			String pimsIdentity = "C";
			String typemod = "EACM";
			String salesOrg = "0147";
			String annDocNo = "123401";
			String check = "wdfm";

			RdhRestProxy rfcProxy = new RdhRestProxy();
			Vector Vecplannedsales = new Vector();

			Vecplannedsales = rfcProxy.r179(typemod, annDocNo, check,
					pimsIdentity, salesOrg);
			PlannedSalesStatus ps = (PlannedSalesStatus) Vecplannedsales.get(0);
			assertEquals("EACM",ps.getMatnr());
			assertEquals("0147", ps.getVkorg());
		
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
		String del_klah = "delete from sapr3.mvke where mandt='200' and matnr='EACM' and vkorg='0147' and vtweg='00' and VMSTD='00000000'";
		int t1 = SqlHelper.runUpdateSql(del_klah, conn);

		if (t1 >= 0) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failed");
		}
	}

}
