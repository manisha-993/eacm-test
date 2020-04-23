package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public class StringColumn implements ColumnType<String> {

	public void populate(Column<String> column, int index, PreparedStatement ps)
			throws SQLException {
		ps.setString(index, column.getValue());
	}

}
