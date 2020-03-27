package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.Prices;

public class CountNewPricesXMLQuery implements Query {

	private String sql;
	
	public int result;

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			sql = "SELECT count(ID) from " + Context.get().getWWPRTXMLTable()
					+ " WHERE STATUS = ?";
		}
		return sql;
	}

	public void handleResult(ResultSet rs) throws SQLException {
		result = 0;
		if (rs.next()) {
			result = rs.getInt(1);
		}
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.setString(1, Prices.STATUS_NEW);
	}

}
