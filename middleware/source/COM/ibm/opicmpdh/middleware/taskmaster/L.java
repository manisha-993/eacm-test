//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: L.java,v $
// Revision 1.17  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.16  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.15  2005/01/25 22:34:58  dave
// Jtest Syntax
//
// Revision 1.14  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.13  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.D;

import COM.ibm.opicmpdh.middleware.MiddlewareServerDynamicProperties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * A class for log output
 * @version @date
 */
public final class L {
	// Class constants
	/**
     * DEBUG_ERR
	 */
	public static final int DEBUG_ERR = 0;
	/**
     * DEBUG_WARN
	 */
	public static final int DEBUG_WARN = 1;
	/**
     * DEBUG_INFO
	 */
	public static final int DEBUG_INFO = 2;
	/**
     * DEBUG_DETAIL
	 */
	public static final int DEBUG_DETAIL = 3;
	/**
     * DEBUG_SPEW
     * 
	 */
	public static final int DEBUG_SPEW = 4;
	// Class variables
	private static boolean c_bEnabled = true;
	private static int c_iDebugLevel = D.EBUG_DETAIL;
	private static String c_strPrefix =
		MiddlewareServerDynamicProperties.getInstanceName();
	// static output is prefixed with instance name
	private static final ThreadLocal DEFAULTDATEFORMAT =
	new ThreadLocal() {
		public Object initialValue() {
				return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000"); 
			}
		};
	// Member variables
	private boolean m_bEnabled = true;
	private int m_iDebugLevel = D.EBUG_DETAIL;
	private String m_strPrefix = ""; 

	// Constructors
	/**
	 * TBD
	 * 
	 * @author Dave
	 */
	public L() {
		this(""); 
	}
	/**
     * TBD
     *
     * @param _strPrefix
     * @author Dave
     */
	public L(String _strPrefix) {
		m_strPrefix = _strPrefix;
	}
	// Class Methods
	private static final void outputLine(String _strLine, String _strPrefix) {
		SimpleDateFormat sdf = (SimpleDateFormat) DEFAULTDATEFORMAT.get();

		if (_strPrefix.length() > 0) {
			System.out.println(sdf.format(new Date()) + " $" + _strPrefix + "$ " + _strLine);   //$NON-NLS-2$
		} else {
			System.out.println(sdf.format(new Date()) + " " + _strLine); 
		}
	}
	private static final void outputLines(String _strText, String _strPrefix) {
		StringTokenizer stLine = new StringTokenizer(_strText, "\n");

		while (stLine.hasMoreTokens()) {
			String strLine = stLine.nextToken();

			L.outputLine(strLine, c_strPrefix);
		}
	}

	/**
	 * TBD
	 * 
	 * @param _strText
	 * @author Dave
	 */
	public static final void display(String _strText) {
		L.outputLines(_strText, c_strPrefix);
	}

	/**
	 * TBD
	 * 
	 * @param _strText
	 * @author Dave
	 */
	public static final void debug(String _strText) {
		if (c_bEnabled) {
			L.outputLines(_strText, c_strPrefix);
		}
	}
	/**
     * debug 1
     * 
	 * @param _iLevel
	 * @param _strText
	 * @author Dave
	 */
	public static final void debug(int _iLevel, String _strText) {
		if (c_bEnabled && (_iLevel <= c_iDebugLevel)) {
			L.outputLines(_strText, c_strPrefix);
		}
	}
	/**
     * debug 2
     * 
	 * @param _bEnabled
	 * @author Dave
	 */
	public static final void debug(boolean _bEnabled) {
		c_bEnabled = _bEnabled;
	}
	/**
     * Set debug Level
     * 
	 * @param _iLevel
	 * @author Dave
	 */
	public static final void debug(int _iLevel) {
		c_iDebugLevel = _iLevel;
	}
	/**
     * determineMemory
     * 
	 * @return
	 * @author Dave
	 */
	public static final String determineMemory() {
		Runtime c_runInfo = Runtime.getRuntime();
		long lTotalMemory = c_runInfo.totalMemory();
		long lFreeMemory = c_runInfo.freeMemory();

		return "free memory: ("
		+ lFreeMemory
		+ "/"
		+ lTotalMemory
		+ ") "
		+ ((lFreeMemory * 100) / lTotalMemory)
		+ "% free";
	}
	/**
     * displayMemory
     * 
	 * @author Dave
	 */
	public static final void displayMemory() {
		L.debug(DEBUG_DETAIL, L.determineMemory());
	}
	// Instance Methods
	// Unconditional output (Like D.isplay)
	/**
     * mdisplay
     * 
	 * @param _strText
	 * @author Dave
	 */
	public final void mdisplay(String _strText) {
		L.outputLines(_strText, m_strPrefix);
	}
	// Conditional output (based only on enabled)
	/**
     * mdebug
     * 
	 * @param _strText
	 * @author Dave
	 */
	public final void mdebug(String _strText) {
		if (m_bEnabled) {
			L.outputLines(_strText, m_strPrefix);
		}
	}
	// Output conditional upon enabled setting and output level
	/**
     * mdebug
     * 
	 * @param _iLevel
	 * @param _strText
	 * @author Dave
	 */
	public final void mdebug(int _iLevel, String _strText) {
		if (c_bEnabled && (_iLevel <= c_iDebugLevel)) {
			L.outputLines(_strText, c_strPrefix);
		}
	}
	// Set enabled true or false
	/**
     * mdebug
     * 
	 * @param _bEnabled
	 * @author Dave
	 */
	public final void mdebug(boolean _bEnabled) {
		m_bEnabled = _bEnabled;
	}
	/**
     * mdebug
     * 
	 * @param _iLevel
	 * @author Dave
	 */
	public final void mdebug(int _iLevel) {
		m_iDebugLevel = _iLevel;
	}
	/**
     * mdetermineMemory
     * 
	 * @return
	 * @author Dave
	 */
	public final String mdetermineMemory() {
		return L.determineMemory();
	}
	/**
     * mdisplayMemory
     * 
	 * @author Dave
	 */
	public final void mdisplayMemory() {
		mdebug(DEBUG_DETAIL, mdetermineMemory());
	}
}
