package com.ibm.eannounce.wwprt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.eannounce.wwprt.Context.AcknowledgmentInterceptor;
import com.ibm.eannounce.wwprt.catcher.CatcherListener;
import com.ibm.eannounce.wwprt.catcher.CloudantCatcher;
import com.ibm.eannounce.wwprt.catcher.CloudantListener;
import com.ibm.eannounce.wwprt.processor.Processor;
import com.ibm.eannounce.wwprt.processor.ProcessorListener;

public class WWPRTInbound {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			printCommands();
			return;
		}
		
		WWPRTInbound inbound = new WWPRTInbound();
		
		String command = args[0];
		
		inbound.init(command);
		if ("catcher".equalsIgnoreCase(command)) {
			//Start the Catcher process
			try {
				inbound.startCatcher();
			} catch (Throwable e) {
				Log.e("Uncought exception", e);
			}
		} else if ("processor".equalsIgnoreCase(command)) {
			//Start the Processor process
			try {
				inbound.startProcessor();
			} catch (Throwable e) {
				Log.e("Uncought exception", e);
			}
		} else if ("cloudant".equals(command)) {
			String t1 = null;
			String t2 = null;
			if(args.length==3){
				 t1 = args[1];
				 t2 = args[2];
			}else if(args.length!=1){
				printCommands();
				return;
			}

			inbound.startCloudCatcher(t1,t2);

		} else if ("test".equalsIgnoreCase(command)) {
			if (args.length < 2) {
				printCommands();
				return;
			} else {
				 try {
					inbound.test(args);
				} catch (Exception e) {
					Log.e("Uncought exception", e);
				}
			}
		}
		
	}
	
	private static void printCommands() {
		System.out.println("Valid params are:");
		System.out.println("catcher - Starts the Catcher");
		System.out.println("cloudant <MODE>- Starts the Catcher full|delta|defect");
		System.out.println("processor - Starts the Processor");
		System.out.println("test <file.xml> - Feed the MQ with an xml file, catch and process it");
	}
	
	private CatcherListener catcher;
	private Processor processor;
	private File lockFile;
	private int interval;
	private Properties properties;

	private CloudantListener cloudantCatcher;


	public void init(String command) throws IOException {
		FileInputStream in = new FileInputStream(new File("wwprt-inbound.properties"));
		properties = new Properties();
		properties.load(in);
		
		int logLevel = Integer.parseInt(properties.getProperty("log.Level", "" + Log.VERBOSE));
		Log.init(logLevel, command);
		Log.v("Log level set to "+Log.level);
	}
	
	private void configure(String mqProperties) throws FileNotFoundException, IOException {
		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);

		MQ mq = new MQ(mqProperties);

		Context context = Context.get();
		context.setMq(mq);
		context.setConnectionFactory(connectionFactory);
		context.setupFromProperties(properties);

		interval = Integer.parseInt(properties.getProperty("interval", "500"));
	}
	private void configureCloud(String cloudPoperties) throws FileNotFoundException, IOException {
		ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);
		CloudantUtil.setup(cloudPoperties);
		CloudantContext  context = CloudantContext.get();
		context.setConnectionFactory(connectionFactory);
		context.setupFromProperties(properties);
		Context context1 = Context.get();
		context1.setConnectionFactory(connectionFactory);
		context1.setupFromProperties(properties);
		interval = Integer.parseInt(properties.getProperty("interval", "500"));
	}
	
	
	public void startCatcher() throws IOException {
		lockFile = new File(".catcher.lock");
		if (lockFile.exists()) {
			throw new IllegalStateException(
					"WWPRT Inbound Catcher is already running ("+lockFile.getName()+" file exists).");
		} else {
			lockFile.createNewFile();
		}
		Log.i("Starting WWPRT In-bound Catcher...");

		configure("mq.eacm.properties");

		catcher = new CatcherListener() {
			@Override
			public void onCheck() {
				if (!lockFile.exists()) {
					//Program shutdown
					WWPRTInbound.this.exit();
				}
			}

			@Override
			public void onStop() {
				if (lockFile.exists()) {
					lockFile.delete();
				}
			}
		};
		catcher.setInterval(interval);
		catcher.start();
	}

	public void startCloudCatcher(String t1 ,String t2) throws IOException {

		lockFile = new File(".catcher.lock");
		if (lockFile.exists()) {
			throw new IllegalStateException(
					"WWPRT Inbound Catcher is already running ("+lockFile.getName()+" file exists).");
		} else {
			lockFile.createNewFile();
		}
		Log.i("Starting WWPRT In-bound Catcher...");

		configureCloud("cloudant.eacm.properties");
		cloudantCatcher = new CloudantListener() {
			@Override
			public void onCheck() {
				if (!lockFile.exists()) {
					//Program shutdown
					WWPRTInbound.this.exit();
				}
			}

			@Override
			public void onStop() {
				if (lockFile.exists()) {
					lockFile.delete();
				}
			}
		};
		cloudantCatcher.setT1(t1);
		cloudantCatcher.setT2(t2);
		cloudantCatcher.setInterval(interval);
		cloudantCatcher.start();
	}
	public void startProcessor() throws IOException {
		lockFile = new File(".processor.lock");
		if (lockFile.exists()) {
			throw new IllegalStateException(
					"WWPRT Inbound Processor is already running ("+lockFile.getName()+" file exists).");
		} else {
			lockFile.createNewFile();
		}
		Log.i("Starting WWPRT In-bound Processor...");

		configure("mq.eacm.properties");
		processor = new Processor();
		processor.setProcessorListener(new ProcessorListener() {
			
			public void onCheck() {
				if (!lockFile.exists()) {
					//Program shutdown
					WWPRTInbound.this.exit();
				}
			}

			public void onStop() {
				if (lockFile.exists()) {
					lockFile.delete();
				}
			}
		});
		processor.setInterval(interval);
		processor.start();
	}

	public void exit() {
		if (lockFile.exists()) {
			lockFile.delete();
		}
		if (catcher != null) {
			catcher.stop();
		}
		if (processor != null) {
			processor.stop();
		}
	}
	
	private void test(String[] args) throws Exception {
		//First feed the MQ
		String xmlFile = args[1];
		File file = new File(xmlFile);
		if (!file.exists()) {
			Log.i("XML file not found: "+xmlFile);
			return;
		}
		
		try {
			MQ mq = new MQ("mq.test.out.properties");
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					Log.i("Loading test XML file: "+child.getName()+" in MQ");
					mq.putMessage(child);
				}
			} else {
				Log.i("Loading test XML file: "+xmlFile+" in MQ");
				mq.putMessage(file);
			}
			Log.i("Done! XML loaded in MQ.");
		} catch (Exception e) {
			Log.e("Error feeding test xml to MQ",e);
		}
		
		// Configure
		configure("mq.test.in.properties");
		
		if (args.length > 2) {
			String param = args[2];
			if ("asca-mq".equalsIgnoreCase(param)) {
				Context.get().setAcknowledgmentInterceptor(new AcknowledgmentInterceptor() {
					public void beforeSendInitialResponse() throws Exception {
						throw new Exception("Simulating MQ problem for Initial Response");
					}
					public void beforeSendFinalResponse() throws Exception {
						throw new Exception("Simulating MQ problem for Final Response");
					}
				});
			}
		}
		
		// Catch the XML
		Log.i("Starting the Catcher...");
		CatcherListener catcher = new CatcherListener();
		Log.i("Checking for messages...");
		catcher.checkMessages();
		
		// Process
		Log.i("Starting the Processor...");
		Processor processor = new Processor();
		processor.initialize();
		processor.execute();
		Log.i("Test completed");
	}

}
