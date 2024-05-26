package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenancePrimaryKeys;
import com.nscorp.obis.dto.EquipmentInitialSpeedCodeMaintenanceDTO;

@Repository
public interface EquipmentInitialSpeedCodeMaintenanceRepository extends JpaRepository<EquipmentInitialSpeedCodeMaintenance, EquipmentInitialSpeedCodeMaintenancePrimaryKeys> {

	@Query(value = "SELECT eqInitSpeedCode " +
			"from EquipmentInitialSpeedCodeMaintenance eqInitSpeedCode " +
			"where (eqInitSpeedCode.eqInitShort = :eqInitShort or :eqInitShort is null) " +
			"AND eqInitSpeedCode.eqType = 'C' " +
			"Order By eqInitSpeedCode.eqInit ASC"
			)
	
	List<EquipmentInitialSpeedCodeMaintenance> findAll(String eqInitShort);

	EquipmentInitialSpeedCodeMaintenance findByEqTypeAndEqInitShort(String eqType, String equipInit);

	boolean existsByEqTypeAndEqInitShort(String eqType, String equipInit);

	@Query("SELECT eqsdcd.eqInit from EquipmentInitialSpeedCodeMaintenance eqsdcd where eqsdcd.eqType = :eqType and eqsdcd.eqInitShort = :equipInit")
	String getEquipmentInit(String eqType, String equipInit);
	
}
