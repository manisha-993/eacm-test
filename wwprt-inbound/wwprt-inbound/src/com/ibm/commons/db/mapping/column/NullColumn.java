package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public class NullColumn implements ColumnType<Void> {

	private final int sqlType;

	public NullColumn(int sqlType) {
		this.sqlType = sqlType;
	}

	public void populate(Column<Void> column, int index, PreparedStatement ps) throws SQLException {
		ps.setNull(index, sqlType);
	}

}
