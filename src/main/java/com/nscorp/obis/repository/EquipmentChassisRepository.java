package com.nscorp.obis.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentChassis;
import com.nscorp.obis.domain.EquipmentChassisPrimaryKey;

@Repository
public interface EquipmentChassisRepository extends JpaRepository<EquipmentChassis, EquipmentChassisPrimaryKey>{


	EquipmentChassis findByChasInitAndChasNbrAndChasTpAndChasId(String chasInit, BigDecimal chasNbr, String chasTp,
			String chasId);

	EquipmentChassis findByChasInitAndChasNbrAndChasEqTpAndChasId(String chasInit, BigDecimal chasNbr, String chasTp,
																String chasId);

    boolean existsByChasInitAndChasNbr(String carInit, BigDecimal carNbr);
}

