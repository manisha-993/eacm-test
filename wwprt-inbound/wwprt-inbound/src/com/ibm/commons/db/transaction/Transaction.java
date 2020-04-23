package com.ibm.commons.db.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import com.ibm.commons.db.transaction.query.BatchUpdate;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.commons.db.transaction.query.Update;


/**
 * Transaction encapsulates the entire process of accessing the database:<br>
 * <ul>
 * <li>1. Open a new connection when required</li>
 * <li>2. Create and populate the Statement object</li>
 * <li>3. Execute and handle the Statement result</li>
 * </ul>
 * 
 * @author lucasrg
 */
public final class Transaction {

	/**
	 * Current connection
	 */
	private Connection connection;
	
	/**
	 * Create a new Transaction using the connection
	 * @param connection to use
	 */
	public Transaction(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Execute a {@link Query} and close the connection if keepConnectionAlive is disabled.
	 * @param query Select Query
	 * @throws SQLException Standard SQL Exception 
	 */
	public void executeQuery(Query query) throws SQLException {
			String sql = removeLineFeed(query.buildSql(this));
		PreparedStatement ps = connection.prepareStatement(sql);
		try {
			query.prepareStatement(ps);
			ResultSet rs = ps.executeQuery();
			try {
				query.handleResult(rs);
			} finally {
				rs.close();
			}

		} finally {
			ps.close();
			}
	}
	
	/**
	 * Execute a {@link Update} and close the connection if keepConnectionAlive is disabled.
	 * @param command Insert/Update/Delete command
	 * @throws SQLException Standard SQL Exception 
	 */
	public int executeUpdate(Update command) throws SQLException {
			String sql = removeLineFeed(command.buildSql(this));
		PreparedStatement ps = connection.prepareStatement(sql);
		try {
			command.prepareStatement(ps);
			return ps.executeUpdate();
		} finally {
			ps.close();
		}
	}
	
	/**
	 * Execute a list of Insert/Update/Delete commands in a single call
	 * @param commands list of BatchUpdate commands
	 * @return Statement results. See {@link Statement} executeBatch() method.
	 * @throws SQLException Standard SQL Exception
	 */
	public int[] executeBatch(Collection<BatchUpdate> commands) throws SQLException {
			Statement statement = connection.createStatement();
		for (BatchUpdate command : commands) {
			String sql = removeLineFeed(command.buildSql(this));
			statement.addBatch(sql);
		}
		try {
			return statement.executeBatch();
		} finally {
			statement.close();
		}
	}

	/**
	 * Removes line feed characters from the SQL command
	 * @param sql SQL String
	 * @return SQL command without line feeds
	 */
	private String removeLineFeed(String sql) {
		return sql == null ? null : sql.replaceAll("(\r\n|\r|\n|\n\r)", " ");
	}

	public Connection getConnection() {
		return connection;
	}
}
