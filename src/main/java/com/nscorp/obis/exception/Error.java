package com.nscorp.obis.exception;

import lombok.Data;

@Data
public class Error {

    public static String UNIVERSAL = Error.class.getName() + ".universal";
    private String entityName;
    private int errorCode;
    private String errorDescription;


    public Error(String entityName, int errorCode, String errorDescription) {
        this.entityName = entityName;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public Error(int errorCode, String errorDescription) {
//        this(UNIVERSAL, errorCode, errorDescription);
    	this(null, errorCode, errorDescription);
    }
}