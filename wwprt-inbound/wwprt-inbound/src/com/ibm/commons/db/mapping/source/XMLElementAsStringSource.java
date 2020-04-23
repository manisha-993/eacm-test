package com.ibm.commons.db.mapping.source;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import com.ibm.commons.db.mapping.InvalidColumnException;

public class XMLElementAsStringSource extends XMLValueSource<String> {

	private XMLSerializer serializer;

	public XMLElementAsStringSource() {
		OutputFormat of = new OutputFormat("XML", "UTF-8", false);
		of.setOmitXMLDeclaration(true);
		of.setIndenting(false);
		of.setPreserveSpace(false);
		of.setMethod("text");
		serializer = new XMLSerializer(of);
	}

	public String getValue() throws InvalidColumnException {
		StringWriter writer = new StringWriter();
		serializer.setOutputCharStream(writer);
		try {
			serializer.serialize(element);
		} catch (IOException e) {
			throw new InvalidColumnException("Unable to serialize the element:"
					+ element.getNodeName() + ": " + e.getMessage());
		}
		return writer.toString();
	}

}
