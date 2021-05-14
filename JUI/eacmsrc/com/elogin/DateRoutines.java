/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DateRoutines.java,v $
 * Revision 1.2  2009/06/09 11:31:15  wendy
 * Performance cleanup
 *
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
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/12/11 19:45:24  tony
 * 53382
 *
 * Revision 1.2  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.Toolkit;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DateRoutines implements EAccessConstants {
	private int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int hour = 0;
	private int min = 0;
	private int sec = 0;

	/**
     * dateRoutines
     * @author Anthony C. Liberto
     */
    public DateRoutines() {
	}

	/**
     * validDate
     * @param dat
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean validDate(char[] dat) {
		return validDate(dat,false);
	}*/

	/**
     * getDaysDif
     * @param _date1
     * @param _date2
     * @return
     * @author Anthony C. Liberto
     * /
    public int getDaysDif(String _date1, String _date2) {				//joan
		java.sql.Date d1 = java.sql.Date.valueOf(_date1);						//joan
		java.sql.Date d2 = java.sql.Date.valueOf(_date2);						//joan
		long l1 = d1.getTime();													//joan
		long l2 = d2.getTime();													//joan
		long diff = Math.abs(l1-l2);											//joan
		long days = diff / (24*60*60*1000);										//joan
		Long l = new Long(days);												//joan
		return l.intValue();													//joan
	}	*/																		//joan

	/**
     * difInMin
     * @param mid
     * @param time
     * @return
     * @author Anthony C. Liberto
     * /
    public int difInMin(String mid, String time) {						//010787
		return difInMin(mid.toCharArray(), time.toCharArray());					//010787
	}	*/																		//010787

	/**
     * difInMin
     * @param mid
     * @param time
     * @return
     * @author Anthony C. Liberto
     * /
    public int difInMin(char[] mid, char[] time) {						//010787
		int mHr = Integer.valueOf(new String(mid,0,2)).intValue();				//010787
		int mMn = Integer.valueOf(new String(mid,3,2)).intValue();				//010787
		int tHr = Integer.valueOf(new String(time,0,2)).intValue();				//010787
		int tMn = Integer.valueOf(new String(time,3,2)).intValue();				//010787
		int out = -1;
        if (mHr == 0) {															//010787
			mHr = 24;
		}															//010787
		if (tHr == 0) {															//010787
			tHr = 24;
		}															//010787
		if (mMn == 0) {															//010787
			mMn = 60;
		}															//010787
		if (tMn == 0) {															//010787
			tMn = 60;
		}															//010787
		out = (mMn - tMn);													//010787
		if (tHr != mHr && out != 0) {												//010787
			++tHr;
		}																//010787
		out = out + ((mHr - tHr) * 60);											//010787
		out = out + 3;															//010787
		return out;																//010787
	}	*/																		//010787

	/**
     * isValidPartialTime
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidPartialTime(String _s) {
		return isValidPartialTime(_s.toCharArray());
	}

	/**
     * isValidPartialTime
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isValidPartialTime(char[] _c) {
		int ii = _c.length;
        int h1 = 0;
        int h2 = 0;
        int m1 = 0;
        int m2 = 0;
		if (ii == 0) {
            return true;
		}
		if (ii > 5) {
            return false;
		}
		if (ii > 0) {
			h1 = Integer.valueOf(String.valueOf(_c[0])).intValue();
		}
		if (ii > 1) {
			h2 = Integer.valueOf(String.valueOf(_c[1])).intValue();
		}
		if (ii > 3) {
			m1 = Integer.valueOf(String.valueOf(_c[3])).intValue();
		}
		if (ii > 4) {
			m2 = Integer.valueOf(String.valueOf(_c[4])).intValue();
		}
		return invalidTime(h1,h2,m1,m2);
	}

	private  boolean invalidTime(int _h1, int _h2, int _m1, int _m2) {
		if (_h1 > 2) {
			return false;
		} else if (_h1 == 2 && _h2 > 3) {
			return false;
		} else if (_m1 > 5) {
			return false;
		}
		return true;
	}

	/**
     * isValidPartialDate
     * @param s
     * @param msg
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidPartialDate(String s, boolean msg) {
        //System.out.println("dateRoutines.isValidPartialDate(" + s + ", " + msg + ")");
		return isValidPartialDate(s.toCharArray(), msg);
	}

	/**
     * isValidPartialDate
     * @param date
     * @param msg
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isValidPartialDate(char[] date, boolean msg) {
		int len = date.length;
        int maxday = -1;
        if (len == 0) {
			return true;
		}
		if (len < 4) {
//53382			int tmp = Integer.valueOf(new String(date,0,len)).intValue();		//e1.0
			int tmp = Routines.toInt(new String(date,0,len));					//53382
			if (len == 1) {														//e1.0
				if (tmp < 1) {													//e1.0
					return false;
				}												//e1.0
			} else if (len == 2) {												//e1.0
				if (tmp < 19) {													//e1.0
					return false;
				}												//e1.0
			} else if (len == 3) {												//e1.0
				if (tmp < 198) {													//e1.0
					return false;
				}												//e1.0
			}																	//e1.0
			return true;
		}
		year = 0;
		month = 0;
		day = 0;

		year = Integer.valueOf(new String(date,0,4)).intValue();
		if (year < 1980) {
			Toolkit.getDefaultToolkit().beep();
			return genMsg(11,msg);
		}
		if (len < 5) {
			return true;
		}

		if (len >= 5) {
			if (!isEqual(date[4],"-")) {
				return false;
			}
		}

		if (len < 6) {
			return true;
		}

		if (len == 6) {
			String s = new String(date,5,1);
			if (s.compareToIgnoreCase("1") < 0) {
				month = Integer.valueOf(s + "1").intValue();
			} else {
				month = Integer.valueOf(s + "0").intValue();
			}
		} else if (len >= 7) {
			month = Integer.valueOf(new String(date,5,2)).intValue();
		}

		if (month > 12 || month < 1) {
			Toolkit.getDefaultToolkit().beep();
			return genMsg(2,msg);
		}

		if (len < 8) {
			return true;
		}

		if (len >= 8) {
			if (!isEqual(date[7],"-")) {
				return false;
			}
		}

		if (len < 9) {
			return true;
		}

		if (len == 9) {
			String s = new String(date,8,1);
			if (s.compareToIgnoreCase("1") < 0) {
				day = Integer.valueOf(s + "1").intValue();
			} else {
				day = Integer.valueOf(s + "0").intValue();
			}
		} else {
			day = Integer.valueOf(new String(date,8,2)).intValue();
		}

		maxday = daysInMonth(year, month);
		if (day > maxday || day < 1) {
			Toolkit.getDefaultToolkit().beep();
			return genMsg(3,msg);
		}
		return true;
	}

	/**
     * validDate
     * @param dat
     * @param msg
     * @return
     * @author Anthony C. Liberto
     */
    private boolean validDate(char[] dat, boolean msg) {
		int len = dat.length;
		int maxday = -1;
        String yr = null;
        String mon = null;
        String d = null;
        if (len == 0) {
			return true;
		}
		if (len != 10 && len != 19 && len != 26) {
			//010553 return genMsg(0,msg);
			return genMsg(10,msg);	//010533
		}
		if (len == 10) {
			if (!isEqual(dat[4],"-") || !isEqual(dat[7],"-")) {
				return genMsg(9,msg);
			}
		} else if (len == 19) {
			if (!isEqual(dat[4],"-")  || !isEqual(dat[7],"-") ||
				!isEqual(dat[10],"-") ||
				!isEqual(dat[13],".") || !isEqual(dat[16],".")) {  //opica10
                return genMsg(9,msg);
			}
		} else if (len == 26) {
			if (!isEqual(dat[4],"-")  || !isEqual(dat[7],"-") ||
                !isEqual(dat[10],"-") || !isEqual(dat[13],".") ||
                !isEqual(dat[16],".") || !isEqual(dat[19],".")){		//opica10
				return genMsg(9,msg);
			}
		}

		try {
			yr = new String(dat,0,4);
			year = Integer.valueOf(yr).intValue();
		}catch (Exception ex) {
            EAccess.report(ex,false);
            return genMsg(8,msg);}
		try {
			mon = new String(dat,5,2);
			month = Integer.valueOf(mon).intValue();
		}catch (Exception ex) {
            EAccess.report(ex,false);
            return genMsg(8,msg);}
		try {
			d = new String(dat,8,2);
			day = Integer.valueOf(d).intValue();
		}catch (Exception ex) {
            EAccess.report(ex,false);
            return genMsg(8,msg);}

		if (month > 12 || month < 1) {
			return genMsg(2,msg);
		}
		maxday = daysInMonth(year, month);
		if (day > maxday || day < 1) {
			return genMsg(3,msg);
		}

		if (year < 1980) {							//2000-11-20
			return genMsg(11,msg);
		}					//2000-11-20

		if (len == 19 || len == 26) {
			try {
				String h = new String(dat,11,2);
				hour = Integer.valueOf(h).intValue();
			} catch (Exception ex) {
                EAccess.report(ex,false);
                return genMsg(4,msg);}
			try {
				String m = new String(dat,14,2);
				min = Integer.valueOf(m).intValue();
			} catch (Exception ex) {
                EAccess.report(ex,false);
                return genMsg(5,msg);}
			try {
				String s = new String(dat,17,2);
				sec = Integer.valueOf(s).intValue();
			} catch (Exception ex) {
                EAccess.report(ex,false);
                return genMsg(6,msg);
            }
			if (hour > 23 || hour < 0) {
				return genMsg(4,msg);
			}
			if (min > 59 || min < 0) {
				return genMsg(5,msg);
			}
			if (sec > 59 || min < 0) {
				return genMsg(6,msg);
			}
		}
		return true;
	}

	/**
     * validDate
     * @param str
     * @return
     * @author Anthony C. Liberto
     */
    public boolean validDate(String str) {
		char[] dat = str.trim().toCharArray();
		return validDate(dat,false);
	}


	/**
     * validDate
     * @param str
     * @param msg
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean validDate(String str, boolean msg) {
		char[] dat = str.trim().toCharArray();
		return validDate(dat,msg);
	}*/

	/**
     * given the from and to dates compare them to determine
     * the the from date is less than the to date
     *
     * @return flag if from date is less than the to date
     * @param f
     * @param msg
     * @param t
     * /
	public boolean compareStrings(String f, String t, boolean msg) {
		if (!compareStrings(f,t)) {
			return genMsg(7,msg);
		}
		return true;
	}*/

	/**
     * compareStrings
     * @param f
     * @param t
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean compareStrings(String f, String t) {
//2000-10-18		if (f.compareTo(t) > 0)
		if (f.compareToIgnoreCase(t) > 0) {		//2000-10-18
			return false;
		}
		return true;
	}*/

//
// Generic Date Functions
//
	/**
	 * compute the number of days given in the current year
	 * @param y the year
	 * @return the number of days in the given year
	 * /
	public int daysInYear(int y) {
		if (isLeapYear(y)) {
			return 366;
		}
		return 365;
	}*/

	/**
	 * analyze the current year to see if it is a leap year
	 * @param y the year
	 * @return flag if it is a leap year
	 */
	protected boolean isLeapYear(int y) {
		boolean test = false;
		if (y == 3600) {
			test = false;

		} else if (y % 100 == 0) {
			if (y % 400 == 0) {
				test = true;
			}
		}
		else if (y % 4 == 0) {
			test = true;
		}
		return test;
	}

	/**
	 * analyze the current moonth to see how many days
	 * are in the given month year combination
	 * @param y the year
	 * @param m the month
	 * @return the number of days in the month
	 */
	protected int daysInMonth(int y, int m) {
		if (isLeapYear(y)) {
			daysInMonths[1] = 29;

		} else {
			daysInMonths[1] = 28;
		}
		return daysInMonths[m - 1];
	}

	/**
	 * where the number of days in a year is
	 * actually computed
	 * @return the number of days in the year
	 */
	protected int daysInYear() {
		if (isLeapYear()) {
			return 366;
		}
		return 365;
	}

	/**
	 * validation to determine if the year
	 * is a valid leap year
	 */
	private  boolean isLeapYear() {
		boolean test = false;
		if (year == 3600) {
			test = false;

		} else if (year % 100 == 0) {
			if (year % 400 == 0) {
				test = true;
			}
		}
		else if (year % 4 == 0) {
			test = true;
		}

		return test;
	}

	/**
	 * returns the number of days in a month
	 * @return the number of days in the month
	 * /
	public int daysInMonth() {
		if (isLeapYear()) {
			daysInMonths[1] = 29;

		} else {
			daysInMonths[1] = 28;
		}
		return daysInMonths[month - 1];
	}*/


	/**
     * isPastDate
     * @param date
     * @param now
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPastDate(String date, String now) {
//2000-10-18		if (date.compareTo(now) <= 0)
		if (date != null && now != null) {
			if (date.compareToIgnoreCase(now) <= 0)	{				//2000-10-18
				return false;
			}
			return true;
		}
		return false;
	}

	/**
     * isFutureDate
     * @param date
     * @param now
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFutureDate(String date, String now) {
//2000-10-18		if (date.compareTo(now) >= 0)
		if (date.compareToIgnoreCase(now) >= 0) {					//2000-10-18
			return false;
		}
		return true;

	}

	/**
	 * add the end of day string to the given date
	 * @param Date the curruent date
	 * @return the new date string
	 * /
	public String endOfDay(String Date) {
//010668		String str = new String(Date + "-23.59.59.999999");
		//String str = new String(Date + " 23:59:00.000000");			//010668
		String str = new String(Date + "-23.59.00.000000");			//opica10
		return str;
	}*/

	/**
     * EOD
     * @param Date
     * @return
     * @author Anthony C. Liberto
     * /
    public String eod(String Date) {							//010787
		//return Date + " 23:59:59" ;									//010787
		return Date + "-23.59.59.000000" ;									//opica10
	}	*/															//010787

	/**
	 * add the start of day string to the given date
	 * @param Date the curruent date
	 * @return the new date string
	 */
    protected String startOfDay(String Date) {
//010668		String str = new String(Date + "-00.00.00.000000");
		//String str = new String(Date + " 00:00:00.000000");			//010668
		String str = new String(Date + "-00.00.00.000000");			//opica10
		return str;
	}

	/**
     * AlmostStartOfDay
     * @param Date
     * @return
     * @author Anthony C. Liberto
     * /
    public String almostStartOfDay(String Date) {			//16621
		String str = new String(Date + "-00.00.00.000001");			//16621
		return str;													//16621
	}*/																//16621

	/**
	 * generate the beginning of time constant
	 * @return the begining date string
	 * /
	public String beginning() {
		return BEGINING;
	}*/

	/**
	 * generate the forever of time constant
	 * @return the forever date string
	 * /
	public String forever() {
		return FOREVER;
	}*/

	/**
     * genMsg
     * @param i
     * @param msg
     * @return
     * @author Anthony C. Liberto
     */
    private boolean genMsg(int i, boolean msg) {
		if (!msg) {
			return false;
		}
		if (i == 0) {
			errorMsg("msg5001");

		} else if (i == 1) {
			errorMsg("msg5002");

		} else if (i == 2) {
			errorMsg("msg5003");

		} else if (i == 3) {
			errorMsg("msg5004");

		} else if (i == 4) {
			errorMsg("msg5005");

		} else if (i == 5) {
			errorMsg("msg5006");

		} else if (i == 6) {
			errorMsg("msg5007");

		} else if (i == 7) {
			errorMsg("msg5008");

		} else if (i == 8) {
			errorMsg("msg5009");

		} else if (i == 9) {
			errorMsg("msg5010");

		} else if (i == 10) {			//010553
			errorMsg("msg5013");	//010553

		} else if (i == 11) {			//2000-11-20
			errorMsg("msg3017");
		}	//2000-11-20
		return false;
	}
    /**
     * added for 013738
     *
     * @return boolean
     * @param c
     * /
    private boolean numValidation(char[] c) {			//15389
		for (int i = 0; i < c.length; i++) {				//15389
			if (! Character.isDigit(c[i])) {					//15389
				return false;
			}								//15389
		}
		return true;										//15389
	}*/

	/**
     * isValidPartialDateString
     * @param date
     * @return
     * @author Anthony C. Liberto
     * /
    public String validPartialDateString(String date) {
		return isValidPartialDateString(date.toCharArray());
	}*/

	/**
     * isValidPartialDateString
     * @param date
     * @return
     * @author Anthony C. Liberto
     * /
    public String isValidPartialDateString(char[] date) {
		int len = date.length;
		String y = null;
        int maxday = -1;
        if (len < 4) {
			return null;
		}
		year = 0;
		month = 0;
		day = 0;

		// 15389 year = Integer.valueOf(new String(date,0,4)).intValue();
		y = new String (date, 0,4);						//15389
		if (numValidation(y.toCharArray())) {						//15389
			year = Integer.valueOf(y).intValue();				//15389

		} else {													//15389
			return null;
		}										//15389

		if (year < 1980) {
			Toolkit.getDefaultToolkit().beep();
			return getCode(11);
		}
		if (len < 5) {
			return null;
		}

		if (len >= 5) {
			if (!isEqual(date[4],"-")) {
				return getCode(8);
			}
		}

		if (len < 6) {
			return null;
		}

		if (len == 6) {
			String s = new String(date,5,1);
			if (s.compareToIgnoreCase("1") < 0) {
				month = Integer.valueOf(s + "1").intValue();
			} else {
				month = Integer.valueOf(s + "0").intValue();
			}
		} else if (len >= 7) {
			String m = new String (date, 5,2);						//15389
			if (numValidation(m.toCharArray())) {						//15389
				month = Integer.valueOf(m).intValue();				//15389

			} else {													//15389
				return null;
			}										//15389
			//15389 month = Integer.valueOf(new String(date,5,2)).intValue();
		}

		if (month > 12 || month < 1) {
			Toolkit.getDefaultToolkit().beep();
			return getCode(2);
		}

		if (len < 8) {
			return null;
		}

		if (len >= 8) {
			if (!isEqual(date[7],"-")) {
				return getCode(8);
			}
		}

		if (len < 9) {
			return null;
		}

		if (len == 9) {
			String s = new String(date,8,1);
			if (s.compareToIgnoreCase("1") < 0) {
				day = Integer.valueOf(s + "1").intValue();
			} else {
				day = Integer.valueOf(s + "0").intValue();
			}
		} else {
			day = Integer.valueOf(new String(date,8,2)).intValue();
		}

		maxday = daysInMonth(year, month);
		if (day > maxday || day < 1) {
			Toolkit.getDefaultToolkit().beep();
			return getCode(3);
		}
		return null;
	}*/

	/**
     * getCode
     * @param i
     * @return
     * @author Anthony C. Liberto
     * /
    public String getCode(int i) {			//013738
		if (i == 0) {									//013738
			return "msg5001";						//013738

		} else if (i == 1) {							//013738
			return "msg5002";						//013738

		} else if (i == 2) {							//013738
			return "msg5003";						//013738

		} else if (i == 3) {							//013738
			return "msg5004";						//013738

		} else if (i == 4) {							//013738
			return "msg5005";						//013738

		} else if (i == 5) {							//013738
			return "msg5006";						//013738

		} else if (i == 6) {							//013738
			return "msg5007";						//013738

		} else if (i == 7) {							//013738
			return "msg5008";						//013738

		} else if (i == 8) {							//013738
			return "msg5009";						//013738

		} else if (i == 9) {							//013738
			return "msg5010";						//013738

		} else if (i == 10) {							//013738
			return "msg5013";						//013738

		} else if (i == 11) {							//013738
			return "msg3017";
		}						//013738
		return null;								//013738
	}		*/										//013738

	/**
     * errorMsg
     * @param _code
     * @author Anthony C. Liberto
     */
    private void errorMsg(String _code) {
    	EAccess.eaccess().setCode(_code);
    	EAccess.eaccess().showError(null);
	}

	/**
     * isEqual
     * @param c
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isEqual(char c, String s) {
		String ss = new String(String.valueOf(c));
		return s.equals(ss);
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     * /
    public void dereference() {
	}*/

}

