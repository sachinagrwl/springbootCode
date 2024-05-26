package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.StackEquipmentWidth;
import com.nscorp.obis.domain.StackEquipmentWidthPrimaryKey;

public interface StackEquipmentWidthRepository
		extends JpaRepository<StackEquipmentWidth, StackEquipmentWidthPrimaryKey> {

	void deleteByUmlerId(Long umlerId);

	boolean existsByUmlerId(Long umlerId);

	List<StackEquipmentWidth> findByUmlerId(Long umlerId);
}
