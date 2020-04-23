package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.commons.db.transaction.query.support.SimpleQuery;

public class DatabaseTestCase {

	@Test
	public void testConnection() throws SQLException, IOException {
		ConnectionFactory connectionFactory = new DB2ConnectionFactory(new File(
				TestUtils.PROPERTIES));
		Connection connection = connectionFactory.getConnection();
		Transaction transaction = new Transaction(connection);
		transaction.executeQuery(new SimpleQuery("select * from testcase.wwprtxml") {
			public void handleResult(ResultSet rs) throws SQLException {
				System.out.println("WORKED");
			}
		});
		connection.rollback();
		connection.close();
	}

}
