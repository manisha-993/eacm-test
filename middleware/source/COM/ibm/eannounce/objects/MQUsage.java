//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MQUsage.java,v $
// Revision 1.30  2012/06/21 02:27:26  liuweimi
// change Debug level for MQ User Properties and Remove System.out
//
// Revision 1.29  2012/03/05 13:21:57  liuweimi
// fix issue with MQ HDR timestamp - SET_IDENTITY_CONTEXT insteadof SET_ALL_CONTEXT
//
// Revision 1.28  2012/02/22 07:00:52  liuweimi
// CQ 88276 Problem with CORRELID being changed going through Corp IEB
// put the XML type (e.g. MODEL_UPDATE, SEO_UPDATE) -- left justified, padded to the right with blanks to a total of 32 characters -- in the MQMD field ApplIdentityData.
// base on BH FS ABR XML System Feed Mapping 20120220.doc
//
// Revision 1.27  2011/12/09 19:02:21  liuweimi
// Set format in the MQ msg Header for CEDS
//
// Revision 1.26  2011/08/23 12:42:32  guobin
// CR: support to Set Persistent message and No Expiry Date in MQ header
//
// Revision 1.25  2011/08/10 07:21:26  guobin
// add MQRFH v2 header to message
//
// Revision 1.24  2011/07/18 19:55:02  wendy
// control logging of xml msg in MQ props file
//
// Revision 1.23  2010/06/02 20:52:24  rick
// BH 1.0 changes -
// change for ADS to use writeString
// and UTF-8 (1208 characterset)
//
// Revision 1.22  2010/02/24 22:24:34  rick
// adding setting correlationId for ADS XML feed
//
// Revision 1.21  2009/03/16 07:21:46  yang
// *** empty log message ***
//
// Revision 1.19  2009/02/18 02:29:53  yang
// change the openOptions of getMQQueue to MQC.MQOO_INPUT_EXCLUSIV
//
// Revision 1.18  2009/02/18 02:07:11  yang
// change the getMQQueue from return void to return String.
//
// Revision 1.17  2009/02/12 20:31:33  yang
// adding GetMQQueue method
//
// Revision 1.16  2008/05/28 13:42:10  wendy
// Added MQCID constant
//
// Revision 1.15  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2007/08/29 02:25:17  yang
// backout of the writeUTF change until later notice
//
// Revision 1.13  2007/08/28 05:49:55  yang
// update from writeUTF() to writeBytes()
//
// Revision 1.12  2007/03/14 16:08:14  wendy
// Add method to allow access to exceptions
//
// Revision 1.11  2006/06/21 15:24:21  joan
// changes
//
// Revision 1.10  2006/04/13 00:21:54  joan
// fixes
//
// Revision 1.9  2006/04/12 15:07:25  joan
// put in debug statement
//
// Revision 1.8  2006/04/12 15:06:46  joan
// put in debug statements
//
// Revision 1.7  2006/03/02 23:52:07  joan
// fixes
//
// Revision 1.6  2006/03/02 23:02:07  joan
// fixes
//
// Revision 1.5  2006/03/02 22:25:39  joan
// add SSL
//
// Revision 1.4  2006/02/20 17:53:06  joan
// fix compile
//
// Revision 1.3  2006/02/20 17:41:09  joan
// change System.out.println to D.ebug statements
//
// Revision 1.2  2005/10/25 20:37:59  joan
// fixes
//
// Revision 1.1  2005/10/25 16:16:27  joan
// intial load
//

package COM.ibm.eannounce.objects;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;
import java.util.*;

import com.ibm.mq.*;

/**
 * MQUsage
 * 
 * @author Joan Tran To change the template for this generated type comment go
 *         to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 *         Comments
 */
public class MQUsage {

	/**
	 * MQUsage
	 *  
	 * @author Joan Tran
	 */

	public static final String MQCID = "MQCID";
	public static final String NOTIFY = "NOTIFY";
	public static final String MQUSERID = "MQUSERID";
	public static final String MQPASSWORD = "MQPASSWORD";
	public static final String MQPORT = "MQPORT";
	public static final String MQHOSTNAME = "MQHOSTNAME";
	public static final String MQCHANNEL = "MQCHANNEL";
	public static final String MQMANAGER = "MQMANAGER";
	public static final String MQQUEUE = "MQQUEUE";
	public static final String MQSTRFORMAT_IN_HEADER = "MQSTRFORMAT_IN_HEADER";
	public static final String MQSSL = "MQSSL";
	public static final String KSTORE = "KSTORE";
	public static final String KSPASSWORD = "KSPASSWORD";
	public static final String TSTORE = "TSTORE";
	public static final String TSPASSWORD = "TSPASSWORD";
	public static final String TRUE = "TRUE";
	public static final String XMLTYPE = "XMLTYPE";
	public static final String MSGPERSISTENT = "MSGPERSISTENT";
	public static final String MSGLOGLEVEL = "MSGLOGLEVEL";
	public static final String HEADER = "HEADER_";
	public static final String HEADER_KEYS = "HEADER_PROPERTY_KEYS";
	public static final String HEADER_DEFAULT = "HEADER_DEFAULT_";

	// private static MQQueueManager qMgr = null; moved inside method to make
	// threadsafe
	// private static MQQueue local_queue = null;
	/*
	 * private class MyMQExits implements MQSendExit, MQReceiveExit,
	 * MQSecurityExit { // This method comes from the send exit public byte[]
	 * sendExit(MQChannelExit channelExitParms, MQChannelDefinition
	 * channelDefParms,byte agentBuffer[]) { // fill in the body of the send
	 * exit here return agentBuffer; } // This method comes from the receive
	 * exit public byte[] receiveExit(MQChannelExit channelExitParms,
	 * MQChannelDefinition channelDefParms, byte agentBuffer[]) { // fill in the
	 * body of the receive exit here return agentBuffer; } // This method comes
	 * from the security exit public byte[] securityExit(MQChannelExit
	 * channelExitParms, MQChannelDefinition channelDefParms, byte
	 * agentBuffer[]) { // fill in the body of the security exit here return
	 * agentBuffer; } }
	 */
	public static void putToMQ(String _strMsg, Hashtable _htMQVars) {
		try {
			putToMQQueue(_strMsg, _htMQVars);
		} catch (Exception e) {
			// ignore this, already handled and caller doesn't want it
		}
	}

	// need exceptions to come back for this
	public static String GetMQQueue(Hashtable _htMQVars) throws MQException,
			java.io.IOException {
		String strTraceBase = " getFromMQ method";
		// Set up MQSeries environment
		// get the name of your host to connect to
		String strUserId = (String) _htMQVars.get(MQUSERID);
		String strPassWord = (String) _htMQVars.get(MQPASSWORD);
		String strPort = (String) _htMQVars.get(MQPORT);
		String strHostName = (String) _htMQVars.get(MQHOSTNAME);
		String strChannel = (String) _htMQVars.get(MQCHANNEL);
		String strMQManager = (String) _htMQVars.get(MQMANAGER);
		String strMQQueue = (String) _htMQVars.get(MQQUEUE);
		String strSSL = (String) _htMQVars.get(MQSSL);
		String strKStore = (String) _htMQVars.get(KSTORE);
		String strKSPassword = (String) _htMQVars.get(KSPASSWORD);
		String strTStore = (String) _htMQVars.get(TSTORE);
		String strTSPassword = (String) _htMQVars.get(TSPASSWORD);		
		String strMsgLogLvl = (String) _htMQVars.get(MSGLOGLEVEL);
		if(strMsgLogLvl==null||strMsgLogLvl.trim().length()==0){
			strMsgLogLvl = "2";
		}
		int msgLogLvl = Integer.parseInt(strMsgLogLvl);
		String msgText = null;

		if (strUserId != null && strUserId.length() > 0) {
			MQEnvironment.userID = strUserId;
		}
		if (strPassWord != null && strPassWord.length() > 0) {
			MQEnvironment.password = strPassWord;
		}
		if (strPort != null && strPort.length() > 0) {
			MQEnvironment.port = Integer.parseInt(strPort);
		}
		if (strSSL != null && strSSL.length() > 0) {
			MQEnvironment.sslCipherSuite = strSSL;
			if (strKStore != null && strKStore.length() > 0) {
				System.setProperty("javax.net.ssl.keyStore", strKStore);
			}
			if (strKSPassword != null && strKSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.keyStorePassword",
						strKSPassword);
			}
			if (strTStore != null && strTStore.length() > 0) {
				System.setProperty("javax.net.ssl.trustStore", strTStore);
			}
			if (strTSPassword != null && strTSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.trustStorePassword",
						strTSPassword);
			}
		}

		D.ebug(D.EBUG_SPEW, strTraceBase + ":" + strUserId + ":" + strPassWord
				+ ":" + strPort + ":" + strHostName + ":" + strChannel + ":"
				+ strMQManager + ":" + strMQQueue);
		MQEnvironment.hostname = strHostName;
		// define name of channel for client to use.
		// Note. assumes MQSeries Server is listening on the default
		// TCPIP port of 1414
		MQEnvironment.channel = strChannel;

		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,// Set TCP/IP or
				// server
				MQC.TRANSPORT_MQSERIES);// Connection

		try {
			// define name of queue manager object to connect to.
			// Create a connection to the queue manager
			D.ebug(D.EBUG_INFO,strTraceBase + " ***Starting to get MQ***");
			D.ebug(D.EBUG_SPEW, strTraceBase
					+ " creating a connection to the queue manager");
			MQQueueManager qMgr = new MQQueueManager(strMQManager);

			// Set up the options on the queue we wish to open...
			// Note. All MQSeries Options are prefixed with MQC in Java.
			// int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT ;
			int openOptions = MQC.MQOO_INPUT_EXCLUSIVE
					| MQC.MQOO_INPUT_AS_Q_DEF;

			// Now specify the queue that we wish to open, and the open
			// options...
			MQQueue local_queue = qMgr.accessQueue(strMQQueue, openOptions,
					null, // default q manager
					null, // no dynamic q name
					null); // no alternate user id

			// Define a simple MQSeries message, and write some text in UTF
			// format..
			MQMessage hello_world = new MQMessage();
			// hello_world.writeUTF(msgText);
			// hello_world.writeBytes(msgText);

			// get the message
			// First define a WebSphere MQ message buffer to receive the message
			// into..

			MQMessage retrievedMessage = new MQMessage();
			retrievedMessage.messageId = hello_world.messageId;

			// Set the get message options...

			MQGetMessageOptions gmo = new MQGetMessageOptions(); // accept
			// the
			// defaults

			// get the message off the queue...

			local_queue.get(retrievedMessage, gmo);

			// And prove we have the message by displaying the UTF message text

			msgText = retrievedMessage.readUTF();
			D.ebug(msgLogLvl, strTraceBase +" The message is: " + msgText);

			/** ************************ */
			/* Set up a loop exit flag */
			/** ************************ */
			boolean done = false;
			do {
				try {
					/** ************************************** */
					/* Reset the message and IDs to be empty */
					/** ************************************** */
					D.ebug(D.EBUG_DETAIL,strTraceBase +" Removing retrieved Message: "
							+ retrievedMessage);
					retrievedMessage.clearMessage();
					retrievedMessage.correlationId = hello_world.messageId;
					retrievedMessage.messageId = hello_world.messageId;

					done = true;
				} catch (java.io.IOException ex) {
					D.ebug(D.EBUG_ERR,strTraceBase +" Java exception: " + ex);
					done = true;
				}

			} while (!done);

			// Close the queue
			D.ebug(D.EBUG_SPEW, strTraceBase + " closing the queue");
			local_queue.close();

			// Disconnect from the queue manager
			qMgr.commit();
			qMgr.disconnect();

			return msgText;

		} catch (MQException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An MQSeries error occurred : Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode);
			throw ex;
		} catch (java.io.IOException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An error occurred whilst writing to the message buffer: "
							+ ex);
			throw ex;
		}
	}

	// need exceptions to come back for this
	// get all message by yangxiaojiang
	public static Vector GetAllMQQueue(Hashtable _htMQVars) throws MQException,
			java.io.IOException {
		Vector vector = new Vector();
		String strTraceBase = " getAllMessagesFromMQ method";
		// Set up MQSeries environment
		// get the name of your host to connect to
		String strUserId = (String) _htMQVars.get(MQUSERID);
		String strPassWord = (String) _htMQVars.get(MQPASSWORD);
		String strPort = (String) _htMQVars.get(MQPORT);
		String strHostName = (String) _htMQVars.get(MQHOSTNAME);
		String strChannel = (String) _htMQVars.get(MQCHANNEL);
		String strMQManager = (String) _htMQVars.get(MQMANAGER);
		String strMQQueue = (String) _htMQVars.get(MQQUEUE);
		String strSSL = (String) _htMQVars.get(MQSSL);
		String strKStore = (String) _htMQVars.get(KSTORE);
		String strKSPassword = (String) _htMQVars.get(KSPASSWORD);
		String strTStore = (String) _htMQVars.get(TSTORE);
		String strTSPassword = (String) _htMQVars.get(TSPASSWORD);
		String strMsgLogLvl = (String) _htMQVars.get(MSGLOGLEVEL);
		if(strMsgLogLvl==null||strMsgLogLvl.trim().length()==0){
			strMsgLogLvl = "2";
		}
		int msgLogLvl = Integer.parseInt(strMsgLogLvl);
		String msgText = null;

		if (strUserId != null && strUserId.length() > 0) {
			MQEnvironment.userID = strUserId;
		}
		if (strPassWord != null && strPassWord.length() > 0) {
			MQEnvironment.password = strPassWord;
		}
		if (strPort != null && strPort.length() > 0) {
			MQEnvironment.port = Integer.parseInt(strPort);
		}
		if (strSSL != null && strSSL.length() > 0) {
			MQEnvironment.sslCipherSuite = strSSL;
			if (strKStore != null && strKStore.length() > 0) {
				System.setProperty("javax.net.ssl.keyStore", strKStore);
			}
			if (strKSPassword != null && strKSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.keyStorePassword",
						strKSPassword);
			}
			if (strTStore != null && strTStore.length() > 0) {
				System.setProperty("javax.net.ssl.trustStore", strTStore);
			}
			if (strTSPassword != null && strTSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.trustStorePassword",
						strTSPassword);
			}
		}

		D.ebug(D.EBUG_SPEW, strTraceBase + ":" + strUserId + ":" + strPassWord
				+ ":" + strPort + ":" + strHostName + ":" + strChannel + ":"
				+ strMQManager + ":" + strMQQueue);
		
		MQEnvironment.hostname = strHostName;
		// define name of channel for client to use.
		// Note. assumes MQSeries Server is listening on the default
		// TCPIP port of 1414
		MQEnvironment.channel = strChannel;

		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,// Set TCP/IP or
				// server
				MQC.TRANSPORT_MQSERIES);// Connection

		try {
			// define name of queue manager object to connect to.
			// Create a connection to the queue manager
			D.ebug(D.EBUG_INFO,strTraceBase + " ***Starting to get MQ***");
			D.ebug(D.EBUG_SPEW, strTraceBase
					+ " creating a connection to the queue manager");
			MQQueueManager qMgr = new MQQueueManager(strMQManager);

			// Set up the options on the queue we wish to open...
			// Note. All MQSeries Options are prefixed with MQC in Java.
			// int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT ;

			int openOptions = MQC.MQOO_FAIL_IF_QUIESCING
			| MQC.MQOO_INPUT_SHARED | MQC.MQOO_INQUIRE;

			// Now specify the queue that we wish to open, and the open
			// options...
			MQQueue local_queue = qMgr.accessQueue(strMQQueue, openOptions,
					null, // default q manager
					null, // no dynamic q name
					null); // no alternate user id

			// Define a simple MQSeries message, and write some text in UTF
			// format..
//			MQMessage hello_world = new MQMessage();
			// hello_world.writeUTF(msgText);
			// hello_world.writeBytes(msgText);

			// get the message
			// First define a WebSphere MQ message buffer to receive the message
			// into..

//			MQMessage retrievedMessage = new MQMessage();
			// retrievedMessage.messageId = hello_world.messageId;

			// Set the get message options...

			MQGetMessageOptions gmo = new MQGetMessageOptions(); // accept
			gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_FAIL_IF_QUIESCING;
			
			
			// the
			// defaults

//			int depth = local_queue.getCurrentDepth();// get the message's
			// depth
			while(local_queue.getCurrentDepth()>0){
				MQMessage retrievedMessage = new MQMessage();
//			for (int i = 0; i < depth; i++) {
				// get the message off the queue...

				// local_queue.get(retrievedMessage, gmo);

				// And prove we have the message by displaying the UTF message
				// text

				local_queue.get(retrievedMessage, gmo);
				msgText = retrievedMessage.readUTF();

//				msgText = retrievedMessage.readLine();
				D.ebug(msgLogLvl, strTraceBase +" The message is: " + msgText);
				vector.addElement(msgText);
			}

			/** ************************ */
			/* Set up a loop exit flag */
			/** ************************ */
//			boolean done = false;
//			do {
//				try {
//					/** ************************************** */
//					/* Reset the message and IDs to be empty */
//					/** ************************************** */
//					System.out.println("Removing retrieved Message: "
//							+ retrievedMessage);
//					retrievedMessage.clearMessage();
//					retrievedMessage.correlationId = hello_world.messageId;
//					retrievedMessage.messageId = hello_world.messageId;
//
//					done = true;
//				} catch (java.io.IOException ex) {
//					System.out.println("Java exception: " + ex);
//					done = true;
//				}
//
//			} while (!done);

			// Close the queue
			D.ebug(D.EBUG_SPEW, strTraceBase + " closing the queue");
			local_queue.close();

			// Disconnect from the queue manager
			qMgr.commit();
			qMgr.disconnect();

			return vector;

		} catch (MQException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An MQSeries error occurred : Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode);
			throw ex;
		} catch (java.io.IOException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An error occurred whilst writing to the message buffer: "
							+ ex);
			throw ex;
		}
	}

	// need exceptions to come back for this
	public static void putToMQQueue(String _strMsg, Hashtable _htMQVars)
			throws MQException, java.io.IOException {
		putToMQQueueWithRFH2(_strMsg, _htMQVars, null);
	}

	/**
	 * 
	 * @param _strMsg Message 
	 * @param _htMQVars MQ information that should be used to connect to queue
	 * @param _htUserInfor The properties that should be in RFH2 header, if it is null, will not generate the RFH2 header
	 * @throws MQException
	 * @throws IOException
	 */
	public static void putToMQQueueWithRFH2(String _strMsg, Hashtable _htMQVars, Hashtable _htUserInfor)
			throws MQException, IOException {
		String strTraceBase = " putToMQ method";
		// Set up MQSeries environment
		// get the name of your host to connect to
		String strUserId = (String) _htMQVars.get(MQUSERID);
		String strPassWord = (String) _htMQVars.get(MQPASSWORD);
		String strPort = (String) _htMQVars.get(MQPORT);
		String strHostName = (String) _htMQVars.get(MQHOSTNAME);
		String strChannel = (String) _htMQVars.get(MQCHANNEL);
		String strMQManager = (String) _htMQVars.get(MQMANAGER);
		String strMQQueue = (String) _htMQVars.get(MQQUEUE);
		String strSSL = (String) _htMQVars.get(MQSSL);
		boolean isMQSTRINGFORMAT = ((Boolean) _htMQVars.get(MQSTRFORMAT_IN_HEADER)).booleanValue();
		String strKStore = (String) _htMQVars.get(KSTORE);
		String strKSPassword = (String) _htMQVars.get(KSPASSWORD);
		String strTStore = (String) _htMQVars.get(TSTORE);
		String strTSPassword = (String) _htMQVars.get(TSPASSWORD);
		String strMQCid = (String) _htMQVars.get(MQCID);
		String strXMLType = (String) _htMQVars.get(XMLTYPE);
		String strMsgPersistent = (String) _htMQVars.get(MSGPERSISTENT);
		String strMsgLogLvl = (String) _htMQVars.get(MSGLOGLEVEL);
		if(strMsgLogLvl==null||strMsgLogLvl.trim().length()==0){
			strMsgLogLvl = "2";
		}
		int msgLogLvl = Integer.parseInt(strMsgLogLvl);

		if (strUserId != null && strUserId.length() > 0) {
			MQEnvironment.userID = strUserId;
		}
		if (strPassWord != null && strPassWord.length() > 0) {
			MQEnvironment.password = strPassWord;
		}
		if (strPort != null && strPort.length() > 0) {
			MQEnvironment.port = Integer.parseInt(strPort);
		}
		if (strSSL != null && strSSL.length() > 0) {
			MQEnvironment.sslCipherSuite = strSSL;
			if (strKStore != null && strKStore.length() > 0) {
				System.setProperty("javax.net.ssl.keyStore", strKStore);
			}
			if (strKSPassword != null && strKSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.keyStorePassword",
						strKSPassword);
			}
			if (strTStore != null && strTStore.length() > 0) {
				System.setProperty("javax.net.ssl.trustStore", strTStore);
			}
			if (strTSPassword != null && strTSPassword.length() > 0) {
				System.setProperty("javax.net.ssl.trustStorePassword",
						strTSPassword);
			}
		}
		
		D.ebug(D.EBUG_SPEW, strTraceBase + ":" + strUserId + ":" + strPassWord
				+ ":" + strPort + ":" + strHostName + ":" + strChannel + ":"
				+ strMQManager + ":" + strMQQueue);
		
		MQEnvironment.hostname = strHostName;
		// define name of channel for client to use.
		// Note. assumes MQSeries Server is listening on the default
		// TCPIP port of 1414
		MQEnvironment.channel = strChannel;

		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,// Set TCP/IP or
				// server
				MQC.TRANSPORT_MQSERIES);// Connection

		try {
			// define name of queue manager object to connect to.
			// Create a connection to the queue manager
			D.ebug(D.EBUG_SPEW, strTraceBase
					+ " creating a connection to the queue manager");
			MQQueueManager qMgr = new MQQueueManager(strMQManager);

			// Set up the options on the queue we wish to open...
			// Note. All MQSeries Options are prefixed with MQC in Java.
			// int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT ;
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE | MQC.MQOO_SET_IDENTITY_CONTEXT ;

			// Now specify the queue that we wish to open, and the open
			// options...
			MQQueue local_queue = qMgr.accessQueue(strMQQueue, openOptions,
					null, // default q manager
					null, // no dynamic q name
					null); // no alternate user id

			// Define a simple MQSeries message, and write some text in UTF
			// format..
			MQMessage hello_world = new MQMessage();
			
			if (strMQCid != null && strMQCid.length() > 0) {
				hello_world.correlationId = strMQCid.getBytes();
				hello_world.applicationIdData = strMQCid;//added it based on BH FS ABR XML System Feed Mapping 20120220.doc
				D.ebug(D.EBUG_SPEW,strTraceBase +" MQCID is: " + strMQCid);
			} 
			if(isMQSTRINGFORMAT){			
				hello_world.format = MQC.MQFMT_STRING; 
				D.ebug(msgLogLvl, "Setting String format in the Header");
			}
			// If an ADS msg then we must use writeString to accomodate a larger then 
			// 64k length msg.
			if (strXMLType != null && strXMLType.length() > 0 &&
				strXMLType.equals("ADS")) {
				hello_world.characterSet = 1208;	
				hello_world.writeString(_strMsg);
			}
			else {
				hello_world.writeUTF(_strMsg);
			}
			
			// hello_world.writeBytes(_strMsg);

			// specify the message options...
			MQPutMessageOptions pmo = new MQPutMessageOptions(); // accept
			// the
			// defaults,
			// same as
			// MQPMO_DEFAULT
			// constant
			pmo.options = MQC.MQPMO_SET_IDENTITY_CONTEXT;
			if(strMsgPersistent == null || "0".equals(strMsgPersistent)){
				hello_world.persistence = MQC.MQPER_NOT_PERSISTENT;
			}else if("1".equals(strMsgPersistent)){
				hello_world.persistence = MQC.MQPER_PERSISTENT;
			}else if("2".equals(strMsgPersistent)){
				hello_world.persistence = MQC.MQPER_PERSISTENCE_AS_Q_DEF;
			}else{
				D.ebug(D.EBUG_WARN,"the value of persistence is invaild, set it to default value: MQPER_NOT_PERSISTENT.");
				hello_world.persistence = MQC.MQPER_NOT_PERSISTENT;
			}
			
			//Add user properties to MQRFH2 header using set properties in MQ7
			if(_htUserInfor != null && _htUserInfor.size() > 0){
				for (Iterator it = _htUserInfor.keySet().iterator(); it
						.hasNext();) {
					String key = (String) it.next();
					String value = (String)_htUserInfor.get(key);
					if(value != null){
						hello_world.setStringProperty(key,value);
					}					
				}
//				hello_world.setStringProperty("ServiceURI", "/BH/MDM/COMMON/PDMI/IN");
			}

			// put the message on the queue
			//System.out.println( change to control output thru props file
			D.ebug(msgLogLvl, strTraceBase
					+ " putting the message on the queue" + _strMsg);
			local_queue.put(hello_world, pmo);

			// Close the queue
			D.ebug(D.EBUG_SPEW, strTraceBase + " closing the queue");
			local_queue.close();

			// Disconnect from the queue manager
			qMgr.commit();
			qMgr.disconnect();

		} catch (MQException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An MQSeries error occurred : Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode);
			throw ex;
		} catch (java.io.IOException ex) {
			D.ebug(D.EBUG_ERR,strTraceBase +" An error occurred whilst writing to the message buffer: "
							+ ex);
			throw ex;
		}
	}

	public static Hashtable getMQSeriesVars(ResourceBundle _rsBundle) {
		Hashtable ht = new Hashtable();
		ht.put(NOTIFY, new Boolean(_rsBundle.getString(NOTIFY)
				.equalsIgnoreCase(TRUE)));
		ht.put(MQUSERID, _rsBundle.getString(MQUSERID));
		ht.put(MQPASSWORD, _rsBundle.getString(MQPASSWORD));
		ht.put(MQPORT, _rsBundle.getString(MQPORT));
		ht.put(MQHOSTNAME, _rsBundle.getString(MQHOSTNAME));
		ht.put(MQCHANNEL, _rsBundle.getString(MQCHANNEL));
		ht.put(MQMANAGER, _rsBundle.getString(MQMANAGER));
		
		ht.put(MQQUEUE, _rsBundle.getString(MQQUEUE));
		ht.put(MQSSL, _rsBundle.getString(MQSSL));
		ht.put(KSTORE, _rsBundle.getString(KSTORE));
		ht.put(KSPASSWORD, _rsBundle.getString(KSPASSWORD));
		ht.put(TSTORE, _rsBundle.getString(TSTORE));
		ht.put(TSPASSWORD, _rsBundle.getString(TSPASSWORD));
		try{
			ht.put(MSGPERSISTENT, _rsBundle.getString(MSGPERSISTENT));
		}catch(java.util.MissingResourceException e){
			D.ebug(D.EBUG_WARN, "getMQSeriesVars "+e.getMessage());
		}	
		try{
			ht.put(MQSTRFORMAT_IN_HEADER, new Boolean(TRUE.equalsIgnoreCase(_rsBundle.getString(MQSTRFORMAT_IN_HEADER))));
		}catch(java.util.MissingResourceException e){
			D.ebug(D.EBUG_WARN, "getMQSeriesVars "+e.getMessage());
			ht.put(MQSTRFORMAT_IN_HEADER,new Boolean(false));
		}
		
		try{
			//this may not be specified, if it doesnt exist there will be an exception
			ht.put(MSGLOGLEVEL, _rsBundle.getString(MSGLOGLEVEL));
		}catch(java.util.MissingResourceException e){
			D.ebug(D.EBUG_ERR, "getMQSeriesVars "+e.getMessage()+" using MSGLOGLEVEL=2");
		}
		return ht;
	}
	
	public static Hashtable getUserProperties(ResourceBundle rsbds,String Prefix){
		Hashtable ht = new Hashtable();

		String keys = getValfromProperties(rsbds, HEADER_KEYS);		
		
		if(keys == null){
			return null;
		}
		String[] keyArray = MQUsage.getArray(keys, ",");		
		for (int i = 0; i < keyArray.length; i++) {
			String realKey = keyArray[i];
			String key = HEADER + Prefix + "_" + realKey;
			String value = (String)getValfromProperties(rsbds, key);
			if(value ==null){
				D.ebug(D.EBUG_SPEW, "getUserProperties: Use default value for " + key);
				value = getValfromProperties(rsbds, HEADER_DEFAULT + realKey);								
			}
			if(value !=null){
				ht.put(realKey, value);
			}
		}

		D.ebug(D.EBUG_SPEW, "getUserProperties "+ ht);
		return ht;
	}

	private static String getValfromProperties(ResourceBundle rsbds, String key) {
		try{
			return rsbds.getString(key);
		}catch(java.util.MissingResourceException e){
			D.ebug(D.EBUG_SPEW, key + " is not existed in properties file. ");
		}
		return null;
	}
	
	private static String[] getArray(String value, String delimiter) 
	{
		if(value == null || value.trim().equalsIgnoreCase("")) {
			return null;
		}
		
		int position = 0; 
		String element = null;
		String[] elements = null;
	 	Vector items = new Vector();      
		
	 	try {
			while(true){
				if ((position = value.indexOf(delimiter)) == -1) break;
				element = value.substring(0, position);
				value = value.substring(position + delimiter.length());
				items.add(element.trim());
			}
			items.add(value.trim());

			elements = new String[items.size()];
			for(int i=0; i<items.size(); i++) elements[i] = (String)items.get(i);			
			return elements;
	 	}
	 	catch(Throwable ex){
	 		D.ebug(D.EBUG_ERR, "getArray "+ex.getMessage()+" using MSGLOGLEVEL=2");
	 	}
		return null;
	}
}
