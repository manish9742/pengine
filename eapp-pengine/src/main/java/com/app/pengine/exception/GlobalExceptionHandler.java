package com.app.pengine.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class related to handling of global exception
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ Exception.class,DataNotFoundException.class,DataValidationException.class })
	public @ResponseBody ExceptionResponse  handleAllException(final Exception excep, final WebRequest request) {

		LOGGER.error("Exception in GlobalExceptionHandler ", excep);
		ExceptionResponse response=new ExceptionResponse();
		final HttpHeaders headers = new HttpHeaders();

		if (excep instanceof DataNotFoundException) {
			response= handleDataNotFoundException(excep, headers, request);
		} else if(excep instanceof  DataValidationException) {
			response= handleDataValidationException(excep, headers, request);
		}else if (excep instanceof Exception) {
			response= handleDefaultException(headers, request, excep);
		}

		return response;

	}
	
	private ExceptionResponse handleDataValidationException(Exception excep, HttpHeaders headers,WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), excep.getLocalizedMessage(),
				request.getDescription(false),HttpStatus.NOT_ACCEPTABLE);
		return exceptionResponse;
	}

	private  ExceptionResponse  handleDataNotFoundException(Exception excep, HttpHeaders headers,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), excep.getLocalizedMessage(),
				request.getDescription(false),HttpStatus.NOT_FOUND);
		return exceptionResponse;
	}

	public final  ExceptionResponse  handleDefaultException(HttpHeaders headers, WebRequest req,
			Exception excep) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), excep.getLocalizedMessage(),
				req.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR);
		return  exceptionResponse;
	}

}