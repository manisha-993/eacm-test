package com.ibm.commons.db.transaction.query.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Query;

/**
 * Simple implementation of a Query interface.
 * 
 * @author lucasrg
 *
 */
public abstract class SimpleQuery implements Query {

	private final String sql;
	
	private final Object[] params;
	
	/**
	 * Creates a new simple query using the SQL string and it's parameters
	 * @param sql
	 * @param params Optional list of parameters in the same order as in the SQL.  
	 */
	public SimpleQuery(String sql, Object ... params) {
		this.sql = sql;
		this.params = params;
	}

	public String buildSql(Transaction transaction) {
		return sql;
	}

	public void prepareStatement(PreparedStatement ps) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i+1, params[i]);
		}
	}

}
