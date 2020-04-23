package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public class DoubleColumn implements ColumnType<Double> {


	public void populate(Column<Double> column, int index, PreparedStatement ps) throws SQLException {
		if (column.getValue() == null) {
			ps.setDouble(index, 0);
		} else {
			ps.setDouble(index, column.getValue());
		}
	}

}
