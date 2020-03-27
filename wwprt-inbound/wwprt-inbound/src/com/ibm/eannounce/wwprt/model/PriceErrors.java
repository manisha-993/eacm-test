package com.ibm.eannounce.wwprt.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class PriceErrors {
	
	private String offering;
	
	private Map<String, String> errors = new LinkedHashMap<String, String>();

	public PriceErrors(String offering) {
		this.offering = offering;
	}
	
	public void addError(String id, String message) {
		errors.put(id, message);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public String getOffering() {
		return offering;
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
}
