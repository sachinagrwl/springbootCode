package com.nscorp.obis.domain;

public enum EquipmentTypeRestrict {

    CONTAINER("C"), FLATCAR("F");

    private final String code;

    private EquipmentTypeRestrict(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
