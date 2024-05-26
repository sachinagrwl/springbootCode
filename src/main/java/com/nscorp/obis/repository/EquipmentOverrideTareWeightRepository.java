package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import com.nscorp.obis.common.CommonKeyGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentOverrideTareWeight;

@Repository
public interface EquipmentOverrideTareWeightRepository extends JpaRepository<EquipmentOverrideTareWeight, Long>, CommonKeyGenerator {

	@Query(value = "SELECT eqOverrideTareWeight " + 
			"from EquipmentOverrideTareWeight eqOverrideTareWeight " +
			"where (eqOverrideTareWeight.equipmentInit like CONCAT(upper(:equipmentInit),'%') or :equipmentInit is null) " +
			"AND (eqOverrideTareWeight.equipmentNumberLow = :equipmentNumberLow or :equipmentNumberLow is null) " +
			"AND (eqOverrideTareWeight.equipmentNumberHigh = :equipmentNumberHigh or :equipmentNumberHigh is null) " +
			"AND (eqOverrideTareWeight.equipmentType like CONCAT(upper(:equipmentType),'%') or :equipmentType is null) " +
			"AND (eqOverrideTareWeight.overrideTareWeight = :overrideTareWeight or :overrideTareWeight is null) " +
			"Order By eqOverrideTareWeight.equipmentInit ASC, eqOverrideTareWeight.equipmentNumberLow ASC, eqOverrideTareWeight.equipmentNumberHigh ASC, eqOverrideTareWeight.equipmentLength ASC, eqOverrideTareWeight.equipmentType ASC "
			)
	
	List<EquipmentOverrideTareWeight> findAll(String equipmentInit, BigDecimal equipmentNumberLow,
			BigDecimal equipmentNumberHigh, String equipmentType, Integer overrideTareWeight);


	boolean existsByOverrideId(Long overrideId);

	void deleteByOverrideId(Long overrideId);

	EquipmentOverrideTareWeight findByOverrideId(Long overrideId);

	boolean existsByEquipmentInit(String equipmentInit);

	List<EquipmentOverrideTareWeight> findByEquipmentInit(String equipmentInit);
}
