package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class RulesCircularPrimaryKeys implements Serializable {

    private String equipmentType;

    private Integer equipmentLength;


    public RulesCircularPrimaryKeys(String equipmentType, Integer equipmentLength) {
        super();
        this.equipmentType = equipmentType;
        this.equipmentLength = equipmentLength;
    }

    public RulesCircularPrimaryKeys() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (o == null || getClass() != o.getClass())
            return false;
        RulesCircularPrimaryKeys that = (RulesCircularPrimaryKeys) o;
        return Objects.equals(equipmentType, that.equipmentType) &&
               Objects.equals(equipmentLength, that.equipmentLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentType, equipmentLength);
    }
}
