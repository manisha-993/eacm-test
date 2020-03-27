package com.ibm.eannounce.miw;

import java.text.ParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MIWModel extends DefaultHandler {

	public String DTSOFMSG = "";

	public String DTSMIWCREATE = "";

	public String PRODUCTID = "";

	public String FEATID = "";

	public String TYPE;

	private String currentElement;

	public void startElement(String uri, String localName, String name, Attributes attributes)
			throws SAXException {
		currentElement = name;
		if (name.equalsIgnoreCase("REFOFER_DATA")) {
			TYPE = "REFOFER";
		} else if (name.equalsIgnoreCase("REFOFERFEAT_DATA")) {
			TYPE = "REFOFERFEAT";
		}
	}

	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		currentElement = null;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentElement == null)
			return;
		if (currentElement.equalsIgnoreCase("DTSOFMSG")) {
			DTSOFMSG += new String(ch, start, length);
		} else if (currentElement.equalsIgnoreCase("DTSMIWCREATE")) {
			DTSMIWCREATE += new String(ch, start, length);
		} else if (currentElement.equalsIgnoreCase("PRODUCTID")) {
			PRODUCTID += new String(ch, start, length);
		} else if (currentElement.equalsIgnoreCase("FEATID")) {
			FEATID += new String(ch, start, length);
		}
	}

	public void validate() throws Exception {
		if (TYPE == null)
			throw new Exception("Unable to identify the TYPE (REFOFER | REFOFERFEAT)");
		
		if ("REFOFER".equalsIgnoreCase(TYPE)) {
			if (PRODUCTID.length() == 0)
				throw new Exception("REFOFER requires element <PRODUCTID>");
		}

		if ("REFOFERFEAT".equalsIgnoreCase(TYPE)) {
			if (FEATID.length() == 0)
				throw new Exception("REFOFERFEAT requires element <FEATID>");
		}
		
		if (DTSOFMSG.length() == 0) {
			throw new Exception("<DTSOFMSG> cannot be empty");
		}
		
		////change to parse and convert it
		try {
			
			DTSOFMSG = DateConverter.timestamp(DTSOFMSG);
		} catch (ParseException e) {
			throw new Exception("<DTSOFMSG> invalid date format: "+e.getMessage());
		}
		
		if (DTSMIWCREATE.length() == 0) {
			throw new Exception("<DTSMIWCREATE> cannot be empty");
		}
		
		try {
			DTSMIWCREATE = DateConverter.timestamp(DTSMIWCREATE);
		} catch (ParseException e) {
			throw new Exception("<DTSMIWCREATE> invalid date format: "+e.getMessage());
		}

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TYPE=");
		sb.append(TYPE);
		sb.append("\nDTSOFMESSAGE=");
		sb.append(DTSOFMSG);
		sb.append("\nDTSMIWCREATE=");
		sb.append(DTSMIWCREATE);
		sb.append("\nPRODUCTID=");
		sb.append(PRODUCTID);
		sb.append("\nFEATID=");
		sb.append(FEATID);
		sb.append("\n");
		return sb.toString();
	}
}
