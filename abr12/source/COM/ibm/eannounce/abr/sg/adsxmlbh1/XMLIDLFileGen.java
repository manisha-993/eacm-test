/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: XMLIDLFileGen.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2013
 *                LICENSED MATERIAL - PROGRAM PROPERTY OF IBM
 *                REFER TO COPYRIGHT INSTRUCTION FORM#G120-2083
 *                RESTRICTED MATERIALS OF IBM
 *                IBM CONFIDENTIAL
 *
 *   Version: 1.0
 *
 *   Functional Description: 
 *   A standalone Java program needs to be written to read messages from an MQSeries queue and write them to a file.
 *   
 *   Component : 
 *   Author(s) Name(s): Will
 *   Date of Creation: Jan 10, 2013
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   Jan 10, 2013     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

/**
 * The queue name to be read from specified in property file
The path and filename prefix for the file to be created specified in property file May want to be able to put entity type or XML message type in the filename?
Optionally the property file can contain a maximum number of messages to be written to file
File is created in path specified, filename is filename prefix || "." || current timestamp.   If file already exists, program terminates and writes message to report file
Report file created in same path, filename is filename prefix || "." || current timestamp ||".REPORT"

Conceptual Program Flow - I haven't included all the possible places where errors can occur.

Program tests whether queue can be accessed and whether it has any messages in it.
	If access fails or no messages, write appropriate message to report file and terminate.
Write Header Record to file.  Header record contains filename || " START"
Record Count = 0
Initialize Hash Total Follow up - we need an algorithm to create hash total based on records in file.  Does Wendy's FTP program include hash total?  Is there an reusable algorithm/Java routine available?
Do while (messages on queue) and (Record Count for any one msg type < Max records if specified in property file)
	Read message
	Write message as single line to file  Possible problem - can we write multibyte data to an ASCII file?
	Record count = Record count + 1
	Add record to Hash Total as per algorithm
End Do
Record Count = Record Count + 2 (include header and trailer record in count
Write trailer record to file.  Trailer record is Record Count || " " || Hash Total
Write success message to Report file

For overall architecture:

EACM BHFEED User uses IDL function to send messages to local EACM queue.
Standalone program is invoked ( how invoked??  Via command line?  Scheduled somehow? Is it "Catcher" that terminates at some point?)
EACM User reads report file to validate all records were sent via IDL were processed by program
EACM User / development / operations use SFTP to send file to destination

For ASCA purposes, write a desk procedure to cover details of this manual flow.

 * @author Will
 *
 */
public class XMLIDLFileGen {
	
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
	private ResourceBundle rsbd;
	private String reportfile=null;
	
	/**
	 * @param rsbd
	 */
	public XMLIDLFileGen(ResourceBundle rsbd) {
		super();
		this.rsbd = rsbd;		
	}
	
	private void setReportFile(String filename){
		reportfile = filename;
	}
	
	private void log(String log){
		saveDataToFile(reportfile, log, true);
	}
	
	private String getValfromProperties(String key) {
		try {
			return rsbd.getString(key);
		} catch (java.util.MissingResourceException e) {
			log(key + " is not existed in properties file. ");
		}
		return null;
	}

	public void getMsgToFile() {
		
		String file_prefix = "IDL_";
		String filepath = "./report/";
		try{
			file_prefix = (String) rsbd.getString("FILE_PREFIX");		
			filepath = (String) rsbd.getString("FILE_PATH");	
		}catch(Exception e){}
		if(!new File(filepath).exists()){
			new File(filepath).mkdir();
		}
		String currentTimestamp = getCurrentTimestamp();
		String report = filepath+file_prefix.trim()+"."+currentTimestamp + ".REPORT";
		this.setReportFile(report);
	
		// Set up MQSeries environment
		// get the name of your host to connect to
		String strUserId = (String) getValfromProperties( "MQUSERID");
		String strPassWord = (String) getValfromProperties("MQPASSWORD");
		String strPort = (String) getValfromProperties( "MQPORT");
		String strHostName = (String) getValfromProperties("MQHOSTNAME");
		String strChannel = (String) getValfromProperties("MQCHANNEL");
		String strMQManager = (String) getValfromProperties("MQMANAGER");
		String strMQQueue = (String) getValfromProperties( "MQQUEUE");
		String strSSL = (String) getValfromProperties( "MQSSL");
		String strKStore = (String) getValfromProperties( "KSTORE");
		String strKSPassword = (String) getValfromProperties("KSPASSWORD");
		String strTStore = (String) getValfromProperties( "TSTORE");
		String strTSPassword = (String) getValfromProperties("TSPASSWORD");
		
	
		String strMsgMax = (String) getValfromProperties("MSGMAX_IN_FILE");
		if (strMsgMax == null || strMsgMax.trim().length() == 0) {
			strMsgMax = "0";
		}
		int max = Integer.parseInt(strMsgMax);
		
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

		log("MQ Infor:" + strUserId + ":" + strPassWord
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
			log("***Starting to get MQ***");
			log("Creating a connection to the queue manager");
			
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

			// Set the get message options...
			MQGetMessageOptions gmo = new MQGetMessageOptions(); // accept
			gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_FAIL_IF_QUIESCING;
			if(local_queue.getCurrentDepth() > 0){			
			
				Hashtable countTab = new Hashtable();
				MessageDigest md = null;
				try{
					md = MessageDigest.getInstance("SHA1");
				}catch(Exception e){
					log(e.getMessage());
				}
				while (local_queue.getCurrentDepth() > 0) {
					MQMessage retrievedMessage = new MQMessage();
					// And prove we have the message by displaying the UTF message text	
					
					local_queue.get(retrievedMessage, gmo);
					String msgText = retrievedMessage.readLine();
					
					String filename = file_prefix.trim()+"_"+retrievedMessage.applicationIdData.trim()+"."+currentTimestamp;
					String fullfilename = filepath + filename;
					
					if(!(new File(fullfilename).exists())){		
						new File(fullfilename).createNewFile();
						String header = filename + " START";
						saveDataToFile(fullfilename,header,false);
					}
					byte[] dataBytes = (msgText+NEWLINE).getBytes("UTF-8");
					md.update(dataBytes);
					saveDataToFile(fullfilename,msgText,true);
					Object obj = countTab.get(fullfilename);
					int count = 0;
					if(obj != null){
						count = ((Integer)obj).intValue();
					}				
					count++;
					
					//TODO add hash count + match the hash count in the end user
					countTab.put(fullfilename, new Integer(count));
					if(max!=0 && count >= max) {
						log(filename+" Reach the defined max count: " + max);
						break;
					}		
				}
				byte[] mdbytes = md.digest();
				 
			    //convert the byte to hex format
			    StringBuffer sb = new StringBuffer("");
			    for (int i = 0; i < mdbytes.length; i++) {
			    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			    }		 
			    
				for (Iterator keys = countTab.keySet().iterator(); keys.hasNext();) {
					String key = (String) keys.next();
					int count=((Integer)(countTab.get(key))).intValue();
					String tailer = (count+2) + " " +sb.toString();
					saveDataToFile(key,tailer,true);
					log(key+ " " + tailer);
				}
			}else{
				log("***No Messge in the MQ***");
			}
			// Close the queue
			log("Closing the queue");
			local_queue.close();

			// Disconnect from the queue manager
			qMgr.commit();
			qMgr.disconnect();	

		} catch (MQException ex) {
			log("Error: An MQSeries error occurred : Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode);			
		} catch (java.io.IOException ex) {
			log("Error: An error occurred whilst writing to the message buffer: "
									+ ex);			
		}
	}
	
	public void saveDataToFile(String FileName, String Data, boolean append) {		
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(FileName, append), "UTF-8"));
			out.write(Data + NEWLINE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getCurrentTimestamp(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
        String tsStr = "";  
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss.SSS");  
        try { 
            tsStr = sdf.format(ts); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return tsStr;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String propFile = "IDLFILEGen";
		if(args.length > 0){
			propFile = args[0];
		}else{
			System.out.println("Use the defalut properties file - IDLFILEGen.properties");
		}
		ResourceBundle rsBundleMQ = ResourceBundle.getBundle(propFile);
		XMLIDLFileGen gen = new XMLIDLFileGen(rsBundleMQ);
		gen.getMsgToFile();

	}

}
