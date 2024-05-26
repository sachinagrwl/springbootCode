package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentHitch;
import com.nscorp.obis.domain.EquipmentHitchPrimaryKey;

@Repository
public interface EquipmentHitchRepository extends JpaRepository<EquipmentHitch, EquipmentHitchPrimaryKey> {

	List<EquipmentHitch> findByCarInitAndCarNbrAndCarEquipType(String carInit, BigDecimal carNbr, String carEquipType);

}
