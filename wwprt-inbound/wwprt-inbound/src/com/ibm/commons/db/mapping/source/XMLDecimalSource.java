package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLDecimalSource implements ValueSource<String> {

	private ValueSource<String> source;

	public XMLDecimalSource(ValueSource<String> source) {
		this.source = source;
	}

	public String getValue() throws InvalidColumnException {
		String value = source.getValue();
		if (value == null) {
			return null;
		}
		if (value.length() > 24) {
			throw new InvalidColumnException("Invalid value: '" + value + "' in " + source
					+ " - Expected 17:6 decimal");
		}
		return value;
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}

}
