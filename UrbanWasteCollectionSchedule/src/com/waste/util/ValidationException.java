package com.waste.util;

@SuppressWarnings("serial")
public class ValidationException extends Exception{

	@Override
	public String toString() {
		return "Invalid input data.Please check Details";
	}

}
