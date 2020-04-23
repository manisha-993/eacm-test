package com.ibm.eannounce.wwprt.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.MQ;
import com.ibm.eannounce.wwprt.catcher.CatcherListener;
import com.ibm.eannounce.wwprt.processor.Processor;
import com.ibm.eannounce.wwprt.test.TestUtils;

public class WorkflowSimulator {

	static {
		try {
			FileInputStream in = new FileInputStream(new File("test_data/log.properties"));
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (final IOException e) {
			Logger.getAnonymousLogger()
					.severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream(new File(TestUtils.PROPERTIES));
		Properties properties = new Properties();
		properties.load(in);
		in.close();

		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);

		MQ mq = new MQ(TestUtils.MQ_EACM);

		Context context = Context.get();
		context.setMq(mq);
		context.setConnectionFactory(connectionFactory);
		context.setupFromProperties(properties);

		CatcherListener catcher = new CatcherListener();
		catcher.start();

		Processor processor = new Processor();
		processor.start();

		WWPRTSideSimulator wwprtSimulator = new WWPRTSideSimulator();
		wwprtSimulator.start();

		//Wait for a input to stop
		System.out.println("Any key to stop");
		System.in.read();

		System.err.println("Stopping simulators...");
		catcher.stop();
		processor.stop();
		wwprtSimulator.stop();
	}

}

