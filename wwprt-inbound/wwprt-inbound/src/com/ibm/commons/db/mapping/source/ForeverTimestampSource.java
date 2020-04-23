package com.ibm.commons.db.mapping.source;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.ibm.commons.db.mapping.InvalidColumnException;
import com.ibm.eannounce.wwprt.Context;

public class ForeverTimestampSource implements ValueSource<Timestamp> {

	public Timestamp getValue() throws InvalidColumnException {
		try {
			Date date = Context.XML_TIMESTAMP_FORMAT
					.parse("9999-12-31T00:00:00.000");
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new InvalidColumnException(
					"Unable to parse Zero Timestamp: " + e.getMessage());
		}
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
	}

}
