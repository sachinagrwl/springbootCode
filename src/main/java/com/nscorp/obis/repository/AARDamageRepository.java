package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.AARDamage;

public interface AARDamageRepository extends JpaRepository<AARDamage, Integer>{

	boolean existsByJobCode(Integer aarJobCode);

	List<AARDamage> findAllByOrderByJobCodeAsc();

}
