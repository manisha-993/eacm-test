package com.ibm.eannounce.wwprt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class MQ {

	private boolean environmentSet;

	private String queueManagerName;

	private String queueGetName;

	private String queuePutName;

	private MQQueueManager queueManager;

	private MQQueue putQueue;

	private MQQueue getQueue;
	
	private int delay = 0;

	public MQ(String propertiesFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
			setup(properties);
		} catch (IOException e) {
			Log.e( "Unable to load MQ properties file: " + propertiesFile, e);
		}
	}

	public MQ(Properties properties) {
		setup(properties);
	}

	@SuppressWarnings("unchecked")
	private void setup(Properties properties) {
		try {
			MQEnvironment.hostname = properties.getProperty("hostname");
			MQEnvironment.channel = properties.getProperty("channel");
			MQEnvironment.port = Integer.parseInt(properties.getProperty("port"));
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
			delay = Integer.parseInt(properties.getProperty("delay", "0"));
			
			queueManagerName = properties.getProperty("queueManager");
			queueGetName = properties.getProperty("queueGetName");
			queuePutName = properties.getProperty("queuePutName");
			queueManager = new MQQueueManager(queueManagerName);

			int putOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			putQueue = queueManager.accessQueue(queuePutName, putOptions);

			int getOptions = MQC.MQOO_BROWSE | MQC.MQOO_INQUIRE;
			getQueue = queueManager.accessQueue(queueGetName, getOptions);

			
			environmentSet = true;
		} catch (Throwable e) {
			e.printStackTrace();
			Log.e( "Unable to setup MQ Environment properties", e);
			environmentSet = false;
			throw new IllegalStateException("Unable to setup MQ Environment properties.", e);
		}
	}

	public void putMessage(File file) throws Exception {
		if (!environmentSet) {
			throw new IllegalStateException("MQ Environment not set. Call MQ.setup() before.");
		}


		MQMessage message = new MQMessage();
		message.format = MQC.MQFMT_STRING;
		message.messageType = MQC.MQMT_DATAGRAM;
		message.persistence = MQC.MQPER_PERSISTENT;
		
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
		
		//message.writeUTF(data);

		MQPutMessageOptions PMO = new MQPutMessageOptions();
		PMO.options = MQC.MQPMO_SYNCPOINT + MQC.MQPMO_FAIL_IF_QUIESCING;

		putQueue.put(message, PMO);
		queueManager.commit();
	}

	public void putMessage(String messageString) throws MQException, IOException {
		if (!environmentSet) {
			throw new IllegalStateException("MQ Environment not set. Call MQ.setup() before.");
		}

		MQMessage message = new MQMessage();
		message.format = MQC.MQFMT_STRING;
		message.messageType = MQC.MQMT_DATAGRAM;
		message.persistence = MQC.MQPER_PERSISTENT;
		message.writeString(messageString);

		MQPutMessageOptions PMO = new MQPutMessageOptions();
		PMO.options = MQC.MQPMO_SYNCPOINT + MQC.MQPMO_FAIL_IF_QUIESCING;

		putQueue.put(message, PMO);
		queueManager.commit();
	}

	public int getQueuedMessageCount() {
		try {
			return getQueue.getCurrentDepth();
		} catch (MQException e) {
			Log.e("Unable to query message count.");
			return 0;
		}
	}

	public boolean getMessages(MessageListener messageListener) throws Exception {
		try {
		int depth = getQueue.getCurrentDepth();
		if (depth > 0) {
			int outputOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING;
			MQQueue messageQueue = queueManager.accessQueue(queueGetName, outputOptions);
			for (int i = 0; i < depth; i++) {
				MQGetMessageOptions GMO = new MQGetMessageOptions();
				GMO.options = GMO.options + MQC.MQGMO_FAIL_IF_QUIESCING;
				MQMessage message = new MQMessage();
				messageQueue.get(message, GMO);
				messageListener.readMessage(message);
				if (delay > 0) {
					Log.v("Delaying read messages from MQ by: "+delay+"ms");
					Thread.sleep(delay);
				}
			}
			messageQueue.close();
			return true;
		}
		} catch (MQException e) {
			if (e.completionCode == 2 && e.reasonCode == 2033) {
				Log.e("MQException: MQJE001: Completion Code '2', Reason '2033' (Possible timeout)");
			} else {
				throw e;
			}
		}
		return false;
		
	}

	public void close() {
		if (!environmentSet) {
			throw new IllegalStateException("MQ Environment not set. Call MQ.setup() before.");
		}

		try {
			putQueue.close();
			queueManager.disconnect();
		} catch (Exception e) {
			Log.e( "Unable to close MQ Environment", e);
		}
	}

	public interface MessageListener {
		public void readMessage(MQMessage msg) throws Exception;
		public void noMessagesToRead() throws Exception;
	}
}
