package com.tarun.cleancode;

public class BooleanArgument 
	implements Argument<Boolean> {

	private boolean value = false;
	
	@Override
	public void setValue(
		Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

}
