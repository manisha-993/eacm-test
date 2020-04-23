package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public interface ValueSource<T> {

	T getValue() throws InvalidColumnException;

	void accept(ValueSourceVisitor visitor);
	
}
