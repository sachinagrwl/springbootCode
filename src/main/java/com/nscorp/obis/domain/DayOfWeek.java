package com.nscorp.obis.domain;

import java.util.stream.Stream;

public enum DayOfWeek {
    SUN(0), MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);

    private final int value;
    private DayOfWeek(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    public static DayOfWeek of(Integer value) {

    	return Stream.of(DayOfWeek.values()).filter(d -> d.getValue().equals(value))
    			.findFirst()
    			.orElseThrow(IllegalArgumentException::new);
    }
}

