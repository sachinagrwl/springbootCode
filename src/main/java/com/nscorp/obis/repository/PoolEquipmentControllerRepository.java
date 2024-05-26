package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.PoolEquipmentController;

import java.util.List;

public interface PoolEquipmentControllerRepository extends JpaRepository<PoolEquipmentController, Long>, CommonKeyGenerator {

	boolean existsByEquipmentTypeAndCustomerPrimary6(String equipmentType, String customerPrimary6);

	boolean existsByPoolId(Long poolId);

	boolean existsByPoolControllerIdAndUversion(Long poolControllerId, String uversion);

    List<PoolEquipmentController> findByPoolId(Long poolId);

	boolean existsByPoolIdAndEquipmentTypeAndCustomerPrimary6(Long poolId, String equipmentType,
			String customerPrimary6);
}
