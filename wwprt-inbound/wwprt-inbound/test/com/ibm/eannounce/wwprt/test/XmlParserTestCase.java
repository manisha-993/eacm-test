package com.ibm.eannounce.wwprt.test;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserTestCase {

	@Test
	public void testParseFirstElement() throws Exception {
		// Get SAX Parser Factory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// Turn on validation, and turn off namespaces
		factory.setValidating(true);
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		final String[] id = new String[1];
		try {
			parser.parse(new File("test_data/prices.xml"), new DefaultHandler() {
				@Override
				public void startElement(String uri, String localName, String name,
						Attributes attributes) throws SAXException {
					System.out.println("Start Element :" + name);
					if (name.equalsIgnoreCase("wwprttxn")) {
						System.out.println("ID" + localName + " is " + attributes.getValue("id"));
						id[0] = attributes.getValue("id");
						throw new SAXException("Found it, can stop now!");
					}
				}
			});
		} catch (SAXException e) {
			Assert.assertEquals("99999999", id[0]);
		}
	}

}
