package com.ibm.pprds.epimshw;

import org.apache.log4j.Logger;


import com.ibm.pprds.epimshw.util.LogManager;

/**
 * For errors that are not automatically recoverable.  Will usually be a DB2 error.
 */
public class HWPIMSAbnormalException extends Exception
{

	private static Logger logger = LogManager.getLogManager().getPromoteLogger();
	/**
	 * HWPIMSAbnormalException constructor comment.
	 */
	public HWPIMSAbnormalException() {
		super();
		logException();
	}
	/**
	 * HWPIMSAbnormalException constructor comment.
	 * @param s java.lang.String
	 */
	public HWPIMSAbnormalException(String s) {
		super(s);
		logException();
	}
	/**
	 * HWPIMSAbnormalException constructor comment.
	 * @param s java.lang.String
	 */
	public HWPIMSAbnormalException(String s, Throwable cause) {
		super(s, cause);
		logException();
	}
	
	private void logException()
	{
		logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(this));
	}
}
