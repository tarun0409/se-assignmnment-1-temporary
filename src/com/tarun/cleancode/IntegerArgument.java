package com.tarun.cleancode;

public class IntegerArgument 
	implements Argument<Integer> {
	
	private int value = 0;
	
	@Override
	public void setValue(
		Integer value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

}
