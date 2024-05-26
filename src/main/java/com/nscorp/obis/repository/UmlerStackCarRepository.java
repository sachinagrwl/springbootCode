package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.domain.UmlerStackCar;

@Repository
public interface UmlerStackCarRepository extends JpaRepository<UmlerStackCar, Long>, CommonKeyGenerator {
	@Query(value = "SELECT umlerCar from UmlerStackCar umlerCar "
			+ "where (umlerCar.aarType like CONCAT(upper(:aarType),'%') or :aarType is null) "
			+ "AND (umlerCar.carInit like CONCAT(upper(:carInit),'%') or :carInit is null) "
			+ "Order By umlerCar.aarType ASC, umlerCar.aar1stNoLow ASC, umlerCar.aar2ndNoLow ASC, umlerCar.aar3rdNo ASC, "
			+ "umlerCar.carInit ASC, umlerCar.carLowNr ASC, umlerCar.carHighNr ASC")
	List<UmlerStackCar> findAllByAarTypeAndCarInit(String aarType, String carInit);

	 boolean existsByUmlerIdAndUversion(Long umlerId, String uversion);
	 UmlerConventionalCar findByUmlerId(Long umlerId);

	boolean existsByAarType(String aarType);

	boolean existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(String aarCd, String aar1stNoLow,
			String aar1stNoHigh, String aar2ndNoLow, String aar2ndNoHigh);

	boolean existsByCarInitAndCarLowNrAndCarHighNr(String carInit, BigDecimal carLowNr, BigDecimal carHighNr);
}
