package com.nscorp.obis.domain;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "POOL_RANGE_CON_V")
@Immutable
@IdClass(value = PoolEquipmentConflictRangePrimaryKeys.class)
public class PoolEquipmentConflictRange {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POOL_ID", referencedColumnName = "POOL_ID", insertable = false, updatable = false)
    private Pool pool;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
    private Terminal terminal;

    @Id
    @Column(name = "RANGE_TP", columnDefinition = "char(1)", nullable = false)
    private String rangeType;

    @Id
    @Column(name = "RANGE_INIT", columnDefinition = "char(1)", nullable = false)
    private String rangeInit;

    @Id
    @Column(name = "RANGE_LOW_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal rangeLowNumber;

    @Id
    @Column(name = "RANGE_HIGH_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal rangeHighNumber;

    @Id
    @Column(name = "RANGE_POOL_ID", columnDefinition = "double", nullable = false)
    private Long poolRangeId;

    public Pool getPool() {
        return pool;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public String getRangeType() {
        return rangeType;
    }

    public String getRangeInit() {
        return rangeInit;
    }

    public BigDecimal getRangeLowNumber() {
        return rangeLowNumber;
    }

    public BigDecimal getRangeHighNumber() {
        return rangeHighNumber;
    }

    public Long getPoolRangeId() {
        return poolRangeId;
    }

    public PoolEquipmentConflictRange() {
        super();
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public void setRangeInit(String rangeInit) {
        this.rangeInit = rangeInit;
    }

    public void setRangeLowNumber(BigDecimal rangeLowNumber) {
        this.rangeLowNumber = rangeLowNumber;
    }

    public void setRangeHighNumber(BigDecimal rangeHighNumber) {
        this.rangeHighNumber = rangeHighNumber;
    }

    public void setPoolRangeId(Long poolRangeId) {
        this.poolRangeId = poolRangeId;
    }

    public PoolEquipmentConflictRange(Pool pool, Terminal terminal, String rangeType, String rangeInit, BigDecimal rangeLowNumber, BigDecimal rangeHighNumber, Long poolRangeId) {
        this.pool = pool;
        this.terminal = terminal;
        this.rangeType = rangeType;
        this.rangeInit = rangeInit;
        this.rangeLowNumber = rangeLowNumber;
        this.rangeHighNumber = rangeHighNumber;
        this.poolRangeId = poolRangeId;
    }
}
