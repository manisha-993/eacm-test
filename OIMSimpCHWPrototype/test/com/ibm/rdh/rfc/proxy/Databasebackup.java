package com.ibm.rdh.rfc.proxy;

import org.junit.Test;

public class Databasebackup extends RdhRestProxyTest {

	String From_MANDT = "300";

	public int ZDM_GEO_TO_CLASS() {
		String sql_delete = "delete from SAPR3.ZDM_GEO_TO_CLASS where mandt='200'";
		String sql_insert = "insert into SAPR3.ZDM_GEO_TO_CLASS select '200', Z_GEO, Z_CLASS, Z_PIMS_ID from "
				+ "SAPR3.ZDM_GEO_TO_CLASS where mandt='" + From_MANDT + "'";
		int t1 = SqlHelper.runUpdateSql(sql_delete, conn);
		int t2 = SqlHelper.runUpdateSql(sql_insert, conn);
		if (t1 >= 0 && t2 >= 0) {
			return 0;
		} else {
			return -1;
		}
	}

	public int ZDM_DISTRIBUTION() {
		String sql_delete = "delete from SAPR3.ZDM_DISTRIBUTION where mandt='200'";
		String sql_insert = "insert into SAPR3.ZDM_DISTRIBUTION select '200', Z_CLASS, Z_ACTIVITY, Z_KZKFG,"
				+ " Z_MESTYP, Z_OBJTYP, Z_TEXT, Z_SOURCE, ZDM_SEND_ZDMCLF from "
				+ "SAPR3.ZDM_DISTRIBUTION where mandt='" + From_MANDT + "'";
		int t1 = SqlHelper.runUpdateSql(sql_delete, conn);
		int t2 = SqlHelper.runUpdateSql(sql_insert, conn);
		if (t1 >= 0 && t2 >= 0) {
			return 0;
		} else {
			return -1;
		}
	}

	public int ZDM_RDXCUSTMODEL() {
		String sql_delete = "delete from SAPR3.ZDM_RDXCUSTMODEL where mandt='200'";
		String sql_insert = "insert into SAPR3.ZDM_RDXCUSTMODEL select '200', ZDM_CLASS, RCVSYSTEM, ZDM_SYSTEM_TYPE,"
				+ " ZDM_SYST_DEFAULT, ZDM_CLASS_CHECK, ZDM_HST_CHECK, ZDM_BJOBS_FLAG, ZDM_ISEND_EVENT, ZDM_IPOST_EVENT, ZDM_PRICE_EVENT, "
				+ " ZDM_CREATE_DATE, ZDM_CREATE_TIME, ZDM_CREATE_USER, ZDM_UPDATE_DATE, ZDM_UPDATE_TIME, ZDM_UPDATE_USER, ZDM_SEND_ZDMCLF from "
				+ "SAPR3.ZDM_RDXCUSTMODEL where mandt='" + From_MANDT + "'";
		int t1 = SqlHelper.runUpdateSql(sql_delete, conn);
		int t2 = SqlHelper.runUpdateSql(sql_insert, conn);
		if (t1 >= 0 && t2 >= 0) {
			return 0;
		} else {
			return -1;
		}
	}

	public int ZDM_MULTICAST() {
		String sql_delete = "delete from SAPR3.ZDM_MULTICAST where mandt='200'";
		String sql_insert = "insert into SAPR3.ZDM_MULTICAST select '200', SNDSYSTEM,RCVSYSTEM,MESTYP,ZDM_EVENT,ZDM_ACTIVE_FLAG,ZDM_CREATE_DATE,ZDM_CREATE_TIME,ZDM_CREATE_USER,ZDM_UPDATE_DATE,ZDM_UPDATE_TIME,ZDM_UPDATE_USER from SAPR3.ZDM_MULTICAST where mandt='"
				+ From_MANDT + "'";
		int t1 = SqlHelper.runUpdateSql(sql_delete, conn);
		int t2 = SqlHelper.runUpdateSql(sql_insert, conn);
		if (t1 >= 0 && t2 >= 0) {
			return 0;
		} else {
			return -1;
		}
	}

	public int ZDM_DUPL_FILTER() {
		String sql_delete = "delete from SAPR3.ZDM_DUPL_FILTER where mandt='200'";
		String sql_insert = "insert into SAPR3.ZDM_DUPL_FILTER select '200', RCVSYSTEM, MESTYP, FOBJTYPE, FOBJVALUE, ZDM_UPDATE_DATE,  ZDM_UPDATE_TIME,ZDM_UPDATE_USER from SAPR3.ZDM_DUPL_FILTER where mandt='"
				+ From_MANDT + "'";
		int t1 = SqlHelper.runUpdateSql(sql_delete, conn);
		int t2 = SqlHelper.runUpdateSql(sql_insert, conn);
		if (t1 >= 0 && t2 >= 0) {
			return 0;
		} else {
			return -1;
		}
	}

	@Test
	public void reset() {

		int t1 = ZDM_GEO_TO_CLASS();
		int t2 = ZDM_DISTRIBUTION();
		int t3 = ZDM_RDXCUSTMODEL();
		int t4 = ZDM_MULTICAST();
		int t5 = ZDM_DUPL_FILTER();

		System.out.println(t1 + "," + t2 + "," + t3 + "," + t4 + "," + t5);

	}
}
