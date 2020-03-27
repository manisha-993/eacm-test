package com.ibm.eannounce.miw;

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

	private MQQueueManager queueManager;

	private MQQueue getQueue;
	
	private boolean skipMessageChars;

	static final String TAG = "MQ";

	public MQ(String propertiesFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
			setup(properties);
		} catch (IOException e) {
			Log.e(TAG, "Unable to load MQ properties file: " + propertiesFile, e);
			throw new IllegalStateException("Unable to load MQ properties file: " + propertiesFile);
		}
	}

	public MQ(Properties properties) {
		setup(properties);
	}

	private void setup(Properties properties) {
		try {
			MQEnvironment.hostname = properties.getProperty(Keys.MQ_HOSTNAME);
			MQEnvironment.channel = properties.getProperty(Keys.MQ_CHANNEL);
			MQEnvironment.port = Integer.parseInt(properties.getProperty(Keys.MQ_PORT));
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);

			skipMessageChars = Boolean.valueOf(properties.getProperty(Keys.MQ_SKIP_CHARS, "false")).booleanValue();
			queueManagerName = properties.getProperty(Keys.MQ_QUEUE_MANAGER);
			queueGetName = properties.getProperty(Keys.MQ_QUEUE_NAME);

			queueManager = new MQQueueManager(queueManagerName);

			int getOptions = MQC.MQOO_BROWSE | MQC.MQOO_INQUIRE;
			getQueue = queueManager.accessQueue(queueGetName, getOptions);

			environmentSet = true;
		} catch (Throwable e) {
			Log.e(TAG, "Unable to setup MQ Environment properties", e);
			environmentSet = false;
			throw new IllegalStateException("Unable to setup MQ Environment properties.");
		}
	}

	public int getQueuedMessageCount() {
		try {
			return getQueue.getCurrentDepth();
		} catch (MQException e) {
			Log.e(TAG, "Unable to query message count.", e);
			throw new IllegalStateException("Unable to query message count.");
		}
	}

	public boolean getMessages(MessageListener messageListener) throws MQException {
		int depth = getQueue.getCurrentDepth();
		if (depth > 0) {
			int outputOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING;
			MQQueue messageQueue = queueManager.accessQueue(queueGetName, outputOptions);
			for (int i = 0; i < depth; i++) {
				MQGetMessageOptions GMO = new MQGetMessageOptions();
				GMO.options = GMO.options + MQC.MQGMO_FAIL_IF_QUIESCING;
				MQMessage message = new MQMessage();
				messageQueue.get(message, GMO);
				messageListener.readMessage(message, skipMessageChars);
			}
			messageQueue.close();
			return true;
		}
		return false;
	}

	public boolean browseMessages(MessageListener messageListener) throws MQException {
		int depth = getQueue.getCurrentDepth();
		if (depth > 0) {
			int outputOptions = MQC.MQOO_BROWSE | MQC.MQOO_FAIL_IF_QUIESCING;
			MQQueue messageQueue = queueManager.accessQueue(queueGetName, outputOptions);
			for (int i = 0; i < depth; i++) {
				MQGetMessageOptions GMO = new MQGetMessageOptions();
				GMO.options = GMO.options + MQC.MQGMO_FAIL_IF_QUIESCING;
				if (i == 0) {
					GMO.options = GMO.options + MQC.MQGMO_BROWSE_FIRST;
				} else {
					GMO.options = GMO.options + MQC.MQGMO_BROWSE_NEXT;
				}
				MQMessage message = new MQMessage();
				messageQueue.get(message, GMO);
				messageListener.readMessage(message, skipMessageChars);
			}
			messageQueue.close();
			return true;
		}
		return false;
	}

	public void close() {
		if (!environmentSet) {
			throw new IllegalStateException("MQ Environment not set. Call MQ.setup() before.");
		}
		try {
			getQueue.close();
			queueManager.disconnect();
		} catch (Exception e) {
			Log.e(TAG, "Unable to close MQ Environment", e);
		}
	}

	public interface MessageListener {
		public void readMessage(MQMessage msg, boolean skipChars);
	}

	/**
	 * Put message on the inbound queue
	 * @param file
	 * @throws Exception
	 */
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

		MQPutMessageOptions PMO = new MQPutMessageOptions();
		PMO.options = MQC.MQPMO_SYNCPOINT + MQC.MQPMO_FAIL_IF_QUIESCING;

		int putOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
		MQQueue putQueue = queueManager.accessQueue(queueGetName, putOptions);
		putQueue.put(message, PMO);
		queueManager.commit();
	}
}
