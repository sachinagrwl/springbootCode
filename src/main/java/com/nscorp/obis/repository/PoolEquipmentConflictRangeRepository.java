package com.nscorp.obis.repository;

import com.nscorp.obis.domain.PoolEquipmentConflictRange;
import com.nscorp.obis.domain.PoolEquipmentConflictRangePrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PoolEquipmentConflictRangeRepository extends JpaRepository<PoolEquipmentConflictRange, PoolEquipmentConflictRangePrimaryKeys> {
    boolean existsByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(Long poolId, String equipmentType, String equipmentInit, BigDecimal equipmentLowNumber, BigDecimal equipmentHighNumber);

    PoolEquipmentConflictRange findByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(Long poolId, String equipmentType, String equipmentInit, BigDecimal equipmentLowNumber, BigDecimal equipmentHighNumber);
}
