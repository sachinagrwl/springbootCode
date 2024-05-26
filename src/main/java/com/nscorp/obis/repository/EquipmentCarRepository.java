package com.nscorp.obis.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.domain.EquipmentCarPrimaryKey;

@Repository
public interface EquipmentCarRepository extends JpaRepository<EquipmentCar, EquipmentCarPrimaryKey> {

	EquipmentCar findByCarInitAndCarNbrAndCarEquipType(String carInit, BigDecimal carNbr,
			String carEquipType);

    boolean existsByCarInitAndCarNbrAndCarEquipType(String carInit, BigDecimal carNbr, String carEquipType);

	boolean existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(String carInit, BigDecimal carNbr, String carEquipType,
			String uversion);
}
