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
	
	 /**
	   * 
	   * @param excp
	   */
	public DataNotFoundException() {
		super();
	}
	
	
	public DataNotFoundException(String message) {
		super(message);
	}
	
	
	
	

}
