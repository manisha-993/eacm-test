package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class MidStringSource implements ValueSource<String> {
	
	private final ValueSource<String> source;
	
	private final int count;

	private final int start;
	
	public MidStringSource(ValueSource<String> source, int start, int count) {
		this.source = source;
		this.start = start;
		this.count = count;
	}

	public String getValue() throws InvalidColumnException {
		String value = source.getValue();
		if (value == null) {
			return null;
		}
		return value.substring(start, start+count);
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}
}
