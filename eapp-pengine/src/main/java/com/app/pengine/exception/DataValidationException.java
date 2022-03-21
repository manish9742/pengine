package com.app.pengine.exception;

public class DataValidationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private   ExceptionResponse excp;
	
	
	 /**
	   * 
	   * @param excp
	   */
	public DataValidationException(ExceptionResponse excp) {
		super();
		this.excp = excp;
	}
	
	/**
	   * Getter of excp.
	   * @return excp
	   */
	  public ExceptionResponse getException() {
	    return excp;
	  }
	

}
