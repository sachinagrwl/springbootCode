package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.EquipmentOwnerPrefix;

public interface EquipmentOwnerPrefixRepository extends JpaRepository<EquipmentOwnerPrefix, Double> {

	boolean existsByEquipInit(String equipInit);

	void deleteByEquipInit(String equipInit);
	
	EquipmentOwnerPrefix findByEquipInit(String equipInit);

}
