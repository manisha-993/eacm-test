package com.ibm.eannounce.wwprt.test;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.ibm.eannounce.wwprt.notification.Notification;
import com.ibm.eannounce.wwprt.notification.NotificationBuilder;

public class NotificationTestCase {

	@Test
	public void testNotification() throws IOException {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "9.56.224.216");
		properties.put("mail.debug", "true");
		properties.setProperty("mail.sender","opicmadm@eacmbh1.lexington.ibm.com");
			
		Notification notification = new Notification();
		notification.init(properties, "test_data/subscriptions.txt");
		
		NotificationBuilder nb = new NotificationBuilder("Testing 1,2,3");
		nb.append("This is a notification test").p("This is a paragraph");

		notification.postMail("test", nb.getTitle(), nb.getReport());
	}
	
}
