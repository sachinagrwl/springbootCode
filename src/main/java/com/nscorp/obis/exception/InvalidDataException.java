package com.nscorp.obis.exception;

import org.springframework.http.ResponseEntity;

public class InvalidDataException extends RuntimeException{
	private ResponseEntity<ErrorResponse> errorResponse;
	private static final long serialVersionUID = 1L;
	
	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(){

	}

	public ResponseEntity<ErrorResponse> getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ResponseEntity<ErrorResponse> errorResponse) {
		this.errorResponse = errorResponse;
	}
}
