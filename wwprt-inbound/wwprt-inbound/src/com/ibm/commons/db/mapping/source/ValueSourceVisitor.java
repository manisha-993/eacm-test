package com.ibm.commons.db.mapping.source;

public interface ValueSourceVisitor {

	void visit(ValueSource<?> valueSource);
	
}
