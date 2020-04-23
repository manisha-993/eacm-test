package com.ibm.commons.db.mapping.source;

import org.w3c.dom.Element;

public abstract class XMLValueSource<T> implements ValueSource<T> {

	protected Element element;

	public void setElement(Element element) {
		this.element = element;
	}
	
	public void accept(ValueSourceVisitor visitor) {
		visitor.visit(this);
	}
	
}
