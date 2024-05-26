package com.nscorp.obis.repository;

import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.domain.EquipmentTareWeightPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentDefaultTareWeightMaintenanceRepository extends JpaRepository<EquipmentDefaultTareWeightMaintenance, EquipmentTareWeightPrimaryKeys> {


	boolean existsByEqTpAndEqLgth(String eqTp, Integer eqLgth);

	List<EquipmentDefaultTareWeightMaintenance> findByEqTp(String equipmentType);

	EquipmentDefaultTareWeightMaintenance findByEqLgthAndEqTp(Integer eqLgth, String eqTp);

	void deleteByEqTpAndEqLgth(String eqTp, Integer eqLgth);
}
