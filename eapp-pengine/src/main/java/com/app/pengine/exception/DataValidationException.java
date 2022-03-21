package com.app.pengine.exception;

public class DataValidationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private   String message;
	
	
	 /**
	   * 
	   * @param excp
	   */
	
	public DataValidationException() {
		 super();
		 
	}
	
	public DataValidationException(String message) {
		 super(message);
		 
	}
	
}
