package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DamageLocationConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageLocationConversionRepository extends JpaRepository<DamageLocationConversion, String> {
    boolean existsByLocDescAndUversion(String locDesc, String uversion);
    DamageLocationConversion findByLocDescAndUversion(String locDesc, String uversion);
	boolean existsByLocDscrAndUversion(String locDscr, String uversion);
	DamageLocationConversion findByLocDscr(String locDscr);
	boolean existsByLocDscr(String locDscr);
	DamageLocationConversion findByLocDscrAndUversion(String locDscr, String uversion);
}
