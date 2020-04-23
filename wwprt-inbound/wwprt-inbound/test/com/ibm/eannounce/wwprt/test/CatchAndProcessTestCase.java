package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Test;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.Log;
import com.ibm.eannounce.wwprt.MQ;
import com.ibm.eannounce.wwprt.catcher.Catcher;
import com.ibm.eannounce.wwprt.processor.Processor;
import com.ibm.mq.MQMessage;

public class CatchAndProcessTestCase {

	@Test
	public void testAllXmls() throws Exception {
		Log.init(Log.INFO, "test");
		
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

		catcher.initConnection();
		File dir = new File("test_data/xml");
		for (File file : dir.listFiles()) {
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
		catcher.noMessagesToRead();

	}

	@Test
	public void testLargeXml() throws Exception {
		Log.init(Log.DEBUG, "test");

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

		File file = new File("test_data/xml/22634_20100929114906.xml");
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
	
	@Test
	public void testPkError() throws Exception {
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

		File[] files = {
				new File("test_data/xml/26294_lf_20110816.xml"),
				new File("test_data/xml/26295_20101122005147.xml"),
				new File("test_data/xml/lf_1234_20110818.xml"),
		};
		for (File file : files) {
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
	
	@Test
	public void testCatchAllXmls() throws Exception {
		Log.init(Log.INFO, false, new File("logs"), "test");
		
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);

		Context.get().setConnectionFactory(connectionFactory);
		Context.get().setupFromProperties(properties);

		Catcher catcher = new Catcher();
		catcher.initConnection();
		File dir = new File("test_data/xml");
		for (File file : dir.listFiles()) {
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
		}
		catcher.noMessagesToRead();

	}

}
