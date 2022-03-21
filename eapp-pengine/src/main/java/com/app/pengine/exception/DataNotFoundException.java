package com.app.pengine.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is class related to custom exception.
  * @description DataNotFoundException class
 *
 */
@ResponseStatus
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private   ExceptionResponse excp;
	
	
	 /**
	   * 
	   * @param excp
	   */
	public DataNotFoundException(ExceptionResponse excp) {
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
