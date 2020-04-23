package com.ibm.commons.db.transaction.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;

/**
 * Interface to define a SQL Query<br>
 * For Insert, Update and Delete use the {@link Update} interface
 * 
 * @author lucasrg
 */
public interface Query {
	
	/**
	 * Build the SQL string
	 * @param transaction Current transaction
	 * @return The SQL to be executed
	 */
	String buildSql(Transaction transaction);
	
	/**
	 * Prepare the statement before executing the query
	 * 
	 * @param ps Statement being executed
	 * @throws SQLException
	 */
	void prepareStatement(PreparedStatement ps) throws SQLException;
	
	/**
	 * Handle the query results
	 * 
	 * @param rs ResultSet returned from executed query
	 * @throws SQLException
	 */
	void handleResult(ResultSet rs) throws SQLException;
	
}
