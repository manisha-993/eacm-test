package com.ibm.eannounce.wwprt.dao;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.Prices;

public class InsertPricesXML implements Update {

	private final String id;
	private final String data;
	private String sql;

	public InsertPricesXML(String id, String data) {
		this.id = id;
		this.data = data;
	}

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			sql = "insert into "+
			Context.get().getWWPRTXMLTable()
			+" (INSERT_TS, ID, STATUS, PRICEXML) values (?,?,?,?)";
		}
		return sql;
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.setTimestamp(1, new Timestamp(new Date().getTime()));
		ps.setString(2, id);
		ps.setString(3, Prices.STATUS_NEW); //Always insert as 'New'
		ps.setCharacterStream(4, new StringReader(data), data.length());
	}

}
