package com.ibm.eannounce.wwprt.catcher;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.Log;
import com.ibm.eannounce.wwprt.MQ.MessageListener;
import com.ibm.eannounce.wwprt.dao.InsertPricesXML;
import com.ibm.eannounce.wwprt.notification.NotificationBuilder;
import com.ibm.mq.MQMessage;

public class Catcher implements MessageListener {

	private SAXParser parser;

	private Connection connection;

	public Catcher() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			parser = factory.newSAXParser();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to create SAX Parser. Java 1.5 is required.");
		}
	}

	public void readMessage(final MQMessage msg) throws Exception {
		catchAndProcessMessage(msg);
	}

	public void noMessagesToRead() throws Exception {
		// Close the connection if active
		if (connection != null && !connection.isClosed()) {
			Log.v("Catcher read all messages, closing the connection...");
			ConnectionFactory connectionFactory = Context.get().getConnectionFactory();
			connectionFactory.commit(connection);
			connectionFactory.close(connection);
			connection = null;
			Log.v("Catcher connection closed");
		}
	}

	public boolean initConnection() {
		// First of all check the connection
		ConnectionFactory connectionFactory = Context.get().getConnectionFactory();
		try {
			if (connection == null || connection.isClosed()) {
				connection = connectionFactory.getConnection();
				Log.v("Catcher started a new db connection");
			}
			return true;
		} catch (SQLException e) {
			if (connection != null) {
				Log.e( "Disconnected from the DB", e);
				try {
					connectionFactory.close(connection);
				} catch (SQLException e2) {
					Log.e( "Unable to close the connection: " + e2.getMessage());
				}
			}
		}
		return false;
	}

	private void catchAndProcessMessage(final MQMessage msg) {
		Context context = Context.get();
		boolean ackSent = false;
		String pricesId = "Invalid ID";
		try {
			/* Catcher
			 * 1) Read the message
			 * 2) Parse and find the message ID
			 * 3) Store the XML in WWPRTXML table
			 * 4) Send acknowledgment MQ message
			 */
			long catcherTime = System.currentTimeMillis();

			//1) Read the message
			Log.v("Reading MQ Message data...");
			String data = readMQMessageData(msg);

			//2) Parse and find the message ID
			Log.v("Parsing XML...");
			pricesId = parseMessageId(data);
			if (pricesId == null || pricesId.length() == 0) {
				pricesId = "Invalid ID";
				throw new CatcherException("Price list don't have a valid 'id' attribute");
			}

			//3) Store the XML in WWPRTXML table
			Log.v("Saving XML data to WWPRTXML table...");
			ConnectionFactory connectionFactory = context.getConnectionFactory();
			try {
				Transaction transaction = new Transaction(connection);
				transaction.executeUpdate(new InsertPricesXML(pricesId, data));
				connectionFactory.commit(connection);
			} catch (SQLException e) {
				String errorMsg = "Unable to store the Prices XML in the DB (WWPRTXML table) - Prices ID = "
						+ pricesId + " - " + e.getMessage();
				if (connection != null) {
					Log.e( errorMsg);
					try {
						connectionFactory.rollback(connection);
						connectionFactory.close(connection);
					} catch (SQLException e2) {
						Log.e( "Unable to close the connection: "
								+ e2.getMessage());
					}
				}

				// Save the XML to a local file to use later if needed
				/*File failedXmlFile = new File("failed/" + pricesId + ".xml");
				try {
					if (!failedXmlFile.getParentFile().exists()) {
						failedXmlFile.getParentFile().mkdirs();
					}
					BufferedWriter out = new BufferedWriter(new FileWriter(failedXmlFile));
					out.write(data);
					out.close();
				} catch (IOException e2) {
					Log.e( "Unable save failed xml file " + failedXmlFile, e2);
				}*/

				// Set the connection to null try to reconnect
				connection = null;
				throw new CatcherException(errorMsg, e);
			}

			//4) Send successful acknowledgment MQ message
			context.sendInitialResponse(true, pricesId, null);
			ackSent = true;

			if (Log.isLevel(Log.VERBOSE)) {
				Runtime runtime = Runtime.getRuntime();
				long t = runtime.totalMemory();
				long f = runtime.freeMemory();
				long u = t - f;
				Log.v("Catcher read, parsed and stored the XML data in "
					+ (System.currentTimeMillis() - catcherTime) + "ms - Mem(t) " + t + ", Mem(f) "
					+ f + " Mem(u) " + u);
			} else {
				Log.i("Catcher read, parsed and stored the XML data in "
						+ (System.currentTimeMillis() - catcherTime) + "ms");
				
			}
		} catch (CatcherException e) {
			Log.e( e.getMessage(), e);
			NotificationBuilder nb = new NotificationBuilder("WWPRT Inbound - Catcher problem ("
					+ pricesId + ")");
			nb.exception(e);
			context.getNotification().post(pricesId, nb);
			if (!ackSent) {
				context.sendInitialResponse(false, pricesId, e.getMessage());
			}
		}

	}

	private String readMQMessageData(final MQMessage msg) throws CatcherException {
		try {
			byte[] b = new byte[msg.getMessageLength()];
			msg.readFully(b);
			return new String(b);
		} catch (IOException e) {
			throw new CatcherException("Unable to read the MQ message (IOException)", e);
		}
	}

	private String parseMessageId(String data) throws CatcherException {
		IdFinderHandler idFinderHandler = new IdFinderHandler();
		try {
			InputSource is = new InputSource(new StringReader(data));
			is.setEncoding("UTF-8");
			parser.parse(is, idFinderHandler);
		} catch (StopSAXException e) {
			//This exception will be thrown when the ID is found
			//The parser can stop reading
		} catch (Exception e) {
			throw new CatcherException("Unable to parse the message: " + e.getMessage(), e);
		}
		return idFinderHandler.id;
	}

	class IdFinderHandler extends DefaultHandler {

		String id;

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes)
				throws SAXException {
			if (name.equalsIgnoreCase("wwprttxn")) {
				id = attributes.getValue("id");
				throw new StopSAXException();
			}
		}
	}

	class StopSAXException extends SAXException {

		private static final long serialVersionUID = 1L;

	}
}
