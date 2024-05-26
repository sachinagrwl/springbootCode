package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nscorp.obis.domain.ConventionalEquipmentWidth;
import com.nscorp.obis.domain.ConventionalEquipmentWidthPrimaryKey;

public interface ConventionalEquipmentWidthRepository extends JpaRepository<ConventionalEquipmentWidth, ConventionalEquipmentWidthPrimaryKey> {

    void deleteByUmlerId(Long umlerId);

    boolean existsByUmlerId(Long umlerId);

    boolean existsByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);

    ConventionalEquipmentWidth findByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);

    @Query(value = "SELECT convWidth.aar1stNr  FROM ConventionalEquipmentWidth convWidth where convWidth.umlerId = :umlerId")
	List<String> findAar1stNRByUmlerId(Long umlerId);
    
    void deleteByUmlerIdAndAar1stNr(Long umlerId, String aar1stNr);

	List<ConventionalEquipmentWidth> findByUmlerId(Long umlerId);
}
