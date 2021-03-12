package com.ibm.eannounce.lenovo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import COM.ibm.opicmpdh.middleware.MiddlewareException;

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
		
		Log.i(TAG, "get T1");
		String T1date = getT1();
		if(T1date == null || T1date.equals("")) {
			T1date = "1980-01-01 00:00:00.000000";
		}
		if (args.length == 0) {
			//Read from MQ
			Boolean is = execute(T1date);
			Log.i(TAG, "create records " + (is==true?"succeed":"failed"));
			String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
			DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
			if(is) updateT1(inDateTimeFormat.format(new Date()));
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
			List ibmType = entityManager.getIbmType();
			List ibmPseudoType = entityManager.getIbmPseudoType();
			List entities = entityManager.getRecords(T1);
			entities = filterModelRule(entities, ibmType, ibmPseudoType);
			Log.i(TAG, "find " + entities.size()+ " records change");
			for(int i = 0;i<entities.size();i++) {
				MIWModel m = (MIWModel) entities.get(i);
				
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
	
	// need consummate
	private List filterModelRule(List entities, List ibmType, List ibmPseudoType) {
		
		for(int i = 0;i<entities.size();i++) {
			MIWModel m = (MIWModel) entities.get(i);
			/**
			Business rule 1: If the machine type is DCG (business rule 3) AND in the set that was made available for Lenovo (business rule 4) 
					AND type is alpha numeric AND type on the approved list from MIW AND there is a model in MTM (model not blank) 
					THEN it is a valid one to be used in IBM (can be used in CHIS/CONGA for contract).
			Business rule 2: If the machine type is DCG (business rule 3) AND in the set that was made available for Lenovo (business rule 4) 
					AND type numeric only AND type not matching with an IBM type (business rule 8) or an IBM pseudo type (business rule 7) 
					AND there is a model (model not blank) in MTM THEN it is valid to be used in IBM (can be used in CHIS/CONGA for a contract).
			Business rule 3: a type is DCG if the product division is 4S, Z3, 13, Y1 or G9
			Business rule 4: the set of types that was made available for Lenovo type model have a type that start with 1,2,3,4,5,6,7,8,9
			Business rule 5: If the machine type is NOT DCG (NOT business rule 3) AND in the set that was made available for Lenovo (business rule 4) 
					AND type numeric only AND type not the same as an IBM type (business rule 8) or an IBM pseudo type (business rule 7) 
					THEN it is valid to be used in IBM for Delivery (available in FedCat but not to be used in CHIS/CONGA contracts)
			Business rule 6: If a machine type is NOT DCG (NOT business rule 3) AND in the set that was made available for Lenovo (business rule 4) 
					AND type alpha numeric AND type not the same as an IBM type (business rule 8) or an IBM pseudo type (business rule 7) 
					THEN it is valid to be used in IBM for Delivery (available in FedCat but not to be used by CHIS/CONGA contracts)
			Business rule 7: all valid IBM pseudo types are registered in MIW
			Business rule 8: all valid IBM types are registered in EACM (edited)
			Business rule 9: If the machine type is DCG it needs to have a model. If the machine is NOT DCG it should be kept type only 
					and not be enriched with a model.
			*/

			String type = m.getPRODUCTID().substring(0, 3);
			Boolean filter = true;
			String div = m.getMKTGDIV();
			List<String> l = Arrays.asList("4S","Z3","13","Y1","G9");
			Pattern pattern = Pattern.compile("[1-9]*");
			Matcher isNum = pattern.matcher(type.charAt(0)+"");
			if(l.contains(div)&&isNum.matches()) {
				//DCG type				
				if(normalstr(type)&& ibmPseudoType.contains(type)) {
					filter = false;
				}
				if(type.matches("[0-9]+")&&(!ibmType.contains(type)||!ibmPseudoType.contains(type))) {
					filter = false;
				}
			}else if(!l.contains(div)&&isNum.matches()){
				if(type.matches("[0-9]+")&&(!ibmType.contains(type)||!ibmPseudoType.contains(type))) {
					filter = false;
				}
				if(normalstr(type)&&(!ibmType.contains(type)||!ibmPseudoType.contains(type))) {
					filter = false;
				}	
			}		
			if(filter) { 
				entities.remove(m);
			}
		}
		
		return entities;
	}
	/**
	 * check type is alpha numeric
	 * @param s
	 * @return
	 */
	public boolean normalstr(String s){
	   	int len = s.length();
	   	for(int i=0;i<len;i++) {
	   		char ch = s.charAt(i);
	   		if(!Character.isLetterOrDigit(ch)) {
	   			return false;
	   		}
	   	}
	   	return true;
	}
	
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
