package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.FhwaStopRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface FhwaStopRangeRepository extends JpaRepository<FhwaStopRange, Long>, CommonKeyGenerator {

    @Query(value = "SELECT fhwaStopRange from FhwaStopRange fhwaStopRange " +
            "where (fhwaStopRange.equipmentInit like CONCAT(upper(:equipmentInit),'%') or :equipmentInit is null) " +
            "AND (fhwaStopRange.equipmentNumberLow = :equipmentNumberLow or :equipmentNumberLow is null) " +
            "AND (fhwaStopRange.equipmentNumberHigh = :equipmentNumberHigh or :equipmentNumberHigh is null) " +
            "AND (fhwaStopRange.equipmentType like CONCAT(upper(:equipmentType),'%') or :equipmentType is null) " +
            "Order By fhwaStopRange.equipmentInit ASC, fhwaStopRange.equipmentType ASC, fhwaStopRange.equipmentNumberLow ASC, fhwaStopRange.equipmentNumberHigh ASC"
    )
    List<FhwaStopRange> findAll(String equipmentInit, String equipmentType, BigDecimal equipmentNumberLow,
                                BigDecimal equipmentNumberHigh);

    FhwaStopRange findByRangeId(Long rangeId);

    boolean existsByEquipmentTypeAndEquipmentInit(String equipmentType, String equipmentInit);

    boolean existsByEquipmentTypeAndEquipmentInitAndEquipmentNumberLowAndEquipmentNumberHigh(String equipmentType, String equipmentInit, BigDecimal eqNrLow, BigDecimal eqNrHigh);

    List<FhwaStopRange> findByEquipmentTypeAndEquipmentInit(String equipmentType, String equipmentInit);
}
