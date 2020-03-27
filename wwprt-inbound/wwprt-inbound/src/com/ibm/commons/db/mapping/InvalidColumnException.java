package com.ibm.commons.db.mapping;

public class InvalidColumnException extends Exception {

	private static final long serialVersionUID = 1L;

	private String id;

	public InvalidColumnException(String message) {
		super(message);
	}

	public InvalidColumnException(String id, String message) {
		super(message);
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
