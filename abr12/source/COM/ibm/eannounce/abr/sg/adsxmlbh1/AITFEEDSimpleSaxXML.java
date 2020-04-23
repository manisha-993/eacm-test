// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

// $Log: AITFEEDSimpleSaxXML.java,v $
// Revision 1.1  2015/08/05 09:27:43  wangyul
// EACM to AIT feed
//
public class AITFEEDSimpleSaxXML {
	private OutputStream os;
	private TransformerHandler th;
	private AttributesImpl emptyAttr;
	private Transformer transformer;
	private OutputStreamWriter osw;
	
	public AITFEEDSimpleSaxXML(String fileName) throws FileNotFoundException, TransformerConfigurationException {
		init(fileName);
	}
	
	private void init(String fileName) throws TransformerConfigurationException, FileNotFoundException {
		os = new FileOutputStream(fileName);
		try {
			osw = new OutputStreamWriter(os, "UTF-8");
			Result resultXml = new StreamResult(osw);
			SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			th = sff.newTransformerHandler();
			transformer = th.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			th.setResult(resultXml);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	
	public void startDocument() throws SAXException {
		th.startDocument();
	}
	
	public void endDocument() throws SAXException {
		th.endDocument();
	}
	
	public void addElement(String nodeName, String nodeValue, Attributes attr) throws SAXException {
		th.startElement("", "", nodeName, attr);
        th.characters(nodeValue.toCharArray(), 0, nodeValue.length());     
        th.endElement("", "", nodeName);
	}
	
	public void addElement(String nodeName, String nodeValue) throws SAXException {
		addElement(nodeName, nodeValue, emptyAttr);
	}

	public void startElement(String nodeName, Attributes attr) throws SAXException {
		th.startElement("", "", nodeName, attr);
	}
	
	public void startElement(String nodeName) throws SAXException {
		th.startElement("", "", nodeName, emptyAttr);
	}
	
	public void endElement(String nodeName) throws SAXException {
		th.endElement("", "", nodeName);
	}
	
	public AttributesImpl createAttribute(String attrCode, String attrValue) {
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", attrCode, "", attrValue);
		return attr;
	}
	
	public void setOutputProperty(String attr, String value) {
		transformer.setOutputProperty(attr, value);
	}
	
	public void close() {
		if (osw != null) {
			try {
				osw.flush();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(os != null) {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
