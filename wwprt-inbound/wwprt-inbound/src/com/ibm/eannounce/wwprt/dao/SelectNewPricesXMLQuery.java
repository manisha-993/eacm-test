package com.ibm.eannounce.wwprt.dao;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.Prices;

public abstract class SelectNewPricesXMLQuery implements Query {

	private String sql;

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			sql = "SELECT ID, PRICEXML, INSERT_TS from " + Context.get().getWWPRTXMLTable()
					+ " WHERE STATUS = ? ORDER BY INSERT_TS FETCH FIRST 10 ROWS ONLY";
		}
		return sql;
	}

	public void handleResult(ResultSet rs) throws SQLException {
		while (rs.next()) {
			String pricesId = rs.getString(1);
			try {
				String xml = rs.getString(2);
				DocumentBuilder builder = Context.get().createDocumentBuilder();
				Document result = builder.parse(new InputSource(new StringReader(xml)));
				handleDocument(pricesId, result);
			} catch (Exception e) {
				handleParseError(pricesId, e);
			}
		}
	}

	protected abstract void handleDocument(String pricesId, Document document);

	protected abstract void handleParseError(String pricesId, Exception e);

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.setString(1, Prices.STATUS_NEW);
	}

}
