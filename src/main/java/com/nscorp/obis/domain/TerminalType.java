package com.nscorp.obis.domain;

import java.util.Arrays;
import java.util.List;

public enum TerminalType {

    SYSTEM ("S"),
    FOREIGN ("F"),
    CONRAIL ("C"),
    PHASED_GATING ("P"),
    HYPHEN ("-");

	 private final String code;

	    private TerminalType(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
	    public static TerminalType of(String value) {
	    	TerminalType[] type = TerminalType.values();
	    	List<TerminalType> tupeList =Arrays.asList(type);
	    	for(TerminalType terminalType :tupeList) {
	    		if(terminalType.getCode().equals(value)) {
	    			return terminalType;
	    			
	    		}
	    	}
	    	return null;
	    }
	}

