package com.ibm.commons.db.mapping.source;

import org.w3c.dom.NodeList;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLContentSource extends XMLValueSource<String> {

	private String elementName;

	private String defaultValue;

	public XMLContentSource(String elementName) {
		this.elementName = elementName;
	}

	public XMLContentSource(String elementName, String defaultValue) {
		this.elementName = elementName;
		this.defaultValue = defaultValue;
	}

	public String getValue() throws InvalidColumnException {
		NodeList nodeList = element.getElementsByTagName(elementName);
		if (nodeList.getLength() == 0) {
			throw new InvalidColumnException("Element '" + element.getNodeName()
					+ "' don't have the element '" + elementName + "'");
		} else if (nodeList.getLength() > 1) {
			throw new InvalidColumnException("Element '" + element.getNodeName() + 
					"' have too many elements of type '"
					+ elementName + "'");
		}
		String value = nodeList.item(0).getTextContent();
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public String toString() {
		return element.getNodeName() + "/" + elementName;
	}
}
