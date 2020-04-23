package com.ibm.commons.db.mapping.column;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.mapping.Column;

public class ClobColumn implements ColumnType<String> {

	public void populate(Column<String> column, int index, PreparedStatement ps)
			throws SQLException {
		String data = column.getValue();
		ps.setCharacterStream(index, new StringReader(data), data.length());
	}

}
