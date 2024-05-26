package com.nscorp.obis.exception;

public class RecordNotDeletedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RecordNotDeletedException(String message) {
		super(message);
		
	}

	public RecordNotDeletedException() {
	}
}