package com.ibm.pprds.epimshw;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.LogManager;

public class HWPIMSExistMastNotDefinedStkoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();

	public HWPIMSExistMastNotDefinedStkoException() {
		super();
		logException();
	}

	public HWPIMSExistMastNotDefinedStkoException(String s) {
		super(s);
		logException();
	}

	public HWPIMSExistMastNotDefinedStkoException(String s, Throwable cause) {
		super(s, cause);
		logException();
	}
	
	private void logException() {
		logger.error(this.getMessage() + "\n" + ExceptionUtility.getStackTrace(this));
	}
	
}
