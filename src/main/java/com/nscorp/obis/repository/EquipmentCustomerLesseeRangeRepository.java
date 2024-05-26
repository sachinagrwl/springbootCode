package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;

public interface EquipmentCustomerLesseeRangeRepository extends JpaRepository<EquipmentCustomerLesseeRange, Long> {

	Page<EquipmentCustomerLesseeRange> findByEquipmentInitAndCorporateCustomer_CorporateLongName(String equipmentInit,
			String corporateLongName, Pageable pageable);

	Page<EquipmentCustomerLesseeRange> findByEquipmentInit(String equipmentInit, Pageable pageable);

	Page<EquipmentCustomerLesseeRange> findByCorporateCustomer_CorporateLongName(String corporateLongName,
			Pageable pageable);

	boolean existsByEquipmentCustomerRangeIdAndUversion(Long equipmentCustomerRangeId, String uversion);

	@Query("SELECT DISTINCT Lessee.equipmentInit FROM EquipmentCustomerLesseeRange Lessee")
	List<String> findAllDistinctEquipmentInit();

	@Query("SELECT DISTINCT Lessee.equipmentLesseeId FROM EquipmentCustomerLesseeRange Lessee")
	List<Long> findAllDistinctCorporateLongName();

}
