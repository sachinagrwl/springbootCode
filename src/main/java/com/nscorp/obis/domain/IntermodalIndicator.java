package com.nscorp.obis.domain;

public enum IntermodalIndicator {
    SYSTEM("S"),
    OFFLINE("O");

    private final String code;

    private IntermodalIndicator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}