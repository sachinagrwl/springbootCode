package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
@SuppressWarnings("serial")
public class PoolEquipmentConflictRangePrimaryKeys implements Serializable {
    private Pool pool;

    private Terminal terminal;

    private String rangeType;

    private String rangeInit;

    private BigDecimal rangeLowNumber;

    private BigDecimal rangeHighNumber;

    private Long poolRangeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoolEquipmentConflictRangePrimaryKeys that = (PoolEquipmentConflictRangePrimaryKeys) o;
        return pool.equals(that.pool) && terminal.equals(that.terminal) && rangeType.equals(that.rangeType) && rangeInit.equals(that.rangeInit) && rangeLowNumber.equals(that.rangeLowNumber) && rangeHighNumber.equals(that.rangeHighNumber) && poolRangeId.equals(that.poolRangeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pool, terminal, rangeType, rangeInit, rangeLowNumber, rangeHighNumber, poolRangeId);
    }

    public PoolEquipmentConflictRangePrimaryKeys() {
        super();
    }

    public PoolEquipmentConflictRangePrimaryKeys(Pool pool, Terminal terminal, String rangeType, String rangeInit, BigDecimal rangeLowNumber, BigDecimal rangeHighNumber, Long poolRangeId) {
        this.pool = pool;
        this.terminal = terminal;
        this.rangeType = rangeType;
        this.rangeInit = rangeInit;
        this.rangeLowNumber = rangeLowNumber;
        this.rangeHighNumber = rangeHighNumber;
        this.poolRangeId = poolRangeId;
    }
}

