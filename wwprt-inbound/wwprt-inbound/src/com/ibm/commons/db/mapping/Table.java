package com.ibm.commons.db.mapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibm.commons.db.mapping.column.ColumnType;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.ValueSourceVisitor;

public class Table {

	private Map<String, Column<?>> columns = new LinkedHashMap<String, Column<?>>();
	private String tableName;
	private String insertSql;
	private String updateSql;
	private String deleteSql;
	
	public Table(){		
	}

	public Table(String tableName) {
		this.tableName = tableName;
	}

	public <T> void add(Column<T> column) {
		columns.put(column.getColumnName(), column);
	}

	public <T> void add(String columnName, ColumnType<T> columnType, ValueSource<T> value) {
		add(columnName, columnType, value, false, false);
	}

	public <T> void add(String columnName, ColumnType<T> columnType, ValueSource<T> value,
			boolean required) {
		add(columnName, columnType, value, required, false);
	}

	public <T> void add(String columnName, ColumnType<T> columnType, ValueSource<T> value,
			boolean required, boolean pk) {
		add(new Column<T>(columnName, columnType, value, required, pk));
	}

	public String buildInsertSql() {
		if (insertSql == null) {
			StringBuilder values = new StringBuilder();
			StringBuilder sb = new StringBuilder();
			sb.append("insert into " + tableName + "(");
			boolean first = true;
			for (Column<?> column : columns.values()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
					values.append(",");
				}
				sb.append(column.getColumnName());
				values.append("?");
			}
			sb.append(") values (");
			sb.append(values.toString());
			sb.append(")");
			insertSql = sb.toString();
		}
		return insertSql;
	}
	
	public String buildUpdateSql() {
		if (updateSql == null) {
			StringBuilder where = new StringBuilder();
			StringBuilder sb = new StringBuilder();
			sb.append("update " + tableName + " set ");
			boolean first = true;
			boolean firstWhere = true;
			for (Column<?> column : columns.values()) {
				if (column.isPk()) {
					if (firstWhere) {
						firstWhere = false;
					} else {
						where.append(" AND ");
					}
					where.append(column.getColumnName());
					where.append("=?");
				} else {
					if (first) {
						first = false;
					} else {
						sb.append(",");
					}
					sb.append(column.getColumnName());
					sb.append("=?");
				}
			}
			sb.append(" where ");
			sb.append(where.toString());
			updateSql = sb.toString();
		}
		return updateSql;
	}

	public String buildDeleteSql() {
		if (deleteSql == null) {
			StringBuilder where = new StringBuilder();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from ");
			sb.append(tableName);
			boolean firstWhere = true;
			for (Column<?> column : columns.values()) {
				if (column.isPk()) {
					if (firstWhere) {
						firstWhere = false;
					} else {
						where.append(" AND ");
					}
					where.append(column.getColumnName());
					where.append("=?");
				}
			}
			sb.append(" where ");
			sb.append(where.toString());
			deleteSql = sb.toString();
		}
		return deleteSql;
	}

	public void acceptVisitor(ValueSourceVisitor visitor) {
		for (Column<?> column : columns.values()) {
			if (column.getValueSource() != null) {
				column.getValueSource().accept(visitor);
			}
		}
	}

	public List<Column<?>> getColumns() {
		return new ArrayList<Column<?>>(columns.values());
	}
	
	public Column<?> getColumn(String name) {
		return columns.get(name);
	}

}
