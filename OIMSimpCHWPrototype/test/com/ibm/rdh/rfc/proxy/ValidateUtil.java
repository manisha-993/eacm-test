package com.ibm.rdh.rfc.proxy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValidateUtil {
	public static String getDateFromCalendar(Calendar calendar, String format){    	
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	String dateStr = sdf.format(calendar.getTime());
    	return dateStr;
    }
	
	public static String getDateString(Date date, String format){
		String sReturn = "";
		if(date!=null){
			SimpleDateFormat sf=new SimpleDateFormat(format);
			sReturn=sf.format(date);
		}
		return sReturn;
		
	}
	
	public static String getPadBlankChar(String str, int all){
		int count = str.length();
		String result = str;
		for(int i=0;i<all-count;i++){
			result += " ";
		}
		return result;		
	}
	
	public static void main(String[] args) {
		String str = ValidateUtil.getPadBlankChar("1234567", 18);
		
		String sdate = ValidateUtil.getDateString(new Date(), "YYYYMMDD");
		
		
		System.out.println("str=##" + str +"###" + str.length());
		
		System.out.println("sdate=" + sdate);
		
		
		
	}
	
	
	
	

}
