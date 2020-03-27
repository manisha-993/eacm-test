package com.ibm.commons.db.mapping.source;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.ibm.commons.db.mapping.InvalidColumnException;
import com.ibm.eannounce.wwprt.Context;

public class XMLTimestampSource implements ValueSource<Timestamp> {

	private ValueSource<String> source;

	public XMLTimestampSource(ValueSource<String> source) {
		this.source = source;
	}

	public Timestamp getValue() throws InvalidColumnException {
		String value = source.getValue();
		try {
			Date date = Context.XML_TIMESTAMP_FORMAT.parse(value);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new InvalidColumnException("Invalid timestamp value: '"
					+ value + "' in " + source);
		}
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}

}
