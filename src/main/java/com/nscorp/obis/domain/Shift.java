package com.nscorp.obis.domain;

import java.util.stream.Stream;

public enum Shift {
    FIRST(1), SECOND(2), THIRD(3);

    private final Integer value;

    private Shift(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    
    public static Shift getShiftByValue(Integer value) {
    	return Stream.of(Shift.values()).filter(s -> s.getValue().equals(value))
    			.findFirst()
    			.orElseThrow(IllegalArgumentException::new);
    }
}
