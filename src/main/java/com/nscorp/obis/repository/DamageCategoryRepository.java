package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.DamageCategory;

@Repository
public interface DamageCategoryRepository extends JpaRepository<DamageCategory, Integer>{

	boolean existsByCatCd(Integer catCd);

	List<DamageCategory> findByCatCd(Integer catCd);

	void deleteByCatCd(Integer catCd);

	boolean existsByCatCdAndUversion(Integer catCd, String uVersion);

}