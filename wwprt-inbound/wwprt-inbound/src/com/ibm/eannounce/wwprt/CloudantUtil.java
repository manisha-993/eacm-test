package com.ibm.eannounce.wwprt;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.PostFindOptions;
import com.ibm.cloud.sdk.core.security.BasicAuthenticator;
import com.ibm.mq.*;

import java.io.FileInputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

public class CloudantUtil {

	private static String apiKey;
	private static String cloudant_host;
	private static String cloudant_password;
	private static String cloudant_dbname;
	private static int span;
	private static int size;
	private static String propertiesFile="test.properties";
	private static boolean environmentSet;
	private static Cloudant client;
	private static DateTimeFormatter cloudant_time_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnnnnnXXX");

	private static int delay = 0;


	public CloudantUtil() {
		setup();
	}
	public CloudantUtil(String properties) {
		propertiesFile = properties;
		setup();
	}

	public static void setup(String properties) {
		propertiesFile = properties;
		setup();
	}
	@SuppressWarnings("unchecked")
	private static void setup() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(propertiesFile));
			apiKey = properties.getProperty("cloudant.apikey");
			cloudant_host = properties.getProperty("cloudant.host");
			cloudant_password= properties.getProperty("cloudant.password");
			cloudant_dbname = properties.getProperty("cloudant.dbname");
			span = Integer.parseInt(properties.getProperty("cloudant.span","4"));
			size = Integer.parseInt(properties.getProperty("cloudant.size","200"));
			delay = Integer.parseInt(properties.getProperty("delay", "0"));

			BasicAuthenticator authenticator = new BasicAuthenticator.Builder()
					.username(apiKey)
					.password(cloudant_password)
					.build();
			client = new Cloudant(Cloudant.DEFAULT_SERVICE_NAME,authenticator);
			client.setServiceUrl(cloudant_host);

			environmentSet = true;
		} catch (Throwable e) {
			e.printStackTrace();
			Log.e( "Unable to setup MQ Environment properties", e);
			environmentSet = false;
			throw new IllegalStateException("Unable to setup MQ Environment properties.", e);
		}
	}

	public static Cloudant getClient(){
		if(client==null){
			setup();
		}
		return client;
	}

	public static int getSpan() {
		return span;
	}

	public static void setSpan(int span) {
		CloudantUtil.span = span;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		CloudantUtil.size = size;
	}

	public static PostFindOptions getFindOptions(Map<String, Object> selector , long skip, int limit){
		PostFindOptions findOptions = new PostFindOptions.Builder()
				.db(cloudant_dbname).selector(selector).
				limit(limit).
				skip(skip).
				build();
		return findOptions;
	}

	public void close() {
		if (!environmentSet) {
			throw new IllegalStateException("MQ Environment not set. Call MQ.setup() before.");
		}

		try {

		} catch (Exception e) {
			Log.e( "Unable to close MQ Environment", e);
		}
	}
	public static String getNow(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnnXXX"));
		// OffsetDateTime  zonedDateTime = OffsetDateTime.parse("2023-02-03T21:03:54.465000000000+00:00", formatter);
		String dateTimeString = "2023-02-03T21:03:54.465000000000+00:00";
		System.out.println(dateTimeString.length());
		OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString.substring(0, 29) + dateTimeString.substring(32), formatter);
		System.out.println(offsetDateTime);
		return null;
	}
	public static String[] getPreviousTimePeriod(int span) {
		String timePeriod[] = new String[2];
		OffsetDateTime now = OffsetDateTime.now();
		int hour = now.getHour();
		if (hour < span) {
			now = now.minusHours(span);
		}
		int roundedHour = hour - (hour % span);
		OffsetDateTime t1 = now.withHour(roundedHour).withMinute(0).withSecond(0).withNano(0); // 设置整点时间
		OffsetDateTime t2 = t1.plusHours(span);
		timePeriod[0]=t1.format(cloudant_time_formatter);;
		timePeriod[1]=t2.format(cloudant_time_formatter);
		return timePeriod;
	}

	public static void main(String[] args) {
		getNow();
	}
	public interface MessageListener {
		public void readMessage(MQMessage msg) throws Exception;
		public void noMessagesToRead() throws Exception;
	}
}
