package com.ibm.eannounce.miw;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

public class MIWXmlParseTestCase extends TestCase {

	public void testParseRefOfer() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		MIWModel model = new MIWModel();
		parser.parse(new File("test_data/Product.xml"), model);
		model.validate();
		assertEquals("REFOFER",model.TYPE);
		assertEquals("2012-03-12 21:47:17.0",model.DTSOFMSG);
		assertEquals("AF48000",model.PRODUCTID);
		System.out.println(model);
	}
	
	public void testValidateRefOfer() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		MIWModel model = new MIWModel();
		parser.parse(new File("test_data/Product.xml"), model);
		try {
			model.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	public void testValidateFeature() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		MIWModel model = new MIWModel();
		parser.parse(new File("test_data/Feature.xml"), model);
		try {
			model.validate();
			System.out.println("test_data/Feature.xml");
			System.out.println(model);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	public void testInvalidRefOfer() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		MIWModel model = new MIWModel();
		parser.parse(new File("test_data/InvalidReoffer.xml"), model);
		try {
			System.out.println(model.toString());
			model.validate();
			fail("Validation should have thrown an exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
