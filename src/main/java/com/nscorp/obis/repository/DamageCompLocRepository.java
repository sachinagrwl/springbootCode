package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.domain.DamageCompLocPrimaryKeys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageCompLocRepository extends JpaRepository<DamageCompLoc, DamageCompLocPrimaryKeys> {
    boolean existsByDamageArea_AreaCd(String areaCd);

	void deleteByDamageArea_AreaCd(String areaCd);

	boolean existsByJobCode(Integer jobCode);


	List<DamageCompLoc> findByDamageComponent_JobCodeAndDamageArea_AreaCdIgnoreCase(Integer jobCode, String areaCode);
	List<DamageCompLoc> findByDamageComponent_JobCode(Integer jobCode);
	List<DamageCompLoc> findByDamageArea_AreaCdIgnoreCase(String areaCode);

	boolean existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCdAndUversion(String compLocCode,
			Integer jobCode, String areaCd, String uversion);

	boolean existsByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(String compLocCode,
																						 Integer jobCode, String areaCd);
	
	DamageCompLoc findByDamageArea_AreaCd(String areaCd);

	List<DamageCompLoc> findByJobCode(Integer jobCode);

	DamageCompLoc findByCompLocCodeAndDamageComponent_JobCodeAndDamageArea_AreaCd(String compLocCode, Integer jobCode,
			String areaCd);
}
