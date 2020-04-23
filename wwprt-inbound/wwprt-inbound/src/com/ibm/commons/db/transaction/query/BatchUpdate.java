package com.ibm.commons.db.transaction.query;

import com.ibm.commons.db.transaction.Transaction;

/**
 * Interface to create SQL to be executed in a batch
 * 
 * @author lucasrg
 *
 */
public interface BatchUpdate {

	/**
	 * Build the SQL string
	 * @param transaction Current transaction
	 * @return The SQL to be executed
	 */
	String buildSql(Transaction transaction);
	
}
