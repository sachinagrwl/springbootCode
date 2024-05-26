package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum NSTimeZone {

    EST_TIMEZONE(17),
    CST_TIMEZONE(18),
    MST_TIMEZONE(19),
    PST_TIMEZONE(20);

	private final Integer code;

    private NSTimeZone(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    public static NSTimeZone of(Integer value) {
    	NSTimeZone[] type = NSTimeZone.values();
    	List<NSTimeZone> tupeList =Arrays.asList(type);
    	for(NSTimeZone terminalType :tupeList) {
    		if(terminalType.getCode().equals(value)) {
    			return terminalType;
    			
    		}
    	}
    	return null;
    }
}

