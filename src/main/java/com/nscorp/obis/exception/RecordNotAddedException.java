package com.nscorp.obis.exception;

public class RecordNotAddedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RecordNotAddedException(String message) {
		super(message);
		
	}

	public RecordNotAddedException() {
	}
}
