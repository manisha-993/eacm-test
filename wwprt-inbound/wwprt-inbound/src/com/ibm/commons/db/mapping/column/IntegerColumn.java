package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public class IntegerColumn implements ColumnType<Integer> {


	public void populate(Column<Integer> column, int index, PreparedStatement ps) throws SQLException {
		if (column.getValue() == null) {
			ps.setInt(index, 0);
		} else {
			ps.setInt(index, column.getValue());
		}
	}

}
