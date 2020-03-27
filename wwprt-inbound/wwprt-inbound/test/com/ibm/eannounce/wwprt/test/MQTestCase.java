package com.ibm.eannounce.wwprt.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.ibm.eannounce.wwprt.MQ;
import com.ibm.eannounce.wwprt.MQ.MessageListener;
import com.ibm.mq.MQMessage;


public class MQTestCase {

	@Test
	public void testSimpleMessage() throws Exception {
		MQ mq = new MQ(TestUtils.MQ_EACM);
		mq.putMessage("Testing 123");
		mq.getMessages(new StringMessageListener() {
			@Override
			public void readMessageString(String message) {
				Assert.assertEquals("Testing 123", message);
			}

			public void noMessagesToRead() throws Exception {
				System.out.println("No more messages");
			}
		});
		mq.close();
	}

	@Test
	public void testXMLMessage() throws Exception {
		MQ mq = new MQ(TestUtils.MQ_WWPRT);
		mq.putMessage(new File("test_data/utf.xml"));
		mq.getMessages(new StringMessageListener() {
			@Override
			public void readMessageString(String message) {
				System.out.println("utf.xml");
				System.out.println(message);
			}

			public void noMessagesToRead() throws Exception {
				System.out.println("No more messages");
			}
		});
		
		mq.putMessage(new File("test_data/prices.xml"));
		mq.getMessages(new StringMessageListener() {
			@Override
			public void readMessageString(String message) {
				System.out.println("price.xml");
				System.out.println(message);
			}

			public void noMessagesToRead() throws Exception {
				System.out.println("No more messages");
				
			}
		});
		mq.close();
	}
	
	abstract class StringMessageListener implements MessageListener {

		public void readMessage(MQMessage msg) throws Exception {
			byte[] b = new byte[msg.getMessageLength()];
			msg.readFully(b);
			//String messageString = msg.readUTF();
			String messageString = new String(b);
			readMessageString(messageString);
		}

		public abstract void readMessageString(String message);

	}
}

