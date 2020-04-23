package com.ibm.commons.db.mapping.source;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLAttributeSource extends XMLValueSource<String> {

	private String attributeName;

	public XMLAttributeSource(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getValue() throws InvalidColumnException {
		return element.getAttribute(attributeName);
	}

}
