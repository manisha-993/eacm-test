//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Log.java,v $
// Revision 1.38  2005/01/26 01:05:19  dave
// Jtest cleanup
//
// Revision 1.37  2005/01/24 21:58:58  dave
// starting to clean up per new IBM standards
//
// Revision 1.36  2002/10/02 23:06:24  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
* A class for log output
* @version @date
*/
public final class Log {
	// Class constants
	// Class variables

	private static final ThreadLocal DEFAULTDATEFORMAT = new ThreadLocal() {
		public Object initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
		}
	};

	// Instance constants
	// Instance variables
	private String m_strPrefix = null;

	/**
	* Main Constructor for Log
	* Instantiate a log with no prefixed message
	*/
	public Log() {
		this("");
	}

	/**
     * Main Constructor for Log
     * It takes a prefix and remembers it and prints it out
     * And redirects system out to a File System
     *
     * @param _strPrefix 
     */
	public Log(String _strPrefix) {

		String strLogFileName = MiddlewareServerProperties.getLogFileName();
		setLogPrefix(_strPrefix);

		if (strLogFileName.length() > 0) {

			PrintStream ps = null;
			try {
				FileOutputStream fos =
					new FileOutputStream(strLogFileName, true);
				ps = new PrintStream(fos, true);
			} catch (FileNotFoundException fne) {
				System.out.println("can't redirect output " + fne);
			}
			System.setOut(ps);
		}

	}

	/**
     * A static way to write to leveled Message to System out
     *
     * @param _iLevel
     * @param _strMessage 
     */
	public static final void out(int _iLevel, String _strMessage) {
		Log.out("level (" + _iLevel + ") " + _strMessage);
	}

	/**
     * A static way to write to System out
     *
     * @param _strMessage 
     */
	public static final void out(String _strMessage) {
		SimpleDateFormat sdf = (SimpleDateFormat) DEFAULTDATEFORMAT.get();
		System.out.println(sdf.format(new Date()) + " " + _strMessage);
	}

	/**
     * Writes the log to System.out
     *
     * @param _strMessage 
     */
	public final void log(String _strMessage) {

		SimpleDateFormat sdf = (SimpleDateFormat) DEFAULTDATEFORMAT.get();
		if (m_strPrefix.length() > 0) {
			System.out.println(
				sdf.format(new Date())
					+ " $"
					+ m_strPrefix
					+ "$ "
					+ _strMessage);
		} else {
			System.out.println(sdf.format(new Date()) + " " + _strMessage);
		}
	}

	/**
     * Sets the log prefix with what was passed
     *
     * @param _strPrefix 
     */
	public final void setLogPrefix(String _strPrefix) {
		m_strPrefix = _strPrefix;
	}

	/**
	* Return the date/time this class was generated
	* @return the date/time this class was generated
	*/
	public final static String getVersion() {
		return "$Id: Log.java,v 1.38 2005/01/26 01:05:19 dave Exp $";
	}
}
