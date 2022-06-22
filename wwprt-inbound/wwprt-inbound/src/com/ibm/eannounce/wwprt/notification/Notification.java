package com.ibm.eannounce.wwprt.notification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ibm.eannounce.wwprt.Log;

public class Notification {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-sss");
	
	private Properties properties;
	private List<String> subscriptions;
	
	public Notification() {
		subscriptions = new LinkedList<String>();
	}

	public void init(Properties properties, String subscriptionFilename) throws IOException {
		this.properties = properties;
		//Read subscription files
		subscriptions.clear();
		File subscriptionFile = new File(subscriptionFilename);
		if (subscriptionFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(subscriptionFile));
				String line;
				String output = "Subscriptions:\n";
				while ((line = reader.readLine()) != null) {
					if (line.trim().length() > 0) {
						subscriptions.add(line);
						output += line+"\n";
					}
				}
				output += "------------";
				Log.i(output);
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void post(String id, NotificationBuilder nb) {
		postMail(id, nb.getTitle(), nb.getReport());
	}
	
	
	
	public void postMail(String id, String subject, String message) {
		// Write to a file
		File rpt = new File("rpt");
		if (!rpt.exists()) {
			rpt.mkdirs();
		}
		if (id == null) {
			id = "";
		} else {
			id = id.replaceAll(" ", "-")+"_";
		}
		File report = new File(rpt,"rpt_"+id+dateFormat.format(new Date())+".htm");
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(report),"utf-8"));
			writer.write(message);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (subscriptions.size() == 0) {
			Log.v("No subscriptions - mail not sent:\nSubject: "+subject);
			return;
		}
		try {
			// create some properties and get the default Session
			Session session = Session.getDefaultInstance(properties, null);

			// create a message
			Message msg = new MimeMessage(session);

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(
					properties.getProperty("mail.sender","notification@eannounce.ibm.com"));
			msg.setFrom(addressFrom);

			InternetAddress[] addressTo = new InternetAddress[subscriptions.size()];
			for (int i = 0; i < subscriptions.size(); i++) {
				addressTo[i] = new InternetAddress(subscriptions.get(i));
			}
			if(mailValidate(addressTo)) {
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			}
			if(mailValidate(subject))
			{
				msg.setSubject(subject);
			}
			if(mailValidate(message))
			{
				msg.setContent(message, "text/html");
			}
			
			Transport.send(msg);
		} catch (MessagingException e) {
			if (Log.isLevel(Log.DEBUG)) {
				Log.e("Unable to send mail", e);
			} else {
				Log.e("Unable to send mail - "+e.getClass()+":"+e.getMessage());
			}
		}
	}

	private boolean mailValidate(Object value) {
		
		return true;
	}
}
