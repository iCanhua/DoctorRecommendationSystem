package com.scut.adrs.recommendation.exception;

public class UnExistRdfException extends Exception{

	public UnExistRdfException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
		System.out.println(message);
	}
	
}
