package com.ibm.commons.db.transaction.query.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;

/**
 * Simple implementation of the Update interface
 * @author lucasrg
 * 
 */
public class SimpleUpdate implements Update {

	private String sql;
	private final Object[] params;
	
	/**
	 * Creates a new simple update using the SQL string and it's parameters
	 * @param sql
	 * @param params Optional list of parameters in the same order as in the SQL.  
	 */
	public SimpleUpdate(String sql, Object ... params) {
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
