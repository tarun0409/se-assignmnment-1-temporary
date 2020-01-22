package com.tarun.cleancode;

import java.util.HashMap;
import java.util.Map;

public class MapArgument implements Argument<Map<String,String>> {

	private Map<String,String> value = new HashMap<String,String>();
	@Override
	public void setValue(
			Map<String, String> value) {
		this.value = value;
	}

	@Override
	public Map<String, String> getValue() {
		return this.value;
	}

}
