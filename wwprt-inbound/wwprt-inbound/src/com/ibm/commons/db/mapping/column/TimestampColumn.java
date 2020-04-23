package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import com.ibm.commons.db.mapping.Column;

public class TimestampColumn implements ColumnType<Timestamp> {

	public void populate(Column<Timestamp> column, int index, PreparedStatement ps)
			throws SQLException {
		if (column.getValue() == null) {
			ps.setNull(index, Types.TIMESTAMP);
		} else {
			ps.setTimestamp(index, column.getValue());
		}
	}

}
