package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.PoolEquipmentRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoolEquipmentRangeRepository extends JpaRepository<PoolEquipmentRange, Long>, CommonKeyGenerator {
    boolean existsByPoolId(Long poolId);

    boolean existsByPoolRangeId(Long poolRangeId);

    boolean existsByPoolRangeIdAndUversion(Long poolRangeId, String uversion);

    PoolEquipmentRange findByPoolRangeIdAndUversion(Long poolRangeId, String uversion);

    boolean existsByPoolIdAndEquipmentInitAndEquipmentType(Long poolId, String equipmentInit, String equipmentType);

    List<PoolEquipmentRange> findByPoolIdAndEquipmentInitAndEquipmentType(Long poolId, String equipmentInit, String equipmentType);

    List<PoolEquipmentRange> findByPoolId(Long poolId);
}
