package com.salimcankaya.exception;


public class DuplicateTcknException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static final String GENERIC_MESSAGE = "Provided tckn already exists!";

    public DuplicateTcknException() {
        super(GENERIC_MESSAGE);
    }
}