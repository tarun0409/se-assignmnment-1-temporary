package com.tarun.cleancode;

public class InvalidArgumentList 
	extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidArgumentList() {
		super();
	}
	public InvalidArgumentList(
		String message) {
		super(message);
	}
}
