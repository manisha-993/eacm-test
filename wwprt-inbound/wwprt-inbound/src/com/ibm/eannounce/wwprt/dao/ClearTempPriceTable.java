package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;

public class ClearTempPriceTable implements Update {

	public String buildSql(Transaction transaction) {
		//Defect 745902 WWPRT Inbound IDL test - SQLCode - 1477
		/**
		 * The error SQL1477N means that the specified object cannot be accessed. 
		 * An attempt was made to access a table that is not accessible
		 * Not use alter table to empty the table but use delete to empty the table
		 */
		//return "ALTER TABLE "+Context.get().getTempPriceTable()+" ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE";
		return "DELETE FROM "+Context.get().getTempPriceTable()+" WITH UR";
	}

	public void prepareStatement(PreparedStatement arg0) throws SQLException {
	}

}
