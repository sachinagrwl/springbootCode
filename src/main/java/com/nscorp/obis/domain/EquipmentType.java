package com.nscorp.obis.domain;

public enum EquipmentType {
	
	CONTAINER("C"), TRAILER("T"), CHASSIS("Z"), FLATCAR("F"), GENSET("G");

    private final String code;

    private EquipmentType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
