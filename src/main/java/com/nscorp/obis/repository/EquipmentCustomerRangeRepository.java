package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EquipmentCustomerRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

public interface EquipmentCustomerRangeRepository extends JpaRepository<EquipmentCustomerRange, Long> {

    @Query("select equipCustRange from EquipmentCustomerRange equipCustRange " +
            "where (equipCustRange.equipmentInit = :carInit " +
            "and equipCustRange.equipmentLowNumber <= :carNbr) " +
            "and equipCustRange.equipmentHighNumber >= :carNbr")
    EquipmentCustomerRange getEquipmentCustomerRange(String carInit, BigDecimal carNbr);

    boolean existsByEquipmentInit(String carInit);
    
    //boolean existsByEquipment(String equipInit);
}
