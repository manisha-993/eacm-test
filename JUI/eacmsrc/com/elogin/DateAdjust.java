/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DateAdjust.java,v $
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:35  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/05/12 23:38:51  tony
 * 50614
 *
 * Revision 1.1.1.1  2003/03/03 18:03:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DateAdjust implements EAccessConstants {
	private final SimpleDateFormat YEAR = new SimpleDateFormat("yyyy");
	private final SimpleDateFormat MONTH = new SimpleDateFormat("MM");
	private final SimpleDateFormat DAY = new SimpleDateFormat("dd");

	private int year = 0;
	private int month = 0;
	private int day = 0;

	private DateRoutines dRoutines = null;

	/**
     * dateAdjust
     * @author Anthony C. Liberto
     */
    public DateAdjust() {
		dRoutines = new DateRoutines();
		return;
	}

	/**
     * getDateRoutines
     * @return
     * @author Anthony C. Liberto
     */
    public DateRoutines getDateRoutines() {
		return dRoutines;
	}

	/**
     * adjustDate
     * @param d
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String adjustDate(Date d, int i) {
		String y = YEAR.format(d);
		String m = MONTH.format(d);
		String dy = DAY.format(d);
		return adjustDate(y,m,dy,i);
	}

	/**
     * adjustDate
     * @param in
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String adjustDate(String in, int i) {
		char[] tmp = in.toCharArray();
		char[] iny = {tmp[0] , tmp [1] , tmp[2] , tmp[3]};
		char[] inm = {tmp[5] , tmp [6]};
		char[] indy = {tmp[8] , tmp [9]};
		String y = new String(iny);
		String m = new String(inm);
		String dy = new String(indy);
		return adjustDateFormatted(y,m,dy,i);
	}

	/**
     * adjustDateFormatted
     * @param y
     * @param m
     * @param dy
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String adjustDateFormatted(String y, String m, String dy, int i) {
		String strTmp = new String(adjustDate(y,m,dy,i));
		String strDash = DASH;
		char[] tmp = strTmp.toCharArray();
		char dash = strDash.charAt(0);
		char[] outTmp = {tmp[0] , tmp [1] , tmp[2] , tmp[3], dash, tmp[4] , tmp [5], dash, tmp[6] , tmp [7]};
		String strOut = new String(outTmp);
		return dRoutines.startOfDay(strOut);
	}

	/**
	 * adjust the date by adding the integer for number of days to
	 * the given date.  The Date d should probably be changed to
	 * a string in future version of the code
	 * @param y the year
	 * @param m the month
	 * @param dy the day
	 * @param i the number of days to add to the date
	 * @return the new date
	 */
	public String adjustDate(String y, String m, String dy, int i) {
        int dim = 0;
        String yy = null;
        String mm = null;
        String dd = null;
        int mult = 1;
		year = Integer.valueOf(y).intValue();
		month = Integer.valueOf(m).intValue();
		day = Integer.valueOf(dy).intValue();

		if (i < 0) {
			mult = mult * - 1;
			i = i * - 1;
		}

		while (i > 0) {
			if (dRoutines.isLeapYear(year)) {
				if (i >= 366) {
					i = adjustYear(i, mult);
				}
			} else if (i >= 365) {
				i = adjustYear(i, mult);
			}

			dim = dRoutines.daysInMonth(year, month);
			if (i >= dim) {
				i = adjustMonth(i, mult);
			}

			if (i > 0) {
				i = adjustDay(i, mult);
			}
		}
		if (day == 0) {
			addOneToMonth(-1);
		}
		yy = Integer.toString(year);
		mm = Integer.toString(month);
		dd = Integer.toString(day);

		if (month < 10) {
			mm = "0" + Integer.toString(month);
		}
		if (day < 10) {
			dd = "0" + Integer.toString(day);
		}

		return yy + mm + dd;
	}

	/**
	 * add to the year if the number of days is greater than a year
	 * @param i the number of days
	 * @param m the multiplier for adding to or subtracting from the year
	 * @return the new year
	 */
	private int adjustYear(int i, int m) {
		int diy = dRoutines.daysInYear();
		while (i > diy) {
			addOneToYear(m);
			i = i - diy;
			diy = dRoutines.daysInYear();
		}
		return i;
	}

	/**
	 * add to the month if the number of days is greater than a month
	 * @param i the number of days
	 * @param m the multiplier for adding to or subtracting from the month
	 * @return the new month
	 */
	private int adjustMonth(int i, int m) {
		int dim = dRoutines.daysInMonth(year,month);
		if (m > 0) {
			i = i - (dim - day);

		} else {
			i = i - day;
		}
		addOneToMonth(m);
		dim = dRoutines.daysInMonth(year,month);
		while (i >= dim) {
			addOneToMonth(m);
			i = i - dim;
			dim = dRoutines.daysInMonth(year,month);
		}

		return i;
	}

	/**
	 * add a single year to the existing year
	 * @param m the year multiplier
	 */
	private void addOneToYear(int m) {
		year = year + (1 * m);
		return;
	}

	/**
	 * add a single month to the current month
	 * if the month effects the year the year is
	 * also adjusted
	 * @param m the month multiplier
	 */
	private void addOneToMonth(int m) {
		month = month + (1 * m);
		if (month > 12) {
			month = 1;
			addOneToYear(1);
		}
		if (month <= 0) {
			month = 12;
			addOneToYear(-1);
		}
		if (m > 0) {
			day = 0;

		} else {
			day = dRoutines.daysInMonth(year,month);
		}
		return;
	}

	/**
	 * add a single day to the current day
	 * if the day effects the month the month is
	 * also adjusted
	 * if the month effects the year the year is
	 * also adjusted
	 * @param i the number of days
	 * @param m the month multiplier
	 */
	private int adjustDay(int i, int m) {
		int days = (dRoutines.daysInMonth(year,month) - day);
		if (days <= (i * m)) {
			i = i - days;
			addOneToMonth(m);
		}
		for (int x = 0; x < i; ++x) {
			addOneToDay(m);
		}
		return 0;
	}
	/**
	 * where one day is added to the month
	 * @param m the day multiplier
	 */
	private void addOneToDay(int m) {
		day = day + (1 * m);
		if (day <= 0) {
			addOneToMonth(-1);
			day = dRoutines.daysInMonth(year,month);
		}
		else if (day > dRoutines.daysInMonth(year,month)) {
			addOneToMonth(+1);
			day = dRoutines.daysInMonth(year,month);
		}
		return;
	}

	/**
     * getDateString
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getDateString(int _i) {
		SimpleDateFormat procTime = new SimpleDateFormat("HHmmss");
		Date inDate = new Date();
		String time = procTime.format(inDate);
		String date = adjustDate(inDate,_i);
		String out = date + time + LOG_EXTENSION;
		return out;
	}

}

