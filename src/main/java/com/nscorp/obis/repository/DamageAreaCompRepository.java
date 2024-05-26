package com.nscorp.obis.repository;

import com.nscorp.obis.domain.DamageAreaComp;
import com.nscorp.obis.domain.DamageAreaComponentPrimaryKeys;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageAreaCompRepository extends JpaRepository<DamageAreaComp, DamageAreaComponentPrimaryKeys> {

	boolean existsByAreaCd(String areaCd);

	List<DamageAreaComp> findByJobCodeAndAreaCdIgnoreCase(Integer jobCode, String areaCode, Sort sort);

	List<DamageAreaComp> findByJobCode(Integer jobCode,Sort sort);

	List<DamageAreaComp> findByAreaCdIgnoreCase(String areaCode,Sort sort);

	List<DamageAreaComp> findAll(Sort sort);

	DamageAreaComp findByAreaCdAndJobCode(String areaCd, Integer jobCode);
	boolean existsByJobCodeAndAreaCdAndOrderCode(Integer jobCode, String areaCd, String orderCode);
}
