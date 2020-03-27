package com.ibm.eannounce.miw;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {

	static String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
	static DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
	static int shortTimestampLength = sDateTimeFormat.length();
	
	/**
	 * Fix Defect 742829 : Error found during prod IDL of MIW data - 
	 * Incorrect Date/timestamp forma
	 * The dateformat is 2012-12-31 12:03:15.000000 (yyyy-MM-dd hh:mm:ss.SSSSSS)
	 * @param inputDate
	 * @return
	 * @throws ParseException
	 */
	public static String timestamp(String inputDate) throws ParseException {
		int dateLength= inputDate.length();
		if (dateLength == shortTimestampLength) {
			inDateTimeFormat.parse(inputDate);
		} else {
			if(dateLength>shortTimestampLength){
				inputDate = inputDate.substring(0,shortTimestampLength);
			}
			inDateTimeFormat.parse(inputDate);
		}
		return inputDate;
	}

}
