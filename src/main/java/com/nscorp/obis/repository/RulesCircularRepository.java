package com.nscorp.obis.repository;

import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.domain.RulesCircularPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulesCircularRepository extends JpaRepository<RulesCircular, RulesCircularPrimaryKeys> {

    List<RulesCircular> findAllByOrderByEquipmentTypeAscEquipmentLengthAsc();

    boolean existsByEquipmentTypeAndEquipmentLength(String equipmentType, Integer equipmentLength);

    RulesCircular findByEquipmentTypeAndEquipmentLength(String equipmentType, Integer equipmentLength);
    
    void deleteByEquipmentTypeAndEquipmentLength(String equipmentType, Integer equipmentLength);
}
