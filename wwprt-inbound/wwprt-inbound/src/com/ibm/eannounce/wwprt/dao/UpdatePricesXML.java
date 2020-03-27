package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.Prices;

public class UpdatePricesXML implements Update {

	private String sql;
	private final String id;
	private final String message;
	private final String status;

	public UpdatePricesXML(String id, String status, String errorMessage) {
		this.id = id;
		this.status = status;
		this.message = errorMessage;
	}

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			sql = "update "+Context.get().getWWPRTXMLTable()+" set STATUS=?, " +
					"ERROR_MESSAGE=? where ID = ? and STATUS =?";
		}
		return sql;
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.setString(1, status);
		if (message != null && message.length() > 32000) {
			//Truncate long error message
			ps.setString(2, message.substring(0, 32000));
		} else {
			ps.setString(2, message);
		}
		ps.setString(3, id);
		ps.setString(4, Prices.STATUS_NEW);
	}

}
