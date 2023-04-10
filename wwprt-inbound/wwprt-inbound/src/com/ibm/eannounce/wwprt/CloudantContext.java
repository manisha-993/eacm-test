package com.ibm.eannounce.wwprt;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.eannounce.wwprt.model.PriceErrors;
import com.ibm.eannounce.wwprt.notification.Notification;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class CloudantContext {

	private static CloudantContext context;

	public static CloudantContext get() {
		if (context == null) {
			context = new CloudantContext();
		}
		return context;
	}

	private CloudantContext() {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	}

	public static final DateFormat XML_TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.S");

	public static final DateFormat XML_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private ConnectionFactory connectionFactory;

	private DocumentBuilderFactory documentBuilderFactory;

	private String wwprtXmlTable;

	private String priceTable;

	private String tempPriceTable;
	
	//
	private String offeringtypes;

	private String span;
	private Notification notification = new Notification();


	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}



	public DocumentBuilder createDocumentBuilder() {
		try {
			return documentBuilderFactory.newDocumentBuilder();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to start XML parser", e);
		}
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public String getWWPRTXMLTable() {
		return wwprtXmlTable;
	}

	public void setWwprtXmlTable(String wwprtXmlTable) {
		this.wwprtXmlTable = wwprtXmlTable;
	}

	public String getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(String priceTable) {
		this.priceTable = priceTable;
	}

	public void setTempPriceTable(String tempPriceTable) {
		this.tempPriceTable = tempPriceTable;
	}

	public void setupFromProperties(Properties properties) {
		setWwprtXmlTable(properties.getProperty("table.WWPRTXML"));
		setPriceTable(properties.getProperty("table.PRICE"));
		setTempPriceTable(properties.getProperty("table.TEMPPRICE"));
		setOfferingtypes(properties.getProperty("price.offeringtypes"));

	}

	public String getOfferingtypes() {
		return offeringtypes;
	}

	public void setOfferingtypes(String offeringtypes) {
		this.offeringtypes = offeringtypes;
	}




	public String errorsToString(List<PriceErrors> errors) {
		StringBuilder sb = new StringBuilder();
		sb.append("<errors count=\"" + errors.size() + "\">");
		sb.append("\n");
		int i = 0;
		for (PriceErrors error : errors) {
			if (sb.length() > 700) {
				sb.append("<p>And more " + (errors.size() - i) + " errors...</p>");
				break;
			}
			sb.append("<offering=\"" + error.getOffering() + "\">");
			sb.append("\n");
			Map<String, String> map = error.getErrors();
			int size = map.size();
			for (Entry<String, String> entry : map.entrySet()) {
				String id = entry.getKey();
				String msg = entry.getValue();
				if (size == 1) {
					sb.append(msg);
				} else {
					if (id != null) {
						sb.append("<error element=\"");
						sb.append(id);
						sb.append("\">");
					} else {
						sb.append("<error>");
					}
					sb.append(msg);
					sb.append("</error>");
				}
				sb.append("\n");
			}
			sb.append("</offering>");
			sb.append("\n");
			i++;
		}
		sb.append("</errors>");
		return sb.toString();
	}

	public String getTempPriceTable() {
		return tempPriceTable;
	}

	public Notification getNotification() {
		return notification;
	}


}
