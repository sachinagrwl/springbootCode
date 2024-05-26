package com.nscorp.obis.domain;

public enum EMSAllotmentType {
	
	DAILY("D"), FIXED("F");

    private final String code;

    private EMSAllotmentType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
	
}
