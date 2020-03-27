package com.ibm.eannounce.miw;

import java.text.ParseException;

import junit.framework.TestCase;

public class DateParseTestCase extends TestCase {
	
	public void testParseDate() throws ParseException {
		String shortTimestamp = "27/05/99 10:25:48 GMT";
		String longTimestamp = "14/09/2011 17:19:16 GMT";
//		String shortDate = "27/05/99";
//		String longDate = "31/12/2007";
		System.out.println(shortTimestamp + " = " +DateConverter.timestamp(shortTimestamp));
		System.out.println(longTimestamp + " = " +DateConverter.timestamp(longTimestamp));
//		System.out.println(shortDate + " = " +DateConverter.date(shortDate));
//		System.out.println(longDate + " = " +DateConverter.date(longDate));
	}

}
