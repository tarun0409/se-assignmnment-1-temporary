package com.tarun.cleancode;

public interface Argument<T> {
	void setValue(T value);
	T getValue();
}
