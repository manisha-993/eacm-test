package com.ibm.commons.db.transaction.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

	Connection getConnection() throws SQLException;

	void commit(Connection connection) throws SQLException;

	void close(Connection connection) throws SQLException;

	void rollback(Connection connection) throws SQLException;
	
}
