package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class LeftStringSource implements ValueSource<String> {
	
	private ValueSource<String> source;
	
	private int count;
	
	public LeftStringSource(ValueSource<String> source, int count) {
		this.source = source;
		this.count = count;
	}

	public String getValue() throws InvalidColumnException {
		String value = source.getValue();
		if (value == null) {
			return null;
		}
		return value.substring(0,count);
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}
}
