package com.nscorp.obis.domain;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "POOL_EQ_RANGE")
public class PoolEquipmentRange extends AuditInfo {
    @Id
    @Column(name = "POOL_RANGE_ID", columnDefinition = "double", nullable = false)
    private Long poolRangeId;

    @Column(name = "POOL_ID", columnDefinition = "double", nullable = false)
    private Long poolId;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_LOW_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentLowNumber;

    @Column(name = "EQ_HIGH_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentHighNumber;

    public Long getPoolRangeId() {
        return poolRangeId;
    }

    public void setPoolRangeId(Long poolRangeId) {
        this.poolRangeId = poolRangeId;
    }

    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public String getEquipmentType() {
        if(equipmentType != null) {
            return equipmentType.trim();
        }
        else {
            return null;
        }
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentInit() {
        if(equipmentInit != null) {
            return equipmentInit.trim();
        }
        else {
            return null;
        }
    }

    public void setEquipmentInit(String equipmentInit) {
        this.equipmentInit = equipmentInit;
    }

    public BigDecimal getEquipmentLowNumber() {
        return equipmentLowNumber;
    }

    public void setEquipmentLowNumber(BigDecimal equipmentLowNumber) {
        this.equipmentLowNumber = equipmentLowNumber;
    }

    public BigDecimal getEquipmentHighNumber() {
        return equipmentHighNumber;
    }

    public void setEquipmentHighNumber(BigDecimal equipmentHighNumber) {
        this.equipmentHighNumber = equipmentHighNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PoolEquipmentRange that = (PoolEquipmentRange) o;
        return getPoolRangeId() != null && Objects.equals(getPoolRangeId(), that.getPoolRangeId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}