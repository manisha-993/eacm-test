package com.ibm.commons.db.mapping.source;

import java.text.ParseException;
import java.util.Date;

import com.ibm.commons.db.mapping.InvalidColumnException;
import com.ibm.eannounce.wwprt.Context;

public class XMLDateSource implements ValueSource<java.sql.Date>{
	
	private ValueSource<String> source;
	
	public XMLDateSource(ValueSource<String> source) {
		this.source = source;
	}

	public java.sql.Date getValue() throws InvalidColumnException {
		String value = source.getValue();
		try {
			Date date = Context.XML_DATE_FORMAT.parse(value);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			throw new InvalidColumnException("Invalid date value: '"+value+"' in "+source);
		}
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}
}
