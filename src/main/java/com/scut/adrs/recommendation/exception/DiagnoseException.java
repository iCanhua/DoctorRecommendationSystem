package com.scut.adrs.recommendation.exception;

public class DiagnoseException extends RuntimeException{

	public DiagnoseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
		System.out.println(message);
	}
	
}
