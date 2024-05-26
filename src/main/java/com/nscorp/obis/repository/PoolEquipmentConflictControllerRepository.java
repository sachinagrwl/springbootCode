package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.PoolEquipmentConflictController;
import com.nscorp.obis.domain.PoolEquipmentConflictControllerPrimaryKeys;

public interface PoolEquipmentConflictControllerRepository extends JpaRepository<PoolEquipmentConflictController, PoolEquipmentConflictControllerPrimaryKeys> {

	boolean existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(String equipmentType, String customerPrimary6,
			Long poolId);

	List<PoolEquipmentConflictController> findByControllerTypeAndCustomerPrimary6AndPoolControllerId(String equipmentType,
			String customerPrimary6, Long poolId);

}
