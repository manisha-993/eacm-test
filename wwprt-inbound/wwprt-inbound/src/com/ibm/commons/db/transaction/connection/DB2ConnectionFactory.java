package com.ibm.commons.db.transaction.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.eacm.AES256Utils;

public class DB2ConnectionFactory implements ConnectionFactory {

	static final String DRIVER = "com.ibm.db2.jcc.DB2Driver";
	private String url;
	private String username;
	private String password;

	public DB2ConnectionFactory(File propertiesFile) throws IOException {
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
	
	public DB2ConnectionFactory(Properties properties) {
		String server = properties.getProperty("server");
		String database = properties.getProperty("database");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		setup(server, database, username, password);
	}
	
	public DB2ConnectionFactory(String server, String database, String username, String password) {
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
		try {
			this.password = AES256Utils.decrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//URL example "jdbc:db2://localhost:50000/dbname"
		url = "jdbc:db2://"+server+"/"+database;
	}
	
	public Connection getConnection() throws SQLException {
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", username);
	    connectionProps.put("password", password);
	    Connection connection = DriverManager.getConnection(url, connectionProps);
	    connection.setAutoCommit(false);
	    return connection;
	}

	public void close(Connection connection) throws SQLException {
		connection.close();
	}

	public void commit(Connection connection) throws SQLException {
		connection.commit();
	}

	public void rollback(Connection connection) throws SQLException {
		connection.rollback();
	}

}
