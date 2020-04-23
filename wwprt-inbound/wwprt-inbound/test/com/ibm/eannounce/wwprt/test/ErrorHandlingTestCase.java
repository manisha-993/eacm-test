package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.MQ;
import com.ibm.eannounce.wwprt.Context.AcknowledgmentInterceptor;
import com.ibm.eannounce.wwprt.catcher.Catcher;
import com.ibm.eannounce.wwprt.catcher.CatcherListener;
import com.ibm.eannounce.wwprt.processor.Processor;
import com.ibm.mq.MQMessage;

public class ErrorHandlingTestCase {

	@Test
	public void testInvalidPricesXML() throws Exception {
		MQMessage message = new MQMessage();
		message.writeString("Invalid XML message format");
		message.seek(0);
		Catcher catcher = new Catcher();
		catcher.readMessage(message);
	}

	@Test
	public void testInvalidPricesIdXML() throws Exception {
		MQMessage message = new MQMessage();
		message.writeString("<wwprttxn type=\"price\"></wwprttxn>");
		message.seek(0);
		Catcher catcher = new Catcher();
		catcher.readMessage(message);
	}

	@Test
	public void testCatcherDBConnectionProblem() throws Exception {
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		Context.get().setConnectionFactory(new DB2ConnectionFactory(properties));
		MQMessage message = new MQMessage();
		message.writeString("<wwprttxn type=\"price\" id=\"123\"></wwprttxn>");
		message.seek(0);
		Catcher catcher = new Catcher();
		catcher.initConnection();
		catcher.readMessage(message);
		catcher.noMessagesToRead();
	}

	@Test
	public void testProcessInvalidXML() throws Exception {
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);
		in = new FileInputStream("test_data/prices-invalid.xml");
		Document document = Context.get().createDocumentBuilder().parse(in);
		in.close();

		Context.get().setConnectionFactory(connectionFactory);
		Context.get().setupFromProperties(properties);
		Context.get().setMq(new MQ(TestUtils.MQ_EACM));

		Processor processor = new Processor();
		processor.initialize();
		processor.processPrices(document);
	}

	@Test
	public void testProcessBrokenXML() throws Exception {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);

		Context.get().setConnectionFactory(connectionFactory);
		Context.get().setupFromProperties(properties);

		Catcher catcher = new Catcher();
		Processor processor = new Processor();
		processor.initialize();

		File file = new File("test_data/price-broken.xml");
		System.out.println("*****************************************");
		System.out.println("FILE: " + file);
		MQMessage message = new MQMessage();
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		try {
			int n;
			while ((n = is.read(buffer)) != -1) {
				message.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		message.seek(0);
		catcher.readMessage(message);
		processor.execute();

		//Execute again, it shouldn't process anymore
		processor.execute();
	}
	
	
	@Test
	public void testProcessUnmmapedPricePointType() throws Exception {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);

		Context.get().setConnectionFactory(connectionFactory);
		Context.get().setupFromProperties(properties);
		Context.get().setMq(new MQ(TestUtils.MQ_EACM));
		
		Catcher catcher = new Catcher();
		Processor processor = new Processor();
		processor.initialize();

		File file = new File("test_data/price-invalid-type.xml");
		System.out.println("*****************************************");
		System.out.println("FILE: " + file);
		MQMessage message = new MQMessage();
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		try {
			int n;
			while ((n = is.read(buffer)) != -1) {
				message.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		message.seek(0);
		catcher.readMessage(message);
		processor.execute();

		//Execute again, it shouldn't process anymore
		processor.execute();
	}


	@Test
	public void testCatcherDBConnectionProblemLoop() throws Exception {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

		
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		Context.get().setupFromProperties(properties);
		Context.get().setConnectionFactory(new DB2ConnectionFactory(properties));
		
		MQ mq = new MQ(TestUtils.MQ_WWPRT);
		
		Context.get().setMq(new MQ(TestUtils.MQ_EACM));
		
		CatcherListener catcherListener = new CatcherListener();
		for (int i = 0; i < 10; i++) {
			catcherListener.checkMessages();
		}
		
		mq.putMessage(new File("test_data/xml/11111_lf_20110816.xml"));
		mq.putMessage(new File("test_data/xml/12346_SWF_20110818.xml"));
		mq.putMessage(new File("test_data/xml/22635_20100929115005.xml"));
		mq.putMessage(new File("test_data/xml/22641_20100929115202.xml"));
		for (int i = 0; i < 10; i++) {
			catcherListener.checkMessages();
		}
	}
	
	@Test
	public void testMQInitialAndFinalResponseProblem() throws Exception {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		Context.get().setupFromProperties(properties);
		Context.get().setConnectionFactory(new DB2ConnectionFactory(properties));
		
		Context.get().setMq(new MQ(TestUtils.MQ_EACM));
		Context.get().setAcknowledgmentInterceptor(new AcknowledgmentInterceptor() {
			public void beforeSendInitialResponse() throws Exception {
				throw new Exception("Simulating an MQ connection for Initial Response");
			}
			public void beforeSendFinalResponse() throws Exception {
				throw new Exception("Simulating an MQ connection for Final Response");
			}
		});
		
		Catcher catcher = new Catcher();
		catcher.initConnection();
		Processor processor = new Processor();
		processor.initialize();

		File file = new File("test_data/xml/11111_lf_20110816.xml");
		System.out.println("*****************************************");
		System.out.println("FILE: " + file);
		MQMessage message = new MQMessage();
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		try {
			int n;
			while ((n = is.read(buffer)) != -1) {
				message.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		message.seek(0);
		catcher.readMessage(message);
		processor.execute();

	}
}
