package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.dao.ClearTempPriceTable;

public class ClearTempPriceTestCase {

	@Test
	public void testClearTempPrice() throws Exception {
		Connection connection = null;
		try {
			FileInputStream in = new FileInputStream(
					new File(TestUtils.PROPERTIES));
			Properties properties = new Properties();
			properties.load(in);
			in.close();
			ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);
			connection = connectionFactory.getConnection();
			Transaction transaction = new Transaction(connection);
			Context.get().setupFromProperties(properties);
			transaction.executeQuery(new DebugQuery(Context.get().getTempPriceTable()));
			transaction.executeUpdate(new ClearTempPriceTable());
			
			transaction.executeQuery(new DebugQuery(Context.get().getTempPriceTable()));
			transaction.executeUpdate(new ClearTempPriceTable());
			
			transaction.executeQuery(new DebugQuery(Context.get().getTempPriceTable()));
			transaction.executeUpdate(new ClearTempPriceTable());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.rollback();
				connection.close();
			}
		}
	}
	
	class DebugQuery implements Query {
		
		String table;
		
		public DebugQuery(String table) {
			this.table = table;
		}

		public String buildSql(Transaction transaction) {
			return "select * from " + table;
		}

		public void handleResult(ResultSet rs) throws SQLException {
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println("");
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnName = rsmd.getColumnName(i);
				System.out.print(columnName);
			}
			System.out.println("");
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
				}
				System.out.println("");
			}
		}

		public void prepareStatement(PreparedStatement ps) throws SQLException {
		}

	};
}
