package com.ibm.commons.db.mapping;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;

public class TableUpdate implements Update {

	public enum UpdateMode {
		INSERT, UPDATE, DELETE
	}

	private final UpdateMode updateMode;
	private final String sql;
	private final Table table;
	
	public TableUpdate(Table table, UpdateMode updateMode) {
		this.table = table;
		this.updateMode = updateMode;
		if (updateMode == UpdateMode.INSERT) {
			sql = table.buildInsertSql();
		} else if (updateMode == UpdateMode.UPDATE) {
			sql = table.buildUpdateSql();
		} else if (updateMode == UpdateMode.DELETE) {
			sql = table.buildDeleteSql();
		} else {
			throw new IllegalStateException("UpdateMode is not correct: "+updateMode);
		}
	}

	public String buildSql(Transaction transaction) {
		return sql;
	}

	@SuppressWarnings("unchecked")
	public void prepareStatement(PreparedStatement ps) throws SQLException {
		int index = 1;
		if (updateMode == UpdateMode.INSERT) {
			for (Column column : table.getColumns()) {
				column.getColumnType().populate(column, index, ps);
				index++;
			}
		} else if (updateMode == UpdateMode.UPDATE) {
			for (Column column : table.getColumns()) {
				if (!column.isPk()) {
					column.getColumnType().populate(column, index, ps);
					index++;
				}
			}
			for (Column column : table.getColumns()) {
				if (column.isPk()) {
					column.getColumnType().populate(column, index, ps);
					index++;
				}
			}
		} else if (updateMode == UpdateMode.DELETE) {
			for (Column column : table.getColumns()) {
				if (column.isPk()) {
					column.getColumnType().populate(column, index, ps);
					index++;
				}
			}
		}
			
	}
	
}
