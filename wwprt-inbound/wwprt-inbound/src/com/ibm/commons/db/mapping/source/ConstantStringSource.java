package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class ConstantStringSource implements ValueSource<String> {

	private String value;

	public ConstantStringSource(String value) {
		this.value = value;
	}

	public String getValue() throws InvalidColumnException {
		return value;
	}

	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
	}

}
