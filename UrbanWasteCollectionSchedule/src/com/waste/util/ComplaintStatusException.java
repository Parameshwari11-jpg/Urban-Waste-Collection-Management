package com.waste.util;

@SuppressWarnings("serial")
public class ComplaintStatusException extends Exception {

	@Override
	public String toString() {
		return "Invalid Complaint Status Transistion";
	}

}
