package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLIntegerSource implements ValueSource<Integer> {

	private ValueSource<String> source;

	public XMLIntegerSource(ValueSource<String> source) {
		this.source = source;
	}

	public Integer getValue() throws InvalidColumnException {
		String value = source.getValue();
		try {
			if (value == null || value.length() == 0) {
				return new Integer(0);
			}
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new InvalidColumnException("Invalid float value: '"
					+ value + "' in " + source);
		}
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
		visitor.visit(source);
	}

}
