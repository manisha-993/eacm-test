package com.ibm.eannounce.lenovo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
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
		boolean logPersistent = Boolean.valueOf(properties.getProperty(Keys.LOG_PERSISTENT, "false")).booleanValue();
		Log.init(logLevel, logPersistent, new File("logs"), "lenovo");

		Log.i(TAG, "get T1");
		String T1date = getT1();
		if (T1date == null || T1date.equals("")) {
			T1date = "1980-01-01 00:00:00.000000";
		}
		if (args.length == 0) {
			// Read from MQ
			Boolean is = execute(T1date);
			Log.i(TAG, "create records " + (is == true ? "succeed" : "failed"));
			String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
			DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
			if (is)
				updateT1(inDateTimeFormat.format(new Date()));
			Log.i(TAG, "Lenovo Catcher Finished.");
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
			List entities = entityManager.filterRecords(T1);
			
			List noDcgEntities = (List) entities.get(0);
			List dcgEntities = (List) entities.get(1);
			
			Log.i(TAG, "find " + noDcgEntities.size() + " Not DCG records change");
			Log.i(TAG, "find " + dcgEntities.size() + " DCG records change");
			for (int i = 0; i < noDcgEntities.size(); i++) {
				MIWModel m = (MIWModel) noDcgEntities.get(i);

				entityManager.createEntity(m);
				Log.i(TAG, "create No DCG REFOFER entity: " + m.toString());
			}
			for (int i = 0; i < dcgEntities.size(); i++) {
				MIWModel m = (MIWModel) dcgEntities.get(i);

				entityManager.createEntity(m);
				Log.i(TAG, "create DCG REFOFER entity: " + m.toString());
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

	// need consummate

	private String getT1() {
		String date = null;
		File file = new File("tmp");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			date = reader.readLine();
			Log.i(TAG, "get Last Ran time: " + date);
			reader.close();
		} catch (IOException e) {
			Log.e(TAG, "get Last Ran time exception: " + e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return date;
	}

	private void updateT1(String current) {

		try {

			File file = new File("tmp");
			if (!file.exists())
				file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(current);
			bw.flush();
			bw.close();
			Log.i(TAG, "Update Last Ran time to: " + current);
		} catch (IOException e) {
			Log.e(TAG, "Update Last Ran time exception: " + e);
		}

	}

}
