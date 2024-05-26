package com.nscorp.obis.exception;

public enum ErrorCodes {

    VALIDATIONERROR (5000),
    SIZE (5001),
    RANGE (5001),
    MAX(5001),
    DIGITS (5001),
    MIN (5001),
    NOTBLANK (5002),
    NOTNULL (5002),
    DELETEERROR(5004);


    public int getStatusCode() {
        return statusCode;
    }

    private int statusCode;


    ErrorCodes(int statusCode) {
        this.statusCode = statusCode;
    }


}
