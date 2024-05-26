package com.nscorp.obis.exception;

import org.springframework.http.ResponseEntity;

;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoRecordsFoundException extends RuntimeException {

    private ResponseEntity<ErrorResponse> errorResponse;
    private static final long serialVersionUID = 1L;


    public NoRecordsFoundException(String message) {
        super(message);
    }

    public NoRecordsFoundException() {

    }

	public ResponseEntity<ErrorResponse> getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ResponseEntity<ErrorResponse> errorResponse) {
		this.errorResponse = errorResponse;
	}
}
