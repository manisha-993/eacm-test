package com.ibm.eannounce.lenovo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Catcher {

	static final String TAG = "Catcher";
	private EntityManager entityManager;
	private Properties properties;

	
	public static void main(String[] args) {
		Catcher catcher = new Catcher("lenovo.properties", args);
		catcher.dispose();
	}

	public Catcher(String propertiesFile, String[] args) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int logLevel = Integer.parseInt(properties.getProperty(Keys.LOG_LEVEL, "" + Log.INFO));
		boolean logPersistent = Boolean.valueOf(
				properties.getProperty(Keys.LOG_PERSISTENT, "false")).booleanValue();
		Log.init(logLevel, logPersistent, new File("logs"), "lenovo");
	
		String T1date = getT1();
		if(T1date == null || T1date.equals("")) {
			T1date = "1980-01-01 00:00:00.000000";
		}
		if (args.length == 0) {
			//Read from MQ
			Boolean is = execute(T1date);
			String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
			DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
			if(is) updateT1(inDateTimeFormat.format(new Date()));
		}
	}

	public void dispose() {
		Log.close();
	}

	public Boolean execute(String T1) {
		Boolean isSuccess = false;
		entityManager = new EntityManager(properties);
		entityManager.connect();
		Log.i(TAG, "Reading table records...");
		
		long startTime = System.currentTimeMillis();
		try {
			List entitys = entityManager.getRecords(T1);
			for(int i = 0;i<entitys.size();i++) {
				MIWModel m = (MIWModel) entitys.get(i);
				
				entityManager.createEntity(m);
				Log.i(TAG, "create REFOFER entity: " + m.toString());
			}
			isSuccess = true;
		} catch (Exception e) {
			Log.e(TAG, "Unable to create REFOFER entity", e);	
			throw new IllegalStateException("Unable to create entity");
		} finally {
			entityManager.disconnect();
			long time = Math.abs(startTime - System.currentTimeMillis());
			Log.i(TAG, "Execution time: " + Math.round(time / 1000L));
		}
		return isSuccess;
	}
	
	private String getT1() {
		String date = null;
		try {
			FileInputStream in = new FileInputStream("tmp");
			ObjectInputStream s = new ObjectInputStream(in);
			date = (String)s.readObject();
			Log.i(TAG, "get Last Ran time: " + date);
			s.close();
		} catch (IOException | ClassNotFoundException e) {
			Log.e(TAG, "get Last Ran time exception: " + e);
		} 
		return date;
	}
	
	private void updateT1(String current) {
		
		try {
			FileOutputStream f = new FileOutputStream("tmp");
			ObjectOutputStream s = new ObjectOutputStream(f);
			s.writeObject(current);
			s.flush();
			Log.i(TAG, "Update Last Ran time to: " + current);
			s.close();
		} catch (IOException e) {
			Log.e(TAG, "Update Last Ran time exception: " + e);
		}
		
	}

}
