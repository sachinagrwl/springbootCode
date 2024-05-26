package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import com.nscorp.obis.common.CommonKeyGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.UmlerLowProfileCar;

@Repository
public interface UmlerLowProfileCarRepository extends JpaRepository<UmlerLowProfileCar, Long>, CommonKeyGenerator {
	@Query(value = "SELECT umlerCar from UmlerLowProfileCar umlerCar "
			+ "where (umlerCar.aarType like CONCAT(upper(:aarType),'%') or :aarType is null) "
			+ "AND (umlerCar.carInit like CONCAT(upper(:carInit),'%') or :carInit is null) "
			+ "Order By umlerCar.aarType ASC, umlerCar.aar1stNoLow ASC, umlerCar.aar2ndNoLow ASC, umlerCar.aar3rdNo ASC, "
			+ "umlerCar.carInit ASC, umlerCar.carLowNr ASC, umlerCar.carHighNr ASC")
	List<UmlerLowProfileCar> findAllByAarTypeAndCarInit(String aarType, String carInit);

	boolean existsByUmlerIdAndUversion(Long umlerId, String uversion);

    boolean existsByAarType(String aarType);

	boolean existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(String aarCd, String aar1stNoLow, String aar1stNoHigh, String aar2ndNoLow, String aar2ndNoHigh);

	boolean existsByCarInitAndCarLowNrAndCarHighNr(String carInit, BigDecimal carLowNr, BigDecimal carHighNr);

	Long SGKLong();
}
