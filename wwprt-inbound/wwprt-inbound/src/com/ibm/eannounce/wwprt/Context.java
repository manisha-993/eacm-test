package com.ibm.eannounce.wwprt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.eannounce.wwprt.model.PriceErrors;
import com.ibm.eannounce.wwprt.notification.Notification;

public class Context {
	
	private static Context context;

	public static Context get() {
		if (context == null) {
			context = new Context();
		}
		return context;
	}

	private Context() {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
	}

	public static final DateFormat XML_TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.S");

	public static final DateFormat XML_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private ConnectionFactory connectionFactory;

	private MQ mq;

	private DocumentBuilderFactory documentBuilderFactory;

	private String wwprtXmlTable;

	private String priceTable;

	private String tempPriceTable;
	
	//
	private String offeringtypes;

	private Notification notification = new Notification();

	private AcknowledgmentInterceptor acknowledgmentInterceptor;

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public MQ getMq() {
		return mq;
	}

	public void setMq(MQ mq) {
		this.mq = mq;
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
		try {
			notification.init(properties, "subscriptions.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getOfferingtypes() {
		return offeringtypes;
	}

	public void setOfferingtypes(String offeringtypes) {
		this.offeringtypes = offeringtypes;
	}

	public boolean sendInitialResponse(boolean success, String priceId, String message) {
		try {
			if (acknowledgmentInterceptor != null) {
				acknowledgmentInterceptor.beforeSendInitialResponse();
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			sb.append("<wwprttxnresponse type=\"price\" id=\"");
			sb.append(priceId);
			sb.append("\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"response.xsd\">");
			sb.append("<createts>");
			sb.append(Context.XML_TIMESTAMP_FORMAT.format(new Date()));
			sb.append("</createts>");
			sb.append("<status>");
			if (success) {
				sb.append("received");
			} else {
				sb.append("failure");
			}
			sb.append("</status>");
			//defect 740029 --remove the error tag
			//WWPRT full IDL test - some response messages sent seem to have an additional "error" tag
//			if (message != null) {
//				sb.append("<error>");
//				sb.append(message);
//				sb.append("</error>");
//			}
			sb.append("</wwprttxnresponse>");
	
			//Send acknowledgment
			if (mq != null) {
				mq.putMessage(sb.toString());
				return true;
			}
		} catch (Exception e) {
			Log.e( "Unable to send Initial response acknowledgment to MQ. Prices ID: "+priceId, e);
		}
		return false;
	}

	public boolean sendFinalResponse(boolean success, String priceId, List<PriceErrors> errors) {
		try {
			if (acknowledgmentInterceptor != null) {
				acknowledgmentInterceptor.beforeSendFinalResponse();
			}

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			sb.append("<wwprttxnresponse type=\"price\" id=\"");
			sb.append(priceId);
			sb.append("\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"response.xsd\">");
			sb.append("<createts>");
			sb.append(Context.XML_TIMESTAMP_FORMAT.format(new Date()));
			sb.append("</createts>");
			sb.append("<status>");
			if (success) {
				sb.append("success");
			} else {
				sb.append("failure");
			}
			sb.append("</status>");
			//defect 740029 --remove the error tag
			//WWPRT full IDL test - some response messages sent seem to have an additional "error" tag
//			if (!errors.isEmpty()) {
//				sb.append(errorsToString(errors));
//			}
			sb.append("</wwprttxnresponse>");
	
			//Send acknowledgment
			if (mq != null) {
				mq.putMessage(sb.toString());
				return true;
			}
		} catch (Exception e) {
			Log.e( "Unable to send Final response acknowledgment to MQ. Prices ID: "+priceId, e);
		}
		return false;
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

	public void setAcknowledgmentInterceptor(AcknowledgmentInterceptor acknowledgmentInterceptor) {
		this.acknowledgmentInterceptor = acknowledgmentInterceptor;
	}
	
	/**
	 * Class used to test MQ connection problems when sending the initial and final response
	 */
	public interface AcknowledgmentInterceptor {
		
		public void beforeSendInitialResponse() throws Exception;
		
		public void beforeSendFinalResponse() throws Exception;
		
	}
}
