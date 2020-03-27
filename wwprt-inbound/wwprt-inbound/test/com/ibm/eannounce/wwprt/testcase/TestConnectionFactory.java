package com.ibm.eannounce.wwprt.testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;

public class TestConnectionFactory implements ConnectionFactory {

	static final String DRIVER = "com.ibm.db2.jcc.DB2Driver";
	private String url;
	private String username;
	private String password;
	private Connection connection;

	public TestConnectionFactory(File propertiesFile) throws IOException {
		Properties properties = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(propertiesFile);
			properties.load(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		String server = properties.getProperty("server");
		String database = properties.getProperty("database");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		setup(server, database, username, password);
	}

	public TestConnectionFactory(Properties properties) {
		String server = properties.getProperty("server");
		String database = properties.getProperty("database");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		setup(server, database, username, password);
	}

	public TestConnectionFactory(String server, String database, String username, String password) {
		setup(server, database, username, password);
	}

	private void setup(String server, String database, String username, String password) {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("DB2 JDBC Driver not found!");
		}
		if (username == null) {
			throw new RuntimeException("DB Username cannot be null");
		}
		if (password == null) {
			throw new RuntimeException("DB Password cannot be null");
		}
		this.username = username;
		this.password = password;
		//URL example "jdbc:db2://localhost:50000/dbname"
		url = "jdbc:db2://" + server + "/" + database;
	}

	public Connection getConnection() throws SQLException {
		if (connection == null) {
			Properties connectionProps = new Properties();
			connectionProps.put("user", username);
			connectionProps.put("password", password);
			connection = DriverManager.getConnection(url, connectionProps);
			connection.setAutoCommit(false);
		}
		return connection;
	}

	public void close(Connection connection) throws SQLException {
		//Ignore
	}

	public void commit(Connection connection) throws SQLException {
		// Ignore
	}

	public void rollback(Connection connection) throws SQLException {
		// Ignore
	}

}
