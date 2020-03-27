package com.ibm.commons.db.transaction.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;

/**
 * Interface to create SQL Insert, Update or Delete
 * 
 * @author lucasrg
 *
 */
public interface Update {

	/**
	 * Build the SQL string
	 * @param transaction Current transaction
	 * @return The SQL to be executed
	 */
	String buildSql(Transaction transaction);
	
	/**
	 * Prepare the statement before executing the update
	 * 
	 * @param ps Statement being executed
	 * @throws SQLException
	 */	
	void prepareStatement(PreparedStatement ps) throws SQLException;
}
