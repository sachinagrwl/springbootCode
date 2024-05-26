package com.nscorp.obis.response.data;

public enum ResponseStatusCode {

    SUCCESS (100),
    FAILURE (120),
    INFORMATION (110),
    FAILURE_OPTIMISTICLOCK (121);

    private int statusCode;

    ResponseStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
