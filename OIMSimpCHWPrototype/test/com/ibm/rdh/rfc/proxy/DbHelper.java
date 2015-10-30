package com.ibm.rdh.rfc.proxy;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.LogManager;


public class DbHelper {
	private static Logger logger =  LogManager.getLogManager().getPromoteLogger();
	
	public static boolean insertMARA(Connection conn,String MATNR){
		String insert_mara_sql = " insert into SAPR3.MARA(MANDT,MATNR,ERSDA,ERNAM,LAEDA,AENAM,VPSTA,PSTAT,MTART,ZEINR,ZEIAR,SPART) "
	               + " values('"+Constants.MANDT+"','"+MATNR+"','20150512','SDPI','20150511','SDPI','K','K','ZMAT','RFA002','RFA','2H')";		
		int t = SqlHelper.runUpdateSql(insert_mara_sql, conn);
		if(t>=0) return true;
		else return false;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("test");
	}

}
