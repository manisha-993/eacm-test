package com.ibm.eannounce.wwprt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.query.Update;

/**
 * Using {@link ClearTempPriceTable} now
 * @author lucasrg
 *
 */
@Deprecated
public class CreateTempPriceTable implements Update {

	public String buildSql(Transaction transaction) {
		return "";//Context.get().getTempPriceTableDefinition();
	}

	public void prepareStatement(PreparedStatement arg0) throws SQLException {
	}

}
