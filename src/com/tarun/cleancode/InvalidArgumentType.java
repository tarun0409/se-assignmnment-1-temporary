package com.tarun.cleancode;

public class InvalidArgumentType 
	extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidArgumentType() {
		super();
	}
	
	public InvalidArgumentType(
		String message) {
		super(message);
	}
}
