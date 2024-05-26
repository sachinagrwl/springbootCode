package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.AARLocationCode;

public interface AARLocationCodeRepository extends JpaRepository <AARLocationCode, String>{

	boolean existsByLocCd(String locCd);

	boolean existsByLocCdAndUversion(String locCd, String uVersion);

	AARLocationCode findByLocCd(String locCd);

	
}
