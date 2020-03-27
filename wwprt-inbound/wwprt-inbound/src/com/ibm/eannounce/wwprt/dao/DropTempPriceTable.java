package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;

/**
 * Using {@link ClearTempPriceTable} now
 * @author lucasrg
 *
 */
@Deprecated
public class DropTempPriceTable implements Update {

	public String buildSql(Transaction transaction) {
		return "drop table "+Context.get().getTempPriceTable();
	}

	public void prepareStatement(PreparedStatement arg0) throws SQLException {
	}

}
