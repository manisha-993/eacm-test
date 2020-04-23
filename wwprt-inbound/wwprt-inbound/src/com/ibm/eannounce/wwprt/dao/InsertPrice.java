package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;
import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.model.PriceTable;

public class InsertPrice implements Update {

	public enum UpdateMode {
		INSERT, UPDATE, DELETE
	}

	private String sql;
	private PriceTable table;

	public void prepare(PriceTable table) {
		this.table = table;
		if (sql == null) {
			sql = table.buildInsertSql();
		}
	}

	public String buildSql(Transaction transaction) {
		return sql;
	}

	@SuppressWarnings("unchecked")
	public void prepareStatement(PreparedStatement ps) throws SQLException {
		int index = 1;
		for (Column column : table.getColumns()) {
			column.getColumnType().populate(column, index, ps);
			index++;
		}
	}

}
