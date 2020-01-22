package com.tarun.cleancode;

public class InvalidSchemaElement extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidSchemaElement() {
		super();
	}
	
	public InvalidSchemaElement(
		String message) {
		super(message);
	}

}
