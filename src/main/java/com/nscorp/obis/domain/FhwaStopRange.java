package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FHWA_STOP_RANGE")
public class FhwaStopRange extends AuditInfo{
    @Id
    @Column(name = "RANGE_ID", columnDefinition = "Double", nullable = false)
    private Long rangeId;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
    private String equipmentType;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_LOW_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentNumberLow;

    @Column(name = "EQ_HIGH_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentNumberHigh;

    public Long getRangeId() {
        return rangeId;
    }

    public void setRangeId(Long rangeId) {
        this.rangeId = rangeId;
    }

    public String getEquipmentType() {
        if(equipmentType != null) {
            return equipmentType.trim();
        }
        else {
            return equipmentType;
        }
    }

    public void setEquipmentType(String equipmentType) {
        if(equipmentType != null){
            this.equipmentType = equipmentType.toUpperCase();
        }
        else {
            this.equipmentType = equipmentType;
        }
    }

    public String getEquipmentInit() {
        if(equipmentInit != null) {
            return equipmentInit.trim();
        }
        else {
            return equipmentInit;
        }
    }

    public void setEquipmentInit(String equipmentInit) {
        if(equipmentInit != null)
            this.equipmentInit = equipmentInit.toUpperCase();
        else
            this.equipmentInit = equipmentInit;
    }

    public BigDecimal getEquipmentNumberLow() {
        return equipmentNumberLow;
    }

    public void setEquipmentNumberLow(BigDecimal equipmentNumberLow) {
        this.equipmentNumberLow = equipmentNumberLow;
    }

    public BigDecimal getEquipmentNumberHigh() {
        return equipmentNumberHigh;
    }

    public void setEquipmentNumberHigh(BigDecimal equipmentNumberHigh) {
        this.equipmentNumberHigh = equipmentNumberHigh;
    }
    public FhwaStopRange(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long rangeId, String equipmentType, String equipmentInit, BigDecimal equipmentNumberLow, BigDecimal equipmentNumberHigh) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.rangeId = rangeId;
        this.equipmentType = equipmentType;
        this.equipmentInit = equipmentInit;
        this.equipmentNumberLow = equipmentNumberLow;
        this.equipmentNumberHigh = equipmentNumberHigh;
    }

    public FhwaStopRange() {
        super();
    }
}