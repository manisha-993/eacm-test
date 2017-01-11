package com.ibm.pprds.epimshw;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.LogManager;

public class HWPIMSNotFoundInMastException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();

	public HWPIMSNotFoundInMastException() {
		super();
		logException();
	}

	public HWPIMSNotFoundInMastException(String s) {
		super(s);
		logException();
	}

	public HWPIMSNotFoundInMastException(String s, Throwable cause) {
		super(s, cause);
		logException();
	}
	
	private void logException() {
		logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(this));
	}
	
}
