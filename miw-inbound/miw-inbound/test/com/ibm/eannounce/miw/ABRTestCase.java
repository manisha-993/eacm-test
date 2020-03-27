package com.ibm.eannounce.miw;

import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.w3c.dom.Document;

public class ABRTestCase extends TestCase {

	private void test(String fileName) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		SAXParser parser = factory.newSAXParser();
		MIWModel model = new MIWModel();
		File file = new File(fileName);
		parser.parse(file, model);
		try {
			model.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		MockMXABRSTATUS abr = new MockMXABRSTATUS();
		Document document = abr.parseXML(new FileReader(file));
		abr.execute(model.TYPE, document);
	}
	
	
	public void testProduct() throws Exception {
		test("test_data/Product.xml");
	}
	
	public void testInvalidProduct() throws Exception {
		try {
			test("test_data/InvalidProduct.xml");
			fail("Exception excepted");
		} catch (Exception e) {
		}
	}
	
	public void testFeature() throws Exception {
		test("test_data/Feature.xml");
		
	}
}
