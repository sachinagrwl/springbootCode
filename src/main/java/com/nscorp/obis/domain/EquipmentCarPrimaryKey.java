package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuppressWarnings("serial")
public class EquipmentCarPrimaryKey implements Serializable {

    @Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = false)
    private String carInit;

    @Column(name = "CAR_NR", columnDefinition = "decimal", nullable = false)
    private BigDecimal carNbr;

    @Column(name = "CAR_EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String carEquipType;

    public EquipmentCarPrimaryKey(String carInit, BigDecimal carNbr, String carEquipType) {
        super();
        this.carInit = carInit;
        this.carNbr = carNbr;
        this.carEquipType = carEquipType;
    }

    public EquipmentCarPrimaryKey() {
        super();
        // TODO Auto-generated constructor stub
    }
}