package com.ibm.commons.db.mapping.source;

import java.sql.Timestamp;
import java.util.Date;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class ConstantTimestampSource implements ValueSource<Timestamp>{

	private final Date date;
	
	public ConstantTimestampSource(Date date) {
		this.date = date;
	}

	public Timestamp getValue() throws InvalidColumnException {
		return new Timestamp(date.getTime());
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
	}
}
