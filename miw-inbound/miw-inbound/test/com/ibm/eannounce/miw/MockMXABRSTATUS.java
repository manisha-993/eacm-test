package com.ibm.eannounce.miw;

import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class MockMXABRSTATUS {
	
	public void execute(String MXTYPE, Document document) throws Exception {
		Element root = document.getDocumentElement();
		if ("REFOFER".equals(MXTYPE)) {
			RefoferModel refoferModel = new RefoferModel(root);
			refoferModel.validate();
		} else if ("REFOFERFEAT".equals(MXTYPE)) {
			RefoferFeatModel refoferFeatModel = new RefoferFeatModel(root);
			refoferFeatModel.validate();
		}
	}
	
	public Document parseXML(Reader reader) throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(reader);
			return builder.parse(is);
		} catch (SAXParseException exception) {
			throw exception;
		}
	}
	
	static DateFormat inDateFormatShort = new SimpleDateFormat("dd/MM/yy");
	static DateFormat inDateFormatLong = new SimpleDateFormat("dd/MM/yyyy");

	static DateFormat inDateTimeFormatShort = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	static DateFormat inDateTimeFormatLong = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	static DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	static int shortDateLength = "27/05/99".length();
	static int shortTimestampLength = "27/05/99 10:25:48 GMT".length();

	public static String date(String inputDate) throws ParseException {
		Date date;
		if (inputDate.length() == shortDateLength) {
			date = inDateFormatShort.parse(inputDate);
		} else {
			date = inDateFormatLong.parse(inputDate);
		}
		return outFormat.format(date);
	}

	public static String timestamp(String inputDateAndTime) throws ParseException {
		Date date;
		if (inputDateAndTime.length() == shortTimestampLength) {
			date = inDateTimeFormatShort.parse(inputDateAndTime);
		} else {
			date = inDateTimeFormatLong.parse(inputDateAndTime);
		}
		return outFormat.format(date);
	}

	class Model {

		public String ACTIVITY;

		public String DTSOFMSG;

		public String DTSMIWCREATE;

		public void rejectIfNullOrEmpty(String name, String value) throws Exception {
			if (value == null || value.length() == 0) {
				throw new Exception("Element '" + name + "' cannot be empty.");
			}
		}

		public String parseTimestampAndRejectIfInvalid(String name, String value) throws Exception {
			try {
				return timestamp(value);
			} catch (ParseException e) {
				throw new Exception("Element '" + name + "' timestamp '" + value + "' is invalid: "
						+ e.getMessage());
			}
		}

		public String parseDateAndRejectIfInvalid(String name, String value) throws Exception {
			try {
				return date(value);
			} catch (ParseException e) {
				throw new Exception("Element '" + name + "' date '" + value + "' is invalid: "
						+ e.getMessage());
			}
		}
	}
	
	class RefoferModel extends Model {

		public String PRODUCTID;
		public String MFRPRODTYPE;
		public String MFRPRODDESC;
		public String MKTGDIV;
		public String PRFTCTR;
		public String CATGSHRTDESC;
		public String STRTOSVC;
		public String ENDOFSVC;
		public String VENDNAM;
		public String MACHRATECATG;
		public String CECSPRODKEY;
		public String MAINTANNBILLELIGINDC;
		public String FSLMCPU;
		public String PRODSUPRTCD;

		public RefoferModel(Element element) {
			ACTIVITY = getNodeValue(element, "ACTIVITY");
			DTSOFMSG = getNodeValue(element, "DTSOFMSG");
			DTSMIWCREATE = getNodeValue(element, "DTSMIWCREATE");
			PRODUCTID = getNodeValue(element, "PRODUCTID");
			MFRPRODTYPE = getNodeValue(element, "MFRPRODTYPE");
			MFRPRODDESC = getNodeValue(element, "MFRPRODDESC");
			MKTGDIV = getNodeValue(element, "MKTGDIV");
			PRFTCTR = getNodeValue(element, "PRFTCTR");
			CATGSHRTDESC = getNodeValue(element, "CATGSHRTDESC");
			STRTOSVC = getNodeValue(element, "STRTOSVC");
			ENDOFSVC = getNodeValue(element, "ENDOFSVC");
			VENDNAM = getNodeValue(element, "VENDNAM");
			MACHRATECATG = getNodeValue(element, "MACHRATECATG");
			CECSPRODKEY = getNodeValue(element, "CECSPRODKEY");
			MAINTANNBILLELIGINDC = getNodeValue(element, "MAINTANNBILLELIGINDC");
			FSLMCPU = getNodeValue(element, "FSLMCPU");
			PRODSUPRTCD = getNodeValue(element, "PRODSUPRTCD");
		}

		public void validate() throws Exception {
			rejectIfNullOrEmpty("ACTIVITY", ACTIVITY);
			rejectIfNullOrEmpty("DTSOFMSG", DTSOFMSG);
			rejectIfNullOrEmpty("DTSMIWCREATE", DTSMIWCREATE);
			rejectIfNullOrEmpty("PRODUCTID", PRODUCTID);
			rejectIfNullOrEmpty("MFRPRODTYPE", MFRPRODTYPE);
			rejectIfNullOrEmpty("MFRPRODDESC", MFRPRODDESC);
			rejectIfNullOrEmpty("MKTGDIV", MKTGDIV);
			rejectIfNullOrEmpty("PRFTCTR", PRFTCTR);
			rejectIfNullOrEmpty("CATGSHRTDESC", CATGSHRTDESC);
			rejectIfNullOrEmpty("STRTOSVC", STRTOSVC);
			rejectIfNullOrEmpty("ENDOFSVC", ENDOFSVC);
			rejectIfNullOrEmpty("VENDNAM", VENDNAM);
			rejectIfNullOrEmpty("MACHRATECATG", MACHRATECATG);
			rejectIfNullOrEmpty("CECSPRODKEY", CECSPRODKEY);
			rejectIfNullOrEmpty("MAINTANNBILLELIGINDC", MAINTANNBILLELIGINDC);
			rejectIfNullOrEmpty("FSLMCPU", FSLMCPU);
			rejectIfNullOrEmpty("PRODSUPRTCD", PRODSUPRTCD);

			DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", DTSOFMSG);
			DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", DTSMIWCREATE);
			STRTOSVC = parseDateAndRejectIfInvalid("STRTOSVC", STRTOSVC);
			ENDOFSVC = parseDateAndRejectIfInvalid("ENDOFSVC", ENDOFSVC);
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Date/Time=" + DTSOFMSG);
			sb.append(" \nMIW Create=" + DTSMIWCREATE);
			sb.append(" \nMessage Type=REFOFER");
			sb.append(" \nProduct ID=" + PRODUCTID);
			sb.append("\n");
			return sb.toString();
		}
	}

	class RefoferFeatModel extends Model {

		public String PRODUCTID;
		public String PRODUCTACTIVITY;
		public String FEATID;
		public String MFRFEATID;
		public String MFRFEATDESC;
		public String MFRFEATLNGDESC;
		public String MKTGDIV;
		public String PRFTCTR;

		public RefoferFeatModel(Element element) {
			ACTIVITY = getNodeValue(element, "ACTIVITY");
			String pa = getNodeValue(element, "PRODUCTACTIVITY");
			if (pa == null || pa.length() == 0) {
				// Default is UPDATE
				PRODUCTACTIVITY = "Update";
			} else {
				PRODUCTACTIVITY = pa;
			}
			DTSOFMSG = getNodeValue(element, "DTSOFMSG");
			DTSMIWCREATE = getNodeValue(element, "DTSMIWCREATE");
			FEATID = getNodeValue(element, "FEATID");
			PRODUCTID = getNodeValue(element, "PRODUCTID");
			MFRFEATID = getNodeValue(element, "MFRFEATID");
			MFRFEATDESC = getNodeValue(element, "MFRFEATDESC");
			MFRFEATLNGDESC = getNodeValue(element, "MFRFEATLNGDESC");
			MKTGDIV = getNodeValue(element, "MKTGDIV");
			PRFTCTR = getNodeValue(element, "PRFTCTR");
		}

		public void validate() throws Exception {
			rejectIfNullOrEmpty("ACTIVITY", ACTIVITY);
			rejectIfNullOrEmpty("PRODUCTACTIVITY", PRODUCTACTIVITY);
			rejectIfNullOrEmpty("DTSOFMSG", DTSOFMSG);
			rejectIfNullOrEmpty("DTSMIWCREATE", DTSMIWCREATE);
			rejectIfNullOrEmpty("PRODUCTID", PRODUCTID);
			rejectIfNullOrEmpty("FEATID", FEATID);
			rejectIfNullOrEmpty("MFRFEATID", MFRFEATID);
			rejectIfNullOrEmpty("MFRFEATDESC", MFRFEATDESC);
			rejectIfNullOrEmpty("MFRFEATLNGDESC", MFRFEATLNGDESC);
			rejectIfNullOrEmpty("MKTGDIV", MKTGDIV);
			rejectIfNullOrEmpty("PRFTCTR", PRFTCTR);
			DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", DTSOFMSG);
			DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", DTSMIWCREATE);
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Message Type=REFOFERFEAT");
			sb.append(" \nDate/Time=" + DTSOFMSG);
			sb.append(" \nMIW Create=" + DTSMIWCREATE);
			sb.append(" \nFeature Id=" + FEATID);
			sb.append(" \nProduct Id=" + PRODUCTID);
			sb.append("\n");
			return sb.toString();
		}
	}
	
	public String getNodeValue(Element element, String tagName) {
		String value = null;
		NodeList nlist = element.getElementsByTagName(tagName);
		if (nlist == null) {
			addDebug("getNodeValue: " + tagName + " element returned null");
		} else if (nlist.getLength() == 0) {
			addDebug("getNodeValue: " + tagName + " was not found in the XML");
		} else if (nlist.getLength() > 1) {
			addDebug("getNodeValue: " + tagName + " has more then 1 element");
		} else {
			Element node = (Element) nlist.item(0);
			Element el = (Element) nlist.item(0);
			if (node.hasChildNodes()) {
				value = el.getFirstChild().getNodeValue();
			} else {
				value = el.getNodeValue();
			}
			addDebug("getNodeValue: " + tagName + " = " + value);
		}
		return value;
	}

	private void addDebug(String string) {
		System.out.println(string);
	}
}
