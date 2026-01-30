package com.waste.util;

@SuppressWarnings("serial")
public class ActiveComplaintsExistException extends Exception{

	@Override
	public String toString() {
		return "Citizens Has open Complaints.Open them first....";
	}

}
