package com.tarun.cleancode;

import java.util.ArrayList;

public class StringArrayArgument 
	implements Argument<ArrayList<String>> {
	
	private ArrayList<String> value = new ArrayList<String>();
	
	@Override
	public void setValue(
		ArrayList<String> value) {
		this.value = value;
	}

	@Override
	public ArrayList<String> getValue() {
		return value;
	}

}
