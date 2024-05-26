package com.nscorp.obis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LENGTH_REQUIRED)
public class SizeExceedException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public SizeExceedException(String message) {
        super(message);
    }

    public SizeExceedException() {

    }
}
