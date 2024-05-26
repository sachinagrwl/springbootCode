package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.LowProfileEquipmentWidth;
import com.nscorp.obis.domain.LowProfileEquipmentWidthPrimaryKey;

public interface LowProfileEquipmentWidthRepository extends JpaRepository<LowProfileEquipmentWidth, LowProfileEquipmentWidthPrimaryKey>{

	boolean existsByUmlerId(Long umlerId);

	void deleteByUmlerId(Long umlerId);

    boolean existsByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);

	LowProfileEquipmentWidth findByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);
}
