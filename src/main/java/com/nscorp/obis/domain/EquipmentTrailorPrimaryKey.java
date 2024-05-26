package com.nscorp.obis.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class EquipmentTrailorPrimaryKey implements Serializable {

    @Column(name = "TRLR_INIT", columnDefinition = "char(4)", nullable = false)
    private String trailorInit;

    @Column(name = "TRLR_NR", columnDefinition = "decimal", nullable = false)
    private BigDecimal trailorNumber;

    @Column(name = "TRLR_EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String trailorEquipType;

    @Column(name = "TRLR_ID", columnDefinition = "char(4)", nullable = false)
    private String trailorId;

    public EquipmentTrailorPrimaryKey(String trailorInit, BigDecimal trailorNumber, String trailorEquipType, String trailorId) {
        super();
        this.trailorInit = trailorInit;
        this.trailorNumber = trailorNumber;
        this.trailorEquipType = trailorEquipType;
        this.trailorId = trailorId;
    }

    public EquipmentTrailorPrimaryKey() {
        super();
    }
}
