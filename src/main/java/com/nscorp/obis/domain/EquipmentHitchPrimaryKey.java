package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuppressWarnings("serial")
public class EquipmentHitchPrimaryKey implements Serializable {

    @Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = false)
    private String carInit;

    @Column(name = "CAR_NR", columnDefinition = "decimal", nullable = false)
    private BigDecimal carNbr;

    @Column(name = "CAR_TP", columnDefinition = "char(1)", nullable = false)
    private String carEquipType;

    @Column(name = "HCH_LOC", columnDefinition = "char(2)", nullable = false)
    private String hitchLocation;

    public EquipmentHitchPrimaryKey(String carInit, BigDecimal carNbr, String carEquipType, String hitchLocation) {
        super();
        this.carInit = carInit;
        this.carNbr = carNbr;
        this.carEquipType = carEquipType;
        this.hitchLocation = hitchLocation;
    }

    public EquipmentHitchPrimaryKey() {
        super();
        // TODO Auto-generated constructor stub
    }
}