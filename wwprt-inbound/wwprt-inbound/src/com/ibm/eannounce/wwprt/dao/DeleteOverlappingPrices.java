package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.model.PriceTable;

public class DeleteOverlappingPrices implements Update {
	
	static String SQL;
	private PriceTable price;
	private Date fromTime;
	private Date effectiveDate;
	
	public void prepare(PriceTable price, Date fromTime, Date effectiveDate) {
		this.price = price;
		this.fromTime = fromTime;
		this.effectiveDate = effectiveDate;
	}

	public String buildSql(Transaction transaction) {
		if (SQL == null) {
			SQL = "delete from " + Context.get().getPriceTable()
			+ " where OFFERING=? and PRICE_POINT_TYPE=? and PRICE_POINT_VALUE=? "
			+ "and PRICE_TYPE=? and COUNTRY=? and ONSHORE=? and ACTION='I' " 
			+ "and END_DATE > ? and START_DATE < ? and END_DATE <> ? "
			+ "and INSERT_TS < ?";
		}
		return SQL;
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.setObject(1, price.getColumn("OFFERING").getValue());
		ps.setObject(2, price.getColumn("PRICE_POINT_TYPE").getValue());
		ps.setObject(3, price.getColumn("PRICE_POINT_VALUE").getValue());
		ps.setObject(4, price.getColumn("PRICE_TYPE").getValue());
		ps.setObject(5, price.getColumn("COUNTRY").getValue());
		ps.setObject(6, price.getColumn("ONSHORE").getValue());
		ps.setObject(7, price.getColumn("START_DATE").getValue());
		ps.setObject(8, price.getColumn("END_DATE").getValue());
		ps.setDate(9, new java.sql.Date(effectiveDate.getTime()));
		ps.setTimestamp(10, new Timestamp(fromTime.getTime()));
	}

}
