package com.ibm.pprds.epimshw;

import org.apache.log4j.Logger;


import com.ibm.pprds.epimshw.util.LogManager;

/**
 * Exception thrown to write the error information to DB2
 */
public class HWPIMSException extends Exception
{
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();

	/** Object containing the error information
	*/
	protected HWPIMSErrorInformation errorInfo = null;

/**
 * HWPIMSException constructor.
 */
public HWPIMSException() {
	super();
	logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(this));
}


/**
 * HWPIMSException constructor used whenever an HWPIMSErrorInformation object already exists
 * @param errorInformation com.ibm.pprds.epimshw.HWPIMSErrorInformation
 */
public HWPIMSException(HWPIMSErrorInformation errorInformation)
{
	this(errorInformation.getMessage());
	errorInfo = errorInformation;
}
/**
 * HWPIMSException constructor.
 * @param s java.lang.String
 */
public HWPIMSException(String s) {
	super(s);
	logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(this));
}

/**
 * HWPIMSException constructor.
 * @param s java.lang.String exception message string
 * @param ex - the exception upon which this exception is based
 */
public HWPIMSException(String s, Exception cause) {
	super(s + " Cause: " + cause.getMessage());
	logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(cause));
}

/**
 * HWPIMSException constructor passing element within the HWPIMSErrorInformation object.
 * Creation date: (4/5/00 11:28:50 AM)
 * @param action String
 * @param actionNo String
 * @param version int
 * @param status String
 * @param message String
 * @param user String
 * @param event String
 */
 
public HWPIMSException(String action, String actionNo, int version, String status, String message, String user, String event) {

	errorInfo = new HWPIMSErrorInformation(action, actionNo, version, status, message, user, event);
	
	}
/**
 * errorInformation getter
 * @return com.ibm.pprds.epimshw.HWPIMSErrorInformation
 */
public HWPIMSErrorInformation getErrorInfo() {
	return errorInfo;
}
/**
 * errorInformation setter
 * @param newErrorInfo com.ibm.pprds.epimshw.HWPIMSErrorInformation
 */
public void setErrorInfo(HWPIMSErrorInformation newErrorInfo) {
	errorInfo = newErrorInfo;
}
/**
 * Calls the errorInformation method to write the error info to the database
 * @param aConnection Connection db2Connection
 */
public void writeErrorInformation() throws HWPIMSAbnormalException {
		//errorInfo.writeErrorInfo();
}
}
