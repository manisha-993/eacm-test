package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.ibm.commons.db.mapping.Column;
import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;
import com.ibm.eannounce.wwprt.Context;

public class MergeTablesUpdate implements Update {

	private List<Column<?>> columns;
	private String tableName;
	private String sql;
	private String tempTableName;
	
	

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public MergeTablesUpdate(List<Column<?>> columns) {
		this.columns = columns;
		this.tableName = Context.get().getPriceTable();
		this.tempTableName = Context.get().getTempPriceTable();
	}

	public String buildSql(Transaction transaction) {
		if (sql == null) {
			StringBuilder select = new StringBuilder();
			StringBuilder on = new StringBuilder();
			boolean first = true;
			for (Column<?> attr : columns) {
				if (attr.isPk()) {
					if (first) {
						first = false;
					} else {
						select.append(", ");
						on.append(" and ");
					}
					select.append(attr.getColumnName());
					on.append("T1."+attr.getColumnName());
					on.append("=");
					on.append("T2." + attr.getColumnName());
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("MERGE INTO ");
			sb.append(tableName);
			sb.append(" T1 USING ( SELECT * FROM "+tempTableName);
			sb.append(") T2 ON (");
			sb.append(on);
			sb.append(") ");
			sb.append("WHEN MATCHED THEN " + buildUpdateSql());
			sb.append(" WHEN NOT MATCHED THEN "+ buildInsertSql());
			sql = sb.toString();
		}
		return sql;
	}

	private String buildInsertSql() {
		StringBuilder values = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		sb.append("insert (");
		boolean first = true;
		for (Column<?> attribute : columns) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
				values.append(",");
			}
			sb.append(attribute.getColumnName());
			values.append("T2."+attribute.getColumnName());
		}
		sb.append(") values (");
		sb.append(values.toString());
		sb.append(")");
		return sb.toString();
	}

	private String buildUpdateSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("update set ");
		boolean first = true;
		for (Column<?> attribute : columns) {
			if (!attribute.isPk()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append(attribute.getColumnName());
				sb.append("=T2."+attribute.getColumnName());
			}
		}
		return sb.toString();
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
	}
}
