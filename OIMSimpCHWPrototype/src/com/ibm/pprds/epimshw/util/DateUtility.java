package com.ibm.pprds.epimshw.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	private static DateFormat _compactFormat;
	private static DateFormat _format ; 

	static { 
		_format =  new SimpleDateFormat ( "yyyy-MM-dd" ) ; 
		_compactFormat = new SimpleDateFormat( "ddMMyy" ) ; 

	}

	public DateUtility() {
		super();
	}

	public static boolean isToday( Calendar cal ) {
		boolean ans = false;
		if (cal != null) {
			// rjc - normalize to the end of day for comparison
			Calendar c1 = getEndOfDay( cal ) ; 
			Calendar c2 = getEndOfToday() ; 
			ans =  c1.equals( c2 ) ; 
		}
		return ans;
	}
	
	public static boolean isToday( Date date ) {
		// rjc - normalize to the end of day for comparison
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return DateUtility.isToday(cal);
	}
	
	public static boolean isAfterToday( Calendar cal ) {
		boolean ans = false;
		if (cal != null) {
			Calendar today = getEndOfToday() ; 
			ans =  cal.after( today ) ;
		} 
		return ans;
	}
	
	public static boolean isAfterToday( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return DateUtility.isAfterToday(cal);
	}
	
	public static boolean isTodayOrEarlier( Calendar cal ) {
		boolean ans = false;
		if (cal != null) {
			Calendar today = getEndOfToday() ; 
			ans = cal.before( today ) ; 
		}
		return ans;
	}
	
	public static boolean isTodayOrEarlier( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return DateUtility.isTodayOrEarlier(cal);
	}
	
	private static Calendar getEndOfToday() {
		return getEndOfDay( Calendar.getInstance() ) ;
	} 
	private static Calendar getEndOfDay( Calendar cal ) { 
		Calendar ans = Calendar.getInstance() ; 
		// rjc - move time to the last scond of the day 
		ans.set( Calendar.SECOND, 59 ) ; 
		ans.set( Calendar.MINUTE, 59 ) ; 
		ans.set( Calendar.HOUR_OF_DAY, 23 ) ;
		
		// rjc - now copy the rest of the date
		ans.set( Calendar.DAY_OF_MONTH, cal.get( Calendar.DAY_OF_MONTH ) ) ;  
		ans.set( Calendar.MONTH, cal.get( Calendar.MONTH ) ) ;  
		ans.set( Calendar.YEAR, cal.get( Calendar.YEAR ) ) ;  
		return ans ; 
	}

	private static Calendar getStartOfDay( Calendar cal ) { 
		Calendar ans = Calendar.getInstance() ; 
		// rjc - move time to the last scond of the day 
		ans.set( Calendar.SECOND, 0 ) ; 
		ans.set( Calendar.MINUTE, 0 ) ; 
		ans.set( Calendar.HOUR_OF_DAY, 0 ) ;
		
		// rjc - now copy the rest of the date
		ans.set( Calendar.DAY_OF_MONTH, cal.get( Calendar.DAY_OF_MONTH ) ) ;  
		ans.set( Calendar.MONTH, cal.get( Calendar.MONTH ) ) ;  
		ans.set( Calendar.YEAR, cal.get( Calendar.YEAR ) ) ;  
		return ans ; 
	}

	public static boolean isSameDay( Calendar cal1, Calendar cal2 ) {
		boolean ans = false;
		if (cal1 != null && cal2 != null) {
			
			Calendar c1 = getEndOfDay( cal1 ) ; 
			Calendar c2 = getEndOfDay( cal2 ) ; 
			
			ans =  c1.equals( c2 ) ; 
		}
		return ans;
	}
	
	public static boolean isSameDay( Date date1, Date date2 ) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			ans =  DateUtility.isSameDay(cal1, cal2);
		} 
		return ans;
	}

	public static Date getTodayDate() {
		Calendar cal = getToday() ;  
		return cal.getTime() ; 
	}
	
	public static Calendar getToday() {
		return Calendar.getInstance() ; 
	}

	public static String getTodayString() {
		return getDateString( getTodayDate() ) ;  
	}

	public static String getDateString( Calendar cal ) {
		return getDateString( cal.getTime() ) ; 
	}

	public static String getDateString( Date date ) {
		return getDateStringUsing( date, _format ) ;  
	}

	public static String getCompactDateString( Calendar cal ) {
		return getCompactDateString( cal.getTime() ) ; 
	}

	public static String getCompactDateString( Date date ) {
		return getDateStringUsing( date, _compactFormat ) ;  
	}

	private static String getDateStringUsing(Date date, DateFormat dateFormat) {
		return dateFormat.format( date ) ; 
	}

	public static boolean dateIsAfter(Calendar date1, Calendar date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			// rjc - return true if date1 is after date2
			Calendar c2 = getEndOfDay( date2 ) ;
			// rjc - we normalized date2 to the very start of the day.  
			Calendar c1 = getStartOfDay (date1);
			// gaj and normalize date1 to start so same day returns false
			ans =  c1.after( c2 ) ;  
		} 
		return ans;
	}
	
	public static boolean dateIsAfter(Date date1, Date date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			ans = DateUtility.dateIsAfter(cal1, cal2); 
		} 
		return ans;
	}
	
	public static boolean dateIsBefore(Calendar date1, Calendar date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			// return true if date1 is before date2
			Calendar c2 = getStartOfDay( date2 ) ;
			Calendar c1 = getEndOfDay(date1);
			ans =  c1.before( c2 ) ; 
		} 
		return ans;
	}
	
	public static boolean dateIsBefore(Date date1, Date date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			ans =  DateUtility.dateIsBefore(cal1, cal2);
		}
		return ans;
	}
	
	public static boolean dateIsTodayOrAfter(Calendar date1, Calendar date2) {
		return (DateUtility.dateIsAfter(date1, date2) ||
				DateUtility.isSameDay(date1, date2)) ; 
	}
	
	public static boolean dateIsTodayOrAfter(Date date1, Date date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			ans = DateUtility.dateIsAfter(cal1, cal2); 
		} 
		return ans;
	}
	
	public static boolean dateIsTodayOrBefore(Calendar date1, Calendar date2) {
		return (DateUtility.dateIsBefore(date1, date2) ||
			DateUtility.isSameDay(date1, date2)) ;
 
	}
	
	public static boolean dateIsTodayOrBefore(Date date1, Date date2) {
		boolean ans = false;
		if (date1 != null && date2 != null) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			ans = DateUtility.dateIsTodayOrBefore(cal1, cal2);
		}
		return ans;
	}
	
	public static Date getInfinityDate() {
		Calendar c = Calendar.getInstance(); //c.getTime();
		c.set(Calendar.MONTH, Calendar.DECEMBER);
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.YEAR, 9999);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		c.set(Calendar.MILLISECOND,0);
		return  c.getTime();
	}
	
	public static Date getDateMinusOne (Date inDate) {
		Calendar c = Calendar.getInstance();
		
		c.setTime(inDate);
		c.add(Calendar.DATE, -1);
		return c.getTime(); 
	}

	/**
	 * Get a timestamp value without milliseconds.
	 * This timestamp is consistent for use with Versata displays
	 * from DB2 data sources. Millseconds must be eliminated 
	 * to have Versata display times properly.
	 *
	 * @return Timestamp for the current time rounded to the
	 * nearest second.
	 */ 
	public static java.sql.Timestamp getCurrentTimestampNoMillis()
	{
		final int ROUND_FACTOR = 1000; 
		long l = (System.currentTimeMillis() + ROUND_FACTOR / 2) / ROUND_FACTOR;
		return new java.sql.Timestamp(l * ROUND_FACTOR);
	}
	
}
