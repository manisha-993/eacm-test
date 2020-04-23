package com.ibm.commons.db.mapping.column;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.ibm.commons.db.mapping.Column;

public class DateColumn implements ColumnType<Date> {

	public void populate(Column<Date> column, int index, PreparedStatement ps) throws SQLException {
		if (column.getValue() == null) {
			ps.setNull(index, Types.DATE);
		} else {
			ps.setDate(index, column.getValue());
		}
	}

}
