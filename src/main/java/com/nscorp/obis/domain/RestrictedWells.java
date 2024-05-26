package com.nscorp.obis.domain;

import java.util.stream.Stream;

public enum RestrictedWells {
	
	A(0), B(1), C(2), D(3), E(4);
	
	private final int value;

	private RestrictedWells(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
	public static RestrictedWells of(Integer value) {

    	return Stream.of(RestrictedWells.values()).filter(d -> d.getValue().equals(value))
    			.findFirst()
    			.orElseThrow(IllegalArgumentException::new);
	}

}
