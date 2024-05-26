package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@SuppressWarnings("serial")
public class EquipmentAAR600ContPrimaryKeys implements Serializable {

    private String equipInit;

    private BigDecimal beginningEqNr;

    private BigDecimal endEqNbr;

    public EquipmentAAR600ContPrimaryKeys(String equipInit, BigDecimal beginningEqNr, BigDecimal endEqNbr) {
        super();
        this.equipInit = equipInit;
        this.beginningEqNr = beginningEqNr;
        this.endEqNbr = endEqNbr;
    }

    public EquipmentAAR600ContPrimaryKeys() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipInit,beginningEqNr,equipInit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        EquipmentAAR600ContPrimaryKeys equipmentAAR600ContPrimaryKeys = (EquipmentAAR600ContPrimaryKeys) obj;

        return Objects.equals(equipInit, equipmentAAR600ContPrimaryKeys.equipInit)
                && Objects.equals(beginningEqNr, equipmentAAR600ContPrimaryKeys.beginningEqNr)
                && Objects.equals(equipInit, equipmentAAR600ContPrimaryKeys.endEqNbr);
    }
}
