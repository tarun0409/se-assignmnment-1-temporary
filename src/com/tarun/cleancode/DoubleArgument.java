package com.tarun.cleancode;

public class DoubleArgument 
	implements Argument<Double> {

	private double value = 0;
	
	@Override
	public void setValue(
		Double value) {
		this.value = value;
	}

	@Override
	public Double getValue() {
		return this.value;
	}

}
