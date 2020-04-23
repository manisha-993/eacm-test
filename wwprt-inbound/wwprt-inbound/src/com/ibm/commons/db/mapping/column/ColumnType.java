package com.ibm.commons.db.mapping.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public interface ColumnType<T> {

	void populate(Column<T> column, int index, PreparedStatement ps) throws SQLException;
	
}
