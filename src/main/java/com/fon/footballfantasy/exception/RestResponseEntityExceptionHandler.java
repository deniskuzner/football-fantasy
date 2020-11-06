package com.fon.footballfantasy.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("EXCEPTION HANDLER");

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolationException(Exception ex) {
		LOGGER.error(ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex) {
		LOGGER.error(ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		LOGGER.error(ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
