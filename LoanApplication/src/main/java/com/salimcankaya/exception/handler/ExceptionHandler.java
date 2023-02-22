package com.salimcankaya.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salimcankaya.exception.CustomerNotFoundException;
import com.salimcankaya.exception.DuplicateTcknException;


@RestControllerAdvice
public class ExceptionHandler {
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
	protected ResponseEntity<Map<String, String>> customerNotFoundException(CustomerNotFoundException e) {
		
		Map<String, String> error = new HashMap<>();
		error.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DuplicateTcknException.class)
	protected ResponseEntity<Map<String, String>> duplicateTcknException(DuplicateTcknException e) {
		
		Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
