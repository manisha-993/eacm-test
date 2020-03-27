package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.PriceTable;

public class UpdateOverlappingPrices implements Update {
	
	static String SQL;
	private PriceTable price;
	private Date currentTime;
	private Date effectiveDate;
	
	public void prepare(PriceTable price, Date currentTime, Date effectiveDate) {
		this.price = price;
		this.currentTime = currentTime;
		this.effectiveDate = effectiveDate;
	}

	public String buildSql(Transaction transaction) {
		if (SQL == null) {
			SQL = "update " + Context.get().getPriceTable()
					+ " set ACTION='D', INSERT_TS=? "
					+ "where OFFERING=? and PRICE_POINT_TYPE=? and PRICE_POINT_VALUE=? "
					+ "and PRICE_TYPE=? and COUNTRY=? and ONSHORE=? and ACTION='I' " 
					+ "and END_DATE > ? and START_DATE < ? and END_DATE <> ? "
					+ "and INSERT_TS < ?";
		}
		return SQL;
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		Timestamp now = new Timestamp(currentTime.getTime());
		ps.setTimestamp(1, now);
		ps.setObject(2, price.getColumn("OFFERING").getValue());
		ps.setObject(3, price.getColumn("PRICE_POINT_TYPE").getValue());
		ps.setObject(4, price.getColumn("PRICE_POINT_VALUE").getValue());
		ps.setObject(5, price.getColumn("PRICE_TYPE").getValue());
		ps.setObject(6, price.getColumn("COUNTRY").getValue());
		ps.setObject(7, price.getColumn("ONSHORE").getValue());
		ps.setObject(8, price.getColumn("START_DATE").getValue());
		ps.setObject(9, price.getColumn("END_DATE").getValue());
		ps.setDate(10, new java.sql.Date(effectiveDate.getTime()));
		ps.setTimestamp(11, now);
	}

}
