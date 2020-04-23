package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class RightStringSource implements ValueSource<String> {
	
	private ValueSource<String> source;
	
	private int count;
	
	public RightStringSource(ValueSource<String> source, int count) {
		this.source = source;
		this.count = count;
	}

	public String getValue() throws InvalidColumnException {
		String value = source.getValue();
		if (value == null) {
			return null;
		}
		return value.substring(value.length() - count, value.length());
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}
}
