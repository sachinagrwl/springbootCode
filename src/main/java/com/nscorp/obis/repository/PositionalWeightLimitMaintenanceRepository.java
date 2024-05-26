package com.nscorp.obis.repository;

import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;
import com.nscorp.obis.domain.PositionalWeightLimitPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PositionalWeightLimitMaintenanceRepository extends JpaRepository<PositionalWeightLimitMaintenance, PositionalWeightLimitPrimaryKeys> {
			
	List<PositionalWeightLimitMaintenance> findByCarInit(String car_init);

//	List<PositionalWeightLimitMaintenance> findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(String carInit,
//			Double carNrLow, Double carNrHigh, String carEquipmentType);
	
	/* This Method Is Used To Add Values */
	boolean existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(String carInit, Long carNrLow, Long carNrHigh, String carEquipmentType);

	/* This Method Is Used To Update Values */
	PositionalWeightLimitMaintenance findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(String carInit,
			Long carNrLow, Long carNrHigh, String carEquipmentType);
	
	/* This Method Is Used To Delete Values */
	void deleteByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(String carInit, Long carNrLow, Long carNrHigh,
			String carEquipmentType);
	
	/*This method is used in the Junit Delete Functionality*/
//	OngoingStubbing<List<PositionalWeightLimitMaintenance>> findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(
//			Class<PositionalWeightLimitMaintenance> class1);

	

}
