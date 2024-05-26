package com.nscorp.obis.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.domain.DamageLocationCompositePrimaryKeys;

@Repository
public interface DamageLocationRepository extends JpaRepository<DamageLocation, DamageLocationCompositePrimaryKeys> {
    
    boolean existsByCatCdAndLocCd(Integer catCd, Integer locCd);

	Optional<DamageLocation> findByCatCdAndLocCd(Integer catCd, Integer locCd);

	void deleteByCatCdAndLocCd(Integer catCd, Integer locCd);

}
