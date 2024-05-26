package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.domain.EquipmentAAR600ContPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EquipmentAAR600ContRepository extends JpaRepository<EquipmentAAR600Cont, EquipmentAAR600ContPrimaryKeys> {

	boolean existsByEquipInitAndBeginningEqNrAndEndEqNbr(String equipInit, BigDecimal beginningEqNr,
			BigDecimal endEqNbr);

	List<EquipmentAAR600Cont> findAllByOrderByEquipInit();

	void deleteByEquipInitAndBeginningEqNrAndEndEqNbr(String equipInit, BigDecimal beginningEqNr, BigDecimal endEqNbr);

//    List<EquipmentAAR600Cont> findAll();
}
