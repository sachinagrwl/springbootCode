package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.EquipmentRestrict;

@Repository
public interface EquipmentRestrictRepository extends JpaRepository<EquipmentRestrict, Long>, CommonKeyGenerator {


    EquipmentRestrict findByRestrictionId(Long restrictionId);

    boolean existsByRestrictionId(Long restrictionId);

    void deleteByRestrictionId(Long restrictionId);
}
