package com.ibm.eannounce.wwprt.notification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NotificationBuilder {

	private String title;
	
	private StringBuilder sb = new StringBuilder();

	public NotificationBuilder(String title) {
		this.title = title;
		h1(title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public NotificationBuilder append(String text) {
		sb.append(text);
		sb.append("\n");
		return this;
	}
	
	public NotificationBuilder h1(String text) {
		sb.append("<h1>");
		sb.append(text);
		sb.append("</h1>");
		sb.append("\n");
		return this;
	}
	
	public NotificationBuilder p(String text) {
		sb.append("<p>");
		sb.append(text);
		sb.append("</p>");
		sb.append("\n");
		return this;
	}
	
	public NotificationBuilder exception(Exception e) {
		sb.append("<p><b>");
		sb.append(e.getClass().getName());
		sb.append("</b><br>");
		sb.append(e.getMessage());
		sb.append("</p>");
		sb.append("\n");
		return this;
	}
	
	public String getReport() {
		try {
			String template = readAsString(new FileInputStream("report.htm"));
			template = template.replace("${title}", title);
			template = template.replace("${content}", sb.toString());
			return template;
		} catch (IOException e) {
			throw new IllegalStateException("Unable to generate report",e);
		}
	}

	private static String readAsString(InputStream inputStream) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

}
