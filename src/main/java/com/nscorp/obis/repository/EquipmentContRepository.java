package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EquipmentContPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentCont;

import java.math.BigDecimal;

@Repository
public interface EquipmentContRepository extends JpaRepository <EquipmentCont, EquipmentContPrimaryKeys>{


	boolean existsByContainerInit(String equipInit);


	boolean existsByContainerInitAndContainerNbr(String carInit, BigDecimal carNbr);
}
