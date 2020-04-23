package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLDoubleSource implements ValueSource<Double> {

	private ValueSource<String> source;

	public XMLDoubleSource(ValueSource<String> source) {
		this.source = source;
	}

	public Double getValue() throws InvalidColumnException {
		String value = source.getValue();
		try {
			if (value == null || value.length() == 0) {
				return new Double(0);
			}
			return Double.parseDouble(value);
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
