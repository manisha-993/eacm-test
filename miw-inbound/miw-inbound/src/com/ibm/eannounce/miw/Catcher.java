package com.ibm.eannounce.miw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.eannounce.miw.MQ.MessageListener;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;

public class Catcher implements MessageListener {

	static final String TAG = "Catcher";
	private SAXParser parser;
	private MIWEntityManager entityManager;
	private Properties properties;

	
	public static void main(String[] args) {
		Catcher catcher = new Catcher("miw.properties", args);
		catcher.dispose();
	}

	public Catcher(String propertiesFile, String[] args) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int logLevel = Integer.parseInt(properties.getProperty(Keys.LOG_LEVEL, "" + Log.INFO));
		boolean logPersistent = Boolean.valueOf(
				properties.getProperty(Keys.LOG_PERSISTENT, "false")).booleanValue();
		Log.init(logLevel, logPersistent, new File("logs"), "miw");

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			parser = factory.newSAXParser();
		} catch (Exception e) {
			Log.e(TAG, "Unable to create SAX Parser", e);
			throw new IllegalStateException("Unable to create SAX Parser.");
		}
		
		if (args.length == 0) {
			//Read from MQ
			execute();
		} else if (args.length == 2) {
			if ("put".equals(args[0])) {
				//Test message by putting it on MQ
				Log.i(TAG, "Testing putting XML in MQ: " + args[1]);
				try {
					MQ mq = new MQ(properties);
					mq.putMessage(new File(args[1]));
					mq.close();
					execute();
				} catch (Exception e) {
					Log.e(TAG, "Unable to put message on queue", e);
				}
			} else if ("catch".equals(args[0])) {
				//Test XML by reading it from file
				Log.i(TAG, "Testing XML file: " + args[1]);
				long startTime = System.currentTimeMillis();
				try {
					String xml = readFileAsString(args[1]);
					entityManager = new MIWEntityManager(properties);
					entityManager.connect();
					Log.i(TAG, "Parsing XML message...");
					boolean skipChars = Boolean.valueOf(properties.getProperty(Keys.MQ_SKIP_CHARS, "false")).booleanValue();
					parseAndCreate(xml, skipChars);
				} catch (IOException e) {
					Log.e(TAG, "Unable to read XML file: " + args[1], e);
				} catch (SAXException e) {
					Log.e(TAG, "Unable to parse XML file: " + args[1], e);
				} catch (Exception e) {
					Log.e(TAG, "Unable to create MIW entity", e);
				} finally {
					entityManager.disconnect();
					long time = Math.abs(startTime - System.currentTimeMillis());
					Log.i(TAG, "Execution time: " + Math.round(time / 1000L));
				}
			} else {
				System.out.println("Usage params: [put|catch] file.xml");
			}
		}

	}

	private static String readFileAsString(String filePath) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				filePath), "UTF-8"));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	public void dispose() {
		Log.close();
	}

	public void execute() {
		entityManager = new MIWEntityManager(properties);
		entityManager.connect();
		Log.i(TAG, "Reading MQ messages...");
		MQ mq = new MQ(properties);
		long startTime = System.currentTimeMillis();
		try {
			if (!mq.getMessages(this)) {
				Log.i(TAG, "No messages found.");
			}
		} catch (MQException e) {
			Log.e(TAG, "Unable to get MQ messages", e);
			throw new IllegalStateException("Unable to get MQ messages");
		} finally {
			mq.close();
			entityManager.disconnect();
			long time = Math.abs(startTime - System.currentTimeMillis());
			Log.i(TAG, "Execution time: " + Math.round(time / 1000L));
		}
	}
	
	public void readMessage(MQMessage msg, boolean skipChars) {
		String data = null;
		try {
			byte[] b = new byte[msg.getMessageLength()];
			msg.readFully(b);
			data = new String(b);
			if (skipChars) {
				int i = data.indexOf("<");
				if (i > 0) {
					data = data.substring(i);
				}
			}
			//Parse
			parseAndCreate(data, skipChars);
		} catch (IOException e) {
			Log.e(TAG, "Unable access message data", e);
		} catch (SAXException e) {
			Log.e(TAG, "Unable to parse XML: " + data, e);
		} catch (MiddlewareException e) {
			Log.e(TAG, "Unable to create entity (MiddlewareException)", e);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to create entity (SQLException)", e);
		} catch (Exception e) {
			Log.e(TAG, "Invalid entity: " + e.getMessage(), e);
		}
	}

	private void parseAndCreate(String xml, boolean skipChars) throws SAXException, IOException, Exception,
			MiddlewareException, SQLException {

		if (skipChars) {
			int i = xml.indexOf("<");
			if (i > 0) {
				xml = xml.substring(i);
			}
		}
		
		MIWModel model = new MIWModel();

		InputSource is = new InputSource(new StringReader(xml));
		is.setEncoding("UTF-8");
		parser.parse(is, model);

		//Validate
		model.validate();

		//Store
		entityManager.createEntity(model, xml);
	}

}
