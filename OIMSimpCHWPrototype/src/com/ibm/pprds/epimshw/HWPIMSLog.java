package com.ibm.pprds.epimshw;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.LogManager;
/**
 * Used to log information about HWPIMS.  It uses the Singleton Work pattern so that static methods
 * can be called to carry out it tasks.  The name of the log file will be whatever is in the
 * HWPIMSmmddyy where mmddyy is the date. A log file will only contain log messages for that day.
 */
public class HWPIMSLog {
	
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();

	/** instance of the class for Singleton access */
	protected static HWPIMSLog log;

	/** path for the file to write the log entries */
	protected java.lang.String logFileNameLoc;

	/** date format used to derive the file name */
	protected java.text.SimpleDateFormat fileNameDateFormat;

	/** date format to be used within the log file for a log entry */
	protected java.text.SimpleDateFormat logEntryDateFormat;

	/** Level of logging to perform.  This will be "N" - None, "E" - Error, or "I" - Information (and Error)
	*/
	protected java.lang.String logLevel;
/**
 * HWPIMSLog constructor.
 */
public HWPIMSLog()
{
	super();
	logger.debug("Creating new HWPIMSLog");
	fileNameDateFormat = new SimpleDateFormat("MMddyy");
	logEntryDateFormat = new SimpleDateFormat("HH:mm:ss");
}


/**
 * logLevel getter
 * @return java.lang.String
 */
public java.lang.String getLogLevel() {
	return logLevel;
}
/**
 * Returns the instance of HWPIMSLog
 * and will create it if it has not been constructed.
 */
public static synchronized HWPIMSLog instanceOf() {
	if (log == null) {
		log = new HWPIMSLog();
	}
	return log;
}

/**
 * logLevel setter
 * @param newLogLevel java.lang.String
 */
public void setLogLevel(java.lang.String newLogLevel) {
	logLevel = newLogLevel;
}
/**
 * Write the message to the log if we are currently capturing that msgLevel
 * @param message java.lang.String
 * @param msgLevel java.lang.String
 */
public void write(String message, String msgLevel)
{
		if (msgLevel.equals("E"))
		{
			logger.error(message);
		}
		else if (msgLevel.equals("I"))
		{
			logger.info(message);
		}
		else
		{
			logger.debug(message);
		}
}

	
	

	
/**
 * Writes a message to the  log file if the msgLevel is a level that is being captured.
 * @param message java.lang.String - The message to be written
 * @param msgLevel java.lang.String - The msgLevel
 */
public static void Write(String message, String msgLevel) {
	
	instanceOf().write(message,msgLevel);
	
	}
}
