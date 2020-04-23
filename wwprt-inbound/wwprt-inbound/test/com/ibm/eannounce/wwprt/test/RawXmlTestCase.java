package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ibm.commons.db.mapping.InvalidColumnException;
import com.ibm.commons.db.mapping.source.XMLElementAsStringSource;
import com.ibm.eannounce.wwprt.Context;

public class RawXmlTestCase {

	@Test
	public void test() throws SAXException, IOException, InvalidColumnException {
		DocumentBuilder builder = Context.get().createDocumentBuilder();
		Document result = builder.parse(new File("test_data/xml/single.xml"));
		Element element = (Element) result.getElementsByTagName("price").item(0);
		
		XMLElementAsStringSource source = new XMLElementAsStringSource();
		source.setElement(element);
		System.out.println(source.getValue());
	}
	
}
