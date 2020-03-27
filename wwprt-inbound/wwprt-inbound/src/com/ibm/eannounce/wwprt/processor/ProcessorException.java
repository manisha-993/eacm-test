package com.ibm.eannounce.wwprt.processor;

public class ProcessorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String pricesId;
	
	private boolean sendFailResponse;

	public ProcessorException(String pricesId, String message, Throwable cause) {
		super(message, cause);
		this.pricesId = pricesId;
		sendFailResponse = true;
	}

	public ProcessorException(String pricesId, String message, Throwable cause, boolean sendFailResponse) {
		super(message, cause);
		this.pricesId = pricesId;
		this.sendFailResponse = sendFailResponse;
	}
		
	public ProcessorException(String pricesId, String message) {
		super(message);
		this.pricesId = pricesId;
		sendFailResponse = true;
	}
	
	public boolean isSendFailResponse() {
		return sendFailResponse;
	}
	
	public String getPricesId() {
		return pricesId;
	}
	

}
