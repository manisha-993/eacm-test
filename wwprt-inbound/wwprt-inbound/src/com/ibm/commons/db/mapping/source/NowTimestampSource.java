package com.ibm.commons.db.mapping.source;

import java.sql.Timestamp;
import java.util.Date;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class NowTimestampSource implements ValueSource<Timestamp>{

	public Timestamp getValue() throws InvalidColumnException {
		return new Timestamp(new Date().getTime());
	}
	
	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
	}
	
}
