package com.ibm.eannounce.wwprt.dao;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.Prices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaxPricesIDQuery implements Query {

	private String sql;
	
	public int id;

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			sql = "SELECT max(ID) from " + Context.get().getWWPRTXMLTable();
		}
		return sql;
	}

	public void handleResult(ResultSet rs) throws SQLException {
		id = 0;
		if (rs.next()) {
			id = rs.getInt(1);
		}
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {

	}

}
