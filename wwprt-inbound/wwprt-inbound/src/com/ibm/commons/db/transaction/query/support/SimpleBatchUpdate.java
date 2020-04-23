package com.ibm.commons.db.transaction.query.support;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.BatchUpdate;

/**
 * Simple implementation of the BatchUpdate interface
 * 
 * @author lucasrg
 * 
 */
public class SimpleBatchUpdate implements BatchUpdate {

	private String sql;

	/**
	 * Creates a new simple batch update using the SQL string
	 * 
	 * @param sql Update, insert or delete SQL
	 */
	public SimpleBatchUpdate(String sql) {
		this.sql = sql;
	}

	public String buildSql(Transaction transaction) {
		return sql;
	}

}
