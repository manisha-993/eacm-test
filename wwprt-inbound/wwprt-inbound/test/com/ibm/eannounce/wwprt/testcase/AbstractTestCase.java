package com.ibm.eannounce.wwprt.testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.catcher.Catcher;
import com.ibm.eannounce.wwprt.processor.Processor;
import com.ibm.eannounce.wwprt.test.TestUtils;
import com.ibm.mq.MQMessage;

public class AbstractTestCase {

	Catcher catcher;
	Processor processor;
	private Connection connection;
	
	@BeforeClass
	public static void beforeClass() {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger()
					.severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		
	}

	@Before
	public void before() throws IOException, SQLException {
		Properties properties = new Properties();
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new TestConnectionFactory(properties);
		connection = connectionFactory.getConnection();
		Context.get().setConnectionFactory(connectionFactory);
		Context.get().setupFromProperties(properties);

		catcher = new Catcher();
		processor = new Processor();
		processor.initialize();
	}
	
	public void process(String filename) throws Exception {
		System.err.println();
		System.err.println("--- Processing "+filename);
		MQMessage message = new MQMessage();
		InputStream is = new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		try {
			int n;
			while ((n = is.read(buffer)) != -1) {
				message.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		message.seek(0);
		catcher.readMessage(message);
		processor.execute();
	}

	public void printPriceTable() throws SQLException {
		Transaction transaction = new Transaction(connection);
		transaction.executeQuery(new DebugQuery(Context.get().getPriceTable()));	
	}
	
	@After
	public void after() throws SQLException {
		if (connection != null) {
			connection.rollback();
			connection.close();
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
			System.err.println("");
			int numberOfColumns = 14;//rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1)
					System.err.print(",  ");
				String columnName = rsmd.getColumnName(i);
				System.err.print(columnName);
			}
			System.err.println("");
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						System.err.print(",  ");
					String columnValue = rs.getString(i);
					if (columnValue != null && columnValue.startsWith("<price")) {
						System.err.print("[XML]");
					} else {
						System.err.print(columnValue);
					}
				}
				System.err.println("");
			}
		}

		public void prepareStatement(PreparedStatement ps) throws SQLException {
		}

	};
}
