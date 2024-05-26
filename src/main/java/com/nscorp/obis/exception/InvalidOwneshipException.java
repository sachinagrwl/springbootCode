package com.nscorp.obis.exception;

public class InvalidOwneshipException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	

    public InvalidOwneshipException(String message) {
        super(message);
    }
    public InvalidOwneshipException() {
    }

}
